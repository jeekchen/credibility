package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.account.User;
import models.form.Field;
import models.form.Form;
import models.service.Provider;
import models.service.Requirement;
import models.service.ServiceModel;
import models.system.Area;
import models.system.Trade;

import org.bson.types.ObjectId;

import play.Logger;
import play.cache.Cache;
import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.data.validation.URL;
import play.modules.morphia.MorphiaPlugin;
import play.mvc.Before;
import play.mvc.Finally;
import play.mvc.With;
import utils.DateUtil;
import utils.StringUtil;

import com.google.code.morphia.annotations.Reference;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

@With(Secure.class)
public class Admin extends SessionController {
	private final static Pattern pattern = Pattern
			.compile("\\$\\{(now|user|provider|service)\\(['|\"]([\\w|_|-]+)['|\"]\\)\\}");

	@Before
	public static void before() {
		long t1 = System.currentTimeMillis();
		Cache.set(session.getId() + "_before_time", t1);
		Logger.info("[%s] %s before", session.get("username"),
				request.actionMethod);
	}

	@Finally
	public static void after() {
		long t1 = Cache.get(session.getId() + "_before_time", Long.class);
		long t2 = System.currentTimeMillis();
		Logger.info("[%s] %s finally：%d毫秒", session.get("username"),
				request.actionMethod, (t2 - t1));
	}

	public static void index() {
		List<ServiceModel> serviceModels = ServiceModel.findAll();
		List<Area> areas = Area.findAll();
		List<Trade> trades = Trade.findAll();
		render(serviceModels, areas, trades);
	}

	public static void push(String serviceModelId) {
		if (serviceModelId != null) {
			ServiceModel model = ServiceModel.findById(serviceModelId);
			List<Field> fields = model.queryForm.fields;

			BasicDBObject qry = new BasicDBObject();
			for (Field field : fields) {
				String value = params.get(field.name);
				if (value != null && value.trim().length() > 0) {
					Pattern pattern = Pattern.compile("^.*" + value + ".*$",
							Pattern.CASE_INSENSITIVE);
					qry.append(field.name, pattern);
				}
			}
			Cache.set(session.getId() + "_service_qry", qry);
			render(serviceModelId, fields);
		}
		render(serviceModelId);
	}
	
	public static void requirement(String trade, String area, String providerName){
		render(trade, area, providerName);
	}
	
	public static void feedback() {
		render();
	}

	public static void getQueryForm(String modelId) {
		Form form = null;
		if (modelId!=null){
			ServiceModel model = ServiceModel.findById(modelId);		
			if (model != null) {			
				form = model.queryForm;
			}
		}
		renderJSON(form);
	}
	
	public static void queryProvider(String trade, String area, String providerName,  Integer page,
			Integer rows) {
		if (page == null || page < 1)
			page = 1;

		Long count = Provider.countProviders(trade, area, providerName);		
		Integer totalPage = 0;
		if (count > 0) {
			totalPage = (int) Math.ceil(count * 1.0 / rows);
		}
		if (page > totalPage)
			page = totalPage;

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", totalPage);
		result.put("page", page);
		result.put("records", count);
		
		List<Provider> providers = Provider.findProviders(trade, area, providerName, page, rows);
		ArrayList<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		for (Provider provider : providers){
			Map<String, Object> providerMap = new LinkedHashMap<String, Object>();
			providerMap.put("providerId", provider.getId().toString());
			providerMap.put("name", provider.name);
			providerMap.put("linkName", provider.linkName);
			providerMap.put("linkTitle", provider.linkTitle);
			providerMap.put("telNum", provider.telNum);
			providerMap.put("fax", provider.fax);
			providerMap.put("address", provider.address);
			providerMap.put("url", provider.url);
			providerMap.put("email", provider.email);
			providerMap.put("desc", provider.desc);
			providerMap.put("area", provider.area);
			providerMap.put("trade", provider.trade);
			providerMap.put("created", DateUtil.format(new Date((Long)provider._getCreated()), "yyyy年MM月dd日 HH:mm"));
			records.add(providerMap);
		}
		result.put("rows", records);
		renderJSON(result);
	}
	
	public static void queryService(String serviceModelId, Integer page,
			Integer rows) {
		if (page == null || page < 1)
			page = 1;

		BasicDBObject qry = Cache.get(session.getId() + "_service_qry",
				BasicDBObject.class);

		if (serviceModelId == null || qry == null) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("total", 0);
			result.put("page", page);
			result.put("records", 0);
			result.put("rows", new ArrayList());
			renderJSON(result);
		}
		ServiceModel model = ServiceModel.findById(serviceModelId);

		DB db = MorphiaPlugin.ds().getDB();
		DBCollection coll = db.getCollection(model.serviceForm.name);

		Long count = coll.count(qry);
		DBCursor cursor = coll.find(qry).limit(rows);
		Integer totalPage = 0;

		if (count > 0) {
			totalPage = (int) Math.ceil(count * 1.0 / rows);
		}
		if (page > totalPage)
			page = totalPage;

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", totalPage);
		result.put("page", page);
		result.put("records", count);

		ArrayList<Map<String, Object>> records = new ArrayList<Map<String, Object>>();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("_id", ((ObjectId) obj.get("_id")).toString());
			map.put("_created", DateUtil.format(new Date((Long)obj.get("_created")), "yyyy年MM月dd日 HH:mm"));
			map.put("_modified", DateUtil.format(new Date((Long)obj.get("_modified")), "yyyy年MM月dd日 HH:mm"));
			
			map.put("model", model);

			DBRef providerRef = (DBRef) obj.get("provider");
			Provider providerObj = Provider.findById(providerRef.getId());
			map.put("provider", providerObj);
			map.put("providerId", providerObj.getId().toString());
			
			map.put("smsContent", getContent(model.smsTemplate, providerObj, obj));
			map.put("emailContent", getContent(model.emailTemplate, providerObj, obj));
			
			List<Field> fields = model.serviceForm.fields;
			for (Field field : fields) {
				map.put(field.name, obj.get(field.name));
			}
			records.add(map);
		}

		result.put("rows", records);
		renderJSON(result);
	}

	private static String getContent(String template, Provider provider,
			DBObject service) {
		String content = template;
		content = StringUtil.replaceAll(content, "${now}",
				DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		content = replaceValue(pattern, content, provider, service);
		return content;
	}

	private static String replaceValue(Pattern p, String str,
			Provider provider, DBObject service) {
		try {
			Matcher m = p.matcher(str);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String type = m.group(1);
				String var = m.group(2);

				String replaceText = null;
				if (type.equalsIgnoreCase("now")) {
					replaceText = DateUtil.format(new Date(), var);
				} else if (type.equalsIgnoreCase("user")) {
					User user = getLoginUser();
					if (var.equalsIgnoreCase("userName"))
						replaceText = user.userName;
					else if (var.equalsIgnoreCase("email"))
						replaceText = user.email;
					else if (var.equalsIgnoreCase("fullName"))
						replaceText = user.fullName;
					else if (var.equalsIgnoreCase("telNum"))
						replaceText = user.telNum;
				} else if (type.equalsIgnoreCase("provider")) {
					//name,linkName,linkTitle,telNum,fax,address,url,email,desc,area,trade
					if (var.equalsIgnoreCase("name"))
						replaceText = provider.name;
					else if (var.equalsIgnoreCase("linkName"))
						replaceText = provider.linkName;
					else if (var.equalsIgnoreCase("linkTitle"))
						replaceText = provider.linkTitle;
					else if (var.equalsIgnoreCase("telNum"))
						replaceText = provider.telNum;
					else if (var.equalsIgnoreCase("fax"))
						replaceText = provider.fax;
					else if (var.equalsIgnoreCase("address"))
						replaceText = provider.address;
					else if (var.equalsIgnoreCase("url"))
						replaceText = provider.url;
					else if (var.equalsIgnoreCase("email"))
						replaceText = provider.email;
					else if (var.equalsIgnoreCase("desc"))
						replaceText = provider.desc;
					else if (var.equalsIgnoreCase("area"))
						replaceText = provider.area.name;
					else if (var.equalsIgnoreCase("trade"))
						replaceText = provider.trade.name;
				}else if (type.equalsIgnoreCase("service")) {
					replaceText = (String) service.get(var);
				}
				if (replaceText != null)
					m.appendReplacement(sb, replaceText);
				else
					throw new Exception(String.format("无法在\"%s\"中匹配\" %s\"",
							type, var));
			}
			m.appendTail(sb);
			return sb.toString();
		} catch (Exception e) {
			Logger.warn(e, e.getMessage());
		}
		return str;
	}

	public static void subscription() {
		String serviceModelId = params.get("serviceModelId");
		String providerId = params.get("providerId");
		String serviceId = params.get("serviceId");
		String overTime = params.get("overTime");

		ServiceModel model = ServiceModel.findById(serviceModelId);
		User user = getLoginUser();

		DB db = MorphiaPlugin.ds().getDB();
		DBCollection coll = db.getCollection(model.subscribeForm.name);

		BasicDBObject doc = new BasicDBObject();
		doc.put("model", new DBRef(db, "ServiceModel", model.getId()));
		doc.put("provider", new DBRef(db, "Provider", new ObjectId(providerId)));
		doc.put("service", new DBRef(db, model.serviceForm.name, new ObjectId(
				serviceId)));
		doc.put("user", new DBRef(db, "User", user.getId()));
		doc.put("overTime", overTime);
		doc.put("status", 1);
		List<Field> fields = model.subscribeForm.fields;
		for (Field field : fields) {
			doc.put(field.name, params.get(field.name));
		}
		doc.put("_created", System.currentTimeMillis());
		doc.put("_modified", System.currentTimeMillis());
		coll.insert(doc);

		renderJSON("{\"message\":\"订阅成功\"}");
	}

	public static void pushSms() {
		renderJSON("{\"message\":\"已经提交\"}");
	}

	public static void pushEmail() {
		renderJSON("{\"message\":\"已经提交\"}");
	}
	
	public static void getUserDatas(String term){		
		//需要判断当前是否为坐席权限，以免带来安全问题
		List<Map<String, Object>> users = User.getUsersByTerm(term);
		renderJSON(users);
	}
	
	public static void userCallin(User user){	
		if (user.isNew()){
			user.password = user.userName;
			user.role = getLoginUser().role;
		}
		if (user.validateAndSave()){
			session.put("callinUserName", user.userName);
			renderJSON(user);
		}else{			
			renderJSON(validation.errorsMap());	
		}
	}
	
	public static void getSubscription(String serviceModelId, String providerId){		
		ServiceModel model = ServiceModel.findById(serviceModelId);
		User user = getCallinUser();
		if (user==null){
			renderMsg("还没有呼入用户");
			return;
		}
		
		DB db = MorphiaPlugin.ds().getDB();
		DBCollection coll = db.getCollection(model.subscribeForm.name);

		BasicDBObject qry = new BasicDBObject();
		qry.put("model", new DBRef(db, "ServiceModel", model.getId()));
		qry.put("user", new DBRef(db, "User", user.getId()));
		qry.put("provider", new DBRef(db, "Provider", new ObjectId(providerId)));
		qry.put("status", 1);
		
		DBObject subscribe = coll.findOne(qry);
		Map<String, Object> subscribeMap = new HashMap<String, Object>();		
		if (subscribe!=null){
			for (Field field: model.subscribeForm.fields){
				subscribeMap.put(field.name, subscribe.get(field.name));
			}
			subscribeMap.put("id", ((ObjectId)subscribe.get("_id")).toString());
			subscribeMap.put("_created", DateUtil.format(new Date((Long)subscribe.get("_created")), "yyyy年MM月dd日 HH:mm"));
			subscribeMap.put("_modified", DateUtil.format(new Date((Long)subscribe.get("_modified")), "yyyy年MM月dd日 HH:mm"));
			subscribeMap.put("overTime", subscribe.get("overTime"));			
			
		}
		renderJSON(subscribeMap);
	}
	
	public static void addRequirement(Requirement requirement){	
		requirement.user = getCallinUser();
		if (requirement.user ==null){
			renderMsg("还没有呼入用户");
			return;
		}
		
		String providerId = params.get("providerId");
		requirement.provider = Provider.findById(providerId);
		if (requirement.provider ==null){
			renderMsg("还没有选择供应商");
			return;
		}
		
		if (requirement.validateAndSave()){			
			renderJSON(requirement);
		}else{			
			renderJSON(validation.errorsMap());	
		}
	}
}

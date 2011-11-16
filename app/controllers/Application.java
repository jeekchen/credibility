package controllers;

import java.util.ArrayList;
import java.util.List;

import models.form.Field;
import models.form.FieldType;
import models.form.Form;
import models.form.ImageField;
import models.form.NumberField;
import models.form.StringField;
import models.service.ModelType;
import models.service.Provider;
import models.service.ServiceModel;
import models.system.Area;
import models.system.Trade;

import org.bson.types.ObjectId;

import play.Logger;
import play.modules.morphia.MorphiaPlugin;
import play.mvc.Controller;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBRef;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void addServiceModel() {
		ServiceModel model = new ServiceModel();
		model.name = "企业招聘";
		model.description = "企业招聘";
		model.modelType = ModelType.subscribe;
		model.queryForm = getJobQueryForm();
		model.serviceForm = getJobServiceForm();
		model.save();

		model = new ServiceModel();
		model.name = "融资贷款";
		model.description = "融资贷款";
		model.modelType = ModelType.subscribe;
		model.queryForm = getForm("daikuan", "融资贷款");
		model.save();

		model = new ServiceModel();
		model.name = "政策传达";
		model.description = "政策传达";
		model.modelType = ModelType.subscribe;
		model.queryForm = getForm("chuangda", "政策传达");
		model.save();
		
	}
	
	private static Form getJobQueryForm(){
		Form form = new Form();
		form.caption = "企业招聘查询表单";
		form.name = "jobsQuery";
		List<Field> fields = new ArrayList<Field>();
		
		StringField field1 = new StringField();
		field1.name = "position";
		field1.label = "职位";
		field1.length = 50;
		field1.type = FieldType.string;
		field1.require = false;
		field1.helpText = "必须输入";
		fields.add(field1);
		
		StringField field2 = new StringField();
		field2.name = "description";
		field2.label = "职位描述";
		field2.length = 1024;
		field2.type = FieldType.string;
		field2.require = false;
		field2.helpText = "必须输入";
		fields.add(field2);
		
		form.fields = fields;
		form.save();
		return form;
	}
	
	private static Form getJobServiceForm(){
		Form form = new Form();
		form.caption = "企业招聘服务表单";
		form.name = "jobsService";
		List<Field> fields = new ArrayList<Field>();
		
		StringField field1 = new StringField();
		field1.name = "position";
		field1.label = "职位";
		field1.length = 50;
		field1.type = FieldType.string;
		field1.require = false;
		field1.helpText = "必须输入";
		fields.add(field1);
		
		StringField field2 = new StringField();
		field2.name = "description";
		field2.label = "职位描述";
		field2.length = 1024;
		field2.type = FieldType.string;
		field2.require = false;
		field2.helpText = "必须输入";
		fields.add(field2);
		
		form.fields = fields;
		form.save();
		return form;
	}
	
	private static Form getJobSubscribeForm(){
		Form form = new Form();
		form.caption = "企业招聘订阅表单";
		form.name = "jobsSubscribe";
		List<Field> fields = new ArrayList<Field>();
		
		StringField field1 = new StringField();
		field1.name = "graduateTime";
		field1.label = "毕业时间";
		field1.length = 19;
		field1.type = FieldType.string;
		field1.require = true;
		field1.helpText = "必须输入";
		fields.add(field1);
		
		StringField field4 = new StringField();
		field4.name = "school";
		field4.label = "毕业学校";
		field4.length = 50;
		field4.type = FieldType.string;
		field4.require = true;
		field4.helpText = "必须输入";
		fields.add(field4);
		
		StringField field2 = new StringField();
		field2.name = "education";
		field2.label = "学历";
		field2.length = 50;
		field2.type = FieldType.string;
		field2.require = false;
		field2.helpText = "必须输入";
		fields.add(field2);
		
		StringField field3 = new StringField();
		field3.name = "major";
		field3.label = "专业";
		field3.length = 50;
		field3.type = FieldType.string;
		field3.require = false;
		field3.helpText = "必须输入";
		fields.add(field3);
		
		form.fields = fields;
		form.save();
		return form;
	}
	
	private static Form getForm(String name, String caption){
		Form form = new Form();
		form.caption = caption;
		form.name = name;
		List<Field> fields = new ArrayList<Field>();
		for (int i = 0; i < 4; i++) {
			StringField field = new StringField();
			field.name = "field" + i;
			field.label = "字段" + i;
			field.length = 0;
			field.type = FieldType.string;
			field.require = true;
			field.helpText = "必须输入";
			fields.add(field);
		}
		form.fields = fields;
		form.save();
		return form;
	}
	
	public static void addForm() {
		Form form = new Form();
		form.caption = "油烟机维修2";
		form.name = "youyanji2";

		List<Field> fields = new ArrayList<Field>();
		for (int i = 0; i < 10; i++) {
			StringField field = new StringField();
			field.name = "field" + i;
			field.label = "字段" + i;
			field.length = 0;
			field.type = FieldType.string;
			field.require = true;
			field.helpText = "必须输入";
			fields.add(field);
		}
		ImageField field = new ImageField();
		field.name = "image";
		field.label = "油烟机图片";
		field.type = FieldType.image;
		field.require = true;
		field.helpText = "必须输入";
		field.maxWidth = 100;
		field.maxHeight = 100;
		fields.add(field);

		form.fields = fields;
		form.save();

		renderJSON(form);
	}
	
	public  static  void example(){
		Area area1 = new Area();
		area1.code = "010";
		area1.level = 1;
		area1.name = "北京";
		area1.parent = null;
		area1.save();
		
		Area area2 = new Area();
		area2.code = "021";
		area2.level = 1;
		area2.name = "上海";
		area2.parent = null;
		area2.save();
		
		Trade trade1 = new Trade();
		trade1.code = "1001";
		trade1.level = 1;
		trade1.name = "互联网/IT";
		trade1.parent = null;
		trade1.save();
		
		Trade trade2 = new Trade();
		trade2.code = "1002";
		trade2.level = 1;
		trade2.name = "房地产";
		trade2.parent = null;
		trade2.save();
		
		Provider provider1 = new Provider();
		provider1.address = "北京市蓝靛厂南路25号";
		provider1.area = area1;
		provider1.email = "jeekchen@gmail.com";
		provider1.fax = "010-88846688";
		provider1.linkName = "陈锦";
		provider1.linkTitle = "总经理";
		provider1.name = "恒信移动股份有限公司";
		provider1.telNum = "010-88846616";
		provider1.url = "http://www.hxgro.com";
		provider1.trade = trade1;
		provider1.desc = "恒信移动商务股份有限公司成立于2001年，专注于移动信息产品的销售与服务，为个人客户和行业客户提供从硬件到软件、从销售到服务的全面解决方案。公司于2010年在深交所创业板成功上市（简称“恒信移动”，300081），是国内唯一一家既有专业地面数码零售连锁业务，又有移动信息技术研发和业务平台运营经验的企业。 ";
		provider1.save();
		
		Provider provider2 = new Provider();
		provider2.address = "上海市南京路25号";
		provider2.area = area2;
		provider2.email = "liulaixi@gmail.com";
		provider2.fax = "021-88846688";
		provider2.linkName = "刘来喜";
		provider2.linkTitle = "总经理";
		provider2.name = "上海刘氏股份有限公司";
		provider2.telNum = "021-88846616";
		provider2.url = "http://www.liushi.com";
		provider2.trade = trade2;
		provider2.desc = "上海刘氏股份有限公司成立于2001年，专注于房地产的销售与服务，为个人客户和行业客户提供从硬件到软件、从销售到服务的全面解决方案。公司于2010年在深交所创业板成功上市（简称“恒信移动”，300081），是国内唯一一家既有专业地面数码零售连锁业务，又有移动信息技术研发和业务平台运营经验的企业。 ";
		provider2.save();
		
		ServiceModel model = new ServiceModel();
		model.name = "企业招聘";
		model.description = "企业招聘";
		model.modelType = ModelType.subscribe;
		model.queryForm = getJobQueryForm();
		model.serviceForm = getJobServiceForm();
		model.subscribeForm = getJobSubscribeForm();
		model.smsTemplate = "${user(\"fullName\")}您好，${provider(\"name\")}公司正在招聘\"${service(\"position\")}\"职位，联系电话:${provider(\"telNum\")}";
		model.emailTemplate = "${user(\"fullName\")},您好!\r\n${provider(\"name\")}公司正在招聘\"${service(\"position\")}\"职位，有意请联系电话:${provider(\"telNum\")}\r\n本邮件由系统自动发出，请勿进行回复！\r\nBDA服务推送平台\r\n${now}";		
		model.save();		
		/*       	
		Service service = new Service();
		service.model = model;
		service.provider = provider1;
		service.stauts = 1;
		service.save();*/
		
		DB db = MorphiaPlugin.ds().getDB() ;
		DBCollection coll = db.getCollection(model.serviceForm.name);
		
		BasicDBObject doc1 = new BasicDBObject();	
		doc1.put("model", new DBRef(db, "ServiceModel", model.getId()));
		doc1.put("provider", new DBRef(db, "Provider", provider1.getId()));
		doc1.put("status", 1);
		doc1.put("_created", System.currentTimeMillis());
		doc1.put("_modified", System.currentTimeMillis());		   
		doc1.put("position", "销售经理");
		doc1.put("description", "负责公司业务销售，底薪+提成+奖金，挑战年薪1000w，你敢来我们就敢给！给自己一个释放真我的机会，COME ON！");
       	coll.insert(doc1);
	    
       	BasicDBObject doc2 = new BasicDBObject();		
       	doc2.put("model", new DBRef(db, "ServiceModel", model.getId()));
		doc2.put("provider", new DBRef(db, "Provider", provider2.getId()));
		doc2.put("status", 1);
		doc2.put("_created", System.currentTimeMillis());
		doc2.put("_modified", System.currentTimeMillis());		 
       	doc2.put("position", "研发经理");
       	doc2.put("description", "负责公司研发部门工作，底薪+提成+奖金，挑战年薪1000w，你敢来我们就敢给！给自己一个释放真我的机会，COME ON！");
       	coll.insert(doc2);
	}
	
	public static void batchAddJob(){
		long t1 = System.currentTimeMillis();
		DB db = MorphiaPlugin.ds().getDB() ;
		DBCollection coll = db.getCollection("jobsService");
		
		DBRef modelRef = new DBRef(db, "ServiceModel", new ObjectId("4eb4062f44ae083a8134c14b"));
		DBRef providerRef1 = new DBRef(db, "Provider", new ObjectId("4eb4062f44ae083a8134c147"));
		DBRef providerRef2 = new DBRef(db, "Provider", new ObjectId("4eb4062f44ae083a8134c148"));
		
		for (int i=0; i<1000000; i++){	
			DBRef providerRef = providerRef1;
			if (i%2==0)
				providerRef = providerRef2;
			DBObject doc = BasicDBObjectBuilder.start()
					.add("model", modelRef)
					.add("provider", providerRef)
					.add("status", 1)
					.add("_created", System.currentTimeMillis())
					.add("_modified", System.currentTimeMillis())		   
					.add("position", "销售经理"+i)
					.add("description", "负责公司业务销售，底薪+提成+奖金，挑战年薪1000w，你敢来我们就敢给！给自己一个释放真我的机会，COME ON！")
					.get();
		       coll.insert(doc);
		}

       	//coll.createIndex(new BasicDBObject("position", -1));
       	
		long t2 = System.currentTimeMillis();
		Logger.info("插入时间：%d秒", (t2-t1)/1000);
	}
}
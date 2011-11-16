package models.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import models.system.Area;
import models.system.Trade;

import org.bson.types.ObjectId;

import play.Logger;
import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.data.validation.URL;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

@Entity
@AutoTimestamp
public class Provider extends Model{
	@Required
	public String name;
	public String linkName;
	public String linkTitle;
	@Phone
	public String telNum;
	@Phone
	public String fax;
	public String address;
	@URL
	public String url;
	@Email
	public String email;
	public String desc;
	public File license;
	
	@Reference
	public Area area;
	@Reference
	public Trade trade;
	
	public static List<Provider> findProviders(String trade, String area, String providerName, int page, int size){
		Pattern pattern = Pattern.compile("^.*" + providerName + ".*$",
				Pattern.CASE_INSENSITIVE);
		String keys = "name";
		List<Object> objs = new ArrayList<Object>();
		objs.add(pattern);
		if (trade!=null && trade.trim().length()>0){
			if (!keys.endsWith(","))
				keys+=",";
			keys += "trade";
			objs.add(Trade.findById(new ObjectId(trade)));
		}		
		
		if (area!=null && area.trim().length()>0){
			if (!keys.endsWith(","))
				keys+=",";
			keys += "area";
			objs.add(Area.findById(new ObjectId(area)));
		}
		List<Provider> providers =  Provider.find(keys, objs.toArray()).fetch(page, size);
		return providers;
	}
	
	public static Long countProviders(String trade, String area, String providerName){
		Pattern pattern = Pattern.compile("^.*" + providerName + ".*$",
				Pattern.CASE_INSENSITIVE);
		String keys = "name";
		List<Object> objs = new ArrayList<Object>();
		objs.add(pattern);
		if (trade!=null && trade.trim().length()>0){
			if (!keys.endsWith(","))
				keys+=",";
			keys += "trade";
			objs.add(Trade.findById(new ObjectId(trade)));
		}		
		
		if (area!=null && area.trim().length()>0){
			if (!keys.endsWith(","))
				keys+=",";
			keys += "area";
			objs.add(Area.findById(new ObjectId(area)));
		}
		return  Provider.find(keys, objs.toArray()).count();
	}
}

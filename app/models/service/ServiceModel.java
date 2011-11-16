package models.service;

import java.util.List;

import models.form.Form;
import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;


@Entity
@AutoTimestamp
public class ServiceModel extends Model{
	@Required
	public String name;
	public String description;
	
	/**
	 * 邮件发送模板，支持环境变量预定义
	 * 环境变量组成：${name("param")}
	 * name可选值有：now（当前时间），user（当前登陆用户,坐席员登陆时为所服务的用户），service（当前服务内容），provider（当前的供应商）
	 * now对应的param为日期格式串，如${now("yyyy-MM-dd")},支持没有参数的形式${now}默认按照yyyy-MM-dd HH:mm:ss来格式化， user的param包含userName,fullName,email,telNum, service的param包含服务模型中定义的所有属性，provider的param包含name,linkName,linkTitle,telNum,fax,address,url,email,desc,area,trade
	 */
	public String emailTemplate;	
	
	public String smsTemplate;	
	
	@Required
	public ModelType modelType;
	
	@Reference
	public Form serviceForm;	//服务内容表单
	
	@Reference
	public Form queryForm;		//查询表单
	
	@Reference	
	public Form subscribeForm;	//订阅表单
	
}

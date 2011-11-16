package models.service;

import models.account.User;
import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

@Entity
@AutoTimestamp
public class Requirement extends Model{
	@Required
	public String subject;
	@Required
	public String content;
	
	@Reference
	public User user;
	
	@Reference
	public Provider provider;
}

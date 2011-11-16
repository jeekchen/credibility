package models.service;

import models.account.User;
import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

@Entity
@AutoTimestamp
public class Verify extends Model{
	@Required
	public Integer result;
	public String cause;
	
	@Reference
	public User verifyUser;
}

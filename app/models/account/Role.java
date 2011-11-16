package models.account;

import java.util.List;

import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

@Entity
@AutoTimestamp
public class Role extends Model{
	@Required
	public String name;
	@Required
	public String flag;
	
	@Reference
	public List<Permission> permissions;
}

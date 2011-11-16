package models.account;

import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

@Entity
@AutoTimestamp
public class PermissionCategory extends Model{
	@Required
	public String name;
	
	@Reference
	public PermissionCategory parent;
}

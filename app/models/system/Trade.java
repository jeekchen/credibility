package models.system;

import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

@Entity
@AutoTimestamp
public class Trade extends Model{
	@Required
	public String name;
	@Required
	public String code;
	public Integer level;
	
	@Reference
	public Area parent;
}

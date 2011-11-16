package models.form;

import java.util.List;

import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

@Entity
@AutoTimestamp
public class Form extends Model{
	@Required
	public String name;
	
	@Required
	public String caption;
	
	@Embedded
	public List<Field> fields;
}

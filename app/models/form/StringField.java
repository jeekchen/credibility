package models.form;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class StringField extends Field{
	public Integer length;
}

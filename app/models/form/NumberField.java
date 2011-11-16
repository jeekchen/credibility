package models.form;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class NumberField extends Field{
	public Integer length;
	public Integer scale = 0;
}

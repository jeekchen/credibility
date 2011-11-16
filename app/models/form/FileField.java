package models.form;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class FileField extends Field{
	public Integer maxSize;
	public String formatLimit;	
}

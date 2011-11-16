package models.form;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class ImageField extends FileField{
	public Integer maxWidth;
	public Integer maxHeight;
}

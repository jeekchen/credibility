package models.form;

import play.data.validation.Required;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Field {
	@Required
	public String name;			//名称
	@Required
	public String label;			//标签
	public String helpText;		//帮助文本
	public Boolean readonly;	//是否只读
	public Boolean visiable;		//是否可见
	public Boolean require;		//是否必须输入
	public Object tag;			//可以随便放任意数据
	public FieldDisplay display;	//显示外观
	public FieldType type;		//类型
}

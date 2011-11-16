$.fn.dynamicFields = function(fields, settings){
	/*var options = jQuery.extend({
		nofound: "没有搜索条件",
		errorType1: ”错误的类型"
	}, settings);*/
	
	var $this = $(this);
	
	empty();
	
	if (!fields || fields.length==0){
		renderNoFound();
		return;
	} 
	
	var renderProcesser = {
		"string" : renderInput,
		"string_select"   : renderSelect,
		"string_range"   : renderInputRange,
		"bool" : renderCheckbox,
		"image" : renderFile,
		"file" : renderFile
	};
	
	$.each(fields, function(i,field){
		$this.append('<div class="content-space">');		
		var processer = renderProcesser[field.type];
    		if (processer){
			processer.call(this, field);	
		}else{
			alert(options.errorType+"\""+field.type+"\"");
		}		
		$this.append('</div>');		
	});
							
	function renderInput(field){
		var html = '<label for="'+field.name+'">'+field.label+'</label><input name="'+field.name+'" value=""  style="width:145px"/>';
		$this.append(html);
	}
	
	function renderCheckbox(field){
		var html = '<label for="'+field.name+'">'+field.label+'</label><input name="'+field.name+'" value=""  style="width:145px"/>';
		$this.append(html);
	}
	
	function renderText(field){
		var html = '<label for="'+field.name+'">'+field.label+'</label><input type="text" name="'+field.name+'" value=""  style="width:145px"/>';
		$this.append(html);
	}
	
	function renderHidden(field){
		var html = '<input type="hidden"  name="'+field.name+'" value=""  />';
		$this.append(html);
	}
	
	function renderSelect(field){
		var html = '<label for="'+field.name+'">'+field.label+'</label><input type="text" name="'+field.name+'" value=""  style="width:145px"/>';
		$this.append(html);
	}
	
	function renderInputRange(field){
		var html = '<label for="'+field.name+'">'+field.label+'</label><input type="text" name="'+field.name+'" value=""  style="width:145px"/>';
		$this.append(html);
	}
	
	function renderFile(field){
		var html = '<label for="'+field.name+'">'+field.label+'</label><input type="file" name="'+field.name+'" value=""  style="width:145px"/>';
		$this.append(html);
	}
	
	function renderNoFound(){
		var html = options.nofound;
		$this.append(html);
	}
	
	function empty(){
		$this.empty();;
	}
}

Date.prototype.format = function(format) {
  var o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(),    //day
    "h+" : this.getHours(),   //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
    "S" : this.getMilliseconds() //millisecond
  }
  if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
      RegExp.$1.length==1 ? o[k] :
        ("00"+ o[k]).substr((""+ o[k]).length));
  return format;
} 
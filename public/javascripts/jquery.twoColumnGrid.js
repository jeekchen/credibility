$.fn.twoColumnGrid = function(fields, data, settings){
	var options = jQuery.extend({
	
	}, settings);
	
	var $this = $(this);	
	empty();
	
	$.each(fields, function(i,field){
		var html = '<tr><td width="100">'+field.label+'</td><td>'+data[field.name]+'</td></tr>';		
		$this.append(html);		
	});
	
	function empty(){
		$this.empty();
	}	
	
}


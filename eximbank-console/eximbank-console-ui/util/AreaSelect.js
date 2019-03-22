
/**
 * 根据国家判断是否需要显示出省，市
 * @param c 国家id
 * @param p 省id
 * @param t 城市id
 */
function showCitySelect(c,p,t){
	var country = $("#"+c).val(); 
	//如果是中国，则显示
	if(country == 1){
		$("#"+p).show();
		$("#"+t).show();
	}else{
		$("#"+p).hide();
		$("#"+t).hide();
		//判断是否有省市标签
		var p_label = $("#"+p).prev().text();
		var c_label = $("#"+t).prev().text();
		if(p_label=='省'){
			$("#"+p).prev().hide();
		}
		if(c_label=='市'){
			$("#"+t).prev().hide();
		}
	}
}
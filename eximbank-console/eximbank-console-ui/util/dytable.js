
/**
 * 新增行
 * @param id
 */
function addRow(id,mtable,leastNum){
	var table = $("#"+mtable);
	var size = table.find("tr").size();
	if(size < 11){
		var newRow = $(id).parent().parent().clone(true);
		var endTd = newRow.find("td:last-child").html();
		if(endTd.indexOf("l-del")==-1){
			var delBtn = getDelBtn(mtable,leastNum,'delRow');
			newRow.find("td:last-child").append(delBtn);
		}
		cleanText(newRow);
		//插入到添加行的下面
		$(id).parent().parent().after(newRow);
	}else{
		alert("最多只能10行!");
		return false;
	}
}

/**
 * 删除行
 * @param id
 */
function delRow(id,mtable,leastNum){
	if(window.confirm("您确认要删除吗?")){
		var table = $("#"+mtable);
		var size = table.find("tr").size();
		var min = 2;
		if(leastNum&&leastNum!=''){
			min = leastNum;
		}
		if(size > min){
			var selectedRow = $(id).parent().parent();
			if(selectedRow!=null){
				selectedRow.remove();
			}
		}
	}
}

/**
 * 获取删除按钮html
 * @param tableId
 * @returns {String}
 */
function getDelBtn(tableId,leastNum,funName){
	var delBtn = "<a class=\"l-del\" href=\"javascript:void(0)\" title=\"删除\" onclick=\""+funName+"(this,'"+tableId+"',"+leastNum+")\">-</a>";
	return delBtn;
}


/**
 * 清空行内容
 * @param newRow
 */
function cleanText(newRow){
	//所有输入框清空
	newRow.find(":text").each(function(i){
		$(this).val("");
	});
	//所有选择框重置
	newRow.find("select").val("");
}



/**
 * 重置下拉框
 * @param cid
 */
function reset_option(sid){
	$("#"+sid).html("");
	var opt = "<option value=''>请选择</option>";
	$("#"+sid).append(opt); 
}

/**
 * 生成option元素
 * @param name 显示名称
 * @param code 值
 * @returns {String}
 */
function getOption(name,code){
	var opt = "<option value='"+code+"'>"+name+"</option>";
	return opt;
}
/**
 * 清空下拉框
 * @param id
 */
function emptyOpt(id){
	var defaultOpt = "<option value=''>请选择</option>";
	$("#"+id).empty();
	$("#"+id).append(defaultOpt);
}
/**
 * 还原下拉框
 * @param thisBox
 */
function cleanOpt(thisBox){
	var defaultOpt = "<option value=''>请选择</option>";
	$(thisBox).empty();
	$(thisBox).append(defaultOpt);
}

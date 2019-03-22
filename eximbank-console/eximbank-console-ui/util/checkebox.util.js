/**
 * 获取checkbox选中项的值
 * @param name
 * @returns
 */
function getCheckBoxChecked(name) {
	var na = name == null ? "ids" : name;
	var size = $("input[name='" + na + "']:checked").size();
	if(size != 1) {
		alert("请选择并只能选择一项!");
		return false;
	}
	var cks = $("input[name='" + na + "']:checked");
	var id = cks.eq(0).val();
	return id;
}

//遍历所有的checkbox，如果有选中的，那么将选中的行的对象放到数组中  
function getCheckId() {
	//所有checkbox  
	var selectFlags = document.getElementsByName("ids");
	var j = 0;
	var id = null;
	for(var i = 0; i < selectFlags.length; i++) {
		if(selectFlags[i].checked) {
			id = selectFlags[i].value;
			j++;
		}
	}

	if(j == 0) {
		alert("请选择需要的数据！");
		return;
	}
	if(j > 1) {
		alert("只能选择一条数据！");
		return;
	}
	return id;
}

//遍历所有的checkbox，如果有选中的，那么将选中的行的对象放到数组中  
function getCheckItemByName(name) {
	//定义数组 
	var checkedItems = new Array();
	//所有checkbox  
	var selectFlags = document.getElementsByName(name);
	var j = 0;
	var id;
	for(var i = 0; i < selectFlags.length; i++) {
		if(selectFlags[i].checked) {
			id = selectFlags[i].value;
			checkedItems[j] = selectFlags[i].value;
			j++;
		}
	}

	if(j == 0) {
		alert("请选择需要的数据！");
		return;
	}
	if(j > 1) {
		alert("只能选择一条数据！");
		return;
	}
	return id;
}

function getCheckItems() {
	//所有checkbox  
	var selectFlags = document.getElementsByName("selectFlag");
	var j = 0;
	var id;
	for(var i = 0; i < selectFlags.length; i++) {
		if(selectFlags[i].checked) {
			id = selectFlags[i].value;
			j++;
		}
	}
	if(j == 0) {
		alert("请选择需要的数据！");
		return;
	}
	if(j > 1) {
		alert("只能选择一条数据！");
		return;
	}
	return id;
}

function getCheckArrays() {
	//定义数组 
	var checkedItems = new Array();
	//所有checkbox  
	var selectFlags = document.getElementsByName("selectFlag");
	var j = 0;
	for(var i = 0; i < selectFlags.length; i++) {
		if(selectFlags[i].checked) {
			checkedItems[j] = selectFlags[i].value;
			j++;
		}
	}
	if(j == 0) {
		alert("请选择需要的数据！");
		return;
	}
	return checkedItems;
}

/**
 * 至少选择一个项目
 * @returns {Boolean}
 */
function isLeastOneCheck() {
	var size = $("input:checked").size();
	if(size < 1) {
		alert("请至少选择一个项目!");
		return false;
	}
	return true;
}

/**
 * 添加table的td的单击选中checkbox事件
 * @param row
 */
function check_one_row_event(form, open) {
	if(open == true) {
		var selectForm = $("#" + form);
		//行点击选中事件
		selectForm.find("table").find("tr").each(function(i) {
			$(this).click(function() {
				selectForm.find("table").children().find(":checkbox").prop("checked", false);
				$(this).find(":checkbox").eq(0).prop("checked", true);
			});
		});
	}
}

/**
 * 描述：获取多选框的值
 * @param {Object} selectFlag
 */
function getCheckValue(selectFlag) {
	//定义数组 
	var checkedItems = new Array();
	//所有checkbox  
	var selectFlags = document.getElementsByName(selectFlag);
	var j = 0;
	for(var i = 0; i < selectFlags.length; i++) {
		if(selectFlags[i].checked) {
			checkedItems[j] = selectFlags[i].value;
			j++;
		}
	}
	if(j == 0) {
		alert("请选择需要的数据！");
		return;
	}
	return checkedItems;
}
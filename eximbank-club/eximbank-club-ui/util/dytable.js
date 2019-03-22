/**
 * 新增行
 * @param id
 */
function addRow(id, mtable, leastNum) {
	var table = $("#" + mtable);
	var size = table.find("tr").size();
	if(size < 10000000000) {
		var newRow = $(id).parent().parent().clone(true);
		var endTd = newRow.find("td:last-child").html();
		if(endTd.indexOf("l-del") == -1) {
			var delBtn = getDelBtn(mtable, leastNum, 'delRow');
			newRow.find("td:last-child").append(delBtn);
		}
		cleanText(newRow);
		//插入到添加行的下面
		$(id).parent().parent().after(newRow);
	} else {
		alert("最多只能10000000000行!");
		return false;
	}
}

/**
 * 删除行
 * @param id
 */
function delRow(id, mtable, leastNum) {
	confirm("提示","您确认要删除吗?",
		function(flag){
			var table = $("#" + mtable);
			var size = table.find("tr").size();
			var min = 2;
			if(leastNum && leastNum != '') {
				min = leastNum;
			}
			if(size > min) {
				var selectedRow = $(id).parent().parent();
				if(selectedRow != null) {
					selectedRow.remove();
				}
			}
		}
	)
}

/***
 * 动态新增表格
 * @param {Object} id
 * @param {Object} leastNum
 */
function addTable(id, divId, leastNum) {
	var divContainer = $("#" + divId);
	var size = divContainer.find(".table-editer-form").size();
	if(size < 10000000000) {
		var newTb = $(id).closest(".table-editer-form").clone(true);
		var delBtn = getTableDelBtn(divId, leastNum, 'delTable');
		//判断是否存在删除按钮
		if(!newTb.hasClass(".del")) {
			newTb.find(".add").after(delBtn);
		}
		//判断是否需要修改隐藏的元素的值(索引)
		if(newTb.find(":hidden[name='selectedGuaranteeIndex']") != null) {
			newTb.find("input[name='selectedGuaranteeIndex']").eq(0).val(size);
		}
		newTb.find(".add").remove();
		cleanTable(newTb);
		//插入到添加表的下面
		divContainer.append(newTb);
		return newTb;
	} else {
		alert("最多只能10000000000项!");
		return false;
	}
}

/**
 * 删除指定的表格
 * @param {Object} id
 * @param {Object} divId
 * @param {Object} leastNum
 */
function delTable(id, divId, leastNum) {
	confirm("提示","您确认要删除吗?",
	 	function(flag){
			var delDiv = $("#" + divId);
			var size = delDiv.find(".table-editer-form").size();
			var min = 1;
			if(leastNum && leastNum != '') {
				min = leastNum;
			}
			//数量不超限，则执行
			if(size > min) {
				var selectedTb = $(id).closest(".table-editer-form");
				if(selectedTb != null) {
					selectedTb.remove();
				}
			}
		}
	 )
}

/**
 * 删除指定的表格
 * @param {Object} id
 * @param {Object} divId
 * @param {Object} leastNum
 */
function delSubTable(id, delDiv, leastNum) {
	confirm("提示","您确认要删除吗?",
	 	function(flag){
			var size = delDiv.find(".table-editer-form2").size();
			var min = 1;
			if(leastNum && leastNum != '') {
				min = leastNum;
			}
			//数量不超限，则执行
			if(size > min) {
				var selectedTb = $(id).closest(".table-editer-form2");
				if(selectedTb != null) {
					selectedTb.remove();
				}
			}
		}
	)
}

/**
 * 功能：重置表格
 * @param {Object} delDiv
 * @param {Object} formClass
 * @param {Object} leastNum
 */
function delAllTable(delDiv,formClass,leastNum) {
	var size = delDiv.find("."+formClass).size();
	var min = 1;
	if(leastNum && leastNum != '') {
		min = leastNum;
	}
	//数量不超限，则执行
	for(var i=size;i>min;i--){
		delDiv.find("."+formClass).eq(i-1).remove();
	}
}

/**
 * 删除表格按钮html
 * @param divId
 * @returns {String}
 */
function getTableDelBtn(divId, leastNum, funName) {
	var delBtn = "<img title=\"刪除\" class=\"u14222_img del\" src=\"res/img/u1628.png\" onclick=\"" + funName + "(this,'" + divId + "'," + leastNum + ")\">";
	//var delBtn = "<button class=\"btn-cyan el-button el-button--primary is-plain del\" style=\"margin-left:5px;\" title=\"删除\" onclick=\""+funName+"(this,'"+divId+"',"+leastNum+")\">-删除</button>";
	return delBtn;
}

/**
 * 删除表格按钮html
 * @param divId
 * @returns {String}
 */
function getTableDelHtml(divTb, leastNum, funName) {
	var delBtn = "<img title=\"刪除\" class=\"u14222_img del\" src=\"res/img/u1628.png\" onclick=\"" + funName + "(this," + divTb + "," + leastNum + ")\">";
	return delBtn;
}

/**
 * 获取删除按钮html
 * @param tableId
 * @returns {String}
 */
function getDelBtn(tableId, leastNum, funName) {
	var delBtn = "<button class=\"btn-cyan el-button el-button--primary is-plain\"  title=\"删除\" onclick=\"" + funName + "(this,'" + tableId + "'," + leastNum + ")\">-删除</button>";
	return delBtn;
}

/**
 * 清空行内容
 * @param newRow
 */
function cleanText(newRow) {
	//所有输入框清空
	newRow.find(":text").each(function(i) {
		$(this).val("");
	});
	newRow.find("input[type=number]").each(function(i) {
		$(this).val("");
	});
	newRow.find("textarea").each(function(i) {
		$(this).val("");
	});
	
	//所有选择框重置
	newRow.find("select").val("");
}

/**
 * 清除验证成功的提示图标
 * @param {Object} newRow
 */
function cleanWarnText(newRow){
	//所有输入框清空
	newRow.find(".vali-success").each(function(i) {
		$(this).remove();
	});
}

/**
 * 重置整个table
 * @param {Object} newTab
 */
function cleanTable(newTab) {
	newTab.find("tr").each(function(i) {
		var newRow = $(this);
		cleanText(newRow);
		cleanWarnText(newRow);
	});
}

/**
 * 重置下拉框
 * @param cid
 */
function reset_option(sid) {
	$("#" + sid).html("");
	var opt = "<option value=''>请选择</option>";
	$("#" + sid).append(opt);
}

/**
 * 生成option元素
 * @param name 显示名称
 * @param code 值
 * @returns {String}
 */
function getOption(name, code) {
	var opt = "<option value='" + code + "'>" + name + "</option>";
	return opt;
}
/**
 * 清空下拉框
 * @param id
 */
function emptyOpt(id) {
	var defaultOpt = "<option value=''>请选择</option>";
	$("#" + id).empty();
	$("#" + id).append(defaultOpt);
}
/**
 * 还原下拉框
 * @param thisBox
 */
function cleanOpt(thisBox) {
	var defaultOpt = "<option value=''>请选择</option>";
	$(thisBox).empty();
	$(thisBox).append(defaultOpt);
}

/***
 * 动态加载表格
 * @param {Object} id
 * @param {Object} leastNum
 */
function createTable(divid, size, leastNum) {
	for(var i = 0; i < size; i++) {
		var newTable = $("#" + divid).find(".table-editer-form").eq(0).clone(true);
		var delBtn = getTableDelBtn(divid, leastNum, 'delTable');
		//判断是否存在删除按钮
		if(!newTable.hasClass(".del")) {
			newTable.find(".add").after(delBtn);
		}
		newTable.find(".add").remove();
		cleanTable(newTable);
		$("#" + divid).append(newTable);
	}
}

/***
 * 功能：动态加载表格
 * @param {Object} divTb
 * @param {Object} size
 * @param {Object} leastNum
 */
function createSubTable(divTb, size, leastNum) {
	//清理所有旧的表格
	delAllTable(divTb,'table-editer-form2',leastNum);
	//生成新的表格
	for(var i = 0; i < size; i++) {
		var newTable = divTb.find(".table-editer-form2").eq(0).clone(true);
		var delBtn = getTableDelHtml(divTb, leastNum, 'delSubTable');
		//判断是否存在删除按钮
		if(!newTable.hasClass(".del")) {
			newTable.find(".add").after(delBtn);
		}
		newTable.find(".add").remove();
		cleanTable(newTable);
		divTb.append(newTable);
	}
}
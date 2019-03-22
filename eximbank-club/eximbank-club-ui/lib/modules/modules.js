/**
 * 功能：显示货币
 */
function getCurrencyDb() {
	var defer = $.Deferred();   //创建一个新的 Deferred（延迟）对象
	$.ajax({
		type: "PUT",
		url: "/eximbank-club/components/currency",
		data: angular.toJson({}),
		success: function(json) {
			var result = json.data;
			//解决Deferred（延迟）对象，并根据给定的参数调用任何 doneCallbacks 回调函数
			defer.resolve(result);  
		},
		error: function(e) {
			console.log(e);
		}
	});
	// 当ajax执行完毕，返回 Deferred 对象
	return defer.promise();  
}

/**
 * 根据产品编码查询类型对象
 * @param {Object} code
 */
function getProductTypes(code) {
	var result = null;
	$.ajax({
		type: "PUT",
		url: "/eximbank-club/components/product",
		data: angular.toJson({
			'code': code
		}),
		async: false,
		success: function(json) {
			result = json.data;
		},
		error: function(e) {
			console.log(e);
		}
	});
	return result;
}

/**
 * 功能：查询字典
 * @param {Object} code
 */
function getDicDB(type) {
	var defer = $.Deferred();   //创建一个新的 Deferred（延迟）对象
	$.ajax({
		type: "POST",
		url: "/eximbank-club/dic/read/list",
		data: angular.toJson({
			'type': type
		}),
		success: function(json) {
			var result = json.data;
			//解决Deferred（延迟）对象，并根据给定的参数调用任何 doneCallbacks 回调函数
			defer.resolve(result);  
		},
		error: function(e) {
			console.log(e);
		}
	});
	// 当ajax执行完毕，返回 Deferred 对象
	return defer.promise();  
}

/**
 * 功能：查询经办人机构
 * @param {Object} code
 */
function getDeptDB() {
	var result = null;
	$.ajax({
		type: "PUT",
		url: "/eximbank-club/components/getDept",
		data: angular.toJson({
			'time': new Date()
		}),
		async: false,
		success: function(json) {
			result = json.data;
		},
		error: function(e) {
			console.log(e);
		}
	});
	return result;
}

/**
 * 功能：根据类型和编码查询字典
 * @param {Object} code 编码
 * @param {Object} type 类型 
 */
function getDicTypeDetail(code, type) {
	$.ajax({
		type: "POST",
		url: "/eximbank-club/dic/read/getByTypeCode",
		data: angular.toJson({
			'code': code,
			'type': type
		}),
		async: false,
		success: function(data) {
			return data;
		},
		error: function(e) {
			console.log(e);
		}
	});
}

/**
 * 功能：显示费用类型
 */
function getChargeTypeDb(code) {
	var defer = $.Deferred();   //创建一个新的 Deferred（延迟）对象
	$.ajax({
		type: "PUT",
		url: "/eximbank-club/components/chargeType",
		data: angular.toJson({
			productTypesCode: code
		}),
		success: function(json) {
			var result = json.data;
			//解决Deferred（延迟）对象，并根据给定的参数调用任何 doneCallbacks 回调函数
			defer.resolve(result);  
		},
		error: function(e) {
			console.log(e);
		}
	});
	// 当ajax执行完毕，返回 Deferred 对象
	return defer.promise();  
}
/**
 * 功能：显示押品类型
 */
function getPledgeTypeByCodeDb(code) {
	var result = null;
	$.ajax({
		type: "PUT",
		url: "/eximbank-club/components/guaranteeType",
		data: angular.toJson({
			parentCode: code
		}),
		async: false,
		success: function(json) {
			result = json.data;
		},
		error: function(e) {
			console.log(e)
		}
	});
	return result;
}

/**
 * 功能：显示担保根类型
 */
function getGuaranteeInfoByCodeDb(code) {
	var result = null;
	$.ajax({
		type: "PUT",
		url: "/eximbank-club/components/guaranteeInfoType",
		data: angular.toJson({
			parentCode: code
		}),
		async: false,
		success: function(json) {
			result = json.data;
		},
		error: function(e) {
			console.log(e);
		}
	});
	return result;
}

/**
 * 功能：查询多级代码类型详情
 * @param {Object} types 功能类型（#分割的字符串）
 * @param {Object} codes 编号字符串（#分割的字符串）
 */
function getMulitLevelByTypeDetailDb(types, codes) {
	var result = null;
	var nowTime = new Date();
	$.ajax({
		type: "PUT",
		cache : false,
		async: false,
		url: "/eximbank-club/components/getDicTypeDetail",
		data: angular.toJson({
			types: types,
			codes: codes,
			time: nowTime
		}),
		success: function(json) {
			result = json.data;
		},
		error: function(e) {
			console.log(e);
		}
	});
	return result;
}

/**
 * 功能：查询多级代码类型
 */
function getMulitTypeDb(code, typeCode) {
	var result = null;
	var nowTime = new Date();
	$.ajax({
		type: "PUT",
		async: false,
		cache: false, //设置数据不缓存
		url: "/eximbank-club/components/getDicType",
		data: angular.toJson({
			parentCode: code,
			type: typeCode,
			time: nowTime
		}),
		success: function(json) {
			result = json.data;
		},
		error: function(e) {
			console.log(e);
		}
	});
	return result;
}

/**
 * 功能：查询代码详情
 */
function getTypeDetailDb(code, typeCode) {
	var defer = $.Deferred();   //创建一个新的 Deferred（延迟）对象
	var nowTime = new Date();
	$.ajax({
		type: "PUT",
		cache: false, //设置数据不缓存
		url: "/eximbank-club/components/getTypeDetail",
		data: angular.toJson({
			code: code,
			type: typeCode,
			time: nowTime
		}),
		success: function(json) {
			var result = json.data;
			//解决Deferred（延迟）对象，并根据给定的参数调用任何 doneCallbacks 回调函数
			defer.resolve(result); 
		},
		error: function(e) {
			console.log(e);
		}
	});
	// 当ajax执行完毕，返回 Deferred 对象
	return defer.promise();  
}

/**
 * 功能：查询多级代码类型
 */
function getMulitLevelByTypeDb(code, typeCode) {
	var defer = $.Deferred();   //创建一个新的 Deferred（延迟）对象
	var nowTime = new Date();
	$.ajax({
		type: "PUT",
		cache: false, //设置数据不缓存
		url: "/eximbank-club/components/getDicType",
		data: angular.toJson({
			parentCode: code,
			type: typeCode,
			time: nowTime
		}),
		success: function(json) {
			var result = json.data;
			//解决Deferred（延迟）对象，并根据给定的参数调用任何 doneCallbacks 回调函数
			defer.resolve(result);  
		},
		error: function(e) {
			console.log(e);
		}
	});
	// 当ajax执行完毕，返回 Deferred 对象
	return defer.promise();  
}

/**
 * 功能：显示利率类型
 */
function getInterestRateByCodeDb(code, str1) {
	var result = null;
	$.ajax({
		type: "PUT",
		url: "/eximbank-club/components/interestRate",
		data: angular.toJson({
			'parentCode': code,
			'srv1': str1
		}),
		async: false,
		success: function(json) {
			result = json.data;
		},
		error: function(e) {
			console.log(e);
		}
	});
	return result;
}

/**
 * 生成担保类型下拉框
 * @param {Object} curId 元素id属性
 */
function bulidGuaranteeInfoSelect(curId) {
	var code = "";
	//清空原来的数据
	$("#" + curId).empty();
	var html = defaultOption();
	//查询数据库
	var jsonResult = getGuaranteeInfoByCodeDb(code);
	html += createOption(jsonResult);
	//连接字符串
	$("#" + curId).append(html);
}

/**
 * 生成二级担保类型下拉框
 * @param {Object} curId 元素id属性
 */
function bulidLevelToGuaranteeInfoSelect(sId) {
	var childBox = $(sId).parent().parent().parent().next().find("select[name='contractType']").eq(0);
	var code = $(sId).val();
	if(!code) {
		code = "";
	}
	if(childBox) {
		//清空原来的数据
		childBox.empty();
		var html = defaultOption();
		//查询数据库
		var jsonResult = getGuaranteeInfoByCodeDb(code);
		html += createOption(jsonResult);
		//连接字符串
		childBox.append(html);
	} else {
		alert('没有找到二级担保类型下拉框！');
		return false;
	}
}

/**
 * 生成货币单位下拉框
 * @param {Object} id 元素id属性
 */
function bulidCurrencySelect(id) {
	//清空原来的数据
	$("#" + id).empty();
	var html = defaultOption();
	//使用延迟对象查询数据库，优化查询效率
	$.when(getCurrencyDb()).done(function(result){
		var jsonResult = result;
		html += createCurrencyOption(jsonResult);
		//连接字符串
		$("#" + id).append(html);
	}).fail(function(){
		alert("查询失败!");
	});
}


/**
 * 生成经办人机构下拉框
 * @param {Object} id 元素id属性
 */
function bulidDeptSelect(id) {
	//清空原来的数据
	$("#" + id).empty();
	var html = defaultOption();
	//查询数据库
	var jsonResult = getDeptDB();
	var size = $(jsonResult).size();
	//如果存在多个分行，则默认为全部分行
	if(size>1){
		html = defaultDeptOption();
	}
	html += createOption(jsonResult);
	//连接字符串
	$("#" + id).append(html);
}

/**
 * 生成收费类型下拉框
 * @param {Object} id 元素id属性
 */
function bulidChargeTypeSelect(id, businessCode) {
	//清空原来的数据
	$("#" + id).empty();
	var html = defaultOption();
	//使用延迟对象查询数据库，优化查询效率
	$.when(getChargeTypeDb(businessCode)).done(function(result){
		var jsonResult = result;
		html += createOption(jsonResult);
		//连接字符串
		$("#" + id).append(html);
	}).fail(function(){
		alert("查询失败!");
	});
}


/**
 * 生成col-sm-5下拉框
 * @param {Object} id 元素id属性
 */
function getPledgeTypeSelect(curId, code) {
	//清空原来的数据
	var html = defaultOption();
	//查询数据库
	var jsonResult = getPledgeTypeByCodeDb(code);
	html += createOption(jsonResult);
	//连接字符串
	$(curId).empty();
	$(curId).append(html);
}

/**
 * 生成col-sm-5下拉框
 * @param {Object} id 元素id属性
 */
function bulidPledgeTypeSelect(curId) {
	var code = $(curId).val();
	if(!code) {
		code = "";
	}
	//清空原来的数据
	var nextBox = $(curId).next();
	var html = defaultOption();
	//查询数据库
	var jsonResult = getPledgeTypeByCodeDb(code);
	html += createOption(jsonResult);
	//连接字符串
	if(code == '') {
		$(curId).empty();
		$(curId).append(html);
	} else {
		nextBox.empty();
		nextBox.append(html);
	}
}

/**
 * 生成利率下拉框
 * @param {Object} id 元素id属性
 */
function bulidInterestRateSelect(id) {
	var code = "";
	//清空原来的数据
	$("#" + id).empty();
	var html = defaultOption();
	//查询数据库
	var jsonResult = getInterestRateByCodeDb(code, '0');
	html += createOption(jsonResult);
	//连接字符串
	$("#" + id).append(html);
}

/**
 * 生成利率期限下拉框
 * @param {Object} id 元素id属性
 */
function bulidInterestRateScopeDateSelect(b) {
	var code = b.value;
	if(!code) {
		code = "";
	}
	//清空原来的数据
	var selectBox = $(b).parent().parent().next().find("select[name='termInterestRate']");
	selectBox.empty();
	var html = defaultOption();
	//查询数据库
	var jsonResult = getInterestRateByCodeDb(code, '1');
	html += createOption(jsonResult);
	//连接字符串
	selectBox.append(html);
}

/**
 * 功能： 根据编码查询币种名称
 * @param {Object} code
 */
function getCurrencyNameByCode(code,jsonResult){
    var resultStr = "";
    if(jsonResult != null && jsonResult!='') {
        $(jsonResult).each(function(i, n) {
            var item_v = n.value;
            var item_n = n.name;
            if(item_v == code){
                resultStr = item_n;
            }
        })
    }
    return resultStr;
}

/**
 * 国家下拉框控件
 * @param {Object} id 元素id属性
 * @param {Object} codeType 码表类型 
 */
function bulidCountry(divid,id,codeType){
	//清空原来的数据
	$("#" + divid).empty();
	var html = "";
	var u_id = getCssById(id);
	var ul = "<ul id=\""+u_id+"\">";
	var li = "<li class='default-border'>";
	li += defaultUI(id);
	
	//使用延迟对象查询数据库，优化查询效率
	$.when(getDicDB(codeType)).done(function(result){
		//连接字符串
		var childUI = addOptionHtml(result, 'son', divid, id);
			li += childUI;
			li += "</li>";
			ul += li;
			ul += "</ul>";
			//
			html += ul;
			$("#" + divid).append(html);
	}).fail(function(){
		alert("查询失败!");
	});
}

/**
 * 创建下拉框内容
 * @param {Object} jsonData
 */
function addOptionHtml(jsonData, css, divid, id) {
	var index = index + 'non';
	var html = "";
	html += "<ul class=\"" + css + "\">";
	$(jsonData).each(function(i, n) {
		var item_v = n.code;
		var item_n = n.codeText;
		
		var even = (i + 1) % 2;
		var li_css = "";
		var downSign = "<label style='cursor: pointer;' class='defaultDownLabel'>▸<label>";
		if(i == 0) {
			li_css = " class='first'";
		} else if(even == 1) {
			li_css = " class='even'";
		}
		var it_label = item_n.substr(0,15);
		var it_title = "";
		if(item_n.length>15){
			it_title = " title='"+item_n+"'";
		}
		html += "<li" + li_css + ">";
		html += "<input style= 'width: 250px;height: 30px;padding-top: 5px;' type=\"radio\" name=\"ck\" value=\"" + item_v + "\" "+it_title+" onclick=\"hide_context_menu(this,'" + divid + "','" + id + "','" + it_label + "')\" />";
		html += "<label style='margin-top: -35px; float: right;padding-left: 13px;' "+it_title+"  class='show_name' >" + it_label + "</label>";
		html += "</li>";
	})
	html += "</ul>";
	return html;
}


/**
 * 生成码表下拉框
 * @param {Object} id 元素id属性
 * @param {Object} codeType 码表类型 
 */
function bulidDicSelect(id, codeType) {
	//清空原来的数据
	$("#" + id).empty();
	var html = defaultOption();
	//使用延迟对象查询数据库，优化查询效率
	$.when(getDicDB(codeType)).done(function(result){
		var jsonResult = result;
		//连接字符串
		html += createDicOption(jsonResult);
		$("#" + id).append(html);
	}).fail(function(){
		alert("查询失败!");
	});
}

/**
 * 功能：批量加载多个下拉框
 * @param {Object} divids
 * @param {Object} ids
 * @param {Object} codes
 * @param {Object} types
 */
function batchBulidMulitSelect(s_ary){
	if(!s_ary || s_ary==null || s_ary==''){
		return null;
	}
	var codes = "";
	var typeCodes = "";
	var size = s_ary.length;
	for(var i=0;i<size;i++){
		var msb = s_ary[i];
		var code = msb.code;
		var type = msb.type;
		codes+=code;
		typeCodes+=type;
		if(size-i>1){
			codes+="#";
			typeCodes+="#";
		}
	}
	console.log(codes+"---"+typeCodes);
	var json = getMulitLevelSelect(codes,typeCodes);
	//迭代生成多个下拉框
	for(var i=0;i<size;i++){
		var msb = s_ary[i];
		var type = msb.type;
		var divid = msb.divid;
		var id = msb.id;
		//
		var jsonResult = getJsonItem(json,type);
		//清空原来的数据
		$("#" + divid).empty();
		var html = "";
		var u_id = getCssById(id);
		var ul = "<ul id=\""+u_id+"\">";
		var li = "<li style='width: 260px;' class='default-border'>";
		li += defaultUI(id);
		//查询数据库
		var childUI = createLI(jsonResult, type, 'son', divid, id);
		li += childUI;
		li += "</li>";
		ul += li;
		ul += "</ul>";
		//
		html += ul;
		$("#" + divid).append(html);
	}
}
/**
 * 从map集合中获取数据
 * @param {Object} index
 * @param {Object} key
 */
function getJsonItem(json,key){
	var result = null;
	if(json!=null){
		$.each(json,function(k,v){
			if(key == k){
				result = v;
			}
		})
	}
	return result;
}
/**
 * 功能：定义多级联动菜单对象
 * @param {Object} divid
 * @param {Object} id
 * @param {Object} code
 * @param {Object} type
 */
function getMulitSelectBox(divid, id, code, type) {
	//清空原来的数据
	$("#" + divid).empty();
	var html = "";
	var u_id = getCssById(id);
	var ul = "<ul id=\"" + u_id + "\">";
	var li = "<li class='default-border'>";
	li += defaultUI(id);
	var jsonResult = getMulitTypeDb(code, type);
	//新增子节点
	var childUI = createLI(jsonResult, type, 'son', divid, id);
	li += childUI;
	li += "</li>";
	ul += li;
	ul += "</ul>";
	//
	html += ul;
	$("#" + divid).append(html);
}
/** 
 * 功能：生成无限级下拉框
 * @param {Object} id
 * @param {Object} code
 * @param {Object} type
 */
function bulidMulitSelect(divid, id, code, type) {
	//清空原来的数据
	$("#" + divid).empty();
	var html = "";
	var u_id = getCssById(id);
	var ul = "<ul id=\""+u_id+"\">";
	var li = "<li class='default-border'>";
	li += defaultUI(id);
	//使用延迟函数查询数据库
	$.when(getMulitLevelByTypeDb(code, type)).done(function(result){
			var childUI = createLI(result, type, 'son', divid, id);
			li += childUI;
			li += "</li>";
			ul += li;
			ul += "</ul>";
			//
			html += ul;
			$("#" + divid).append(html);
	}).fail(function(){
		alert("查询失败!");
	});
	
}

/** 
 * 功能：初始化无限级下拉框
 * @param {Object} divids
 * @param {Object} codes
 * @param {Object} types
 */
function getMulitSelectList(divids, types, codes) {
	//查询单个无限级对象，然后取出显示名称
	var jsonList = getMulitLevelByTypeDetailDb(types, codes);
	if(jsonList != null) {
		$(jsonList).each(function(i, n) {
			var item_v = n.code;
			var item_n = n.name;
			setterLabel(divids,item_v,item_n);
		})
		
	}
}
/**
 * 动态设置DefalutUL的值
 * @param {Object} divids
 * @param {Object} code
 * @param {Object} name
 */
function setterLabel(divids, code, name) {
	var len = divids.length;
	for(var i = 0; i < len; i++) {
		var arrs = divids[i].split(':');
		if(arrs[1] == code) {
			if(name==''||name==null){
				name = "请选择";
			}
			//设置字数长度如果超出15位，则截取
			var label_ = name.substr(0,15);
			//设置下拉框选中的名称
			$("#" + arrs[0]).find(".defaultDownBox").text(label_);
			$("#" + arrs[0]).find(".defaultDownBox").attr("title",name);
			//设置选中的值赋值给隐藏表单
			$("#" + arrs[0]).prev().val(code);
		}
	}
}

/**
 * 根据编码和类型查询码表 
 * @param {Object} id
 * @param {Object} codeType
 * @param {Object} codeVal
 */
function showDicView(codeType, codeVal) {
	var dic = getDicTypeDetail(codeVal, codeType);
	return dic.codeText;
}

/**
 * 功能：根据类型和编码查找码表对象
 * @param {Object} type
 * @param {Object} code
 */
function getDicItem(type,code) {
	$.when(getTypeDetailDb(code,type)).done(function(result){
		return result;	
	}).fail(function(){
		alert("查询失败!");
	});
	
}

/**
 * 生成默认的下拉框
 */
function defaultOption() {
	var option = "<option value=''>请选择</option>";
	return option;
}

/**
 * 生成默认的下拉框
 */
function defaultDeptOption() {
	var option = "<option value=''>全部分行</option>";
	return option;
}

/**
 * 生成currency币种下拉框
 * @param {Object} kvData
 */
function createCurrencyOption(kvData) {
	var html = "";
	if(kvData != null) {
		$(kvData).each(function(i, n) {
			var item_v = n.value;
			var item_n = n.name;
			var opt = "<option value=\'" + item_v + "\'>" + item_n+ "</option>";

			html += opt;
		})
	}
	return html;
}

/**
 * 生成currency币种下拉框,并且选中值
 * @param {Object} kvData
 */
function createCurrencyOptionSelected(kvData,vv) {
	var html = "";
	if(kvData != null) {
		$(kvData).each(function(i, n) {
			var item_v = n.value;
			var item_n = n.name;
			var selected = "";
			if(vv != '' && item_v == vv) {
				selected = "selected";
			}
			var opt = "<option " + selected + " value=\'" + item_v + "\'>" + item_n + "</option>";

			html += opt;
		})
	}
	return html;
}

/**
 * 生成option字符串
 * @param {Object} kvData
 */
function createOption(kvData) {
	var html = "";
	if(kvData != null) {
		$(kvData).each(function(i, n) {
			var item_v = n.value;
			var item_n = n.name;
			var opt = "<option value=\'" + item_v + "\'>" + item_n + "</option>";

			html += opt;
		})
	}
	return html;
}

/**
 * 生成option字符串
 * @param {Object} kvData
 */
function createOptionSelected(kvData, vv) {
	var html = "";
	if(kvData != null) {
		$(kvData).each(function(i, n) {
			var item_v = n.value;
			var item_n = n.name;
			var selected = "";
			if(vv != '' && item_v == vv) {
				selected = "selected";
			}
			var opt = "<option " + selected + " value=\'" + item_v + "\'>" + item_n + "</option>";

			html += opt;
		})
	}
	return html;
}

/**
 * 生成option字符串
 * @param {Object} kvData
 */
function getOptionSelected(kvData, vv) {
	var html = "";
	if(kvData != null) {
		$(kvData).each(function(i, n) {
			var item_v = n.value;
			var item_n = n.name;
			if(vv != '' && item_v == vv) {
				html = "<option selected value=\'" + item_v + "\'>" + item_n + "</option>";
			}
		})
	}
	return html;
}

/**
 * 生成option字符串
 * @param {Object} kvData
 */
function createDicOption(kvData) {
	var html = "";
	if(kvData != null) {
		$(kvData).each(function(i, n) {
			var item_v = n.code;
			var item_n = n.codeText;
			var opt = "<option value=\'" + item_v + "\'>" + item_n + "</option>";

			html += opt;
		})
	}
	return html;
}

/**
 * 功能：如果是不可用，则获取灰色样式
 * @param {Object} bid
 */
function getCssById(bid){
	var hd = $("#"+bid).attr("disabled");
	if("disabled"==hd){
		return "ul2";
	}
	return "ul1";
}

/**
 * 生成默认的下拉框Span
 * @param {Object} bid 隐藏表单的id 
 */
function defaultUI(bid) {
	var label = "<label class='defaultDownBox'>请选择</label><label class='defaultDownLabel'>▾<label/>";
	//如果下拉框不可用。则设置不可用状态
	var hd = $("#"+bid).attr("disabled");
	if("disabled"==hd){
		var d_option = "<span class='show_name'>" + label + "</span>";
		return d_option;
	}
	var option = "<span class='show_name' onclick='show_root_menu(this)'>" + label + "</span>";
	return option;
}

/**
 * 创建下拉框内容
 * @param {Object} jsonData
 */
function createLI(jsonData, type, css, divid, id) {
	var index = index + 'non';
	var html = "";
	html += "<ul class=\"" + css + "\">";
	$(jsonData).each(function(i, n) {
		var it_v = n.code;
		var it_n = n.name;
		var it_p = n.parentCode;
		var it_c = n.children;
		var even = (i + 1) % 2;
		var li_css = "";
		var downSign = "<label style='cursor: pointer;' class='defaultDownLabel'>▸<label>";
		if(i == 0) {
			li_css = " class='first'";
		} else if(even == 1) {
			li_css = " class='even'";
		}
		var it_label = it_n.substr(0,15);
		var it_title = "";
		if(it_n.length>15){
			it_title = " title='"+it_n+"'";
		}
		html += "<li" + li_css + ">";

		//判断是否有子菜单
		if(it_c == 'true'||it_c==true) {
			html += "<input  type=\"radio\" style='display: none;' name=\"ck\" value=\"" + it_v + "\" disabled  onmouseover=\"hide_context_menu(this,'" + divid + "','" + id + "','" + it_label + "')\"/>";
			html += "<label "+it_title+" class='show_name' onmouseover =\"show_context_menu(this,'" + type + "'," + it_c + ",'" + divid + "','" + id + "')\">" + it_label + downSign + "</label>";
		} else {
			html += "<input style= 'width: 250px;height: 30px;padding-top: 5px;' type=\"radio\" name=\"ck\" value=\"" + it_v + "\" "+it_title+" onclick=\"hide_context_menu(this,'" + divid + "','" + id + "','" + it_label + "')\" />";
			html += "<label style='margin-top: -35px; float: right;padding-left: 13px;' "+it_title+"  class='show_name' >" + it_label + "</label>";
		}
		html += "</li>";
	})
	html += "</ul>";
	return html;
}

/**
 * 功能：显示下拉框
 * @param {Object} that
 */
function show_context_menu(that, type, children, divid, id) {
	//判断列表是否存在，存在就让它显示
	if(children) {
		var code = $(that).parent().find('input:radio[name="ck"]').eq(0).val();
		if(!code) {
			code = "";
		}
		//隐藏其他所有的孙子辈菜单下拉框
		closeAllUl(that);
		//如果存在则展示，不生成
		var ul_size = 0;
		if(ul_size < 1) {
			//查询数据库
			$.when(getMulitLevelByTypeDb(code, type)).done(function(result){
				//生成下一级菜单
				var ul = createLI(result, type, 'graSon', divid, id);
				$(that).parent().append(ul);
			}).fail(function(){
				alert("查询失败!");
			});	
		}
		//显示下级菜单
		show_root_menu(that);
	}
}

/**
 * 功能：隐藏下拉框
 * @param {Object} that
 */
function hide_context_menu(that, divid, id, name) {
	var check = $(that).is(":checked");
	var checkVal = that.value;
	//判断列表是否存在，存在就让它显示
	if(check) {
		$("#" + id).val(checkVal);
		//判断是否有必填项消息提示，如果有，清除
		if($("#" + divid).find(".default-border").hasClass("required-warn")) {
			$("#" + divid).find(".default-border").removeClass("required-warn");
			$("#" + id).parent().find(".text-danger").text("");
		}
		$("#" + divid).find(".son").hide();
		$("#" + divid).find(".defaultDownBox").text(name);
		$("#" + divid).trigger('blur');
	}
}

/**
 * 功能：显示根菜单
 */
function show_root_menu(that) {
	// var box =document.getElementById('industryInve');
	var root_div = $(that).parent().parent().parent();
	if(root_div.is(".mulit_context_menu")) {
		$(".mulit_context_menu").css("z-index", "0");
		root_div.css("z-index", "99");
	}
	//显示下级菜单
	$(that).parent().find("ul").slideToggle();

		// document.onclick = function(e) {
		// 	var e = e || window.event;
		// 	var target = e.target || e.srcElement;
        //
		// 	while (target != box && target.parentNode && target != root_div) {
		// 		target = target.parentNode;
		// 	}
		// 	if (target != box && target != root_div) {
		// 		$('.son').hide();
		// 	}
		// };
}

/**
 * 功能：隐藏其它下拉框
 */
function closeAllUl(that) {
	$(that).parent().siblings().each(function(i) {
		$(this).find(".graSon").empty();
	})
}

/**
 * 转换货币字符串成整数,去掉货币中的逗号
 * @param {Object} numStr
 */
function convertNumber(numStr){
	if(numStr==null||numStr==''){
		return numStr;
	}
	return numStr.replace(/,/g, '');
}



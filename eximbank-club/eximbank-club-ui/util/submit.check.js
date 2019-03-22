/**
 * 初始化失去焦点事件
 */
function initBlur() {
	//文本框失去焦点
	$('form :input').blur(function() {
		var $parent = $(this).parent();
		$parent.find(".formtips").remove();
		var in_value = this.value.trim();
		var ckFmt = $(this).is('.format-validate');
		var ip_type = $(this).attr("type");
		var ip_name = $(this).attr("name");
		this.value = in_value;

		//必填项
		if($(this).is('.required')) {
			if(in_value == "") {
				//判断是否有自定义下拉框控件，如果存在，则添加必填项提醒
				if($(this).is(":hidden")) {
					$(this).parent().find(".default-border").addClass("required-warn");
				}
				$(this).addClass("required-warn");
				$parent.find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
				var errorMsg = '必填项';
				if($parent.find("span").hasClass("text-danger")) {
					$parent.find(".text-danger").text(errorMsg);
				} else {
					$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				}
				return false;
			} else {
				if(!ckFmt) {
					//判断是否有自定义下拉框控件，如果存在，则不能删除span
					if($(this).is(":hidden")) {
						$(this).removeClass("required-warn");
						$(this).parent().find(".default-border").removeClass("required-warn");
					} else {
						$(this).removeClass("required-warn");
						$parent.find("span").remove();
					}
				}
			}
			//判断是否是单选框
			if("radio"==ip_type){
				var val=$('input:radio[name="'+ip_name+'"]:checked').val();
	            if(val==null){
	                $(this).addClass("required-warn");
					$parent.find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
					var errorMsg = '必填项';
					if($parent.find("span").hasClass("text-danger")) {
						$parent.find(".text-danger").text(errorMsg);
					} else {
						$parent.append('<span class="text-danger">' + errorMsg + '</span>');
					}
					return false;
	            }else{
	               	$('input:radio[name="'+ip_name+'"]').removeClass("required-warn");
					$parent.find(".text-danger").remove();
	            }
			}
		}

		//是否调用过接口。如果没有提示要求客户调用接口查询数据
		if($(this).is('.interface')) {
			var in_title = $(this).attr("title");
			if(in_value == "") {
				var errorMsg = '请点击';
				if(in_title && in_title != '') {
					errorMsg += in_title + "按钮查看信息后，再进行下一步操作!";
				} else {
					errorMsg = '请选择';
				}
				$(this).addClass("required-warn");
				$parent.find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
				if($parent.find("span").hasClass("text-danger")) {
					$parent.find(".text-danger").text(errorMsg);
				} else {
					$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				}
				return false;
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			}
		}
		//自动补全校验
		if($(this).is('.auto')) {
			if($parent.find("ul").hasClass("ng-hide")) {
				$(this).addClass("required-warn");
				$(this).val("");
			} else {
				$(this).removeClass("auto");

			}

			return false;
		}
		//验证密码
		if($(this).is('.password')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			if(in_value.length >= 8 && in_value.length <= 12) {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			} else {
				var errorMsg = '密码为8位到12位以内的任意字符.';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
				return false;
			}
		}
		//手机号
		if($(this).is('.checkTel')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			var isMobile = /^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/; //手机号码验证规则
			if(!isMobile.test(in_value)) {
				var errorMsg = '请输入正确的手机号';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				return false;
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			}
		}
		//数字(正整数)  /^[1-9]\d*$/
		if($(this).is('.isNumber')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			// var isNumber = /^[1-9]\d*$/; //正整数验证规则
			var isNumber = /(^[1-9]([0-9]*)$|^[0-9]$)/; //正整数验证规则去0

			if(!isNumber.test(in_value)) {
				var errorMsg = '请输入正确格式的数字';
				$(this).addClass("required-warn");
				$parent.find("span").remove();
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				return false;
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			}
		}
		
		//数字(正整数)  /^[1-9]\d*$/
		if($(this).is('.ckNumber')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			// var isNumber = /^[1-9]\d*$/; //正整数验证规则
			var isNumber = /(^[1-9]([0-9]*)$|^[0-9]$)/; //正整数验证规则去0

			if(!isNumber.test(in_value)) {
				var errorMsg = '只能输入数字';
				$(this).addClass("required-warn");
				$parent.find("span").remove();
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				return false;
			} else if(in_value.length<15){
				var errorMsg = '请输入15位数字';
				$(this).addClass("required-warn");
				$parent.find("span").remove();
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				return false;
			} else{
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			}
		}
		//一般金额
		if($(this).is('.amt')) {
			//先判断非空
			if(in_value == "") {
				return false;
			} // /^([1-9]{1}[0-9]{0,3}(\,[0-9]{3,4})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/
			var amt = /^([1-9]\d{0,11})([\.]{1}\d{1,2})?$/; //金额校验规则 /^(\d{1,10})([\.]{1}\d{1,2})?$/
			var amt1 = /^[0-9](\.\d{1,2})?$/;
			in_value = in_value.replace(/,/g, '');
			if(!(amt.test(in_value) || amt1.test(in_value))) {
				var errorMsg = '应填写最多十二位整数，两位小数的金额';
				$(this).addClass("required-warn");
				$parent.find("span").remove();
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find("span").remove();
				}
				$parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
				var dd = in_value.replace(/[^\d,]/g, "");
				if(in_value == dd) {
					var in_value = in_value.replace(/\B(?=(?:\d{3})+$)/g, ',');
				} else {
					var intSum = in_value.substring(0, in_value.indexOf(".")).replace(/\B(?=(?:\d{3})+$)/g, ','); //取到整数部分
					var dot = in_value.substring(in_value.length, in_value.indexOf(".")) //取到小数部分搜索
					if(dot.length == 2) {
						var ret = intSum + dot;
						return this.value = ret + "0"
					}
					var ret = intSum + dot;
					return this.value = ret
				}
				return this.value = in_value + ".00";
			}

		}
		//日元
		if($(this).is('.jpyamt')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			var jpyamt = /^(\d{1,12})?$/; //金额校验规则
			in_value = in_value.replace(/,/g, '');
			if(!jpyamt.test(in_value)) {
				var errorMsg = '日元十二位整数';
				$(this).addClass("required-warn");
				$parent.find("span").remove();
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find("span").remove();
				}
				$parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
				var value = Number(in_value).toLocaleString();
				return this.value = value;
			}
		}
		//联系电话
		if($(this).is('.checkPhone')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			var isMobile = /^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/; //手机号码验证规则
			var isPhone = /^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/; //座机验证规则
			if(!isMobile.test(in_value) && !isPhone.test(in_value)) {
				var errorMsg = '请输入正确的电话号码.';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
				return false;
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			}
		}
		//验证邮件
		if($(this).is('.email')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			var email_pattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			if(in_value == "" || (in_value != "" && !email_pattern.test(in_value))) {
				var errorMsg = '请输入正确的E-Mail地址.';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
				return false;
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			}
		}
		//判断英文字符
		if($(this).is('.isEnglish')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			if(in_value == "" || (in_value != "" && /^[A-Za-z]+$/.test(in_value))) {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			} else {
				var errorMsg = '请输入英文';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
				return false;
			}
		}
		//判断是否只包含字母数字
		if($(this).is('.isCharNumber')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			if(in_value == "" || (in_value != "" && /^[a-zA-Z0-9]+$/.test(in_value))) {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			} else {
				var errorMsg = '请输入字母或数字!';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
				
				return false;
			}
		}
		//判断是否只包含整数或小数
		if($(this).is('.isFloat')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			if(in_value == "" || (in_value != "" && /^[0-9]+(.[0-9]{2,8})?$/.test(in_value))) {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			} else {
				var errorMsg = '请输入小数(精度在2~8位之间)!';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
				return false;
			}
		}
		//判断是否只包含1~6位精度小数
		if($(this).is('.ckFloat')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			var p = /^[1-9](\d+(\.\d{1,6})?)?$/;
			var p1 = /^[0-9](\.\d{1,6})?$/;
			if(in_value == "" || in_value > 999.999999 || (in_value != "" && !(p.test(in_value) || p1.test(in_value)))) {
				var errorMsg = '请输入最多3位整数，6位小数的数据';
				$(this).addClass("required-warn");
				$parent.find("span").remove();
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				return false;
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			}
		}
		//判断是否整数或只包含1~9位精度小数
		if($(this).is('.ckMoney')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			if(in_value != "" && /^([0-9]{1,9}(\.[0-9]{0,9})?)?$/.test(in_value)) {
				if(!ckFmt) {
					var s_i = in_value.indexOf(".");
					if(s_i!=-1){ 
						var slen = in_value.length;
						var f_end=in_value.substring(s_i,slen-1);
						if(f_end==''||f_end.length==0){
							this.value = in_value.substr(0,slen-1);
						}
					}
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			} else {
				var errorMsg = '请输入小数(整数部分在1~9位，精度在1~9位)!';
				$(this).addClass("required-warn");
				$parent.find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
				
				if($parent.find("span").hasClass("text-danger")) {
					$parent.find(".text-danger").text(errorMsg);
				} else {
					$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				}
				return false;
			}
		}
		//验证是否含有中文
		if($(this).is('.en')) {
			//先判断非空
			if(in_value == "") {
				return false;
			}
			if(in_value == "" || (in_value != "" && /[\u4e00-\u9fa5]/g.test(in_value))) {
				var errorMsg = '不能输入中文';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
				return false;
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
				$parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
			}
		}
		//验证利率、费率等百分数(不许第一位写0)
		if($(this).is('.checkFloat')) {
			//先判断非空
			// var p =/^[1-9](\d+(\.\d{1,2})?)?$/; 
			// var p1=/^[0-9](\.\d{1,2})?$/;
			//		    return p.test(s) || p1.test(s);
			if(in_value == "") {
				return false;
			}
			// var isFloat = /^(\d{1,3})([\.]{1}\d{1,2})?$/ // /^((\d+\.\d*[1-9]\d*)|(\d*[1-9]\d*\.\d+)|(\d*[1-9]\d*))$/;
			var p = /^[1-9](\d+(\.\d{1,2})?)?$/;
			var p1 = /^[0-9](\.\d{1,2})?$/;
			if(in_value == "" || in_value > 100 || (in_value != "" && !(p.test(in_value) || p1.test(in_value)))) {
				var errorMsg = '请输入0~100的数据，最多两位小数';
				$(this).addClass("required-warn");
				$parent.find("span").remove();
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				return false;
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
			}
		}
		//比较大小
		if($(this).is('.compare')) {
			//先判断非空

			var v1 = Number($('#rateRangeMix').val());
			var v2 = Number($('#rateRangeMax').val());
			if(v1 == '' && v2 == '') {
				var errorMsg = '请至少输入一个值';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
				return false;
			}
			if(v1 != '' && v2 != '' && v1 > v2) {
				var errorMsg = '利率大小填写错误';
				$parent.append('<span class="text-danger">' + errorMsg + '</span>');
				$(this).addClass("required-warn");
			} else {
				if(!ckFmt) {
					$(this).removeClass("required-warn");
					$parent.find(".text-danger").text("");
				}
				$parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
			}
		}
		//添加样式
		if(!$(this).hasClass("el-button")) {
			if(!$(this).hasClass("required-warn") && $(this).hasClass("required")) {
				//判断是否有自定义下拉框控件，如果存在，则不用删除span
				if($(this).is(":hidden")) {
					//不做任何操作
				} else {
					$parent.find("span").remove();
				}
				$parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
			}
		}

	}); //end blur
}

//触发下一步校验
function validateForm(tab) {
	$("[step=" + tab + "] :input.required ").trigger('blur'); //获取step值，区域触发验证
	$("[step=" + tab + "] :input.interface ").trigger('blur'); //获取step值，区域触发验证
	// $("#" + ckScope).find(":input.interface").trigger('blur');
	if($("div#ngshow.ng-hide").length > 0) { //获取所有的ng-show
		console.log($("div#show.ng-hide").length);
		$("div#ngshow.ng-hide").val(null);
		$("div#ngshow.ng-hide").find("span").remove();
		$("div#ngshow.ng-hide").children().children().children().removeClass("required-warn");
	}
	console.log($("[step=" + tab + "] :input.required ").length);
	if($('span').hasClass('text-danger')) { //通过text-danger判断是否有未填写项
		return false;
	} else {
		return true;
	}
}
/**
 * 功能：清除掉验证的错误消息
 * @param {Object} b
 */
function cleanRequiredWarn(b) {
	var $parent = $(b).parent();
	$(b).removeClass("required-warn");
	$parent.find(".text-danger").text("");
}

/**
 * 功能：判断是否还有输入数据的非空、格式或类型的验证。
 */
function isHaveValidateFmt(b){
	var result = false;
	var b_class = $(b).attr("class");
	var arr = new Array();
	arr[0] = 'checkFloat';
	arr[1] = 'en';
	arr[2] = 'ckMoney';
	arr[3] = 'ckFloat';
	arr[4] = 'isFloat';
	arr[5] = 'isEnglish';
	arr[6] = 'email';
	arr[7] = 'isCharNumber';
	arr[8] = 'checkPhone';
	arr[9] = 'jpyamt';
	arr[10] = 'amt';
	arr[11] = 'ckNumber';
	arr[12] = 'isNumber';
	arr[13] = 'checkTel';
	arr[14] = 'required';
	//判断输入项是否存在则验证的class
	for(var i=0;i<arr.length;i++){
		if(b_class.indexOf(arr[i])!=-1){
			result = true;
		}
	}
	return result;
}

function dedede(b) {
	var $parent = $(b).parent();
	$(b).removeClass("required-warn");
	var $part = $(b);
	$parent.find("span").remove();
}
function adduser(b) {
	var $parent = $(b).parent();
	$(b).removeClass("required-warn");
	var $part = $(b);
	$parent.find("span").remove();
}

/**
 * 显示提醒消息
 * @param {Object} b
 * @param {Object} errorMsg
 */
function alertWarn(b, errorMsg) {
	var $parent = b.parent();
	b.addClass("required-warn");
	if($parent.find("span").hasClass("text-danger")) {
		$parent.find(".text-danger").text(errorMsg);
	} else {
		$parent.append('<span class="text-danger">' + errorMsg + '</span>');
	}
}

//提交，最终验证。
function submitCheck() {
	$("form :input.required").trigger('blur');
	$("form :input.password").trigger('blur');
	$("form :input.checkTel").trigger('blur');
	$("form :input.amt").trigger('blur');
	$("form :input.jpyamt").trigger('blur');
	$("form :input.checkPhone").trigger('blur');
	$("form :input.email").trigger('blur');
	$("form :input.en").trigger('blur');
	$("form :input.checkFloat").trigger('blur');
	$("form :input.ckFloat").trigger('blur');
	$("form :input.interface").trigger('blur');
	var numError = $('form .required-warn').length;
	if(numError) {
		alert("还有必填项未填写的!");
		return false;
	} else {
		return true;
	}
}

/**
 * 点击下一步，验证本页面。
 * @param {Object} ckScope 当前页面范围的区域id
 */
function nextCheck(ckScope, nextId) {
	//当前区域
	if(ckScope && ckScope != '') {
		$("#" + ckScope).find(":input.required").trigger('blur');
		$("#" + ckScope).find(":input.password").trigger('blur');
		$("#" + ckScope).find(":input.checkTel").trigger('blur');
		$("#" + ckScope).find(":input.amt").trigger('blur');
		$("#" + ckScope).find(":input.jpyamt").trigger('blur');
		$("#" + ckScope).find(":input.checkPhone").trigger('blur');
		$("#" + ckScope).find(":input.email").trigger('blur');
		$("#" + ckScope).find(":input.en").trigger('blur');
		$("#" + ckScope).find(":input.checkFloat").trigger('blur');
		$("#" + ckScope).find(":input.ckFloat").trigger('blur');
		$("#" + ckScope).find(":hidden.interface").trigger('blur');

		var numError = $("#" + ckScope).find('.required-warn').length;
		if(numError) {
			if('closeGuaranteeBtn' == nextId || 'closeCustWin' == nextId || 'closeCustLimitBtn' == nextId) {
				$("#" + nextId).parent().next().text("还有必填项未填写!");
			} else {
				alert("还有必填项未填写!");
			}
			return false;
		} else {
			$("#" + nextId).click();
			//如果不是弹出窗口，那么执行滚动条置顶
			if('closeGuaranteeBtn' != nextId && 'closeCustWin' != nextId && 'closeCustLimitBtn' != nextId) {
				setTop();
			}
			return true;
		}
	} else {
		//否则不验证
		return true;
	}
}

//设置滚动条在顶部
function setTop() {
    $("#content").animate({scrollTop:0},1000);//回到顶端
}


/**
 * 功能：重写alert控件
 * @param {Object} e
 */
window.alert = function(txt) {
	var shield = document.createElement("DIV");
	shield.id = "shield";
	shield.style.position = "absolute";
	shield.style.left = "0px";
	shield.style.top = "0px";
	shield.style.width = "100%";
	shield.style.height = document.body.scrollHeight + "px";
	// shield.style.background = "#333";
	shield.style.textAlign = "center";
	shield.style.zIndex = "10000";
	shield.style.filter = "alpha(opacity=0)";
	var alertFram = document.createElement("DIV");
	alertFram.id = "alertFram";
	alertFram.style.position = "absolute";
	alertFram.style.left = "50%";
	alertFram.style.top = "50%";
	alertFram.style.marginLeft = "-125px";
	alertFram.style.marginTop = "-105px";
	alertFram.style.width = "366px";
	alertFram.style.height = "150px";
	alertFram.style.background = "#ccc";
	alertFram.style.textAlign = "center";
	alertFram.style.lineHeight = "150px";
	alertFram.style.zIndex = "10001";
	strHtml = "<ul style=\"list-style:none;box-shadow: 5px 5px 8px #999;margin:0px;padding:0px;width:100%;\">\n";
	strHtml += " <li style=\"background:#f8f8f8;text-align:left;padding-left:20px;font-size:14px;height:25px;line-height:25px;\">提示信息</li>\n";
	strHtml += " <li style=\"background:#fff;text-align:center;font-size:12px;height:120px;line-height:120px;\">" + txt + "</li>\n";

	strHtml += " <li style=\"background:#fff;text-align:center;height:25px;line-height:25px;\">" +
		"<input style=\"display: inline-block;font-size:12px;border:none;color: #fff;padding: 1px 8px;background: #428bca;border-radius: 2px;float: right;margin-right: 15px;margin-top: -12px;cursor: pointer;\" type=\"button\" value=\"确 定\" onclick=\"doOk()\" /></li>\n";

	strHtml += "</ul>\n";
	alertFram.innerHTML = strHtml;
	document.body.appendChild(alertFram);
	document.body.appendChild(shield);
	// var c = 0;
	// this.doAlpha = function(){
	// 	if (c++ > 20){clearInterval(ad);return 0;}
	// 	shield.style.filter = "alpha(opacity="+c+");";
	// }
	// var ad = setInterval("doAlpha()",5);
	this.doOk = function() {
		alertFram.style.display = "none";
		shield.style.display = "none";
	}
	alertFram.focus();
	document.body.onselectstart = function() {
		return false;
	};
}

/**
 * 验证金额长度，如果遇到金额分割符(,)则忽视。
 */
function checkMonenyLen(ad, len){
	var mVal = ad.value;
	//去除分割符
	mVal = mVal.replace(/,/g, '');
	var size = mVal.length;
	if(size > len){
		ad.value = mVal.slice(0,len);
	}
}

//给number增加一个新增方法
Number.prototype.add = function(arg2) {
	 var r1,r2,m;    
     try{r1=this.toString().split(".")[1].length}catch(e){r1=0}    
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}    
     m=Math.pow(10,Math.max(r1,r2));    
     return ((this*m+arg2*m)/m).toFixed(2);   
}
//减    
Number.prototype.sub =function(arg2){    
    var r1,r2,m,n;    
    try{r1=this.toString().split(".")[1].length}catch(e){r1=0}    
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}    
    m=Math.pow(10,Math.max(r1,r2));    
    //动态控制精度长度    
    n=(r1>=r2)?r1:r2;    
    return ((this*m-arg2*m)/m).toFixed(2);    
}     
//乘法   
Number.prototype.mul = function (arg)   
{   
    var m=0,s1=this.toString(),s2=arg.toString();   
    try{m+=s1.split(".")[1].length}catch(e){}   
    try{m+=s2.split(".")[1].length}catch(e){}   
    return (Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)).toFixed(2);   
}  
//除法   
Number.prototype.div = function (arg){   
    var t1=0,t2=0,r1,r2;   
    try{t1=this.toString().split(".")[1].length}catch(e){}   
    try{t2=arg.toString().split(".")[1].length}catch(e){}   
    with(Math){   
        r1=Number(this.toString().replace(".",""))   
        r2=Number(arg.toString().replace(".",""))   
        return ((r1/r2)*pow(10,t2-t1)).toFixed(2);   
    }   
} 
/**
 * 精确到两位
 * @param {Object} num
 */
function roundNum(num){
	 return Math.round(num*100)/100;
}

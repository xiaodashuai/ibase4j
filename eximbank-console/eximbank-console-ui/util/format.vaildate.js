/**
 * 描述：只允许输入字符为：a-zA-Z0-9,-._*\/\\()#@$%'''\s
 * @param {Object} obj
 * @param {Object} e
 */
function OnlyChar(obj,e){
	 var key_value= window.event ? e.keyCode:e.which;
     if(key_value>8 && key_value<=57 && key_value!=17&&key_value!=9&&key_value!=32) {
    	 
     }else {
		 var pos=getPositionForInput(obj);
//		 obj.value=obj.value.replace(/[^a-zA-Z0-9,-.''' ]/g,'');
		 obj.value=obj.value.replace(/[^a-zA-Z0-9,-._*\/\\()#@$%'''\s]/g,''); 
		 if(pos<obj.value.length && pos>=0)
		 {
			 pos=pos-1;
		 }
		 setCursorPosition(obj,pos);
  }
}  

/**
 * 只允许输入浮点型数字
 * @param {Object} obj
 * @param {Object} e
 */
function OnlyFloat(obj,e){
	 var key_value= window.event ? e.keyCode:e.which;
     if(key_value>8 && key_value<=57 && key_value!=17&&key_value!=9&&key_value!=32) {
    	 
     } else {
    	 var pos=getPositionForInput(obj);
    	 obj.value=obj.value.replace(/[^\d.]/g,'');
    	 if(pos<obj.value.length && pos>=0){
    		 pos=pos-1;
    	 }
    	 setCursorPosition(obj,pos);
     }
}

/**
 * 描述：只允许输入数字
 * @param {Object} obj
 * @param {Object} e
 */
function OnlyNumber(obj,e){
	 var key_value= window.event ? e.keyCode:e.which;
	 //屏蔽shift键
	 if (e.shiftKey) {
		 obj.value=obj.value.replace(/\D+/g,'');
         e.cancelBubble = true;
         e.keyCode = 0;
         return false;
	 }
     if(key_value>8 && key_value<=57 && key_value!=17&&key_value!=9&&key_value!=32) {
    	 
     } else {
    	 var pos=getPositionForInput(obj);
    	 obj.value=obj.value.replace(/\D+/g,'');
    	 if(pos<obj.value.length && pos>=0){
    		 pos=pos-1;
    	 }
    	 setCursorPosition(obj,pos);
     }
}

/**
 * 可以输入非汉字的多文本框
 * @param {Object} obj
 * @param {Object} e
 */
function OnlyEnglishNumberTextArea(obj,e){
	var key_value= window.event ? e.keyCode:e.which;
	 
	  ///[^\w\,\.\/\s]/ig
	  if(key_value>8 && key_value<=57 && key_value!=17&&key_value!=9 && key_value!=32) {
		  
	  } else {
		     var pos=getPositionForTextArea(obj);
		     var setPos = /[^a-zA-Z0-9,-._*\/\\()#@$%''' ]/g.test(obj.value);
			 obj.value=obj.value.replace(/[^a-zA-Z0-9,-._*\/\\()#@$%''' ]/g,'');
			 if(pos<obj.value.length && pos>=0){
				 if(setPos){
					 pos=pos-1;
				 }
			 }
			 setCursorPosition(obj,pos);
	  }
}
/**
 * 只能输入非中文
 * @param {Object} obj
 * @param {Object} e
 */
function OnlyEnglishChar(obj,e){
	var key_value= window.event ? e.keyCode:e.which;
	  if(key_value>8 && key_value<=57 && key_value!=17&&key_value!=9 && key_value!=32) {
		  
	  } else {
		     var pos=getPositionForInput(obj);
		     var setPos = /[\u4e00-\u9fa5]/g.test(obj.value);  
			 obj.value=obj.value.replace(/[\u4e00-\u9fa5]/g,'');
			 if(pos<obj.value.length && pos>=0){
				 if(setPos){
					 pos=pos-1;
				 }
			 }
			 setCursorPosition(obj,pos);
	  }
}

/***
 * 只接受
 * @param {Object} obj
 * @param {Object} e
 */
function OnlyEnglishNumber(obj,e){
	  var key_value= window.event ? e.keyCode:e.which;
	 
	  ///[^\w\,\.\/\s]/ig
	  if(key_value>8 && key_value<=57 && key_value!=17&&key_value!=9 && key_value!=32) {
		  
	  } else {
		     var pos=getPositionForInput(obj);
		     var setPos = /[^a-zA-Z0-9,-._*\/\\()#@$%''' ]/g.test(obj.value);
			 obj.value=obj.value.replace(/[^a-zA-Z0-9,-._*\/\\()#@$%''' ]/g,'');
			 if(pos<obj.value.length && pos>=0){
				 if(setPos){
					 pos=pos-1;
				 }
			 }
			 setCursorPosition(obj,pos);
	  }
}

/**
 * 描述：只允许电话号码
 * @param {Object} obj
 * @param {Object} e
 */
function OnlyPhoneNumber(obj,e){
	  var key_value= window.event ? e.keyCode:e.which;
	
	  //屏蔽shift键
	if (e.shiftKey) {
		obj.value=obj.value.replace(/[^0-9-]/g,'');
		e.cancelBubble = true;
		e.keyCode = 0;
		return false;
	}
		 
	  if(key_value>8 && key_value<=57 && key_value!=17&&key_value!=9) {
		  
	  } else {
		     var pos=getPositionForInput(obj);
			 obj.value=obj.value.replace(/[^0-9-]/g,'');
			 //必须保证第一个为数字而不是.
			 obj.value = obj.value.replace(/^\-/g,"");
			 if(pos<obj.value.length && pos>=0)
				 pos=pos-1;
			 setCursorPosition(obj,pos);
	}
}


//清除中文
function cleanCN(obj){
	obj.value=obj.value.replace(/[\u4e00-\u9fa5]/g,'');
}
//限定文本框只能数据数值类型
function clearNoNum(obj)
{
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}


function clearNoNumDec(obj,d_no,e)
{
	var key_value= window.event ? e.keyCode:e.which;
	if (e.shiftKey) {
		 obj.value = obj.value.replace(/[^\d.]/g,"");
        e.cancelBubble = true;
        e.keyCode = 0;
        return false;
	}
	
	if(key_value>=8 && key_value<=57 && key_value!=17 && key_value!=32) 
	{
	    if(obj.value.indexOf(".")>-1){
		     var temNum = obj.value.substring(obj.value.indexOf(".")+1);
		     if(temNum.length>d_no){
			    obj.value = obj.value.substring(0,obj.value.indexOf(".")+1) + temNum.substring(0,d_no).replace(".","");    
		     }
		  }
	 }else {
	     var pos=getPositionForInput(obj);    
		  if(pos<obj.value.length && pos>=0)
			  pos=pos-1;
		  //先把非数字的都替换掉，除了数字和.
		  obj.value = obj.value.replace(/[^\d.]/g,""); 
		  //必须保证第一个为数字而不是.
		  obj.value = obj.value.replace(/^\./g,"");
		  
		  var p = obj.value.indexOf(".");
		  //
		  if(p>-1){
		     var temNum = obj.value.substring(obj.value.indexOf(".")+1);
		     if(temNum.length>d_no){
		    	 obj.value = obj.value.substring(0,obj.value.indexOf(".")+1) + temNum.substring(0,d_no).replace(".","");    
		     } else 
		    	 obj.value = obj.value.substring(0,obj.value.indexOf(".")+1) + temNum.replace(".","");    
		  }
		  setCursorPosition(obj,pos);  	  
	 }
}

/**
 * 描述：限定文本框只能输入整数
 * @param {Object} obj
 */
function clearNoInteger(obj)
{
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\-/g,"");
}

/**
 * 描述：获取多文本框内容的文字定位
 * @param {Object} ctrl
 */
function getPositionForTextArea(ctrl) { 
	var CaretPos = 0; 
	if(document.selection) {// IE Support 
		ctrl.focus(); 
		var Sel = document.selection.createRange(); 
		var Sel2 = Sel.duplicate(); 
		Sel2.moveToElementText(ctrl); 
		var CaretPos = -1; 
		while(Sel2.inRange(Sel)){ 
			Sel2.moveStart('character'); 
			CaretPos++; 
		} 
	}else if(ctrl.selectionStart || ctrl.selectionStart == '0'){// Firefox support 
		CaretPos = ctrl.selectionStart; 
	} 
	return (CaretPos); 
} 

/**
 * 描述：获取光标输入的位置
 * @param {Object} ctrl
 */
function getPositionForInput(ctrl){
	var CaretPos = 0;
	if (document.selection) { // IE Support
		ctrl.focus();
		var Sel = document.selection.createRange();
		Sel.moveStart('character', -ctrl.value.length);
		CaretPos = Sel.text.length;
	}else if(ctrl.selectionStart || ctrl.selectionStart == '0'){// Firefox support
		CaretPos = ctrl.selectionStart;
	}
	return (CaretPos);
} 
/**
 * 描述：设置光标输入的位置
 * @param {Object} ctrl
 * @param {Object} pos
 */
function setCursorPosition(ctrl, pos){
	if(ctrl.setSelectionRange){
		ctrl.focus();
		ctrl.setSelectionRange(pos,pos);
	}
	else if (ctrl.createTextRange) {
		var range = ctrl.createTextRange();
		range.collapse(true);
		range.moveEnd('character', pos);
		range.moveStart('character', pos);
		range.select();
	}
} 

/**
 * 描述：默认小数点后面添加0
 * @param obj
 */
function append_Weight_float(obj){
	var obj_value = obj.value.trim();
	var dh_index = obj_value.indexOf(".");
	var obj_len = obj_value.length;
	if(dh_index != -1){
		if(obj_len - dh_index == 1){
			obj_value = obj_value+"0";
		}
	}
	return obj_value;
}

/**
 * 描述：变成两位小数
 * @param num
 * @returns
 */
function fixed2Level(num){
	var number = num.value;
	if(number == ''){
		num.value = '';
	}else{
		var m = parseFloat(number).toFixed(2);
		num.value = m;
	}
}


/**
 * 描述：　将数据转换成含两位小数点的数值
 * @param {Object} v
 */
function changeTwoDecimal(v) {
    if (isNaN(v)) {//参数为非数字
        return '';
    }
    var fv = parseFloat(v);
    fv = Math.round(fv * 100) / 100; //四舍五入，保留两位小数
    var fs = fv.toString();
    var fp = fs.indexOf('.');
    if (fp < 0) {
        fp = fs.length;
        fs += '.';
    }
    while (fs.length <= fp + 2) { //小数位小于两位，则补0
        fs += '0';
    }
    return fs;
}
/**
 * 将数据转换成含一位小数点的数值，返回值
 * @param {Object} v
 */
function changeOneDecimal(v) {
    if (isNaN(v)) {//参数为非数字
        return '';
    }
    var fv = parseFloat(v);
    fv = Math.round(fv * 10) / 10; //四舍五入，保留两位小数
    var fs = fv.toString();
    var fp = fs.indexOf('.');
    if (fp < 0) {
        fp = fs.length;
        fs += '.';
    }    
	while (fs.length <= fp + 1) { //小数位小于一位，则补0
        fs += '0';
	}
    return fs;
}

/**
 * 描述：将控件数据转换成含一位小数点的数值
 * @param {Object} obj
 */
function OneDecimal(obj) {
    if (isNaN(obj.value)) {//参数为非数字
        return '';
    }
    var fv = parseFloat(obj.value);
    fv = Math.round(fv * 10) / 10; //四舍五入，保留两位小数
    var fs = fv.toString();
    var fp = fs.indexOf('.');
    if (fp < 0) {
        fp = fs.length;
        fs += '.';
    }
    
	while (fs.length <= fp + 1) { //小数位小于一位，则补0
        fs += '0';
    }
	obj.value=fs;
    //return obj;
}

/**
 * 只取英文字母
 * @param en_name
 */
function getOnlyEnName(countryname){
	var i = countryname.indexOf('(');
	var en_countryname = countryname;
	if(i != -1){
		en_countryname = countryname.substring(0,i);
	}
	return en_countryname;
}

/**
 * 描述：清除所有非字母数字空格的其他字符
 * @param {Object} obj
 */
function OnlyPostalCode(obj){
	 obj.value=obj.value.replace(/[^a-zA-Z0-9\s()]/g,''); 
}

//清除所有非字母数字空格的其他字符
function cleanNumber(obj){
	 obj.value=obj.value.replace(/[^0-9]/g,''); 
}


/**
 * 过滤字符串的特殊字符
 * @returns
 */
String.prototype.trimSql = function(){
	return $.trim(this).replace(/\n|\r|\t/g,"").replace(/\'/g,"\\'");
};


function trim(vStr){ 
   return vStr.replace(/(^\s+)|(\s+$)/g, ""); 
}


//让程序支持字符串trim函数
String.prototype.trim=function(){ 
	// return this.replace(/(^\s*)|(\s*$)/g, ""); //注意这个方法在Ie8上失效,所以重写成jQuery的trim
	return $.trim(this);
};

/**
 * 去掉输入框后面的所有空格
 * @param input
 */
function trimAfter(input){
	var in_val = $(input).val().trim();
	$(input).val(in_val);//清除后面的空格
}


//让所有的操作按钮不可用
function disabledBtn(){
	$(":button").each(function(){
		$(this).attr('disabled',"true");
		$(this).css({"color": "#ffffff","background": "#cccccc" });
	});
}



//验证整数
function RepNumber(obj) {
	var reg = /^[\d]+$/g;
	if (!reg.test(obj.value)) {
		var txt = obj.value;
		txt.replace(/[^0-9]+/, function (char, index, val) {// 匹配第一次非数字字符
			obj.value = val.replace(/\D/g, "");// 将非数字字符替换成""
			var rtextRange = null;
			if (obj.setSelectionRange) {
				obj.setSelectionRange(index, index);
			} else {// 支持ie
				rtextRange = obj.createTextRange();
				rtextRange.moveStart('character', index);
				rtextRange.collapse(true);
				rtextRange.select();
			}
		})
	}
}




/**
 * 如果是中文返回真，否则返回假
 * @param in_value
 * @returns
 */
function isChinese(in_value){
	return /[\u4e00-\u9fa5]/g.test(in_value); 
}


/**
 * 限定textarea文本输入域字数的长度
 * @param str
 * @param len
 */
function substrTextareaLen(str,len){
	var str_value = str.value;
	if(str_value != ''){
		var str_len = str_value.length;
		if(len < 0){
			len = 0;
		}
		if(str_len > len){
			str.value = str_value.substr(0,len);
		}
	}
}





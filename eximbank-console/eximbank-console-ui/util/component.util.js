/**
 * 系统共用工具类
 */
function bulidRoleCheckBox(json) {
	var ckboxs = [];
	//
	$.each(json, function(i) {
		var id = json[i].roleId;
		var name = json[i].roleName;
		var nodeStr = getCheckbox(id,name,false);
		ckboxs.push(nodeStr);
	});
	return ckboxs;
}

/**
 * 
 */
var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
function getFileSize(target) {
   var fileSize = 0;
   var filemaxsize = 2;//2M 
   //判断文件类型
   var filePath = target.value;
   if(filePath){ 
 	  var fileend = filePath.substring(filePath.lastIndexOf(".")); 
 	  if(".pdf"!=fileend && ".doc"!=fileend && ".docx"!=fileend 
 			  && ".txt"!=fileend&& ".jpg"!=fileend&& ".png"!=fileend
 			 && ".gif"!=fileend&& ".xls"!=fileend&& ".xlsx"!=fileend){
 		  $(target).next().text("文件类型不正确,只能上传文件或图片！"); 
 		  target.value ="";  
     	  return false; 
 	  }
   }
   //判断是否存在文件
   if (isIE && !target.files) {
      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
      if(!fileSystem.FileExists(filePath)){ 
    	  $(target).next().text("附件不存在，请重新输入！"); 
    	  return false; 
      }
      var file = fileSystem.GetFile (filePath);
      fileSize = file.Size;
   } else {
      fileSize = target.files[0].size;
   }
   var size = fileSize/1024/1024;//kb
   if(size > filemaxsize){
	   $(target).next().text("附件不能大于"+filemaxsize+"M！"); 
	   target.value =""; 
	   return false; 
   }
   if(size<=0){ 
	   $(target).next().text("附件大小不能为0M！"); 
	   target.value =""; 
	   return false; 
   } 
   var change = $("#fileChange");
   if(change){
	  $("#fileChange").val(tru);  
   }
   $(target).next().text("");
   return true;
}


/**
 * 获取多选框
 */
function getCheckbox(k_id,k_check) {
	return {id:Number(id),nocheck:k_check};
}

/**
 * 点击全部选中功能，只支持checkbox的name=ids的
 * @param ck
 */
function checkedAll(ck){
	var ck = ck.checked;
	if(ck){
		$("input[name='ids']").each(function(i){
			this.checked = true;
		});
	}else{
		$("input:checked").each(function(i){
			this.checked = false;
		});
	}
}

/**
 * 选择全部的复选框
 */
function select_all(obj){
	var check_boxs = document.getElementsByName("check_box");

	if(obj.checked){
	   for(var i=0;i<check_boxs.length;i++){
		   check_boxs[i].checked="checked";
	   }	   
	}
	else{
		for(var i=0;i<check_boxs.length;i++){
		   check_boxs[i].checked="";
	   }	   
	}
}


// JavaScript Document
function isIE(){ //ie?
	if (window.navigator.userAgent.toLowerCase().indexOf("msie")>=1){
		return true;
	}else{
		return false;
	}
}


/**
 * 关闭窗口 
 */
function closeWin(){
	window.close();
}

/**
 * 查看窗口
 * @param url
 * @param winTitle
 */
function openWin(url,winTitle){
	var returnVal = null;
	var openUrl = url;
	var iWidth=800; //弹出窗口的宽度;
	var iHeight=500; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	var w_title = winTitle==null?'':winTitle;
	var wd = window.open(openUrl,w_title,"height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft+",resizable=yes,scrollbars=yes");
	if (wd){
		window.wd.focus();//判断窗口是否打开,如果打开,窗口前置  
	}
	if(wd.closed){
		returnVal = window.returnVaule;//子窗体返回值
	}
}

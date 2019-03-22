$(function(){
	$("form :input.required").each(function(){
    var $required = $("<strong class='high'>*</strong>"); //创建元素
		$(this).parent().append($required); //然后将它追加到文档中
	});
	//文本框失去焦点
	$('form :input').blur(function(){
		var $parent = $(this).parent();
		$parent.find(".formtips").remove();
		var in_value = this.value.trim();
		this.value = in_value;
		//验证必填项
		if( $(this).is('.required') ){
			if( in_value=="" ){
				var errorMsg = '必填项.';
				$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
				return false;
			} 
		}
		//验证密码
		if( $(this).is('.password') ){
			//先判断非空
			if(in_value==""){
				return false;
			}
			if( in_value.length >= 8 && in_value.length <= 12){
				var okMsg = '输入正确.';
				$parent.append('<span class="formtips onSuccess">'+okMsg+'</span>'); 
			}else{
				var errorMsg = '密码为8位到12位以内的任意字符.';
				$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
			}
		}
        //手机号
        if( $(this).is('.checkTel') ){
        	//先判断非空
			if(in_value==""){
				return false;
			}
        	var isMobile=/^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/; //手机号码验证规则
        	if(!isMobile.test(in_value)){
        		var errorMsg = '请输入正确的手机号.';
        		$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
        	}else{
        		var okMsg = '输入正确.';
        		$parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
        	}
        }
        //联系电话
        if( $(this).is('.checkPhone') ){
        	//先判断非空
			if(in_value==""){
				return false;
			}
        	var isMobile=/^(?:13\d|15\d|18\d)\d{5}(\d{3}|\*{3})$/; //手机号码验证规则
        	var isPhone=/^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;   //座机验证规则
        	if(!isMobile.test(in_value) && !isPhone.test(in_value)){
        		var errorMsg = '请输入正确的电话号码.';
        		$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
        	}else{
        		var okMsg = '输入正确.';
        		$parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
        	}
        }
              
        //验证邮件
        if( $(this).is('.email') ){
        	//先判断非空
			if(in_value==""){
				return false;
			}
			var email_pattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        	if( in_value=="" || ( in_value!="" && !email_pattern.test(in_value) ) ){
        		var errorMsg = '请输入正确的E-Mail地址.';
        		$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
        	}else{
        		var okMsg = '输入正确.';
        		$parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
        	}
        }
        //验证是否含有中文
        if( $(this).is('.en') ){
        	//先判断非空
			if(in_value==""){
				return false;
			}
        	if( in_value=="" || ( in_value!="" && /[\u4e00-\u9fa5]/g.test(in_value) ) ){
        		var errorMsg = '不能输入中文.';
        		$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
        	}else{
        		var okMsg = '输入正确.';
        		$parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
        	}
        }
             
        //验证浮点数的判定
        if( $(this).is('.checkFloat') ){
        	//先判断非空
			if(in_value==""){
				return false;
			}
        	var isFloat = /^((\d+\.\d*[1-9]\d*)|(\d*[1-9]\d*\.\d+)|(\d*[1-9]\d*))$/; 
        	if( in_value==""||in_value.length > 6 || ( in_value!="" && !isFloat.test(in_value) ) ){
        		var errorMsg = '请输入小于5位的浮点型数据';
        		$parent.append('<span class="formtips onError">'+errorMsg+'</span>');
        	}else{
        		var okMsg = '输入正确.';
        		$parent.append('<span class="formtips onSuccess">'+okMsg+'</span>');
        	}
        } 
	});//end blur 
	//提交，最终验证。
	$(':submit').click(function(){
		alert("--------------");
		$("form :input.required").trigger('blur');
		$("form :input.password").trigger('blur');
		$("form :input.checkTel").trigger('blur');
		$("form :input.checkPhone").trigger('blur');
		$("form :input.email").trigger('blur');
		$("form :input.en").trigger('blur');
		$("form :input.checkFloat").trigger('blur');
		var numError = $('form .onError').length;
		if(numError){
			alert("还有必填项未填写的!");
			return false;
		}
	});
	 
})











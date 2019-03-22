/**
 * version 0.2
 * author fukai xinchen
 * date 2017/06/27
 * 添加token支持
 * commit支持传入before回调
 * date 2017/07/05
 * 快照改为同步上传
 */

var BDContext = (function($) {
	function BDContext(url, token) {
		this.url = url;
		this.token = token == undefined ? "" : token;
	}

	BDContext.prototype.pInterfaceUrl = "http://192.168.202.81:8082/anbangInf"
	BDContext.prototype.setInfUrl = function(infUrl) {
		this.InfUrl = infUrl;
	};
	BDContext.prototype.getInfUrl = function() {
		if(this.InfUrl) //是否设置过接口路径，否则使用默认
			return this.InfUrl;
		return this.pInterfaceUrl;
	};
	//发送interface ajax请求
	BDContext.prototype.invokeInf = function(actionSet, action, args, callback) {
		if(!args)
			args = {};
		$.ajax({
			url: this.getInfUrl(),
			data: {
				actionSet: actionSet,
				action: action,
				args: JSON.stringify(args)
			},
			dataType: "jsonp",
			async: false,
			//contentType:"application/json",
			error: function(xhttp, textStatus, erroThrown) {
				if(!callback) callback = function() {};
				switch(textStatus) {
					case "timeout":
						callback(ErrorCode.R0101.Code);
						break;
					case "error":
						callback(ErrorCode.R0500.Code);
						break;
					case "parsererror":
						callback(ErrorCode.R0501.Code);
						break;
					case "cancel":
						break;
					default:
						callback(ErrorCode.R0500.Code);
						break;
				}
			},
			success: function(resp) {
				if(callback)
					callback(resp.errorcode, resp.data);
			},
			type: "POST",
			headers: {}
		});
	};

	return BDContext;
})(jQuery);
var BDC = new BDContext();
var ErrorCode = (function() {
	var ErrorSeq = new Object();
	ErrorSeq.R0000 = {
		Code: "R0000",
		Msg: "操作成功"
	};
	ErrorSeq.R0003 = {
		Code: "R0003",
		Msg: "会话超时，请重新登录"
	};
	ErrorSeq.R0101 = {
		Code: "R0101",
		Msg: "网络连接失败"
	};
	ErrorSeq.R0500 = {
		Code: "R0500",
		Msg: "服务器调用失败"
	};
	ErrorSeq.R0501 = {
		Code: "R0501",
		Msg: "数据解析失败"
	};
	ErrorSeq.R0404 = {
		Code: "R0404",
		Msg: "未知字段路径"
	};
	return ErrorSeq;
})();

alert = function(title, message, callback) {
	(function(title, message, callback) {
		window.parent.alertBdiag = new window.parent.BootstrapDialog({
			type: BootstrapDialog.TYPE_PRIMARY,
			title: title || "提示",
			message: message,
			closable: false,
			data: {
				callback: callback
			},
			onhide: function(dialog) {
				!dialog.getData('btnClicked') && dialog.isClosable() && typeof dialog.getData('callback') === 'function' && dialog.getData('callback')();
			},
			buttons: [{
				label: '(Y)确定',
				cssClass: 'btn-primary',
				hotkey: 89,
				action: function(dialog) {
					dialog.setData('btnClicked', true);
					typeof dialog.getData('callback') === 'function' && dialog.getData('callback')();
					dialog.close();
				}
			}],
		});
		window.parent.alertBdiag.open();
	})(title, message, callback);
}
confirm = function(title, message, callback) {
	(function(title, message, callback) {
		window.parent.confirmBdiag = new window.parent.BootstrapDialog({
			type: BootstrapDialog.TYPE_PRIMARY,
			title: title || "提示",
			message: message,
			closable: false,
			data: {
				callback: callback
			},
			onhide: function(dialog) {
				!dialog.getData('btnClicked') && dialog.isClosable() && typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(false);
			},
			buttons: [{
					label: '(Y)确定',
					cssClass: 'btn-primary',
					hotkey: 89,
					action: function(dialog) {
						dialog.setData('btnClicked', true);
						typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(true);
						dialog.close();
					}
				},
				{
					label: '(N)取消',
					hotkey: 78,
					action: function(dialog) {
						dialog.setData('btnClicked', true);
						typeof dialog.getData('callback') === 'function' && dialog.getData('callback')(false);
						dialog.close();
					}
				}
			],
		});
		window.parent.confirmBdiag.open();
	})(title, message, callback);
}
/*var bdc = new BDContext();
function test() {
    bdc.invokeInf("khztsxxxcx","khztsxxxcx", ["101"],
        function(errorcode,data){
            console.log("errorcode",errorcode);
            console.log("data",data);
            if(errorcode=="R0000"){
                console.log(Object.keys(data));
                if(data!="10000"){
                }else{

                    alert(message);
                }
            }else{
                alert("调用失败,错误码"+errorcode);
            }
        });
}*/
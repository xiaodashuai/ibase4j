/**
 * 一般页面表单验证
 */
var CzmValidate = function() {
	this.email_pattern = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	this.phone_pattern = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
	this.tel_pattern = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
	this.number = /^\d+$/;
	this.IDCard13 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
	this.IDCard18 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
	/**
	 * 判断是否为数字
	 */
	this.isNumber = function(p) {
		return this.number.test(p);
	}
	
	/**
	 * 验证身份证号码是否正确
	 */
	this.isIDCard = function(cardNo) {
		var info = {
				isTrue : false,
				year : null,
				month : null,
				day : null,
				isMale : false,
				isFemale : false
			};
			if (!cardNo || (15 != cardNo.length && 18 != cardNo.length) ) {
				info.isTrue = false;
				return info.isTrue;
			}
			if (15 == cardNo.length) {
				var year = cardNo.substring(6, 8);
				var month = cardNo.substring(8, 10);
				var day = cardNo.substring(10, 12);
				var p = cardNo.substring(14, 15); //性别位
				var birthday = new Date(year, parseFloat(month) - 1,
						parseFloat(day));
				// 对于老身份证中的年龄则不需考虑千年虫问题而使用getYear()方法  
				if (birthday.getYear() != parseFloat(year)
						|| birthday.getMonth() != parseFloat(month) - 1
						|| birthday.getDate() != parseFloat(day)) {
					info.isTrue = false;
					alert('输入的身份证号里出生日期不对！');
				} else {
					info.isTrue = true;
					info.year = birthday.getFullYear();
					info.month = birthday.getMonth() + 1;
					info.day = birthday.getDate();
					if (p % 2 == 0) {
						info.isFemale = true;
						info.isMale = false;
					} else {
						info.isFemale = false;
						info.isMale = true
					}
				}
				return info.isTrue;
			}
			if (18 == cardNo.length) {
				var year = cardNo.substring(6, 10);
				var month = cardNo.substring(10, 12);
				var day = cardNo.substring(12, 14);
				var p = cardNo.substring(14, 17);
				var birthday = new Date(year, parseFloat(month) - 1,
						parseFloat(day));
				// 这里用getFullYear()获取年份，避免千年虫问题
				if (birthday.getFullYear() != parseFloat(year)
						|| birthday.getMonth() != parseFloat(month) - 1
						|| birthday.getDate() != parseFloat(day)) {
					info.isTrue = false;
					alert('输入的身份证号里出生日期不对！');
					return info.isTrue;
				}
				var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子  
				var Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值.10代表X 
				// 验证校验位
				var sum = 0; // 声明加权求和变量
				var _cardNo = cardNo.split("");
				if (_cardNo[17].toLowerCase() == 'x') {
					_cardNo[17] = 10;// 将最后位为x的验证码替换为10方便后续操作  
				}
				for ( var i = 0; i < 17; i++) {
					sum += Wi[i] * _cardNo[i];// 加权求和  
				}
				var i = sum % 11;// 得到验证码所位置
				if (_cardNo[17] != Y[i]) {
					alert('18位身份证的校验码不正确！');
					return info.isTrue = false; 
				}
				info.isTrue = true;
				info.year = birthday.getFullYear();
				info.month = birthday.getMonth() + 1;
				info.day = birthday.getDate();
				if (p % 2 == 0) {
					info.isFemale = true;
					info.isMale = false;
				} else {
					info.isFemale = false;
					info.isMale = true
				}
				return info.isTrue;
			}
			return info.isTrue;
	}
	/**
	 * 判断身份证号码正确性
	 */
	this.isID = function(num) {
		num = num.toUpperCase();
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
		if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
		{
			alert('输入的身份证号长度不对，或者号码不符合规定！\n15位号码应全为数字，18位号码末位可以为数字或X。');
			return false;
		}
		// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
		// 下面分别分析出生日期和校验位
		len = num.length;
		if (len == 15)
		{
			re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			var arrSplit = num.match(re);
			// 检查生日日期是否正确
			var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3]+ '/' + arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2]))
					&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
					&& (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay)
			{
				alert('输入的身份证号里出生日期不对！');
				return false;
			}else{
				// 将15位身份证转成18位
				// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5','4', '3', '2');
				var nTemp = 0, i;
				num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
				for (i = 0; i < 17; i++)
				{
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				num += arrCh[nTemp % 11];
				return true;
			}
		}
		if (len == 18)
		{
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			var arrSplit = num.match(re);
			// 检查生日日期是否正确
			var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/"+ arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2]))
					&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
					&& (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay)
			{
				//alert(dtmBirth.getYear());
				//alert(arrSplit[2]);
				alert('输入的身份证号里出生日期不对！');
				return false;
			}else{
				// 检验18位身份证的校验码是否正确。
				// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var valnum;
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5','4', '3', '2');
				var nTemp = 0, i;
				for (i = 0; i < 17; i++)
				{
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				valnum = arrCh[nTemp % 11];
				if (valnum != num.substr(17, 1))
				{
					alert('18位身份证的校验码不正确！');
					return false;
				}
				return true;
			}
		}
		return false;
	}
	/**
	 * 匹配字母和数字
	 */
	this.isPassword = function(p) {
		if (p.match(/\d+/g) != null && p.match(/[a-z]+/g) != null
				&& p.match(/[A-Z]+/g) != null && p.length > 7) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 验证电子邮箱格式，正确返回真否则返回假
	 */
	this.isEmail = function(m) {
		return this.email_pattern.test(m);
	};
	/**
	 * 验证手机号码是否正确，正确返回真否则返回假
	 */
	this.isTelephone = function(tel) {
		return this.tel_pattern.test(tel);
	};
	/**
	 * 验证固定电话，正确返回真否则返回假
	 */
	this.isPhone = function(tel) {
		return this.phone_pattern.test(tel);
	};

};

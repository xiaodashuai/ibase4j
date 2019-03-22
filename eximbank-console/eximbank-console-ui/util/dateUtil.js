/**
 * 
 * 日期工具
 * @author czm_hudy@126.com
 * 日期格式说明: 'yy-mm-dd','yy-mm-dd HH','yy-mm-dd HH:MM','yy-mm-dd HH:MM:ss','yy-mm-dd W HH:MM:ss'
 * yy 年(说明：四位的年,例如:2012）
 * mm 月
 * dd 日
 * HH 时(说明：24小时时间，例如03）
 * MM 分
 * ss 秒
 * 
 * @return
 */
function DateUtil(){
	this.config = {
			morning : "上午",
		    afterNoon : "下午",
		    weekNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
		    weekNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
		    patterns: ['yy-mm-dd','yy-mm-dd HH','yy-mm-dd HH:MM','yy-mm-dd HH:MM:ss','yy-mm-dd W HH:MM:ss']
	};
	this.defaultPattern = this.config.patterns[0];
};

DateUtil.prototype.checkFormat = function(pattern){
	var accord = false;
	for(var i=0; i<this.config.patterns.length; i++){
		if(this.config.patterns[i] == pattern){
			accord = true;
			break;
		}
	}
	return accord;
};

/**
 * 判断日期是否合法
 */
DateUtil.prototype.isDate = function(str){
	var reg=/^(\d{1,4})-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
	var r=str.match(reg); 
	if(r==null) return false; 
	r[2]=r[2]-1; 
	var d= new Date(r[1],r[2],r[3],r[4],r[5],r[6]); 
	if(d.getFullYear()!=r[1]) return false; 
	if(d.getMonth()!=r[2]) return false; 
	if(d.getDate()!=r[3]) return false; 
	if(d.getHours()!=r[4]) return false; 
	if(d.getMinutes()!=r[5]) return false; 
	if(d.getSeconds()!=r[6]) return false; 
	return true; 
}

DateUtil.prototype.nowDate = function(pattern){
	if(pattern != ''){
		return this.createDate(new Date(),pattern);
	}else{
		return this.createDate(new Date(),this.defaultPattern);
	}
};

//获取符合中国格式的日期
DateUtil.prototype.getDate = function() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() +1;
	var da = date.getDate();
	if (month < 10) {
		month = "0" + month;
	}
	if (da < 10) {
		da = "0" + da;
	}
	var str = year + "-" + month + "-" + da;
	return str;
}
/**
 * 获取系统日期
 * @param {Object} pattern 格式
 */
DateUtil.prototype.nowTime = function(pattern){
	if(pattern && pattern!= ''){
		return this.createDate(new Date(),pattern);
	}else{
		return this.createDate(new Date(),this.config.patterns[3]);
	}
};

/***
 * 转成日期对象
 */
DateUtil.prototype.parseDate = function(dateString){
	var dates = dateString.split('-');
	var curDate = new Date(dates[0],dates[1]-1,dates[2]);
	return curDate;
}

/***
 * 获取星期几
 */
DateUtil.prototype.getWeek = function(dateString){
	var dt = this.parseDate(dateString);
	var day = dt.getDay();
	switch(day){
	case 0:
		return "周日";
		break;
	case 1:
		return "周一";
		break;
	case 2:
		return "周二";
		break;
	case 3:
		return "周三";
		break;
	case 4:
		return "周四";
		break;
	case 5:
		return "周五";
		break;
	case 6:
		return "周六";
		break;
	}
	return "格式不对";
}

/**
 * 判断日期大小，0：相等；1：大于；-1：小于
 */
DateUtil.prototype.compareDate = function(date1,date2){
		var a =(Date.parse(date2)-Date.parse(date1))/3600/1000;
		if(a == 0){
			return 0;
		}else if(a > 0){
			return 1;
		}else if(a<0){
			return -1;
		}
}

/**
 * 如果datetime1<=datetime2返回真，否则返回假
 * @param datetime1
 * @param datetime2
 * @returns {Boolean}
 */
DateUtil.prototype.compare = function(datetime1,datetime2){
	if(datetime1 != '' && datetime2 != ''){
		var beginTime = datetime1;
		var endTime = datetime2;
		
		var beginTimes=beginTime.substring(0,10).split('-');
		var endTimes=endTime.substring(0,10).split('-');
		
		beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
		endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);

		var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
		if(a<0){
			return true;
		}else{
			return false;
		}
	}
	return false;
};

/**
 * 如果datetime2>datetime1返回真，否则返回假
 * @param datetime1
 * @param datetime2
 * @returns {Boolean}
 */
DateUtil.prototype.compareTo = function(datetime1,datetime2){
	if(datetime1 != '' && datetime2 != ''){
		var beginTime = datetime1;
		var endTime = datetime2;
		
		var beginTimes=beginTime.substring(0,10).split('-');
		var endTimes=endTime.substring(0,10).split('-');
		
		beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
		endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
        
		var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
		if(a> 0 ){
			return true;
		}else{
			return false;
		}
	}
}

//计算相差多少分钟
DateUtil.prototype.diffMinute = function(datestr1,datestr2){
	var beginTimes=datestr1.substring(0,10).split('-');
	var endTimes=datestr2.substring(0,10).split('-');
	
	var beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+datestr1.substring(10,19);
	var endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+datestr2.substring(10,19);
	
	var a =(Date.parse(endTime)-Date.parse(beginTime))/60/1000;
	return a;
}

//增加天  
DateUtil.prototype.addDay = function(date,value)  
{  
	date.setDate(date.getDate()+value);  
}  
 
//   增加月  
DateUtil.prototype.addMonth =function(date,value)  
{  
	date.setMonth(date.getMonth()+value);  
}  
 
//   增加年  
DateUtil.prototype.addYear = function(date,value)  
{  
	date.setFullYear(date.getFullYear()+value);  
}

/***
 * 日期加上天数后的新日期. 
 * 参数：date 日期类型；
 * 参数：days 数字类型；
 * 返回值：返回添加几天后的日期，格式是字符串日期；
 */  
DateUtil.prototype.addDays = function(date,days){
	var nd = new Date(date);  
    nd = nd.valueOf();  
    nd = nd + days * 24 * 60 * 60 * 1000;  
    nd = new Date(nd);  
    // alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" + nd.getDate() +   
    // "日");   
	var y = nd.getFullYear();  
	var m = nd.getMonth()+1;  
	var d = nd.getDate();  
	if(m <= 9) m = "0"+m;  
	if(d <= 9) d = "0"+d;   
	var cdate = y+"-"+m+"-"+d;  
	return cdate;  
} ;

//日期加上天数后的新日期.   
DateUtil.prototype.AddDays = function(datestr,days){
	var nd = this.parseDate(datestr);
//	var nd = new Date(date);  
    nd = nd.valueOf();  
    nd = nd + days * 24 * 60 * 60 * 1000;  
    nd = new Date(nd);  
    // alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" + nd.getDate() +   
    // "日");   
	var y = nd.getFullYear();  
	var m = nd.getMonth()+1;  
	var d = nd.getDate();  
	if(m <= 9) m = "0"+m;  
	if(d <= 9) d = "0"+d;   
	var cdate = y+"-"+m+"-"+d;  
	return cdate;  
} ;

/**
 * 计算两个日期的差多少天
 * @param d1 开始时间字符串yyyy-MM-dd HH:mm:ss
 * @param d2 结束时间字符串yyyy-MM-dd HH:mm:ss
 * @returns 返回相差的天数
 */
DateUtil.prototype.DateTimeDiff = function(d1,d2){
	var day = 24 * 60 * 60 *1000;  
	try{      
	   var timeArr = d1.split(" ");
	   var timeArr2 = d2.split(" ");
	   if(timeArr.length > 1 && timeArr2.length > 1){
		   var dateArr = timeArr[0].split("-");
		   var datetimeArr = timeArr[1].split(":");
		   
		   var checkDate = new Date();  
		   checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);  
		   checkDate.setHours(datetimeArr[0], datetimeArr[1], datetimeArr[2],0);
		   var checkTime = checkDate.getTime();  
		    
		   var dateArr2 = timeArr2[0].split("-");  
		   var datetimeArr2 = timeArr2[1].split(":");
		   
		   var checkDate2 = new Date();  
		   checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);  
		   checkDate2.setHours(datetimeArr2[0], datetimeArr2[1], datetimeArr2[2],0);
		   var checkTime2 = checkDate2.getTime();
		      
		   var cha = (checkTime2 - checkTime)/day;  
		   return Math.floor(cha); 
	   }else{
		  alert("日期不合法，格式如:YYYY-MM-DD HH:mm:SS");
		  return false;
	   }
	}catch(e){  
	   return false;  
	}  
}

/**
 * 两个日期的差值(d1 - d2).
 * @param d1 开始时间yyyy-MM-dd
 * @param d2 结束时间yyyy-MM-dd
 * @returns 返回相差的天数，如果出现异常返回false
 */
DateUtil.prototype.DateDiff = function(d1,d2){  
	var day = 24 * 60 * 60 *1000;  
	try{      
	   var dateArr = d1.split("-");  
	   var checkDate = new Date();  
	   checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]);  
	   var checkTime = checkDate.getTime();  
	    
	   var dateArr2 = d2.split("-");  
	   var checkDate2 = new Date();  
	   checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]);  
	   var checkTime2 = checkDate2.getTime();  
	      
	   var cha = (checkTime - checkTime2)/day;    
	   return cha;  
	}catch(e){  
	   return false;  
	}  
};// end fun   


DateUtil.prototype.createDate = function(date,pattern){
	return this.formatDate(date,pattern);
};

DateUtil.prototype.formatDate = function(date,pattern){
	var format = this.defaultPattern; 
	if(pattern != ''){
		if(this.checkFormat(pattern)){
			format = pattern;
		}else{
//			alert('日期格式不正确!');
		}
	}
	var dateStr = format;
	var splitTimes = format.split(' ');
	if(splitTimes.length == 1){
		var sdate = splitTimes[0];
		//拆分日期，两位一判断
		var sdate_array = sdate.split('-');
		for(var i=0; i<sdate_array.length;i++){
			if(sdate_array[i] == 'yy'){
				dateStr = dateStr.replace(/yy/, date.getFullYear());
			}else if(sdate_array[i] == 'mm'){
				dateStr = dateStr.replace(/mm/, this._appendZero(date.getMonth()+1, 2));
			}else if(sdate_array[i] == 'dd'){
				dateStr = dateStr.replace(/dd/, this._appendZero(date.getDate(), 2));
			}
		}
	}else if(splitTimes.length == 2){
		//先替换日期
		var sdate = splitTimes[0];
		var sdate_array = sdate.split('-');
		for(var i=0; i<sdate_array.length;i++){
			if(sdate_array[i] == 'yy'){
				dateStr = dateStr.replace(/yy/, date.getFullYear());
			}else if(sdate_array[i] == 'mm'){
				dateStr = dateStr.replace(/mm/, this._appendZero(date.getMonth()+1, 2));
			}else if(sdate_array[i] == 'dd'){
				dateStr = dateStr.replace(/dd/, this._appendZero(date.getDate(), 2));
			}
		}
		//在替换时间
		var stime = splitTimes[1];
		var stime_array = stime.split(':');
		for(var i=0;i<stime_array.length;i++){
			if(stime_array[i] == 'HH'){
				//24小时制
				dateStr = dateStr.replace(/HH/, this._appendZero(date.getHours(), 2));
			}else if(stime_array[i] == 'hh'){
				//12小时
				dateStr = dateStr.replace(/hh/, this._appendZero(date.getHours()>12?(date.getHours()-12):date.getHours(), 2));
			}else if(stime_array[i] == 'MM'){
				dateStr = dateStr.replace(/MM/, this._appendZero(date.getMinutes(), 2));
			}else if(stime_array[i] == 'ss'){
				dateStr = dateStr.replace(/ss/, this._appendZero(date.getSeconds(), 2));
			}
		}
	}
	return dateStr;
};

DateUtil.prototype._appendZero = function(value, length){
	   if(value) {
	    value = (value).toString();
	    if (value.length < length){
	     for(var i = 0; i< length - value.length; i++){
	      value = "0" + value;
	     }
	    }
	   }
	   return value;
};


/**       
 * 对Date的扩展，将 Date 转化为指定格式的String       
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符       
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)       
 * eg:       
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423       
 * (new Date()).Format("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04       
 * (new Date()).Format("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04       
 * (new Date()).Format("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04       
 * (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18       
 */          
DateUtil.prototype.Format = function(dt,fmt) { 
    var o = {           
    "M+" : dt.getMonth()+1, //月份            
    "d+" : dt.getDate(), //日            
    "h+" : dt.getHours()%12 == 0 ? 12 : dt.getHours()%12, //小时            
    "H+" : dt.getHours(), //小时            
    "m+" : dt.getMinutes(), //分            
    "s+" : dt.getSeconds(), //秒            
    "q+" : Math.floor((dt.getMonth()+3)/3), //季度            
    "S" : dt.getMilliseconds() //毫秒            
    };           
    var week = {           
    "0" : "/u65e5",           
    "1" : "/u4e00",           
    "2" : "/u4e8c",           
    "3" : "/u4e09",           
    "4" : "/u56db",           
    "5" : "/u4e94",           
    "6" : "/u516d"          
    };           
    if(/(y+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, (dt.getFullYear()+"").substr(4 - RegExp.$1.length));           
    }           
    if(/(E+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[dt.getDay()+""]);           
    }           
    for(var k in o){           
        if(new RegExp("("+ k +")").test(fmt)){           
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));           
        }           
    }           
    return fmt;           
};
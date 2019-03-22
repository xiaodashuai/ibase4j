/**
 * 功能：根据开始日期和结束日期计算还款计划
 * @param {Object} firstDay 开始日期
 * @param {Object} lastDay 结束日期
 * @param {Object} money 总金额
 * @param {Object} currencyText 币种
 * @param {Object} costMode 还款模式年(12),半年(6),季度（3）,月（1）
 */
function math_money_plan(firstDay, lastDay, money, currencyText, costMode) {
	//使用日期控件工具
	var dt = new DateUtil();
	//首先初始化一个还本计划数组
	var payList = new Array(); 

	//首次还款日拆分
	var st_ds = firstDay.split("-");
	var st_y = st_ds[0]; //年
	var st_m = st_ds[1]; //月
	var st_d = st_ds[2]; //日
	//末次还款日拆分
	var en_ds = lastDay.split("-");
	var en_y = en_ds[0]; //年
	var en_m = en_ds[1]; //月 
	var en_d = en_ds[2]; //日

	//下面根据还本方式进行判断计算（12年6半年3季1月）
	var PAY_MODE = parseInt(costMode);
	switch(PAY_MODE) {
		case 12: //年
			var div = parseInt(en_y) - parseInt(st_y); //计算相差多少年
			if(div < 1) {
				//如果是同一年，则生成一条记录
				var record = new Object();
				record.payDate = dt.getDaysByDate(lastDay); //还款日期
				record.currency = currencyText; //币种
				record.principalAmy = money.toFixed(2); //还款金额
				payList.push(record);
				return payList;
			} else {
				//生成多个计划
				var year = parseInt(st_y);
				var money_t = 0.00;
				var money_m = money / (div + 1);
				for(var i = 0; i <= div; i++) {
					var record = new Object();
					var day = year + "-" + st_m + "-" + st_d;
					//每月还款金额，精确2位
					if(div - i > 0) {
						money_t += roundNum(money_m);
					} else {
						money_m = roundNum(money) - roundNum(money_t);
						day = lastDay;
					}
					record.principalAmy = roundNum(money_m).toFixed(2);
					record.payDate = dt.getDaysByDate(day); //还款日期
					record.currency = currencyText; //币种
					payList.push(record);
					year++;
				}
			}
			break;
		case 6: //半年
			var y_div = parseInt(en_y) - parseInt(st_y); //计算相差多少年
			var end_m = parseInt(en_y) * 12 + parseInt(en_m);
			var start_m = parseInt(st_y) * 12 + parseInt(st_m);
			if(en_m == '01' || en_m == '1') {
				if(st_m == '01' || st_m == '1'){
					end_m = parseInt(en_y) * 12 + 0;
				}
			}
			if(st_m == '01' || st_m == '1') {
				start_m = parseInt(st_y) * 12 + 0;
			}
			var div_m = Math.abs(end_m - start_m); //计算两个时间相差多少月
			//跨度不够半年的1条记录
			if(div_m < 6) {
				var record = new Object();
				record.payDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				record.currency = currencyText; //币种
				record.principalAmy = money.toFixed(2); //还款金额
				payList.push(record);
			} else if(6 == div_m) { //跨度够半年的。生成2条记录
				//获取金额
				var money_m = money / 2;
				var record = new Object();
				record.payDate = dt.getDaysByDate(firstDay); //还款日期为首次还款日
				record.currency = currencyText; //币种
				record.principalAmy = roundNum(money_m).toFixed(2); //还款金额
				payList.push(record);
				//结束日期 
				var record2 = new Object();
				record2.payDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				record2.currency = currencyText; //币种
				money_m = roundNum(money) - roundNum(money_m);
				record2.principalAmy = roundNum(money_m).toFixed(2); //还款金额
				payList.push(record2);
				//返回 
				return payList;
			} else if(div_m > 6) { 
				//对6取余如果是0说明完整的半年
				var yu = div_m % 6;
				//超过半年的生成多个还款计划
				var capacity = 1;
				//如果跨年，则需要添加2个循环
				if(y_div == 1 && yu !=0){
					capacity = 2;
				}
				//相差的月份除以6获取半年的个数
				var m_size = parseInt(div_m / 6) + capacity;
				//开始月份
				var month = parseInt(st_m);
				//金额加法器，2位小数
				var money_t = 0.00;
				var money_m = money / m_size;
				var year = parseInt(st_y);
				var k = 0;
				//生成多条还款计划
				for(var i = 0; i < m_size; i++) {
					var record = new Object();
					//判断月份是否有效
					var month_str = dt.getMaxMonth(month);
					var day = '';
					//通过余数获取有效的月份
					var rem = month % 12;
					//如果正常月份，则继续，否则年份进1，然后取余获得月份
					if(month_str != '-1') {
						day = year + "-" + month_str + "-" + st_d;
					} else {
						month_str = dt.getMaxMonth(rem);
						//转换为0月份转换成12月
						if(rem == 0) {
							month_str = dt.getMaxMonth('0');
						}
						//月份复位
						month = parseInt(rem);
						//年份进1
						year++;
						//生成日期
						day = year + "-" + month_str + "-" + st_d;
					}
					//每月还款金额，精确2位
					if(m_size - i > 1) {
						money_t += roundNum(money_m);
					} else { //最后一次为末次还款日
						money_m = roundNum(money) - roundNum(money_t);
						day = lastDay;
					}
					record.payDate = dt.getDaysByDate(day); //还款日期
					record.currency = currencyText; //币种
					record.principalAmy = roundNum(money_m).toFixed(2);
					payList.push(record);
					//增加半年
					month += 6;
				}
			}
			break;
		case 3: //季度
			var y_div = parseInt(en_y) - parseInt(st_y); //计算相差多少年
			var end_m = parseInt(en_y) * 12 + parseInt(en_m);
			var start_m = parseInt(st_y) * 12 + parseInt(st_m);
			if(en_m == '01' || en_m == '1') {
				if(st_m == '01' || st_m == '1'){
					end_m = parseInt(en_y) * 12 + 0;
				}
			}
			if(st_m == '01' || st_m == '1') {
				start_m = parseInt(st_y) * 12 + 0;
			}
			var div_m = Math.abs(end_m - start_m); //计算两个时间相差多少月
			//跨度不够1个季度的生成1条记录
			if(div_m < 3) {
				var record = new Object();
				record.payDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				record.currency = currencyText; //币种
				record.principalAmy = money.toFixed(2); //还款金额
				payList.push(record);
			} else if(3 == div_m) { //跨度够1季度的。生成2条记录
				//获取金额
				var money_m = money / 2;
				var record = new Object();
				record.payDate = dt.getDaysByDate(firstDay); //还款日期为首次还款日
				record.currency = currencyText; //币种
				record.principalAmy = roundNum(money_m).toFixed(2); //还款金额
				payList.push(record);
				//结束日期 
				var record2 = new Object();
				record2.payDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				record2.currency = currencyText; //币种
				money_m = roundNum(money) - roundNum(money_m);
				record2.principalAmy = roundNum(money_m).toFixed(2); //还款金额
				payList.push(record2);
				//返回 
				return payList;
			} else if(div_m > 3) { 
				//对3取余如果是0说明完整的季度,超过1季度的生成多个还款计划
				var yu = div_m % 3;
				//超过1季度的生成多个还款计划
				var capacity = 1;
				//如果跨年，则需要添加2个循环
				if(y_div == 1 && yu !=0){
					capacity = 2;
				}
				//相差的月份除以3获取季度的个数
				var m_size = parseInt(div_m / 3) + capacity;
				//开始月份
				var month = parseInt(st_m);
				//金额加法器，2位小数
				var money_t = 0.00;
				var money_m = money / m_size;
				var year = parseInt(st_y);
				//生成多条还款计划
				for(var i = 0; i < m_size; i++) {
					var record = new Object();
					//判断月份是否有效
					var month_str = dt.getMaxMonth(month);
					var day = '';
					//通过余数获取有效的月份
					var rem = month % 12;
					//如果正常月份，则继续，否则年份进1，然后取余获得月份
					if(month_str != '-1') {
						day = year + "-" + month_str + "-" + st_d;
					} else {
						month_str = dt.getMaxMonth(rem);
						//转换为0月份转换成12月
						if(rem == 0) {
							month_str = dt.getMaxMonth('0');
						}
						//月份复位
						month = parseInt(rem);
						//年份进1
						year++;
						//生成日期
						day = year + "-" + month_str + "-" + st_d;
					}
					//每月还款金额，精确2位
					if(m_size - i > 1) {
						money_t += roundNum(money_m);
					} else { //最后一次为末次还款日
						money_m = roundNum(money) - roundNum(money_t);
						day = lastDay;
					}
					record.payDate = dt.getDaysByDate(day); //还款日期
					record.currency = currencyText; //币种
					record.principalAmy = roundNum(money_m).toFixed(2);
					payList.push(record);
					//增加1季度
					month += 3;
				}
			}
			break;
		case 1: //月
			var end_m = parseInt(en_y) * 12 + parseInt(en_m);
			var start_m = parseInt(st_y) * 12 + parseInt(st_m);
			var month_len = Math.abs(end_m - start_m); //计算相差多少月
			//如果不够1个月，生成1条记录，按照末次还款日生成一条
			if(month_len < 1) {
				var record = new Object();
				//如果是同一年，则生成一条记录
				record.payDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				record.currency = currencyText; //币种
				record.principalAmy = money.toFixed(2); //还款金额
				payList.push(record);
				return payList;
			} else {
				//计算相差几个月
				var month = parseInt(st_m); //开始的月份
				//计算金额
				var money_t = 0.00;
				var money_m = money / (month_len + 1);
				var year = parseInt(st_y);
				//大于1个月生成多条记录
				for(var i = 0; i <= month_len; i++) {
					var record = new Object();
					var month_str = dt.getMaxMonth(month);
					var day = '';
					//余数
					var rem = month % 12;
					//如果正常月份，则继续，否则增加年，然后取余月份
					if(month_str != '-1') {
						day = year + "-" + month_str + "-" + st_d;
					} else {
						month_str = dt.getMaxMonth(rem);
						//转换为0成-1解决不调用函数的问题
						if(rem == 0) {
							month_str = dt.getMaxMonth('0');
						}
						day = year + "-" + month_str + "-" + st_d;
					}
					//每年12个月为1年。所以年份加1
					if(rem == 0) {
						year++;
					}
					//每月还款金额，精确2位
					if(month_len - i > 0) {
						money_t += roundNum(money_m);
					} else {
						money_m = roundNum(money) - roundNum(money_t);
						day = lastDay;
					}
					record.payDate = dt.getDaysByDate(day); //还款日期
					record.currency = currencyText; //币种
					record.principalAmy = roundNum(money_m).toFixed(2);
					payList.push(record);
					//增加1个月
					month += 1;
				}
			}
			break;
		default:
			break;
	}
	//返回结果集
	return payList;
}

/**
 * 功能：根据开始日期和结束日期计算还息计划
 * @param {Object} firstDay
 * @param {Object} lastDay
 * @param {Object} costMode
 */
function math_interest_plan(firstDay, lastDay, costMode) {
	//使用日期控件工具
	var dt = new DateUtil();
	//首先初始化一个还息计划数组
	var payList = new Array(); 

	//首次还款日拆分
	var st_ds = firstDay.split("-");
	var st_y = st_ds[0]; //年
	var st_m = st_ds[1]; //月
	var st_d = st_ds[2]; //日
	//末次还款日拆分
	var en_ds = lastDay.split("-");
	var en_y = en_ds[0]; //年
	var en_m = en_ds[1]; //月
	var en_d = en_ds[2]; //日

	//下面根据还息方式进行判断计算（12年6半年3季1月）
	var PAY_MODE = parseInt(costMode);
	switch(PAY_MODE) {
		case 12: //年
			var div = parseInt(en_y) - parseInt(st_y); //计算相差多少年
			if(div < 1) {
				//如果是同一年，则生成一条记录
				var record = new Object();
				record.interestDate = dt.getDaysByDate(lastDay); //还款日期
				payList.push(record);
				return payList;
			} else {
				//生成多个计划
				var year = parseInt(st_y);
				for(var i = 0; i <= div; i++) {
					var record = new Object();
					var day = year + "-" + st_m + "-" + st_d;
					//每月还款金额，精确2位
					if(div - i <= 0) {
						day = lastDay;
					}
					record.interestDate = dt.getDaysByDate(day); //还款日期
					payList.push(record);
					year++;
				}
			}
			break;
		case 6: //半年
			var y_div = parseInt(en_y) - parseInt(st_y); //计算相差多少年
			var end_m = parseInt(en_y) * 12 + parseInt(en_m);
			var start_m = parseInt(st_y) * 12 + parseInt(st_m);
			if(en_m == '01' || en_m == '1') {
				if(st_m == '01' || st_m == '1'){
					end_m = parseInt(en_y) * 12 + 0;
				}
			}
			if(st_m == '01' || st_m == '1') {
				start_m = parseInt(st_y) * 12 + 0;
			}
			var div_m = Math.abs(end_m - start_m); //计算两个时间相差多少月
			//跨度不够半年的1条记录
			if(div_m < 6) {
				var record = new Object();
				record.interestDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				payList.push(record);
			} else if(6 == div_m) { //跨度够半年的。生成2条记录
				//获取金额
				var money_m = money / 2;
				var record = new Object();
				record.interestDate = dt.getDaysByDate(firstDay); //还款日期为首次还款日
				payList.push(record);
				//结束日期 
				var record2 = new Object();
				record2.interestDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				payList.push(record2);
				//返回 
				return payList;
			} else if(div_m > 6) { 
				//对6取余如果是0说明完整的半年
				var yu = div_m % 6;
				//超过半年的生成多个还款计划
				var capacity = 1;
				//如果跨年，则需要添加2个循环
				if(y_div == 1 && yu !=0){
					capacity = 2;
				}
				//相差的月份除以6获取半年的个数
				var m_size = parseInt(div_m / 6) + capacity;
				//开始月份
				var month = parseInt(st_m);
				var year = parseInt(st_y);
				//生成多条还款计划
				for(var i = 0; i < m_size; i++) {
					var record = new Object();
					//判断月份是否有效
					var month_str = dt.getMaxMonth(month);
					var day = '';
					//通过余数获取有效的月份
					var rem = month % 12;
					//如果正常月份，则继续，否则年份进1，然后取余获得月份
					if(month_str != '-1') {
						day = year + "-" + month_str + "-" + st_d;
					} else {
						month_str = dt.getMaxMonth(rem);
						//转换为0月份转换成12月
						if(rem == 0) {
							month_str = dt.getMaxMonth('0');
						}
						//月份复位
						month = parseInt(rem);
						//年份进1
						year++;
						//生成日期
						day = year + "-" + month_str + "-" + st_d;
					}
					//最后一次为末次还款日
					if(m_size - i <= 1) {
						day = lastDay;
					}
					record.interestDate = dt.getDaysByDate(day); //还款日期
					payList.push(record);
					//增加半年
					month += 6;
				}
			}
			break;
		case 3: //季度
			var y_div = parseInt(en_y) - parseInt(st_y); //计算相差多少年
			var end_m = parseInt(en_y) * 12 + parseInt(en_m);
			var start_m = parseInt(st_y) * 12 + parseInt(st_m);
			if(en_m == '01' || en_m == '1') {
				if(st_m == '01' || st_m == '1'){
					end_m = parseInt(en_y) * 12 + 0;
				}
			}
			if(st_m == '01' || st_m == '1') {
				start_m = parseInt(st_y) * 12 + 0;
			}
			var div_m = Math.abs(end_m - start_m); //计算两个时间相差多少月
			//跨度不够1个季度的生成1条记录
			if(div_m < 3) {
				var record = new Object();
				record.interestDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				payList.push(record);
			} else if(3 == div_m) { //跨度够1季度的。生成2条记录
				//获取金额
				var money_m = money / 2;
				var record = new Object();
				record.interestDate = dt.getDaysByDate(firstDay); //还款日期为首次还款日
				payList.push(record);
				//结束日期 
				var record2 = new Object();
				record2.interestDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				payList.push(record2);
				//返回 
				return payList;
			} else if(div_m > 3) {
				//对3取余如果是0说明完整的季度,超过1季度的生成多个还款计划
				var yu = div_m % 3;
				//超过1季度的生成多个还款计划
				var capacity = 1;
				//如果跨年，则需要添加2个循环
				if(y_div == 1 && yu !=0){
					capacity = 2;
				}
				//相差的月份除以3获取季度的个数
				var m_size = parseInt(div_m / 3) + capacity;
				//开始月份
				var month = parseInt(st_m);
				//加法器
				var year = parseInt(st_y);
				//生成多条还款计划
				for(var i = 0; i < m_size; i++) {
					var record = new Object();
					//判断月份是否有效
					var month_str = dt.getMaxMonth(month);
					var day = '';
					//通过余数获取有效的月份
					var rem = month % 12;
					//如果正常月份，则继续，否则年份进1，然后取余获得月份
					if(month_str != '-1') {
						day = year + "-" + month_str + "-" + st_d;
					} else {
						month_str = dt.getMaxMonth(rem);
						//转换为0月份转换成12月
						if(rem == 0) {
							month_str = dt.getMaxMonth('0');
						}
						//月份复位
						month = parseInt(rem);
						//年份进1
						year++;
						//生成日期
						day = year + "-" + month_str + "-" + st_d;
					}
					//最后一次为末次还款日
					if(m_size - i <= 1) {
						day = lastDay;
					}
					record.interestDate = dt.getDaysByDate(day); //还款日期
					payList.push(record);
					//增加1季度
					month += 3;
				}
			}
			break;
		case 1: //月
			var end_m = parseInt(en_y) * 12 + parseInt(en_m);
			var start_m = parseInt(st_y) * 12 + parseInt(st_m);
			var month_len = Math.abs(end_m - start_m); //计算相差多少月
			//如果不够1个月，生成1条记录，按照末次还款日生成一条
			if(month_len < 1) {
				var record = new Object();
				//如果是同一年，则生成一条记录
				record.interestDate = dt.getDaysByDate(lastDay); //还款日期为末次还款日
				payList.push(record);
				return payList;
			} else {
				//计算相差几个月
				var month = parseInt(st_m); //开始的月份
				//计算金额
				var year = parseInt(st_y);
				//大于1个月生成多条记录
				for(var i = 0; i <= month_len; i++) {
					var record = new Object();
					var month_str = dt.getMaxMonth(month);
					var day = '';
					//余数
					var rem = month % 12;
					//如果正常月份，则继续，否则增加年，然后取余月份
					if(month_str != '-1') {
						day = year + "-" + month_str + "-" + st_d;
					} else {
						month_str = dt.getMaxMonth(rem);
						//转换为0成-1解决不调用函数的问题
						if(rem == 0) {
							month_str = dt.getMaxMonth('0');
						}
						day = year + "-" + month_str + "-" + st_d;
					}
					//每年12个月为1年。所以年份加1
					if(rem == 0) {
						year++;
					}
					//每月还款金额，精确2位
					if(month_len - i <= 0) {
						day = lastDay;
					}
					record.interestDate = dt.getDaysByDate(day); //还款日期
					payList.push(record);
					//增加1个月
					month += 1;
				}
			}
			break;
		default:
			break;
	}
	//返回结果集
	return payList;
}
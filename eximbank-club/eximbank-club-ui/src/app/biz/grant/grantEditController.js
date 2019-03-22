'use strict';

angular.module('app').controller(
    'grantEditController', ['$scope', '$rootScope', '$state', '$http', 'toaster', '$CanvasService', '$filter','WebUploadService','$timeout',
        function($scope, $rootScope, $state, $http, toaster, $CanvasService, $filter,WebUploadService,$timeout) {
            var dateFilter = $filter('date');
			$scope.loading = false;
            //参数传递
            var tempId = $state.params.id;
            var code = $state.params.code; //流水号
            var title = "暂存编辑";
            $scope.title = $rootScope.title = title;
            var proIds = "0"; //单一产品表数据主键

            //切换tab
            $scope.changeTab = function(tab) {
                var promise = $CanvasService.createCanvas(angular.element('.canvas')[0], "B", angular.element('.canvasNum').find('.active').attr("num"), $scope.record.grantCode, null, $scope, tab);
                promise.then(function(data) {
                    $scope.tab = tab;
                    //判断是否已经加载了上传控件，如果没有加载，则再次加载
                    if(tab==3){
                        loadWebUploadTools('999');
                    }
                }, function(data) {
                    console.log("创建快照失败 error data ==" + data);
                }, function(data) {});
            };
            /**
			 * 功能：加载上传控件
			 */
			function loadWebUploadTools(index) {
				var code = $scope.record.grantCode;
				//初始化上传控件
				WebUploadService.initFiles([code, code], [index, "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout);
			}
            //方案查看
            $scope.openPopDebt = function() {
                var debtNum = $scope.record.debtCode;
                $scope.openPop('A', '', debtNum, '');
            }

            //通过编号查询暂存信息
            function getById(tempId) {
                $scope.loading = true;
                $.ajax({
                    type: 'PUT',
                    url: '/eximbank-club/temp/editTemp',
                    data: angular.toJson({
                        "id": tempId
                    })
                }).then(function(result) {
                    $scope.loading = false;
                    if(result) {
                        if(result.httpCode == 200) {
                            $scope.record = result.data;
                            //设置暂存的id到map
                            $scope.record.tempId = tempId;
                            var debtCode = $scope.record.debtCode;
                            var productCode = $scope.record.businessCode;
                            //宽限期默认为 0
                            $scope.record.gracePeriod=0;
                            $scope.record.repaymentType = 1;
                            //贸金业务政策性属性保存的值如果不符合逻辑，那么默认请选择
							if($scope.record.policy == '1' && $scope.record.traneFinanceBusiness == '3') {
								$scope.record.traneFinanceBusiness = '';
							}
							if($scope.record.policy == '0' && $scope.record.traneFinanceBusiness != '3') {
								$scope.record.traneFinanceBusiness = '';
							}
                            //如果政策性描述超过500，则截取
                            $scope.record.policyDescription=subStrLen($scope.record.policyDescription,500);
                            //处理点击详情按钮
                            $scope.custUserLetter = $scope.record.custUserLetter;
                            //如果没有获取到客户主体信息，那么重新获取一次
                            if(!$scope.custUserLetter){
                                getCustUser(debtCode);
                            }
                            //是否421判断
                            var isFour = $scope.record.is421;
                            if(isFour==null||isFour==''||isFour=='null'||isFour==0){
                                $scope.record.is421=2;
                            }
                            //初始化下拉框
                            initSelectBox(productCode, debtCode);
                            //查询约束条件
                            getCheck(debtCode, proIds);
                        } else {
                            $scope.msg = result.msg;
                        }
                    } else {
                        $scope.msg = "服务器异常，请刷新重试或联系管理员";
                    }
                    $scope.$apply();
                });
            }
            //查询数据
            getById(tempId);
            //担保合同编号隐藏
            $("input[name='guaranteeNo']").parent().parent().hide();
            /**
             * 查询方案对应的客户主体信息
             * @param {Object} debtCode
             */
            function getCustUser(debtCode){
                $scope.loading = true;
                $.ajax({
                    type: 'PUT',
                    url: '/eximbank-club/bizRentalFactoring/read/getCustUser',
                    data: angular.toJson({
                        "debtCode": debtCode
                    })
                }).then(function(result) {
                    if(result) {
                        if(result.httpCode == 200) {
                            $scope.record.custUserLetter = result.data;
                            //客户主体信息
                            $scope.custUserLetter = $scope.record.custUserLetter;
                        } else {
                            $scope.msg = result.msg;
                        }
                    } else {
                        $scope.msg = "服务器异常，请刷新重试或联系管理员";
                    }
                    $scope.loading = false;
                    $scope.$apply();
                });
            }

            //查询方案约束条件
            function getCheck(debtCode, pval) {
                $scope.loading = true;
                $.ajax({
                    type: 'PUT',
                    url: '/eximbank-club/temp/check',
                    data: angular.toJson({
                        "properInfo": pval,
                        "debtCode": debtCode
                    })
                }).then(function(result) {
                    if(result) {
                        if(result.httpCode == 200) {
                            $scope.gantRuleVerifVo = result.data;
                            //方案费率类型,如果等于0说明没有费率
							var progRateType = $scope.gantRuleVerifVo.progRateType;
							if(progRateType==0){
								//设置有无费用：无
								disabledRateType();
							}
                            setTimeout(function() {
                                initData();
                            }, 100);
                        } else {
                            $scope.msg = result.msg;
                        }
                    } else {
                        $scope.msg = "服务器异常，请刷新重试或联系管理员";
                    }
                    $scope.loading = false;
                    $scope.$apply();
                });
            }
            /**
			 * 判断方案是否无费率
			 */
			function disabledRateType(){
				//设置有无费用：无
				$scope.record.isRate = 2;
				$("input:radio[name='isRate']").eq(0).removeAttr('checked'); 
				$("input:radio[name='isRate']").eq(1).attr('checked','checked'); 
				$("input:radio[name='isRate']").attr("disabled",true);
				chgIsRate();
			}
            /**
			 * 点击详细信息，调用接口查询客户详情
			 * @param {Object} item
			 * @param {Object} index
			 */
			$scope.checkCust = function(item, index) {
				//设置选中项目索引值
				$scope.selectedLetterIndex = index;
				//展示对应的客户详情
				$scope.recordUseLetter = item;
				//cu是0说明是查询客户，展示关闭按钮
				var customerNo = item.custNo;
				//设置弹出框标题
				$scope.title = "用信主体授信方案";
				//挡板测试
				var dbc = [];
				var a = {
					amountType: "1101",
					creditLineName: "最高综合授信额度",
					creditNo: "1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-05",
					maturityDate: "2018-09-20"
				};
				var b = {
					amountType: "1102",
					creditLineName: "融资类保函额度",
					creditNo: "1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-05",
					maturityDate: "2018-09-20"
				};
				var c = {
					amountType: "1103",
					creditLineName: "非融资类保函额度",
					creditNo: "1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-05",
					maturityDate: "2018-09-20"
				};
				dbc.push(a);
				dbc.push(b);
				dbc.push(c);
				//调用信贷接口获取数据,使用延迟对象查询数据库，优化查询效率
				$.when(getCustomerCreditInfo(customerNo)).done(function(result){
					if(result && result!=-1){
						$scope.recordUseLetter.creditLinesList = result;
					}
				}).fail(function(){
					alert("调用信贷接口失败!");
				});
				if(!$scope.recordUseLetter.creditLinesList){
					console.log("信贷接口调用异常,系统将自动显示挡板测试的数据!");
					$scope.recordUseLetter.creditLinesList = dbc;
				}
				//设置已验证标识
				var names = document.getElementsByName('synchrRefreshCust');
				names[index].value = "yes";
				cleanRequiredWarn(names[index]);
			}
            //调用接口查询担保合同信息
            $scope.checkGuarantee = function($event) {
            	//获取索引
                var index = $($event.target).prev().val();
                //初始化上传控件
                loadWebUploadTools(index);
                //获取发放编号
                var code = $scope.record.grantCode;
                var contractList = $scope.record.bizGuaranteeContractList;
                
                $scope.title = "合同上传页面";
                $scope.selectedGuaranteeIndex = index;
                
                //查找到本按钮对应的form表单
                var table = $($event.target).closest(".table-editer-form");
                var guaranType = table.find("select[name='guaranType']").eq(0).val();//选择的担保类型
                //获取合同编号
				var guaranteeNo = table.find("input[name='guaranteeNo']").eq(0).val();
                $scope.selectedGuaranteeIndexCode = guaranteeNo;
	      		//从页面缓存（数组）中读取合同信息
				var contract = guaranteeGet(guaranteeNo);
				//判断旧的（数据库存的）合同信息是否空
                if(contractList != null && contractList.length>0) {
                    //如果数组中不存在，那么从旧数据中取
                    if(contract==null){
                 		contract = getGuaranteeByNo(contractList,guaranteeNo);
                 	}
                    //如果旧数据中取到后从新加载币种下拉框
					if(contract && contract!=null){
						var payCny = contract.currency;
						getCurrencySelect(payCny, $("select[name='gCurrency']").eq(0));
					}
                    //初始化弹出框内的内容
                    initContract(contract);
                    //如果数据库中保存的担保合同不全或不匹配，那么需要重新填写
                    if(contract==null){
                    	//加载币种,默认选中人民币
						getCurrencySelect('CNY', $("select[name='gCurrency']").eq(0));
						//担保合同编号赋值
						$("#guarNo").val(guaranteeNo);
						$("#agent").val($scope.record.agent); //经办人
						$("#agencies").val($scope.record.agencies); //经办机构
						var custName = $($event.target).parent().parent().next().find("input[name='guaranteeCustName']").eq(0).val();
						var custCode = $($event.target).parent().parent().next().find("input[name='guarantorCustId']").eq(0).val();
						$("#guarCustName").val(custName); //担保人客户名称
						$("#guarCustNo").val(custCode); //担保人客户编号
                    }
                }else{
                    //从页面数组中获取合同信息
					var contract = guaranteeGet(guaranteeNo);
                    //清理弹出框内的内容，注意：顺序一定不能放下面的代码下面
                    initContract(contract);
                    //如果没有合同信息，那么需要输入合同信息
                    if(contract==null){
                        //加载币种,默认选中人民币
                        getCurrencySelect('CNY', $("select[name='gCurrency']").eq(0));
                        //担保合同编号赋值
                        $("#guarNo").val(guaranteeNo);
                        $("#agent").val($scope.record.agent);//经办人
                        $("#agencies").val($scope.record.agencies);//经办机构
                        var custName = $($event.target).parent().parent().next().find("input[name='guaranteeCustName']").eq(0).val();
                        var custCode = $($event.target).parent().parent().next().find("input[name='guarantorCustId']").eq(0).val();
                        $("#guarCustName").val(custName); //担保人客户名称
                        $("#guarCustNo").val(custCode); //担保人客户编号
                    }else{
                        var payCny = contract.currency;
                        getCurrencySelect(payCny, $("select[name='gCurrency']").eq(0));
                    }
                }
                //设置担保类型一致
                var guarantorTypeCode = $($event.target).parent().parent().next().find("input[name='guarantorTypeCode']").eq(0).val();
                $("#guarContType").val(guarantorTypeCode);//担保合同类型--占用额度类型
            	//如果是保证C101，则保证担保类型可用，否则不可用
             	if(guaranType=='C101'){
             		$("#warrandice").removeProp("disabled");
             		$("#warrandice").addClass("required");
            	}else{
            		$("#warrandice").val("");
             		$("#warrandice").prop("disabled","disabled");
             		$("#warrandice").removeClass("required");
            	}
            	//如果是其他类型C104，则担保合同类型选择其他担保及保障措施（UE20）                          
				if(guaranType=='C104'){                                         
					$("#guarContType").val('UE20');//担保合同类型--占用额度类型             
				} 
            	//如果担保类型中的方式为一般，可明确划分使用本担保方式的份额默认100%，可修改。
        		var g_select = table.find("select[name='guaranType2']").eq(0);
        		var g_text = g_select.find("option:selected").text();
        		//担保类型中是否存在是否最高额担保合同,如果存在，那么是否最高额担保合同默认选中是，且不可修改
        		var common_index = g_text.indexOf('最高额');
        		if(common_index!=-1){
        			$("#guarMaxAmt").val(1);
        		}else{
        			$("#guarMaxAmt").val(0);
        		}
                //清理下有值的输入框但还是提醒必输消息
   	 			cleanGuarRequireWarn();
            }
            $scope.custLimitInfos = [{}];
            $scope.is_amtdisabled = [];
            $scope.custLimitFlag = true;
            /**
             * 功能：弹出窗口显示额度占用信息
             * @param {Object} item
             */
            $scope.checkCustLimit= function($event) {
                //获取索引
                var index = $($event.target).prev().val();
                $scope.selectedCustLimitIndex = index;
                //展示对应的客户详情
                var custLimitInfoList = $scope.record.bizCustLimitInfoList;
                $scope.title = "额度占用页面";
                var debtNum = $scope.record.debtCode;
                var code = $scope.gantRuleVerifVo.grantCode
                var currency = document.getElementsByName("currency");
                var convertedPrice = document.getElementsByName("convertedPrice");
                var paymentAmt = getSumAmt()+"";

                if (convertedPrice[0].value==0) {
                    alert("提示", "折算牌价未填写");
                }
                if (paymentAmt==0) {
                    alert("提示", "发放金额未填写");
                }

                if (custLimitInfoList != null && custLimitInfoList.length > 0) {
                    $scope.custLimitInfos = custLimitInfoList;
                } else if ($scope.custLimitFlag == true) {
                    $.ajax({
                        type: 'PUT',
                        url: '/eximbank-club/grant/getCustLimit',
                        data: angular.toJson({
                            "debtCode": debtNum,
                            "grantCode": code,
                            "cur": currency[0].value,
                            "amt": paymentAmt,
                            "convertedPrice": convertedPrice[0].value
                        })
                    }).then(function (result) {
                        $scope.loading = false;
                        if (result) {
                            if (result.httpCode == 200) {
                                var custLimitInfos = result.data;
                                $scope.custLimitInfos = custLimitInfos;
                            } else {
                                $scope.msg = "服务器异常，请刷新重试或联系管理员";
                            }
                        }
                        var existflag = false;

                        //进行校验，最高保证获取保证人的可明确划分之和，最高保证+信用，信用必须手填。
                        angular.forEach($scope.custLimitInfos, function (item, index, array) {
                            // item等价于array[index];
                            console.log(item.a + '=' + array[index].a);
                            if (item.creditLineName == "最高保证额度") {
                                existflag = true;
                                //可以手动修改
                                $scope.is_amtdisabled[index] = false;
                                //获取可明确之和
                                $scope.custLimitInfos[index].amt = getSumCalculateAmt();
                            } else if (item.creditLineName == "信用额度（免反担保额度）" && existflag == true) {
                                //可以手动修改
                                $scope.is_amtdisabled[index] = false;
                                $scope.custLimitInfos[index].amt = null;
                            } else {
                                $scope.is_amtdisabled[index] = true;
                            }

                        });
                        $scope.$apply();
                    });
                }



            }

            /**
             * 功能：把额度占用信息数组拆分成字符串
             */
            function splitCustLimitArray(CustLimits){
                var len = CustLimits.length;
                var result = "";
                for(var i=0;i<len;i++){
                    var contract = CustLimits[i];
                    var row = "amountType:"+contract.amountType+"#";
                    row+="amt:"+contract.amt+"#";
                    row+="bankTellerId:"+contract.bankTellerId+"#";
                    row+="changeNum:"+contract.changeNum+"#";
                    row+="creditLineName:"+contract.creditLineName+"#";
                    row+="creditNo:"+contract.creditNo+"#";
                    row+="cur:"+contract.cur+"#";
                    row+="custNameCN:"+contract.custNameCN+"#";
                    row+="custNo:"+contract.custNo+"#";
                    row+="debtCode:"+contract.debtCode+"#";
                    row+="grantCode:"+contract.grantCode+"#";
                    row+="id_:"+contract.id_+"#";
                    row+="objinr:"+contract.objinr+"#";
                    row+="objtyp:"+contract.objtyp+"#";
                    row+="ptyinr:"+contract.ptyinr+"#";
                    row+="remark:"+contract.remark+"#";
                    row+="warranteeNo:"+contract.warranteeNo+"#";
                    //连接每一个对象字符串
                    result+=row+"$";
                }
                return result;
            }

            /**
             * 额度占用信息刷新数据后从弹出框返回，同时返回数据
             * @param {Object} d
             */
            $scope.getCustInterfaceData = function(divId, btnId, hideName) {
                //验证表单里面必输项是否填写完成
                var check = nextCheck(divId, btnId);
                var custLimitIsEmpty = true;
                //
                angular.forEach($scope.custLimitInfos, function(item,index,array){
                    if (item.amt === "" || item.amt == null) {
                        alert("还有必填项未填写的!");
                        custLimitIsEmpty = false;
                    }
                });
                if(check && custLimitIsEmpty) {
                    $scope.custLimitFlag = false;
                    //清空验证消息
                    $("#warnmsg").text("");
                    //新增担保数据
                    $scope.custLimitAdd();
                    var index = $scope.selectedCustLimitIndex;
                    var names = document.getElementsByName(hideName);
                    if(names != null) {
                        names[index].value = "yes";
                        cleanRequiredWarn(names[index]);
                    }
                }
            }
            var bizCustLimitInfoList = new Array();
            /**
             * 额度占用信息添加
             *
             */
            $scope.custLimitAdd = function() {
                //把额度占用信息数组拆分成字符串,存入record中传入后台
                if ($scope.custLimitInfos !="" || $scope.custLimitInfos !=null) {
                    bizCustLimitInfoList = $scope.custLimitInfos;
                    // $scope.record.bizCustLimitInfoList =splitCustLimitArray($scope.custLimitInfos);
                }

            };


            /**
             * 额度占用信息获取保证人的可明确划分金额之和
             */
            function  getSumCalculateAmt() {
                var total = 0.00;
                var guaranTypes = document.getElementsByName("guaranType");
                var amtVals = document.getElementsByName("clearRatioAmt");

                for(var i = 0; i < guaranTypes.length; i++) {
                    var guaranTypeValue = guaranTypes[i].value;
                    if(guaranTypeValue == "C101") {
                        var amtValue = amtVals[i].value;
                        var amt_val = 0;
                        if(amtValue != null && amtValue != '') {
                            amt_val = amtValue.replace(/,/g, '');
                        }
                        total += Number(amt_val);
                    }
                }
                return total;
            }


            /**
             * 刷新数据后返回弹出框，同时返回数据
             * @param {Object} d
             */
            $scope.getInterfaceData = function(divId, btnId, hideName) {
                //验证表单里面必输项是否填写完成
				var check = nextCheck(divId, btnId);
				if(check) {
					//清空验证消息
					$("#warnmsg").text("");
					var index = $scope.selectedGuaranteeIndex;
					var code = $scope.selectedGuaranteeIndexCode;
					//新增担保数据
					$scope.guaranteeAdd(code);
					var names = document.getElementsByName(hideName);
					if(names != null) {
						names[index].value = "yes";
						cleanRequiredWarn(names[index]);
					}
				}
            }
            /**
             * 关闭主体授信
             * @param {Object} divId
             * @param {Object} hideName
             */
            $scope.closeLetter = function(divId, btnId, hideName) {
                var check = nextCheck(divId, btnId);
                if(check) {
                    var i = $scope.selectedLetterIndex;
                    var names = document.getElementsByName(hideName);
                    if(names != null) {
                        names[i].value = "yes";
                        cleanRequiredWarn(names[i]);
                    }
                }
            }
            /**
             * 切换是否精准扶贫贷款
             */
            $scope.chgAlleviationLoan = function(){
                var ck = $scope.record.alleviationLoan;
                if(ck != 1){
                    //产业精准扶贫贷款带动人数-去掉必输以及验证信息
                    $("#loanServicePeopleNum").removeClass("required");
                    $("#loanServicePeopleNum").removeClass("required-warn");
                    $("#loanServicePeopleNum").next().text("");
                    //精准扶贫贷款资金来源-去掉必输以及验证信息
                    $("#loanFunResource").removeClass("required");
                    $("#loanFunResource").removeClass("required-warn");
                    $("#loanFunResource").next().text("");
                    //精准扶贫主体性质-去掉必输以及验证信息
                    $("#povertyProperty").removeClass("required");
                    $("#povertyProperty").removeClass("required-warn");
                    $("#povertyProperty").next().text("");
                }
            }

            /**
             * 切换是否银团
             */
            $scope.chgIsSyndicated = function(){
                var ck = $scope.record.isSyndicated;
                $scope.record.syndicatedStatus=$("#syndicatedStatus").val();
                //是
                if(ck == 1){
                    //是否银团代理行-去掉必输以及验证信息
                    $(":radio[name='isSyndicatedAgency']").addClass("required");
                    //我行银团地位-去掉必输以及验证信息
                    $("#syndicatedStatus").addClass("required");
                }else{
                    //产业精准扶贫贷款带动人数-去掉必输以及验证信息
                    $(":radio[name='isSyndicatedAgency']").removeClass("required");
                    $(":radio[name='isSyndicatedAgency']").removeClass("required-warn");
                    $(":radio[name='isSyndicatedAgency']").next().text("");
                    //精准扶贫贷款资金来源-去掉必输以及验证信息
                    $("#syndicatedStatus").val("");
                    $("#syndicatedStatus").removeClass("required");
                    $("#syndicatedStatus").removeClass("required-warn");
                    $("#syndicatedStatus").next().text("");
                }
            }

            ///////////初始化//////////////
            function initData() {
                //费用
                var chargeTypes = $scope.record.chargeTypeList;
                var rateValues = $scope.record.rateValueList;
                var chargeTypeList = splitToArrayWithoutBlank(chargeTypes);
                var rateValueList = splitToArrayWithoutBlank(rateValues);
                //初始化是否存在费用类型
                chgIsRate();
                //加载多个表格
                createTable('chargeTable', chargeTypeList.length - 1, 1);
                //设置值选中或赋值
                for(var i = 0; i < chargeTypeList.length; i++) {
                    $("select[name='chargeType']").eq(i).val(chargeTypeList[i]);
                    $("input[name='rateValue']").eq(i).val(convertBlankOrEmpty(rateValueList[i]));
                }
                //币种
                var currencys = $scope.record.currencyList;
                var convertedPrices = $scope.record.convertedPriceList;
                var paymentAmts = $scope.record.paymentAmtList;
                var rateTypes = $scope.record.rateTypeList;
                var rateVals = $scope.record.rateValList;
                var irBks = $scope.record.irBkList;
                var termInterestRates = $scope.record.termInterestRateList;
                var floatDirectioins = $scope.record.floatDirectioinList;
                var floatModes = $scope.record.floatModeList;
                var floatingRates = $scope.record.floatingRateList;
                var varCycles = $scope.record.varCycleList;
                var chgCycleUnits = $scope.record.chgCycleUnitList;
                var overdueRateCalcModes = $scope.record.overdueRateCalcModeList;
                var overdueIncrRatios = $scope.record.overdueIncrRatioList;
                var calcDays = $scope.record.calcDaysList;
                var paymentModes = $scope.record.paymentModeList;
                //用币种做循环的参考对象
                var currencyList = splitToArrayWithoutBlank(currencys);
                var convertedPriceList = splitToArrayWithoutBlank(convertedPrices);
                var paymentAmtList = splitToArrayWithoutBlank(paymentAmts);
                var rateTypeList = splitToArrayWithoutBlank(rateTypes);
                var rateValList = splitToArrayWithoutBlank(rateVals);
                var irBkList = splitToArrayWithoutBlank(irBks);
                var termInterestRateList = splitToArrayWithoutBlank(termInterestRates);
                var floatDirectioinList = splitToArrayWithoutBlank(floatDirectioins);
                var floatModeList = splitToArrayWithoutBlank(floatModes);
                var floatingRateList = splitToArrayWithoutBlank(floatingRates);
                var varCycleList = splitToArrayWithoutBlank(varCycles);
                var chgCycleUnitList = splitToArrayWithoutBlank(chgCycleUnits);
                var overdueRateCalcModeList = splitToArrayWithoutBlank(overdueRateCalcModes);
                var overdueIncrRatioList = splitToArrayWithoutBlank(overdueIncrRatios);
                var calcDaysList = splitToArrayWithoutBlank(calcDays);
                var paymentModeList = splitToArrayWithoutBlank(paymentModes);
                //加载多个表格
                createTable('rateTable', currencyList.length - 1, 1);
                //设置值选中或赋值
                for(var i = 0; i < currencyList.length; i++) {
                    $("select[name='currency']").eq(i).val(currencyList[i]);
                    $("input[name='convertedPrice']").eq(i).val(convertBlankOrEmpty(convertedPriceList[i]));
                    $("input[name='paymentAmt']").eq(i).val(convertBlankOrEmpty(paymentAmtList[i]));
                    $("select[name='rateType']").eq(i).val(rateTypeList[i]);
                    //如果是浮动利率，利率输入框为只读模式
                    changeRateType($("select[name='rateType']").eq(i));
                    $("input[name='rateVal']").eq(i).val(convertBlankOrEmpty(rateValList[i]));
                    $("select[name='irBk']").eq(i).val(irBkList[i]);
                    $("select[name='floatDirectioin']").eq(i).val(floatDirectioinList[i]);
                    $("select[name='floatMode']").eq(i).val(floatModeList[i]);
                    $("input[name='floatingRate']").eq(i).val(convertBlankOrEmpty(floatingRateList[i]));
                    $("input[name='varCycle']").eq(i).val(convertBlankOrEmpty(varCycleList[i]));
                    $("select[name='chgCycleUnit']").eq(i).val(chgCycleUnitList[i]);
                    $("select[name='overdueRateCalcMode']").eq(i).val(overdueRateCalcModeList[i]);
                    $("input[name='overdueIncrRatio']").eq(i).val(convertBlankOrEmpty(overdueIncrRatioList[i]));
                    $("input[name='calcDays']").eq(i).val(convertBlankOrEmpty(calcDaysList[i])==''?360:calcDaysList[i]);
                    $("select[name='paymentMode']").eq(i).val(convertBlankOrEmpty(paymentModeList[i])==''?2:paymentModeList[i]);
                }
                //下面是担保信息回写
                var guaranTypes = $scope.record.guaranTypeList;
                var guaranType0 = $scope.record.guaranTypeLists;
                var guaranPortTypes = $scope.record.guaranPortTypeList;
                var clearRatios = $scope.record.clearRatioList;
                var clearRatioAmts = $scope.record.clearRatioAmtList;
                var notClearAmts = $scope.record.notClearAmtList;
                var pledgeTypes = $scope.record.pledgeTypeList;
                var contractList = $scope.record.bizGuaranteeContractList;
                //使用担保类型解析
                var guaranTypeList0 = splitToArrayWithoutBlank(guaranType0);
                var guaranTypeList = splitToArrayWithoutBlank(guaranTypes);
                var guaranPortTypeList = splitToArrayWithoutBlank(guaranPortTypes);
                var clearRatioList = splitToArrayWithoutBlank(clearRatios);
                var clearRatioAmtList = splitToArrayWithoutBlank(clearRatioAmts);
                var notClearAmtList = splitToArrayWithoutBlank(notClearAmts);
                var pledgeTypeList = splitToArrayWithoutBlank(pledgeTypes);
                var debtNum = $scope.record.debtCode;

                //加载多个表格
                createTable('guaranteeTable', guaranTypeList.length - 1, 1);
                //设置值选中或赋值
                for(var i = 0; i < guaranTypeList.length; i++) {
                    $("select[name='guaranType']").eq(i).val(guaranTypeList0[i]);
                    //加载二级下拉框
                    getGuaranteeInfoSelect(guaranTypeList0[i], guaranTypeList[i], $("select[name='guaranType2']").eq(i), debtNum);
                    $("select[name='guaranPortType']").eq(i).val(guaranPortTypeList[i]);
                    //如果是可明确划分份额，那么可明确划分使用本担保方式的份额必填，不可明确的信息不用填
                    guaranPortTypeChange($("select[name='guaranPortType']").eq(i));
                    $("input[name='clearRatio']").eq(i).val(clearRatioList[i]);
                    $("input[name='clearRatioAmt']").eq(i).val(clearRatioAmtList[i]);
                    $("input[name='notClearAmt']").eq(i).val(notClearAmtList[i]);
                    $("input[name='selectedGuaranteeIndex']").eq(i).val(i);
                    //通过二级担保类型加载担保和押品信息
                    getEditGuaranteeChildrenList($("select[name='guaranType2']").eq(i), pledgeTypeList[i], i);
                    //初始化合同信息
                	copyGuaranteeDbToArray(contractList[i]);
                }
                //初始化政策性属性分类
                var traneFinanceTypes = $scope.record.traneFinanceTypeList;
                var traneFinanceTypeList = splitToArrayWithoutBlank(traneFinanceTypes);
                $(":radio[name='traneFinanceType']").each(function(i) {
                    var tval = $(this).val();
                    if(isTraneFinanceTypeChecked(tval, traneFinanceTypeList)) {
                        $(this).prop("checked", true);
                    }
                })
                //政策性贷款业务状态
                var policyLendingStatus = $scope.record.policyLendingStatus;
                $("#policyLendingStatus").find("option[value='" + policyLendingStatus + "']").attr("selected", true);
                var newSyndicatedStatus=$scope.record.syndicatedStatus;
                if(newSyndicatedStatus=="" || newSyndicatedStatus==null){
                    $("#syndicatedStatus").find("option[value='']").attr("selected", true);
                }else{
                    $("#syndicatedStatus").find("option[value='" + newSyndicatedStatus + "']").attr("selected", true);
                }
                //判断银团地位是否为必填项
                checkSyndicatedStatus($scope.record.isSyndicated);
                //背景国别
                var backgroundNationality = $scope.record.backgroundNationality;
                $("select[name='backgroundNationality']").eq(0).val(backgroundNationality);
                //行业投向回显005
                var industryInvestment = $scope.record.industryInvestment;
                //中国制造2025及战略新兴产业分类回显006
                var emergingIndustryClassify = $scope.record.emergingIndustryClassify;
                //中国制造2025及战略新兴产业分类回显014
                var compare = $scope.record.compare;
                //贷款领域回显008
                var loanDomain = $scope.record.loanDomain;
                //产品类型回显012
                var businessType = $scope.record.businessType;
                businessType == ''?'9000890':businessType;//如果为空，则默认其他租金保理
                //创新业务类型回显011
                var innovativeBusinessType = $scope.record.innovativeBusinessType;
                //进出口货物及服务回显010
                var importExportGoodsService = $scope.record.importExportGoodsService;
                //对外投资贷款分类回显009
                var investmentLoanCkassifcation = $scope.record.investmentLoanCkassifcation;
                //精准扶贫项目所在地区回显013
                var povertyAddress = $scope.record.povertyAddress;
                if(povertyAddress==''){
                    povertyAddress = "0";
                }
                //精准扶贫项目类别
                var povertySort = $scope.record.povertySort;
                if(povertySort==''){
                    povertySort = "0";
                }
                //类型字符串
                var types = "005#006#008#009#010#011#012#013#014#015";
                //编码字符串
                var codes = convertNotBlank(industryInvestment);
                codes += "#" + convertNotBlank(emergingIndustryClassify);
                codes += "#" + convertNotBlank(loanDomain);
                codes += "#" + convertNotBlank(investmentLoanCkassifcation);
                codes += "#" + convertNotBlank(importExportGoodsService);
                codes += "#" + convertNotBlank(innovativeBusinessType);
                codes += "#" + convertNotBlank(businessType);
                codes += "#" + convertNotBlank(povertyAddress);
                codes += "#" + convertNotBlank(compare);
                codes += "#" + convertNotBlank(povertySort);

                var ul_arr = [
                    "industryInvestmentUL:" + industryInvestment,
                    "emergingIndustryClassifyUL:" + emergingIndustryClassify,
                    "compareUL:" + compare,
                    "loanDomainUL:" + loanDomain,
                    "businessTypeUL:" + businessType,
                    "innovativeBusinessTypeUL:" + innovativeBusinessType,
                    "importExportGoodsServiceUL:" + importExportGoodsService,
                    "investmentLoanCkassifcationUL:" + investmentLoanCkassifcation,
                    "povertyAddressUL:" + povertyAddress,
                    "povertySortUL:" + povertySort
                ];
                getMulitSelectList(ul_arr, types, codes);
                //如果进出口货物以及服务是无，设置隐藏表单的为不可用
                if(loanDomain && loanDomain.substr(0, 1) != 'A') {
					$("#importExportGoodsService").prop("disabled", true);
					$("#importExportGoodsServiceUL").find("#ul1").attr("id", "ul2");
					//重新初始化进出口货物及服务下拉框
   					getMulitSelectBox("importExportGoodsServiceUL", "importExportGoodsService", "AGR0280002", "010");
			   		//重新赋值操作
					$("#importExportGoodsServiceUL").find(".defaultDownBox").text("无");
					$("#importExportGoodsService").val("Y");
				}
                /////以下是还款计划
                $scope.payList = $scope.record.payList;
                $scope.loanList = $scope.record.loanList;
                //发放计划显示
                $scope.showTable = '1';
                //费用类型是否可新增
                chargeTypeReadOnly();
                //判断币种是否可修改 
				cnyReadOnly();
                //初始化表单验证
                initBlur();
            }
            //判断银团地位是否有值
            function checkSyndicatedStatus(data){
                if(data==1){
                    $("#syndicatedStatus").addClass("required");
                }
            }
            /**
			 * 功能：判断币种是否可修改
			 */
			function cnyReadOnly(){
				//判断币种是否可修改 
				var mCurrency = $scope.gantRuleVerifVo.mCurrency;//主币种
				var oCurrency = $scope.gantRuleVerifVo.aCurrency;//方案辅助币种(0无1其他可选币种2等值其他币种)
				$("select[name='currency']").each(function(i){
					//如果没有其他币种，则币种都不可修改，且默认选方案的主币种
					if('0'==oCurrency){
						$(this).prop("disabled", true);
						$(this).val(mCurrency);
						$(".cnyBtn").hide();
					}
				});
			}
            /**
             * 功能：判断费用类型是否可新增
             */
            function chargeTypeReadOnly(){
                //判断币种是否可修改
                var size=$("select[name='chargeType']").find("option").size();
                //如果没有其他费用，则不可添加
             	if(size < 3){
            		$(".ctAdd").hide();
            	}
            }
            /**
             * 获取非空的字符串，如果参数为空，则转换成0
             */
            function convertNotBlank(str){
                if(str==null||str==''){
                    return "0";
                }
                return str;
            }
            /**
              * 获取非空的字符串，如果参数为空或null或'null'，则转换成空字符串
              * @param {Object} str
              */
            function convertBlankOrEmpty(str){
                if(str==null||str==''||str=='null'){
                    return "";
                }
                return str;
            }
            /**
              * 截取字符串
              * @param {Object} str
              */
            function subStrLen(str,len){
                if(str==null||str==''||str=='null'){
                    return "";
                }
                var size = str.length;
                if(len > size){
                	len = size;
                }
                
                return str.substr(0,len);
            }
            /**
             * 判断是数组中是否存在元素
             * @param {Object} it 元素
             * @param {Object} arr 数组
             */
            function isTraneFinanceTypeChecked(it, arr) {
                if(arr) {
                    for(var i = 0; i < arr.length; i++) {
                        if(it == arr[i]) {
                            return true;
                        }
                    }
                }
                return false;
            }
            /**
             * 分割字符串，转换成数组
             * @param {Object} strs
             */
            function splitToArrayWithoutBlank(strs) {
                var a = [];
                if(strs == null || strs == '') {
                    return a;
                }
                var strArray = strs.split("#");
                $.each(strArray, function(i, v) {
                    var data = $.trim(v); //$.trim()函数来自jQuery
                    if('' != data) {
                        a.push(data);
                    }
                });
                return a;
            }

            //////////下面是验证输入表单是否合法/////////////////
            /**
             * 验证表单格式和逻辑
             */
            function validateFlied() {
                var ck = $scope.checkSendDate();
                if(!ck) {
                    alert("发放日期不合法!");
                    return false;
                }
                ck = $scope.checkBusinessContractDate();
				if(!ck) {
					alert("提示", "商务合同签订日期不合法!");
					return false;
				}
                ck = $scope.checkScopeBnsPeriod();
                if(!ck) {
                    alert("业务期限不合法!");
                    return false;
                }
                ck = $scope.checkRateValue();
                if(!ck) {
                    return false;
                }
                ck = $scope.checkPriceValue();
                if(!ck) {
                    return false;
                }
                ck = $scope.checkGuaranPortType();
                if(!ck) {
                    return false;
                }
                ck = $scope.checkInterfaceNull();
                if(!ck) {
                    return false;
                }
                ck = $scope.changeCaclWay();
                if(!ck) {
                    return false;
                }
                ck = $scope.changePayDate();
                if(!ck) {
                    return false;
                }
                ck = $scope.changeInterestDate();
                if(!ck) {
                    return false;
                }
                ck = $scope.changeLastDate();
                if(!ck) {
                    return false;
                }
                ck = $scope.checkStatus();
				if(!ck) {
					alert("方案状态已变化,不允许提交!详情请查看方案!");
					return false;
				}
                return true;
            }
            /**
             * 验证是否通过接口查询了数据
             */
            $scope.checkInterfaceNull = function() {
                //担保合同是否填写完毕
                var names1 = document.getElementsByName("synchrRefreshGuarantee");
                for(var i = 0; i < names1.length; i++) {
                    var name = names1[i].value;
                    if('yes' != name) {
                        $(name).parent().find(".text-danger").text("担保合同信息没有填写完整，请填写完整后再操作！");
                        return false;
                    }
                }
                //用信主体授信方案是否进行了接口调用
                var names2 = document.getElementsByName("synchrRefreshCust");
                for(var i = 0; i < names2.length; i++) {
                    var name = names2[i].value;
                    if('yes' != name) {
                        $(name).parent().find(".text-danger").text("用信主体授信方案没有查询详细信息，请查看详细信息重新后再操作！");
                        return false;
                    }
                }
                return true;
            }
			/**
             * 检验方案状态，验证是否可以进行发放
             */
            $scope.checkStatus = function() {
            	var debtCode = $scope.record.debtCode;
            	var sub = false;
            	$scope.loading = true;
				$.ajax({
					type: 'PUT',
					async: false,
					url: '/eximbank-club/grant/getDebt',
					data: angular.toJson({
						"debtCode": debtCode
					})
				}).then(function(result) {
					$scope.loading = false;
					if(result) {
						if(result.httpCode == 200) {
							var model = result.data;
							var status = model.solutionState;
							//如果方案状态是否为审核通过
							sub = status==6;
						} else {
							$scope.msg = "服务器异常，请刷新重试或联系管理员";
						}
					}
				});
				return sub;
            }
            //校验还本日期顺序
            $scope.changePayDate = function() {
				var payDateList = $scope.payList;
				for(var i = 1; i < payDateList.length; i++) {
					var fDay = payDateList[i].payDate;
					var money = payDateList[i].principalAmy;
					if(fDay==''){
						alert("提示", "还款日期不能为空!");
                        return false;
					}
					if(money==''){
						alert("提示", "还款金额不能为空!");
                        return false;
					}
					var lDay = payDateList[i - 1].payDate;
					if(Date.parse(fDay) - Date.parse(lDay) < 0) {
						alert("提示", "还款日期("+payDateList[i].payDate+")顺序有误");
                        return false;
					}
				}
                return true;
			}
            //校验还息日期顺序
            $scope.changeInterestDate = function() {
				var interestDateList = $scope.loanList;
				for(var i = 1; i < interestDateList.length; i++) {
					var fDay = interestDateList[i].interestDate;
					if(fDay==''){
						alert("提示", "计划还息日不能为空!");
                        return false;
					}
					var lDay = interestDateList[i-1].interestDate;
					if(Date.parse(fDay) - Date.parse(lDay) < 0) {
						alert("提示", "计划还息日顺序有误");
						return false;
					}
				}
				return true;
			}
            //还款计划表最后一天日期校验
            $scope.changeLastDate = function() {
                //末次还款日
				var payDateList = $scope.payList;
				var payLen = payDateList.length;
				var payLastDay = payDateList[payLen-1].payDate;
				//末次还息日
				var interestDateList = $scope.loanList;
				var itLen = interestDateList.length;
				var itLastDay = interestDateList[itLen-1].interestDate;
				//末次还款日
				var lastDay = $("#lastRepayDay").val();
				var dt = new DateUtil();
				if(dt.compareDate(lastDay, payLastDay) == 1) {
					alert("提示", "还本计划表日期超出末次还款日!");
					return false;
				}
				if(dt.compareDate(lastDay,payLastDay) != 0) {
					alert("提示", "还本计划表最后一条应为末次还款日!");
					return false;
				}
				if(dt.compareDate(lastDay,itLastDay) == 1) {
					alert("提示", "还息计划表日期超出末次还款日!");
					return false;
				}
				if(dt.compareDate(lastDay,itLastDay) != 0) {
					alert("提示", "还息计划表最后一条应为末次还款日!");
					return false;
				}
				return true;
            }
            //校验末次还息日期
			$scope.disableInterestDate = function (inDate) {
                var lDay = $("#lastRepayDay").val();
                if(lDay==inDate){
                	return true;
				}
				return false;
            }
            /**
             * 验证商务日期
             */
            $scope.checkBusinessContractDate = function() {
                 var sendDt = $("#businessContractDate").val();
                if(sendDt == '') {
                    return false;
                }
                var dt = new DateUtil();
                var now = dt.nowTime('yy-mm-dd'); //系统日期（当天)
                var checkBeanVo = $scope.gantRuleVerifVo; //查询到的约束条件对象
               	var efeeDate = dt.formatDateStr(checkBeanVo.efeectiveDate,'yy-mm-dd'); //方案生效时间
                var expiDate = dt.formatDateStr(checkBeanVo.expiDate,'yy-mm-dd');//失效时间
                //验证日期
                if(dt.compareDate(sendDt, efeeDate) == 1) {
                    var msg = "商务合同签订日期必须在方案生效时间(" + efeeDate + ")之后!";
                    alertWarn($("#businessContractDate"), msg);
                    return false;
                } else if(dt.compareDate(expiDate, sendDt) == 1) {
                    var msg = "商务合同签订日期必须在失效时间(" + expiDate + ")之前!";
                    alertWarn($("#businessContractDate"), msg);
                    return false;
                } else {
                    cleanRequiredWarn($("#businessContractDate"));
                }
                return true;
            }
            /**
             * 验证发放日期是否合适，发放日期，不可在方案失效日期之后，
             * 不可在系统日期之前；必须在生效日期之后
             */
            $scope.checkSendDate = function() {
                //初始化日期验证工具
                var dt = new DateUtil();
                var sendDt = $("#sendDate").val();
                if(sendDt == '') {
					return false;
				}
                var sendEndDate = $("#sendEndDate").val();
                if(sendDt == '') {
                    return false;
                }
                if(dt.compareDate(sendEndDate, sendDt) == 1) {
                    var msg = "发放失效日期必须在发放开始日期(" + sendDt + ")之后!";
                    alertWarn($("#sendEndDate"), msg);
                    $("#sendEndDate").parent().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
                    return false;
                } 
             	//获取暂存的类型
                var method = $scope.record.taskid;
                //如果是数据迁移。则不验证日期
                if("M"==method){
                    return true;
                }
                var now = dt.nowTime('yy-mm-dd'); //系统日期（当天)
                var checkBeanVo = $scope.gantRuleVerifVo; //查询到的约束条件对象
                var efeeDate = dt.formatDateStr(checkBeanVo.efeectiveDate,'yy-mm-dd'); //方案生效时间
				var expiDate = dt.formatDateStr(checkBeanVo.expiDate,'yy-mm-dd'); //失效时间
                //验证发放日期是否超出方案日期范围
                if(dt.compareDate(sendDt, efeeDate) == 1) {
                    var msg = "发放开始日期必须在方案生效日期(" + efeeDate + ")之后!";
                    alertWarn($("#sendDate"), msg);
                    $("#sendDate").parent().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
                    return false;
                } else if(dt.compareDate(expiDate, sendDt) == 1) {
                    var msg = "发放开始日期必须在方案失效日期(" + expiDate + ")之前!";
                    alertWarn($("#sendDate"), msg);
                    $("#sendDate").parent().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
                    return false;
                }  else if(dt.compareDate(expiDate, sendEndDate) == 1) {
                    var msg = "发放失效日期必须在方案失效日期(" + expiDate + ")之前!";
                    alertWarn($("#sendEndDate"), msg);
                    $("#sendEndDate").parent().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
                    return false;
                } else {
                    cleanRequiredWarn($("#sendDate"));
                    cleanRequiredWarn($("#sendEndDate"));
                }
                return true;
            }
            /**
			 * 功能：到期日期大于开始日期
			 */
			$scope.isHgDate = function() {
				var startDt = $("#startDate").val();
                if(startDt == '') {
                    return false;
                }
                var endDate = $("#dueDate").val();
                if(endDate == '') {
                    return false;
                }
                var dt = new DateUtil();
           		//验证日期是否在到期日期之后
               	if(dt.compareDate(endDate, startDt) == 1) {
                    var msg = "开始日期("+startDt+")必须小于到期日期("+endDate+")!";
                    alertWarn($("#startDate"), msg);
                    return false;
                } else{
                    cleanRequiredWarn($("#startDate"));
                }
                return true;
			}
            /**
			 * 功能：根据合同的开始日期和结束日期计算期限
			 */
            $scope.checkDueDate = function() {
                var startDt = $("#startDate").val();
                if(startDt == '') {
                    return false;
                }
                var dayUnit = $("#termUnit").val();
				var dayNum = $("#term").val();
				if(dayUnit == '') {
                    return false;
                }
				if(dayNum == '') {
                    return false;
                }
                var dt = new DateUtil();
                var now = dt.nowTime('yy-mm-dd'); //系统日期（当天)
                //验证日期是否在系统日期之后
//              if(dt.compareDate(startDt, now) == 1) {
//                  var msg = "开始日期("+startDt+")必须在系统日期之后!";
//                  alertWarn($("#dueDate"), msg);
//                  return false;
//              } else{
//                  cleanRequiredWarn($("#dueDate"));
//              }
				
				var period = 0;
				//计算闰年
				var year = new Date().getFullYear();
				var isLeap = (0==year%4)&&(0==year%100)&&(0==year%400);
				var days = isLeap?366:365;
				//根据间隔时间类型计算相差的天数
				if(dayUnit == 1){//年
					period = parseInt(dayNum)*days;
				}else if(dayUnit == 2){//月
					period = parseInt(dayNum)*30;
				}else if(dayUnit == 3){//日
					period = parseInt(dayNum);
				}
				//添加天数
				var dueDt = dt.AddDays(startDt,period);
				//赋值给到期日期
				$("#dueDate").val(dueDt);
                return true;
            }
            /**
             * 验证还款最后日期必须大于首次日期
             */
            $scope.checklastRepayDay = function() {
                var startDt = $("#fristRepayDate").val();
                var dueDt = $("#lastRepayDay").val();
                if(startDt == '') {
                    return false;
                }
                var dt = new DateUtil();
                var now = dt.nowTime('yy-mm-dd'); //系统日期（当天)
                //验证日期
                if(dt.compareDate(dueDt, startDt) == 1) {
                    var msg = "末次还款日必须在首次还本日(" + startDt + ")之后!";
                    alertWarn($("#lastRepayDay"), msg);
                    //					alert("提示",msg);
                    return false;
                } else {
                    cleanRequiredWarn($("#lastRepayDay"));
                }
                return true;
            }
            /**
             * 产品业务期限（融资、远期、付款）需要小于等于方案中填写的期限天数
             */
            $scope.checkScopeBnsPeriod = function() {
                var scopeBusinPeriod = $("#scopeBusinPeriod").val();
				if(scopeBusinPeriod == '') {
					return false;
				}
				var isNumber = /(^[1-9]([0-9]*)$|^[0-9]$)/; //正整数验证规则去0
				if(!isNumber.test(scopeBusinPeriod)) {
					var errorMsg = '请输入正确格式的数字';
					alertWarn($("#scopeBusinPeriod"), errorMsg);
					return false;
				}
				var checkBeanVo = $scope.gantRuleVerifVo; //查询到的约束条件对象
				var checkBusinPeriod = checkBeanVo.scopeBnsPeriod; //方案中填写的期限天数
				if(parseInt(scopeBusinPeriod) > parseInt(checkBusinPeriod)) {
					var msg = "业务期限不能大于方案的业务期限(" + checkBusinPeriod + ")天!";
					alertWarn($("#scopeBusinPeriod"), msg);
					return false;
				} else {
					cleanRequiredWarn($("#scopeBusinPeriod"));
				}
				return true;
            }

            /**
             * 获取所有费率值之和
             */
            function getTotalRate() {
                var total = 0.00;
                var rateVals = document.getElementsByName("rateValue");
                for(var i = 0; i < rateVals.length; i++) {
                    total += Number(rateVals[i].value);
                }
                return total;
            }
            /**
             * 费率值验证，每次发放条件时，验证R1<=r1+r2+r3<=R2
             */
            $scope.checkRateValue = function($event) {
                var checkBeanVo = $scope.gantRuleVerifVo; //查询到的约束条件对象
                var schemeRateForm = checkBeanVo.schemeRateForm; //方案费率形式（1方案费率、2方案费率范围）
                var packageRate = checkBeanVo.packageRate; //方案费率
                var rateRangeMix = checkBeanVo.rateRangeMix; //方案费率范围最低值
                var rateRangeMax = checkBeanVo.rateRangeMax; //方案费率范围最高值
                var isZk = checkBeanVo.whetherRateDiscount;//是否有折扣，如果存在折扣，则计算
				var zkRate = checkBeanVo.programRateDiscount;//方案费率折扣
				var zkMsg = "";//折扣消息提示
                //如果方案费率为空，则初始化为0
                if(packageRate==null||packageRate==''||packageRate=='null'){
                    packageRate = 0;
                    console.log('方案费率为空了！');
                }
                if(rateRangeMix == null) {
                    rateRangeMix = 0;
                }
                if(rateRangeMax == null) {
                    rateRangeMax = 1000;
                }
                //有折扣
				if(isZk && (isZk=='1'||isZk==1)){
					var zkValue = roundNum(Number(zkRate)/100);
					packageRate = packageRate*zkValue;
					zkMsg = "有"+zkRate+"%折扣,折扣后的费率"+packageRate;
				}
                var size = $("input[name='rateValue']").size(); //
                //如果是失去焦点事件触发验证如下：
                if($event != null) {
                    //清理消息
                    cleanRequiredWarn($("input[name='rateValue']"));
                    //判断
                    var rateValue = $($event.target).val();
                    rateValue = parseFloat(rateValue);
                    if(rateValue != '') {
                        //如果费率是具体值，验证如下：
                        if(schemeRateForm == 0) {
                            var resultRate = packageRate;
                            //当输入是多个费率值的时候
                            if(size > 1) {
                                resultRate = packageRate - getTotalRate();
                                if(resultRate <0) {
                                    var msg = "方案的费率值"+zkMsg+"是(" + packageRate + "),输入的费率值不能大于方案的总费率值!";
                                    alertWarn($($event.target), msg);
                                    return false;
                                }
                            } else if(size == 1) {
                                if(rateValue > resultRate) {
                                    var msg = "方案的费率值"+zkMsg+"是(" + packageRate + "),输入的费率值不能大于方案的总费率值!";
                                    alertWarn($($event.target), msg);
                                    return false;
                                }
                            }
                        } else if(schemeRateForm == 1) {
                            //如果费率是一个范围区间，验证如下：
                            var resultRate = getTotalRate(); //输入的值相加;
                            //当输入是多个费率值的时候
                            if(size > 1) {
                                if(resultRate > rateRangeMax || resultRate < rateRangeMix) {
                                    var msg = "方案的费率范围是[" + rateRangeMix + "~" + rateRangeMax + "],输入的费率值必须在该范围内!";
                                    alertWarn($($event.target), msg);
                                    return false;
                                }
                            } else if(size == 1) {
                                if(rateValue > rateRangeMax || rateValue < rateRangeMix) {
                                    var msg = "方案的费率范围是[" + rateRangeMix + "~" + rateRangeMax + "],输入的费率值必须在该范围内!";
                                    alertWarn($($event.target), msg);
                                    return false;
                                }
                            }
                        }
                    }
                } else {
                    //如果是最终提交验证如下---------
                    var resultRate = getTotalRate(); //实际总费率值;
                    //1.如果费率是具体值，验证如下：
                    if(schemeRateForm == 0) {
                        resultRate = packageRate - getTotalRate();
                        if(resultRate <0) {
                            var msg = "方案的费率值"+zkMsg+"是(" + packageRate + "),输入的费率总值不能大于方案的总费率值!";
                            alert(msg);
                            return false;
                        }
                    } else if(schemeRateForm == 1) {
                        //2.如果费率是一个范围区间，验证如下：
                        if(resultRate > rateRangeMax || resultRate < rateRangeMix) {
                            var msg = "方案的费率范围是[" + rateRangeMix + "~" + rateRangeMax + "],输入的费率总值必须在该范围内!";
                            alert(msg);
                            return false;
                        }
                    }
                }
                return true;
            }
            /**
             * 获取所有金额之和
             */
            function getTotalAmt() {
                var total = 0.00;
                var amtVals = document.getElementsByName("paymentAmt");
                for(var i = 0; i < amtVals.length; i++) {
                    var amtValue = amtVals[i].value;
                    var amt_val = 0;
                    if(amtValue!=null&&amtValue!=''){
                        amt_val = amtValue.replace(/,/g, '');
                    }
                    total += Number(amt_val);
                }
                return total;
            }
            /**
             * 验证金额是否超限
             */
            $scope.checkPriceValue = function($event) {
                var checkBeanVo = $scope.gantRuleVerifVo; //查询到的约束条件对象
               	//方案总金额
				var amt = checkBeanVo.amtStr;
				amt = amt.replace(/,/g, '');
                if($event != null) {
                    var price = $($event.target).val();
                    if(price==''){
                        alertWarn($($event.target), "请输入金额!");
                    }
                    //总金额;
                    var resultAmt = getTotalAmt();
                    if(Number(amt) < Number(resultAmt)) {
                        var msg = "输入的发放总金额不能大于方案金额(" + amt + ")!";
                        alertWarn($($event.target), msg);
                        return false;
                    } else {
                        cleanRequiredWarn($event.target);
                    }
                } else {
                    //如果是最终提交验证如下：
                    var resultAmt = getTotalAmt(); //总金额;
                    if(Number(amt) < Number(resultAmt)) {
                        var msg = "输入的金额不能大于方案金额(" + amt + ")!";
                        alert(msg);
                        return false;
                    }
                }
                return true;
            }
            /**
             * 判断担保份额划分方式
             */
            $scope.checkGuaranPortType = function() {
                var gpVal = 0;
                $("select[name='guaranPortType']").each(function(i) {
                    var gpt = $(this).val();
                    //同一笔发放审批中，如果有多个担保类型选择的不可明确划分，那么所有担保类型的不可明确金额必须相同
                    if(2 == gpt) {
                        var nca = $("input[name='notClearAmt']").eq(i).val();
                        //比较大小之前必须去掉金额里面的逗号
                       	nca = nca.replace(/,/g, '');
                        if(gpVal != '' && Number(gpVal) != Number(nca)) {
                            var msg = "不可明确金额必须相同！";
                            alertWarn($("input[name='notClearAmt']").eq(i), msg);
                            return false;
                        } else {
                            gpVal = nca;
                            cleanRequiredWarn($("input[name='notClearAmt']").eq(i));
                        }
                    }
                });
                return true;
            }
            //初始化验证
            $scope.submit = function() {
            	$scope.loading = true;
                //提交的时候需要快照
                var promise = $CanvasService.createCanvas(angular.element('.canvas')[0], "B", angular.element('.canvasNum').find('.active').attr("num"), $scope.record.grantCode, null, $scope, 4);
                promise.then(function(data) {
                    $scope.tab = 4;
                }, function(data) {
                    console.log("创建快照失败 error data ==" + data);
                }, function(data) {});
                //提交数据
                var m = $scope.record;
                if(submitCheck() && validateFlied() && m) {
                    //
                    var sendDt = $("#sendDate").val();
                    var sendEndDate = $("#sendEndDate").val();
                    $scope.record.sendDate = sendDt;
                    $scope.record.sendEndDate = sendEndDate;
                    $scope.record.properInfo = proIds;
                    $scope.record.bizGuaranteeContractList = splitContractArray(guaranteeContractList);
                    $scope.record.bizCustLimitInfoList =splitCustLimitArray(bizCustLimitInfoList);
                    $scope.record.custUserLetter = $scope.custUserLetter;
                    $scope.record.syndicatedStatus=$("#syndicatedStatus").val();
                    //组合字符串
                    getFecList();
                    getFepList();
                    getGuaranteeResultList();
                    bizRepaymentPlanList();
                    getZzxx();
                    //获取暂存的类型
                    var method = $scope.record.taskid;
                    //默认新增发放条件的提交
                    var actionUrl = "/eximbank-club/grant/save";
                    //判断暂存提交后，应该提交到哪个业务
                    if("M"==method){
                        //数据迁移的暂存，应该使用数据迁移的提交
                        actionUrl = "/eximbank-club/migratioin/save";
                    }else if("R"==method){
                        //如果是重新提交的暂存，则使用重新提交的保存
                        actionUrl = "/eximbank-club/grant/update";
                    }else if("C"==method){
                        //如果是变更的暂存，则使用变更的保存
                        actionUrl = "/eximbank-club/grant/save";
                    }else if("A"==method){
                        //如果是新增的暂存，则使用新增的保存
                        actionUrl = "/eximbank-club/grant/save";
                    }
                    //设置不能重复提交
                    $scope.isDisabled = true; //提交disabled
                    $.ajax({
                        url: actionUrl,
                        type: 'POST',
                        data: angular.toJson($scope.record)
                    }).then(callback);
                }else{
					//如果验证不通过，则也必须可以释放加载中...
					$scope.loading = false;
				}

                function callback(result) {
                	$scope.loading = false;
                    if(result.httpCode == 200) { //成功
                        alert("提交成功");
                        // 其他流程使用的统一跳转到发放条件查询界面
                        var queryURL = "main.biz.grant.query";
                        // 如果暂存类型是数据迁移类型，则转向到数据迁移列表
                        var taskId = $scope.record.taskid;
                        if(taskId=='M'){
                            queryURL = "main.biz.grant.migratioin";
                        }
                        setTimeout(function() {
                            $state.go(queryURL);
                        }, 1000);
                    } else {
                        alert(result.msg);
                        $scope.isDisabled = false;
                    }
                }
            }
            //暂存
            $scope.tempSave = function(vtype) {
                var m = $scope.record;
                if(m) {
                    if('dv1' == vtype) {
                        //政策性属性赋值
                        getZzxx();
                        $scope.record.syndicatedStatus=$("#syndicatedStatus").val();
                    } else if('dv2' == vtype || 'dv3' == vtype || 'dv4' == vtype) {
                        //政策性属性赋值
                        getZzxx();
                        //其他赋值操作
                        var sendDt = $("#sendDate").val();
                        $scope.record.sendDate = sendDt;
                        var sendEndDate = $("#sendEndDate").val();
                        $scope.record.sendEndDate = sendEndDate;
                        //费率，利率，担保类型信息计算赋值
                        getFecList();
                        getFepList();
                        getGuaranteeResultList();
                        $scope.record.bizGuaranteeContractList = guaranteeContractList;
                        $scope.record.bizCustLimitInfoList = bizCustLimitInfoList;
                        $scope.record.custUserLetter = $scope.custUserLetter;
                        $scope.record.syndicatedStatus=$("#syndicatedStatus").val();

                    }
                    //专有信息直接使用
                    if('dv3' == vtype) {
                        //不需要实现
                    }
                    if('dv4' == vtype) {
                        //还款计划赋值
                        bizRepaymentPlanList();
                        $scope.record.payList = $scope.payList;
                        $scope.record.loanList = $scope.loanList;
                    }
                    $.ajax({
                        url: '/eximbank-club/temp/tempStorage',
                        type: 'PUT',
                        data: angular.toJson($scope.record)
                    }).then(callback);
                }

                function callback(result) {
                    if(result.httpCode == 200) { //成功
                        alert("保存成功");
                        // 其他流程使用的统一暂存列表
                        var queryURL = "main.biz.grant.queryTemp";
                        // 如果暂存类型是数据迁移类型，则转向到数据迁移的暂存列表
                        var taskId = $scope.record.taskid;
                        if(taskId=='M'){
                            queryURL = "main.biz.grant.queryHistory";
                        }
                        setTimeout(function() {
                            $state.go(queryURL);
                        }, 1000);
                    } else {
                        alert(result.msg);
                    }
                }

            }

            /**
             * 功能：多选菜单控件提交前重新赋值
             * 需要通过jquery赋值给angular的scope变量
             */
            function getZzxx() {
                //背景国别
                var bn = $("#backgroundNationality").val();
                $scope.record.backgroundNationality = bn;
                //获取政策性属性分类
                var traneFinanceTypeList = $("input[name='traneFinanceType']:checked").val();
                console.log(traneFinanceTypeList);
                $scope.record.traneFinanceTypeList = traneFinanceTypeList;
                //无限级下拉框赋值
                var eic = $("#emergingIndustryClassify").val();
                $scope.record.emergingIndustryClassify = eic;
                var industryInvestment = $("#industryInvestment").val();
                $scope.record.industryInvestment = industryInvestment;
                var compare = $("#compare").val();
                $scope.record.compare = compare;
                var loanDomain = $("#loanDomain").val();
                $scope.record.loanDomain = loanDomain;
                var businessType = $("#businessType").val();
                $scope.record.businessType = businessType;
                var innovativeBusinessType = $("#innovativeBusinessType").val();
                $scope.record.innovativeBusinessType = innovativeBusinessType;
                var importExportGoodsService = $("#importExportGoodsService").val();
                $scope.record.importExportGoodsService = importExportGoodsService;
                var investmentLoanCkassifcation = $("#investmentLoanCkassifcation").val();
                $scope.record.investmentLoanCkassifcation = investmentLoanCkassifcation;
                var povertyAddress = $("#povertyAddress").val();
                $scope.record.povertyAddress = povertyAddress;
                var povertySort = $("#povertySort").val();
                $scope.record.povertySort = povertySort;
            }
            /**
             * 多个收息收费表
             */
            function getFepList(fd) {
                var chargeType = document.getElementsByName("chargeType");
				var rateValue = document.getElementsByName("rateValue");
				var isRate = $("input[name='isRate']:checked").val();
				//构造数组
				var chargeTypeList = "";
				for(var i = 0; i < chargeType.length; i++) {
					chargeTypeList += chargeType[i].value + "#";
				}
				var rateValueList = "";
				for(var i = 0; i < rateValue.length; i++) {
					rateValueList += rateValue[i].value + "#";
				}
				//如果有费用则提交到后台
				if(isRate!=2){
					$scope.record.chargeTypeList = chargeTypeList;
					$scope.record.rateValueList = rateValueList;
				}
            }
            /**
             * 多个利率表；
             */
            function getFecList(fd) {
                var currency = document.getElementsByName("currency");
                var rateType = document.getElementsByName("rateType");
                var rateVal = document.getElementsByName("rateVal");
                var floatingRate = document.getElementsByName("floatingRate");
                var convertedPrice = document.getElementsByName("convertedPrice");
                var paymentAmt = document.getElementsByName("paymentAmt");
                var irBk = document.getElementsByName("irBk");
                var termInterestRate = document.getElementsByName("termInterestRate");
                var floatDirectioin = document.getElementsByName("floatDirectioin");
                var floatMode = document.getElementsByName("floatMode");
                var paymentMode = document.getElementsByName("paymentMode");
                var chgCycleUnit = document.getElementsByName("chgCycleUnit");
                var varCycle = document.getElementsByName("varCycle");
                var overdueRateCalcMode = document.getElementsByName("overdueRateCalcMode");
                var overdueIncrRatio = document.getElementsByName("overdueIncrRatio");
                var calcDays = document.getElementsByName("calcDays");
                var firstLoanFlag = document.getElementsByName("firstLoanFlag");
                var lastLoanFlag = document.getElementsByName("lastLoanFlag");
                //构造数组
                var currencyList = "";
                for(var i = 0; i < currency.length; i++) {
                    currencyList += currency[i].value + "#";
                }
                var rateTypeList = "";
                for(var i = 0; i < rateType.length; i++) {
                    rateTypeList += rateType[i].value + "#";
                }
                var convertedPriceList = "";
                for(var i = 0; i < convertedPrice.length; i++) {
                    convertedPriceList += convertedPrice[i].value + "#";
                }
                var paymentAmtList = "";
                for(var i = 0; i < paymentAmt.length; i++) {
                    var amt_val = paymentAmt[i].value.replace(/,/g, '');
                    paymentAmtList += amt_val + "#";
                }
                var irBkList = "";
                for(var i = 0; i < irBk.length; i++) {
                    var ib_val = irBk[i].value;
                    if(ib_val==''||ib_val==null){
                        ib_val = "0";
                    }
                    irBkList += ib_val+ "#";
                }
                var termInterestRateList = "";
                for(var i = 0; i < termInterestRate.length; i++) {
                    termInterestRateList += termInterestRate[i].value + "#";
                }
                var floatDirectioinList = "";
                for(var i = 0; i < floatDirectioin.length; i++) {
                    floatDirectioinList += convertNotBlank(floatDirectioin[i].value) + "#";
                }
                var floatModeList = "";
                for(var i = 0; i < floatMode.length; i++) {
                    floatModeList += convertNotBlank(floatMode[i].value) + "#";
                }
                var floatingRateList = "";
                for(var i = 0; i < floatingRate.length; i++) {
                    floatingRateList += convertNotBlank(floatingRate[i].value) + "#";
                }
                var rateValList = "";
                for(var i = 0; i < rateVal.length; i++) {
                    //判断是否存在利率，如果不存在，则设置为0
                    var rate_value = rateVal[i].value;
                    if(rate_value==''||rate_value==null){
                        rate_value = '0';
                    }
                    rateValList += rate_value+ "#";
                }
                var paymentModeList = "";
                for(var i = 0; i < paymentMode.length; i++) {
                    paymentModeList += paymentMode[i].value + "#";
                }
                var chgCycleUnitList = "";
                for(var i = 0; i < chgCycleUnit.length; i++) {
                    chgCycleUnitList += convertNotBlank(chgCycleUnit[i].value) + "#";
                }
                var varCycleList = "";
                for(var i = 0; i < varCycle.length; i++) {
                    varCycleList += convertNotBlank(varCycle[i].value) + "#";
                }
                var overdueRateCalcModeList = "";
                for(var i = 0; i < overdueRateCalcMode.length; i++) {
                    overdueRateCalcModeList += overdueRateCalcMode[i].value + "#";
                }
                var overdueIncrRatioList = "";
                for(var i = 0; i < overdueIncrRatio.length; i++) {
                    overdueIncrRatioList += overdueIncrRatio[i].value + "#";
                }
                var calcDaysList = "";
                for(var i = 0; i < calcDays.length; i++) {
                    calcDaysList += calcDays[i].value + "#";
                }
                var firstLoanFlagList = "";
                for(var i = 0; i < firstLoanFlag.length; i++) {
                    firstLoanFlagList += firstLoanFlag[i].value + "#";
                }
                var lastLoanFlagList = "";
                for(var i = 0; i < lastLoanFlag.length; i++) {
                    lastLoanFlagList += lastLoanFlag[i].value + "#";
                }
                var totalMoney = getTotalAmt();
                $scope.record.grantAmt = totalMoney;
                $scope.record.currencyList = currencyList;
                $scope.record.rateTypeList = rateTypeList;
                $scope.record.convertedPriceList = convertedPriceList;
                $scope.record.paymentAmtList = paymentAmtList;
                $scope.record.irBkList = irBkList;
                $scope.record.termInterestRateList = termInterestRateList;
                $scope.record.floatDirectioinList = floatDirectioinList;
                $scope.record.floatModeList = floatModeList;
                $scope.record.floatingRateList = floatingRateList;
                $scope.record.rateValList = rateValList;
                $scope.record.paymentModeList = paymentModeList;
                $scope.record.chgCycleUnitList = chgCycleUnitList;
                $scope.record.varCycleList = varCycleList;
                $scope.record.overdueRateCalcModeList = overdueRateCalcModeList;
                $scope.record.overdueIncrRatioList = overdueIncrRatioList;
                $scope.record.calcDaysList = calcDaysList;
                $scope.record.firstLoanFlagList = firstLoanFlagList;
                $scope.record.lastLoanFlagList = lastLoanFlagList;
            }
            /**
             * 多个担保信息
             */
            function getGuaranteeResultList(fd) {
                var guaranType2 = document.getElementsByName("guaranType2");
                var guaranTypes = document.getElementsByName("guaranType");
                var guaranPortType = document.getElementsByName("guaranPortType");
                var notClearAmt = document.getElementsByName("notClearAmt");
                var clearRatio = document.getElementsByName("clearRatio");
                var clearRatioAmt = document.getElementsByName("clearRatioAmt");
                var pledgeType = document.getElementsByName("pledgeType");
                var pledgeState = document.getElementsByName("pledgeState");
                var pledgeNo = document.getElementsByName("pledgeNo");
                var pledgeRemark = document.getElementsByName("pledgeRemark");
                var guaranRemark = document.getElementsByName("guaranRemark");
                //构造数组
                var guaranTypeList = "";
                for(var i = 0; i < guaranType2.length; i++) {
                    guaranTypeList += guaranType2[i].value + "#";
                }
                var guaranTypeLists = "";
                for(var i = 0; i < guaranTypes.length; i++) {
                    guaranTypeLists += guaranTypes[i].value + "#";
                }
                var guaranPortTypeList = "";
                for(var i = 0; i < guaranPortType.length; i++) {
                    guaranPortTypeList += guaranPortType[i].value + "#";
                }
                var notClearAmtList = "";
                for(var i = 0; i < notClearAmt.length; i++) {
                    var amt_val = notClearAmt[i].value.replace(/,/g, '');
                    notClearAmtList += convertNotBlank(amt_val) + "#";
                }
                var clearRatioList = "";
                for(var i = 0; i < clearRatio.length; i++) {
                    clearRatioList += convertNotBlank(clearRatio[i].value) + "#";
                }
                var clearRatioAmtList = "";
                for(var i = 0; i < clearRatioAmt.length; i++) {
                    var amt_val = clearRatioAmt[i].value.replace(/,/g, '');
                    clearRatioAmtList += convertNotBlank(amt_val) + "#";
                }
                var pledgeTypeList = "";
                for(var i = 0; i < pledgeType.length; i++) {
                    pledgeTypeList += convertNotBlank(pledgeType[i].value) + "#";
                }
                var pledgeStateList = "";
                for(var i = 0; i < pledgeState.length; i++) {
                    pledgeStateList += convertNotBlank(pledgeState[i].value) + "#";
                }
                var pledgeNoList = "";
                for(var i = 0; i < pledgeNo.length; i++) {
                    pledgeNoList += convertNotBlank(pledgeNo[i].value) + "#";
                }
                var pledgeRemarkList = "";
                for(var i = 0; i < pledgeRemark.length; i++) {
                    pledgeRemarkList += convertNotBlank(pledgeRemark[i].value) + "#";
                }
                var guaranRemarkList = "";
                for(var i = 0; i < guaranRemark.length; i++) {
                    guaranRemarkList += convertNotBlank(guaranRemark[i].value) + "#";
                }
                //append方法添加键值对
                $scope.record.guaranTypeList = guaranTypeList;
                $scope.record.guaranTypeLists = guaranTypeLists;
                $scope.record.guaranPortTypeList = guaranPortTypeList;
                $scope.record.notClearAmtList = notClearAmtList;
                $scope.record.clearRatioList = clearRatioList;
                $scope.record.clearRatioAmtList = clearRatioAmtList;
                $scope.record.pledgeTypeList = pledgeTypeList;
                $scope.record.pledgeStateList = pledgeStateList;
                $scope.record.pledgeNoList = pledgeNoList;
                $scope.record.pledgeRemarkList = pledgeRemarkList;
                $scope.record.guaranRemarkList = guaranRemarkList;
            }
            //还款计划
            function bizRepaymentPlanList(fd) {
                var plist = $scope.payList;
                var llist = $scope.loanList;
                if(!plist || !llist){
                    return null;
                }
                var payDateList = "";
                for(var i = 0; i < plist.length; i++) {
                    payDateList += dateFilter(plist[i].payDate, 'yyyy-MM-dd') + "#";
                }
                var principalAmyList = "";
                for(var i = 0; i < plist.length; i++) {
                    var plamt = plist[i].principalAmy+'';
					plamt = plamt.replace(/,/g, '');
					principalAmyList += plamt + "#";
                }
                var clearinterestDateRatioList = "";
                for(var i = 0; i < llist.length; i++) {
                    clearinterestDateRatioList += dateFilter(llist[i].interestDate, 'yyyy-MM-dd') + "#";
                }
                $scope.record.payDateList = payDateList;
                $scope.record.principalAmyList = principalAmyList;
                $scope.record.clearinterestDateRatioList = clearinterestDateRatioList;
            }
            //组织提交的字符串
            $scope.payListChange = function() {
                var money = getTotalAmt();
				if(money == 0) {
					alert("请先录入发放金额");
					return false;
				} else {
					$scope.payList = getPayList(money);
					$scope.loanList = getLoanList();
					$scope.showTable = '1';
				}
            }
            //计算金额是否匹配
            $scope.changeCaclWay = function() {
                var paylistMoney = $scope.payList;
				var money = 0;
				var moneyList = document.getElementsByName("paymentAmt");
				for(var i = 0; i < moneyList.length; i++) {
					var n = moneyList[i].value.replace(/,/g, '');
					var num = roundNum(Number(n));
					money = roundNum(money)+num;
				}
				var payMoney = 0;
				for(var i = 0; i < paylistMoney.length; i++) {
					var pamt = paylistMoney[i].principalAmy+'';
					pamt = pamt.replace(/,/g, '');
					payMoney = roundNum(payMoney) + roundNum(Number(pamt));
				}
				if(roundNum(money) != roundNum(payMoney)) {
					alert("提示", "还款计划表金额与发放金额不符");
					return false;
				}
				return true;
            }
            //还款计划表最后一天日期校验
			$scope.changeLastDate = function() {
				//末次还款日
				var payDateList = $scope.payList;
				var payLen = payDateList.length;
				var payLastDay = payDateList[payLen-1].payDate;
				//末次还息日
				var interestDateList = $scope.loanList;
				var itLen = interestDateList.length;
				var itLastDay = interestDateList[itLen-1].interestDate;
				//末次还款日
				var lastDay = $("#lastRepayDay").val();
				var dt = new DateUtil();
				if(dt.compareDate(lastDay, payLastDay) == 1) {
					alert("提示", "还本计划表最后一条日期超出末次还款日!");
					return false;
				}
				if(dt.compareDate(lastDay,payLastDay) != 0) {
					alert("提示", "还本计划表最后一条应为末次还款日!");
					return false;
				}
				if(dt.compareDate(lastDay,itLastDay) == 1) {
					alert("提示", "还息计划表最后一条日期超出末次还款日!");
					return false;
				}
				if(dt.compareDate(lastDay,itLastDay) != 0) {
					alert("提示", "还息计划表最后一条应为末次还款日!");
					return false;
				}
				return true;
			}
            //
            $scope.printInfo = function() {
                for(var i = 0; i < $scope.payList.length; i++) {
                    console.log($scope.payList[i]);
                }
            };
            $scope.showVBs = [{
                "Tag": "Tag1",
                "NO": "No1",
                "remarks": "remarks1"
            }, {
                "Tag": "Tag2",
                "NO": "No2",
                "remarks": "remarks2"
            }];
            $scope.BDetailsAdd = function(i) {
                //获取发放条件选择的主币种
    			var currency = $("select[name='currency']").eq(0).find("option:selected").text(); 
				$scope.payList.splice(i,0,{currency:currency,payDate:'',principalAmy:''});
            };
            $scope.BDetailsDel = function(Count) {
                $scope.payList.splice(Count, 1);
            };
            $scope.ADetailsAdd = function(i) {
                $scope.loanList.splice(i,0,{interestDate:''});
            };
            $scope.ADetailsDel = function(Count) {
                $scope.loanList.splice(Count, 1);
            };

            //担保合同初始化
            var guaranteeContractList = new Array();
            /**
             * 担保合同添加
             * @param {Object} code
             */
            $scope.guaranteeAdd = function(code) {
                //数组长度
                var size = guaranteeContractList.length;
                var record = new Object();
                //如果数组内有元素修改，修改的原理是首先删除元素，然后再新增进数组
				if(size > 0) {
					var k = getGuaranteeIndexByCode(guaranteeContractList,code);
					if(k!=-1){
						guaranteeDel(k);
					}
				}
                var guarAmount = $("#guarAmount").val();
                var amt_val = guarAmount.replace(/,/g, '');
                record.guarNo = $("#guarNo").val(),
                    record.guarManualNo = $("#guarManualNo").val(),
                    record.guarCustName = $("#guarCustName").val(),
                    record.guarCustNo = $("#guarCustNo").val(),
                    record.guarAmount = amt_val,
                    record.currency = $("#gCurrency").val(),
                    record.startDate = $("#startDate").val(),
                    record.dueDate = $("#dueDate").val(),
                    record.term = $("#term").val(),
                    record.termUnit = $("#termUnit").val(),
                    record.warrandice = $("#warrandice").val(),
                    record.guarContType = $("#guarContType").val(),
                    record.guarContState = $("#guarContState").val(),
                    record.guarMaxAmt = $("#guarMaxAmt").val(),
                    record.agent = $("#agent").val(),
                    record.agencies = $("#agencies").val();

                guaranteeContractList.push(record);
            };
            //担保合同数组删除指定的元素
            function guaranteeDel(i) {
                var size = guaranteeContractList.length;
                if(i<0){
                    i = 0;
                }
                if(size>0 && i>=size){
                    i=size-1;
                }
                guaranteeContractList.splice(i, 1);
            };
            //从索引位置获取数组指定的元素
            function guaranteeGet(icode) {
                var size = guaranteeContractList.length;
				
				if(size == 0) {
					return null;
				}
				
				return getGuaranteeByNo(guaranteeContractList,code);
            };
            
            /**
			 * 根据合同编号查询索引 
			 * @param {Object} contractList
			 * @param {Object} code
			 */
			function getGuaranteeIndexByCode(contractList,code){
				var len = contractList.length;
				for(var i=0;i<len;i++){
					var item = contractList[i];
					if(item.guarNo==code){
						return i;
					}
				}
				return -1;
			}
            /**
			 * 根据合同编号查询
			 * @param {Object} contractList
			 * @param {Object} no
			 */
			function getGuaranteeByNo(contractList,no){
				var len = contractList.length;
				for(var i=0;i<len;i++){
					var item = contractList[i];
					if(item.guarNo==no){
						return item;
					}
				}
				return null;
			}
			/**
			 * 功能：初始化回显的时候把原来数据库的合同赋值给数组中
			 */
			function copyGuaranteeDbToArray(record) {
				if(record!=null){
					guaranteeContractList.push(record);
				}
			}
            /**
             * 功能：把数组拆分成字符串
             */
            function splitContractArray(guaranteeContracts){
            	var len = guaranteeContracts.length;
            	var result = "";
            	for(var i=0;i<len;i++){
            		var contract = guaranteeContracts[i];
            		var row = "guarNo:"+contract.guarNo+"#";
            		row+="guarManualNo:"+contract.guarManualNo+"#";
            		row+="guarCustName:"+contract.guarCustName+"#";
            		row+="guarCustNo:"+contract.guarCustNo+"#";
            		row+="guarAmount:"+contract.guarAmount+"#";
            		row+="startDate:"+contract.startDate+"#";
            		row+="dueDate:"+contract.dueDate+"#";
            		row+="term:"+contract.term+"#";
            		row+="termUnit:"+contract.termUnit+"#";
            		row+="warrandice:"+contract.warrandice+"#";
            		row+="guarContType:"+contract.guarContType+"#";
            		row+="guarContState:"+contract.guarContState+"#";
            		row+="guarMaxAmt:"+contract.guarMaxAmt+"#";
            		row+="currency:"+contract.currency+"#";
            		row+="agent:"+contract.agent+"#";
            		row+="agencies:"+contract.agencies+"#";
            		//连接每一个对象字符串
            		result+=row+"$";
            	}
            	return result;
            }
        }
    ]);

/**
 * 功能：生成还款计划
 * 日期：2018-12-13 重构JS 
 * 作者：czm
 */
function getPayList(money) {
	var payList = new Array(); //首先初始化一个还本计划数组
	//使用日期控件工具
	var dt = new DateUtil();
	//获取页面开始、结束日期和还款方式
	var fCostDay = $("#fristRepayDate").val(); //首次还本日
	var lCostDay = $("#lastRepayDay").val(); //末次还款日
	var costMode = $("#repaymentCostMode").val(); //还本方式
	var currency = document.getElementsByName("currency")[0].value; //币种
	//获取发放条件选择的主币种
	var currencyText = $("select[name='currency']").eq(0).find("option:selected").text();
	//防止浏览器雪崩宕机
	if(costMode == '' || costMode == null) {
		var msg = "请选择还本方式!";
		alertWarn($("#repaymentCostMode"), msg);
		return payList;
	} else {
		cleanRequiredWarn($("#repaymentCostMode"));
	}
	if(fCostDay == '' || fCostDay == '') {
		var msg = "请选择首次还本日期";
		alertWarn($("#fristRepayDate"), msg);
		return payList;
	} else {
		cleanRequiredWarn($("#fristRepayDate"));
	}
	if(lCostDay == '' || lCostDay == '') {
		var msg = "请选择末次还款日期";
		alertWarn($("#lastRepayDay"), msg);
		return payList;
	} else {
		cleanRequiredWarn($("#lastRepayDay"));
	}
	//判断开始日期不能大于结束日期
	if(dt.compareDate(lCostDay, fCostDay) == 1) {
		var msg = '首次还本日期（' + fCostDay + '）不能大于末次还款日期(' + lCostDay + ')';
		alertWarn($("#lastRepayDay"), msg);
		return payList;
	} else {
		cleanRequiredWarn($("#lastRepayDay"));
	}
	money = Number(money);

	//首次还款日和末次还款日同一天，系统生成一条还款计划
	if(dt.compareDate(fCostDay, lCostDay) == 0) {
		record.payDate = fCostDay; //还款日期
		record.currency = currencyText; //币种
		record.principalAmy = money; //还款金额
		payList.push(record);
		return payList;
	}
	//下面根据还本方式进行判断计算（12年6半年3季1月）
	payList = math_money_plan(fCostDay,lCostDay,money,currencyText,costMode);
	//返回计算结果
	return payList;
}

//获得还息内容
function getLoanList() {
	var LoanList = new Array(); //首先初始化一个还利息计划数组
	//使用日期控件工具
	var dt = new DateUtil();
	//获取页面开始、结束日期和还款方式
	var fInterestDay = $("#firstDay").val(); //首次还息日期
	var lCostDay = $("#lastRepayDay").val(); //末次还款日
	var costMode = $("#caclWay").val(); //利息计收方式

	//获取币种
	var currency = document.getElementsByName("currency")[0].value; //币种
	//获取发放条件选择的主币种
	var currencyText = $("select[name='currency']").eq(0).find("option:selected").text();

	//防止浏览器雪崩宕机
	if(costMode == '' || costMode == null) {
		var msg = "请选择利息计收方式!";
		alertWarn($("#caclWay"), msg);
		return LoanList;
	} else {
		cleanRequiredWarn($("#caclWay"));
	}
	if(fInterestDay == '' || fInterestDay == '') {
		var msg = "请选择首次还息日期!";
		alertWarn($("#firstDay"), msg);
		return LoanList;
	} else {
		cleanRequiredWarn($("#firstDay"));
	}
	//判断首次还息日期不能大于末次还款日
	if(dt.compareDate(lCostDay, fInterestDay) == 1) {
		var msg = '首次还息日期（' + fInterestDay + '）不能大于末次还款日期(' + lCostDay + ')';
		alertWarn($("#firstDay"), msg);
		return LoanList;
	} else {
		cleanRequiredWarn($("#firstDay"));
	}

	//首次还款日和末次还息日同一天,生成一条还息数据
	if(dt.compareDate(lCostDay, fInterestDay) == 0) {
		var record = new Object();
		record.interestDate = fInterestDay;
		LoanList.push(record);
		return LoanList;
	}
	//根据利息计收方式计算还息计划（12年6半年3季1月）
	LoanList = math_interest_plan(fInterestDay,lCostDay,costMode);
	//返回计算的结果
	return LoanList;
}
/**
 * 功能：初始化下面各类下拉框（动态生成）
 * 包括货币、中国制造2025及战略新兴产业分类、支农投向等
 * @param {Object} businessCode
 * @param {Object} debtNum
 * @param {Object} proIds
 */
function initSelectBox(businessCode, debtNum) {
    //行业投向
	bulidMulitSelect("industryInvestmentUL", "industryInvestment", "A0", "005");
	//中国制造2025及战略新兴产业分类
	bulidMulitSelect("emergingIndustryClassifyUL", "emergingIndustryClassify", "A00000AA", "006");
	//支农投向
	bulidMulitSelect("compareUL", "compare", "HHH", "014");
	//贷款领域
	bulidMulitSelect("loanDomainUL", "loanDomain", "AGR0280001", "008");
	//产品类型       
	bulidMulitSelect("businessTypeUL", "businessType", "9000800", "012");
	//创新业务类型    
	bulidMulitSelect("innovativeBusinessTypeUL", "innovativeBusinessType", "0430", "011");
	//进出口货物及服务      
	bulidMulitSelect("importExportGoodsServiceUL", "importExportGoodsService", "AGR0280002", "010");
	//对外投资贷款分类        
	bulidMulitSelect("investmentLoanCkassifcationUL", "investmentLoanCkassifcation", "PRD0101031", "009");
	//精准扶贫项目所在地区      
	bulidMulitSelect("povertyAddressUL", "povertyAddress", "LOC0101003", "013");
	//精准扶贫项目类别
	bulidMulitSelect("povertySortUL", "povertySort", "JINGZHUN", "015");
	
	//背景国别下拉框
	bulidDicSelect("backgroundNationality",'countryBack');
	//币种下拉框查询
	loadCurrencySelect("currency", debtNum);
	 //担保类型下拉框
	loadGuaranteeInfoSelect("guaranType", debtNum);
	//费用类型下拉框
	bulidChargeTypeSelect('chargeType', businessCode);
	//利率基准
	bulidInterestRateSelect("irBk");
}

/**
 * 功能：切换贸金业务政策性属性
 * @param {Object} b
 */
function chgTraneFinance(b) {
    var chg_type = b.value;
    //政策性贷款项下贸金业务
    if(1 == chg_type) {
        //政策性贷款业务状态-必填
        $("#policyLendingStatus").addClass("required");
    } else {
        //政策性贷款业务状态-不用必填
        $("#policyLendingStatus").removeClass("required");
        $("#policyLendingStatus").removeClass("required-warn");
        $("#policyLendingStatus").next().text("");
    }
}

/**
 * 功能：切换担保份额划分
 * @param {Object} b
 */
function chgGpy(b){
    var chg_type = b.value;
    var tr = $(b).parent().parent();
    //不可明确划分份额
    if(2==chg_type){
        //可明确划分使用本担保方式的份额-隐藏
        tr.next().hide();
        tr.next().find("input[name='clearRatio']").val("");
        tr.next().find("input[name='clearRatio']").removeClass("required");
        tr.next().find("input[name='clearRatio']").removeClass("required-warn");
        tr.next().find("input[name='clearRatio']").next().text("");
        cleanWarnText(tr.next());
        //可明确划分使用本担保方式的金额-隐藏
        tr.next().next().hide();
        tr.next().next().find(":text").val("");
        tr.next().next().find(":text").removeClass("required");
        tr.next().next().find(":text").removeClass("required-warn");
        tr.next().next().find(":text").next().text("");
        cleanWarnText(tr.next().next());
        //不可明确划分使用本担保方式的金额-显示
        tr.next().next().next().show();
        tr.next().next().next().find(":text").addClass("required");
    }else{
        //可明确划分使用本担保方式的份额-显示
        tr.next().show();
        tr.next().find("input[name='clearRatio']").addClass("required");
        //可明确划分使用本担保方式的金额-显示
        tr.next().next().show();
        tr.next().next().find(":text").addClass("required");
        //不可明确划分使用本担保方式的金额-隐藏
        tr.next().next().next().hide();
        tr.next().next().next().find(":text").val("");
        cleanWarnText(tr.next().next().next());
        tr.next().next().next().find(":text").removeClass("required");
        tr.next().next().next().find(":text").removeClass("required-warn");
        tr.next().next().next().find(":text").next().text("");

        //如果担保类型中的方式为一般，可明确划分使用本担保方式的份额默认100%，可修改。
        var g_select =  tr.prev().find("select[name='guaranType2']").eq(0);
        var g_text = g_select.find("option:selected").text();
        //担保类型中是否存在一般
        var common_index = g_text.indexOf('最高');
        //只有类型是可明确划分份额。才设置默认值100%
        var clearRate = tr.next().find("input[name='clearRatio']").eq(0);
        var clear_rate_val = clearRate.val();
        //判断是否需要默认值，没有填写的情况下使用默认值
        if(clear_rate_val==''||clear_rate_val==null||clear_rate_val=='0'){
            //排除最高，其他的都是一般
			if(common_index==-1){
                //默认100%
                clearRate.val(100);
                //计算可明确划分金额
                calculateAmt(clearRate);
            }
        }
        ////
    }
}

/**
 * 功能：新增担保类型-判断是否可以新增多个
 */
function checkGuaranAdd(addBtn,tbId,num){
	var type_select = $(addBtn).parent().find("select[name='guaranType']").eq(0);
	var g_select = $(addBtn).parent().find("select[name='guaranType2']").eq(0);
	//担保类型二级下拉框选项的数量
	var size = 0;
	//担保类型一级下拉框中是否有两个选项
	var type_size = type_select.find("option").size();
	//首先判断是否可以新增表格，如果只有一个选择项且还是一般担保类型，则不能添加
	g_select.find("option").each(function(i){
		var txt = $(this).text();
		var val = $(this).val();
		size++;
	});
	//当担保类型第一级和第二级都是只有一个可选项的时候，判断是否可重复添加
	if(type_size == 2 && size == 2){
		alert("同一个担保类型，不能二次添加！");
		return false; 
	}
	//动态新增担保类型表格 
	var new_table = addTable(addBtn,tbId,num);
	//新增表格后马上删除担保类型二级菜单选项
	var new_select = new_table.find("select[name='guaranType2']").eq(0);
	new_select.empty();
}

/**
 * 判断是否需要删除一般担保方式下拉框
 * @param {Object} stb
 */
function isDelOption(stb){
	var type_val = $(stb).val();
	if(type_val==''){
		return false;
	}
    //判断是否需要删除下拉框中的一般
    var newSelect = $(stb).parent().find("select[name='guaranType2']").eq(0);
    //如果存在一般担保下拉选项，则删除
    newSelect.find("option").each(function(i){
        var txt = $(this).text();
        var val = $(this).val();
        //如此上面存在这个选项，那么第二个下拉框里面就需要删除
        var chk_count = delOtherOption(val);
        if(chk_count > 0){
            $(this).remove();
        }
    });
}
var idelArray = new Array();
/**
 * 删除其他存在的下拉框选项
 */
function delOtherOption(op,is_del){
	var exist = 0;
	delArray();
    //多个担保类型下拉框里面包含几个一般担保
    $("select[name='guaranType2']").each(function(i){
        var selected_val = $(this).val();
        if(selected_val!=''){
        	idelArray[i] = selected_val;
        }
    });
    //遍历存在的集合，查看重复了几次
    for(var i=0;i<idelArray.length;i++){
    	if(idelArray[i] == op){
    		exist++;
    	}
    }
    return exist;
}
//清空数组
function delArray(){
	var len = idelArray.length;
	idelArray.splice(0,len)
}

/**
 * 通过选择担保份额划分方式来判断是否显示下面的输入项
 * @param {Object} b
 */
function guaranPortTypeChange(b) {
    if(b.val() == 1) {
        b.parentsUntil(".table-editer-form").find(".guaranporttype1").show();
        b.parentsUntil(".table-editer-form").find(".guaranporttype2").hide();
        //不显示则不必输入，显示的必须要输入
        b.parentsUntil(".table-editer-form").find("#clearRatio").addClass("required");
        b.parentsUntil(".table-editer-form").find("#clearRatioAmt").addClass("required");
        b.parentsUntil(".table-editer-form").find("#notClearAmt").removeClass("required");
    } else {
        b.parentsUntil(".table-editer-form").find(".guaranporttype1").hide();
        b.parentsUntil(".table-editer-form").find(".guaranporttype2").show();
        //不显示则不必输入，显示的必须要输入
        b.parentsUntil(".table-editer-form").find("#clearRatio").removeClass("required");
        b.parentsUntil(".table-editer-form").find("#clearRatioAmt").removeClass("required");
        b.parentsUntil(".table-editer-form").find("#notClearAmt").addClass("required");
    }
}

/**
 * 切换是否有费用信息
 */
function chgIsRate() {
	var chg_type = $("input[name='isRate']:checked").val();
	//有费用，显示可输入的费用信息
	if(1 == chg_type) {
		$("#chargeTable").show();
		//费用类型-添加必输性验证
		$("#chargeType").addClass("required");
		$("#rateValue").addClass("required");
	} else {
		//没有费用，则隐藏费用信息 
		$("#chargeTable").hide();
		//清空值
		$("#chargeType").val("");
		$("#rateValue").val("");
		//费用类型-去掉必输性验证
		$("#chargeType").removeClass("required");
		$("#chargeType").removeClass("required-warn");
		$("#chargeType").next().text("");
		$("#rateValue").removeClass("required");
		$("#rateValue").removeClass("required-warn");
		$("#rateValue").parent().find(".text-danger").text("");
	}
}

/**
 * 功能：切换利率类型
 * @param {Object} b
 */
function changeRateType(b) {
	var type_val = b.val();
	var table = $(b).closest(".table-editer-form");
	if(type_val == 1) { //固定利率
		//利率只读
		var rateValObj = table.find("input[name='rateVal']").eq(0);
		rateValObj.removeProp("readonly");
		rateValObj.addClass("required");
		//利率基准
		table.find("select[name='irBk']").eq(0).parent().parent().hide();
		table.find("select[name='irBk']").eq(0).removeClass("required");
		table.find("select[name='irBk']").eq(0).removeClass("required-warn");
		table.find("select[name='irBk']").eq(0).parent().find(".text-danger").text("");
		//利率浮动方向
		table.find("select[name='floatDirectioin']").eq(0).parent().parent().hide();
		table.find("select[name='floatDirectioin']").eq(0).removeClass("required");
		table.find("select[name='floatDirectioin']").eq(0).removeClass("required-warn");
		table.find("select[name='floatDirectioin']").eq(0).parent().find(".text-danger").text("");
		//利率浮动方式
		table.find("select[name='floatMode']").eq(0).parent().parent().hide();
		table.find("select[name='floatMode']").eq(0).removeClass("required");
		table.find("select[name='floatMode']").eq(0).removeClass("required-warn");
		table.find("select[name='floatMode']").eq(0).parent().find(".text-danger").text("");
		//浮动率
		table.find("input[name='floatingRate']").eq(0).parent().parent().hide();
		table.find("input[name='floatingRate']").eq(0).removeClass("required");
		table.find("input[name='floatingRate']").eq(0).removeClass("required-warn");
		table.find("input[name='floatingRate']").eq(0).parent().find(".text-danger").text("");
		//利率变动周期
		table.find("input[name='varCycle']").eq(0).parent().parent().hide();
		table.find("input[name='varCycle']").eq(0).removeClass("required");
		table.find("select[name='chgCycleUnit']").eq(0).removeClass("required");
		table.find("input[name='varCycle']").eq(0).removeClass("required-warn");
		table.find("select[name='chgCycleUnit']").eq(0).removeClass("required-warn");
		table.find("select[name='chgCycleUnit']").eq(0).parent().find(".text-danger").text("");
		//逾期利率计算方式-必输
		table.find("select[name='overdueRateCalcMode']").eq(0).addClass("required");
		table.find("select[name='overdueRateCalcMode']").eq(0).parent().prev().addClass("need");
		//逾期加点-必输
		table.find("input[name='overdueIncrRatio']").eq(0).addClass("required");
		table.find("input[name='overdueIncrRatio']").eq(0).parent().prev().addClass("need");
		//利率计算天数-必输
		table.find("input[name='calcDays']").eq(0).addClass("required");
		table.find("input[name='calcDays']").eq(0).parent().prev().addClass("need");
	} else if(type_val == 2) { //浮动利率
		//利率只读
		var rateValObj = table.find("input[name='rateVal']").eq(0);
		rateValObj.prop("readonly", "readonly");
		rateValObj.removeClass("required");
		rateValObj.removeClass("required-warn");
		rateValObj.eq(0).val("");
		rateValObj.next().text("");
		//利率基准
		table.find("select[name='irBk']").eq(0).parent().parent().show();
		table.find("select[name='irBk']").eq(0).addClass("required");
		//利率浮动方向
		table.find("select[name='floatDirectioin']").eq(0).parent().parent().show();
		table.find("select[name='floatDirectioin']").eq(0).addClass("required");
		//利率浮动方式
		table.find("select[name='floatMode']").eq(0).parent().parent().show();
		table.find("select[name='floatMode']").eq(0).addClass("required");
		//浮动率
		table.find("input[name='floatingRate']").eq(0).parent().parent().show();
		table.find("input[name='floatingRate']").eq(0).addClass("required");
		//利率变动周期
		table.find("input[name='varCycle']").eq(0).parent().parent().show();
		table.find("input[name='varCycle']").addClass("required");
		table.find("select[name='chgCycleUnit']").addClass("required");

		//逾期利率计算方式-必输
		table.find("select[name='overdueRateCalcMode']").eq(0).addClass("required");
		table.find("select[name='overdueRateCalcMode']").eq(0).parent().prev().addClass("need");
		//逾期加点-必输
		table.find("input[name='overdueIncrRatio']").eq(0).addClass("required");
		table.find("input[name='overdueIncrRatio']").eq(0).parent().prev().addClass("need");
		//利率计算天数-必输
		table.find("input[name='calcDays']").eq(0).addClass("required");
		table.find("input[name='calcDays']").eq(0).parent().prev().addClass("need");
	} else if(type_val == 3) { //无利率
		//利率不显示
		var rateValObj = table.find("input[name='rateVal']").eq(0);
		rateValObj.removeClass("required");
		rateValObj.removeClass("required-warn");
		rateValObj.parent().find(".text-danger").text("");
		//利率基准
		table.find("select[name='irBk']").eq(0).parent().parent().hide();
		table.find("select[name='irBk']").eq(0).removeClass("required");
		table.find("select[name='irBk']").eq(0).removeClass("required-warn");
		table.find("select[name='irBk']").eq(0).parent().find(".text-danger").text("");
		//利率浮动方向
		table.find("select[name='floatDirectioin']").eq(0).parent().parent().hide();
		table.find("select[name='floatDirectioin']").eq(0).removeClass("required");
		table.find("select[name='floatDirectioin']").eq(0).removeClass("required-warn");
		table.find("select[name='floatDirectioin']").eq(0).parent().find(".text-danger").text("");
		//利率浮动方式
		table.find("select[name='floatMode']").eq(0).parent().parent().hide();
		table.find("select[name='floatMode']").eq(0).removeClass("required");
		table.find("select[name='floatMode']").eq(0).removeClass("required-warn");
		table.find("select[name='floatMode']").eq(0).parent().find(".text-danger").text("");
		//浮动率
		table.find("input[name='floatingRate']").eq(0).parent().parent().hide();
		table.find("input[name='floatingRate']").eq(0).removeClass("required");
		table.find("input[name='floatingRate']").eq(0).removeClass("required-warn");
		table.find("input[name='floatingRate']").eq(0).parent().find(".text-danger").text("");
		//利率变动周期
		table.find("input[name='varCycle']").eq(0).parent().parent().hide();
		table.find("input[name='varCycle']").eq(0).removeClass("required");
		table.find("select[name='chgCycleUnit']").eq(0).removeClass("required");
		table.find("input[name='varCycle']").eq(0).removeClass("required-warn");
		table.find("select[name='chgCycleUnit']").eq(0).removeClass("required-warn");
		table.find("select[name='chgCycleUnit']").eq(0).parent().find(".text-danger").text("");
		//逾期利率计算方式-去掉必输
		table.find("select[name='overdueRateCalcMode']").eq(0).removeClass("required");
		table.find("select[name='overdueRateCalcMode']").eq(0).removeClass("required-warn");
		table.find("select[name='overdueRateCalcMode']").eq(0).parent().find(".text-danger").text("");
		table.find("select[name='overdueRateCalcMode']").eq(0).parent().parent().find(".need").removeClass("need");
		//逾期加点-去掉必输
		table.find("input[name='overdueIncrRatio']").eq(0).removeClass("required");
		table.find("input[name='overdueIncrRatio']").eq(0).removeClass("required-warn");
		table.find("input[name='overdueIncrRatio']").eq(0).parent().find(".text-danger").text("");
		table.find("input[name='overdueIncrRatio']").eq(0).parent().parent().find(".need").removeClass("need");
		//利率计算天数-去掉必输
		table.find("input[name='calcDays']").eq(0).removeClass("required");
		table.find("input[name='calcDays']").eq(0).removeClass("required-warn");
		table.find("input[name='calcDays']").eq(0).parent().find(".text-danger").text("");
		table.find("input[name='calcDays']").eq(0).parent().parent().find(".need").removeClass("need");
	}
}

/**
 * 功能：用户选择担保类型后获取担保对应的押品信息
 * @param {Object} b
 */
function getGuaranteeChildrenList(b) {
    var code = b.value;
    if(code != '') {
        //担保类型
        var guaranType = $(b).prev().val();
        //查询担保类型对应的一套担保信息和押品信息
        var result = getGuaranteeDbById(code);
        var table = $(b).closest(".table-editer-form");

        //C000表示信用类型担保方式为信用的，担保合同编号不显示；其他担保方式（抵押、质押、保证、其他），显示该字段
        if('C000'==guaranType){
            table.find("input[name='guaranteeNo']").eq(0).parent().parent().hide();//担保合同编号不显示
            table.find("input[name='guaranteeNo']").eq(0).parent().parent().next().hide();//担保合同附件上传不显示
            //添加合同上传标记为不验证
            table.find(":hidden[name='synchrRefreshGuarantee']").val("yes");//合同上传功能里面的输入项不要必输
            $("#myCustUserLetter").find(":input").removeClass('required');//去掉合同窗口内的所有元素的必输项
            $("#myCustUserLetter").find(":input").removeClass('required-warn');//去掉合同窗口内的所有元素的必输项消息
            //担保合同编号不需要验证,不必填
            table.find("input[name='guaranteeNo']").eq(0).removeClass("required");
            table.find("input[name='guaranteeNo']").eq(0).removeClass("required-warn");
            table.find("input[name='guaranteeNo']").eq(0).next().text("");
        }else{
            //担保合同编号显示,改为不显示，只在弹框显示
            // table.find("input[name='guaranteeNo']").eq(0).parent().parent().show();
            table.find("input[name='guaranteeNo']").eq(0).val(result.warrantyContractNumber);
            //合同信息必输
            table.find(":hidden[name='synchrRefreshGuarantee']").val("");
            //合同上传功能里面的输入项必输
            setGuaranteeRequired();
            //担保合同附件上传按钮显示
            table.find("input[name='guaranteeNo']").eq(0).parent().parent().next().show();
        }
        //信用类型和其他担保类型，担保方式类型不可显示
        if('C104'==guaranType||'C000'==guaranType){
        	//担保方式类型下拉框初始化数据
        	table.find("select[name='pledgeType']").eq(0).parent().parent().hide();
     		var pledge = table.find("select[name='pledgeType']").eq(0);
    		getPledgeTypeSelect(pledge, result.typePoint);
    		//担保方式类型不需要验证,不必填
            pledge.removeClass("required");
            pledge.removeClass("required-warn");
            pledge.next().text("");
        }else{
        	//担保方式类型下拉框初始化数据
        	table.find("select[name='pledgeType']").eq(0).parent().parent().show();
     		var pledge = table.find("select[name='pledgeType']").eq(0);
    		getPledgeTypeSelect(pledge, result.typePoint);
        }
        //担保人
        table.find("input[name='guaranteeCustName']").eq(0).val(result.guarantor);
        table.find("input[name='guarantorCustId']").eq(0).val(result.guarantorCustId);//担保人id
        table.find("textarea[name='guaranRemark']").eq(0).val(result.note);//备注
        table.find("input[name='guarantorTypeCode']").eq(0).val(result.guaranteeContractType);//占用额度类型
        //查询的多个押品信息
        var betInformationList = result.betInformationList;
        //担保类型为抵押或质押时，需要录入押品信息
        if('C102'==guaranType||'C103'==guaranType){
            table.find("#pledgeRow").show();
            //生成多个押品表格
            var pledgeTableDiv = table.find(".pledgeTable");
            createSubTable(pledgeTableDiv, betInformationList.length - 1, 1);
            var size = betInformationList.length;//押品个数
            //设置值选中或赋值
            for(var i = 0; i < size; i++) {
                //TODO 押品状态默认已存1
                //table.find("select[name='pledgeState']").eq(i).val(1);
                table.find("input[name='pledgeNo']").eq(i).val(betInformationList[i].pledNo);
                table.find("textarea[name='pledgeRemark']").eq(i).val(betInformationList[i].productInformation);
                if(betInformationList[i].pledNo == null || betInformationList[i].pledNo == '') {
                    table.find("input[name='pledgeNo']").eq(i).removeClass("required");
                }
                if(betInformationList[i].productInformation == null || betInformationList[i].productInformation == '') {
                    table.find("textarea[name='pledgeRemark']").eq(i).removeClass("required");
                }
            }
            if(size < 1) {
                table.find("input[name='pledgeNo']").removeClass("required");
                table.find("textarea[name='pledgeRemark']").removeClass("required");
            }
        }else{
            //隐藏，并取消必输验证
            table.find("#pledgeRow").hide();
            table.find("input[name='pledgeNo']").removeClass("required");
            table.find("input[name='pledgeNo']").removeClass("required-warn");
            table.find("input[name='pledgeNo']").next().text("");
            table.find("textarea[name='pledgeRemark']").removeClass("required");
            table.find("textarea[name='pledgeRemark']").removeClass("required-warn");
            table.find("textarea[name='pledgeRemark']").next().text("");
        }
        var guarmartType = result.guarmartType;
        table.find("select[name='pledgeType']").eq(0).val(guarmartType);
    }
}

/**
 * 功能：用户选择担保类型后获取担保对应的押品信息
 * @param {Object} b
 */
function getEditGuaranteeChildrenList(b, pledgeVal, index) {
    var code = b.val();
    if(code != '') {
        //担保类型
        var guaranType = $(b).prev().val();
        var result = getGuaranteeDbById(code);
        var table = b.closest(".table-editer-form");
        //如果存在担保合同编号，则不需要调用接口
        if(result.warrantyContractNumber != '') {
//          table.find(":text[name='synchrRefreshGuarantee']").eq(index).val("yes");
//          $("#myCustUserLetter").find(":input").removeClass('required');
        }
        //C000表示信用类型担保方式为信用的，担保合同编号不显示；其他担保方式（抵押、质押、保证、其他），显示该字段
        if('C000'==guaranType){
            table.find("input[name='guaranteeNo']").eq(0).parent().parent().hide();//担保合同编号不显示
            table.find("input[name='guaranteeNo']").eq(0).parent().parent().next().hide();//担保合同附件上传不显示
            //添加合同上传标记为不验证
            table.find(":hidden[name='synchrRefreshGuarantee']").val("yes");//合同上传功能里面的输入项不要必输
            $("#myCustUserLetter").find(":input").removeClass('required');//去掉合同窗口内的所有元素的必输项
            $("#myCustUserLetter").find(":input").removeClass('required-warn');//去掉合同窗口内的所有元素的必输项消息
            //担保合同编号不需要验证,不必填
            table.find("input[name='guaranteeNo']").eq(0).removeClass("required");
            table.find("input[name='guaranteeNo']").eq(0).removeClass("required-warn");
            table.find("input[name='guaranteeNo']").eq(0).next().text("");
        }else{
            //担保合同编号显示
            table.find("input[name='guaranteeNo']").eq(0).parent().parent().show();
            //担保合同编号修改赋值逻辑
            //table.find("input[name='guaranteeNo']").eq(0).val(result.warrantyContractNumber);
            var tempGuaranteeNo = result.warrantyContractNumber;
            if (''==tempGuaranteeNo){
                var temp1 = b.textContent;
                tempGuaranteeNo = temp1.substr(temp1.length-8);
            }
            table.find("input[name='guaranteeNo']").eq(0).val(tempGuaranteeNo);

            //合同信息必输
            table.find(":hidden[name='synchrRefreshGuarantee']").val("");
            //合同上传功能里面的输入项必输
            setGuaranteeRequired();
            //担保合同附件上传按钮显示
            table.find("input[name='guaranteeNo']").eq(0).parent().parent().next().show();
        }
        //信用类型和其他担保类型，担保方式类型不可显示
        if('C104'==guaranType||'C000'==guaranType){
        	//担保方式类型下拉框初始化数据
        	table.find("select[name='pledgeType']").eq(0).parent().parent().hide();
     		var pledge = table.find("select[name='pledgeType']").eq(0);
    		getPledgeTypeSelect(pledge, result.typePoint);
    		//担保方式类型不需要验证,不必填
            pledge.removeClass("required");
            pledge.removeClass("required-warn");
            pledge.next().text("");
        }else{
        	//担保方式类型下拉框初始化数据
        	table.find("select[name='pledgeType']").eq(0).parent().parent().show();
     		var pledge = table.find("select[name='pledgeType']").eq(0);
    		getPledgeTypeSelect(pledge, result.typePoint);
        }
        //担保人
        table.find("input[name='guaranteeCustName']").eq(0).val(result.guarantor);
        table.find("input[name='guarantorCustId']").eq(0).val(result.guarantorCustId);
        table.find("textarea[name='guaranRemark']").eq(0).val(result.note);
        table.find("input[name='guarantorTypeCode']").eq(0).val(result.guaranteeContractType);//占用额度类型
        //查询的多个押品信息
        var betInformationList = result.betInformationList;
        //担保类型为抵押或质押时，需要录入押品信息
        if('C102'==guaranType||'C103'==guaranType){
            table.find("#pledgeRow").show();
            //生成多个押品表格
            var pledgeTableDiv = table.find(".pledgeTable");
            createSubTable(pledgeTableDiv, betInformationList.length - 1, 1);
            var size = betInformationList.length;//押品个数
            //设置值选中或赋值
            for(var i = 0; i < size; i++) {
                //TODO 押品状态默认已存1
                //table.find("select[name='pledgeState']").eq(i).val(1);
                table.find("input[name='pledgeNo']").eq(i).val(betInformationList[i].pledNo);
                table.find("textarea[name='pledgeRemark']").eq(i).val(betInformationList[i].productInformation);
                if(betInformationList[i].pledNo == null || betInformationList[i].pledNo == '') {
                    table.find("input[name='pledgeNo']").eq(i).removeClass("required");
                }
                if(betInformationList[i].productInformation == null || betInformationList[i].productInformation == '') {
                    table.find("textarea[name='pledgeRemark']").eq(i).removeClass("required");
                }
            }
            if(size < 1) {
                table.find("input[name='pledgeNo']").removeClass("required");
                table.find("textarea[name='pledgeRemark']").removeClass("required");
            }
        }else{
            //隐藏，并取消必输验证
            table.find("#pledgeRow").hide();
            table.find("input[name='pledgeNo']").removeClass("required");
            table.find("input[name='pledgeNo']").removeClass("required-warn");
            table.find("input[name='pledgeNo']").next().text("");
            table.find("textarea[name='pledgeRemark']").removeClass("required");
            table.find("textarea[name='pledgeRemark']").removeClass("required-warn");
            table.find("textarea[name='pledgeRemark']").next().text("");
        }
        var guarmartType = result.guarmartType;
        table.find("select[name='pledgeType']").eq(0).val(guarmartType);
    }
}

/**
 * 动态判断合同是否必输
 */
function setGuaranteeRequired(){
	$("#guarNo").addClass("required");
	$("#guarManualNo").addClass("required");
	$("#guarCustName").addClass("required");
	$("#guarCustNo").addClass("required");
	$("#guarAmount").addClass("required");
	$("#gCurrency").addClass("required");
	$("#startDate").addClass("required");
	$("#dueDate").addClass("required");
	$("#term").addClass("required");
	$("#termUnit").addClass("required");
	$("#guarContType").addClass("required");
	$("#guarContState").addClass("required");
	$("#guarMaxAmt").addClass("required");
}

/**
 * 功能：给担保弹出框的的输入元素回显赋值。如果对象为空，则清空输入框的内容
 * @param {Object} contract
 */
function initContract(contract) {
    if(contract != null) {
        $("#guarNo").val(contract.guarNo);
        $("#guarManualNo").val(contract.guarManualNo);
        $("#guarCustName").val(contract.guarCustName);
        $("#guarCustNo").val(contract.guarCustNo);
        $("#guarAmount").val(contract.guarAmount);
        var st_date = contract.startDateStr;
        if(st_date==''||st_date==null){
            st_date = contract.startDate;
        }
        $("#startDate").val(st_date);
        var du_date = contract.dueDateStr;
        if(du_date==''||du_date==null){
            du_date = contract.dueDate;
        }
        $("#dueDate").val(du_date);
        $("#term").val(contract.term);
        $("#termUnit").val(contract.termUnit);
        $("#warrandice").val(contract.warrandice);
        $("#guarContType").val(contract.guarContType);
        $("#guarContState").val(contract.guarContState);
        $("#guarMaxAmt").val(contract.guarMaxAmt);
        $("#agent").val(contract.agent);
        $("#agencies").val(contract.agencies);
    }else{
        $("#guarNo").val("");
        $("#guarManualNo").val("");
        $("#guarCustName").val("");
        $("#guarCustNo").val("");
        $("#guarAmount").val("");
        $("#startDate").val("");
        $("#dueDate").val("");
        $("#term").val("");
        $("#termUnit").val("");
        $("#warrandice").val("");
        $("#guarContType").val("");
        $("#guarContState").val("");
        $("#guarMaxAmt").val("");
        //不需要清理经办人和经办机构
//		$("#agent").val("");
//		$("#agencies").val("");
    }
}
/**
 * 功能：如果有值，则清除提醒消息
 * @param {Object} id
 */
function cleanGuarRequireWarn(){
	var gNo = $("#guarNo").val();
	var gName = $("#guarCustName").val();
	var gCNo = $("#guarCustNo").val();
	var gContType = $("#guarContType").val();
	var gMaxAmt = $("#guarMaxAmt").val();
	if(gNo!=''&&gNo!=null){
		cleanRequiredWarn($("#guarNo"));
	}
	if(gName!=''&&gName!=null){
		cleanRequiredWarn($("#guarCustName"));
	}
	if(gCNo!=''&&gCNo!=null){
		cleanRequiredWarn($("#guarCustNo"));
	}
	if(gContType!=''&&gContType!=null){
		cleanRequiredWarn($("#guarContType"));
	}
	if(gMaxAmt!=''&&gMaxAmt!=null){
		cleanRequiredWarn($("#guarMaxAmt"));
	}
}

/**
 * 获取所有金额之和
 */
function getSumAmt() {
    var total = 0.00;
    var amtVals = document.getElementsByName("paymentAmt");
    for(var i = 0; i < amtVals.length; i++) {
        var amtValue = amtVals[i].value;
        var amt_val = 0;
        if(amtValue != null && amtValue != '') {
            amt_val = amtValue.replace(/,/g, '');
        }
        total += Number(amt_val);
    }
    return total;
}
/**
 * 获取所有剩余的担保份额,自动排除掉自身的值
 */
function getTotalGuaranteeAmt(select_val) {
    var total = 0.00;
    var amtVals = document.getElementsByName("clearRatio");
    var len = amtVals.length;
    //只有多个份额划分的时候才进行减法，求剩余额度
    if(len > 1) {
        for(var i = 0; i < len; i++) {
            var amtValue = amtVals[i].value;
            //获取担保份额划分类型，只有可明确划分份额（1）才进行计算
            var type = $(amtVals[i]).parent().parent().prev().find("select[name='guaranPortType']").eq(0).val();
            var amt_val = 0;
            if(amtValue != null && amtValue != '') {
                amt_val = amtValue.replace(/,/g, '');
            }
            if(select_val != amt_val && type == 1) {
                total += Number(amt_val);
            }
        }
    }
    return total;
}
/**
 * 系统自动计算可明确划分的担保金额
 */
function calculateAmt(e) {
    var e_val = $(e).val();
    if(e_val != null && e_val != '') {
        //总金额;
        var totalAmt = getSumAmt();
        var restRatio = Number(100.00) - getTotalGuaranteeAmt(Number(e_val));
        if(e_val > restRatio) {
            var msg = '可明确划分使用本担保方式的份额超限!';
            alertWarn($(e), msg);
            return false;
        } else {
            cleanRequiredWarn($(e));
        }

        var result_amt =  (Number(totalAmt) * (Number(e_val)/100.00)).toFixed(2);
        //可明确划分使用本担保方式的金额-赋值
        $(e).parent().parent().next().find("input[name='clearRatioAmt']").eq(0).val(result_amt);
    }
}
/**
 * 系统自动计算可明确划分的担保金额
 */
function calculateAmtText(e) {
	var e_val = $(e).val();
	var isHide = $(e).hasClass('required');
	//如果输入框非必填项，不验证
	if(!isHide){
		return false;
	}
	if(e_val != null && e_val != '') {
		//总金额;
		var totalAmt = getSumAmt();
		var restRatio = Number(100.00) - getTotalGuaranteeAmt(Number(e_val));
		if(e_val > restRatio) {
			var msg = '可明确划分使用本担保方式的份额超限!';
			alertWarn($(e), msg);
			return false;
		} else {
			cleanRequiredWarn($(e));
		}

		var result_amt = (Number(totalAmt) * (Number(e_val) / 100.00)).toFixed(2);
		//可明确划分使用本担保方式的金额-赋值
		$(e).parent().parent().next().find("input[name='clearRatioAmt']").eq(0).val(result_amt);
	}
}
/**
 * 功能：根据贷款领域，A开头的贷款领域），进出口货物及服务必填
 */
function selectedImportExportGoodsService() {
	var iegs = $("#loanDomain").val();
	if(iegs.substr(0, 1) == 'A') {
		$("#importExportGoodsService").removeProp("disabled");
		$("#importExportGoodsService").val("");//清理旧值
		//2.重新初始化进出口货物及服务下拉框
   		getMulitSelectBox("importExportGoodsServiceUL", "importExportGoodsService", "AGR0280002", "010");
	} else {
		//1.首先设置隐藏表单的为不可用
		$("#importExportGoodsService").prop("disabled", true);
		//2.重新初始化进出口货物及服务下拉框
   		getMulitSelectBox("importExportGoodsServiceUL", "importExportGoodsService", "AGR0280002", "010");
   		//3.重新赋值操作
		$("#importExportGoodsServiceUL").find(".defaultDownBox").text("无");
		$("#importExportGoodsService").val("Y");
		//清除验证消息
		cleanRequiredWarn($("#importExportGoodsServiceUL"));
	}
}

/**
 * 功能：显示币种
 */
function getDebtCurrencyDb(debtCode) {
    var result = null;
    $.ajax({
        type: "PUT",
        url: "/eximbank-club/grant/getCurrency",
        data: angular.toJson({
            'debtCode': debtCode
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
 * 功能：后台查询担保信息
 * @param {Object} gid
 */
function getGuaranteeDbById(gid) {
    var result = null;
    $.ajax({
        type: "PUT",
        url: "/eximbank-club/bizRentalFactoring/read/getBizGuaranteeInfo",
        data: angular.toJson({
            'gId': gid,
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
 * 功能：显示担保类型
 * @param {Object} parentCode 父编码
 * @param {Object} debtCode 债项编号
 */
function getDebtGuaranteeInfoByCodeDb(parentCode, debtCode) {
    var result = null;
	$.ajax({
		type: "PUT",
		async: false,
		url: "/eximbank-club/grant/getGuaranteeInfo",
		data: angular.toJson({
			'debtCode': debtCode,
			'parentCode': parentCode
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
 * 显示 方案选中的币种下拉框
 * @param {Object} id 元素id属性
 */
function loadCurrencySelect(id, debtCode) {
    //清空原来的数据
    $("#" + id).empty();
    var html = defaultOption();
    //查询数据库
    var jsonResult = getDebtCurrencyDb(debtCode);
    html += createCurrencyOption(jsonResult);
    //连接字符串
    $("#" + id).append(html);
}

/**
 * 生成担保类型下拉框
 * @param {Object} curId 元素id属性
 */
function loadGuaranteeInfoSelect(curId, code) {
    var parentCode = "";
	//清空原来的数据
	$("#" + curId).empty();
	var html = defaultOption();
	//使用延迟函数查询数据库
	var jsonResult = getDebtGuaranteeInfoByCodeDb(parentCode, code);
	html += createOption(jsonResult);
	//连接字符串
	$("#" + curId).append(html);
}

/**
 * 通过（担保类型）一级下拉框生成二级下拉框
 * @param {Object} curId 元素id属性
 */
function changeGuaranteeInfoSelect(curId) {
    var parentCode = $(curId).val();
    if(!parentCode) {
        return null;
    }
    var debtNum = $("#debtCode").val();
    //清空原来的数据
    $(curId).next().empty();
    var html = defaultOption();
    //查询数据库
    var jsonResult = getDebtGuaranteeInfoByCodeDb(parentCode, debtNum);
	html += createOption(jsonResult);
	//连接字符串
	$(curId).next().append(html);
}

/////以下是初始化加载的函数///////////////

/**
 * 通过（担保类型）一级下拉框生成二级下拉框
 * @param {Object} parentCode
 * @param {Object} cVal
 * @param {Object} selectBox
 */
function getGuaranteeInfoSelect(parentCode, cVal, selectBox, debtNum) {
    //清空原来的数据
    selectBox.empty();
    var html = defaultOption();
    //查询数据库
    var jsonResult = getDebtGuaranteeInfoByCodeDb(parentCode, debtNum);
	html += getOptionSelected(jsonResult, cVal);
	//连接字符串
 	selectBox.append(html);	
}
/**
 * 生成利率期限下拉框
 * @param {Object} code
 * @param {Object} cVal
 * @param {Object} selectBox
 */
function getInterestRateSelect(code, cVal, selectBox) {
    //清空原来的数据
    selectBox.empty();
    var html = defaultOption();
    //查询数据库
    var jsonResult = getInterestRateByCodeDb(code, '1');
    html += createOptionSelected(jsonResult, cVal);
    //连接字符串
    selectBox.append(html);
}


/**
 * 生成col-sm-5下拉框
 * @param {Object} parentCode
 * @param {Object} cVal
 * @param {Object} selectBox
 */
function getEditPledgeTypeSelect(selectBox, parentCode, cVal) {
    //清空原来的数据
    var html = defaultOption();
    //查询数据库
    var jsonResult = getPledgeTypeByCodeDb(parentCode);
    html += createOptionSelected(jsonResult, cVal);
    //连接字符串
    selectBox.empty();
    selectBox.append(html);
}
/**
 * 生成货币单位下拉框
 * @param {Object} cVal
 * @param {Object} selectBox
 */
function getCurrencySelect(cVal, selectBox) {
    //默认选中人民币
    if(cVal==null||cVal==''){
        cVal="CNY";
    }
    //清空原来的数据
    selectBox.empty();
    var html = defaultOption();
    //使用延迟对象查询数据库，优化查询效率
	$.when(getCurrencyDb()).done(function(result){
		var jsonResult = result;
		html += createCurrencyOptionSelected(jsonResult, cVal);
		//连接字符串
		selectBox.append(html);
		cleanRequiredWarn(selectBox);
	}).fail(function(){
		alert("查询失败!");
	});
}

/**
 * 检查利率值是否为0，必须大于0 
 */
function checkRateVal(b){
	var bVal = b.value;
	var isReadOnly = $(b).hasClass("required");
	//只有必填时才验证是否大于0
	if(!isReadOnly){
		return false;
	}
	if(bVal!='' && parseFloat(bVal)==0){
		var msg = '利率必须大于0!';
    	alertWarn($(b), msg);
    	return false;
	}else{
		cleanRequiredWarn($(b));
	}
}

/**
 * 功能：获取客户授信信息接口
 */
function getCustomerCreditInfo(customerNo) {
	var defer = $.Deferred();   //创建一个新的 Deferred（延迟）对象
	//初始化接口
	var bdc = new BDContext();
	//执行接口调用
	bdc.invokeInf("khztsxxxcx", "khztsxxxcx", [customerNo],
		function(errorcode, data) 
		{
			console.log("code", errorcode);
			console.log("data", data);
			if(errorcode == "R0000") 
			{
				var temCredit = null;
				angular.forEach(data, function(v, index) {
					//接口调用成功
					if(v.RspCode == '000000' && index == 0) {
						data.splice(index, 1);
						temCredit = '11';
					}
					//接口调用失败
					if(temCredit == null) {
						var errorMsg = '调用信贷接口错误，请稍后后再试!';
						alert(errorMsg);
						return -1;
					}
					//接口调用成功
					if(temCredit == '11') {
						//接口下没有数据
						if(v.ErrorNo == '0') {
							var errorMsg = '该客户下没有最高额合同信息，请查证后再试!';
							alert(errorMsg);
							return -1;
						}
					}
				});
			} else {
				alert("接口调用失败,错误码" + errorcode);
				return -1;
			}
			//解决Deferred（延迟）对象，并根据给定的参数调用任何 doneCallbacks 回调函数
			defer.resolve(data);  
		}
	);
	// 当ajax执行完毕，返回 Deferred 对象
	return defer.promise(); 
}
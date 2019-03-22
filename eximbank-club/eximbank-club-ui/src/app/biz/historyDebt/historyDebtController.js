angular.module('app')
	.controller('historyDebtController', ['$scope', '$rootScope', '$http', '$state', '$stateParams', '$timeout', '$filter', '$CanvasService', 'WebUploadService',
		function($scope, $rootScope, $http, $state, $stateParams, $timeout, $filter, $CanvasService, WebUploadService) {
			$("input").attr("autocomplete", "off");
			$scope.loading = false;
			//得到债项概要编号
			var debtCode = $state.params.debtCode;
			//参数传递
			var tempId = $state.params.id;
			//处理其他可选币种
			var oc = [];
			$scope.currencyProMix = [];

			/*页面切换*/
			$scope.webUploadStatus = 0;
			$scope.changeTab = function(tab, Y) {
				if(tab == 3) {
					var temBloo = true;
					angular.forEach($scope.rentFacList, function(v1, k1) {
						if(v1.customersList.length == 0) {
							alert("提醒", "您还没有选择用信客户，请点击添加客户，填写客户信息！");
							temBloo = false;
						}

					});
					if(temBloo == false) {
						return temBloo;
					}

				}

				if(Y == 1) {
					//					$scope.tab = tab;
				} else {
					var t = validateForm(tab - 1).valueOf();
					console.log(t);
					if(!t) {
						$.growl.error({
							title: "提醒",
							message: "存在未填项！",
							size: "large"
						});
						return false;
					} else {
						//快照
						var promise = $CanvasService.createCanvas(angular.element('.canvas')[0], "A", angular.element('.canvasNum').find('.active').attr("num"), debtCode, null, null, null);
						promise.then(function(data) {
							if(tab == 4 && $scope.webUploadStatus == 0) {
								$scope.webUploadStatus++;
								WebUploadService.initFiles([debtCode, debtCode], ["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout);
							}
							$scope.tab = tab;

							if(window.parent) {
								$("html,body", window.parent.document).animate({
									scrollTop: 0
								}, 200);
							} else {
								$("html,body").animate({
									scrollTop: 0
								}, 200);
							}
						}, function(data) {
							console.log("创建快照失败 error data ==" + data);
						}, function(data) {});

					}
				}
			};

			//上一步
			$scope.selectBefore = function(tabl) {
				$(".text-danger").remove();
				$(".required-warn").removeClass("required-warn");
				$scope.tab = tabl - 1;
			}

			//得到全部的客户
			getAllCust();

			//辅助币种去除主币种选项
			$scope.updateCurrency = function() {
				var currencyListNew = $scope.currencyList;
				var currencyListNew2 = [];
				for(var i = 0; i < currencyListNew.length; i++) {
					currencyListNew2.push(currencyListNew[i]);
				}
				var index = -1;
				for(var i = 0; i < currencyListNew.length; i++) {
					if(currencyListNew[i].monCode == $scope.debtMain.mpc) {
						index = i;
					}
				}
				currencyListNew2.splice(index, 1);
				$scope.lastCurrencyList = currencyListNew2;
			}

			$timeout(function() {
				initBlur();
				// keep();
			}, 1000);

			//回显暂存
			function getAllSchemeInforma() {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/temStorage/editTemp',
					data: angular.toJson({
						"id": tempId
					})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {

						//全局产品规则,制成空
						if(result.data.debtMain.ruleType != null) {
							result.data.debtMain.ruleType = "";
						}
						//处理其他可选币种
						if(result.data.debtMain.oc.length != 0) {
							oc = result.data.debtMain.oc.split(',');
							for(var i = 0; i < oc.length; i++) {
								if(i == oc.length - 1) {
									oc.splice(i);
								}
							}
						}

						$scope.debtMain = result.data.debtMain;
						$scope.rentFacList = result.data.rentFacList;
						//处理产品选项
						angular.forEach($scope.rentFacList, function(v, k) {
							$scope.proRentCode = v.code;
							return false;
						});

						//处理前台时间格式
						$scope.debtMain.processInitiatTime = $filter('date')($scope.debtMain.processInitiatTime, 'yyyy-MM-dd');
						$scope.debtMain.pgEffectivDate = $filter('date')($scope.debtMain.pgEffectivDate, 'yyyy-MM-dd');
						$scope.debtMain.pgExpiDate = $filter('date')($scope.debtMain.pgExpiDate, 'yyyy-MM-dd');

						var ccussTem = null;
						//处理额度类型
						angular.forEach($scope.rentFacList, function(v1, k1) {
							ccussTem = v1.custNo;
							angular.forEach(v1.customersList, function(v2, k2) {
								angular.forEach(v2.creditLinesList, function(v3, k3) {
									v3.amountType = v3.amountType.toString();
								});
							});
						});
						//处理承租人
						/*angular.forEach($scope.allCustList, function(v6, k6) {
							if(ccussTem == v6.custNo) {
								$scope.custName = v6.custNameCN;
								$scope.custPin = v6.creditRating;
							}
						});*/

						//处理所属机构
						angular.forEach($scope.allCustList, function(v6, k6) {

						})

						$scope.bizGuaranteeInfoList = result.data.bizGuaranteeInfoList;

						//定义一个临时数组，来判断担保类型总的显示
						var guarTem = [];

						angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {

							//到到担保合同类型/占用额度类型
							if(v.typePoint != "") {
								getContractTypeList(v.typePoint);
							}
							//假如eve是1，说明是抵押,假如eve是2，说明质押
							if(v.typePoint == "1" || v.typePoint == "2" || v.typePoint == "3") {
								$scope.showBet = true;
								//得到抵押子集押品
								getMortgageList(v.typePoint);

							}
							//担保合同编号除了信用是隐藏，其他都是展示
							if(v.typePoint == '4') {
								$scope.showContackNum = false;
							} else {
								$scope.showContackNum = true;
							}

							//转String类型，基础担保类型(分)
							//							v.typePoint=v.typePoint.toString();
							//转String类型，担保合同类型/占用额度类型
							//							v.guaranteeContractType=v.guaranteeContractType.toString();
							//转String类型，担保合同编号
							//							v.warrantyContractNumber=v.warrantyContractNumber.toString();
							//转String类型，担保人客户编号
							//							v.guarantorCustId=v.guarantorCustId.toString();
							//转String类型，担保人客户编号
							//							v.guarantorCustId=v.guarantorCustId.toString();
							/*angular.forEach(v.betInformationList,function(v1,k1){
								//转String类型，担保方式类型
								v1.guarmartType=v1.guarmartType.toString();
								
							});*/

							if(v.typePoint == "1") {
								guarTem.push(v.typePoint);
							}
							if(v.typePoint == "2") {
								guarTem.push(v.typePoint);
							}
							if(v.typePoint == "3") {
								guarTem.push(v.typePoint);
							}
							if(v.typePoint == "4") {
								guarTem.push(v.typePoint);
							}
							if(v.typePoint == "5") {
								guarTem.push(v.typePoint);
							}
						});

						//抵押 担保类型总
						if(guarTem.indexOf("1") > -1) {
							$scope.rent1 = true;
						} else {
							$scope.rent1 = false;
						}
						//质押 担保类型总	
						if(guarTem.indexOf("2") > -1) {
							$scope.rent2 = true;
						} else {
							$scope.rent2 = false;
						}
						//保证 担保类型总
						if(guarTem.indexOf("3") > -1) {
							$scope.rent3 = true;
						} else {
							$scope.rent3 = false;
						}
						//信用   担保类型总
						if(guarTem.indexOf("4") > -1) {
							$scope.rent4 = true;
						} else {
							$scope.rent4 = false;
						}
						//其他
						if(guarTem.indexOf("5") > -1) {
							$scope.rent5 = true;
						} else {
							$scope.rent5 = false;
						}

						//处理行业投向
						angular.forEach($scope.rentFacList, function(v, k) {
							$scope.industryToTem = v.industryTo;

							//处理用信主体,用信主体为空的话，需要添加一个空的客户进去
							if(v.customersList.length == 0) {
								var cus = {
									custNo: '',
									custNameCN: '',
									creditRatio: '100',
									custScale: '',
									groupNumber: '',
									custNameEN: '',
									groupName: '',
									custAddressCN: '',
									mainBusiness: '',
									custAddressEN: '',
									custCountry: '',
									custBusinessUnit: '',
									creditRating: '',
									custRegistrastionType: '',
									ratiTime: '',
									custRatingCreditType: '',
									custManager: '',
									creditLinesList: []
								};
								var a = {
									amountType: "1101",
									creditLineName: "最高综合授信额度",
									creditNo: "1101",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var b = {
									amountType: "1102",
									creditLineName: "流动资金类贷款额度",
									creditNo: "1102",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var c = {
									amountType: "1103",
									creditLineName: "贸易融资额度",
									creditNo: "1103",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var d = {
									amountType: "1104",
									creditLineName: "融资类保函额度",
									creditNo: "1104",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var e = {
									amountType: "1105",
									creditLineName: "非融资类保函额度",
									creditNo: "1105",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var f = {
									amountType: "1106",
									creditLineName: "最高保证额度",
									creditNo: "1106",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var g = {
									amountType: "1107",
									creditLineName: "公开授信额度项下业务的免担保授信额度",
									creditNo: "1107",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var h = {
									amountType: "1108",
									creditLineName: "非公开授信额度项下业务的免担保授信额度",
									creditNo: "1108",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var i = {
									amountType: "1109",
									creditLineName: "融资类保函免反担保额度",
									creditNo: "1109",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var j = {
									amountType: "1110",
									creditLineName: "船舶预付款退款保函免反担保额度",
									creditNo: "1110",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var k = {
									amountType: "1111",
									creditLineName: "其他非融资类保函免反担保额度",
									creditNo: "1111",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var l = {
									amountType: "1112",
									creditLineName: "专项授信（或其他类型授信额度）",
									creditNo: "1112",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								cus.creditLinesList.push(a);
								cus.creditLinesList.push(b);
								cus.creditLinesList.push(c);
								cus.creditLinesList.push(d);
								cus.creditLinesList.push(e);
								cus.creditLinesList.push(f);
								cus.creditLinesList.push(g);
								cus.creditLinesList.push(h);
								cus.creditLinesList.push(i);
								cus.creditLinesList.push(j);
								cus.creditLinesList.push(k);
								cus.creditLinesList.push(l);
								var cusList = [];
								cusList.push(cus);
								v.customersList = cusList;
							}

							//处理评级有效期
							angular.forEach(v.customersList, function(v1, k1) {
								if(v1.ratiTime != '') {
									v1.ratiTime = $filter('date')(v1.ratiTime, 'yyyy-MM-dd');
								}
							});
						});

						setTimeout(function() {
							//行业投向
							bulidMulitSelect("industryInve", "industryTo", "A0", "005");
							initBlur();
						}, 1000);

					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			$timeout(function() {
				//行业投向
				initMulitBox($scope.industryToTem);
			}, 4000);
			/**
			 * 功能 ： 回显多级下拉框 
			 * @param {Object} code
			 */
			function initMulitBox(code) {
				//类型字符串				
				var types = "005";
				//编码字符串
				var codes = code;
				var ul_arr = ["industryInve:" + code]
				getMulitSelectList(ul_arr, types, codes);
			}

			//回显方案所有信息
			function getAllSchemeInformation() {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/historyDebt/list',
					data: angular.toJson({
						'debtCode': debtCode
					})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						//转String类型，是否单笔单批
						result.data.debtMain.singleBtch = result.data.debtMain.singleBtch.toString();
						//转String类型，方案循环标志
						result.data.debtMain.ls = result.data.debtMain.ls.toString();
						//全局产品规则,制成空
						result.data.debtMain.ruleType = "";
						//转String类型，可办理笔数限制
						result.data.debtMain.ltnopa = result.data.debtMain.ltnopa.toString();
						//转String类型，可办理笔数
						if(result.data.debtMain.tdwln != null) {
							result.data.debtMain.tdwln = result.data.debtMain.tdwln.toString();
						}
						//处理循环标注
						if(result.data.debtMain.validityCcycleGauge != null) {
							result.data.debtMain.validityCcycleGauge = result.data.debtMain.validityCcycleGauge.toString();
						}

						//处理前台时间格式
						result.data.debtMain.processInitiatTime = $filter('date')(result.data.debtMain.processInitiatTime, 'yyyy-MM-dd');
						result.data.debtMain.pgEffectivDate = $filter('date')(result.data.debtMain.pgEffectivDate, 'yyyy-MM-dd');
						result.data.debtMain.pgExpiDate = $filter('date')(result.data.debtMain.pgExpiDate, 'yyyy-MM-dd');

						//处理其他可选币种
						if(result.data.debtMain.oc.length != 0) {
							oc = result.data.debtMain.oc.split(',');
							for(var i = 0; i < oc.length; i++) {
								if(i == oc.length - 1) {
									oc.splice(i);
								}
							}
						}

						//处理其他可选币种没有值的情况
						if(result.data.debtMain.mpc != null) {
							/*							var currencyListNew = $scope.currencyList;
														var currencyListNew2 = [];
														for(var i=0;i<currencyListNew.length;i++){
															currencyListNew2.push(currencyListNew[i]);
														}
														var index = -1;
														for(var i=0;i<currencyListNew.length;i++){
															if(currencyListNew[i].monCode == $scope.debtMain.mpc){
																index = i;
															}
														}
														currencyListNew2.splice(index,1);
														$scope.lastCurrencyList = currencyListNew2;*/
							$scope.lastCurrencyList = $scope.currencyList;
						}

						//转String类型，方案费率类型
						result.data.debtMain.progRateType = result.data.debtMain.progRateType.toString();
						//转String类型，方案费率形式
						if(result.data.debtMain.schemeRateForm != null) {
							result.data.debtMain.schemeRateForm = result.data.debtMain.schemeRateForm.toString();
						}
						//转String类型，方案费率
						if(result.data.debtMain.packageRate != null) {
							result.data.debtMain.packageRate = result.data.debtMain.packageRate.toString();
						}
						//转String类型，是否经审批
						if(result.data.debtMain.approve != null) {
							result.data.debtMain.approve = result.data.debtMain.approve.toString();
						}
						//转String类型，审批机构
						if(result.data.debtMain.raaa != null) {
							result.data.debtMain.raaa = result.data.debtMain.raaa.toString();
						}
						//转String类型，是否存在方案费率折扣
						//						result.data.debtMain.whetherRateDiscount=result.data.debtMain.whetherRateDiscount.toString();
						//转String类型，方案费率折扣
						if(result.data.debtMain.programRateDiscount != null) {
							result.data.debtMain.programRateDiscount = result.data.debtMain.programRateDiscount.toString();
						}
						if(result.data.debtMain.progRateType == '1') {
							result.data.debtMain.programRateDiscount = 0;
						}

						$scope.debtMain = result.data.debtMain;
						$scope.rentFacList = result.data.rentFacList;
						//处理产品选项
						angular.forEach($scope.rentFacList, function(v, k) {
							$scope.proRentCode = v.code;
							return false;
						});

						//处理其他可选币种
						if(result.data.debtMain.oc.length != 0) {
							oc = result.data.debtMain.oc.split(',');
							for(var i = 0; i < oc.length; i++) {
								if(i == oc.length - 1) {
									oc.splice(i);
								}
							}
						}

						var ccussTem = null;
						//处理额度类型
						angular.forEach($scope.rentFacList, function(v1, k1) {
							ccussTem = v1.custNo;
							angular.forEach(v1.customersList, function(v2, k2) {
								angular.forEach(v2.creditLinesList, function(v3, k3) {
									v3.amountType = v3.amountType.toString();
								});
							});
						});
						//处理承租人
						angular.forEach($scope.allCustList, function(v6, k6) {
							if(ccussTem == v6.custNo) {
								$scope.custName = v6.custNameCN;
								$scope.custPin = v6.creditRating;
							}
						});

						$scope.bizGuaranteeInfoList = result.data.bizGuaranteeInfoList;

						//定义一个临时数组，来判断担保类型总的显示
						var guarTem = [];

						angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {

							//到到担保合同类型/占用额度类型
							if(v.typePoint != null) {
								getContractTypeList(v.typePoint, index);
							}

							//假如eve是1，说明是抵押,假如eve是2，说明质押
							if(v.typePoint == "1" || v.typePoint == "2" || v.typePoint == "3") {
								if(v.typePoint == "1" || v.typePoint == "2") {
									$scope.showBet = true;
								} else {
									$scope.showBet = false;
								}
								//得到担保方式类型
								getMortgageList(v.typePoint, index);

							}
							//担保合同编号除了信用是隐藏，其他都是展示
							if(v.typePoint == '4') {
								$scope.showContackNum = false;
							} else {
								$scope.showContackNum = true;
							}

							//转String类型，基础担保类型(分)
							if(v.typePoint != null) {
								v.typePoint = v.typePoint.toString();
							}
							//转String类型，担保合同类型/占用额度类型
							if(v.guaranteeContractType != null) {
								v.guaranteeContractType = v.guaranteeContractType.toString();
							}
							//转String类型，担保合同编号
							if(v.warrantyContractNumber != null) {
								v.warrantyContractNumber = v.warrantyContractNumber.toString();
							}
							//转String类型，担保人客户编号
							if(v.guarantorCustId != null) {
								v.guarantorCustId = v.guarantorCustId.toString();
							}
							//转String类型，担保人客户编号
							if(v.guarantorCustId != null) {
								v.guarantorCustId = v.guarantorCustId.toString();
							}
							angular.forEach(v.betInformationList, function(v1, k1) {
								//转String类型，担保方式类型
								if(v1.guarmartType != null) {
									v1.guarmartType = v1.guarmartType.toString();
								}
							});

							if(v.typePoint == "1") {
								guarTem.push(v.typePoint);
							}
							if(v.typePoint == "2") {
								guarTem.push(v.typePoint);
							}
							if(v.typePoint == "3") {
								guarTem.push(v.typePoint);
							}
							if(v.typePoint == "4") {
								guarTem.push(v.typePoint);
							}
							if(v.typePoint == "5") {
								guarTem.push(v.typePoint);
							}
						});

						//抵押 担保类型总
						if(guarTem.indexOf("1") > -1) {
							$scope.rent1 = true;
						} else {
							$scope.rent1 = false;
						}
						//质押 担保类型总	
						if(guarTem.indexOf("2") > -1) {
							$scope.rent2 = true;
						} else {
							$scope.rent2 = false;
						}
						//保证 担保类型总
						if(guarTem.indexOf("3") > -1) {
							$scope.rent3 = true;
						} else {
							$scope.rent3 = false;
						}
						//信用   担保类型总
						if(guarTem.indexOf("4") > -1) {
							$scope.rent4 = true;
						} else {
							$scope.rent4 = false;
						}
						//其他
						if(guarTem.indexOf("5") > -1) {
							$scope.rent5 = true;
						} else {
							$scope.rent5 = false;
						}

						//处理行业投向
						angular.forEach($scope.rentFacList, function(v, k) {
							$scope.industryToTem = v.industryTo;

							//处理用信主体,用信主体为空的话，需要添加一个空的客户进去
							if(v.customersList.length == 0) {
								var cus = {
									custNo: '',
									custNameCN: '',
									creditRatio: '100',
									custScale: '',
									groupNumber: '',
									custNameEN: '',
									groupName: '',
									custAddressCN: '',
									mainBusiness: '',
									custAddressEN: '',
									custCountry: '',
									custBusinessUnit: '',
									creditRating: '',
									custRegistrastionType: '',
									ratiTime: '',
									custRatingCreditType: '',
									custManager: '',
									creditLinesList: []
								};
								var a = {
									amountType: "1101",
									creditLineName: "最高综合授信额度",
									creditNo: "1101",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var b = {
									amountType: "1102",
									creditLineName: "流动资金类贷款额度",
									creditNo: "1102",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var c = {
									amountType: "1103",
									creditLineName: "贸易融资额度",
									creditNo: "1103",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var d = {
									amountType: "1104",
									creditLineName: "融资类保函额度",
									creditNo: "1104",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var e = {
									amountType: "1105",
									creditLineName: "非融资类保函额度",
									creditNo: "1105",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var f = {
									amountType: "1106",
									creditLineName: "最高保证额度",
									creditNo: "1106",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var g = {
									amountType: "1107",
									creditLineName: "公开授信额度项下业务的免担保授信额度",
									creditNo: "1107",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var h = {
									amountType: "1108",
									creditLineName: "非公开授信额度项下业务的免担保授信额度",
									creditNo: "1108",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var i = {
									amountType: "1109",
									creditLineName: "融资类保函免反担保额度",
									creditNo: "1109",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var j = {
									amountType: "1110",
									creditLineName: "船舶预付款退款保函免反担保额度",
									creditNo: "1110",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var k = {
									amountType: "1111",
									creditLineName: "其他非融资类保函免反担保额度",
									creditNo: "1111",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								var l = {
									amountType: "1112",
									creditLineName: "专项授信（或其他类型授信额度）",
									creditNo: "1112",
									totalAmount: "",
									usedAmount: "",
									availableBalance: "",
									startDate: "",
									maturityDate: ""
								};
								cus.creditLinesList.push(a);
								cus.creditLinesList.push(b);
								cus.creditLinesList.push(c);
								cus.creditLinesList.push(d);
								cus.creditLinesList.push(e);
								cus.creditLinesList.push(f);
								cus.creditLinesList.push(g);
								cus.creditLinesList.push(h);
								cus.creditLinesList.push(i);
								cus.creditLinesList.push(j);
								cus.creditLinesList.push(k);
								cus.creditLinesList.push(l);
								var cusList = [];
								cusList.push(cus);
								v.customersList = cusList;
							}

							//处理评级有效期
							angular.forEach(v.customersList, function(v1, k1) {
								if(v1.ratiTime != '') {
									v1.ratiTime = $filter('date')(v1.ratiTime, 'yyyy-MM-dd');
								}
							});
						});

						setTimeout(function() {
							//行业投向
							bulidMulitSelect("industryInve", "industryTo", "A0", "005");
							initBlur();
						}, 1000);

					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			$timeout(function() {
				//行业投向
				initMulitBox($scope.industryToTem);
			}, 4000);
			/**
			 * 功能 ： 回显多级下拉框 
			 * @param {Object} code
			 */
			function initMulitBox(code) {
				//类型字符串				
				var types = "005";
				//编码字符串
				var codes = code;
				var ul_arr = ["industryInve:" + code]
				getMulitSelectList(ul_arr, types, codes);
			}

			/*
			 * 债项概要部分
			 * 
			 * */
			//加载提示
			$scope.loading = false;
			//初始化的时候全局展示
			$scope.show1 = true;

			//得到信贷员
			//getUserList();
			//得到用户所属的机构
			//getDeptList();
			//查询出下拉款的是否
			getSelect();
			//查询出全部的产品
			//getProList();
			//查询出租金保理
			getRentFactorList();

			//得到信贷员
			function getUserList() {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/getUserList/list',
					data: angular.toJson({})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.userList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			//得到用户所属的机构
			function getDeptList() {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/getDeptList/list',
					data: angular.toJson({})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.deptList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			//查询出下拉款的是否
			function getSelect() {
				$scope.loading = true;
				var tem = 'RADIO1';
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/read/query',
					data: angular.toJson({
						'type': tem
					})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.dicList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			//查询出租金保理
			function getRentFactorList() {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/getRentFactorList/list',
					data: angular.toJson({})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.rentFactorList = result.data;
						//取出债项方案id
						//						angular.forEach($scope.rentFactorList, function(v, k) {
						//							v.checked = false;
						//							$scope.debtCode = v.debtCode;
						//						})
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			//获取点击的租金保理
			$scope.selelct = function(te) {
				$scope.rentFacList = [];
				angular.forEach($scope.rentFactorList, function(v, k) {
					if(te == v.code) {
						//先向产品中插入一个空的客户对象
						var cus = {
							custNo: '',
							custNameCN: '',
							creditRatio: '100',
							custScale: '',
							groupNumber: '',
							custNameEN: '',
							groupName: '',
							custAddressCN: '',
							mainBusiness: '',
							custAddressEN: '',
							custCountry: '',
							custBusinessUnit: '',
							creditRating: '',
							custRegistrastionType: '',
							ratiTime: '',
							custRatingCreditType: '',
							custManager: '',
							creditLinesList: []
						};
						var a = {
							amountType: "1101",
							creditLineName: "最高综合授信额度",
							creditNo: "1101",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var b = {
							amountType: "1102",
							creditLineName: "流动资金类贷款额度",
							creditNo: "1102",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var c = {
							amountType: "1103",
							creditLineName: "贸易融资额度",
							creditNo: "1103",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var d = {
							amountType: "1104",
							creditLineName: "融资类保函额度",
							creditNo: "1104",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var e = {
							amountType: "1105",
							creditLineName: "非融资类保函额度",
							creditNo: "1105",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var f = {
							amountType: "1106",
							creditLineName: "最高保证额度",
							creditNo: "1106",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var g = {
							amountType: "1107",
							creditLineName: "公开授信额度项下业务的免担保授信额度",
							creditNo: "1107",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var h = {
							amountType: "1108",
							creditLineName: "非公开授信额度项下业务的免担保授信额度",
							creditNo: "1108",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var i = {
							amountType: "1109",
							creditLineName: "融资类保函免反担保额度",
							creditNo: "1109",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var j = {
							amountType: "1110",
							creditLineName: "船舶预付款退款保函免反担保额度",
							creditNo: "1110",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var k = {
							amountType: "1111",
							creditLineName: "其他非融资类保函免反担保额度",
							creditNo: "1111",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var l = {
							amountType: "1112",
							creditLineName: "专项授信（或其他类型授信额度）",
							creditNo: "1112",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						cus.creditLinesList.push(a);
						cus.creditLinesList.push(b);
						cus.creditLinesList.push(c);
						cus.creditLinesList.push(d);
						cus.creditLinesList.push(e);
						cus.creditLinesList.push(f);
						cus.creditLinesList.push(g);
						cus.creditLinesList.push(h);
						cus.creditLinesList.push(i);
						cus.creditLinesList.push(j);
						cus.creditLinesList.push(k);
						cus.creditLinesList.push(l);
						var cusList = [];
						cusList.push(cus);
						v.customersList = cusList;
						$scope.rentFacList.push(v);
						setTimeout(function() {
							bulidMulitSelect("industryInve", "industryTo", "A0", "005");
							initBlur();
						}, 1000);
					}
				});

			}

			//申请人选中
			$scope.onSelectProposer = function(arg) {
				//申请人名称
				$scope.debtMain.proposer = arg.custNameCN;
				//申请人客户号
				$scope.debtMain.proposerNum = arg.custNo;
				$scope.cctem = arg;
				var dbc = [];
				var a = {
					amountType: "1101",
					creditLineName: "最高综合授信额度",
					creditNo: "1101",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var b = {
					amountType: "1102",
					creditLineName: "流动资金类贷款额度",
					creditNo: "1102",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var c = {
					amountType: "1103",
					creditLineName: "贸易融资额度",
					creditNo: "1103",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var d = {
					amountType: "1104",
					creditLineName: "融资类保函额度",
					creditNo: "1104",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var e = {
					amountType: "1105",
					creditLineName: "非融资类保函额度",
					creditNo: "1105",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var f = {
					amountType: "1106",
					creditLineName: "最高保证额度",
					creditNo: "1106",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var g = {
					amountType: "1107",
					creditLineName: "公开授信额度项下业务的免担保授信额度",
					creditNo: "1107",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var h = {
					amountType: "1108",
					creditLineName: "非公开授信额度项下业务的免担保授信额度",
					creditNo: "1108",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var i = {
					amountType: "1109",
					creditLineName: "融资类保函免反担保额度",
					creditNo: "1109",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var j = {
					amountType: "1110",
					creditLineName: "船舶预付款退款保函免反担保额度",
					creditNo: "1110",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var k = {
					amountType: "1111",
					creditLineName: "其他非融资类保函免反担保额度",
					creditNo: "1111",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				var l = {
					amountType: "1112",
					creditLineName: "专项授信（或其他类型授信额度）",
					creditNo: "1112",
					totalAmount: "",
					usedAmount: "",
					availableBalance: "",
					startDate: "",
					maturityDate: ""
				};
				dbc.push(a);
				dbc.push(b);
				dbc.push(c);
				dbc.push(d);
				dbc.push(e);
				dbc.push(f);
				dbc.push(g);
				dbc.push(h);
				dbc.push(i);
				dbc.push(j);
				dbc.push(k);
				dbc.push(l);
				$scope.cctem.creditLinesList = dbc;
				//判断是否选了产品组合，是的话，就更新用信主体
				if($scope.rentFacList != []) {
					angular.forEach($scope.rentFacList, function(v, k) {
						angular.forEach(v.customersList, function(v1, index) {
							if(index == 0) {
								v.customersList.splice(index, 1, $scope.cctem);
								return false;
							}
						});
					});
				}

			};

			//担保合同编号唯一校验
			$scope.queryConNum = function(arg, type, inde) {
				$scope.loading = true;
				if(type == '11' || type == '31' || type == '21' || type == '23' || type == '5' || type == '4') {
					//判断多套担保信息的担保合同编号是否唯一
					if(inde > 0) {
						angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
							if(index != inde) {
								var index = inde + 'contractNumber';
								if(arg == v.warrantyContractNumber) {
									
									var errorMsg = '该担保合同已经被占用，请重新录入！';
									$('#' + index).removeClass('required');
									$('#' + index).parent().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
									if($('#' + index).parent().find("span").hasClass("text-danger")) {
										$('#' + index).parent().find(".text-danger").text(errorMsg);
									} else {
										$('#' + index).parent().append('<span class="text-danger">' + errorMsg + '</span>');
									}
									$('#' + index).addClass("required-warn");
									$('#' + index).val('');
									return false;
								}else{
									$('#'+index).addClass('required');
									$('#'+index).parent().find('span').remove();
								}
							}

						});
					}

					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/queryConNum/list',
						data: angular.toJson({
							"warrantyContractNumber": arg,
							"debtCodeCon": debtCode
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							if(result.data == true) {
								var index = inde + 'contractNumber';
								var errorMsg = '该担保合同已经被占用，请重新录入！';
								$('#' + index).val('');
								$('#' + index).parent().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
								$('#' + index).parent().append('<span class="text-danger">' + errorMsg + '</span>');
								$('#' + index).addClass("required-warn");
								return false;
							}
						} else {
							$scope.msg = result.msg;
						}
						$rootScope.$applyAsync();
					});
				}
			}

			/*
			 * 用信主体部分
			 * 
			 * */
			/*
			 * 以下是查询客户部分controller
			 */

			//定义一个对象，用来暂存点击的查询客户
			$scope.customerTemm = null;
			//定义一个对象，用来暂存点击的产品
			$scope.proTemm = null;

			$scope.quCustom = function(p, rent) {
				//cu是0说明是查询客户，展示保存按钮
				$scope.cu = 0;
				$scope.title = "查询客户";
				$scope.custoNum = null;
				$scope.recordUseLetter = null;

				$scope.proTemm = p;
				$scope.customerTemm = rent;
				//清空提示信息
				$(".text-danger").remove();
				$(".required-warn").removeClass("required-warn");
			}

			//得到全部的客户
			function getAllCust() {
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/getCustomerList/list',
					data: angular.toJson({})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.allCustList = result.data;
						$scope.allCustList2 = [];
						angular.forEach($scope.allCustList, function(v, k) {
							var no = v.custNo;
							var name = v.custNameCN;
							var retio = v.creditRatio;
							var arr = {
								custNo: no,
								custNameCN: name,
								creditRatio: retio,
							}
							$scope.allCustList2.push(arr);
							var date_now = new Date(parseInt(("/Date(" + v.ratiTime + ")/").substr(6, 13))).toLocaleDateString();
							date_now = date_now.split('/')[0] + '-' + ((date_now.split('/')[1] - '10' < 0) ? '0' + date_now.split('/')[1] : date_now.split('/')[1]) + '-' + ((date_now.split('/')[2] - '10' < 0) ? '0' + date_now.split('/')[2] : date_now.split('/')[2]);
							v.ratiTime = date_now;
						})
						//回显方案所有信息
						if(tempId == "") {
							getAllSchemeInformation();
						}
						//回显方案所有信息
						if(tempId != "") {
							getAllSchemeInforma();
						}
						if(result.data == null) {
							alert("提示", "未查询到此客户，请通知信贷系统添加客户");
							$rootScope.$applyAsync();
						}
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			//点击查询客户的页面的选择，把查询的客户显示到用信页面
			$scope.submitCustUseLetter = function() {
				//				var bdc = new BDContext();
				//				bdc.invokeInf("khztsxxxcx", "khztsxxxcx", ['101'], function(errorcode, data) {
				//					$scope.recordUseLetter.creditLinesList = data;
				if($scope.recordUseLetter != null) {
					//调用接口
					/*var bdc = new BDContext();
					bdc.invokeInf("khztsxxxcx", "khztsxxxcx", [$scope.recordUseLetter.custNo], function(errorcode, data) {
					$scope.recordUseLetter.creditLinesList = data;
				});*/

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
					$scope.recordUseLetter.creditLinesList = dbc;
					var keepGoing = true;
					angular.forEach($scope.proTemm.customersList, function(v, index) {
						if(keepGoing) {
							if(v.custNo == "") {
								$scope.proTemm.customersList.splice(index, 1, $scope.recordUseLetter);
								keepGoing = false;
							}
						}
					});
					angular.forEach($scope.rentFacList, function(v, k) {
						angular.forEach(v.customersList, function(v1, index) {
							if(keepGoing) {
								if(v1.custNo == "") {
									v.customersList.splice(index, 1, $scope.recordUseLetter);
									keepGoing = false;
								}
							}
						});
					});
				} else {
					alert("提示", "您还未查询客户，请查询后进行选择");
				}
				//				});

			}

			//点击添加用信主体
			$scope.addCust = function(p) {
				angular.forEach($scope.rentFacList, function(v, k) {
					if(v.code == p.code) {
						//先向产品中插入一个空的客户对象
						var cus = {
							custNo: '',
							custNameCN: '',
							creditRatio: '100',
							custScale: '',
							groupNumber: '',
							custNameEN: '',
							groupName: '',
							custAddressCN: '',
							mainBusiness: '',
							custAddressEN: '',
							custCountry: '',
							custBusinessUnit: '',
							creditRating: '',
							custRegistrastionType: '',
							ratiTime: '',
							custRatingCreditType: '',
							custManager: '',
							creditLinesList: []
						};
						var a = {
							amountType: "1101",
							creditLineName: "最高综合授信额度",
							creditNo: "1101",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var b = {
							amountType: "1102",
							creditLineName: "流动资金类贷款额度",
							creditNo: "1102",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var c = {
							amountType: "1103",
							creditLineName: "贸易融资额度",
							creditNo: "1103",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var d = {
							amountType: "1104",
							creditLineName: "融资类保函额度",
							creditNo: "1104",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var e = {
							amountType: "1105",
							creditLineName: "非融资类保函额度",
							creditNo: "1105",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var f = {
							amountType: "1106",
							creditLineName: "最高保证额度",
							creditNo: "1106",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var g = {
							amountType: "1107",
							creditLineName: "公开授信额度项下业务的免担保授信额度",
							creditNo: "1107",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var h = {
							amountType: "1108",
							creditLineName: "非公开授信额度项下业务的免担保授信额度",
							creditNo: "1108",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var i = {
							amountType: "1109",
							creditLineName: "融资类保函免反担保额度",
							creditNo: "1109",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var j = {
							amountType: "1110",
							creditLineName: "船舶预付款退款保函免反担保额度",
							creditNo: "1110",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var k = {
							amountType: "1111",
							creditLineName: "其他非融资类保函免反担保额度",
							creditNo: "1111",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						var l = {
							amountType: "1112",
							creditLineName: "专项授信（或其他类型授信额度）",
							creditNo: "1112",
							totalAmount: "",
							usedAmount: "",
							availableBalance: "",
							startDate: "",
							maturityDate: ""
						};
						cus.creditLinesList.push(a);
						cus.creditLinesList.push(b);
						cus.creditLinesList.push(c);
						cus.creditLinesList.push(d);
						cus.creditLinesList.push(e);
						cus.creditLinesList.push(f);
						cus.creditLinesList.push(g);
						cus.creditLinesList.push(h);
						cus.creditLinesList.push(i);
						cus.creditLinesList.push(j);
						cus.creditLinesList.push(k);
						cus.creditLinesList.push(l);
						v.customersList.push(cus);
					}
					/*var names = document.getElementsByName('adduser');
					names[0].value = "yes";
					adduser(names[0]);*/
				});
				$timeout(function() {
					initBlur();
				}, 1000);
			}

			//删除选择的客户
			$scope.deletePro = function(p, indx) {
				angular.forEach($scope.rentFacList, function(v, k) {
					if(v.code == p.code) {
						v.customersList.splice(indx, 1);
					}
				});
			}

			//点击详细信息，展示客户详情
			$scope.checkCust = function(item) {
				$scope.recordUseLetter = [];
				//展示对应的客户详情	
				$scope.recordUseLetter = item;
				$scope.custScale = "无";
				$scope.groupNumber = "无";
				$scope.custNameEN = "无";
				$scope.groupName = "无";
				$scope.custAddressCN = "无";
				$scope.mainBusiness = "无";
				$scope.custAddressEN = "无";
				$scope.custCountry = "无";
				$scope.custBusinessUnit = "无";
				$scope.creditRating = "无";
				$scope.custRegistrastionType = "无";
				$scope.ratiTime = "无";
				$scope.custRatingCreditType = "无";
				$scope.custManager = "无";
				//cu是0说明是查询客户，展示关闭按钮
				$scope.cu = 11045;
				$scope.title = "用信主体及授信方案";
				$scope.custoNum = null;
			}

			//给风险分析的授信方案
			$scope.slectShouXin = function(ss, index) {
				//与客户号的index区分
				var index = index + 'amountType';
				$scope.custUserLetter = [];
				angular.forEach($scope.rentFacList, function(v, k) {
					angular.forEach(v.customersList, function(v1, k1) {
						$scope.custUserLetter.push(v1);
					});
				});
				angular.forEach($scope.custUserLetter, function(v, k) {
					v.creditRating = '';
					v.ratiTime = '';
				});
				//样式控制 1101 为最高综合授信额度code
				if(ss == '1101') {
					angular.element(document.getElementById(index)).removeClass('hidden');
					//					<p id="{{$index}}" class="hidden" style="color: #a94442;">仅占用最高综合授信额度</p>
				} else {
					angular.element(document.getElementById(index)).addClass('hidden');
				}
			}
			/*
			 * 动态模糊搜索框部分
			 */

			//获取搜索结果数据

			//选中事件
			$scope.onSelect = function(arg1, inde) {
				//判断是否客户重复
				var hh = 0;
				angular.forEach($scope.rentFacList, function(v, k) {
					angular.forEach(v.customersList, function(v1, index) {
						if(v1.custNo == arg1.custNo && inde != index) {
							alert("提示", "此产品已占用这个客户，请重新选择");
							//							angular.element(this).parent.find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
							$scope.custoNum = "";
							hh = 1;
							return false;
						}
					});
				});
				if(hh == 0) {
					angular.forEach($scope.allCustList, function(v, k) {
						if(arg1.custNo == v.custNo) {
							arg1 = v;
							arg1.creditRatio = 100;
						}
					})
					$scope.recordUseLetter = arg1;
					var dbc = [];
					var a = {
						amountType: "1101",
						creditLineName: "最高综合授信额度",
						creditNo: "1101",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var b = {
						amountType: "1102",
						creditLineName: "流动资金类贷款额度",
						creditNo: "1102",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var c = {
						amountType: "1103",
						creditLineName: "贸易融资额度",
						creditNo: "1103",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var d = {
						amountType: "1104",
						creditLineName: "融资类保函额度",
						creditNo: "1104",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var e = {
						amountType: "1105",
						creditLineName: "非融资类保函额度",
						creditNo: "1105",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var f = {
						amountType: "1106",
						creditLineName: "最高保证额度",
						creditNo: "1106",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var g = {
						amountType: "1107",
						creditLineName: "公开授信额度项下业务的免担保授信额度",
						creditNo: "1107",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var h = {
						amountType: "1108",
						creditLineName: "非公开授信额度项下业务的免担保授信额度",
						creditNo: "1108",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var i = {
						amountType: "1109",
						creditLineName: "融资类保函免反担保额度",
						creditNo: "1109",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var j = {
						amountType: "1110",
						creditLineName: "船舶预付款退款保函免反担保额度",
						creditNo: "1110",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var k = {
						amountType: "1111",
						creditLineName: "其他非融资类保函免反担保额度",
						creditNo: "1111",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					var l = {
						amountType: "1112",
						creditLineName: "专项授信（或其他类型授信额度）",
						creditNo: "1112",
						totalAmount: "",
						usedAmount: "",
						availableBalance: "",
						startDate: "",
						maturityDate: ""
					};
					dbc.push(a);
					dbc.push(b);
					dbc.push(c);
					dbc.push(d);
					dbc.push(e);
					dbc.push(f);
					dbc.push(g);
					dbc.push(h);
					dbc.push(i);
					dbc.push(j);
					dbc.push(k);
					dbc.push(l);
					$scope.recordUseLetter.creditLinesList = dbc;
					//把匹配的客户存入页面对应的客户里面
					angular.forEach($scope.rentFacList, function(v, k) {
						angular.forEach(v.customersList, function(v1, index) {
							if(inde == index) {
								v.customersList.splice(index, 1, $scope.recordUseLetter);
								return false;
							}
						});
					});
				}
			};
			//没有数据的情况
			$scope.hideNoResults = function(index) {
				$scope.noResults = false;
			};

			/*
			 * 以下是添加产品部分controller
			 */
			//定义两个数组，接受勾选的产品信息
			var proUseLetter1 = new Array();
			//标记
			var sign = null;
			//暂存添加产品传过来的对象
			$scope.custTem = null

			/*
			 * 产品组合部分
			 * 
			 * */
			//查询全部币种
			$timeout(function() {
				getCurrency();
			}, 1000);

			//保存勾选币种的集合
			var proMix = new Array();

			//可办理笔数限制选择限制，展示可办理笔数
			$scope.change8 = function(ap) {
				$scope.debtMain.tdwln = "";
				angular.element("#change8").parent().find("span").remove();
				angular.element("#change8").removeClass("required-warn");
				if(ap == 1 && ap != '') {
					$scope.show13 = true;
				} else {
					$scope.show13 = false;
				}

				$scope.custUserLetter = [];
				//给风险分析的客户评级赋值
				angular.forEach($scope.rentFacList, function(v, k) {
					angular.forEach(v.customersList, function(v1, k1) {
						$scope.custUserLetter.push(v1);
					});
				});
			}
			// function  keep() {
			// 	angular.forEach($scope.debtMain.singleBtch , function(sg) {
			// 		//处理样式控制 1101 为最高综合授信额度code
			// 		if(sg == 1 && sg != '') {
			// 			angular.element("#parentCode").attr("disabled","disabled");
			// 			angular.element("#change8").attr("disabled","disabled");
			// 			angular.element("#ltnopa").attr("disabled","disabled");
			// 		} else {
			// 			angular.element("#parentCode").removeAttr("disabled","true");
			// 			angular.element("#change8").removeAttr("disabled","true");
			// 			angular.element("#ltnopa").removeAttr("disabled","true");
			// 		}
			//
			// 		return false;
			// 	});
			// }
			//是否单笔单批 选择 是 否 时，展示可办理笔数限制，值为限制 不限制
			// $scope.changeSingle = function (sg) {
			// 	if(sg == 1 && sg != '') {
			// 		// $scope.showlimit = true;
			// 		$scope.debtMain.ls="1";
			// 		$scope.debtMain.ltnopa="1";
			// 		$scope.show13 = true;
			// 		$scope.debtMain.tdwln="1";
			// 		angular.element("#parentCode").attr("disabled","disabled");
			// 		angular.element("#change8").attr("disabled","disabled");
			// 		angular.element("#ltnopa").attr("disabled","disabled");
			// 	} else {
			// 		angular.element("#parentCode").removeAttr("disabled","true");
			// 		angular.element("#change8").removeAttr("disabled","true");
			// 		angular.element("#ltnopa").removeAttr("disabled","true");
			// 		// $scope.showlimit = true;
			// 		$scope.debtMain.ls="";
			// 		$scope.debtMain.ltnopa="0";
			// 		$scope.show13 = false;
			// 		$scope.debtMain.tdwln="";
			// 	}
			// }
			//方案辅助币种选择其他可选币种，展示其他可选币种
			$scope.changeCur = function(m) {
				if(m == 1) {
					$scope.showCurrency = true;
					$scope.currencyProMix = [];
				} else {
					$scope.showCurrency = false;
					$scope.currencyProMix = [];
				}
			}
			//方案费率类型选择优惠费率或者非优惠费率，展示方案费率形式
			$scope.change5 = function(sce) {
				angular.element("#change5").parent().find("span").remove();
				angular.element("#change8").removeClass("required-warn");
				if(sce == 2) {
					$scope.show8 = true;
					$scope.show11 = true;
					$scope.show14 = 2;
					$scope.debtMain.programRateDiscount = "";
				}
				if(sce == 1) {
					$scope.show8 = true;
					$scope.show11 = false;
					$scope.show12 = false;
					$scope.show14 = 1;
					$scope.debtMain.programRateDiscount = "0";
				}
				if(sce == 0) {
					$scope.show8 = false;
					$scope.show9 = false;
					$scope.show10 = false;
					$scope.show11 = false;
					$scope.show12 = false;
					$scope.show14 = 0;
					$scope.debtMain.programRateDiscount = "";
				}
			}
			//点击方案费率形式，展开方案费率或者方案费率范围
			$scope.change6 = function(sch) {
				$scope.debtMain.packageRate = "";
				$scope.debtMain.rateRangeMix = "0";
				$scope.debtMain.rateRangeMax = "100";
				angular.element("#change6").parent().find("span").remove();
				angular.element("#change6").removeClass("required-warn");
				angular.element("#rateRangeMix").parent().find("span").remove();
				angular.element("#rateRangeMix").removeClass("required-warn");
				angular.element("#rateRangeMax").parent().find("span").remove();
				angular.element("#rateRangeMax").removeClass("required-warn");
				if(sch == 0 && sch != '') {
					$scope.show9 = true;
					$scope.show10 = false;
				} else if(sch == 1) {
					$scope.show9 = false;
					$scope.show10 = true;
				} else {
					$scope.show9 = false;
					$scope.show10 = false;
				}
			}

			$scope.change17 = function() {
				var v1 = $scope.debtMain.rateRangeMix;
				var v2 = $scope.debtMain.rateRangeMax;
				if(v1 != '' || v2 != '') {
					$('#rateRangeMax').removeClass('required');
					$('#rateRangeMax').removeClass('required-warn');

					$('#rateRangeMix').removeClass('required');
					$('#rateRangeMix').removeClass('required-warn');

					$('#rateRangeMix').parent().find("span").remove();
					$('#rateRangeMax').parent().find("span").remove();
				} else {
					$('#rateRangeMix').addClass('required');
					$('#rateRangeMax').addClass('required');
				}
			}
			//点击是否经审批时，展开审批机构
			$scope.change7 = function(app) {
				$scope.debtMain.raaa = "";
				if(app == 1 && app != '') {
					$scope.show12 = true;
				} else {
					$scope.show12 = false;
				}
			}
			//是否存在方案费率折扣
			$scope.change9 = function(app) {
				if(app == 1 && app != '') {
					$scope.show14 = true;
				} else {
					$scope.show14 = false;
				}
			}
			//是否是否是政策性
			$scope.change10 = function(app) {
				if(app == 1 && app != '') {
					$scope.showPolicy = true;
					$scope.debtMain.policyDescription = "";
				} else {
					$scope.showPolicy = false;
					$scope.debtMain.policyDescription = "";
				}
			}

			//查询全部币种
			function getCurrency() {
				//得到全部币种
				$.ajax({
					type: "POST",
					url: "/eximbank-club/debtSummary/chargeType/query",
					data: angular.toJson({})
				}).then(function(result) {
					$scope.currencyList = result.data;
					angular.forEach($scope.currencyList, function(v, k) {
						var tt = null;
						tt = v.monCode + "   " + v.codeName;
						v.createBy = tt;
					});
				})

			}

			//点击可选其他币种的+，选择辅助币种
			$scope.selectCurrency = function() {
				//得到全部币种
				//$scope.currencyList=getCurrencyDb();
				// $scope.title = "其他可选币种";
				// var names = document.getElementsByName('dede');
				// names[0].value = "yes";
				// dedede(names[0]);
			}

			//全选币种
			$scope.onCheckedCurrency = function(n) {
				angular.forEach($scope.currencyList, function(v, k) {
					v.checked = n;
				});
			}

			//获取勾选的币种
			function getCheckCurrency() {
				proMix = [];
				angular.forEach($scope.currencyList, function(v, k) {
					if(v.checked == true) {
						proMix.push(v);
					}
				});
				return proMix;
			}

			//点击弹窗的币种的保存，把勾选币种展示到产品组合中
			$scope.submitCurency = function() {
				//得到勾选的币种
				$scope.currencyProMix = getCheckCurrency();
			}

			/*
			 * 风险分析部分
			 * 
			 * */
			//定义一个集合，存储风险分析中的产品
			var proRisk = new Array();

			//默认担保合同信息不显示
			$scope.showHigh = false;
			$scope.showSoso = false;

			//定义保存担保信息的集合
			//$scope.bizGuaranteeInfoList = [];

			//初始化的一个空的担保信息
			/*			var recordGuarantee = {
							typePoint: '',
							guaranteeContractType: '',
							guaranteeContractType: '',
							betInformationList: []
						};*/
			//初始化的一个空的押品信息
			/*var betInformation = {
				pledNo: '',
				guarmartType: '',
				productInformationDescription: '',
			};*/
			/*recordGuarantee.betInformationList.push(betInformation);
			$scope.bizGuaranteeInfoList.push(recordGuarantee);*/

			//点击新增一套担保信息
			$scope.addRent = function() {
				// var popupId = Math.floor(Math.random() * 10000);
				// var index = popupId;
				//初始化的一个空的担保信息
				var recordGuarantee = {
					typePoint: '',
					guaranteeContractType: '',
					guaranteeContractType: '',
					betInformationList: [],
					//定义一个数组，接受担保合同类型的数据
					contractTypeList: []
				};
				//初始化的一个空的押品信息
				var betInformation = {
					pledNo: '',
					guarmartType: '',
					productInformationDescription: '',
					//定义一个数组，接受担保方式类型的数据
					mortgageList: []
				};
				recordGuarantee.betInformationList.push(betInformation);
				$scope.bizGuaranteeInfoList.push(recordGuarantee);
				var ind = $scope.bizGuaranteeInfoList.length - 2;
				// $("html,body").animate({scrollTop: $("#"+ind).offset().top}, 500);
				$("#" + ind)[0].scrollIntoView();
				// document.querySelector("#"+ind).scrollIntoView();
				// var ind = document.getElementsByName("typePoint");
				// angular.element(ind).attr("id",popupId);
				$.growl.notice({
					title: "提示",
					message: "新增担保信息成功",
					size: "large"
				});
				$timeout(function() {
					initBlur();
				}, 1000);
			}

			//初始化担保类型分
			getGuaranteeInfo();
			//初始化担保类型分
			function getGuaranteeInfo() {
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/getGuaranteeInfo/list',
					data: angular.toJson({})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.guaranteeInfoList = result.data;
						if(result.data == null) {
							alert("提示", "担保类型分未查到，请联系管理员！");
							$rootScope.$applyAsync();
						}
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			//担保合同编号除了信用是隐藏，其他都是展示
			$scope.showContackNum = true;

			//加载担保合同类型，判断是否出押品信息
			$scope.selectContract = function(eve, y) {

				//点击请选择，担保合同类型要制空
				if(eve == "") {
					angular.forEach($scope.rentFacList, function(v, index) {
						if(y == index) {
							v.guaranteeContractType = "";
						}
					});
				}

				//到到担保合同类型/占用额度类型
				getContractTypeList(eve, y);

				//假如eve是1，说明是抵押,假如eve是2，说明质押，假如eve是3，说明保证
				if(eve == "1" || eve == "2" || eve == "3") {
					if(eve == "1" || eve == "2") {
						$scope.showBet = true;
						//得到押品编号
						getRemandNum(y);
					} else {
						$scope.showBet = false;
					}

					//得到担保方式类型
					getMortgageList(eve, y);

				}

				//假如eve是4时,说明是信用,担保人默认是第一个用信主体
				if(eve == "4") {
					$scope.temCuss = [];
					$scope.showHigh = false;
					$scope.showSoso = true;
					$scope.showContackNum = false;
					//清空担保合同数据
					angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
						if(y == index) {
							v.warrantyContractNumber = "";
							v.guarantor = "";
							v.guarantorCustId = "";
							v.currencyGuarantee = "";
							v.guaranteeAmount = "";
						}
					});

					//担保默认为第一个用信主体
					angular.forEach($scope.rentFacList, function(v, k) {
						angular.forEach(v.customersList, function(v1, index) {
							if(index == 0) {
								$scope.temCuss = v1;
								return false;
							}
						});
					});
					angular.forEach($scope.bizGuaranteeInfoList, function(v5, k5) {
						if(v5.guarantor == null || v5.guarantor == "") {
							v5.guarantor = $scope.temCuss.custNameCN;
							v5.guarantorCustId = $scope.temCuss.custNo;
							return false;
						}
					});

				}

				//假如eve是5时,说明是其他
				if(eve == "5") {
					//清空担保合同数据
					angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
						if(y == index) {
							v.guaranteeContractType = "";
							v.warrantyContractNumber = "";
							v.guarantor = "";
							v.guarantorCustId = "";
							v.currencyGuarantee = "";
							v.guaranteeAmount = "";
						}
					});
					$scope.showHigh = false;
					$scope.showSoso = true;
				}

				//定义一个临时数组，来判断担保类型总的显示
				var guarTem = [];
				angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
					if(v.typePoint == "1") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "2") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "3") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "4") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "5") {
						guarTem.push(v.typePoint);
					}
				})

				//抵押 担保类型总
				if(guarTem.indexOf("1") > -1) {
					$scope.rent1 = true;
				} else {
					$scope.rent1 = false;
				}
				//质押 担保类型总	
				if(guarTem.indexOf("2") > -1) {
					$scope.rent2 = true;
				} else {
					$scope.rent2 = false;
				}
				//保证 担保类型总
				if(guarTem.indexOf("3") > -1) {
					$scope.rent3 = true;
				} else {
					$scope.rent3 = false;
				}
				//信用   担保类型总
				if(guarTem.indexOf("4") > -1) {
					$scope.rent4 = true;
				} else {
					$scope.rent4 = false;
				}
				//其他
				if(guarTem.indexOf("5") > -1) {
					$scope.rent5 = true;
				} else {
					$scope.rent5 = false;
				}
			}

			//根据担保合同类型/占用额度类型的选的一般还是最高，判断担保合同编号的数据来源
			/*$scope.selectTypePoint = function(ev) {
				//假如ev是11,21，23，31，说明是一般
				if(ev == "11" || ev == "21" || ev == "23" || ev == "31") {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
						if(v.warrantyContractNumber != "") {
							v.warrantyContractNumber = "";
							v.guarantor = "";
							v.guarantorCustId = "";
							v.currencyGuarantee = "CNY";
							v.guaranteeAmount = "";
						}
					});
					$scope.showHigh = false;
					$scope.showSoso = true;
				}
				//假如ev是32,12，22，24，说明是最高
				if(ev == "32" || ev == "12" || ev == "22" || ev == "24") {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
						if(v.warrantyContractNumber != "") {
							v.warrantyContractNumber = "";
							v.guarantor = "";
							v.guarantorCustId = "";
							v.currencyGuarantee = "";
							v.guaranteeAmount = "";
						}
					});
					$scope.showHigh = true;
					$scope.showSoso = false;
				}
			}*/

			//根据担保类型分得到担保合同类型/占用额度类型
			function getContractTypeList(eve, y) {
				if(eve != 5) {
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/getContractTypeList/list',
						data: angular.toJson({
							"eve": eve
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							//							$scope.contractTypeList = result.data;
							angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
								if(y == index) {
									v.contractTypeList = result.data;
								}
							});
							if(result.data == null) {
								alert("提示", "担保合同类型/占用额度类型查询出问题，请联系管理员！");
								$rootScope.$applyAsync();
							}
						} else {
							$scope.msg = result.msg;
						}
						$rootScope.$applyAsync();
					});

				}
			}

			//得到抵押或者质押的押品子集
			function getMortgageList(eve, y) {
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/getMortgageList/list',
					data: angular.toJson({
						"eve": eve
					})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						//						$scope.mortgageList = result.data;
						angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
							if(y == index) {
								v.mortgageList = result.data;
							}
						});

						if(result.data == null) {
							alert("提示", "担保方式类型查询出现问题，请联系管理员！");
							$rootScope.$applyAsync();
						}
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});
			}

			//得到押品编号
			function getRemandNum(ind) {
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/getRemandNum/list',
					data: angular.toJson({})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
							angular.forEach(v.betInformationList, function(v1, k1) {
								if(ind == index) {
									if(v1.pledNo == null || v1.pledNo == '') {
										v1.pledNo = result.data.remandNum;
										return false;
									}
								}
							})
						})
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});

			}

			//点击删除担保信息
			$scope.deleteRent = function(indx) {
				$scope.bizGuaranteeInfoList.splice(indx, 1);
				//定义一个临时数组，来判断担保类型总的显示
				var guarTem = [];
				angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
					if(v.typePoint == "1") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "2") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "3") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "4") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "5") {
						guarTem.push(v.typePoint);
					}
				})

				//抵押 担保类型总
				if(guarTem.indexOf("1") > -1) {
					$scope.rent1 = true;
				} else {
					$scope.rent1 = false;
				}
				//质押 担保类型总	
				if(guarTem.indexOf("2") > -1) {
					$scope.rent2 = true;
				} else {
					$scope.rent2 = false;
				}
				//保证 担保类型总
				if(guarTem.indexOf("3") > -1) {
					$scope.rent3 = true;
				} else {
					$scope.rent3 = false;
				}
				//信用   担保类型总
				if(guarTem.indexOf("4") > -1) {
					$scope.rent4 = true;
				} else {
					$scope.rent4 = false;
				}
				//其他
				if(guarTem.indexOf("5") > -1) {
					$scope.rent5 = true;
				} else {
					$scope.rent5 = false;
				}
			}

			//点击押品信息的新增
			$scope.addBet = function(ind) {
				angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
					if(ind == index) {
						//初始化的一个空的押品信息
						var betInformation = {
							pledNo: '',
							guarmartType: '',
							productInformationDescription: '',
						};
						v.betInformationList.push(betInformation);
					}
				});
				$timeout(function() {
					initBlur();
				}, 1000);
				//得到押品编号
				getRemandNum(ind);
			}

			//点击押品的删除
			$scope.deleteBet = function(ind, iner) {
				angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
					if(ind == index) {
						angular.forEach(v.betInformationList, function(v1, index1) {
							if(iner == index1) {
								v.betInformationList.splice(iner, 1);
							}
						});
					}
				});
			}

			//担保合同查询时，选中客户客户
			$scope.onSelectContackty = function(arg) {
				var tem = arg.custNo;
				var cc = [];
				var aa = {
					"custNo": "112",
					"custNameCN": "猎豹移动",
					"guaranteeContractTime": "2018-09-27 15:12:21",
					"securedCreditCurrency": "YSC",
					"securedCreditClaim": "1200",
					"guarantor": "张一山",
					"warrantyContractNumber": "741825",
					"BalanceSecuredClaim": "300",
					"guaranteeStartDate": "2018-09-27 15:12:21",
					"guaranteeEndDate": "2018-09-27 15:12:21",
					"guaranteeContract": "最高额抵押合同",
					"checked": "false",
					"guarantorCustId": "741852",
					"guaranteeMenthod": "多人分保",
					"remarks": "中戏"
				};
				var bb = {
					"custNo": "112",
					"custNameCN": "猎豹移动",
					"guaranteeContractTime": "2018-09-27 15:12:21",
					"securedCreditCurrency": "KKJ",
					"securedCreditClaim": "2200",
					"guarantor": "杨紫",
					"warrantyContractNumber": "124558",
					"BalanceSecuredClaim": "396",
					"guaranteeStartDate": "2018-09-27 15:12:21",
					"guaranteeEndDate": "2018-09-27 15:12:21",
					"guaranteeContract": "最高额动产质押合同",
					"checked": "false",
					"guarantorCustId": "987456",
					"guaranteeMenthod": "多人保证",
					"remarks": "中戏"
				};
				cc.push(aa);
				cc.push(bb);
				$scope.contacktyList = cc;
				//接口查询信贷的最高额担保合同
				//				var bdc = new BDContext();
				//				bdc.invokeInf("zgedbhtcx", "zgedbhtcx", [tem], function(errorcode, data) {
				//					if(data== "119"){
				//						alert("此用户在信贷系统没有对应的担保合同，请核对信息！")
				//					}else{
				//						$scope.contacktyList = data;
				//					}
				//				})
			};

			//临时定义一个最高合同的index
			$scope.selectContrac = function(inn) {
				//清空原有的弹窗内容
				$scope.contckproposer = "";
				$scope.contacktyList = [];
				$scope.indexTemm = inn;
			}

			//判断弹窗里面的担保合同是否多选了
			$scope.checkedRepeat = function(item, ind) {
				$scope.temContack = item;
				/*var a = 0;
				angular.forEach($scope.contacktyList, function(v, index) {
					if(v.checked == true) {
						a++;
					}
				})
				if(a >= 2) {
					alert("提示", "担保合同信息只能选择勾选一个");
				}*/
				//弹框的担保合同赋值给页面
				/*angular.forEach($scope.contacktyList, function(v1, index1) {
					if(v1.checked == true) {
						$scope.temContack = v1;
					}
				})*/
				angular.forEach($scope.bizGuaranteeInfoList, function(v2, index) {
					if($scope.indexTemm == index) {
						v2.warrantyContractNumber = $scope.temContack.warrantyContractNumber;
						v2.guarantor = $scope.temContack.guarantor;
						v2.guarantorCustId = $scope.temContack.guarantorCustId;
						v2.currencyGuarantee = $scope.temContack.securedCreditCurrency;
						v2.guaranteeAmount = $scope.temContack.securedCreditClaim;
						return false;
					}
				})

			};

			//判断弹窗担保合同是否什么都没有选择
			$scope.submitContack = function() {
				/*$scope.temContack = null;
				var b = 0;
				angular.forEach($scope.contacktyList, function(v, index) {
					if(v.checked == true) {
						b++;
					}
				})*/
				//				if(b == 0) {
				//					alert("提示", "请至少选择一个担保合同信息");
				//					return false;
				//				}
				//弹框的担保合同赋值给页面
				/*angular.forEach($scope.contacktyList, function(v1, index1) {
					if(v1.checked == true) {
						$scope.temContack = v1;
					}
				})
				angular.forEach($scope.bizGuaranteeInfoList, function(v2, index2) {
					if(v2.warrantyContractNumber == null || v2.warrantyContractNumber == "") {
						v2.warrantyContractNumber = $scope.temContack.warrantyContractNumber;
						v2.guarantor = $scope.temContack.guarantor;
						v2.guarantorCustId = $scope.temContack.guarantorCustId;
						v2.currencyGuarantee = $scope.temContack.securedCreditCurrency;
						v2.guaranteeAmount = $scope.temContack.securedCreditClaim;
						return false;
					}
				})*/

			}

			/*
			 * 管理信息部分
			 * 
			 * */
			//管理信息上一项

			/*
			 * 专有信息部分
			 * 
			 * */
			//专有信息上一项
			$scope.beforeManageInformation = function() {
				//table页面跳转
				$scope.tab = 5;
			}

			//专有信息的重置
			$scope.resetManageInformation = function() {

			}

			//担保人选中事件
			$scope.onSelectGuarantor = function(arg1, ind) {
				angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
					if(ind == index) {
						v.guarantor = arg1.custNameCN;
						v.guarantorCustId = arg1.custNo;
					}
				});
			};

			$scope.custName = null;
			$scope.custPin = null;

			//得到国别背景
			getBackground('countryBack')

			//承租人选中
			$scope.onSelectCustName = function(arg) {
				//承租人名称
				$scope.custName = arg.custNameCN;
				//承租人客户号
				angular.forEach($scope.rentFacList, function(v, k) {
					v.custNo = arg.custNo;
				});
				//承租人客户评级
				$scope.custPin = arg.creditRating;
			};

			//得到国别背景
			function getBackground(type) {
				$.ajax({
					type: "POST",
					url: "/eximbank-club/dic/read/list",
					data: angular.toJson({
						'type': type
					})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.backgroundList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				})

			}

			$scope.proposerJiaoYan = function(a) {
				switch(a) {
					case 1:
						if($scope.debtMain.proposer == null || $scope.debtMain.proposer == '') {
							angular.element("#proposer1").parent().find("span").remove();
							angular.element("#proposer1").parent().append('<span class="text-danger">' + "请输入申请人名称或者申请人客户号查询" + '</span>');
							count += 1;
						} else {
							angular.element("#proposer1").parent().find("span").remove();
							angular.element("#proposer1").parent().append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
						}
						break;
					case 2:
						angular.forEach($scope.rentFacList, function(v, k) {
							angular.forEach(v.customersList, function(v1, k1) {
								if(v1.custNo == null || v1.custNo == '') {
									angular.element("#proposer2").parent().find("span").remove();
									angular.element("#proposer2").parent().append('<span class="text-danger">' + "请输入申请人名称或者申请人客户号查询" + '</span>');
									count += 1;
								} else {
									angular.element("#proposer2").parent().find("span").remove();
									angular.element("#proposer2").parent().append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
								}

							});
						});
						break;
					case 3:
						if($scope.recordGuarantee.guarantor == null || $scope.recordGuarantee.guarantor == '') {
							angular.element("#proposer3").parent().find("span").remove();
							angular.element("#proposer3").parent().append('<span class="text-danger">' + "请输入申请人名称或者申请人客户号查询" + '</span>');
							count += 1;
						} else {
							angular.element("#proposer3").parent().find("span").remove();
							angular.element("#proposer3").parent().append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
						}
						break;
					case 4:
						$scope.custPin = "";
						if($scope.custName == null || $scope.custName == '') {
							//							angular.element("#proposer4").parent().find("span").remove();
							//							angular.element("#proposer4").parent().append('<span class="text-danger">' + "承租人（债务人）名称或者申请人客户号查询" + '</span>');
							count += 1;
						} else {
							angular.element("#proposer4").parent().find("span").remove();
							angular.element("#proposer4").parent().append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
						}
						break;
					case 5:
						if($scope.contckproposer == null || $scope.contckproposer == '') {
							angular.element("#proposer5").parent().find("span").remove();
							angular.element("#proposer5").parent().append('<span class="text-danger">' + "担保人名称或者担保人客户号查询" + '</span>');
							count += 1;
						} else {
							angular.element("#proposer5").parent().find("span").remove();
							angular.element("#proposer5").parent().append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
						}
						break;
				}
			}

			//方案修订的提交
			$scope.submitDebt = function() {
				confirm("提示", "请确认币种、金额信息，提交后不允许修改", function(flag) {
					if(flag) {
						var t = validateForm(5).valueOf();
						if(!t) {
							$.growl.error({
								title: "提醒",
								message: "存在未填项！",
								size: "large"
							});
							return false;
						}
						angular.element('#submitDebt').attr("disabled", "disabled");
						var promise = $CanvasService.createCanvas(angular.element('.canvas')[0], "A", angular.element('.canvasNum').find('.active').attr("num"), debtCode, null, null, null);
						promise.then(function(data) {

						}, function(data) {
							console.log("创建快照失败 error data ==" + data);
						}, function(data) {});
						//处理项目名称
						if(!$scope.debtMain.projectName) {
							var proposer = $scope.debtMain.proposer;
							proposer = proposer + $scope.debtMain.solutionAmount;
							angular.forEach($scope.rentFacList, function(v, k) {
								proposer = proposer + v.name;
							});
							$scope.debtMain.projectName = proposer + "项目";
						}

						//处理经办机构
						//				$scope.debtMain.institutionCode = $scope.deptList.parentCode;

						//把柜员的id存入概要表中
						//				$scope.debtMain.bankTellerId = $scope.userList.id;
						//把债项概要编号存入债项概要表
						$scope.debtMain.debtCode = debtCode;
						//把债项概要编号存入担保信息表表
						angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
							v.debtCode = debtCode;
						});
						//把其他可选币种存入到debtMain中
						var currentTem = [];
						angular.forEach($scope.currencyProMix, function(v, k) {
							currentTem += v.monCode + ",";
						});
						$scope.debtMain.oc = currentTem;

						//处理行业投向
						var industryTo = document.getElementsByName("industryTo")[0].value;
						//				industryTo = angular.element(industryTo).value;
						angular.forEach($scope.rentFacList, function(v, k) {
							v.industryTo = industryTo;
							return false;
						});

						//处理方案金额逗号
						if($scope.debtMain.mpc != 'JPY') {
							var slat = $('#solutionAmount').val();
						} else {
							var slat = $('#solutionAmountjpy').val();
						}
						$scope.debtMain.solutionAmount = slat.replace(/,/g, '');
						//				if($scope.debtMain.mpc!='JPY'){
						//				$scope.debtMain.solutionAmount =toDecimal($scope.debtMain.solutionAmount);
						//				}
						//处理担保金额逗号

						angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
							if(v.currencyGuarantee != 'JPY') {
								var slat1 = v.guaranteeAmount;
							} else {
								var slat1 = v.guaranteeAmount;
							}
							v.guaranteeAmount = slat1.toString().replace(/,/g, '');
						});

						//处理债项主表id
						$scope.debtMain.id = $scope.debtMain.id_;
						//处理担保信息id
						angular.forEach($scope.bizGuaranteeInfoList, function(v1, k1) {
							v1.id = v1.id_;
							angular.forEach(v1.betInformationList, function(v2, k2) {
								v2.id = v2.id_;
							});
						});

						$.ajax({
							type: 'POST',
							url: '/eximbank-club/debtSummary/historyDebtSave/list',
							data: angular.toJson({
								'debtCode': debtCode,
								//	'proList': $scope.proList,
								'debtMain': $scope.debtMain,
								'rentFacList': $scope.rentFacList,
								'bizGuaranteeInfoList': $scope.bizGuaranteeInfoList
							})
						}).then(function(result) {
							$scope.loading = false;
							if(result.httpCode == 200) {
								$.growl.notice({
									title: "提醒",
									message: "保存成功！",
									size: "large"
								});
								$.ajax({
									type: 'POST',
									url: '/eximbank-club/temStorage/delTemp',
									data: angular.toJson({
										'debtCode': debtCode
									})
								});

								$timeout(function() {
									//							$state.go('main.biz.homepage');
									if(angular.element("a[ui-sref='main.sys.historyDebt.list']").parent().parent().parent().hasClass('active')) {
										angular.element("a[ui-sref='main.sys.historyDebt.list']").trigger('click');
									} else {
										angular.element("a[ui-sref='main.sys.historyDebt.list']").trigger('click');
										angular.element("a[ui-sref='main.sys.historyDebt.list']").parent().parent().prev("a").trigger('click');
									}
								}, 1000);
							} else {
								$scope.msg = result.msg;
								console.log($scope.msg);
								alert("提示", "业务信息提交失败，请稍后再试!");
								angular.element('#submitDebt').removeAttr("disabled");
							}
							$rootScope.$applyAsync();
						});
					}
				})
			}

			//点击暂存
			$scope.submitTemStorage = function() {
				//处理项目名称
				if(!$scope.debtMain.projectName) {
					var proposer = $scope.debtMain.proposer;
					if($scope.debtMain.solutionAmount == undefined) {
						proposer = proposer + "0.00";
					} else {
						proposer = proposer + $scope.debtMain.solutionAmount;
					}
					angular.forEach($scope.rentFacList, function(v, k) {
						proposer = proposer + v.name;
					});
					$scope.debtMain.projectName = proposer + "项目";
				}

				//处理暂存按钮
				angular.element('#submitTemStorage').attr("disabled", "disabled");

				//添加经办机构
				//$scope.debtMain.institutionCode = $scope.deptList.code;

				//把柜员的id存入概要表中
				//				$scope.debtMain.bankTellerId = $scope.userList.id;
				//把债项概要编号存入债项概要表
				$scope.debtMain.debtCode = debtCode;
				//把债项概要编号存入担保信息表表
				if($scope.bizGuaranteeInfoList != []) {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
						v.debtCode = debtCode;
					});
				}
				//把其他可选币种存入到debtMain中
				if($scope.currencyProMix != []) {
					var currentTem = [];
					angular.forEach($scope.currencyProMix, function(v, k) {
						currentTem += v.monCode + ",";
					});
					$scope.debtMain.oc = currentTem;
				}
				//处理行业投向
				var industryTo = document.getElementsByName("industryTo")[0].value;
				angular.forEach($scope.rentFacList, function(v, k) {
					v.industryTo = industryTo;
					return false;
				});

				//处理方案金额逗号
				if($scope.debtMain.mpc != 'JPY') {
					var slat = $('#solutionAmount').val();
				} else {
					var slat = $('#solutionAmountjpy').val();
				}
				$scope.debtMain.solutionAmount = slat.replace(/,/g, '');

				angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
					if(v.currencyGuarantee != 'JPY') {
						var slat = v.guaranteeAmount;
					} else {
						var slat = v.guaranteeAmount;
					}
					if(v.guaranteeAmount) {
						v.guaranteeAmount = slat.replace(/,/g, '');
						console.log(v.guaranteeAmount);
					}

				});

				if(tempId != "") {
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/temStorage/saveScheme/list',
						data: angular.toJson({
							'remark': "2",
							'tempId': tempId,
							'debtCode': debtCode,
							'debtMain': $scope.debtMain,
							'rentFacList': $scope.rentFacList,
							'bizGuaranteeInfoList': $scope.bizGuaranteeInfoList
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							$.growl.notice({
								title: "提醒",
								message: "暂存成功！",
								size: "large"
							});
							//						alert("保存成功");
							$timeout(function() {
								//							$state.go('main.biz.record.schemeTemStorage');
								if(angular.element("a[ui-sref='main.sys.historySchemeTem.list']").parent().parent().parent().hasClass('active')) {
									angular.element("a[ui-sref='main.sys.historySchemeTem.list']").trigger('click');
								} else {
									angular.element("a[ui-sref='main.sys.historySchemeTem.list']").trigger('click');
									angular.element("a[ui-sref='main.sys.historySchemeTem.list']").parent().parent().prev("a").trigger('click');
								}
							}, 1000);
						} else {
							$scope.msg = result.msg;
							console.log($scope.msg);
							alert("提示", "暂存提交失败，请稍后再试!");
							angular.element('#submitTemStorage').removeAttr("disabled");
						}
						$rootScope.$applyAsync();
					});
				} else {
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/temStorage/saveScheme/list',
						data: angular.toJson({
							'remark': "2",
							'debtCode': debtCode,
							'debtMain': $scope.debtMain,
							'rentFacList': $scope.rentFacList,
							'bizGuaranteeInfoList': $scope.bizGuaranteeInfoList
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							$.growl.notice({
								title: "提醒",
								message: "暂存成功！",
								size: "large"
							});
							//						alert("保存成功");
							$timeout(function() {
								//							$state.go('main.biz.record.schemeTemStorage');
								if(angular.element("a[ui-sref='main.sys.historySchemeTem.list']").parent().parent().parent().hasClass('active')) {
									angular.element("a[ui-sref='main.sys.historySchemeTem.list']").trigger('click');
								} else {
									angular.element("a[ui-sref='main.sys.historySchemeTem.list']").trigger('click');
									angular.element("a[ui-sref='main.sys.historySchemeTem.list']").parent().parent().prev("a").trigger('click');
								}
							}, 1000);
						} else {
							$scope.msg = result.msg;
							console.log($scope.msg);
							alert("提示", "暂存提交失败，请稍后再试!");
							angular.element('#submitTemStorage').removeAttr("disabled");
						}
						$rootScope.$applyAsync();
					});
				}

			}

			//制保留2位小数，如：2，会在2后面补上00.即2.00      
			function toDecimal(x) {
				var f = parseFloat(x);
				if(isNaN(f)) {
					return false;
				}
				var f = Math.round(x * 100) / 100;
				var s = f.toString();
				var rs = s.indexOf('.');
				if(rs < 0) {
					rs = s.length;
					s += '.';
				}
				while(s.length <= rs + 2) {
					s += '0';
				}
				return s;
			}

		}
	]);
//屏蔽回车事件
$(document).keydown(function(event) {
	switch(event.keyCode) {
		case 13:
			return false;
	}
});
var count = 0;
//债项概要-是否单笔单批非空校验
function singleBtchJiaoYan(parent) {
	if($("#singleBtch").val() == '?' || $("#singleBtch").val() == '') {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请选择是否单笔单批" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//债项概要-申请人名称非空&查询结果为空校验
function proposerJiaoYan(parent) {
	//		$('#proposerNum').val(null);
	if($("#proposer").val() == null || $("#proposer").val().trim() == '') {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入申请人名称或者申请人客户号查询" + '</span>');
		count += 1;
	} else if($('#proposerNum').val() == null || $('#proposerNum').val().trim() == '') {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "查询结果不存在,请重新输入" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//债项概要-是否政策性非空校验
function policyJiaoYan(parent) {
	if($("#policy").val() == '? undefined:undefined ?' || $("#policy").val() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请选择是否政策性" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//债项概要-政策性属性描述非空校验
//只有"是否政策性"选择"是(0)"时进行非空校验
function policyDescriptionJiaoYan(parent) {
	if($("#policy").val() == '0') {
		if($("#note").val() == null || $("#note").val() == '') {
			parent.find("span").remove();
			parent.append('<span class="text-danger">' + "请输入政策性属性描述" + '</span>');
			count += 1;
		} else {
			parent.find("span").remove();
			parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
		}
	}
}

//失效日期是否小于生效日期的校验&非空校验
function date2JiaoYan(parent) {
	var pgExpiDate = $('#pgExpiDate').val();
	var pgEffectivDate = $('#pgEffectivDate').val();
	console.log(pgExpiDate);
	if(pgExpiDate == null || pgExpiDate == '') {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请选择方案失效日期" + '</span>');
		count += 1;
		return false;
	}
	if(pgExpiDate != null && pgEffectivDate != null) {
		var date1 = new Date(pgExpiDate).getTime();
		var date2 = new Date(pgEffectivDate).getTime();
		if(date2 >= date1) {
			parent.find("span").remove();
			$('#pgExpiDate').addClass('required-warn');
			$('#pgEffectivDate').parent().find("span").remove();
			parent.append('<span class="text-danger">' + "失效日期应大于生效日期,请重新选择" + '</span>');
			$('#pgExpiDate').val(null);
			count += 1;
		} else {
			parent.find("span").remove();
			parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
		}
	}
}
//方案生效日期是否大于方案失效日期的校验&非空校验
function date1JiaoYan(parent) {
	var pgExpiDate = $('#pgExpiDate').val();
	var pgEffectivDate = $('#pgEffectivDate').val();
	if(pgEffectivDate == null || pgEffectivDate == '') {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请选择方案生效日期" + '</span>');
		count += 1;
		return false;
	}
	if(pgExpiDate != null && pgEffectivDate != null) {
		var date1 = new Date(pgExpiDate).getTime();
		var date2 = new Date(pgEffectivDate).getTime();
		if(date2 >= date1) {
			parent.find("span").remove();
			$('#pgEffectivDate').addClass('required-warn');
			$('#pgExpiDate').parent().find("span").remove();
			parent.append('<span class="text-danger">' + "生效日期应小于失效日期,请重新选择" + '</span>');
			$('#pgEffectivDate').val(null);
			count += 1;
		} else {
			parent.find("span").remove();
			parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
		}
	}
}

//债项概要的下一页按钮
function test() {
	var count = 0;
	singleBtchJiaoYan($('#singleBtch').parent());
	proposerJiaoYan($('#proposer').parent());
	policyJiaoYan($('#policy').parent());
	policyDescriptionJiaoYan($('#note').parent());
	codeJiaoYan($('#code').parent());
	date2JiaoYan($('#pgExpiDate').parent());
	date1JiaoYan($('#pgEffectivDate').parent());
	parentCodeJiaoYan($('#parentCode').parent());
	if(count == 0) {
		return true;
	} else {
		alter("还有未填写的必输项");
		return false;
	}
}
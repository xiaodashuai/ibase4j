angular.module('app')
	.controller('debtSchemeController', ['$scope', '$rootScope', '$http', '$state', '$stateParams', '$timeout', '$CanvasService', "$q", "WebUploadService",'$filter',
		function($scope, $rootScope, $http, $state, $stateParams, $timeout, $CanvasService, $q, WebUploadService,$filter) {
			$("input").attr("autocomplete", "off"); //禁用
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
				if(tab == 5) {
					//					$scope.creditLineType = []; //信用额度类型 -- 用已和担保方式进行检验      
					//					$scope.guaranteeTypes = []; //担保方式类型Types of Guarantee
					if($scope.creditLineType.length != 0 && $scope.guaranteeTypes.length == 0) {
						alert("提示", "还未选择信用担保合同类型/占用额度类型!");
						return false;
					}
					if($scope.creditLineType.indexOf("0006") != -1 && $scope.maximumType.length == 0) {
						alert("提示", "还未选择最高保证担保合同类型/占用额度类型!");
						return false;
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
						var promise = $CanvasService.createCanvas(angular.element('.canvas')[0], "A", angular.element('.canvasNum').find('.active').attr("num"), $scope.debtCode, null, $scope, null);
						promise.then(function(data) {
							if(tab == 4 && $scope.webUploadStatus == 0) {
								var debtCode = $scope.debtCode.substring(0, 13); //截取13位，以便在方案修订和重新提交时的附件查看
								$scope.webUploadStatus++;
								WebUploadService.initFiles([$scope.debtCode,$scope.debtCode,$scope.debtCode], ["A", "A","A"], ["租金保理", "租金保理","租金保理"], ["Textupload", "Attachupload","Meetingupload"], $scope, $timeout);
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

			$timeout(function() {
				initBlur();
			}, 1000);

			/*
			 * 债项概要部分
			 * 
			 * */
			//加载提示
			$scope.loading = false;
			//初始化的时候全局展示
			$scope.show1 = true;

			//得到信贷员
			getUserList();
			//得到用户所属的机构
			getDeptList();
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
						angular.forEach($scope.rentFactorList, function(v, k) {
							v.checked = false;
							$scope.debtCode = v.debtCode;
						})
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				});
			}

			$scope.rentFacList = [];
			//获取点击的租金保理
			$scope.selelct = function(te) {
				$scope.rentFacList = [];
				angular.forEach($scope.rentFactorList, function(v, k) {

					if(te == v.code) {
						var cusList = [];
						cusList.push($scope.cctem);
						v.customersList = cusList;
						//设置专有信息的模糊匹配下拉框
						v.hiddenRecordProInform = false;
						$scope.rentFacList.push(v);
						setTimeout(function() {
							bulidMulitSelect("industryInve", "industryTo", "A0", "005");
							initBlur();
						}, 1000);
					}
				});

				//调用信贷接口
				var bdc = new BDContext();
				bdc.invokeInf("khztsxxxcx", "khztsxxxcx", [$scope.cctem.custNo], function(errorcode, data) {
					var temCredit = null;
					angular.forEach(data, function(v, index) {
						if(v.RspCode == '000000' && index == 0) {
							data.splice(index, 1);
							temCredit = '11';
							var index = '0' + 'amountType';
							$('#' + index).parent().find("p").remove();
						}
						if(temCredit == null) {
							//接口调用失败
							var index = '0' + 'amountType';
							var errorMsg = '该客户的授信信息接口调用失败，请重新选择客户！';
							$('#' + index).addClass("required-warn");
							if($('#' + index).parent().find("p").hasClass("text-danger")) {
								$('#' + index).parent().find(".text-danger").text(errorMsg);
							} else {
								$('#' + index).parent().append('<p class="text-danger">' + errorMsg + '</p>');
							}
							return false;
						}

						//接口调用成功
						if(temCredit == '11') {
							//接口下没有数据
							if(v.ErrorNo == '0') {
								var index = '0' + 'amountType';
								var errorMsg = '该客户下没有授信额度，请查证后再试！';
								$('#' + index).addClass("required-warn");
								if($('#' + index).parent().find("p").hasClass("text-danger")) {
									$('#' + index).parent().find(".text-danger").text(errorMsg);
								} else {
									$('#' + index).parent().append('<p class="text-danger">' + errorMsg + '</p>');
								}
								return false;
							}

						}

					});

					$scope.cctem.creditLinesList = data;
					$scope.$apply();
				});
				//调用信贷接口

			}

			$scope.hidden = true;
			//模糊匹配申请人
			$scope.queryProposer = function(v) {
				if(v == "") {
					$scope.hidden = true; //选择框是否隐藏
				}

				if(v.length >= 3) {
					var index="";
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/getCustomerList/list',
						data: angular.toJson({
							"custNo": v
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							$scope.allCustList2 = result.data;
							if($scope.allCustList2.length == 0) {
								var errorMsg = '查询不到该客户，请重新选择！';
								index='proposer1';
							$('#' + index).addClass("required-warn");
							if($('#' + index).parent().find("p").hasClass("text-danger")) {
								$('#' + index).parent().find(".text-danger").text(errorMsg);
							} else {
								$('#' + index).parent().append('<p class="text-danger">' + errorMsg + '</p>');
							}
								$scope.hidden = true;
								return false;
								/*$scope.debtMain.proposer = "";
								$scope.debtMain.proposerNum = "";*/
							}
							//下拉选展示
							if(index==""){
							$('#' + index).parent().find('p').remove();
							}
							$scope.hidden = false;
						} else {
							$scope.msg = result.msg;
						}
						$rootScope.$applyAsync();
					});
				}
				if($scope.debtMain.proposer == "") {
					$scope.debtMain.proposerNum = "";
				}
			}
			//申请人选中
			$scope.onSelectProposer = function(arg) {
				//申请人名称
				$scope.debtMain.proposer = arg[0].custNameCN;
				//申请人客户号
				$scope.debtMain.proposerNum = arg[0].custNo;

				$scope.recordUseLetter = arg[0];
				$scope.hidden = true;
				$scope.cctem = arg[0];

				//挡板
				/*var dbc = [];
				var a = {
					amountType: "0000",
					creditLineName: "最高综合授信额度",
					creditNo: "aaa1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var b = {
					amountType: "0001",
					creditLineName: "公开授信额度项下业务的免担保授信额度",
					creditNo: "bbb1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var c = {
					amountType: "0002",
					creditLineName: "其他非融资类保函免反担保授信额度",
					creditNo: "ccc1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var d = {
					amountType: "0003",
					creditLineName: "融资类保函免反担保授信额度",
					creditNo: "aaa1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var e = {
					amountType: "0004",
					creditLineName: "船舶出口预付款退款保函免反担保授信额度",
					creditNo: "bbb1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var f = {
					amountType: "0006",
					creditLineName: "最高保证授信额度",
					creditNo: "ccc1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var g = {
					amountType: "0007",
					creditLineName: "非公开授信额度项下业务的免担保授信额度",
					creditNo: "aaa1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var h = {
					amountType: "TP03",
					creditLineName: "贸易融资额度",
					creditNo: "bbb1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var i = {
					amountType: "TP04",
					creditLineName: "融资类保函额度",
					creditNo: "ccc1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var j = {
					amountType: "TP05",
					creditLineName: "非融资类保函额度",
					creditNo: "aaa1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var k = {
					amountType: "TP09",
					creditLineName: "流动资金类贷款额度",
					creditNo: "bbb1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var l = {
					amountType: "ZH01",
					creditLineName: "专项授信额度",
					creditNo: "ccc1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
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
				$scope.cctem.creditLinesList = dbc;*/
				//挡板

				//判断是否选了产品组合，是的话，就更新用信主体
				if($scope.rentFacList != []) {
					
					//调用信贷接口
				var bdc = new BDContext();
				bdc.invokeInf("khztsxxxcx", "khztsxxxcx", [$scope.cctem.custNo], function(errorcode, data) {
					var temCredit = null;
					angular.forEach(data, function(v, index) {
						if(v.RspCode == '000000' && index == 0) {
							data.splice(index, 1);
							temCredit = '11';
						}
						if(temCredit == null) {
							//接口调用失败
							var index = '0' + 'amountType';
							var errorMsg = '该客户的授信信息接口调用失败，请重新选择客户！';
							$('#' + index).addClass("required-warn");
							if($('#' + index).parent().find("p").hasClass("text-danger")) {
								$('#' + index).parent().find(".text-danger").text(errorMsg);
							} else {
								$('#' + index).parent().append('<p class="text-danger">' + errorMsg + '</p>');
							}
							return false;
						}

						//接口调用成功
						if(temCredit == '11') {
							//接口下没有数据
							if(v.ErrorNo == '0') {
								var index = '0' + 'amountType';
								var errorMsg = '该客户下没有授信额度，请查证后再试！';
								$('#' + index).addClass("required-warn");
								if($('#' + index).parent().find("p").hasClass("text-danger")) {
									$('#' + index).parent().find(".text-danger").text(errorMsg);
								} else {
									$('#' + index).parent().append('<p class="text-danger">' + errorMsg + '</p>');
								}
								return false;
							}

						}

					});

					$scope.cctem.creditLinesList = data;
					$scope.$apply();
				});
				//调用信贷接口
					
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

			//得到全部的客户
			getAllCust();

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
						})
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

			//点击添加用信主体
			$scope.addCust = function(p) {
				angular.forEach($scope.rentFacList, function(v, k) {
					if(v.code == p.code) {
						//先向产品中插入一个空的客户对象
						var cus = {
							custNo: '',
							custNameCN: '',
							creditRatio: '100',
							//客户的匹配下拉框
							hiddenRecordUseLetter: false
						};
						v.customersList.push(cus);
					}
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
						v.creditLinesId = '';
						$scope.creditLineType.splice(indx - 1, 1); //删除用信时，更新所选额度集合
					}
					$scope.slectShouXin();
					console.log($scope.creditLineType);
				});
			}

			//点击详细信息，展示客户详情
			$scope.checkCust = function(item) {
				//展示对应的客户详情	
				$scope.recordUseLetter = item;
				//cu是0说明是查询客户，展示关闭按钮
				$scope.cu = 11045;
				$scope.title = "用信主体及授信方案";
				$scope.custoNum = null;
			}

			$scope.creditLineType = []; //信用额度类型 -- 用来和担保方式进行检验   
			$scope.maximumType = []; //最高保证类型
			$scope.guaranteeTypes = []; //担保方式类型Types of Guarantee

			//给风险分析的授信方案
			$scope.slectShouXin = function(ss, index) {
				// 临时变量用来清空占用额度类型
				var UserLetterTem = 0;
				// 定义数组校验额度类型的选择规则  -- 最高综合、公开、专项、最高保证
				var creditLinesArr = ["0000", "ZH01", "TP03", "TP04", "TP05", "TP09", "0006"];
				// 信用额度类型
				var creditArr = ["0001", "0002", "0003", "0004", "0007"];
				$scope.custUserLetter = []; //用信主体
				angular.forEach($scope.rentFacList, function(v, k) {
					angular.forEach(v.customersList, function(v1, k1) {
						$scope.custUserLetter.push(v1);
						if(v1.custNo == $scope.recordUseLetter.custNo && index != k1) {
							if(v1.creditLinesId == ss) {
								UserLetterTem = 1;
								alert("提示", "此客户已占用该额度，请重新选择");
							} else if(creditLinesArr.indexOf(v1.creditLinesId) != -1 && creditLinesArr.indexOf(ss) != -1) {
								UserLetterTem = 1;
								alert("提示", "同一个用信主体，只能选择【最高综合授信额度、最高保证额度、公开、专项】四种额度中的一个额度细项进行额度占用，请重新选择");
							}
						}
						if(index == 0) { // 第一个用信主体额度选择  信用时
							if(creditArr.indexOf(v1.creditLinesId) != -1) {
								UserLetterTem = 1;
								alert("提示", "信用额度（免反担保额度）不单独使用，需要同最高综合授信额度（直选）、公开、专项、最高保证一起搭配使用，请先选择其他额度");
							}
						}
						if(UserLetterTem == 1) {
							if(index == k1) {
								v1.creditLinesId = "";
								UserLetterTem = 0;
							}
						}
						if(v1.custNo == $scope.recordUseLetter.custNo && index == k1) {
							if(ss == "0001" || ss == "0002" || ss == "0003" || ss == "0004" || ss == "0007" || ss == "0006") {
								if(index == 0) {
									$scope.creditLineType.push(ss);
								} else {
									$scope.creditLineType.splice(k1, 1, ss);
								}

								/*选择信用时额度占用比例可以不填写*/
								$("#" + index + "creditRatio").removeClass("required");
								//							console.log($scope.creditLineType);
								//							$scope.creditLineType = Array.from(new Set($scope.creditLineType)); // 数组去重   Array.from(new Set(arr));
								console.log($scope.creditLineType);
							}
						}
					});
				});
				//与客户号的index区分
				/*var index = index + 'amountType';
				//样式控制 0000 为最高综合授信额度code
				if(ss == "0000") {
					$("#" + index).removeClass('hidden');
				} else {
					angular.element(document.getElementById(index)).addClass('hidden');
				}*/
			}
			/*
			 * 动态模糊搜索框部分
			 */

			//获取搜索结果数据

			//模糊匹配用信主体
			$scope.queryRecordUseLetter = function(v, indee) {
				if(v == "") {
					angular.forEach($scope.rentFacList, function(v, k) {
						angular.forEach(v.customersList, function(v1, index) {
							if(indee == index) {
								v1.hiddenRecordUseLetter = false;
								//当清理客户号时，自动把客户名称和该产品使用额度类型清空
								v1.custNameCN="";
								v1.creditLinesId="";
								return false;
							}
						});
					});
				}
				if(v.length >= 3) {
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/getCustomerList/list',
						data: angular.toJson({
							"custNo": v
						})
					}).then(function(result) {
						$scope.loading = false;
						$scope.allCustListUseLetter = result.data;
						if(result.httpCode == 200) {
							//下拉选展示
							angular.forEach($scope.rentFacList, function(v, k) {
								angular.forEach(v.customersList, function(v1, index) {
									if(indee == index) {
										v1.hiddenRecordUseLetter = true;
										return false;
									}
								});
							});
						} else {
							$scope.msg = result.msg;
						}
						$rootScope.$applyAsync();
					});
				}
			}

			//选中事件
			$scope.onSelect = function(arg1, inde) {
				arg1[0].creditRatio = 100;
				//下拉选展示
				angular.forEach($scope.rentFacList, function(v, k) {
					angular.forEach(v.customersList, function(v1, index) {
						if(inde == index) {
							v1.hiddenRecordUseLetter = false;
							return false;
						}
					});
				});
				$scope.recordUseLetter = arg1[0];

				//挡板
				/*var dbc = [];
				var dbc = [];
				var a = {
					amountType: "0000",
					creditLineName: "最高综合授信额度",
					creditNo: "aaa1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var b = {
					amountType: "0001",
					creditLineName: "公开授信额度项下业务的免担保授信额度",
					creditNo: "bbb1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var c = {
					amountType: "0002",
					creditLineName: "其他非融资类保函免反担保授信额度",
					creditNo: "ccc1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var d = {
					amountType: "0003",
					creditLineName: "融资类保函免反担保授信额度",
					creditNo: "aaa1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var e = {
					amountType: "0004",
					creditLineName: "船舶出口预付款退款保函免反担保授信额度",
					creditNo: "bbb1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var f = {
					amountType: "0006",
					creditLineName: "最高保证授信额度",
					creditNo: "ccc1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var g = {
					amountType: "0007",
					creditLineName: "非公开授信额度项下业务的免担保授信额度",
					creditNo: "aaa1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var h = {
					amountType: "TP03",
					creditLineName: "贸易融资额度",
					creditNo: "bbb1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var i = {
					amountType: "TP04",
					creditLineName: "融资类保函额度",
					creditNo: "ccc1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var j = {
					amountType: "TP05",
					creditLineName: "非融资类保函额度",
					creditNo: "aaa1101",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var k = {
					amountType: "TP09",
					creditLineName: "流动资金类贷款额度",
					creditNo: "bbb1102",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
				};
				var l = {
					amountType: "ZH01",
					creditLineName: "专项授信额度",
					creditNo: "ccc1103",
					totalAmount: "100000000",
					usedAmount: "2000000",
					availableBalance: "98000000",
					startDate: "2018-09-27 15:12:21",
					maturityDate: "2018-09-27 15:12:21"
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
				$scope.recordUseLetter.creditLinesList = dbc;*/
				//挡板

				//调用信贷额度接口
				var bdc = new BDContext();
				bdc.invokeInf("khztsxxxcx", "khztsxxxcx", [$scope.recordUseLetter.custNo], function(errorcode, data) {
					var temCredit = null;
					angular.forEach(data, function(v, index) {
						if(v.RspCode == '000000' && index == 0) {
							data.splice(index, 1);
							temCredit = '11';
							var index = inde + 'amountType';
							$('#' + index).parent().find("p").remove();
						}
						if(temCredit == null) {
							//接口调用失败
							var index = inde + 'amountType';
							var errorMsg = '该客户的授信信息接口调用失败，请重新选择客户！';
							$('#' + index).addClass("required-warn");
							if($('#' + index).parent().find("p").hasClass("text-danger")) {
								$('#' + index).parent().find(".text-danger").text(errorMsg);
							} else {
								$('#' + index).parent().append('<p class="text-danger">' + errorMsg + '</p>');
							}
							return false;
						}

						//接口调用成功
						if(temCredit == '11') {
							//接口下没有数据
							if(v.ErrorNo == '0') {
								var index = inde + 'amountType';
								var errorMsg = '该客户下没有授信额度，请查证后再试！';
								$('#' + index).addClass("required-warn");
								if($('#' + index).parent().find("p").hasClass("text-danger")) {
									$('#' + index).parent().find(".text-danger").text(errorMsg);
								} else {
									$('#' + index).parent().append('<p class="text-danger">' + errorMsg + '</p>');
								}
								return false;
							}
						}
					});
					$scope.recordUseLetter.creditLinesList = data;
					$scope.$apply();
				});
				//调用信贷额度接口

				//把匹配的客户存入页面对应的客户里面
				angular.forEach($scope.rentFacList, function(v, k) {
					angular.forEach(v.customersList, function(v1, index) {
						if(inde == index) {
							v.customersList.splice(index, 1, $scope.recordUseLetter);
							return false;
						}
					});
				});

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

			}

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
				})

			}

			//点击可选其他币种的+，选择辅助币种
			$scope.selectCurrency = function() {
				//得到全部币种
				//$scope.currencyList=getCurrencyDb();
				// $scope.title = "其他可选币种";
				var names = document.getElementsByName('dede');
				names[0].value = "yes";
				dedede(names[0]);

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

			$scope.currencyProMix = [];
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
			$scope.bizGuaranteeInfoList = [];

			var nclosureCount = 0;
			//初始化的一个空的担保信息
			var recordGuarantee = {
				typePoint: '',
				guaranteeContractType: '',
				guaranteeContractType: '',
				betInformationList: [],
				//定义一个担保币种name
				currencyGuaranteeName: '',
				//定义一个数组，接受担保合同类型的数据
				contractTypeList: [],
				//定义一个字段，设置下拉框的展示
				hiddenRecordGuarantee: false
			};
			//初始化的一个空的押品信息
			var betInformation = {
				pledNo: '',
				guarmartType: '',
				productInformationDescription: '',

			};
			recordGuarantee.betInformationList.push(betInformation);
			$scope.bizGuaranteeInfoList.push(recordGuarantee);

			//点击新增一套担保信息
			$scope.addRent = function() {

				//初始化的一个空的担保信息
				var recordGuarantee = {
					typePoint: '',
					guaranteeContractType: '',
					guaranteeContractType: '',
					betInformationList: [],
					//定义一个担保币种name
					currencyGuaranteeName: '',
					//定义一个数组，接受担保合同类型的数据
					contractTypeList: [],
					//定义一个字段，设置下拉框的展示
					hiddenRecordGuarantee: false
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
					//					for (var i = 0;i<nclosureCode.length-1;i++) {
					//						WebUploadService.initFiles(nclosureCode[i],nclosureType[i],nclosureProd[i],nclosureNam[i], $scope, $timeout);
					//					}
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
				angular.element('select[name=typePoint]').parents('div:eq(2)').nextAll().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
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
				if(eve == "C102" || eve == "C103" || eve == "C101") {
					if(eve == "C102" || eve == "C103") {
						$scope.showBet = true;
						//得到押品编号
						getRemandNum(y);
					} else {
						$scope.showBet = false;
					}

					//得到担保方式类型
					getMortgageList(eve, y);

				} else {
					$scope.showBet = false;
				}

				//假如eve是4时,说明是信用,担保人默认是第一个用信主体
				if(eve == "C000") {
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
				if(eve == "C104") {
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
					if(v.typePoint == "C000") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "C101") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "C102") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "C103") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "C104") {
						guarTem.push(v.typePoint);
					}
				})

				//抵押 担保类型总 C102
				if(guarTem.indexOf("C102") > -1) {
					$scope.rent1 = true;
				} else {
					$scope.rent1 = false;
				}
				//质押 担保类型总	 C103
				if(guarTem.indexOf("C103") > -1) {
					$scope.rent2 = true;
				} else {
					$scope.rent2 = false;
				}
				//保证 担保类型总 C101
				if(guarTem.indexOf("C101") > -1) {
					$scope.rent3 = true;
				} else {
					$scope.rent3 = false;
				}
				//信用   担保类型总 C000
				if(guarTem.indexOf("C000") > -1) {
					$scope.rent4 = true;
				} else {
					$scope.rent4 = false;
				}
				//其他 C104
				if(guarTem.indexOf("C104") > -1) {
					$scope.rent5 = true;
				} else {
					$scope.rent5 = false;
				}

			}

			//根据担保合同类型/占用额度类型的选的一般还是最高，判断担保合同编号的数据来源
			$scope.selectTypePoint = function(ev, indd) { //ev 担保合同类型/占用额度类型   guaranteeContractType
				//假如ev是11,21，23，31，说明是一般
				if(ev == "UE01" || ev == "UE07" || ev == "UE02" || ev == "UE03" || ev == "UE04") {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
						if(indd == index) {
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
				//假如ev是32,12，22，24，说明是最高UE05 UE12 UE13 UE06 UE14 UE15
				if(ev == "UE05" || ev == "UE12" || ev == "UE13" || ev == "UE06" || ev == "UE14" || ev == "UE15") {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
						if(indd == index) {
							v.warrantyContractNumber = "";
							v.guarantor = "";
							v.guarantorCustId = "";
							v.currencyGuarantee = "";
							v.guaranteeAmount = "";
							if(ev == "UE05" || ev == "UE12" || ev == "UE13") {
								$scope.maximumType.splice(index - 1, 1, ev);
							}
						}
					});
					$scope.showHigh = true;
					$scope.showSoso = false;
				}
				if(ev == "0001" || ev == "0002" || ev == "0003" || ev == "0004" || ev == "0007") {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
						if(indd == index && $scope.custUserLetter.indexOf(v.guarantorCustId)) {
							if($scope.creditLineType.indexOf(ev) < 0) {
								alert("提示", '请先在"用信主体"中选择相应信用额度占用类型');
								v.guaranteeContractType = "";
							} else {
								$scope.guaranteeTypes.splice(index, 1, ev);
								console.log($scope.guaranteeTypes);
							}
						}

					});
				}
			}

			//根据担保类型分得到担保合同类型/占用额度类型
			function getContractTypeList(eve, y) {
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
									if(v1.pledNo == "") {
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
					if(v.typePoint == "C000") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "C101") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "C102") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "C103") {
						guarTem.push(v.typePoint);
					}
					if(v.typePoint == "C104") {
						guarTem.push(v.typePoint);
					}
				})

				//抵押 担保类型总 C102
				if(guarTem.indexOf("C102") > -1) {
					$scope.rent1 = true;
				} else {
					$scope.rent1 = false;
				}
				//质押 担保类型总	 C103
				if(guarTem.indexOf("C103") > -1) {
					$scope.rent2 = true;
				} else {
					$scope.rent2 = false;
				}
				//保证 担保类型总 C101
				if(guarTem.indexOf("C101") > -1) {
					$scope.rent3 = true;
				} else {
					$scope.rent3 = false;
				}
				//信用   担保类型总 C000
				if(guarTem.indexOf("C000") > -1) {
					$scope.rent4 = true;
				} else {
					$scope.rent4 = false;
				}
				//其他 C104
				if(guarTem.indexOf("C104") > -1) {
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
						v2.currencyGuaranteeName = $scope.temContack.securedCreditCurrencyName;
						v2.guaranteeAmount = $scope.temContack.securedCreditClaim;
						v2.warrantee=$scope.contckproposer;
						v2.warranteeCustId=$scope.contckproposerId;
						return false;
					}
				})

			};

			//判断弹窗担保合同是否什么都没有选择
			$scope.submitContack = function() {

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

			//模糊匹配担保人
			$scope.queryrecordGuarantee = function(v, indee) {
				if(v == "") {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
						if(indee == k) {
							v.hiddenRecordGuarantee = false;
							v.guarantorCustId = "";
							return false;
						}
					});
				}
				if(v.length >= 3) {
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/getCustomerList/list',
						data: angular.toJson({
							"custNo": v
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							$scope.allCustListGuarantee = result.data;

							//下拉选展示
							angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
								if(indee == k) {
									if($scope.allCustListGuarantee.length == 0) {
										v.guarantor = "";
										v.guarantorCustId = "";
									}
									v.hiddenRecordGuarantee = true;
									return false;
								}

							});
						} else {
							$scope.msg = result.msg;
						}
						$rootScope.$applyAsync();
					});
				}
				if($scope.recordGuarantee.guarantor == "") {
					$scope.recordGuarantee.guarantorCustId = "";
				}
			}

			//担保人选中事件
			$scope.onSelectGuarantor = function(arg1, ind) {
				angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
					if(ind == index) {
						v.guarantor = arg1[0].custNameCN;
						v.guarantorCustId = arg1[0].custNo;
						v.hiddenRecordGuarantee = false;
					}
				});
			};
			
			//模糊匹配被担保人
			$scope.queryrecordWarrantee = function(v, indee) {
				if(v == "") {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
						if(indee == k) {
							v.hiddenRecordWarrantee = false;
							v.warranteeCustId = "";
							return false;
						}
					});
				}
				if(v.length >= 3) {
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/getCustomerList/list',
						data: angular.toJson({
							"custNo": v
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							$scope.allCustListWarrantee = result.data;

							//下拉选展示
							angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
								if(indee == k) {
									if($scope.allCustListWarrantee.length == 0) {
										v.warrantee = "";
										v.warranteeCustId = "";
									}
									v.hiddenRecordWarrantee = true;
									return false;
								}

							});
						} else {
							$scope.msg = result.msg;
						}
						$rootScope.$applyAsync();
					});
				}
				if($scope.recordGuarantee.warrantee == "") {
					$scope.recordGuarantee.warranteeCustId = "";
				}
			}

			//被担保人选中事件
			$scope.onSelectWarrantee = function(arg1, ind) {
				angular.forEach($scope.bizGuaranteeInfoList, function(v, index) {
					if(ind == index) {
						v.warrantee = arg1[0].custNameCN;
						v.warranteeCustId = arg1[0].custNo;
						v.hiddenRecordWarrantee = false;
					}
				});
			};

			//模糊匹配最高额担保合同担保人
			$scope.queryrecordContackty = function(v) {
				$scope.hiddenRecordContackty = false;
				if(v.length >= 3) {
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/getCustomerList/list',
						data: angular.toJson({
							"custNo": v
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							$scope.allCustListContackty = result.data;

							if($scope.allCustListContackty.length == 0) {
								$scope.contckproposer = "";
								$scope.hiddenRecordContackty = false;
							} else {
								$scope.hiddenRecordContackty = true;
							}
						} else {
							$scope.msg = result.msg;
						}
						$rootScope.$applyAsync();
					});
				}
			}
			//最高额担保合同担保人选中事件
			$scope.onSelectContackty = function(arg1) {
				$scope.contckproposer = arg1[0].custNameCN;
				$scope.contckproposerId =arg1[0].custNo;
				$scope.hiddenRecordContackty = false;
				var tem = arg1[0].custNo;

				//挡板
				/*var cc = [];
				var aa = {
					"custNo": "112",
					"custNameCN": "猎豹移动",
					"guaranteeContractTime": "2018-09-27 15:12:21",
					"securedCreditCurrency": "CNY",
					"securedCreditCurrencyName": "人民币",
					"securedCreditClaim": "1200",
					"guarantor": "张一山",
					"warrantyContractNumber": "741825",
					"BalanceSecuredClaim": "300",
					"guaranteeStartDate": "2018-09-27 15:12:21",
					"guaranteeEndDate": "2018-09-27 15:12:21",
					"guaranteeContractName": "最高额抵押合同",
					"checked": "false",
					"guarantorCustId": "741852",
					"guaranteeMenthodName": "多人分保",
					"remarks": "中戏"
				};
				var bb = {
					"custNo": "112",
					"custNameCN": "猎豹移动",
					"guaranteeContractTime": "2018-09-27 15:12:21",
					"securedCreditCurrency": "USD",
					"securedCreditCurrencyName": "美元",
					"securedCreditClaim": "2200",
					"guarantor": "杨紫",
					"warrantyContractNumber": "124558",
					"BalanceSecuredClaim": "396",
					"guaranteeStartDate": "2018-09-27 15:12:21",
					"guaranteeEndDate": "2018-09-27 15:12:21",
					"guaranteeContractName": "最高额动产质押合同",
					"checked": "false",
					"guarantorCustId": "987456",
					"guaranteeMenthodName": "多人保证",
					"remarks": "中戏"
				};
				var dd = {
					"custNo": "112",
					"custNameCN": "猎豹移动",
					"guaranteeContractTime": "2018-09-27 15:12:21",
					"securedCreditCurrency": "USD",
					"securedCreditCurrencyName": "美元",
					"securedCreditClaim": "2200",
					"guarantor": "杨紫",
					"warrantyContractNumber": "124558",
					"BalanceSecuredClaim": "396",
					"guaranteeStartDate": "2018-09-27 15:12:21",
					"guaranteeEndDate": "2018-09-27 15:12:21",
					"guaranteeContractName": "最高额动产质押合同",
					"checked": "false",
					"guarantorCustId": "987456",
					"guaranteeMenthodName": "多人保证",
					"remarks": "中戏"
				};
				cc.push(aa);
				cc.push(bb);
				cc.push(dd);
				$scope.contacktyList = cc;*/
				//挡板

				//接口查询信贷的最高额担保合同
				var bdc = new BDContext();
				bdc.invokeInf("zgedbhtcx", "zgedbhtcx", [tem], function(errorcode, data) {
					var temGuar = null;
					angular.forEach(data, function(v, index) {
						//接口调用成功
						if(v.RspCode == '000000' && index == 0) {
							temGuar = '110';
							data.splice(index, 1);
							$("#proposer5").parent().find("p").remove();
							$("#proposer5").removeClass("required-warn");
						}
						if(temGuar == null) {
							//接口调用失败
							var index = inde + 'amountType';
							var errorMsg = '该客户的最高额接口调用失败，请稍后重试!';
							$("#proposer5").addClass("required-warn");
							$("#proposer5").parent().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
							if($("#proposer5").parent().find("p").hasClass("text-danger")) {
								$("#proposer5").parent().find(".text-danger").text(errorMsg);
							} else {
								$("#proposer5").parent().append('<p class="text-danger">' + errorMsg + '</p>');
							}
							return false;
						}
						if(temGuar == '110') {
							//接口下没有数据
							if(v.ErrorNo == '0') {
								var errorMsg = '该客户下没有最高额合同信息，请查证后再试!';
								$("#proposer5").addClass("required-warn");
								$("#proposer5").parent().find('span').removeClass('glyphicon glyphicon-ok-circle vali-success');
								if($("#proposer5").parent().find("p").hasClass("text-danger")) {
									$("#proposer5").parent().find(".text-danger").text(errorMsg);
								} else {
									$("#proposer5").parent().append('<p class="text-danger">' + errorMsg + '</p>');
								}
								return false;
							}
						}
					});

					$scope.contacktyList = data;
					$scope.$apply();
				});
				//接口查询信贷的最高额担保合同
			};

			//模糊匹配承租人
			$scope.queryRecordProInform = function(v, indee) {
				if(v == "") {
					angular.forEach($scope.rentFacList, function(v, k) {
						if(indee == index) {
							v.hiddenRecordProInform = false;
							return false;
						}
					});
				}
				if(v.length >= 3) {
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/getCustomerList/list',
						data: angular.toJson({
							"custNo": v
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							$scope.allCustListProInform = result.data;
							//下拉选展示
							angular.forEach($scope.rentFacList, function(v, k) {
								if(indee == k) {
									v.hiddenRecordProInform = true;
									return false;
								}
							});
						} else {
							$scope.msg = result.msg;
						}
						$rootScope.$applyAsync();
					});
				}
			}

			//承租人选中
			$scope.onSelectCustName = function(arg) {
				//承租人客户号
				angular.forEach($scope.rentFacList, function(v, k) {
					//承租人客户号
					v.custNo = arg[0].custNo;
					//承租人名称
					v.custName = arg[0].custNameCN;
					//承租人客户评级
					v.custTating = arg[0].creditRating;
					v.hiddenRecordProInform = false;

					//检验申请人和承租人是否一致
					if(v.custNo == $scope.debtMain.proposerNum) {
						var errorMsg = '申请人和承租人不能是同一个人，请重新录入！';
						$('#proposer4').removeClass('required');
						$('#proposer4').parent().find('span').remove();
						$('#proposer4').parent().append('<span class="text-danger">' + errorMsg + '</span>');
						$('#proposer4').addClass("required-warn");
						$('#proposer4').val('');
						return false;
					} else {
						$('#proposer4').addClass('required');
						$('#proposer4').parent().find('span').remove();
					}
					return false;
				});

			};

			//得到国别背景
			$scope.getBackground = function(type, tb) {
				$scope.hinddenBackground = false;
				$.ajax({
					type: "POST",
					url: "/eximbank-club/debtSummary/read/list",
					data: angular.toJson({
						'type': type,
						'tb': tb
					})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.backgroundList = result.data;
						$scope.hinddenBackground = true;
					} else {
						$scope.msg = result.msg;
					}
					$rootScope.$applyAsync();
				})

			}
			//担保合同编号唯一校验
			$scope.queryConNum = function(arg, type, inde) {
				if(type == 'UE01' || type == 'UE07' || type == 'UE02' || type == 'UE03' || type == 'UE04' || type == 'UE20') {
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
								} else {
									$('#' + index).addClass('required');
									$('#' + index).parent().find('span').remove();
								}
							}

						});
					}

					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/queryConNum/list',
						data: angular.toJson({
							"warrantyContractNumber": arg,
							"debtCodeCon": $scope.debtCode
						})
					}).then(function(result) {
						$scope.loading = false;
						if(result.httpCode == 200) {
							if(result.data == '1') {
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

			//国别选中
			var backGroundTem = null;
			$scope.onSelectBackground = function(arg) {
				//承租人客户号
				angular.forEach($scope.rentFacList, function(v, k) {
					//国别赋值,一个是code，一个是name
					v.tbon = arg[0].codeText;
					backGroundTem = arg[0].code;
					$scope.hinddenBackground = false;
				});

			};

			$scope.proposerJiaoYan = function(a, b) {
				//b---$index 避免重名
				switch(a) {
					case 1:
						$scope.debtMain.proposerNum = "";
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
							angular.forEach(v.customersList, function(v1, index) {
								if(v1.custNo == null || v1.custNo == '') {
									angular.element("#" + b).parent().find("span").remove();
									angular.element("#" + b).parent().append('<span class="text-danger">' + "请输入申请人名称或者申请人客户号查询" + '</span>');
									count += 1;
								} else {
									angular.element("#" + b).parent().find("span").remove();
									angular.element("#" + b).parent().append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
								}
								//当客户号为空，客户名称也为空
								if(b == index) {
									v1.custNameCN = "";
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
						$scope.custNo = "";
						$scope.custPin = "";
						if($scope.custName == null || $scope.custName == '') {
							//							angular.element("#proposer4").parent().find("span").remove();
							//							angular.element("#proposer4").parent().append('<span class="text-danger">' + "请输入承租人（债务人）名称或者申请人客户号查询" + '</span>');
							count += 1;
						} else {
							angular.element("#proposer4").parent().find("span").remove();
							angular.element("#proposer4").parent().append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
						}
						break;
					case 5:
						if($scope.contckproposer == null || $scope.contckproposer == '') {
							angular.element("#proposer5").parent().find("span").remove();
							angular.element("#proposer5").parent().append('<span class="text-danger">' + "请输入担保人名称或者担保人客户号查询" + '</span>');
							count += 1;
						} else {
							angular.element("#proposer5").parent().find("span").remove();
							angular.element("#proposer5").parent().append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
						}
						break;
				}
			}
			
			
//			
			//专有信息的提交
			$scope.submitDebt = function() {
				$scope.issueDateList = [$("#issue").val()];
				console.log($scope.issueDateList);
				confirm("提示", "确定提交 " + $scope.debtMain.projectName + "吗？", function(flag) {
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

						var promise = $CanvasService.createCanvas(angular.element('.canvas')[0], "A", angular.element('.canvasNum').find('.active').attr("num"), $scope.debtCode, null, $scope, null);
						promise.then(function(data) {

							//处理项目名称
							if(!$scope.debtMain.projectName) {
								var proposer = $scope.debtMain.proposer;
								proposer = proposer +" "+$scope.debtMain.mpc +" "+ $scope.debtMain.solutionAmount;
								angular.forEach($scope.rentFacList, function(v, k) {
									proposer = proposer + v.name;
								});
								$scope.debtMain.projectName = proposer + "项目";
							}
							//处理经办机构
							$scope.debtMain.institutionCode = $scope.deptList.parentCode;
							
							//把创建人的id存入概要表中
							$scope.debtMain.createPerson = $scope.userList.id;
							$scope.debtMain.createPersonName = $scope.userList.userName;

							//把柜员的id存入概要表中
							$scope.debtMain.bankTellerId = $scope.userList.id;
							//把债项概要编号存入债项概要表
							//$scope.debtMain.debtCode = $scope.debtCode;
							//把债项概要编号存入担保信息表表
							angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
								v.debtCode = $scope.debtCode;
							});
							//把其他可选币种存入到debtMain中
							var currentTem = [];
							angular.forEach($scope.currencyProMix, function(v, k) {
								currentTem += v.monCode + ",";
							});
							$scope.debtMain.oc = currentTem;

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
								v.guaranteeAmount = v.guaranteeAmount.replace(/,/g, '');
								return false;
							});

							//处理行业投向
							//var industryTo = angular.element("#industryTo").value;
							var industryTo = $('#industryTo').val();
							if(industryTo) {
								angular.forEach($scope.rentFacList, function(v, k) {
									v.industryTo = industryTo;
								});
							}

							//背景国别
							angular.forEach($scope.rentFacList, function(v, k) {
								v.tbon = backGroundTem;
							});

							$.ajax({
								type: 'POST',
								url: '/eximbank-club/debtSummary/saveDebt/list',
								data: angular.toJson({
									'state': "方案补录",
									'debtCode': $scope.debtCode,
									//	'proList': $scope.proList,
									'debtMain': $scope.debtMain,
									'rentFacList': $scope.rentFacList,
									'bizGuaranteeInfoList': $scope.bizGuaranteeInfoList,
									'issueDateList':$scope.issueDateList
								})
							}).then(function(result) {
								$scope.loading = false;
								if(result.httpCode == 200) {
									$.growl.notice({
										title: "提醒",
										message: "保存成功！",
										size: "large"
									});
									//							alert("提示","业务信息提交成功!");
									$timeout(function() {
										//								 $state.go('main.biz.record.check');
										if(angular.element("a[ui-sref='main.biz.record.check']").parent().parent().parent().hasClass('active')) {
											angular.element("a[ui-sref='main.biz.record.check']").trigger('click');
										} else {
											angular.element("a[ui-sref='main.biz.record.check']").trigger('click');
											angular.element("a[ui-sref='main.biz.record.check']").parent().parent().prev("a").trigger('click');
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

						}, function(data) {
							console.log("创建快照失败 error data ==" + data);
						}, function(data) {});
					}
				});
			}
			
			var tmp = 0; //变量控制项目名称生成方式
			$scope.xmmc = function() {
				var solutionAmount = $scope.debtMain.solutionAmount.replace(/,/g, '');
				if($scope.debtMain.mpc == "JPY"){
					solutionAmount = $filter("number")(solutionAmount);
				}else{
					solutionAmount = $filter("number")(solutionAmount,2);
				}
				
				//				$scope.debtMain.projectName = proposer + $scope.debtMain.solutionAmount + "项目"; 
				//处理项目名称
				if((!$scope.debtMain.projectName && tmp == 0) || ($scope.debtMain.projectName && tmp == 1)) {
					var proposer = $scope.debtMain.proposer;
					proposer = proposer + " " + $scope.debtMain.mpc +" " + solutionAmount;
					tmp = 1;
					angular.forEach($scope.rentFacList, function(v, k) {
						proposer = proposer + v.name;
					});
					$scope.debtMain.projectName = proposer + "项目";
				}
			}

			//点击暂存
			$scope.submitTemStorage = function() {
				//处理债项方案编号
				var debtCode = $scope.debtCode
				//				debtCode = debtCode + "001";
				$scope.debtCode = debtCode;

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
				//添加经办机构
				$scope.debtMain.institutionCode = $scope.deptList.code;

				//把柜员的id存入概要表中
				$scope.debtMain.bankTellerId = $scope.userList.id;
				//把债项概要编号存入债项概要表
				$scope.debtMain.debtCode = $scope.debtCode;
				//把债项概要编号存入担保信息表表
				if($scope.bizGuaranteeInfoList != []) {
					angular.forEach($scope.bizGuaranteeInfoList, function(v, k) {
						v.debtCode = $scope.debtCode;
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
				//				var industryTo = angular.element("#industryTo").value;
				var industryTo = $('#industryTo').val();
				if(industryTo) {
					angular.forEach($scope.rentFacList, function(v, k) {
						v.industryTo = industryTo;
					});
				}

				$.ajax({
					type: 'POST',
					url: '/eximbank-club/temStorage/saveScheme/list',
					data: angular.toJson({
						'remark': "1",
						'debtCode': $scope.debtCode,
						'debtMain': $scope.debtMain,
						'rentFacList': $scope.rentFacList,
						'bizGuaranteeInfoList': $scope.bizGuaranteeInfoList,
						'issueDateList':$scope.issueDateList
					})
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$.growl.notice({
							title: "提醒",
							message: "暂存成功！",
							size: "large"
						});
						//						alert("提示","保存成功");
						$timeout(function() {
							//$state.go('main.biz.record.schemeTemStorage');
							if(angular.element("a[ui-sref='main.biz.record.schemeTemStorage']").parent().parent().parent().hasClass('active')) {
								angular.element("a[ui-sref='main.biz.record.schemeTemStorage']").trigger('click');
							} else {
								angular.element("a[ui-sref='main.biz.record.schemeTemStorage']").trigger('click');
								angular.element("a[ui-sref='main.biz.record.schemeTemStorage']").parent().parent().prev("a").trigger('click');
							}
						}, 1000);
					} else {
						$scope.msg = result.msg;
						console.log($scope.msg);
						alert("提示", "暂存提交失败，请稍后再试!");
					}
					$rootScope.$applyAsync();
				});

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
			$('#pgExpiDate').val('');
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
			$('#pgEffectivDate').val('');
			count += 1;
		} else {
			parent.find("span").remove();
			parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
		}
	}
}
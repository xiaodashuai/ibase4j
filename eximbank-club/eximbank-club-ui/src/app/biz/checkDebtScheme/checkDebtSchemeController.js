angular.module('app')
	.controller('checkDebtSchemeController', ['$scope', '$rootScope', '$http', '$state', '$stateParams', '$filter', '$timeout', 'WebUploadService',
		function($scope, $rootScope, $http, $state, $stateParams, $filter, $timeout, WebUploadService) {
			$scope.title = '方案查询';
			$scope.loading = false;
			$scope.param = {};
			//查看附件
			$scope.param.countPage = 10;
			$scope.webUploadStatus = 0;
			$scope.openE = function(debtCode, index) {
				var debtCode = debtCode.substring(0, 13);
				if($scope.webUploadStatus == 0) { //第一次点击任意一笔
//					WebUploadService.initFiles([debtCode, debtCode], ["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout, true);
					WebUploadService.initFiles([debtCode,debtCode,debtCode], ["A", "A","A"], ["租金保理", "租金保理","租金保理"], ["Textupload", "Attachupload","Meetingupload"], $scope, $timeout, true);
					$scope.decd = debtCode;
					$scope.webUploadStatus++;
					return false;
				} else if($scope.webUploadStatus != 0) {
					if($scope.decd == debtCode) { //第N次点击同一笔
						return false;
					} else { //第二次点击不同笔
						$scope.realFileName = ""; //清空文件名
						$scope.nclosureName = ""; //清空文件名
						$scope.meetFileName = "";
						angular.element('.filelist').remove(); //清空文件list
						WebUploadService.initFiles([debtCode,debtCode,debtCode], ["A", "A","A"], ["租金保理", "租金保理","租金保理"], ["Textupload", "Attachupload","Meetingupload"], $scope, $timeout, true);
//						WebUploadService.initFiles([debtCode, debtCode], ["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout, true);
						$scope.decd = debtCode;
					}

				}

			}
			//格式化时间，以便比较
			$scope.myDate = $filter("date")(Date.parse(new Date()), "yyyy-MM-dd HH:mm:ss");

			//查询全部币种
			function getCurrency() {
				//得到全部币种
				$.ajax({
					type: "POST",
					url: "/eximbank-club/debtSummary/chargeType/query",
					data: angular.toJson({})
				}).then(function(result) {
					$scope.currencyList = result.data;
					$scope.search();
				})

			}
			getCurrency();
			var temDept = ""; //$scope.param.deptCode
			var temschemeNum = ""; //$scope.param.schemeNum 
			var temprojectNam = ""; //$scope.param.projectNam 
			var temsolutionState = ""; //$scope.param.solutionState 
			var temapplicant = ""; //$scope.param.applicant 
			var temstateTime = ""; //$scope.param.stateTime 
			var temendTime = ""; //$scope.param.endTime 

			$scope.search = function() {
				if($scope.param.endTime && $scope.param.stateTime && $scope.param.endTime < $scope.param.stateTime){
					alert("提示","时间选择错误,请重新选择");
					$scope.param.stateTime = "";
					$scope.param.endTime = "";
					return false;
				}
				$scope.param.deptCode = $("#deptCode").val();
				$scope.loading = true;
				//方案编号小写字母转大写
				var schemeNumber = $scope.param.schemeNum;
				if($scope.param.schemeNum != null) {
					$scope.param.schemeNum = $scope.param.schemeNum.toUpperCase();
				}

				//判断查询条件是否变化
				if(!(temDept == $scope.param.deptCode && temprojectNam == $scope.param.projectNam && temschemeNum == $scope.param.schemeNum && temsolutionState == $scope.param.solutionState && temapplicant == $scope.param.applicant && temstateTime == $scope.param.stateTime && temendTime == $scope.param.endTime)) {
					temDept = $scope.param.deptCode;
					temschemeNum = $scope.param.schemeNum;
					temprojectNam = $scope.param.projectNam;
					temsolutionState = $scope.param.solutionState;
					temapplicant = $scope.param.applicant;
					temstateTime = $scope.param.stateTime;
					temendTime = $scope.param.endTime;
					//1表示变化
					$scope.param.pageNum = 1;
					$scope.param.current = 1;

				}
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/checkDebtScheme/list',
					data: angular.toJson($scope.param)
				}).then(function(result) {
					$scope.loading = false;
					if(result) {
						if(result.httpCode == 200) {
							$scope.param.schemeNum = schemeNumber;
							$scope.pageInfo = result;
							angular.forEach(result.data, function(v, k) {
								angular.forEach($scope.currencyList, function(v1, k1) {
									if(v.mpc == v1.monCode) {
										v.mpc = v1.codeName;
									}
								})
							})
						} else {
							$scope.param.schemeNum = schemeNumber;
							$scope.msg = result.msg;
						}
					} else {

						$scope.msg = "服务器异常，请刷新重试或联系管理员";
					}
					$rootScope.$applyAsync();
				});

			}

			//已驳回的删除操作
			$scope.checkState = function(ite) {
				$scope.loading = true;
				//直接把状态改成8
				ite.solutionState = 8;
				//把币种的处理还原回去
				angular.forEach($scope.currencyList, function(v1, k1) {
					if(ite.mpc == v1.codeName) {
						ite.mpc = v1.monCode;
					}
				})
				confirm("提示", "删除后不能恢复，您确认要执行删除吗？", function(flag) {
					if(flag) {
						$.ajax({
							type: 'POST',
							url: '/eximbank-club/debtSummary/checkState/list',
							data: angular.toJson(ite)
						}).then(function(result) {
							$scope.loading = false;
							if(result) {
								if(result.httpCode == 200) {
									alert("提示", "删除成功!");
									$.ajax({
										type: 'POST',
										url: '/eximbank-club/temStorage/delTemp',
										data: angular.toJson({
											'debtCode': ite.debtCode
										})
									});
								} else {
									alert("提示", "删除失败!")
								}
							} else {
								$scope.msg = "服务器异常，请刷新重试或联系管理员";
							}
							$scope.search();
						});
					} else {
						$scope.search();
					}

				})
			}

			$scope.getDeptList = function() {
				$.ajax({
					url: '/eximbank-club/standingBook/read/getDeptList',
					type: 'PUT',
					data: angular.toJson($scope.param)
				}).then(function(result) {
					$scope.loading = false;
					if(result.httpCode == 200) {
						$scope.deptList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}
			/**
			 * 初始化机构
			 */
			function initDepartment() {
				bulidDeptSelect("deptCode");
				var size = $("#deptCode").find("option").size();
				if(size == 2) {
					$("#deptCode").get(0).selectedIndex = 1;
					$("#deptCode").attr('disabled', true);
				}
			}
			//初始化机构下拉框
			initDepartment();
			//			$scope.getDeptList();
			$scope.search();

			$scope.clearSearch = function() {
				$scope.param.keyword = null;
				$scope.search();
			}

			//点击修订和重新提交的提醒
			$scope.queryTem = function(item, stat) {
				if(stat == 2) {
					$state.go('main.biz.record.schemeRevise', {
						debtCode: item.debtCode,
						statt: stat
					});
				} else {

					//先查询发放条件是否是废弃6，删除12，已发放5的状态，是的话，允许做修订操作
					$.ajax({
						url: '/eximbank-club/debtSummary/queryGrantStatus/list',
						type: 'POST',
						data: angular.toJson({
							'bizcode': item.debtCode
						})
					}).then(function(result) {
						var grantTem = result.data;
						if(grantTem.length == 0 || grantTem[0].grantStatus == '6' || grantTem[0].grantStatus == '12' || grantTem[0].grantStatus == '5' || grantTem[0].grantStatus == null) {
							$.ajax({
								url: '/eximbank-club/debtSummary/queryTem/list',
								type: 'POST',
								data: angular.toJson({
									'bizcode': item.debtCode
								})
							}).then(function(result) {
								$scope.loading = false;
								if(result.httpCode == 200) {
									if(result.data == true) {
										confirm("提示", "该条信息已有暂存记录，如需对暂存继续编辑，请进入暂存查询交易进行操作。取消，取消操作；确定，可能清除或更新暂存记录", function(flag) {
											if(flag) {
												$state.go('main.biz.record.schemeRevise', {
													debtCode: item.debtCode,
													statt: stat
												});
											}
										})
									} else {
										$state.go('main.biz.record.schemeRevise', {
											debtCode: item.debtCode,
											statt: stat
										});
									}
								} else {
									$scope.msg = result.msg;
								}
								$scope.$apply();
							});
						} else {
							alert("提示", "该方案正在进行发放操作，如需进行方案修订，请先完成后续操作再进行修订!");
						}
					})
				}
			}
			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}
	]);
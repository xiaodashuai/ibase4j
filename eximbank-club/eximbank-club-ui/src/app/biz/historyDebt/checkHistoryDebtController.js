angular.module('app')
	.controller('checkHistoryDebtController', ['$scope', '$rootScope', '$http', '$state', '$stateParams', '$timeout', '$CanvasService', 'WebUploadService',
		function($scope, $rootScope, $http, $state, $stateParams, $timeout, $CanvasService, WebUploadService) {
			$scope.title = '债项方案数据迁移列表';
			$scope.loading = false;
			$scope.param = {};

			//查询全部币种
			$scope.param.countPage = 100;

			$scope.webUploadStatus = 0;
			$scope.openE = function(debtCode, index) {
				if($scope.webUploadStatus == 0) { //第一次点击任意一笔
					WebUploadService.initFiles([debtCode, debtCode], ["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout);
					$scope.idd = index;
					$scope.webUploadStatus++;
					return false;
				}
				if($scope.webUploadStatus != 0) {
					if($scope.idd == index) { //第N次点击同一笔
						return false;
					} else { //第二次点击不同笔
						$scope.realFileName = ""; //清空文件名
						$scope.nclosureName = ""; //清空文件名
						angular.element('.filelist').remove(); //清空文件list
						WebUploadService.initFiles([debtCode, debtCode], ["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout);
						$scope.idd = index;
					}

				}

			}
			getCurrency();

			$scope.search = function() {
				$scope.param.deptCode = $("#deptCode").val();
				if($scope.param.stateTime > $scope.param.endTime && $scope.param.endTime != '') {
					alert("输入有误！", "开始时间大于结束时间");
					return false;
				}
				//方案编号小写字母转大写
				if($scope.param.schemeNum != null) {
					$scope.param.schemeNum = $scope.param.schemeNum.toUpperCase();
				}
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/debtSummary/checkHistoryDebt/list',
					data: angular.toJson($scope.param)
				}).then(function(result) {
					$scope.loading = false;
					if(result) {
						if(result.httpCode == 200) {
							angular.forEach(result.data, function(v, k) {
								angular.forEach($scope.currencyList, function(v1, k2) {
									if(v.mpc == v1.monCode) {
										v.mpc = v1.monCode + " " + v1.codeName;
									}
								});
							});
							$scope.pageInfo = result;
						} else {
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "服务器异常，请刷新重试或联系管理员";
					}
					$rootScope.$applyAsync();
				});

			}
			$scope.getDeptList = function() {
				$.ajax({
					url: '/eximbank-club/debtSummary/read/getAllDeptList',
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
					$("#deptCode").attr('disabled',true);
				}
			}
			//初始化机构下拉框
			initDepartment();
			//			$scope.getDeptList();

			$scope.clearSearch = function() {
				$scope.param.keyword = null;
				$scope.search();
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
					$scope.search();
					/*angular.forEach($scope.currencyList, function(v, k) {
						var tt = null;
						tt = v.monCode + "   " + v.codeName;
						v.createBy = tt;
					});*/
				})

			}

			//编辑提醒
			$scope.queryTem = function(item) {
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
									$state.go('main.sys.historyDebt.edit', {
										debtCode: item.debtCode
									});
								}
							})
						} else {
							$state.go('main.sys.historyDebt.edit', {
								debtCode: item.debtCode
							});
						}
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}

			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}
	]);
angular.module('app').controller(
	'schemeTemStorageController', ['$rootScope', '$scope', '$state',
		function($rootScope, $scope, $state) {
			$scope.title = '暂存列表';
			$scope.param = {};
			$scope.loading = false;

			//查询全部币种
			getCurrency();
			var temDept = "";//$scope.param.deptCode
			var temschemeNum = "";//$scope.param.schemeNum 
			var temprojectNam = "";//$scope.param.projectNam 
			var temapplicant = "";//$scope.param.applicant 
			var temstateTime = "";//$scope.param.stateTime 
			var temendTime = "";//$scope.param.endTime 
			

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
				if($scope.param.schemeNum != null) {
					$scope.param.schemeNum = $scope.param.schemeNum.toUpperCase();
				}
				
				//判断查询条件是否变化
				if(!(temDept == $scope.param.deptCode && temprojectNam == $scope.param.projectNam  && temschemeNum == $scope.param.schemeNum && temapplicant == $scope.param.applicant && temstateTime == $scope.param.stateTime && temendTime == $scope.param.endTime)) {
					temDept = $scope.param.deptCode;
					temschemeNum = $scope.param.schemeNum ;
					temprojectNam = $scope.param.projectNam;
					temapplicant = $scope.param.applicant;
					temstateTime = $scope.param.stateTime;
					temendTime = $scope.param.endTime;
					//1表示变化
					$scope.param.pageNum = 1;
					$scope.param.current = 1;
				}
				
				$.ajax({
					type: 'POST',
					url: '/eximbank-club/temStorage/queryTemp',
					data: angular.toJson($scope.param)
				}).then(function(result) {
					$scope.loading = false;
					if(result) {
						if(result.httpCode == 200) {
							angular.forEach(result.data, function(v, k) {
								angular.forEach($scope.currencyList, function(v1, k2) {
									if(v.obj.debtMain.mpc == v1.monCode) {
										v.obj.debtMain.mpc = v1.codeName;
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
//			$scope.search();

			$scope.clearSearch = function() {
				$scope.param.keyword = null;
				$scope.search();
			}

			/**
			 * 删除暂存
			 */
			$scope.deleteItem = function(item) {
				//				var item = getCheckItem();
				if(item == null) {
					alert("提示", "请选择一个项目!");
					return false;
				}
				var role = $scope.roleCodes;
				if(role == '1001' || role == '1020') {
					if(item.remark == '3') {
						alert("提示", "驳回后的方案不能删除!");
						return false;
					}
					confirm("提示", "删除后不能恢复，您确认要执行删除吗？", function(flag) {
						if(flag) {
							$scope.loading = true;
							$.ajax({
								type: 'PUT',
								url: '/eximbank-club/temp/delTemp',
								data: angular.toJson({
									'id': item.id
								})
							}).then(function(result) {
								console.log(result)
								if(result) {
									if(result.httpCode == 200) {
										alert("提示", "删除成功!");
										$scope.search();
									} else {
										$scope.msg = result.msg;
									}
								} else {
									$scope.msg = "服务器异常，请刷新重试或联系管理员";
								}
								$rootScope.$applyAsync();
							});
						}
					});
				} else {
					console.log("--------不是业务发起处室经办，没有删除权限-------------");
					alert("提示", "您没有删除权限!");
					return false;
				}
			}

			$scope.clearSearch = function() {
				$scope.param.keyword = null;
				$scope.search();
			}

			// 单个选择
			$scope.selectOne = function(m, n) {
				angular.forEach($scope.pageInfo.data, function(v, k) {
					if(n == v.id) {
						v.checked = m;
					} else {
						// 如果选中一个,其余的都自动设置未选中
						if(m == true) {
							v.checked = false;
						}
					}
				})
			}
			/**
			 * 获取选中的值
			 */
			function getCheckItem() {
				var item = null;
				angular.forEach($scope.pageInfo.data, function(v, k) {
					if(v.checked == true) {
						item = v;
					}
				});
				return item;
			}

			// 编辑账号
			$scope.edit = function(item) {
				//				var item = getCheckItem();
				if(item == null) {
					alert("提示", "请选择一个项目!");
					return false;
				}
				$state.go('main.biz.record.edit', {
					id: item.id,
					bizcode: item.bizcode,
					statee: item.remark
				});
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
				})

			}

			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}

	]);
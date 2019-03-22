angular.module('app').controller(
	'historySchemeTemStorageController', ['$rootScope', '$scope', '$state',
		function($rootScope, $scope, $state) {
			$scope.title = '暂存列表';
			$scope.param = {};
			$scope.loading = false;
			
			//查询全部币种
				getCurrency();

			$scope.search = function() {
				$scope.param.deptCode = $("#deptCode").val();
				if($scope.param.stateTime>$scope.param.endTime && $scope.param.endTime!=''){
					alert("输入有误！","开始时间大于结束时间");
					return false;
				}
				$scope.loading = true;
					//方案编号小写字母转大写
				if($scope.param.schemeNum!=null){
					$scope.param.schemeNum=$scope.param.schemeNum.toUpperCase();
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
								if(v.obj.debtMain.mpc ==v1.monCode){
									v.obj.debtMain.mpc=v1.monCode + " " + v1.codeName;
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
			function initDepartment(){
				bulidDeptSelect("deptCode");
				var size = $("#deptCode").find("option").size();
				if(size == 2){
					$("#deptCode").get(0).selectedIndex = 1;
					$("#deptCode").attr('disabled',true);
				}
			}
			//初始化机构下拉框
			initDepartment();
			$scope.search();

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
					alert("请选择一个项目!");
					return false;
				}
				confirm("提示", "删除后不能恢复，您确认要执行删除吗？", function(flag) {
								if(flag) {
						$.ajax({
						type: 'PUT',
						url: '/eximbank-club/temp/delTemp',
						data: angular.toJson({
							'id': item.id
						})
					}).then(function(result) {
						if(result){
							if(result.httpCode == 200) {
								alert("删除成功!");
//								toaster.clear('*');
//                  			toaster.pop('success', '', "删除成功!");
								$scope.search();
							} else {
//								toaster.clear('*');
//                  			toaster.pop('error', '', result.msg);
								$scope.msg = result.msg;
							}
						}else{
							$scope.msg = "服务器异常，请刷新重试或联系管理员";
						}
						$rootScope.$applyAsync();
					});
					}
								})
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
					alert("请选择一个项目!");
					return false;
				}
				$state.go('main.sys.historyDebt.edit', {
					id: item.id,
					debtCode: item.bizcode
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
					/*angular.forEach($scope.currencyList, function(v, k) {
						var tt = null;
						tt = v.monCode + "   " + v.codeName;
						v.createBy = tt;
					});*/
				})

			}
			
			
			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}

	]);
angular.module('app').controller(
		'userController',
		[ '$rootScope', '$scope', '$http', '$state',
				function($rootScope, $scope, $http, $state) {
					$scope.title = '用户管理';
					$scope.param = {};
					$scope.loading = false;

					$scope.search = function() {
						$scope.loading = true;
						$.ajax({
							type : 'PUT',
							url : '/eximbank-club/user/read/list',
							data : angular.toJson($scope.param)
						}).then(function(result) {
							$scope.loading = false;
							if (result.httpCode == 200) {
								$scope.pageInfo = result;
							} else {
								$scope.msg = result.msg;
							}
							$scope.$apply();
						});
					}

					$scope.search();

					$scope.clearSearch = function() {
						$scope.param.keyword = null;
						$scope.search();
					}

					/**
					 * 启用，禁止
					 */
					$scope.disableItem = function(id, enable) {
						console.log(id);
						if (window.confirm("您确认要执行吗？")) {
							$scope.loading = true;
							$.ajax({
								type : 'POST',
								url : '/eximbank-club/user/update/state',
								data : angular.toJson({
									'id' : id,
									'enable' : enable
								})
							}).then(function(result) {
								if (result.httpCode == 200) {
									$scope.search();
								} else {
									$scope.msg = result.msg;
								}
								$scope.$apply();
							});
						}
					}

					// 设置角色权限
					$scope.bindRole = function(id) {
						$state.go('main.sys.user.bindRole', {
							userId : id
						});
					}

					$scope.search();

					$scope.clearSearch = function() {
						$scope.param.keyword = null;
						$scope.search();
					}

					// 单个选择
					$scope.selectOne = function(m, n) {
						angular.forEach($scope.pageInfo.data, function(v, k) {
							if (n == v.id) {
								v.checked = m;
							} else {
								// 如果选中一个,其余的都自动设置未选中
								if (m == true) {
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
							if (v.checked == true) {
								item = v;
							}
						});
						return item;
					}

					/**
					 * 启用，禁止
					 */
					$scope.disableItem = function(enable) {
						var item = getCheckItem();
						if (item == null) {
							alert("请选择一个项目!");
							return false;
						}
						// 可以冻结
						if (enable == 0 && item.enable != 1) {
							alert("禁止操作!");
							return false;
						} else if (enable == 1 && item.enable == 1) {
							alert("禁止操作!");
							return false;
						}
						if (window.confirm("您确认要执行吗？")) {
							$scope.loading = true;
							$.ajax({
								type : 'POST',
								url : '/eximbank-club/user/update/state',
								data : angular.toJson({
									'id' : item.id,
									'enable' : enable
								})
							}).then(function(result) {
								if (result.httpCode == 200) {
									$scope.search();
								} else {
									$scope.msg = result.msg;
								}
								$scope.$apply();
							});
						}
					}
					// 编辑账号
					$scope.edit = function() {
						var item = getCheckItem();
						if (item == null) {
							alert("请选择一个项目!");
							return false;
						}
						$state.go('main.sys.user.update', {
							id : item.id
						});
					}
					// 翻页
					$scope.pagination = function(page) {
						$scope.param.pageNum = page;
						$scope.search();
					};

				}

		]);
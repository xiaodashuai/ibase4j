'use strict';

angular.module('app')
	.controller('roleController', ['$rootScope', '$scope', '$http', '$state', 'toaster',
		function ($rootScope, $scope, $http, $state, toaster) {
			$scope.title = '岗位管理';
			$scope.param = {};
			$scope.loading = false;
			//查询岗位
			$scope.search = function () {
				$scope.loading = true;
				$.ajax({
					url: '/eximbank-console/role/read/list',
					type: 'POST',
					dataType: 'json',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					$scope.loading = false;
					if (result) {
						if (result.httpCode == 200) {
							console.log(result);
							$scope.pageInfo = result;
						} else {
							console.log(result);
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "系统走神了,请系统管理员!";
					}
					$scope.$applyAsync();
				});

			}
			$scope.query = function () {
				$scope.param.pageNum = 1;
				$.ajax({
					url: '/eximbank-console/role/read/list',
					type: 'POST',
					dataType: 'json',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					$scope.loading = false;
					if (result) {
						if (result.httpCode == 200) {
							console.log(result);
							$scope.pageInfo = result;
						} else {
							console.log(result);
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "系统走神了,请系统管理员!";
					}
					$scope.$applyAsync();
				})
			}

			$scope.search();

			$scope.clearSearch = function () {
				$scope.param.keyword = null;
				$scope.search();
			}

			//单个选择
			$scope.selectOne = function (m, n) {
				angular.forEach($scope.pageInfo.data, function (v, k) {
					if (n == v.id) {
						v.checked = m;
					} else {
						//如果选中一个,其余的都自动设置未选中
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
				angular.forEach($scope.pageInfo.data, function (v, k) {
					if (v.checked == true) {
						item = v;
					}
				});
				return item;
			}

			//启用禁用
			$scope.disableItem = function (enable) {
				var item = getCheckItem();
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				}
				//可以冻结
				if (enable == 0 && item.enable != 1) {
					alert("禁止操作!");
					return false;
				} else if (enable == 1 && item.enable == 1) {
					alert("禁止操作!");
					return false;
				}
				if (window.confirm("您确认要执行吗？")) {
					$.ajax({
						type: 'POST',
						url: '/eximbank-console/role/update/changeRole',
						data: angular.toJson({
							'id': item.id,
							'enable': enable
						})
					}).then(function (result) {
						if (result) {
							if (result.httpCode == 200) {
								toaster.clear('*');
								toaster.pop('success', '', "操作成功");
								location.reload(true);
							} else {
								$scope.msg = result.msg;
							}
						} else {
							$scope.msg = "系统走神了,请系统管理员!";
						}
						$scope.$applyAsync();
					})
				}
			}

			//编辑账号
			$scope.edit = function () {
				var item = getCheckItem();
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				}
				//默认业务权限类型 
				var editURL = "main.sys.role.modify";
				if (item.roleType == 2) {
					editURL = "main.sys.role.update";
				}
				//跳转页面
				$state.go(editURL, {
					id: item.id
				});
			}

			//组织机构下拉框
			function getOrgSelectBox(id) {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/dept/read/queryOrgList',
					data: angular.toJson({
						'type': 0
					})
				}).then(function (result) {
					$scope.loading = false;
					if (result) {
						if (result.httpCode == 200) {
							$scope.orgList = result.data;
						} else {
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "系统走神了,请系统管理员!";
					}
					$scope.$applyAsync();
				})
			}

			// 翻页
			$scope.pagination = function (page) {
				$scope.param.pageNum = page;
				$scope.search();
			};
		}
	]);
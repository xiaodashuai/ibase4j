'use strict';

angular.module('app')
	.controller('chargeTypeController', ['$rootScope', '$scope', '$http', '$state', '$window', 'toaster',
		function ($rootScope, $scope, $http, $state, $window, toaster) {
			$scope.title = '收费类型管理';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function () {
				$scope.loading = true;
				$.ajax({
					url: '/eximbank-console/chargeType/read/list',
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json;charset=UTF-8',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.pageInfo = result;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				})
			}
			$scope.query = function () {
				$scope.loading = true;
				$scope.param.pageNum = 1;
				$.ajax({
					url: '/eximbank-console/chargeType/read/list',
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json;charset=UTF-8',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.pageInfo = result;
					} else {
						$scope.msg = result.msg;
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
			//修改
			$scope.edit = function () {
				var item = getCheckItem();
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				}
				$state.go('main.post.chargeType.update', {
					id: item.id
				});
			}
			//新增
			$scope.create = function () {
				var item = getCheckItem();
				if (item != null) {
					alert("请取消选中项再进行新增操作!");
					return false;
				}
				$state.go('main.post.chargeType.create', {

				});
			}
			//删除
			$scope.delete = function () {
				var item = getCheckItem();
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				} else {
					if ($window.confirm("是否确认删除该条数据？")) {
						$.ajax({
							type: 'POST',
							url: '/eximbank-console/chargeType/delete',
							data: angular.toJson({
								'id': item.id
							})
						}).then(function (result) {
							if (result.httpCode == 200) {
								$scope.search();
							} else {
								$scope.msg = result.msg;
							}
							$scope.$applyAsync();
						})
					}

				}
			}
			// 翻页
			$scope.pagination = function (page) {
				$scope.param.pageNum = page;
				$scope.search();
			};
		}
	]);
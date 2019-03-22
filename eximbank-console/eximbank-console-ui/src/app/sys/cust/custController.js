'use strict';

angular.module('app')
	.controller('custController', ['$rootScope', '$scope', '$http', '$state',
		function ($rootScope, $scope, $http, $state) {
			$scope.title = '客户信息查询';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function () {
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/cust/read/list',
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
				$scope.param.pageNum = 1;

				$.ajax({
					type: 'POST',
					url: '/eximbank-console/cust/read/list',
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
				if ($scope.pageInfo != null) {
					angular.forEach($scope.pageInfo.data, function (v, k) {
						if (v.checked == true) {
							item = v;
						}
					});
				} else {
					alert("抱歉，没有相关数据！");
					return "error";
				}
				return item;
			}
			$scope.edit = function () {
				var item = getCheckItem();
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				} else if (item == "error") {
					return null;
				}
				$state.go('main.sys.cust.detail', {
					id: item.id
				});
			}

			// 翻页
			$scope.pagination = function (page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}

	]);
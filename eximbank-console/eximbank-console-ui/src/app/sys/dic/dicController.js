'use strict';

angular.module('app')
	.controller('dicController', ['$rootScope', '$scope', '$http', '$state',
		function ($rootScope, $scope, $http, $state) {
			$scope.title = '二级码表';
			$scope.param = {};
			$scope.loading = false;
			$scope.search = function () {
				$scope.loading = true;
				var type = $state.params.code;
				$scope.param.type = type;

				$.ajax({
					url: '/eximbank-console/dic/read/query/type',
					type: 'POST',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.pageInfo = result;
						$scope.itemType = type;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				})
			}
			$scope.query = function () {
				$scope.loading = true;
				var type = $state.params.code;
				$scope.param.type = type;
				$scope.param.pageNum = 1;
				$.ajax({
					url: '/eximbank-console/dic/read/query/type',
					type: 'POST',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.pageInfo = result;
						$scope.itemType = type;
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
			$scope.disableItem = function (id, enable) {}
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

			//编辑账号
			$scope.edit = function () {
				var item = getCheckItem();
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				}
				$state.go('main.sys.dic.update', {
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
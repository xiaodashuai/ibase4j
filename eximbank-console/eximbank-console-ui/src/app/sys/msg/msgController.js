'use strict';

angular.module('app')
	.controller('msgController', ['$rootScope', '$scope', '$http', '$state',
		function ($rootScope, $scope, $http, $state) {
			$scope.title = '短信发送记录';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function () {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/msg/read/list',
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


			// 翻页
			$scope.pagination = function (page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}

	]);
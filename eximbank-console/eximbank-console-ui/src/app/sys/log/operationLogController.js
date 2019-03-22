'use strict';

angular.module('app')
	.controller('operationLogController', ['$rootScope', '$scope', '$http', '$state',
		function ($rootScope, $scope, $http, $state) {
			$scope.title = '操作日志';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function () {
				$scope.loading = true;
				$.ajax({
					url: '/eximbank-console/operationLog/read/list',
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
			// 翻页
			$scope.pagination = function (page) {
				$scope.param.pageNum = page;
				$scope.search();
			};
		}
	]);
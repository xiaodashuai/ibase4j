'use strict';

angular.module('app')
	.controller('processDesignController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
		function ($scope, $rootScope, $state, $timeout, toaster) {
			var title = "";
			$scope.title = $rootScope.title = title;
			$scope.loading = true;

			//初始化
			function initSrc() {
				$.ajax({
					type: 'POST',
					url: 'eximbank-console/matrix/read/getFrameSrc',
				}).then(function (result) {
					if (result) {
						if (result.httpCode == 200) {
							$scope.record = result.data;
						} else {
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "系统走神了,请系统管理员!";
					}
					$scope.$applyAsync();
				})
			}
			//initSrc();

		}
	]);
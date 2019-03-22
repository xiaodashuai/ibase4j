'use strict';

angular.module('app')
	.controller('loginController', ['$rootScope', '$scope', '$http', '$state', function ($rootScope, $scope, $http, $state) {
		$scope.user = {};
		$scope.login = function () {

			$.ajax({
				type: 'POST',
				url: '/eximbank-console/login',
				data: angular.toJson($scope.user)
			}).then(function (result) {
				if (result) {
					if (result.httpCode == 200) {
						$state.go('main.sys.emailTemplate.list');
					} else {
						$scope.msg = result.msg;
						$rootScope.$applyAsync();
					}
				} else {
					$scope.msg = "系统走神了，请联系系统管理员！";
				}
			})
		}
	}]);
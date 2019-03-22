'use strict';

angular.module('app')
	.controller('obligationGrantController', [ '$rootScope', '$scope', '$http', '$state','toaster',
	                                function($rootScope, $scope, $http, $state,toaster) {
		$scope.title = '我的待办';
        $scope.param = { };
        $scope.loading = false;


		$scope.search = function () {
			$scope.loading = true;
			$.ajax({
				url : '/eximbank-club/uploadTest/getFileMenu',
				type: 'POST',
				dataType: 'json',
				contentType:'application/json;charset=UTF-8',
				data: angular.toJson($scope.param)
			}).then(function(result) {
				$scope.loading = false;
				if (result.httpCode == 200) {
					$scope.fileNames = result.data;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		}



        $scope.search();

		$scope.disableItem = function(fileName) {

            window.location.href="http://127.0.0.1:8080/lib/generic/web/abc/"+fileName;
		}



} ]);
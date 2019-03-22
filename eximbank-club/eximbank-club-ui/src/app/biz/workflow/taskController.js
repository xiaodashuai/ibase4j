'use strict';

angular.module('app')
	.controller('taskController', [ '$rootScope', '$scope', '$http', '$state',
	                                function($rootScope, $scope, $http, $state) {
        $scope.param = {};
        $scope.param.pageNumber = 1;
		$scope.debt = true;
		$scope.grant = false;
		$scope.getGrantToDoTaskPage = function (){
            $scope.debt = false;
            $scope.grant = true;
            $scope.loading = true;
			$.ajax({
				url : '/eximbank-club/workflow/read/getGrantToDoTask',
				type: 'POST',
				data: angular.toJson({'data':$scope.param})
			}).then(function(result) {
				$scope.loading = false;
				if (result.httpCode == 200) {
					$scope.pageInfoToDoGrantTask = result.data.toGrantDoTaskPage;

				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		}
		$scope.getSchemeToDoTaskPage = function (){
            $scope.debt = true;
            $scope.grant = false;
            $scope.loading = true;
			$.ajax({
				url : '/eximbank-club/workflow/read/getSchemeToDoTask',
				type: 'POST',
				data: angular.toJson({'data':$scope.param})
			}).then(function(result) {
				$scope.loading = false;
				if (result.httpCode == 200) {
					$scope.pageInfoToDoSchemeTask = result.data.toSchemeDoTaskPage;

				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		}


		// 查询发放已办任务
		$scope.getGrantHaveDoneTaskPage = function (){
            $scope.loading = true;
            $scope.debt = false;
            $scope.grant = true;
			$.ajax({
				url : '/eximbank-club/workflow/read/getGrantHaveDoneTask',
				type: 'POST',
				data: angular.toJson({'data':$scope.param})
			}).then(function(result) {
				$scope.loading = false;
				if (result.httpCode == 200) {
					$scope.pageInfoHaveDoneGrantTask = result.data.haveGrantDonePage;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		}
		// 查询方案已办任务
		$scope.getSchemeHaveDoneTaskPage = function (){
            $scope.debt = true;
            $scope.grant = false;
            $scope.loading = true;
			$.ajax({
				url : '/eximbank-club/workflow/read/getSchemeHaveDoneTask',
				type: 'POST',
				data: angular.toJson({'data':$scope.param})
			}).then(function(result) {
				$scope.loading = false;
				if (result.httpCode == 200) {
					$scope.pageInfoHaveDoneSchemeTask = result.data.haveSchemeDonePage;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		}
		// 翻页
		$scope.schemeToDoTaskPagination = function(page) {
			$scope.param.pageNumber = page;
			$scope.getSchemeToDoTaskPage();
		};
		// 翻页
		$scope.grantToDoTaskPagination = function(page) {
			$scope.param.pageNumber = page;
			$scope.getGrantToDoTaskPage();
		};
		// 翻页
		$scope.schemeHaveDoneTaskPagination = function(page) {
			$scope.param.pageNumber = page;
			$scope.getSchemeHaveDoneTaskPage();
		};
		// 翻页
		$scope.grantHaveDoneTaskPagination = function(page) {
			$scope.param.pageNumber = page;
			$scope.getGrantHaveDoneTaskPage();
		};


		$scope.getSchemeToDoTaskPage();
		$scope.getSchemeHaveDoneTaskPage();
} ]);

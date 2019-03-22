'use strict';

angular.module('app')
	.controller('obligationGrantForFQCSJBController', [ '$rootScope', '$scope', '$http', '$state',
	                                function($rootScope, $scope, $http, $state) {
		$scope.title = '我的待办';
        $scope.param = { };
        $scope.loading = false;
		// 保存数据
		$scope.submit= function(){


			$state.go('main.biz.grant.obligationGrantForFQCSCZ');

		}

} ]);
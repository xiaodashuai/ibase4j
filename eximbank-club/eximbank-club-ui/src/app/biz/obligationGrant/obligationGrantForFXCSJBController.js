'use strict';

    angular.module('app')
        .controller('obligationGrantForFXCSJBController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
        var title = "";
		$scope.title = $rootScope.title = title;
        $scope.loading = true;
		$scope.record = {};
		// 根据前手提交的债项id查询出所有的产品id
		$scope.products=[
            {'productId':'31'},
			{'productId':'32'}];
		$scope.initProductId = $scope.products[0].productId;

         $scope.submit= function(){

             $state.go('main.biz.grant.obligationGrantForFXCSCZ');

         }

    }]);

 
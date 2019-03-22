'use strict';

    angular.module('app')
        .controller('obligationGrantForFXCSCZController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
                                             function($scope, $rootScope, $state, $timeout, toaster) {
         $scope.title = '';
         $scope.param = { };
         $scope.loading = false;
         // 保存数据
         $scope.submit= function(){
             // 业务相关数据
             var m = $scope.record;
             if(m){
                 if(m.commment==1){
                     $state.go('main.biz.grant.obligationGrantForMJCSJB');
                 }else if(m.commment==2){
                     $state.go('main.biz.grant.obligationGrantForFQCSJB');
                 }
             }

         }

    }]);

 
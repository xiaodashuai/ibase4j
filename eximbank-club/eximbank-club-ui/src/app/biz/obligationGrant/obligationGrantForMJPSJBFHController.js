'use strict';

angular.module('app')
	.controller('obligationGrantForMJPSJBFHController', [ '$rootScope', '$scope', '$http', '$state',
	                                function($rootScope, $scope, $http, $state) {
		$scope.title = '我的待办';
        $scope.param = { };
        $scope.loading = false;
		// 保存数据
		$scope.submit= function(){
			// 业务相关数据
			var m = $scope.record;
			if(m){
				if(m.commment==1){
					$state.go('main.biz.grant.obligationGrantForMJPSCZFH');
				}else if(m.commment==2){
					$state.go('main.biz.grant.obligationGrantForMJSCBLFJ');
				}
			}

		}

} ]);
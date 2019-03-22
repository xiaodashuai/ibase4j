angular.module('app').controller(
	'grantInfoDetailController', ['$rootScope', '$scope', '$http', '$state','$timeout','toaster','$filter',
		function($rootScope, $scope, $http, $state,$timeout,toaster,$filter) {
            $scope.title = '发放条件台账详情';
			$scope.param = {};
			$scope.loading = false;
			
			function getGrantAMTDetail() {
				var grantCode = $state.params.grantCode;
				var debtCode = $state.params.debtCode;
				$.ajax({
					url : '/eximbank-club/standingBook/read/getGrantAMTDetail',
					type: 'PUT',
					data: angular.toJson({'grantCode':grantCode,'debtCode':debtCode})
				}).then(function(result){
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.amtList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}
            getGrantAMTDetail();

            $scope.back = function () {
                $state.go("main.biz.makeloans.grant");
            }
		}
	]);
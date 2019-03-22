angular.module('app').controller(
	'debtInfoDetailController', ['$rootScope', '$scope', '$http', '$state','$timeout','toaster','$filter',
		function($rootScope, $scope, $http, $state,$timeout,toaster,$filter) {
			var dateFilter = $filter('date');
            $scope.title = '发案审核台账详情';
			$scope.param = {};
			$scope.loading = false;

			
			function getDebtInfoForStandingBook() {
				var debtCode = $state.params.debtCode;
				$.ajax({
					url : '/eximbank-club/standingBook/read/getDebtInfoForStandingBook',
					type: 'PUT',
					data: angular.toJson({'debtCode':debtCode})
				}).then(function(result){
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.dataMap = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}
            //getDebtInfoForStandingBook();
		
		}
	]);
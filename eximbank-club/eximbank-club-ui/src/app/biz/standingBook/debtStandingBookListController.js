angular.module('app').controller(
	'debtStandingBookListController', ['$rootScope', '$scope', '$http', '$state','$timeout','toaster','$filter',
		function($rootScope, $scope, $http, $state,$timeout,toaster,$filter) {
			var dateFilter = $filter('date');
			$scope.title = '方案信息台账列表';
			$scope.param = { };
			$scope.loading = false;

            $scope.getDebtStandingBookList = function() {
				$.ajax({
					url : '/eximbank-club/standingBook/read/debtStandingBookList',
					type: 'PUT',
					data: angular.toJson($scope.param)
				}).then(function(result){
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.pageInfo = result;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}
            $scope.getDebtStandingBookList();
		
		}
	]);
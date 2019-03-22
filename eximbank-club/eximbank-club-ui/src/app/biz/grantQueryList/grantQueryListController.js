angular.module('app')
	.controller('grantQueryListController', ['$scope', '$rootScope','$http', '$state', '$stateParams', '$timeout',
		function($scope, $rootScope, $http,$state, $stateParams, $timeout) {
			$scope.title = '发放条件查询';
			$scope.loading = false;
			$scope.param = {};
			
			
			$scope.search = function() {
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/grant/grantQueryList',
					data: angular.toJson($scope.param)
				}).then(function(result) {
					$scope.loading = false;
					if(result){
						if(result.httpCode == 200) {
							$scope.pageInfo = result;
						} else {
							$scope.msg = result.msg;
						}
					}else{
						$scope.msg = "服务器异常，请刷新重试或联系管理员";
					}
					$scope.$apply();
				});
			}
			
			$scope.search();
			
			$scope.clearSearch = function() {
				$scope.param.keyword = null;
				$scope.search();
			}
			
			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

			
			
			

		}
	]);
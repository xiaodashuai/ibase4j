angular.module('app').controller(
	'makeloansController', ['$rootScope', '$scope', '$http', '$state','$timeout','toaster','$stateParams','WebUploadService',
		function($rootScope, $scope, $http, $state,$timeout,toaster,$stateParams,WebUploadService) {
			$scope.title = '发放列表';
			$scope.param = {};
			$scope.loading = false;

            $scope.param.grantCode = $stateParams.grantCode;
            $scope.param.productName = $stateParams.productName;
            $scope.param.proposer = $stateParams.proposer;
            $scope.param.beginDate = $stateParams.beginDate;
            $scope.param.endDate = $stateParams.endDate;

            $scope.getMakeLoansDebts = function() {
				$.ajax({
					url : '/eximbank-club/makeLoans/read/getMakeLoansDebts',
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
            $scope.getMakeLoansDebts();
            // 翻页
            $scope.pagination = function(page) {
                $scope.param.pageNum = page;
                $scope.getMakeLoansDebts();
            };
            // 搜索查询
            $scope.queryMakeLoansDebts = function () {
                $scope.param.pageNum = {};
                $scope.getMakeLoansDebts();
            };

            $scope.webUploadStatus = 0;
            /**
             * 功能：预览附件
             * @param {Object} code
             * @param {Object} index
             */
            $scope.openWindow = function(code, index) {
                if($scope.webUploadStatus == 0) { //第一次点击任意一笔
                    WebUploadService.initFiles([code, code], ["B", "B"], ["租金保理", "租金保理"],["zjblfa", "zjblsc"], $scope, $timeout,true);
                    $scope.idd = index;
                    $scope.webUploadStatus++;
                    return false;
                }
                if($scope.webUploadStatus != 0) {
                    if($scope.idd == index) { //第N次点击同一笔
                        return false;
                    } else { //第二次点击不同笔
                        $scope.realFileName = ""; //清空文件名
                        $scope.nclosureName = ""; //清空文件名
                        angular.element('.filelist').remove(); //清空文件list
                        WebUploadService.initFiles([code, code], ["B", "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout,true);
                        $scope.idd = index;
                    }
                }
            }
		}
	]);
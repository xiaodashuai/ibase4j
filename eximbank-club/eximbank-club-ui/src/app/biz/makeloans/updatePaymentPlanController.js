angular.module('app').controller(
    'updatePaymentPlanController', ['$rootScope', '$scope', '$http', '$state', '$timeout', '$filter',
        function ($rootScope, $scope, $http, $state, $timeout, $filter) {
            var dateFilter = $filter('date');
            $scope.title = '方案详情';
            $scope.param = {};
            $scope.record = {};

            // 获取还款计划详情
            function getRepaymentInfo() {
                var grantCode = $state.params.grantCode;
                var objInr = $state.params.objInr;
                var debtCode = $state.params.debtCode;
                var businessTypes = $state.params.businessTypes;
                $.ajax({
                    url: '/eximbank-club/makeLoans/read/getRepaymentInfo',
                    type: 'PUT',
                    data: angular.toJson({
                        'grantCode': grantCode,
                        'businessTypes': businessTypes,
                        'objInr': objInr,
                        'debtCode': debtCode
                    })
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.dataMap = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }

            getRepaymentInfo();

            // 发送还款计划
            $scope.submit = function () {
                var grantCode = $state.params.grantCode;
                var objInr = $state.params.objInr;
                var debtCode = $state.params.debtCode;
                var businessTypes = $state.params.businessTypes;
                //设置不能重复提交
                $scope.isDisabled = true; //提交disabled
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-club/makeLoans/update/updateRepaymentPlan',
                    data: angular.toJson({
                        'grantCode': grantCode,
                        'businessTypes': businessTypes,
                        'objInr': objInr,
                        'debtCode': debtCode
                    })
                }).then(function (result) {
                    if (result.httpCode == 200) {
                        alert("提示",result.data.result);
                        $timeout(function () {
                            $state.go('main.biz.makeloans.list');
                        }, 2000);
                    } else {
                        $scope.msg = result.msg;
                        alert("提示",$scope.msg);
                        $scope.isDisabled = false;
                    }
                    $scope.$apply();
                });
            }

            $scope.back = function () {
                $state.go("main.biz.makeloans.list");
            }
        }
    ]);
angular.module('app').controller(
    'makeloansDetailController', ['$rootScope', '$scope', '$http', '$state', '$timeout', '$filter',
        function ($rootScope, $scope, $http, $state, $timeout, $filter) {
            var dateFilter = $filter('date');
            $scope.title = '方案详情';
            $scope.param = {};
            var currentTime = dateFilter(new Date(), 'yyyy-MM-dd');
            $scope.record = {};

            // 获取方案详情
            function getMakeloansDetail() {
                var grantCode = $state.params.grantCode;
                var objInr = $state.params.objInr;
                var debtCode = $state.params.debtCode;
                var businessTypes = $state.params.businessTypes;
                $.ajax({
                    url: '/eximbank-club/makeLoans/read/getMakeLoansDebtInfo',
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
                        $scope.record.currentTime = currentTime;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }

            getMakeloansDetail();

            // 发放
            $scope.submit = function () {
                // TODO 校验发放时间是否在有效期内
                var grantCode = $scope.dataMap.debtInfoForMakeLoan.grantCode;
                var iouCode = $scope.dataMap.debtInfoForMakeLoan.iouCode;
                var identNumber = $scope.dataMap.debtInfoForMakeLoan.identNumber;
                var objInr = $scope.dataMap.debtInfoForMakeLoan.objInr;
                var debtInfoForMakeLoan = $scope.dataMap.debtInfoForMakeLoan;

                var bTime = $scope.dataMap.debtInfoForMakeLoan.sendDate;
                var eTime = $scope.dataMap.debtInfoForMakeLoan.endDate;

                var flag = nowInDateBetwen(bTime,eTime);
                if(!flag){
                    alert("提示","发放时间不在有效期内");
                    return;
                }
                //设置不能重复提交
                $scope.isDisabled = true; //提交disabled
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-club/makeLoans/update/requestMakeLoansInterface',
                    data: angular.toJson({'grantCode': grantCode,'iouCode':iouCode,'identNumber':identNumber,'objInr':objInr,'debtInfoForMakeLoan':debtInfoForMakeLoan})
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

            function nowInDateBetwen (d1,d2) {
                //如果时间格式是正确的，那下面这一步转化时间格式就可以不用了
                // var dateBegin = new Date(d1.replace(/-/g, "/"));//将-转化为/，使用new Date
                // var dateEnd = new Date(d2.replace(/-/g, "/"));//将-转化为/，使用new Date

                var dt = new DateUtil();
                var dateBegin = dt.createDate(new Date(d1),'yy-mm-dd'); //将-转化为/，使用new Date
                var dateEnd = dt.createDate(new Date(d2),'yy-mm-dd');//将-转化为/，使用new Date
                // 当前时间
                var dateNow = dt.nowDate('yy-mm-dd');

                var beginDayDiff = dt.DateDiff(dateNow,dateBegin);
                var endDayDiff = dt.DateDiff(dateEnd,dateNow);
                if (endDayDiff < 0) {//已过期
                    return false
                }
                if (beginDayDiff < 0) {//没到开始时间
                    return false;
                }
                return true;
            }

            $scope.back = function () {
                var grantCode = $state.params.grantCodes;
                var productName = $state.params.productName;
                var proposer = $state.params.proposer;
                var beginDate = $state.params.beginDate;
                var endDate = $state.params.endDate;
                $state.go("main.biz.makeloans.list",{'grantCode' : grantCode,'productName' : productName,'proposer':proposer,'beginDate':beginDate,'endDate':endDate});
            };
        }
    ]);
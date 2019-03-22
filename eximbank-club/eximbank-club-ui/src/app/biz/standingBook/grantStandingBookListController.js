angular.module('app').controller(
    'grantStandingBookListController', ['$rootScope', '$scope', '$http', '$state', '$timeout', 'toaster', '$filter',
        function ($rootScope, $scope, $http, $state, $timeout, toaster, $filter) {
            var dateFilter = $filter('date');
            $scope.title = '发放审核台账列表';
            $scope.param = {};
            $scope.loading = false;

            $scope.getGrantStandingBookList = function () {
                $.ajax({
                    url: '/eximbank-club/standingBook/read/grantStandingBookList',
                    type: 'PUT',
                    data: angular.toJson($scope.param)
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.pageInfo = result;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }

            $scope.getDeptList = function (){
                $.ajax({
                    url: '/eximbank-club/standingBook/read/getDeptList',
                    type: 'PUT',
                    data: angular.toJson($scope.param)
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.deptList = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }
            $scope.getDeptList();
            $scope.getGrantStandingBookList();

            $scope.exportExcel = function () {
                //设置不能重复提交
                $scope.isDisabled = true; //提交disabled
                $http({
                    url: '/eximbank-club/standingBook/read/exportExcelForAMTDetail',
                    withCredentials: true,
                    method: "PUT",//接口方法
                    headers: {
                        'Content-type': 'application/json'
                    },
                    data: angular.toJson($scope.param),
                    responseType: 'arraybuffer'
                }).success(function (data) {
                    var blob = new Blob([data], {type: "application/vnd.ms-excel;charset=utf-8"});
                    var objectUrl = URL.createObjectURL(blob);
                    var a = document.createElement('a');
                    document.body.appendChild(a);
                    a.setAttribute('style', 'display:none');
                    a.setAttribute('href', objectUrl);
                    var filename="金额流水.xlsx";
                    a.setAttribute('download', filename);
                    a.click();
                    URL.revokeObjectURL(objectUrl);
                    $scope.isDisabled = false;
                })
            }
            // 翻页
            $scope.pagination = function(page) {
                $scope.param.pageNum = page;
                $scope.getGrantStandingBookList();
            };

            // 搜索查询
            $scope.queryGrantStandingBookList = function () {
                $scope.param.pageNum = {};
                $scope.getGrantStandingBookList();
            };

        }
    ]);
'use strict';

angular.module('app')
    .controller('dataRuleModalController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
        function ($scope, $rootScope, $state, $timeout, toaster) {
            $scope.title = '选择数据权限';
            $scope.loading = false;
            var userId = $state.params.userId;
            getUserDataRuleList(userId);

            //初始化页面
            function getUserDataRuleList(userId) {
                $scope.loading = true;
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/dataRule/read/getUserDataRuleList',
                    data: angular.toJson({
                        'userId': userId
                    })
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        if (result.iTotalRecords > 0) {
                            $scope.list = result.data;
                            angular.forEach($scope.list, function (v, k) {
                                if (v.checked == 'true') {
                                    v.checked = true;
                                } else {
                                    v.checked = false;
                                }
                            })
                        }
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$applyAsync();
                })
            }

            //提交保存
            $scope.submitForm = function () {
                var userId = $state.params.userId;
                var m = getCheckIds();
                if (m.length > 0) {
                    $scope.isDisabled = true; //提交disabled
                    $.ajax({
                        url: '/eximbank-console/dataRule/read/saveUserDataRule',
                        type: 'POST',
                        data: angular.toJson({
                            'DataRuleIds': m,
                            'userId': userId
                        })
                    }).then(callback)
                } else {
                    toaster.clear('*');
                    toaster.pop('success', '', "请至少选择一条数据");
                    $scope.isDisabled = false;
                }

                function callback(result) {
                    if (result.httpCode == 200) { //成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        setTimeout(function () {
                            $state.go('main.sys.user.list');
                        }, 1000);
                    } else {
                        toaster.clear('*');
                        toaster.pop('error', '', result.msg);
                        $scope.isDisabled = false;
                    }
                }
            }
            //获取选中的数据权限的id 
            function getCheckIds() {
                var idStr = '';
                angular.forEach($scope.list, function (v, k) {
                    if (v.checked == true) {
                        idStr += v.dataruleID + ",";
                    }
                });
                console.log(idStr);
                return idStr;
            }
            //全选 事件
            $scope.all = function () {
                angular.forEach($scope.list, function (v, k) { //v是回写的每个vo，
                    v.checked = $scope.selectAll;
                })
            }
            //单个选择按钮
            $scope.selectOne = function (m, n) {
                angular.forEach($scope.list, function (v, k) {
                    if (n == v.dataruleID) {
                        v.checked = m;
                    }
                })
            }



        }
    ]);
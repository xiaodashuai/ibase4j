'use strict';

angular.module('app')
    .controller('addVisitJurisdictionController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
        function ($scope, $rootScope, $state, $timeout, toaster) {
            $scope.title = '选择机构拥有的访问权限';
            $scope.loading = false;
            var deptId = $state.params.id; //得到机构id
            getDeptDataRuleList(deptId);

            //初始化页面
            function getDeptDataRuleList(deptId) {
                $scope.loading = true;

                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/dataRule/read/getDeptDatarule',
                    data: angular.toJson({
                        'deptId': deptId
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
                var deptId = $state.params.id;
                var m = getCheckIds();
                if (m.length > 0) {
                    $scope.isDisabled = true; //提交disabled

                    $.ajax({
                        url: '/eximbank-console/dataRule/read/saveDeptDataRule',
                        type: 'POST',
                        data: angular.toJson({
                            'dataRuleIds': m,
                            'deptId': deptId
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
                            $state.go('main.sys.dept.list');
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
'use strict';

angular.module('app')
    .controller('updateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
        function ($scope, $rootScope, $state, $timeout, toaster) {
            var title = "";
            $scope.title = title;
            if ($state.includes('**.emailTemplate.update')) {
                title = "模板修改";
                var id = $state.params.id;
                getOrgSelectBox();
                activate(id);
                validate();
            } else if ($state.includes('**.emailTemplate.create')) {
                title = "模板新增";
                getOrgSelectBox();
                validate();
            }
            $scope.title = title;
            $scope.submit = function () {
                var m = $scope.record;
                if (m) {
                    $scope.isDisabled = true; //提交disabled
                    $.ajax({
                        url: '/eximbank-console/emailTemplate/save',
                        type: 'POST',
                        data: angular.toJson($scope.record)
                    }).then(callback)
                }

                function callback(result) {
                    if (result.httpCode == 200) { //成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        $timeout(function () {
                            $state.go('main.sys.emailTemplate.list');
                        }, 2000);
                    } else {
                        toaster.clear('*');
                        toaster.pop('error', '', result.msg);
                        $scope.isDisabled = false;
                    }
                }
            }
            // 初始化页面
            function activate(id) {
                $scope.loading = true;

                $.ajax({
                    url: '/eximbank-console/emailTemplate/read/detail',
                    type: 'POST',
                    data: angular.toJson({
                        'id': id
                    })
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.record = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$applyAsync();
                })
            }
            //组织机构下拉框
            function getOrgSelectBox() {
                $scope.loading = true;

                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/dept/read/queryOrgList',
                    data: angular.toJson()
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.orgList = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$applyAsync();
                })
            }
            //表单验证
            function validate() {
                jQuery('form').validate({
                    rules: {},
                    messages: {},
                    submitHandler: function () {
                        $scope.submit();
                    }
                });
            }

        }
    ]);
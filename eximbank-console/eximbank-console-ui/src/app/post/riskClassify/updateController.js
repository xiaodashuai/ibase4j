'use strict';

angular.module('app')
    .controller('riskClassifyUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', '$stateParams',
        function ($scope, $rootScope, $state, $timeout, toaster, $stateParams) {
            var title = "";
            var defaultAva = $rootScope.defaultAvatar;
            var id = $stateParams.id;

            if ($state.includes('**.riskClassify.update')) {
                title = "初分类规则修改";
                //console.log("------"+unitId);
                activate(id);
                //组织机构初始化
                getOrgSelectBox();
                validate();
            } else if ($state.includes('**.riskClassify.add')) {
                title = "初分类规则新增";
                validate();
                //组织机构初始化
                getOrgSelectBox();
            }
            $scope.title = $rootScope.title = title;
            $scope.loading = true;
            //初始化验证
            //validate($scope);
            $scope.submit = function () {
                var m = $scope.record;
                if (m) {
                    var id = m.id;
                    var doURL = "/eximbank-console/riskClassify";
                    if (id != undefined) {
                        doURL = "/eximbank-console/riskClassify";
                    } else {
                        doURL = "/eximbank-console/riskClassify/save";
                    }
                    $scope.isDisabled = true; //提交disabled
                    $.ajax({
                        type: 'POST',
                        url: doURL,
                        data: angular.toJson($scope.record)
                    }).then(callback)
                }

                function callback(result) {
                    if (result.httpCode == 200) { //成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        $timeout(function () {
                            $state.go('main.post.riskClassify.list');
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
                    type: 'POST',
                    url: '/eximbank-console/riskClassify/read/detail',
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
            function getOrgSelectBox(id) {
                $scope.loading = true;
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/riskClassify/read/queryOrgList',
                    data: angular.toJson({
                        'enable': 1
                    })
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
            function validate(userId) {
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
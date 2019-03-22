'use strict';

angular.module('app')
    .controller('classifyLevelUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', '$stateParams',
        function ($scope, $rootScope, $state, $timeout, toaster, $stateParams) {
            var title = "";

            if ($state.includes('**.classifyLevel.update')) {
                title = "分类等级修改";
                var id = $stateParams.id;
                var unitId = $stateParams.unitid;
                //console.log("------"+unitId);
                activate(id);
                //组织机构初始化
                getOrgSelectBox(unitId);
                validate(id);
                getActiveSelectBox();
            } else if ($state.includes('**.classifyLevel.add')) {
                title = "分类等级新增";
                validate(null);
                //组织机构初始化
                getOrgSelectBox(null);
                getActiveSelectBox();
            }
            $scope.title = $rootScope.title = title;
            $scope.loading = true;
            //初始化验证
            //validate($scope);
            $scope.submit = function () {
                var m = $scope.record;
                if (m) {
                    var id = m.id;
                    var doURL = "/eximbank-console/classifyLevel";
                    if (id != undefined) {
                        doURL = "/eximbank-console/classifyLevel";
                    } else {
                        doURL = "/eximbank-console/classifyLevel/save";
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
                            $state.go('main.post.classifyLevel.list');
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
                    url: '/eximbank-console/classifyLevel/read/detail',
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
                    url: '/eximbank-console/classifyLevel/read/queryOrgList',
                    data: angular.toJson({
                        'type': 0
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
            //获取字典(有效性)
            function getActiveSelectBox() {
                $scope.loading = true;
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/dic/read/query',
                    data: angular.toJson({
                        'type': 'LOCKED'
                    })
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.dicList = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$applyAsync();
                })
            }
            //表单验证
            function validate(userId) {
                jQuery('form').validate({
                    rules: {
                        classifyLevelName: {
                            required: true,
                            stringCheck: [],
                            maxLengthB: [20]
                        },
                        sortNo: {
                            required: true
                        }
                    },
                    messages: {
                        classifyLevelName: {
                            required: '请填写组名称',
                            maxLengthB: "组名称不得超过{0}个字符"
                        },
                        sortNo: {
                            required: '请填写排序'
                        }
                    },
                    submitHandler: function () {
                        $scope.submit();
                    }
                });
            }

        }
    ]);
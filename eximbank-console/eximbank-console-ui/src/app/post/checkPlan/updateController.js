'use strict';

angular.module('app')
    .controller('checkPlanUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', '$stateParams',
        function ($scope, $rootScope, $state, $timeout, toaster, $stateParams) {
            var title = "检查计划配置管理";
            var defaultAva = $rootScope.defaultAvatar;


            if ($state.includes('**.checkPlan.update')) {
                title = "检查计划修改";
                var id = $stateParams.id;
                var classifyid = $stateParams.classifyid;
                //console.log("------"+unitId);
                getClassifySelectBox(classifyid);
                activate();
                //组织机构初始化

                validate();
                // getActiveSelectBox();
            } else if ($state.includes('**.checkPlan.add')) {
                title = "检查计划新增";
                getClassifySelectBox();
                validate();
                //组织机构初始化

                // getActiveSelectBox();

            }
            $scope.title = $rootScope.title = title;
            $scope.loading = true;
            //初始化验证
            //validate($scope);
            $scope.submit = function () {
                var m = $scope.record;
                if (m) {
                    var id = m.id;
                    var doURL;
                    if (id != undefined) {
                        doURL = "/eximbank-console/checkPlan";
                    } else {
                        doURL = "/eximbank-console/checkPlan/update/updateCheckPlan";
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
                            $state.go('main.post.checkPlan.list');
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
                    url: '/eximbank-console/checkPlan/read/detail',
                    data: angular.toJson({
                        'id': $stateParams.id
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
            //风险分类等级下拉框
            function getClassifySelectBox() {
                $scope.loading = true;
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/checkPlan/read/queryClassifyList',
                    data: angular.toJson({})
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.classifyList = result.data;
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
                    messages: {

                    },
                    submitHandler: function () {
                        $scope.submit();
                    }
                });
            }

        }
    ]);
'use strict';

angular.module('app')
    .controller('countryRiskRatingUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', '$stateParams',
        function ($scope, $rootScope, $state, $timeout, toaster, $stateParams) {
            var title = "国别风险等级修改";
            var defaultAva = $rootScope.defaultAvatar;
            var id = $stateParams.id;
            var classifyid = $stateParams.riskLevel;
            getClassifySelectBox();
            activate();
            //组织机构初始化
            validate();
            // getActiveSelectBox();

            $scope.title = $rootScope.title = title;
            $scope.loading = true;
            //初始化验证
            //validate($scope);
            $scope.submit = function () {
                var m = $scope.record;
                if (m) {
                    var id = m.id;
                    $scope.isDisabled = true; //提交disabled
                    $.ajax({
                        type: 'POST',
                        url: '/eximbank-console/countryRiskRating',
                        data: angular.toJson($scope.record)
                    }).then(callback)
                }

                function callback(result) {
                    if (result.httpCode == 200) { //成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        $timeout(function () {
                            $state.go('main.biz.countryRiskRating.list');
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
                    url: '/eximbank-console/countryRiskRating/read/detail',
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
                    url: '/eximbank-console/countryRiskRating/read/riskLevel',
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
                    messages: {},
                    submitHandler: function () {
                        $scope.submit();
                    }
                });
            }

        }
    ]);
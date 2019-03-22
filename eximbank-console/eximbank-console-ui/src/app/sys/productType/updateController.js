'use strict';

angular.module('app')
    .controller('productTypeUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
        function ($scope, $rootScope, $state, $timeout, toaster) {
            var title = "";
            if ($state.includes('**.productType.update')) {
                title = "编辑产品品种";
                var id = $state.params.id;
                activate(id);
                searchParentName();
                validate();
            } else if ($state.includes('**.productType.create')) {
                title = "添加产品品种";
                searchParentName();
                validate();
            }
            $scope.title = $rootScope.title = title;
            $scope.loading = true;

            $scope.submit = function () {
                var m = $scope.record;
                if (m) {
                    $scope.isDisabled = true; //提交disabled
                    $.ajax({
                        url: '/eximbank-console/productType',
                        type: 'POST',
                        data: angular.toJson($scope.record)
                    }).then(callback)

                }

                function callback(result) {
                    if (result.httpCode == 200) { //成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        $timeout(function () {
                            $state.go('main.post.productType.list');
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
                    url: '/eximbank-console/productType/read/detail',
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
            // 初始化下拉框
            function searchParentName() {
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/productType/read/parentList',
                    data: angular.toJson({
                        'enable': 1
                    })
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.parentProTypes = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$applyAsync();
                })
            }
            // 表单验证
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
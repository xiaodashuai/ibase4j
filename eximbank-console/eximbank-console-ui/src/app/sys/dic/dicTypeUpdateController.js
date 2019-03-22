'use strict';

angular.module('app')
    .controller('dicTypeUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
        function ($scope, $rootScope, $state, $timeout, toaster) {
            var title = "";
            if ($state.includes('**.dicType.update')) {
                title = "修改码表";
                var id = $state.params.id;
                activate(id);
                validate();
            } else if ($state.includes('**.dicType.create')) {
                title = "新增码表";
                validate();

            }
            $scope.title = $rootScope.title = title;
            $scope.loading = true;
            //初始化验证
            //validate($scope);
            $scope.submit = function () {
                var m = $scope.record;
                if (m) {
                    $scope.isDisabled = true; //提交disabled

                    $.ajax({
                        url: '/eximbank-console/dicType/dicType',
                        type: 'POST',
                        data: angular.toJson($scope.record)
                    }).then(callback)
                }

                function callback(result) {
                    if (result.httpCode == 200) { //成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        $timeout(function () {
                            $state.go('main.sys.dicType.list');
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
                    url: '/eximbank-console/dicType/read/detail', //对应后台SysDicController的@RequiresPermissions
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
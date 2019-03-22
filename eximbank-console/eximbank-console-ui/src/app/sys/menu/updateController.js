'use strict';

angular.module('app')
    .controller('menuUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', '$stateParams',
        function ($scope, $rootScope, $state, $timeout, toaster, $stateParams) {
            var title = "";
            var menuname = $stateParams.menuName;
            if ($state.includes('**.menu.update')) {
                title = "业务菜单修改";
                var id = $state.params.id;
                var parentId = $state.params.parentId;
                activate(id);
                //add by zhy  20180207 显示上级菜单信息
                getOrgSelectBox(parentId);
                selectMenuType();
                validate();
            } else if ($state.includes('**.menu.add')) {
                title = "业务菜单新增";
                //add by zhy  20180207 显示上级菜单信息
                selectMenuType();
                getOrgSelectBox(null);
                validate();
            }

            $scope.title = $rootScope.title = title;
            $scope.loading = true;
            //初始化验证
            //validate($scope);
            $scope.submit = function () {
                var m = $scope.record;
                if (m) {
                    var id = m.id;
                    var doURL = "/eximbank-console/menu";
                    if (id != undefined) {
                        doURL = "/eximbank-console/menu";
                    } else {
                        doURL = "/eximbank-console/menu/save";
                    }
                    $scope.isDisabled = true; //提交disabled
                    $scope.record.powerType = 1;
                    $scope.record.enable = 1;
                    $scope.record.isShow = 1;
                    $.ajax({
                        url: doURL,
                        type: 'POST',
                        data: angular.toJson($scope.record)
                    }).then(callback)
                }

                function callback(result) {
                    if (result.httpCode == 200) { //成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        $timeout(function () {
                            $state.go('main.sys.menu.list');
                        }, 2000);
                    } else {
                        toaster.clear('*');
                        toaster.pop('error', '', result.msg);
                        $scope.isDisabled = false;
                    }
                }
            }

            function selectMenuType() {
                if ($("input[name='menuType']").val() == "1") {
                    $("input[name='menuType']").prop("checked", "true");
                }
            }
            // 初始化页面
            function activate(id) {
                $scope.loading = true;
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/menu/read/detail',
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
            //add by zhy 20180207 
            function getOrgSelectBox(id) {
                $scope.loading = true;
                $.ajax({
                    type: 'POST',
                    url: '/eximbank-console/menu/read/queryOrgList',
                    data: angular.toJson({
                        'isShow': 1,
                        'powerType': 1
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
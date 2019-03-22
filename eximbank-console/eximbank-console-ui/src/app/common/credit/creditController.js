'use strict';

angular.module('app')
    .controller('creditController', ['$rootScope', '$scope', '$http', '$state',
        function ($rootScope, $scope, $http, $state) {
            $scope.title = '检查计划配置管理';
            $scope.param = {};
            $scope.loading = false;

            $scope.search = function () {
                $scope.loading = true;
                $.ajax({
                    async: false,
                    contentType: 'application/json',
                    headers: {},
                    url: '/eximbank-console/checkPlan/read/list',
                    type: 'POST',
                    data: angular.toJson($scope.param)
                }).then(function (result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.pageInfo = result;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$applyAsync();
                })
            }

            $scope.search();
            getClassifySelectBox();
            $scope.clearSearch = function () {
                $scope.param.keyword = null;
                $scope.search();
            }
            //风险分类等级下拉框
            function getClassifySelectBox(id) {
                $scope.loading = true;
                $.ajax({
                    async: false,
                    contentType: 'application/json',
                    headers: {},
                    type: 'POST',
                    url: '/eximbank-console/checkPlan/read/queryClassifyList',
                    data: angular.toJson({
                        'type': 0
                    })
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
            $scope.disableItem = function (id, enable) {
                $.ajax({
                    async: false,
                    contentType: 'application/json',
                    headers: {},
                    timeout: 5000,
                    type: 'POST',
                    url: '/eximbank-console/checkPlan/update/updateCheckPlan',
                    data: angular.toJson({
                        'id': id,
                        'enable': enable
                    })
                }).then(function (result) {
                    if (result.httpCode == 200) {
                        $scope.search();
                        getClassifySelectBox();
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$applyAsync();
                })
            }

            // 翻页
            $scope.pagination = function (page) {
                $scope.param.pageNum = page;
                $scope.search();
            };
        }
    ]);
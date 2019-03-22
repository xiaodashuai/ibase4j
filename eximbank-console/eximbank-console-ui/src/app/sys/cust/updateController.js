'use strict';

angular.module('app')
    .controller('custUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
        function ($scope, $rootScope, $state, $timeout, toaster) {
            var title = "";
            $scope.title = title;
            var defaultAva = $rootScope.defaultAvatar;
            title = "客户信息详情";
            var id = $state.params.id;
            activate(id);
            $scope.title = title;
            // 初始化页面
            function activate(id) {
                $scope.loading = true;
                $.ajax({
                    url: '/eximbank-console/cust/read/detail',
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
        }
    ]);
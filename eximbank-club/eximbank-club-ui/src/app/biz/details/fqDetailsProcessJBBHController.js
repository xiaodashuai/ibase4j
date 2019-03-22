'use strict';

angular.module('app')
    .controller('fqDetailsProcessJBBHController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster','WebUploadService',
        function($scope, $rootScope, $state, $timeout, toaster,WebUploadService) {
            var title = "";
            $scope.title = $rootScope.title = title;
            $scope.loading = true;
            var grantCode=$state.params.grantCode;
            //查看快照
            $scope.openPopGrant = function () {
                $scope.openPop('B','',grantCode,'');
            }
            //历史意见查看快照
            $scope.openPopDebtApproval = function (taskID) {
                $scope.openPop('C','',taskID,'');
            }
            $scope.webUploadStatus = 0;
            $scope.openE = function(grantCode, index) {
                var code = grantCode;
                if($scope.webUploadStatus == 0) {//第一次点击任意一笔
                    WebUploadService.initFiles([code, code], [index, "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout,true);
                    $scope.idd = index;
                    $scope.webUploadStatus++;
                    return false;
                }
                if($scope.webUploadStatus != 0 ){
                    if ($scope.idd == index) {//第N次点击同一笔
                        return false;
                    }else{//第二次点击不同笔
                        $scope.realFileName = "";//清空文件名
                        $scope.nclosureName = "";//清空文件名
                        angular.element('.filelist').remove();//清空文件list
                        WebUploadService.initFiles([code, code], [index, "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout,true);
                        $scope.idd = index;
                    }

                }
            }
            //根据流程实例id查看历史意见
            function getHistoryCommentInfo(){
                var piid = $state.params.piid;
                $.ajax({
                    type: 'POST',
                    url : '/eximbank-club/approval/read/getHistoryCommentInfo',
                    data: angular.toJson({'piid':piid})
                }).then(function(result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.voList = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }
            //查询债项发放信息
            function getGrantInfo(){
                $.ajax({
                    type: 'POST',
                    url : '/eximbank-club/grant/getGrantInfo',
                    data: angular.toJson({"grantCode":grantCode})
                }).then(function(result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.grantInfo = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }

            function searchChecked () {
                var aiid = $state.params.aiid;
                var starterId = $state.params.starterId;
                $.ajax({
                    url : '/eximbank-club/approval/read/getCkeckValueJBBH',
                    type: 'POST',
                    dataType: 'json',
                    async: true,
                    contentType:'application/json;charset=UTF-8',
                    data: angular.toJson({'aiid':aiid,'starterId':starterId})
                }).then(function(result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.record = result.data;
                        console.log($scope.record);
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }

            // 查询待选步骤
            function getNextTask() {
                var piid = $state.params.piid;
                var ptid = $state.params.ptid;
                var adid = $state.params.adid;
                $scope.loading = true;
                $.ajax({
                    type: 'POST',
                    url : '/eximbank-club/workflow/read/getNextTask',
                    data: angular.toJson({'piid':piid,'ptid':ptid,'adid':adid})
                }).then(function(result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.commentList = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }

            getHistoryCommentInfo();
            getNextTask();
            searchChecked ();
            getGrantInfo();
        }]);

 
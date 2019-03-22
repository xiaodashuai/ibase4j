'use strict';

angular.module('app')
    .controller('xdProcessDetailsCZController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster','WebUploadService',
        function($scope, $rootScope, $state, $timeout, toaster,WebUploadService) {
            var piid = $state.params.piid;
            var debtCode=$state.params.debtCode;
            var taskId=$state.params.taskId;
            //附件查看快照
            $scope.openPopDebt = function () {
                $scope.openPop('A','',debtCode.substring(0,13)+"001",'');
            }
            //历史意见查看快照
            $scope.openPopDebtApproval = function (taskID) {
                $scope.openPop('C','',taskID,'');
            }
            $scope.webUploadStatus = 0;
            $scope.openE = function(debtCode, index) {
                var newDebtCode=debtCode.substring(0,13);
                if($scope.webUploadStatus == 0) {//第一次点击任意一笔
                    WebUploadService.initFiles([newDebtCode, newDebtCode], ["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout,true);
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
                        WebUploadService.initFiles([newDebtCode, newDebtCode], ["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout,true);
                        $scope.idd = index;
                    }

                }


            }
            //根据流程实例id查看历史意见
            function getHistoryCommentInfo(){
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
            //查询债项方案信息
            function getSchemeInfo(){
                $.ajax({
                    type: 'POST',
                    url : '/eximbank-club/debtSummary/getSumInformation/list',
                    data: angular.toJson({"debtCode":debtCode})
                }).then(function(result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.summaryInfo = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }
            function searchChecked () {
                $.ajax({
                    url : '/eximbank-club/approval/read/getCkeckValueDetails',
                    type: 'POST',
                    dataType: 'json',
                    async: true,
                    contentType:'application/json;charset=UTF-8',
                    data: angular.toJson({'taskId':taskId})
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
            searchChecked();
            getHistoryCommentInfo();
            getSchemeInfo();
        }]);

 
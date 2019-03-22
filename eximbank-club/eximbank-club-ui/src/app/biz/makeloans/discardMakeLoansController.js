'use strict';

angular.module('app')
    .controller('discardMakeLoansController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster','WebUploadService',
        function($scope, $rootScope, $state, $timeout, toaster,WebUploadService) {
            $scope.record={};
            var grantCode=$state.params.grantCode;
            var piid = $state.params.piid;
            var ptid = $state.params.ptid;
            var adid = $state.params.adid;
            //查看快照
            $scope.openPopGrant = function () {
                $scope.openPop('B','',grantCode,'');
            }

            //查询债项发放信息
            function getGrantInfo(){
                $.ajax({
                    type: 'POST',
                    url : '/eximbank-club/makeLoans/read/getDiscardMakeLoansInfo',
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

            // 查询待选步骤
            function getNextTask() {

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

            // 保存数据
            $scope.submit= function(){
                // 业务相关数据
                var paramsJson = {}
                // 业务数据
                paramsJson.taskId=$state.params.taskId;
                paramsJson.debtCode = $state.params.debtCode;
                paramsJson.grantCode = grantCode;
                paramsJson.commentInfo = $scope.record.commentInfo;
                var m = $scope.record;
                //设置不能重复提交
                $scope.isDisabled = true; //提交disabled
                if(m){
                    $scope.isDisabled = true;//提交disabled
                    $.ajax({
                        url : '/eximbank-club/makeLoans/update/discardMakeLoans',
                        type: 'POST',
                        data: angular.toJson(paramsJson)
                    }).then(callback);
                }
                function callback(result) {
                    if(result.httpCode ==200){//成功
                        alert("提示",result.data.result);
                        $timeout(function(){
                            $state.go('main.biz.workflow.todo');
                        },2000);
                    }else{
                        toaster.clear('*');
                        toaster.pop('error', '', result.msg);
                        $scope.isDisabled = false;
                    }
                }
            }
            getNextTask();
            getGrantInfo();

            $scope.webUploadStatus = 0;
            /**
             * 功能：预览附件
             * @param {Object} code
             * @param {Object} index
             */
            $scope.openWindow = function(code) {
                if($scope.webUploadStatus == 0) { //第一次点击任意一笔
                    WebUploadService.initFiles([code, code], ["B", "B"], ["租金保理", "租金保理"],["zjblfa", "zjblsc"], $scope, $timeout,true);
                    $scope.webUploadStatus++;
                    return false;
                }
                if($scope.webUploadStatus != 0) {
                    //第二次点击不同笔
                        $scope.realFileName = ""; //清空文件名
                        $scope.nclosureName = ""; //清空文件名
                        angular.element('.filelist').remove(); //清空文件list
                        WebUploadService.initFiles([code, code], ["B", "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout,true);
                }
            }
        }]);

 
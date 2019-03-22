'use strict';

angular.module('app')
    .controller('xdProcessJBBHController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster','$CanvasService' ,'WebUploadService',
        function($scope, $rootScope, $state, $timeout, toaster, $CanvasService,WebUploadService) {
            $scope.record={};
            $scope.comment ={};
            var debtCode=$state.params.debtCode;
            var taskId=$state.params.taskId;
            var aiid = $state.params.aiid;
            var starterId = $state.params.starterId;
            var piid = $state.params.piid;
            var ptid = $state.params.ptid;
            var adid = $state.params.adid;
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
            function searchChecked () {

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
                angular.element('#submitDebt').attr("disabled", "disabled");
                //快照

                var promise = $CanvasService.createCanvas(angular.element('.canvas')[0], "C", angular.element('.canvasNum').find('.active').attr("num"), taskId, null, $scope, null);
                promise.then(function(data) {
                    console.log("创建快照成功 success data ==" + data);
                // 业务相关数据
                var paramsJson = {}
                // 流程相关数据
                var processParams = {}
                var updateHaveDone = 1;
                $scope.comment = $scope.commentList[0];
                processParams.taskId = $state.params.taskId;
                processParams.adid = $scope.comment.toActDID;
                processParams.tdid=$scope.comment.tdid;
                processParams.piid = $state.params.piid;
                processParams.ptid = $state.params.ptid;
                paramsJson.processParams=processParams;
                // 业务数据
                paramsJson.taskId=$state.params.taskId;
                paramsJson.debtCode = $state.params.debtCode;
                paramsJson.commentInfo = $scope.comment.name;
                paramsJson.commentId = $scope.comment.tdid;
                paramsJson.record = $scope.record;
                paramsJson.updateHaveDone = updateHaveDone;
                paramsJson.trn = "main";
                paramsJson.process ="";
                var m = $scope.record;
                if(m){
                    $scope.isDisabled = true;//提交disabled
                    $.ajax({
                        url : '/eximbank-club/approval/update/submitApprovalAndUpdateStatus',
                        type: 'POST',
                        data: angular.toJson(paramsJson)
                    }).then(callback);
                }
                function callback(result) {
                    if(result.httpCode ==200){//成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        $timeout(function(){
                            $state.go('main.biz.workflow.todo');
                        },2000);
                    }else{
                        toaster.clear('*');
                        toaster.pop('error', '', result.msg);
                        $scope.isDisabled = false;
                    }
                }
                }, function(data) {
                    console.log("创建快照失败 error data ==" + data);
                }, function(data) {});
            }
            getHistoryCommentInfo();
            getNextTask();
            searchChecked ();
            getSchemeInfo();

        }]);

 
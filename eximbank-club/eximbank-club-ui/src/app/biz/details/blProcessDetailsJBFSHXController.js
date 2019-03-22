'use strict';

    angular.module('app')
        .controller('blProcessDetailsJBFSHXController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster','WebUploadService',
                                             function($scope, $rootScope, $state, $timeout, toaster,WebUploadService) {
        $scope.comment ={};
        $scope.record={};
        $scope.invok="";
        var debtCode=$state.params.debtCode;
        var piid = $state.params.piid;
        var ptid = $state.params.ptid;
        var adid = $state.params.adid;
         //附件查看快照
         $scope.openPopDebt = function () {
             $scope.openPop('A','',debtCode,'');
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
                     if($scope.summaryInfo.transok=='0'){
                         if($scope.summaryInfo.identNumber !=null && $scope.summaryInfo.identNumber !=""){
                             alert("贷款账号",$scope.summaryInfo.identNumber);
                         }else{
                             if($scope.summaryInfo.errNo=='A001'){
                                 alert("","ESB平台未发送核心");
                             }else{
                                 alert("","接口平台返回数据接收错误");
                             }
                             $scope.invok=1;
                         }
                     }else if($scope.summaryInfo.transok=='1'){
                         alert("","("+$scope.summaryInfo.errNo+")"+$scope.summaryInfo.errorMessage);
                     }else {
                         alert("","接口调用失败");
                     }
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
         //接口
         $scope.submitInvokeInf= function(){
             angular.element('#refer').attr("disabled",true);
             $.ajax({
                 type: 'POST',
                 async: false,
                 url : '/eximbank-club/approval/read/invokeInfHX',
                 data: angular.toJson({'debtCode':debtCode})
             }).then(function(result) {
                 $scope.loading = false;
                 if (result.httpCode == 200) {
                     getSchemeInfo();
                     angular.element('#refer').removeAttr("disabled");
                     $scope.commentList = result;
                 } else {
                     angular.element('#refer').removeAttr("disabled");
                     $scope.msg = result.msg;
                 }
             });
         }

         getHistoryCommentInfo();
         getNextTask();
         getSchemeInfo();
    }]);

 
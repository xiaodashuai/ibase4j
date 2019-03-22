'use strict';

    angular.module('app')
        .controller('bgProcessFXJBController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster','$CanvasService','WebUploadService',
                                             function($scope, $rootScope, $state, $timeout, toaster,$CanvasService,WebUploadService) {
        $scope.record={};

        var debtCode=$state.params.debtCode;
        var taskId=$state.params.taskId;
        var grantCode=$state.params.grantCode;
        var piid = $state.params.piid;
        var ptid = $state.params.ptid;
        var adid = $state.params.adid;
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
                 url : '/eximbank-club/approval/read/getInformation',
                 data: angular.toJson({"debtCode":debtCode,"grantCode":grantCode})
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
         //锁表
         function locking() {
             $.ajax({
                 type: 'POST',
                 url : '/eximbank-club/locking/submitUserLog',
                 data: angular.toJson({'debtCode':debtCode,'code':grantCode})
             }).then(function(result) {
                 $scope.loading = false;
                 if (result.httpCode == 200) {
                     $scope.bizLocking = result.data;
                     if(result.data !=null){
                         $scope.bizLocking.userName;
                         if ($rootScope.userInfo.id!=$scope.bizLocking.userId){
                             alert("",$scope.bizLocking.userName+"正在操作中,请联系该用户进入待办列表点击返回按钮解锁该数据信息");
                         window.history.back(-1);
                         }
                     }
                 } else {
                     $scope.msg = result.msg;
                 }
                 $scope.$apply();
             });

         }
         //解锁
         $scope.goBack= function(){
             $scope.loading = true;
             $.ajax({
                 type: 'POST',
                 url : '/eximbank-club/locking/unlockUser',
                 data: angular.toJson({'code':grantCode})
             }).then(function(result) {
                 $scope.loading = false;
                 if (result.httpCode == 200) {
                     history.back(-1);
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
                     $scope.transitions = result.data;
                     $scope.comment = $scope.transitions[0];
                 } else {
                     $scope.msg = result.msg;
                 }
                 $scope.$apply();
             });
         }
         //控制提交按钮灰显 驳回时 不用选check值 也可以提交
         var changeSelected=0;//标记位
         $scope.changeSelected = function(){
             angular.element('#refer').removeAttr("ng-disabled");
             angular.element('#refer').attr("disabled",true);
             //如果cheak框先选中了 根据审核结果 设置提交按钮是否灰显
             if (angular.element('#verification').hasClass('ng-not-empty')){
                 //
                 if ($("#selectedId option:selected").text()=='通过') {
                     changeSelected=1;
                 } else if($("#selectedId option:selected").text()=='驳回'){
                     changeSelected=2;
                 }else if($("#selectedId option:selected").text()==''){
                     changeSelected=0;
                 }
                 angular.element('#refer').attr("ng-disabled","isDisabled");
                 angular.element('#refer').removeAttr("disabled");
             }else{
                 if ($("#selectedId option:selected").text()=='通过') {
                     changeSelected=1;
                 } else if($("#selectedId option:selected").text()=='驳回'){
                     changeSelected=2;
                     angular.element('#refer').attr("ng-disabled","isDisabled");
                     angular.element('#refer').removeAttr("disabled");
                 }else if($("#selectedId option:selected").text()==''){
                     changeSelected=0;
                 }
             }
         }
         //控制提交按钮灰显
         $scope.changeCheck = function(){
             if(changeSelected==1||changeSelected==0){
                 if (angular.element('#verification').hasClass('ng-not-empty')) {
                     angular.element('#refer').attr("ng-disabled","isDisabled");
                     angular.element('#refer').removeAttr("disabled");
                 } else{
                     angular.element('#refer').removeAttr("ng-disabled");
                     angular.element('#refer').attr("disabled",true);
                 }
             }
             //如果审批结果 是驳回  提交按钮不用回显
             else if (changeSelected==2){
                 angular.element('#refer').attr("ng-disabled","isDisabled");
                 angular.element('#refer').removeAttr("disabled");
             }
         }
         // 保存数据
         $scope.submit= function(){
             angular.element('#refer').attr("disabled",true);
             if($scope.comment==null || $scope.comment==''){

                 angular.element('#refer').removeAttr("disabled");
                 return false;
             }
             //快照
             var promise = $CanvasService.createCanvas(angular.element('.canvas')[0], "C", angular.element('.canvasNum').find('.active').attr("num"), taskId, null, $scope, null);
             promise.then(function(data) {
                 console.log("创建快照成功 success data ==" + data);
                 // 业务相关数据
                 var paramsJson = {}
                 // 流程相关数据
                 var processParams = {}
                 processParams.taskId = $state.params.taskId;
                 processParams.adid = $scope.comment.toActDID;
                 processParams.tdid=$scope.comment.tdid;
                 processParams.piid = $state.params.piid;
                 processParams.ptid = $state.params.ptid;
                 paramsJson.processParams=processParams;
                 // 业务数据
                 paramsJson.taskId=$state.params.taskId;
                 paramsJson.grantCode = $state.params.grantCode;
                 paramsJson.debtCode = $state.params.debtCode;
                 paramsJson.commentInfo = $scope.comment.name;
                 paramsJson.commentId = $scope.comment.tdid;
                 paramsJson.record = $scope.record;
                 paramsJson.trn = "grant";
                 paramsJson.process ="FFSH";
                 if($scope.comment.name=="驳回"){
                     var updateGrant = 1;
                     var commite="BGBH";
                     paramsJson.commite = commite;
                     paramsJson.updateGrant = updateGrant;
                     if($scope.record.grantOpinion==null || $scope.record.grantOpinion==""){
                         alert("","请填写驳回意见");
                         angular.element('#refer').removeAttr("disabled");
                         return false;
                     }
                 }
                 if($scope.record.zjaff1==null || $scope.record.zjaff1==""){
                     alert("","'变更后业务条件是否符合原方案' 选项请选择");
                     angular.element('#refer').removeAttr("disabled");
                     return false;
                 }
                 if($scope.record.zjaff1==2 ){
                     if ($("#selectedId option:selected").text()=='驳回') {
                         alert(""," 请先修改方案,再修改债项发放条件");
                     }
                     if ($("#selectedId option:selected").text()=='通过') {
                         alert(""," '审核结果' 请选择 '驳回'");
                         angular.element('#refer').removeAttr("disabled");
                         return false;
                     }
                 }
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
                         if(result.resply!=null){
                             alert("",result.resply);
                         }
                         toaster.clear('*');
                         toaster.pop('success', '', "保存成功");
                         $timeout(function(){
                             $state.go('main.biz.workflow.todo');
                         },2000);
                     }else{
                         toaster.clear('*');
                         toaster.pop('error', '', result.msg);
                         angular.element('#refer').removeAttr("disabled");
                     }
                 }
             }, function(data) {
                 console.log("创建快照失败 error data ==" + data);
             }, function(data) {});

         }
         getHistoryCommentInfo();
         getNextTask();
         getSchemeInfo();
         locking();

    }]);

 
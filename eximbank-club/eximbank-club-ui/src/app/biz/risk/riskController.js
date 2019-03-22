'use strict';

angular.module('app')
    .controller('riskController', ['$scope', '$rootScope', '$state', '$timeout','$stateParams',
        function($scope, $rootScope, $state, $timeout,$stateParams) {
            
            $scope.loading = false;
             //得到债项概要编号
           var debtNum = $state.params.debtNum;
            
           //查询出勾选的产品
           getProductBussiness(debtNum); 
            
            
            
           //查询出勾选的产品
            function getProductBussiness(debtNum){
                $scope.loading = true;
                $.ajax({
                    type: 'PUT',
                    url : '/eximbank-club/debtSummary/getProduct/list',
                    data: angular.toJson({"debtNum":debtNum})
                }).then(function(result) {
                    $scope.loading = false;
                    if (result.httpCode == 200) {
                        $scope.proList = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            } 
            
            //保存风险分析信息，担保信息，授信额度信息表
         	 $scope.submit= function(){
         	 	$scope.record.debtNum=debtNum;
         	 	var m=$scope.record;
           		
                $scope.single.debtNum=debtNum;
                var n=$scope.single;
                
                if(m.length>0){
                    $.ajax({
                        type: 'POST',
                        url : '/eximbank-club/debtSummary/getDebtSummary/save',
                        data: angular.toJson(m)
                    });
                }
                 if(n.length>0){
                    $.ajax({
                        type: 'POST',
                        url : '/eximbank-club/debtSummary/getSingleRule/save',
                        data: angular.toJson(n)
                    }).then(callback1);
                }
                function callback1(result) {
                    if(result.httpCode ==200){//成功
                        /*toaster.clear('*');
                        toaster.pop('success', '', "保存成功");*/
                        $timeout(function(){
                            $state.go('main.sys.user.risk',{debtNum:debtNum});
                        },2000);
                    }else{
                        /*toaster.clear('*');
                        toaster.pop('error', '', result.msg);*/
                        $scope.isDisabled = false;
                    }
                }
                }

        }]);
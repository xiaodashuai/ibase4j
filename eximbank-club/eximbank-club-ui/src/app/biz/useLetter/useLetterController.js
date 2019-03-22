

angular.module('app')
	.controller('useLetterController', [ '$rootScope', '$scope', '$http', '$state','$stateParams',
	                                function($rootScope, $scope, $http, $state,  $stateParams) {
        $scope.loading = false;
        
        //得到债项概要编号
         var debtNum = $state.params.debtNum;
         $scope.debtNum=$state.params.debtNum;
         //得到为客户绑定的产品code
         var proIds = $state.params.proIds;
         $scope.proIds=$state.params.proIds;
        
        //查询客户
        getCustomerList();
		//查询为客户绑定的产品
		queryCusPro(proIds);
		
		
		
		
		
		//查询为客户绑定的产品
		function queryCusPro(proIds) {
			$scope.loading = true;
			$.ajax({
				type: 'PUT',
				url: '/eximbank-club/useLetter/queryCusPro/list',
				data: angular.toJson({"proIds":proIds})
			}).then(function(result) {
				$scope.loading = false;
				if(result.httpCode == 200) {
					$scope.cusProList= result.data;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		}


		 //回显选择的客户
		function getCustomerList() {
			$scope.loading = true;
			$.ajax({
				type: 'PUT',
				url: '/eximbank-club/useLetter/getCustList/list',
				data: angular.toJson({"debtNum":debtNum})
			}).then(function(result) {
				$scope.loading = false;
				if(result.httpCode == 200) {
					$scope.custList= result.data;
				} else {
					$scope.msg = result.msg;
				}
				$scope.$apply();
			});
		}
		
		//提交保存用信主体信息,暂时只能保存一套客户对应的产品额度类型信息
            $scope.submit= function(){
            	
            	angular.forEach($scope.cusProList,function(v,k){
            		//校验产品使用额度类型
            		if(v.creditLinesId==null||v.creditLinesId==""){
            			alert("请选择产品使用额度类型");
						$scope.$apply();
            		}
            		//校验产品用信比例
            		if(v.creditRatio==null){
            			alert("请输入产品用信比例");
						$scope.$apply();
            		}
            		//校验产品用信比例是否符合输入要求
            		if(v.creditRatio<0||v.creditRatio>100){
            			alert("产品用信比例必须输入0--100的任意数字");
						$scope.$apply();
            		}
            	});
            	
              angular.forEach($scope.cusProList,function(v,k){
              	$.ajax({
        				url : '/eximbank-club/useLetter/getProLine/save',
        				type: 'POST',
        				data: angular.toJson(v)
        			})
              });
              	$state.go('main.biz.response.productMix',{debtNum:debtNum});
                /*if(n.length > 0 ){
                    $.ajax({
        				url : '/eximbank-club/useLetter/getProLine/save',
        				type: 'POST',
        				data: angular.toJson(n)
        			}).then(function(result) {
	                    $scope.loading = false;
	                    if (result.httpCode == 200) {
	                       setTimeout(function(){
	                            $state.go('main.sys.response.productMix',{debtNum:debtNum});
	                        },1000);
	                    } else {
	                        $scope.msg = result.msg;
	                        $scope.isDisabled = false;
	                    }
	                    $scope.$apply();
	                });
                }*/
            }
		
		//重置
		$scope.reset=function(){
			$scope.custList=null;
			$scope.cusProList=null;
			alert("重置成功!");
		}
		
		
} ]);
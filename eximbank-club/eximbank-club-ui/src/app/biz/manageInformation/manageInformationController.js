
// angular.module('app')
//
//     .controller('manageInformationController',['$scope','$log','$modal',
//         function($scope,$log,$modal){
//         $scope.items = ['return data'];
//         var TempUrl='src/app/biz/debtScheme/new_file.html';
//         $scope.open = function(TempUrl){
//             var modalInstance = $modal.open({
//                 templateUrl : TempUrl, //modal对应的html文件
//                 // controller : 'ModalInstanceCtrl',
//                 resolve : {
//                     items : function(){
//                         return $scope.items;
//                     }
//                 }
//             })
//
//             modalInstance.result.then(function(selectedItem){
//                 $scope.selected = selectedItem;
//             },function(){
//
//
//
//
//
//             })
//         }
//     }])
angular.module('app')
    .controller('manageInformationController', ['$scope', '$rootScope', '$state', '$timeout', '$stateParams',
        function($scope, $rootScope, $state, $timeout,  $stateParams) {

        $scope.loading = false;
        //初始化的时候全局不展示
           $scope.show1=false;
        //初始化的时候单一不展示
           $scope.show2=false;
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

        	//保存全局产品规则m,单一产品规则n
         	 $scope.submit= function(){
         	 	//校验产品规则
         	 	 if($scope.record.ruleType==null){
         	 	 	alert("请选择产品规则");
         	 	 	$scope.$apply();
         	 	 }
         	 	 //校验可办理笔数限制
         	 	 if($scope.record.ltnopa==null){
         	 	 	alert("请选择可办理笔数限制");
         	 	 	$scope.$apply();
         	 	 }

         	 	 //校验可办理笔数
         	 	 if($scope.record.ltnopa==1){
         	 		 if($scope.record.tdwln==null){
         	 	 	alert("请选择可办理笔数");
         	 	 	$scope.$apply();
         	 	 }
         	 		if($scope.record.tdwln<0||$scope.record.tdwln>999){
         	 	 	alert("可办理笔数必须在0-999范围内");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 	 //校验主方案币种
         	 	 if($scope.record.mpc==null){
         	 	 	alert("请选择主方案币种");
         	 	 	$scope.$apply();
         	 	 }
         	 	//校验辅助币种
         	 	if($scope.record.ac==null){
         	 	 	alert("请选择辅助币种");
         	 	 	$scope.$apply();
         	 	 }
         	 	//校验方案金额形式
         	 	if($scope.record.amountType==null){
         	 	 	alert("请选择方案金额形式");
         	 	 	$scope.$apply();
         	 	 }
         	 	 //校验方案金额
         	 	 if($scope.record.amountType==0){
         	 		 if($scope.record.solutionAmount==null){
         	 	 	alert("请输入方案金额");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 	//校验方案金额范围
         	 	 if($scope.record.amountType==1){
         	 		 if($scope.record.amounttrangemix==null&&$scope.record.amounttrangemax==null){
         	 	 	alert("请至少输入一个方案金额");
         	 	 	$scope.$apply();
         	 	 }
         	 		if($scope.record.amounttrangemix>$scope.record.amounttrangemax){
         	 	 	alert("方案金额范围最小值不能大于最大值");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 	//校验方案业务期限范围
         	 	if($scope.record.dopo==null){
         	 	 	alert("请输入方案业务期限");
         	 	 	$scope.$apply();
         	 	 }
         	 	if($scope.record.dopo<0||$scope.record.dopo>1800){
         	 	 	alert("方案业务期限必须在5年内（1800天）");
         	 	 	$scope.$apply();
         	 	 }

         	 	//校验方案利率类型
         	 	if($scope.record.srt==null){
         	 	 	alert("请选择方案利率类型");
         	 	 	$scope.$apply();
         	 	 }
         	 	//校验方案利率形式
         	 	 if($scope.record.srt==0){
         	 		 if($scope.record.sru==null){
         	 	 	alert("请选择方案利率形式");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 	//校验方案利率
         	 	 if($scope.record.srt==0){
         	 		 if($scope.record.sru==0){
         	 		 	if($scope.record.packageRates==null){
         	 	 	alert("请输入方案利率");
         	 	 	$scope.$apply();
         	 	 }
         	 		 	if($scope.record.packageRates<0||$scope.record.packageRates>100){
         	 		 		alert("方案利率必须输入0--100的数字");
         	 	 			$scope.$apply();
         	 		 	}
         	 	}
         	 	}
         	 	//校验方案利率范围
         	 	 if($scope.record.srt==0){
         	 		 if($scope.record.sru==1){
         	 		 	if($scope.record.rateRangeMix==null&&$scope.record.rateRangeMax==null){
         	 	 	alert("请至少输入一个方案利率范围");
         	 	 	$scope.$apply();
         	 	 }
         	 		 	if($scope.record.rateRangeMix > $scope.record.rateRangeMax){
         	 	 	alert("方案利率范围最小值不能大于最大值");
         	 	 	$scope.$apply();
         	 	 }
         	 		 	if($scope.record.rateRangeMax < 0||$scope.record.rateRangeMax > 100){
         	 	 	alert("方案利率范围最大值必须在0-100范围内");
         	 	 	$scope.$apply();
         	 	 }
         	 		 	if($scope.record.rateRangeMix < 0||$scope.record.rateRangeMix > 100){
         	 	 	alert("方案利率范围最小值必须在0-100范围内");
         	 	 	$scope.$apply();
         	 	 }

         	 	}
         	 	}
         	 	 //校验利率审批机构
         	 	if($scope.record.eaaa==null){
         	 	 	alert("请选择利率审批机构");
         	 	 	$scope.$apply();
         	 	 }
         	 	 //校验方案费率类型
         	 	if($scope.record.progRateType==null){
         	 	 	alert("请选择方案费率类型");
         	 	 	$scope.$apply();
         	 	 }
         	 	//校验方案费率形式
         	 	 if($scope.record.progRateType==1|$scope.record.progRateType==2){
         	 		 if($scope.record.schemeRateForm==null){
         	 	 	alert("请选择方案费率形式");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 	 //校验方案费率和方案费率范围
         	 	 if($scope.record.progRateType==1|$scope.record.progRateType==2){
         	 		 if($scope.record.schemeRateForm==0){
         	 		 	if($scope.record.packageRate==null){
         	 	 	alert("请输入方案费率");
         	 	 	$scope.$apply();
         	 	 }
         	 		 	if($scope.record.packageRate<0||$scope.record.packageRate>100){
         	 		 		alert("方案费率必须输入0--100的数字");
         	 	 			$scope.$apply();
         	 		 	}
         	 	}
         	 		 if($scope.record.schemeRateForm==1){
         	 		 	if($scope.record.prrMix==null&&$scope.record.prrMax==null){
         	 	 	alert("请至少输入一个方案费率范围");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 		if($scope.record.schemeRateForm==1){
         	 		 	if($scope.record.prrMix > $scope.record.prrMax){
         	 	 	alert("方案费率范围最小值不能大于最大值");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 		if($scope.record.schemeRateForm==1){
         	 		 	if($scope.record.prrMix <0 || $scope.record.prrMix>100){
         	 	 	alert("方案费率范围最小值必须在0-100范围内");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 		if($scope.record.schemeRateForm==1){
         	 		 	if($scope.record.prrMax <0 || $scope.record.prrMax>100){
         	 	 	alert("方案费率范围最大值必须在0-100范围内");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 	}
         	 	 //校验费率是否经审批
         	 	 if($scope.record.progRateType==2){
         	 		 if($scope.record.approve==null){
         	 	 	alert("请选择费率是否经审批");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 	 //校验费率审批机构
         	 	 if($scope.record.progRateType==2){
         	 		 if($scope.record.approve==0){
         	 		 	if($scope.record.raaa==null){
         	 	 	alert("请选择费率审批机构");
         	 	 	$scope.$apply();
         	 	 }
         	 	}
         	 	}

         	 	 //保存单一产品规则
         	 	/* angular.forEach($scope.proList, function(v, k) {

					v.debtNum=debtNum;
					$.ajax({
						type: 'POST',
						url: '/eximbank-club/debtSummary/getSingleRule/save',
						data: angular.toJson(v)
					})
				});*/

         	 	 //保存全局产品规则
         	 	$scope.record.debtNum=debtNum;
         	 	var m=$scope.record;

                    $.ajax({
                        type: 'POST',
                        url : '/eximbank-club/debtSummary/getDebtSummary/save',
                        data: angular.toJson(m)
                    }).then(callback1);
                function callback1(result) {
                    if(result.httpCode ==200){//成功
                        /*toaster.clear('*');
                        toaster.pop('success', '', "保存成功");*/
                        $timeout(function(){
                            $state.go('main.biz.response.risk',{debtNum:debtNum});
                        },2000);
                    }else{
                        /*toaster.clear('*');
                        toaster.pop('error', '', result.msg);*/
                        $scope.isDisabled = false;
                    }
                }

                 /*if(n.length>0){
                    $.ajax({
                        type: 'POST',
                        url : '/eximbank-club/debtSummary/getSingleRule/save',
                        data: angular.toJson(n)
                    }).then(callback1);
                }
                function callback1(result) {
                    if(result.httpCode ==200){//成功
                        toaster.clear('*');
                        toaster.pop('success', '', "保存成功");
                        $timeout(function(){
                            $state.go('main.biz.response.risk',{debtNum:debtNum});
                        },2000);
                    }else{
                        toaster.clear('*');
                        toaster.pop('error', '', result.msg);
                        $scope.isDisabled = false;
                    }
                }*/
                }

        //点击产品规则,展开全局，单一，全局+单一
           $scope.change1=function(m){
           	var a=m;
           	//全局
           	if(a==0){
           		$scope.show1=true;
           		$scope.show2=false;
           	}
           	//全局+单一
           	if(a==1){
           		$scope.show2=true;
           		$scope.show1=true;
           	}
           }

        //点击方案金额形式，0是方案金额，1是方案金额范围
        $scope.change2=function(n){
        	if(n==0){
        		$scope.show3=true;
           		$scope.show4=false;
        	}
        	if(n==1){
        		$scope.show3=false;
           		$scope.show4=true;
        	}
        }

        //点击方案利率类型，展开方案利率形式
        $scope.change3=function(srt){
        	if(srt==0){
        		$scope.show5=true;
        	}else{
        		$scope.show5=false;
        	}
        }
         //点击方案利率形式，展开方案利率和方案利率范围
        $scope.change4=function(sru){
        	if(sru==0){
        		$scope.show6=true;
        		$scope.show7=false;
        	}
        	if(sru==1){
        		$scope.show6=false;
        		$scope.show7=true;
        	}
        }
        //点击方案费率类型，展开方案费率形式和是否经审批
        $scope.change5=function(sce){
        	if(sce==2){
        		$scope.show8=true;
        		$scope.show11=true;
        	}
        	if(sce==1){
        		$scope.show8=true;
        	}
        	if(sce==0){
        		$scope.show8=false;
        		$scope.show11=false;
        	}
        }

        //点击方案费率形式，展开方案费率或者方案费率范围
        $scope.change6=function(sch){
        	if(sch==0){
        		$scope.show9=true;
        		$scope.show10=false;
        	}
        	if(sch==1){
        		$scope.show9=false;
        		$scope.show10=true;
        	}
        }


        //点击是否经审批时，展开审批机构
        $scope.change7=function(app){
        	if(app==0){
        		$scope.show12=true;
        	}else{
        		$scope.show12=false;
        	}

        }

        //点击可办理笔数限制时，展开可办理笔数
        $scope.change8=function(ap){
        	if(ap==1){
        		$scope.show13=true;
        	}else{
        		$scope.show13=false;
        	}

        }

        //重置
		$scope.reset=function(){
			//重置全局规则
			$scope.record=null;
			$scope.show1=false;
			$scope.show2=false;
			$scope.show3=false;
			$scope.show4=false;
			$scope.show5=false;
			$scope.show6=false;
			$scope.show7=false;
			$scope.show8=false;
			$scope.show9=false;
			$scope.show10=false;
			$scope.show11=false;
			$scope.show12=false;
			alert("重置成功!");
		}



        }]);
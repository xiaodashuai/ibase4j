angular.module('app').controller(
    'grantSupplementProgrammeController', ['$rootScope', '$scope', '$http', '$state',
        function($rootScope, $scope, $http, $state) {
            $scope.title = '发放管理';
            $scope.param = {};
            $scope.loading = false;

            $scope.search = function() {
                $scope.loading = true;
                $.ajax({
                    type: 'PUT',
                    url: '/eximbank-club/grant/read/list',
                    data: angular.toJson($scope.param)
                }).then(function(result) {
                    $scope.loading = false;
                    if(result){
                        if(result.httpCode == 200) {
                            $scope.pageInfo = result;
                        } else {
                            $scope.msg = result.msg;
                        }
                    }else{
                        $scope.msg = "服务器异常，请刷新重试或联系管理员";
                    }
                    $scope.$apply();
                });
            }

            $scope.search();

            $scope.clearSearch = function() {
                $scope.param.keyword = null;
                $scope.search();
            }

            /**
             * 启用，禁止
             */
            $scope.addItem = function() {
                var code = $scope.ckNode;
                if(!checkOverRange()){
                    return false;
                }
//				console.log(code);
                if(code==undefined) {
                    alert("请选择一个项目!");
                    return false;
                }else{
                    $state.go('main.biz.grant.addSupplement', {
                        proIds:$scope.ckNode.properInfo,
                        businessCode:$scope.ckNode.businessType,
                        debtNum: $scope.ckNode.debtCode
                    });
                }
            }
            /**
             * 验证日期或笔数是否超限
             */
            function checkOverRange(){
                //1.首先检查日期
                var dt = new DateUtil();
                var ckBean = $scope.bean;
                var amt = ckBean.amt;//可发放金额
                var usedNum = ckBean.usedNum;//已发放笔数
                var ruleType = ckBean.ruleType;//产品规则类型(1:全局产品规则、2:单一产品规则)
                var efeeDate = ckBean.efeectiveDate;//生效日期
                var expiDate = ckBean.expiDate;//失效日期

                var maxPck = ckBean.numLimit;//产品可办理笔数限制(1不限制、2限制)
                var maxPnum = ckBean.dealNum;//产品可办理笔数

                var maxFck = ckBean.ltnopa;//方案可办理笔数限制(1不限制、2限制)
                var maxFNum = ckBean.tdwln;//方案可办理笔数

                var now = dt.nowTime();
//				console.log(now);
                //判断金额是否满足发放
                if(!amt>0){
                    alert("方案金额("+amt+")不满足发放条件!");
                    return false;
                }
                //验证日期
                if(dt.compareDate(now,efeeDate)==1){
                    alert("还未到方案生效时间("+efeeDate+")!");
                    return false;
                }else if(dt.compareDate(expiDate,now)==1){
                    alert("方案已超过失效时间("+expiDate+")!");
                    return false;
                }
                //2.检查笔数限制
                if(ruleType==1){//全局规则
                    if(maxFck==2){//限制笔数
                        var resultNum = parseInt(maxFNum)-parseInt(usedNum);
                        if(resultNum<1){
                            alert("已经超出限制笔数:"+maxFNum);
                            return false;
                        }
                    }
                }else{//全局+单
                    if(maxFck==2){
                        //全局限制笔数
                        var resultNum = parseInt(maxFNum)-parseInt(usedNum);
                        //判断
                        var resultNum2 = parseInt(maxPnum)-parseInt(usedNum);
                        //成功
                        if(resultNum>0){
                            return true;
                        }else if(resultNum<1){
                            alert("已经超出全局限制笔数:"+maxFNum);
                            return false;
                        }else if(resultNum2<1){
                            alert("已经超出单一产品限制笔数:"+maxPnum);
                            return false;
                        }
                    }else if(maxPck==2){//产品限制

                    }
                }
                return true;
            }

            /**
             * 通过产品编号和方案编码获取有效期
             * @param {Object} pId
             * @param {Object} code
             */
            $scope.getProductDb = function(pId,code){
                $.ajax({
                    type: 'PUT',
                    url: '/eximbank-club/grant/check',
                    data: angular.toJson({
                        'properInfo':pId,
                        'debtCode':code
                    })
                }).then(function(result) {
                    if(result.httpCode == 200) {
                        $scope.bean = result.data;
                    } else {
                        $scope.msg = result.msg;
                    }
                    $scope.$apply();
                });
            }

            // 翻页
            $scope.pagination = function(page) {
                $scope.param.pageNum = page;
                $scope.search();
            };

        }

    ]);
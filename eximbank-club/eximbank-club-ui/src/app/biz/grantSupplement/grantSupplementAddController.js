'use strict';

angular.module('app').controller(
    'grantSupplementAddController', ['$scope', '$rootScope', '$state', '$http', 'toaster', '$CanvasService','$filter',
        function($scope, $rootScope, $state, $http, toaster, $CanvasService,$filter) {
            var debtNum = $state.params.debtNum;
            var proIds = $state.params.proIds; //单一产品表数据主键
            var businessCode = $state.params.businessCode; //产品类型编码

            getByCode(debtNum);

            //$timeout(function(){
            search();
            //},1000);)

            function getByCode(code) {
                $scope.loading = true;
                $.ajax({
                    type: 'PUT',
                    url: '/eximbank-club/grant/add',
                    data: angular.toJson({
                        "debtCode": code
                    })
                }).then(function(result) {
                    $scope.loading = false;
                    if(result) {
                        if(result.httpCode == 200) {
                            $scope.model = result.data;
                            $scope.model.debtCode = debtNum;
                            //初始化下拉框
                            initSelectBox(businessCode, debtNum);
                            //查询约束条件
                            getCheck(debtNum, proIds);
                            //初始化表单验证
                            initBlur();
                            //根据产品编码查询产品名称
                            var pTypes = getProductTypes(businessCode);
                            $scope.model.businessName = pTypes.name;
                        } else {
                            $scope.msg = result.msg;
                        }
                    } else {
                        $scope.msg = "服务器异常，请刷新重试或联系管理员";
                    }
                    $scope.$apply();
                });
            }

            //查询方案约束条件
            function getCheck(code, pval) {
                $scope.loading = true;
                $.ajax({
                    type: 'PUT',
                    url: '/eximbank-club/grant/check',
                    data: angular.toJson({
                        "properInfo": pval,
                        "debtCode": code
                    })
                }).then(function(result) {
                    $scope.loading = false;
                    if(result) {
                        if(result.httpCode == 200) {
                            $scope.gantRuleVerifVo = result.data;
                        } else {
                            $scope.msg = result.msg;
                        }
                    } else {
                        $scope.msg = "服务器异常，请刷新重试或联系管理员";
                    }
                    $scope.$apply();
                });
            }

            function search(){
                //$scope.loading = true;
                var grantCode = 'CLB2018080141039';
                // $scope.param={'id':'1'};
                $.ajax({
                    url: '/eximbank-club/grant/queryGrant',
                    type: 'POST',
                    data: angular.toJson({
                        'grantCode': grantCode
                    })
                }).then(function(result) {
                    //$scope.loading = false;
                    if(result) {
                        if(result.httpCode == 200) {
                            $scope.record = result.data;
                            console.log($scope.record);
                            $scope.payList=$scope.record.payList;
                            $scope.loanList=$scope.record.loanList;
                        } else {
                            $scope.msg = result.msg;
                        }
                    } else {
                        $scope.msg = "服务器异常，请刷新重试或联系管理员";
                    }
                    $scope.$apply();
                });
            }
            //$scope.search();

            $scope.songdan=function(){
                var arr=new Array();
                if ($scope.record.ydyl==true) arr.push("ydyl");
                if ($scope.record.gjcn==true) arr.push("gjcn");
                if ($scope.record.zdzb==true) arr.push("zdzb");
                if ($scope.record.dycb==true) arr.push("dycb");
                if ($scope.record.nyzy==true) arr.push("nyzy");
                var d = new Date(2015, 11, 31);
                // 因为getMonth()获取的月份的值只能在0~11之间所以我们在进行setMonth()之前先给其减一
                d.setMonth((d.getMonth()-1) + 3);
                var yy1 = d.getFullYear();
                var mm1 = d.getMonth()+1;
                console.log(mm1);
                var dd1 = d.getDate();
                if (mm1 < 10 ) {
                    mm1 = '0' + mm1;
                }
                if (dd1 < 10) {
                    dd1 = '0' + dd1;
                }
                console.log(yy1 + '-' + mm1 + '-' + dd1);
                console.log(arr);
            };

            $scope.showTable = '1';

            $scope.payListChange = function() {
                var fDay = document.getElementsByName("fristRepayDate")[0].value;
                var sDay = document.getElementsByName("startDate")[0].value;
                console.log(fDay);
                console.log(sDay);
                console.log($scope.payList);
            }

            $scope.changeTab = function(tab) {
                $scope.tab = tab;
                //				$CanvasService.createCanvas(angular.element('.canvas')[0],"B",angular.element('.canvasNum').find('.active').attr("num"),$scope.gantRuleVerifVo.grantCode,null,$scope,tab);
            };
            //
        }
    ]);

function getOneSelectBox(code,children,type) {
    $scope.loading = true;
    $.ajax({
        type: 'PUT',
        url: '/eximbank-club/components/queryOneList',
        data: angular.toJson({
            'code': code,
            'children':children,
            'type':type
        })
    }).then(function(result) {
        $scope.loading = false;
        if(result.httpCode == 200) {
            $scope.oneList = result.data;
        } else {
            $scope.msg = result.msg;
        }
        $scope.$apply();
    });
}

function getPayList() {
    var fDay = document.getElementsByName("fristRepayDate")[0].value;
    var lDay = document.getElementsByName("lastRepayDay")[0].value;
    var repaymentCostMode = document.getElementsByName("repaymentCostMode")[0].value;
    var money = document.getElementsByName("paymentAmt")[0].value;
    money = Number(money);
    var payList = new Array();
    var record = new Object();

    if(repaymentCostMode == 1) {
        var d2 = new Date(fDay);
        var d3 = new Date(lDay);
        var startMonth = d2.getMonth();
        var endMonth = d3.getMonth();
        var yue = (d3.getFullYear() * 12 + endMonth) - (d2.getFullYear() * 12 + startMonth) + 1;
        record.payDate = fDay;
        record.principalAmy = Number(money / yue * repaymentCostMode).toFixed(2);
        payList.push(record);
        for(var i = 1; i < (yue - 1) / repaymentCostMode; i++) {
            record = new Object();
            var aDay = new Date(fDay).getTime() + 2592000000 * i * repaymentCostMode;
            //record.payDate = new Date(aDay).Format("yyyy-MM-dd");
            record.payDate = dateUpdete(fDay, repaymentCostMode, (i + 1));
            record.principalAmy = Number(money / yue * repaymentCostMode).toFixed(2);
            payList.push(record);
        }
        record = new Object();
        record.payDate = lDay;
        var totalVal = Number(money) - Number(money / yue * repaymentCostMode) * (yue - 1);
        record.principalAmy = Number(totalVal).toFixed(2);
        payList.push(record);
        return payList;
    } else {
        var d2 = new Date(fDay);
        var d3 = new Date(lDay);
        var startMonth = d2.getMonth();
        var endMonth = d3.getMonth();
        var yue = (d3.getFullYear() * 12 + endMonth) - (d2.getFullYear() * 12 + startMonth) + 1;
        record.payDate = new Date(fDay).Format("yyyy-MM-dd");
        record.principalAmy = Number(money / (yue / repaymentCostMode + 1)).toFixed(2);
        payList.push(record);
        for(var i = 0; i < (yue - 1) / repaymentCostMode - 1; i++) {
            record = new Object();
            var aDay = new Date(fDay) + 2592000000 * i * repaymentCostMode;
            //record.payDate = new Date(aDay).Format("yyyy-MM-dd");
            record.payDate = dateUpdete(fDay, repaymentCostMode, (i + 1));
            record.principalAmy = Number(money / (yue / repaymentCostMode + 1)).toFixed(2);
            payList.push(record);
        }
        record = new Object();
        record.payDate = new Date(lDay).Format("yyyy-MM-dd");
        var totalVal = Number(money - (money / (yue / repaymentCostMode + 1)) * (yue / repaymentCostMode));
        record.principalAmy = Number(totalVal).toFixed(2);
        payList.push(record);
        return payList;
    }

}

function dateUpdete(dateUpdt, mouth, number) {
    var d = new Date(dateUpdt);
    // 因为getMonth()获取的月份的值只能在0~11之间所以我们在进行setMonth()之前先给其减一
    d.setMonth((d.getMonth() - 1) + mouth * number);
    var yy1 = d.getFullYear();
    var mm1 = d.getMonth() + 1;
    //	console.log(mm1);
    var dd1 = d.getDate();
    if(mm1 < 10) {
        mm1 = '0' + mm1;
    }
    if(dd1 < 10) {
        dd1 = '0' + dd1;
    }
    //	console.log(yy1 + '-' + mm1 + '-' + dd1);
    var sucDate = yy1 + '-' + mm1 + '-' + dd1;
    return sucDate;
}
//获得还息内容
function getLoanList() {

    var fDay = document.getElementsByName("fristRepayDate")[0].value;
    var lDay = document.getElementsByName("lastRepayDay")[0].value;

    var flDay = document.getElementsByName("firstDay")[0].value;
    var repaymentCostMode = document.getElementsByName("calcWay")[0].value;

    if(repaymentCostMode == 1) {
        var d2 = new Date(fDay);
        var d3 = new Date(lDay);
        var startMonth = d2.getMonth();
        var endMonth = d3.getMonth();
        var yue = (d3.getFullYear() * 12 + endMonth) - (d2.getFullYear() * 12 + startMonth) + 1;
        var LoanList = new Array();
        var record = new Object();
        record.interestDate = flDay;
        LoanList.push(record);
        for(var i = 1; i < yue / repaymentCostMode - 1; i++) {
            record = new Object();
            //var aDay = new Date(flDay).getTime() + 2592000000 * (i + 1) * repaymentCostMode;
            //record.interestDate = new Date(aDay).Format("yyyy-MM-dd");
            record.interestDate = dateUpdete(flDay, repaymentCostMode, (i + 1));
            LoanList.push(record);
        }
        record = new Object();
        record.interestDate = new Date(lDay).Format("yyyy-MM-dd");
        LoanList.push(record);
        return LoanList;
    } else {
        var d2 = new Date(fDay);
        var d3 = new Date(lDay);
        var startMonth = d2.getMonth();
        var endMonth = d3.getMonth();
        var yue = (d3.getFullYear() * 12 + endMonth) - (d2.getFullYear() * 12 + startMonth) + 1;
        var LoanList = new Array();
        var record = new Object();
        record.interestDate = flDay;
        LoanList.push(record);
        for(var i = 0; i < yue / repaymentCostMode - 1; i++) {
            record = new Object();
            //var aDay = new Date(flDay).getTime() + 2592000000 * (i + 1) * repaymentCostMode;
            //record.interestDate = new Date(aDay).Format("yyyy-MM-dd");
            record.interestDate = dateUpdete(flDay, repaymentCostMode, (i + 1));
            LoanList.push(record);
        }
        record = new Object();
        record.interestDate = new Date(lDay).Format("yyyy-MM-dd");
        LoanList.push(record);

        return LoanList;
    }

}

/**
 * 初始化货币
 */
function initSelectBox(businessCode, debtNum) {
    //行业投向
    bulidMulitSelect("industryInvestmentUL", "industryInvestment", "A0", "005");
    getDic(); //调用码表下拉框
    //中国制造2025及战略新兴产业分类
    bulidMulitSelect("chinaEmergingIndustryClassifyUL", "chinaEmergingIndustryClassify", "A00000AA", "006");
    loadCurrencySelect("currency", debtNum);
    //支农投向
    bulidMulitSelect("compareUL", "compare", "HHH", "014");
    bulidCurrencySelect("payCny");
    //贷款领域
    bulidMulitSelect("loanDomainUL", "loanDomain", "AGR0280001", "008");
    loadGuaranteeInfoSelect("guaranType", debtNum);
    //产品类型
    bulidMulitSelect("businessTypeUL", "businessType", "PRD0101005", "012");
    bulidChargeTypeSelect('chargeType', businessCode);
    //创新业务类型
    bulidMulitSelect("innovativeBusinessTypeUL", "innovativeBusinessType", "PRD0101008", "011");
    var pledgeType0 = document.getElementsByName("pledgeType0")[0];
    bulidPledgeTypeSelect(pledgeType0);
    //进出口货物及服务
    bulidMulitSelect("importExportGoodsServiceUL", "importExportGoodsService", "AGR0280002", "010");
    bulidInterestRateSelect("irBk");
    //对外投资贷款分类
    bulidMulitSelect("investmentLoanCkassifcationUL", "investmentLoanCkassifcation", "AAA", "009");
    bulidPledgeTypeSelect(pledgeType0);
    //精准扶贫项目所在地区
    bulidMulitSelect("povertyAddressUL", "povertyAddress", "LOC0101003", "013");
}

//设置滚动条在顶部
function setTop() {
    $(document).scrollTop($(".tab-menu").offset().top - 100);
}

/**
 * 获取字典
 */
function getDic() {
    //TODO 生成下拉
//	var typeCode = "TRADEMODE";
//	bulidDicSelect('type', typeCode);
    var typeCode2 = "countryBack";
    bulidDicSelect('backgroundNationality', typeCode2);
}

/**
 * 功能：显示币种
 */
function getDebtCurrencyDb(debtCode) {
    var result = null;
    $.ajax({
        type: "PUT",
        url: "/eximbank-club/grant/getCurrency",
        data: angular.toJson({
            'debtCode': debtCode
        }),
        async: false,
        success: function(json) {
            result = json.data;
        },
        error: function(e) {
            console.log(e);
        }
    });
    return result;
}
/**
 * 功能：显示担保类型
 * @param {Object} parentCode 父编码
 * @param {Object} debtCode 债项编号
 */
function getDebtGuaranteeInfoByCodeDb(parentCode, debtCode) {
    var result = null;
    $.ajax({
        type: "PUT",
        url: "/eximbank-club/grant/getGuaranteeInfo",
        data: angular.toJson({
            'debtCode': debtCode,
            'parentCode': parentCode
        }),
        async: false,
        success: function(json) {
            result = json.data;
        },
        error: function(e) {
            console.log(e);
        }
    });
    return result;
}

/**
 * 生成币种下拉框
 * @param {Object} id 元素id属性
 */
function loadCurrencySelect(id, debtCode) {
    //清空原来的数据
    $("#" + id).empty();
    var html = defaultOption();
    //查询数据库
    var jsonResult = getDebtCurrencyDb(debtCode);
    html += createOption(jsonResult);
    //连接字符串
    $("#" + id).append(html);
}

/**
 * 生成担保类型下拉框
 * @param {Object} curId 元素id属性
 */
function loadGuaranteeInfoSelect(curId, code) {
    var parentCode = "";
    //清空原来的数据
    $("#" + curId).empty();
    var html = defaultOption();
    //查询数据库
    var jsonResult = getDebtGuaranteeInfoByCodeDb(parentCode, code);
    html += createOption(jsonResult);
    //连接字符串
    $("#" + curId).append(html);
}

/**
 * 通过（担保类型）一级下拉框生成二级下拉框
 * @param {Object} curId 元素id属性
 */
function changeGuaranteeInfoSelect(curId) {
    var parentCode = $(curId).val();
    if(!parentCode) {
        parentCode = "";
    }
    var debtNum = $("#debtCode").val();
    //清空原来的数据
    $(curId).next().empty();
    var html = defaultOption();
    //查询数据库
    var jsonResult = getDebtGuaranteeInfoByCodeDb(parentCode, debtNum);
    html += createOption(jsonResult);
    //连接字符串
    $(curId).next().append(html);
}
'use strict';

angular.module('app')
	.controller('custUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
		function ($scope, $rootScope, $state, $timeout, toaster) {
			var title = "";
			var id = $state.params.id;
			if ($state.includes('**.cust.detail')) {
				title = "客户信息详情";
				$('#submitBtn').hide();
				$('#deleteBtn').hide();
				activate(id);
			} else if ($state.includes('**.cust.update')) {
				title = "客户信息修改";
				$('#deleteBtn').hide();
				activate(id);
				validate();
			} else if ($state.includes('**.cust.delete')) {
				title = "客户信息删除";
				$('#submitBtn').hide();
				activate(id);
			}
			$scope.title = title;
			// 初始化页面
			function activate(id) {
				$scope.loading = true;
				$.ajax({
					url: '/eximbank-club/cust/read/detail',
					type: 'PUT',
					data: angular.toJson({
						'id': id
					})
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.record = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}
			$scope.loading = true;
			//提交保存
			$scope.submit = function () {
				var m = $scope.record;
				if (m) {
					$.ajax({
						url: '/eximbank-club/cust/read/update',
						type: 'POST',
						data: angular.toJson($scope.record)
					}).then(callback);
				}

				function callback(result) {
					if (result) {
						if (result.httpCode == 200) { //成功
							toaster.clear('*');
							toaster.pop('success', '', "保存成功");
							$timeout(function () {
								$state.go('main.sys.cust.list');
							}, 2000);
						} else {
							toaster.clear('*');
							toaster.pop('error', '', "有点小故障,请刷新重试");
						}
					}
				}
			}
			//删除操作
			$scope.deleteTest = function() {
				var falg = confirm("客户信息一旦删除将无法恢复,是否确认删除?");
				if (falg) {
					$.ajax({
						url: '/eximbank-club/cust/read/delete',
						type: 'PUT',
						data: angular.toJson({
							'id': id
						})
					}).then(callback);
				}

				function callback(result) {
					if (result) {
						if (result.httpCode == 200) { //成功
							toaster.clear('*');
							toaster.pop('success', '', "删除成功");
							$timeout(function () {
								$state.go('main.sys.cust.list');
							}, 2000);
						}else if(result.httpCode == 403){
							alert('您没有该权限!');
						}else {
							toaster.clear('*');
							toaster.pop('error', '', "有点小故障,请刷新重试");
						}
					}
				}
			}

			//表单验证
			function validate() {
				jQuery('form').validate({
					rules: {},
					messages: {},
					submitHandler: function () {
						$scope.submit();
					}
				});
			}
		}
	]);


//录入项校验        
var count = 0;
//客户编号非空校验
function custNoJiaoYan() {
	var parent = $('#custNo').parent();
	if ($("#custNo").val() == null || $("#custNo").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户编号" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
// //公有信息编号非空校验
// function debtCodeJiaoYan() {
//     var parent = $('#debtCode').parent();
// 	if($("#debtCode").val() == null || $("#debtCode").val().trim() == "") {
// 		parent.find("span").remove();
// 		parent.append('<span class="text-danger">' + "请输入公有信息编号" + '</span>');
// 		count += 1;
// 	} else {
// 		parent.find("span").remove();
// 		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
// 	}
// }
//客户名称(中文)非空校验
function custNameCNJiaoYan() {
	var parent = $('#custNameCN').parent();
	if ($("#custNameCN").val() == null || $("#custNameCN").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户名称" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户名称(英文)非空校验
function custNameENJiaoYan() {
	var parent = $('#custNameEN').parent();
	if ($("#custNameEN").val() == null || $("#custNameEN").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户名称(英文)" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
// //客户证件类型非空校验
// function custIdTypeJiaoYan() {
//     var parent = $('#custIdType').parent();
// 	if($("#custIdType").val() == null || $("#custIdType").val().trim() == "") {
// 		parent.find("span").remove();
// 		parent.append('<span class="text-danger">' + "请输入客户证件类型" + '</span>');
// 		count += 1;
// 	} else {
// 		parent.find("span").remove();
// 		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
// 	}
// }
//客户证件号非空校验
function custLicenseNoJiaoYan() {
	var parent = $('#custLicenseNo').parent();
	if ($("#custLicenseNo").val() == null || $("#custLicenseNo").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户证件号" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户所在地址（中文）非空校验
function custAddressCNJiaoYan() {
	var parent = $('#custAddressCN').parent();
	if ($("#custAddressCN").val() == null || $("#custAddressCN").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户所在地址（中文）" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户所在地址（英文）非空校验
function custAddressENJiaoYan() {
	var parent = $('#custAddressEN').parent();
	if ($("#custAddressEN").val() == null || $("#custAddressEN").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户所在地址（英文）" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户属辖经营单位非空校验
function custBusinessUnitJiaoYan() {
	var parent = $('#custBusinessUnit').parent();
	if ($("#custBusinessUnit").val() == null || $("#custBusinessUnit").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户属辖经营单位" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户登记注册类型非空校验
function custRegTypeJiaoYan() {
	var parent = $('#custRegType').parent();
	if ($("#custRegType").val() == null || $("#custRegType").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户登记注册类型" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户评级授信类型非空校验
function custRcreditTypeJiaoYan() {
	var parent = $('#custRcreditType').parent();
	if ($("#custRcreditType").val() == null || $("#custRcreditType").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户评级授信类型" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//信用评级非空校验
function creditRatingJiaoYan() {
	var parent = $('#creditRating').parent();
	if ($("#creditRating").val() == null || $("#creditRating").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入信用评级" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//评级时间非空校验
function ratingTimeJiaoYan() {
	var parent = $('#ratingTime').parent();
	if ($("#ratingTime").val() == null || $("#ratingTime").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入评级开始时间" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//评级有效期非空校验
function ratValiPeriodJiaoYan() {
	var parent = $('#ratValiPeriod').parent();
	if ($("#ratValiPeriod").val() == null || $("#ratValiPeriod").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入评级结束时间" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户所属国别非空校验
function custCountryJiaoYan() {
	var parent = $('#custCountry').parent();
	if ($("#custCountry").val() == null || $("#custCountry").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户所属国别" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户所属行业非空校验
function mainBusinessJiaoYan() {
	var parent = $('#mainBusiness').parent();
	if ($("#mainBusiness").val() == null || $("#mainBusiness").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户所属行业" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户规模非空校验
function custScaleJiaoYan() {
	var parent = $('#custScale').parent();
	if ($("#custScale").val() == null || $("#custScale").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户规模" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//客户经理非空校验
function custManagerJiaoYan() {
	var parent = $('#custManager').parent();
	if ($("#custManager").val() == null || $("#custManager").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入客户经理" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//所属集团编号非空校验
function groupNoJiaoYan() {
	var parent = $('#groupNo').parent();
	if ($("#groupNo").val() == null || $("#groupNo").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入所属集团编号" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
//所属集团名称非空校验
function groupNameJiaoYan() {
	var parent = $('#groupName').parent();
	if ($("#groupName").val() == null || $("#groupName").val().trim() == "") {
		parent.find("span").remove();
		parent.append('<span class="text-danger">' + "请输入所属集团名称" + '</span>');
		count += 1;
	} else {
		parent.find("span").remove();
		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
	}
}
// //是否高风险客户非空校验
// function highRiskFlgJiaoYan() {
//     var parent = $('#highRiskFlg').parent();
// 	if($("#highRiskFlg").val() == null || $("#highRiskFlg").val().trim() == "") {
// 		parent.find("span").remove();
// 		parent.append('<span class="text-danger">' + "请输入是否高风险客户" + '</span>');
// 		count += 1;
// 	} else {
// 		parent.find("span").remove();
// 		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
// 	}
// }
// //在我行所属合并授信主体客户号非空校验
// function grpNoJiaoYan() {
//     var parent = $('#grpNo').parent();
// 	if($("#grpNo").val() == null || $("#grpNo").val().trim() == "") {
// 		parent.find("span").remove();
// 		parent.append('<span class="text-danger">' + "请输入在我行所属合并授信主体客户号" + '</span>');
// 		count += 1;
// 	} else {
// 		parent.find("span").remove();
// 		parent.append('<span class="glyphicon glyphicon-ok-circle vali-success"></span>');
// 	}
// }
//提交校验
function test() {
	count = 0;
	custNoJiaoYan();
	custNameCNJiaoYan();
	custNameENJiaoYan();
	custLicenseNoJiaoYan();
	custAddressCNJiaoYan();
	custAddressENJiaoYan();
	custBusinessUnitJiaoYan();
	custRegTypeJiaoYan();
	custRcreditTypeJiaoYan();
	ratingTimeJiaoYan();
	ratValiPeriodJiaoYan();
	custCountryJiaoYan();
	custManagerJiaoYan();
	mainBusinessJiaoYan();
	custScaleJiaoYan();
	groupNoJiaoYan();
	groupNameJiaoYan();
	if (count == 0) {
		$("#submitBtn").attr("type", "submit");
	} else {
		$("#submitBtn").attr("type", "button");
	}
}
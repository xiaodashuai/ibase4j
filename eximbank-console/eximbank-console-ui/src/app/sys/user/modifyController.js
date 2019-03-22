'use strict';

/* App Module */
var appModule = angular.module('app');
// 操作控制
appModule.controller('counterUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', '$stateParams',
	function ($scope, $rootScope, $state, $timeout, toaster, $stateParams) {
		var title = "";
		if ($state.includes('**.counter.update')) {
			title = "柜员修改";
			var id = $stateParams.id;
			activate(id);
			// validate();
		} else if ($state.includes('**.counter.create')) {
			title = "柜员新增";
			getUnList();
			// validate();
		}
		$scope.title = $rootScope.title = title;
		// 初始化验证
		$scope.submit = function () {
			var count = 0;
			var account = $("#account").val();
			var user_id = $('#biaoji').val();
			//帐号格式验证
			var validate = checkAccount();
			if (validate == false) {
				count++;
			}
			//修改模式不需要验证帐户唯一
			if (user_id == null) {
				validate = accountJiaoYan(account);
				validate = staffNoJiaoYan($('#staffNo').val());
				if (validate == false) {
					count++;
				}
			}
			validate = userNameJiaoYan();
			if (validate == false) {
				count++;
			}
			validate = phoneJiaoYan();
			if (validate == false) {
				count++;
			}
			validate = telephoneJiaoYan();
			if (validate == false) {
				count++;
			}
			validate = emailJiaoYan();
			if (validate == false) {
				count++;
			}
			//如果是新增模式或修改模式但需要重置密码，则验证密码规则
			var checkPwd = $("input[name='resetPwd']:checked").val();
			if (checkPwd == 1 || user_id == null || user_id == '') {
				validate = passwordJiaoYan();
				if (validate == false) {
					count++;
				}
				validate = confirmPasswordJiaoYan();
				if (validate == false) {
					count++;
				}
			}

			validate = staffNoJiaoYan($('#staffNo').val());
			if (validate == false) {
				count++;
			}
			validate = departmentJiaoYan();
			if (validate == false) {
				count++;
			}
			validate = checkedRole();
			if (validate == false) {
				count++;
			}
			//调用angluarjs的提交
			if (count != 0 || $("#accountTest").text() != "" || $("#staffNoTest").text() != "") {
				alert("有录入项填写不正确，请检查后重试");
				return false;
			} else {
				//登录名存 员工编号  页面没有这个字段了
				$scope.record.account=$scope.record.staffNo;
				$scope.record.enable = $('input[name="enable"]:checked').val();
				var m = $scope.record;
				if (m) {
					m.userType = 1;
					$scope.loading = true;
					//获取到所有选中的岗位id
					var roleIds = getCheckRoleIds();
					m.roles = roleIds;
					$scope.isDisabled = true; // 提交disabled
					$.ajax({
						type: 'POST',
						url: '/eximbank-console/counter',
						data: angular.toJson(m)
					}).then(callback)
				};
			}
			//保存后的回调函数
			function callback(result) {
				if (result.httpCode != null) {
					if (result.httpCode == 200) { // 成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$timeout(function () {
							$state.go('main.sys.counter.list');
						}, 2000);
					} else {
						toaster.clear('*');
						toaster.pop('error', '', "有点小故障,请刷新重试");
						$scope.isDisabled = false;
					}
				}
				$scope.loading = false;
			}
		};
		/**
		 * 选中用户
		 */
		function getUserId() {
			if ($scope.record) {
				return $scope.record.id;
			} else {
				return null;
			}
		}

		//单个选择按钮
		$scope.addChecked = function (m, n) {
			angular.forEach($scope.roleList, function (v, k) {
				if (n == v.roleId) {
					v.checked = m;
					//m = v.checked;
				}
			})
		}
		//获取选中的岗位id 
		function getCheckRoleIds() {
			var idStr = '';
			angular.forEach($scope.roleList, function (v, k) {
				if (v.checked == true || v.checked == 'true') {
					idStr += v.roleId + ",";
				}
			});
			//console.log(idStr);
			return idStr;
		}
		// 初始化页面,查询所有角色
		function getUnList() {
			//编辑用户,对应的用户的id
			var userId = getUserId();
			$scope.loading = true;
			$.ajax({
				type: 'POST',
				url: '/eximbank-console/role/read/listUnBind',
				data: angular.toJson({
					'roleType': "1"
				})
			}).then(function (result) {
				$scope.loading = false;
				if (result) {
					if (result.httpCode == 200) {
						$scope.roleList = result.data;
						if (userId != null) {
							getBindList(userId, result.data);
						}
					} else {
						$scope.msg = result.msg;
					}
				} else {
					$scope.msg = "系统走神了,请找系统柜员!";
				}
				$scope.$applyAsync();
			})
		}

		/**
		 * 初始化页面,查询已经邦定的岗位
		 * @param {Object} userId
		 * @param {Object} list
		 */
		function getBindList(userId, list) {
			$.ajax({
				type: 'POST',
				url: '/eximbank-console/role/read/getBinded',
				data: angular.toJson({
					'userId': userId
				})
			}).then(function (result) {
				if (result.httpCode == 200) {
					if (result.iTotalRecords > 0) {
						angular.forEach(result.data, function (vv, kk) {
							angular.forEach(list, function (v, k) {
								if (vv.roleId == v.roleId) {
									v.checked = true;
								}
							})
						})
					}
				} else {
					$scope.msg = result.msg;
				}
				$scope.$applyAsync();
			})
		}
		// 初始化页面
		function activate(id) {
			$scope.loading = true;
			$.ajax({
				type: 'POST',
				url: '/eximbank-console/counter/read/detail',
				data: angular.toJson({
					'id': $stateParams.id
				})
			}).then(
				function (result) {
					$scope.loading = false;
					if (result) {
						if (result.httpCode == 200) {
							$scope.record = result.data;
							//加载岗位
							resetPassword(0);
							getUnList();
						} else {
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "系统走神了,请联系找系统管理员!";
					}
					$scope.$applyAsync();
				})
		}

		function clearTest(checked) {
			if (checked) {
				$scope.checkedTest = "";
			}
		}
	}
]);

//点击选择是否需要重置密码
function resetPassword(open) {
	if (open == "1") {
		$(".mypassword").show();
	} else {
		$(".mypassword").hide();
	}
}
/**
 * 验证帐号格式
 */
function checkAccount() {
	//不校验了 直接返回true
	return true;
	if ($('#biaoji').val() == null || $('#biaoji').val() == '') {
		if ($("#account").val() == null || $("#account").val().trim() == '') {
			$("#accountTest").text("请输入登录名");
			return false;
		} else if (/^[a-zA-Z0-9]*$/.test($("#account").val()) == false) {
			$("#accountTest").text("登录名只能包含数字,字母");
			return false;
		} else if ($("#account").val().length > 18) {
			$("#accountTest").text("登录名最多只能为18位");
			return false;
		}
		return true;
	}

}
//验证帐户是否唯一
function accountJiaoYan(account) {
	//不校验了  直接返回false
	return false;
	var ck_acc = checkAccount();

	if (ck_acc) {
		jQuery.ajax({
			url: '/eximbank-console/counter/read/checkOut',
			type: 'GET',
			async: false,
			data: {
				account: account
			},
			dataType: 'json',
			success: function (data) {
				if (data.accounts == "true" && $('#title').text() != "柜员修改") {
					$("#accountTest").text("登录名已存在");
					return false;
				} else {
					$("#accountTest").text("");
				}
			}
		});
	}

	return true;
}


function checkedRole() {
	//获取到所有选中的岗位id
	var roleSize = $("input[name='roles']:checked").val();
	if (!roleSize || roleSize == 0) {
		$("#checkedRoleTest").text("请选岗位！");
		return false;
	} else {
		$("#checkedRoleTest").text("");
	}
	return true;
}
//信息来源的非空校验
function userNameJiaoYan() {
	if ($('#userName').val() == null || $('#userName').val().trim() == '') {
		$('#userNameTest').text("请输入柜员姓名");
		return false;
	} else {
		$('#userNameTest').text("");
	}
	return true;
}
// //员工编号验证
// function staffNoCheck() {
// 	if($('#userName').val() == null || $('#userName').val().trim() == '') {
// 		$('#userNameTest').text("请输入柜员姓名");
// 		return false;
// 	} else {
// 		$('#userNameTest').text("");
// 	}
// 	return true;
// }
//验证手机号码格式
function phoneJiaoYan() {
	if ($('#phone').val() == null || $('#phone').val().trim() == '') {
		$('#phoneTest').text("请输入电话号码");
		return false;
	} else if (/^1[3-8][0-9]\d{8}$/.test($('#phone').val()) == false) {
		$('#phoneTest').text("请输入正确格式的手机或电话号码");
		return false;
	} else {
		$('#phoneTest').text("");
	}
	return true;
}

function telephoneJiaoYan() {
	if ($('#telephone').val() == null || $('#telephone').val().trim() == '') {
		$('#telephoneTest').text("请输入电话号码");
		return false;
	} else if ( /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,8}$/.test($('#telephone').val()) == false) {
		$('#telephoneTest').text("请输入正确格式的电话号码");
		return false;
	} else {
		$('#telephoneTest').text("");
	}
	return true;
}
//邮箱格式验证
function emailJiaoYan() {
	if ($('#email').val() != null && $('#email').val().trim().length!=0) {
		if (/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test($('#email').val()) == false) {
			$('#emailTest').text("邮箱格式不正确");
			return false;
		}
	} else {
		$('#emailTest').text("");
	}
}
//密码验证
function passwordJiaoYan() {
	if ($('#password').val() == null || $('#password').val().trim() == '') {
		$('#passwordTest').text("请输入密码");
		return false;
	} else if (/^[0-9A-Za-z]+$/.test($('#password').val()) == false || /[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/.test($('#password').val()) == false) {
		$('#passwordTest').text("密码为长度在6-16位之间的字母与数字组合,且必须包含数字和字母,不能包含特殊符号");
		return false;
	} else if ($('#password').val().length > 16 || $('#password').val().length < 6) {
		$('#passwordTest').text("密码为长度在6-16位之间的字母与数字组合,且必须包含数字和字母,不能包含特殊符号");
		return false;
	} else {
		$('#passwordTest').text("");
	}
	return true;
}
//确认密码验证
function confirmPasswordJiaoYan() {
	if ($('#password').val() != $('#confirmPassword').val()) {
		$('#confirmPasswordTest').text("两次输入的密码不一致");
		return false;
	} else {
		$('#confirmPasswordTest').text("");
		return true;
	}
}

function staffNoJiaoYan(staffNo) {
	if ($('#biaoji').val() == null || $('#biaoji').val() == "") {
		if ($('#staffNo').val() == null || $('#staffNo').val().trim() == '') {
			$("#staffNoTest").text("请填写柜员编号");
			return false;
		}else if(/^[0-9]{6}$/.test($('#staffNo').val()) == false){
			$("#staffNoTest").text("请输入6位纯数字");
			return false;
		}
		jQuery.ajax({
			url: '/eximbank-console/counter/read/staffNo',
			type: 'GET',
			async: false,
			data: {
				account: $('#staffNo').val()
			},
			dataType: 'json',
			success: function (data) {
				if (data.accounts == "true") {
					$("#staffNoTest").text("柜员编号已存在");
					return false;
				} else {
					$("#staffNoTest").text("");
					return true;
				}
			}
		});
	}
}
//部门是否选中验证
function departmentJiaoYan() {
	var deptName = $("#department").val();
	var deptId = $("#deptId").val();
	if (deptId == '' || deptName == '') {
		$('#departmentTest').text("请选择柜员所属机构");
		return false;
	} else {
		$('#departmentTest').text("");
	}
	return true;
}

// 菜单树
appModule.directive('tree', function () {
	return {
		require: '?ngModel',
		restrict: 'A',
		link: function ($scope, element, attrs, ngModel) {
			// var opts = angular.extend({},
			// $scope.$eval(attrs.nlUploadify));
			// 初始化树
			showDept($scope, element, ngModel, 1);
		}
	};
});
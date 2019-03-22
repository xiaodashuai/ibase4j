'use strict';

angular.module('app')
	.controller('loginController', ['$rootScope', '$scope', '$http', '$state','$desUtil', function ($rootScope, $scope, $http, $state,$desUtil) {
		$scope.user = {};
		$scope.count = 0;
		$scope.changePassword = {};
		$scope.login = function () {
			$scope.user.userType = 1;
			var password = $desUtil.encode($desUtil.encode($scope.user.password));
            $.ajax({
                type: 'POST',
                url: '/eximbank-club/login',
                data: angular.toJson({
                    'account': $scope.user.account,
                    'password': password,
                    'userType': 1
                }),
            }).then(function (result) {
                if (result.httpCode == 200) {
                    $state.go('main.biz.homepage');
                } else {
                    $scope.msg = result.msg;
                    $rootScope.$apply();
                }
            });
        }
		$scope.commitChange = function () {
			$scope.count = 0;
			$scope.userNameJiaoYan();
			$scope.passwordJiaoYan();
			$scope.newPasswordJiaoYan();
			$scope.confirmNewPasswordJiaoYan();
			if ($scope.count != 0) {
				return false;
			}
			$.ajax({
				type: 'POST',
				url: '/eximbank-club/system/changePassword',
				data: angular.toJson($scope.changePassword)
			}).then(function (result) {
				if (result.httpCode == 200) {
					alert(result.data.massage);
					$("#myModal").hide();
				} else {
					$scope.msg = result.msg;
					$rootScope.$apply();
				}
			});
		}

		//用户名非空校验
		$scope.userNameJiaoYan = function () {
			var parent = $('#userName').parent();
			if ($("#userName").val() == null || $("#userName").val().trim() == "") {
				parent.find("span").remove();
				parent.append('<span class="text-danger" style="margin-left: -60px;font-size: 14;">' + "请输入登录名" + '</span>');
				$scope.count += 1;
			} else {
				parent.find("span").remove();
				parent.append('<span class="glyphicon glyphicon-ok-circle vali-success" style="margin-top:5px;"></span>');
			}
		}
		//旧密码非空校验
		$scope.passwordJiaoYan = function () {
			var parent = $('#password').parent();
			if ($("#password").val() == null || $("#password").val().trim() == "") {
				parent.find("span").remove();
				parent.append('<span class="text-danger" style="margin-left: -60px;font-size: 14;">' + "请输入旧密码" + '</span>');
				$scope.count += 1;
			} else {
				parent.find("span").remove();
				parent.append('<span class="glyphicon glyphicon-ok-circle vali-success" style="margin-top:5px;"></span>');
			}
		}
		//新密码非空校验
		$scope.newPasswordJiaoYan = function () {
			var parent = $('#newPassword').parent();
			if ($("#newPassword").val() == null || $("#newPassword").val().trim() == "") {
				parent.find("span").remove();
				parent.append('<span class="text-danger" style="margin-left: -60px;font-size: 14;">' + "请输入新密码" + '</span>');
				$scope.count += 1;
			} else if (/^[0-9A-Za-z]+$/.test($('#newPassword').val()) == false || /[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/.test($('#newPassword').val()) == false) {
				parent.find("span").remove();
				parent.append('<span class="text-danger" style="margin-left: -60px;font-size: 14;">' + "密码须包含数字和字母,不包含特殊符号" + '</span>');
				$scope.count += 1;
			} else if ($('#newPassword').val().length > 16 || $('#newPassword').val().length < 6) {
				parent.find("span").remove();
				parent.append('<span class="text-danger" style="margin-left: -60px;font-size: 14;">' + "密码长度在6~16位之间" + '</span>');
				$scope.count += 1;
			} else {
				parent.find("span").remove();
				parent.append('<span class="glyphicon glyphicon-ok-circle vali-success" style="margin-top:5px;"></span>');
			}
		}
		//确认密码非空校验
		$scope.confirmNewPasswordJiaoYan = function () {
			var parent = $('#confirmNewPassword').parent();
			if ($("#confirmNewPassword").val() != $("#newPassword").val()) {
				parent.find("span").remove();
				parent.append('<span class="text-danger" style="margin-left: -45px;font-size: 14;">' + "两次录入的密码不同,请重新输入" + '</span>');
				$scope.count += 1;
			} else if ($("#confirmNewPassword").val() == null || $("#confirmNewPassword").val().trim() == "") {
				parent.find("span").remove();
				parent.append('<span class="text-danger" style="margin-left: -45px;font-size: 14;">' + "请再次输入新密码" + '</span>');
				$scope.count += 1;
			} else {
				parent.find("span").remove();
				parent.append('<span class="glyphicon glyphicon-ok-circle vali-success" style="margin-top:5px;padding-left:60px;"></span>');
			}
		}
	}]);
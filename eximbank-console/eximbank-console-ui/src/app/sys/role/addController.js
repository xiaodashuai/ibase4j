'use strict';

/* App Module */
var appModule = angular.module('app');
// 操作控制
appModule.controller('roleAddController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
	function ($scope, $rootScope, $state, $timeout, toaster) {
		var title = "";
		//设置权限类别是管理权限
		if ($state.includes('**.role.modify')) {
			var id = $state.params.id;
			title = "业务岗位修改";
			active(id);
			validate();
		} else if ($state.includes('**.role.add')) {
			title = "业务岗位新增";
			validate();
		}
		$scope.title = $rootScope.title = title;
		$scope.loading = true;
		//初始化验证
		$scope.submit = function () {
			if (validate2()) {
				//设置类型是管理岗
				$scope.record.roleType = 1;
				var m = $scope.record;
				if (m) {
					$scope.isDisabled = true; //提交disabled
					$.ajax({
						url: '/eximbank-console/role',
						type: 'POST',
						data: angular.toJson($scope.record)
					}).then(callback)
				}

				function callback(result) {
					if (result) {
						if (result.httpCode == 200) { //成功
							toaster.clear('*');
							toaster.pop('success', '', "保存成功");
							$timeout(function () {
								$state.go('main.sys.role.list');
							}, 2000);
						} else {
							toaster.clear('*');
							toaster.pop('error', '', result.msg);
							$scope.isDisabled = false;
						}
					} else {
						$scope.msg = "系统走神了,请系统管理员!";
					}
				}
			}
		}
		// 初始化页面
		function active(id) {
			$scope.loading = true;
			$.ajax({
				type: 'POST',
				url: '/eximbank-console/role/read/detail',
				data: angular.toJson({
					'id': id
				})
			}).then(function (result) {
				$scope.loading = false;
				if (result) {
					if (result.httpCode == 200) {
						$scope.record = result.data;
						getChecked(id);
					} else {
						$scope.msg = result.msg;
					}
				} else {
					$scope.msg = "系统走神了,请系统管理员!";
				}
				$scope.$applyAsync();
			})
		}
		//查看选中的菜单
		function getChecked(roleid) {
			$scope.loading = true;
			$.ajax({
				type: 'POST',
				url: '/eximbank-console/role/read/getSelectedMenu',
				data: angular.toJson({
					'roleId': roleid
				})
			}).then(function (result) {
				$scope.loading = false;
				if (result != null) {
					if (result.httpCode == 200) {
						setTreeCheced(result.data);
					} else {
						$scope.msg = result.msg;
					}
				} else {
					$scope.msg = "系统走神了,请找系统管理员!";
				}
				$scope.$applyAsync();
			})
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
		//表单验证
		function validate2() {
			var ids = $("#menus").val();
			console.log(ids);
			if (ids == '') {
				$scope.roleMsg = "请选择权限！";
				return false;
			} else {
				$scope.roleMsg = "";

			}
			return true;
		}
	}
]);

/**
 * 功能：设置权限菜单选中
 * @param {Object} data
 */
function setTreeCheced(data) {
	var zTree = $.fn.zTree.getZTreeObj("tree");
	//zTree.cancelSelectedNode(); //先取消所有的选中状态
	$.each(data, function (i, v) {
		var ckId = v.menuId;
		var node = zTree.getNodeByParam("id", ckId);
		if (node != null) {
			node.checked = true; //将指定ID的节点选中
			zTree.updateNode(node);
		}
	});
}
// 菜单树
appModule.directive('addmenutree', function () {
	return {
		require: '?ngModel',
		restrict: 'A',
		link: function ($scope, element, attrs, ngModel) {
			// 初始化树
			showTree($scope, element, ngModel, 3, 1);
		}
	};
});
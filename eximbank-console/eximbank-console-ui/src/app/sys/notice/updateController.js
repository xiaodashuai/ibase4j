'use strict';

angular.module('app')
	.controller('noticeUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
		function ($scope, $rootScope, $state, $timeout, toaster) {
			var title = "";
			if ($state.includes('**.notice.update')) {
				title = "公告修改";
				biaoji();
				var id = $state.params.id;
				activate(id);
				getOrgSelectBox();
				getActiveSelectBox();
				validate();
			} else if ($state.includes('**.notice.create')) {
				title = "公告新增";
				getActiveSelectBox();
				getOrgSelectBox();
				validate();

			}
			$scope.title = $rootScope.title = title;
			$scope.loading = true;

			$scope.submit = function () {
				var m = $scope.record;
				if (m) {
					$scope.isDisabled = true; //提交disabled
					$.ajax({
						url: '/eximbank-console/notice',
						type: 'POST',
						data: angular.toJson($scope.record)
					}).then(callback)
				}

				function callback(result) {
					if (result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$timeout(function () {
							$state.go('main.sys.notice.list');
						}, 2000);
					} else {
						toaster.clear('*');
						toaster.pop('error', '', result.msg);
						$scope.isDisabled = false;
					}
				}
			}

			// 初始化页面
			function activate(id) {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/notice/read/detail',
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
					$scope.$applyAsync();
				})
			}
			//组织机构下拉框
			function getOrgSelectBox() {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/dept/read/queryOrgList',
					data: angular.toJson()
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.orgList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				});
			}

			//获取字典
			function getActiveSelectBox() {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/dic/read/query',
					data: angular.toJson({
						'type': 'NOTICETYPE'
					})
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.dicList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				})
			}
			// 表单验证
			function validate() {
				// notEqual 规则
				$.validator.addMethod('notEqual', function (value, ele) {
					return value != this.settings.rules[ele.name].notEqual;
				});
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
//编辑页面隐藏标签,令其无法修改
function biaoji() {
	if ($("#biaoji").val() != null) {
		$("#noticeTitles").hide();
	}
}
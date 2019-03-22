'use strict';

angular.module('app')
	.controller('chargeTypeUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
		function ($scope, $rootScope, $state, $timeout, toaster) {
			var title = "";
			if ($state.includes('**.chargeType.update')) {
				title = "费用类型修改";
				var id = $state.params.id;
				activate(id);
				getOrgSelectBox();
				validate();
			} else if ($state.includes('**.chargeType.create')) {
				title = "费用类型新增";
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
						url: '/eximbank-console/chargeType',
						type: 'POST',
						data: angular.toJson($scope.record)
					}).then(callback)
				}

				function callback(result) {
					if (result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$timeout(function () {
							$state.go('main.post.chargeType.list');
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
					url: '/eximbank-console/chargeType/read/detail',
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
					url: '/eximbank-console/productType/read/queryChildList',
					data: angular.toJson()
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.childList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				})
			}
			// 表单验证
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
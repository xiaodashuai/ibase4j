'use strict';

angular.module('app')
	.controller('dicUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
		function ($scope, $rootScope, $state, $timeout, toaster) {
			var title = "";
			var parentType = $state.params.type;
			$scope.itemType = parentType;

			if ($state.includes('**.dic.update')) {
				title = "修改二级码表";
				var id = $state.params.id;
				validate();
				activate(id);
			} else if ($state.includes('**.dic.create')) {
				validate();
				title = "新增二级码表";
			}
			$scope.title = $rootScope.title = title;
			$scope.loading = true;
			//保存
			$scope.submit = function () {
				var m = $scope.record;
				if (m) {
					$scope.isDisabled = true; //提交disabled
					$scope.record.type = $scope.itemType;
					$.ajax({
						url: '/eximbank-console/dic/dic',
						type: 'POST',
						data: angular.toJson($scope.record)
					}).then(callback)
				}

				function callback(result) {
					if (result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$timeout(function () {
							$state.go('main.sys.dicType.list');
						}, 2000);
					} else {
						toaster.clear('*');
						toaster.pop('error', '', result.msg);
						$scope.isDisabled = false;
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

			// 初始化页面
			function activate(id) {
				$scope.loading = true;

				$.ajax({
					url: '/eximbank-console/dic/read/detail',
					type: 'POST',
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

		}
	]);
'use strict';
/* App Module */
var appModule = angular.module('app');

appModule.controller('deptTreeController', [
	'$rootScope',
	'$scope',
	'$http',
	"$timeout",
	'$state',
	'toaster',
	function ($rootScope, $scope, $http, $timeout, $state, toaster) {
		$scope.title = '部门管理';
		$scope.param = {};
		$scope.loading = false;

		//组织机构初始化
		getOrgSelectBox();
		//获取字典(有效性)
		getActiveSelectBox();

		//组织机构下拉框
		function getOrgSelectBox() {
			$scope.loading = true;
			$.ajax({
				type: 'POST',
				url: '/eximbank-console/dept/read/queryOrgList',
				data: angular.toJson({
					'type': 0
				})
			}).then(function (result) {
				$scope.loading = false;
				if (result != null) {
					if (result != null && result.httpCode == 200) {
						$scope.orgList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				}
			})
		}

		//获取字典(有效性)
		function getActiveSelectBox() {
			$scope.loading = true;

			$.ajax({
				type: 'POST',
				url: '/eximbank-console/dic/read/query',
				data: angular.toJson({
					'type': 'LOCKED'
				})
			}).then(function (result) {
				$scope.loading = false;
				if (result != null) {
					if (result.httpCode == 200) {
						$scope.dicList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				}
			})
		}

		//保存新增部门
		$scope.submit = function () {
			var m = $scope.record;
			if (m) {
				$scope.isDisabled = true; //提交disabled

				$.ajax({
					type: 'POST',
					url: '/eximbank-console/dept',
					data: angular.toJson($scope.record)
				}).then(callback)
			}

			function callback(result) {
				if (result.httpCode == 200) { //成功
					toaster.clear('*');
					toaster.pop('success', '', "保存成功");
					$timeout(function () {
						$state.go('main.sys.dept.tree');
					}, 2000);
				} else {
					toaster.clear('*');
					toaster.pop('error', '', result.msg);
					$scope.isDisabled = false;
				}
			}
		}

	}
]);

// 菜单树
appModule.directive('edittree', function () {
	return {
		require: '?ngModel',
		restrict: 'A',
		link: function ($scope, element, attrs, ngModel) {
			// var opts = angular.extend({},
			// $scope.$eval(attrs.nlUploadify));
			// 初始化树
			showDeptZtree($scope, element, ngModel, 2);
		}
	};
});
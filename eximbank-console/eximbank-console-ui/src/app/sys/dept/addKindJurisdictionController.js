'use strict';

angular.module('app')
	.controller('addKindJurisdictionController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster',
		function ($scope, $rootScope, $state, $timeout, toaster) {
			$scope.title = '选择对应的种类';
			$scope.loading = false;
			var deptId = $state.params.id; //得到传过来的机构id
			getDeptproductTypeList(deptId);

			//初始化页面
			function getDeptproductTypeList(deptId) {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/productType/read/getProductType',
					data: angular.toJson({
						'deptId': deptId
					})
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						if (result.iTotalRecords > 0) {
							$scope.list = result.data;
							angular.forEach($scope.list, function (v, k) {
								if (v.checked == 'true') {
									v.checked = true;
								} else {
									v.checked = false;
								}
								angular.forEach(v.sonList, function (v1, k1) {
									if (v1.checked == 'true') {
										v1.checked = true;
									} else {
										v1.checked = false;
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

			//提交保存
			$scope.submitForm = function () {
				var deptId = $state.params.id;
				var m = getCheckIds();
				if (m.length > 0) {
					$scope.isDisabled = true; //提交disabled
					
							$.ajax({
								url: '/eximbank-console/productType/read/saveDeptProductType',
								type: 'POST',
								data: angular.toJson({
									'productTypesIds': m,
									'deptId': deptId
								})
							}).then(callback)
				} else {
					toaster.clear('*');
					toaster.pop('success', '', "请至少选择一条数据");
					$scope.isDisabled = false;
				}

				function callback(result) {
					if (result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						setTimeout(function () {
							$state.go('main.sys.dept.list');
						}, 1000);
					} else {
						toaster.clear('*');
						toaster.pop('error', '', result.msg);
						$scope.isDisabled = false;
					}
				}
			}
			//获取选中的数据权限的id 
			function getCheckIds() {
				var idStr = '';
				angular.forEach($scope.list, function (v, k) {
					if (v.checked == true) {
						idStr += v.code + ",";
						angular.forEach(v.sonList, function (v1, k2) {
							if (v1.checked == true) {
								idStr += v1.code + ",";
							}
						})
					}
				});
				console.log(idStr);
				return idStr;
			}
			//全选 事件
			$scope.all = function () {
				angular.forEach($scope.list, function (v, k) { //v是回写的每个vo，
					v.checked = $scope.selectAll;
					angular.forEach(v.sonList, function (v1, k1) {
						v1.checked = $scope.selectAll;
					})
				})
			}
			//单个选择按钮
			$scope.selectOne = function (m, n) {
				angular.forEach($scope.list, function (v, k) {
					angular.forEach(v.sonList, function (v1, k1) {
						if (n == v.code) {
							v.checked = m;
							v1.checked = m;
						}
					})
				})
			}

			//勾选子产品下选项框
			$scope.selectButton = function (m, n) {
				angular.forEach($scope.list, function (v, k) {
					var tom = 0;
					angular.forEach(v.sonList, function (v1, k1) {
						if (n == v1.code) {
							v1.checked = m;
						}
						if (v1.checked == true) {
							tom = +1;
						}
					})
					if (tom > 0) {
						v.checked = true;
					} else {
						v.checked = false;
					}
				})
			}

		}
	]);
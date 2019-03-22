'use strict';

angular.module('app')
	.controller('productTypeController', ['$rootScope', '$scope', '$http', '$state', '$window', 'toaster',
		function ($rootScope, $scope, $http, $state, $window, toaster) {
			$scope.title = '产品管理';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function () {
				$scope.loading = true;
				$.ajax({
					url: '/eximbank-console/productType/read/list',
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json;charset=UTF-8',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.pageInfo = result;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				})

			}

			$scope.query = function () {
				$scope.loading = true;
				$scope.param.pageNum = 1;
				$.ajax({
					url: '/eximbank-console/productType/read/list',
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json;charset=UTF-8',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.pageInfo = result;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				});
			}

			$scope.search();


			$scope.searchParentName = function () {
				$.ajax({
					url: '/eximbank-console/productType/read/parentList',
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json;charset=UTF-8',
					data: angular.toJson($scope.param)
				}).then(function (result) {
					if (result.httpCode == 200) {
						$scope.parentProTypes = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				})
			}



			$scope.clearSearch = function () {
				$scope.param.keyword = null;
				$scope.search();
			}

			//删除产品分类
			$scope.delete = function (id) {

				if ($window.confirm("确认删除该种类吗？")) {
					$.ajax({
						type: 'POST',
						url: '/eximbank-console/productType/update/deleteproductType',
						data: angular.toJson({
							'id': id,
							'enable': 0
						})
					}).then(function (result) {
						if (result.httpCode == 200) {
							$scope.search();
						} else {
							$scope.msg = result.msg;
						}
						$scope.$applyAsync();
					})
				}
			}
			//设置业务权限
			$scope.bindBusiness = function (id) {
				$state.go('main.post.businessType.bindBusiness', {
					productId: id
				});
			}

			//单个选择
			$scope.selectOne = function (m, n) {
				angular.forEach($scope.pageInfo.data, function (v, k) {
					if (n == v.id) {
						v.checked = m;
					} else {
						//如果选中一个,其余的都自动设置未选中
						if (m == true) {
							v.checked = false;
						}
					}
				})
			}
			/**
			 * 获取选中的值
			 */
			function getCheckItem() {
				var item = null;
				angular.forEach($scope.pageInfo.data, function (v, k) {
					if (v.checked == true) {
						item = v;
					}
				});
				return item;
			}

			//编辑账号
			$scope.edit = function () {
				var item = getCheckItem();
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				}
				$state.go('main.post.productType.update', {
					id: item.id
				});
			}
			// 翻页
			$scope.pagination = function (page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}
	]);
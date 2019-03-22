'use strict';

angular.module('app')
	.controller('checkPlanController', ['$rootScope', '$scope', '$http', '$state',
		function ($rootScope, $scope, $http, $state) {
			$scope.title = '检查计划配置管理';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function () {
				$scope.loading = true;
				$.ajax({
					url: '/eximbank-console/checkPlan/read/list',
					type: 'POST',
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
					url: '/eximbank-console/checkPlan/read/list',
					type: 'POST',
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

			$scope.search();
			getClassifySelectBox();
			$scope.clearSearch = function () {
				$scope.param.keyword = null;
				$scope.search();
			}
			//风险分类等级下拉框
			function getClassifySelectBox(id) {
				$scope.loading = true;
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/checkPlan/read/queryClassifyList',
					data: angular.toJson({
						'type': 0
					})
				}).then(function (result) {
					$scope.loading = false;
					if (result.httpCode == 200) {
						$scope.classifyList = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				})
			}
			$scope.disableItem = function (id, enable) {
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/checkPlan/update/updateCheckPlan',
					data: angular.toJson({
						'id': id,
						'enable': enable
					})
				}).then(function (result) {
					if (result.httpCode == 200) {
						$scope.search();
						getClassifySelectBox();
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				})
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
				$state.go('main.post.checkPlan.update', {
					id: item.id,
					classifyid: item.classfyLevelId
				});
			}
			// 翻页
			$scope.pagination = function (page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}
	]);
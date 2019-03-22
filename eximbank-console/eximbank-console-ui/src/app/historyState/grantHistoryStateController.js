'use strict';

angular.module('app')
	.controller('grantHistoryStateController', ['$rootScope', '$scope', '$http', '$state', '$window', 
			function ($rootScope, $scope, $http, $state, $window, ) {
				$scope.title = '发放审核历史数据状态管理';
				$scope.param = {};
				$scope.loading = false;

				$scope.search = function () {
					$scope.loading = true;
					$.ajax({
						url: '/eximbank-console/grantHistoryState/read/list',
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
						url: '/eximbank-console/grantHistoryState/read/list',
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

				$scope.search();

				$scope.clearSearch = function () {
					$scope.param.keyword = null;
					$scope.search();
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
					/**
			 * 启用，禁止
			 */
			$scope.disableItem = function (historyState) {
				var item = getCheckItem();
				if (item == null) {
					alert("请选择一条数据!");
					return false;
				}
				if(item.historyState == '3' && historyState == '3'){
					alert("状态已是未提交，禁止操作");
					return false;
				}else if(item.historyState == '4' && historyState == '4'){
					alert("状态已是已提交，禁止操作");
					return false;
				}
				if (window.confirm("您确认要执行吗？")) {
					item.historyState = historyState;
					$.ajax({
						type: 'POST',
						url: '/eximbank-console/grantHistoryState/update',
						data: angular.toJson(item)
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
					// 翻页
					$scope.pagination = function (page) {
						$scope.param.pageNum = page;
						$scope.search();
					};

				}
			]);
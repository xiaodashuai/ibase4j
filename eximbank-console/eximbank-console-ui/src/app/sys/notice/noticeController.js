'use strict';

angular.module('app')
	.controller('noticeController', ['$rootScope', '$scope', '$http', '$state', '$window', 'toaster',
		function ($rootScope, $scope, $http, $state, $window, toaster) {
			$scope.title = '公告管理';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function () {
				$scope.loading = true;
				$.ajax({
					url: '/eximbank-console/notice/read/list',
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

			$scope.clearSearch = function () {
				$scope.param.keyword = null;
				$scope.search();
			}

			$scope.publish = function (id, status) {
				$.ajax({
					type: 'POST',
					url: '/eximbank-console/notice/update/publishNotice',
					data: angular.toJson({
						'id': id,
						'status': status
					})
				}).then(function (result) {
					if (result.httpCode == 200) {
						toaster.clear('*');
						toaster.pop('success', '', "发布成功");
						$scope.search();
					} else {
						$scope.msg = result.msg;
					}
					$scope.$applyAsync();
				});
			}

			$scope.delete = function (id, afterStatus, nowStatus) {
				if (nowStatus == '1') {
					$window.alert("已发布的公告无法删除");
				} else {
					if ($window.confirm("确认删除该公告？")) {
						$.ajax({
							type: 'POST',
							url: '/eximbank-console/notice/update/deleteNotice',
							data: angular.toJson({
								'id': id,
								'status': status
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
			$scope.disableItem = function (status) {
				var url = null;
				var item = getCheckItem();
				if (status == 0) {
					url = '/eximbank-console/notice/update/deleteNotice';
					if (item.status == 1) {
						alert('公告已发布，不能删除！');
						return false;
					}
				} else if (status == 1) {
					url = '/eximbank-console/notice/update/publishNotice';
				}
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				}
				var id = item.id;
				if (window.confirm("您确认要执行吗？")) {
					$scope.loading = true;
					$.ajax({
						type: 'POST',
						url: url,
						data: angular.toJson({
							'id': id,
							'status': status
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
			//编辑账号
			$scope.edit = function () {

				var item = getCheckItem();
				if (item.status == 1) {
					alert('公告已发布，不能再次修改！');
					return false;
				}
				if (item == null) {
					alert("请选择一个项目!");
					return false;
				}
				$state.go('main.sys.notice.update', {
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
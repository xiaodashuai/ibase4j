angular.module('app').controller(
	'grantDiscardController', ['$rootScope', '$scope', '$state','$timeout','WebUploadService',
		function($rootScope, $scope, $state,$timeout,WebUploadService) {

			//参数传递
			var grantId = $state.params.id;

			var title = "发放废弃";
			$scope.title = $rootScope.title = title;

			//查询方案约束条件
			function getById(grantId) {
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/grant/getById',
					data: angular.toJson({
						"grantId": grantId
					})
				}).then(function(result) {
					if(result) {
						if(result.httpCode == 200) {
							$scope.record = result.data;
							$scope.record.gracePeriod = 0;
						} else {
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "服务器异常，请刷新重试或联系管理员";
					}
					$scope.loading = false;
					$scope.$apply();
				});
			}
			//查询数据
			getById(grantId);
			
			$scope.openStatus = 0;
			/**
			 * 功能：预览附件
			 * @param {Object} code
			 * @param {Object} index
			 */
			$scope.openWindow = function(code, index) {
				if($scope.openStatus == 0) { //第一次点击任意一笔
					WebUploadService.initFiles([code, code], [index, "B"], ["租金保理", "租金保理"],["zjblfa", "zjblsc"], $scope, $timeout,true);
					$scope.idd = index;
					$scope.openStatus++;
					return false;
				}
				if($scope.openStatus != 0) {
					if($scope.idd == index) { //第N次点击同一笔
						return false;
					} else { //第二次点击不同笔
						$scope.realFileName = ""; //清空文件名
						$scope.nclosureName = ""; //清空文件名
						angular.element('.filelist').remove(); //清空文件list
						WebUploadService.initFiles([code, code], [index, "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout,true);
						$scope.idd = index;
					}
				}
			}

			//初始化验证
			$scope.submit = function() {
				var m = $scope.record;
				if(validateFlied()&& m) {
					//设置不能重复提交                    
					$scope.isDisabled = true; //提交disabled
					$.ajax({
						url: '/eximbank-club/grant/saveDiscard',
						type: 'POST',
						data: angular.toJson($scope.record)
					}).then(callback);
				}
				function callback(result) {
					if(result.httpCode == 200) { //成功
						alert("提交成功");
						setTimeout(function() {
							$state.go('main.biz.grant.query');
						}, 1000);
					} else {
						alert(result.msg);
						$scope.isDisabled = false;
					}
				}
			}
			/**
			 * 验证表单格式和逻辑 
			 */
			function validateFlied() {
				var reason = $("#reason").val();
				if(reason=='') {
					alert("请输入废弃理由!");
					return false;
				}
				return true;
			}
		}

	]);
angular.module('app')
	.controller('bizController', ['$scope', '$modal', '$CanvasService','WebUploadService','$timeout',
		function($scope, $modal, $CanvasService, WebUploadService,$timeout) {

			$scope.openPop = function(canvasType, num, canvasCode, extra) {

				var modalInstance = $modal.open({
					templateUrl: 'src/app/biz/popup/canvas.tpl.html',
					controller: CanvasInstanceCtrl,
					backdrop: "static",
					resolve: {
						items: function($CanvasService) {
							var promise = $CanvasService.readCanvas(canvasType, num, canvasCode, extra);
							promise.then(function(success) {
								return success.data;
							});
							return promise;
						}
					}
				});
				/*modalInstance.opened.then(function() {
				    // 模态窗口打开之后执行的函数
				});
				modalInstance.result.then(function(result) {
				    // console.log(result);
				}, function(reason) {
				    // console.log(reason);
				});*/
			};
			var CanvasInstanceCtrl = function($scope, $modalInstance, items) {

				var slides = $scope.slides = [];
				var addSlide = function() {
					angular.forEach(items, function(data, index) {
						slides.push({
							id: index,
							image: data
						});
					});
				};
				addSlide();
				$scope.close = function() {
					$modalInstance.dismiss('cancel');
				};

			};

			// 查看附件
			$scope.openE = function(bizCode, bizType, product, fieldName,$scope,$timeout) {
				console.log(bizCode);
				var modalInstance = $modal.open({
					templateUrl: 'src/app/biz/popup/enclosure.tpl.html',
					controller: enclosureInstanceCtrl,
					backdrop: "static",
					resolve: {
						items: function(WebUploadService) {
							return WebUploadService.initFiles(bizCode, bizType, product, fieldName,$scope,$timeout);
						}
					}
				});
			};
			var enclosureInstanceCtrl = function($scope, $modalInstance, items) {
				WebUploadService.initFiles(bizCode, bizType, product, fieldName,$scope,$timeout);
				$scope.close = function() {
					$modalInstance.dismiss('cancel');
				};

			};

		}
	]);
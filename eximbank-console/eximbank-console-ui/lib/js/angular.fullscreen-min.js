var apiGatewayCtrls = angular.module('app', ['ionic']);

apiGatewayCtrls.controller('AppCtrl', function($scope, $window) {
	$scope.fullScreen = function() {
		var viewFullScreen = document.getElementById("view-fullscreen");
		if(viewFullScreen) {
			viewFullScreen.addEventListener("click", function() {
				var docElm = document.documentElement;
				if(docElm.requestFullscreen) {
					docElm.requestFullscreen();
				} else if(docElm.msRequestFullscreen) {
					docElm = document.body; //overwrite the element (for IE)
					docElm.msRequestFullscreen();
				} else if(docElm.mozRequestFullScreen) {
					docElm.mozRequestFullScreen();
				} else if(docElm.webkitRequestFullScreen) {
					docElm.webkitRequestFullScreen();
				}
			}, false);
		}
	}
	$scope.exitScreen = function() {
		var cancelFullScreen = document.getElementById("cancel-fullscreen");
		if(cancelFullScreen) {
			cancelFullScreen.addEventListener("click", function() {
				if(document.exitFullscreen) {
					document.exitFullscreen();
				} else if(document.msExitFullscreen) {
					document.msExitFullscreen();
				} else if(document.mozCancelFullScreen) {
					document.mozCancelFullScreen();
				} else if(document.webkitCancelFullScreen) {
					document.webkitCancelFullScreen();
				}
			}, false);
		}
	}
	$scope.systemClose = function() {
		var sytemExit = document.getElementById("sytemExit");
		if(sytemExit) {
			sytemExit.addEventListener("click", function() {
				if(confirm("您确定要关闭本系统吗？")) {
					$window.close();
				} else {}
			})
		}
	}
});
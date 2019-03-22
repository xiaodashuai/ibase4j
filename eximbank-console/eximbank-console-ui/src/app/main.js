'use strict';

var app = angular.module('app', [
    'ui.load',
    'ui.router',
    'ngStorage',
    'brantwills.paging',
    'oc.lazyLoad',
    'ngImgCrop'
]);

/* Controllers */
angular.module('app')
    .controller('AppCtrl', ['$scope', '$localStorage', '$window', '$http', '$state', '$rootScope',
        function ($scope, $localStorage, $window, $http, $state, $rootScope) {
            // add 'ie' classes to html
            var isIE = !!navigator.userAgent.match(/MSIE/i);
            isIE && angular.element($window.document.body).addClass('ie');
            isSmartDevice($window) && angular.element($window.document.body).addClass('smart');

            // config
            $scope.app = {
                name: 'eximbank',
                version: '0.0.2',
                // for chart colors
                color: {
                    primary: '#7266ba',
                    info: '#23b7e5',
                    success: '#27c24c',
                    warning: '#fad733',
                    danger: '#f05050',
                    light: '#e8eff0',
                    dark: '#3a3f51',
                    black: '#1c2b36'
                },
                settings: {
                    themeID: 1,
                    navbarHeaderColor: 'bg-black-only',
                    navbarCollapseColor: 'bg-dark-blue-only',
                    asideColor: 'bg-black',

                    headerFixed: true,
                    asideFixed: true,
                    asideFolded: false,
                    asideDock: false,
                    container: false
                }
            }
            $scope.defaultAvatar = $rootScope.defaultAvatar = 'res/img/np.png';

            $scope.logout = function () {
                if (!window.confirm("您确认要退出系统吗？")) {
                    return false;
                }
                return $http({
                    method: 'POST',
                    withCredentials: true,
                    url: '/eximbank-console/logout'
                }).then(function (result) {
                    var d = result.data;
                    if (d.httpCode == 200) { //注销成功
                        //deleteUserInfo();
                        $state.go('access.login');
                    }
                });

                function deleteUserInfo() {
                    //$.cookie('_ihome_uid', null);
                }
            }
            /**
             * 功能：点击行选中左侧复选 框
             * @param {Object} datas
             * @param {Object} current
             */
            $scope.onCheckedRow = function (datas, current) {
                if (current.checked == undefined) {
                    current.checked = true;
                } else {
                    current.checked = !current.checked;
                }
                angular.forEach(datas, function (v, k) {
                    if (current.id == v.id) {
                        v.checked = current.checked;
                    } else {
                        //如果选中一个,其余的都自动设置未选中
                        if (current.checked == true) {
                            v.checked = false;
                        }
                    }
                })
            };
            //显示通知
            $scope.showNotification = function () {
                if ($scope.notificationShow == 'active') {
                    $scope.notificationShow = "";
                } else {
                    $scope.notificationShow = "active";
                    //关闭公告
                    $scope.messagePopupShow = "";
                    //关闭工具栏
                    $scope.toolsBoxShow = "";
                }
            }
            //显示公告
            $scope.showMessage = function () {
                if ($scope.messagePopupShow == 'active') {
                    $scope.messagePopupShow = "";
                } else {
                    $scope.messagePopupShow = "active";
                    //关闭通知
                    $scope.notificationShow = "";
                    //关闭工具栏
                    $scope.toolsBoxShow = "";
                }
            }
            //显示工具栏
            $scope.showTools = function () {
                if ($scope.toolsBoxShow == 'active') {
                    $scope.toolsBoxShow = "";
                } else {
                    $scope.toolsBoxShow = "active";
                    //关闭通知
                    $scope.notificationShow = "";
                    //关闭公告
                    $scope.messagePopupShow = "";
                }
            }
            //全都不显示
            $scope.showOut = function () {
                $scope.toolsBoxShow = "";
                $scope.messagePopupShow = "";
                $scope.notificationShow = "";
                $scope.notificationShow = "";

            }
            //全屏显示模式
            $scope.showFullScreen = function () {
                var viewFullScreen = document.getElementById("view-fullscreen");
                if (viewFullScreen) {
                    var docElm = document.documentElement;
                    if (docElm.requestFullscreen) {
                        docElm.requestFullscreen();
                    } else if (docElm.msRequestFullscreen) {
                        docElm = document.body; //overwrite the element (for IE)
                        docElm.msRequestFullscreen();
                    } else if (docElm.mozRequestFullScreen) {
                        docElm.mozRequestFullScreen();
                    } else if (docElm.webkitRequestFullScreen) {
                        docElm.webkitRequestFullScreen();
                    }
                    $("#view-fullscreen").attr("title", "退出全屏");
                    $("#view-fullscreen").attr("id", "cancel-fullscreen");
                } else {
                    if (document.exitFullscreen) {
                        document.exitFullscreen();
                    } else if (document.msExitFullscreen) {
                        document.msExitFullscreen();
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen();
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen();
                    }
                    $("#cancel-fullscreen").attr("title", "全屏模式");
                    $("#cancel-fullscreen").attr("id", "view-fullscreen");
                }
            }
            //隐藏左侧菜单
            $scope.hideLeft = function () {
                $("html").toggleClass("js menu-active");
                var w_width = $("#app-aside").width();
                if (w_width == 75) {
                    $("#app-aside").width(200);
                    $("#app-content").css("margin-left", "200px");
                } else {
                    $("#app-aside").width(75);
                    $("#app-content").css("margin-left", "75px");
                }
            }
            $localStorage.settings = $scope.app.settings;

            // save settings to local storage  暂不支持自定义布局
            /*if (angular.isDefined($localStorage.settings)) {
                $scope.app.settings = $localStorage.settings;
            } else {
                $localStorage.settings = $scope.app.settings;
            }*/
            $scope.$watch('app.settings', function () {
                if ($scope.app.settings.asideDock && $scope.app.settings.asideFixed) {
                    // aside dock and fixed must set the header fixed.
                    $scope.app.settings.headerFixed = true;
                }
                // save to local storage
                $localStorage.settings = $scope.app.settings;
            }, true);

            // angular translate
            //$scope.lang = { isopen: false };
            //$scope.langs = {en:'English', de_DE:'German', it_IT:'Italian'};
            function isSmartDevice($window) {
                // Adapted from http://www.detectmobilebrowsers.com
                var ua = $window['navigator']['userAgent'] || $window['navigator']['vendor'] || $window['opera'];
                // Checks for iOs, Android, Blackberry, Opera Mini, and Windows mobile devices
                return (/iPhone|iPod|iPad|Silk|Android|BlackBerry|Opera Mini|IEMobile/).test(ua);
            }

            $.ajaxSetup({
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                xhrFields: {
                    withCredentials: true
                },
                beforeSend: function (evt, request, settings) {
                    //request.url = 'iBase4J-Web' + request.url;
                },
                dataFilter: function (result) {
                    try {
                        result = JSON.parse(result);
                        if (result.httpCode == 401) {
                            $state.go('access.login');
                            return null;
                        } else if (result.httpCode == 303) {
                        } else if (result.httpCode == 200) {
                            if (result.data && result.data.id) {
                                result.data.id = result.data.id_;
                            } else if (result.data) {
                                var r = result.data;
                                for (var i = 0; i < r.length; i++)
                                    r[i].id = r[i].id_;
                            }
                        } else if (result.data) {
                            error(result.data.msg);
                        }
                        return JSON.stringify(result);
                    } catch (e) {
                        return result;
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    switch (jqXHR.status) {
                        case(404):
                            alert("未找到请求的资源");
                            break;
                    }
                }
            });
        }
    ]);
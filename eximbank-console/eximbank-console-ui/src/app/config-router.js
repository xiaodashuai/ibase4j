'use strict';
//  

var app = angular.module('app')
	.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
		// 默认地址
		$urlRouterProvider.otherwise('/access/login');
		// 状态配置
		$stateProvider
			.state('main', {
				abstract: true,
				url: '',
				templateUrl: 'src/tpl/app.html'
			})
			.state('access', {
				url: '/access',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('access.login', {
				url: '/login',
				templateUrl: 'src/app/sys/login/login.html',
				controller: 'loginController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/login/loginController.js');
					}]
				}
			})
			.state('main.sys', {
				url: '/sys',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			}) //业务
			.state('main.biz', {
				url: '/biz',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.historyState', {
				url: '/historyState',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			//债项方案数据迁移状态管理
			.state('main.historyState.debt', {
				url: '/debt',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.historyState.debt.list', {
				url: '/list',
				templateUrl: 'src/app/historyState/debtHistoryState.html',
				controller: 'debtHistoryStateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/historyState/debtHistoryStateController.js');
					}]
				}
			})
			//发放审核数据迁移状态管理
			.state('main.historyState.grant', {
				url: '/grant',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.historyState.grant.list', {
				url: '/list',
				templateUrl: 'src/app/historyState/grantHistoryState.html',
				controller: 'grantHistoryStateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/historyState/grantHistoryStateController.js');
					}]
				}
			})
			.state('main.log', {
				url: '/log',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			
			// 登录日志
			.state('main.log.loginLog', {
				url: '/loginLog',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.log.loginLog.list', {
				url: '/list',
				templateUrl: 'src/app/sys/log/loginLog.html',
				controller: 'loginLogController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/log/loginLogController.js');
					}]
				}
			})
			// 操作日志
			.state('main.log.operationLog', {
				url: '/operationLog',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.log.operationLog.list', {
				url: '/list',
				templateUrl: 'src/app/sys/log/operationLog.html',
				controller: 'operationLogController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/log/operationLogController.js');
					}]
				}
			})
			// 管理员
			.state('main.sys.user', {
				url: '/user',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.user.list', {
				url: '/list',
				templateUrl: 'src/app/sys/user/user.html',
				controller: 'userController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/userController.js');
					}]
				}
			})
			.state('main.sys.user.model', {
				url: '/model',
				templateUrl: 'src/app/sys/motai/motai.html',
				controller: 'userController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/userController.js');
					}]
				}
			})
			.state('main.sys.user.create', {
				url: '/create',
				templateUrl: 'src/app/sys/user/update.html',
				controller: 'userUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.user.update', {
				url: '/update/{id}?params',
				//url: '/update/:id',
				templateUrl: 'src/app/sys/user/update.html',
				controller: 'userUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.user.bindDataRule', {
				url: '/list/{userId}?params',
				templateUrl: 'src/app/sys/user/dataRule_modal.html',
				controller: 'dataRuleModalController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/dataRuleModalController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //柜员 
			.state('main.sys.counter', {
				url: '/counter',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			//柜员列表
			.state('main.sys.counter.list', {
				url: '/list',
				templateUrl: 'src/app/sys/user/counter.html',
				controller: 'counterController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/counterController.js');
					}]
				}
			})
			.state('main.sys.counter.create', {
				url: '/create',
				templateUrl: 'src/app/sys/user/modify.html',
				controller: 'counterUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/modifyController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.counter.update', {
				url: '/update/{id}?params',
				//url: '/update/:id',
				templateUrl: 'src/app/sys/user/modify.html',
				controller: 'counterUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/user/modifyController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.counter.bindDataRule', {
				url: '/list/{Id}?params',
				templateUrl: 'src/app/sys/counter/dataRule_modal.html',
				controller: 'dataRuleModalController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/counter/dataRuleModalController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 部门
			.state('main.sys.dept', {
				url: '/dept',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.dept.list', {
				url: '/list',
				templateUrl: 'src/app/sys/dept/dept.html',
				controller: 'deptController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/deptController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //部门tree显示
			.state('main.sys.dept.tree', {
				url: '/tree',
				templateUrl: 'src/app/sys/dept/dept_tree.html',
				controller: 'deptTreeController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/deptTreeController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dept.create', {
				url: '/create',
				templateUrl: 'src/app/sys/dept/update.html',
				controller: 'deptUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dept.update', {
				url: '/update/:id/:parentCode',
				templateUrl: 'src/app/sys/dept/update.html',
				controller: 'deptUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//机构访问权限绑定
			.state('main.sys.dept.addVisitJurisdiction', {
				url: '/addVisitJurisdiction/:id/:unitid',
				templateUrl: 'src/app/sys/dept/addVisitJurisdiction.html',
				controller: 'addVisitJurisdictionController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/addVisitJurisdictionController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//机构和产品种类绑定
			.state('main.sys.dept.addKindJurisdiction', {
				url: '/addKindJurisdiction/:id/:unitid',
				templateUrl: 'src/app/sys/dept/addKindJurisdiction.html',
				controller: 'addKindJurisdictionController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dept/addKindJurisdictionController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//邮件模板
			.state('main.sys.emailTemplate', {
				url: '/emailTemplate',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.emailTemplate.list', {
				url: '/list',
				templateUrl: 'src/app/sys/email/emailTemplate.html',
				controller: 'emailTemplateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/email/emailTemplateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.emailTemplate.create', {
				url: '/create',
				templateUrl: 'src/app/sys/email/update.html',
				controller: 'updateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/email/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.emailTemplate.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/email/update.html',
				controller: 'updateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/email/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//短信模板
			.state('main.sys.msgConfig', {
				url: '/msgConfig',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.msgConfig.list', {
				url: '/list',
				templateUrl: 'src/app/sys/msg/msgConfig.html',
				controller: 'msgConfigController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/msg/msgConfigController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.msgConfig.create', {
				url: '/create',
				templateUrl: 'src/app/sys/msg/update.html',
				controller: 'updateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/msg/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.msgConfig.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/msg/update.html',
				controller: 'updateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/msg/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 菜单
			.state('main.sys.menu', {
				url: '/menu',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.menu.list', {
				url: '/list',
				templateUrl: 'src/app/sys/menu/menu.html',
				controller: 'menuController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/menu/menuController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.menu.add', {
				url: '/create',
				templateUrl: 'src/app/sys/menu/update.html',
				controller: 'menuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/menu/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.menu.addSystem', {
				url: '/createSystem',
				templateUrl: 'src/app/sys/menu/updateSystem.html',
				controller: 'menuUpdateSystemController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/menu/updateSystemController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.menu.update', {
				url: '/update/{id}?params/:menuName',
				templateUrl: 'src/app/sys/menu/update.html',
				controller: 'menuUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/menu/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.menu.updateSystem', {
				url: '/updateSystem/{id}?params/:menuName',
				templateUrl: 'src/app/sys/menu/updateSystem.html',
				controller: 'menuUpdateSystemController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/menu/updateSystemController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 角色
			.state('main.sys.role', {
				url: '/role',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.role.list', {
				url: '/list',
				templateUrl: 'src/app/sys/role/role.html',
				controller: 'roleController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role/roleController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //添加管理岗
			.state('main.sys.role.create', {
				url: '/create',
				templateUrl: 'src/app/sys/role/update.html',
				controller: 'roleUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //添加业务岗
			.state('main.sys.role.add', {
				url: '/add',
				templateUrl: 'src/app/sys/role/add.html',
				controller: 'roleAddController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role/addController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.role.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/role/update.html',
				controller: 'roleUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.role.modify', {
				url: '/modify/{id}?params',
				templateUrl: 'src/app/sys/role/add.html',
				controller: 'roleAddController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/role/addController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 会话
			.state('main.sys.session', {
				url: '/session',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.session.list', {
				url: '/list',
				templateUrl: 'src/app/sys/session/session.html',
				controller: 'sessionController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/session/sessionController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 字典
			.state('main.sys.dicType', {
				url: '/dicType',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.dicType.list', {
				url: '/list',
				templateUrl: 'src/app/sys/dic/dicType.html',
				controller: 'dicTypeController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/dicTypeController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dicType.create', { //新增
				url: '/create',
				templateUrl: 'src/app/sys/dic/dicTypeUpdate.html',
				controller: 'dicTypeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/dicTypeUpdateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dicType.update', { //编辑
				url: '/update/{id}?dataRule',
				templateUrl: 'src/app/sys/dic/dicTypeUpdate.html',
				controller: 'dicTypeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/dicTypeUpdateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dicType.detail', { //二级字典
				url: '/detail/{code}?params',
				templateUrl: 'src/app/sys/dic/dic.html',
				controller: 'dicController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/dicController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //二级
			.state('main.sys.dic', {
				url: '/dic',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.dic.create', { //二级添加
				url: '/create/{type}?params',
				templateUrl: 'src/app/sys/dic/update.html',
				controller: 'dicUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dic.update', { //二级编辑
				url: '/update/{id}?dataRule',
				templateUrl: 'src/app/sys/dic/update.html',
				controller: 'dicUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dic/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 访问控制(数据访问权限)
			.state('main.sys.dataRule', {
				url: '/dataRule',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.dataRule.list', {
				url: '/list',
				templateUrl: 'src/app/sys/dataRule/dataRule.html',
				controller: 'dataRuleController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dataRule/dataRuleController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dataRule.create', {
				url: '/create',
				templateUrl: 'src/app/sys/dataRule/update.html',
				controller: 'updateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dataRule/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.dataRule.update', {
				url: '/update/{id}?dataRule',
				templateUrl: 'src/app/sys/dataRule/update.html',
				controller: 'updateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/dataRule/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) // 公告管理
			.state('main.sys.notice', {
				url: '/notice',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.sys.notice.list', {
				url: '/list',
				templateUrl: 'src/app/sys/notice/notice.html',
				controller: 'noticeController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/notice/noticeController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.notice.create', {
				url: '/create',
				templateUrl: 'src/app/sys/notice/update.html',
				controller: 'noticeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/notice/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.notice.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/notice/update.html',
				controller: 'noticeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/notice/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})

			.state('main.sys.group.addJurisdiction', {
				url: '/addJurisdiction/{id}?params',
				templateUrl: 'src/app/sys/group/addJurisdiction.html',
				controller: 'addJurisdictionController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/group/addJurisdictionController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 调度
			.state('main.task', {
				url: '/task',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.task.scheduled', {
				url: '/scheduled',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.task.scheduled.list', {
				url: '/list',
				templateUrl: 'src/app/task/scheduled/scheduled.html',
				controller: 'taskScheduledController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduled/scheduledController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.scheduled.create', {
				url: '/update',
				templateUrl: 'src/app/task/scheduled/update.html',
				controller: 'scheduledUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduled/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.scheduled.update', {
				url: '/update',
				params: {
					'taskGroup': null,
					'taskName': null,
					'taskCron': null,
					'taskDesc': null,
					'taskType': null,
					'jobType': null,
					'targetObject': null,
					'targetMethod': null
				},
				templateUrl: 'src/app/task/scheduled/update.html',
				controller: 'scheduledUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduled/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.task.log', {
				url: '/log',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.task.log.list', {
				url: '/list',
				templateUrl: 'src/app/task/scheduled/log.html',
				controller: 'scheduledLogController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/task/scheduled/logController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})

			.state('main.biz.countryRiskRating', {
				url: '/countryRiskRating',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			// 国别风险信息查询
			.state('main.biz.countryRiskRating.list', {
				url: '/list',
				templateUrl: 'src/app/sys/countryRiskRating/countryRiskRating.html',
				controller: 'countryRiskRatingController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/countryRiskRating/countryRiskRatingController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//国别风险信息详情
			.state('main.biz.countryRiskRating.detail', {
				url: '/detail/:id/:riskLevel',
				templateUrl: 'src/app/sys/countryRiskRating/update.html',
				controller: 'countryRiskRatingUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/countryRiskRating/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.sys.cust', {
				url: '/cust',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})

			// 客户信息查询
			.state('main.sys.cust.list', {
				url: '/list',
				templateUrl: 'src/app/sys/cust/cust.html',
				controller: 'custController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/cust/custController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//客户信息详情
			.state('main.sys.cust.detail', {
				url: '/detail/:id',
				templateUrl: 'src/app/sys/cust/update.html',
				controller: 'custUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/cust/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 业务功能 --DJ
			.state('main.biz.workflow', {
				url: '/workflow',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			// 我的待办 --DJ
			.state('main.biz.workflow.todo', {
				url: '/toDoTask',
				templateUrl: 'src/app/biz/workflow/toDoTask.html',
				controller: 'toDoTaskController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/biz/workflow/taskController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 我的已办 --DJ
			.state('main.biz.workflow.havedone', {
				url: '/haveDoneTask',
				templateUrl: 'src/app/biz/workflow/toDoTask.html',
				controller: 'haveDoneTaskController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/biz/workflow/haveDoneTaskController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 待办详情页面 --DJ
			.state('main.biz.workflow.taskDetail', {
				url: '/taskDetail/:taskId/:piid/:ptid/:adid',
				templateUrl: 'src/app/biz/middle/middleTest.html',
				controller: 'taskDetailController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/biz/workflow/taskDetailController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 业务功能
			.state('main.biz.leave', {
				url: '/leave',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			//待办
			.state('main.biz.leave.list', {
				url: '/read',
				templateUrl: 'src/app/biz/task/wait.html',
				controller: 'leaveController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/biz/task/waitController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //已办
			.state('main.biz.leave.query', {
				url: '/query',
				templateUrl: 'src/app/biz/task/finished.html',
				controller: 'leaveController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/biz/task/finishedController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //申请
			.state('main.biz.leave.create', {
				url: '/create',
				templateUrl: 'src/app/biz/task/update.html',
				controller: 'leaveUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/biz/task/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //修改
			.state('main.biz.leave.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/biz/task/update.html',
				controller: 'leaveUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/biz/task/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //审核
			.state('main.biz.leave.audit', {
				url: '/audit/:id/:taskId/:piid/:ptid/:adid',
				templateUrl: 'src/app/biz/task/audit.html',
				controller: 'leaveAuditController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/biz/task/leaveAuditController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//欢迎页面
			.state('main.sys.welcome', {
				url: '/welcome',
				templateUrl: 'src/app/welcome.html',
				controller: 'portalController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/portal/portalController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//事后配置
			.state('main.post', {
				url: '/post',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			//费用类型管理
			.state('main.post.chargeType', {
				url: '/chargeType',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.post.chargeType.list', {
				url: '/list',
				templateUrl: 'src/app/post/charge/chargeType.html',
				controller: 'chargeTypeController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/charge/chargeTypeController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.chargeType.create', {
				url: '/create',
				templateUrl: 'src/app/post/charge/update.html',
				controller: 'chargeTypeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/charge/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.chargeType.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/post/charge/update.html',
				controller: 'chargeTypeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/charge/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// .state('main.post.chargeType.delete', {
			// 	url: '/delete/{id}?params',
			// 	templateUrl: 'src/app/post/charge/update.html',
			// 	controller: 'chargeTypeUpdateController',
			// 	resolve: {
			// 		deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
			// 			return uiLoad.load('src/app/post/charge/updateController.js').then(function() {
			// 				return $ocLazyLoad.load('toaster');
			// 			});
			// 		}]
			// 	}
			// })

			//产品种类管理
			.state('main.post.productType', {
				url: '/productType',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.post.productType.list', {
				url: '/list',
				templateUrl: 'src/app/sys/productType/productType.html',
				controller: 'productTypeController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/productType/productTypeController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.productType.create', {
				url: '/create',
				templateUrl: 'src/app/sys/productType/update.html',
				controller: 'productTypeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/productType/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.productType.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/productType/update.html',
				controller: 'productTypeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/productType/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//业务种类管理
			.state('main.post.businessType', {
				url: '/businessType',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.post.businessType.list', {
				url: '/list',
				templateUrl: 'src/app/sys/businessType/businessType.html',
				controller: 'businessTypeController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/businessType/businessTypeController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.businessType.create', {
				url: '/create',
				templateUrl: 'src/app/sys/businessType/update.html',
				controller: 'businessTypeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/businessType/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.businessType.update', {
				url: '/update/{id}?params',
				templateUrl: 'src/app/sys/businessType/update.html',
				controller: 'businessTypeUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/businessType/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.businessType.bindBusiness', {
				url: '/list/{productId}?params',
				templateUrl: 'src/app/sys/businessType/business_modal.html',
				controller: 'businessModalController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/businessType/businessModalController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//检查计划配置
			.state('main.post.checkPlan', {
				url: '/checkPlan',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.post.checkPlan.list', {
				url: '/list',
				templateUrl: 'src/app/post/checkPlan/checkPlan.html',
				controller: 'checkPlanController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/checkPlan/checkPlanController.js');
					}]
				}
			})
			.state('main.post.checkPlan.add', {
				url: '/create',
				templateUrl: 'src/app/post/checkPlan/update.html',
				controller: 'checkPlanUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/checkPlan/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.checkPlan.update', {
				url: '/update/:id/:classifyid',
				templateUrl: 'src/app/post/checkPlan/update.html',
				controller: 'checkPlanUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/checkPlan/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})

			//减值比例配置
			.state('main.post.reductionRatioFormula', {
				url: '/reductionRatioFormula',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.post.reductionRatioFormula.list', {
				url: '/list',
				templateUrl: 'src/app/post/reductionRatioFormula/reductionRatioFormula.html',
				controller: 'reductionRatioFormulaController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/reductionRatioFormula/reductionRatioFormulaController.js');
					}]
				}
			})
			.state('main.post.reductionRatioFormula.add', {
				url: '/add',
				templateUrl: 'src/app/post/reductionRatioFormula/update.html',
				controller: 'reductionRatioFormulaUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/reductionRatioFormula/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.reductionRatioFormula.update', {
				url: '/update/:id/:classifyid',
				templateUrl: 'src/app/post/reductionRatioFormula/update.html',
				controller: 'reductionRatioFormulaUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/reductionRatioFormula/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.classifyLevel', {
				url: '/classifyLevel',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.post.classifyLevel.list', {
				url: '/list',
				templateUrl: 'src/app/post/classifyLevel/classifyLevel.html',
				controller: 'classifyLevelController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/classifyLevel/classifyLevelController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.classifyLevel.add', {
				url: '/add',
				templateUrl: 'src/app/post/classifyLevel/update.html',
				controller: 'classifyLevelUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/classifyLevel/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.classifyLevel.update', {
				url: '/update/:id/:unitid',
				templateUrl: 'src/app/post/classifyLevel/update.html',
				controller: 'classifyLevelUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/classifyLevel/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			}) //风险初分类设置管理
			.state('main.post.riskClassify', {
				url: '/riskClassify',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.post.riskClassify.list', {
				url: '/list',
				templateUrl: 'src/app/post/riskClassify/riskClassify.html',
				controller: 'riskClassifyController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/riskClassify/riskClassifyController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.riskClassify.add', {
				url: '/add',
				templateUrl: 'src/app/post/riskClassify/update.html',
				controller: 'riskClassifyUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/riskClassify/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			.state('main.post.riskClassify.update', {
				url: '/update/:id',
				templateUrl: 'src/app/post/riskClassify/update.html',
				controller: 'riskClassifyUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/post/riskClassify/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//信息记录
			.state('main.record', {
				url: '/record',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})

			// 邮件发送记录
			.state('main.record.email', {
				url: '/email',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.record.email.list', {
				url: '/list',
				templateUrl: 'src/app/sys/email/lookEmail.html',
				controller: 'lookEmailController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/email/lookEmailController.js');
					}]
				}
			})
			// 短信发送记录
			.state('main.record.msg', {
				url: '/msg',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			.state('main.record.msg.list', {
				url: '/list',
				templateUrl: 'src/app/sys/msg/msg.html',
				controller: 'msgController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/msg/msgController.js');
					}]
				}
			})
			//公共功能
			.state('main.common', {
				url: '/common',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})

			//客户主体信息
			.state('main.common.customer', {
				url: '/customer',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			//客户主体信息获取显示
			.state('main.common.customer.detail', {
				url: '/list',
				templateUrl: 'src/app/common/customer/customer.html',
				controller: 'customerController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/common/customer/customerController.js');
					}]
				}
			})
			//客户主体信息保存
			.state('main.common.customer.add', {
				url: '/add/:custNo',
				templateUrl: 'src/app/common/customer/tanchuang.html',
				controller: 'tanchuangController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/common/customer/tanchuangController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			//客户授信信息
			.state('main.common.credit', {
				url: '/credit',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			//客户授信信息获取显示
			.state('main.common.credit.detail', {
				url: '/detail',
				templateUrl: 'src/app/common/credit/customer.html',
				controller: 'creditController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/common/credit/creditController.js');
					}]
				}
			})
			.state('main.common.credit.list', {
				url: '/list',
				templateUrl: 'src/app/common/credit/credit.html',
				controller: 'creditController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/common/credit/creditController.js');
					}]
				}
			})
			//客户授信信息保存
			.state('main.common.credit.add', {
				url: '/add',
				templateUrl: 'src/app/common/credit/update.html',
				controller: 'creditUpdateController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/common/credit/updateController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
			// 工作流
			.state('main.sys.workflow', {
				url: '/workflow',
				template: '<div ui-view class="fade-in-right-big smooth"></div>'
			})
			// 工作流设计器
			.state('main.sys.workflow.design', {
				url: '/design',
				templateUrl: 'src/app/sys/processDesign/processDesign.html',
				controller: 'processDesignController',
				resolve: {
					deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
						return uiLoad.load('src/app/sys/processDesign/processDesignController.js').then(function () {
							return $ocLazyLoad.load('toaster');
						});
					}]
				}
			})
	}])
	.controller("navCtrl", ['$rootScope', '$http', '$q',
	function ($rootScope, $http, $q) {
		var deferred = $q.defer();
		$http({
			method: 'GET',
			url: '/eximbank-console/read/promission',
		}).then(function successCallback(response) {

			if (response != null) {
				if (response.status == 200) {
					$rootScope.userInfo = response.data.user;
					$rootScope.menuList = response.data.menus;
					$rootScope.$applyAsync();
				}
			}
			console.log("successCallback", response);
			deferred.resolve(response);
		}, function errorCallback(response) {
			if (response.status == 502 || response.status == 0 || response.status == 302) {
				$state.go('access.login');
			}
			console.log("errorCallback", response);
			deferred.reject(response);
		});
		return deferred.promise;
	}])
	.run(['$rootScope', '$state', '$stateParams', '$timeout', '$templateCache',
		function ($rootScope, $state, $stateParams, $timeout, $templateCache) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
			$rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
				var from = fromState.name,
					to = toState.name;
				if (from && to) { // 解决 相应模块从列表进入编辑后 状态丢失问题
					var s1 = from.substring(0, from.lastIndexOf(".")),
						g1 = from.substring(from.lastIndexOf(".") + 1),
						s2 = to.substring(0, to.lastIndexOf(".")),
						g2 = to.substring(to.lastIndexOf(".") + 1);
					if (s1 == s2) {
						if (g1 == 'list' && (g2 == 'update' || g2 == 'view')) { //进行编辑
							toParams['params'] = window.location.hash;
						}
						//返回列表
						if ((g1 == "update" || g1 == 'view') && g2 == 'list') {
							var h = fromParams['params'];
							if (h) {
								$timeout(function () {
									window.location.hash = h;
								});
							}
						}
					}
				}
			});
		}
	]);
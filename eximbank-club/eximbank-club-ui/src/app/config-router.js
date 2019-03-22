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
                controller: 'bizController',
                template: '<div ui-view class="fade-in-right-big smooth"></div>',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/main/bizController.js');
                    }]

                }
            })
            //业务
            .state('main.biz', {
                url: '/biz',
                controller: 'bizController',
                template: '<div ui-view class="fade-in-right-big smooth"></div>',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/main/bizController.js');
                    }]

                }
            })

            //事前
            .state('main.biz.response', {
                url: '/response',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })
            //补录
            .state('main.biz.record', {
                url: '/record',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })
            //方案数据迁移
            .state('main.sys.historyDebt', {
                url: '/historyDebt',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })
            //方案数据迁移暂存
            .state('main.sys.historySchemeTem', {
                url: '/historySchemeTem',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })
            // 用户
            .state('main.sys.user', {
                url: '/user',
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
            //客户信息查询
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
            .state('main.sys.cust.create', {
                url: '/create',
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
            .state('main.sys.cust.update', {
                url: '/update/{id}?params',
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
            .state('main.sys.cust.delete', {
                url: '/delete/{id}?params',
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
            //找回密码
            .state('main.sys.user.forgetPwd', {
                url: '/forgetPassword',
                templateUrl: 'src/app/sys/login/forgetPassword.html',
                controller: 'forgetPasswordController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/sys/login/forgetPasswordController.js');
                    }]
                }
            })
            //主页
            .state('main.biz.homepage', {
                url: '/list',
                templateUrl: 'src/app/sys/login/homePage.html',
                controller: 'homePageController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/sys/login/homePageController.js');
                    }]
                }
            })
            //事前项方案---xiaoshuiquan
            .state('main.biz.response.list', {
                url: '/list',
                templateUrl: 'src/app/biz/debtScheme/debtScheme.html',
                controller: 'debtSchemeController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/debtScheme/debtSchemeController.js');
                    }]
                }
            })
            //方案查看
            .state('main.biz.record.check', {
                url: '/check',
                templateUrl: 'src/app/biz/checkDebtScheme/checkDebtScheme.html',
                controller: 'checkDebtSchemeController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/checkDebtScheme/checkDebtSchemeController.js');
                    }]
                }
            })

            //方案审核查看
            .state('main.biz.grant.grantQueryList', {
                url: '/grantQueryList',
                templateUrl: 'src/app/biz/grantQueryList/grantQueryList.html',
                controller: 'grantQueryListController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grantQueryList/grantQueryListController.js');
                    }]
                }
            })

            //方案补录---xiaoshuiquan
            .state('main.biz.record.programme', {
                url: '/programme',
                templateUrl: 'src/app/biz/debtScheme/debtScheme.html',
                controller: 'debtSchemeController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/debtScheme/debtSchemeController.js');
                    }]
                }
            })
            //方案修订---xiaoshuiquan
            .state('main.biz.record.schemeRevise', {
                url: '/schemeRevise/:debtCode/:statt',
                templateUrl: 'src/app/biz/checkDebtScheme/schemeRevise.html',
                controller: 'schemeReviseController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/checkDebtScheme/schemeReviseController.js');
                    }]
                }
            })

            // 债项暂存查看----xiaoshuiquan
            .state('main.biz.record.schemeTemStorage', {
                url: '/schemeTemStorage',
                templateUrl: 'src/app/biz/schemeTemStorage/schemeTemStorage.html',
                controller: 'schemeTemStorageController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/schemeTemStorage/schemeTemStorageController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 债项暂存继续----xiaoshuiquan
            .state('main.biz.record.edit', {
                url: '/edit/:id/:bizcode/:statee',
                templateUrl: 'src/app/biz/schemeTemStorage/schemeTemContinue.html',
                controller: 'schemeTemContinueController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/schemeTemStorage/schemeTemContinueController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 数据迁移列表----xiaoshuiquan
            .state('main.sys.historyDebt.list', {
                url: '/list',
                templateUrl: 'src/app/biz/historyDebt/checkHistoryDebt.html',
                controller: 'checkHistoryDebtController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/historyDebt/checkHistoryDebtController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 数据迁移继续----xiaoshuiquan
            .state('main.sys.historyDebt.edit', {
                url: '/edit/:id/:debtCode',
                templateUrl: 'src/app/biz/historyDebt/historyDebt.html',
                controller: 'historyDebtController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/historyDebt/historyDebtController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 数据迁移暂存列表----xiaoshuiquan
            .state('main.sys.historySchemeTem.list', {
                url: '/list',
                templateUrl: 'src/app/biz/historyDebt/historySchemeTemStorage.html',
                controller: 'historySchemeTemStorageController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/historyDebt/historySchemeTemStorageController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // .state('main.biz.response.list', {
            //     url: '/list',
            //     templateUrl: 'src/app/biz/debtScheme/debtScheme.html',
            //     controller: 'modalDemo',
            //     resolve: {
            //         deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
            //             return uiLoad.load('lib/js/AlertC.js');
            //         }]
            //     }
            // })

            //债项概要
            /*.state('main.biz.response.debtSummary', {
                url: '/debtSummary/{debtNum}?params',
                templateUrl: 'src/app/biz/debtSummary/debtSummary.html',
                controller: 'debtSummaryController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/debtSummary/debtSummaryController.js');
                    }]
                }
            })

            //用信主体
            .state('main.biz.response.useLetter', {
                url: '/useLetter/:proIds/:debtNum',
                templateUrl: 'src/app/biz/useLetter/useLetter.html',
                controller: 'useLetterController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/useLetter/useLetterController.js');
                    }]
                }
            })

            //查询信贷系统的客户
            .state('main.biz.response.queryCustomer', {
                url: '/queryCustomer/{debtNum}?params',
                templateUrl: 'src/app/biz/useLetter/queryCustomer.html',
                controller: 'queryCustomerController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/useLetter/queryCustomerController.js');
                    }]
                }
            })

            //点击详情，回显客户
            .state('main.biz.response.selectCustomer', {
                url: '/selectCustomer/:custNo/:debtNum/:proIds',
                templateUrl: 'src/app/biz/useLetter/queryCustomer.html',
                controller: 'queryCustomerController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/useLetter/queryCustomerController.js');
                    }]
                }
            })

            //为客户选择产品
            .state('main.biz.response.queryProduct', {
                url: '/queryProduct/:debtNum',
                templateUrl: 'src/app/biz/useLetter/queryProduct.html',
                controller: 'queryProductController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/useLetter/queryProductController.js');
                    }]
                }
            })

            //产品组合
            .state('main.biz.response.productMix', {
                url: '/productMix/:debtNum',
                templateUrl: 'src/app/biz/productMix/productMix.html',
                controller: 'productMixController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/productMix/productMixController.js');
                    }]
                }
            })

            //风险分析
            .state('main.biz.response.risk', {
                url: '/risk/:debtNum',
                templateUrl: 'src/app/biz/risk/risk.html',
                controller: 'riskController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/risk/riskController.js');
                    }]
                }
            })

            //专有信息页面
            .state('main.biz.response.propInformation', {
                url: '/propInformation/:debtNum',
                templateUrl: 'src/app/biz/propInformation/propInformation.html',
                controller: 'propInformationController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function(uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/propInformation/propInformationController.js');
                    }]
                }
            })*/

            // 工作流 --DJ
            .state('main.biz.workflow', {
                url: '/workflow',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })

            // 我的待办 --DJ
            .state('main.biz.workflow.todo', {
                url: '/toDoTask',
                templateUrl: 'src/app/biz/workflow/toDoTask.html',
                controller: 'taskController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/workflow/taskController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 我的已办 --DJ
            .state('main.biz.workflow.haveDone', {
                url: '/haveDoneTask',
                templateUrl: 'src/app/biz/workflow/haveDoneTask.html',
                controller: 'taskController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/workflow/taskController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 用户
            .state('main.biz.approve', {
                url: '/approve',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })
            // 发放模块
            .state('main.biz.grant', {
                url: '/grant',
                template: '<div ui-view class="fade-in-right-big smooth"></div>',
            })
            //弹出框
            // 审批 --lwh
            .state('main.biz.grant.obligationList', {
                url: '/obligationGrant',
                templateUrl: 'src/app/biz/obligationGrant/obligationGrant.html',
                controller: 'obligationGrantController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/obligationGrant/obligationGrantController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放页面For发起处事经办
            .state('main.biz.grant.list', {
                url: '/list',
                templateUrl: 'src/app/biz/grant/programme_list.html',
                controller: 'programmeController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/programmeController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 已提交的发放申请
            .state('main.biz.grant.query', {
                url: '/query',
                templateUrl: 'src/app/biz/grant/grant_list.html',
                controller: 'grantController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 发放数据迁移补录
            .state('main.biz.grant.migratioin', {
                url: '/migr',
                templateUrl: 'src/app/biz/grant/grant_migratioin_list.html',
                controller: 'grantMigratioinController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantMigratioinController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 查询暂存
            .state('main.biz.grant.queryTemp', {
                url: '/queryTemp',
                templateUrl: 'src/app/biz/grant/temp_list.html',
                controller: 'tempController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/tempController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 查询发放数据迁移暂存
            .state('main.biz.grant.queryHistory', {
                url: '/queryHistory',
                templateUrl: 'src/app/biz/grant/temp_histroy_list.html',
                controller: 'tempHistroyController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/tempHistroyController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放暂存编辑
            .state('main.biz.grant.edit', {
                url: '/edit/:id/:code',
                templateUrl: 'src/app/biz/grant/grant_edit.html',
                controller: 'grantEditController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantEditController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放变更的暂存编辑
            .state('main.biz.grant.editChangeTemp', { 
                url: '/editChangeTemp/:id/:code',
                templateUrl: 'src/app/biz/grant/grant_edit_temp.html',
                controller: 'grantEditChangeTempController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantEditChangeTempController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放可变更列表查询
            .state('main.biz.grant.change', {
                url: '/change',
                templateUrl: 'src/app/biz/grant/grant_approved_list.html',
                controller: 'grantApprovedController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantApprovedController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放变更编辑
            .state('main.biz.grant.changeEdit', {
                url: '/changeEdit/:id',
                templateUrl: 'src/app/biz/grant/grant_edit_change.html',
                controller: 'grantEditChangeController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantEditChangeController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放变更编辑
            .state('main.biz.grant.repeatEdit', {
                url: '/repeatEdit/:id',
                templateUrl: 'src/app/biz/grant/grant_repeat_edit.html',
                controller: 'grantEditRepeatController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantEditRepeatController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 编辑债项发放数据迁移
            .state('main.biz.grant.migrationEdit', {
                url: '/dataMigration/:id',
                templateUrl: 'src/app/biz/grant/grant_migration_edit.html',
                controller: 'grantEditMigrationController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantEditMigrationController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放废弃
            .state('main.biz.grant.discard', {
                url: '/discard/:id',
                templateUrl: 'src/app/biz/grant/discard_edit.html',
                controller: 'grantDiscardController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantDiscardController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放添加申请For发起处事经办
            .state('main.biz.grant.add', {
                url: '/add/:proIds/:businessCode/:debtNum',
                templateUrl: 'src/app/biz/grant/grant_add.html',
                controller: 'grantAddController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/grantAddController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放页面For发起处事经办
            .state('main.biz.grant.attachmentUp', {
                url: '/attachmentUp',
                templateUrl: 'src/app/biz/grant/attachmentUp.html',
                controller: 'attachmentUpController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grant/attachmentUpController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放页面For补录发放列表
            .state('main.biz.grant.listSupplement', {
                url: '/listSupplement',
                templateUrl: 'src/app/biz/grantSupplement/grantSupplementProgramme_list.html',
                controller: 'grantSupplementProgrammeController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grantSupplement/grantSupplementProgrammeController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放添加申请For补录发放
            .state('main.biz.grant.addSupplement', {
                url: '/addSupplement/:proIds/:businessCode/:debtNum',
                templateUrl: 'src/app/biz/grantSupplement/grantSupplement_add.html',
                controller: 'grantSupplementAddController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grantSupplement/grantSupplementAddController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })


            // 债项发放页面For发起处室经办
            .state('main.biz.grant.manageInformation', {
                url: '/grant_ZCXSX',
                templateUrl: 'src/app/biz/grant/grant_ZCXSX.html',
                controller: 'manageInformationController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/manageInformation/manageInformationController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放页面For业务发起处室经办驳回 --YLQ
            .state('main.biz.grant.grantLoanYWJBBH', {
                url: '/grantLoanYWJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/grantLoan/grantLoanYWJBBH.html',
                controller: 'grantLoanYWJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grantLoan/grantLoanYWJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //审批详情 --YLQ
            .state('main.biz.grant.grantDetailsLoanYWJBBH', {
                url: '/grantDetailsLoanYWJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/details/grantDetailsLoanYWJBBH.html',
                controller: 'grantDetailsLoanYWJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/grantDetailsLoanYWJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //发放启动审批详情 --YLQ
            .state('main.biz.grant.grantDetailsLoanYWJBQD', {
                url: '/grantLoanDetailsYWJBQD/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/details/grantLoanDetailsYWJBQD.html',
                controller: 'grantLoanDetailsYWJBQDController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/grantLoanDetailsYWJBQDController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 债项发放页面For业务发起处室经办审批 --YLQ
            .state('main.biz.grant.grantLoanYWJB', {
                url: '/grantLoanYWJB/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/grantLoan/grantLoanYWJB.html',
                controller: 'grantLoanYWJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grantLoan/grantLoanYWJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //审批详情 --YLQ
            .state('main.biz.grant.grantDetailsLoanYWJB', {
                url: '/grantDetailsLoanYWJB/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/details/grantDetailsLoanYWJB.html',
                controller: 'grantDetailsLoanYWJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/grantDetailsLoanYWJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放页面For业务发起处室处长审批 --YLQ
            .state('main.biz.grant.grantLoanYWCZ', {
                url: '/grantLoanYWCZ/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/grantLoan/grantLoanYWCZ.html',
                controller: 'grantLoanYWCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grantLoan/grantLoanYWCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //审批详情 --YLQ
            .state('main.biz.grant.grantLoanDetailsYWCZ', {
                url: '/grantLoanDetailsYWCZ/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/details/grantLoanDetailsYWCZ.html',
                controller: 'grantLoanDetailsYWCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/grantLoanDetailsYWCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放页面For风险经办发放审批 --YLQ
            .state('main.biz.grant.grantLoanFXJB', {
                url: '/grantLoanFXJB/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/grantLoan/grantLoanFXJB.html',
                controller: 'grantLoanFXJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grantLoan/grantLoanFXJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //审批详情 --YLQ
            .state('main.biz.grant.grantLoanDetailsFXJB', {
                url: '/grantLoanDetailsFXJB/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/details/grantLoanDetailsFXJB.html',
                controller: 'grantLoanDetailsFXJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/grantLoanDetailsFXJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 债项发放页面For风险处长发放审批 --YLQ
            .state('main.biz.grant.grantLoanFXCZ', {
                url: '/grantLoanFXCZ/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/grantLoan/grantLoanFXCZ.html',
                controller: 'grantLoanFXCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/grantLoan/grantLoanFXCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //审批详情 --YLQ
            .state('main.biz.grant.grantLoanDetailsFXCZ', {
                url: '/grantLoanDetailsFXCZ/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:grantCode/:debtCode',
                templateUrl: 'src/app/biz/details/grantLoanDetailsFXCZ.html',
                controller: 'grantLoanDetailsFXCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/grantLoanDetailsFXCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 补录业务处长审批 --YLQ
            .state('main.biz.grant.blProcessCZ', {
                url: '/blProcessCZ/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/process/blProcessCZ.html',
                controller: 'blProcessCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/blProcessCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //详情
            .state('main.biz.grant.blProcessDetailsCZ', {
                url: '/blProcessDetailsCZ/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/details/blProcessDetailsCZ.html',
                controller: 'blProcessDetailsCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/blProcessDetailsCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 补录业务经办驳回 --YLQ
            .state('main.biz.grant.blProcessJBBH', {
                url: '/blProcessJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:debtCode',
                templateUrl: 'src/app/biz/process/blProcessJBBH.html',
                controller: 'blProcessJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/blProcessJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //详情
            .state('main.biz.grant.blDetailsProcessJBBH', {
                url: '/blDetailsProcessJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:debtCode',
                templateUrl: 'src/app/biz/details/blDetailsProcessJBBH.html',
                controller: 'blDetailsProcessJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/blDetailsProcessJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //补录启动已办详情
            .state('main.biz.grant.blDetailsProcessJBQD', {
                url: '/blDetailsProcessJBQD/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/details/blDetailsProcessJBQD.html',
                controller: 'blDetailsProcessJBQDController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/blDetailsProcessJBQDController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 补录业务经办发送核心 --YLQ
            .state('main.biz.grant.blProcessCZFSHX', {
                url: '/blProcessJBFSHX/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/process/blProcessJBFSHX.html',
                controller: 'blProcessJBFSHXController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/blProcessJBFSHXController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //详情
            .state('main.biz.grant.blProcessDetailsJBFSHX', {
                url: '/blProcessDetailsJBFSHX/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/details/blProcessDetailsJBFSHX.html',
                controller: 'blProcessDetailsJBFSHXController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/blProcessDetailsJBFSHXController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 修订业务处长 --YLQ
            .state('main.biz.grant.xdProcessCZ', {
                url: '/xdProcessCZ/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/process/xdProcessCZ.html',
                controller: 'xdProcessCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/xdProcessCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //详情
            .state('main.biz.grant.xdProcessDetailsCZ', {
                url: '/xdProcessDetailsCZ/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/details/xdProcessDetailsCZ.html',
                controller: 'xdProcessDetailsCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/xdProcessDetailsCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 修订业务经办 --YLQ
            .state('main.biz.grant.xdProcessJB', {
                url: '/xdProcessJB/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/process/xdProcessJB.html',
                controller: 'xdProcessJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/xdProcessJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //详情
            .state('main.biz.grant.xdProcessDetailsJB', {
                url: '/xdProcessDetailsJB/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/details/xdProcessDetailsJB.html',
                controller: 'xdProcessDetailsJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/xdProcessDetailsJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 修订经办驳回 --YLQ
            .state('main.biz.grant.xdProcessJBBH', {
                url: '/xdProcessJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:debtCode',
                templateUrl: 'src/app/biz/process/xdProcessJBBH.html',
                controller: 'xdProcessJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/xdProcessJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //详情
            .state('main.biz.grant.xdDetailsProcessJBBH', {
                url: '/xdDetailsProcessJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:debtCode',
                templateUrl: 'src/app/biz/details/xdDetailsProcessJBBH.html',
                controller: 'xdDetailsProcessJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/xdDetailsProcessJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //修订启动详情
            .state('main.biz.grant.xdDetailsProcessJBQD', {
                url: '/xdDetailsProcessJBQD/:taskId/:piid/:ptid/:adid/:debtCode',
                templateUrl: 'src/app/biz/details/xdDetailsProcessJBQD.html',
                controller: 'xdDetailsProcessJBQDController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/xdDetailsProcessJBQDController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 废弃业务经办 --YLQ
            .state('main.biz.grant.fqProcessJB', {
                url: '/fqProcessJB/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/process/fqProcessJB.html',
                controller: 'fqProcessJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/fqProcessJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //废弃经办通过详情
            .state('main.biz.grant.fqProcessDetailsJB', {
                url: '/fqProcessDetailsJB/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/fqProcessDetailsJB.html',
                controller: 'fqProcessDetailsJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/fqProcessDetailsJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })


            // 废弃业务处长 --YLQ
            .state('main.biz.grant.fqProcessCZ', {
                url: '/fqProcessCZ/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/process/fqProcessCZ.html',
                controller: 'fqProcessCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/fqProcessCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //废弃处长审核详情
            .state('main.biz.grant.fqProcessDetailsCZ', {
                url: '/fqProcessDetailsCZ/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/fqProcessDetailsCZ.html',
                controller: 'fqProcessDetailsCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/fqProcessDetailsCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })



            // 废弃业务经办驳回 --YLQ
            .state('main.biz.grant.fqProcessJBBH', {
                url: '/fqProcessJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/process/fqProcessJBBH.html',
                controller: 'fqProcessJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/fqProcessJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //废弃经办驳回详情
            .state('main.biz.grant.fqDetailsProcessJBBH', {
                url: '/fqDetailsProcessJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/fqDetailsProcessJBBH.html',
                controller: 'fqDetailsProcessJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/fqDetailsProcessJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //废弃启动详情
            .state('main.biz.grant.fqDetailsProcessJBQD', {
                url: '/fqDetailsProcessJBQD/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/fqDetailsProcessJBQD.html',
                controller: 'fqDetailsProcessJBQDController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/fqDetailsProcessJBQDController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更启动详情
            .state('main.biz.grant.bgDetailsProcessJBQD', {
                url: '/bgDetailsProcessJBQD/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/bgDetailsProcessJBQD.html',
                controller: 'bgDetailsProcessJBQDController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/bgDetailsProcessJBQDController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更处长详情
            .state('main.biz.grant.bgProcessDetailsCZ', {
                url: '/bgProcessDetailsCZ/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/bgProcessDetailsCZ.html',
                controller: 'bgProcessDetailsCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/bgProcessDetailsCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更风险经办审核
            .state('main.biz.grant.bgProcessFXJB', {
                url: '/bgProcessJB/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/process/bgProcessFXJB.html',
                controller: 'bgProcessFXJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/bgProcessFXJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更风险经办审核详情
            .state('main.biz.grant.bgProcessDetailsFXJB', {
                url: '/bgProcessDetailsFXJB/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/bgProcessDetailsFXJB.html',
                controller: 'bgProcessDetailsFXJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/bgProcessDetailsFXJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更风险处长审核
            .state('main.biz.grant.bgProcessFXCZ', {
                url: '/bgProcessFXCZ/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/process/bgProcessFXCZ.html',
                controller: 'bgProcessFXCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/bgProcessFXCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更风险处长审核详情
            .state('main.biz.grant.bgProcessDetailsFXCZ', {
                url: '/bgProcessDetailsFXCZ/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/bgProcessDetailsFXCZ.html',
                controller: 'bgProcessDetailsFXCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/bgProcessDetailsFXCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更经办审核
            .state('main.biz.grant.bgProcessJB', {
                url: '/bgProcessJB/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/process/bgProcessJB.html',
                controller: 'bgProcessJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/bgProcessJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //详情
            .state('main.biz.grant.bgProcessDetailsJB', {
                url: '/bgProcessDetailsJB/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/bgProcessDetailsJB.html',
                controller: 'bgProcessDetailsJBController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/bgProcessDetailsJBController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //详情
            .state('main.biz.grant.bgDetailsProcessJBBH', {
                url: '/bgDetailsProcessJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/details/bgDetailsProcessJBBH.html',
                controller: 'bgDetailsProcessJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/details/bgDetailsProcessJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更处长审核
            .state('main.biz.grant.bgProcessCZ', {
                url: '/bgProcessCZ/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/process/bgProcessCZ.html',
                controller: 'bgProcessCZController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/bgProcessCZController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            //变更经办驳回审核
            .state('main.biz.grant.bgProcessJBBH', {
                url: '/bgProcessJBBH/:taskId/:piid/:ptid/:adid/:aiid/:starterId/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/process/bgProcessJBBH.html',
                controller: 'bgProcessJBBHController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/process/bgProcessJBBHController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 放款 --DJ
            .state('main.biz.makeloans', {
                url: '/makeloans',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })
            // 查询所有满足放款条件的方案
            .state('main.biz.makeloans.list', {
                url: '/list/:grantCode/:productName/:proposer/:beginDate/:endDate',
                templateUrl: 'src/app/biz/makeloans/makeloans.html',
                controller: 'makeloansController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/makeloans/makeloansController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 查询满足放款条件方案的详情
            .state('main.biz.makeloans.detail', {
                url: '/detail/:grantCode/:businessTypes/:objInr/:debtCode/:productName/:proposer/:beginDate/:endDate/:grantCodes',
                templateUrl: 'src/app/biz/makeloans/makeloansDetail.html',
                controller: 'makeloansDetailController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/makeloans/makeloansDetailController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 还款计划
            .state('main.biz.makeloans.paymentPlan', {
                url: '/paymentPlan/:grantCode/:businessTypes/:objInr/:debtCode/:productName/:proposer/:beginDate/:endDate/:grantCodes',
                templateUrl: 'src/app/biz/makeloans/paymentPlan.html',
                controller: 'paymentPlanController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/makeloans/paymentPlanController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 还款计划
            .state('main.biz.makeloans.updatePaymentPlan', {
                url: '/updatePaymentPlan/:grantCode/:businessTypes/:objInr/:debtCode',
                templateUrl: 'src/app/biz/makeloans/updatePaymentPlan.html',
                controller: 'updatePaymentPlanController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/makeloans/updatePaymentPlanController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 放款废弃 待办
            .state('main.biz.makeloans.toDoDiscard', {
                url: '/discard/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/makeloans/discardMakeLoans.html',
                controller: 'discardMakeLoansController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/makeloans/discardMakeLoansController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 放款废弃 已办
            .state('main.biz.makeloans.hadDiscard', {
                url: '/discard/:taskId/:piid/:ptid/:adid/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/makeloans/hadDiscardMakeLoans.html',
                controller: 'discardMakeLoansController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/makeloans/discardMakeLoansController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })

            // 台账 --DJ
            .state('main.biz.standingBook', {
                url: '/standingBook',
                template: '<div ui-view class="fade-in-right-big smooth"></div>'
            })
            // 发放条件台账
            .state('main.biz.makeloans.grant', {
                url: '/grant',
                templateUrl: 'src/app/biz/standingBook/grantStandingBookList.html',
                controller: 'grantStandingBookListController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/standingBook/grantStandingBookListController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 发放条件台账 发放条件明细
            .state('main.biz.standingBook.grantInfoDetail', {
                url: '/grantInfoDetail/:debtCode/:grantCode',
                templateUrl: 'src/app/biz/standingBook/grantInfoDetail.html',
                controller: 'grantInfoDetailController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/standingBook/grantInfoDetailController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 统计信息查询
            .state('main.biz.standingBook.statisticaInfo', {
                url: '/statisticaInfo',
                templateUrl: 'src/app/biz/standingBook/statisticaInfoList.html',
                controller: 'statisticaInfoListController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/standingBook/statisticaInfoListController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 方案信息台账
            .state('main.biz.standingBook.debt', {
                url: '/debt',
                templateUrl: 'src/app/biz/standingBook/debtStandingBookList.html',
                controller: 'debtStandingBookListController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/standingBook/debtStandingBookListController.js').then(function () {
                            return $ocLazyLoad.load('toaster');
                        });
                    }]
                }
            })
            // 方案信息台账 方案信息明细
            .state('main.biz.standingBook.debtInfoDetail', {
                url: '/debtInfoDetail/:debtCode',
                templateUrl: 'src/app/biz/standingBook/debtInfoDetail.html',
                controller: 'debtInfoDetailController',
                resolve: {
                    deps: ['uiLoad', '$ocLazyLoad', function (uiLoad, $ocLazyLoad) {
                        return uiLoad.load('src/app/biz/standingBook/debtInfoDetailController.js').then(function () {
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
            });
    }])
    .controller("navCtrl", function ($rootScope, $state) {
        $.ajax({
            url: '/eximbank-club/read/promission',
            type: 'POST',
            timeout: 2500,
            cache: false,
            async: false,
            contentType: 'application/json',
            headers: {},
            success: function (result) {
                if (result != null) {
                    if (result.httpCode == 200) {
                        $rootScope.userInfo = result.user;
                        $rootScope.roleCodes = result.roleCodes;
                        $rootScope.menuList = result.menus;
                        $rootScope.$applyAsync();
                    }
                }
            }
        });
})
.
run(['$rootScope', '$state', '$stateParams', '$timeout', '$templateCache',
    function ($rootScope, $state, $stateParams, $timeout, $templateCache) {
        $rootScope.$state = $state;
        $rootScope.$stateParams = $stateParams;
        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            var from = fromState.name,
                to = toState.name;
            if (from && to) { // 解决 相应模块从列表进入编辑后 状态丢失问题
            	//设置$state.go跳转后，左侧菜单自动切换（高亮显示）
            	on_active_menu(to);
            	//
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
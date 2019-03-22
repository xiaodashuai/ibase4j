angular.module('app').controller(
	'grantMigratioinController', ['$rootScope', '$scope', '$state','$http','WebUploadService','$timeout',
		function($rootScope, $scope, $state,$http,WebUploadService,$timeout) {
			$scope.title = '发放条件查询';
			$scope.param = {};
			$scope.loading = false;
			
			$scope.search = function() {
				var dt = new DateUtil();
				var st_dt = $scope.param.stateTime;
				var end_dt = $scope.param.endTime;
				if(dt.compareDate(end_dt, st_dt) == 1) {
					var msg = "开始日期必须在结束日期(" + end_dt + ")之前!";
					alert(msg);
					return false; 
				}
				$scope.param.pageNum=1;
				$scope.param.pageIndex=1;
				$scope.param.deptCode = $("#deptCode").val();
				//执行查询
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/migratioin/read/list',
					data: angular.toJson($scope.param)
				}).then(function(result) {
					$scope.loading = false;
					if(result) {
						if(result.httpCode == 200) {
							$scope.pageInfo = result;
						} else {
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "服务器异常，请刷新重试或联系管理员";
					}
					$scope.$apply();
				});
			}
			/**
			 * 初始化机构
			 */
			function initDepartment(){
				bulidDeptSelect("deptCode");
				var size = $("#deptCode").find("option").size();
				if(size == 2){
					$("#deptCode").get(0).selectedIndex=1; 
				}
			}
			//初始化机构下拉框
			initDepartment();
			//查询列表
			$scope.search();
			
			/**
			 * 清除查询条件
			 */
			$scope.clearSearch = function(fm) {
				//清空 angluarjs的参数
				$scope.param.schemeNum = null;
				$scope.param.businessName = null;
				$scope.param.applicant = null;
				$scope.param.stateTime = null;
				$scope.param.endTime = null;
				$scope.param.historyStatus = null;
				var size = $("#deptCode").find("option").size();
				if(size == 2){
					$("#deptCode").get(0).selectedIndex=1; 
				}else if(size>2){//多个所属机构才清空选择 
					$("#deptCode").val("");
					$scope.param.deptCode = null;
				}
			}
			$scope.webUploadStatus = 0;
			/**
			 * 功能：预览附件
			 * @param {Object} code
			 * @param {Object} index
			 */
			$scope.openWindow = function(code, index) {
				if($scope.webUploadStatus == 0) { //第一次点击任意一笔
					WebUploadService.initFiles([code, code], ["", "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout,true);
					$scope.idd = index;
					$scope.webUploadStatus++;
					return false;
				}
				if($scope.webUploadStatus != 0) {
					if($scope.idd == index) { //第N次点击同一笔
						return false;
					} else { //第二次点击不同笔
						$scope.realFileName = ""; //清空文件名
						$scope.nclosureName = ""; //清空文件名
						angular.element('.filelist').remove(); //清空文件list
						WebUploadService.initFiles([code, code], ["", "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout,true);
						$scope.idd = index;
					}
				}
			}
			
			/**
			 * 导出excel文件
			 */
			$scope.exportExcel = function () {
                $http({
                    url: '/eximbank-club/migratioin/read/exportExcel',
                    method: "PUT",//接口方法
                    headers: {
                        'Content-type': 'application/json'
                    },
                    data: angular.toJson($scope.param),
                    responseType: 'arraybuffer'
                }).success(function (data) {
                    var blob = new Blob([data], {type: "application/vnd.ms-excel;charset=utf-8"});
                    var objectUrl = URL.createObjectURL(blob);
                    var a = document.createElement('a');
                    document.body.appendChild(a);
                    a.setAttribute('style', 'display:none');
                    a.setAttribute('href', objectUrl); 
                    var filename="发放条件历史数据.xls";
                    a.setAttribute('download', filename);
                    a.click();
                    URL.revokeObjectURL(objectUrl);
                })
            }
			
			/**
			 * 编辑数据
			 * @param {Object} sid
			 */
			$scope.edit = function(sid,code) {
				//判断是否有暂存
				alertTempMsg(sid,code);
			}
			
			/**
			 * 功能：提醒暂存信息
			 * @param {Object} grantCode
			 */
			function alertTempMsg(sid,grantCode){
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/temp/getTempCount',
					data: angular.toJson({
						"grantCode": grantCode
					})
				}).then(function(result) {
					if(result) {
						if(result.httpCode == 200) {
							var count = result.data;
							if(count>0){
								var msg = "该条信息已有暂存记录，如需对暂存继续编辑，请进入暂存查询交易进行操作。取消，取消操作；确定，可能清除或更新暂存记录！";
								confirm("提示",msg,
								function(flag){
									if(flag){
										//进入编辑
										$state.go('main.biz.grant.migrationEdit', {id: sid});
									}
								})
							}else{
								//不提醒直接进入编辑
								$state.go('main.biz.grant.migrationEdit', {id: sid});
							}
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
			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}

	]);
angular.module('app').controller(
	'grantController', ['$rootScope', '$scope', '$state', '$timeout', 'WebUploadService',
		function($rootScope, $scope, $state, $timeout, WebUploadService) {
			$scope.title = '发放条件查询';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function(clean) {
				var dt = new DateUtil();
				var st_dt = $scope.param.stateTime;
				var end_dt = $scope.param.endTime;
				//验证日期是否合法
				if(dt.compareDate(end_dt, st_dt) == 1) {
					var msg = "开始日期必须在结束日期(" + end_dt + ")之前!";
					alert(msg);
					return false;
				}
				//重置分页 
				isResetFirstPage(clean);
				//部门编码赋值给angluarjs
				$scope.param.deptCode = $("#deptCode").val();
				//执行查询
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/grant/grantQueryList',
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
			function initDepartment() {
				bulidDeptSelect("deptCode");
				var size = $("#deptCode").find("option").size();
				if(size == 2) {
					$("#deptCode").get(0).selectedIndex = 1;
					$("#deptCode").prop("disabled", true);
				}else if(size > 2){
					$("#deptCode").removeProp("disabled");
				}
			}
			//初始化机构下拉框
			initDepartment();
			//查询列表
			$scope.search(0);

			$scope.webUploadStatus = 0;
			/**
			 * 功能：预览附件
			 * @param {Object} code
			 * @param {Object} index
			 */
			$scope.openWindow = function(code, index) {
				if($scope.webUploadStatus == 0) { //第一次点击任意一笔
					WebUploadService.initFiles([code, code], [index, "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout, true);
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
						WebUploadService.initFiles([code, code], [index, "B"], ["租金保理", "租金保理"], ["zjblfa", "zjblsc"], $scope, $timeout, true);
						$scope.idd = index;
					}
				}
			}

			/**
			 * 清除查询条件
			 */
			$scope.clearSearch = function(fm) {
				//清空 angluarjs的参数
				$scope.param.keyword = null;
				$scope.param.schemeNum = null;
				$scope.param.businessName = null;
				$scope.param.applicant = null;
				$scope.param.stateTime = null;
				$scope.param.endTime = null;
				$scope.param.grantStatus = null;
				var size = $("#deptCode").find("option").size();
				if(size == 2) {
					$("#deptCode").get(0).selectedIndex = 1;
				} else if(size > 2) { //多个所属机构才清空选择
					$("#deptCode").val("");
					$scope.param.deptCode = null;
				}
			}
			
			/**
			 * 判断是否需要重置分页
			 */
			function isResetFirstPage(clean){
				var isW = false;
				if($scope.param.keyword != null){
					isW = true;
				}
				if($scope.param.schemeNum != null){
					isW = true;
				}
				if($scope.param.businessName != null){
					isW = true;
				}
				if($scope.param.applicant != null){
					isW = true;
				}
				if($scope.param.stateTime != null){
					isW = true;
				}
				if($scope.param.endTime != null){
					isW = true;
				}
				if($scope.param.grantStatus != null){
					isW = true;
				}
				var size = $("#deptCode").find("option").size();
				var deptCode = $("#deptCode").val();
				//只要存在大于1个部门，并且对部门作为条件进行了搜索，那么需要重置分页
				if(size>2 && deptCode != ''){
					isW = true;
				}
				if(!$scope.pageInfo){
					return false;
				}else{
					var top = $scope.pageInfo.total;
					//如果符合条件的数据小于10，那么重置当前页1
					if(top<=10){
						$scope.param.pageNum = 1;
					}else if(isW && clean ==0){
						//查询条件存在，且是通过查询按钮进行提交，则重置当前页1
						$scope.param.pageNum = 1;
					}
				}
			}
			
			$scope.openPopDet = function() {
				var item = getCheckItem();
				if(item == null) {
					alert("请先选则一个发放");
				}
				$scope.openPop('B', '', item.grantCode, '');
			}
			/**
			 * 废弃
			 */
			$scope.disabledGrant = function(id) {
				$state.go('main.biz.grant.discard', {
					id: id,
				});
			}

			/**
			 * 删除发放
			 */
			$scope.delGrant = function(id) {
				confirm("提示", "您确认要删除吗?",
					function(flag) {
						if(!flag) {
							return false;
						}
						$scope.loading = true;
						$.ajax({
							type: 'POST',
							url: '/eximbank-club/grant/del',
							data: angular.toJson({
								'grantId': id
							})
						}).then(function(result) {
							if(result) {
								if(result.httpCode == 200) {
									alert("删除成功!");
									$scope.search(0);
								} else {
									$scope.msg = result.msg;
								}
							} else {
								$scope.msg = "服务器异常，请刷新重试或联系管理员";
							}
							$scope.$apply();
						});
					}
				)
			}

			//发放变更
			$scope.editGrant = function(id, code) {
				if(id == null) {
					alert("请选择一个项目!");
					return false;
				}
				var gURL = "main.biz.grant.changeEdit";
				//判断是否有暂存
				alertTempMsg(id, code, gURL, 5);
			}

			// 驳回后编辑
			$scope.editNew = function(id, code) {
				if(id == null || code == null) {
					alert("请选择一个项目!");
					return false;
				}
				var gURL = "main.biz.grant.repeatEdit";
				//判断是否有暂存
				alertTempMsg(id, code, gURL, 10);
			}

			/**
			 * 功能：提醒暂存信息
			 * @param {Object} grantCode
			 */
			function alertTempMsg(id, grantCode, gURL, status) {
				//其他流程通过发放编码精确匹配
				var xURL = "/eximbank-club/temp/getTempCount";
				var count = checkTempCount(xURL,grantCode);
				//如果是变更流程，则除了精确匹配外，还需模糊匹配属于同一个方案的发放的暂存
				if(status == 5) {
					xURL = "/eximbank-club/temp/getChangeTempCount";
					count = checkTempCount(xURL,grantCode);
				}
				if(count > 0) {
					var msg = "该条信息已有暂存记录，如需对暂存继续编辑，请进入暂存查询交易进行操作。取消，取消操作；确定，可能清除或更新暂存记录！";
					confirm("提示", msg,
						function(flag) {
							if(flag) {
								//进入编辑
								$state.go(gURL, {
									id: id
								});
							}
						})
				} else {
					//不提醒直接进入编辑
					$state.go(gURL, {
						id: id
					});
				}
			}
			/**
			 * 功能：查询有没有暂存记录
			 * @param {Object} xURL
			 */
			function checkTempCount(xURL,grantCode) {
				var count = 0;
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					async: false,
					url: xURL,
					data: angular.toJson({
						"grantCode": grantCode
					})
				}).then(function(result) {
					if(result) {
						if(result.httpCode == 200) {
							count = result.data;
						} else {
							$scope.msg = result.msg;
						}
					} else {
						$scope.msg = "服务器异常，请刷新重试或联系管理员";
					}
					$scope.loading = false;
				});
				return count;
			}

			// 单个选择
			$scope.selectOne = function(m, n) {
				angular.forEach($scope.pageInfo.data, function(v, k) {
					if(n == v.id) {
						v.checked = m;
					} else {
						// 如果选中一个,其余的都自动设置未选中
						if(m == true) {
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
				angular.forEach($scope.pageInfo.data, function(v, k) {
					if(v.checked == true) {
						item = v;
					}
				});
				return item;
			}

			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search(1);
			};

		}

	]);

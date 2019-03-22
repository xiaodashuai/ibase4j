angular.module('app').controller(
	'tempController', ['$rootScope', '$scope', '$state',
		function($rootScope, $scope, $state) {
			$scope.title = '暂存列表';
			$scope.param = {};
			$scope.loading = false;

			$scope.search = function(clean) {
				var dt = new DateUtil();
				var st_dt = $scope.param.stateTime;
				var end_dt = $scope.param.endTime;
				if(dt.compareDate(end_dt, st_dt) == 1) {
					var msg = "开始日期必须在结束日期(" + end_dt + ")之前!";
					alert(msg);
					return false;
				}
				//重置分页 
				isResetFirstPage(clean);
				//部门编码赋值给angluarjs
				$scope.param.deptCode = $("#deptCode").val();
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/temp/queryTemp',
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
					$("#deptCode").prop("disabled", true);
				}else if(size > 2){
					$("#deptCode").removeProp("disabled");
				}
				//查询所有币种
				$.when(getCurrencyDb()).done(function(result){
					$scope.currencyList = result;
				}).fail(function(){
					alert("查询失败!");
				});
			}
			//初始化机构下拉框
			initDepartment();
			//执行查询
			$scope.search(0);
			/**
			 * 清除查询条件
			 */ 
			$scope.clearSearch = function(fm){
				//清空 angluarjs的参数
				$scope.param.schemeNum = null;
				$scope.param.projectNam = null;
				$scope.param.remark_ = null;
				$scope.param.stateTime = null;
				$scope.param.endTime = null; 
				var size = $("#deptCode").find("option").size();
				if(size == 2){
					$("#deptCode").get(0).selectedIndex=1; 
				}else if(size>2){//多个所属机构才清空选择 
					$("#deptCode").val("");
					$scope.param.deptCode = null;
				}
			}
			/**
			 * 判断是否需要重置分页
			 */
			function isResetFirstPage(clean){
				var isW = false;
				if($scope.param.schemeNum != null){
					isW = true;
				}
				if($scope.param.projectNam != null){
					isW = true;
				}
				if($scope.param.remark_ != null){
					isW = true;
				}
				if($scope.param.stateTime != null){
					isW = true;
				}
				if($scope.param.endTime != null){
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

			/**
			 * 分割字符串，转换成数组
			 * @param {Object} strs
			 */
			$scope.subFirst=function(strs) {
				var a =[];
				if(strs == null || strs == '') {
					return a;
				}
				var strArray = strs.split("#");
				$.each(strArray, function(i, v) {
					var data = $.trim(v); //$.trim()函数来自jQuery  
					if('' != data) {
						a.push(data);
					}
				});
				return a[0];
			}
			/**
			 * 转换币种获取中文描述
			 * @param {Object} strs
			 */
			$scope.cvtCurrency=function(cny) {
				if(cny == null || cny == '') {
					return "";
				}
				var list = $scope.currencyList;
				var cnyName = getCurrencyNameByCode(cny,list);
				return cnyName;
			}
			
			/**
			 * 删除暂存
			 */
			$scope.deleteItem = function(item) {
				if(item == null) {
					alert("请选择一个项目!");
					return false;
				}
				//登录用户的角色
				var role = $rootScope.roleCodes;
				var createId = $rootScope.userInfo.Id_;
				var tempCreateId = item.userid;//暂存人员id 
				if(role.indexOf('1001')==-1&&role.indexOf('1020')==-1&&tempCreateId!=createId){
					console.log("--------不是业务发起处室经办，没有删除权限-------------");
					alert("您没有删除权限!");
					return false;
				}
				confirm("提示","删除后不能恢复，您确认要执行删除吗？",
					function(flag){
						if(flag==false){
							return false;
						}
						$scope.loading = true;
						$.ajax({
							type: 'PUT',
							url: '/eximbank-club/temp/delTemp',
							data: angular.toJson({
								'id': item.id
							})
						}).then(function(result) {
							$scope.loading = false;
							if(result){
								if(result.httpCode == 200) {
									alert("删除成功!");
									$scope.search(0);
								} else {
									$scope.msg = result.msg;
								}
							}else{
								$scope.msg = "服务器异常，请刷新重试或联系管理员";
							}
							$scope.$apply();
						});
					}
				)
			}

			// 编辑账号
			$scope.edit = function(item) {
				if(item == null) {
					alert("请选择一个项目!");
					return false;
				}
				//其他类型的暂存
				var editURL = "main.biz.grant.edit";
				var method = item.taskid;
				//如果是变更的暂存，则跳转到变更暂存页面
				if("C"==method){
					editURL = "main.biz.grant.editChangeTemp";
				}
				$state.go(editURL, {
					id: item.id,
					code: item.bizcode
				});
			}
			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search(1);
			};

		}

	]);
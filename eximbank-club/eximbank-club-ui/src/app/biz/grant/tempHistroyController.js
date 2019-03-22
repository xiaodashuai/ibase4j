angular.module('app').controller(
	'tempHistroyController', ['$rootScope', '$scope', '$state',
		function($rootScope, $scope, $state) {
			$scope.title = '发放数据迁移暂存列表';
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
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/temp/queryHistory',
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
				var jsonResult = getCurrencyDb();
				$scope.currencyList = jsonResult;
			}
			//初始化机构下拉框
			initDepartment();
			
			$scope.search();

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
			$scope.deleteItem = function(item){
				if(item == null) {
					alert("请选择一个项目!");
					return false;
				}
				if(item.taskid=='R'){
					alert("被驳回的不可删除!");
					return false;
				}
				confirm("提示","删除后不能恢复，您确认要执行删除吗？",
					function(flag){
						if(!flag){
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
							if(result){
								if(result.httpCode == 200) {
									alert("删除成功!");
									$scope.search();
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

			// 编辑发放
			$scope.edit = function(item) {
				if(item == null) {
					alert("请选择一个项目!");
					return false;
				}
				$state.go('main.biz.grant.edit', {
					id: item.id,
					code: item.bizcode
				});
			}
			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search();
			};

		}

	]);
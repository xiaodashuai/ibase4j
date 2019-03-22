angular.module('app').controller(
	'programmeController', ['$rootScope', '$scope', '$http', '$state','$timeout','WebUploadService',
		function($rootScope, $scope, $http, $state,$timeout,WebUploadService) {
			$scope.title = '发放管理';
			$scope.param = {};
			$scope.loading = false;
			
			$scope.search = function(clean) {
				var dt = new DateUtil();
				var stDt = $scope.param.startDate;
				var endDt = $scope.param.endDate;
				if(dt.compareDate(endDt, stDt) == 1) {
					var msg = "开始日期必须在结束日期(" + endDt + ")之前!";
					alert(msg);
					return false;
				}
				isResetFirstPage(clean);
				$scope.loading = true;
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/grant/read/list',
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

			$scope.search(0);
			
			$scope.webUploadStatus = 0;
			/**
			 * 功能：预览附件
			 * @param {Object} code
			 * @param {Object} index
			 */
			$scope.openWindow = function(code, index) {
				var code = code.substring(0, 13);
				if($scope.webUploadStatus == 0) { //第一次点击任意一笔
					WebUploadService.initFiles([code, code], ["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout,true);
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
						WebUploadService.initFiles([code, code],["A", "A"], ["租金保理", "租金保理"], ["Textupload", "Attachupload"], $scope, $timeout,true);
						$scope.idd = index;
					}
				}
			}
			
			/**
			 * 清除查询条件
			 */
			$scope.clearSearch = function(fm){
				//清空 angluarjs的参数
				$scope.param.keyword = null;
				$scope.param.projectNam = null;
				$scope.param.applicant = null;
				$scope.param.proposer = null;
				$scope.param.startDate = null;
				$scope.param.endDate = null;
			}
			/**
			 * 判断是否需要重置分页
			 */
			function isResetFirstPage(clean){
				var isW = false;
				if($scope.param.keyword != null){
					isW = true;
				}
				if($scope.param.projectNam != null){
					isW = true;
				}
				if($scope.param.applicant != null){
					isW = true;
				}
				if($scope.param.proposer != null){
					isW = true;
				}
				if($scope.param.startDate != null){
					isW = true;
				}
				if($scope.param.endDate != null){
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
			 * 新增一个产品的发放申请
			 */
			$scope.addItem = function() {
				var bean = $scope.ckNode;
				if(bean == undefined) {
					$("#warnmsg").text("请选择一个产品进行发放!");
					return false;
				} else {
					if(!checkOverRange()) {
						return false;
					}
					//关闭弹出框
					$("#closeProductWin").click();
					//转请求
					setTimeout(function() {
						$state.go('main.biz.grant.add', {
							proIds: 0, 
							businessCode: bean.businessType,
							debtNum: bean.debtCode
						});
					}, 400);

				}
			}
			
			/**
			 * 验证日期或笔数是否超限
			 */
			function checkOverRange() {
				//1.首先检查日期
				var dt = new DateUtil();
				var ckBean = $scope.bean;
				var amt = ckBean.amt; //可发放金额
				var usedNum = ckBean.usedNum; //已发放笔数 
				var ruleType = ckBean.ruleType; //产品规则类型(1:全局产品规则、2:单一产品规则)
				var efeeDate = dt.formatDateStr(ckBean.efeectiveDate,'yy-mm-dd');; //生效日期
				var expiDate = dt.formatDateStr(ckBean.expiDate,'yy-mm-dd');; //失效日期

				var maxPck = ckBean.numLimit; //产品可办理笔数限制(1不限制、2限制)
				var maxPnum = ckBean.dealNum; //产品可办理笔数

				var maxFck = ckBean.ltnopa; //方案可办理笔数限制(1不限制、2限制)
				var maxFNum = ckBean.tdwln; //方案可办理笔数

				var now = dt.nowTime('yy-mm-dd');
				//判断金额是否满足发放
				if(amt) {
					if(!amt > 0) {
						alert("方案金额(" + amt + ")不满足发放条件!");
						return false;
					}
				}
				//验证日期
				if(dt.compareDate(now, efeeDate) == 1) {
					alert("还未到方案生效时间(" + efeeDate + ")!");
					return false;
				} else if(dt.compareDate(expiDate, now) == 1) {
					alert("方案已超过失效时间(" + expiDate + ")!");
					return false;
				}
				if(parseInt(maxFNum)==0){
					alert("方案限制笔数是0,不能进行发放!");
					return false;
				}
				//TODO 第一期只使用与单笔单批
				if(usedNum && parseInt(usedNum) > 0) {
					alert("只能发放一笔!");
					return false;
				}
				//2.检查笔数限制 
				if(ruleType == 1) { //全局规则 
					if(maxFck == 2) { //限制笔数 
						var resultNum = parseInt(maxFNum) - parseInt(usedNum);
						if(resultNum < 1) {
							alert("已经超出限制笔数:" + maxFNum);
							return false;
						}
					}
				} else { //全局+单
					if(maxFck == 2) {
						//全局限制笔数
						var resultNum = parseInt(maxFNum) - parseInt(usedNum);
						//判断
						var resultNum2 = parseInt(maxPnum) - parseInt(usedNum);
						//成功
						if(resultNum > 0) {
							return true;
						} else if(resultNum < 1) {
							alert("已经超出全局限制笔数:" + maxFNum);
							return false;
						} else if(resultNum2 < 1) {
							alert("已经超出单一产品限制笔数:" + maxPnum);
							return false;
						}
					} else if(maxPck == 2) { //产品限制

					}
				}
				return true;
			}

			/**
			 * 通过产品编号和方案编码获取有效期
			 * @param {Object} item
			 */
			$scope.getCheckRule = function(item) {
				$.ajax({
					type: 'PUT',
					url: '/eximbank-club/grant/check',
					data: angular.toJson({
						'properInfo': 0,
						'debtCode': item.debtCode
					})
				}).then(function(result) {
					if(result.httpCode == 200) {
						$scope.bean = result.data;
					} else {
						$scope.msg = result.msg;
					}
					$scope.$apply();
				});
			}

			// 单选择一个产品
			$scope.checkOne = function(m, n) {
				angular.forEach($scope.List, function(v, k) {
					if(n == v.businessType) {
						v.checked = m;
						$("#warnmsg").text("");
						//选中的记录
						$scope.ckNode = v;
						$scope.getCheckRule(v);
					} else {
						// 如果选中一个,其余的都自动设置未选中
						if(m == true) {
							v.checked = false;
						}
					}
				})
			}

			/**
			 * 获取方案中的产品列表
			 * @param {Object} btn 点击的发放申请按钮id 
			 * @param {Object} debtCode 方案编号
			 */
			$scope.openList = function(btn,item) {
				if(item == undefined) {
					alert("请选择一个方案!");
					return false;
				}else if(!checkGuarteeStatus(item)){
					alert("由于方案合同创建没成功，不允许发放!");
					return false;
				} else {
					//查询方案下的产品，并在弹出窗口内容显示
					$.ajax({
						type: 'PUT',
						url: '/eximbank-club/grant/read/getProductList',
						data: angular.toJson({
							'debtCode': item.debtCode
						})
					}).then(function(result) {
						if(result) {
							if(result.httpCode == 200) {
								$scope.List = result.data;
								//打开窗口显示内容
								$("#" + btn).click();
							} else {
								$scope.msg = result.msg;
							}
						} else {
							$scope.msg = "服务器异常，请刷新重试或联系管理员";
						}
						$scope.$apply();
					});
				}
			}
			/**
			 * 验证合同状态是否创建成功，不成功不能发放
			 */
			function checkGuarteeStatus(item){
				var transOk = item.transok;
				//64007接口返回0表示合同创建成功
				if(transOk=='0'){
					return true;
				}
				return false;
			}
			
			// 翻页
			$scope.pagination = function(page) {
				$scope.param.pageNum = page;
				$scope.search(1);
			};

		}

	]);
	

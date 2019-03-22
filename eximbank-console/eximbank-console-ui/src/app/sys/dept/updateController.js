'use strict';
var appModule = angular.module('app');
appModule.controller('deptUpdateController', ['$scope', '$rootScope', '$state', '$timeout', 'toaster', '$stateParams',
	function ($scope, $rootScope, $state, $timeout, toaster, $stateParams) {
		var title = "";
		if ($state.includes('**.dept.update')) {
			title = "编辑机构";
			var id = $stateParams.id;
			var parentCode = $stateParams.parentCode;
			activate(id);
			//级别回显 选中
			var  deptLevel=$scope.record.deptLevel;
			// console.log("deptLevel:"+deptLevel);
			$("#deptLevel").find("option[value='" + deptLevel + "']").prop("selected", true);
			//状态 是否 禁用 回显
			var  enable=$scope.record.enable;
			$("#enable").find("input[value='" + enable + "']").prop("checked", true);

			//省市县数据  根据返回值 选中 下拉数据
			var  province = $scope.record.province;
			var  city = $scope.record.city;
			var  district = $scope.record.district;

			console.log(province+" "+city+" "+district);
			//先获取省级数据 然后选中省
			if(province!=null&&province!=""){
				//选中省
				setSelectProvince("province", "LOC0101003","013",province);
				setProvinceOnchange();

			}else{
				loadinitProvince()
			}



			//组织机构初始化
			validate();
			// getOrgSelectBox(parentCode);
		} else if ($state.includes('**.dept.create')) {
			title = "新增机构";
			//组织机构初始化
			// getOrgSelectBox(null);
			loadinitProvince();
			validate();
		}
		$scope.title = $rootScope.title = title;
		$scope.loading = true;
		//提交保存
		$scope.submit = function () {
			var m = $scope.record;
			if (m) {
				$scope.isDisabled = true; //提交disabled
				$.ajax({
					url: '/eximbank-console/dept',
					type: 'POST',
					data: angular.toJson($scope.record)
				}).then(callback)
			}

			function callback(result) {
				if (result) {
					if (result.httpCode == 200) { //成功
						toaster.clear('*');
						toaster.pop('success', '', "保存成功");
						$timeout(function () {
							$state.go('main.sys.dept.list');
						}, 2000);
					} else {
						toaster.clear('*');
						toaster.pop('error', '', "有点小故障,请刷新重试");
						$scope.isDisabled = false;
					}
				}

			}
		}
		// 初始化页面
		function activate(id) {
			$scope.loading = true;

			$.ajax({
				type: 'POST',
				async:false,
				url: '/eximbank-console/dept/read/detail',
				data: angular.toJson({
					'id': id
				})
			}).then(function (result) {
				$scope.loading = false;
				if (result.httpCode == 200) {
					$scope.record = result.data;

				} else {
					$scope.msg = result.msg;
				}
				$scope.$applyAsync();
			})
		}
		//表单验证
		function validate() {
			jQuery('form').validate({
				rules: {},
				messages: {},
				submitHandler: function () {
					$scope.submit();
				}
			});
		}

		//selectid:select标签的id，code数据库省级县级的code
		function setSelectProvince(selectid, code,typeCode,name) {
			var nowTime=new Date();
			if(code!=null&& code!=""){
				$.ajax({
					type: "PUT",
					async: true,
					cache: false, //设置数据不缓存
					url: "/eximbank-console/components/getDicType",
					data: angular.toJson({
						parentCode: code,
						type: typeCode,
						time: nowTime
					}),
					success: function(json) {
						var result = json.data;
						//注意!!!这里必须使用eval(res)函数，否则获取到的json数据无法遍历,无话获取到数据
						var arr = eval(result);
						//遍历返回的json数据加载到select标签;
						$.each(arr, function(key, val) {
							$("#" + selectid).append(" <option class=\"form-control\" id='" + val.code + "'>"+ myUpdateTrim(val.name) + "</option>");
							// console.log(key+" "+$(this).text());
							// console.log(key+" "+val.name);
							if(name!=null&&name!=""){
								if(myUpdateTrim(val.name)==name){
									$($("#" +  val.code)).attr("selected",true);
									//选中省级后 查下级接着选中
									if(city!=null&&city!=""){
										setSelectCity("city",val.code,typeCode,city);
									}
								}
							}

						});
					},
					error: function(e) {
						console.log(e);
					}
				});
			}
		};

		//selectid:select标签的id，code数据库省级县级的code
		function setSelectCity(selectid, code,typeCode,name) {
			var nowTime=new Date();
			if(code!=null&& code!=""){
				$.ajax({
					type: "PUT",
					async: true,
					cache: false, //设置数据不缓存
					url: "/eximbank-console/components/getDicType",
					data: angular.toJson({
						parentCode: code,
						type: typeCode,
						time: nowTime
					}),
					success: function(json) {
						var result = json.data;
						//注意!!!这里必须使用eval(res)函数，否则获取到的json数据无法遍历,无话获取到数据
						var arr = eval(result);
						//遍历返回的json数据加载到select标签;
						$.each(arr, function(key, val) {
							$("#" + selectid).append(" <option class=\"form-control\" id='" + val.code + "'>"+ myUpdateTrim(val.name) + "</option>");
							// console.log(key+" "+$(this).text());
							// console.log(key+" "+val.name);
							if(name!=null&&name!=""){
								if(myUpdateTrim(val.name)==name){
									$($("#" +  val.code)).attr("selected",true);
									//选中省级后 查下级接着选中
									if(district!=null&&district!=""){
										setSelectDistrict("district",val.code,typeCode,district);
									}
								}
							}

						});
					},
					error: function(e) {
						console.log(e);
					}
				});
			}
		};
//selectid:select标签的id，code数据库省级县级的code
		function setSelectDistrict(selectid, code,typeCode,name) {
			var nowTime=new Date();
			if(code!=null&& code!=""){
				$.ajax({
					type: "PUT",
					async: true,
					cache: false, //设置数据不缓存
					url: "/eximbank-console/components/getDicType",
					data: angular.toJson({
						parentCode: code,
						type: typeCode,
						time: nowTime
					}),
					success: function(json) {
						var result = json.data;
						//注意!!!这里必须使用eval(res)函数，否则获取到的json数据无法遍历,无话获取到数据
						var arr = eval(result);
						//遍历返回的json数据加载到select标签;
						$.each(arr, function(key, val) {
							$("#" + selectid).append(" <option class=\"form-control\" id='" + val.code + "'>"+ myUpdateTrim(val.name) + "</option>");
							// console.log(key+" "+$(this).text());
							// console.log(key+" "+val.name);
							if(name!=null&&name!=""){
								if(myUpdateTrim(val.name)==name){
									$($("#" +  val.code)).attr("selected",true);
									//选中省级后 查下级接着选中

								}
							}

						});
					},
					error: function(e) {
						console.log(e);
					}
				});
			}
		};


		function myUpdateTrim(x) {
			return x.replace(/^\s+|\s+$/gm,'');
		}



		function loadinitProvince(){
			// setCity("top", "0");//页面加载时就现实数据库第一个数据，一定要加上
			// $("#province option").remove();
			// $("#city option").remove();
			// $("#district option").remove();
			setCity("province", "LOC0101003","013");
			setProvinceOnchange();
		};
		function setProvinceOnchange(){
			$("#province").change(function() {
				// 当省级改变的时候，让市级和县级文本清空
				$("#city option").remove();
				$("#district option").remove();
				//获得省级的id
				var parentCode=$("#province option:selected").attr("id");
				//加载该省级的市级数据
				$("#city").append(" <option class=\"form-control\" selected>请选择市</option>");
				$("#district").append(" <option class=\"form-control\" selected>请选择县/区</option>");
				setCity("city", parentCode);
			});
			$("#city").change(function() {
				//当市级改变的时候，让县级文本清空
				$("#district option").remove();
				//获取城市的parentCode
				var parentCode=$("#city option:selected").attr("id");
				//加载该省市级的县级数据
				$("#district").append(" <option class=\"form-control\" selected>请选择县/区</option>");
				setCity("district",parentCode );
			});
		}
		//selectid:select标签的id，code数据库省级县级的code
		function setCity(selectid, code,typeCode) {
			var nowTime=new Date();
			if(code!=null&& code!=""){
				$.ajax({
					type: "PUT",
					async: true,
					cache: false, //设置数据不缓存
					url: "/eximbank-console/components/getDicType",
					data: angular.toJson({
						parentCode: code,
						type: typeCode,
						time: nowTime
					}),
					success: function(json) {
						var result = json.data;
						//注意!!!这里必须使用eval(res)函数，否则获取到的json数据无法遍历,无话获取到数据
						var arr = eval(result);
						//遍历返回的json数据加载到select标签;
						$.each(arr, function(key, val) {
							$("#" + selectid).append(" <option class=\"form-control\" id='" + val.code + "'>"+ myTrim(val.name) + "</option>");
						});
					},
					error: function(e) {
						console.log(e);
					}
				});
			}
		};


		function myTrim(x) {
			return x.replace(/^\s+|\s+$/gm,'');
		}
	}
]);
// 菜单树
appModule.directive('tree', function () {
	return {
		require: '?ngModel',
		restrict: 'A',
		link: function ($scope, element, attrs, ngModel) {
			// var opts = angular.extend({},
			// $scope.$eval(attrs.nlUploadify));
			// 初始化树
			showDept($scope, element, ngModel, 1);
		}
	};
});
/**
 * 功能：查看菜单树
 * @param {Object} $scope
 * @param {Object} element
 * @param {Object} ngModel
 * @param {Object} modelType 2:带编辑功能；1:单选框树；3:复选框树 
 */
function showTree($scope, element, ngModel, modelType, paramType) {
	//默认业务菜单
	var role_type = '1';
	if (paramType && paramType == 2) {
		role_type = '2';
	}
	$scope.loading = true;
	$.ajax({
		type: 'GET',
		url: '/eximbank-console/menu/read/getAllMenu',
		data: {
			roleType: role_type
		} //默认查询系统类别的权限
	}).then(function (result) {
		$scope.loading = false;
		if (result != null) {
			if (result.httpCode == 200) {
				if (modelType == 2) {
					editTree($scope, element, result.data, ngModel);
				} else {
					viewTree($scope, element, result.data);
				}
			} else {
				$scope.msg = result.msg;
			}
		}
	});
}

/**
 * 功能：构造（配置）树-没有树编辑功能
 * 
 * @param element
 * @param treeData
 */
function viewTree($scope, element, treeData) {
	// 配置
	var setting = {
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: {
				"Y": "s",
				"N": "ps"
			}
		},
		view: {
			showIcon: false,
			selectedMulti: true, //可以多选
			showLine: false,
			expandSpeed: 'fast',
			dblClickExpand: false
		},
		data: {
			key: {
				name: "name"
			},
			simpleData: {
				idKey: "id",
				pIdKey: "pId",
				enable: true
			}
		},
		callback: {
			onCheck: function (e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("tree");
				var nodes = zTree.getCheckedNodes(true);
				var v = "";
				var n = "";
				for (var i = 0, l = nodes.length; i < l; i++) {
					n += nodes[i].name + ",";
					v += nodes[i].id + ",";
				}
				if (v.length > 0) {
					v = v.substring(0, v.length - 1);
					n = n.substring(0, n.length - 1);
				}
				console.log(v);
				$("#menus").val(v);
				$scope.$apply(function () {
					$scope.record.menus = v;
					$scope.roleMsg = "";
				});
			},
			onClick: function (e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("tree");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				return false;
			}
		}
	};
	var zNodes = sortTreeNode(treeData);
	// 加载树
	$.fn.zTree.init(element, setting, zNodes);
}

function showMenuTo() {
	$("#tree").css({
		left: "100px",
		top: "10px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
	$("#tree").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "deptTree" || $(event.target).parents("#deptTree").length > 0)) {
		hideMenu();
	}
}

/**
 * 转化普通json到zTreeNode的数据结构
 * 
 * @param jsonData
 * @returns {String}
 */
function sortTreeNode(jsonData) {
	var nodes = [];
	//
	$.each(jsonData, function (i) {
		var id = jsonData[i].nId;
		var pid = jsonData[i].pId;
		var name = jsonData[i].name;
		var open = jsonData[i].open;
		var nocheck = jsonData[i].nocheck;
		var nodeStr = getZNode(id, pid, name, open, nocheck);
		nodes.push(nodeStr);
	});
	return nodes;
}

/**
 * 获取机构树
 */
function getZNode(n_id, n_pid, n_name, n_open, n_nocheck) {
	return {
		id: String(n_id),
		pId: String(n_pid),
		name: n_name,
		open: true,
		nocheck: false
	};
}
/**
 * 加载zTree
 */

/**
 * 功能：显示所属部门
 * @param deptId 部门id 
 * @param element 
 * @param modelType 2:带编辑功能；1:单选框树；3:复选框树 
 */
function showDept($scope, element, ngModel, modelType) {
	$scope.loading = true;
	$.ajax({
		type: 'POST',
		url: '/eximbank-console/dept/read/getAllDept'
	}).then(function (result) {
		$scope.loading = false;
		if (result != null) {
			if (result.httpCode == 200) {
				if (modelType == 2) {
					editTree($scope, element, result.data, ngModel);
				} else {
					loadTree($scope, element, result.data);
				}
			} else {
				$scope.msg = result.msg;
			}
		}
	})

}

/**
 * 功能：构造（配置）树-具有编辑功能
 * 
 * @param element
 * @param treeData
 */
function editTree($scope, element, treeData, ngModel) {
	// 配置
	var setting = {
		view: {
			addHoverDom: function (treeId, treeNode) {
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
				var addStr = "<span class='button add' id='addBtn_" + treeNode.tId +
					"' title='添加新部门' onfocus='this.blur();'></span>";
				sObj.after(addStr);

				var btn = $("#addBtn_" + treeNode.tId);
				if (btn) btn.bind("click", function () {
					$("#ex").show();
					/*zTree = $.fn.zTree.getZTreeObj("tree");
					 zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});*/
					return false;
				});
			},
			removeHoverDom: removeHoverDom,
			selectedMulti: false,
			dblClickExpand: false
		},
		check: {
			enable: false
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
		edit: {
			enable: true
		},
		callback: {
			onClick: function (event, treeId, treeNode, clickFlag) {
				$scope.$apply(function () {
					ngModel.$setViewValue(treeNode);
				});
			}
		}
	};
	var zNodes = sortTreeNode(treeData);
	// 加载树
	$.fn.zTree.init(element, setting, zNodes);
}

//树型的添加功能
/*function addHoverDom(treeId,treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='添加新部门' onfocus='this.blur();'></span>";
    sObj.after(addStr);

    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function($scope){
    	$scope.showDeptWindow=true;
        var id=treeNode.id;
        var pId=treeNode.pId;
        var name=treeNode.name;
        /!*$('#ex').append(a);*!/
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
         return false;
    });
};*/

// 删除树节点
function removeHoverDom(treeId, treeNode) {
	/*var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.unbind("click", function() {
	    var id = treeNode.id;
	    $.ajax({
	        url: '/dept/delete/deleteDept',
	        type: 'POST',
	        data: angular.toJson({'id': id})
	        /!*$("#addBtn_"+treeNode.tId).unbind().remove();*!/
	    })
	})*/
};

/**
 * 功能：构造（配置）树-没有树编辑功能
 * 
 * @param element
 * @param treeData
 */
function loadTree($scope, element, treeData) {
	// 配置
	var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		view: {
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
				$("#department").val(n);
				$("#deptId").val(v);
				console.log(v);
				$scope.$apply(function () {
					$scope.record.deptCode = v;
				});
				//选中后隐藏树
				hideMenu();
			},
			onClick: function (e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("tree");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				var nodes = zTree.getCheckedNodes(true);
				var deptId = nodes[0].id;
				//加载子树
				getDeptData(deptId, zTree, treeNode);
				return false;
			}
		}
	};
	var zNodes = sortTreeNode(treeData);
	// 加载树
	$.fn.zTree.init(element, setting, zNodes);
	//setCheckedNode
	if ($scope.record) {
		setCheckedNode($scope.record.deptCode);
	}
}

/**
 * 查询子树
 * @param {Object} pId
 * @param {Object} zTree
 */
function getDeptData(pId, zTree, currentNode) {
	//TODO 
	var treeJson = "";
	rebulidTree(treeJson, zTree);
}
/**
 * 显示子树 
 * @param {Object} treeJson
 * @param {Object} zTree
 */
function rebulidTree(treeJson, zTree, currentNode) {
	var newNodes = treeJson;
	var treeObj = $.fn.zTree.getZTreeObj("treeDiv");
	zTree.addNodes(currentNode, newNodes);
}
/**
 * 编辑回显机构树选中的状态
 */
function setCheckedNode(checkDeptCode) {
	var zTree = $.fn.zTree.getZTreeObj("tree");
	zTree.expandAll(true);
	var node = zTree.getNodeByParam("id", checkDeptCode);
	//如果是修改
	if (node) {
		var nodeName = node.name;
		zTree.selectNode(node);
		zTree.checkNode(node, true, true);
		$("#department").val(nodeName);
	}
}

function showMenu() {
	var cityObj = $("#department");
	var cityOffset = $("#department").offset();
	$("#tree").css({
		left: cityOffset.left + "px",
		top: cityOffset.top + cityObj.outerHeight() + "px"
	}).slideDown("fast");
	//加载点击事件
	$("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
	$("#tree").fadeOut("fast");
	$("#tree").prev().text("");
	$("body").unbind("mousedown", onBodyDown);
}
/**
 * 加上界面点击事件.点击页面下拉框消失
 * @param {Object} event
 */
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
		id: Number(n_id),
		pId: Number(n_pid),
		name: n_name,
		open: false,
		nocheck: false
	};
}
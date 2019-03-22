/**
 * 系统共用工具类
 */


function bulidRoleCheckBox(json) {
	var ckboxs = [];
	//
	$.each(json, function(i) {
		var id = json[i].roleId;
		var name = json[i].roleName;
		var nodeStr = getCheckbox(id,name,false);
		ckboxs.push(nodeStr);
	});
	return ckboxs;
}

/**
 * 获取多选框
 */
function getCheckbox(k_id,k_check) {
	return {id:Number(id),nocheck:k_check};
}
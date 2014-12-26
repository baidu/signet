var projectId;
$(document).ready(function() {
	projectId = baidu.url.getQueryValue(window.location.href, 'projectId');
	initConfig(projectId);
	initUser();
	initRoles(projectId);
	baidu.g('saveBtn').onclick = function() {
		var mode;
		if (baidu.g('single').checked) {
			mode = '0';
		} else {
			mode = '1';
		}
		var bugPath = baidu.g('bugPath').value;
		request.dao.saveProperty(projectId, {
			'roleMode' : mode,
			'bugPath' : bugPath
		}, function(data) {
			alert('保存完成');
		});
	};
	baidu.g('saveSignetBtn').onclick = function() {
		var name = baidu.g('signetName').value;
		var type = baidu.g('signetType').value;
		request.dao.saveRole(projectId, {
			'name' : name,
			'roleSignType' : type
		}, function(data) {
			if (data.result) {
				alert('保存完成');
				initRoles(projectId);
			} else {
				alert('保存失败');
			}
		});
	};
	baidu.g('base_tab').onclick = function() {
		baidu.g('signet_tab').className = 'mala-flow-item mala-fl';
		baidu.g('base_tab').className = 'mala-flow-item active mala-fl';
		baidu.g('signet_content').style.display = 'none';
		baidu.g('base_content').style.display = '';
	};

	baidu.g('signet_tab').onclick = function() {
		baidu.g('base_tab').className = 'mala-flow-item mala-fl';
		baidu.g('signet_tab').className = 'mala-flow-item active mala-fl';
		baidu.g('base_content').style.display = 'none';
		baidu.g('signet_content').style.display = '';
	};
});
function initRoles(projectId) {
	request.dao
			.listRoles(
					projectId,
					function(data) {
						var html = '<table>';
						for ( var i = 0; i < data.length; i++) {
							if ((i % 4) == 0) {
								html = html + '<tr>';
							}
							html = html + '<td onclick="delete_role('
									+ data[i].roleId
									+ ')" style="width:220px;height: 50px;">';
							html = html
									+ '<span><img style="width:36px;vertical-align: middle;" src="../resources/signs/'
									+ projectId
									+ '/'
									+ data[i].roleTag
									+ '.jpg"></img><span style="font-size:21px;vertical-align: middle;padding-left: 10px;cursor:pointer;">'
									+ data[i].name + '</span></span>';
							html = html + '</td>';
							if ((i % 4) == 3) {
								html = html + '</tr>';
							}
						}
						baidu.g('roles').innerHTML = html;
					});
}
function delete_role(roleId) {
	if (confirm("是否删除签章?删除将不可恢复")) {
		request.dao.deleteRole(projectId, roleId, function(data) {
			alert("已完成删除");
			initRoles(projectId);
		});
	}
}
function initUser() {
	request.dao.getUser(function(data) {
		baidu.g('login_user').innerHTML = data.user;
	});
}
function initConfig(projectId) {
	request.dao.queryProperty(projectId, function(data) {
		var roleMode = data.muli_signet;
		if (roleMode == 1) {
			baidu.g('single').checked = '';
			baidu.g('mult').checked = 'true';
		} else {
			baidu.g('single').checked = 'true';
			baidu.g('mult').checked = '';
		}

		var bugPath = data.bug_space_id;
		baidu.g('bugPath').value = bugPath;
	});
}
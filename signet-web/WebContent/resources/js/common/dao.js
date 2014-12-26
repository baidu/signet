/**
 * st-qa desc: 定义服务端请求  depend:
 * jquery, sender
 */
var request = request || {};
var root = '/signet-web';
request.dao = {

	getUser : function(success, failure) {
		var url = root + '/signet/user';
		sender.doGet(url, success);
	},
	listProject : function(success, failure) {
		var url = root + '/signet/project';
		sender.doGet(url, success);
	},
	listStory : function(projectId, projectName, conditions, searchFlag,
			success, failure) {
		var url = root + '/signet/project/' + projectId + '/story'
				+ '?projectName=' + projectName + '&conditions=' + conditions
				+ '&searchFlag=' + searchFlag;
		sender.doGet(url, success);
	},
	listRoles : function(projectId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/role';
		sender.doGet(url, success);
	},
	listNodes : function(projectId, storyId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/node';
		sender.doGet(url, success);
	},
	addNode : function(projectId, storyId, params, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/node';
		sender.doPost(url, params, success);
	},
	updateNode : function(projectId, storyId, nodeId, type, params, success,
			failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/node/' + nodeId + '/' + type;
		sender.doPut(url, params, success);
	},
	deleteNode : function(projectId, storyId, nodeId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/node/' + nodeId;
		sender.doDelete(url, success);
	},
	applyToEdit : function(projectId, storyId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/authority';
		sender.doPost(url, {}, success);
	},
	releaseCardEditLock : function(projectId, storyId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/authority';
		sender.doDelete(url, success);
	},
	querySubmit : function(projectId, storyId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/submission';
		sender.doGet(url, success);
	},
	cancelSubmit : function(projectId, storyId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/submission';
		sender.doDelete(url, success);
	},
	submit : function(projectId, storyId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/submission';
		sender.doPost(url, {}, success);
	},
	queryRemark : function(projectId, storyId, nodeId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/node/' + nodeId + '/remark';
		sender.doGet(url, success);
	},
	removeRemark : function(projectId, storyId, nodeId, remarkId, success,
			failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/node/' + nodeId + '/remark/' + remarkId;
		sender.doDelete(url, success);
	},
	addRemark : function(projectId, storyId, nodeId, params, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/node/' + nodeId + '/remark';
		sender.doPost(url, params, success);
	},
	queryStoryDetail : function(projectId, storyId, projectName, success,
			failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '?projectName=' + projectName;
		sender.doGet(url, success);
	},
	queryProperty : function(projectId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/property';
		sender.doGet(url, success);
	},
	saveProperty : function(projectId, params, success, failure) {
		var url = root + '/signet/project/' + projectId + '/property';
		sender.doPost(url, params, success);
	},
	saveRole : function(projectId, params, success, failure) {
		var url = root + '/signet/project/' + projectId + '/role';
		sender.doPost(url, params, success);
	},
	deleteRole : function(projectId, roleId, params, success, failure) {
		var url = root + '/signet/project/' + projectId + '/role/' + roleId;
		sender.doDelete(url, success);
	},
	showStatistics : function(projectId, storyIds, success, failure) {
		var url = root + '/signet/project/' + projectId
				+ '/statistics?storyIds=' + storyIds;
		sender.doGet(url, success);
	},
	countAllVisit : function(success, failure) {
		var url = root + '/signet/count';
		sender.doGet(url, success);
	},
	countNewlyVisit : function(date, success, failure) {
		var url = root + '/signet/count?date=' + date + '&line=';
		sender.doGet(url, success);
	},
	countAllLine : function(success, failure) {
		var url = root + '/signet/count?line=1';
		sender.doGet(url, success);
	},
	copyNode : function(projectId, storyId, sId, tId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/copy/' + sId + '/' + tId;
		sender.doPost(url, {}, success);
	},
	createBrotherNode : function(projectId, storyId, nodeId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + storyId
				+ '/brother/' + nodeId;
		sender.doPost(url, {}, success);
	},
	batchCopy : function(projectId, sId, tId, success, failure) {
		var url = root + '/signet/project/' + projectId + '/story/' + sId
				+ '/copy/' + tId;
		sender.doPost(url, {}, success);
	}

};

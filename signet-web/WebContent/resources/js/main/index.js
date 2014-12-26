$(document).ready(function() {
	initUser();
	baidu.g('countBtn').onclick = function() {
		window.open('../count/index.jsp');
	};
});
function initUser() {
	request.dao.getUser(function(data) {
		baidu.g('login_user').innerHTML = data.user;
	});
}

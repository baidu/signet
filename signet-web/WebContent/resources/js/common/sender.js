/**
 * 实现跨域请求  depend: jquery,
 * tangram
 */
var sender = (function() {

	function addParams4URL(url, params) {
		return url + (url.indexOf('?') >= 0 ? '&' : '?') + params;
	}
	return {
		doGet : function(url, success) {
			$.ajax({
				url : url,
				type : 'get',
				contentType : "application/json;charset=utf-8",
				success : success
			});
		},
		doDelete : function(url, success) {
			$.ajax({
				url : url,
				type : 'DELETE',
				success : success
			});
		},
		doPost : function(url, params, success) {
			$.ajax({
				url : url,
				type : 'post',
				contentType : "application/json;charset=UTF-8",
				data : JSON.stringify(params),
				success : success
			});
		},
		doPut : function(url, params, success) {
			$.ajax({
				url : url,
				type : 'put',
				contentType : "application/json;charset=UTF-8",
				data : JSON.stringify(params),
				dataType : 'json',
				success : success
			});
		}
	};
})();
var projectDiv = baidu('<div></div>');
var projects = null;
var static_projectId = null;
var static_projectName = null;
var story_ids = '';

$(document)
		.ready(
				function() {
					baidu('#search_btn').on(
							'click',
							function(e) {
								var condition = baidu('#queryCondition').val();
								listStory(static_projectId, static_projectName,
										condition, true);
							});
					baidu('#copy_btn').on(
							'click',
							function(e) {
								if (confirm("目标Story原有Case将被删除,是否确认此操作?")) {
									var sId = baidu('#sId').val();
									var tId = baidu('#tId').val();
									request.dao.batchCopy(static_projectId,
											sId, tId, function() {
												alert("已完成");
											});
								}

							});
					baidu('#config').on(
							'click',
							function(e) {
								window.open('../config/index.jsp?projectId='
										+ static_projectId);
							});
					baidu('#close_float_btn').on('click', function(e) {
						baidu('#loading_float').css('display', 'none');
						baidu('#content_float').css('display', 'none');
						baidu('#role_view').css('display', '');
						baidu('#create_view').css('display', 'none');
						baidu('#sign_view').css('display', 'none');
						baidu('#pie_float').css('display', 'none');
					});
					baidu('#pie')
							.on(
									'click',
									function(e) {
										baidu('#pie_float').css('display', '');
										baidu('#sign_btn').css(
												'background-color', '#AAD9F2');
										baidu('#sign_btn').css('border-color',
												'#AAD9F2');
										baidu('#role_btn').css(
												'background-color', '#a6b6c6');
										baidu('#role_btn').css('border-color',
												'#a6b6c6');
										baidu('#create_btn').css(
												'background-color', '#a6b6c6');
										baidu('#create_btn').css(
												'border-color', '#a6b6c6');
										request.dao
												.showStatistics(
														static_projectId,
														story_ids,
														function(data) {
															baidu(
																	'#loading_float')
																	.css(
																			'display',
																			'none');
															baidu(
																	'#content_float')
																	.css(
																			'display',
																			'');
															var signData = setPieData(data.signResult);
															drawPieMain(
																	'sign_view',
																	signData);
															var roleData = setPieData(data.roleResult);
															drawPieMain(
																	'role_view',
																	roleData);
															var createData = setPieData(data.createResult);
															drawPieMain(
																	'create_view',
																	createData);
															if (data.total == 0
																	|| data.total == '0') {
																var info = '0% (0/0)';
															} else {
																var info = (100 * parseInt(data.tested) / parseInt(data.total))
																		.toFixed(2)
																		+ '% ('
																		+ data.tested
																		+ '/'
																		+ data.total
																		+ ')'
															}
															baidu(
																	'#static_info')
																	.empty();
															baidu(
																	'#static_info')
																	.append(
																			info);
														});
									});
					baidu('#sign_btn').on(
							'click',
							function(e) {
								baidu('#role_view').css('display', 'none');
								baidu('#create_view').css('display', 'none');
								baidu('#sign_view').css('display', '');
								baidu('#sign_btn').css('background-color',
										'#AAD9F2');
								baidu('#sign_btn').css('border-color',
										'#AAD9F2');
								baidu('#role_btn').css('background-color',
										'#a6b6c6');
								baidu('#role_btn').css('border-color',
										'#a6b6c6');
								baidu('#create_btn').css('background-color',
										'#a6b6c6');
								baidu('#create_btn').css('border-color',
										'#a6b6c6');
							});
					baidu('#role_btn').on(
							'click',
							function(e) {
								baidu('#sign_view').css('display', 'none');
								baidu('#create_view').css('display', 'none');
								baidu('#role_view').css('display', '');
								baidu('#role_btn').css('background-color',
										'#AAD9F2');
								baidu('#role_btn').css('border-color',
										'#AAD9F2');
								baidu('#sign_btn').css('background-color',
										'#a6b6c6');
								baidu('#sign_btn').css('border-color',
										'#a6b6c6');
								baidu('#create_btn').css('background-color',
										'#a6b6c6');
								baidu('#create_btn').css('border-color',
										'#a6b6c6');
							});
					baidu('#create_btn').on(
							'click',
							function(e) {
								baidu('#sign_view').css('display', 'none');
								baidu('#role_view').css('display', 'none');
								baidu('#create_view').css('display', '');
								baidu('#create_btn').css('background-color',
										'#AAD9F2');
								baidu('#create_btn').css('border-color',
										'#AAD9F2');
								baidu('#sign_btn').css('background-color',
										'#a6b6c6');
								baidu('#sign_btn').css('border-color',
										'#a6b6c6');
								baidu('#role_btn').css('background-color',
										'#a6b6c6');
								baidu('#role_btn').css('border-color',
										'#a6b6c6');

							});
					baidu('#query_project_input').bind('input', function(e) {
						listProject(baidu('#query_project_input').val());
					});

					listProject(null);
					initUser();

				});
function drawPieMain(chartId, piedata) {
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		var myChart = ec.init(document.getElementById(chartId));
		option = {
			title : {

				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			toolbox : {
				show : false,
				feature : {
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			series : [ {
				name : '',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : piedata.seriesData
			} ]
		};

		myChart.setOption(option);
	});
}

function setPieData(seriesData) {
	var piedata = {};

	piedata = {
		'seriesData' : seriesData
	}
	return piedata;
}
function initUser() {
	request.dao.getUser(function(data) {
		baidu('#login_user').append(data.user);
	});
}
function listProject(condition) {
	baidu('#project_loading').css('display', '');
	projectDiv.empty();
	baidu('#js-aside').prepend(projectDiv);
	if (projects == null) {
		request.dao.listProject(function(data) {
			projects = data;
			genPoject(data, null);
			baidu('#project_loading').css('display', 'none');
		});
	} else {
		genPoject(projects, condition);
		baidu('#project_loading').css('display', 'none');
	}

}
function genPoject(data, conditions) {
	var html = '';
	for ( var i = 0; i < data.length; i++) {
		if (conditions != null) {
			if (data[i].name.toLowerCase().indexOf(conditions.toLowerCase()) < 0
					&& data[i].descr.toLowerCase().indexOf(
							conditions.toLowerCase()) < 0) {
				continue;
			}
		}

		html = '<li class="mala-subnavi-item">';
		html = html + '<a id="project" onclick="listStory(' + data[i].id + ','
				+ '\'' + data[i].descr + '\',' + '\'\'' + ',' + false
				+ ')" class="mala-subnavi-link" >';
		html = html
				+ '<i class="fa fa-angle-double-right fa-fw" style="position: absolute;margin-top:11px;margin-left:18px;">';
		html = html + '</i><ul class="data" >';
		html = html + '<li class="dataname">';
		html = html + data[i].name;
		var html = html + '</li></ul></a></li>';
		var list = baidu(html);
		projectDiv.append(list);
	}
}
function open_case_detail(storyId) {
	window.open('../case/index.jsp?projectId=' + static_projectId + '&storyId='
			+ storyId + '&projectName=' + static_projectName);

}
function listStory(projectId, projectName, condition, flag) {
	static_projectId = projectId;
	static_projectName = projectName;
	baidu('#wellcome').css('display', 'none');
	baidu('#storyList').css('display', 'none');
	baidu('#loading').css('display', '');
	baidu('#tools').css('display', 'none');
	request.dao
			.listStory(
					projectId,
					projectName,
					condition,
					flag,
					function(data) {
						var _data = data.storyList;
						var _condition = data.conditions;

						if (flag == false) {
							if (_condition == null) {
								_condition = '';
							}
							baidu('#queryCondition').val(_condition);
						}

						baidu('#loading').css('display', 'none');
						baidu('#tools').css('display', '');
						var html = '';
						story_ids = '';
						for ( var i = 0; i < _data.length; i++) {
							if (i == (_data.length - 1)) {
								story_ids = story_ids + _data[i].id;
							} else {
								story_ids = story_ids + _data[i].id + ',';
							}
							var percent = 0;
							var color = '#FF9E9E';
							if (_data[i].totalCaseNum != 0) {
								percent = parseInt(_data[i].testedCaseNum * 100
										/ _data[i].totalCaseNum);
							} else {

								color = '#DBDBDB';
							}
							if (percent < 50 && percent > 0) {
								color = '#FF9E9E';
							} else if (percent < 100 && percent >= 50) {
								color = '#FFD700';
							} else if (percent == 100) {
								color = '#76EE00';
							}
							html = html
									+ '<div class="story" onclick="open_case_detail('
									+ _data[i].id + ')">';
							html = html + '<div class="story-information">';
							html = html + '#' + _data[i].id + ' '
									+ _data[i].title;
							html = html + '</div>';
							html = html
									+ '<div class="story-information-ext" style="color: #ffffff;background:'
									+ color
									+ ';margin:0px;height:28px;line-height:30px;text-align:center">';
							html = html + '&nbsp;&nbsp;' + percent + '%('
									+ _data[i].testedCaseNum + '/'
									+ _data[i].totalCaseNum + ')&nbsp;&nbsp;';
							html = html + '</div>';
							html = html
									+ '<div class="story-information-ext" style="margin-right: 30px;">';
							html = html + _data[i].type;
							html = html + '</div>';
							html = html + '<div class="story-information-ext">';
							html = html + '|';
							html = html + '</div>';
							html = html + '<div class="story-information-ext">';
							html = html + _data[i].status;
							html = html + '</div>';

							html = html
									+ '<div class="story-complite" style="width:';
							html = html + percent;
							html = html + '%;"></div>';
							html = html + '</div>';
						}
						baidu('#storyList').empty();
						baidu('#storyList').prepend(html);
						baidu('#storyList').css('display', '');
					});
}

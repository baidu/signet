//页面初始化
var seriesData = [ {
	name : '',
	value : ''
} ];
var xAxisDataLine = [];
var seriesDataLine = [];
var finish_flag = 3;

function initUser() {
	request.dao.getUser(function(data) {
		baidu.g('login_user').innerHTML = data.user;
	});
}

$(document).ready(function() {
	baidu.g('allBtn').style.color = '#4EF764';
	initUser();
	initPage();
	baidu.g('allBtn').onclick = function() {
		if (finish_flag != 0) {
			return;
		}
		baidu.g('allBtn').style.color = '#4EF764';
		baidu.g('forteenBtn').style.color = '#787878';
		baidu.g('perDayBtn').style.color = '#787878';
		baidu.g('loading').style.display = 'none';
		baidu.g('last14visitpie').style.display = 'none';
		baidu.g('totalpvuvline').style.display = 'none';
		baidu.g('totalvisitpie').style.display = '';
	};
	baidu.g('forteenBtn').onclick = function() {
		if (finish_flag != 0) {
			return;
		}
		baidu.g('forteenBtn').style.color = '#4EF764';
		baidu.g('allBtn').style.color = '#787878';
		baidu.g('perDayBtn').style.color = '#787878';
		baidu.g('loading').style.display = 'none';
		baidu.g('totalvisitpie').style.display = 'none';
		baidu.g('totalpvuvline').style.display = 'none';
		baidu.g('last14visitpie').style.display = '';
	};
	baidu.g('perDayBtn').onclick = function() {
		if (finish_flag != 0) {
			return;
		}
		baidu.g('perDayBtn').style.color = '#4EF764';
		baidu.g('allBtn').style.color = '#787878';
		baidu.g('forteenBtn').style.color = '#787878';
		baidu.g('loading').style.display = 'none';
		baidu.g('totalvisitpie').style.display = 'none';
		baidu.g('last14visitpie').style.display = 'none';
		baidu.g('totalpvuvline').style.display = '';
	};
});
function initPage() {
	request.dao.countAllVisit(function(data) {
		var piedata_all = setPieData(data);
		drawPieMain('totalvisitpie', piedata_all, '33%');
		finish_flag = finish_flag - 1;
		if (finish_flag == 0) {
			baidu.g('loading').style.display = 'none';
			baidu.g('last14visitpie').style.display = 'none';
			baidu.g('totalpvuvline').style.display = 'none';
			baidu.g('totalvisitpie').style.display = '';

		}
	});

	var dateNow = new Date;
	var date = new Date(dateNow.getFullYear(), dateNow.getMonth(), dateNow
			.getDate() - 14);
	date = baidu.date.format(date, 'yyyy-MM-dd');

	request.dao.countNewlyVisit(date, function(data) {
		var piedata_14 = setPieData(data);
		drawPieMain('last14visitpie', piedata_14, '50%');
		finish_flag = finish_flag - 1;
		if (finish_flag == 0) {
			baidu.g('loading').style.display = 'none';
			baidu.g('last14visitpie').style.display = 'none';
			baidu.g('totalpvuvline').style.display = 'none';
			baidu.g('totalvisitpie').style.display = '';
		}
	});

	request.dao.countAllLine(function(data) {
		for ( var i = 0; i < data.length; i++) {
			xAxisDataLine[i] = data[i].name;
			seriesDataLine[i] = data[i].value;
		}
		var linedata = setLineDate(xAxisDataLine, seriesDataLine);
		drawLineMain('totalpvuvline', linedata);
		finish_flag = finish_flag - 1;
		if (finish_flag == 0) {
			baidu.g('loading').style.display = 'none';
			baidu.g('last14visitpie').style.display = 'none';
			baidu.g('totalpvuvline').style.display = 'none';
			baidu.g('totalvisitpie').style.display = '';
		}

	});
}

// 工具栏趋势图
function setPieData(seriesData) {
	var piedata = {};
	piedata = {
		'seriesData' : seriesData
	};
	return piedata;
}

function setLineDate(xAxisDataLine, seriesDataLine) {
	var linedata = {};
	var seriesData = [ {
		name : '访问量',
		type : 'line',
		data : seriesDataLine
	} ];
	linedata = {
		'xAxisData' : xAxisDataLine,
		'seriesData' : seriesData
	}
	return linedata;
}

function drawPieMain(chartId, piedata, percent) {
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		var myChart = ec.init(document.getElementById(chartId));

		option = {
			title : {
				text : '',
				subtext : '',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				x : 'left',
				data : piedata.legendData
			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'pie', 'funnel' ],
						option : {
							funnel : {
								x : '25%',
								width : '50%',
								funnelAlign : 'left',
								max : 1548
							}
						}
					},
					restore : {
						show : true
					},
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
				center : [ '50%', percent ],
				data : piedata.seriesData
			} ]
		};

		myChart.setOption(option);
	});
}

function drawLineMain(chartId, linedata) {
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		var myChart = ec.init(document.getElementById(chartId));
		option = {
			title : {
				text : '',
				subtext : 'signet'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '访问量' ]
			},
			toolbox : {
				show : false,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : linedata.xAxisData
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : linedata.seriesData
		};

		myChart.setOption(option);
	});
}

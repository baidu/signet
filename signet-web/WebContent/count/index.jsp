<!doctype html>
<html>

<head>
<meta charset="UTF-8">
<title>签章测试系统</title>
<link rel="icon" href="../resources/images/logo.ico" type="image/x-icom" />
<link href="../resources/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link href="../resources/css/freeue.css" rel="stylesheet"
	type="text/css">
<script src="../resources/js/common/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="../resources/js/common/esl.js"></script>
<script type="text/javascript">
	require.config({
		paths : {
			'echarts' : '../resources/js/common/echarts',
			'echarts/chart/pie' : '../resources/js/common/echarts',
			'echarts/chart/line' : '../resources/js/common/echarts',
			'echarts/chart/force' : '../resources/js/common/echarts',
			'echarts/chart/bar' : '../resources/js/common/echarts'
		}
	});
</script>
<script src="../resources/js/common/tangram-1.5.2.1.js"
	type="text/javascript"></script>
<script src="../resources/js/common/sender.js" type="text/javascript"></script>
<script src="../resources/js/common/dao.js" type="text/javascript"></script>
<script src="../resources/js/count/count.js" type="text/javascript"></script>
</head>

<body>
	<header id="js-header" class="mala-header mala-header-fix">
		<div class="mala-clearfix">
			<a href="../main/index.jsp" class="mala-logo mala-fl"></a>
			<div class="mala-user-info mala-fr js-header-dropmenu">
				<span class="mala-username"><span id="login_user"></span><i
					class="mala-arrow mala-arrow-down  mala-ml"></i> </span>
			</div>
			<nav class="mala-fr">
				<ul class="mala-nav-list mala-clearfix">
					<li class="mala-nav-item mala-fl">&nbsp;&nbsp;<a href="#"
						id="allBtn" class="mala-nav-link">访问量[All]</a> &nbsp;&nbsp;</li>
					<li class="mala-nav-item mala-fl">&nbsp;&nbsp;<a href="#"
						id="forteenBtn" class="mala-nav-link">访问量[最近14天]</a> &nbsp;&nbsp;</li>
					<li class="mala-nav-item mala-fl">&nbsp;&nbsp;<a href="#"
						id="perDayBtn" class="mala-nav-link">访问量[每日]</a> &nbsp;&nbsp;</li>
				</ul>
			</nav>
		</div>
	</header>
	<div class="mala-main" style="padding-top: 60px; width: 100%">
		<div id="loading"
			style="z-index: 99999; position: absolute; width: 100%; height: 2500px; background-color: #fff; text-align: center; margin-top: 1px;">
			<img src="../resources/images/loading.gif" />加载中……
		</div>
		<div id="totalvisitpie"
			style="width: 100%; height: 600px; overflow: auto;"></div>

		<div id="last14visitpie"
			style="width: 100%; height: 600px; overflow: auto;"></div>

		<div id="totalpvuvline"
			style="width: 100%; height: 600px; overflow: auto;"></div>
	</div>
</body>
</html>
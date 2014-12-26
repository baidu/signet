<!doctype html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签章测试系统</title>
<link rel="icon" href="../resources/images/logo.ico" type="image/x-icom" />
<link href="../resources/css/jquery.gridster.css" rel="stylesheet"
	type="text/css">
<link href="../resources/css/jquery.autocomplete.css" rel="stylesheet"
	type="text/css">
<link href="../resources/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link href="../resources/css/freeue.css" rel="stylesheet"
	type="text/css">
<link href="../resources/css/index.css" rel="stylesheet" type="text/css">
<style>
</style>
</head>

<body>
	<!--=============================导航栏================================-->
	<header id="js-header" class="mala-header mala-header-fix">
		<div class="mala-clearfix">
			<!--=============================右侧的个人中心================-->
			<a href="../main/index.jsp" class="mala-logo mala-fl"></a>
			<div class="mala-user-info mala-fr js-header-dropmenu">
				<span class="mala-username"><span id="login_user"></span><i
					class="mala-arrow mala-arrow-down  mala-ml"></i> </span>
			</div>
		</div>
	</header>
	<!--=====================左侧栏=============-->
	<nav class="mala-aside">
		<input id="query_project_input" type="text" class="mala-input"
			style="" placeholder="请输入关键字">
		<h3 class="mala-subnavi-title">空间名称</h3>
		<div id="project_loading"
			style="display: none; position: absolute; width: 100%; text-align: center; padding-top: 30px;">
			<img src="../resources/images/loading.gif" />加载中……
		</div>
		<ul class="mala-subnavi-list" id="js-aside">

		</ul>
	</nav>


	<!--=========主内容区======-->

	<section class="mala-content">
		<div id="loading"
			style="display: none; position: absolute; width: 100%; text-align: center; padding-top: 170px;">
			<img src="../resources/images/loading.gif" />加载中……若长时间未加载，请检查账号是否有权限
		</div>
		<div class="mala-modal-dialog-out" id="pie_float"
			style="display: none">
			<div class="mala-modal-dialog">
				<div class="mala-modal-content">
					<div class="mala-modal-header">
						<button id="close_float_btn" type="button" class="close"
							data-dismiss="modal">
							<span aria-hidden="true">×</span>
						</button>
						<h4 class="mala-modal-title">统计</h4>
					</div>
					<div class="mala-modal-body">
						<div class="mala-form" style="width: 100%; height: 320px">
							<div id="loading_float"
								style="position: absolute; width: 95%; text-align: center; padding-top: 100px;">
								<img src="../resources/images/loading.gif" />加载中……
							</div>
							<div id="content_float">
								<div class="mala-btn-group mala-mr">
									<a class="mala-btn mala-btn-default mala-btn-smaller mala-mr"
										id="sign_btn">分用户统计</a> <a
										class="mala-btn mala-btn-default mala-btn-smaller mala-mr"
										id="role_btn">分角色统计</a> <a
										class="mala-btn mala-btn-default mala-btn-smaller mala-mr"
										id="create_btn">创建人统计</a> <span id="static_info"></span>
								</div>
								<div id="sign_view"
									style="width: 500px; height: 250px; overflow: auto;"></div>
								<div id="role_view"
									style="display: none; width: 500px; height: 250px; overflow: auto;"></div>
								<div id="create_view"
									style="display: none; width: 500px; height: 250px; overflow: auto;"></div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		<div class="mala-container-fluid" id="wellcome" style="">
			<div class="mala-container mala-clearfix" style="width: 100%">
				<figure class="mala-figure" style="margin-left: -20px;">
					<div class="mala-service-list">
						<div class="mala-row">
							<div class="mala-overview-bar">
								<div class="mala-welcome">你好！欢迎登陆签章测试平台。</div>
								
								<hr />
								
							</div>
						</div>
					</div>
				</figure>
			</div>
		</div>
		<div class="mala-container-fluid" id="tools" style="display: none">
			<input id="queryCondition" name="queryCondition" type="text"
				class="mala-input" style="width: 300px; margin-left: 0px;"
				placeholder="查询框"> <a
				class="mala-btn mala-btn-primary mala-btn-small mala-mr"
				style="margin-top: 9px;" id="search_btn"><i class="fa fa-search"></i>Search</a>

			<input id="sId" name="" type="text" class="mala-input"
				style="width: 100px; margin-left: 0px;" placeholder="源Story ID">
			<input id="tId" name="" type="text" class="mala-input"
				style="width: 100px; margin-left: 0px;" placeholder="目标Story ID">
			<a class="mala-btn mala-btn-primary mala-btn-small mala-mr"
				style="margin-top: 9px;" id="copy_btn">Copy</a>

			<div class="setting" id="config"></div>
			<div class="pie" id="pie"></div>
		</div>

		<div class="mala-container-fluid" id="storyList"></div>

	</section>
	<div class="mala-aside-opt">
		<a href="#" class="" id="mala-aside-hide"> <i
			class="fa fa-arrow-left"></i> </a> <a href="#" class=""
			id="mala-aside-show" style="display: none"> <i
			class="fa fa-arrow-right"></i> </a>
	</div>
	<script src="../resources/js/common/jquery-1.7.2.min.js"></script>
	<script src="../resources/js/common/jquery-ui.js"></script>
	<script src="../resources/js/common/jquery.gridster.js"></script>
	<script src="../resources/js/common/jquery.ui.autocomplete.js"></script>

	<script type="text/javascript" src="../resources/js/common/esl.js"></script>
	<script type="text/javascript">
			require.config({
				paths: {
					'echarts': '../resources/js/common/echarts',
					'echarts/chart/pie': '../resources/js/common/echarts',
					'echarts/chart/line': '../resources/js/common/echarts',
					'echarts/chart/force': '../resources/js/common/echarts',
					'echarts/chart/bar': '../resources/js/common/echarts'
				}
			});
		</script>

	<script src="../resources/js/common/common.js"></script>
	<script src="../resources/js/common/tangram-min.js"></script>
	<script src="../resources/js/common/sender.js"></script>
	<script src="../resources/js/common/dao.js"></script>
	<script src="../resources/js/project/index.js"></script>


</body>

</html>

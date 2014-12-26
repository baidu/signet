<!doctype html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签章测试系统</title>
<link rel="icon" href="../resources/images/logo.ico" type="image/x-icom" />
<link href="../resources/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link href="../resources/css/freeue.css" rel="stylesheet"
	type="text/css">
<link href="../resources/css/tree.css" rel="stylesheet" type="text/css">
</head>

<body>
	<header id="js-header" class="mala-header mala-header-fix">
		<div class="mala-clearfix">
			<a href="../main/index.jsp" class="mala-logo mala-fl"></a>
			<div class="mala-user-info mala-fr js-header-dropmenu">
				<span class="mala-username"><span id="login_user"></span> <i
					class="mala-arrow mala-arrow-down  mala-ml"></i> </span>
			</div>
			<div id="loading"
				style="display: none; position: absolute; width: 100%; text-align: center; padding-top: 22px;">
				<img src="../resources/images/loading.gif" />加载中……
			</div>
			<nav class="mala-fr">
				<ul class="mala-nav-list mala-clearfix">
					<li class="mala-nav-item mala-fl">&nbsp;&nbsp;<a href="#"
						id="editBtn" class="mala-nav-link">编辑</a> &nbsp;&nbsp;</li>
					<li class="mala-nav-item mala-fl">&nbsp;&nbsp;<a href="#"
						id="submitBtn" class="mala-nav-link">提测</a> &nbsp;&nbsp;</li>
					<li class="mala-nav-item mala-fl">&nbsp;&nbsp;<a href="#"
						id="showDetailBtn" class="mala-nav-link">显示详情</a> &nbsp;&nbsp;</li>
					<li class="mala-nav-item mala-fl">&nbsp;&nbsp;<a href="#"
						id="newBugBtn" class="mala-nav-link">新建BUG</a> &nbsp;&nbsp;</li>
					<li class="mala-nav-item mala-fl">&nbsp;&nbsp;<a href="#"
						id="importBtn" class="mala-nav-link">批量导入</a> &nbsp;&nbsp;</li>
				</ul>
			</nav>
		</div>
	</header>
	<section class="mala-all">

		<div id="containner">
			<div id="icafe_status_float"
				style="display: none; position: absolute; right: 0px; bottom: 0px; z-index: 65536; width: 160px; height: 100px; background-image: url(../resources/images/dia.png);">
				<p style="margin-top: 35px; margin-left: 21px;">
					<span id="icafe_status_from"></span>
				</p>
				<p style="margin-left: 38px;" id="status_change_url">
					<a href="#" id="change_icafe_status_url">修改为：<span
						id="icafe_status_to"></span>
					</a>
				</p>
			</div>
			<div id="cardDetail"
				style="border-bottom: 1px #CCCCCC solid; padding-left: 10px; overflow: auto; display: none;">
				<br />
				<p>
				<div id="title_text" style="font-size: 15px"></div>
				</p>
				<br />
				<p>
				<div id="detail_text"></div>
				</p>
			</div>
			<div id="treeContainner"
				style="-webkit-user-select: none; width: 100%; height: 100%; overflow: auto; position: relative;"></div>

		</div>
		<div id="flow" style="display: none">
			<div id="changePwdFloat"
				class""
			style="position: absolute; width: 100px; top: 30px; right: 70px; border: solid 1px #adb6c9; border-radius: 0px; background-color: white">
				<div class="alert_content" style="">
					<p>节点操作说明</p>
					<p>自动化签章说明</p>
					<p>角色签章说明</p>
					<p>角色签章流程</p>
				</div>
			</div>
		</div>
		<div id="masker" class="masker" style="display: none">
			<div id="changePwdFloat" class="alert_win"
				style="position: absolute; z-index: 9999;">
				<div class="alert_title">批量导入</div>
				<div class="alert_content">
					<form id="temp_download_id" action="" name="form" method="post">
						<input id="eDownloadTemplate" type="button" value="下载模板">
					</form>
					<form id="importForm" target="hidden_frame"
						enctype="multipart/form-data" action="" method="post">
						<table>
							<tr>
								<td>通过XLS格式的文件，可批量导入验收点。</td>
							</tr>
							<tr>
								<td><input class="input_file" type="file" name="xls"
									size="60" /></td>
							</tr>
						</table>

					</form>
					<div style="width: 250px;" id="messageDiv"></div>
					<iframe name="hidden_frame" id="hidden_frame" style="display: none"></iframe>
					<div class="alert_buttons">
						<div type="submit" class="alert_btn" id="btn_ok_change">确定</div>
						<div class="alert_btn" id="btn_cancel_change">取消</div>
					</div>
				</div>
			</div>
		</div>

		<div id="masker" class="masker" style="display: none">
			<div id="changePwdFloat" class="alert_win"
				style="position: absolute; z-index: 9999;">
				<div class="alert_title">批量导入</div>
				<div class="alert_content"></div>
			</div>
		</div>

		<div id="introduceLayer" style="display: none">
			<img id="arrowLayer" src='../resources/images/arrow_bg.png'> <img
				id="closeLayer" src='../resources/images/delete.png'> <img
				src='../resources/images/introduce.png' />
		</div>
		<div id="scoreLayer" style="display: none">
			<img id="scorearrowLayer" src='../resources/images/arrow_bg.png'>
			<img id="closescoreLayer" src='../resources/images/delete.png'>
			<table border=0 cellspacing=0 width=100% bordercolorlight=#333333
				bordercolordark=#efefef>
				<tr>
					<td><fieldset>
							<legend>good</legend>
							<span id="goodinfo"></span><br> <span id="goodcasenuminfo"></span><br>
							<span id="buginfo"></span> <br>
						</fieldset></td>
				</tr>
				<tr>
					<td><fieldset style="height: 100px">
							<legend>bad</legend>
							<span id="badinfo"></span> <br>
						</fieldset></td>
				</tr>
				<tr>
					<td>
						<p>评分趋势图</p> <span id="trendgraph"
						style="width: 80%; height: 30%; position: absolute; top: 223px; left: 5px; font-family: 微软雅黑；"></span><br>
					</td>
				</tr>
			</table>
		</div>
	</section>
	<script src="../resources/js/common/jquery-1.7.2.min.js"></script>
	<script src="../resources/js/common/common.js"></script>
	<script src="../resources/js/common/tangram-1.5.2.1.js"></script>
	<script src="../resources/js/common/sender.js"></script>
	<script src="../resources/js/common/dao.js"></script>
	<script src="../resources/js/case/index.js"></script>
	</script>
</body>
</html>

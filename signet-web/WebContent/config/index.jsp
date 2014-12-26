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
				<span class="mala-username"><span id="login_user"></span><i
					class="mala-arrow mala-arrow-down  mala-ml"></i> </span>
			</div>
		</div>
	</header>
	<section class="mala-all" style="top: 70px">
		<div class="mala-flow-bar">
			<ul class="mala-flow-list mala-clearfix">
				<li class="mala-flow-item active mala-fl" style="cursor: pointer;"
					id="base_tab"><i class="fa fa-list"></i>基础设置</li>
				<li class="mala-flow-item mala-fl" style="cursor: pointer;"
					id="signet_tab"><i class="fa fa-search"></i>签章管理</li>
			</ul>
		</div>
		<div id="base_content" class="mala-container-fluid">
			<div class="mala-form">
				<div class="mala-form-item mala-row">
					<div class="mala-col-md-2">
						<label for="" class="mala-label mala-form-fix"> <span
							class="mala-required">*</span>签章模式：</label>
					</div>
					<div class="mala-col-md-6" style="padding-top: 5px;">
						<input type="radio" id="single" name="roleMode"
							class="mala-form-fix">单角色签章 <input type="radio" id="mult"
							name="roleMode" class="mala-form-fix">多角色签章
					</div>
				</div>
				<div class="mala-form-item mala-row">
					<div class="mala-col-md-2">
						<label for="" class="mala-label mala-form-fix"> 新增BUG地址：</label>
					</div>
					<div class="mala-col-md-6">
						<input type="text" id="bugPath"
							class="mala-input mala-form-control" style="margin: 0px">
					</div>
				</div>
				<div class="mala-form-item mala-row">
					<div class="mala-col-md-2"></div>
					<div class="mala-col-md-6">
						<input type="submit" id="saveBtn"
							class="mala-btn mala-btn-primary mala-btn-small mala-mr"
							value="保存">
					</div>
				</div>
			</div>
		</div>
		<div id="signet_content" class="mala-container-fluid"
			style="display: none">
			<div class="mala-form">

				<div class="mala-form-item mala-row">
					<div class="mala-col-md-2">
						<label for="" class="mala-label mala-form-fix"> <span
							class="mala-required">*</span>签章名称 ：</label>
					</div>
					<div class="mala-col-md-6">
						<input type="text" id="signetName"
							class="mala-input mala-form-control" style="margin: 0px">
					</div>
				</div>
				<div class="mala-form-item mala-row">
					<div class="mala-col-md-2">
						<label for="" class="mala-label mala-form-fix"> <span
							class="mala-required">*</span>签章角色 ：</label>
					</div>
					<div class="mala-col-md-6">
						<select id="signetType" class="mala-select mala-mr"
							style="margin: 0px">
							<option value="3">QA</option>
							<option value="1">FE</option>
							<option value="1">RD</option>
							<option value="2">PM</option>
						</select>
					</div>
				</div>
				<div class="mala-form-item mala-row">
					<div class="mala-col-md-2"></div>
					<div class="mala-col-md-6">
						<input type="submit" id="saveSignetBtn"
							class="mala-btn mala-btn-primary mala-btn-small mala-mr"
							value="生成签章">
					</div>
				</div>
				<div class="mala-form-item mala-row" id="roles"
					style="padding: 50px"></div>
			</div>
		</div>
	</section>
	<script src="../resources/js/common/jquery-1.7.2.min.js"></script>
	<script src="../resources/js/common/common.js"></script>
	<script src="../resources/js/common/tangram-1.5.2.1.js"></script>
	<script src="../resources/js/common/sender.js"></script>
	<script src="../resources/js/common/dao.js"></script>
	<script src="../resources/js/config/index.js"></script>
	</script>
</body>
</html>

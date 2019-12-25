<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Kanrisha - A Premium HTML5 Responsive Admin Template</title>
	<!--[if lt IE 9]>
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<script src="Javascript/Flot/excanvas.js"></script>
	<![endif]-->
	<!-- The Fonts -->
	<!-- The Main CSS File -->
	<link rel="stylesheet" href="CSS/style.css" />
	<!-- jQuery -->
	<script src="Javascript/jQuery/jquery-1.7.2.min.js"></script>
	<!-- Flot -->
	<script src="Javascript/Flot/jquery.flot.js"></script>
	<script src="Javascript/Flot/jquery.flot.resize.js"></script>
	<script src="Javascript/Flot/jquery.flot.pie.js"></script>
	<!-- DataTables -->
	<script src="Javascript/DataTables/jquery.dataTables.min.js"></script>
	<!-- ColResizable -->
	<script src="Javascript/ColResizable/colResizable-1.3.js"></script>
	<!-- jQuryUI -->
	<script src="Javascript/jQueryUI/jquery-ui-1.8.21.min.js"></script>
	<!-- Uniform -->
	<script src="Javascript/Uniform/jquery.uniform.js"></script>
	<!-- Tipsy -->
	<script src="Javascript/Tipsy/jquery.tipsy.js"></script>
	<!-- Elastic -->
	<script src="Javascript/Elastic/jquery.elastic.js"></script>
	<!-- ColorPicker -->
	<script src="Javascript/ColorPicker/colorpicker.js"></script>
	<!-- SuperTextarea -->
	<script src="Javascript/SuperTextarea/jquery.supertextarea.min.js"></script>
	<!-- UISpinner -->
	<script src="Javascript/UISpinner/ui.spinner.js"></script>
	<!-- MaskedInput -->
	<script src="Javascript/MaskedInput/jquery.maskedinput-1.3.js"></script>
	<!-- ClEditor -->
	<script src="Javascript/ClEditor/jquery.cleditor.js"></script>
	<!-- Full Calendar -->
	<script src="Javascript/FullCalendar/fullcalendar.js"></script>
	<!-- Color Box -->
	<script src="Javascript/ColorBox/jquery.colorbox.js"></script>
	<!-- Kanrisha Script -->
	<script src="Javascript/kanrisha.js"></script>
	
	<link rel="stylesheet" href="CSS/processbar.css" />
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style>
	#progressBar>span:nth-child(2){
		left: 25%;
		background:green;
	}
	#progressBar>span:nth-child(3){
		left: 50%;
		background:green;
	}
	#progressBar div span{
		width: 50%;
	}
	</style>
</head>
<body>
<!-- Change Pattern -->
	<div class="changePattern">
		<span id="pattern1"></span>
		<span id="pattern2"></span>
		<span id="pattern3"></span>
		<span id="pattern4"></span>
		<span id="pattern5"></span>
		<span id="pattern6"></span>
	</div>
	<!-- Top Panel -->
	<div class="top_panel">
		<div class="wrapper">
			<div class="user">
				<img src="Images/user_avatar.png" alt="user_avatar" class="user_avatar" />
				<span class="label">Yuhang Wang</span>
				<!-- Top Tooltip -->
				<div class="top_tooltip">
					<div>
						<ul class="user_options">
							<li class="i_16_profile"><a href="#" title="Profile"></a></li>
							<li class="i_16_tasks"><a href="#" title="Tasks"></a></li>
							<li class="i_16_notes"><a href="#" title="Notes"></a></li>
							<li class="i_16_options"><a href="#" title="Options"></a></li>
							<li class="i_16_logout"><a href="#" title="Log-Out"></a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="copyrights">Collect from <a href="http://www.cssmoban.com/" >企业网站模板</a></div>
	<header class="main_header">
		<div class="wrapper">
			<div class="logo">
				<a href="#" title="Kanrisha Home">
					<img src="Images/kanrisha_logo.png" alt="kanrisha_logo" />
				</a>
			</div>
		</div>
	</header>

	<div class="wrapper small_menu">
		<ul class="menu_small_buttons">
			<li><a title="General Info" class="i_22_dashboard smActive" href="index.html"></a></li>
			<li><a title="Your Messages" class="i_22_inbox" href="inbox.html"></a></li>
			<li><a title="Visual Data" class="i_22_charts" href="charts.html"></a></li>
			<li><a title="Kit elements" class="i_22_ui" href="ui.html"></a></li>
			<li><a title="Some Rows" class="i_22_tables" href="tables.html"></a></li>
			<li><a title="Some Fields" class="i_22_forms" href="forms.html"></a></li>
		</ul>
	</div>

	<div class="wrapper contents_wrapper">
		
		<aside class="sidebar">
			<ul class="tab_nav">
				<li class="i_32_dashboard">
					<a href="auroraIndex.html" title="General Info">
						<span class="tab_label">查询</span>
					</a>
				</li>
				<li class="active_tab i_32_tables">
					<a href="auroraTables.html" title="Some Rows">
						<span class="tab_label">分类</span>
					</a>
				</li>
				<li class="i_32_forms">
					<a href="auroraForms.html" title="Some Fields">
						<span class="tab_label">导入</span>
					</a>
				</li>
				<li class="i_32_ui">
					<a href="auroraUi.html" title="Kit elements">
						<span class="tab_label">标注</span>
					</a>
				</li>
			</ul>
		</aside>

		<div class="contents">
			<div class="grid_wrapper">
			
				<div class="g_6 contents_header">
					<h3 class="i_16_forms tab_label">分类</h3>
				</div>
				
				<div class="g_12 separator"><span></span></div>
			
				<div class="g_12">
					<div class="widget_header">
						<h4 class="widget_header_title wwIcon i_16_forms">分类</h4>
					</div>
					<div class="widget_contents noPadding">
						<div class="line_grid">
							<div id="progressBar">
								 <!-- 进度条 -->
								 <div>
								 	<span>
								 	</span>
								 </div>
								 <!-- 五个圆 -->
								 <span>预处理</span>
								 <span>特征提取</span>
								 <span>机器学习</span>
								 <span>统计分析</span>
								 <span>初始化</span>
							</div>
						</div>
						<div class="line_grid">
							<div class="g_3"><span class="label">实验编号</span></div>
							<div class="g_9">
								<input class="simple_field" type="text" readonly value="76886"/>
							</div>
						</div>
						
						<div class="line_grid">
							<div class="g_3"><span class="label">特征提取算法&参数</span></div>
							<div class="g_2">
								<select class="simple_form">
									<option value="LBP" selected="selected" />LBP
									<option value="DSIFT" />DSIFT
								</select>
							</div>
							<div class="g_4">
								<input class="simple_field" type="text" />
							</div>
						</div>
						
						<div class="line_grid">
							<div class="g_3"><span class="label">下一步</span></div>
							<div class="g_9">
								<a href="auroraClassification.jsp"><input type="submit" value="下一步" class="simple_buttons" /></a>
							</div>
						</div>	
						
											
					</div>
			    </div>

			</div>
		</div>
		
		
	</div>

	
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>
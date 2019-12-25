<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Aurora Data Management System</title>
	<!--[if lt IE 9]>
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<script src="Javascript/Flot/excanvas.js"></script>
	<![endif]-->
	<!-- The Fonts -->
	<!-- The Main CSS File -->
	<link rel="stylesheet" href="CSS/style.css" />
	<link rel="stylesheet" href="CSS/jquery-ui.min.css" />
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
	<!-- Calendar Script -->
	<!-- <link rel="CSS/stylesheet" href="anytime.5.1.2.css" /> -->
	<script src="Javascript/anytime.5.1.2.js"></script>
	<script src="Javascript/laydate.js"></script>
	<script src="Javascript/galleria/galleria-1.5.1.js"></script>
	
	<link rel="stylesheet" href="CSS/jquery.datetimepicker.css" />
	<script src="Javascript/jquery.datetimepicker.js"></script>
	
	<script>
   		$('#datetimepicker').datetimepicker({lang:'ch'});
	</script>
	
	
	<style type="text/css">
  
		  .select1{width:100%;height:20px;margin-left:10px;margin-top:320px;}
		  .selectWaveBand{width:18%;height:350px;float:left;}
		  .images{width:80%; height:350px;margin-left:18%;margin-top:20px;margin-bottom:20px;margin-right:20px;}
		  
		  
		  .g1{margin:2px 0px 100px 20px; float:left}
		  .g2{margin:2px 0px 100px 20px; float:left}
		  .m-tab .u-tab.active{
			    background: aqua;
				background-color: #f9f9f9;
			}
			 
			.m-ct ul{
			    display: none;
			    
			}
			.m-ct ul.active{
			    display: block;
			    
			    
			}
		</style>
		<!-- <script type="text/javascript">
		  $(document).ready(function(){
		    Galleria.loadTheme('Javascript/galleria/themes/classic/galleria.classic.js');
		        $('.images').galleria({
		           image_crop:true,
		           transition: 'fade',
		        });
		  }); 
		</script>
		-->
		<script>
		$(function(){
		    $(".u-tab").on("click",function(){
		        var oTab=$(this);
		        var oUl=$(".m-ct ul").eq(oTab.index());
		        oUl.show().siblings().hide();
		        oTab.addClass("active").siblings().removeClass("active");
		 
		        var url=oTab.attr("data-url");
		        $("#j-more").attr("href",url);
		    });  
		});
		</script>
		
	<link rel="stylesheet" href="CSS/switch.css" />
	
	<script>
	$(document).ready(function() {  
    $("#onoffswitch").on('click', function(){  
        clickSwitch()  
    });  
  
    var clickSwitch = function() {  
        if ($("#onoffswitch").is(':checked')) {  
        	console.log("cc1");
            <%request.getSession().setAttribute("flag","1");%>
            console.log("cc1"+<%request.getSession().getAttribute("title");%>);  
        } else {   
        	console.log("cc2");
        	<%request.getSession().setAttribute("flag","0");%>
            console.log("cc2"+<%request.getSession().getAttribute("flag");%>);  
        }  
    };  
	});
	</script>
	
	
	
	<%@page import="java.util.ArrayList,java.util.List" %>
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
				<li class="<%=query %> i_32_dashboard">
					<a href="index.jsp" title="General Info">
						<span class="tab_label">数据探索</span>
					</a>
				</li>
				<!--<li class="<%=classify %> i_32_tables">
					<a href="classify.jsp" title="Some Rows">
						<span class="tab_label">Classify</span>
					</a>
				</li>-->
				<li class="<%=regroup %> i_32_forms">
					<a href="regroup.jsp" title="Some Fields">
						<span class="tab_label">数据导入</span>
					</a>
				</li>
				<li class="<%=label %> i_32_ui">
					<a href="label.jsp" title="Kit elements">
						<span class="tab_label">人工标注</span>
					</a>
				</li>
			</ul>
		</aside>
		
			
		<div class="contents">
			<div class="grid_wrapper">
				<jsp:include page="<%=bodyfile %>" />
			</div>
		</div>
	</div>

<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>

</html>
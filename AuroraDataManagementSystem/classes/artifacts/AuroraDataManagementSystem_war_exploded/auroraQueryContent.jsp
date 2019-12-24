<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
    
<%@page import="java.util.ArrayList,java.util.List" %>
<%@page import="aurora.ScoreImage" %>

<!-- Galleria Script -->
    <script type="text/javascript" src="Javascript/galleria/galleria-1.5.1.js"></script>
    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
  <script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
  <script>
  $(function() {
    $( "#tabs" ).tabs();
  });
  </script>
  <script>
  $(function() {
    $( "#tabs" ).tabs();
  });
  </script>
<style type="text/css">
  .images{width:100%; height:300px;margin-left:10px;margin-top:20px;margin-bottom:20px;margin-right:10px;float:left;}
  .select1{width:20%;height:150px;margin-left:40px;margin-top:600px;float:left;}
.list li{list-style-type:none}
</style>  
    
<div class="g_6 contents_header">
	<h3 class="i_16_forms tab_label">查询</h3>
</div>

<div class="g_12 separator"><span></span></div>
			
<form name="timeQueryForm" method="POST" action="showImgServlet">
	<div class="g_6">
		<div class="widget_header">
			<h4 class="widget_header_title wwIcon i_16_forms">时间查询</h4>
		</div>
		<div class="widget_contents noPadding">
			<%@ include file="auroraQueryCommon.jsp" %>
			<div class="line_grid">
				<div class="g_3"><span class="label">类型</span></div>
				<div class="g_9">
					<input type="checkbox" class="simple_form" name="One"/>多重弧
					<input type="checkbox" class="simple_form" name="Two"/>帷幔型冕状
					<input type="checkbox" class="simple_form" name="Three"/>放射型冕状
					<input type="checkbox" class="simple_form" name="Four"/>热点型极光
				</div>
			</div>
			<div class="line_grid">
				<div class="g_3"><span class="label">确认</span></div>
				<div class="g_9">
					<input type="submit" value="查询" class="simple_buttons" />
				</div>
			</div>
		</div>
    </div>
</form>

<form name="similarForm" method="POST" action="similarquery" enctype="multipart/form-data">
<div class="g_6">
	<div class="widget_header">
		<h4 class="widget_header_title wwIcon i_16_forms">原图查询</h4>
	</div>
	<div class="widget_contents noPadding">
		<div class="line_grid">
			<div class="g_3"><span class="label">原图路径</span></div>
			<div class="g_9">
		    	<input type="file" name="uploadFile" />
		    </div>
		</div>
		<div class="line_grid">
		    <div class="g_3"><span class="label">确认</span></div>
		    <div class="g_9">
		    <input type="submit" value="上传" />
		    </div>
		</div>
	</div>
</div>
</form>
 
<%if(request.getAttribute("SI")!=null){%>
<div class="g_12">
	<div class="widget_header">
		<h4 class="widget_header_title wwIcon i_16_forms">查询结果</h4>
	</div>
	<div class="widget_contents noPadding">
	<% 
		List<ScoreImage> si = (ArrayList)request.getAttribute("SI");
	%> 
	<%for(ScoreImage n:si){ %>
	
		<div class="line_grid">
			<div class="g_3"><span class="label"><%=n.getName() %>   相似度：<%=n.getScore() %></span></div>
			<div class="g_9">
				<img src="/AuroraDataManagementSystem/picquery?name=<%=n.getName() %>" alt="">		
			</div>
		</div>
	<%} %>
	</div>
</div>
<%}%>

<%if(request.getSession().getAttribute("name")!=null){%>

<script type="text/javascript">
  $(document).ready(function(){
    Galleria.loadTheme('Javascript/galleria/themes/classic/galleria.classic.js');
        $('.images').galleria({
           image_crop:false,
           transition: 'fade',
        });
  }); 
</script>
<div class="g_12">
	<div class="widget_header">
		<h4 class="widget_header_title wwIcon i_16_forms">查询结果</h4>
	</div>
	<div class="widget_contents noPadding">
		<!--<div class="testswitch">  
	        <input class="testswitch-checkbox" id="onoffswitch" type="checkbox">  
	        <label class="testswitch-label" for="onoffswitch">  
	            <span class="testswitch-inner" data-on="Keogram" data-off="原图"></span>  
	            <span class="testswitch-switch"></span>  
	        </label>  
	     </div> -->
		<% 
			List<String> name = (ArrayList)request.getSession().getAttribute("name");
		    int i = name.size();
			List<String> title = (ArrayList)request.getSession().getAttribute("title");
		%> 
		<div class="g_6_test"  display:inline>
		<div class="g_6">
			<div class="images">
		<%for(int n=0;n<i;n++){ %>
			<img src="/AuroraDataManagementSystem/showServlet?id=<%=n%>"  title="<%=title.get(n)%>">	
		<%} %>
			</div>
		</div>
		<div class="g_6">
			<div class="images">
			<%for(int n=0;n<i;n++){ %>
			<a href="/AuroraDataManagementSystem/showkeogramServlet">
				<img src="/AuroraDataManagementSystem/showServlet?id=<%=n%>" title = "<%=title.get(n)%>" >	
			</a>	
           <%} %>	
			</div>
		</div>
		</div> 
	</div>
</div>
<%}%>
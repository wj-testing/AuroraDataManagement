<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
    
<%@page import="java.util.ArrayList,java.util.List" %>

   <script type="text/javascript" src="Javascript/galleria/galleria-1.5.1.js"></script>
    <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
  <script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>

<style type="text/css">
  .images{width:70%; height:650px;margin-left:10px;margin-top:20px;margin-bottom:20px;margin-right:20px;float:left; }
  .select1{width:80%;height:150px;margin-left:10px;margin-top:400px;float:left; }
  .list li{list-style-type:none}
</style>  

<div class="g_6 contents_header">
	<h3 class="i_16_forms tab_label">查询</h3>
</div>

<div class="g_12 separator"><span></span></div>
<form name="labelQueryForm" method="POST" action="queryMarkServlet">
	<div class="g_12">
		<div class="widget_contents noPadding">
			<%@ include file="auroraQueryCommon.jsp" %>
			<div class="line_grid">
				<div class="g_3"><span class="label">确认</span></div>
				<div class="g_9">
					<input type="submit" value="查询" class="simple_buttons" />
				</div>
			</div>
		</div>
    </div>
</form>

<%if(request.getSession().getAttribute("name1")!=null){ %>


<script type="text/javascript">
  $(document).ready(function(){
    Galleria.loadTheme('Javascript/galleria/themes/classic/galleria.classic.js');
    Galleria.ready(function(options) {
	    this.bind('image', function(e) {
	        Galleria.log(e.index) // the image index
	         var abc = $("#index")
	        abc.val(e.index.toString()) 
	    });
	});
        $('.images').galleria({
           image_crop:false,
           transition: 'fade',
        });
        
  }); 
</script>

<script type="text/javascript">
function settypeFun(){
    $.ajax({
        type: "POST",
        url: "markServlet",
        data: $('#settype').serialize(),
        success: function(msg){
              alert("hello");
        }
    })
    var objects = document.getElementsByTagName("input")
	for(var i=0;i<objects.length;i++){
		if(objects[i].type=='radio' ){
				objects[i].checked=""
 		}
	}
}
</script>

<% 
	int i = 0;
	List<String> name = new ArrayList();
	List<String> title1 = new ArrayList();
	name = (ArrayList)request.getSession().getAttribute("name1");
	title1 = (ArrayList)request.getSession().getAttribute("title1");
	i = name.size();
%> 


<div class="g_12">
	<div class="widget_header">
		<h4 class="widget_header_title wwIcon i_16_forms">查询结果</h4>
	</div>
	<div class="widget_contents noPadding">
		<div class="images">
           <%for(int n=0;n<i;n++){ %>
           <img src="/AuroraDataManagementSystem/markshowServlet?id=<%=n%>" title = "<%=title1.get(n)%>">
            <%} %>
    	</div> 
	    <div class="g_3 ">
		    <div class="select1">
			    <div class="list">
					   <form  id="settype" >
					   <ol>
					   <li>
						<input type="radio" name="setType" value="1">多重弧
						</li>
						<br />
						<li>
						<input type="radio" name="setType" value="2">帷幔型冕状
						</li>
						<br />
						<li>
						<input type="radio" name="setType" value="3">放射型冕状
						</li>
						<br />
						<li>
						<input type="radio" name="setType" value="4">热点型极光
						<br />
						<li>
						<input type="text" name="index" id="index" value=""  hidden="true">
						</li>
						<br />
						<li>
						<input type="button" onclick="settypeFun()" value="设置类型"> 
						</li>
						</ol>	
					   </form>
				</div>
			</div>
	    </div>
	</div>
</div>
<%}%>
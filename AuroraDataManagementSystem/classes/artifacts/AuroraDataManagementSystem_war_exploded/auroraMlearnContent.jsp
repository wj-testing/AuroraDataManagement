<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
    
<link rel="stylesheet" href="CSS/processbar.css" />

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
		width: 65%;
	}
</style>

<div class="g_6 contents_header">
	<h3 class="i_16_forms tab_label">����</h3>
</div>
				
<div class="g_12 separator"><span></span></div>

<form name="classifyForm" method="POST" action="classify">			
<div class="g_12">
	<div class="widget_header">
		<h4 class="widget_header_title wwIcon i_16_forms">����</h4>
	</div>
	
	<div class="widget_contents noPadding">
		<%@ include file="progressbar.jsp" %>
		
		<%
			String seq = request.getAttribute("seq").toString();
		%>
		
		<div class="line_grid">
			<div class="g_3"><span class="label">ʵ����</span></div>
			<div class="g_9">
				<input class="simple_field" type="text" name="seq" readonly value=<%=seq %>/>
			</div>
		</div>
						
		<div class="line_grid">
			<div class="g_3"><span class="label">������෽��&����</span></div>
			<div class="g_2">
				<select class="simple_form" name="algorithm">
					<option value="LBP" selected="selected" />BOW
					<option value="DSIFT" />KMEANS
				</select>
			</div>
			<div class="g_4">
				<input class="simple_field" type="text" name="parameter"/>
			</div>
		</div>
						
		<div class="line_grid">
			<div class="g_3"><span class="label">��һ��</span></div>
			<div class="g_9">
				<a href="result.jsp"><input type="submit" value="��һ��" class="simple_buttons" /></a>
			</div>
		</div>					
	</div>
</div>
</form>
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
	#progressBar>span:nth-child(4){
		left: 75%;
		background:green;
	}
	#progressBar div span{
		width: 100%;
	}
</style>

<div class="g_6 contents_header">
	<h3 class="i_16_forms tab_label">����</h3>
</div>

<div class="g_12 separator"><span></span></div>

<form name="resultForm" method="POST" action="result">
<div class="g_12">
	<div class="widget_header">
		<h4 class="widget_header_title wwIcon i_16_forms">����</h4>
	</div>
	<div class="widget_contents noPadding">
		<%@ include file="progressbar.jsp" %>
		
		<div class="line_grid">
			<div class="g_3"><span class="label">ʵ����</span></div>
			<div class="g_9">
				<input class="simple_field" type="text" readonly value="76886"/>
			</div>
		</div>
		<div class="line_grid">	
			<div class="widget_header">
				<h4 class="widget_header_title wwIcon i_16_charts">Charts</h4>
			</div>
			<div class="widget_contents">
				<div class="charts"></div>
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">����ʵ����</span></div>
			<div class="g_9">
				<a href="classify.jsp"><input type="submit" value="����" class="simple_buttons" /></a>
			</div>
		</div>	
		<div class="line_grid">
			<div class="g_3"><span class="label">ʵ��Ա�</span></div>
			<div class="g_6">
				<input class="simple_field" type="text" />
			</div>
			<div class="g_3">
				<a href="analysis.jsp"><input type="submit" value="�Ƚ�" class="simple_buttons" /></a>
			</div>
		</div>		
	</div>
</div>
</form>
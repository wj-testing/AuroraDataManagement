<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
    
<%@page import="java.util.ArrayList,java.util.List" %>
    
<div class="g_6 contents_header">
	<h3 class="i_16_forms tab_label">查询</h3>
</div>

<div class="g_12 separator"><span></span></div>
			
<form name="timeQueryForm" method="POST" action="timequery">
	<div class="g_6">
		<div class="widget_header">
			<h4 class="widget_header_title wwIcon i_16_forms">时间查询</h4>
		</div>
		<div class="widget_contents noPadding">
			<%@ include file="auroraQueryCommon.jsp" %>
			<div class="line_grid">
				<div class="g_3"><span class="label">类型</span></div>
				<div class="g_9">
					<input type="checkbox" class="simple_form" />多重弧
					<input type="checkbox" class="simple_form" />帷幔型冕状
					<input type="checkbox" class="simple_form" />放射型冕状
					<input type="checkbox" class="simple_form" />热点型极光
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

<div class="g_6">
	<div class="widget_header">
		<h4 class="widget_header_title wwIcon i_16_forms">查询结果</h4>
	</div>
	<div class="widget_contents noPadding">
		<div class="line_grid">
			<span class="u-tab active">Original</span>
        	<span class="u-tab">Keogram</span>
		</div>
		<div class="line_grid">
			 <div class="images">
           
		           <img src="Images/22/N20031221G030231.jpg" title="拍摄时间：2012-06-13  波段：G 类型：多重弧"
		           >
		           <img src="Images/22/N20031221G030241.jpg">
		           <img src="Images/22/N20031221G030251.jpg " title="拍摄时间：2012-06-13 波段：G 类型：无">
		           <img src="Images/22/N20031221G030301.jpg">
		           <img src="Images/22/N20031221G030311.jpg">
		           <img src="Images/22/N20031221G030321.jpg">
		           <img src="Images/22/N20031221G030331.jpg">
		           <img src="Images/22/N20031221G030341.jpg">
		           <img src="Images/22/N20031221G031041.jpg">
		           <img src="Images/22/N20031221G031051.jpg">
		           <img src="Images/22/N20031221G031101.jpg">
		           <img src="Images/22/N20031221G031111.jpg">
		           <img src="Images/22/N20031221G031121.jpg">
            </div> 
		</div>
	</div>
</div>
 

<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>

<script>  
	$(function(){$("#startdate").datepicker()});
	$(function(){$("#enddate").datepicker()});
</script>  

<form name="importForm" method="POST" action="import" enctype="multipart/form-data">
	<div class="g_6 contents_header">
		<h3 class="i_16_forms tab_label">重组</h3>
	</div>
	
	<div class="g_12 separator"><span></span></div>
	
	<div class="g_12">
		<div class="widget_header">
			<h4 class="widget_header_title wwIcon i_16_forms">重组</h4>
		</div>
		<div class="widget_contents noPadding">
<%-- 		<div class="line_grid">--%>
<%--				<div class="g_3"><span class="label">请选择要重组的文件的开始时间</span></div>--%>
<%--				<div class="g_9">--%>
<%--					<input type="text" class="simple_form" name="startdate" id="startdate"/>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--			<div class="line_grid">--%>
<%--				<div class="g_3"><span class="label">请选择要重组的文件的结束时间</span></div>--%>
<%--				<div class="g_9">--%>
<%--					<input type="text" class="simple_form" name="enddate" id="enddate"/>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--			<div class="line_grid">--%>
<%--				<div class="g_3"><span class="label">确认</span></div>--%>
<%--				<div class="g_9">--%>
<%--					<input type="submit" value="重组" class="simple_buttons" />--%>
<%--				</div>--%>
<%--			</div>--%>

		<div class="line_grid">
			<div class="g_3"><span class="label">请选择要重组的文件</span></div>
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
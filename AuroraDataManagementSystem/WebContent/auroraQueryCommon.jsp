<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>
    
<script>  
	$(function(){$("#startdate").datepicker()});
	$(function(){$("#enddate").datepicker()});
</script>  
        
<div class="line_grid">
	<div class="g_3"><span class="label">开始时间</span></div>
	<div class="g_9">
		<!--<input class="simple_field" type="text" name="startTime"/>-->
		<input class="laydate-icon" onclick="laydate()" name="starttime">
	</div>
</div>
<div class="line_grid">
	<div class="g_3"><span class="label">结束时间</span></div>
	<div class="g_9">
		<input class="laydate-icon" onclick="laydate()" name="endtime">
	</div>
</div>
<div class="line_grid">
	<div class="g_3"><span class="label">波段</span></div>
	<div class="g_9">
		<input type="checkbox" class="simple_form" name="Gband"/>G
		<input type="checkbox" class="simple_form" name="Vband"/>V
		<input type="checkbox" class="simple_form" name="Rband"/>R
	</div>
</div>
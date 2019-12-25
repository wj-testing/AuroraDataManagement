<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>

<link rel="stylesheet" href="CSS/processbar.css" />

<script>
;!function(){
laydate({
   elem: '#demo'
})
}();
</script> 

<div class="g_6 contents_header">
	<h3 class="i_16_forms tab_label">Classification</h3>
</div>
			
<div class="g_12 separator"><span></span></div>

<form name="initializeForm" method="POST" action="initial">
<div class="g_12">
	<div class="widget_header">
		<h4 class="widget_header_title wwIcon i_16_forms">Classification</h4>
	</div>
	<div class="widget_contents noPadding">
		<%@ include file="progressbar.jsp" %>
		
		<div class="line_grid">
			<div class="g_3"><span class="label">Start Time of Data Observe</span></div>
			<div class="g_3">
				<input class="laydate-icon" onclick="laydate()">
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">End Time of Data Observe</span></div>
			<div class="g_3">
				<input class="laydate-icon" onclick="laydate()">
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">Add Another Time Period for Data Acquisition</span></div>
			<div class="g_9">
				<input type="submit" value="Add" class="simple_buttons" />
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">Band</span></div>
			<div class="g_9">
				<input type="checkbox" class="simple_form" name="type" value="1"/>V
				<input type="checkbox" class="simple_form" name="type" value="2"/>G
				<input type="checkbox" class="simple_form" name="type" value="3"/>R
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">The Ratio of Training Set and Test Set</span></div>
			<div class="g_3">
				<input class="simple_field" type="text" name="trainratio"/>
			</div>
			<div class="g_3">
				<input class="simple_field" type="text" name="testratio"/>
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">The Number of Times to Repeat OCE</span></div>
			<div class="g_3">
				<input class="simple_field" type="text" name="repeatnum"/>
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">Next(FE Feature Extraction)</span></div>
			<div class="g_9">
				<a href="extraction.jsp"><input type="submit" value="Next" class="simple_buttons" /></a>
			</div>
		</div>						
	</div>
</div>
</form>
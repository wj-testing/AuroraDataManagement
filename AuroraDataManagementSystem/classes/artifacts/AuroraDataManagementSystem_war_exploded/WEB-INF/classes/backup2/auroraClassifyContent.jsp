<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="gb2312"%>

<link rel="stylesheet" href="CSS/processbar.css" />

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
				 </div>
				 <!-- 五个圆 -->
				 <span>特征提取</span>
				 <span>极光分类</span>
				 <span>统计分析</span>
				 <span>初始化</span>
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">实验编号</span></div>
			<div class="g_9">
				<input class="simple_field" type="text" />
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">开始时间</span></div>
			<div class="g_9">
				<input class="simple_field" type="text" />
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">结束时间</span></div>
			<div class="g_9">
				<input class="simple_field" type="text" />
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">波段</span></div>
			<div class="g_9">
				<input class="simple_field" type="text" />
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">训练集:测试集</span></div>
			<div class="g_4">
				<input class="simple_field" type="text" />
			</div>
			<div class="g_4">
				<input class="simple_field" type="text" />
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">重复试验次数</span></div>
			<div class="g_9">
				<input type="text" class="simple_field spinner1" />
			</div>
		</div>
		<div class="line_grid">
			<div class="g_3"><span class="label">下一步</span></div>
			<div class="g_9">
				<a href="extraction.jsp"><input type="submit" value="下一步" class="simple_buttons" /></a>
			</div>
		</div>						
	</div>
</div>
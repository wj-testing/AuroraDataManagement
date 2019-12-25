# AuroraDataManagement
信息存储管理课程第二组期末项目
小组成员：王晋、张丽、罗亦翔
项目名称：案例二极光数据管理系统

开发环境：
jdk：1.8
tomcat：apache-tomcat-9.0.0
mongodb：4.0
Intellij Idea 2019.1.3

运行环境：
jdk：1.8
tomcat：apache-tomcat-9.0.0
mongodb：4.0
Intellij Idea 2019.1.3
浏览器 ：Microsoft Edge

运行步骤：
1.在Intellij Idea 2019.1.3中打开AuroraDataManagementSystem 
2 在Intellij Idea 2019.1.3 配置 tomcat-9.0.0
3. 找到重新设计过的 MetaAll.json 文件 本地终端中 mongorestore  -d aurora <MetaAll.json的路径> 命令恢复数据库集合
4.打开浏览器，输入http://localhost:8080/AuroraDataManagementSystem/index.jsp访问 即可进行相关操作



注意事项：
数据库里的数据为2003年12月21日到2003年12月31日的极光图片，其中没有27日的图片
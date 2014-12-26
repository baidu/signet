【Signet签章测试系统】
签章测试系统用于敏捷团队进行验收条件管理，配合签章自测流程，可有效提高敏捷团队成熟度
（1）支持RD、QA多角色共同质量保证，有效利用RD自测成果指导测试，提高团队交付效率
（2）Web脑图形式编写和管理验收条件，粒度小，易分发，更适合敏捷流程，提高验收条件准备效率
（3）以签章代表测试执行人，测试可追寻
（4）多维统计，及时发现敏捷团队测试问题

【安装与部署】
签章测试模块是一个可插拔的模块，不包含Story管理功能，因此可以与任何Story管理系统配合使用
包含两个模块：
a)signet-web 
b)signet-extends-api
具体：
（1）Story信息接口实现：使用者需实现signet-extends-api中的接口（主要用于获取story相关信息）
（2）创建数据库，使用signet-web database目录中的create_tables.sql创建数据库
（3）修改signet-web src/main/resource目录中configuration.properties文件中的数据库配置
（4）用signet-web src/main/resource/conf/local中的context.xml替换tomcat的context.xml文件
（5）编译部署signet-web.war到tomcat容器
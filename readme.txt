一、Signet签章测试系统简介
签章测试系统用于敏捷团队进行验收条件管理，配合签章自测流程，可有效提高敏捷团队成熟度
（1）支持RD、QA多角色共同质量保证，有效利用RD自测成果指导测试，提高团队交付效率
（2）Web脑图形式编写和管理验收条件，粒度小，易分发，更适合敏捷流程，提高验收条件准备效率
（3）以签章代表测试执行人，测试可追寻
（4）多维统计，及时发现敏捷团队测试问题

二、安装与部署
2.1 数据库
要求mysql 5.1以上，执行signet-web/database create_tables.sql创建数据库

2.2 模块说明
（1）signet-web Web站点服务模块
（2）signet-extends-api story信息接口，定义外部接口
（3）signet-extends-demo signet-extends-api实现demo

2.2 Story信息扩展
签章测试模块是一个可插拔的模块，不包含Story管理功能，因此可以与任何Story管理系统配合使用
使用者需实现signet-extends-api中的ProjectService和StoryService（主要用于获取story相关信息）
模块signet-extends-demo提供了实现这两个接口的简单Demo

2.3 编译与部署
（1）修改signet-web src/main/resource目录中configuration.properties文件中的数据库配置
（2）修改signet-web src/main/resource目录中configuration.properties文件中签章图片保存位置
（3）在signet模块下 执行mvn install 命令 （jdk1.6）
（4）将signet-web模块的target目录中的signet-web.war部署与tomcat（6及以上版本）下
（5）启动tomcat

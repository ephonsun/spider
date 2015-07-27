1. 使用spring mvc+jsoup的方式构建自动查询并解析获取所需的返回数据
2. 实现一个新的网站查询，只需要实现controller和parser接口，其中controller
用于接收外部访问参数，parser是用于解析特定网站响应数据。
3. 在webapp下，可以有html静态页面导向，用于本地测试用，WEB-INF/pages下则是
返回的jsp页面，用于数据返回
4. 部署： 本地测试使用pom的jetty:run即可启动，默认端口8094，也可以打包成
war形式放在tomcat上运行
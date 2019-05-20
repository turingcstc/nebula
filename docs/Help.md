# Help

# Windows 下开发环境配置

## Intellij IDEA 配置

### 插件安装
* Lombok (必须，否则在 IDE 中因为找不到类的方法显示编译错误)
    - [install help](https://projectlombok.org/setup/intellij)
    - Lombok Requires Annotation Processing.For the lombok plugin to function correctly,please enable it under 
File -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors
    注意，上面这个步骤如果将IDE的编译委托给 gradle，则可以不用设置(见下面说明)，否则需要设置，若不设置会提示编译错误。
    
* MapStruct Support(可选，提供 IDE 编辑支持) [help](http://mapstruct.org/documentation/ide-support/) 

* google-java-format (可选，格式化代码) [help](https://github.com/google/google-java-format) 
    - File -> Settings -> Other Settings -> google-java-format Settings Enable google-java-format
* [Material Theme UI (可选，样式主题)](https://plugins.jetbrains.com/plugin/8006-material-theme-ui) [help](https://www.material-theme.com/docs/introduction/)

* [Save Actions 可选，including "optimize imports", "reformat code", "rearrange code"](https://plugins.jetbrains.com/plugin/7642-save-actions)

### 插件的离线安装
若因为国内的网络无法在IDE中安装可以通过下面网址下载插件后离线安装。
http://plugins.jetbrains.com/idea

### Application “hot restart” with Spring Boot devtools
* 默认 Intellij IDE 当文件保存的时候不会自动编译，要启用这个功能。
    File -> Settings -> Build, Execution, Deployment -> Compiler 启用 Build project automatically
* 当程序运行的时候文件更改后也要允许自动编译。
     Open the Action window :
    - Linux : CTRL+SHIFT+A
    - Mac OSX : SHIFT+COMMAND+A
    - Windows : CTRL+ALT+SHIFT+/
    - Enter Registry... and enable compiler.automake.allow.when.app.running
 
### Import project from a gradle model

Select File | New | Project from Existing Sources from the main menu.
In the dialog that opens, select the directory that contains the project you want to import or a file that contains a Gradle project description (build.gradle). Click OK.
    
### 运行

* 将IDE的编译委托给 gradle.
With this setting annotation processing is automatically configured and you won’t have duplicated classes when mixing IDE and cli builds.
(注意：发现一个奇怪的问题，当使用这种方法时，spring boot devtools 会频繁自己重新启动，虽然委托给 gradle 编译，但IDE还是会频繁生成class到out目录下引起的，暂时不要用这种方法。)
    File -> Settings -> Build, Execution, Deployment -> Gradle -> Runner 
    启用 Delegate build/run actions to gradle
    
* 在项目文件导航面板中选择 NebulaApplication ，右键 Run 'NebulaApplication'。以启动程序主类的方式运行。 
    - 可以在 Run Dashboard 面板中右键选择 NebulaApplication -> Edit configuration 设置 Active profiles 为 dev 或 prod，若不设置默认为 dev  
    
* 另外一种运行方式，在 Gradle 面板中点击 Tasks\application\bootRun 运行。或者从命令终端 Terminal 执行 gradle 的 bootRun 命令。   

### 容器

运行一个 MySQL 容器
docker run --name dbserver -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql  

### 工具

Postman https://www.getpostman.com/
MySQL Workbench 

### 启动一个用于测试的 Authorization Server

- 在 docker 容器中运行 keycloak 应用

    > docker run -d -p 8085:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin jboss/keycloak

- 打开 http://localhost:8085/ 管理员 admin 账号登录。创建 realm 
    - name = demo，
- demo realm 下创建 user
    - user = demouser 
    - password = demouser,
- demo realm 下创建 Client
   - Client ID = myapp
   - Valid Redirect URIs = http://localhost:8080/* 
   - Client Protocol = openid-connect  

- Postman 设置
    - URL : http://localhost:8085/auth/realms/demo/protocol/openid-connect/token
    - Body type : x-www-form-urlencoded
    - Form fields: 
        - client_id:myapp
        - grant_type:password
        - username:demouser
        - password:demouser
- 参考
    - https://www.keycloak.org/docs/latest/getting_started/index.html 
    - https://hub.docker.com/r/jboss/keycloak
    - http://xpam.pl/blog/?p=154
    
    
## 命令行运行

    - profiles : dev or prod 若省略此参数则是dev
    > java -jar nebular.jar --server.port=8888 --spring.profiles.active=prod


http://localhost:8080/h2-console/
JDBC URL : jdbc:h2:mem:NebulaDB


docker 
https://hellokoding.com/spring-boot-restful-api-documentation-with-swagger-and-springfox/


### Using TLS and HTTP/2 in development

- Generate a certificate using
As the certificate is self-signed, your browser will issue a warning, and you will need to ignore it (or import it) in order to access the application.
    |||
    |:---|:--- |
    | keytool | 安装在 jdk bin 目录下 |
    | alias | 别名 |
    | storetype | 密钥库类型 |
    | keyalg | 密钥算法名称 |
    | keysize | 密钥位大小 |
    | keystore | 密钥库名称 |
    | validity | 有效天数 |
 
    > keytool -genkeypair -alias <your-application> -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
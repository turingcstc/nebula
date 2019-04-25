# Intellij IDEA 开发环境配置

## 插件安装
* Lombok (必须，否则无法在 IDE 中编译)
    - [install help](https://projectlombok.org/setup/intellij)
    - Lombok Requires Annotation Processing.For the lombok plugin to function correctly,please enable it under 
File -> Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors

* MapStruct Support(可选，提供 IDE 编辑支持) [help](http://mapstruct.org/documentation/ide-support/) 

## Application “hot restart” with Spring Boot devtools
* 默认 Intellij IDE 当文件保存的时候不会自动编译，要启用这个功能。
    File -> Settings -> Build, Execution, Deployment -> Compiler 启用 Build project automatically
* 当程序运行的时候文件更改后也要允许自动编译。
     Open the Action window :
    - Linux : CTRL+SHIFT+A
    - Mac OSX : SHIFT+COMMAND+A
    - Windows : CTRL+ALT+SHIFT+/
    - Enter Registry... and enable compiler.automake.allow.when.app.running
 
* 将IDE的编译委托给 gradle，虽然不是必须的，但还是推荐使用这种方式运行.
    File -> Settings -> Build, Execution, Deployment -> Gradle -> Runner 
    启用 Delegate build/run actions to gradle
    
## 运行

* 在项目文件导航面板中选择 NebulaApplication ，右键 Run 'NebulaApplication'。以启动程序主类的方式运行。 
    - 可以在 Run Dashboard 面板中右键选择 NebulaApplication -> Edit configuration 设置 Active profiles 为 dev 或 prod，若不设置默认为 dev  
    
* 另外一种运行方式，在 Gradle 面板中点击 Tasks\application\bootRun 运行。或者从命令行执行 gradle 的 bootRun 命令。   


## 容器

运行一个 MySQL 容器
docker run --name dbserver -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql     
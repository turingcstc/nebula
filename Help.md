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
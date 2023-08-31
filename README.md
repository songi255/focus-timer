# Focus Timer Desktop App

prototype version of JavaFX Visual timer desktop application.

It supports windows, mac, linux but tested only with windows. 

## Demo

![demo video](demo.gif)

Features:
- always on top
- full screen
- overlay with opacity
- pomodoro mode
- save settings

## How to run

you need `Java 17+` version to run this app.
please install OpenJDK 17 on [Here](https://docs.aws.amazon.com/corretto/latest/corretto-17-ug/downloads-list.html).

Current version has Jlink issue.
don't `deploy` but `package`.

``` shell
.\mvnw clean package
```

you can see Jar file named with `~~SANPSHOT-jar-with-dependencies.jar` in the `target` folder.

run with `java -jar` command.

```shell
java -jar "yourfile.jar"
```

## Warning

This version is just prototype and planned to refactoring.

But no fatal error exists, you can use this version.
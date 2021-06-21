::防止启动后被杀死进程
set BUILD_ID=dontKillMe

@echo off
setlocal enabledelayedexpansion

::设置端口
set PORT=8084

:: 设置生成路径
set OLD_PATH=C:\Windows\System32\config\systemprofile\AppData\Local\Jenkins\.jenkins\workspace\mes-plus-java\target

:: 设置新的存放路径
set NEW_PATH=C:\out\mes-plus\

:: 设置jar包名
set JAR_NAME=i_mes_plus-0.0.1-SNAPSHOT.jar

::set /p port=Please enter the port ：
for /f "tokens=1-5" %%a in ('netstat -ano ^| find ":%PORT%"') do (
    if "%%e%" == "" (
        set pid=%%d
    ) else (
        set pid=%%e
    )
    echo !pid!
    taskkill /f /pid !pid!
)

::复制文件
XCOPY %OLD_PATH%\%JAR_NAME%  "%NEW_PATH%" /y

::启动jar包
start javaw -jar %NEW_PATH%\%JAR_NAME%
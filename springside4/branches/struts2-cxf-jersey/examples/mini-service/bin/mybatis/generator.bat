@echo off

cd %~dp0/../../
call mvn mybatis-generator:generate
if errorlevel 1 goto end

echo [INFO] ���������ɵ�target/generated-sources/mybatis-generatorĿ¼��.

:end
pause
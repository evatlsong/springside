@echo off

cd %~dp0/../../
call mvn cxf-codegen:wsdl2java
if errorlevel 1 goto end

echo [INFO] ���������ɵ�target/generated-sources/cxfĿ¼��.

:end
pause
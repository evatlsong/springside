@echo off
echo [INFO] ȷ������pathϵͳ������ANT1.7���ϰ汾��binĿ¼.
echo [INFO] ȷ������WebServiceӦ��������.

cd %~dp0
call ant
if errorlevel 1 goto end

echo [INFO] WSDL�ѱ��浽webapp/wsdl/mini-service.wsdl.

:end
pause
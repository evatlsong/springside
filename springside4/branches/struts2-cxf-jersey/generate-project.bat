@echo off
echo [INFO] ȷ������mvn install���btools\maven\archetype�е�mini-web�Ŀģ��

set MVN=mvn
if exist "tools\maven\apache-maven-3.0.2\" set MVN="%cd%\tools\maven\apache-maven-3.0.2\bin\mvn.bat"
echo Maven����Ϊ%MVN%

mkdir generated-projects
cd generated-projects
call mvn archetype:generate -DarchetypeCatalog=local

pause



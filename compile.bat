@echo off
echo ========================================
echo   LIMPIANDO Y COMPILANDO PROYECTO
echo ========================================
echo.

echo [1/3] Limpiando proyecto...
call mvnw clean

echo.
echo [2/3] Compilando proyecto...
call mvnw compile

echo.
echo [3/3] Empaquetando proyecto...
call mvnw package -DskipTests

echo.
echo ========================================
echo   COMPILACION COMPLETADA
echo ========================================
echo.
echo Para ejecutar la aplicacion:
echo   mvnw spring-boot:run
echo.
pause

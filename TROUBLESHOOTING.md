# 🔧 Solución de Problemas - IntelliJ IDEA

## ⚠️ Problema: Lombok no genera código (Error: `cannot find symbol: method builder()`)

### 📋 Descripción del problema

Al intentar compilar el proyecto, aparecen errores como:
```
cannot find symbol: method builder()
cannot find symbol: method getData()
cannot find symbol: method setData()
```

Esto ocurre porque **IntelliJ IDEA no tiene habilitado el procesamiento de anotaciones de Lombok**, por lo que el código generado automáticamente (getters, setters, builders, etc.) no se crea durante la compilación.

---

## ✅ Solución: Habilitar Annotation Processing en IntelliJ IDEA

### **Pasos a seguir:**

1. **Abrir configuración de IntelliJ IDEA:**
   - Ve a: `File` → `Settings` (o presiona `Ctrl + Alt + S`)
   - En macOS: `IntelliJ IDEA` → `Preferences` (o `Cmd + ,`)

2. **Navegar a Annotation Processors:**
   - En el panel izquierdo, ve a:
     ```
     Build, Execution, Deployment
       → Compiler
         → Annotation Processors
     ```

3. **Habilitar el procesamiento de anotaciones:**
   - ✅ Marca la casilla: **"Enable annotation processing"**
   - Click en `Apply`
   - Click en `OK`

4. **Invalidar caché y reiniciar (recomendado):**
   - Ve a: `File` → `Invalidate Caches...`
   - Selecciona: **"Invalidate and Restart"**
   - Espera a que IntelliJ reinicie

5. **Rebuild del proyecto:**
   - Ve a: `Build` → `Rebuild Project`
   - Espera a que termine la compilación

---

## 🎯 Verificación

Después de seguir estos pasos, el proyecto debería compilar sin errores. Los métodos generados por Lombok (como `builder()`, getters, setters) estarán disponibles.

---

## 📁 Nota sobre carpetas de configuración local

Las carpetas `.idea/` y `.mvn/` contienen configuraciones locales de tu IDE y Maven Wrapper. Estas carpetas:

- ✅ **Se generan automáticamente** al abrir el proyecto en IntelliJ IDEA
- ✅ **Son específicas de tu máquina** (rutas locales, preferencias personales)
- ✅ **No se deben subir a Git** (ya están en `.gitignore`)

Si otro desarrollador clona el proyecto, IntelliJ generará estas carpetas automáticamente con su propia configuración.

---

## 🚨 Si el problema persiste

Si después de habilitar Annotation Processing el error continúa:

1. **Verifica que Lombok esté instalado:**
   - Ve a: `File` → `Settings` → `Plugins`
   - Busca "Lombok"
   - Si no está instalado, instálalo y reinicia IntelliJ

2. **Limpia y recompila desde Maven:**
   ```bash
   mvnw clean compile
   ```

3. **Verifica la versión de Lombok en `pom.xml`:**
   ```xml
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.34</version>
   </dependency>
   ```

---

**Última actualización:** Enero 2026

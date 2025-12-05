# Sistema de Gestión de Evidencias

## 📁 Estructura de Carpetas

El proyecto ahora cuenta con un sistema automático de organización de capturas de pantalla (evidencias):

```
test-output/
├── evidencia/              # Carpeta principal de evidencias
│   ├── passed/            # Screenshots de tests exitosos ✅
│   └── failed/            # Screenshots de tests fallidos ❌
├── capturaPantalla/       # Carpeta temporal para capturas durante ejecución
├── screenshots/           # Screenshots de errores capturados por TestListener
└── ExtentReports/         # Reportes HTML generados
```

## 🎯 ¿Cómo Funciona?

### 1. **Durante la Ejecución del Test**
- Los tests toman capturas de pantalla usando:
  - `basePage.capturaPantallaCompleta("nombre")` - Captura toda la pantalla (incluye barra de Windows)
  - `basePage.capturaPantalla("nombre")` - Captura solo el navegador
- Las capturas se guardan temporalmente en `test-output/capturaPantalla/`

### 2. **Al Finalizar el Test**
- El `TestListener` detecta automáticamente si el test **pasó** o **falló**
- **Si el test pasó (✅)**:
  - Todas las capturas se mueven a `evidencia/passed/`
  - Se registra en el reporte Extent con estado SUCCESS
- **Si el test falló (❌)**:
  - Todas las capturas se mueven a `evidencia/failed/`
  - Se toma una captura adicional del error
  - Se registra en el reporte Extent con el stack trace

### 3. **Organización Automática**
- Las capturas mantienen su nombre original con timestamp
- Ejemplo: `t001_Login_20251203_083045.png`
- Se organizan automáticamente sin intervención manual

## 🔧 Configuración Técnica

### GlobalVariables.java
Se agregaron las siguientes constantes:

```java
// Rutas de evidencias organizadas por resultado
public static final String PATH_EVIDENCIA = "...\\test-output\\evidencia\\";
public static final String PATH_EVIDENCIA_PASSED = PATH_EVIDENCIA + "passed\\";
public static final String PATH_EVIDENCIA_FAILED = PATH_EVIDENCIA + "failed\\";
```

### TestListener.java
Se agregó el método `moveCapturesToFolder()`:
- Se ejecuta automáticamente en `onTestSuccess()` y `onTestFailure()`
- Mueve todas las capturas (.png y .jpg) a la carpeta correspondiente
- Crea las carpetas si no existen
- Registra logs de las operaciones realizadas

## 📊 Beneficios

1. **Organización Automática**: No necesitas mover manualmente las capturas
2. **Fácil Análisis**: Puedes revisar rápidamente solo los tests fallidos
3. **Evidencia Clara**: Las capturas exitosas y fallidas están separadas
4. **Trazabilidad**: Cada captura tiene timestamp único
5. **Integración con Extent Reports**: Los reportes HTML incluyen las capturas

## 🚀 Uso en Tests

No se requieren cambios en los tests existentes. Ejemplo:

```java
@Test(priority = 1, description = "Login en BCI Seguros")
public void t001_login() throws Exception {
    // Tomar capturas como siempre
    basePage.capturaPantallaCompleta("t001_Login");
    
    // El TestListener se encarga del resto automáticamente
}
```

## 📝 Notas Importantes

- Las carpetas se crean automáticamente en el primer test
- `capturaPantalla/` queda vacía después de cada test (capturas se mueven)
- Los nombres de archivo mantienen el formato: `{nombre}_{timestamp}.{extensión}`
- Compatible con reportes Extent Reports existentes

## 🔍 Verificación

Para verificar que funciona correctamente:

1. Ejecuta un test que pase: `mvn test -Dtest=NombreTest`
2. Verifica que las capturas estén en `test-output/evidencia/passed/`
3. Fuerza un test a fallar
4. Verifica que las capturas estén en `test-output/evidencia/failed/`

---

**Implementado**: Diciembre 2025  
**Compatible con**: Selenium 4.20.0, TestNG 7.10.2, ExtentReports 5.0.9

# Proyecto Base Automatización

Proyecto base para automatizaciones web con Selenium WebDriver y TestNG.

## Estructura del Proyecto

```
Proyecto_Base_Automatizacion/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── base/                  # Clases base reutilizables
│   │   │   │   ├── BasePage.java      # Page Object base con métodos comunes
│   │   │   │   ├── GlobalVariables.java   # Variables globales del proyecto
│   │   │   │   ├── ExtentReportManager.java  # Gestor de reportes
│   │   │   │   └── TestListener.java  # Listener para TestNG
│   │   │   └── exceptions/            # Excepciones personalizadas
│   │   │       └── ConfirmaProceso.java
│   │   └── resources/
│   │       └── testdata/              # Archivos JSON con datos de prueba
│   │           └── datos_ejemplo.json
│   └── test/
│       ├── java/
│       │   └── tests/                 # Clases de tests
│       │       └── EjemploTest.java
│       └── resources/
│           └── testng.xml             # Configuración de TestNG
├── test-output/                       # Resultados de ejecución
│   ├── capturaPantalla/               # Screenshots automáticas
│   ├── archivoDescarga/               # Archivos descargados en tests
│   └── ExtentReports/                 # Reportes HTML generados
└── pom.xml                            # Dependencias Maven
```

## Requisitos

- Java JDK 21
- Maven 3.6+
- Chrome Browser (última versión)

## Configuración Inicial

1. **Modificar variables globales**: Edita `src/main/java/base/GlobalVariables.java`
   - Cambia `BASE_URL` a la URL de tu aplicación
   - Ajusta los tiempos de espera según necesites

2. **Actualizar información del reporte**: Edita `src/main/java/base/ExtentReportManager.java`
   - Cambia el nombre del ingeniero de pruebas
   - Ajusta el ambiente si es necesario

3. **Crear datos de prueba**: Agrega archivos JSON en `src/main/resources/testdata/`

## Cómo Usar

### Crear un nuevo test

1. Crea una clase en `src/test/java/tests/`
2. Extiende o usa `BasePage` para acceder a métodos comunes
3. Agrega el test a `testng.xml`

Ejemplo:
```java
package tests;

import org.testng.annotations.Test;
import base.BasePage;
import base.GlobalVariables;

public class MiNuevoTest {
    
    @Test
    public void testEjemplo() {
        WebDriver driver = BasePage.setupChrome(
            GlobalVariables.PATH_DESCARGA, 
            GlobalVariables.PATH_CAPTURA
        );
        BasePage page = new BasePage(driver);
        page.llamarUrl("https://miapp.com");
        // Tu código aquí
        page.driverQuit();
    }
}
```

### Ejecutar tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar con un archivo testng.xml específico
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# Ejecutar y saltar la compilación
mvn -DskipTests=false test

# Limpiar y ejecutar
mvn clean test
```

## Métodos Principales de BasePage

### Navegación
- `llamarUrl(String url)` - Abre una URL
- `driverClose()` - Cierra ventana actual
- `driverQuit()` - Cierra el navegador completamente

### Interacción con Elementos
- `click(By locator)` - Click con reintentos automáticos
- `insertarDatos(String texto, By locator)` - Ingresa texto en un campo
- `selecSelect(By locator, String valor)` - Selecciona opción de dropdown
- `dobleClick(By locator)` - Doble click
- `selecRadioButton(By locator)` - Selecciona radio button
- `selecCheckBox(By locator)` - Selecciona checkbox

### Esperas
- `implicitWait()` - Espera implícita general
- `pausaPorElementoVisible(By locator)` - Espera hasta que elemento sea visible
- `pausaPorElementoClickeable(By locator)` - Espera hasta que elemento sea clickeable
- `pausaFijaSeg(int segundos)` - Pausa fija en segundos
- `waitLoading(By locator)` - Espera hasta que loader desaparezca

### Validaciones
- `elementoVisible(By locator)` - Verifica si elemento es visible
- `obtenerTextoTxt(By locator)` - Obtiene valor de un campo

### Capturas
- `capturaPantalla(String nombre)` - Captura screenshot del navegador
- `capturaPantallaCompleta(String nombre)` - Captura screenshot de toda la pantalla

### Datos de Prueba
- `obtenerJson(String archivo, String objeto, String clave)` - Lee datos de archivos JSON

## Reportes

Los reportes se generan automáticamente en `test-output/ExtentReports/` después de ejecutar los tests.
Abre el archivo HTML generado en tu navegador para ver los resultados detallados.

## Personalización

### Agregar nuevos métodos base
Edita `src/main/java/base/BasePage.java` y agrega tus métodos reutilizables.

### Agregar nuevas excepciones
Crea nuevas clases en `src/main/java/exceptions/` siguiendo el patrón de `ConfirmaProceso.java`.

### Modificar configuración de TestNG
Edita `src/test/resources/testng.xml` para:
- Agregar/quitar tests
- Configurar ejecución paralela
- Agregar grupos de tests

## Buenas Prácticas

1. **Page Object Model**: Crea una clase Page por cada página de la aplicación
2. **Reutilización**: Usa los métodos de `BasePage` en lugar de código duplicado
3. **Datos externos**: Mantén los datos de prueba en archivos JSON
4. **Capturas**: Captura pantallas en puntos clave del test
5. **Esperas explícitas**: Usa esperas explícitas en lugar de `Thread.sleep()`
6. **Nombres descriptivos**: Usa nombres claros para tests y métodos

## Troubleshooting

### Error: WebDriver no encontrado
- Verifica que Chrome esté instalado
- WebDriverManager descarga automáticamente el driver correcto

### Error: Tests no se ejecutan
- Verifica que la clase esté registrada en `testng.xml`
- Compila el proyecto: `mvn clean compile`

### Error: No se generan reportes
- Verifica que `TestListener` esté configurado en `testng.xml`
- Revisa que la carpeta `test-output/ExtentReports/` exista

## Contacto

Para dudas o sugerencias, contacta al equipo de automatización.

---
**Versión**: 1.0  
**Última actualización**: Diciembre 2025

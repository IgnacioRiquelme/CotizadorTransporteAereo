# Guía Rápida de Inicio

## 🚀 Inicio Rápido (5 minutos)

### 1. Configuración Inicial

**Edita estos archivos primero:**

```
src/main/java/base/GlobalVariables.java
```
- Cambia `BASE_URL` a la URL de tu aplicación

```
src/main/java/base/ExtentReportManager.java
```
- Cambia `"Tu Nombre"` por tu nombre en la línea del ingeniero de pruebas

### 2. Crear tu Primer Page Object

Copia la plantilla:
```bash
# Duplica el archivo PageTemplate.java
cp src/main/java/pages/PageTemplate.java src/main/java/pages/LoginPage.java
```

Edita `LoginPage.java`:
```java
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import base.BasePage;

public class LoginPage extends BasePage {
    
    // Define tus locators
    private By txtUsuario = By.id("username");
    private By txtPassword = By.id("password");
    private By btnLogin = By.cssSelector("button[type='submit']");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void login(String usuario, String password) {
        insertarDatos(usuario, txtUsuario);
        insertarDatos(password, txtPassword);
        click(btnLogin);
    }
}
```

### 3. Crear tu Primer Test

Crea `src/test/java/tests/LoginTest.java`:
```java
package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BasePage;
import base.GlobalVariables;
import pages.LoginPage;

public class LoginTest {
    
    private WebDriver driver;
    private LoginPage loginPage;
    
    @BeforeMethod
    public void setUp() {
        driver = BasePage.setupChrome(
            GlobalVariables.PATH_DESCARGA, 
            GlobalVariables.PATH_CAPTURA
        );
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }
    
    @Test(description = "Login exitoso con credenciales válidas")
    public void testLoginExitoso() {
        loginPage.llamarUrl(GlobalVariables.BASE_URL);
        loginPage.login("usuario@test.com", "password123");
        loginPage.pausaFijaSeg(2);
        loginPage.capturaPantalla("login_exitoso");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            loginPage.driverQuit();
        }
    }
}
```

### 4. Registrar el Test en TestNG

Edita `src/test/resources/testng.xml`:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Suite de Automatización" verbose="1">
    <listeners>
        <listener class-name="base.TestListener"/>
    </listeners>
    
    <test name="Tests de Login" preserve-order="true">
        <classes>
            <class name="tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

### 5. Ejecutar

```bash
# Compilar
mvn clean compile

# Ejecutar tests
mvn test
```

### 6. Ver Reportes

Abre el archivo generado:
```
test-output/ExtentReports/TestReport_[fecha].html
```

---

## 📋 Checklist para Nuevo Proyecto

- [ ] Cambiar `BASE_URL` en `GlobalVariables.java`
- [ ] Actualizar nombre del ingeniero en `ExtentReportManager.java`
- [ ] Crear archivo JSON con datos de prueba en `src/main/resources/testdata/`
- [ ] Crear Page Objects en `src/main/java/pages/`
- [ ] Crear Tests en `src/test/java/tests/`
- [ ] Actualizar `testng.xml` con las nuevas clases de test
- [ ] Ejecutar `mvn test` para verificar
- [ ] Revisar reporte en `test-output/ExtentReports/`

---

## 🔧 Comandos Maven Útiles

```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar sin compilar tests
mvn -DskipTests package

# Ejecutar un test específico
mvn test -Dtest=LoginTest

# Ejecutar con un testng.xml específico
mvn test -DsuiteXmlFile=src/test/resources/testng.xml

# Ver dependencias
mvn dependency:tree

# Actualizar dependencias
mvn versions:display-dependency-updates
```

---

## 💡 Tips Rápidos

### Obtener datos de JSON
```java
String usuario = basePage.obtenerJson("datos_ejemplo", "DatosEjemplo", "usuario");
```

### Esperar un elemento
```java
basePage.pausaPorElementoVisible(miLocator);
basePage.pausaPorElementoClickeable(miBoton);
```

### Capturar pantalla
```java
basePage.capturaPantalla("nombre_screenshot");
basePage.capturaPantallaCompleta("pantalla_completa");
```

### Manejo de combos
```java
basePage.selecSelect(miCombo, "valor");
// o
basePage.seleccionar(botonCombo, opcionDelCombo);
```

### Validaciones
```java
Assert.assertTrue(basePage.elementoVisible(miElemento), "El elemento debe ser visible");
String texto = basePage.obtenerTextoTxt(miCampo);
Assert.assertEquals(texto, "esperado");
```

---

## ❓ Problemas Comunes

### "No se encuentra ChromeDriver"
✅ El proyecto usa WebDriverManager - se descarga automáticamente

### "Tests no se ejecutan"
✅ Verifica que la clase esté en `testng.xml`

### "No se genera reporte"
✅ Verifica que `TestListener` esté en `testng.xml`

### "Timeout en elementos"
✅ Aumenta `PAUSA_GENERAL` en `GlobalVariables.java`

---

**¡Listo para automatizar! 🎉**

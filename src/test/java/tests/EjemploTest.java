package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BasePage;
import base.GlobalVariables;
import pages.LoginPage;

/**
 * Clase de ejemplo para tests automatizados.
 * Modifica esta clase según las necesidades del proyecto.
 */
public class EjemploTest {

    private WebDriver driver;
    private BasePage basePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        // Configuración del WebDriver antes de cada test
        driver = BasePage.setupChrome(
            GlobalVariables.PATH_DESCARGA, 
            GlobalVariables.PATH_CAPTURA
        );
        driver.manage().window().maximize();
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Test de login - Ingresar a oficina virtual BCI Seguros")
    public void testLogin() {
        // Obtener datos del JSON
        String url = basePage.obtenerJson("datos_ejemplo", "LoginData", "url");
        String rut = basePage.obtenerJson("datos_ejemplo", "LoginData", "rut");
        String contraseña = basePage.obtenerJson("datos_ejemplo", "LoginData", "contraseña");

        // Navegar a la página de login
        loginPage.navegarAPaginaLogin(url);

        // Realizar login
        loginPage.realizarLogin(rut, contraseña);

        // Pausa para verificar ingreso
        basePage.pausaFijaSeg(5);

        // Captura de pantalla
        basePage.capturaPantalla("login_captura");

        // Assertion básica: verificar que no haya errores (ajustar según la página)
        String titulo = driver.getTitle();
        Assert.assertNotNull(titulo, "El título no debe ser nulo");
    }

    @Test(description = "Test de ejemplo - Interacción con elementos", enabled = false)
    public void testInteraccionElementos() {
        // MODIFICAR: Este es un ejemplo deshabilitado (enabled = false)
        // Habilítalo y ajusta los selectores según tu proyecto
        basePage.llamarUrl(GlobalVariables.BASE_URL);
        
        // Ejemplo de interacciones
        By botonEjemplo = By.id("miBoton");
        By campoTexto = By.name("usuario");
        
        basePage.pausaPorElementoVisible(campoTexto);
        basePage.insertarDatos("usuario_ejemplo", campoTexto);
        basePage.click(botonEjemplo);
        
        basePage.capturaPantalla("test_interaccion");
    }

    @AfterMethod
    public void tearDown() {
        // Cerrar el navegador después de cada test
        if (driver != null) {
            basePage.driverQuit();
        }
    }
}

package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BasePage;
import base.GlobalVariables;
import pages.CotizadorTransporteAereoPage;

/**
 * Test para automatización de Cotizador de Transporte Aéreo
 */
public class CotizadorTransporteAereoTest {

    private WebDriver driver;
    private BasePage basePage;
    private CotizadorTransporteAereoPage cotizadorPage;

    @BeforeMethod
    public void setUp() {
        // Configuración del WebDriver antes de cada test
        driver = BasePage.setupChrome(
            GlobalVariables.PATH_DESCARGA,
            GlobalVariables.PATH_CAPTURA
        );
        driver.manage().window().maximize();
        basePage = new BasePage(driver);
        cotizadorPage = new CotizadorTransporteAereoPage(driver);
    }

    @Test(description = "Navegación completa al Cotizador de Transporte Aéreo")
    public void testNavegacionCotizadorTransporteAereo() {
        // Navegar al sitio
        System.out.println("\n---> Ingresamos a la página: https://oficinavirtual.bciseguros.cl/");
        cotizadorPage.navegarAlSitio();

        // Obtener credenciales del JSON
        String rut = basePage.obtenerJson("datos_ejemplo", "LoginData", "rut");
        String password = basePage.obtenerJson("datos_ejemplo", "LoginData", "contraseña");

        // Realizar login
        System.out.println("---> Ingresamos las credenciales");
        // Llenar credenciales sin enviar para tomar evidencia
        cotizadorPage.ingresarCredencialesSinEnviar(rut, password);
        // Pequeña espera para que los campos queden visibles en la captura
        basePage.pausaFijaSeg(2);
        // Captura completa con barra de Windows antes de enviar el login
        basePage.capturaPantallaCompleta("t001_Login");
        // Enviar login
        cotizadorPage.enviarLogin();

        // Verificar que se logueó
        Assert.assertTrue(cotizadorPage.isPaginaCargada(), "La página no se cargó después del login");
        System.out.println("---> Menú Inicio");

        // Pausa de 2 segundos para que cargue completamente la página de inicio
        basePage.pausaFijaSeg(2);

        // Capturar pantalla de la página de inicio antes de desplegar el menú
        basePage.capturaPantallaCompleta("t002_Inicio");

        // Desplegar el menú lateral
        System.out.println("---> Ingresamos al Menú lateral");
        cotizadorPage.desplegarMenuLateral();

        // Pausa de 1 segundo para que se despliegue el menú
        basePage.pausaFijaSeg(1);

        // Capturar pantalla completa del sistema con el menú desplegado y Cotizadores visible (ANTES del click)
        basePage.capturaPantallaCompleta("t003_MenuLateral");

        // Seleccionar Cotizadores
        System.out.println("---> Seleccionamos Cotizadores");
        cotizadorPage.seleccionarCotizadores();

        // Pausa para que cargue la página de cotizadores
        basePage.pausaFijaSeg(3);

        // Capturar pantalla después de seleccionar Cotizadores
        basePage.capturaPantallaCompleta("t004_Cotizadores");

        // Seleccionar Transporte para desplegar submenu
        System.out.println("---> Seleccionamos Transporte");
        cotizadorPage.seleccionarTransporte();

        // Capturar pantalla después de seleccionar Transporte
        basePage.capturaPantallaCompleta("t005_TransporteSeleccionado");

        // Aplicar hover sobre Transporte Aéreo
        System.out.println("---> Aplicamos hover sobre Transporte Aéreo");
        cotizadorPage.hoverTransporteAereo();

        // Capturar pantalla con Transporte Aéreo en hover
        basePage.capturaPantallaCompleta("t006_TransporteAereoHover");

        // Hacer clic en Transporte Aéreo para ingresar
        System.out.println("---> Ingresamos a Transporte Aéreo");
        cotizadorPage.ingresarTransporteAereo();

        // Pausa de 2 segundos para que cargue completamente la página
        basePage.pausaFijaSeg(2);

        // Capturar pantalla después de ingresar a Transporte Aéreo
        basePage.capturaPantallaCompleta("t007_TransporteAereo");

        System.out.println("---> Finaliza Test\n");
    }

    @AfterMethod
    public void tearDown() {
        // Cerrar el navegador después de cada test
        if (driver != null) {
            basePage.driverQuit();
        }
    }
}
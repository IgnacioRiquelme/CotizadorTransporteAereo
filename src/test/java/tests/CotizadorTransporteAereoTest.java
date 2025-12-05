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
        String rutContratante = basePage.obtenerJson("datos_ejemplo", "CotizacionData", "rutContratante");

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

        // Pausa de 3 segundos para que cargue completamente la página
        basePage.pausaFijaSeg(3);

        // Verificar que se redirigió correctamente
        String currentUrl = driver.getCurrentUrl();
        System.out.println("URL después del ingreso: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("TPC=8") || currentUrl.contains("transporteaereo"), "No se redirigió a la página de Transporte Aéreo");

        // Capturar pantalla después de ingresar a Transporte Aéreo
        basePage.capturaPantallaCompleta("t007_TransporteAereo");

        // Seleccionar corredor BCI Seguros Generales
        System.out.println("---> Seleccionamos corredor BCI Seguros Generales");
        cotizadorPage.seleccionarCorredor("99.147.000-K");

        // Capturar pantalla después de seleccionar corredor
        basePage.capturaPantallaCompleta("t008_CorredorSeleccionado");

        // Seleccionar sucursal CASA MATRIZ
        System.out.println("---> Seleccionamos sucursal CASA MATRIZ");
        cotizadorPage.seleccionarSucursal("M");

        // Capturar pantalla después de seleccionar sucursal
        basePage.capturaPantallaCompleta("t009_SucursalSeleccionada");

        // Ingresar RUT del contratante
        System.out.println("---> Ingresamos RUT del contratante: " + rutContratante);
        cotizadorPage.ingresarRutContratante(rutContratante);

        // Hacer clic en Siguiente y esperar carga de datos
        System.out.println("---> Damos clic en Siguiente y esperamos carga de datos");
        cotizadorPage.clickSiguiente();

        // Capturar pantalla después de clic en Siguiente
        basePage.capturaPantallaCompleta("t010_Siguiente");

        // Capturar pantalla antes de seleccionar opciones
        basePage.capturaPantallaCompleta("t010b_AntesOpciones");

        // Seleccionar opciones en la nueva página
        System.out.println("---> Seleccionamos Viaje Especifico");
        cotizadorPage.seleccionarViajeEspecifico();

        System.out.println("---> Seleccionamos Moneda UF");
        cotizadorPage.seleccionarMonedaUF();

        System.out.println("---> Seleccionamos Tipo de Viaje Nacional");
        cotizadorPage.seleccionarViajeNacional();

        // Capturar pantalla después de seleccionar opciones
        basePage.capturaPantallaCompleta("t011_OpcionesSeleccionadas");

        System.out.println("---> Ingresamos tipos de carga: Alimentos De Mascotas y Electrónica");
        cotizadorPage.ingresarTiposCarga();

        System.out.println("---> Agregamos tipo de carga");
        cotizadorPage.agregarTipoCarga();

        // Capturar pantalla después de agregar tipo de carga
        basePage.capturaPantallaCompleta("t012_TipoCargaAgregado");

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
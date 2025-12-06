package tests;

import org.openqa.selenium.JavascriptExecutor;
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
    public com.aventstack.extentreports.ExtentTest extentTest;

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
        extentTest = base.TestListener.getExtentTest();
        // Navegar al sitio
        base.Log.info("---> Ingresamos a la página: https://oficinavirtual.bciseguros.cl/");
        cotizadorPage.navegarAlSitio();

        // Obtener credenciales del JSON
        String rut = basePage.obtenerJson("datos_ejemplo", "LoginData", "rut");
        String password = basePage.obtenerJson("datos_ejemplo", "LoginData", "contraseña");
        String rutContratante = basePage.obtenerJson("datos_ejemplo", "CotizacionData", "rutContratante");

        // Realizar login
        base.Log.info("---> Ingresamos las credenciales");
        // Llenar credenciales sin enviar para tomar evidencia
        cotizadorPage.ingresarCredencialesSinEnviar(rut, password);
        // Pequeña espera para que los campos queden visibles en la captura
        basePage.pausaFijaSeg(2);
        // Captura completa con barra de Windows antes de enviar el login
        basePage.capturaPantallaCompleta("t001_Login");
        extentTest.addScreenCaptureFromPath("../capturas/t001_Login.png");
        // Enviar login
        cotizadorPage.enviarLogin();

        // Verificar que se logueó
        Assert.assertTrue(cotizadorPage.isPaginaCargada(), "La página no se cargó después del login");
        base.Log.info("---> Menú Inicio");

        // Pausa de 2 segundos para que cargue completamente la página de inicio
        basePage.pausaFijaSeg(2);

        // Capturar pantalla de la página de inicio antes de desplegar el menú
        basePage.capturaPantallaCompleta("t002_Inicio");
        extentTest.addScreenCaptureFromPath("../capturas/t002_Inicio.png");

        // Desplegar el menú lateral
        base.Log.info("---> Ingresamos al Menú lateral");
        cotizadorPage.desplegarMenuLateral();

        // Pausa de 1 segundo para que se despliegue el menú
        basePage.pausaFijaSeg(1);

        // Capturar pantalla completa del sistema con el menú desplegado y Cotizadores visible (ANTES del click)
        basePage.capturaPantallaCompleta("t003_MenuLateral");
        extentTest.addScreenCaptureFromPath("../capturas/t003_MenuLateral.png");

        // Seleccionar Cotizadores
        base.Log.info("---> Seleccionamos Cotizadores");
        cotizadorPage.seleccionarCotizadores();

        // Pausa para que cargue la página de cotizadores
        basePage.pausaFijaSeg(3);

        // Seleccionar Transporte para desplegar submenu
        base.Log.info("---> Seleccionamos Transporte");
        cotizadorPage.seleccionarTransporte();

        // Aplicar hover sobre Transporte Aéreo
        base.Log.info("---> Aplicamos hover sobre Transporte Aéreo");
        cotizadorPage.hoverTransporteAereo();

        // Capturar pantalla con Transporte Aéreo en hover (renombrada a t004_TransporteAereo)
        basePage.capturaPantallaCompleta("t004_TransporteAereo");
        extentTest.addScreenCaptureFromPath("../capturas/t004_TransporteAereo.png");

        // Hacer clic en Transporte Aéreo para ingresar
        base.Log.info("---> Ingresamos a Transporte Aéreo");
        cotizadorPage.ingresarTransporteAereo();

        // Pausa de 3 segundos para que cargue completamente la página
        basePage.pausaFijaSeg(3);

        // Verificar que se redirigió correctamente
        String currentUrl = driver.getCurrentUrl();
        base.Log.info("URL después del ingreso: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("TPC=8") || currentUrl.contains("transporteaereo"), "No se redirigió a la página de Transporte Aéreo");

        // Seleccionar corredor BCI Seguros Generales
        base.Log.info("---> Seleccionamos corredor BCI Seguros Generales");
        cotizadorPage.seleccionarCorredor("99.147.000-K");

        // Seleccionar sucursal CASA MATRIZ
        base.Log.info("---> Seleccionamos sucursal CASA MATRIZ");
        cotizadorPage.seleccionarSucursal("M");

        // Ingresar RUT del contratante
        base.Log.info("---> Ingresamos RUT del contratante: " + rutContratante);
        cotizadorPage.ingresarRutContratante(rutContratante);

        // Capturar pantalla ANTES de hacer clic en Siguiente (renombrada a t005_Datos)
        basePage.capturaPantallaCompleta("t005_Datos");
        extentTest.addScreenCaptureFromPath("../capturas/t005_Datos.png");

        // Hacer clic en Siguiente y esperar carga de datos
        base.Log.info("---> Damos clic en Siguiente y esperamos carga de datos");
        cotizadorPage.clickSiguiente();

        // Seleccionar opciones en la nueva página
        base.Log.info("---> Seleccionamos Viaje Especifico");
        cotizadorPage.seleccionarViajeEspecifico();

        // Aplicar zoom out al 67% después de seleccionar Viaje Específico
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom = '0.67'");
        basePage.pausaFijaMs(500);
        base.Log.info("---> Zoom out aplicado al 67%");

        base.Log.info("---> Seleccionamos Moneda UF");
        cotizadorPage.seleccionarMonedaUF();

        base.Log.info("---> Seleccionamos Tipo de Viaje Nacional");
        cotizadorPage.seleccionarViajeNacional();

        base.Log.info("---> Ingresamos tipos de carga: Alimentos De Mascotas y Electrónica");
        cotizadorPage.ingresarTiposCarga();

        base.Log.info("---> Agregamos tipo de carga");
        cotizadorPage.agregarTipoCarga();

        // Seleccionar y agregar primer embalaje: CAJAS DE MADERA
        base.Log.info("---> Seleccionamos embalaje: CAJAS DE MADERA");
        cotizadorPage.seleccionarEmbalaje("CAJAS DE MADERA");

        base.Log.info("---> Agregamos embalaje: CAJAS DE MADERA");
        cotizadorPage.agregarEmbalaje();

        // Seleccionar y agregar segundo embalaje: BOLSAS
        base.Log.info("---> Seleccionamos embalaje: BOLSAS");
        cotizadorPage.seleccionarEmbalaje("BOLSAS");

        base.Log.info("---> Agregamos embalaje: BOLSAS");
        cotizadorPage.agregarEmbalaje();

        // Obtener datos del viaje del JSON
        String lineaAerea = basePage.obtenerJson("datos_ejemplo", "ViajeData", "lineaAerea");
        String ciudadOrigen = basePage.obtenerJson("datos_ejemplo", "ViajeData", "ciudadOrigen");
        String ciudadDestino = basePage.obtenerJson("datos_ejemplo", "ViajeData", "ciudadDestino");
        String montoAsegurado = basePage.obtenerJson("datos_ejemplo", "ViajeData", "montoAsegurado");
        String numeroFactura = basePage.obtenerJson("datos_ejemplo", "ViajeData", "numeroFactura");
        String numeroReferencia = basePage.obtenerJson("datos_ejemplo", "ViajeData", "numeroReferencia");

        // Seleccionar línea aérea
        base.Log.info("---> Seleccionamos línea aérea: " + lineaAerea);
        cotizadorPage.seleccionarLineaAerea(lineaAerea);

        // Llenar datos del viaje
        base.Log.info("---> Seleccionamos ciudad origen: " + ciudadOrigen);
        cotizadorPage.seleccionarCiudadOrigen(ciudadOrigen);

        base.Log.info("---> Seleccionamos ciudad destino: " + ciudadDestino);
        cotizadorPage.seleccionarCiudadDestino(ciudadDestino);

        base.Log.info("---> Ingresamos monto asegurado: " + montoAsegurado);
        cotizadorPage.ingresarMontoAsegurado(montoAsegurado);

        base.Log.info("---> Ingresamos números de factura/guía: " + numeroFactura);
        cotizadorPage.ingresarNumerosFactura(numeroFactura);

        base.Log.info("---> Ingresamos números de referencia: " + numeroReferencia);
        cotizadorPage.ingresarNumerosReferencia(numeroReferencia);

        base.Log.info("---> Marcamos las declaraciones requeridas");
        cotizadorPage.marcarDeclaraciones();

        // Capturar pantalla antes de hacer clic en Siguiente (renombrada a t006_Items)
        basePage.capturaPantallaCompleta("t006_Items");
        extentTest.addScreenCaptureFromPath("../capturas/t006_Items.png");

        base.Log.info("---> Clic en Siguiente del viaje");
        cotizadorPage.clickSiguienteViaje();

        // Restaurar zoom a 100% después del botón Siguiente
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom = '1.0'");
        basePage.pausaFijaMs(500);
        base.Log.info("---> Zoom restaurado a 100%");

        // Capturar pantalla después de hacer clic en Siguiente (renombrada a t007_Cotizar)
        basePage.capturaPantallaCompleta("t007_Cotizar");
        extentTest.addScreenCaptureFromPath("../capturas/t007_Cotizar.png");

        base.Log.info("---> Clic en Siguiente final (lnkSiguienteSus)");
        cotizadorPage.clickSiguienteSus();

        // Captura final (tarificación) cuando cargue la página de tarificación y se finalice el test (renombrada a t008_Tarificacion)
        basePage.capturaPantalla("t008_Tarificacion");
        extentTest.addScreenCaptureFromPath("../capturas/t008_Tarificacion.jpg");

        base.Log.info("---> Finaliza Test\n");
    }

    @AfterMethod
    public void tearDown() {
        // Control opcional para mantener el navegador abierto durante depuración
        String keepOpen = System.getenv("KEEP_BROWSER_OPEN");
        if (keepOpen != null && keepOpen.equalsIgnoreCase("true")) {
            extentTest.info("KEEP_BROWSER_OPEN=true -> se mantiene el navegador abierto para depuración");
            return;
        }

        // Cerrar el navegador después de cada test (por defecto)
        if (driver != null) {
            basePage.driverQuit();
        }
    }
}
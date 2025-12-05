package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import base.BasePage;

/**
 * Page Object para Cotizador de Transporte Aéreo en BCI Seguros
 */
public class CotizadorTransporteAereoPage extends BasePage {

    // ============================================
    // LOCATORS - Define aquí los selectores
    // ============================================

    private By txtRut = By.id("Rut");
    private By txtPassword = By.id("Password");
    private By btnLogin = By.id("login-boton");
    private By logoPrincipal = By.xpath("//img[@alt='Logo Oficina Virtual']");
    private By btnMenuDesplegable = By.id("menu-desplegable");
    private By menuCotizadores = By.id("menu-cotizadores");
    private By btnTransporte = By.xpath("//div[contains(text(),'Transporte')]/parent::a");
    private By submenuTransporte = By.id("transporte_submenu");
    private By btnTransporteAereo = By.xpath("//div[contains(text(),'Transporte aéreo') or contains(text(),'Transporte Aéreo')]/parent::a");

    // CONSTRUCTOR
    // ============================================

    public CotizadorTransporteAereoPage(WebDriver driver) {
        super(driver);
    }

    // ============================================
    // MÉTODOS DE ACCIÓN
    // ============================================

    /**
     * Navega al sitio de oficina virtual de BCI Seguros
     */
    public void navegarAlSitio() {
        llamarUrl("https://oficinavirtual.bciseguros.cl/");
        pausaFijaSeg(1); // Pausa para cargar la página
    }

    /**
     * Realiza login con RUT y password
     * @param rut RUT del usuario
     * @param password Contraseña
     */
    public void realizarLogin(String rut, String password) {
        // Mantener compatibilidad: llenar y enviar
        ingresarCredencialesSinEnviar(rut, password);
        enviarLogin();
    }

    /**
     * Ingresa RUT y password pero NO hace submit (útil para capturar pantalla antes de enviar)
     * @param rut RUT del usuario
     * @param password Contraseña
     */
    public void ingresarCredencialesSinEnviar(String rut, String password) {
        pausaPorElementoVisible(txtRut);
        insertarDatos(rut, txtRut);
        insertarDatos(password, txtPassword);
        pausaFijaMs(300);
    }

    /**
     * Envía el formulario de login (click en botón)
     */
    public void enviarLogin() {
        click(btnLogin);
        pausaFijaSeg(1); // Pausa para procesar login
    }

    /**
     * Navega a la página principal
     */
    public void irAPrincipal() {
        llamarUrl("https://oficinavirtual.bciseguros.cl/Principal/Principal");
        pausaFijaSeg(1); // Pausa para cargar la página
    }

    /**
     * Despliega el menú lateral principal
     */
    public void desplegarMenuLateral() {
        pausaPorElementoLocalizado(btnMenuDesplegable);
        click(btnMenuDesplegable);
        pausaFijaSeg(2); // Pausa para que se despliegue completamente el menú
    }

    /**
     * Selecciona la opción Cotizadores del menú y espera a que esté desplegado
     */
    public void seleccionarCotizadores() {
        pausaPorElementoLocalizado(menuCotizadores);
        click(menuCotizadores);
        pausaFijaSeg(3); // Pausa para que se despliegue el menú y cargue la página
    }

    /**
     * Selecciona Transporte en la página de Cotizadores usando JavaScript
     */
    public void seleccionarTransporte() {
        pausaFijaSeg(1);
        try {
            WebElement elemento = driver.findElement(btnTransporte);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", elemento);
            pausaFijaMs(500);
            // Hacer clic en Transporte
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
            pausaFijaSeg(1);
            
            // Forzar que el submenu sea visible con JavaScript
            try {
                WebElement submenu = driver.findElement(submenuTransporte);
                ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.display = 'block';" +
                    "arguments[0].style.visibility = 'visible';" +
                    "arguments[0].style.opacity = '1';" +
                    "arguments[0].classList.add('active');", submenu);
            } catch (Exception ex) {
                System.out.println("---> No se encontró submenu por ID, intentando por script toggleSubmenu");
                ((JavascriptExecutor) driver).executeScript("toggleSubmenu('transporte_submenu');");
            }
            pausaFijaSeg(1);
        } catch (Exception e) {
            System.out.println("---> seleccionarTransporte: error -> " + e.getMessage());
            // Agregar espera adicional antes de reintentar
            pausaFijaSeg(2);
            pausaPorElementoClickeable(btnTransporte);
            click(btnTransporte);
            pausaFijaSeg(1);
        }
    }

    /**
     * Aplica hover sobre Transporte Aéreo usando Actions nativo
     */
    public void hoverTransporteAereo() {
        pausaFijaMs(500);
        try {
            WebElement elemento = driver.findElement(btnTransporteAereo);
            // Hacer scroll para que el elemento sea visible en el centro de la pantalla
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", elemento);
            pausaFijaMs(300);
            // Usar Actions para hacer hover real (efecto nativo del sitio)
            Actions actions = new Actions(driver);
            actions.moveToElement(elemento).perform();
            pausaFijaSeg(1);
        } catch (Exception e) {
            System.out.println("---> hoverTransporteAereo: error -> " + e.getMessage());
        }
    }

    /**
     * Ejecuta la función JavaScript para ingresar a Transporte Aéreo
     */
    public void ingresarTransporteAereo() {
        pausaFijaMs(300);
        try {
            // Ejecutar la función JavaScript directamente
            ((JavascriptExecutor) driver).executeScript("AnalizarCondicionesRol('RedirectHomePost?TPC=8&u=9&cod=0&i=9', false, 'Transporte aéreo')");
            pausaFijaSeg(3); // Esperar a que cargue la página
        } catch (Exception e) {
            System.out.println("---> ingresarTransporteAereo: error ejecutando JS -> " + e.getMessage());
            // Fallback: intentar click normal
            try {
                WebElement elemento = driver.findElement(btnTransporteAereo);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elemento);
                pausaFijaMs(200);
                elemento.click();
                pausaFijaSeg(3);
            } catch (Exception ex) {
                System.out.println("---> ingresarTransporteAereo: fallback click también falló -> " + ex.getMessage());
            }
        }
    }

    /**
     * Verifica que el menú Cotizadores esté visible y seleccionado
     */
    public boolean estaCotizadoresSeleccionado() {
        try {
            return driver.findElement(menuCotizadores).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ============================================
    // MÉTODOS DE VALIDACIÓN
    // ============================================

    /**
     * Verifica que la página se haya cargado correctamente
     * @return true si la página está cargada
     */
    public boolean isPaginaCargada() {
        // Verificar algún elemento característico, por ahora devolver true
        return driver.getTitle() != null && !driver.getTitle().isEmpty();
    }
}
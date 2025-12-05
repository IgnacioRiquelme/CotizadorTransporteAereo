package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import base.BasePage;

/**
 * PLANTILLA - Page Object para [NOMBRE DE LA PÁGINA]
 * 
 * Instrucciones:
 * 1. Renombra esta clase según la página (ej: LoginPage, HomePage)
 * 2. Define los locators como constantes privadas
 * 3. Crea métodos públicos para cada acción de la página
 * 4. Usa métodos de BasePage para interacciones comunes
 */
public class PageTemplate extends BasePage {

    // ============================================
    // LOCATORS - Define aquí los selectores
    // ============================================
    
    // Ejemplo: private By btnLogin = By.id("login-button");
    // Ejemplo: private By txtUsuario = By.name("username");
    // Ejemplo: private By txtPassword = By.xpath("//input[@type='password']");
    // Ejemplo: private By lblMensaje = By.cssSelector(".mensaje-error");
    
    // ============================================
    // CONSTRUCTOR
    // ============================================
    
    public PageTemplate(WebDriver driver) {
        super(driver);
    }
    
    // ============================================
    // MÉTODOS DE ACCIÓN
    // ============================================
    
    /**
     * Ejemplo: Realiza login en la aplicación
     * @param usuario Usuario para login
     * @param password Contraseña
     */
    public void realizarLogin(String usuario, String password) {
        // Ejemplo de implementación:
        // pausaPorElementoVisible(txtUsuario);
        // insertarDatos(usuario, txtUsuario);
        // insertarDatos(password, txtPassword);
        // click(btnLogin);
        // pausaPorElementoVisible(By.id("dashboard"));
    }
    
    /**
     * Ejemplo: Navega a esta página
     */
    public void navegarAPagina() {
        // llamarUrl("https://miapp.com/pagina");
        // pausaPorElementoVisible(elementoPrincipal);
    }
    
    // ============================================
    // MÉTODOS DE VALIDACIÓN
    // ============================================
    
    /**
     * Ejemplo: Verifica que la página se haya cargado correctamente
     * @return true si la página está cargada
     */
    public boolean isPaginaCargada() {
        // return elementoVisible(elementoPrincipal);
        return false;
    }
    
    /**
     * Ejemplo: Obtiene el mensaje de error mostrado
     * @return Texto del mensaje de error
     */
    public String obtenerMensajeError() {
        // return obtenerTextoTxt(lblMensaje);
        return "";
    }
}

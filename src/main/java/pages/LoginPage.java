package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import base.BasePage;

/**
 * Page Object para la página de login de BCI Seguros
 */
public class LoginPage extends BasePage {

    // ============================================
    // LOCATORS - Define aquí los selectores
    // ============================================

    private By txtRut = By.id("Rut");
    private By txtPassword = By.xpath("//input[@type='password']"); // Locator para campo de contraseña
    private By btnLogin = By.id("login-boton"); // Locator del botón de login

    // ============================================
    // CONSTRUCTOR
    // ============================================

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ============================================
    // MÉTODOS DE ACCIÓN
    // ============================================

    /**
     * Realiza login en la aplicación con RUT y contraseña
     * @param rut RUT del usuario
     * @param password Contraseña
     */
    public void realizarLogin(String rut, String password) {
        pausaPorElementoVisible(txtRut);
        insertarDatos(rut, txtRut);
        insertarDatos(password, txtPassword);
        click(btnLogin);
        // pausaPorElementoVisible(By.id("dashboard")); // Ajustar según la página siguiente
    }

    /**
     * Navega a la página de login
     */
    public void navegarAPaginaLogin(String url) {
        llamarUrl(url);
        pausaPorElementoVisible(txtRut);
    }

    // ============================================
    // MÉTODOS DE VALIDACIÓN
    // ============================================

    /**
     * Verifica que la página de login se haya cargado correctamente
     * @return true si la página está cargada
     */
    public boolean isPaginaLoginCargada() {
        return elementoVisible(txtRut);
    }

    /**
     * Obtiene el mensaje de error si existe
     * @return Texto del mensaje de error
     */
    public String obtenerMensajeError() {
        // Ajustar locator según la página
        // return obtenerTextoTxt(By.className("error-message"));
        return "";
    }
}
package base;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Clase base con métodos comunes para Page Object Model.
 * Contiene utilidades para interactuar con elementos web, esperas, capturas, etc.
 */
public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Configura y retorna una instancia de ChromeDriver.
     */
    public static WebDriver setupChrome(String pathDescarga, String pathCaptura) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-notifications");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", pathDescarga);
        prefs.put("download.prompt_for_download", false);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.directory_upgrade", true);
        prefs.put("safebrowsing.enabled", true);

        chromeOptions.setExperimentalOption("prefs", prefs);
        // chromeOptions.addArguments("--incognito"); // Descomentar para modo incógnito

        return new ChromeDriver(chromeOptions);
    }

    /**
     * Abre una URL en el navegador.
     */
    public void llamarUrl(String url) {
        try {
            driver.get(url);
            log(">>>>>> Info: Launching....." + url);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera log en el reporte de TestNG.
     */
    public void log(String message) {
        Reporter.log(message);
    }

    /**
     * Inserta texto en un campo de entrada.
     */
    public void insertarDatos(String inputText, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
            try {
                el.clear();
            } catch (Exception ignore) {
            }
            el.sendKeys(inputText);
        } catch (Exception e) {
            System.out.println("insertarDatos: no se pudo insertar en " + locator + " -> " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Hace click en un elemento con reintentos y fallbacks.
     */
    public void click(By locator) {
        try {
            boolean clicked = reintentarClick(locator);
            if (clicked)
                return;

            try {
                WebElement el = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                return;
            } catch (Exception ignored) {
            }

            driver.findElement(locator).click();
        } catch (Exception e) {
            System.out.println("click: no se pudo hacer click en " + locator + " -> " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Hace doble click en un elemento.
     */
    public void dobleClick(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.doubleClick(element).perform();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selecciona un radio button si no está seleccionado.
     */
    public void selecRadioButton(By locator) {
        WebElement radioButton = driver.findElement(locator);
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }

    /**
     * Selecciona un checkbox si no está seleccionado.
     */
    public void selecCheckBox(By locator) {
        WebElement checkBox = driver.findElement(locator);
        if (!checkBox.isSelected()) {
            checkBox.click();
        }
    }

    /**
     * Envía la tecla TAB a un elemento.
     */
    public void tabulador(By locator) {
        WebElement cajaTexto = driver.findElement(locator);
        cajaTexto.sendKeys(Keys.TAB);
    }

    /**
     * Selecciona una opción de un elemento Select por valor.
     */
    public void selecSelect(By locator, String inputText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elementoSelect = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(elementoSelect);
        select.selectByValue(inputText);
    }

    /**
     * Selecciona una opción de combo: hace click en el combo y luego en la selección.
     */
    public void seleccionar(By locator1, By locator2) {
        try {
            WebElement combo = driver.findElement(locator1);
            combo.click();
            WebElement seleccion = driver.findElement(locator2);
            seleccion.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Aplica zoom al 70%.
     */
    public void zoomMenos() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='70%'");
    }

    /**
     * Aplica zoom al 45%.
     */
    public void zoomFinal() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='45%'");
    }

    /**
     * Espera implícita general.
     */
    public void implicitWait() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalVariables.PAUSA_GENERAL));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Espera implícita con valor personalizado.
     */
    public void implicitWait(int pausa) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(pausa));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Espera explícita hasta que un elemento sea visible.
     */
    public void pausaPorElementoVisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalVariables.PAUSA_GENERAL));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            System.out.println("pausaPorElementoVisible: timeout esperando visible " + locator + ". Intentando fallback...");
            try {
                WebElement el = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
                WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait2.until(ExpectedConditions.visibilityOf(el));
                return;
            } catch (Exception ex) {
                throw new RuntimeException("Elemento no visible: " + locator.toString(), e);
            }
        }
    }

    /**
     * Espera explícita hasta que un elemento esté presente en el DOM.
     */
    public void pausaPorElementoLocalizado(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Espera explícita hasta que un elemento sea clickeable.
     */
    public void pausaPorElementoClickeable(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            System.out.println("pausaPorElementoClickeable: timeout esperando clickable " + locator + ". Intentando fallback...");
            try {
                WebElement el = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", el);
                WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait2.until(ExpectedConditions.elementToBeClickable(el));
                return;
            } catch (Exception ex) {
                throw new RuntimeException("Elemento no clickable: " + locator.toString(), e);
            }
        }
    }

    /**
     * Espera hasta que un elemento sea invisible.
     */
    public void pausaPorElementoInvisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalVariables.PAUSA_GENERAL));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pausa fija en milisegundos.
     */
    public void pausaFijaMs(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Pausa fija en segundos.
     */
    public void pausaFijaSeg(int seg) {
        pausaFijaMs(seg * 1000L);
    }

    /**
     * Valida si un elemento es visible.
     */
    public boolean elementoVisible(By locator) {
        try {
            driver.findElement(locator).isDisplayed();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Valida si un elemento NO es visible.
     */
    public boolean elementoNoVisible(By locator) {
        try {
            driver.findElement(locator).isDisplayed();
            return false;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Reintenta hacer click hasta 3 veces con esperas.
     */
    public boolean reintentarClick(By locator) {
        boolean result = false;
        int intento = 0;
        while (intento < 3) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                result = true;
                break;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
                intento++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return result;
    }

    /**
     * Reintenta hacer click con parámetros personalizados.
     */
    public void reintentarClick2(By locator, int intentosMaximos, int tiempoEntreIntentosSegundos) {
        int intento = 0;
        boolean exito = false;

        while (intento < intentosMaximos) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(tiempoEntreIntentosSegundos));
                WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
                elemento.click();
                exito = true;
                System.out.println("Intento " + (intento + 1) + ": Click exitoso en " + locator.toString());
                break;
            } catch (Exception e) {
                System.out.println("Intento " + (intento + 1) + ": Falló el click sobre " + locator.toString() + ". Reintentando...");
                intento++;
                try {
                    Thread.sleep(tiempoEntreIntentosSegundos * 1000L);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrumpido durante espera entre reintentos.", ie);
                }
            }
        }

        if (!exito) {
            throw new RuntimeException("No se pudo hacer click en " + locator.toString() + " luego de " + intentosMaximos + " intentos.");
        }
    }

    /**
     * Click especializado para iconos con múltiples estrategias de fallback.
     */
    public boolean clickComoIcono(By locator) {
        try {
            WebElement element = driver.findElement(locator);

            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(element).click().perform();
                return true;
            } catch (Exception ignored) {
            }

            try {
                List<WebElement> children = element.findElements(By.xpath(".//*[name() = 'i' or name() = 'span' or name() = 'a']"));
                for (WebElement ch : children) {
                    try {
                        ch.click();
                        return true;
                    } catch (Exception ignoredChild) {
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ch);
                            return true;
                        } catch (Exception ignoredJs) {
                        }
                    }
                }
            } catch (Exception ignoredChildren) {
            }

            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                return true;
            } catch (Exception ignoredJs) {
            }

            return reintentarClick(locator);
        } catch (Exception e) {
            System.out.println("clickComoIcono: fallo detectado -> " + e.getMessage());
            return false;
        }
    }

    /**
     * Cierra modales si existen (genérico).
     */
    public void cerrarModalSiExiste() {
        try {
            By[] posibles = new By[] {
                By.cssSelector("a.boton.bg-gris"),
                By.cssSelector("button.close"),
                By.cssSelector("button[data-dismiss='modal']"),
                By.xpath("//button[contains(text(),'Cerrar')]")
            };

            boolean cerrado = false;
            for (By sel : posibles) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(sel));
                    if (el != null && el.isDisplayed()) {
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
                        } catch (Exception jsEx) {
                            try {
                                el.click();
                            } catch (Exception clickEx) {
                            }
                        }
                        pausaFijaSeg(1);
                        cerrado = true;
                        break;
                    }
                } catch (Exception e) {
                }
            }

            if (!cerrado) {
                try {
                    String script = "var ms = document.querySelectorAll('.modal.show'); ms.forEach(function(m){m.classList.remove('show'); m.style.display='none';}); var bs = document.querySelectorAll('.modal-backdrop.show'); bs.forEach(function(b){b.parentNode.removeChild(b);});";
                    ((JavascriptExecutor) driver).executeScript(script);
                    pausaFijaSeg(1);
                } catch (Exception ex) {
                    System.out.println(">>>>>> cerrarModalSiExiste: no pudo eliminar modal por JS: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(">>>>>> cerrarModalSiExiste: error general: " + ex.getMessage());
        }
    }

    /**
     * Obtiene el texto del atributo 'value' de un campo de entrada.
     */
    public String obtenerTextoTxt(By locator) {
        try {
            WebElement inputField = driver.findElement(locator);
            String inputValue = inputField.getAttribute("value");
            System.out.println(inputValue);
            return inputValue;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Cierra la ventana actual del navegador.
     */
    public void driverClose() {
        try {
            driver.close();
        } catch (NoSuchSessionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cierra el navegador completamente.
     */
    public void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Obtiene datos de un archivo JSON.
     */
    public String obtenerJson(String fileName, String jsonFileObject, String jsonKey) {
        try {
            InputStream inputStream = new FileInputStream(GlobalVariables.PATH_JSON_DATA + fileName + ".json");
            JSONObject jsonObject = new JSONObject(new JSONTokener(inputStream));
            String jsonValue = (String) jsonObject.getJSONObject(jsonFileObject).get(jsonKey);
            return jsonValue;
        } catch (FileNotFoundException e) {
            Assert.fail("No existe Json File");
            return null;
        }
    }

    /**
     * Captura una screenshot del navegador.
     */
    public void capturaPantalla(String var) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete"));

            Thread.sleep(2000);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            // Guardar siempre en formato PNG (requerimiento)
            File destinationFile = new File(GlobalVariables.PATH_CAPTURA + var + "_" + timestamp + ".png");
            FileHandler.copy(screenshot, destinationFile);
        } catch (NoSuchSessionException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Captura screenshot de toda la pantalla (usando Robot).
     */
    public void capturaPantallaCompleta(String nombreArchivo) {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String ruta = GlobalVariables.PATH_CAPTURA + nombreArchivo + "_" + timestamp + ".png";
            ImageIO.write(screenFullImage, "png", new File(ruta));
        } catch (AWTException | IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Enfoca un elemento moviéndose con el mouse.
     */
    public void focusElemento(By locator) {
        try {
            WebElement elemento = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.moveToElement(elemento).perform();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al enfocar el elemento: " + e.getMessage());
        }
    }

    /**
     * Obtiene el atributo 'src' de una imagen.
     */
    public String obtenerSrc(WebElement img) {
        return img.getAttribute("src");
    }

    /**
     * Espera hasta que un loader desaparezca.
     */
    public void waitLoading(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Limpia caracteres no deseados en mensajes de log para evitar '?' u otros símbolos.
     */
    public static String limpiarMensaje(String msg) {
        if (msg == null) return "";
        try {
            return msg.replaceAll("[?✓✗]", "");
        } catch (Exception e) {
            return msg;
        }
    }
}

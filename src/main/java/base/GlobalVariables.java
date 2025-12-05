package base;

import java.io.File;

/**
 * Variables globales para el framework de automatización.
 * Modifica estos valores según el proyecto específico.
 */
public class GlobalVariables {

    // URL base de la aplicación - MODIFICAR SEGÚN PROYECTO
    public static final String BASE_URL = "https://tu-aplicacion.com/";

    // Tiempos de espera
    public static final int PAUSA_GENERAL = 5;
    public static final int PAUSA_EXTENDIDA = 10;
    
    // Rutas de archivos
    public static final String PATH_JSON_DATA = "./src/main/resources/testdata/";
    public static final String PATH_CAPTURA = System.getProperty("user.dir") + File.separator 
                                              + "test-output" + File.separator 
                                              + "capturaPantalla" + File.separator;
    public static final String PATH_DESCARGA = System.getProperty("user.dir") + File.separator 
                                               + "test-output" + File.separator 
                                               + "archivoDescarga" + File.separator;
    public static final String PATH_EXTENT_REPORTS = System.getProperty("user.dir") + File.separator 
                                                     + "test-output" + File.separator 
                                                     + "ExtentReports" + File.separator;
}

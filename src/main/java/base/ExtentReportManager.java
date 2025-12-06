package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Gestor de reportes Extent Reports.
 * Crea y configura el reporte HTML de pruebas automatizadas.
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static final String REPORT_PATH = System.getProperty("user.dir") + "/test-output/ExtentReports/";
    private static String currentReportFile;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String fileName = "TestReport_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".html";
            currentReportFile = fileName;
            createReportDirectory();
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(REPORT_PATH + fileName);
            
            htmlReporter.config().setDocumentTitle("Reporte - Cotizador Transporte Aéreo");
            htmlReporter.config().setReportName("Automatización BCI Seguros");
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a");
            
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Sistema Operativo", System.getProperty("os.name"));
            extent.setSystemInfo("Ambiente", "QA");
            extent.setSystemInfo("Proyecto", "Cotizador Transporte Aéreo");
            extent.setSystemInfo("URL Base", "https://oficinavirtual.bciseguros.cl/");
        }
        return extent;
    }

    private static void createReportDirectory() {
        File directory = new File(REPORT_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}

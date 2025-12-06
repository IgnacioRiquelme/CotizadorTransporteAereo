package base;

import com.aventstack.extentreports.ExtentTest;

/**
 * Helper simple para logs: escribe en consola y en ExtentReport si hay test activo.
 */
public class Log {

    public static void info(String message) {
        // Siempre imprimir en consola
        System.out.println(message);

        try {
            ExtentTest et = TestListener.getExtentTest();
            if (et != null) {
                et.info(message);
            }
        } catch (Exception e) {
            // No bloquear por logging
        }
    }
}

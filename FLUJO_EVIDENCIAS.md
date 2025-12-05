# Flujo del Sistema de Evidencias

## 📊 Diagrama de Flujo

```
┌─────────────────────────────────────────────────────────────┐
│                    INICIO DEL TEST                          │
└────────────────────┬────────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────────┐
│  Test ejecutándose: CotizadorAsientoPasajerosTest          │
│                                                              │
│  • basePage.capturaPantallaCompleta("t001_Login")          │
│  • basePage.capturaPantallaCompleta("t002_Inicio")         │
│  • basePage.capturaPantallaCompleta("t003_MenuLateral")    │
│  • basePage.capturaPantallaCompleta("t004_AsientoPasajero")│
│  • basePage.capturaPantallaCompleta("t005_Cotizador")      │
│  • basePage.capturaPantallaCompleta("t006_Planes")         │
│  • basePage.capturaPantallaCompleta("t007_Grupos_Riesgo")  │
└────────────────────┬────────────────────────────────────────┘
                     │
                     │ Todas las capturas se guardan en:
                     │ test-output/capturaPantalla/
                     │
                     ▼
┌─────────────────────────────────────────────────────────────┐
│              FINALIZACIÓN DEL TEST                          │
│           TestListener.onTestSuccess() o                    │
│           TestListener.onTestFailure()                      │
└────────┬───────────────────────────────────────────┬────────┘
         │                                           │
         │ TEST PASÓ ✅                             │ TEST FALLÓ ❌
         │                                           │
         ▼                                           ▼
┌──────────────────────┐                  ┌──────────────────────┐
│ moveCapturesToFolder │                  │ moveCapturesToFolder │
│    (passed=true)     │                  │    (passed=false)    │
└──────┬───────────────┘                  └──────┬───────────────┘
       │                                          │
       │ Mueve todas las capturas:                │ Mueve todas las capturas:
       │                                          │
       ▼                                          ▼
┌──────────────────────┐                  ┌──────────────────────┐
│ test-output/         │                  │ test-output/         │
│   evidencia/         │                  │   evidencia/         │
│     passed/          │                  │     failed/          │
│       ├─ t001_*.png  │                  │       ├─ t001_*.png  │
│       ├─ t002_*.png  │                  │       ├─ t002_*.png  │
│       ├─ t003_*.png  │                  │       ├─ t003_*.png  │
│       ├─ t004_*.png  │                  │       ├─ t004_*.png  │
│       ├─ t005_*.png  │                  │       ├─ t005_*.png  │
│       ├─ t006_*.png  │                  │       ├─ t006_*.png  │
│       └─ t007_*.png  │                  │       └─ t007_*.png  │
└──────────────────────┘                  └──────────────────────┘
       │                                          │
       ▼                                          ▼
┌──────────────────────────────────────────────────────────────┐
│             REPORTE EXTENT HTML GENERADO                      │
│  • Incluye las capturas organizadas                          │
│  • Estado del test (PASSED/FAILED)                           │
│  • Stack trace si hubo error                                 │
└──────────────────────────────────────────────────────────────┘
```

## 🔄 Ciclo Completo de Ejecución

### Ejemplo: Test Exitoso ✅

1. **@BeforeClass**: Se inicializa el driver y se configura el entorno
2. **Durante @Test**:
   ```
   test-output/capturaPantalla/
   ├── t001_Login_20251203_083045.png
   ├── t002_Inicio_20251203_083050.png
   ├── t003_MenuLateral_20251203_083055.png
   ├── t004_AsientoPasajero_20251203_083100.png
   ├── t005_Cotizador_20251203_083105.png
   ├── t006_Planes_20251203_083110.png
   └── t007_Grupos_Riesgo_20251203_083115.png
   ```
3. **onTestSuccess()**: TestListener detecta éxito
4. **moveCapturesToFolder(testName, true)**: Mueve todas las capturas
5. **Resultado Final**:
   ```
   test-output/capturaPantalla/  [VACÍA]
   
   test-output/evidencia/passed/
   ├── t001_Login_20251203_083045.png
   ├── t002_Inicio_20251203_083050.png
   ├── t003_MenuLateral_20251203_083055.png
   ├── t004_AsientoPasajero_20251203_083100.png
   ├── t005_Cotizador_20251203_083105.png
   ├── t006_Planes_20251203_083110.png
   └── t007_Grupos_Riesgo_20251203_083115.png
   ```

### Ejemplo: Test Fallido ❌

1. **Durante @Test**: El test falla en el paso 5 (ejemplo)
   ```
   test-output/capturaPantalla/
   ├── t001_Login_20251203_083045.png
   ├── t002_Inicio_20251203_083050.png
   ├── t003_MenuLateral_20251203_083055.png
   ├── t004_AsientoPasajero_20251203_083100.png
   └── t005_Cotizador_20251203_083105.png  [ERROR AQUÍ]
   ```
2. **onTestFailure()**: TestListener detecta fallo
3. **captureScreenshot()**: Toma captura adicional del error
4. **moveCapturesToFolder(testName, false)**: Mueve todas las capturas
5. **Resultado Final**:
   ```
   test-output/capturaPantalla/  [VACÍA]
   
   test-output/evidencia/failed/
   ├── t001_Login_20251203_083045.png
   ├── t002_Inicio_20251203_083050.png
   ├── t003_MenuLateral_20251203_083055.png
   ├── t004_AsientoPasajero_20251203_083100.png
   ├── t005_Cotizador_20251203_083105.png
   └── CotizadorAsientoPasajerosTest_20251203_083106.png  [SCREENSHOT DEL ERROR]
   ```

## 🎨 Ventajas del Sistema

| Característica | Beneficio |
|----------------|-----------|
| **Automático** | No requiere intervención manual |
| **Organizado** | Fácil identificar tests problemáticos |
| **Limpio** | capturaPantalla/ queda vacía después de cada test |
| **Trazable** | Timestamps únicos en cada archivo |
| **Integrado** | Compatible con Extent Reports |
| **Escalable** | Funciona con múltiples tests en suite |

## 🧪 Comparación: Antes vs Después

### ANTES (Sin organización)
```
test-output/capturaPantalla/
├── t001_Login_test1.png
├── t001_Login_test2.png
├── t002_Inicio_test1.png
├── t002_Inicio_test2.png
├── ...  [TODAS MEZCLADAS]
└── t007_Grupos_Riesgo_test5.png
```
❌ No sabes qué test pasó o falló  
❌ Difícil encontrar evidencias específicas  
❌ Carpeta saturada con cientos de archivos  

### DESPUÉS (Con organización)
```
test-output/evidencia/
├── passed/
│   ├── test1_capturas...
│   └── test3_capturas...
└── failed/
    ├── test2_capturas...
    └── test4_capturas...
```
✅ Identificación inmediata de fallos  
✅ Evidencias organizadas por resultado  
✅ Fácil localización y análisis  

---

**Nota**: Este sistema se ejecuta automáticamente en cada test sin modificar el código existente de tus tests.

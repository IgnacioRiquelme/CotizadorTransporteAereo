# 📸 Sistema de Gestión de Evidencias - Resumen Ejecutivo

## ✅ Implementación Completada

Se ha implementado exitosamente un sistema automático de organización de capturas de pantalla (evidencias) en tu proyecto de automatización.

---

## 🎯 ¿Qué se Implementó?

### 1️⃣ Estructura de Carpetas
```
test-output/
├── evidencia/          ← NUEVA carpeta principal
│   ├── passed/        ← Capturas de tests exitosos ✅
│   └── failed/        ← Capturas de tests fallidos ❌
```

### 2️⃣ Archivos Modificados

| Archivo | Cambios Realizados |
|---------|-------------------|
| **GlobalVariables.java** | ✅ Agregadas constantes: `PATH_EVIDENCIA`, `PATH_EVIDENCIA_PASSED`, `PATH_EVIDENCIA_FAILED` |
| **TestListener.java** | ✅ Agregado método `moveCapturesToFolder()` que se ejecuta automáticamente |
| **pom.xml** | ✅ Agregada dependencia `commons-io` versión 2.15.1 |

### 3️⃣ Archivos Nuevos Creados

| Archivo | Propósito |
|---------|-----------|
| `EVIDENCIAS_README.md` | 📖 Documentación completa del sistema |
| `FLUJO_EVIDENCIAS.md` | 📊 Diagrama de flujo visual |
| `scripts/test_evidencias.bat` | 🔧 Script de prueba (Windows CMD) |
| `scripts/test_evidencias.ps1` | 🔧 Script de prueba (PowerShell) |

---

## 🔄 Cómo Funciona (Automático)

```
ANTES DEL TEST
└─ Las carpetas evidencia/passed y evidencia/failed se crean automáticamente

DURANTE EL TEST
└─ Las capturas se guardan temporalmente en capturaPantalla/
   • t001_Login_timestamp.png
   • t002_Inicio_timestamp.png
   • t003_MenuLateral_timestamp.png
   • etc.

AL FINALIZAR EL TEST
├─ Si el test PASÓ ✅
│  └─ Todas las capturas → evidencia/passed/
│
└─ Si el test FALLÓ ❌
   └─ Todas las capturas → evidencia/failed/
       + Screenshot adicional del error
```

---

## 🚀 Ventajas Implementadas

| Beneficio | Descripción |
|-----------|-------------|
| ⚡ **100% Automático** | No requiere cambios en tus tests existentes |
| 📁 **Auto-organización** | Las capturas se mueven solas según el resultado |
| 🔍 **Fácil Debug** | Identifica rápidamente tests problemáticos |
| 🧹 **Limpieza Automática** | `capturaPantalla/` queda vacía después de cada test |
| 📊 **Integrado** | Compatible con tus reportes Extent existentes |
| ⏰ **Trazabilidad** | Cada captura tiene timestamp único |

---

## 📝 No Requiere Cambios en Tus Tests

Tu código de test **NO NECESITA MODIFICACIONES**. Ejemplo:

```java
@Test(priority = 1, description = "Login en BCI Seguros")
public void t001_login() throws Exception {
    // Tu código existente funciona igual
    basePage.capturaPantallaCompleta("t001_Login");
    basePage.capturaPantallaCompleta("t002_Inicio");
    
    // El TestListener organiza automáticamente las capturas ✅
}
```

---

## 🧪 Prueba el Sistema

### Opción 1: PowerShell (Recomendado)
```powershell
.\scripts\test_evidencias.ps1
```

### Opción 2: CMD
```cmd
.\scripts\test_evidencias.bat
```

### Opción 3: Manual
```powershell
mvn clean test
```
Luego verifica:
- `test-output/evidencia/passed/` → Tests exitosos
- `test-output/evidencia/failed/` → Tests fallidos

---

## 📂 Ejemplo de Resultado Real

### Después de ejecutar un test exitoso:

```
test-output/
├── evidencia/
│   ├── passed/
│   │   ├── t001_Login_20251203_083045.png
│   │   ├── t002_Inicio_20251203_083050.png
│   │   ├── t003_MenuLateral_20251203_083055.png
│   │   ├── t004_AsientoPasajero_20251203_083100.png
│   │   ├── t005_Cotizador_20251203_083105.png
│   │   ├── t006_Planes_20251203_083110.png
│   │   └── t007_Grupos_Riesgo_20251203_083115.png
│   └── failed/
│       └── [vacía]
└── capturaPantalla/
    └── [vacía - las capturas se movieron]
```

### Después de ejecutar un test fallido:

```
test-output/
├── evidencia/
│   ├── passed/
│   │   └── [vacía]
│   └── failed/
│       ├── t001_Login_20251203_090030.png
│       ├── t002_Inicio_20251203_090035.png
│       ├── t003_MenuLateral_20251203_090040.png
│       └── CotizadorAsientoPasajerosTest_20251203_090042.png ← Screenshot del error
└── capturaPantalla/
    └── [vacía - las capturas se movieron]
```

---

## 🔍 Verificación Rápida

Ejecuta este comando para ver la estructura:

```powershell
tree /F test-output\evidencia
```

Deberías ver:
```
test-output\evidencia
├── failed
└── passed
```

---

## 🎓 Documentación Adicional

- **Guía Completa**: `EVIDENCIAS_README.md`
- **Diagramas de Flujo**: `FLUJO_EVIDENCIAS.md`

---

## 💡 Consejos

1. **No elimines** la carpeta `capturaPantalla/` - se usa como temporal
2. **Las capturas en evidencia/** son las finales - úsalas para análisis
3. **El sistema funciona con múltiples tests** en suite
4. **Compatible con Extent Reports** - los reportes incluyen las capturas

---

## 🔧 Tecnologías Utilizadas

- **Apache Commons IO 2.15.1** - Para operaciones de archivos
- **TestNG ITestListener** - Para detectar resultado de tests
- **Java NIO File Operations** - Para crear carpetas automáticamente

---

## ✨ Estado: LISTO PARA USAR

El sistema está completamente funcional y listo para usar en tus tests existentes. ¡No se requiere ninguna acción adicional!

---

**Implementado**: 3 de diciembre de 2025  
**Proyecto**: Cotizador_Asiento_Pasajeros  
**Framework**: Selenium 4.20.0 + TestNG 7.10.2  
**Estado**: ✅ Producción

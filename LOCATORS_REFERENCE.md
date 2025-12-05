# Referencia Rápida de Locators

## Estrategias de Localización en Selenium

### 1. Por ID (Más recomendado)
```java
By locator = By.id("username");
By locator = By.id("btn-login");
```
**Cuándo usar**: Cuando el elemento tiene un atributo `id` único.  
**HTML**: `<input id="username" />`

---

### 2. Por Name
```java
By locator = By.name("email");
By locator = By.name("password");
```
**Cuándo usar**: Cuando el elemento tiene un atributo `name` único.  
**HTML**: `<input name="email" />`

---

### 3. Por CSS Selector (Recomendado)
```java
// Por clase
By locator = By.cssSelector(".btn-primary");
By locator = By.cssSelector("button.submit");

// Por atributo
By locator = By.cssSelector("input[type='text']");
By locator = By.cssSelector("button[data-action='submit']");

// Por combinación
By locator = By.cssSelector("form#login input[name='user']");
By locator = By.cssSelector("div.container > button.submit");

// Por texto parcial en atributo
By locator = By.cssSelector("a[href*='logout']");
By locator = By.cssSelector("input[placeholder^='Ingrese']");
```
**Cuándo usar**: Locators flexibles y eficientes.

---

### 4. Por XPath
```java
// Absoluto (NO recomendado)
By locator = By.xpath("/html/body/div[1]/form/input");

// Relativo (Recomendado)
By locator = By.xpath("//input[@id='username']");
By locator = By.xpath("//button[@type='submit']");

// Por texto
By locator = By.xpath("//button[text()='Iniciar Sesión']");
By locator = By.xpath("//span[contains(text(),'Bienvenido')]");

// Por atributos múltiples
By locator = By.xpath("//input[@type='text' and @name='user']");

// Navegación por ancestros/descendientes
By locator = By.xpath("//div[@class='form']//input[@name='email']");
By locator = By.xpath("//table//tr[2]//td[3]");

// Por índice
By locator = By.xpath("(//button[@class='btn'])[2]");

// Navegación hacia arriba
By locator = By.xpath("//span[text()='Usuario']/parent::div/input");
By locator = By.xpath("//input[@id='user']/ancestor::form");
```
**Cuándo usar**: Cuando necesitas navegación compleja o texto exacto.

---

### 5. Por Clase
```java
By locator = By.className("btn-primary");
By locator = By.className("error-message");
```
**Cuándo usar**: Cuando la clase es única (cuidado con clases múltiples).  
**HTML**: `<button class="btn-primary">Login</button>`

---

### 6. Por Tag Name
```java
By locator = By.tagName("h1");
By locator = By.tagName("button");
```
**Cuándo usar**: Cuando quieres todos los elementos de un tipo.  
**HTML**: `<h1>Título</h1>`

---

### 7. Por Link Text
```java
By locator = By.linkText("Cerrar Sesión");
By locator = By.linkText("Términos y Condiciones");
```
**Cuándo usar**: Para enlaces (`<a>`) con texto exacto.  
**HTML**: `<a href="/logout">Cerrar Sesión</a>`

---

### 8. Por Partial Link Text
```java
By locator = By.partialLinkText("Cerrar");
By locator = By.partialLinkText("Términos");
```
**Cuándo usar**: Para enlaces con texto parcial.  
**HTML**: `<a href="/logout">Cerrar Sesión Ahora</a>`

---

## Ejemplos Prácticos por Escenario

### Login Form
```java
// Campos de texto
By txtUsuario = By.id("username");
By txtPassword = By.name("password");

// Botones
By btnLogin = By.cssSelector("button[type='submit']");
By btnLogin2 = By.xpath("//button[text()='Iniciar Sesión']");

// Checkbox "Recordarme"
By chkRecordar = By.id("remember-me");

// Mensaje de error
By lblError = By.cssSelector(".error-message");
By lblError2 = By.xpath("//div[@class='alert alert-danger']");
```

### Tabla de Datos
```java
// Primera fila
By primeraFila = By.xpath("//table[@id='datos']//tr[1]");

// Celda específica (fila 2, columna 3)
By celda = By.xpath("//table[@id='datos']//tr[2]/td[3]");

// Botón editar de una fila específica
By btnEditar = By.xpath("//tr[contains(.,'Juan')]//button[@title='Editar']");

// Todas las filas
By todasFilas = By.xpath("//table[@id='datos']//tbody//tr");
```

### Dropdown / Select
```java
// El select mismo
By cboCategoria = By.id("categoria");

// Opción específica
By opcionVentas = By.xpath("//select[@id='categoria']/option[text()='Ventas']");
```

### Modal / Dialog
```java
// El modal
By modal = By.cssSelector("div.modal.show");

// Botón cerrar (X)
By btnCerrarModal = By.cssSelector("button.close");

// Botón confirmar
By btnConfirmar = By.xpath("//div[@class='modal-footer']//button[text()='Confirmar']");
```

### Menú de Navegación
```java
// Item del menú
By menuInicio = By.linkText("Inicio");
By menuReportes = By.xpath("//nav//a[contains(text(),'Reportes')]");

// Submenú
By submenuVentas = By.cssSelector("ul.dropdown-menu a[href='/ventas']");
```

### Formulario Dinámico
```java
// Input por placeholder
By txtBuscar = By.cssSelector("input[placeholder='Buscar...']");

// Botón por data-attribute
By btnGuardar = By.cssSelector("button[data-action='save']");

// Elemento por aria-label
By btnCerrar = By.cssSelector("button[aria-label='Close']");
```

---

## Tips para Crear Buenos Locators

### ✅ BUENAS PRÁCTICAS

1. **Preferir IDs únicos**
   ```java
   By locator = By.id("btn-submit"); // MEJOR
   ```

2. **CSS Selector para atributos**
   ```java
   By locator = By.cssSelector("button[data-testid='submit']"); // MEJOR
   ```

3. **XPath para texto**
   ```java
   By locator = By.xpath("//button[text()='Guardar']"); // MEJOR para texto exacto
   ```

4. **Evitar índices cuando sea posible**
   ```java
   // EVITAR
   By locator = By.xpath("(//div)[5]");
   
   // MEJOR
   By locator = By.xpath("//div[@class='resultado'][1]");
   ```

### ❌ MALAS PRÁCTICAS

1. **XPath absolutos**
   ```java
   // MAL - Se rompe con cualquier cambio
   By locator = By.xpath("/html/body/div[1]/div[2]/form/input[3]");
   ```

2. **Depender de estructura visual**
   ```java
   // MAL - Cambia si cambia el diseño
   By locator = By.xpath("//div[1]/div[2]/div[3]/button");
   ```

3. **Clases CSS de frameworks**
   ```java
   // MAL - Clases de Bootstrap/Tailwind cambian
   By locator = By.className("btn btn-primary btn-lg");
   ```

---

## Herramientas para Obtener Locators

### Chrome DevTools
1. Click derecho en el elemento → Inspeccionar
2. Click derecho en el código HTML → Copy → Copy selector / Copy XPath

### Extensiones Útiles
- **ChroPath** (Chrome/Firefox): Genera XPath y CSS selectores
- **SelectorsHub**: Similar a ChroPath con más features
- **Ranorex Selocity**: Generador de selectores inteligente

### Comando en Consola del Navegador
```javascript
// Probar CSS Selector
$("button[type='submit']")

// Probar XPath
$x("//button[@type='submit']")
```

---

## Validar Locators Antes de Usar

### En DevTools (F12)
```javascript
// CSS Selector
$$("tu-selector-css") // Debe devolver elementos

// XPath
$x("tu-xpath") // Debe devolver elementos
```

Si devuelve `[]` o `null`, el locator está mal.

---

## Locators Dinámicos

Cuando los IDs o atributos cambian cada vez:

```java
// Usar contains para match parcial
By locator = By.xpath("//div[contains(@id,'resultado')]");
By locator = By.cssSelector("div[id*='resultado']");

// Usar starts-with
By locator = By.xpath("//input[starts-with(@id,'user_')]");
By locator = By.cssSelector("input[id^='user_']");

// Combinar con otros atributos estables
By locator = By.xpath("//input[@type='text' and contains(@class,'username')]");
```

---

**Nota**: Siempre prefiere locators que sean:
- **Únicos**: Identifica solo un elemento
- **Estables**: No cambia con cada deploy
- **Descriptivos**: Es claro qué elemento localiza

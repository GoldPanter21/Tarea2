# 2. Versión Jetpack Compose (Kotlin Native)

Aplicación nativa para Android construida mediante el paradigma declarativo moderno de Google. En lugar de separar el diseño y la lógica, la interfaz se construye completamente en Kotlin utilizando funciones etiquetadas con `@Composable`. El árbol de la interfaz gráfica se actualiza automáticamente (recomposición) al mutar las variables de estado (`State<T>`).

## Instrucciones de Compilación y Ejecución
1. Abre Android Studio.
2. Selecciona **File > Open** y navega hasta la carpeta raíz del proyecto llamada `android-compose`.
3. Ejecuta una limpieza del proyecto yendo a **Build > Clean Project** para asegurar que no haya artefactos residuales, seguido de la sincronización de Gradle.
4. Selecciona tu dispositivo de prueba en el menú superior.
5. Presiona el botón verde de **Run (Shift + F10)**.
*   *Nota:* Si utilizas un emulador, asegúrate de que tenga conexión a internet configurada para que el componente `AsyncImage` (Coil) pueda descargar los recursos de red sin lanzar excepciones.

## Detalles de Implementación

### Dependencias Agregadas (`build.gradle.kts`)
A diferencia de la versión con XML, Jetpack Compose utiliza un paradigma declarativo nativo. Se integraron las siguientes librerías principales:

*   **Compose Material 3** (`androidx.compose.material3:material3`): Provee los componentes visuales modernos y la tematización (Material You). Suele venir incluida por defecto al crear un proyecto de Compose.
*   **Navigation Compose** (`androidx.navigation:navigation-compose`): Fundamental para implementar la arquitectura de una sola Actividad, permitiendo la navegación entre funciones `@Composable` a través de un sistema de rutas.
*   **Coil Compose** (`io.coil-kt:coil-compose`): Librería ligera y optimizada para Kotlin Coroutines, encargada de la carga asíncrona de la imagen web en la Sección 5 mediante el componente `AsyncImage`.

### Archivos Modificados y Acciones Realizadas

**1. Configuración y Manifiesto**
*   `build.gradle.kts (Module :app)`: Se agregaron las dependencias de Navigation y Coil.
*   `AndroidManifest.xml`: Se incluyó el permiso `<uses-permission android:name="android.permission.INTERNET" />` para la descarga de recursos de red. No se declararon más de un `Activity`.

**2. Navegación y Estructura Base**
*   **Archivos XML Eliminados:** Toda la carpeta `res/layout` fue omitida, ya que la interfaz se construye 100% en código Kotlin.
*   `MainActivity.kt`: Funciona como el único punto de entrada. Aquí se implementó el `Scaffold` principal que contiene el `NavigationBar` (menú inferior) y el `NavHost`.
*   **Grafo de Navegación:** En lugar de un archivo XML para el NavGraph, se utilizó el bloque `NavHost(navController, startDestination)` para registrar las rutas (ej. `"input"`, `"buttons"`) y vincularlas a sus respectivas funciones Composable.

**3. Diseño de Interfaces y Lógica (Funciones `@Composable`)**
*   `InputScreen.kt` a `StructureScreen.kt` (o dentro del archivo correspondiente): Se programaron las 6 secciones. A diferencia de Views, diseño y lógica coexisten en la misma función.
*   **Gestión del Estado:** Se utilizó `remember { mutableStateOf() }` para controlar reactivamente la interfaz. Al cambiar el valor de estas variables (texto en un input, selección de un radio button, visibilidad de un diálogo), Compose automáticamente redibuja (recompone) los componentes afectados.
*   **Listas y Colecciones (Sección 4):** Se reemplazó el uso complejo de `RecyclerView` y `Adapters` por los componentes `LazyColumn` y `LazyVerticalGrid`. La lógica de arrastre se implementó con `SwipeToDismissBox` (o `SwipeToDismiss`), y el encabezado fijo con `stickyHeader`.
*   **Retroalimentación (Sección 5):** Los diálogos (`AlertDialog`) y las hojas inferiores (`ModalBottomSheet`) se incorporaron directamente en el árbol de componentes, mostrando u ocultando su contenido en base a variables booleanas de estado. Los mensajes rápidos se manejaron a través del `SnackbarHostState` vinculado al `Scaffold`.
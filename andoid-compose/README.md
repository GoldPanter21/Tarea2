# 2. Versión Jetpack Compose (Kotlin Native)

Aplicación nativa para Android construida mediante el paradigma declarativo moderno de Google. En lugar de separar el diseño y la lógica, la interfaz se construye completamente en Kotlin utilizando funciones etiquetadas con `@Composable`. El árbol de la interfaz gráfica se actualiza automáticamente (recomposición) al mutar las variables de estado (`State<T>`).

## Instrucciones de Compilación y Ejecución
1. Abre Android Studio.
2. Selecciona **File > Open** y navega hasta la carpeta raíz del proyecto llamada `android-compose`.
3. Ejecuta una limpieza del proyecto yendo a **Build > Clean Project** para asegurar que no haya artefactos residuales, seguido de la sincronización de Gradle.
4. Selecciona tu dispositivo de prueba en el menú superior.
5. Presiona el botón verde de **Run (Shift + F10)**.
*   *Nota:* Si utilizas un emulador, asegúrate de que tenga conexión a internet configurada para que el componente `AsyncImage` (Coil) pueda descargar los recursos de red sin lanzar excepciones.

## Binarios (APK)
*   **Ubicación del APK:** `android-compose/app/build/outputs/apk/debug/app-debug.apk`
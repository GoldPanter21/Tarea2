# 3. Versión Flutter (Dart)

Aplicación multiplataforma construida con el SDK de Flutter. Utiliza el lenguaje Dart y una arquitectura fuertemente declarativa basada en un árbol de "Widgets". Esta versión compila directamente a código nativo ARM para ofrecer un rendimiento a 60/120 fps y utiliza el sistema de diseño de Material 3. Se gestionó la memoria y el ciclo de vida explícitamente mediante `StatefulWidget` y controladores (ej. `TextEditingController`).

## Instrucciones de Compilación y Ejecución
1. Asegúrate de tener el SDK de Flutter instalado y configurado en tus variables de entorno.
2. Abre Visual Studio Code (o Android Studio) y carga la carpeta `app_basicas_flutter`.
3. Abre la terminal integrada en la raíz de esta carpeta y ejecuta el comando:
    flutter pub get
   *(Esto descargará las dependencias necesarias definidas en el `pubspec.yaml`).*
4. Conecta tu dispositivo (físico o emulador) y selecciónalo en la esquina inferior derecha de VSCode.
5. Presiona **F5** (Run / Debug) o ejecuta el siguiente comando en la terminal:
    flutter run

## Binarios (APK)
*   **Ubicación del APK compilado:** Para generar el ejecutable, ejecuta `flutter build apk` en la terminal. El archivo resultante se depositará en: 
    `app_basicas_flutter/build/app/outputs/flutter-apk/app-release.apk`

## Detalles de Implementación

### Dependencias y Configuración (`pubspec.yaml`)
A diferencia de las versiones nativas de Android, el SDK de Flutter incluye de forma nativa la mayoría de las herramientas necesarias para construir interfaces complejas, enrutamiento y peticiones de red básicas, por lo que la dependencia de paquetes de terceros es mínima.

*   **Material Design 3:** Habilitado por defecto en el archivo `main.dart` a través de la propiedad `useMaterial3: true` dentro del `ThemeData`. Esto garantiza el acceso a componentes modernos como `SegmentedButton` y `DropdownMenu`.
*   **Carga de imágenes:** No se requirió ninguna librería externa (como Glide o Coil) para la carga asíncrona de imágenes desde la web, ya que el SDK de Flutter incluye el widget nativo `Image.network` que maneja la descarga y el caché automáticamente.
*   **Permisos de Red:** En entorno de desarrollo, Flutter habilita el acceso a Internet por defecto. Para el binario final (Release), se aseguró la inclusión del permiso de Internet en los manifiestos nativos generados automáticamente.

### Archivos Modificados y Acciones Realizadas

**1. Estructura Base y Navegación (`lib/main.dart`)**
*   **Punto de Entrada:** Se utilizó la función `main()` ejecutando `runApp(const MyApp())` para inicializar el árbol de widgets.
*   **Enrutamiento (Routing):** En lugar de usar `NavGraph` (XML) o `NavHost` (Compose), la navegación de la "Single Activity" se implementó de forma nativa en el widget `MaterialApp` mediante un mapa de rutas (`routes: {...}`). 
*   **Navegación entre pantallas:** Se utilizó `Navigator.pushNamed(context, '/ruta')` para abrir las secciones y `Navigator.pop(context)` para volver al menú principal.

**2. Diseño de Interfaces (Widgets Visuales)**
*   Todo en Flutter es un Widget. Las pantallas se diseñaron componiendo widgets estructurales (`Scaffold`, `Column`, `Row`, `Expanded`, `Stack`) directamente en el código Dart.
*   **Listas y Colecciones (Sección 4):** Se emplearon constructores eficientes (equivalentes a `LazyColumn` o `RecyclerView`) como `ListView.builder` y `GridView.builder`. 
*   **Componentes Avanzados:** Para el efecto de arrastrar para eliminar se utilizó el widget `Dismissible`, y para los encabezados fijos (*Sticky Headers*) se implementó un `CustomScrollView` apoyado de *Slivers* (`SliverPersistentHeader`).

**3. Lógica de Controladores y Gestión de Estado**
*   **Estado Local:** A diferencia de Compose, donde se usa `mutableStateOf`, aquí la reactividad se manejó envolviendo las pantallas en `StatefulWidget`. La interfaz se actualiza llamando explícitamente a la función `setState(() { ... })` cada vez que una variable cambia (ej. el valor de un *checkbox* o un *switch*).
*   **Entradas de Texto (Sección 1):** La memoria de los campos de texto se gestionó instanciando `TextEditingController`s, asegurando su liberación de memoria en el método `dispose()` del ciclo de vida del widget para evitar *memory leaks*.
*   **Retroalimentación y Diálogos (Sección 5):** Los *Toasts* clásicos fueron reemplazados por el estándar actual de Flutter utilizando `ScaffoldMessenger.of(context).showSnackBar()`. Los diálogos y hojas inferiores se invocaron mediante funciones imperativas globales como `showDialog()` y `showModalBottomSheet()`, delegando la construcción visual a su *builder* interno.
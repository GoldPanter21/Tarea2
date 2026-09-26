# 1. Versión Android Views (XML)

Una aplicación con Android Views es aquella que se construye mediante Activities y Fragments. En esta práctica desarrollamos un Activity principal y 6 Fragments (1 por sección). De forma que se cumplen los requerimientos de la tarea.
Actualmnete lo recomendable es seguir este tipo de arquitectura con la menor cantidad de Activities y varios Fragments, debido a que de esta forma la reutlización del código puede ser más sencilla.

## Instrucciones de Compilación y Ejecución
1. Abre Android Studio.
2. Selecciona **File > Open** y navega hasta la carpeta raíz del proyecto llamada `android-views`.
3. Espera a que Gradle sincronice las dependencias (se requiere conexión a internet para descargar librerías como Glide o Material Components si no están en caché).
4. Selecciona un dispositivo físico (con depuración USB activa) o un emulador de Android (API 24+ recomendado) en la barra superior.
5. Presiona el botón verde de **Run (Shift + F10)**.

## Detalles de Implementación

### Dependencias Agregadas (`build.gradle.kts`)
Para cumplir con los componentes requeridos, se integraron las siguientes librerías de terceros y de AndroidX:

*   **Material Components for Android** (`com.google.android.material:material`): Necesario para los componentes visuales modernos como `TextInputLayout`, `FloatingActionButton`, `BottomNavigationView`, `Slider`, `Chip` y `BottomSheetDialog`.
*   **Android Navigation Component** (`androidx.navigation:navigation-fragment-ktx` y `androidx.navigation:navigation-ui-ktx`): Utilizado para implementar la arquitectura de una sola Actividad (Single Activity) y gestionar el enrutamiento entre los 6 Fragments.
*   **Glide** (`com.github.bumptech.glide:glide`): Librería especializada para la carga, decodificación y caché de la imagen asíncrona desde una URL (solicitada en la Sección 5).
*   **ViewBinding**: Habilitado mediante el bloque `buildFeatures { viewBinding = true }` para vincular de forma segura los elementos XML con el código Kotlin, eliminando el uso obsoleto de `findViewById`.

### Archivos Modificados y Acciones Realizadas

**1. Configuración y Manifiesto**
*   `build.gradle.kts (Module :app)`: Se añadieron las dependencias mencionadas anteriormente y se habilitó ViewBinding.
*   `AndroidManifest.xml`: Se agregó el permiso `<uses-permission android:name="android.permission.INTERNET" />` para permitir que Glide/Picasso descargue la imagen de la URL.

**2. Navegación y Estructura Base**
*   `res/navigation/nav_graph.xml`: Se creó el grafo de navegación visual que define las rutas y acciones entre la pantalla principal y los 6 destinos (Fragments).
*   `res/menu/bottom_nav_menu.xml`: Se diseñó el menú con los íconos correspondientes para la barra de navegación inferior.
*   `res/layout/activity_main.xml`: Se estructuró el contenedor principal que aloja el `FragmentContainerView` (donde rotan las pantallas) y el `BottomNavigationView` en la parte inferior.
*   `MainActivity.kt`: Se configuró el `NavController` para enlazar el `BottomNavigationView` con el grafo de navegación, permitiendo el cambio automático de fragments.

**3. Diseño de Interfaces (Carpetas `res/layout/`)**
*   `fragment_input.xml` a `fragment_structure.xml`: Se construyeron las 6 interfaces utilizando contenedores nativos (principalmente `LinearLayout` y `ScrollView` para desplazamiento), declarando los componentes de Material Design equivalentes a cada sección.
*   `item_list.xml`: Se creó el diseño individual (tarjeta) para los elementos de las listas (`RecyclerView`) de la Sección 4.

**4. Lógica de Controladores (Clases Kotlin)**
*   `InputFragment.kt` a `StructureFragment.kt`: Se implementó la lógica de interfaz de cada sección utilizando ViewBinding. Aquí se configuraron los *listeners* (eventos de clic, cambio de texto, selección de radio buttons), la invocación de `Toast` y `Snackbar`, la apertura de diálogos (`AlertDialog` y `BottomSheetDialog`), y la gestión de la interfaz de usuario.
*   `ListAdapter.kt` (o equivalente): Se programó la clase adaptadora heredada de `RecyclerView.Adapter` junto con su `ViewHolder` para poblar dinámicamente la lista de la Sección 4, incluyendo la lógica para el arrastre (*Swipe to dismiss*) y la selección individual de elementos.
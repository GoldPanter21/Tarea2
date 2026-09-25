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
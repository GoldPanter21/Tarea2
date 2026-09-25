package com.gmail.angelisairock.appbasicascompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items
import com.gmail.angelisairock.appbasicascompose.ui.theme.AppBasicasComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppBasicasComposeTheme {
                AppNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Observar la ruta actual para cambiar el título de la barra superior
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    val title = when (currentRoute) {
        "home" -> "Catálogo de UI"
        "input" -> "1. Entrada de Texto"
        "buttons" -> "2. Botones y Acciones"
        "selection" -> "3. Selección"
        "lists" -> "4. Listas y Colecciones"
        "info" -> "5. Información"
        "structure" -> "6. Contenedores"
        else -> "Catálogo de UI"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    if (currentRoute != "home") {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("input") { InputScreen() }
            composable("buttons") { ButtonsScreen() }
            composable("selection") { SelectionScreen() }
            composable("lists") { ListsScreen() }
            composable("info") { InfoScreen() }
            composable("structure") { StructureScreen() }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("input") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text("1. Entrada de Texto")
        }
        Button(onClick = { navController.navigate("buttons") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text("2. Botones y Acciones")
        }
        Button(onClick = { navController.navigate("selection") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text("3. Elementos de Selección")
        }
        Button(onClick = { navController.navigate("lists") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text("4. Listas y Colecciones")
        }
        Button(onClick = { navController.navigate("info") }, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Text("5. Información y Retroalimentación")
        }
        Button(onClick = { navController.navigate("structure") }, modifier = Modifier.fillMaxWidth()) {
            Text("6. Contenedores")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen() {
    // Estados para guardar lo que el usuario escribe
    var textoSimple by remember { mutableStateOf("") }
    var textoError by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var textoNumerico by remember { mutableStateOf("") }
    var textoMultiline by remember { mutableStateOf("") }
    var busqueda by remember { mutableStateOf("") }

    // Estados para el menú desplegable
    val opciones = listOf("Views (XML)", "Jetpack Compose", "Flutter", "Kotlin Native")
    var expandido by remember { mutableStateOf(false) }
    var opcionSeleccionada by remember { mutableStateOf(opciones[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("1. Entrada de Texto", style = MaterialTheme.typography.titleLarge)

        // 1. Campo simple
        OutlinedTextField(
            value = textoSimple,
            onValueChange = { textoSimple = it },
            label = { Text("Campo simple") },
            modifier = Modifier.fillMaxWidth()
        )

        // 2. Campo con validación y error
        val isError = textoError.isNotEmpty() && textoError.length < 5
        OutlinedTextField(
            value = textoError,
            onValueChange = { textoError = it },
            label = { Text("Validador (mínimo 5 letras)") },
            isError = isError,
            supportingText = {
                if (isError) Text("Faltan caracteres")
            },
            modifier = Modifier.fillMaxWidth()
        )

        // 3. Campo de contraseña con toggle
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(image, "Alternar visibilidad")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // 4. Teclado Numérico (puedes cambiarlo a Phone o Email)
        OutlinedTextField(
            value = textoNumerico,
            onValueChange = { textoNumerico = it },
            label = { Text("Teclado Numérico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // 5. Campo Multilínea
        OutlinedTextField(
            value = textoMultiline,
            onValueChange = { textoMultiline = it },
            label = { Text("Comentarios (Multilínea)") },
            minLines = 3,
            maxLines = 5,
            modifier = Modifier.fillMaxWidth()
        )

        // 6. Menú Desplegable (Sugerencias)
        ExposedDropdownMenuBox(
            expanded = expandido,
            onExpandedChange = { expandido = !expandido }
        ) {
            OutlinedTextField(
                value = opcionSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona una tecnología") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandido,
                onDismissRequest = { expandido = false }
            ) {
                opciones.forEach { seleccion ->
                    DropdownMenuItem(
                        text = { Text(seleccion) },
                        onClick = {
                            opcionSeleccionada = seleccion
                            expandido = false
                        }
                    )
                }
            }
        }

        // 7. Barra de búsqueda
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            label = { Text("Buscar elemento...") },
            leadingIcon = { Icon(Icons.Filled.Search, "Buscar") },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge // Le da el aspecto redondeado de barra de búsqueda
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Estados para los botones interactivos
    var isLoading by remember { mutableStateOf(false) }
    var fabExpanded by remember { mutableStateOf(true) }

    // Estado para Selector Segmentado
    val opcionesSegmentadas = listOf("Opción 1", "Opción 2", "Opción 3")
    var opcionSeleccionada by remember { mutableStateOf(0) }

    // Función auxiliar para no repetir código
    fun mostrarMensaje(mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("2. Botones y Acciones", style = MaterialTheme.typography.titleLarge)

        // 1. Botones Básicos
        Button(onClick = { mostrarMensaje("Botón Relleno pulsado") }, modifier = Modifier.fillMaxWidth()) {
            Text("Botón Relleno")
        }

        OutlinedButton(onClick = { mostrarMensaje("Botón con Contorno pulsado") }, modifier = Modifier.fillMaxWidth()) {
            Text("Botón con Contorno")
        }

        TextButton(onClick = { mostrarMensaje("Botón de Solo Texto pulsado") }, modifier = Modifier.fillMaxWidth()) {
            Text("Botón de Solo Texto")
        }

        // 2. Botones con Ícono (Solo ícono y con texto)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { mostrarMensaje("Enviar (Ícono + Texto) pulsado") }) {
                Icon(Icons.Filled.Send, contentDescription = "Enviar", modifier = Modifier.padding(end = 8.dp))
                Text("Enviar")
            }

            IconButton(onClick = { mostrarMensaje("Botón de solo ícono pulsado") }, modifier = Modifier.size(48.dp)) {
                Icon(Icons.Filled.Send, contentDescription = "Solo ícono")
            }
        }

        // 3. Botones de Acción Flotante (FAB)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            FloatingActionButton(onClick = { mostrarMensaje("FAB Normal pulsado") }) {
                Icon(Icons.Filled.Add, contentDescription = "Añadir")
            }

            ExtendedFloatingActionButton(
                onClick = {
                    fabExpanded = !fabExpanded
                    mostrarMensaje(if (fabExpanded) "FAB Extendido" else "FAB Colapsado")
                },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Añadir Extendido") },
                text = { Text("Crear Nuevo") },
                expanded = fabExpanded
            )
        }

        // 4. Selector Segmentado
        Text("Selector Segmentado:")
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            opcionesSegmentadas.forEachIndexed { index, texto ->
                SegmentedButton(
                    selected = opcionSeleccionada == index,
                    onClick = {
                        opcionSeleccionada = index
                        mostrarMensaje("Seleccionaste: $texto")
                    },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = opcionesSegmentadas.size)
                ) {
                    Text(texto)
                }
            }
        }

        // 5. Botones de Estado (Deshabilitado y Carga)
        Button(
            onClick = { },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Botón Deshabilitado")
        }

        Button(
            onClick = {
                if (!isLoading) {
                    isLoading = true
                    coroutineScope.launch {
                        delay(2000) // Simula una tarea pesada de 2 segundos
                        isLoading = false
                        mostrarMensaje("Carga completada")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cargando...")
            } else {
                Text("Iniciar Carga")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionScreen() {
    val context = LocalContext.current

    // 1. Estados de Casillas de Verificación
    var checkedNormal by remember { mutableStateOf(false) }
    var triState by remember { mutableStateOf(androidx.compose.ui.state.ToggleableState.Indeterminate) }

    // 2. Estados de Radio Buttons
    val radioOptions = listOf("Modo Claro", "Modo Oscuro", "Automático")
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }

    // 3. Estado de Interruptor
    var switchChecked by remember { mutableStateOf(false) }

    // 4. Estados de Sliders
    var sliderPosition by remember { mutableStateOf(50f) }
    var rangeSliderPosition by remember { mutableStateOf(20f..80f) }

    // 5. Estados de Lista Desplegable
    val opcionesJuegos = listOf("Celeste", "Stardew Valley", "Fortnite")
    var dropdownExpanded by remember { mutableStateOf(false) }
    var juegoSeleccionado by remember { mutableStateOf(opcionesJuegos[0]) }

    // 6. Estados de Pickers (Fecha y Hora)
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(initialHour = 12, initialMinute = 0)

    // 7. Estados de Chips de Filtro
    var chipOpenCV by remember { mutableStateOf(false) }
    var chipPydicom by remember { mutableStateOf(false) }
    var chipNumpy by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("3. Elementos de Selección", style = MaterialTheme.typography.titleLarge)

        // 1. Casillas de Verificación
        Text("Casillas de verificación:", fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checkedNormal, onCheckedChange = { checkedNormal = it })
            Text("Opción estándar")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            TriStateCheckbox(
                state = triState,
                onClick = {
                    triState = when (triState) {
                        androidx.compose.ui.state.ToggleableState.On -> androidx.compose.ui.state.ToggleableState.Off
                        androidx.compose.ui.state.ToggleableState.Off -> androidx.compose.ui.state.ToggleableState.Indeterminate
                        androidx.compose.ui.state.ToggleableState.Indeterminate -> androidx.compose.ui.state.ToggleableState.On
                    }
                }
            )
            Text("Opción con estado indeterminado")
        }

        Divider()

        // 2. RadioGroup (Botones mutuamente excluyentes)
        Text("Botones mutuamente excluyentes:", fontWeight = FontWeight.Bold)
        radioOptions.forEach { text ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedOption = text }
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = { selectedOption = text }
                )
                Text(text = text, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Divider()

        // 3. Interruptor (Switch)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Activar notificaciones", fontWeight = FontWeight.Bold)
            Switch(
                checked = switchChecked,
                onCheckedChange = { switchChecked = it }
            )
        }

        Divider()

        // 4. Deslizadores (Sliders)
        Text("Deslizador de valor único:", fontWeight = FontWeight.Bold)
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f
        )
        Text("Valor: ${sliderPosition.toInt()}")

        Spacer(modifier = Modifier.height(8.dp))

        Text("Deslizador de rango:", fontWeight = FontWeight.Bold)
        RangeSlider(
            value = rangeSliderPosition,
            onValueChange = { rangeSliderPosition = it },
            valueRange = 0f..100f
        )
        Text("Rango: ${rangeSliderPosition.start.toInt()} - ${rangeSliderPosition.endInclusive.toInt()}")

        Divider()

        // 5. Lista Desplegable
        Text("Lista desplegable:", fontWeight = FontWeight.Bold)
        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { dropdownExpanded = !dropdownExpanded }
        ) {
            OutlinedTextField(
                value = juegoSeleccionado,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                opcionesJuegos.forEach { juego ->
                    DropdownMenuItem(
                        text = { Text(juego) },
                        onClick = {
                            juegoSeleccionado = juego
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Divider()

        // 6. Selectores de Fecha y Hora
        Text("Selectores de Fecha y Hora:", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(onClick = { showDatePicker = true }, modifier = Modifier.weight(1f)) {
                Text("Elegir Fecha")
            }
            OutlinedButton(onClick = { showTimePicker = true }, modifier = Modifier.weight(1f)) {
                Text("Elegir Hora")
            }
        }

        // Lógica visual del DatePicker
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        Toast.makeText(context, "Fecha seleccionada", Toast.LENGTH_SHORT).show()
                    }) { Text("Aceptar") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // Lógica visual del TimePicker (Usando AlertDialog como contenedor)
        if (showTimePicker) {
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showTimePicker = false
                        Toast.makeText(context, "Hora: ${timePickerState.hour}:${timePickerState.minute}", Toast.LENGTH_SHORT).show()
                    }) { Text("Aceptar") }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) { Text("Cancelar") }
                },
                text = { TimePicker(state = timePickerState) }
            )
        }

        Divider()

        // 7. Chips de Filtro
        Text("Chips de filtro:", fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = chipOpenCV,
                onClick = { chipOpenCV = !chipOpenCV },
                label = { Text("OpenCV") }
            )
            FilterChip(
                selected = chipPydicom,
                onClick = { chipPydicom = !chipPydicom },
                label = { Text("pydicom") }
            )
            FilterChip(
                selected = chipNumpy,
                onClick = { chipNumpy = !chipNumpy },
                label = { Text("NumPy") }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListsScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Datos base (15 elementos)
    val datosOriginales = listOf(
        "Algoritmos Genéticos", "Optimización PSO", "Transformación Sigmoidal",
        "Ecualización de Histograma", "Sockets TCP", "Imágenes DICOM",
        "ATmega8535 (Ensamblador)", "Motor a Pasos", "Servomotor",
        "Enrutamiento Estático", "RIP v2", "Contenedores Docker",
        "Máquina Virtual Azure", "Stardew Valley", "Celeste"
    )

    // Estados
    var listaElementos by remember { mutableStateOf(datosOriginales) }
    var isRefreshing by remember { mutableStateOf(false) }

    // Estado para las pestañas y el paginador
    val titulosPestañas = listOf("Vertical", "Cuadrícula", "Encabezados")
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(pageCount = { titulosPestañas.size })

    Column(modifier = Modifier.fillMaxSize()) {
        // Pestañas (Tabs)
        TabRow(selectedTabIndex = pagerState.currentPage) {
            titulosPestañas.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    },
                    text = { Text(title) }
                )
            }
        }

        // Contenido deslizable entre pestañas
        androidx.compose.foundation.pager.HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> {
                    // PESTAÑA 1: Lista Vertical con PullToRefreshBox actualizado para Material 3
                    androidx.compose.material3.pulltorefresh.PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = {
                            isRefreshing = true
                            coroutineScope.launch {
                                delay(1000) // Simular recarga
                                listaElementos = datosOriginales
                                isRefreshing = false
                                Toast.makeText(context, "Lista actualizada", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        if (listaElementos.isEmpty()) {
                            // Estado Vacío (usamos verticalScroll para que el pull to refresh siga funcionando si está vacío)
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Vacío",
                                    modifier = Modifier.size(100.dp),
                                    tint = Color.Gray.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text("La lista está vacía", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(
                                    items = listaElementos,
                                    key = { it } // Clave única necesaria para la animación
                                ) { item ->
                                    val dismissState = rememberSwipeToDismissBoxState(
                                        confirmValueChange = { dismissValue ->
                                            if (dismissValue == SwipeToDismissBoxValue.EndToStart || dismissValue == SwipeToDismissBoxValue.StartToEnd) {
                                                listaElementos = listaElementos.filter { it != item }
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    )

                                    SwipeToDismissBox(
                                        state = dismissState,
                                        backgroundContent = {
                                            val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                                                MaterialTheme.colorScheme.errorContainer
                                            } else {
                                                Color.Transparent
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(color, shape = MaterialTheme.shapes.medium)
                                                    .padding(horizontal = 16.dp),
                                                contentAlignment = Alignment.CenterEnd
                                            ) {
                                                if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.onErrorContainer)
                                                }
                                            }
                                        }
                                    ) {
                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    Toast.makeText(context, "Detalle de: $item", Toast.LENGTH_SHORT).show()
                                                }
                                        ) {
                                            Text(text = item, modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // PESTAÑA 2: Cuadrícula de elementos
                    androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
                        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listaElementos) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clickable { Toast.makeText(context, "Seleccionado: $item", Toast.LENGTH_SHORT).show() }
                            ) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text(item, modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // PESTAÑA 3: Lista con encabezados (Sticky Headers)
                    val categorias = mapOf(
                        "Visión por Computadora" to listOf("Ecualización de Histograma", "Transformación Sigmoidal", "Imágenes DICOM"),
                        "Redes y Despliegue" to listOf("Contenedores Docker", "Enrutamiento Estático", "Máquina Virtual Azure"),
                        "Videojuegos" to listOf("Stardew Valley", "Celeste")
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        categorias.forEach { (encabezado, items) ->
                            stickyHeader {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = encabezado.uppercase(),
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                            items(items) { item ->
                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // El Snackbar requiere un host y un estado para mostrarse correctamente
    val snackbarHostState = remember { SnackbarHostState() }

    // Estados para los diálogos y hojas inferiores
    var showDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    // Usamos un Scaffold interno solo para poder anclar el Snackbar en la parte inferior
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Textos con distintos estilos
            Text("5. Información y Retroalimentación", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Texto con énfasis (Cursiva y Negrita)",
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider()

            // 2. Tarjeta con Imágenes
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Imagen Local", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))

                        // SOLUCIÓN: Usar un ícono vectorial estándar de Compose en lugar del recurso del sistema
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "Local",
                            modifier = Modifier.size(100.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Desde URL (Fit)", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        AsyncImage(
                            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
                            contentDescription = "Imagen Remota",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            }

            // 3. Indicadores de Progreso
            Text("Lineal (Determinado e Indeterminado):", fontWeight = FontWeight.Bold)
            LinearProgressIndicator(progress = { 0.65f}, modifier = Modifier.fillMaxWidth())
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth()) // Indeterminado

            Text("Circular (Determinado e Indeterminado):", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                CircularProgressIndicator(progress = { 0.75f })
                CircularProgressIndicator() // Indeterminado
            }

            // Distintivo numérico (Badge)
            Text("Distintivo (Badge):", fontWeight = FontWeight.Bold)
            BadgedBox(
                badge = {
                    Badge { Text("9") }
                }
            ) {
                Icon(Icons.Filled.Email, contentDescription = "Correos", modifier = Modifier.size(48.dp))
            }

            HorizontalDivider()

            // 4. Retroalimentación y Diálogos
            Button(onClick = { android.widget.Toast.makeText(context, "Esto es un Toast breve", android.widget.Toast.LENGTH_SHORT).show() }, modifier = Modifier.fillMaxWidth()) {
                Text("Mostrar Toast")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "Archivo eliminado",
                            actionLabel = "DESHACER",
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            android.widget.Toast.makeText(context, "Acción deshecha", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mostrar Snackbar con Acción")
            }

            Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Mostrar Diálogo de Confirmación")
            }

            Button(onClick = { showBottomSheet = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Abrir Hoja Inferior (Bottom Sheet)")
            }
        }
    }

    // Lógica visual del Diálogo
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar Acción") },
            text = { Text("¿Estás seguro de que deseas continuar? Esta acción no se puede revertir.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    android.widget.Toast.makeText(context, "Aceptado", android.widget.Toast.LENGTH_SHORT).show()
                }) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            }
        )
    }

    // Lógica visual del Bottom Sheet
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Contenido de la Hoja Inferior (Bottom Sheet).", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Puedes arrastrarme hacia abajo para cerrarme.")
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StructureScreen() {
    val context = LocalContext.current

    // Estado para saber qué pestaña de la barra inferior está seleccionada
    var selectedBottomTab by remember { mutableStateOf(0) }

    // 1. Usamos un Scaffold interno para demostrar las barras superior e inferior
    // independientemente de la navegación principal de la app
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Demo Barra Superior") },
                actions = {
                    IconButton(onClick = { android.widget.Toast.makeText(context, "Buscar", android.widget.Toast.LENGTH_SHORT).show() }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = { android.widget.Toast.makeText(context, "Ajustes", android.widget.Toast.LENGTH_SHORT).show() }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Ajustes")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = selectedBottomTab == 0,
                    onClick = {
                        selectedBottomTab = 0
                        android.widget.Toast.makeText(context, "Pestaña Inicio", android.widget.Toast.LENGTH_SHORT).show()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
                    label = { Text("Favoritos") },
                    selected = selectedBottomTab == 1,
                    onClick = {
                        selectedBottomTab = 1
                        android.widget.Toast.makeText(context, "Pestaña Favoritos", android.widget.Toast.LENGTH_SHORT).show()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = selectedBottomTab == 2,
                    onClick = {
                        selectedBottomTab = 2
                        android.widget.Toast.makeText(context, "Pestaña Perfil", android.widget.Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    ) { paddingValues ->

        // 2. Contenedor con desplazamiento vertical (ScrollView equivalente)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // 3. Distribución en Columna (ya estamos dentro de una)
            Text("Distribución en Fila y Pesos (Row / Column)", fontWeight = FontWeight.Bold)

            // 4. Distribución en Fila y Pesos Proporcionales
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f) // Ocupa 1 parte del espacio
                        .height(60.dp)
                        .background(Color(0xFFFFCDD2)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Peso 1", color = Color.Black)
                }
                Box(
                    modifier = Modifier
                        .weight(2f) // Ocupa el doble (2 partes)
                        .height(60.dp)
                        .background(Color(0xFFC8E6C9)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Peso 2 (Doble)", color = Color.Black)
                }
            }

            Text("Distribución Superpuesta (Box)", fontWeight = FontWeight.Bold)

            // 5. Distribución Superpuesta (FrameLayout equivalente)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFFBBDEFB))
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF1565C0))
                        .align(Alignment.Center), // Centrado en el fondo
                    contentAlignment = Alignment.Center
                ) {
                    Text("Fondo", color = Color.White)
                }

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color(0xFFFF8F00))
                        .align(Alignment.BottomEnd) // Superpuesto en la esquina inferior derecha
                        .offset(x = (-16).dp, y = (-16).dp), // Margen manual para separarlo de la orilla
                    contentAlignment = Alignment.Center
                ) {
                    Text("Frente", color = Color.White, fontSize = 12.sp)
                }
            }

            Text("Ejemplo con Pesos en Botones", fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { }, modifier = Modifier.weight(0.3f)) {
                    Text("30%")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { }, modifier = Modifier.weight(0.7f)) {
                    Text("70%")
                }
            }
        }
    }
}
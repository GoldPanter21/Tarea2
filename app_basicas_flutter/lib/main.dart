import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Catálogo UI Flutter',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => const HomeScreen(),
        '/input': (context) => const InputScreen(),
        '/buttons': (context) => const ButtonsScreen(),
        '/selection': (context) => const SelectionScreen(),
        '/lists': (context) => const ListsScreen(),
        '/info': (context) => const InfoScreen(),
        '/structure': (context) => const StructureScreen(),
      },
    );
  }
}

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Catálogo de UI', style: TextStyle(color: Colors.white)),
        backgroundColor: Theme.of(context).colorScheme.primary,
      ),
      body: Center(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              ElevatedButton(
                onPressed: () => Navigator.pushNamed(context, '/input'),
                child: const Text('1. Entrada de Texto'),
              ),
              const SizedBox(height: 12),
              ElevatedButton(
                onPressed: () => Navigator.pushNamed(context, '/buttons'),
                child: const Text('2. Botones y Acciones'),
              ),
              const SizedBox(height: 12),
              ElevatedButton(
                onPressed: () => Navigator.pushNamed(context, '/selection'),
                child: const Text('3. Elementos de Selección'),
              ),
              const SizedBox(height: 12),
              ElevatedButton(
                onPressed: () => Navigator.pushNamed(context, '/lists'),
                child: const Text('4. Listas y Colecciones'),
              ),
              const SizedBox(height: 12),
              ElevatedButton(
                onPressed: () => Navigator.pushNamed(context, '/info'),
                child: const Text('5. Información y Retroalimentación'),
              ),
              const SizedBox(height: 12),
              ElevatedButton(
                onPressed: () => Navigator.pushNamed(context, '/structure'),
                child: const Text('6. Contenedores'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

// ==========================================
// 1. PANTALLA DE ENTRADA DE TEXTO
// ==========================================
class InputScreen extends StatefulWidget {
  const InputScreen({super.key});

  @override
  State<InputScreen> createState() => _InputScreenState();
}

class _InputScreenState extends State<InputScreen> {
  final TextEditingController _simpleController = TextEditingController();
  final TextEditingController _numericController = TextEditingController();
  final TextEditingController _multilineController = TextEditingController();
  final TextEditingController _searchController = TextEditingController();

  String _textoError = "";
  bool _passwordVisible = false;
  
  final List<String> _opciones = ["Views (XML)", "Jetpack Compose", "Flutter", "Kotlin Native"];
  String? _opcionSeleccionada;

  @override
  void dispose() {
    _simpleController.dispose();
    _numericController.dispose();
    _multilineController.dispose();
    _searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('1. Entrada de Texto', style: TextStyle(color: Colors.white)),
        backgroundColor: Theme.of(context).colorScheme.primary,
        iconTheme: const IconThemeData(color: Colors.white),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            TextField(
              controller: _simpleController,
              decoration: const InputDecoration(
                labelText: 'Campo simple',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 16),
            TextField(
              onChanged: (value) {
                setState(() {
                  _textoError = value;
                });
              },
              decoration: InputDecoration(
                labelText: 'Validador (mínimo 5 letras)',
                border: const OutlineInputBorder(),
                errorText: (_textoError.isNotEmpty && _textoError.length < 5) 
                    ? 'Faltan caracteres' 
                    : null,
              ),
            ),
            const SizedBox(height: 16),
            TextField(
              obscureText: !_passwordVisible,
              keyboardType: TextInputType.visiblePassword,
              decoration: InputDecoration(
                labelText: 'Contraseña',
                border: const OutlineInputBorder(),
                suffixIcon: IconButton(
                  icon: Icon(
                    _passwordVisible ? Icons.visibility : Icons.visibility_off,
                  ),
                  onPressed: () {
                    setState(() {
                      _passwordVisible = !_passwordVisible;
                    });
                  },
                ),
              ),
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _numericController,
              keyboardType: TextInputType.number,
              decoration: const InputDecoration(
                labelText: 'Teclado Numérico',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _multilineController,
              minLines: 3,
              maxLines: 5,
              decoration: const InputDecoration(
                labelText: 'Comentarios (Multilínea)',
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 16),
            DropdownButtonFormField<String>(
              decoration: const InputDecoration(
                labelText: 'Selecciona una tecnología',
                border: OutlineInputBorder(),
              ),
              value: _opcionSeleccionada,
              items: _opciones.map((String opcion) {
                return DropdownMenuItem<String>(
                  value: opcion,
                  child: Text(opcion),
                );
              }).toList(),
              onChanged: (String? newValue) {
                setState(() {
                  _opcionSeleccionada = newValue;
                });
              },
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _searchController,
              decoration: InputDecoration(
                labelText: 'Buscar elemento...',
                prefixIcon: const Icon(Icons.search),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(30.0),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class ButtonsScreen extends StatefulWidget {
  const ButtonsScreen({super.key});

  @override
  State<ButtonsScreen> createState() => _ButtonsScreenState();
}

class _ButtonsScreenState extends State<ButtonsScreen> {
  // Estados para controlar los botones
  bool _isLoading = false;
  bool _fabExpanded = true;
  int _opcionSeleccionada = 0;

  // En Flutter el equivalente al Toast es un SnackBar
  void _mostrarMensaje(String mensaje) {
    ScaffoldMessenger.of(context).clearSnackBars(); // Limpia los mensajes previos rápido
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(mensaje),
        duration: const Duration(seconds: 1),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('2. Botones y Acciones', style: TextStyle(color: Colors.white)),
        backgroundColor: Theme.of(context).colorScheme.primary,
        iconTheme: const IconThemeData(color: Colors.white),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // 1. Botones Básicos
            ElevatedButton(
              onPressed: () => _mostrarMensaje('Botón Relleno pulsado'),
              child: const Text('Botón Relleno'),
            ),
            const SizedBox(height: 12),
            
            OutlinedButton(
              onPressed: () => _mostrarMensaje('Botón con Contorno pulsado'),
              child: const Text('Botón con Contorno'),
            ),
            const SizedBox(height: 12),
            
            TextButton(
              onPressed: () => _mostrarMensaje('Botón de Solo Texto pulsado'),
              child: const Text('Botón de Solo Texto'),
            ),
            const SizedBox(height: 24),

            // 2. Botones con Ícono (Combinando Fila)
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                ElevatedButton.icon(
                  onPressed: () => _mostrarMensaje('Enviar pulsado'),
                  icon: const Icon(Icons.send),
                  label: const Text('Enviar'),
                ),
                IconButton(
                  iconSize: 32,
                  style: IconButton.styleFrom(
                    backgroundColor: Theme.of(context).colorScheme.secondaryContainer,
                  ),
                  icon: const Icon(Icons.send),
                  onPressed: () => _mostrarMensaje('Solo ícono pulsado'),
                ),
              ],
            ),
            const SizedBox(height: 24),

            // 3. Botones de Acción Flotante (FAB)
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                FloatingActionButton(
                  heroTag: 'fab1', // Evita errores de animación cuando hay más de un FAB
                  onPressed: () => _mostrarMensaje('FAB Normal pulsado'),
                  child: const Icon(Icons.add),
                ),
                FloatingActionButton.extended(
                  heroTag: 'fab2',
                  isExtended: _fabExpanded,
                  onPressed: () {
                    setState(() {
                      _fabExpanded = !_fabExpanded;
                    });
                    _mostrarMensaje(_fabExpanded ? 'FAB Extendido' : 'FAB Colapsado');
                  },
                  icon: const Icon(Icons.add),
                  label: const Text('Crear Nuevo'),
                ),
              ],
            ),
            const SizedBox(height: 24),

            // 4. Selector Segmentado (Nuevo componente de Material 3)
            const Text('Selector Segmentado:', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            SegmentedButton<int>(
              segments: const [
                ButtonSegment(value: 0, label: Text('Opción 1')),
                ButtonSegment(value: 1, label: Text('Opción 2')),
                ButtonSegment(value: 2, label: Text('Opción 3')),
              ],
              selected: {_opcionSeleccionada}, // Flutter requiere un Set {} para la selección
              onSelectionChanged: (Set<int> newSelection) {
                setState(() {
                  _opcionSeleccionada = newSelection.first;
                });
                _mostrarMensaje('Seleccionaste: Opción ${_opcionSeleccionada + 1}');
              },
            ),
            const SizedBox(height: 24),

            // 5. Botones de Estado (Deshabilitado y Carga asíncrona)
            const ElevatedButton(
              onPressed: null, // Asignar null deshabilita el botón visualmente de forma automática
              child: Text('Botón Deshabilitado'),
            ),
            const SizedBox(height: 12),
            
            ElevatedButton(
              onPressed: _isLoading
                  ? null
                  : () async {
                      setState(() {
                        _isLoading = true;
                      });
                      
                      // Simulamos una tarea pesada de red de 2 segundos
                      await Future.delayed(const Duration(seconds: 2));
                      
                      setState(() {
                        _isLoading = false;
                      });
                      _mostrarMensaje('Carga completada');
                    },
              child: _isLoading
                  ? const SizedBox(
                      height: 20,
                      width: 20,
                      child: CircularProgressIndicator(
                        strokeWidth: 2,
                      ),
                    )
                  : const Text('Iniciar Carga'),
            ),
          ],
        ),
      ),
    );
  }
}

class SelectionScreen extends StatefulWidget {
  const SelectionScreen({super.key});

  @override
  State<SelectionScreen> createState() => _SelectionScreenState();
}

class _SelectionScreenState extends State<SelectionScreen> {
  // 1. Estados de Casillas de Verificación
  bool _checkedNormal = false;
  bool? _checkedTriState; // En Flutter, 'null' representa el estado indeterminado

  // 2. Estados de Radio Buttons
  final List<String> _radioOptions = ["Modo Claro", "Modo Oscuro", "Automático"];
  String _selectedRadio = "Modo Claro";

  // 3. Estado de Interruptor
  bool _switchChecked = false;

  // 4. Estados de Sliders
  double _sliderPosition = 50.0;
  RangeValues _rangeSliderPosition = const RangeValues(20.0, 80.0);

  // 5. Estado de Lista Desplegable
  final List<String> _opcionesJuegos = ["Celeste", "Stardew Valley", "Fortnite"];
  String _juegoSeleccionado = "Celeste";

  // 6. Estados de Chips
  bool _chipOpenCV = false;
  bool _chipPydicom = false;
  bool _chipNumpy = false;

  // Funciones asíncronas para Selectores de Fecha y Hora
  Future<void> _seleccionarFecha() async {
    final DateTime? fecha = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2100),
    );
    if (fecha != null && mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Fecha: ${fecha.day}/${fecha.month}/${fecha.year}')),
      );
    }
  }

  Future<void> _seleccionarHora() async {
    final TimeOfDay? hora = await showTimePicker(
      context: context,
      initialTime: TimeOfDay.now(),
    );
    if (hora != null && mounted) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Hora: ${hora.hour}:${hora.minute}')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('3. Elementos de Selección', style: TextStyle(color: Colors.white)),
        backgroundColor: Theme.of(context).colorScheme.primary,
        iconTheme: const IconThemeData(color: Colors.white),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // 1. Casillas de Verificación
            const Text("Casillas de verificación:", style: TextStyle(fontWeight: FontWeight.bold)),
            Row(
              children: [
                Checkbox(
                  value: _checkedNormal,
                  onChanged: (bool? value) {
                    setState(() {
                      _checkedNormal = value ?? false;
                    });
                  },
                ),
                const Text("Opción estándar"),
              ],
            ),
            Row(
              children: [
                Checkbox(
                  tristate: true,
                  value: _checkedTriState,
                  onChanged: (bool? value) {
                    setState(() {
                      _checkedTriState = value;
                    });
                  },
                ),
                const Text("Opción con estado indeterminado"),
              ],
            ),
            const Divider(height: 32),

            // 2. Radio Buttons (ListTile incluye el texto clickeable por defecto)
            const Text("Botones mutuamente excluyentes:", style: TextStyle(fontWeight: FontWeight.bold)),
            ..._radioOptions.map((String texto) {
              return RadioListTile<String>(
                title: Text(texto),
                value: texto,
                groupValue: _selectedRadio,
                contentPadding: EdgeInsets.zero,
                onChanged: (String? value) {
                  setState(() {
                    _selectedRadio = value!;
                  });
                },
              );
            }),
            const Divider(height: 32),

            // 3. Switch
            SwitchListTile(
              title: const Text("Activar notificaciones", style: TextStyle(fontWeight: FontWeight.bold)),
              value: _switchChecked,
              contentPadding: EdgeInsets.zero,
              onChanged: (bool value) {
                setState(() {
                  _switchChecked = value;
                });
              },
            ),
            const Divider(height: 32),

            // 4. Sliders
            const Text("Deslizador de valor único:", style: TextStyle(fontWeight: FontWeight.bold)),
            Slider(
              value: _sliderPosition,
              min: 0,
              max: 100,
              divisions: 100,
              label: _sliderPosition.round().toString(),
              onChanged: (double value) {
                setState(() {
                  _sliderPosition = value;
                });
              },
            ),
            Text("Valor: ${_sliderPosition.round()}", textAlign: TextAlign.center),
            const SizedBox(height: 16),

            const Text("Deslizador de rango:", style: TextStyle(fontWeight: FontWeight.bold)),
            RangeSlider(
              values: _rangeSliderPosition,
              min: 0,
              max: 100,
              divisions: 100,
              labels: RangeLabels(
                _rangeSliderPosition.start.round().toString(),
                _rangeSliderPosition.end.round().toString(),
              ),
              onChanged: (RangeValues values) {
                setState(() {
                  _rangeSliderPosition = values;
                });
              },
            ),
            Text("Rango: ${_rangeSliderPosition.start.round()} - ${_rangeSliderPosition.end.round()}", textAlign: TextAlign.center),
            const Divider(height: 32),

            // 5. Lista Desplegable (DropdownMenu - Estilo Material 3)
            const Text("Lista desplegable:", style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            DropdownMenu<String>(
              initialSelection: _juegoSeleccionado,
              expandedInsets: EdgeInsets.zero, // Esta es la nueva forma de expandirlo
              onSelected: (String? value) {
                setState(() {
                  _juegoSeleccionado = value!;
                });
              },
  dropdownMenuEntries: _opcionesJuegos.map<DropdownMenuEntry<String>>((String value) {
    return DropdownMenuEntry<String>(value: value, label: value);
  }).toList(),
),
            const Divider(height: 32),

            // 6. Pickers (Fecha y Hora)
            const Text("Selectores de Fecha y Hora:", style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Row(
              children: [
                Expanded(
                  child: OutlinedButton(
                    onPressed: _seleccionarFecha,
                    child: const Text("Elegir Fecha"),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  child: OutlinedButton(
                    onPressed: _seleccionarHora,
                    child: const Text("Elegir Hora"),
                  ),
                ),
              ],
            ),
            const Divider(height: 32),

            // 7. Chips de Filtro
            const Text("Chips de filtro:", style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Wrap(
              spacing: 8.0, // Espacio horizontal entre chips
              runSpacing: 4.0, // Espacio vertical si saltan de línea
              children: [
                FilterChip(
                  label: const Text('OpenCV'),
                  selected: _chipOpenCV,
                  onSelected: (bool value) {
                    setState(() {
                      _chipOpenCV = value;
                    });
                  },
                ),
                FilterChip(
                  label: const Text('pydicom'),
                  selected: _chipPydicom,
                  onSelected: (bool value) {
                    setState(() {
                      _chipPydicom = value;
                    });
                  },
                ),
                FilterChip(
                  label: const Text('NumPy'),
                  selected: _chipNumpy,
                  onSelected: (bool value) {
                    setState(() {
                      _chipNumpy = value;
                    });
                  },
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

// ==========================================
// 4. LISTAS Y COLECCIONES
// ==========================================
class ListsScreen extends StatefulWidget {
  const ListsScreen({super.key});

  @override
  State<ListsScreen> createState() => _ListsScreenState();
}

class _ListsScreenState extends State<ListsScreen> {
  // Datos base
  final List<String> _datosOriginales = [
    "Algoritmos Genéticos", "Optimización PSO", "Transformación Sigmoidal",
    "Ecualización de Histograma", "Sockets TCP", "Imágenes DICOM",
    "ATmega8535 (Ensamblador)", "Motor a Pasos", "Servomotor",
    "Enrutamiento Estático", "RIP v2", "Contenedores Docker",
    "Máquina Virtual Azure", "Stardew Valley", "Celeste"
  ];

  late List<String> _listaElementos;

  @override
  void initState() {
    super.initState();
    _listaElementos = List.from(_datosOriginales);
  }

  // Lógica de Pull-to-Refresh
  Future<void> _recargarLista() async {
    await Future.delayed(const Duration(seconds: 1)); // Simula red
    setState(() {
      _listaElementos = List.from(_datosOriginales);
    });
    if (mounted) {
      ScaffoldMessenger.of(context).clearSnackBars();
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Lista actualizada')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    // Definimos las categorías para la pestaña 3
    final Map<String, List<String>> categorias = {
      "Visión por Computadora": ["Ecualización de Histograma", "Transformación Sigmoidal", "Imágenes DICOM"],
      "Redes y Despliegue": ["Contenedores Docker", "Enrutamiento Estático", "Máquina Virtual Azure"],
      "Videojuegos": ["Stardew Valley", "Celeste"],
    };

    // DefaultTabController maneja el estado de las pestañas automáticamente
    return DefaultTabController(
      length: 3,
      child: Scaffold(
        appBar: AppBar(
          title: const Text('4. Listas y Colecciones', style: TextStyle(color: Colors.white)),
          backgroundColor: Theme.of(context).colorScheme.primary,
          iconTheme: const IconThemeData(color: Colors.white),
          bottom: const TabBar(
            labelColor: Colors.white,
            unselectedLabelColor: Colors.white70,
            indicatorColor: Colors.white,
            tabs: [
              Tab(text: "Vertical"),
              Tab(text: "Cuadrícula"),
              Tab(text: "Encabezados"),
            ],
          ),
        ),
        body: TabBarView(
          children: [
            // ==========================================
            // PESTAÑA 1: Lista Vertical con Pull-to-Refresh y Swipe-to-Dismiss
            // ==========================================
            RefreshIndicator(
              onRefresh: _recargarLista,
              child: _listaElementos.isEmpty
                  ? CustomScrollView( // Usamos CustomScrollView para que el RefreshIndicator funcione aún vacío
                      slivers: [
                        SliverFillRemaining(
                          child: Center(
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Icon(Icons.search_off, size: 100, color: Colors.grey.withOpacity(0.5)),
                                const SizedBox(height: 16),
                                const Text("La lista está vacía", style: TextStyle(color: Colors.grey, fontSize: 18)),
                              ],
                            ),
                          ),
                        ),
                      ],
                    )
                  : ListView.builder(
                      padding: const EdgeInsets.all(8.0),
                      itemCount: _listaElementos.length,
                      itemBuilder: (context, index) {
                        final item = _listaElementos[index];
                        return Dismissible(
                          // Key es obligatoria para que Flutter sepa qué elemento exacto se eliminó
                          key: Key(item),
                          direction: DismissDirection.endToStart,
                          onDismissed: (direction) {
                            setState(() {
                              _listaElementos.removeAt(index);
                            });
                          },
                          background: Container(
                            alignment: Alignment.centerRight,
                            padding: const EdgeInsets.only(right: 20.0),
                            color: Theme.of(context).colorScheme.errorContainer,
                            child: Icon(Icons.delete, color: Theme.of(context).colorScheme.onErrorContainer),
                          ),
                          child: Card(
                            child: ListTile(
                              title: Text(item, style: const TextStyle(fontWeight: FontWeight.bold)),
                              onTap: () {
                                ScaffoldMessenger.of(context).clearSnackBars();
                                ScaffoldMessenger.of(context).showSnackBar(
                                  SnackBar(content: Text('Detalle de: $item'), duration: const Duration(seconds: 1)),
                                );
                              },
                            ),
                          ),
                        );
                      },
                    ),
            ),

            // ==========================================
            // PESTAÑA 2: Cuadrícula (Grid)
            // ==========================================
            GridView.builder(
              padding: const EdgeInsets.all(16.0),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 2, // 2 columnas
                crossAxisSpacing: 12.0,
                mainAxisSpacing: 12.0,
                childAspectRatio: 2.5, // Proporción ancho/alto de las tarjetas
              ),
              itemCount: _listaElementos.length,
              itemBuilder: (context, index) {
                final item = _listaElementos[index];
                return Card(
                  child: InkWell(
                    onTap: () {
                      ScaffoldMessenger.of(context).clearSnackBars();
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(content: Text('Seleccionado: $item'), duration: const Duration(seconds: 1)),
                      );
                    },
                    child: Center(
                      child: Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Text(item, textAlign: TextAlign.center, style: const TextStyle(fontWeight: FontWeight.bold)),
                      ),
                    ),
                  ),
                );
              },
            ),

            // ==========================================
            // PESTAÑA 3: Lista con encabezados (Sticky Headers) usando Slivers
            // ==========================================
            CustomScrollView(
              slivers: categorias.entries.map((entry) {
                return SliverMainAxisGroup(
                  slivers: [
                    SliverPersistentHeader(
                      pinned: true,
                      delegate: _StickyHeaderDelegate(
                        minHeight: 40.0,
                        maxHeight: 40.0,
                        child: Container(
                          color: Theme.of(context).colorScheme.primaryContainer,
                          alignment: Alignment.centerLeft,
                          padding: const EdgeInsets.symmetric(horizontal: 16.0),
                          child: Text(
                            entry.key.toUpperCase(),
                            style: TextStyle(
                              color: Theme.of(context).colorScheme.onPrimaryContainer,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ),
                    ),
                    SliverList(
                      delegate: SliverChildBuilderDelegate(
                        (context, index) {
                          return Column(
                            children: [
                              ListTile(title: Text(entry.value[index])),
                              const Divider(height: 1),
                            ],
                          );
                        },
                        childCount: entry.value.length,
                      ),
                    ),
                  ],
                );
              }).toList(),
            ),
          ],
        ),
      ),
    );
  }
}

class _StickyHeaderDelegate extends SliverPersistentHeaderDelegate {
  final double minHeight;
  final double maxHeight;
  final Widget child;

  _StickyHeaderDelegate({
    required this.minHeight,
    required this.maxHeight,
    required this.child,
  });

  @override
  double get minExtent => minHeight;

  @override
  double get maxExtent => maxHeight;

  @override
  Widget build(BuildContext context, double shrinkOffset, bool overlapsContent) {
    return SizedBox.expand(child: child);
  }

  @override
  bool shouldRebuild(_StickyHeaderDelegate oldDelegate) {
    return maxHeight != oldDelegate.maxHeight ||
        minHeight != oldDelegate.minHeight ||
        child != oldDelegate.child;
  }
}

class InfoScreen extends StatelessWidget {
  const InfoScreen({super.key});

  // Flutter usa ScaffoldMessenger para mostrar SnackBars en lugar del viejo Toast
  void _mostrarSnackBar(BuildContext context, String mensaje, {bool conAccion = false}) {
    ScaffoldMessenger.of(context).clearSnackBars();
    final snackBar = SnackBar(
      content: Text(mensaje),
      duration: const Duration(seconds: 2),
      action: conAccion
          ? SnackBarAction(
              label: 'DESHACER',
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Acción deshecha')),
                );
              },
            )
          : null,
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  // Función para mostrar el Diálogo de Alerta
  void _mostrarDialogo(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Confirmar Acción'),
          content: const Text('¿Estás seguro de que deseas continuar? Esta acción no se puede revertir.'),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(), // Cierra el diálogo
              child: const Text('Cancelar'),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Cierra el diálogo
                _mostrarSnackBar(context, 'Aceptado');
              },
              child: const Text('Aceptar'),
            ),
          ],
        );
      },
    );
  }

  // Función para mostrar la Hoja Inferior (Bottom Sheet)
  void _mostrarBottomSheet(BuildContext context) {
    showModalBottomSheet(
      context: context,
      builder: (BuildContext context) {
        return Padding(
          padding: const EdgeInsets.all(32.0),
          child: Column(
            mainAxisSize: MainAxisSize.min, // Ocupa solo el espacio necesario
            children: [
              Text(
                'Contenido de la Hoja Inferior (Bottom Sheet).',
                style: Theme.of(context).textTheme.titleMedium,
                textAlign: TextAlign.center,
              ),
              const SizedBox(height: 16),
              const Text('Puedes arrastrarme hacia abajo para cerrarme.'),
              const SizedBox(height: 32),
            ],
          ),
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('5. Información', style: TextStyle(color: Colors.white)),
        backgroundColor: Theme.of(context).colorScheme.primary,
        iconTheme: const IconThemeData(color: Colors.white),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            // 1. Textos
            Text('5. Información y Retroalimentación', style: Theme.of(context).textTheme.titleLarge),
            const SizedBox(height: 16),
            Text(
              'Texto con énfasis (Cursiva y Negrita)',
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontStyle: FontStyle.italic,
                color: Theme.of(context).colorScheme.primary,
              ),
            ),
            const Divider(height: 32),

            // 2. Tarjeta con Imágenes
            Card(
              elevation: 4,
              child: Padding(
                padding: const EdgeInsets.all(12.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Column(
                      children: [
                        Text('Ícono Local', style: Theme.of(context).textTheme.bodySmall),
                        const SizedBox(height: 8),
                        Icon(Icons.build, size: 80, color: Theme.of(context).colorScheme.primary),
                      ],
                    ),
                    Column(
                      children: [
                        Text('Desde URL (Fit)', style: Theme.of(context).textTheme.bodySmall),
                        const SizedBox(height: 8),
                        Image.network(
                          'https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png',
                          width: 80,
                          height: 80,
                          fit: BoxFit.contain,
                          // Si hay error al cargar, mostramos un ícono de error
                          errorBuilder: (context, error, stackTrace) => const Icon(Icons.error, size: 80),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            const Divider(height: 32),

            // 3. Indicadores de Progreso
            const Text('Lineal (Determinado e Indeterminado):', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 12),
            const LinearProgressIndicator(value: 0.65), // value de 0 a 1 lo hace determinado
            const SizedBox(height: 8),
            const LinearProgressIndicator(), // sin value es indeterminado
            const SizedBox(height: 24),

            const Text('Circular (Determinado e Indeterminado):', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 12),
            const Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                CircularProgressIndicator(value: 0.75),
                CircularProgressIndicator(),
              ],
            ),
            const SizedBox(height: 24),

            // Distintivo numérico (Badge)
            const Text('Distintivo (Badge):', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 12),
            const Align(
              alignment: Alignment.centerLeft,
              child: Badge(
                label: Text('9'),
                child: Icon(Icons.email, size: 48),
              ),
            ),
            const Divider(height: 32),

            // 4. Retroalimentación y Diálogos
            ElevatedButton(
              onPressed: () => _mostrarSnackBar(context, 'Esto es un SnackBar breve (Equivalente a Toast)'),
              child: const Text('Mostrar Toast (SnackBar)'),
            ),
            const SizedBox(height: 12),
            
            ElevatedButton(
              onPressed: () => _mostrarSnackBar(context, 'Archivo eliminado', conAccion: true),
              child: const Text('Mostrar SnackBar con Acción'),
            ),
            const SizedBox(height: 12),
            
            ElevatedButton(
              onPressed: () => _mostrarDialogo(context),
              child: const Text('Mostrar Diálogo de Confirmación'),
            ),
            const SizedBox(height: 12),
            
            ElevatedButton(
              onPressed: () => _mostrarBottomSheet(context),
              child: const Text('Abrir Hoja Inferior (Bottom Sheet)'),
            ),
          ],
        ),
      ),
    );
  }
}

class StructureScreen extends StatefulWidget {
  const StructureScreen({super.key});

  @override
  State<StructureScreen> createState() => _StructureScreenState();
}

class _StructureScreenState extends State<StructureScreen> {
  // Estado para saber qué pestaña de la barra inferior está seleccionada
  int _selectedBottomTab = 0;

  void _mostrarMensaje(String mensaje) {
    ScaffoldMessenger.of(context).clearSnackBars();
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(mensaje), duration: const Duration(seconds: 1)),
    );
  }

  @override
  Widget build(BuildContext context) {
    // Scaffold interno para demostrar las barras superior e inferior
    return Scaffold(
      appBar: AppBar(
        title: const Text('Demo Barra Superior', style: TextStyle(color: Colors.white)),
        backgroundColor: Theme.of(context).colorScheme.primaryContainer,
        foregroundColor: Theme.of(context).colorScheme.onPrimaryContainer,
        iconTheme: const IconThemeData(color: Colors.black87),
        actions: [
          IconButton(
            icon: const Icon(Icons.search),
            onPressed: () => _mostrarMensaje('Buscar'),
          ),
          IconButton(
            icon: const Icon(Icons.settings),
            onPressed: () => _mostrarMensaje('Ajustes'),
          ),
        ],
      ),
      bottomNavigationBar: NavigationBar(
        selectedIndex: _selectedBottomTab,
        onDestinationSelected: (int index) {
          setState(() {
            _selectedBottomTab = index;
          });
          final pestanas = ['Inicio', 'Favoritos', 'Perfil'];
          _mostrarMensaje('Pestaña ${pestanas[index]}');
        },
        destinations: const [
          NavigationDestination(
            icon: Icon(Icons.home_outlined),
            selectedIcon: Icon(Icons.home),
            label: 'Inicio',
          ),
          NavigationDestination(
            icon: Icon(Icons.favorite_outline),
            selectedIcon: Icon(Icons.favorite),
            label: 'Favoritos',
          ),
          NavigationDestination(
            icon: Icon(Icons.person_outline),
            selectedIcon: Icon(Icons.person),
            label: 'Perfil',
          ),
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const Text('Distribución en Fila y Pesos (Row / Column)', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),

            // Distribución en Fila y Pesos Proporcionales
            Row(
              children: [
                Expanded(
                  flex: 1, // Ocupa 1 parte del espacio
                  child: Container(
                    height: 60,
                    color: Colors.red[100],
                    alignment: Alignment.center,
                    child: const Text('Peso 1', style: TextStyle(color: Colors.black)),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  flex: 2, // Ocupa el doble (2 partes)
                  child: Container(
                    height: 60,
                    color: Colors.green[100],
                    alignment: Alignment.center,
                    child: const Text('Peso 2 (Doble)', style: TextStyle(color: Colors.black)),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 24),

            const Text('Distribución Superpuesta (Stack)', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),

            // Distribución Superpuesta (Equivalente a Box en Compose o FrameLayout en XML)
            Container(
              height: 150,
              width: double.infinity,
              color: Colors.blue[100],
              child: Stack(
                children: [
                  // Centrado en el fondo
                  Align(
                    alignment: Alignment.center,
                    child: Container(
                      width: 100,
                      height: 100,
                      color: Colors.blue[800],
                      alignment: Alignment.center,
                      child: const Text('Fondo', style: TextStyle(color: Colors.white)),
                    ),
                  ),
                  // Superpuesto en la esquina inferior derecha con Positioned
                  Positioned(
                    bottom: 16,
                    right: 16,
                    child: Container(
                      width: 60,
                      height: 60,
                      color: Colors.orange[800],
                      alignment: Alignment.center,
                      child: const Text('Frente', style: TextStyle(color: Colors.white, fontSize: 12)),
                    ),
                  ),
                ],
              ),
            ),
            const SizedBox(height: 24),

            const Text('Ejemplo con Pesos en Botones', style: TextStyle(fontWeight: FontWeight.bold)),
            const SizedBox(height: 16),
            Row(
              children: [
                Expanded(
                  flex: 3, // 30% del ancho
                  child: ElevatedButton(
                    onPressed: () {},
                    child: const Text('30%'),
                  ),
                ),
                const SizedBox(width: 8),
                Expanded(
                  flex: 7, // 70% del ancho
                  child: ElevatedButton(
                    onPressed: () {},
                    child: const Text('70%'),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
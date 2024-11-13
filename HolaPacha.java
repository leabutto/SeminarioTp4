import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class HolaPacha {
    private static final List<Administrador> administradores = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<Producto> productos = new ArrayList<>();
    private static final List<Inventario> inventarios = new ArrayList<>();
    private static final List<Venta> ventas = new ArrayList<>();
    private static final List<Proveedor> proveedores = new ArrayList<>();
    private static List<AlertaStock> alertas = new ArrayList<>();

    private static Connection conexion; // Mover conexion fuera de main para que sea accesible en otros métodos

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;


        try {
            // Conectar a la base de datos
            conexion = ConexionBd.conectarBD();

            if (conexion == null) {
                System.out.println("No se pudo establecer la conexion a la base de datos. El programa no puede continuar.");
                return;
            }
            conexion.setAutoCommit(false);

            do {
                System.out.println("=== MENÚ DE GESTION ===");
                System.out.println("________________________________");
                System.out.println("1. Gestionar Usuarios");
                System.out.println("2. Gestionar Productos");
                System.out.println("3. Gestionar Inventarios");
                System.out.println("4. Gestionar Ventas");
                System.out.println("5. Gestionar Proveedores");
                System.out.println("6. Gestionar Alertas de Stock");
                System.out.println("7. Gestionar Facturas");
                System.out.println("0. Salir");
                System.out.println("________________________________");
                System.out.print("Seleccione una opción: ");
                option = scanner.nextInt();

                switch (option) {
                    case 1 -> gestionarUsuarios(scanner);
                    case 2 -> gestionarProductos(scanner);
                    case 3 -> gestionarInventarios(scanner);
                    case 4 -> gestionarVentas(scanner);
                    case 5 -> gestionarProveedores(scanner);
                    case 6 -> gestionarAlertasStock(scanner);
                    case 7 -> System.out.println("Funcionalidad de gestión de facturas no implementada aún.");
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción no válida. Intente nuevamente.");
                }
            } while (option != 0);

        } catch (SQLException e) {
            System.out.println("Error al interactuar con la base de datos.");
            e.printStackTrace();

            // Hacer rollback en caso de error
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            scanner.close();
        }
    }

    private static void gestionarProveedores(Scanner scanner) {
        ProveedorDAO proveedorDAO = new ProveedorDAO(conexion);  // Pasa la conexión aquí
        int opcion;
        do {
            System.out.println("=== Gestión de Proveedores ===");
            System.out.println("1. Agregar Proveedor");
            System.out.println("2. Listar Proveedores");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> {
                    scanner.nextLine();  // limpiar el buffer
                    System.out.print("Ingrese el nombre del proveedor: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese el teléfono del proveedor: ");
                    int telefono = scanner.nextInt();
                    scanner.nextLine();  // limpiar el buffer
                    System.out.print("Ingrese la dirección del proveedor: ");
                    String direccion = scanner.nextLine();
                    System.out.print("Ingrese el email del proveedor: ");
                    String email = scanner.nextLine();

                    Proveedor proveedor = new Proveedor(0, nombre, telefono, direccion, email);
                    try {
                        proveedorDAO.insertarProveedor(proveedor);
                        System.out.println("Proveedor agregado correctamente.");
                    } catch (SQLException e) {
                        System.out.println("Error al agregar el proveedor: " + e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        List<Proveedor> proveedores = proveedorDAO.listarProveedores();
                        System.out.println("=== Lista de Proveedores ===");
                        for (Proveedor p : proveedores) {
                            System.out.println(p);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al listar proveedores: " + e.getMessage());
                    }
                }
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void listarProveedores() {
        System.out.println("=== Lista de Proveedores ===");
        for (Proveedor proveedor : proveedores) {
            System.out.println(proveedor);
        }
    }

    private static void agregarProveedor(Scanner scanner) {
        scanner.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el nombre del proveedor: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el contacto del proveedor: ");
        String contacto = scanner.nextLine();

        Proveedor proveedor = new Proveedor(proveedores.size() + 1, nombre, contacto);
        proveedores.add(proveedor);
        System.out.println("Proveedor agregado correctamente.");
    }

    private static void gestionarVentas(Scanner scanner) {
        int opcion;
        do {
            System.out.println("=== Gestión de Ventas ===");
            System.out.println("1. Registrar Venta");
            System.out.println("2. Listar Ventas");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarVenta(scanner);
                case 2 -> listarVentas();
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);

    }

    private static void listarVentas() {
        System.out.println("=== Lista de Ventas ===");
        for (Venta venta : ventas) {
            System.out.println(venta);
        }
    }

    private static void registrarVenta(Scanner scanner) {
        System.out.print("Ingrese el ID del producto vendido: ");
        int idProducto = scanner.nextInt();
        System.out.print("Ingrese la cantidad vendida: ");
        int cantidadVendida = scanner.nextInt();

        Producto producto = productos.stream().filter(p -> p.getIdProducto() == idProducto).findFirst().orElse(null);
        if (producto != null && producto.getCantidad() >= cantidadVendida) {
            producto.setCantidad(producto.getCantidad() - cantidadVendida);
            Venta venta = new Venta(ventas.size() + 1, producto, cantidadVendida, new Date());
            ventas.add(venta);
            System.out.println("Venta registrada correctamente.");
        } else {
            System.out.println("Producto no encontrado o cantidad insuficiente en inventario.");
        }
    }

    private static void gestionarInventarios(Scanner scanner) {
        int opcion;
        do {
            System.out.println("=== Gestión de Inventarios ===");
            System.out.println("1. Agregar Inventario");
            System.out.println("2. Listar Inventarios");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> agregarInventario(scanner);
                case 2 -> listarInventarios();
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void listarInventarios() {
        System.out.println("=== Lista de Inventarios ===");
        for (Inventario inventario : inventarios) {
            System.out.println(inventario);
        }
    }

    private static void agregarInventario(Scanner scanner) {
        InventarioDAO inventarioDAO = new InventarioDAO(conexion);
        int opcion;
        do {
            System.out.println("=== Gestión de Inventarios ===");
            System.out.println("1. Agregar Inventario");
            System.out.println("2. Listar Inventarios");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> {
                    agregarInventario(scanner, inventarioDAO);
                }
                case 2 -> {
                    listarInventarios(inventarioDAO);
                }
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void agregarInventario(Scanner scanner, InventarioDAO inventarioDAO) {
        System.out.print("Ingrese el ID del producto: ");
        int idProducto = scanner.nextInt();
        System.out.print("Ingrese la cantidad inicial en el inventario: ");
        int cantidadInicial = scanner.nextInt();

        Producto producto = productos.stream().filter(p -> p.getIdProducto() == idProducto).findFirst().orElse(null);
        if (producto != null) {
            Inventario inventario = new Inventario(0, producto, cantidadInicial);
            try {
                inventarioDAO.insertarInventario(inventario);
                System.out.println("Inventario agregado correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al agregar inventario: " + e.getMessage());
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void listarInventarios(InventarioDAO inventarioDAO) {
        try {
            List<Inventario> inventarios = inventarioDAO.listarInventarios();
            System.out.println("=== Lista de Inventarios ===");
            for (Inventario inventario : inventarios) {
                System.out.println(inventario);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar inventarios: " + e.getMessage());
        }
    }

    private static void gestionarAlertasStock(Scanner scanner) {
        int opcion;
        do {
            System.out.println("=== Gestión de Alertas de Stock ===");
            System.out.println("1. Crear Alerta de Stock Mínimo");
            System.out.println("2. Listar Alertas de Stock");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> crearAlertaStock(scanner);
                case 2 -> listarAlertasStock();
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void crearAlertaStock(Scanner scanner) {
        System.out.print("Ingrese el ID del producto para la alerta de stock: ");
        int idProducto = scanner.nextInt();
        System.out.print("Ingrese la cantidad mínima de stock para la alerta: ");
        int cantidadMinima = scanner.nextInt();

        Producto producto = productos.stream().filter(p -> p.getIdProducto() == idProducto).findFirst().orElse(null);
        if (producto != null) {
            AlertaStock alerta = new AlertaStock(alertas.size() + 1, producto, cantidadMinima);
            alertas.add(alerta);
            System.out.println("Alerta de stock creada correctamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void listarAlertasStock() {
        System.out.println("=== Lista de Alertas de Stock ===");
        for (AlertaStock alerta : alertas) {
            Producto producto = alerta.getProducto();
            if (producto.getCantidad() < alerta.getCantidadMinima()) {
                System.out.println("Alerta ID: " + alerta.getIdAlerta() +
                        ", Producto: " + producto.getNombre() +
                        ", Stock actual: " + producto.getCantidad() +
                        ", Stock mínimo requerido: " + alerta.getCantidadMinima());
            }
        }
    }

    private static void gestionarUsuarios(Scanner scanner) {
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexion);
        int opcion;
        do {
            System.out.println("=== Gestión de Usuarios ===");
            System.out.println("1. Agregar Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> {
                    scanner.nextLine();  // limpiar el buffer
                    System.out.print("Ingrese el nombre del usuario: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese el email del usuario: ");
                    String email = scanner.nextLine();
                    System.out.print("Ingrese la contraseña del usuario: ");
                    String password = scanner.nextLine();

                    Usuario usuario = new Usuario(0, nombre, email, password);
                    try {
                        usuarioDAO.insertarUsuario(usuario);
                        System.out.println("Usuario agregado correctamente.");
                    } catch (SQLException e) {
                        System.out.println("Error al agregar el usuario: " + e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
                        System.out.println("=== Lista de Usuarios ===");
                        for (Usuario u : usuarios) {
                            System.out.println(u);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al listar usuarios: " + e.getMessage());
                    }
                }
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void agregarUsuario(Scanner scanner) {
        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = scanner.next();
        System.out.print("Ingrese el email del usuario: ");
        String email = scanner.next();
        System.out.print("Ingrese la contraseña del usuario: ");
        String password = scanner.next();

        Usuario usuario = new Usuario(usuarios.size() + 1, nombre, email, password);
        usuarios.add(usuario);
        System.out.println("Usuario agregado correctamente.");
    }

    private static void listarUsuarios() {
        System.out.println("=== Lista de Usuarios ===");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private static void gestionarProductos(Scanner scanner) throws SQLException {
        ProductoDAO productoDAO = new ProductoDAO(conexion);  // Instancia de ProductoDAO con la conexión
        int opcion;
        do {
            System.out.println("=== Gestión de Productos ===");
            System.out.println("1. Agregar Producto");
            System.out.println("2. Listar Productos");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> agregarProducto(scanner, productoDAO);
                case 2 -> listarProductos(productoDAO);
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void agregarProducto(Scanner scanner, ProductoDAO productoDAO) throws SQLException {
        scanner.nextLine();  // Limpiar el buffer para evitar conflictos
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese la descripción del producto: ");
        String descripcion = scanner.nextLine();

        int cantidad = 0;
        while (true) {
            try {
                System.out.print("Ingrese la cantidad en stock: ");
                cantidad = scanner.nextInt();
                if (cantidad < 0) {
                    System.out.println("La cantidad no puede ser negativa. Intente nuevamente.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero para la cantidad.");
                scanner.next();
            }
        }

        float precioCompra = 0;
        while (true) {
            try {
                System.out.print("Ingrese el precio de compra: ");
                precioCompra = scanner.nextFloat();
                if (precioCompra < 0) {
                    System.out.println("El precio no puede ser negativo. Intente nuevamente.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número decimal para el precio de compra.");
                scanner.next();
            }
        }

        float precioVenta = 0;
        while (true) {
            try {
                System.out.print("Ingrese el precio de venta: ");
                precioVenta = scanner.nextFloat();
                if (precioVenta < 0) {
                    System.out.println("El precio no puede ser negativo. Intente nuevamente.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número decimal para el precio de venta.");
                scanner.next();
            }
        }

        // Crear una instancia de Producto y guardarla usando ProductoDAO
        Producto producto = new Producto(0, nombre, descripcion, cantidad, precioCompra, precioVenta);
        productoDAO.insertarProducto(producto);

    }

    private static void listarProductos(ProductoDAO productoDAO) {
        List<Producto> productos;
        productos = productoDAO.listarProductos();
        System.out.println("=== Lista de Productos ===");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

}

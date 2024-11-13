
public class Producto {
    private int idProducto;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private float precioCompra;
    private float precioVenta;

    public Producto(int idProducto, String nombre, String descripcion, int cantidad, float precioCompra, float precioVenta) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }

    public Producto(String nombre, String descripcion, int cantidad, float precioCompra, float precioVenta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
    }

    public Producto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setCantidad(int i) {
        this.cantidad = i;
    }

    public int getIdProducto() {
        return idProducto;
    }
}

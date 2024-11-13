public class Inventario {
    private int idInventario;
    private Producto producto;
    private int cantidad;

    public Inventario(int idInventario, Producto producto, int cantidad) {
        this.idInventario = idInventario;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "idInventario=" + idInventario +
                ", producto=" + producto.getNombre() +
                ", cantidad=" + cantidad +
                '}';
    }
}

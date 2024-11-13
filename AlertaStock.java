public class AlertaStock {
    private int idAlerta;
    private Producto producto;
    private int cantidadMinima;

    public AlertaStock(int idAlerta, Producto producto, int cantidadMinima) {
        this.idAlerta = idAlerta;
        this.producto = producto;
        this.cantidadMinima = cantidadMinima;
    }

    public int getIdAlerta() {
        return idAlerta;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public boolean verificarAlerta() {
        return producto.getCantidad() < cantidadMinima;
    }

    @Override
    public String toString() {
        return "AlertaStock{" +
                "idAlerta=" + idAlerta +
                ", producto=" + producto.getNombre() +
                ", cantidadMinima=" + cantidadMinima +
                '}';
    }
}

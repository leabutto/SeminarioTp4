import java.util.Date;
import java.util.List;

public class Venta {
    private int idVenta;
    private Date fecha;
    private float totalVenta;
    private List<Producto> productos;

    public Venta(int idVenta, Date fecha, float totalVenta, List<Producto> productos) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.totalVenta = totalVenta;
        this.productos = productos;
    }

    public Venta(int idVenta, Date fecha, float totalVenta) {
    }

    public Venta(int idVenta, Producto producto, int cantidadVendida, Date date) {
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public float getTotalVenta() {
        return totalVenta;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", fecha=" + fecha +
                ", totalVenta=" + totalVenta +
                ", productos=" + productos +
                '}';
    }
}

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Factura {
    private int idFactura;
    private Date fecha;
    private float total;
    private List<Producto> productos;

    public Factura(int idFactura, Date fecha, float total, List<Producto> productos) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.total = total;
        this.productos = productos;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public float getTotal() {
        return total;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", fecha=" + fecha +
                ", total=" + total +
                ", productos=" + productos +
                '}';
    }
}

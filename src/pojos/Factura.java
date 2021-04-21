package pojos;
import java.sql.Date;

public class Factura {
	
	private int id;
	private Date fecha;
	private Float importe;
	private boolean metodo_de_pago;
	
	public Factura() {
		super();
	}
	
	public Factura(Date fecha, Float importe, boolean metodo_de_pago) {
		super();
		this.fecha = fecha;
		this.importe = importe;
		this.metodo_de_pago = metodo_de_pago;
	}

	public Factura(int id, Date fecha, Float importe, boolean metodo_de_pago) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.importe = importe;
		this.metodo_de_pago = metodo_de_pago;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Float getImporte() {
		return importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	public boolean isMetodo_de_pago() {
		return metodo_de_pago;
	}

	public void setMetodo_de_pago(boolean metodo_de_pago) {
		this.metodo_de_pago = metodo_de_pago;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", importe=" + importe + ", metodo_de_pago=" + metodo_de_pago
				+ "]";
	}
	
	
}

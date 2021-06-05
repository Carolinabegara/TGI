package pojos;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Factura {
	@XmlAttribute
	private int id;
	

	private Date fecha;
	@XmlElement
	private Float importe;
	@XmlElement
	private boolean metodo_de_pago;
	
	@XmlTransient
	private Empleado empleado;
	@XmlTransient
	private Cliente cliente;
	@XmlElement(name = "producto")
	@XmlElementWrapper(name="productos")
	private List<Producto> productos;
	
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
	
	
 
	public Factura(int id, Date fecha, Float importe, boolean metodo_de_pago, Empleado empleado, Cliente cliente) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.importe = importe;
		this.metodo_de_pago = metodo_de_pago;
		this.empleado = empleado;
		this.cliente = cliente;
		this.productos = new ArrayList<>();
	}
	public Factura(Date fecha, Float importe, boolean metodo_de_pago,Cliente cliente) {
		super();
		this.fecha = fecha;
		this.importe = importe;
		this.metodo_de_pago = metodo_de_pago;
		this.cliente = cliente;
		this.productos = new ArrayList<>();
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
	
	public boolean getMetodo_de_pago() {
		return metodo_de_pago;
	}

	public void setMetodo_de_pago(boolean metodo_de_pago) {
		this.metodo_de_pago = metodo_de_pago;
	}
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", fecha=" + fecha + ", importe=" + importe + ", metodo_de_pago=" + metodo_de_pago
				+ "]";
	}
	
	
}

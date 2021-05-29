package pojos;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement


public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 8307842871655194072L;
	@XmlAttribute
	private int id;
	@XmlElement
	private String nombre;
	@XmlElement
	private int telefono;
	@XmlElement
	private String direccion;
	@XmlElement
	private String dni;
	@XmlElement(name = "factura")
	@XmlElementWrapper(name="facturas")
	private List<Factura> facturas;
	
	
	public Cliente() {
		super();
	}
	
	public Cliente(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		
	}
	
	public Cliente(String nombre, int telefono, String direccion, String dni) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.dni = dni;
	}
	
	public Cliente(int id, String nombre, int telefono, String direccion, String dni) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.dni = dni;
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion
				+ ", dni=" + dni + "]";
	}
	

}

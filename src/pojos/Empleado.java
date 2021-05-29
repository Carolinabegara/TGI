package pojos;
import java.sql.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import db.xml.SQLDateAdapter;

import java.sql.Blob;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement

public class Empleado {
	@XmlAttribute
	private int id;
	@XmlElement
	private String nombre;
	@XmlElement
	private int telefono;
	@XmlElement
	private String direccion;
	@XmlElement
	private String DNI;
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date fecha_Nac;
	@XmlElement
	private float sueldo;
	@XmlTransient
	//Ponemos byte[] en vez de BLOB porque SQLITE no nos permite trabajar con BLOB
	private byte[] foto;
	@XmlElement(name = "plantacion")
	@XmlElementWrapper(name="plantaciones")
	private List<Plantacion> plantaciones;
	
	@XmlElement(name = "animal")
	@XmlElementWrapper(name="animales")
	private List<Animal> animales;
	
	@XmlElement(name = "factura")
	@XmlElementWrapper(name="facturas")
	private List <Factura> facturas;
	
	public Empleado() {
		super();
	}
	public Empleado(String nombre, int telefono, String direccion, String dNI, Date fecha_Nac, float sueldo) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		DNI = dNI;
		this.fecha_Nac = fecha_Nac;
		this.sueldo = sueldo;
		
	}
	public Empleado(int id, String nombre, int telefono, String direccion, String dNI, Date fecha_Nac, float sueldo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		DNI = dNI;
		this.fecha_Nac = fecha_Nac;
		this.sueldo = sueldo;
		
	}
	public Empleado(int id, String nombre, int telefono, String direccion, String dNI, Date fecha_Nac, float sueldo,
			byte[] foto) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		DNI = dNI;
		this.fecha_Nac = fecha_Nac;
		this.sueldo = sueldo;
		this.foto = foto;
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
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public Date getFecha_Nac() {
		return fecha_Nac;
	}
	public void setFecha_Nac(Date fecha_Nac) {
		this.fecha_Nac = fecha_Nac;
	}
	public Float getSueldo() {
		return sueldo;
	}
	public void setSueldo(Float sueldo) {
		this.sueldo = sueldo;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion
				+ ", DNI=" + DNI + ", fecha_Nac=" + fecha_Nac + ", sueldo=" + sueldo + ", foto=" + foto + "]";
	}
	
	
}

package pojos;
import java.sql.Date;
import java.sql.Blob;

public class Empleado {
	
	int id;
	private String nombre;
	private int telefono;
	private String direccion;
	private String DNI;
	private Date fecha_Nac;
	private Float sueldo;
	private Blob foto;
	
	
	public Empleado() {
		super();
	}
	public Empleado(String nombre, int telefono, String direccion, String dNI, Date fecha_Nac, Float sueldo) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		DNI = dNI;
		this.fecha_Nac = fecha_Nac;
		this.sueldo = sueldo;
		
	}
	public Empleado(int id, String nombre, int telefono, String direccion, String dNI, Date fecha_Nac, Float sueldo,
			Blob foto) {
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
	public Blob getFoto() {
		return foto;
	}
	public void setFoto(Blob foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return "Empleado [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion
				+ ", DNI=" + DNI + ", fecha_Nac=" + fecha_Nac + ", sueldo=" + sueldo + ", foto=" + foto + "]";
	}
	
	
}

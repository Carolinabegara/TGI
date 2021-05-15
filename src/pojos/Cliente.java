package pojos;

import java.util.List;

public class Cliente {
	
	private int id;
	private String nombre;
	private int telefono;
	private String direccion;
	private String dni;
	
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

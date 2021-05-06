package pojos;

import java.util.List;

public class Producto {
	
	private int id;
	private String nombre;
	private int cantidad;
	private String tipo;
	private String unidades;
	private float precio;
	
	private List<Factura> facturas;
	private Animal animal;
	private Plantacion plantacion;
	
	public Producto() {
		super();
	}
	
	public Producto(int id, String nombre, int cantidad, String tipo, String unidades, float precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.tipo = tipo;
		this.unidades = unidades;
		this.precio = precio;
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

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", tipo=" + tipo + ", unidades="
				+ unidades + ", precio=" + precio + "]";
	}
	
}

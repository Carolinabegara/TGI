package pojos;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Producto {
	@XmlAttribute
	private int id;
	@XmlElement
	private String nombre;
	@XmlElement
	private int cantidad;
	@XmlElement
	private String tipo;
	@XmlElement
	private String unidades;
	@XmlElement
	private float precio;
	@XmlTransient
	private List<Factura> facturas;
	@XmlElement
	private Animal animal;
	@XmlElement
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
	
	public Producto(String nombre, int cantidad, String tipo, String unidades, float precio) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.tipo = tipo;
		this.unidades = unidades;
		this.precio = precio;
	}
	public Producto(String nombre, int cantidad, String tipo, String unidades, float precio,Animal animal) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.tipo = tipo;
		this.unidades = unidades;
		this.precio = precio;
		this.animal = animal;
	}
	public Producto(String nombre, int cantidad, String tipo, String unidades, float precio,
			Animal animal, Plantacion plantacion) {
		super();
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.tipo = tipo;
		this.unidades = unidades;
		this.precio = precio;
		this.animal = animal;
		this.plantacion = plantacion;
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

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Plantacion getPlantacion() {
		return plantacion;
	}

	public void setPlantacion(Plantacion plantacion) {
		this.plantacion = plantacion;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", tipo=" + tipo + ", unidades="
				+ unidades + ", precio=" + precio + "]";
	}
	
}

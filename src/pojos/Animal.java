package pojos;

import java.sql.Date;
import java.util.List;
import java.sql.Blob;

public class Animal {
	
	private int id;
	private String especie;
	private Date fecha_Nac;
	private byte[] foto;
	
	private List<Empleado> empleados;
	private List<Producto> productos;
	
	public Animal() {
		super();
	}
	
	public Animal(int id, String especie, Date fecha_Nac, byte[] foto) {
		super();
		this.id = id;
		this.especie = especie;
		this.fecha_Nac = fecha_Nac;
		this.foto = foto;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public Date getFecha_Nac() {
		return fecha_Nac;
	}
	public void setFecha_Nac(Date fecha_Nac) {
		this.fecha_Nac = fecha_Nac;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	@Override
	public String toString() {
		return "Animal [id=" + id + ", especie=" + especie + ", fecha_Nac=" + fecha_Nac + ", foto=" + foto + "]";
	}
	
	
}

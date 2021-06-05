package pojos;

import java.io.Serializable;
//import java.sql.Blob;
import java.sql.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import db.xml.SQLDateAdapter;


//Hay que poner esta línea cada vez que queramos anotar una clase para utilizar XML
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement


public class Animal implements Serializable {

	private static final long serialVersionUID = -1906453913375955622L;
	@XmlAttribute
	private int id;
	@XmlElement
	private String especie;
	@XmlElement
	private String peso;
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date fecha_Nac;
	@XmlTransient
	private byte[] foto;

	@XmlTransient
	private List<Empleado> empleados;
	@XmlTransient
	private List<Producto> productos;

	public Animal() {
		super();
	}

	public Animal(int id, String especie,String peso, Date fecha_Nac, byte[] foto) {
		super();
		this.id = id;
		this.especie = especie;
		this.peso = peso;
		this.fecha_Nac = fecha_Nac;
		this.foto = foto;
	}
	public Animal(int id, String especie,String peso, Date fecha_Nac) {
		super();
		this.id = id;
		this.especie = especie;
		this.peso = peso;
		this.fecha_Nac = fecha_Nac;
		
	}

	public Animal(String especie, String peso, Date fecha_Nac) {
		super();
		this.especie = especie;
		this.peso = peso;
		this.fecha_Nac = fecha_Nac;
		
	}
	public Animal(String especie, Date fecha_Nac) {
		super();
		this.especie = especie;
		this.fecha_Nac = fecha_Nac;
		
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
	

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", especie=" + especie + ", fecha_Nac=" + fecha_Nac + ", foto=" + foto + "]";
	}


}

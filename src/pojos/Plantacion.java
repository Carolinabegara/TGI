package pojos;
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Plantacion {
	@XmlAttribute
	private int id;
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date ultimo_regado;
	@XmlElement
	private float hectareas;
	@XmlTransient
	private List <Empleado> empleados;
	
	
	public Plantacion() {
		super();
	}
	
	public Plantacion(Date ultimo_regado, float hectareas) {
		super();
		this.ultimo_regado = ultimo_regado;
		this.hectareas = hectareas;
	}

	public Plantacion(int id, Date ultimo_regado, float hectareas) {
		super();
		this.id = id;
		this.ultimo_regado = ultimo_regado;
		this.hectareas = hectareas;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getUltimo_regado() {
		return ultimo_regado;
	}
	public void setUltimo_regado(Date ultimo_regado) {
		this.ultimo_regado = ultimo_regado;
	}
	public float getHectareas() {
		return hectareas;
	}
	public void setHectareas(float hectareas) {
		this.hectareas = hectareas;
	}
	
	@Override
	public String toString() {
		return "Plantación [id=" + id + ", ultimo_regado=" + ultimo_regado + ", hectareas=" + hectareas + "]";
	}
	
	
	
}

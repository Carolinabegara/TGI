package pojos;
import java.sql.Date;
import java.util.List;
public class Plantacion {
	
	private int id;
	private Date ultimo_regado;
	private float hectareas;
	
	private List <Empleado> empleados;
	private Producto producto;
	
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

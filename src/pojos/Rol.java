package pojos;
//Clase 5 de mayo

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="Roles")

public class Rol {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	//Se pone rol porque es como se llama el atributo de la otra entidad
	@OneToMany(mappedBy="rol")
	private List<Usuario> usuarios;

	public Rol() {
		super();
		usuarios = new ArrayList<>();
	}
	public Rol(String nombre) {
		super();
		this.nombre = nombre;
		usuarios = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void addUsuario(Usuario usuario) {
		if(!usuarios.contains(usuario)) {//Comprueba si el usuario ya existe, si no existe lo añade a la lista. 
			usuarios.add(usuario);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombre=" + nombre+"]";
	}



}

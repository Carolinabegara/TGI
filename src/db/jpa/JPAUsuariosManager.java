package db.jpa;

import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import db.interfaces.DBManager;
import db.interfaces.UsuariosManager;
import pojos.Rol;
import pojos.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

public class JPAUsuariosManager implements UsuariosManager {

private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private EntityManagerFactory factory;
	//Ponemos aquí fuera private EntityManager em para poder usarlo en todos los métodos. 
	private EntityManager em;
	private final String PERSISTENCE_PROVIDER = "granja-provider";

	@Override
	public void connect() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_PROVIDER);
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		if(getRoles().size() ==0) {//Si no existen los roles que los cree. 
			crearRoles();
		}

	}


	private void crearRoles() {
		Rol rol1 = new Rol("empleado");
		Rol rol2 = new Rol("cliente");
		addRol(rol1);
		addRol(rol2);

	}
	
	@Override
	public void addRol(Rol rol) {
		em.getTransaction().begin();//Sirve para añadir los nuevos roles a la base de datos.
		em.persist(rol);
		em.getTransaction().commit();

	}

	@Override
	public void disconnect() {
		em.close();
		factory.close();
	}

	@Override
	public List<Rol> getRoles() {
		Query q = em.createNativeQuery("SELECT * FROM Roles", Rol.class);
		return q.getResultList();
	}

	@Override
	public Rol getRolById(int rolId) {
		Query q = em.createNativeQuery("SELECT * FROM Roles WHERE id = ?", Rol.class);//Junto con la query especificamos que tipo de objeto nos va a devolver el createNativeQuery
		q.setParameter(1,rolId);
		//Tener cuidado si la id seleccionada por el usuario no existe va a saltar una excepción. 
		return (Rol) q.getSingleResult(); //¨DUDA: Por qué hay que hacer un cast a Rol que devuelve exactamente getSingleResult()

	}

	@Override
	public void addUsuario(Usuario usuario) {
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();

	}

	@Override
	public Usuario verifyPassword(String email, String password) {
		try {
			//Con estas tres líneas construimos el hash
			MessageDigest md = MessageDigest.getInstance("MD5");//nuestro algoritmo de cifrado se llama MD5
			md.update(password.getBytes());
			byte[] hash = md.digest();//Convertimos la contraseña en un array de bytes
			Query q = em.createNativeQuery("SELECT * FROM Usuarios WHERE email = ? AND password = ?", Usuario.class);//Junto con la query especificamos que tipo de objeto nos va a devolver el createNativeQuery
			q.setParameter(1,email);
			q.setParameter(2,hash);
			Usuario usuario = (Usuario) q.getSingleResult();
			return usuario;
		}catch(NoSuchAlgorithmException | NoResultException e) {
			return null;
		}
		
	}
	@Override
	public void updatePassword(Usuario usuario, String new_password) {
		try {
			
			em.getTransaction().begin();
			MessageDigest nuevo_md = MessageDigest.getInstance("MD5");//nuestro algoritmo de cifrado se llama MD5
			nuevo_md.update(new_password.getBytes());
			byte[] nuevo_hash = nuevo_md.digest();
			usuario.setPassword(nuevo_hash);
			em.getTransaction().commit();
		}catch(NoSuchAlgorithmException| NoResultException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void deleteUser(Usuario usuario) {
	
			em.getTransaction().begin();
			em.remove(usuario);
			em.getTransaction().commit();
		
		
	}
}


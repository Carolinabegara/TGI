package db.interfaces;

import java.util.List;

import pojos.Rol;
import pojos.Usuario;

public interface UsuariosManager {
	void connect();
	void disconnect();
	List<Rol> getRoles();
	Rol getRolById(int rolId);
	void addUsuario(Usuario usuario);
	void addRol(Rol rol);
	Usuario verifyPassword(String email, String password);
	void updatePassword(Usuario usuario, String password);
	void deleteUser(Usuario usuario);
}

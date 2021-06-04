package db.interfaces;

import java.util.List;

import pojos.Cliente;
import pojos.Empleado;
import pojos.Factura;
import pojos.Plantacion;

public interface DBManager {
	//declaramos cabeceras
	
	public void connect();
	//Acceder a la DB e inicializamos cuando corresponda
	
	public void disconnect();
	//Cerrar conexion

	public void addPlantacion(Plantacion plantacion);

	public void addFactura(Factura factura);
	
	public void addEmpleado(Empleado empleado);
	
	public void addCliente(Cliente cliente);

	public List<Empleado> searchEmpleados();


	public boolean eliminarEmpleado(String nombreEmpleado);

	List<Empleado> searchEmpleadoByNombre(String nombreEmpleado);

	List<Cliente> searchClientes();

	List<Empleado> searchEmpleadoById(int idEmpleado);

	public boolean actualizarEmpleado(int idEmpleado);

	public void addCliente(Empleado empleado);

	public void addImagen(Empleado empleado);

	public int searchEmpleadoByDNI(String DniEmpleado);

	

	
}

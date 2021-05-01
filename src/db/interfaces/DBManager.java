package db.interfaces;

import java.util.List;

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

	public List<Empleado> searchEmpleados();


	public boolean eliminarEmpleado(String nombreEmpleado);

	List<Empleado> searchEmpleadoByNombre(String nombreEmpleado);

	
}

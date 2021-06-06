package db.interfaces;

import java.sql.Date;
import java.util.List;

import pojos.Animal;
import pojos.Producto;
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

	//public void addCliente(Empleado empleado);

	public void addImagen(Empleado empleado);
	
	//public List<Cliente> searchClienteByDNI(String Dnicliente);
	public int searchClienteByDNI(String Dnicliente);
	public void addProducto(Producto producto);
	public void addFacturaP(Factura factura);
	public void addAnimal(Animal animal);
	public int searchAnimal(Animal animal);
	public void addProductoP(Producto producto);
	public void addProductoConPlantacion(Producto producto);
	public int searchPlantacion(Plantacion plantacion);

	public List<Animal> searchAnimales();

	public void addEmpleadoAnimal(Empleado empleado,Animal animal);
	public List<Plantacion> searchPlantaciones();
	public void addEmpleadoPlantacion(Empleado empleado,Plantacion plantacion);
	public List<Factura> searchFacturas();
	public List<Producto> searchProductos();
	public void addFacturasProductos(Factura factura,Producto producto);
	public List<Empleado> searchEmpleadoByDni(String dniEmpleado);
}

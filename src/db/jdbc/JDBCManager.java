package db.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import db.interfaces.DBManager;
import pojos.Cliente;
import pojos.Empleado;
import pojos.Factura;
import pojos.Plantacion;

import java.io.IOException;

public class JDBCManager implements DBManager{
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Connection c;

	private final String addPlantacion = "INSERT INTO Plantaciones (Hectareas, HoraRegado) VALUES (?,?);";
	private final String addFactura = "INSERT INTO Facturas (Fecha, Importe, Metodo_de_pago) VALUES (?,?,?);";
	private final String addEmpleado = "INSERT INTO Empleados (Nombre, Telefono, Direccion, DNI, Fech_Nac, Sueldo) VALUES (?,?,?,?,?,?);";
	private final String addCliente = "INSERT INTO Clientes (Nombre, Telefono, Direccion, DNI) VALUES (?,?,?,?);";
	private final String eliminarUnEmpleado = "DELETE FROM Empleados WHERE Nombre LIKE ?;";
	private final String searchEmpleados = "SELECT * FROM Empleados;";
	private final String searchClientes = "SELECT * FROM Clientes;";
	//private final String searchEmpleadoByNombre = "SELECT * FROM Empleados WHERE Nombre = ?;";
	private final String searchUnEmpleado = "SELECT * FROM Empleados WHERE Nombre LIKE ?;";
	private final String searchUnEmpleadoId = "SELECT * FROM Empleados WHERE Id = ?;";
	private final String actualizarNumeroTelefono = "UPDATE Empleados SET Telefono = ?;";
	private final String insertarImagen = "UPDATE Empleados SET Foto = ? WHERE Id = ?;";
	
	
	
	@Override
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			//c es el conector para conectarnos a la base de datos
			c = DriverManager.getConnection("jdbc:sqlite:./db/granja.db");
			Statement stmt = c.createStatement();
			stmt.execute("PRAGMA foreign_keys=ON");
			stmt.close();
			initializeDB();
			LOGGER.info("Se ha establecido la conexión con la BD");
		} catch (ClassNotFoundException e) {
			LOGGER.severe("No se ha encontrado la clase org.sqlite.JDBC");
			e.printStackTrace();
		} catch (SQLException e) {
			LOGGER.severe("Error al crear la conexión con la DB");
			e.printStackTrace();
		}       

	}

	private void initializeDB() {

		try {
			Statement stmt = c.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Empleados("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Nombre TEXT NOT NULL,"
					+ "Telefono INTEGER NOT NULL,"
					+ "Direccion TEXT NOT NULL,"
					+ "DNI TEXT NOT NULL,"
					+ "Fech_Nac DATE NOT NULL,"
					+ "Sueldo REAL NOT NULL,"
					+ "Foto BLOB)");
			stmt.close();
			Statement stmt1 = c.createStatement();
			stmt1.executeUpdate("CREATE TABLE IF NOT EXISTS Clientes("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Nombre TEXT NOT NULL,"
					+ "Telefono INTEGER NOT NULL,"
					+ "Direccion TEXT NOT NULL,"
					+ "DNI TEXT NOT NULL)");
			stmt1.close();
			Statement stmt2 = c.createStatement();
			stmt2.executeUpdate("CREATE TABLE IF NOT EXISTS Productos("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Nombre TEXT NOT NULL,"
					+ "Tipo TEXT NOT NULL,"
					+ "Cantidad INTEGER NOT NULL,"
					+ "Precio REAL NOT NULL, "
					+ "Unidades TEXT NOT NULL,"
					+ "AnimalId INTEGER NOT NULL REFERENCES Animales ON DELETE CASCADE);");
			stmt2.close();
			Statement stmt3 = c.createStatement();
			stmt3.executeUpdate("CREATE TABLE IF NOT EXISTS Facturas("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Fecha DATE NOT NULL,"
					+ "Importe REAL NOT NULL,"
					+ "MetodoPago TEXT NOT NULL,"
					+ "ClienteId INTEGER NOT NULL REFERENCES Clientes ON DELETE CASCADE,"
					+ "ProductoId INTEGER NOT NULL REFERENCES Productos ON DELETE CASCADE);");
			stmt3.close();
			Statement stmt4 = c.createStatement();
			stmt4.executeUpdate("CREATE TABLE IF NOT EXISTS FacturasProductos("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "FacturaId INTEGER NOT NULL REFERENCES Facturas ON DELETE CASCADE,"
					+ "ClienteId INTEGER NOT NULL REFERENCES Clientes ON DELETE CASCADE);");
			stmt4.close();

			Statement stmt5 = c.createStatement();
			stmt5.executeUpdate("CREATE TABLE IF NOT EXISTS Animales("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Especie TEXT NOT NULL,"
					+ "Fech_Nac DATE NOT NULL,"
					+ "Foto BLOB,"
					+ "Peso TEXT NOT NULL);");
			stmt5.close();

			Statement stmt6 = c.createStatement();
			stmt6.executeUpdate( "CREATE TABLE IF NOT EXISTS Plantaciones("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Hectareas REAL NOT NULL,"
					+ "ProductoId INTEGER NOT NULL REFERENCES Productos ON DELETE CASCADE,"
					+ "HoraRegado TEXT NOT NULL);");
			stmt6.close();

			Statement stmt7 = c.createStatement();
			stmt7.executeUpdate("CREATE TABLE IF NOT EXISTS EmpleadosPlantaciones("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "EmpleadoId INTEGER NOT NULL REFERENCES Empleados ON DELETE CASCADE,"
					+ "PlantacionId NOT NULL REFERENCES Plantaciones ON DELETE CASCADE);");
			stmt7.close();

			Statement stmt8 = c.createStatement();
			stmt8.executeUpdate("CREATE TABLE IF NOT EXISTS EmpleadosAnimales("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "EmpleadoId INTEGER NOT NULL REFERENCES Empleados ON DELETE CASCADE,"
					+ "AnimalId INTEGER  NOT NULL REFERENCES Animales ON DELETE CASCADE);");
			stmt8.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al crear las tablas");
			e.printStackTrace();
		}

	}

	@Override
	public void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al cerrar la conexión con la BD");
			e.printStackTrace();
		}

	}

	@Override
	public void addPlantacion(Plantacion plantacion) {

		try {
			//El 1 es de la primera interrogación que tenemos en String addPlantación
			PreparedStatement prep = c.prepareStatement(addPlantacion);
			prep.setFloat(1, plantacion.getHectareas());
			prep.setDate(2, plantacion.getUltimo_regado());

			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

	}

	@Override
	public void addFactura(Factura factura) {

		try {

			PreparedStatement prep = c.prepareStatement(addFactura);
			prep.setDate(1, factura.getFecha());
			prep.setFloat(2, factura.getImporte());
			prep.setBoolean(3, factura.getMetodo_de_pago());

			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

	}

	@Override

	public void addEmpleado (Empleado empleado) {

		try {
			PreparedStatement prep = c.prepareStatement(addEmpleado);
			prep.setString(1, empleado.getNombre());
			prep.setInt(2, empleado.getTelefono());
			prep.setString(3, empleado.getDireccion());
			prep.setString(4, empleado.getDNI());
			prep.setDate(5, empleado.getFecha_Nac());
			prep.setFloat(6, empleado.getSueldo());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

	}
	@Override
	public void addCliente(Cliente cliente) {

		try {
			PreparedStatement prep = c.prepareStatement(addCliente);
			prep.setString(1, cliente.getNombre());
			prep.setInt(2, cliente.getTelefono());
			prep.setString(3, cliente.getDireccion());
			prep.setString(4, cliente.getDni());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			

	}
	
	public void addImagen(Empleado empleado) {
		try {
			PreparedStatement prep = c.prepareStatement(insertarImagen);
			prep.setBytes(1, empleado.getFoto());
			prep.setInt(2, empleado.getId());
			int resultado = prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
			

	}
	@Override
	public List<Empleado> searchEmpleados() {
		/*Iniciamos nuestro ArrayList que va a contener todos los empleados que vamos a ejecutar en nuestra búsqueda. 
		Inicialmente va a estar vacía*/
		List<Empleado> empleados= new ArrayList<Empleado>();

		/*En este caso el resultado es un ResultSet (que contiene la información que queremos)*/
		try {
			Statement stmt = c.createStatement();
			//La Query nos va a devolver el ResultSet va a tener 0 o más elementos con los resultados que ha devuelvo esa query correspondiente.
			ResultSet rs = stmt.executeQuery(searchEmpleados);
			/*Recorremos el resultSet, es decir, mientras haya elementos nos devuelve un true y 
			 podemos obtener cada uno de los elementos de las columnas*/
			while(rs.next()){
				/*Creamos un objeto empleado rellenando los datos obtenidos en la base de datos para que java lo entienda*/
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				String direccion = rs.getString("Direccion");
				String DNI = rs.getString("DNI");
				Date Fecha_Nac = rs.getDate("Fech_Nac");
				Float sueldo = rs.getFloat("Sueldo");
				byte[] foto = rs.getBytes("Foto");
				//FALTA AÑADIR LA FOTO
				Empleado empleado = new Empleado(id, nombre, telefono, direccion, DNI, Fecha_Nac, sueldo, foto);
				//Añadimos un empleado a nuestra lista
				empleados.add(empleado);
				LOGGER.fine("Empleado encontrado: "+ empleado);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		//Vamos a devolver el ArrayList
		return empleados;
	}
	
	
	@Override
	public List<Cliente> searchClientes() {
		/*Iniciamos nuestro ArrayList que va a contener todos los empleados que vamos a ejecutar en nuestra búsqueda. 
		Inicialmente va a estar vacía*/
		List<Cliente> clientes= new ArrayList<Cliente>();

		/*En este caso el resultado es un ResultSet (que contiene la información que queremos)*/
		try {
			Statement stmt = c.createStatement();
			//La Query nos va a devolver el ResultSet va a tener 0 o más elementos con los resultados que ha devuelvo esa query correspondiente.
			ResultSet rs = stmt.executeQuery(searchClientes);
			/*Recorremos el resultSet, es decir, mientras haya elementos nos devuelve un true y 
			 podemos obtener cada uno de los elementos de las columnas*/
			while(rs.next()){
				/*Creamos un objeto empleado rellenando los datos obtenidos en la base de datos para que java lo entienda*/
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				String direccion = rs.getString("Direccion");
				String DNI = rs.getString("DNI");
				//PREGUNTA!!! Tenemos que crear un constructor con las relaciones (factura) relación 1 cliente muchas facturas???
				Cliente cliente = new Cliente(id, nombre, telefono, direccion, DNI);
				//Añadimos un cliente a nuestra lista
				clientes.add(cliente);
				LOGGER.fine("Cliente encontrado: "+ cliente);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		//Vamos a devolver el ArrayList
		return clientes;
	}
	@Override
	public boolean eliminarEmpleado(String nombreEmpleado) {
		boolean existe = false;
		try {
			PreparedStatement prep = c.prepareStatement(eliminarUnEmpleado);
			prep.setString(1,"%" + nombreEmpleado + "%");
			int res = prep.executeUpdate();//si no hace ningun cambio devuelve 0 
											//y si hace cambios devuelve el numero de filas afectas
			if(res > 0)
				existe = true;
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;
	}

	
	@Override
	
	public List<Empleado> searchEmpleadoByNombre(String nombreEmpleado){
		List<Empleado> empleados = new ArrayList<Empleado>();
		try {
			PreparedStatement prep = c.prepareStatement(searchUnEmpleado);
			prep.setString(1,"%" + nombreEmpleado + "%");
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				String direccion = rs.getString("Direccion");
				String DNI = rs.getString("DNI");
				Date fecha_nac = rs.getDate("Fech_Nac");
				float sueldo = rs.getFloat("Sueldo");
				
				Empleado empleado = new Empleado (nombre, telefono, direccion, DNI, fecha_nac, sueldo);
				empleados.add(empleado);
				LOGGER.fine("Empleado encontrados: " + empleado);
			}
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return empleados;
	}
	
	
	
	@Override
	
	public List<Empleado> searchEmpleadoById(int idEmpleado){
		List<Empleado> empleados = new ArrayList<Empleado>();
		try {
			PreparedStatement prep = c.prepareStatement(searchUnEmpleadoId);
			prep.setInt(1, idEmpleado);
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				String direccion = rs.getString("Direccion");
				String DNI = rs.getString("DNI");
				Date fecha_nac = rs.getDate("Fech_Nac");
				float sueldo = rs.getFloat("Sueldo");
				
				Empleado empleado = new Empleado (id, nombre, telefono, direccion, DNI, fecha_nac, sueldo);
				empleados.add(empleado);
				LOGGER.fine("Empleado encontrados: " + empleado);
			}
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return empleados;
	}
	
	
	@Override
	public boolean actualizarEmpleado(int idEmpleado) {
		boolean existe = false;
		try {
			PreparedStatement prep = c.prepareStatement(actualizarNumeroTelefono);
			
			prep.setInt(1, idEmpleado);
			int res = prep.executeUpdate();//si no hace ningun cambio devuelve 0 
											//y si hace cambios devuelve el numero de filas afectas
			if(res > 0)
				existe = true;
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existe;
	}

	@Override
	public void addCliente(Empleado empleado) {
		// TODO Auto-generated method stub
		
	}

}

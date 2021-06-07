package db.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import db.interfaces.DBManager;
import pojos.*;


import java.io.IOException;

public class JDBCManager implements DBManager{
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private Connection c;

	private final String addPlantacion = "INSERT INTO Plantaciones (Hectareas, HoraRegado) VALUES (?,?);";
	private final String addFactura = "INSERT INTO Facturas (Fecha, Importe, MetodoPago) VALUES (?,?,?);";
	private final String addEmpleado = "INSERT INTO Empleados (Nombre, Telefono, Direccion, DNI, Fech_Nac, Sueldo) VALUES (?,?,?,?,?,?);";
	private final String addCliente = "INSERT INTO Clientes (Nombre, Telefono, Direccion, DNI) VALUES (?,?,?,?);";
	private final String eliminarUnEmpleado = "DELETE FROM Empleados WHERE Nombre LIKE ?;";
	private final String searchEmpleados = "SELECT * FROM Empleados;";
	private final String searchClientes = "SELECT * FROM Clientes;";
	private final String searchUnEmpleado = "SELECT * FROM Empleados WHERE Nombre LIKE ?;";
	private final String searchUnEmpleadoId = "SELECT * FROM Empleados WHERE Id = ?;";
	private final String searchUnClienteDni = "SELECT * FROM Cliente WHERE DNI = ?;";
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
					+ "Foto BLOB);");
			stmt.close();
			Statement stmt1 = c.createStatement();
			stmt1.executeUpdate("CREATE TABLE IF NOT EXISTS Clientes("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Nombre TEXT NOT NULL,"
					+ "Telefono INTEGER,"
					+ "Direccion TEXT,"
					+ "DNI TEXT);");
			stmt1.close();
			Statement stmt2 = c.createStatement();
			stmt2.executeUpdate("CREATE TABLE IF NOT EXISTS Productos("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Nombre TEXT NOT NULL,"
					+ "Tipo TEXT NOT NULL,"
					+ "Cantidad INTEGER NOT NULL,"
					+ "Precio REAL NOT NULL, "
					+ "Unidades TEXT NOT NULL,"
					+ "AnimalId INTEGER REFERENCES Animales ON DELETE CASCADE,"
					+ "PlantacionId INTEGER REFERENCES Plantaciones ON DELETE CASCADE);");
			stmt2.close();
			Statement stmt3 = c.createStatement();
			stmt3.executeUpdate("CREATE TABLE IF NOT EXISTS Facturas("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Fecha DATE NOT NULL,"
					+ "Importe REAL NOT NULL,"
					+ "MetodoPago TEXT NOT NULL,"
					+ "ClienteId INTEGER REFERENCES Clientes ON DELETE CASCADE,"
					+ "EmpleadoId INTEGER REFERENCES Empleados ON DELETE CASCADE);");
			stmt3.close();
			Statement stmt4 = c.createStatement();
			stmt4.executeUpdate("CREATE TABLE IF NOT EXISTS FacturasProductos("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "FacturaId INTEGER REFERENCES Facturas ON DELETE CASCADE,"
					+ "ProductoId INTEGER REFERENCES Productos ON DELETE CASCADE);");
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
					+ "HoraRegado DATE NOT NULL);");
			stmt6.close();

			Statement stmt7 = c.createStatement();
			stmt7.executeUpdate("CREATE TABLE IF NOT EXISTS EmpleadosPlantaciones("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "EmpleadoId INTEGER REFERENCES Empleados ON DELETE CASCADE,"
					+ "PlantacionId REFERENCES Plantaciones ON DELETE CASCADE);");
			stmt7.close();

			Statement stmt8 = c.createStatement();
			stmt8.executeUpdate("CREATE TABLE IF NOT EXISTS EmpleadosAnimales("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "EmpleadoId INTEGER REFERENCES Empleados ON DELETE CASCADE,"
					+ "AnimalId INTEGER REFERENCES Animales ON DELETE CASCADE);");
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

			PreparedStatement prep = c.prepareStatement("INSERT INTO Facturas (Fecha, Importe, MetodoPago, EmpleadoId, ClienteId) VALUES (?,?,?,?,?);");

			prep.setDate(1, factura.getFecha());
			prep.setFloat(2, factura.getImporte());
			prep.setBoolean(3, factura.getMetodoPago());
			Empleado empleadoSinId = factura.getEmpleado();
			
			/*El empleado que tiene factura no tiene Id porque el id se asigna automáticamente en la base de datos 
			 * por ello tenemos que buscar el Id en la base de datos. Además, el método searchEmpleadoByDni devuelve una lista de empleados
			 * pero solo cogemos el primer elemento porque los dni son únicos para cada persona por lo que solo debería devolver un
			 * único empleado.*/
			
			prep.setInt(4, (searchEmpleadoByDni(empleadoSinId.getDNI()).get(0)).getId()); //get(0) devuelve el primer empleado
			Cliente cliente = factura.getCliente();
			prep.setInt(5, searchClienteByDNI(cliente.getDni()));//id del cliente

			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}			

	}
	@Override

	public void addAnimal(Animal animal) {

		try {

			PreparedStatement prep = c.prepareStatement("INSERT INTO Animales (Especie, Fech_Nac, Peso) VALUES (?,?,?);");

			prep.setString(1, animal.getEspecie());
			prep.setDate(2, animal. getFecha_Nac());
			prep.setString(3, animal.getPeso());


			prep.executeUpdate();
			prep.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}			

	}

	@Override
	public void addProductoP(Producto producto) {

		try {
			PreparedStatement prep = c.prepareStatement("INSERT INTO Productos (Nombre, Tipo, Cantidad, Precio,Unidades,AnimalId) VALUES (?,?,?,?,?,?);");
			prep.setString(1, producto.getNombre());
			prep.setString(2, producto.getTipo());
			prep.setInt(3, producto.getCantidad());
			prep.setFloat(4, producto.getPrecio());
			prep.setString(5, producto.getUnidades());
			prep.setInt(6, searchAnimal(producto.getAnimal()));
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}			

	}
	@Override
	public void addProductoConPlantacion(Producto producto) {

		try {
			PreparedStatement prep = c.prepareStatement( "INSERT INTO Productos (Nombre, Tipo, Cantidad, Precio,Unidades, PlantacionId) VALUES (?,?,?,?,?,?);");
			prep.setString(1, producto.getNombre());
			prep.setString(2, producto.getTipo());
			prep.setInt(3, producto.getCantidad());
			prep.setFloat(4, producto.getPrecio());
			prep.setString(5, producto.getUnidades());
			prep.setInt(6, searchPlantacion(producto.getPlantacion()));
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}			

	}
	@Override
	public int searchPlantacion(Plantacion plantacion){
		int id = -1;
		try {
			PreparedStatement prep = c.prepareStatement("SELECT Id FROM Plantaciones WHERE Hectareas = ? AND HoraRegado = ?;");
			prep.setFloat(1, plantacion.getHectareas());
			prep.setDate(2, plantacion.getUltimo_regado());

			ResultSet rs = prep.executeQuery();
			id = rs.getInt("Id");
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();

		}
		return id;	
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
			e.printStackTrace();
		}			

	}
	@Override
	public void addProducto(Producto producto) {

		try {
			PreparedStatement prep = c.prepareStatement( "INSERT INTO Productos (Nombre, Tipo, Cantidad, Precio,Unidades) VALUES (?,?,?,?,?);");
			prep.setString(1, producto.getNombre());
			prep.setInt(2, producto.getCantidad());
			prep.setString(3, producto.getTipo());
			prep.setString(4, producto.getUnidades());
			prep.setFloat(5, producto.getPrecio());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}			

		
	}

	@Override
	public void addEmpleadoAnimal(Empleado empleado,Animal animal) {
		try {
			PreparedStatement prep = c.prepareStatement( "INSERT INTO EmpleadosAnimales (EmpleadoId, AnimalId) VALUES (?,?);");
			prep.setInt(1, empleado.getId());
			prep.setInt(2, animal.getId());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void addFacturasProductos(Factura factura,Producto producto) {
		try {
			PreparedStatement prep = c.prepareStatement( "INSERT INTO FacturasProductos (FacturaId, ProductoId) VALUES (?,?);");
			prep.setInt(1, factura.getId());
			prep.setInt(2, producto.getId());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addEmpleadoPlantacion(Empleado empleado,Plantacion plantacion) {
		try {
			PreparedStatement prep = c.prepareStatement( "INSERT INTO EmpleadosPlantaciones (EmpleadoId, PlantacionId) VALUES (?,?);");
			prep.setInt(1, empleado.getId());
			prep.setInt(2, plantacion.getId());
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
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
	public List<Empleado> searchEmpleadosById(int empleadoId) {
		List<Empleado> empleados= new ArrayList<Empleado>();
		try {
			Statement stmt = c.createStatement();
			PreparedStatement prep = c.prepareStatement("SELECT * FROM Empleados WHERE Id = ?;");
			prep.setInt(1, empleadoId);
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				String direccion = rs.getString("Direccion");
				String DNI = rs.getString("DNI");
				Date Fecha_Nac = rs.getDate("Fech_Nac");
				Float sueldo = rs.getFloat("Sueldo");
				byte[] foto = rs.getBytes("Foto");
				Empleado empleado = new Empleado(id, nombre, telefono, direccion, DNI, Fecha_Nac, sueldo, foto);
				empleados.add(empleado);
				LOGGER.fine("Empleado encontrado: "+ empleado);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return empleados;
	}

	@Override
	public List<Cliente> searchClientes() {
		List<Cliente> clientes= new ArrayList<Cliente>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(searchClientes);
			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				String direccion = rs.getString("Direccion");
				String DNI = rs.getString("DNI");
				Cliente cliente = new Cliente(id, nombre, telefono, direccion, DNI);
				clientes.add(cliente);
				LOGGER.fine("Cliente encontrado: "+ cliente);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return clientes;
	}

	@Override
	public List<Plantacion> searchPlantaciones() {
		List<Plantacion> plantaciones= new ArrayList<Plantacion>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Plantaciones;");

			while(rs.next()){
				int id = rs.getInt("Id");
				float hectareas = rs.getFloat("Hectareas");
				Date ultimo_regado = rs.getDate("HoraRegado");
				
				Plantacion plantacion = new Plantacion(id,ultimo_regado, hectareas);
				plantaciones.add(plantacion);
				LOGGER.fine("Plantacion encontrada: "+ plantacion);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return plantaciones;
	}
	
	
	@Override
	public List<Factura> searchFacturas() {
		List<Factura> facturas= new ArrayList<Factura>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Facturas;");

			while(rs.next()){
				int id = rs.getInt("Id");
				Date fecha = rs.getDate("Fecha");
				float importe = rs.getFloat("Importe");
				boolean metodo_de_pago = rs.getBoolean("MetodoPago");
				Factura factura = new Factura(id,fecha,  importe, metodo_de_pago);
				facturas.add(factura);
				LOGGER.fine("Factura encontrada: "+ factura);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return facturas;
	}
	@Override
	public List<Cliente> searchClientesById(int idCliente) {
		List<Cliente> clientes= new ArrayList<Cliente>();
		try {
			Statement stmt = c.createStatement();
			PreparedStatement prep = c.prepareStatement("SELECT * FROM Clientes WHERE Id = ?;");
			prep.setInt(1, idCliente);
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int telefono = rs.getInt("Telefono");
				String direccion = rs.getString("Direccion");
				String dni = rs.getString("DNI");
				Cliente cliente = new Cliente(id,nombre,telefono, direccion,dni);
				clientes.add(cliente);
				LOGGER.fine("Cliente encontrado: "+ cliente);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return clientes;
	}

	@Override
	public List<Producto> searchProductos() {
		List<Producto> productos= new ArrayList<Producto>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Productos;");

			while(rs.next()){
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String tipo = rs.getString("Tipo");
				int cantidad = rs.getInt("Cantidad");
				float precio = rs.getFloat("Precio");
				String unidades = rs.getString("Unidades");
				Producto producto = new Producto(id, nombre,cantidad, tipo, unidades, precio);
				productos.add(producto);
				LOGGER.fine("Producto encontrado: "+ producto);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return productos;
	}
	@Override
	public List<Animal> searchAnimales() {
		List<Animal> animales= new ArrayList<Animal>();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Animales;");
			while(rs.next()){
				int id = rs.getInt("Id");
				String especie = rs.getString("Especie");
				Date fech_Nac = rs.getDate("Fech_Nac");
				String peso = rs.getString("Peso");
				Animal animal = new Animal(id, especie,peso, fech_Nac);
				animales.add(animal);
				LOGGER.fine("Animal encontrado: "+ animal);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();
		}
		return animales;
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

	public List<Empleado> searchEmpleadoByDni(String dniEmpleado){
		List<Empleado> empleados = new ArrayList<Empleado>();
		try {
			PreparedStatement prep = c.prepareStatement("SELECT * FROM Empleados WHERE DNI = ?");
			prep.setString(1, dniEmpleado);
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
	public int searchClienteByDNI(String Dnicliente){
		int id = -1;
		try {
			PreparedStatement prep = c.prepareStatement("SELECT Id FROM Clientes WHERE DNI = ?;");
			prep.setString(1, Dnicliente);
			ResultSet rs = prep.executeQuery();
			id = rs.getInt("Id");
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();

		}
		return id;	
	}

	@Override
	public int searchAnimal(Animal animal){
		int id = -1;
		try {
			PreparedStatement prep = c.prepareStatement("SELECT Id FROM Animales WHERE Especie = ? AND Fech_Nac = ? AND Peso = ?;");
			prep.setString(1, animal.getEspecie());
			prep.setDate(2, animal.getFecha_Nac());
			prep.setString(3, animal.getPeso());

			ResultSet rs = prep.executeQuery();
			id = rs.getInt("Id");
			prep.close();
		} catch (SQLException e) {
			LOGGER.severe("Error al hacer un SELECT");
			e.printStackTrace();

		}
		return id;	
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
			e.printStackTrace();
		}
		return existe;
	}
}

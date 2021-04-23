package db.jdbc;

import java.sql.*;
import java.util.logging.Logger;
import db.interfaces.DBManager;
import pojos.Empleado;
import pojos.Factura;
import pojos.Plantacion;

import java.io.IOException;

public class JDBCManager implements DBManager{
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Connection c;

	private final String addPlantacion = "INSERT INTO Plantaciones (Hectareas, HoraRegado) VALUES (?,?)";
	private final String addFactura = "INSERT INTO Facturas (Fecha, Importe, Metodo_de_pago) VALUES (?,?,?)";
		

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
					+ "Fech_Nac TEXT NOT NULL,"
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
					+ "AnimalId INTEGER NOT NULL REFERENCES Animales);");
			stmt2.close();
			Statement stmt3 = c.createStatement();
			stmt3.executeUpdate("CREATE TABLE IF NOT EXISTS Facturas("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Fecha TEXT NOT NULL,"
					+ "Importe REAL NOT NULL,"
					+ "MetodoPago TEXT NOT NULL,"
					+ "ClienteId INTEGER NOT NULL REFERENCES Clientes,"
					+ "ProductoId INTEGER NOT NULL REFERENCES Productos);");
			stmt3.close();
			Statement stmt4 = c.createStatement();
			stmt4.executeUpdate("CREATE TABLE IF NOT EXISTS FacturasProductos("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "FacturaId INTEGER NOT NULL REFERENCES Facturas,"
					+ "ClienteId INTEGER NOT NULL REFERENCES Clientes);");
			stmt4.close();
			
			Statement stmt5 = c.createStatement();
			stmt5.executeUpdate("CREATE TABLE IF NOT EXISTS Animales("
					+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
					+ "Especie TEXT NOT NULL,"
					+ "Fech_Nac TEXT NOT NULL,"
					+ "Foto BLOB,"
					+ "Peso TEXT NOT NULL);");
			stmt5.close();
					
			Statement stmt6 = c.createStatement();
			stmt6.executeUpdate( "CREATE TABLE IF NOT EXISTS Plantaciones("
							+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
							+ "Hectarias REAL NOT NULL,"
							+ "ProductoId INTEGER NOT NULL REFERENCES Productos,"
							+ "HoraRegado TEXT NOT NULL);");
			stmt6.close();
			
			Statement stmt7 = c.createStatement();
			stmt7.executeUpdate("CREATE TABLE IF NOT EXISTS EmpleadosPlantaciones("
							+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
							+ "EmpleadoId INTEGER NOT NULL REFERENCES Empleados,"
							+ "PlantacionId NOT NULL REFERENCES Plantaciones);");
			stmt7.close();
					
			Statement stmt8 = c.createStatement();
			stmt8.executeUpdate("CREATE TABLE IF NOT EXISTS EmpleadosAnimales("
							+ "Id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
							+ "EmpleadoId INTEGER NOT NULL REFERENCES Empleados,"
							+ "AnimalId INTEGER  NOT NULL REFERENCES Animales);");
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

}

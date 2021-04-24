//Carolina y Sara
package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;
import logging.MyLogger;
import pojos.Empleado;
import pojos.Factura;
import pojos.Pelicula;
import pojos.Plantacion;

public class Menu {
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	/*private String nombre;
	private int telefono;
	private String direccion;
	private String DNI;
	private Date fecha_Nac;
	private Float sueldo;
	private Blob foto;
	*/
	
	//PRUEBA
	private final static String[] EMPLEADOS_NOMBRES = {"Sara", "Carolina", "Alvaro", "Cristina", "Gabriela"};
	private final static int[] EMPLEADOS_TELEFONOS = {607078090, 677655443, 667827162, 638427470, 691827381};
	private final static String[] EMPLEADOS_DIRECCIONES = {"c/valle del sella","c/valle franco","c/Toledo","c/San Jose","c/Rio Duero"};
	private final static String[] EMPLEADOS_DNI = {"54298850V", "48201146Q","47460214E","00496776Q","50193218U"};
	private final static String[] EMPLEADOS_FECHA = {"2020-09-24","1994-07-30","1990-08-02","1993-05-06","2000-03-24","1980-07-19"};
	private final static int[] EMPLEADOS_SUELDO = {1800,2000,3000,1600,1500,2300};
	
	
	
	
	
	public static void main(String[] args) {

		try {
			MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbman.connect();
		int opcion;
		int opcion_empleado;
		int opcion_cliente;

		do {
			System.out.println("Elije una opción:");
			System.out.println("1. Empleado");
			System.out.println("2. Cliente");
			System.out.println("0. Salir");

			try {
				opcion = Integer.parseInt(reader.readLine());
				LOGGER.info("El usuario elije " + opcion);
			} catch (NumberFormatException | IOException e) {
				opcion = -1;
				LOGGER.warning("El usuario no ha introducido un número");
				e.printStackTrace();
			}

			switch(opcion) {

			case 1:
				opcion_empleado = 6;
				while (opcion_empleado != 0) {
					System.out.println("Elije una opción:");
					System.out.println("1. Consultar");
					System.out.println("2. Insertar");
					System.out.println("3. Borrar");
					System.out.println("4. Actualizar");
					System.out.println("5. Comprar");
					System.out.println("0. Salir");

					try {
						opcion_empleado = Integer.parseInt(reader.readLine());
						LOGGER.info("El usuario elije " + opcion_empleado);
					} catch (NumberFormatException | IOException e) {
						opcion_empleado = -1;
						LOGGER.warning("El usuario no ha introducido un número");
						e.printStackTrace();
					}


					switch(opcion_empleado) {
					case 1: 
						searchEmpleado();

						break;
					case 2:
						int opcionInsertar = menuInserta();
						switch(opcionInsertar){
						case 1:
							addPlantacion();
							break;
						case 2:
							addFactura();
							break;
						case 3:
							addEmpleado();

							break;
						}
						//Opcion de insertar
						break;


					}
				} 

				break;
			case 2:


				System.out.println("1. Registrarse");
				System.out.println("0. Salir");
				try {
					opcion_cliente = Integer.parseInt(reader.readLine());
					LOGGER.info("El usuario elije " + opcion_cliente);
				} catch (NumberFormatException | IOException e) {
					opcion_cliente = -1;
					LOGGER.warning("El usuario no ha introducido un número");
					e.printStackTrace();
				}

				break;
			case 3:

				break;
			case 0:
				System.out.println("Que tenga un buen día.");
				break;
			default:
				System.out.println("El número introducido no es válido.");
				break;
			}
		} while (opcion != 0);
		dbman.disconnect();
	}



	private static int  menuInserta() {
		int opcionInsertar;
		System.out.println("Inserta:");
		System.out.println("1. Plantacion");
		System.out.println("2. Factura");
		System.out.println("3. Empleado");
		try {
			opcionInsertar = Integer.parseInt(reader.readLine());
			LOGGER.info("El usuario elije " + opcionInsertar);
		} catch (NumberFormatException | IOException e) {
			opcionInsertar = -1;
			LOGGER.warning("El usuario no ha introducido un número");
			e.printStackTrace();
		}


		return opcionInsertar;

	}

	private static void addFactura() {
		try {
			System.out.println("Indique la fecha (yyyy-MM-dd): ");
			LocalDate fecha = LocalDate.parse(reader.readLine(), formatter);

			System.out.println("Indique el importe: ");
			float importe = Float.parseFloat(reader.readLine());

			System.out.println("Indique método de pago: ");
			System.out.println("0. Tarjeta");
			System.out.println("1. Efectivo");
			boolean metodo_pago = Boolean.parseBoolean(reader.readLine());
			Factura factura = new Factura (Date.valueOf(fecha),importe, metodo_pago);
			dbman.addFactura(factura);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private static void addPlantacion() {
		try {
			System.out.println("Indique la última vez que se regó (yyyy-MM-dd): ");
			LocalDate ultimo_regado = LocalDate.parse(reader.readLine(), formatter);

			System.out.println("Indique las hectáreas: ");
			float hectareas = Float.parseFloat(reader.readLine());

			Plantacion plantacion = new Plantacion(Date.valueOf(ultimo_regado), hectareas);
			dbman.addPlantacion(plantacion);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private static void addEmpleado() {
		try {
			System.out.println("Indique el nombre del empleado: ");
			String nombre = reader.readLine();

			System.out.println("Indique el número de teléfono: ");
			int telefono = Integer.parseInt(reader.readLine());

			System.out.println("Indique la dirección: ");
			String direccion = reader.readLine();

			System.out.println("Indique el DNI del empleado: ");
			String dni = reader.readLine();

			System.out.println("Indique la fecha de nacimiento (yyyy-MM-dd): ");
			LocalDate fech_Nac = LocalDate.parse(reader.readLine(), formatter);

			System.out.println("Indique el sueldo: ");
			float sueldo = Float.parseFloat(reader.readLine());

			Empleado empleado = new Empleado(nombre, telefono, direccion, dni, Date.valueOf(fech_Nac), sueldo);
			dbman.addEmpleado(empleado);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void searchEmpleado() {
			List<Empleado> empleados = dbman.searchEmpleados();
			//Hacemos un bucle para recorrer la lista de empleados
			System.out.println("Se han encontrado los siguientes empleados");
			for(Empleado empleado : empleados) {
				System.out.println(empleado);
			}
	}
	
	private static void generarEmpleados() {
	/*	for(int i = 0; i < EMPLEADOS_NOMBRES.length; i++) {
			
			Empleado empleado = new Empleado(EMPLEADOS_NOMBRES[i], EMPLEADOS_NOMBRES[i], Date.valueOf(fecha));
			dbman.addPelicula(pelicula);
		}
		System.out.println("Se han generado " + PELICULAS_NOMBRES.length + " peliculas.");
		mostrarPeliculas();
		*/
	}
	
	

}



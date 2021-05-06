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
import pojos.Cliente;
import pojos.Empleado;
import pojos.Factura;
import pojos.Plantacion;

public class Menu {
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	//PRUEBA
	private final static String[] EMPLEADOS_NOMBRES = {"Sara", "Carolina", "Alvaro", "Cristina", "Gabriela"};
	private final static int[] EMPLEADOS_TELEFONOS = {607078090, 677655443, 667827162, 638427470, 691827381};
	private final static String[] EMPLEADOS_DIRECCIONES = {"c/valle del sella","c/valle franco","c/Toledo","c/San Jose","c/Rio Duero"};
	private final static String[] EMPLEADOS_DNI = {"54298850V", "48201146Q","4746214E","00496776Q","50193218U"};
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
				/*El programa se queda con el último valor válido que se ha introducido previamente
				  con esta excepción lo solucionamos*/
				LOGGER.warning("El usuario no ha introducido un número");
				e.printStackTrace();
			}

			switch(opcion) {

			case 1:
				opcion_empleado =-1;
				while (opcion_empleado != 0) {
					System.out.println("Elije una opción:");
					System.out.println("1. Consultar");
					System.out.println("2. Insertar");
					System.out.println("3. Borrar");
					System.out.println("4. Actualizar teléfono");
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
					case 1: //Opción consultar
						
						int opcionConsultar = menuConsultar();
						switch(opcionConsultar){
						case 1:
							searchEmpleado();
							break;
						case 2:
							searchCliente();
							break;
						
						}
						break;
						
					case 2: //Insertar
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
					case 3: //Borrar
						eliminarEmpleado();
						break;
					case 4: //Actualizar
						actualizarTelefono();
						break;
					}
				} 

				break;
			case 2: //Cliente
				System.out.println("1. Registrarse");
				//System.out.println("1. ¿Es usted cliente nuestro?");
				System.out.println("0. Salir");

				try {
					opcion_cliente = Integer.parseInt(reader.readLine());
					LOGGER.info("El usuario elije " + opcion_cliente);
				} catch (NumberFormatException | IOException e) {
					opcion_cliente = -1;
					LOGGER.warning("El usuario no ha introducido un número");
					e.printStackTrace();
				}

				switch(opcion_cliente) {
				case 1: 
					addCliente();

					break;
				case 2:

					break;
				case 0:
					System.out.println("Que tenga un buen día.");
					break;
				default:
					System.out.println("El número introducido no es válido.");
					break;
				}
			}
		} while (opcion != 0);
		dbman.disconnect();
	}

	private static int  menuConsultar() {
		int opcionConsultar;
		System.out.println("Consultar:");
		System.out.println("1. Empleado");
		System.out.println("2. Cliente");
	
		try {
			opcionConsultar = Integer.parseInt(reader.readLine());
			LOGGER.info("El usuario elije " + opcionConsultar);
		} catch (NumberFormatException | IOException e) {
			opcionConsultar = -1;
			LOGGER.warning("El usuario no ha introducido un número");
			e.printStackTrace();
		}


		return opcionConsultar;

	}

	private static int  menuInserta() {
		int opcionInsertar;
		System.out.println("Inserta:");
		System.out.println("1. Plantacion");
		System.out.println("2. Factura");
		System.out.println("3. Empleado");
		System.out.println("4. Cliente");
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
	//Añadir
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
		} catch (NumberFormatException | IOException e) {

			LOGGER.warning("");
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
			//
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

	private static void addCliente() {
		try {
			System.out.println("Indique el nombre del cliente: ");
			String nombre = reader.readLine();

			System.out.println("Indique el número de teléfono: ");
			int telefono = Integer.parseInt(reader.readLine());

			System.out.println("Indique la dirección: ");
			String direccion = reader.readLine();

			System.out.println("Indique el DNI del cliente: ");
			String dni = reader.readLine();

			Cliente cliente = new Cliente (nombre, telefono, direccion, dni);
			dbman.addCliente(cliente);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void generarEmpleados() {
		for(int i = 0; i < EMPLEADOS_NOMBRES.length; i++) {
			LocalDate fecha = LocalDate.parse(EMPLEADOS_FECHA[i], formatter);
			Empleado empleado = new Empleado(EMPLEADOS_NOMBRES[i], EMPLEADOS_TELEFONOS[i], EMPLEADOS_DIRECCIONES[i], EMPLEADOS_DNI[i], Date.valueOf(fecha), EMPLEADOS_SUELDO[i]);
			dbman.addEmpleado(empleado);
		}
		System.out.println("Se han generado " + EMPLEADOS_NOMBRES.length + " empleados.");
		searchEmpleado();

	}
	//Búsquedas
	private static void searchEmpleado() {
		List<Empleado> empleados = dbman.searchEmpleados();
		//Hacemos un bucle para recorrer la lista de empleados
		System.out.println("Se han encontrado los siguientes empleados");
		for(Empleado empleado : empleados) {
			System.out.println(empleado);
		}
	}

	private static void searchCliente() {
		List<Cliente> clientes = dbman.searchClientes();
		//Hacemos un bucle para recorrer la lista de empleados
		System.out.println("Se han encontrado los siguientes clientes");
		for(Cliente cliente : clientes) {
			System.out.println(cliente);
		}
	}

	private static void eliminarEmpleado() {
		searchEmpleado();
		System.out.println("Introduzca nombre del empleado:");
		try {
			String nombreEmpleado = reader.readLine();
			List<Empleado> empleados = dbman.searchEmpleadoByNombre(nombreEmpleado);
			if (empleados.size() > 0) {
				System.out.println("Se van a borrar los siguientes empleados:");
				for(Empleado empleado : empleados) {
					System.out.println(empleado);
				}
				System.out.println("¿Confirmar borrado?(s/n)");
				String respuesta = reader.readLine();
				if(respuesta.equalsIgnoreCase("s")) {
					boolean existeEmpleado = dbman.eliminarEmpleado(nombreEmpleado);
					if(existeEmpleado) {
						System.out.println("El empleado se ha borrado con éxito");
					} else {
						System.out.println("Ha habido un error al intentar eliminar el empleado");
					}
				} else {
					System.out.println("Se ha cancelado la operación de borrado");
				}
			} else {
				System.out.println("El empleado no existe");
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void actualizarTelefono(){
		searchEmpleado();
		System.out.println("Introduzca id del empleado:");
		try {
			int idEmpleado = Integer.parseInt(reader.readLine());
			List<Empleado> empleados = dbman.searchEmpleadoById(idEmpleado);
			if (empleados.size() > 0) {
				System.out.println("El empleado que vamos a modificar es: ");
				for(Empleado empleado : empleados) {
					System.out.println(empleado);
				}
				System.out.println("¿Confirmar la actualización?(s/n)");
				String respuesta = reader.readLine();
				if(respuesta.equalsIgnoreCase("s")) {
					System.out.println("Introduzca el nuevo número de teléfono: ");
					int nuevoTelefono = Integer.parseInt(reader.readLine());
					boolean existeEmpleado = dbman.actualizarEmpleado(nuevoTelefono);
					if(existeEmpleado) {
						System.out.println("El empleado se ha actualizado con éxito");
					} else {
						System.out.println("Ha habido un error al intentar actualizar el empleado");
					}
				} else {
					System.out.println("Se ha cancelado la operación de actualizado");
				}
			} else {
				System.out.println("El empleado no existe");
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}



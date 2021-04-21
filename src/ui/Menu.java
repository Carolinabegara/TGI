//Carolina y Sara
package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;
import logging.MyLogger;
import pojos.Factura;
import pojos.Plantacion;

public class Menu {
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
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
				do {
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
				} while (opcion_empleado != 0);

				switch(opcion_empleado) {
				case 1: 
					//Opcion de consultar


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
					}
					//Opcion de insertar
					break;


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
			System.out.println("Indique la fecha (yyy-MM-dd): ");
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
			System.out.println("Indique la última vez que se regó: ");
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

}



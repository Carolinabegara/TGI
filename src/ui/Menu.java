//Carolina y Sara
package ui;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import db.interfaces.DBManager;
import db.interfaces.UsuariosManager;
import db.interfaces.XMLManager;
import db.jdbc.JDBCManager;
import db.jpa.JPAUsuariosManager;
import db.xml.SQLDateAdapter;
import db.xml.XMLFicherosManager;
import logging.MyLogger;
import pojos.*;


public class Menu {
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static DBManager dbman = new JDBCManager();
	private static UsuariosManager userman = new JPAUsuariosManager();

	private static XMLManager xmlman = new XMLFicherosManager();

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	//DATOS DE PRUEBA
	private final static String[] EMPLEADOS_NOMBRES = {"Sara", "Carolina", "Alvaro", "Cristina", "Gabriela"};
	private final static int[] EMPLEADOS_TELEFONOS = {607078090, 677655443, 667827162, 638427470, 691827381};
	private final static String[] EMPLEADOS_DIRECCIONES = {"c/valle del sella","c/valle franco","c/Toledo","c/San Jose","c/Rio Duero"};
	private final static String[] EMPLEADOS_DNI = {"54298850V", "48201146Q","4746214E","00496776Q","50193218U"};
	private final static String[] EMPLEADOS_FECHA = {"2020-09-24","1994-07-30","1990-08-02","1993-05-06","2000-03-24","1980-07-19"};
	private final static int[] EMPLEADOS_SUELDO = {1800,2000,3000,1600,1500,2300};

	private final static String[] CLIENTES_NOMBRES = {"Sara", "Carolina", "Alvaro", "Cristina", "Gabriela"};
	private final static int[] CLIENTES_TELEFONOS = {607078090, 677655443, 667827162, 638427470, 691827381};
	private final static String[] CLIENTES_DIRECCIONES = {"c/valle del sella","c/valle franco","c/Toledo","c/San Jose","c/Rio Duero"};
	private final static String[] CLIENTES_DNI = {"54298850V", "48201146Q","4746214E","00496776Q","50193218U"};

	public static void main(String[] args)  throws JAXBException{

		try {
			MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbman.connect();
		userman.connect();
		int opcion;

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//														    															//
		//________________________________________________MENUS_________________________________________________________________//
		//                                                                                                                      //
		//                                                                                                                      //
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		System.out.println("�Bienvenido a la Granja Para�so!");
		do {
			System.out.println("Elige una opci�n:");
			System.out.println("1. Iniciar sesi�n");
			System.out.println("2. �Eres nuevo en nuestra granja? Crea una cuenta");
			System.out.println("0. Salir");
			try {
				opcion = Integer.parseInt(reader.readLine());
				LOGGER.info("El usuario elige " + opcion);
			} catch (NumberFormatException | IOException e) {
				opcion = -1;
				LOGGER.warning("El usuario no ha introducido un n�mero");
				e.printStackTrace();
			}
			switch(opcion) {
			case 1:
				iniciarSesion();//JPA
				break;
			case 2:
				crearCuenta();//JPA
				break;
			case 3:
				tablaFacturas();
				tablaProductos();
				tablaPlantaciones();
				generarEmpleados();
				tablaEmpleadoAnimal();
				tablaEmpleadoPlantaciones();
				tablaFacturasProductos();
				break;
			case 0:
				break;
			}
		} while (opcion != 0);

		userman.disconnect();
		dbman.disconnect();

	}

	//_________________________MENU EMPLEADO____________________________________

	public static void menuEmpleado() throws JAXBException{
		int opcion_empleado=-1;
		while(opcion_empleado!=0) {
			System.out.println("Men� empleado");
			System.out.println("Elije una opci�n:");
			System.out.println("1. Consultar");
			System.out.println("2. Insertar");
			System.out.println("3. Borrar");
			System.out.println("4. Actualizar tel�fono");
			System.out.println("5. Actualizar contrase�a");
			System.out.println("6. Generar empleados de prueba");
			System.out.println("7. Generar clientes de prueba");
			System.out.println("8. Marshalling animal");
			System.out.println("9. Marshalling plantacion");
			System.out.println("10. Marshalling producto");
			System.out.println("11. Unmarshalling animal");
			System.out.println("12. Unmarshalling plantacion");
			System.out.println("13. Validar XML de producto (DTD)");
			System.out.println("14. Validar XML de animal (DTD)");
			System.out.println("15. Validar XML de plantacion (DTD)");
			System.out.println("16. Generar HTML de producto");
			System.out.println("17. Generar HTML de animal");
			System.out.println("18. Generar HTML de plantacion");


			System.out.println("0. Salir");

			try {
				opcion_empleado = Integer.parseInt(reader.readLine());
				LOGGER.info("El usuario elije " + opcion_empleado);
			} catch (NumberFormatException | IOException e) {
				opcion_empleado = -1;
				LOGGER.warning("El usuario no ha introducido un n�mero");
				e.printStackTrace();
			}


			switch(opcion_empleado) {
			case 1: 

				int opcionConsultar = menuConsultar();

				switch(opcionConsultar){
				case 1:
					mostrarEmpleados();
					break;
				case 2:
					mostrarClientes();
					break;

				}
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
					addImagen();
					break;
				case 0:
					break;
				}
				break;

			case 3: 
				int borrar =-1;
				System.out.println("1. Borrar empleado");
				System.out.println("2. Borrar usuario");
				System.out.println("0. Salir");
				try {
					borrar = Integer.parseInt(reader.readLine());
					LOGGER.info("El usuario elige " + borrar);
				} catch (NumberFormatException | IOException e) {
					borrar = -1;
					LOGGER.warning("El usuario no ha introducido un n�mero");
					e.printStackTrace();
				}
				switch(borrar) {
				case 1:
					eliminarEmpleado();
					break;
				case 2:
					borrarUsuario();//JPA
					//Ponemos opcion_empleado = 0 porque cuando borras un usuario queremos echarle y que inicie sesi�n de nuevo
					opcion_empleado =0;
					break;
				case 0:
					break;
				}

				break;
			case 4: 
				actualizarTelefono();
				break;
			case 5: 
				actualizarContrasenia();//JPA
				break;
			case 6:
				generarEmpleados();
				break;
			case 7:
				generarClientes();
				break;
			case 8:
				Animal animal = new Animal("Vaca",Date.valueOf("2020-06-12"));//OJO TENEMOS QUE A�ADIR EL PESO
				xmlman.marshallingAnimal(animal);
				break;
			case 9:
				Plantacion plantacion = new Plantacion(Date.valueOf("2020-06-12"),1003);
				xmlman.marshallingPlantacion(plantacion);
				break;
			case 10:

				Plantacion plantacion2 = new Plantacion(Date.valueOf("2021-05-20"),1003);
				Producto producto = new Producto("Patatas",100,"Plantaci�n","Kilos",1,null,plantacion2);
				xmlman.marshallingProductoPrueba(producto);
				//xmlman.marshallingProducto(producto);

				break;
			case 11:
				xmlman.unmarshallingAnimal();
				break;
			case 12:
				xmlman.unmarshallingPlantacion();
				break;

			case 13:
				File xmlFileProducto = new File("./xml/Producto.xml");
				xmlman.xmlValido(xmlFileProducto);
				break;
			case 14:
				File xmlFileAnimal = new File("./xml/Animal.xml");
				xmlman.xmlValido(xmlFileAnimal);
				break;
			case 15:
				File xmlFilePlantacion = new File("./xml/Plantacion.xml");
				xmlman.xmlValido(xmlFilePlantacion);
				break;
			case 16:
				String sourcePathProd = "./xml/Producto.xml";
				String xsltPathProd = "./xml/Producto.xslt";
				String resultDirProd = "./xml/Producto.html";
				xmlman.generarXSLT(sourcePathProd, xsltPathProd, resultDirProd);
				break;
			case 17:
				String sourcePathAnim = "./xml/Animal.xml";
				String xsltPathAnim = "./xml/Animal.xslt";
				String resultDirAnim = "./xml/Animal.html";
				xmlman.generarXSLT(sourcePathAnim, xsltPathAnim, resultDirAnim);
				break;
			case 18:
				String sourcePathPlant = "./xml/Plantacion.xml";
				String xsltPathPlant = "./xml/Plantacion.xslt";
				String resultDirPlant = "./xml/Plantacion.html";
				xmlman.generarXSLT(sourcePathPlant, xsltPathPlant, resultDirPlant);
				break;

			case 0:
				break;
			}
		}
	}

	//_____________________MEN�_CONSULTA_________________

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
			LOGGER.warning("El usuario no ha introducido un n�mero");
			e.printStackTrace();
		}

		return opcionConsultar;

	}
	//__________________________MEN�_INSERTAR______________________________________

	private static int  menuInserta() {
		int opcionInsertar;
		System.out.println("Inserta:");
		System.out.println("1. Plantacion");
		System.out.println("2. Factura");
		System.out.println("3. Insertar imagen");
		System.out.println("0. Salir");
		try {
			opcionInsertar = Integer.parseInt(reader.readLine());
			LOGGER.info("El usuario elije " + opcionInsertar);
		} catch (NumberFormatException | IOException e) {
			opcionInsertar = -1;
			LOGGER.warning("El usuario no ha introducido un n�mero");
			e.printStackTrace();
		}
		return opcionInsertar;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//																														//
	//____________________________________________M�TODOS_JPA_______________________________________________________________//
	// 																													    //
	//  																													//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	//____________________________________________INICIAR_SESI�N____________________________________________________________//

	private static void iniciarSesion() throws JAXBException {
		try {
			System.out.println("Indique su email");
			String email = reader.readLine();
			System.out.println("Indique su contrase�a");
			String password = reader.readLine();
			//Comprobamos la contrase�a
			Usuario usuario = userman.verifyPassword(email , password);

			if(usuario == null) {
				System.out.println("Email o contrase�a incorrectos");
			}else {
				//IgnoreCase ignora entre may�sculas y minusculas
				if(usuario.getRol().getNombre().equalsIgnoreCase("empleado")) {
					menuEmpleado();

				}else if(usuario.getRol().getNombre().equalsIgnoreCase("cliente")) {
					//menuCliente();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	//____________________________________________CREAR_CUENTA__________________________________________________________//	

	private static void crearCuenta() {
		try {
			System.out.println("E-mail:");
			String email = reader.readLine();
			System.out.println("Contrase�a:");
			//No queremos guardar nuestra contra�a en claro en nuestra base de datos
			String pass = reader.readLine();
			MessageDigest md = MessageDigest.getInstance("MD5");//nuestro algoritmo de cifrado se llama MD5
			md.update(pass.getBytes());
			byte[] hash = md.digest();//Convertimos la contrase�a en un array de bytes
			System.out.println("Nombre:");
			String nombre = reader.readLine();
			System.out.println(userman.getRoles());//mostramos los roles al usuario
			System.out.println("Id del rol:");
			int rolId = Integer.parseInt(reader.readLine());
			Rol rol = userman.getRolById(rolId);
			Usuario usuario = new Usuario(email, hash, rol);
			LOGGER.info(usuario.toString());

			userman.addUsuario(usuario); //Persistimos el objeto en la base de datos
			System.out.println("El email y la contrase�a se han guardado correctamente");
			LOGGER.info(usuario.toString());

			if(usuario.getRol().getNombre().equalsIgnoreCase("empleado")) {
				addEmpleado(nombre,usuario);
			}else if(usuario.getRol().getNombre().equalsIgnoreCase("cliente")) {
				addCliente(nombre, usuario);
			}
		} catch (IOException e) {
			LOGGER.warning("Error al registrarse");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

	}

	//____________________________________________BORRAR_USUARIO____________________________________________________________//

	private static void borrarUsuario() {

		try {
			System.out.println("Indique su email");
			String email = reader.readLine();
			System.out.println("Indique su contrase�a");
			String password = reader.readLine();
			//Comprobamos la contrase�a
			Usuario usuario = userman.verifyPassword(email , password);

			if(usuario == null) {
				System.out.println("Email o contrase�a incorrectos");
			}else {
				System.out.println("Procedemos a borrar el usuario");
				userman.deleteUser(usuario);
				//FALTA ELIMINAR EL EMPLEADO/CLIENTE QUE CORRESPONDA A DICHO USUARIO
				System.out.println("Usuario borrado");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//______________________________________ACTUALIZAR_CONTRASE�A____________________________________________________________//


	private static void actualizarContrasenia() {

		try {
			System.out.println("Indique su email");
			String email = reader.readLine();
			System.out.println("Indique su contrase�a");
			String password = reader.readLine();
			//Comprobamos la contrase�a
			Usuario usuario = userman.verifyPassword(email , password);

			if(usuario == null) {
				System.out.println("Email o contrase�a incorrectos");
			}else {
				System.out.println("Introduzca la nueva contrase�a");
				String new_password = reader.readLine();
				userman.updatePassword(usuario,new_password);	
				System.out.println("contrase�a actualizada");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//																														//
	//____________________________________________M�TODOS_JDBC______________________________________________________________//
	//	    																												//
	//																													    //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	//1.CONSULTAR__________________________MOSTRAR_EMPLEADOS________________________________________________________________//

	private static void mostrarEmpleados() {
		List<Empleado> empleados = dbman.searchEmpleados();
		System.out.println("Se han encontrado los siguientes empleados");
		//Hacemos un bucle para recorrer la lista de empleados
		for(Empleado empleado : empleados) {
			System.out.println(empleado);
		}
	}

	//1.CONSULTAR__________________________MOSTRAR_CLIENTES________________________________________________________________//

	private static void mostrarClientes() {
		List<Cliente> clientes = dbman.searchClientes();
		System.out.println("Se han encontrado los siguientes clientes");
		for(Cliente cliente : clientes) {
			System.out.println(cliente);
		}
	}

	//2.INSERTAR__________________________PLANTACI�N_______________________________________________________________________//	

	private static void addPlantacion() {
		try {
			System.out.println("Indique la �ltima vez que se reg� (yyyy-MM-dd): ");
			LocalDate ultimo_regado = LocalDate.parse(reader.readLine(), formatter);

			System.out.println("Indique las hect�reas: ");
			float hectareas = Float.parseFloat(reader.readLine());

			//addProducto();
			Plantacion plantacion = new Plantacion(Date.valueOf(ultimo_regado), hectareas);
			dbman.addPlantacion(plantacion);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	//2.INSERTAR__________________________FACTURA_________________________________________________________________________//	

	private static void addFactura() {

		try {
			System.out.println("Indique la fecha (yyyy-MM-dd): ");
			LocalDate fecha = LocalDate.parse(reader.readLine(), formatter);

			System.out.println("Indique el importe: ");
			float importe = Float.parseFloat(reader.readLine());

			System.out.println("Indique m�todo de pago: ");
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

	//2.INSERTAR__________________________IMAGEN_________________________________________________________________________//

	private static void addImagen() {
		try {
			System.out.println("Indique el id del empleado");
			mostrarEmpleados();
			int idEmpleado = Integer.parseInt(reader.readLine());
			System.out.println("Indique la foto que desea agregar:");
			String imagenNombre = reader.readLine();
			Empleado empleado = new Empleado ();
			empleado.setId(idEmpleado);
			empleado.setFoto(readFile(imagenNombre));
			dbman.addImagen(empleado);
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	//3.BORRAR__________________________ELIMINAR_EMPLEADO__________________________________________________________________//
	private static void eliminarEmpleado() {
		mostrarEmpleados();
		System.out.println("Introduzca nombre del empleado:");
		try {
			String nombreEmpleado = reader.readLine();
			List<Empleado> empleados = dbman.searchEmpleadoByNombre(nombreEmpleado);
			if (empleados.size() > 0) {
				System.out.println("Se van a borrar los siguientes empleados:");
				for(Empleado empleado : empleados) {
					System.out.println(empleado);
				}
				System.out.println("�Confirmar borrado?(s/n)");
				String respuesta = reader.readLine();
				if(respuesta.equalsIgnoreCase("s")) {
					boolean existeEmpleado = dbman.eliminarEmpleado(nombreEmpleado);
					if(existeEmpleado) {
						System.out.println("El empleado se ha borrado con �xito");
					} else {
						System.out.println("Ha habido un error al intentar eliminar el empleado");
					}
				} else {
					System.out.println("Se ha cancelado la operaci�n de borrado");
				}
			} else {
				System.out.println("El empleado no existe");
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	//4.ACTUALIZAR__________________________TEL�FONO__________________________________________________________________//

	private static void actualizarTelefono(){
		mostrarEmpleados();
		System.out.println("Introduzca id del empleado:");
		try {
			int idEmpleado = Integer.parseInt(reader.readLine());
			List<Empleado> empleados = dbman.searchEmpleadoById(idEmpleado);
			if (empleados.size() > 0) {
				System.out.println("El empleado que vamos a modificar es: ");
				for(Empleado empleado : empleados) {
					System.out.println(empleado);
				}
				System.out.println("�Confirmar la actualizaci�n?(s/n)");
				String respuesta = reader.readLine();
				if(respuesta.equalsIgnoreCase("s")) {
					System.out.println("Introduzca el nuevo n�mero de tel�fono: ");
					int nuevoTelefono = Integer.parseInt(reader.readLine());
					boolean existeEmpleado = dbman.actualizarEmpleado(nuevoTelefono);
					if(existeEmpleado) {
						System.out.println("El empleado se ha actualizado con �xito");
					} else {
						System.out.println("Ha habido un error al intentar actualizar el empleado");
					}
				} else {
					System.out.println("Se ha cancelado la operaci�n de actualizado");
				}
			} else {
				System.out.println("El empleado no existe");
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	//6.DATOS_DE_PRUEBA__________________________GENERAR_EMPLEADOS_______________________________________________________//


	private static void generarEmpleados() {
		for(int i = 0; i < EMPLEADOS_NOMBRES.length; i++) {
			LocalDate fecha = LocalDate.parse(EMPLEADOS_FECHA[i], formatter);
			Empleado empleado = new Empleado(EMPLEADOS_NOMBRES[i], EMPLEADOS_TELEFONOS[i], EMPLEADOS_DIRECCIONES[i], EMPLEADOS_DNI[i], Date.valueOf(fecha), EMPLEADOS_SUELDO[i]);
			dbman.addEmpleado(empleado);
		}
		System.out.println("Se han generado " + CLIENTES_NOMBRES.length + " empleados.");
		mostrarEmpleados();

	}

	//6.DATOS_DE_PRUEBA__________________________GENERAR_CLIENTES_______________________________________________________//	


	private static void generarClientes() {
		for(int i = 0; i < CLIENTES_NOMBRES.length; i++) {
			Cliente cliente = new Cliente (CLIENTES_NOMBRES[i], CLIENTES_TELEFONOS[i], CLIENTES_DIRECCIONES[i], CLIENTES_DNI[i]);
			dbman.addCliente(cliente);
		}
		System.out.println("Se han generado " + CLIENTES_NOMBRES.length + " clientes.");
		mostrarClientes();

	}

	//________________________________________A�ADIR_EMPLEADO_____________________________________________________________//
	//Estos dos m�todos se utilizan para crear una cuenta
	private static void addEmpleado(String nombre, Usuario usuario) {
		try {

			System.out.println("Indique el n�mero de tel�fono: ");
			int telefono = Integer.parseInt(reader.readLine());

			System.out.println("Indique la direcci�n: ");
			String direccion = reader.readLine();

			System.out.println("Indique el DNI del empleado: ");
			String dni = reader.readLine();

			System.out.println("Indique la fecha de nacimiento (yyyy-MM-dd): ");
			LocalDate fech_Nac = LocalDate.parse(reader.readLine(), formatter);

			System.out.println("Indique el sueldo: ");
			float sueldo = Float.parseFloat(reader.readLine());

			Empleado empleado = new Empleado(usuario.getId(), nombre, telefono, direccion, dni, Date.valueOf(fech_Nac), sueldo);
			dbman.addEmpleado(empleado);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//________________________________________A�ADIR_CLIENTE_____________________________________________________________//

	private static void addCliente(String nombre, Usuario usuario) {
		try {
			System.out.println("Indique el n�mero de tel�fono: ");
			int telefono = Integer.parseInt(reader.readLine());

			System.out.println("Indique la direcci�n: ");
			String direccion = reader.readLine();

			System.out.println("Indique el DNI del cliente: ");
			String dni = reader.readLine();
			System.out.println("Te has registrado con �xito");
			Cliente cliente = new Cliente (usuario.getId(), nombre, telefono, direccion, dni);
			dbman.addCliente(cliente);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//______________________________________Pruebas__________________________________________________________
	private static void tablaFacturas() {

		//datos de CLIENTE

		String nombreCliente = "Carolina";
		int telefono = 681772631;
		String direccion = "C/ Las Matas";
		String dni = "43432819W";
		Cliente cliente = new Cliente(nombreCliente, telefono,direccion,dni);
		dbman.addCliente(cliente);


		//datos de FACTURA

		LocalDate fecha = LocalDate.parse("2021-06-05");
		Float importe = 50.87f;
		boolean metodo_de_pago = false;//si es en efectivo false si es con tarjeta true
		Factura factura = new Factura(Date.valueOf(fecha),importe,metodo_de_pago, cliente);
		dbman.addFacturaP(factura);
	}
	private static void tablaProductos() {
		//datos de ANIMAL

		String especie = "Vaca";
		String peso = "843 Kg";
		LocalDate fecha_Nac = LocalDate.parse("2020-06-05");
		Animal animal = new Animal(especie, peso, Date.valueOf(fecha_Nac));
		dbman.addAnimal(animal);

		//datos de PRODUCTO
		String nombreProducto = "Leche";
		int cantidad = 4;
		String tipo = "Animal";
		String unidades = "Litros";
		float precio = 2.4f;
		Producto producto = new Producto(nombreProducto,cantidad,tipo,unidades,precio, animal);
		dbman.addProductoP(producto);
	}
	private static void tablaPlantaciones() {
		//datos Plantacion
		LocalDate ultimo_regado = LocalDate.parse("2021-06-05");
		float hectareas = 2000.58f;
		Plantacion plantacion = new Plantacion(Date.valueOf(ultimo_regado), hectareas);
		dbman.addPlantacion(plantacion);
		//datos Producto
		String nombreProducto = "Maiz";
		int cantidad = 1000;
		String tipo = "Plantacion";
		String unidades = "Kilos";
		float precio = 4500.89f;
		Producto producto = new Producto(nombreProducto,cantidad,tipo,unidades,precio, null,plantacion);
		dbman.addProductoConPlantacion(producto);

		
	}

	private static void tablaEmpleadoAnimal() {
		List<Empleado> empleados = dbman.searchEmpleados();//miro cuantos empleados tengo en mi base de datos
		int empleadoId = (int) Math.floor(Math.random()*empleados.size());//genero un id aleatorio entre 1 y el numero de empleados que tenga 
																				//para obtener un empleado aleatorio

		List<Animal> animales = dbman.searchAnimales();
		int animalId = (int) Math.floor(Math.random()*animales.size());
		dbman.addEmpleadoAnimal(empleados.get(empleadoId),animales.get(animalId));

	}

	private static void tablaEmpleadoPlantaciones() {
		List<Empleado> empleados = dbman.searchEmpleados();
		int empleadoId = (int) Math.floor(Math.random()*empleados.size());

		List<Plantacion> plantaciones = dbman.searchPlantaciones();
		int plantacionId = (int) Math.floor(Math.random()*plantaciones.size());

		dbman.addEmpleadoPlantacion(empleados.get(empleadoId),plantaciones.get(plantacionId));

	}
	private static void tablaFacturasProductos() {
		List<Factura> facturas = dbman.searchFacturas();
		int facturaId = (int) Math.floor(Math.random()*facturas.size());

		List<Producto> productos = dbman.searchProductos();
		int productoId = (int) Math.floor(Math.random()*productos.size());

		dbman.addFacturasProductos(facturas.get(facturaId),productos.get(productoId));

	}

	/*
	 * M�todo extra�do de https://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
	 */
	//m�todo para que funcione la imagen
	private static byte[] readFile(String file) {
		file = "img\\" + file;
		ByteArrayOutputStream bos = null;
		try {
			File f = new File(file);
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024];
			bos = new ByteArrayOutputStream();
			for (int len; (len = fis.read(buffer)) != -1;) {
				bos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e2) {
			System.err.println(e2.getMessage());
		}
		return bos != null ? bos.toByteArray() : null;
	}
	/*		//datos de Producto1
		List<Producto> productos= new ArrayList<Producto>();

		String nombreProducto = "Leche";
		int cantidad = 4;
		String tipo = "Animal";
		String unidades = "Litros";
		float precio = 2.4f;
		Producto producto = new Producto(nombreProducto,cantidad,tipo,unidades,precio);
		dbman.addProducto(producto);
		productos.add(producto);
		//datos de Producto2

		String nombreProducto2 = "Leche";
		int cantidad2 = 4;
		String tipo2 = "Animal";
		String unidades2 = "Litros";
		float precio2 = 2.4f;
		Producto producto2 = new Producto(nombreProducto2,cantidad2,tipo2,unidades2,precio2);
		dbman.addProducto(producto2);
		productos.add(producto2);*/
}



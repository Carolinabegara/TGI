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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

	//Usamos los siguientes datos para empleados y clientes
	private final static String[] NOMBRES = {"Sara", "Carolina", "Alvaro", "Cristina", "Gabriela"};
	private final static int[] TELEFONOS = {607078090, 677655443, 667827162, 638427470, 691827381};
	private final static String[] DIRECCIONES = {"c/valle del sella","c/valle franco","c/Toledo","c/San Jose","c/Rio Duero"};

	//Datos para factura
	private final static Float[] FACTURAS_IMPORTES = {200.54f,30.0f,50.65f,10.50f,5.45f,17.20f,40.45f,1.50f,34.8f,25.5f,350.67f,550.3f};
	private final static Boolean[] FACTURAS_METODO_PAGO = {false,true};

	//Datos para animales
	private final static String[] ANIMALES_ESPECIE = {"Vaca","Cerdo","Gallina","Oveja"};

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

		System.out.println("¡Bienvenido a la Granja Paraíso!");
		do {
			System.out.println("Elige una opción:");
			System.out.println("1. Iniciar sesión");
			System.out.println("2. ¿Eres nuevo en nuestra granja? Crea una cuenta");
			System.out.println("0. Salir");
			try {
				opcion = Integer.parseInt(reader.readLine());
				LOGGER.info("El usuario elige " + opcion);
			} catch (NumberFormatException | IOException e) {
				opcion = -1;
				LOGGER.warning("El usuario no ha introducido un número");
				e.printStackTrace();
			}
			switch(opcion) {
			case 1:
				iniciarSesion();//JPA
				break;
			case 2:
				crearCuenta();//JPA
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
			System.out.println("Menú empleado");
			System.out.println("Elije una opción:");
			System.out.println("1. Consultar");
			System.out.println("2. Insertar");
			System.out.println("3. Borrar");
			System.out.println("4. Actualizar");
			System.out.println("5. Generar empleados o clientes");
			System.out.println("6. Marshalling");
			System.out.println("7. Unmarshalling");
			System.out.println("8. Validar XML con (DTD)");
			System.out.println("9. Generar HTML");
			System.out.println("10. Insertar datos");
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
					LOGGER.warning("El usuario no ha introducido un número");
					e.printStackTrace();
				}
				switch(borrar) {
				case 1:
					eliminarEmpleado();
					break;
				case 2:
					borrarUsuario();//JPA
					//Ponemos opcion_empleado = 0 porque cuando borras un usuario queremos echarle y que inicie sesión de nuevo
					opcion_empleado =0;
					break;
				case 0:
					break;
				}

				break;
			case 4: 
				actualizar();
				break;

			case 5:
				generarEmpleadosClientes();
				break;

			case 6:
				ejecutarMarshalling();

				break;

			case 7:
				ejecutarUnMarshalling();
				break;
			case 8:
				xmlDTD();
				break;

			case 9:
				generarHTML();
				break;
			case 10:
				insertarDatos();
				break;


			case 0:
				break;
			}
		}
	}



	public static void actualizar() throws JAXBException{
		int opcion_empleado=-1;
		while(opcion_empleado!=0) {
			System.out.println("Actualizar");
			System.out.println("Elije una opción:");
			System.out.println("1. Actualizar teléfono");
			System.out.println("2. Actualizar contraseña");
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
				actualizarTelefono();
				break;
			case 2: 
				actualizarContrasenia();//JPA
				break;
			case 0:
				break;


			}
		}
	}

	public static void generarEmpleadosClientes() throws JAXBException{
		int opcion_empleado=-1;
		while(opcion_empleado!=0) {
			System.out.println("Generar empleados o clientes");
			System.out.println("Elije una opción:");
			System.out.println("1. Generar empleados");
			System.out.println("2. Generar clientes");
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
				generarEmpleados();
				break;
			case 2:
				generarClientes();
				break;
			case 0:
				break;


			}
		}
	}

	public static void ejecutarMarshalling() throws JAXBException{
		int opcion_empleado=-1;
		while(opcion_empleado!=0) {
			System.out.println("Marshaling");
			System.out.println("Elije una opción:");
			System.out.println("1. Marshalling animal");
			System.out.println("2. Marshalling plantacion");
			System.out.println("3. Marshalling producto");
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
				Animal animal = new Animal("Vaca","700Kg",Date.valueOf("2020-06-12"));
				xmlman.marshallingAnimal(animal);
				break;
			case 2:
				Plantacion plantacion = new Plantacion(Date.valueOf("2020-06-12"),1003);
				xmlman.marshallingPlantacion(plantacion);
				break;
			case 3:
				Plantacion plantacion2 = new Plantacion(Date.valueOf("2021-05-20"),1003);
				Producto producto = new Producto("Patatas",100,"Plantación","Kilos",1,null,plantacion2);
				xmlman.marshallingProducto(producto);

				break;
			case 0:
				break;


			}
		}
	}

	public static void ejecutarUnMarshalling() throws JAXBException{
		int opcion_empleado=-1;
		while(opcion_empleado!=0) {
			System.out.println("Unmarshalling");
			System.out.println("Elije una opción:");
			System.out.println("1. Unmarshalling animal");
			System.out.println("2. Unmarshalling plantacion");
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
				xmlman.unmarshallingAnimal();
				break;
			case 2:
				xmlman.unmarshallingPlantacion();
				break;
			case 0:
				break;


			}
		}
	}

	public static void xmlDTD() throws JAXBException{
		int opcion_empleado=-1;
		while(opcion_empleado!=0) {
			System.out.println("Actualizar");
			System.out.println("Elije una opción:");
			System.out.println("1. Validar XML de producto (DTD)");
			System.out.println("2. Validar XML de animal (DTD)");
			System.out.println("3. Validar XML de plantacion (DTD)");
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
				File xmlFileProducto = new File("./xml/Producto.xml");
				xmlman.xmlValido(xmlFileProducto);
				break;
			case 2:
				File xmlFileAnimal = new File("./xml/Animal.xml");
				xmlman.xmlValido(xmlFileAnimal);
				break;
			case 3:
				File xmlFilePlantacion = new File("./xml/Plantacion.xml");
				xmlman.xmlValido(xmlFilePlantacion);
				break;
			case 0:
				break;


			}
		}
	}


	public static void generarHTML() throws JAXBException{
		int opcion_empleado=-1;
		while(opcion_empleado!=0) {

			System.out.println("Generar HTML");
			System.out.println("Elije una opción:");
			System.out.println("1. Generar HTML de producto");
			System.out.println("2. Generar HTML de animal");
			System.out.println("3.Generar HTML de plantacion");
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

				String sourcePathProd = "./xml/Producto.xml";
				String xsltPathProd = "./xml/Producto.xslt";
				String resultDirProd = "./xml/Producto.html";
				xmlman.generarXSLT(sourcePathProd, xsltPathProd, resultDirProd);
				break;
			case 2:
				String sourcePathAnim = "./xml/Animal.xml";
				String xsltPathAnim = "./xml/Animal.xslt";
				String resultDirAnim = "./xml/Animal.html";
				xmlman.generarXSLT(sourcePathAnim, xsltPathAnim, resultDirAnim);
				break;
			case 3:
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

	//CLIENTE
	public static void menuCliente() throws JAXBException{
		int opcion_cliente=-1;
		while(opcion_cliente!=0) {
			System.out.println("Menú cliente");
			System.out.println("Elije una opción:");
			System.out.println("1. Consultar producto");
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
				mostrarProductos();
				break;
			case 0:
				break;
			}
		}
	}
	//_____________________MENÚ_CONSULTA_________________

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
	//__________________________MENÚ_INSERTAR______________________________________

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
			LOGGER.warning("El usuario no ha introducido un número");
			e.printStackTrace();
		}
		return opcionInsertar;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//																														//
	//____________________________________________MÉTODOS_JPA_______________________________________________________________//
	// 																													    //
	//  																													//
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	//____________________________________________INICIAR_SESIÓN____________________________________________________________//

	private static void iniciarSesion() throws JAXBException {
		try {
			System.out.println("Indique su email");
			String email = reader.readLine();
			System.out.println("Indique su contraseña");
			String password = reader.readLine();
			//Comprobamos la contraseña
			Usuario usuario = userman.verifyPassword(email , password);

			if(usuario == null) {
				System.out.println("Email o contraseña incorrectos");
			}else {
				//IgnoreCase ignora entre mayúsculas y minusculas
				if(usuario.getRol().getNombre().equalsIgnoreCase("empleado")) {
					menuEmpleado();

				}else if(usuario.getRol().getNombre().equalsIgnoreCase("cliente")) {
					menuCliente();
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
			System.out.println("Contraseña:");
			//No queremos guardar nuestra contraña en claro en nuestra base de datos
			String pass = reader.readLine();
			MessageDigest md = MessageDigest.getInstance("MD5");//nuestro algoritmo de cifrado se llama MD5
			md.update(pass.getBytes());
			byte[] hash = md.digest();//Convertimos la contraseña en un array de bytes
			System.out.println("Nombre:");
			String nombre = reader.readLine();
			System.out.println(userman.getRoles());//mostramos los roles al usuario
			System.out.println("Id del rol:");
			int rolId = Integer.parseInt(reader.readLine());
			Rol rol = userman.getRolById(rolId);
			Usuario usuario = new Usuario(email, hash, rol);
			LOGGER.info(usuario.toString());

			userman.addUsuario(usuario); //Persistimos el objeto en la base de datos
			System.out.println("El email y la contraseña se han guardado correctamente");
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
			System.out.println("Indique su contraseña");
			String password = reader.readLine();
			//Comprobamos la contraseña
			Usuario usuario = userman.verifyPassword(email , password);

			if(usuario == null) {
				System.out.println("Email o contraseña incorrectos");
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


	//______________________________________ACTUALIZAR_CONTRASEÑA____________________________________________________________//


	private static void actualizarContrasenia() {

		try {
			System.out.println("Indique su email");
			String email = reader.readLine();
			System.out.println("Indique su contraseña");
			String password = reader.readLine();
			//Comprobamos la contraseña
			Usuario usuario = userman.verifyPassword(email , password);

			if(usuario == null) {
				System.out.println("Email o contraseña incorrectos");
			}else {
				System.out.println("Introduzca la nueva contraseña");
				String new_password = reader.readLine();
				userman.updatePassword(usuario,new_password);	
				System.out.println("contraseña actualizada");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//																														//
	//____________________________________________MÉTODOS_JDBC______________________________________________________________//
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


	//1.MOSTRAR__________________________PRODUCTOS________________________________________________________________//
	private static void mostrarProductos() {
		List<Producto> productos = dbman.searchProductos();
		System.out.println("Se han encontrado los siguientes productos");

		for(Producto producto : productos) {
			System.out.println(productos);
		}
	}

	//2.INSERTAR__________________________PLANTACIÓN_______________________________________________________________________//	

	private static void addPlantacion() {
		try {
			System.out.println("Indique la última vez que se regó (yyyy-MM-dd): ");
			LocalDate ultimo_regado = LocalDate.parse(reader.readLine(), formatter);

			System.out.println("Indique las hectáreas: ");
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
			e.printStackTrace();
		}
	}

	//4.ACTUALIZAR__________________________TELÉFONO__________________________________________________________________//

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
			e.printStackTrace();
		}
	}

	//6.DATOS_DE_PRUEBA__________________________GENERAR_EMPLEADOS_______________________________________________________//


	private static void generarEmpleados() {
		for(int i = 0; i < NOMBRES.length; i++) {
			float randomSueldo = randomFloat(500f,2000f);
			Empleado empleado = new Empleado(NOMBRES[i], TELEFONOS[i], DIRECCIONES[i], generarDniAleatorio(), generarFechaAleatoria("Empleado"), Math.round(randomSueldo*100.0/100.0));
			dbman.addEmpleado(empleado);
		}
		System.out.println("Se han generado " + NOMBRES.length + " empleados.");
		mostrarEmpleados();

	}

	//6.DATOS_DE_PRUEBA__________________________GENERAR_CLIENTES_______________________________________________________//	


	private static void generarClientes() {
		for(int i = 0; i < NOMBRES.length; i++) {
			Cliente cliente = new Cliente (NOMBRES[i], TELEFONOS[i], DIRECCIONES[i], generarDniAleatorio());
			dbman.addCliente(cliente);
		}
		System.out.println("Se han generado " + NOMBRES.length + " clientes.");
		mostrarClientes();

	}

	//________________________________________AÑADIR_EMPLEADO_____________________________________________________________//
	//Estos dos métodos se utilizan para crear una cuenta
	private static void addEmpleado(String nombre, Usuario usuario) {
		try {

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

			Empleado empleado = new Empleado(usuario.getId(), nombre, telefono, direccion, dni, Date.valueOf(fech_Nac), sueldo);
			dbman.addEmpleado(empleado);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//________________________________________AÑADIR_CLIENTE_____________________________________________________________//

	private static void addCliente(String nombre, Usuario usuario) {
		try {
			System.out.println("Indique el número de teléfono: ");
			int telefono = Integer.parseInt(reader.readLine());

			System.out.println("Indique la dirección: ");
			String direccion = reader.readLine();

			System.out.println("Indique el DNI del cliente: ");
			String dni = reader.readLine();
			System.out.println("Te has registrado con éxito");
			Cliente cliente = new Cliente (usuario.getId(), nombre, telefono, direccion, dni);
			dbman.addCliente(cliente);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//______________________________________Pruebas__________________________________________________________
	/*Math.random() devuelve un dooble mayor que 0 y menor que 1*/
	private static void insertarDatos() {
		tablaFacturas();
		tablaProductos();
		tablaPlantaciones();
		generarEmpleados();
		tablaEmpleadoAnimal();
		tablaEmpleadoPlantaciones();
		tablaFacturasProductos();
	}
	private static void tablaFacturas() {

		//datos de CLIENTE

		int randomName =(int) Math.floor(Math.random()*NOMBRES.length);//generamos un número entre 0 y el número de nombres que tengamos en el array
		int randomPhone = (int) Math.floor(Math.random()*TELEFONOS.length);
		int randomAddress = (int) Math.floor(Math.random()*DIRECCIONES.length);


		Cliente cliente = new Cliente(NOMBRES[randomName],TELEFONOS[randomPhone],DIRECCIONES[randomAddress],generarDniAleatorio());
		dbman.addCliente(cliente);

		//datos de EMPLEADO

		int randomNameEmp = (int) Math.floor(Math.random()*NOMBRES.length);
		int randomPhoneEmp = (int) Math.floor(Math.random()*TELEFONOS.length);
		int randomAddressEmp = (int) Math.floor(Math.random()*DIRECCIONES.length);

		Empleado empleado = new Empleado(NOMBRES[randomNameEmp],TELEFONOS[randomPhoneEmp],DIRECCIONES[randomAddressEmp],generarDniAleatorio(),generarFechaAleatoria("Empleado"),randomFloat(500f,2000f));//sueldo
		dbman.addEmpleado(empleado);

		//datos de FACTURA


		int randomAmount = (int) Math.floor(Math.random()*FACTURAS_IMPORTES.length);
		int randomPaymentMethod = (int) Math.floor(Math.random()*FACTURAS_METODO_PAGO.length);

		Factura factura = new Factura(generarFechaAleatoria("Factura"),FACTURAS_IMPORTES[randomAmount],FACTURAS_METODO_PAGO[randomPaymentMethod],empleado, cliente);
		dbman.addFacturaP(factura);
	}

	private static void tablaProductos() {

		int randomKind =(int) Math.floor(Math.random()*ANIMALES_ESPECIE.length);
		int producto_cantidad = randomInt(1,100);
		String tipo = "Animal";
		//Distinguimos entre los animales de nuestra graja porque no tienen el mismo peso ni dan los mismos productos
		if(ANIMALES_ESPECIE[randomKind].equals("Vaca")) {

			Animal animal = new Animal(ANIMALES_ESPECIE[randomKind], (randomInt(400,700)+"Kg"), generarFechaAleatoria(tipo));
			dbman.addAnimal(animal);
			//datos de PRODUCTO
			String nombreProducto = "Leche";
			String unidades = "Litros";
			float precio = (float)(0.9*producto_cantidad);//el litro de leche son 0.9€
			Producto producto = new Producto(nombreProducto,producto_cantidad,tipo,unidades,precio, animal,null);//no son producto de plantacion por eso pasamos un objeto null
			dbman.addProductoP(producto);

		}else if(ANIMALES_ESPECIE[randomKind].equals("Gallina")){

			Animal animal = new Animal(ANIMALES_ESPECIE[randomKind], (randomFloat(2.5f,7.5f)+"Kg"),generarFechaAleatoria(tipo));
			dbman.addAnimal(animal);
			//datos de PRODUCTO
			String nombreProducto = "Huevos";
			String unidades = "Unidades";
			float precio = (float)(0.3*producto_cantidad);//el litro de leche son 0.3€
			Producto producto = new Producto(nombreProducto,producto_cantidad,tipo,unidades,precio, animal,null);
			dbman.addProductoP(producto);

		}else {//Puede ser un Cerdo o una oveja que más o menos tienen el mismo peso

			Animal animal = new Animal(ANIMALES_ESPECIE[randomKind], (randomInt(200,100)+"Kg"), generarFechaAleatoria(tipo));
			dbman.addAnimal(animal);

			if(ANIMALES_ESPECIE[randomKind].equals("Cerdo")) {

				//datos de PRODUCTO
				String nombreProducto = "Jamon serrano";
				String unidades = "Kilos";
				float precio = (float)(15*producto_cantidad);
				Producto producto = new Producto(nombreProducto,producto_cantidad,tipo,unidades,precio, animal,null);
				dbman.addProductoP(producto);

			}else {//OVEJA
				String[] PRODUCTOS_OVEJA = {"Leche","Lana"};
				int randomName = (int) Math.floor(Math.random()*PRODUCTOS_OVEJA.length);

				if(PRODUCTOS_OVEJA[randomName].equals("Leche")) {
					String unidades = "Litros";
					float precio = (float)(4*producto_cantidad);
					Producto producto = new Producto(PRODUCTOS_OVEJA[randomName],producto_cantidad,tipo,unidades,precio, animal,null);
					dbman.addProductoP(producto);

				}else {//lana

					String unidades = "Kilos";
					float precio = (float)(2.75*producto_cantidad);
					Producto producto = new Producto(PRODUCTOS_OVEJA[randomName],producto_cantidad,tipo,unidades,precio, animal,null);
					dbman.addProductoP(producto);
				}

			}
		}



	}
	private static void tablaPlantaciones() {
		//datos Plantacion

		Plantacion plantacion = new Plantacion(generarFechaAleatoria("Plantacion"),(randomFloat(1.5f,1500.50f)));
		dbman.addPlantacion(plantacion);

		//datos Producto
		String [] PLANTACIONES_NOMBREPRODUCTOS = {"Maiz","Tomate","Uvas","Pimientos","Patatas","Lechugas","Fresas","Sandias","Zanahorias","Pepinos","Melones","Alcachofas"};
		int randomPlantacion = (int) Math.floor(Math.random()*PLANTACIONES_NOMBREPRODUCTOS.length);
		int cantidad =  (int) Math.floor(Math.random()*1000);
		String tipo = "Plantacion";
		String unidades = "Kilos";
		float precio = (float) (0.8*cantidad);//Asumimos que todos los productos valen lo mismo al kilo
		Producto producto = new Producto(PLANTACIONES_NOMBREPRODUCTOS[randomPlantacion],cantidad,tipo,unidades,precio, null,plantacion);//Como no es un producto animal pasamos un objeto null
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
	private static String generarDniAleatorio() {
		int numDni = (int) Math.floor(Math.random()*100000000);
		String dni = "";
		switch(numDni%23){//hacemos el resto del número aleatorio para hallar la letra
		case 0: dni = numDni + "T"; break;
		case 1: dni = numDni + "R";break;
		case 2: dni = numDni + "W";break;
		case 3: dni = numDni + "A";break;
		case 4: dni = numDni + "G";break;
		case 5: dni = numDni + "M";break;
		case 6: dni = numDni + "Y";break;
		case 7: dni = numDni + "F";break;
		case 8: dni = numDni + "P";break;
		case 9: dni = numDni + "D";break;
		case 10: dni = numDni + "X";break;
		case 11: dni = numDni + "B";break;
		case 12: dni = numDni + "N";break;
		case 13: dni = numDni + "J";break;
		case 14: dni = numDni + "Z";break;
		case 15: dni = numDni + "S";break;
		case 16: dni = numDni + "Q";break;
		case 17: dni = numDni + "V";break;
		case 18: dni = numDni + "H";break;
		case 19: dni = numDni + "L";break;
		case 20: dni = numDni + "C";break;
		case 21: dni = numDni + "K";break;
		case 22: dni = numDni + "E";break;
		}
		return dni;
	}

	private static float randomFloat (float max, float min) {
		return (float) (Math.random()*(max - min)+min);//devuelve un número entre el mínimo y el máximo
	}
	private static Date generarFechaAleatoria(String tipoFecha) {//pasamos un string para saber si es un empleado o una fatura/plantacion
		int year = -1;

		if(tipoFecha.equals("Empleado")){//rango de fechas de 60 años
			year = 1960 + randomInt(0,40);//vamos a tener trabajadores de entre 60 y 20 años
		}else if(tipoFecha.equals("Animal")){
			year = 2010 + randomInt(0,12);//los animales no pueden haber nacido hace 60 años por eso ponemos este rango de fechas
		}else {//fecha para plantaciones y facturas
			year = 2021;//La granja se abrió este año
		}

		int month = randomInt(1,12);//genera número entre [1,13), es decir, un numero entre el 1 y el 12
		int date = randomInt(0,30);
		String monthText = (month <=9)? "0" + month : "" + month;//si (month <=9) es true devuelve lo que está antes de : y sino lo que está después 
		String dateText = (date <=9)? "0" + date : "" + date;
		LocalDate fecha = LocalDate.parse(year + "-" + monthText + "-" + dateText, formatter);

		return Date.valueOf(fecha);
	}
	private static int randomInt(int max, int min) {
		return (int) (Math.random()*(max - min)+min);
	}

	/*
	 * Método extraído de https://www.sqlitetutorial.net/sqlite-java/jdbc-read-write-blob/
	 */
	//método para que funcione la imagen
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



}



package db.xml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import db.interfaces.XMLManager;
import pojos.Animal;
import pojos.Empleado;
import pojos.Plantacion;
import pojos.Producto;


public class TestXML implements XMLManager{
//	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//No sabemos si la fecha está bien puesta así.
	
	
	@Override
	public void marshallingAnimal(Animal animal) throws JAXBException {
	
		// Creamos el JAXBContext
		JAXBContext jaxbC= JAXBContext.newInstance(Animal.class);
		// Creamos el JAXBMarshaller
		Marshaller jaxbM= jaxbC.createMarshaller();
		// Formateo bonito
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		// Escribiendo en un fichero
		File XMLfile= new File("./xml/Animal.xml");
		jaxbM.marshal(animal, XMLfile);
		// Escribiendo por pantalla
		jaxbM.marshal(animal, System.out);
	}
	@Override
	public void marshallingPlantacion(Plantacion plantacion) throws JAXBException {

		// Creamos el JAXBContext
		JAXBContext jaxbC= JAXBContext.newInstance(Plantacion.class);
		// Creamos el JAXBMarshaller
		Marshaller jaxbM= jaxbC.createMarshaller();
		// Formateo bonito
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		// Escribiendo en un fichero
		File XMLfile= new File("./xml/Plantacion.xml");
		jaxbM.marshal(plantacion, XMLfile);
		// Escribiendo por pantalla
		jaxbM.marshal(plantacion, System.out);
	}
	@Override
	public void marshallingProducto(Producto prod) throws JAXBException {
		// Creamos el JAXBContext
		JAXBContext jaxbC = JAXBContext.newInstance(Producto.class);
		// Creamos el JAXBMarshaller
		Marshaller jaxbM = jaxbC.createMarshaller();
		// Formateo bonito
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		jaxbM.setProperty("com.sun.xml.bind.xmlHeaders", "\n<!DOCTYPE producto SYSTEM \"producto.dtd\">");
		jaxbM.setProperty("com.sun.xml.bind.xmlDeclaration", false);
		// Escribiendo en un fichero
		File XMLfile = new File("./xml/Producto.xml");
		jaxbM.marshal(prod, XMLfile);
		// Escribiendo por pantalla
		jaxbM.marshal(prod, System.out);
	}
	@Override
	public void unmarshallingAnimal() throws JAXBException {
		
			// Creamos el JAXBContext
			JAXBContext jaxbC = JAXBContext.newInstance(Animal.class);
			// Creamos el JAXBMarshaller
			Unmarshaller jaxbU = jaxbC.createUnmarshaller();
			// Leyendo un fichero
			File XMLfile = new File("./xml/Animal.xml");
			// Creando el objeto
			Animal animal = (Animal) jaxbU.unmarshal(XMLfile);
			// Escribiendo por pantalla el objeto
			System.out.println(animal);
		
			//LOGGER.warning("El fichero no existe debe hacer marshall primero");
			//e.printStackTrace();
		

	}
	@Override
	public void unmarshallingPlantacion() throws JAXBException {
		// Creamos el JAXBContext
		JAXBContext jaxbC = JAXBContext.newInstance(Plantacion.class);
		// Creamos el JAXBMarshaller
		Unmarshaller jaxbU = jaxbC.createUnmarshaller();
		// Leyendo un fichero
		File XMLfile = new File("./xml/Plantacion.xml");
		// Creando el objeto
		Plantacion plantacion = (Plantacion) jaxbU.unmarshal(XMLfile);
		// Escribiendo por pantalla el objeto
		System.out.println(plantacion);
	}
	@Override
	public  void xmlValido() {
		File xmlFile = new File("./xml/Producto.xml");
		try {
			DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
			dBF.setValidating(true);
			DocumentBuilder builder = dBF.newDocumentBuilder();
			CustomErrorHandler customErrorHandler = new CustomErrorHandler();
			builder.setErrorHandler(customErrorHandler);
			Document doc = builder.parse(xmlFile);
			if (customErrorHandler.isValid()) {
				System.out.println(xmlFile + " was valid!");
			} else {
				System.out.println(xmlFile + " was not valid!");
			}
		} catch (ParserConfigurationException ex) {
			System.out.println(xmlFile + " error while parsing!");
			Logger.getLogger(CheckDTD.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SAXException ex) {
			System.out.println(xmlFile + " was not well-formed!");
			Logger.getLogger(CheckDTD.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			System.out.println(xmlFile + " was not accesible!");
			Logger.getLogger(CheckDTD.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}

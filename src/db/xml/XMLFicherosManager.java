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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import db.interfaces.XMLManager;
import pojos.Animal;
import pojos.Empleado;
import pojos.Plantacion;
import pojos.Producto;


public class XMLFicherosManager implements XMLManager{

	@Override
	public void marshallingAnimal(Animal animal) throws JAXBException {

		// Creamos el JAXBContext
		JAXBContext jaxbC = JAXBContext.newInstance(Animal.class);
		// Creamos el JAXBMarshaller
		Marshaller jaxbM = jaxbC.createMarshaller();
		// Formateo bonito
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		jaxbM.setProperty("com.sun.xml.bind.xmlHeaders", "\n<!DOCTYPE animal SYSTEM \"Animal.dtd\">");
		jaxbM.setProperty("com.sun.xml.bind.xmlDeclaration", false);
		// Escribiendo en un fichero
		File XMLfile = new File("./xml/Animal.xml");
		jaxbM.marshal(animal, XMLfile);
		// Escribiendo por pantalla
		jaxbM.marshal(animal, System.out);
	}
	@Override
	public void marshallingPlantacion(Plantacion plantacion) throws JAXBException {

		// Creamos el JAXBContext
		JAXBContext jaxbC = JAXBContext.newInstance(Plantacion.class);
		// Creamos el JAXBMarshaller
		Marshaller jaxbM = jaxbC.createMarshaller();
		// Formateo bonito
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		jaxbM.setProperty("com.sun.xml.bind.xmlHeaders", "\n<!DOCTYPE plantacion SYSTEM \"Plantacion.dtd\">");
		jaxbM.setProperty("com.sun.xml.bind.xmlDeclaration", false);
		// Escribiendo en un fichero
		File XMLfile = new File("./xml/Plantacion.xml");
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
		jaxbM.setProperty("com.sun.xml.bind.xmlHeaders", "\n<!DOCTYPE producto SYSTEM \"Producto.dtd\">");
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
	public  void xmlValido(File xmlFile) {

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
			Logger.getLogger(XMLFicherosManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SAXException ex) {
			System.out.println(xmlFile + " was not well-formed!");
			Logger.getLogger(XMLFicherosManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			System.out.println(xmlFile + " was not accesible!");
			Logger.getLogger(XMLFicherosManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	@Override
	public void generarXSLT(String sourcePath, String xsltPath, String resultDir) {

		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.transform(new StreamSource(new File(sourcePath)),new StreamResult(new File(resultDir)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
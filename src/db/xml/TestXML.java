package db.xml;

import java.io.File;
import java.sql.Date;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import db.interfaces.XMLManager;
import pojos.Animal;


public class TestXML implements XMLManager{

	//No sabemos si la fecha está bien puesta así.
	@Override
	public void marshalling(Animal animal) throws JAXBException {
		// Creamos el JAXBContext
		JAXBContext jaxbC = JAXBContext.newInstance(Animal.class);
		// Creamos el JAXBMarshaller
		Marshaller jaxbM = jaxbC.createMarshaller();
		// Formateo bonito
		jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
		jaxbM.setProperty("com.sun.xml.bind.xmlHeaders", "\n<!DOCTYPE animal SYSTEM \"animal.dtd\">");
		jaxbM.setProperty("com.sun.xml.bind.xmlDeclaration", false);
		// Escribiendo en un fichero
		File XMLfile = new File("./xml/Animal.xml");
		jaxbM.marshal(animal, XMLfile);
		// Escribiendo por pantalla
		jaxbM.marshal(animal, System.out);
	}

	

}

package db.xml;

import java.io.File;

import java.util.logging.Logger;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import db.interfaces.XMLManager;
import pojos.Animal;
import pojos.Plantacion;


public class TestXML implements XMLManager{
//	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	//No sabemos si la fecha está bien puesta así.
	
	
	@Override
	public void marshalling(Animal animal) throws JAXBException {
	
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
	public void marshalling(Plantacion plantacion) throws JAXBException {

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

}

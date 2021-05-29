package db.interfaces;

import javax.xml.bind.JAXBException;

import pojos.Animal;
import pojos.Plantacion;

public interface XMLManager{
	void marshalling(Animal animal) throws JAXBException;
	void marshalling(Plantacion plantacion) throws JAXBException;
	void unmarshallingPlantacion() throws JAXBException;
	void unmarshallingAnimal() throws JAXBException;


}

package db.interfaces;

import javax.xml.bind.JAXBException;

import pojos.Animal;

public interface XMLManager{
	void marshalling(Animal animal) throws JAXBException;

}

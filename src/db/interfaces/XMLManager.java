package db.interfaces;

import java.io.File;

import javax.xml.bind.JAXBException;

import pojos.Animal;
import pojos.Plantacion;
import pojos.Producto;

public interface XMLManager{
	
	void marshallingAnimal(Animal animal) throws JAXBException;
	void marshallingProducto(Producto prod) throws JAXBException;
	void unmarshallingPlantacion() throws JAXBException;
	void unmarshallingAnimal() throws JAXBException;
	void marshallingPlantacion(Plantacion plantacion) throws JAXBException;
	void TestXSLT();
	void xmlValido(File xmlFile);
}

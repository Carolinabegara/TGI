package db.interfaces;

import pojos.Factura;
import pojos.Plantacion;

public interface DBManager {
	//declaramos cabeceras
	
	public void connect();
	//Acceder a la DB e inicializamos cuando corresponda
	
	public void disconnect();
	//Cerrar conexion

	public void addPlantacion(Plantacion plantacion);

	public void addFactura(Factura factura);
}

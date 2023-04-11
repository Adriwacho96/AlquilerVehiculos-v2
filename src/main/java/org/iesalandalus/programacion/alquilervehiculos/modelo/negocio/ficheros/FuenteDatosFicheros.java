package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class FuenteDatosFicheros implements IFuenteDatos {

	@Override
	public IClientes crearClientes() {	
		return new Clientes();
	}

	@Override
	public IVehiculos crearVehiculos() {
		return new Vehiculos();
	}

	@Override
	public IAlquileres crearAlquileres() {
		return new Alquileres();
	}

}


//COMENZAR MODELO CLIENTES = NEW CLIENTES() //MODELO NUEVO CLIENTES = FUENTEDATOS.CREARCLIENTES();
//FUENTEDEDATOS SOLO EL METODO SET. REFACTOR EN MODELO -> MODELOCASCADA. SUPERCLASS DE MODELO. EXTRACT TODOS HASTA INSERTAR(NO INCLUSIVE) EL RESTO CON LA OTRA ACCION.
//ENUM UN METODO ABSTRACTO QUE NOS CREA UNA FUENTE DE DATOS U OTRO, SOLO TENEMOS LA DE MEMORIA AHORA MISMO.
//VAMOS A NEGOCIO Y CREAMOS ENUMERADO
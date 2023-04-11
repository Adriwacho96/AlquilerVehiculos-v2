package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

public interface IFuenteDatos {

	public IClientes crearClientes();

	public IVehiculos crearVehiculos();

	public IAlquileres crearAlquileres();

}
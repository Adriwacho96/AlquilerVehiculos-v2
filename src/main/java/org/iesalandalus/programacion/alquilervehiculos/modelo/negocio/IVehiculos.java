package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IVehiculos {

	public List<Vehiculo> get();

	public int getCantidad();

	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException;

	public Vehiculo buscar(Vehiculo vehiculo);

	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException;

}
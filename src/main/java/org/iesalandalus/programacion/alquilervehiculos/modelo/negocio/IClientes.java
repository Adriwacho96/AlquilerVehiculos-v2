package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

public interface IClientes {

	public List<Cliente> get();

	public int getCantidad();

	public void insertar(Cliente cliente) throws OperationNotSupportedException;

	public Cliente buscar(Cliente cliente);

	public void borrar(Cliente cliente) throws OperationNotSupportedException;

	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException;

}
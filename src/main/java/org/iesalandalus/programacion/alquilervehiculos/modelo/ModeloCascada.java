package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;

public class ModeloCascada extends Modelo {

	public ModeloCascada(IFuenteDatos fuenteDatos) {
		super();
		setFuenteDatos(fuenteDatos);
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		getClientes().insertar(cliente);

	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		getVehiculos().insertar(vehiculo);

	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if(alquiler!=null) {
			buscar(alquiler.getCliente());
			buscar(alquiler.getVehiculo());
			getAlquileres().insertar(alquiler);
			
		}else {
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		return new Cliente(getClientes().buscar(cliente));
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		getVehiculos().buscar(vehiculo);
		return Vehiculo.copiar(vehiculo);
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		return new Alquiler(getAlquileres().buscar(alquiler));
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		getClientes().modificar(cliente, nombre, telefono);
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		getAlquileres().devolver(cliente, fechaDevolucion);
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		getAlquileres().devolver(vehiculo, fechaDevolucion);
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		for (Alquiler alquiler : getAlquileres().get(cliente)) {
			getAlquileres().borrar(alquiler);
			getClientes().borrar(cliente);
		}

	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		for (Alquiler alquiler : getAlquileres().get(vehiculo)) {
			getAlquileres().borrar(alquiler);
			getVehiculos().borrar(vehiculo);
		}

	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		getAlquileres().borrar(alquiler);
	}
	
	@Override
	public List<Cliente> getListaClientes() {
		List<Cliente> clientesD = new ArrayList<>();
		for (Cliente cliente : getClientes().get()) {
			getAlquileres().get(cliente);
			clientesD.add(new Cliente(cliente));
		}
		return clientesD;

	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		List<Vehiculo> vehiculoD = new ArrayList<>();
		for (Vehiculo vehiculo : getVehiculos().get()) {
			getAlquileres().get(vehiculo);
			vehiculoD.add(Vehiculo.copiar(vehiculo));
		}
		return vehiculoD;
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		List<Alquiler> alquileresD = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get()) {
			alquiler.getCliente();
			alquileresD.add(new Alquiler(alquiler));

		}
		return alquileresD;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		List<Alquiler> alquileresC = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get(cliente)) {
			getClientes().buscar(cliente);
			alquileresC.add(new Alquiler(alquiler));
		}
		return alquileresC;
	}

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		List<Alquiler> vehiculoD = new ArrayList<>();
		for (Alquiler alquiler : getAlquileres().get(vehiculo)) {
			getVehiculos().buscar(vehiculo);
			vehiculoD.add(new Alquiler(alquiler));
		}

		return vehiculoD;
	}

}
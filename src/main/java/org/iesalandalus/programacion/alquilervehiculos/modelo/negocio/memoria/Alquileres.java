package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres {
	List<Alquiler> coleccionAlquileres;

	public Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	@Override
	public List<Alquiler> get() {
		return coleccionAlquileres;
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> aCliente = new ArrayList<>();
		for (Alquiler coleccionAlquiler : coleccionAlquileres) {
			if (coleccionAlquiler.getCliente().equals(cliente)) {
				aCliente.add(coleccionAlquiler);
			}
		}
		return aCliente;
	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {
		List<Alquiler> aVehiculo = new ArrayList<>();
		for (Alquiler coleccionAlquiler : coleccionAlquileres) {
			if (coleccionAlquiler.getVehiculo().equals(vehiculo)) {
				aVehiculo.add(coleccionAlquiler);
			}
		}
		return aVehiculo;
	}

	@Override
	public int getCantidad() {
		return coleccionAlquileres.size();
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		if (!coleccionAlquileres.contains(alquiler)) {
			comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
			coleccionAlquileres.add(alquiler);
		}

	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo turismo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler coleccionAlquiler : coleccionAlquileres) {
			if (coleccionAlquiler.getFechaDevolucion() == null) {
				if (coleccionAlquiler.getCliente().equals(cliente)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
				}
				if (coleccionAlquiler.getVehiculo().equals(turismo)) {
					throw new OperationNotSupportedException("ERROR: El turismo está actualmente alquilado.");
				}
			} else {
				if (coleccionAlquiler.getCliente().equals(cliente)
						&& !coleccionAlquiler.getFechaDevolucion().isBefore(fechaAlquiler)) {
					throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
				}
				if (coleccionAlquiler.getVehiculo().equals(turismo)
						&& !coleccionAlquiler.getFechaDevolucion().isBefore(fechaAlquiler)) {
					throw new OperationNotSupportedException("ERROR: El turismo tiene un alquiler posterior.");
				}
			}

		}
	}

	/*
	 * @Override public void devolver(Alquiler alquiler, LocalDate fechaDevolucion)
	 * throws OperationNotSupportedException { if (alquiler == null) { throw new
	 * NullPointerException("ERROR: No se puede devolver un alquiler nulo."); } if
	 * (!coleccionAlquileres.contains(alquiler)) { throw new
	 * OperationNotSupportedException("ERROR: No existe ningún alquiler igual."); }
	 * else { alquiler.devolver(fechaDevolucion); }
	 * 
	 * }
	 */

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Alquiler alquilerC = null;
		for (Iterator<Alquiler> iterator = coleccionAlquileres.iterator(); alquilerC == null && iterator.hasNext();) {
			Alquiler alquiler = iterator.next();
			if (alquiler.getCliente().equals(cliente) && alquiler.getFechaDevolucion() == null) {
				alquilerC = alquiler;
			}
		}
		return alquilerC;
	}

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		Alquiler alquiler = getAlquilerAbierto(cliente);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}

		alquiler.devolver(fechaDevolucion);
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Alquiler alquilerV = null;
		for (Iterator<Alquiler> iterator = coleccionAlquileres.iterator(); alquilerV == null && iterator.hasNext();) {
			Alquiler alquiler = iterator.next();
			if (alquiler.getVehiculo().equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				alquilerV = alquiler;
			}
		}
		return alquilerV;
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Alquiler alquiler = getAlquilerAbierto(vehiculo);
		if (alquiler == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquiler.devolver(fechaDevolucion);

	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		if (coleccionAlquileres.indexOf(alquiler) == -1) {
			alquiler = null;
		} else {
			coleccionAlquileres.get(coleccionAlquileres.indexOf(alquiler));
		}
		return alquiler;
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		if (!coleccionAlquileres.contains(alquiler)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		} else {
			coleccionAlquileres.remove(alquiler);
		}
	}

}
package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	private static final File FICHERO_ALQUILERES = new File(
			String.format("%s%s%s", "datos", File.separator, "alquileres.xml"));
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String RAIZ = "alquileres";
	private static Alquileres instancia;
	private static final String ALQUILER = "alquiler";
	private static final String CLIENTE = "cliente";
	private static final String VEHICULO = "vehiculo";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";
	private List<Alquiler> coleccionAlquileres;

	private Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	static Alquileres getInstancia() {
		if (instancia == null) {
			instancia = new Alquileres();
		}
		return instancia;
	}

	private void leerDom(Document documentoXml) {
		NodeList alquileres = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < alquileres.getLength(); i++) {
			Node nAlquiler = alquileres.item(i);
			if (nAlquiler.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getAlquiler((Element)alquileres));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private Alquiler getAlquiler(Element elemento) throws OperationNotSupportedException {
		String fechaAlquiler = elemento.getAttribute(FECHA_ALQUILER);
		LocalDate fechaA = LocalDate.parse(fechaAlquiler, FORMATO_FECHA);
		
		String fechaDevolucion = elemento.getAttribute(FECHA_DEVOLUCION);
		LocalDate fechaD = LocalDate.parse(fechaDevolucion, FORMATO_FECHA);
		
		
		String cliente = elemento.getAttribute(CLIENTE);
		Cliente clienteB =Clientes.getInstancia().buscar(Cliente.getClienteConDni(cliente));
		
		String vehiculo = elemento.getAttribute(VEHICULO);
		Vehiculo vehiculoB = Vehiculos.getInstancia().buscar(Vehiculo.getVehiculoConMatricula(vehiculo));
		

		
		if(clienteB == null) {
			
		}
		if(vehiculoB == null) {
			
		}
		Alquiler alquiler = new Alquiler(clienteB,vehiculoB,fechaA);
		if(elemento.hasAttribute(FECHA_DEVOLUCION)) {
			alquiler.devolver(fechaD);
		}
		return alquiler;

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

	@Override
	public void comenzar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminar() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Alquiler> get() {
		
		return new ArrayList<>(coleccionAlquileres);
	}

}
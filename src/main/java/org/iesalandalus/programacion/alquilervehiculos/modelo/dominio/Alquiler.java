package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.naming.OperationNotSupportedException;

public class Alquiler {
	static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final int PRECIO_DIA = 20;
	private LocalDate fechaAlquiler;
	private LocalDate fechaDevolucion;
	private Cliente cliente;
	private Vehiculo vehiculo;

	public Alquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) {

		setCliente(cliente);
		setVehiculo(vehiculo);
		setFechaAlquiler(fechaAlquiler);

	}

	public Alquiler(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alquiler nulo.");
		}

		cliente = new Cliente(alquiler.getCliente());
		vehiculo = Vehiculo.copiar(vehiculo);
		setFechaAlquiler(alquiler.getFechaAlquiler());
		setFechaDevolucion(alquiler.getFechaDevolucion());

	}

	public Cliente getCliente() {
		return cliente;
	}

	private void setCliente(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: El cliente no puede ser nulo.");
		}
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {

		return vehiculo;
	}

	private void setVehiculo(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: El vehículo no puede ser nulo.");
		}

		this.vehiculo = vehiculo;
	}

	public LocalDate getFechaAlquiler() {
		return fechaAlquiler;
	}

	public void setFechaAlquiler(LocalDate fechaAlquiler) {
		if (fechaAlquiler == null) {
			throw new NullPointerException("ERROR: La fecha de alquiler no puede ser nula.");
		}
		if (fechaAlquiler.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");
		}
		this.fechaAlquiler = fechaAlquiler;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	/*
	 * * La fecha de devolución no puede ser igual o anterior a la fecha de alquiler
	 * y tampoco puede ser posterior a hoy.
	 */
	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		if (fechaDevolucion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		if (!fechaDevolucion.isAfter(getFechaAlquiler())) {
			throw new IllegalArgumentException(
					"ERROR: La fecha de devolución debe ser posterior a la fecha de alquiler.");
		}
		this.fechaDevolucion = fechaDevolucion;
	}

	public void devolver(LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (this.fechaDevolucion != null) {
			throw new OperationNotSupportedException("ERROR: La devolución ya estaba registrada.");
		}
		setFechaDevolucion(fechaDevolucion);
	}

	public int getPrecio() {
		int f = 0;
		if (this.fechaDevolucion == null) {
			return f;
		} else {
			int numDias = (int) ChronoUnit.DAYS.between(fechaAlquiler, fechaDevolucion);// fechaDevolucion.getDayOfYear()
																						// -
			return (vehiculo.getFactorPrecio() + PRECIO_DIA) * numDias; // fechaAlquiler.getDayOfYear();
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, vehiculo, fechaAlquiler);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) // NO ES CORRECTO PARA LA HERENCIA
			return false;
		Alquiler other = (Alquiler) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(vehiculo, other.vehiculo)
				&& Objects.equals(fechaAlquiler, other.fechaAlquiler);
	}

	@Override
	public String toString() {
		String cadenaEsperada = null;
		if (fechaDevolucion == null) {
			cadenaEsperada = String.format("%s <---> %s, %s - %s (%d€)", cliente, vehiculo,
					fechaAlquiler.format(FORMATO_FECHA), "Aún no devuelto", getPrecio());
		} else {
			cadenaEsperada = String.format("%s <---> %s, %s - %s (%d€)", cliente, vehiculo,
					getFechaAlquiler().format(FORMATO_FECHA), fechaDevolucion.format(FORMATO_FECHA), getPrecio());
		}
		return cadenaEsperada;
	}
}

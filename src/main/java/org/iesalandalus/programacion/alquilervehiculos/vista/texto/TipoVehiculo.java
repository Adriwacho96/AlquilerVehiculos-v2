package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public enum TipoVehiculo {
	TURISMO("Turismo"), AUTOBUS("Autobus"), FURGONETA("Furgoneta");

	private String nombre;

	private TipoVehiculo(String nombre) {
		this.nombre = nombre;
	}

	public static TipoVehiculo get(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IllegalArgumentException("El numero no es válido");
		}
		return values()[ordinal];
	}

	public static TipoVehiculo get(Vehiculo vehiculo) {
		TipoVehiculo tipoVehiculo = null;
		if (vehiculo == null) {
			throw new NullPointerException("El vehiculo no puede ser nulo");
		}
		if (vehiculo instanceof Turismo) {
			tipoVehiculo = TipoVehiculo.TURISMO;
		} else if (vehiculo instanceof Furgoneta) {
			tipoVehiculo = TipoVehiculo.FURGONETA;
		} else if (vehiculo instanceof Autobus) {
			tipoVehiculo = TipoVehiculo.AUTOBUS;
		}
		return tipoVehiculo;
	}

	@Override
	public String toString() {

		return String.format(" %s", nombre);
	}
}

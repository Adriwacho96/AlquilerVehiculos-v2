package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;

public abstract class Vista {
	private Controlador controlador;

	protected Vista() {
		super();
	}

	protected Controlador getControlador() {
		return controlador;
	}

	public void setControlador(Controlador controlador) {
		if (controlador != null) {
			this.controlador = controlador;
		}

	}

	public abstract void comenzar();

	public abstract void terminar();

}
package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.Map;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {
	private Controlador controlador;

	public VistaTexto() {
		super();
		Accion.setVista(this);
	}

	public void comenzar() {
		getControlador().comenzar();
	}

	public void terminar() {
		getControlador().terminar();
	}

	public void insertarCliente() {

		try {
			controlador.insertar(Consola.leerClienteDni());
			System.out.println("El cliente ha sido introducido.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarVehiculo() {
		try {
			controlador.insertar(Consola.leerVehiculoMatricula());
			System.out.println("El turismo ha sido introducido.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarAlquiler() {
		try {
			controlador.insertar(Consola.leerAlquiler());
			System.out.println("El alquiler ha sido introducido");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void buscarCliente() {
		try {
			System.out.println(controlador.buscar(Consola.leerClienteDni()));

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void buscarVehiculo() {
		try {
			System.out.println(controlador.buscar(Consola.leerVehiculoMatricula()));
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	public void buscarAlquiler() {
		try {
			controlador.buscar(Consola.leerAlquiler());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void modificarCliente() {
		try {
			controlador.modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.println("El cliente ha sido modificado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerCliente() {
		try {
			controlador.devolverAlquilerCliente(Consola.leerClienteDni(), Consola.leerFechaDevolucion());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerVehiculo() {
		try {
			controlador.devolverAlquilerVehiculo(Consola.leerVehiculoMatricula(), Consola.leerFechaDevolucion());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarCliente() {
		try {
			controlador.borrar(Consola.leerCliente());
			System.out.println("El cliente ha sido borrado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarVehiculo() {
		try {
			controlador.borrar(Consola.leerVehiculoMatricula());
			System.out.println("El turismo ha sido borrado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlquiler() {
		try {
			controlador.borrar(Consola.leerAlquiler());
			System.out.println("El alquiler ha sido borrado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarClientes() {
		try {
			for (Cliente cliente : controlador.getClientes()) {
				System.out.println(cliente.toString());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarVehiculos() {
		try {
			for (Vehiculo vehiculos : controlador.getVehiculos()) {
				System.out.println(vehiculos.toString());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileres() {
		try {
			for (Alquiler alquileres : controlador.getAlquileres()) {
				System.out.println(alquileres.toString());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresCliente() {
		try {
			for (Alquiler alquileresC : controlador.getAlquileres(Consola.leerClienteDni())) {
				System.out.println(alquileresC.toString());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresTurismo() {
		try {
			for (Alquiler alquileresT : controlador.getAlquileres(Consola.leerVehiculoMatricula()))
				System.out.println(alquileresT.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {
		LocalDate fechaIni = Consola.leerMes();
		LocalDate fechaFin = fechaIni.plusMonths(1);

		Map<TipoVehiculo, Integer> mapa = inicializarEstadisticas();

		for (Alquiler alquiler : getControlador().getAlquileres()) {
			if (alquiler.getFechaAlquiler().isEqual(fechaIni) && alquiler.getFechaAlquiler().isBefore(fechaFin)) {
				if (alquiler.getVehiculo() instanceof Turismo) {
					int cantidad = mapa.get(TipoVehiculo.TURISMO);
					mapa.put(TipoVehiculo.TURISMO, cantidad + 1);
				}

				if (alquiler.getVehiculo() instanceof Autobus) {
					int cantidad = mapa.get(TipoVehiculo.AUTOBUS);
					mapa.put(TipoVehiculo.AUTOBUS, cantidad + 1);

				}
				if (alquiler.getVehiculo() instanceof Furgoneta) {
					int cantidad = mapa.get(TipoVehiculo.FURGONETA);
					mapa.put(TipoVehiculo.FURGONETA, cantidad + 1);
				}
			}
		}
	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {

		return aol;

	}
}

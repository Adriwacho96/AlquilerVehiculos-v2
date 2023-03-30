package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	private static final String PATRON_FECHA = "dd/MM/yyyy";
	private static final String PATRON_MES = "MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {

	}

	public static void mostrarCabecera(String mensaje) {
		StringBuilder cabecera = new StringBuilder();
		cabecera.append("-");
		for (int i = 0; i < mensaje.length(); i++) {
			System.out.print(cabecera);
		}
		System.out.println();
	}

	public static void mostrarMenu() {
		mostrarCabecera("Menu principal");
		for (Accion opcion : Accion.values()) {
			System.out.println(opcion.toString());
		}
	}

	private static String leerCadena(String mensaje) {
		System.out.println(mensaje);
		return Entrada.cadena();
	}

	private static Integer leerEntero(String mensaje) {
		System.out.println(mensaje);
		return Entrada.entero();
	}

	private static LocalDate leerFecha(String mensaje, String patron) {
		System.out.println(mensaje);

		try {
			do {
				patron = Entrada.cadena();
			} while (!patron.matches(PATRON_FECHA));

		} catch (Exception e) {
			System.out.print("No puede elegirse la fecha");
		}

		return LocalDate.parse(patron, FORMATO_FECHA);
	}

	public static Accion elegirOpcion() {
		Accion opcion = null;
		do {
			try {
				opcion = Accion.get(leerEntero("Introduce un numero: "));
			} catch (Exception e) {
				System.out.print("No se puede elegir la opcion");
			}

		} while (opcion == null && opcion != Accion.SALIR);
		return opcion;
	}

	public static Cliente leerCliente() {
		return new Cliente(leerCadena("Introduce un nombre: "), leerCadena("Introduce un dni: "),
				leerCadena("Introduce un telefono: "));
	}

	public static Cliente leerClienteDni() {
		String dni = leerCadena("Introduzca un dni");
		return Cliente.getClienteConDni(dni);
	}

	public static String leerNombre() {
		return leerCadena("Introduce un nombre");
	}

	public static String leerTelefono() {
		return leerCadena("Introduce un telefono");
	}

	private static void mostrarMenuTiposVehiculo() {

		mostrarCabecera("Elige un vehiculo");
		for (TipoVehiculo tipoVehiculo : TipoVehiculo.values()) {
			System.out.printf("%s %s", tipoVehiculo.ordinal(), tipoVehiculo.toString());
		}
	}

	private static TipoVehiculo elegirTipoVehiculo() {
		mostrarMenuTiposVehiculo();
		TipoVehiculo tipoVehiculo = null;
		do {
			try {
				tipoVehiculo = TipoVehiculo.get(leerEntero("Introduce un numero"));

			} catch (Exception e) {
			}
		} while (tipoVehiculo == null);
		return tipoVehiculo;
	}

	public Vehiculo leeVehiculo() {
		return Vehiculo.copiar(leerVehiculo(elegirTipoVehiculo()));
	}

	private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		Vehiculo vehiculo = null;
		String marca = leerCadena("Introduzca una marca");
		String modelo = leerCadena("Introduzca un modelo");
		String matricula = leerCadena("Introduzca una matricula");
		int enteroCilindrada = leerEntero("Introduce la cilindrada");
		int enteroPlazas = leerEntero("Introduce las plazas");
		int enteroPma = leerEntero("Introduce el peso");

		switch (tipoVehiculo) {
		case AUTOBUS:
			vehiculo = new Autobus(marca, modelo, enteroPlazas, matricula);
			break;

		case FURGONETA:
			vehiculo = new Furgoneta(marca, modelo, enteroPma, enteroPlazas, matricula);
			break;
		case TURISMO:
			vehiculo = new Turismo(marca, modelo, enteroCilindrada, matricula);
		}
		return vehiculo;
	}

	public static Vehiculo leerVehiculoMatricula() {
		String matricula = leerCadena("Introduzca una matricula");
		return Vehiculo.getVehiculoConMatricula(matricula);
	}

	public static Alquiler leerAlquiler() {
		LocalDate fecha = leerFecha("Introduzca una fecha", PATRON_FECHA);
		Vehiculo vehiculo = leerVehiculoMatricula();
		Cliente clienteDni = leerClienteDni();
		return new Alquiler(clienteDni, vehiculo, fecha);
	}

	public static LocalDate leerFechaDevolucion() {
		LocalDate fecha = leerFecha("Introduzca una fecha de devolucion", PATRON_MES);
		return fecha;
	}

	public static LocalDate leerMes() {
		LocalDate mes = leerFecha("Introduzca el mes", PATRON_MES);
		return mes;
	}
}

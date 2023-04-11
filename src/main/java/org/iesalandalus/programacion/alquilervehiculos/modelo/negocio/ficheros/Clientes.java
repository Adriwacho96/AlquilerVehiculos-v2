package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Clientes implements IClientes {
	private static final File FICHERO_CLIENTES = new File(
			String.format("%s%s%s", "datos", File.separator, "clientes.xml"));
	private static final String RAIZ = "clientes";
	private static  Clientes instancia;
	private static final String CLIENTE = "cliente";
	private static final String ER_NOMBRE = "[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*";
	private static final String ER_DNI = "\\d{8}[A-HJ-NP-TV-Z]";
	private static final String ER_TELEFONO = "[6-9]\\d{8}";
	static List<Cliente> coleccionClientes;

	private Clientes() {

		coleccionClientes = new ArrayList<>();
	}

	static Clientes getInstancia() {
		if(instancia == null) {
			instancia = new Clientes();
		}
		return instancia;
	}

	@Override
	public void comenzar() {
		Document documento = UtilidadesXml.leerXmlDeFichero(FICHERO_CLIENTES);
		if (documento != null) {
			System.out.println("Fichero XML leído correctamente.");
			leerDom(documento);
		} else {
			System.out.printf("No se puede leer el fichero de entrada: %s.%n", FICHERO_CLIENTES);
		}
	}

	private void leerDom(Document documentoXml) {
		NodeList clientes = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < clientes.getLength(); i++) {
			Node nPersona = clientes.item(i);
			if (nPersona.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getCliente((Element) clientes));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	private Cliente getCliente(Element elemento) {
		String dni = elemento.getAttribute(ER_DNI);
		String nombre = elemento.getAttribute(ER_NOMBRE);
		String telefono = elemento.getAttribute(ER_TELEFONO);
		return new Cliente(dni, nombre, telefono);

	}

	@Override
	public void terminar() {
		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_CLIENTES);
	}

	private static Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Cliente cliente : coleccionClientes) {
				Element elementoCliente = getElemento(documentoXml, cliente);
				documentoXml.getDocumentElement().appendChild(elementoCliente);
			}
		}
		return documentoXml;
	}

	private static Element getElemento(Document documentoXml, Cliente cliente) {
		Element elementoCliente = getElemento(documentoXml, cliente);
		elementoCliente.setAttribute(ER_DNI, cliente.getDni());
		elementoCliente.setAttribute(ER_NOMBRE, cliente.getNombre());
		elementoCliente.setAttribute(ER_TELEFONO, cliente.getTelefono());
		return elementoCliente;
	}

	@Override
	public List<Cliente> get() {

		return new ArrayList<>(coleccionClientes) ;
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {

		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (!coleccionClientes.contains(cliente)) {
			coleccionClientes.add(cliente);
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}

	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			return cliente;
		} else {
			return null;
		}

	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (coleccionClientes.contains(cliente)) {
			coleccionClientes.remove(cliente);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		if (nombre != null && !nombre.isBlank()) {
			cliente.setNombre(nombre);
		}
		if (telefono != null && !telefono.isBlank()) {
			cliente.setTelefono(telefono);
		}

		if (!coleccionClientes.contains(cliente)) {

			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}

	}
}

package frsf.isi.died.guia08.problema01;

public class EmpleadoExistenteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmpleadoExistenteException(Integer cuil) {
		
		super("El empleado " + cuil + " ya existe en la lista de empleados.");
	}
}
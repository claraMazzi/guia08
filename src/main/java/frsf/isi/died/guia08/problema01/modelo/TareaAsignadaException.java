
package frsf.isi.died.guia08.problema01.modelo;

public class TareaAsignadaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TareaAsignadaException(Integer idEmpleado) {
		
		super("El empleado " + idEmpleado +" no puede realizar la tarea, ya ha sido asignada a otro empleado");
		
	}

}
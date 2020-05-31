  
package frsf.isi.died.guia08.problema01.modelo;

public class TareaEmpezadaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TareaEmpezadaException(Integer idTarea) {
		super("La tarea" + idTarea +" ya está en ejecución");
	}
}
  
package frsf.isi.died.guia08.problema01.modelo;

public class TareaNoEmpezadaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TareaNoEmpezadaException(Integer idTarea) {
		super("La tarea de " + idTarea + " no ha sido empezada");
		
	}
}
package frsf.isi.died.guia08.problema01.modelo;

public class FechaFinInvalidaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FechaFinInvalidaException(Integer idTarea) {

	super("La tarea " + idTarea + " no tiene una fecha de fin válida");

	}
}
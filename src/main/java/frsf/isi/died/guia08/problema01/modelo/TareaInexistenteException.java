package frsf.isi.died.guia08.problema01.modelo;

public class TareaInexistenteException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TareaInexistenteException(Integer idTarea) {
		
		super("La tarea" + idTarea +" no existe en las tareas asignadas");
		
	}
}
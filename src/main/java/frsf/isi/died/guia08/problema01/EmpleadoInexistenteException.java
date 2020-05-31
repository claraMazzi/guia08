
package frsf.isi.died.guia08.problema01;

public class EmpleadoInexistenteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmpleadoInexistenteException(Integer cuil) {
		
		super("El empleado "+cuil+" no existe en la lista de empleados, agréguelo e intente de nuevo");
		
	}
}
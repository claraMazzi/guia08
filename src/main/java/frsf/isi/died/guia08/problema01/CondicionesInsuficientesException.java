package frsf.isi.died.guia08.problema01;

public class CondicionesInsuficientesException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CondicionesInsuficientesException(Integer cuil) {
		super("El empleado "+cuil+" no cumple las condiciones correspondientes");
		
	}

	
}
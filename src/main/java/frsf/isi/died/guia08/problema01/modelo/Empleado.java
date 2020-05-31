package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}; 
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;


	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;		
	private Predicate<Tarea> puedeAsignarTarea;
	
	public Double getCostoHora() {
		return costoHora;
	}

	public void setCostoHora(Double costoHora) {
		this.costoHora = costoHora;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	
	public Empleado() {
		
	}
	
	public Empleado(Integer cuil, String nombre, Double costoHora, String tipo) {
		
		super();
		this.cuil = cuil;
		this.setNombre(nombre);
		this.costoHora = costoHora;
		this.tareasAsignadas = new ArrayList<Tarea>();
		
		switch (tipo) {

			case "CONTRATADO":{
				
				this.setTipo(Tipo.CONTRATADO);
				this.puedeAsignarTarea = (Tarea tarea)->this.tareasAsignadas.stream().filter((Tarea tarea1)->(tarea1.getFechaFin()==null)).count()<5;
				this.calculoPagoPorTarea = (Tarea tarea)->{
												Long retorno = tarea.diferenciaDias();
												if(retorno*4<tarea.getDuracionEstimada()) {
													return (0.30*tarea.getEmpleadoAsignado().costoHora)*retorno*4 + retorno*4*tarea.getEmpleadoAsignado().costoHora;
												}
												if(retorno - (tarea.getDuracionEstimada()/4) >2) {
													return retorno*4*0.75*(tarea.getEmpleadoAsignado().costoHora);
												}
												
												return retorno*4*tarea.getEmpleadoAsignado().costoHora;
					};
			}	break;
				
			case "EFECTIVO":{
				
				this.setTipo(Tipo.EFECTIVO);
				this.puedeAsignarTarea = ((Tarea tarea)->this.tareasAsignadas.stream().filter(tarea1->(tarea1.getFechaFin()==null)).map((Tarea tar)->tar.getDuracionEstimada()).reduce((Integer acum, Integer hs)-> {return acum + hs;}).orElse(0)<=15);
				this.calculoPagoPorTarea = (Tarea tarea)->{
												Long retorno = tarea.diferenciaDias();
					
												if(retorno*4<tarea.getDuracionEstimada()) {
													return (1.20*tarea.getEmpleadoAsignado().costoHora)*retorno*4 ;
												}
												return retorno*4*tarea.getEmpleadoAsignado().costoHora;
					};				
			}	break;
		}
		
	}

	public Integer getCuil() {

		return this.cuil;
		
	}
	
	public List<Tarea> getTareasAsignadas(){
		
		return this.tareasAsignadas;
		
	}
	
	
	public Double salario() {
		Double salario = 0.0;
		List<Tarea> noFacturadas = this.tareasAsignadas.stream().filter((Tarea tarea)-> (!tarea.getFacturada() && !(tarea.getFechaInicio()==null))).collect(Collectors.toList());

			if(noFacturadas.isEmpty()) {
				return salario;
			}

			salario = noFacturadas.stream().map(t->this.costoTarea(t)).reduce((Double acum, Double pago)->{return acum+pago;}).get();
			Consumer<Tarea> consumerTarea = (Tarea t)-> t.setFacturada(true);
			noFacturadas.stream().forEach(consumerTarea);


				return salario;

	}
	
	
	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	public Double costoTarea(Tarea t) {
		
		if(!(t.getFechaFin()==null)) {
			
			return this.calculoPagoPorTarea.apply(t);
			
		}
		
		return this.costoHora*t.getDuracionEstimada();
		
	}
		
public Boolean asignarTarea(Tarea tarea) throws TareaAsignadaException {
		Boolean retorno=false;
		if(puedeAsignarTarea.test(tarea)) {
			
			this.tareasAsignadas.add(tarea);
			tarea.asignarEmpleado(this);
			
			retorno=true;
		}
		
		return retorno;
		
	}

	
public void comenzar(Integer idTarea) throws Exception{
	
	// busca la tarea en la lista de tareas asignadas 
	Optional<Tarea> tarea = this.tareasAsignadas.stream().filter(t-> t.getId()==idTarea).findFirst();
												
	// si la tarea no existe lanza una excepción
	if(tarea.isPresent()) {
		throw new TareaInexistenteException(idTarea);
		
	}else if(tarea.get().getFechaFin()==null) {
			
			if(tarea.get().getFechaInicio()==null) {
				tarea.get().setFechaInicio(LocalDateTime.now());
			} else {
				throw new TareaEmpezadaException(idTarea);
			}
		} else {
			throw new TareaFinalizadaException(idTarea); 
			
		}
		
	}
	
	
public void finalizar(Integer idTarea) throws Exception {
	
	Optional<Tarea> tarea = this.tareasAsignadas.stream().filter(t-> t.getId()==idTarea).findFirst();
												
	if(tarea.isPresent()) {
		throw new TareaInexistenteException(idTarea);
		
	} else if(tarea.get().getFechaFin()==null) {
			
			if(tarea.get().getFechaInicio()==null) {
				
				throw new TareaNoEmpezadaException(idTarea);
			
			}else {
			
			tarea.get().setFechaFin(LocalDateTime.now());
			}
		} else {
			
			throw new TareaFinalizadaException(idTarea); 
			
		}
	}


public void comenzar(Integer idTarea,String fecha) throws Exception{
	

	Optional<Tarea> tarea = this.tareasAsignadas.stream().filter(t-> t.getId()==idTarea).findFirst();
												
	if(tarea.isPresent()) {
		throw new TareaInexistenteException(idTarea);
	}
	else if(tarea.get().getFechaFin()==null) {
			
			if(tarea.get().getFechaInicio()==null) {
				
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");

				tarea.get().setFechaInicio(LocalDateTime.parse(fecha, formato));		
			}else {
				
				throw new TareaEmpezadaException(idTarea);
			}
		}
		else {
			
			throw new TareaFinalizadaException(idTarea); 
			
		}
		
	}

	
public void finalizar(Integer idTarea,String fecha) throws Exception{

	Optional<Tarea> tarea = this.tareasAsignadas.stream().filter(t-> t.getId()==idTarea).findFirst();
												
	if(tarea.isPresent()) {
		throw new TareaInexistenteException(idTarea);
		
	}else if(tarea.get().getFechaFin()==null) {
			
			if(tarea.get().getFechaInicio()==null) {
				
				throw new TareaNoEmpezadaException(idTarea);
			
			}
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");
			
			LocalDateTime fechaFin = LocalDateTime.parse(fecha, formato);
			
			if(fechaFin.compareTo(tarea.get().getFechaInicio())>0) {

				tarea.get().setFechaFin(fechaFin);
			}else {

				throw new FechaFinInvalidaException(idTarea);
			}
			
		}else {
			
			throw new TareaFinalizadaException(idTarea); 
			
		}
	}
	


	@Override
		public boolean equals(Object o) {
	
			return (o instanceof Empleado) && ((Empleado)o).getCuil()==this.cuil;
			
	}

	
	
	
	
}
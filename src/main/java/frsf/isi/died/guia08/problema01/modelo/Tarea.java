package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	
	
	public Tarea() {
		
	}
	
	
	public Tarea(Integer id, String descripcion, Integer duracionEstimada) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		this.facturada=false;
	}
	
	
	public void asignarEmpleado(Empleado e) throws TareaAsignadaException {
		
		
		// si la tarea ya tiene un empleado asignado, lanza excepción
		if(this.empleadoAsignado!=null) {
			
			throw new TareaAsignadaException(e.getCuil());
			
		}
		
		//Asigno tarea si no hubo errores.
		this.empleadoAsignado=e;
		
	}
	
	public Long diferenciaDias() {
		
		long diferencia = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
		if(diferencia==0) {
			
			return 1L;
		}
		
		return diferencia;
		
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	
	public String asCsv() {
		
			if(this.fechaInicio==null || this.fechaFin==null) {
			
				return "";
			} else {
				return this.id + ";\"" + this.descripcion + "\";" + this.duracionEstimada + ";\"" + this.fechaInicio.format( DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")) + "\";\"" + this.fechaFin.format( DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm")) + "\";" + this.empleadoAsignado.getCuil();
			}
		
	}
	
}

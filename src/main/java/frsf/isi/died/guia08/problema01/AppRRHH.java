package frsf.isi.died.guia08.problema01;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.modelo.TareaAsignadaException;
import frsf.isi.died.guia08.problema01.modelo.TareaEmpezadaException;
import frsf.isi.died.guia08.problema01.modelo.TareaFinalizadaException;
import frsf.isi.died.guia08.problema01.modelo.TareaInexistenteException;
import frsf.isi.died.guia08.problema01.modelo.TareaNoEmpezadaException;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public AppRRHH() {
		
		this.empleados = new ArrayList<Empleado>();
				
	}
	
	public List<Empleado> getLista(){
		return this.empleados;
	}
	
	
	public Boolean agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) throws EmpleadoExistenteException{
		Boolean retorno=false;
		Empleado e = new Empleado(cuil, nombre, costoHora, "CONTRATADO");
		
		if(this.buscarEmpleado((Empleado emp)-> (emp.getCuil()==cuil)).isPresent()) {
			
			throw new EmpleadoExistenteException(cuil);
		} else {
			
			this.empleados.add(e);
			try {
				guardarEmpleadoCSV(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			retorno=true;
		}
		return retorno;
	}
	

	public Boolean agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) throws EmpleadoExistenteException{
		Boolean retorno=false;
		Empleado e = new Empleado(cuil, nombre, costoHora, "EFECTIVO");
		
		if(this.buscarEmpleado((Empleado emp)-> (emp.getCuil()==cuil)).isPresent()){
			
			throw new EmpleadoExistenteException(cuil);
		}
		else {
			
			this.empleados.add(e);	
			
			try {
				guardarEmpleadoCSV(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			retorno=true;
		}
		return retorno;
	}
	
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) throws Exception{
		
		Optional<Empleado> emp = this.buscarEmpleado((Empleado e) -> e.getCuil()==cuil);
		
		if(emp.isPresent()) {
			
			Optional<Tarea> tarea = this.buscarTarea((Tarea ta)-> ta.getId()==idTarea);
			
			if(tarea.isPresent()) {

				if(!emp.get().asignarTarea(new Tarea(idTarea, descripcion, duracionEstimada))) {
					
					throw new CondicionesInsuficientesException(cuil);
				}
			}
			else {

				throw new TareaAsignadaException(cuil);
			}
		}
		else {
			
			throw new EmpleadoInexistenteException(cuil);
			
		}
		
	}
	

	public void empezarTarea(Integer cuil,Integer idTarea) throws Exception, TareaInexistenteException, TareaFinalizadaException, TareaEmpezadaException{
		

		Optional<Empleado> emp = this.buscarEmpleado((Empleado emp1)->emp1.getCuil()==cuil); 
		
		if(emp.isPresent()) {
				
				emp.get().comenzar(idTarea);
				
		}
		else {

			throw new EmpleadoInexistenteException(cuil);
		}
		
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) throws Exception, TareaInexistenteException, TareaFinalizadaException, TareaNoEmpezadaException{
		
		Optional<Empleado> optEmp = this.buscarEmpleado((Empleado emp)->emp.getCuil()==cuil); 
		
		if(optEmp.isPresent()) {
				
				optEmp.get().finalizar(idTarea);
		}
		else {
			
			throw new EmpleadoInexistenteException(cuil);
		}
		
	}

	
	

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) throws FileNotFoundException, IOException, EmpleadoExistenteException, Exception {
		File file = new File(nombreArchivo);
		  
		if (!file.exists()) {
            file.createNewFile();
        }
		
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
            	String line = input.nextLine();
            	String[] fila = line.split(";");
				
				try {
					
					this.agregarEmpleadoEfectivo(Integer.valueOf(fila[0]), fila[1], Double.valueOf(fila[2]));
					
				} catch (NumberFormatException e) {
					
					throw new Exception("Hubo un error al intentar leer los datos del archivo");
				}
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo)  throws FileNotFoundException, IOException, EmpleadoExistenteException, Exception {

		File file = new File(nombreArchivo);
		  
		if (!file.exists()) {
            file.createNewFile();
        }
		
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
            	String line = input.nextLine();
            	String[] fila = line.split(";");
				
				try {
					
					this.agregarEmpleadoEfectivo(Integer.valueOf(fila[0]), fila[1], Double.valueOf(fila[2]));
					
				} catch (NumberFormatException e) {
					
					throw new Exception("Hubo un error al intentar leer los datos del archivo");
				}
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public void cargarTareasCSV(String nombreArchivo) throws FileNotFoundException, IOException, TareaFinalizadaException, TareaAsignadaException, Exception {
		File file = new File(nombreArchivo);
		  
		if (!file.exists()) {
            file.createNewFile();
        }
		
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
            	String line = input.nextLine();
            String[] fila = line.split(";");
							
					try {	
						this.asignarTarea(Integer.valueOf(fila[3]), Integer.valueOf(fila[0]), fila[1], Integer.valueOf(fila[2]));	
					} catch (NumberFormatException e) {
								
						throw new Exception("Error al intentar leer los datos del archivo");
					}
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	private void guardarEmpleadoCSV(Empleado emp) throws IOException {
		
	
		try {
			File file = null;
			if(emp.getTipo()==Tipo.CONTRATADO) {
				
				
				 file = new File(".\\EmpleadosContratados.csv");
				
			} else if (emp.getTipo()==Tipo.EFECTIVO) {
				
				 file = new File(".\\EmpleadosEfectivos.csv");	
			}
			
		if (!file.exists()) {
            file.createNewFile();
        }
		
		Writer fileWriter= new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fileWriter);
		String escribir = emp.getCuil() + ";" + emp.getNombre() + ";" + emp.getCostoHora() + ";";
		
		out.write(escribir);	

		out.close();
		
		} catch (Exception e) {
            e.printStackTrace();
        }
	}

	private void guardarTareasTerminadasCSV(String ruta) throws IOException {
		
		List<Tarea> tareas = this.empleados.stream().map((Empleado emp)->emp.getTareasAsignadas()).flatMap(List::stream).filter((Tarea t)-> (!(t.getFechaFin()==null) && !t.getFacturada())).collect(Collectors.toList());
		try {
			
		
		File file = new File(ruta);
		  
		if (!file.exists()) {
            file.createNewFile();
        }
		
		Writer fileWriter= new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fileWriter);
	
		for(Tarea t: tareas) {
				out.write(t.asCsv()+ System.getProperty("line.separator"));	
			}
		
		out.close();
		
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	

	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}
	
	
	
	public Double facturar() throws IOException {
		this.guardarTareasTerminadasCSV(".\\TareasTerminadas.csv");
		return this.empleados.stream().mapToDouble(e -> e.salario()).sum();
	}
	
	
	private Optional<Tarea> buscarTarea(Predicate<Tarea> p){	

		return this.empleados.stream().map((Empleado emp)->emp.getTareasAsignadas()).flatMap(List::stream).filter(p).findFirst();
		
	}
	
}
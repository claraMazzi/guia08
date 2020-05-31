package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.junit.Test;


import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.TareaAsignadaException;
import frsf.isi.died.guia08.problema01.modelo.TareaEmpezadaException;
import frsf.isi.died.guia08.problema01.modelo.TareaFinalizadaException;
import frsf.isi.died.guia08.problema01.modelo.TareaInexistenteException;


public class AppRRHHTest {

	AppRRHH app = new AppRRHH();
	
	Empleado emp1 = new Empleado(40,"Empleado Contratado 1", 20.0, "CONTRATADO");
	Empleado emp2 = new Empleado(50,"Empleado Efectivo 1", 30.0, "EFECTIVO");
	Empleado emp3 = new Empleado(40,"Empleado Contratado 1", 20.0, "CONTRATADO");
	Empleado emp4 = new Empleado(50,"Empleado Efectivo 1", 30.0, "EFECTIVO");

	
	@Test
	public void agregarEmpleadoContratado() {
		
		try {
			assertTrue(app.agregarEmpleadoContratado(40, "Empleado Contratado 1", 20.0));
			
		} catch (EmpleadoExistenteException e) {
			e.printStackTrace();
		}
		assertThrows(EmpleadoExistenteException.class, ()->app.agregarEmpleadoContratado(40, "Empleado Contratado 1", 20.0));

	}

	@Test
	public void agregarEmpleadoEfectivo() {
		
		//Agrego empleado Efectivo
		try {
			assertTrue(app.agregarEmpleadoEfectivo(50, "Empleado Efectivo 1", 30.0));
			
		} catch (EmpleadoExistenteException e) {
			
			e.printStackTrace();
		}
		
		
		//Intengo de agregar un empleado contratado ya existente.
		assertThrows(EmpleadoExistenteException.class, ()->app.agregarEmpleadoEfectivo(50, "Empleado Efectivo 1", 30.0));

	}
	
	@Test 
	public void asignarTarea() {
		
		try {
			app.agregarEmpleadoContratado(40, "Empleado Contratado 1", 20.0);
			app.agregarEmpleadoEfectivo(50, "Empleado Efectivo 1", 30.0);
		} catch (EmpleadoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		assertThrows(EmpleadoInexistenteException.class, ()->app.asignarTarea(60, 1, "Tarea 1", 10));
		

		try {
			app.asignarTarea(40, 1, "Tarea 1", 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		assertThrows(TareaAsignadaException.class, ()->app.asignarTarea(50, 1, "Tarea 1", 10));
		
	}
	
	@Test 
	public void empezarTarea() throws Exception {
		
		app.agregarEmpleadoContratado(40, "Empleado Contratado 1", 20.0);
		app.agregarEmpleadoEfectivo(50, "Empleado Efectivo 1", 30.0);
		app.asignarTarea(40, 2, "Tarea 2", 10);
		app.asignarTarea(40, 3, "Tarea 3", 10);
		app.asignarTarea(40, 4, "Tarea 4", 10);
		app.asignarTarea(40, 5, "Tarea 5", 10);
		app.asignarTarea(50, 7, "Tarea 7", 10);
		app.asignarTarea(50, 8, "Tarea 8", 10);
		
		app.empezarTarea(40, 2);
		
		assertThrows(TareaInexistenteException.class, ()->app.empezarTarea(40, 6));

		assertThrows(EmpleadoInexistenteException.class, ()->app.empezarTarea(60, 2));
	
		
		assertThrows(TareaEmpezadaException.class, ()->app.empezarTarea(40, 2));

	}
	
	@Test 
	public void terminarTarea() throws Exception {
		
		app.agregarEmpleadoContratado(40, "Empleado Contratado 1", 20.0);
		app.agregarEmpleadoEfectivo(50, "Empleado Efectivo 1", 30.0);
		app.asignarTarea(40, 2, "Tarea 2", 10);
		app.asignarTarea(40, 3, "Tarea 3", 10);
		app.asignarTarea(40, 4, "Tarea 4", 10);
		app.asignarTarea(40, 5, "Tarea 5", 10);
		app.asignarTarea(50, 7, "Tarea 7", 10);
		app.asignarTarea(50, 8, "Tarea 8", 10);
		

		assertThrows(TareaInexistenteException.class, ()->app.terminarTarea(40, 6));


		assertThrows(EmpleadoInexistenteException.class, ()->app.terminarTarea(60, 1));
		
		app.empezarTarea(40, 2);
		app.terminarTarea(40, 2);
		
		assertThrows(TareaFinalizadaException.class, ()->app.terminarTarea(40,2));

		
		assertThrows(TareaFinalizadaException.class, ()->app.empezarTarea(40, 2));


	}
	
	@Test 
	public void cargarEmpleadosContratadosCSV() {
		
		try {
			
			app.cargarEmpleadosContratadosCSV(".\\EmpleadosContratados.csv");

		} catch (FileNotFoundException e) {

			System.out.println(e.getMessage());
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			
		} catch (EmpleadoExistenteException e) {
			System.out.println(e.getMessage());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
	}
	
	@Test 
	public void cargarEmpleadosEfectivosCSV() {
		
		try {
			
			app.cargarEmpleadosEfectivosCSV(".\\EmpleadosEfectivos.csv");
			
		} catch (FileNotFoundException e) {

			System.out.println(e.getMessage());
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			
		} catch (EmpleadoExistenteException e) {
			System.out.println(e.getMessage());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
	}
	
	
	@Test 
	public void cargarTareasCSV() {
		
		
		try {
			app.cargarEmpleadosContratadosCSV(".\\EmpleadosContratados.csv");
			app.cargarEmpleadosEfectivosCSV(".\\EmpleadosEfectivos.csv");
			app.cargarTareasCSV(".\\Tareas.csv");
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TareaFinalizadaException e) {
			e.printStackTrace();
		} catch (TareaAsignadaException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test 
	public void facturar() {
		
		
		try {
			
			app.cargarEmpleadosContratadosCSV(".\\EmpleadosContratados.csv");
			app.cargarEmpleadosEfectivosCSV(".\\EmpleadosEfectivos.csv");
			app.cargarTareasCSV(".\\Tareas.csv");
			
			//20.0*4+20.0*4*0.30
			app.empezarTarea(40, 1);
			app.terminarTarea(40, 1);
			
			//20.0*4+20.0*4*0.30
			app.empezarTarea(40, 2);
			app.terminarTarea(40, 2);
			
			//60.0*4+60.0*4*0.20
			app.empezarTarea(60, 4);
			app.terminarTarea(60, 4);
			
			//60.0*4+20.0*4*0.20
			app.empezarTarea(60, 5);
			app.terminarTarea(60, 5);
			

			//Almacena en un archivo "TareasTerminadas"
			Double resultado = app.facturar();
			Double esperado = 784.0;
			assertEquals(resultado, esperado);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmpleadoExistenteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	
}
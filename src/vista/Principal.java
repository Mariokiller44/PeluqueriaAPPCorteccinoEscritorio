package vista;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Comparator;

import controlador.ConexionBD;
import controlador.Configuracion;
import modelo.Cita;
import modelo.Cliente;
import modelo.Personal;
import modelo.Usuario;

public class Principal {

	public static ConexionBD conexionBD;
	public static Configuracion config;
	private static ArrayList<Cliente> listaClientes;
	private static ArrayList<Personal> listaPersonal;
	private static ArrayList<Cita> listaCitas;
	private static Cita cita;

	public static void main(String[] args) {
		try {
			Connection conectar = realizarConexionInicial();
			citasDelEmpleadoMasExitoso(conectar);

			System.out.println("Programa Completado. Vuelva otra vez");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Hubo un error en la conexion " + e.getMessage());
		}
	}

	private static boolean citasDelEmpleadoMasExitoso(Connection conectar) {
		boolean flag=false;
		try {
			listaPersonal = Personal.buscarTodoElPersonal(conectar);
			listaPersonal.sort((Comparator.comparingDouble(Personal::getSalario)).reversed());
			listaCitas = Cita.buscarCitasPorEmpleado(listaPersonal.getFirst().getId(), conectar);
			System.out.println("El empleado que más cobra es " + listaPersonal.getFirst().getNombre() + " "
					+ listaPersonal.getFirst().getApellidos());
			System.out.println("-----------------------------------------");
			System.out.println("|         Detalles de las citas         |");
			System.out.println("-----------------------------------------");
			for (Cita cita : listaCitas) {
				System.out.println("| Cliente  |       " + cita.getCliente().getNombre());
				System.out.println("| Servicio |       " + cita.getHorario().getServicio().getDescripcion());
				System.out.println("| Fecha    |       " + cita.getHorario().getFechaFormateada());
				System.out.println("| Precio   |       " + cita.getHorario().getServicio().getPrecio() + "€");
				System.out.println("-----------------------------------------");

			} 
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error al buscar las citas del empleado más exitoso. "+e.getMessage());
			flag=false;
		}
		return flag;
	}

	private static boolean mostrarEmpleadosQueMasCobra(Connection conectar) {
		boolean flag = false;
		try {
			ArrayList<Personal> listaPersonal;
			listaPersonal = Personal.buscarTodoElPersonal(conectar);
			System.out.println("Datos de los empleados...");
			listaPersonal.sort((Comparator.comparingDouble(Personal::getSalario)).reversed());
			System.out.println("----------Detalles (del que mas cobra al de que menos)-----------\n");
			for (Personal personal : listaPersonal) {
				System.out.println(personal.getNombre() + ", cobra " + personal.getSalario() + "€, y es "
						+ personal.getTipo().toLowerCase() + "\n");
			}
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error al mostrar los empleados que más cobran");
			flag = false;
		}
		return flag;
	}

	private static Connection realizarConexionInicial() {
		System.out.println("Conectando...");
		conexionBD = new ConexionBD("devmario", "marioDev26");
		Connection conectar = conexionBD.getConexion();
		System.out.println("Conexion exitosa.");
		return conectar;
	}
}

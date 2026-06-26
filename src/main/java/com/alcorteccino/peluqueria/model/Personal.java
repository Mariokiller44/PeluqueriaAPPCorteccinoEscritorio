package com.alcorteccino.peluqueria.model;

import com.alcorteccino.peluqueria.model.Usuario;

/**
 * Representa a un miembro del personal de la peluquería.
 *
 * Incluye la información específica relacionada con el puesto desempeñado y el
 * salario asociado.
 *
 * Los datos específicos del personal se almacenan en la tabla Perfiles_Usuario.
 *
 * @author Mario
 * @version 1.0.3
 */
public class Personal extends Usuario {

	private double salario;
	private String tipo;

	public Personal() {
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}

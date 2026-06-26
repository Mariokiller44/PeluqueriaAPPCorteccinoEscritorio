package com.alcorteccino.peluqueria.model;

import com.alcorteccino.peluqueria.model.Usuario;

/**
 * Representa a un cliente registrado en la peluquería.
 *
 * Un cliente es un usuario que posee una categoría asociada para segmentar su
 * relación comercial con el negocio.
 *
 * Las categorías disponibles se almacenan en la tabla
 *
 * @author Mario
 * @version 1.0.3
 */
public class Cliente extends Usuario {
	private String categoria;

	public Cliente() {
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return super.toString() + ", categoría del cliente: " + categoria;
	}

}

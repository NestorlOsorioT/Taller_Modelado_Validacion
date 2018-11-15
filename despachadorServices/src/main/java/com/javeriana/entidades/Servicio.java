package com.javeriana.entidades;



public class Servicio {
	private String nombre = null;

	  public Servicio nombre(String nombre) {
	    this.nombre = nombre;
	    return this;
	  }
	  public String getNombre() {
		    return nombre;
		  }

		  public void setNombre(String nombre) {
		    this.nombre = nombre;
		  }

}

package com.javeriana.edu.co.constantes;

public enum MetodosEnum {
	REST("REST", 1), SOAP("SOAP", 2);

	private String metodo;
	private Integer numeroMetodo;

	private MetodosEnum(String metodo, int numeroMetodo) {
		this.metodo = metodo;
		this.numeroMetodo = numeroMetodo;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public Integer getNumeroMetodo() {
		return numeroMetodo;
	}

	public void setNumeroMetodo(Integer numeroMetodo) {
		this.numeroMetodo = numeroMetodo;
	}

}

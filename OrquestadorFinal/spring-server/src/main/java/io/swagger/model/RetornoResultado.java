package io.swagger.model;

public class RetornoResultado {
	private int idFactura;
	private Double valorFactura;
	private String mensaje;
	
	
	public RetornoResultado() {
		
	}


	public RetornoResultado(int idFactura, Double valorFactura, String mensaje) {
		super();
		this.idFactura = idFactura;
		this.valorFactura = valorFactura;
		this.mensaje = mensaje;
	}


	public int getIdFactura() {
		return idFactura;
	}


	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}


	public Double getValorFactura() {
		return valorFactura;
	}


	public void setValorFactura(Double valorFactura) {
		this.valorFactura = valorFactura;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	

}

package com.javeriana.entidades;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Factura {

	private int idFactura;
	private Double valorFactura;
	
	public Factura() {
		
	}
	
	

	public Factura(int idFactura, Double valorFactura) {
		this.idFactura = idFactura;
		this.valorFactura = valorFactura;
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


}

package com.javeriana.edu.co.service;

import java.io.Serializable;

public class DispatcherResponse  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5434039116014733821L;
	private int idFactura;
    private double valorFactura;
    private Integer idFacturaPago;
    private double valorPago;
    private String mensaje;
    
    public DispatcherResponse() {
    }

    public DispatcherResponse(double valorFactura) {
        this.valorFactura = valorFactura;
    }

    public DispatcherResponse(int idFactura, double valorFactura) {
        this.idFactura = idFactura;
        this.valorFactura = valorFactura;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public double getValorFactura() {
        return valorFactura;
    }

    public void setValorFactura(double valorFactura) {
        this.valorFactura = valorFactura;
    }

	public Integer getIdFacturaPago() {
		return idFacturaPago;
	}

	public void setIdFacturaPago(Integer idFacturaPago) {
		this.idFacturaPago = idFacturaPago;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}

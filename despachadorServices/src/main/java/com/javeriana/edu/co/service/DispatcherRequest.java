package com.javeriana.edu.co.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;
@Service
public class DispatcherRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6594942228404724201L;
	private Integer idFacturaPago;
    private String url;
    
    public DispatcherRequest() {} 
    
	public DispatcherRequest(Integer idFacturaPago, String url) {
        this.idFacturaPago = idFacturaPago;
        this.url = url;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIdFacturaPago() {
		return idFacturaPago;
	}

	public void setIdFacturaPago(Integer idFacturaPago) {
		this.idFacturaPago = idFacturaPago;
	}

	
}
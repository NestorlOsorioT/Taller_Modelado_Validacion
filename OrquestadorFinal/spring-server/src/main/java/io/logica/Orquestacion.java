package io.logica;


import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import io.swagger.model.Factura;
import io.swagger.model.RetornoResultado;
import io.swagger.model.Servicio;

public class Orquestacion {
	static final String URL_ROUTING = "http://localhost:5000/routing";
	static final String URL_DISPATCHING = "http://localhost:8080/despacharServicio/transformarServicio?leerServicios=";
public String obtenerConvenio(String numeroFactura, String operacion, BigDecimal valor) {
	String res = "";
	Factura newFactura = new Factura();
    newFactura.setNroFactura(numeroFactura);
    newFactura.setOperacion(operacion);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept", MediaType.APPLICATION_XML_VALUE);
    headers.setContentType(MediaType.APPLICATION_XML);

    RestTemplate restTemplate = new RestTemplate();

    // Data attached to the request.
    HttpEntity<Factura> requestBody = new HttpEntity<>(newFactura, headers);

    // Send request with POST method.
    Servicio e = restTemplate.postForObject(URL_ROUTING, requestBody, Servicio.class);
    res = e.getNombre();
    if (e != null && e.getNombre() != null) {

       System.out.println("Dato Recibido: " + e.getNombre());
       String cadena = e.getNombre() + "&idfactura="+numeroFactura + "&valorfactura="+Double.parseDouble(""+valor);
       RetornoResultado result = restTemplate.getForObject(URL_DISPATCHING + cadena, RetornoResultado.class);
       res = "{'idfactura':"+result.getIdFactura()+",'Mensaje':"+result.getMensaje()+",'Valorfactura':"+result.getValorFactura()+"}";
    } else {
       System.out.println("Error!");
    }

	return res;
}
}

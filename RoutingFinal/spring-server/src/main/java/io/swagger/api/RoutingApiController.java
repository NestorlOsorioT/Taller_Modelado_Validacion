package io.swagger.api;

import io.swagger.model.Factura;
import io.swagger.model.Servicio;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.logica.Routing;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-13T00:43:10.373Z")

@Controller
public class RoutingApiController implements RoutingApi {

    private static final Logger log = LoggerFactory.getLogger(RoutingApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public RoutingApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Servicio> routingPost(@ApiParam(value = "Numero de la factura y operacion a consumir" ,required=true )  @Valid @RequestBody Factura body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	Routing r = new Routing();
            	String ruta = r.devolverRuta(body.getNroFactura(),body.getOperacion());
            	Servicio res = new Servicio();
            	res.setNombre(ruta);
            	return new ResponseEntity<Servicio>(res, HttpStatus.OK);
            	//return new ResponseEntity<Servicio>(objectMapper.readValue("{  \"nombre\" : \"http://localhost:9090/servicios/pagos/v1/payments1|2|plantillaxml|operacion|metodo\"}", Servicio.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Servicio>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (accept != null && accept.contains("application/xml")) {
            try {
            	Routing r = new Routing();
            	String ruta = r.devolverRuta(body.getNroFactura(),body.getOperacion());
            	Servicio res = new Servicio();
            	res.setNombre(ruta);
            	return new ResponseEntity<Servicio>(res, HttpStatus.OK);
                //return new ResponseEntity<Servicio>(objectMapper.readValue("<servicio>  <nombre>http://localhost:9090/servicios/pagos/v1/payments1|2|plantillaxml|operacion|metodo</nombre></servicio>", Servicio.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/xml", e);
                return new ResponseEntity<Servicio>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Servicio>(HttpStatus.NOT_IMPLEMENTED);
    }

}

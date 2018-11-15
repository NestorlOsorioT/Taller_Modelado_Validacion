package io.swagger.api;

import io.swagger.model.Factura;
import io.swagger.model.RespuestaOperacion;
import io.swagger.model.Servicio;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.logica.Orquestacion;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-13T01:11:41.400Z")

@Controller
public class OrquestadorApiController implements OrquestadorApi {

    private static final Logger log = LoggerFactory.getLogger(OrquestadorApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public OrquestadorApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<RespuestaOperacion> orquestadorPost(@ApiParam(value = "Datos de la factura" ,required=true )  @Valid @RequestBody Factura body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                Orquestacion o = new Orquestacion();
                String convenio = o.obtenerConvenio(body.getNroFactura(), body.getOperacion(),body.getValor());
                RespuestaOperacion res = new RespuestaOperacion();
                res.setConfirmacion(convenio);
            	return new ResponseEntity<RespuestaOperacion>(res, HttpStatus.OK);
            	//return new ResponseEntity<RespuestaOperacion>(objectMapper.readValue("{  \"confirmacion\" : \"Pago realizado\"}", RespuestaOperacion.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<RespuestaOperacion>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (accept != null && accept.contains("application/xml")) {
            try {
            	Orquestacion o = new Orquestacion();
                String convenio = o.obtenerConvenio(body.getNroFactura(), body.getOperacion(),body.getValor());
                RespuestaOperacion res = new RespuestaOperacion();
                res.setConfirmacion(convenio);
            	return new ResponseEntity<RespuestaOperacion>(res, HttpStatus.OK);
            	
            	//return new ResponseEntity<RespuestaOperacion>(objectMapper.readValue("<respuestaPago>  <confirmacion>Pago realizado</confirmacion></respuestaPago>", RespuestaOperacion.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/xml", e);
                return new ResponseEntity<RespuestaOperacion>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<RespuestaOperacion>(HttpStatus.NOT_IMPLEMENTED);
    }

}

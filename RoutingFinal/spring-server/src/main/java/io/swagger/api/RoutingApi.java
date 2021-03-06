/**
 * NOTE: This class is auto generated by the swagger code generator program (2.3.1).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Factura;
import io.swagger.model.Servicio;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-13T00:43:10.373Z")

@Api(value = "routing", description = "the routing API")
public interface RoutingApi {

    @ApiOperation(value = "", nickname = "routingPost", notes = "Retorna la ruta del servicio al que corresponde la factura", response = Servicio.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Retorna los datos del servicio que se debe consumir.", response = Servicio.class),
        @ApiResponse(code = 404, message = "Error en los parámetros, página no encontrada.") })
    @RequestMapping(value = "/routing",
        produces = { "application/json", "application/xml" }, 
        consumes = { "application/json", "application/xml" },
        method = RequestMethod.POST)
    ResponseEntity<Servicio> routingPost(@ApiParam(value = "Numero de la factura y operacion a consumir" ,required=true )  @Valid @RequestBody Factura body);

}

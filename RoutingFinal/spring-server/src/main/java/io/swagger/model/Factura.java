package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Factura
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-13T00:43:10.373Z")

public class Factura   {
  @JsonProperty("nroFactura")
  private String nroFactura = null;

  @JsonProperty("operacion")
  private String operacion = null;

  public Factura nroFactura(String nroFactura) {
    this.nroFactura = nroFactura;
    return this;
  }

  /**
   * Get nroFactura
   * @return nroFactura
  **/
  @ApiModelProperty(example = "= \"1234567\"", required = true, value = "")
  @NotNull


  public String getNroFactura() {
    return nroFactura;
  }

  public void setNroFactura(String nroFactura) {
    this.nroFactura = nroFactura;
  }

  public Factura operacion(String operacion) {
    this.operacion = operacion;
    return this;
  }

  /**
   * Get operacion
   * @return operacion
  **/
  @ApiModelProperty(example = "= \"Consultar,Pagar,Compensar\"", required = true, value = "")
  @NotNull


  public String getOperacion() {
    return operacion;
  }

  public void setOperacion(String operacion) {
    this.operacion = operacion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Factura factura = (Factura) o;
    return Objects.equals(this.nroFactura, factura.nroFactura) &&
        Objects.equals(this.operacion, factura.operacion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nroFactura, operacion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Factura {\n");
    
    sb.append("    nroFactura: ").append(toIndentedString(nroFactura)).append("\n");
    sb.append("    operacion: ").append(toIndentedString(operacion)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


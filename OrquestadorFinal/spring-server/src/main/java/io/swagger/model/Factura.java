package io.swagger.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Factura
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-13T01:11:41.400Z")

public class Factura   {
  @JsonProperty("nroFactura")
  private String nroFactura = null;

  @JsonProperty("operacion")
  private String operacion = null;

  @JsonProperty("valor")
  private BigDecimal valor = null;

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
  @ApiModelProperty(example = "=\"Consultar,Pagar,Compensar\"", required = true, value = "")
  @NotNull


  public String getOperacion() {
    return operacion;
  }

  public void setOperacion(String operacion) {
    this.operacion = operacion;
  }

  public Factura valor(BigDecimal valor) {
    this.valor = valor;
    return this;
  }

  /**
   * Get valor
   * @return valor
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
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
        Objects.equals(this.operacion, factura.operacion) &&
        Objects.equals(this.valor, factura.valor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nroFactura, operacion, valor);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Factura {\n");
    
    sb.append("    nroFactura: ").append(toIndentedString(nroFactura)).append("\n");
    sb.append("    operacion: ").append(toIndentedString(operacion)).append("\n");
    sb.append("    valor: ").append(toIndentedString(valor)).append("\n");
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


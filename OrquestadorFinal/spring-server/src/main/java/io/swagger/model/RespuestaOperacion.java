package io.swagger.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * RespuestaOperacion
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-13T01:11:41.400Z")

public class RespuestaOperacion   {
  @JsonProperty("confirmacion")
  private String confirmacion = null;

  public RespuestaOperacion confirmacion(String confirmacion) {
    this.confirmacion = confirmacion;
    return this;
  }

  /**
   * Get confirmacion
   * @return confirmacion
  **/
  @ApiModelProperty(example = "Pago realizado", required = true, value = "")
  @NotNull


  public String getConfirmacion() {
    return confirmacion;
  }

  public void setConfirmacion(String confirmacion) {
    this.confirmacion = confirmacion;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RespuestaOperacion respuestaOperacion = (RespuestaOperacion) o;
    return Objects.equals(this.confirmacion, respuestaOperacion.confirmacion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(confirmacion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RespuestaOperacion {\n");
    
    sb.append("    confirmacion: ").append(toIndentedString(confirmacion)).append("\n");
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


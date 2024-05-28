package api_festivos.api_festivos.entidades.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FestivoDto {
  private String festivo;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date fecha;

  // Constructores
  public FestivoDto() {
  }

  public FestivoDto(String festivo, Date fecha) {
    this.festivo = festivo;
    this.fecha = fecha;
  }

  // Getters y Setters

  public String getFestivo() {
    return festivo;
  }

  public void setFestivo(String festivo) {
    this.festivo = festivo;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

}

package api_festivos.api_festivos.interfaces;

import java.util.Date;
import java.util.List;

import api_festivos.api_festivos.entidades.dtos.FestivoDto;

public interface IFestivoServicio {
  // Lista de métodos públicos que se implementarán en la clase FestivoServicio

  // Método que devuelve una lista de festivos dado el año
  public List<FestivoDto> obtenerFestivosPorAño(int año);

  // Métodos para validar si la fecha es válida
  public boolean esFechaValida(String fecha);

  // Método para validar si la fecha dada es festivo
  public boolean esFestivo(Date fecha);
}

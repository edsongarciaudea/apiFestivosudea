package api_festivos.api_festivos.controladores;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_festivos.api_festivos.entidades.dtos.FestivoDto;
import api_festivos.api_festivos.servicios.FestivoServicio;

@RestController
@RequestMapping("/festivos")
public class FestivoControlador {

  private FestivoServicio festivoServicio;

  public FestivoControlador(FestivoServicio festivoServicio) {
    this.festivoServicio = festivoServicio;
  }

  @SuppressWarnings("deprecation")
  @GetMapping("verificar/{año}/{mes}/{dia}")
  public String verificarSiEsFestivo(@PathVariable int año, @PathVariable int mes, @PathVariable int dia) {
    // Valido si la fecha es válida
    if (festivoServicio.esFechaValida(String.valueOf(año) + "-" + String.valueOf(mes) + "-" + String.valueOf(dia))) {
      Date fecha = new Date(año - 1900, mes - 1, dia);
      if (festivoServicio.esFestivo(fecha)) {
        return "Es festivo";
      } else {
        return "No es festivo";
      }
    } else {
      return "Fecha no válida";
    }
  }

  @GetMapping("listar/{año}")
  public List<FestivoDto> listarFestivosPorAño(@PathVariable int año) {
    return festivoServicio.obtenerFestivosPorAño(año);
  }
}

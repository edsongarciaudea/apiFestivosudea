package api_festivos.api_festivos.servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import api_festivos.api_festivos.entidades.Festivo;
import api_festivos.api_festivos.entidades.dtos.FestivoDto;
import api_festivos.api_festivos.interfaces.IFestivoServicio;
import api_festivos.api_festivos.repositorios.IFestivoRepositorio;

@Service
public class FestivoServicio implements IFestivoServicio {

  private IFestivoRepositorio festivoRepositorio;

  public FestivoServicio(IFestivoRepositorio festivoRepositorio) {
    this.festivoRepositorio = festivoRepositorio;
  }

  public boolean esFechaValida(String fecha) {
    try {
      SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
      formatoFecha.setLenient(false);
      formatoFecha.parse(fecha);
      return true;
    } catch (ParseException e) {
      return false;
    }
  }

  @SuppressWarnings("deprecation")
  private Date conseguirDomingoPascuaPorAño(final int año) {
    final int a = año % 19;
    final int b = año / 100;
    final int c = año % 100;
    final int d = b / 4;
    final int e = b % 4;
    final int f = (b + 8) / 25;
    final int g = (b - f + 1) / 3;
    final int h = (19 * a + b - d - g + 15) % 30;
    final int i = c / 4;
    final int k = c % 4;
    final int l = (32 + 2 * e + 2 * i - h - k) % 7;
    final int m = (a + 11 * h + 22 * l) / 451;
    final int mes = (h + l - 7 * m + 114) / 31;
    final int dia = ((h + l - 7 * m + 114) % 31) + 1;
    return new Date(año - 1900, mes - 1, dia);
  }

  private Date moverAlSiguienteLunes(final Date fecha) {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(fecha);
    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
    return calendar.getTime();
  }

  private Date agregarDiasAFecha(final Date fecha, final int dias) {
    final Calendar calendar = Calendar.getInstance();
    calendar.setTime(fecha);
    calendar.add(Calendar.DAY_OF_MONTH, dias);
    return calendar.getTime();
  }

  @SuppressWarnings("deprecation")
  private List<Festivo> conseguirFestivosPorAño(final List<Festivo> festivos, final int año) {
    if (festivos != null) {
      final Date domingoDePascua = conseguirDomingoPascuaPorAño(año);

      int i = 0;

      // Tipo 1. Festivos inamovibles
      // Tipo 2. Festivos trasladables al siguiente lunes
      // Tipo 3. Festivos que dependen del domingo de pascua
      // Tipo 4. Festivos que dependen del domingo de pascua y se trasladan al
      // siguiente lunes
      for (final Festivo festivo : festivos) {
        if (festivo.getTipo().getId() == 1) {
          festivo.setFecha(new Date(año - 1900, festivo.getMes() - 1, festivo.getDia()));
        } else if (festivo.getTipo().getId() == 2) {
          final Date fecha = new Date(año - 1900, festivo.getMes() - 1, festivo.getDia());
          festivo.setFecha(moverAlSiguienteLunes(fecha));
        } else if (festivo.getTipo().getId() == 3) {
          final Date fecha = agregarDiasAFecha(domingoDePascua, festivo.getDiaspascua());
          festivo.setFecha(fecha);
        } else if (festivo.getTipo().getId() == 4) {
          final Date fecha = agregarDiasAFecha(domingoDePascua, festivo.getDiaspascua());
          festivo.setFecha(moverAlSiguienteLunes(fecha));
        }
        festivos.set(i, festivo);
        i++;
      }
    }

    return festivos;
  }

  public boolean sonFechasIguales(Date fecha1, Date fecha2) {
    final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    return formatoFecha.format(fecha1).equals(formatoFecha.format(fecha2));
  }

  @SuppressWarnings("deprecation")
  private boolean esFestivo(Date fecha, List<Festivo> festivos) {
    if (festivos != null) {
      // Actualizar los festivos dado el año
      festivos = conseguirFestivosPorAño(festivos, fecha.getYear() + 1900);      
      for (final Festivo festivo : festivos) {
        if (sonFechasIguales(fecha, festivo.getFecha())) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public boolean esFestivo(Date fecha) {
    List<Festivo> festivos = festivoRepositorio.findAll();
    return esFestivo(fecha, festivos);
  }

  @Override
  public List<FestivoDto> obtenerFestivosPorAño(int año) {
    List<Festivo> festivos = festivoRepositorio.findAll();
    festivos = conseguirFestivosPorAño(festivos, año);
    List<FestivoDto> festivosActualizados = new ArrayList<FestivoDto>();

    for (final Festivo festivo : festivos) {
      festivosActualizados.add(new FestivoDto(festivo.getNombre(), festivo.getFecha()));
    }

    return festivosActualizados;
  }

}

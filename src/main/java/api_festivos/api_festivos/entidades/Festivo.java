package api_festivos.api_festivos.entidades;

import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Festivo {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "tipo_id_seq")
  @GenericGenerator(name = "tipo_id_seq", strategy = "increment")
  private int id;

  @Column(name="dia")
  private int dia;

  @Column(name="mes")
  private int mes;

  @Column(name="nombre")
  private String nombre;

  @Column(name="diaspascua")
  private int diaspascua;

  @ManyToOne
  @JoinColumn(name="idtipo", referencedColumnName = "id")
  private Tipo tipo;

  private Date fecha;

  // Constructores

  public Festivo() {
  }

  public Festivo(int id, int dia, int mes, String nombre, int diaspascua, Tipo tipo) {
    this.id = id;
    this.dia = dia;
    this.mes = mes;
    this.nombre = nombre;
    this.diaspascua = diaspascua;
    this.tipo = tipo;
  }

  // Getters y Setters

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDia() {
    return dia;
  }

  public void setDia(int dia) {
    this.dia = dia;
  }

  public int getMes() {
    return mes;
  }

  public void setMes(int mes) {
    this.mes = mes;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getDiaspascua() {
    return diaspascua;
  }

  public void setDiaspascua(int diaspascua) {
    this.diaspascua = diaspascua;
  }

  public Tipo getTipo() {
    return tipo;
  }

  public void setTipo(Tipo tipo) {
    this.tipo = tipo;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
}

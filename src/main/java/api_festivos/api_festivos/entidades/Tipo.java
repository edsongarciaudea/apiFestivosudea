package api_festivos.api_festivos.entidades;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tipo {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "tipo_id_seq")
  @GenericGenerator(name = "tipo_id_seq", strategy = "increment")
  private int id;

  @Column(name = "tipo", unique = true)
  private String tipo;

  // Constructores
  public Tipo() {
  }

  public Tipo(int id, String tipo) {
    this.id = id;
    this.tipo = tipo;
  }

  // Getters y Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}

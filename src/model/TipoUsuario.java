package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TipoUsuario {
    private int id;
    private String nome;
    private LocalTime horasPermitidas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalTime getHorasPermitidas() {
        return horasPermitidas;
    }

    public void setHorasPermitidas(LocalTime horasPermitidas) {
        this.horasPermitidas = horasPermitidas;
    }

    @Override
    public String toString() {
        return "TipoUsuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", horasPermitidas=" + horasPermitidas +
                '}';
    }
}

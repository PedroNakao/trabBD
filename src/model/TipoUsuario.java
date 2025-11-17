package model;

import java.time.LocalDateTime;

public class TipoUsuario {
    private int id;
    private String nome;
    private LocalDateTime horasPermitidas;

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

    public LocalDateTime getHorasPermitidas() {
        return horasPermitidas;
    }

    public void setHorasPermitidas(LocalDateTime horasPermitidas) {
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

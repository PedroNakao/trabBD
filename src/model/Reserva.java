package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
    private int ReservaId;
    private Sala SalaId;
    private Usuario UsuarioId;
    private Recurso RecursoId;
    private LocalDate dataReserva;
    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFim;

    public int getReservaId() {
        return ReservaId;
    }

    public void setReservaId(int reservaId) {
        ReservaId = reservaId;
    }

    public Sala getSalaId() {
        return SalaId;
    }

    public void setSalaId(Sala salaId) {
        SalaId = salaId;
    }

    public Usuario getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        UsuarioId = usuarioId;
    }

    public Recurso getRecursoId() {
        return RecursoId;
    }

    public void setRecursoId(Recurso recursoId) {
        RecursoId = recursoId;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalDateTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalDateTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "ReservaId=" + ReservaId +
                ", SalaId=" + SalaId +
                ", UsuarioId=" + UsuarioId +
                ", RecursoId=" + RecursoId +
                ", dataReserva=" + dataReserva +
                ", horarioInicio=" + horarioInicio +
                ", horarioFim=" + horarioFim +
                '}';
    }
}


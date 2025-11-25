package controller;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import persisitence.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaController {
    private ReservaDao rDao;
    private SalaDao sDao;
    private UsuarioDao uDao;
    private RecursoDao reDao;


    public ReservaController(ReservaDao rDao, SalaDao sDao, UsuarioDao uDao, RecursoDao reDao){
        this.uDao = uDao;
        this.sDao = sDao;
        this.uDao = uDao;
        this.reDao = reDao;
    }

    ObservableList<Reserva> lista = FXCollections.observableArrayList();
    ObservableList<Sala> listaSalas = FXCollections.observableArrayList();
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    ObservableList<Recurso> listaRecursos = FXCollections.observableArrayList();

    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<Sala> sala = new SimpleObjectProperty<>();
    private ObjectProperty<Usuario> usuario = new SimpleObjectProperty<>();
    private ObjectProperty<Recurso> recurso = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> dataReserva = new SimpleObjectProperty<>();
    private ObjectProperty<LocalTime> horaInicio = new SimpleObjectProperty<>();
    private ObjectProperty<LocalTime> horaFim = new SimpleObjectProperty<>();

    public IntegerProperty idProperty() {
        return id;
    }
    public ObjectProperty<Sala> salaProperty(){
        return sala;
    }
    public ObjectProperty<Usuario> usuarioProperty(){
        return usuario;
    }
    public ObjectProperty<Recurso> recursoProperty(){
        return recurso;
    }
    public ObjectProperty<LocalDate> dataReservaProperty(){
        return dataReserva;
    }
    public ObjectProperty<LocalTime> horaInicioProperty(){
        return horaInicio;
    }
    public ObjectProperty<LocalTime> horaFimProperty(){
        return horaFim;
    }

    public ObservableList<Reserva> listaProperty() {
        return lista;
    }
    public ObservableList<Sala> getListaSalas() {
        return listaSalas;
    }
    public ObservableList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    public ObservableList<Recurso> getListaRecursos() {
        return listaRecursos;
    }

    public Reserva toEntity(){
        Reserva r = new Reserva();
        r.setReservaId(id.get());
        r.setSalaId(sala.get());
        r.setUsuarioId(usuario.get());
        r.setRecursoId(recurso.get());
        r.setDataReserva(dataReserva.get());
        return r;
    }
    public void fromEntity(Reserva r){
        id.set(r.getReservaId());
        sala.set(r.getSalaId());
        usuario.set(r.getUsuarioId());
        recurso.set(r.getRecursoId());
        dataReserva.set(r.getDataReserva());
        horaInicio.set(r.getHorarioInicio());
        horaFim.set(r.getHorarioFim());
    }

    public void inserir() throws SQLException, ClassNotFoundException{
        Reserva r = toEntity();
        rDao.inserir(r);
        lista.add(r);
    }
    public void deletar(Reserva r) throws SQLException, ClassNotFoundException {
        rDao.excluir(r);
        lista.remove(r);
    }
    public void modificar() throws SQLException, ClassNotFoundException {
        Reserva r = toEntity();
        rDao.atualizar(r);
        listar();
    }

    public void buscarPorId() throws SQLException, ClassNotFoundException {
        Reserva filtro = new Reserva();
        filtro.setReservaId(id.get());

        Reserva r = rDao.consultar(filtro);

        if(r != null && r.getReservaId() != 0){
            fromEntity(r);
        }
    }
    public void listar() throws SQLException, ClassNotFoundException {
        lista.clear();
        lista.addAll(rDao.listar(null));
    }
    public void limparCampos(){
        id.set(0);
        sala.set(null);
        usuario.set(null);
        recurso.set(null);
        dataReserva.set(LocalDate.of(0,0,0));
        horaInicio.set(LocalTime.of(0,0));
        horaFim.set(LocalTime.of(0,0));

    }
    public void carregarTipos() throws SQLException, ClassNotFoundException {
        listaSalas.clear();
        Sala filtro = new Sala();
        listaSalas.addAll(sDao.listar(filtro));

        listaUsuarios.clear();
        Usuario filtro2 = new Usuario();
        listaUsuarios.addAll(uDao.listar(filtro2));

        listaRecursos.clear();
        Recurso filtro3 = new Recurso() ;
        listaRecursos.addAll(reDao.listar(filtro3));
    }
}



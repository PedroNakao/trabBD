package controller;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.TipoUsuario;
import persisitence.TipoUsuarioDao;
import java.sql.SQLException;
import java.time.LocalTime;

public class TipoUsuarioController {
    ObservableList<TipoUsuario> lista = FXCollections.observableArrayList();
    private TipoUsuarioDao tDao;

    public TipoUsuarioController(TipoUsuarioDao tDao){this.tDao = tDao;}

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private ObjectProperty<LocalTime> horasPermitidas = new SimpleObjectProperty<>();
    public ObservableList<TipoUsuario> listaProperty(){
        return lista;
    }

    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty nomeProperty(){
        return nome;
    }
    public ObjectProperty<LocalTime> horasPermitidasProperty(){
        return horasPermitidas;
    }

    public ObservableList<TipoUsuario> getLista() {
        return lista;
    }

    public void fromEntity(TipoUsuario t){
        id.set(t.getId());
        nome.set(t.getNome());
        horasPermitidas.set(t.getHorasPermitidas());
    }
    public TipoUsuario toEntity(){
        TipoUsuario t = new TipoUsuario();
        t.setId(id.get());
        t.setNome(nome.get());
        t.setHorasPermitidas(horasPermitidas.get());
        return t;
    }
    public void inserir() throws SQLException, ClassNotFoundException{
        TipoUsuario t = toEntity();
        tDao.inserir(t);
        lista.add(t);
    }
    public void deletar(TipoUsuario t) throws SQLException, ClassNotFoundException {
        tDao.excluir(t);
        lista.remove(t);
    }
    public void modificar() throws SQLException, ClassNotFoundException {
        TipoUsuario t = toEntity();
        tDao.atualizar(t);
        listar();
    }

    public void buscarPorId() throws SQLException, ClassNotFoundException {
        TipoUsuario filtro = new TipoUsuario();
        filtro.setId(id.get());

        TipoUsuario t = tDao.consultar(filtro);

        if(t != null && t.getId() != 0){
            fromEntity(t);
        }
    }
    public void listar() throws SQLException, ClassNotFoundException {
        lista.clear();
        lista.addAll(tDao.listar(null));
    }
    public void limparCampos(){
        id.set(0);
        nome.set("");
        horasPermitidas.set(LocalTime.of(0,0));
    }
}

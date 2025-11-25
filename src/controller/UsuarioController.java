package controller;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.TipoUsuario;
import model.Usuario;
import persisitence.GenericDao;
import persisitence.TipoUsuarioDao;
import persisitence.UsuarioDao;

import java.sql.SQLException;

public class UsuarioController {
    ObservableList<Usuario> lista = FXCollections.observableArrayList();
    ObservableList<TipoUsuario> listaTipos = FXCollections.observableArrayList();
    private UsuarioDao uDao;
    private TipoUsuarioDao tDao;

    public UsuarioController(UsuarioDao uDao, TipoUsuarioDao tDao) {
        this.uDao = uDao;
        this.tDao = tDao;
    }

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private ObjectProperty<TipoUsuario> tipoUsuario = new SimpleObjectProperty<>();

    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty nomeProperty(){
        return nome;
    }
    public StringProperty emailProperty(){
        return email;
    }
    public ObjectProperty<TipoUsuario> tipoUsuarioObjectProperty(){
        return tipoUsuario;
    }
    public ObservableList<Usuario> listaProperty() {
        return lista;
    }
    public ObservableList<TipoUsuario> getListaTipos() {
        return listaTipos;
    }

    public Usuario toEntity(){
        Usuario u = new Usuario();
        u.setId(id.get());
        u.setNome(nome.get());
        u.setEmail(email.get());
        u.setTipoUsuario(tipoUsuario.get());
        return u;
    }
    public void fromEntity(Usuario u){
        id.set(u.getId());
        nome.set(u.getNome());
        email.set(u.getEmail());
        tipoUsuario.set(u.getTipoUsuario());
    }

    public void inserir() throws SQLException, ClassNotFoundException{
        Usuario u = toEntity();
        uDao.inserir(u);
        lista.add(u);
    }
    public void deletar(Usuario u) throws SQLException, ClassNotFoundException {
        uDao.excluir(u);
        lista.remove(u);
    }
    public void modificar() throws SQLException, ClassNotFoundException {
        Usuario u = toEntity();
        uDao.atualizar(u);
        listar();
    }

    public void buscarPorId() throws SQLException, ClassNotFoundException {
        Usuario filtro = new Usuario();
        filtro.setId(id.get());

        Usuario u = uDao.consultar(filtro);

        if(u != null && u.getId() != 0){
            fromEntity(u);
        }
    }
    public void listar() throws SQLException, ClassNotFoundException {
        lista.clear();
        lista.addAll(uDao.listar(null));
    }
    public void limparCampos(){
        id.set(0);
        nome.set("");
        email.set("");
        tipoUsuario.set(null);

    }
    public void carregarTipos() throws SQLException, ClassNotFoundException {
        listaTipos.clear();
        TipoUsuario filtro = new TipoUsuario();
        listaTipos.addAll(tDao.listar(filtro));
    }
}




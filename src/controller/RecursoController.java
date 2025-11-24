package controller;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Recurso;
import persisitence.RecursoDao;

import java.sql.SQLException;

public class RecursoController {
    ObservableList<Recurso> lista = FXCollections.observableArrayList();
    private RecursoDao rDao;

    public RecursoController(RecursoDao rDao){
        this.rDao = rDao;
    }
    //Properties
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty descricao = new SimpleStringProperty();
    private BooleanProperty emManutencao = new SimpleBooleanProperty();


    public IntegerProperty idProperty(){
        return id;
    }
    public StringProperty nomeProperty(){
        return nome;
    }
    public StringProperty descricaoProperty(){
        return descricao;
    }
    public BooleanProperty emManutencaoProperty(){
        return emManutencao;
    }
    public ObservableList<Recurso> listaProperty(){
        return lista;
    }

    public void fromEntity(Recurso r){
        id.set(r.getId());
        nome.set(r.getNome());
        descricao.set(r.getDescricao());
        emManutencao.set(r.getisEmManutencao());
    }
    public Recurso toEntity(){
        Recurso r = new Recurso();
        r.setId(id.get());
        r.setNome(nome.get());
        r.setDescricao(descricao.get());
        r.setEmManutencao(emManutencao.get());
        return r;
    }

    public void inserir() throws SQLException, ClassNotFoundException{
        Recurso r = toEntity();
        rDao.inserir(r);
        lista.add(r);
    }
    public void deletar(Recurso r) throws SQLException, ClassNotFoundException {
        rDao.excluir(r);
        lista.remove(r);
    }
    public void modificar(Recurso r) throws SQLException, ClassNotFoundException {
        rDao.atualizar(r);
    }
    public void buscarPorId() throws SQLException, ClassNotFoundException {
        Recurso filtro = new Recurso();
        filtro.setId(id.get());

        Recurso r = rDao.consultar(filtro);
        lista.clear();

        if(r != null & r.getId() != 0){
            lista.add(r);
            fromEntity(r);
        }
    }
    public void listar() throws SQLException, ClassNotFoundException {
        lista.clear();
        lista.addAll(rDao.listar(null));
    }
    public void limparCampos(){
        id.set(0);
        nome.set("");
        descricao.set("");
        emManutencao.set(false);
    }
}

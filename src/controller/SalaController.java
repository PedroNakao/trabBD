package controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Recurso;
import model.Sala;
import persisitence.SalaDao;

import java.sql.SQLException;

public class SalaController {
    ObservableList<Sala> lista = FXCollections.observableArrayList();
    private SalaDao sDao;

    public SalaController(SalaDao sDao){this.sDao = sDao;}

    //Properties
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private IntegerProperty capacidade = new SimpleIntegerProperty();

    public IntegerProperty idProperty(){
        return id;
    }
    public StringProperty nomeProperty(){
        return nome;
    }
    public IntegerProperty capacidadeProperty(){return capacidade;}
    public ObservableList<Sala> ListaProperty() {return lista;}

    public void fromEntity(Sala s){
        id.set(s.getId());
        nome.set(s.getNome());
        capacidade.set(s.getCapacidade());
    }
    public Sala toEntity(){
        Sala s = new Sala();
        s.setId(id.get());
        s.setNome(nome.get());
        s.setCapacidade(capacidade.get());
        return s;
    }

    public void inserir() throws SQLException, ClassNotFoundException{
        Sala s = toEntity();
        sDao.inserir(s);
        lista.add(s);
    }
    public void deletar(Sala s) throws SQLException, ClassNotFoundException {
        sDao.excluir(s);
        lista.remove(s);
    }
    public void modificar() throws SQLException, ClassNotFoundException {
        Sala s = toEntity();
        sDao.atualizar(s);
        listar();
    }

    public void buscarPorId() throws SQLException, ClassNotFoundException {
        Sala filtro = new Sala();
        filtro.setId(id.get());

        Sala s = sDao.consultar(filtro);

        if(s != null && s.getId() != 0){
            fromEntity(s);
        }
    }
    public void listar() throws SQLException, ClassNotFoundException {
        lista.clear();
        lista.addAll(sDao.listar(null));
    }
    public void limparCampos(){
        id.set(0);
        nome.set("");
        capacidade.set(0);

    }
}

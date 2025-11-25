package view;

import controller.RecursoController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.Recurso;
import persisitence.GenericDao;
import persisitence.RecursoDao;

import java.sql.SQLException;

public class RecursoView implements Tela {
    private TextField txtID = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtDescricao = new TextField();
    private RecursoController control = new RecursoController(new RecursoDao(new GenericDao()));
    private TableView<Recurso> tblRecurso = new TableView<Recurso>();
    private CheckBox txtManutencao = new CheckBox();

    @Override
    public Pane render() {
        BorderPane panePrincipal = new  BorderPane();
        GridPane gridCadastro = new GridPane();

        gridCadastro.add(new Label("ID:"), 0,0);
        gridCadastro.add(txtID, 1,0);
        gridCadastro.add(new Label("Nome :"), 0,1);
        gridCadastro.add(txtNome, 1,1);
        gridCadastro.add(new Label("Descrição :"), 0,2);
        gridCadastro.add(txtDescricao, 1,2);
        gridCadastro.add(new Label("Em manutenção:"), 0,3);
        gridCadastro.add(txtManutencao, 1,3);


        Button btnInserir = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");

        TableColumn<Recurso, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(e ->
                new ReadOnlyIntegerWrapper(e.getValue().getId())
        );

        TableColumn<Recurso, String> colNome = new TableColumn<>();
        colNome.setCellValueFactory( e -> new ReadOnlyStringWrapper(e.getValue().getNome()));

        TableColumn<Recurso, String> colDescricao = new TableColumn<>();
        colDescricao.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().getDescricao()));

        TableColumn<Recurso, String> colEmManutencao = new TableColumn<>();
        colEmManutencao.setCellValueFactory(e->new ReadOnlyStringWrapper(String.valueOf(e.getValue().getisEmManutencao())));

        colId.setText("ID");
        colNome.setText("Nome");
        colDescricao.setText("Descrição");
        colEmManutencao.setText("Manutenção");
        tblRecurso.setItems(control.listaProperty());


        Callback<TableColumn<Recurso, Void>, TableCell<Recurso, Void>> fabricanteColunaAcoes =
                ( param ) -> new TableCell<>() {
                    private Button btnApagar = new Button("Apagar");
                    private Button btnEditar = new Button("Editar");

                    {
                        btnApagar.setOnAction( e -> {
                            Recurso r = getTableView().getItems().get(getIndex());
                                    //Adicionar método do controle para apagar()
                            try {
                                control.deletar(r);
                                tblRecurso.refresh();
                                new Alert(Alert.AlertType.INFORMATION,
                                        "Registro apagado com sucesso " )
                                        .showAndWait();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            } catch (ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }


                                }
                        );

                        btnEditar.setOnAction( e -> {
                                    //Adicionar método do controle para editar()
                                    Recurso r = getTableView().getItems().get(getIndex());
                                    control.fromEntity(r); //
                                    new Alert(Alert.AlertType.INFORMATION,
                                            "Registro aberto para edição " )
                                            .showAndWait();
                                }
                        );
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setGraphic( new HBox(btnApagar, btnEditar) );
                        } else {
                            setGraphic( null );
                        }
                    }
                };

        TableColumn<Recurso, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(fabricanteColunaAcoes);

        tblRecurso.getColumns().addAll(colId, colNome, colDescricao, colEmManutencao, colAcoes);


        //Colocar os Bindings
        Bindings.bindBidirectional(txtID.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtDescricao.textProperty(), control.descricaoProperty());
        Bindings.bindBidirectional(txtManutencao.selectedProperty(), control.emManutencaoProperty());

        //Metodo control para inserir e limpar tela
        btnInserir.setOnAction(
                e ->  {
                    try {
                        control.inserir();
                        tblRecurso.refresh();
                        control.limparCampos();
                        new Alert(Alert.AlertType.INFORMATION, "Recurso Salvo com sucesso")
                                .showAndWait();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                }
        );

        btnBuscar.setOnAction(
                e -> {
                    //Metodo control para pesquisar/buscar
                    try {
                        control.buscarPorId();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        btnAtualizar.setOnAction(e -> {
            try {
                control.modificar();
                tblRecurso.refresh();
                control.limparCampos();
                new Alert(Alert.AlertType.INFORMATION, "Registro atualizado com sucesso!")
                        .showAndWait();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });


        HBox panBotoes = new HBox();
        panBotoes.getChildren().addAll(btnInserir, btnBuscar, btnAtualizar);

        VBox panSuperior = new VBox();
        panSuperior.getChildren().addAll(gridCadastro, panBotoes);

        panePrincipal.setTop(panSuperior);
        panePrincipal.setCenter(tblRecurso);

        try {
            control.listar();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return panePrincipal;
    }
}

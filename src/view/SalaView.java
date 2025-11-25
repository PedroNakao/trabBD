package view;

import controller.SalaController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.Recurso;
import model.Sala;
import persisitence.GenericDao;
import persisitence.SalaDao;

import java.sql.SQLException;

public class SalaView implements Tela {
    private TextField txtID = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtCapacidade = new TextField();
    private SalaController control = new SalaController(new SalaDao(new GenericDao()));
    private TableView<Sala> tblSala = new TableView<Sala>();

    @Override
    public Pane render() {
        BorderPane panePrincipal = new  BorderPane();
        GridPane gridCadastro = new GridPane();

        gridCadastro.add(new Label("ID:"), 0,0);
        gridCadastro.add(txtID, 1,0);
        gridCadastro.add(new Label("Nome :"), 0,1);
        gridCadastro.add(txtNome, 1,1);
        gridCadastro.add(new Label("Capacidade :"), 0,2);
        gridCadastro.add(txtCapacidade, 1,2);


        Button btnInserir = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");

        TableColumn<Sala, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(e ->
                new ReadOnlyIntegerWrapper(e.getValue().getId())
        );

        TableColumn<Sala, String> colNome = new TableColumn<>();
        colNome.setCellValueFactory( e -> new ReadOnlyStringWrapper(e.getValue().getNome()));

        TableColumn<Sala, String> colCapacidade = new TableColumn<>();
        colCapacidade.setCellValueFactory(e->new ReadOnlyStringWrapper(String.valueOf(e.getValue().getCapacidade())));

        colId.setText("ID");
        colNome.setText("Nome");
        colCapacidade.setText("Capacidade");
        tblSala.setItems(control.ListaProperty());

        Callback<TableColumn<Sala, Void>, TableCell<Sala, Void>> fabricanteColunaAcoes =
                ( param ) -> new TableCell<>() {
                    private Button btnApagar = new Button("Apagar");
                    private Button btnEditar = new Button("Editar");

                    {
                        btnApagar.setOnAction( e -> {
                            Sala s = getTableView().getItems().get(getIndex());
                                    //Adicionar método do controle para apagar()
                            try {
                                control.deletar(s);
                                tblSala.refresh();
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
                            Sala s = getTableView().getItems().get(getIndex());
                                    //Adicionar método do controle para editar()
                                    control.fromEntity(s);
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

        TableColumn<Sala, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(fabricanteColunaAcoes);

        tblSala.getColumns().addAll(colId,colNome, colCapacidade, colAcoes);


        //Colocar os Bindings
        Bindings.bindBidirectional(txtID.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtCapacidade.textProperty(), control.capacidadeProperty(),new NumberStringConverter());

        btnInserir.setOnAction(
                e ->  {
                    //Metodo gravar do control
                    try {
                        control.inserir();
                        tblSala.refresh();
                        control.limparCampos();
                        new Alert(Alert.AlertType.INFORMATION, "Sala Salva com sucesso")
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
                    try {
                        control.buscarPorId();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    //Metodo control para pesquisar/buscar
                }
        );
        btnAtualizar.setOnAction(e -> {
            try {
                control.modificar();
                tblSala.refresh();
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
        panePrincipal.setCenter(tblSala);

        try {
            control.listar();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return panePrincipal;
    }
}

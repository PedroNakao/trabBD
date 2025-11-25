package view;

import controller.UsuarioController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import model.TipoUsuario;
import model.Usuario;
import persisitence.GenericDao;
import persisitence.TipoUsuarioDao;
import persisitence.UsuarioDao;

import java.sql.SQLException;

public class UsuarioView implements Tela {
    private TextField txtID = new TextField();
    private TextField txtNome = new TextField();
    private ComboBox<TipoUsuario> txtTipoUsuario = new ComboBox<>();
    private TextField txtEmail = new TextField();
    private UsuarioController control = new UsuarioController(new UsuarioDao(new GenericDao()),
            new TipoUsuarioDao(new GenericDao()));
    private TableView<Usuario> tblUsuario = new TableView<Usuario>();

    @Override
    public Pane render() {
        BorderPane panePrincipal = new  BorderPane();
        GridPane gridCadastro = new GridPane();

        gridCadastro.add(new Label("ID:"), 0,0);
        gridCadastro.add(txtID, 1,0);
        gridCadastro.add(new Label("Nome :"), 0,1);
        gridCadastro.add(txtNome, 1,1);
        gridCadastro.add(new Label("Email :"), 0,2);
        gridCadastro.add(txtEmail, 1,2);
        gridCadastro.add(new Label("Tipo Usuário :"), 0,3);
        gridCadastro.add(txtTipoUsuario, 1,3);

        try {
            control.carregarTipos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtTipoUsuario.setItems(control.getListaTipos());

        txtTipoUsuario.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(TipoUsuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });

        txtTipoUsuario.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TipoUsuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });



        Button btnInserir = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");

        TableColumn<Usuario, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(e ->
                new ReadOnlyIntegerWrapper(e.getValue().getId())
        );

        TableColumn<Usuario, String> colNome = new TableColumn<>();
        colNome.setCellValueFactory( e -> new ReadOnlyStringWrapper(e.getValue().getNome()));

        TableColumn<Usuario, String> colEmail = new TableColumn<>();
        colEmail.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().getEmail()));

        TableColumn<Usuario, String> colTipoUsuario = new TableColumn<>();
        colTipoUsuario.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().getTipoUsuario().getNome()));

        colId.setText("ID");
        colNome.setText("Nome");
        colEmail.setText("Capacidade");
        colTipoUsuario.setText("Tipo Usuario");
        tblUsuario.setItems(control.listaProperty());

        Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>> fabricanteColunaAcoes =
                ( param ) -> new TableCell<>() {
                    private Button btnApagar = new Button("Apagar");
                    private Button btnEditar = new Button("Editar");

                    {
                        btnApagar.setOnAction( e -> {
                            Usuario u = getTableView().getItems().get(getIndex());
                            //Adicionar método do controle para apagar()
                            try {
                                control.deletar(u);
                                tblUsuario.refresh();
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
                            Usuario u = getTableView().getItems().get(getIndex());
                            //Adicionar método do controle para editar()
                                    control.fromEntity(u);
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

        TableColumn<Usuario, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(fabricanteColunaAcoes);

        tblUsuario.getColumns().addAll(colId, colNome, colEmail, colTipoUsuario, colAcoes);


        //Colocar os Bindings
        Bindings.bindBidirectional(txtID.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtEmail.textProperty(),control.emailProperty());
        Bindings.bindBidirectional(txtTipoUsuario.valueProperty(), control.tipoUsuarioObjectProperty());


        btnInserir.setOnAction(
                e ->  {
                   //Metodo gravar do control
                    try {
                        control.inserir();
                        tblUsuario.refresh();
                        control.limparCampos();
                        new Alert(Alert.AlertType.INFORMATION, "Usuário Salvo com sucesso")
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
                tblUsuario.refresh();
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
        panBotoes.getChildren().addAll(btnInserir, btnBuscar,btnAtualizar);

        VBox panSuperior = new VBox();
        panSuperior.getChildren().addAll(gridCadastro, panBotoes);

        panePrincipal.setTop(panSuperior);
        panePrincipal.setCenter(tblUsuario);

        return panePrincipal;
    }
}

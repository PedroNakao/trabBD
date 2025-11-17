package view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import model.Usuario;

public class UsuarioView implements Tela {
    private TextField txtID = new TextField();
    private TextField txtNome = new TextField();
    private ComboBox<String> txtTipoUsuario = new ComboBox<>();
    private TextField txtEmail = new TextField();
    //private UsuarioControl control = new UsuarioControl();
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

        txtTipoUsuario.getItems().addAll("Professor", "Aluno", "Visitante");
        Button btnInserir = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");

        TableColumn<Usuario, String> colNome = new TableColumn<>();
        colNome.setCellValueFactory( e -> new ReadOnlyStringWrapper(e.getValue().getNome()));

        TableColumn<Usuario, String> colEmail = new TableColumn<>();
        colEmail.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().getEmail()));

        TableColumn<Usuario, String> colTipoUsuario = new TableColumn<>();
        colTipoUsuario.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().getTipoUsuario()));


        Callback<TableColumn<Usuario, Void>, TableCell<Usuario, Void>> fabricanteColunaAcoes =
                ( param ) -> new TableCell<>() {
                    private Button btnApagar = new Button("Apagar");
                    private Button btnEditar = new Button("Editar");

                    {
                        btnApagar.setOnAction( e -> {
                            //Adicionar método do controle para apagar()
                                    new Alert(Alert.AlertType.INFORMATION,
                                            "Registro apagado com sucesso " )
                                            .showAndWait();
                                }
                        );

                        btnEditar.setOnAction( e -> {
                            //Adicionar método do controle para editar()
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

        tblUsuario.getColumns().addAll(colNome, colEmail, colTipoUsuario, colAcoes);


        //Colocar os Bindings


        btnInserir.setOnAction(
                e ->  {
                   //Metodo gravar do control
                    new Alert(Alert.AlertType.INFORMATION, "Usuário Salvo com sucesso")
                            .showAndWait();
                    tblUsuario.refresh();
                    //Metodo control para limpar tela
                }
        );

        btnBuscar.setOnAction(
                e -> {
                    //Metodo control para pesquisar/buscar
                }
        );


        HBox panBotoes = new HBox();
        panBotoes.getChildren().addAll(btnInserir, btnBuscar);

        VBox panSuperior = new VBox();
        panSuperior.getChildren().addAll(gridCadastro, panBotoes);

        panePrincipal.setTop(panSuperior);
        panePrincipal.setCenter(tblUsuario);

        return panePrincipal;
    }
}

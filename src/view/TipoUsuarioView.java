package view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import model.TipoUsuario;
import model.Usuario;

public class TipoUsuarioView implements Tela{
    private TextField txtID = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtHorasPermitidas = new TextField();

    private TableView<TipoUsuario> tblTipoUsuario = new TableView<TipoUsuario>();

    @Override
    public Pane render() {

        BorderPane panePrincipal = new  BorderPane();
        GridPane gridCadastro = new GridPane();

        gridCadastro.add(new Label("ID:"), 0,0);
        gridCadastro.add(txtID, 1,0);
        gridCadastro.add(new Label("Nome :"), 0,1);
        gridCadastro.add(txtNome, 1,1);
        gridCadastro.add(new Label("Horas Permitidas :"), 0,2);
        gridCadastro.add(txtHorasPermitidas, 1,2);

        Button btnInserir = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");

        TableColumn<TipoUsuario, String> colNome = new TableColumn<>();
        colNome.setCellValueFactory( e -> new ReadOnlyStringWrapper(e.getValue().getNome()));

        TableColumn<TipoUsuario, String> colHorasPermitidas = new TableColumn<>();
        colHorasPermitidas.setCellValueFactory(e->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getHorasPermitidas()))
        );

        Callback<TableColumn<TipoUsuario, Void>, TableCell<TipoUsuario, Void>> fabricanteColunaAcoes =
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

        TableColumn<TipoUsuario, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(fabricanteColunaAcoes);

        tblTipoUsuario.getColumns().addAll(colNome, colHorasPermitidas, colAcoes);


        //Colocar os Bindings


        btnInserir.setOnAction(
                e ->  {
                    //Metodo gravar do control
                    new Alert(Alert.AlertType.INFORMATION, "Tipo Usuário Salvo com sucesso")
                            .showAndWait();
                    tblTipoUsuario.refresh();
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
        panePrincipal.setCenter(tblTipoUsuario);

        return panePrincipal;

    }
}

package view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import model.Sala;

public class SalaView implements Tela {
    private TextField txtID = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtCapacidade = new TextField();
    //private SalaControl control = new SalaControl();
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

        TableColumn<Sala, String> colNome = new TableColumn<>();
        colNome.setCellValueFactory( e -> new ReadOnlyStringWrapper(e.getValue().getNome()));

        TableColumn<Sala, String> colCapacidade = new TableColumn<>();
        colCapacidade.setCellValueFactory(e->new ReadOnlyStringWrapper(String.valueOf(e.getValue().getCapacidade())));


        Callback<TableColumn<Sala, Void>, TableCell<Sala, Void>> fabricanteColunaAcoes =
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

        TableColumn<Sala, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(fabricanteColunaAcoes);

        tblSala.getColumns().addAll(colNome, colCapacidade, colAcoes);


        //Colocar os Bindings


        btnInserir.setOnAction(
                e ->  {
                    //Metodo gravar do control
                    new Alert(Alert.AlertType.INFORMATION, "Sala Salva com sucesso")
                            .showAndWait();
                    tblSala.refresh();
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
        panePrincipal.setCenter(tblSala);

        return panePrincipal;
    }
}

package view;

import controller.TipoUsuarioController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import model.TipoUsuario;
import persisitence.GenericDao;
import persisitence.TipoUsuarioDao;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TipoUsuarioView implements Tela{
    private TextField txtID = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtHorasPermitidas = new TextField();
    private TipoUsuarioController control = new TipoUsuarioController(new TipoUsuarioDao(new GenericDao()));

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
        Button btnAtualizar = new Button("Atualizar");

        TableColumn<TipoUsuario, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(e ->
                new ReadOnlyIntegerWrapper(e.getValue().getId())
        );

        TableColumn<TipoUsuario, String> colNome = new TableColumn<>();
        colNome.setCellValueFactory( e -> new ReadOnlyStringWrapper(e.getValue().getNome()));

        TableColumn<TipoUsuario, String> colHorasPermitidas = new TableColumn<>();
        colHorasPermitidas.setCellValueFactory(e->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getHorasPermitidas()))
        );
        colId.setText("ID");
        colNome.setText("Nome");
        colHorasPermitidas.setText("Horas Permitidas");
        tblTipoUsuario.setItems(control.getLista());

        Callback<TableColumn<TipoUsuario, Void>, TableCell<TipoUsuario, Void>> fabricanteColunaAcoes =
                ( param ) -> new TableCell<>() {
                    private Button btnApagar = new Button("Apagar");
                    private Button btnEditar = new Button("Editar");

                    {
                        btnApagar.setOnAction( e -> {
                            TipoUsuario t = getTableView().getItems().get(getIndex());
                                    //Adicionar método do controle para apagar()
                            try {
                                control.deletar(t);
                                tblTipoUsuario.refresh();
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
                            TipoUsuario t = getTableView().getItems().get(getIndex());
                                    //Adicionar método do controle para editar()
                                    control.fromEntity(t);
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

        tblTipoUsuario.getColumns().addAll(colId,colNome, colHorasPermitidas, colAcoes);


        //Colocar os Bindings
        Bindings.bindBidirectional(txtID.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtHorasPermitidas.textProperty(),
                control.horasPermitidasProperty(),
            new StringConverter<LocalTime>() {
                @Override
                public String toString(LocalTime time) {
                    if (time == null) return "";
                    return time.format(DateTimeFormatter.ofPattern("HH:mm"));
                }

                @Override
                public LocalTime fromString(String text) {
                    // Campo vazio → LocalTime = null
                    if (text == null || text.isEmpty()) {
                        return null;
                    }

                    // Se o usuário ainda está digitando (ex: "6", "12", "12:")
                    // não tenta converter ainda
                    if (text.length() < 5) {
                        return null;
                    }

                    // Só converte quando tiver o formato completo (HH:mm)
                    try {
                        return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm"));
                    } catch (Exception e) {
                        return null;
                    }
                }
            });

        btnInserir.setOnAction(
                e ->  {
                    //Metodo gravar do control
                    try {
                        control.inserir();
                        tblTipoUsuario.refresh();
                        control.limparCampos();
                        new Alert(Alert.AlertType.INFORMATION, "Tipo Usuário Salvo com sucesso")
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
                tblTipoUsuario.refresh();
                control.limparCampos();
                new Alert(Alert.AlertType.INFORMATION, "Registro atualizado com sucesso!");
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
        panePrincipal.setCenter(tblTipoUsuario);

        try {
            control.listar();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return panePrincipal;

    }
}

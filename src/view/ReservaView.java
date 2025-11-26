package view;

import controller.ReservaController;
import controller.UsuarioController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import model.*;
import persisitence.*;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ReservaView implements Tela{
    private TextField txtIDReserva = new TextField();

    private ComboBox<Sala> txtIDSala = new ComboBox<>();
    private ComboBox<Usuario> txtIDUsuario = new ComboBox<>();
    private ComboBox<Recurso> txtIDRecurso = new ComboBox<>();
    private TextField txtIDTipo = new TextField();
    private DatePicker txtDataReserva = new DatePicker();
    private TextField txthorarioInicio = new TextField();
    private TextField txthorarioFim = new TextField();
    private ReservaController control = new ReservaController(new ReservaDao(new GenericDao()),
            new SalaDao(new GenericDao()), new UsuarioDao(new GenericDao()), new RecursoDao(new GenericDao()));

    private TableView<Reserva> tblReserva = new TableView<Reserva>();

    public void refreshData() {
        try {
            // Recarrega as listas de Sala, Usuário e Recurso do banco de dados
            control.carregarTipos();
            // A lista observável (ObservableList) associada à ComboBox é atualizada aqui.
        } catch (Exception e) {
            // Se houver qualquer erro de banco de dados durante a recarga, ele será capturado aqui.
            e.printStackTrace();
        }
    }

    @Override
    public Pane render() {
        BorderPane panePrincipal = new  BorderPane();
        GridPane gridCadastro = new GridPane();

        gridCadastro.add(new Label("ID Reserva:"), 0,0);
        gridCadastro.add(txtIDReserva, 1,0);
        gridCadastro.add(new Label("Nome Sala :"), 0,1);
        gridCadastro.add(txtIDSala, 1,1);
        gridCadastro.add(new Label("Nome Usuário :"), 0,2);
        gridCadastro.add(txtIDUsuario, 1,2);
        gridCadastro.add(new Label("Nome Tipo :"), 2,2);
        gridCadastro.add(txtIDTipo, 3,2);
        gridCadastro.add(new Label("Nome Recurso :"), 0,3);
        gridCadastro.add(txtIDRecurso, 1,3);
        gridCadastro.add(new Label("Data Reserva :"), 0,4);
        gridCadastro.add(txtDataReserva, 1,4);
        gridCadastro.add(new Label("Horário Início :"), 0,5);
        gridCadastro.add(txthorarioInicio, 1,5);
        gridCadastro.add(new Label("Horário Fim :"), 0,6);
        gridCadastro.add(txthorarioFim, 1,6);

        txtIDTipo.setEditable(false);

        try {
            control.carregarTipos();
            control.listar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtIDSala.setItems(control.getListaSalas());
        txtIDUsuario.setItems(control.getListaUsuarios());
        txtIDRecurso.setItems(control.getListaRecursos());


        txtIDSala.setCellFactory(cb -> new ListCell<Sala>() {
            @Override
            protected void updateItem(Sala item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });
        txtIDSala.setButtonCell(new ListCell<Sala>() {
            @Override
            protected void updateItem(Sala item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });

        // Configurando ComboBox para mostrar o nome do Usuário
        txtIDUsuario.setCellFactory(cb -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });
        txtIDUsuario.setButtonCell(new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });

        // Configurando ComboBox para mostrar o nome do Recurso
        txtIDRecurso.setCellFactory(cb -> new ListCell<Recurso>() {
            @Override
            protected void updateItem(Recurso item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });
        txtIDRecurso.setButtonCell(new ListCell<Recurso>() {
            @Override
            protected void updateItem(Recurso item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });


        Button btnInserir = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");
        Button btnAtualizar = new Button("Atualizar");

        TableColumn<Reserva, String> colIDReserva = new TableColumn<>();
        colIDReserva.setCellValueFactory(e ->
                        new ReadOnlyStringWrapper(String.valueOf(e.getValue().getReservaId())));

        TableColumn<Reserva, String> colIDSala= new TableColumn<>();
        colIDSala.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getSalaId().getNome())));

        TableColumn<Reserva, String> colIDUsuario = new TableColumn<>();
        colIDUsuario.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getUsuarioId().getNome())));

        TableColumn<Reserva, String> colIDTipo = new TableColumn<>();
        colIDTipo.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getUsuarioId().getTipoUsuario().getNome())));

        TableColumn<Reserva, String> colIDRecurso = new TableColumn<>();
        colIDRecurso.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getRecursoId().getNome())));

        TableColumn<Reserva, String> colDataReserva = new TableColumn<>();
        colDataReserva.setCellValueFactory(e->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getDataReserva())));

        TableColumn<Reserva, String> colhorarioInicio = new TableColumn<>();
        colhorarioInicio.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getHorarioInicio())));

        TableColumn<Reserva, String> colhorarioFim = new TableColumn<>();
        colhorarioFim.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getHorarioFim())));

        colIDReserva.setText("id Reserva");
        colIDSala.setText("Sala");
        colIDUsuario.setText("Usuario");
        colIDTipo.setText("Tipo Usuario");
        colIDRecurso.setText("Recurso");
        colDataReserva.setText("Data Reserva");
        colhorarioInicio.setText("Hora Inicio");

        tblReserva.setItems(control.listaProperty());


        Callback<TableColumn<Reserva, Void>, TableCell<Reserva, Void>> fabricanteColunaAcoes =
                ( param ) -> new TableCell<>() {
                    private Button btnApagar = new Button("Apagar");
                    private Button btnEditar = new Button("Editar");

                    {
                        btnApagar.setOnAction( e -> {
                            Reserva r = getTableView().getItems().get(getIndex());

                                    //Adicionar método do controle para apagar()
                            try {
                                control.deletar(r);
                                tblReserva.refresh();
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
                            Reserva r = getTableView().getItems().get(getIndex());
                                    control.fromEntity(r);
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

        TableColumn<Reserva, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(fabricanteColunaAcoes);
        tblReserva.getColumns().addAll
                (colIDReserva, colIDSala, colIDUsuario, colIDRecurso, colDataReserva, colhorarioInicio, colhorarioFim,
                        colAcoes);


        //Colocar os Bindings
        txtIDTipo.textProperty().bind(
                Bindings.selectString(control.usuarioProperty(), "tipoUsuario", "nome")
        );
        Bindings.bindBidirectional(txtIDReserva.textProperty(), control.idProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(txtIDSala.valueProperty(), control.salaProperty());
        Bindings.bindBidirectional(txtIDUsuario.valueProperty(),control.usuarioProperty());
        Bindings.bindBidirectional(txtIDRecurso.valueProperty(),control.recursoProperty());
        Bindings.bindBidirectional(txtDataReserva.valueProperty(), control.dataReservaProperty());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Bindings.bindBidirectional(
                txthorarioInicio.textProperty(),
                control.horaInicioProperty(),
                new LocalTimeStringConverter(timeFormatter, timeFormatter)
        );

        Bindings.bindBidirectional(
                txthorarioFim.textProperty(),
                control.horaFimProperty(),
                new LocalTimeStringConverter(timeFormatter, timeFormatter)
        );


        btnInserir.setOnAction(
                e ->  {
                    //Metodo gravar do control
                    try {
                        control.inserir();
                        tblReserva.refresh();
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
                tblReserva.refresh();
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
        panBotoes.getChildren().addAll(btnInserir, btnBuscar);

        VBox panSuperior = new VBox();
        panSuperior.getChildren().addAll(gridCadastro, panBotoes);

        panePrincipal.setTop(panSuperior);
        panePrincipal.setCenter(tblReserva);

        return panePrincipal;
    }
}

package view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import model.Recurso;
import model.Reserva;
import model.Sala;
import model.Usuario;

public class ReservaView implements Tela{
    private TextField txtIDReserva = new TextField();

    private ComboBox<Integer> txtIDSala = new ComboBox<>();
    private ComboBox<Integer> txtIDUsuario = new ComboBox<>();
    private ComboBox<Integer> txtIDRecurso = new ComboBox<>();
    private TextField txtDataReserva = new TextField();
    private TextField txthorarioInicio = new TextField();
    private TextField txthorarioFim = new TextField();

    private TableView<Reserva> tblReserva = new TableView<Reserva>();

    @Override
    public Pane render() {
        BorderPane panePrincipal = new  BorderPane();
        GridPane gridCadastro = new GridPane();

        gridCadastro.add(new Label("ID Reserva:"), 0,0);
        gridCadastro.add(txtIDReserva, 1,0);
        gridCadastro.add(new Label("ID Sala :"), 0,1);
        gridCadastro.add(txtIDSala, 1,1);
        gridCadastro.add(new Label("ID Usuário :"), 0,2);
        gridCadastro.add(txtIDUsuario, 1,2);
        gridCadastro.add(new Label("ID Recurso :"), 0,3);
        gridCadastro.add(txtIDRecurso, 1,3);
        gridCadastro.add(new Label("Data Reserva :"), 0,4);
        gridCadastro.add(txtDataReserva, 1,4);
        gridCadastro.add(new Label("Horário Início :"), 0,5);
        gridCadastro.add(txthorarioInicio, 1,5);
        gridCadastro.add(new Label("Horário Fim :"), 0,6);
        gridCadastro.add(txthorarioFim, 1,6);


        Button btnInserir = new Button("Inserir");
        Button btnBuscar = new Button("Buscar");

        TableColumn<Reserva, String> colIDReserva = new TableColumn<>();
        colIDReserva.setCellValueFactory(e ->
                        new ReadOnlyStringWrapper(String.valueOf(e.getValue().getReservaId())));

        TableColumn<Reserva, String> colIDSala= new TableColumn<>();
        colIDSala.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getSalaId())));

        TableColumn<Reserva, String> colIDUsuario = new TableColumn<>();
        colIDUsuario.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getUsuarioId())));

        TableColumn<Reserva, String> colIDRecurso = new TableColumn<>();
        colIDReserva.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getRecursoId())));

        TableColumn<Reserva, String> colDataReserva = new TableColumn<>();
        colDataReserva.setCellValueFactory(e->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getDataReserva())));

        TableColumn<Reserva, String> colhorarioInicio = new TableColumn<>();
        colhorarioInicio.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getHorarioInicio())));

        TableColumn<Reserva, String> colhorarioFim = new TableColumn<>();
        colhorarioFim.setCellValueFactory(e ->
                new ReadOnlyStringWrapper(String.valueOf(e.getValue().getHorarioFim())));


        Callback<TableColumn<Reserva, Void>, TableCell<Reserva, Void>> fabricanteColunaAcoes =
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

        TableColumn<Reserva, Void> colAcoes = new TableColumn<>("Ações");
        colAcoes.setCellFactory(fabricanteColunaAcoes);
        tblReserva.getColumns().addAll
                (colIDReserva, colIDSala, colIDUsuario, colIDRecurso, colDataReserva, colhorarioInicio, colhorarioFim, colAcoes);


        //Colocar os Bindings


        btnInserir.setOnAction(
                e ->  {
                    //Metodo gravar do control
                    new Alert(Alert.AlertType.INFORMATION, "Usuário Salvo com sucesso")
                            .showAndWait();
                    tblReserva.refresh();
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
        panePrincipal.setCenter(tblReserva);

        return panePrincipal;
    }
}

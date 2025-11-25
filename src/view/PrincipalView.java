package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class  PrincipalView extends Application {
    private Tela recursoView = new RecursoView();
    private Tela salaView = new SalaView();
    private Tela usuarioView = new UsuarioView();
    private Tela tipoUsuarioView = new TipoUsuarioView();
    private Tela reservaView = new ReservaView();

    @Override
    public void start(Stage stage) throws Exception {

        Pane telarecursoView =  recursoView.render();
        Pane telasalaView = salaView.render();
        Pane telausuarioView = usuarioView.render();
        Pane telatipoUsuarioView = tipoUsuarioView.render();
        Pane telareservaPaneView = reservaView.render();

        BorderPane panPrincipal = new BorderPane();

        Scene scn = new Scene(panPrincipal, 600, 400);

        MenuBar menuBar = new MenuBar();

        Menu menuReserva = new Menu("Reserva");
        Menu menuTela = new Menu("Tela");

        menuBar.getMenus().addAll(
                menuTela, menuReserva);

        MenuItem itemRecurso = new MenuItem("Recurso");
        MenuItem itemSala = new MenuItem("Sala");
        MenuItem itemUsuario = new MenuItem("Usuario");
        MenuItem itemTipoUsuario = new MenuItem("Tipo Usuario");
        MenuItem itemReserva = new MenuItem("Reserva");

        menuTela.getItems().addAll( itemRecurso, itemSala, itemUsuario,  itemTipoUsuario);

        menuReserva.getItems().addAll(itemReserva);

        itemRecurso.setOnAction( e ->
                panPrincipal.setCenter( telarecursoView )
        );
        itemSala.setOnAction( e ->
                panPrincipal.setCenter( telasalaView)
        );
        itemUsuario.setOnAction(e->
                panPrincipal.setCenter( telausuarioView )
        );

        itemTipoUsuario.setOnAction(e->
                panPrincipal.setCenter( telatipoUsuarioView)
        );

        itemReserva.setOnAction(e->
                panPrincipal.setCenter( telareservaPaneView)
        );

        panPrincipal.setTop( menuBar );

        stage.setScene(scn);
        stage.setTitle("Sistema de gerenciar reserva de salas");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(PrincipalView.class, args);
    }

}

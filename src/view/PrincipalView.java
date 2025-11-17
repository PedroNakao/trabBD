package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrincipalView extends Application {
    private Tela recursoView = new RecursoView();
    private Tela salaView = new SalaView();
    private Tela usuarioView = new UsuarioView();

    @Override
    public void start(Stage stage) throws Exception {

        Pane telarecursoView =  recursoView.render();
        Pane telasalaView = salaView.render();
        Pane telausuarioView = usuarioView.render();

        BorderPane panPrincipal = new BorderPane();

        Scene scn = new Scene(panPrincipal, 600, 400);

        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuTela = new Menu("Tela");
        Menu menuHelp = new Menu("Help");

        menuBar.getMenus().addAll( menuFile, menuEdit,
                menuTela, menuHelp);

        MenuItem itemRecurso = new MenuItem("Recurso");
        MenuItem itemSala = new MenuItem("Sala");
        MenuItem itemUsuario = new MenuItem("Usuario");
        menuTela.getItems().addAll( itemRecurso, itemSala, itemUsuario );

        itemRecurso.setOnAction( e ->
                panPrincipal.setCenter( telarecursoView )
        );
        itemSala.setOnAction( e ->
                panPrincipal.setCenter( telasalaView)
        );
        itemUsuario.setOnAction(e->
                panPrincipal.setCenter( telausuarioView )
        );

        panPrincipal.setTop( menuBar );

        stage.setScene(scn);
        stage.setTitle("Sistema de Contatos e Redes Sociais");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(PrincipalView.class, args);
    }

}

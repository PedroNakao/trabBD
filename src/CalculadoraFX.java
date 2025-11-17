import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CalculadoraFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane panPrincipal = new BorderPane();
        Scene scn = new Scene(panPrincipal, 600, 400);

        FlowPane panTela = new FlowPane();
        TextField txtTela = new TextField();
        Button btnCE = new Button("CE");
        panTela.getChildren().addAll(txtTela,btnCE);
        panPrincipal.setTop( panTela );

        GridPane panButton = new GridPane();
        Button btn1 = new Button("1");
        Button btn2 = new Button("2");
        Button btn3 = new Button("3");
        Button btnMais = new Button("+");
        Button btn4 = new Button("4");
        Button btn5 = new Button("5");
        Button btn6 = new Button("6");
        Button btnMenos = new Button("-");
        Button btn7 = new Button("7");
        Button btn8 = new Button("8");
        Button btn9 = new Button("9");
        Button btnVezes = new Button("*");
        Button btnVirgula = new Button(",");
        Button btn0 = new Button("0");
        Button btnIgual = new Button("=");
        Button btnDivisao = new Button("/");
        panButton.add( btn1, 0, 0 );
        panButton.add( btn2, 1, 0 );
        panButton.add( btn3, 2, 0 );
        panButton.add( btnMais, 3, 0 );
        panButton.add( btn4, 0, 1 );
        panButton.add( btn5, 1, 1 );
        panButton.add( btn6, 2, 1 );
        panButton.add( btnMenos, 3, 1 );
        panButton.add( btn7, 0, 2 );
        panButton.add( btn8, 1, 2 );
        panButton.add( btn9, 2, 2 );
        panButton.add( btnVezes, 3, 2 );
        panButton.add( btnVirgula, 0, 3 );
        panButton.add( btn0, 1, 3 );
        panButton.add( btnIgual, 2, 3 );
        panButton.add( btnDivisao, 3, 3 );
        panPrincipal.setCenter( panButton );

        stage.setScene(scn);
        stage.setTitle("Calculadora");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(CalculadoraFX.class, args);
    }

}


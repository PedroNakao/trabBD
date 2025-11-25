package persisitence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericDao {
    private Connection con;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String hostName = "DESKTOP-30ME809";
        String dbName = "GerenciadorReservas";
        String user = "fernando";
        String senha = "flecha11";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//Driver
        String url = "jdbc:sqlserver://" + hostName + ":1433;"
                + "databaseName=" + dbName + ";"
                + "encrypt=false;"    // importante para evitar erro de SSL
                + "trustServerCertificate=true;";
        con = DriverManager.getConnection(url, user, senha
        );
        return con;
    }
}

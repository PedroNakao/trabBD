package persisitence;
import net.sourceforge.jtds.jdbc.Driver;
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
        Class.forName("net.sourceforge.jtds.jdbc.Driver");//Driver
        con = DriverManager.getConnection(String.format(
                "jdbc:jtds:sqlserver://%s:1433:databaseName=%s:user=%s:password=%s", hostName, dbName, user, senha
        ));
        return con;
    }
}

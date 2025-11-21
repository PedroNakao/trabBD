package persisitence;

import model.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaDao implements ICrud<Sala>{
    private GenericDao gDao;

    public SalaDao (GenericDao gDao){
        this.gDao = gDao;
    }

    @Override
    public void inserir(Sala sala) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "INSERT INTO Sala VALUES (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,sala.getId());
        ps.setString(2,sala.getNome());
        ps.setInt(3,sala.getCapacidade());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public void excluir(Sala sala) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "DELETE Sala WHERE idSala = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,sala.getId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public void atualizar(Sala sala) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "UPDATE Sala SET nome = ?, capacidade = ? WHERE idSala = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,sala.getId());
        ps.setString(2,sala.getNome());
        ps.setInt(3,sala.getCapacidade());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public Sala consultar(Sala sala) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "SELECT idSala, nome, capacidade FROM Sala WHERE idSala = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,sala.getId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            sala.setId(rs.getInt("idRecurso"));
            sala.setNome(rs.getString("nome"));
            sala.setCapacidade(rs.getInt("capacidade"));
        }
        rs.close();
        ps.close();
        con.close();
        return sala;
    }

    @Override
    public List<Sala> listar(Sala sala) throws SQLException, ClassNotFoundException {
        List<Sala> salas = new ArrayList<>();
        Connection con = gDao.getConnection();
        String sql = "SELECT idSala, nome, capacidade FROM Sala WHERE idSala = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,sala.getId());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Sala s = new Sala();
            s.setId(rs.getInt("idRecurso"));
            s.setNome(rs.getString("nome"));
            s.setCapacidade(rs.getInt("capacidade"));
            salas.add(s);
        }
        rs.close();
        ps.close();
        con.close();
        return salas;
    }
}

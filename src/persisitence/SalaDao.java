package persisitence;

import model.Sala;

import java.sql.*;
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
        String sql = "INSERT INTO Sala (nome, capacidade) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,sala.getNome());
        ps.setInt(2,sala.getCapacidade());
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int idGerado = rs.getInt(1);
            sala.setId(idGerado);
        }
        rs.close();
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
        ps.setString(1,sala.getNome());
        ps.setInt(2,sala.getCapacidade());
        ps.setInt(3,sala.getId());
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
        String sql = "SELECT idSala, nome, capacidade FROM Sala";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Sala s = new Sala();
            s.setId(rs.getInt("idSala"));
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

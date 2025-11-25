package persisitence;

import model.Recurso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecursoDao implements ICrud<Recurso>{

    private GenericDao gDao;

    public RecursoDao (GenericDao gDao){
        this.gDao = gDao;
    }

    @Override
    public void inserir(Recurso recurso) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "INSERT INTO Recurso (nome, descricao, manutencao) VALUES (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,recurso.getNome());
        ps.setString(2,recurso.getDescricao());
        ps.setBoolean(3,recurso.getisEmManutencao());
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int idGerado = rs.getInt(1);
            recurso.setId(idGerado);

            rs.close();
            ps.close();
            con.close();
        }
    }

    @Override
    public void excluir(Recurso recurso) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "DELETE Recurso WHERE idRecurso = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,recurso.getId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public void atualizar(Recurso recurso) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "UPDATE Recurso SET nome = ?, descricao = ?, manutencao = ? WHERE idRecurso = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,recurso.getNome());
        ps.setString(2,recurso.getDescricao());
        ps.setBoolean(3,recurso.getisEmManutencao());
        ps.setInt(4,recurso.getId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public Recurso consultar(Recurso recurso) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "SELECT idRecurso, nome, descricao, manutencao FROM Recurso WHERE idRecurso = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,recurso.getId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            recurso.setId(rs.getInt("idRecurso"));
            recurso.setNome(rs.getString("nome"));
            recurso.setDescricao(rs.getString("descricao"));
            recurso.setEmManutencao(rs.getBoolean("manutencao"));
        }
        rs.close();
        ps.close();
        con.close();
        return recurso;
    }

    @Override
    public List<Recurso> listar(Recurso recurso) throws SQLException, ClassNotFoundException {
        List<Recurso> recursos = new ArrayList<>();
        Connection con = gDao.getConnection();
        String sql = "SELECT idRecurso, nome, descricao, manutencao FROM Recurso ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Recurso r = new Recurso();
            r.setId(rs.getInt("idRecurso"));
            r.setNome(rs.getString("nome"));
            r.setDescricao(rs.getString("descricao"));
            r.setEmManutencao(rs.getBoolean("manutencao"));
            recursos.add(r);
        }
        rs.close();
        ps.close();
        con.close();
        return recursos;
    }
}

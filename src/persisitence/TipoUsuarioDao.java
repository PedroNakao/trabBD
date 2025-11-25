package persisitence;

import model.TipoUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoUsuarioDao implements ICrud<TipoUsuario>{
    private GenericDao gDao;

    public TipoUsuarioDao (GenericDao gDao){
        this.gDao = gDao;
    }
    @Override
    public void inserir(TipoUsuario tipoUsuario) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "INSERT INTO TipoUsuario (nome, horasPermitidas) VALUES (?,?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,tipoUsuario.getNome());
        ps.setTime(2,java.sql.Time.valueOf(tipoUsuario.getHorasPermitidas()));
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int idGerado = rs.getInt(1);
            tipoUsuario.setId(idGerado);
        }
        rs.close();
        ps.close();
        con.close();
    }

    @Override
    public void excluir(TipoUsuario tipoUsuario) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "DELETE TipoUsuario WHERE idTipo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,tipoUsuario.getId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public void atualizar(TipoUsuario tipoUsuario) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "UPDATE TipoUsuario SET nome= ?, horasPermitidas = ? WHERE idTipo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,tipoUsuario.getNome());
        ps.setTime(2,java.sql.Time.valueOf(tipoUsuario.getHorasPermitidas()));
        ps.setInt(3,tipoUsuario.getId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public TipoUsuario consultar(TipoUsuario tipoUsuario) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "SELECT idTipo, nome, horasPermitidas FROM TipoUsuario WHERE idTipo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,tipoUsuario.getId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            tipoUsuario.setId(rs.getInt("idTipo"));
            tipoUsuario.setNome(rs.getString("nome"));
            tipoUsuario.setHorasPermitidas(rs.getTime("horasPermitidas").toLocalTime());
        }
        return tipoUsuario;
    }

    @Override
    public List<TipoUsuario> listar(TipoUsuario tipoUsuario) throws SQLException, ClassNotFoundException {
        List<TipoUsuario> tipoUsuarios = new ArrayList<>();
        Connection con = gDao.getConnection();
        String sql = "SELECT idTipo, nome, horasPermitidas FROM TipoUsuario";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            TipoUsuario t = new TipoUsuario();
            t.setId(rs.getInt("idTipo"));
            t.setNome(rs.getString("nome"));
            t.setHorasPermitidas(rs.getTime("horasPermitidas").toLocalTime());
            tipoUsuarios.add(t);
        }
        return tipoUsuarios;
    }
}

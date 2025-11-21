package persisitence;

import model.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO TipoUsuario VALUES (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,tipoUsuario.getId());
        ps.setString(2,tipoUsuario.getNome());
        ps.setTime(3,java.sql.Time.valueOf(tipoUsuario.getHorasPermitidas()));
        ps.execute();
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
        String sql = "UPDATE TipoUsuario SET nome= ?, horasPermitida= ? WHERE idTipo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,tipoUsuario.getId());
        ps.setString(2,tipoUsuario.getNome());
        ps.setTime(3,java.sql.Time.valueOf(tipoUsuario.getHorasPermitidas()));
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
        String sql = "SELECT idTipo, nome, horasPermitidas FROM TipoUsuario WHERE idTipo = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,tipoUsuario.getId());
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

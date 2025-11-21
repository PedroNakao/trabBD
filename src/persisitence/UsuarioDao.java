package persisitence;

import model.TipoUsuario;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements ICrud<Usuario>{

    private GenericDao gDao;

    public UsuarioDao (GenericDao gDao){
        this.gDao = gDao;
    }

    @Override
    public void inserir(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "INSERT INTO Usuario VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,usuario.getId());
        ps.setString(2,usuario.getNome());
        ps.setString(4,usuario.getEmail());
        ps.setInt(1,usuario.getTipoUsuario().getId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public void excluir(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "DELETE Usuario WHERE idUsuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,usuario.getId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public void atualizar(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "UPDATE Usuario SET nome = ?, email = ?, tipoUsuarioId =?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,usuario.getId());
        ps.setString(2,usuario.getNome());
        ps.setString(4,usuario.getEmail());
        ps.setInt(1,usuario.getTipoUsuario().getId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public Usuario consultar(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT u.idUsuario, u.nome AS nomeUsuario, u.email AS emailUsuario, t.nome AS nomeTipo ");
        sql.append("FROM Usuario u,TipoUsuario t ");
        sql.append("WHERE t.idTipo = u.tipoUsuarioId ");
        sql.append("AND u.idUsuario = ?");
        PreparedStatement ps = con.prepareStatement(sql.toString());
        ps.setInt(1,usuario.getId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            TipoUsuario t = new TipoUsuario();
            t.setNome(rs.getString("nomeTipo"));
            usuario.setId(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nomeUsuario"));
            usuario.setEmail(rs.getString("emailUsuario"));
            usuario.setTipoUsuario(t);
        }
        rs.close();
        ps.close();
        con.close();
        return usuario;
    }

    @Override
    public List<Usuario> listar(Usuario usuario) throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = gDao.getConnection();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT u.idUsuario, u.nome AS nomeUsuario, u.email AS emailUsuario, t.nome AS nomeTipo ");
        sql.append("FROM Usuario u,TipoUsuario t ");
        sql.append("WHERE t.idTipo = u.tipoUsuarioId ");
        PreparedStatement ps = con.prepareStatement(sql.toString());
        ps.setInt(1,usuario.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Usuario u = new Usuario();
            TipoUsuario t = new TipoUsuario();
            t.setNome(rs.getString("nomeTipo"));
            u.setId(rs.getInt("idUsuario"));
            u.setNome(rs.getString("nomeUsuario"));
            u.setEmail(rs.getString("emailUsuario"));
            u.setTipoUsuario(t);
            usuarios.add(u);
        }
        rs.close();
        ps.close();
        con.close();
        return usuarios;
    }
}

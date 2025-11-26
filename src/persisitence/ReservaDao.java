package persisitence;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDao implements ICrud<Reserva>{

    private GenericDao gDao;

    public ReservaDao (GenericDao gDao){
        this.gDao = gDao;
    }

    @Override
    public void inserir(Reserva reserva) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "INSERT INTO Reserva (salaId, UsuarioId, RecursoId, dataReserva," +
                " horaInicio, horaFim )VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,reserva.getSalaId().getId());
        ps.setInt(2,reserva.getUsuarioId().getId());
        ps.setInt(3,reserva.getRecursoId().getId());
        ps.setDate(4, Date.valueOf(reserva.getDataReserva()));
        ps.setTime(5, Time.valueOf(reserva.getHorarioInicio()));
        ps.setTime(6,Time.valueOf(reserva.getHorarioFim()));
        ps.execute();
        ps.close();
        con.close();

    }

    @Override
    public void excluir(Reserva reserva) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        String sql = "DELETE Reserva WHERE idReserva = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,reserva.getReservaId());
        ps.execute();
        ps.close();
        con.close();
    }

    @Override
    public void atualizar(Reserva reserva) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE Reserva SET salaId = ?, UsuarioId = ?," );
        sql.append("RecursoId = ?, ");
        sql.append("dataReserva = ?, horaInicio = ?, horaFim = ?");
        sql.append("WHERE idReserva = ?");
        PreparedStatement ps = con.prepareStatement(sql.toString());
        ps.setInt(1,reserva.getSalaId().getId());
        ps.setInt(2,reserva.getUsuarioId().getId());
        ps.setInt(3,reserva.getRecursoId().getId());
        ps.setDate(4, Date.valueOf(reserva.getDataReserva()));
        ps.setTime(5, Time.valueOf(reserva.getHorarioInicio()));
        ps.setTime(6,Time.valueOf(reserva.getHorarioFim()));
        ps.setInt(7,reserva.getReservaId());
        ps.execute();
        ps.close();
        con.close();

    }

    @Override
    public Reserva consultar(Reserva reserva) throws SQLException, ClassNotFoundException {
        Connection con = gDao.getConnection();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT r.idReserva, r.dataReserva, r.horaInicio, r.horaFim, ");
        sql.append("s.nome AS nomeSala, ");
        sql.append("u.nome AS nomeUsuario, ");
        sql.append("re.nome AS nomeRecurso, ");
        sql.append("t.nome AS nomeTipo ");
        sql.append("FROM Reserva r, Sala s, Usuario u, Recurso re, TipoUsuario t "); // Inclui TipoUsuario t
        sql.append("WHERE r.salaId = s.idSala ");
        sql.append("AND r.UsuarioId = u.idUsuario ");
        sql.append("AND u.tipoUsuarioId = t.idTipo "); // NOVO JOIN: Usuário -> TipoUsuario
        sql.append("AND r.RecursoId = re.idRecurso ");
        sql.append("AND r.idReserva = ?");
        PreparedStatement ps = con.prepareStatement(sql.toString());
        ps.setInt(1,reserva.getReservaId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            Sala s = new Sala();
            Usuario u = new Usuario();
            TipoUsuario t = new TipoUsuario();
            Recurso re = new Recurso();
            s.setNome(rs.getString("nomeSala"));
            u.setNome(rs.getString("nomeUsuario"));
            t.setNome(rs.getString("nomeTipo"));
            re.setNome(rs.getString("nomeRecurso"));
            u.setTipoUsuario(t);
            reserva.setReservaId(rs.getInt("idReserva"));
            reserva.setDataReserva(rs.getDate("dataReserva").toLocalDate());
            reserva.setHorarioInicio(rs.getTime("horaInicio").toLocalTime());
            reserva.setHorarioFim(rs.getTime("horaFim").toLocalTime());
            reserva.setSalaId(s);
            reserva.setUsuarioId(u);
            reserva.setRecursoId(re);
        }
        rs.close();
        ps.close();
        con.close();
        return reserva;
    }

    @Override
    public List<Reserva> listar(Reserva reserva) throws SQLException, ClassNotFoundException {
        List<Reserva> reservas = new ArrayList<>();
        Connection con = gDao.getConnection();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT r.idReserva, r.dataReserva, r.horaInicio, r.horaFim, ");
        sql.append("s.nome AS nomeSala, u.nome AS nomeUsuario, ");
        sql.append("re.nome AS nomeRecurso, ");
        sql.append("t.nome AS nomeTipo "); // Selecionando o nome do Tipo
        sql.append("FROM Reserva r, Sala s, Usuario u, TipoUsuario t, Recurso re "); // Inclui TipoUsuario t
        sql.append("WHERE r.salaId = s.idSala ");
        sql.append("AND r.UsuarioId = u.idUsuario ");
        sql.append("AND u.tipoUsuarioId = t.idTipo "); // NOVO JOIN: Usuário -> TipoUsuario
        sql.append("AND r.RecursoId = re.idRecurso ");
        PreparedStatement ps = con.prepareStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Reserva r = new Reserva();
            Sala s = new Sala();
            Usuario u = new Usuario();
            TipoUsuario t = new TipoUsuario();
            Recurso re = new Recurso();
            s.setNome(rs.getString("nomeSala"));
            u.setNome(rs.getString("nomeUsuario"));
            u.setTipoUsuario(t);
            t.setNome(rs.getString("nomeTipo"));
            re.setNome(rs.getString("nomeRecurso"));
            r.setReservaId(rs.getInt("idReserva"));
            r.setDataReserva(rs.getDate("dataReserva").toLocalDate());
            r.setHorarioInicio(rs.getTime("horaInicio").toLocalTime());
            r.setHorarioFim(rs.getTime("horaFim").toLocalTime());
            r.setSalaId(s);
            r.setUsuarioId(u);
            r.setRecursoId(re);
            reservas.add(r);
        }
        rs.close();
        ps.close();
        con.close();
        return reservas;
    }


}

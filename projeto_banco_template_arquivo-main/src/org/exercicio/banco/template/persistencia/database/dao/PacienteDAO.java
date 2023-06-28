package org.exercicio.banco.template.persistencia.database.dao;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Exame;
import org.exemplo.persistencia.database.model.Paciente;

public class PacienteDAO implements IEntityDAO<Paciente>{

	private IConnection conn;
	private IEntityDAO<Exame> exameDAO;
	
	public PacienteDAO(IConnection conn) {
		this.conn = conn;
		exameDAO = new ExameDAO(conn);
	}
	
	public void save(Paciente p) {
		String sql = "INSERT INTO PRONTUARIO.PACIENTE VALUES (?,?,?,?);";
		
		try {
			PreparedStatement ptsm = conn.getConnection().prepareStatement(sql);
			ptsm.setInt(1, 0);
			ptsm.setString(2, p.getNome());
			ptsm.setFloat(3, p.getAltura());
			ptsm.setFloat(4, p.getPeso());
			ptsm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Exame e : p.getExames()) {
			exameDAO.save(e);
		}
		
		conn.closeConnection();
	}
	
	public void delete(Paciente p) {
		
	}
	
	public void update(Paciente p) {
		Paciente temp = findById(p.getId());
		temp.setNome(p.getNome());
		temp.setAltura(p.getAltura());
		temp.setPeso(p.getPeso());
		String sql="UPDATE paciente SET nome= ?, altura= ?,peso= ? WHERE ID= ?;";
		 
		
		try {
			PreparedStatement ptsm =conn.getConnection().prepareStatement(sql);
			ptsm.setString(1,temp.getNome());
			ptsm.setFloat(2,temp.getAltura());
			ptsm.setFloat(3,temp.getPeso());
			ptsm.setInt(4,p.getId());
			
			ptsm.executeUpdate();
		   for(Exame e : p.getExames()) {
			   exameDAO.save(e);
		   }
		
	}catch (SQLException e) {
		e.printStackTrace();
		
	}
	}
	
	public Paciente findById(Integer id) {
		String sql1 = "SELECT * FROM PACIENTE WHERE ID = ?;";
		String sql2 = "SELECT * FROM EXAME E WHERE E.IDPACIENTE = ?;";
		ResultSet rs;
		Paciente p = new Paciente();
		List<Exame> exames = new ArrayList<>();
		
		try {
			PreparedStatement ptsm = conn.getConnection().prepareStatement(sql1);
			ptsm.setInt(1, id);
			rs = ptsm.executeQuery();
			
			while(rs.next()) {
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setAltura(rs.getFloat(3));
				p.setPeso(rs.getFloat(4));
			}
			
			ptsm = conn.getConnection().prepareStatement(sql2);
			ptsm.setInt(1, id);
			rs = ptsm.executeQuery();
			
			while(rs.next()) {
				exames.add(new Exame(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}
			
			p.setExames(exames);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			conn.closeConnection();
		}
		return p;
	}
}

package org.exercicio.banco.template.persistencia.database.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.exemplo.persistencia.database.dao.IEntityDAO;
import org.exemplo.persistencia.database.db.IConnection;
import org.exemplo.persistencia.database.model.Exame;

public class ExameDAO implements IEntityDAO<Exame> {

	private IConnection conn;

	public ExameDAO(IConnection conn) {
		this.conn = conn;
	}

	@Override
	public void save(Exame t) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO PRONTUARIO.EXAME VALUES (?,?,?,?);";

		try {
			PreparedStatement ptsm = conn.getConnection().prepareStatement(sql);
			ptsm.setInt(1, 0);
			ptsm.setString(2, t.getNome());
			ptsm.setString(3, t.getDescricao());
			ptsm.setInt(4, t.getIdPaciente());
			ptsm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Exame findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Exame t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Exame t) {
		// TODO Auto-generated method stub

	}

}

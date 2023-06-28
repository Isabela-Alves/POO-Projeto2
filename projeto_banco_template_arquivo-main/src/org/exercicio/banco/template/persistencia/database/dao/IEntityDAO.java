package org.exercicio.banco.template.persistencia.database.dao;

public interface IEntityDAO <T>{

	public void save(T t);
	public T findById(Integer id);
	public void update(T t);
	public void delete(T t);
}

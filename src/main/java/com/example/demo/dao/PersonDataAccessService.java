package com.example.demo.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertPerson(UUID id, Person person) {
		// TODO Auto-generated method stub
		String sql = "" + "INSERT INTO person (" + " id, " + " name) " + "VALUES (?, ?)";
		return jdbcTemplate.update(sql, id, person.getName());
	}

	@Override
	public List<Person> selectAllPeople() {
		// TODO Auto-generated method stub
		final String sql = "select id, name from person";
		return jdbcTemplate.query(sql, (resultSet, i) -> {
			return new Person(UUID.fromString(resultSet.getString("id")), resultSet.getString("name"));
		});
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		// TODO Auto-generated method stub
		final String sql = "select id, name from person where id = ?";
		Person person = jdbcTemplate.queryForObject(sql, new Object[] { id }, (resultSet, i) -> {
			UUID pid = UUID.fromString(resultSet.getString("id"));
			String pname = resultSet.getString("name");
			return new Person(pid, pname);
		});

		return Optional.ofNullable(person);
	}

	@Override
	public int deletePersonById(UUID id) {
		// TODO Auto-generated method stub
		String sql = "" + "DELETE FROM person " + "WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		// TODO Auto-generated method stub
		String sql = "" + "UPDATE person " + "SET name = ? " + "WHERE id = ?";
		return jdbcTemplate.update(sql, person.getName(), id);
	}

}

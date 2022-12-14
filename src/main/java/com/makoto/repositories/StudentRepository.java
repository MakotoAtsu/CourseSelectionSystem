package com.makoto.repositories;

import java.sql.SQLException;
import java.util.Collection;
import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.makoto.domain.entities.Student;

public class StudentRepository {

    private Dao<Student, Integer> studentDAO;

    @Inject
    public StudentRepository(DBConnector connector) throws SQLException {

        this.studentDAO = DaoManager.createDao(connector.getConnection(), Student.class);

        TableUtils.createTableIfNotExists(connector.getConnection(), Student.class);
    }

    public void create(Student teacher) throws SQLException {
        this.studentDAO.create(teacher);
    }


    public Student findByStudentId(String student_id) throws SQLException {
        QueryBuilder<Student, Integer> query = this.studentDAO.queryBuilder();
        query.where().eq(Student.FIELD_STUDENT_ID, student_id);
        var student = this.studentDAO.queryForFirst(query.prepare());
        return student;
    }

    public boolean exist(String student_id) throws SQLException {
        QueryBuilder<Student, Integer> query = this.studentDAO.queryBuilder();
        query.setCountOf(true);
        query.where().eq(Student.FIELD_STUDENT_ID, student_id);
        return this.studentDAO.countOf(query.prepare()) > 0;
    }

    public void delete(Student student) throws SQLException {
        this.studentDAO.delete(student);
    }

    public Collection<Student> getAll() throws SQLException {
        return this.studentDAO.queryForAll();
    }

}

package com.makoto.repositories;

import java.sql.SQLException;
import java.util.Collection;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.makoto.domain.entities.Teacher;

@Singleton
public class TeacherRepository {

    private Dao<Teacher, Integer> teacherDAO;

    @Inject
    public TeacherRepository(DBConnector connector) throws SQLException {

        this.teacherDAO = DaoManager.createDao(connector.getConnection(), Teacher.class);

        TableUtils.createTableIfNotExists(connector.getConnection(), Teacher.class);
    }

    public void create(Teacher teacher) throws SQLException {
        this.teacherDAO.create(teacher);
    }

    public Teacher findByTeacherId(String teacher_id) throws SQLException {
        QueryBuilder<Teacher, Integer> query = this.teacherDAO.queryBuilder();
        query.where().eq(Teacher.FEILD_TEACHER_ID, teacher_id);
        var teacher = this.teacherDAO.queryForFirst(query.prepare());
        return teacher;
    }

    public boolean exist(String teacher_id) throws SQLException {
        QueryBuilder<Teacher, Integer> query = this.teacherDAO.queryBuilder();
        query.setCountOf(true);
        query.where().eq(Teacher.FEILD_TEACHER_ID, teacher_id);
        return this.teacherDAO.countOf(query.prepare()) > 0;
    }

    public void delete(Teacher teacher) throws SQLException {
        this.teacherDAO.delete(teacher);
    }

    public Collection<Teacher> getAll() throws SQLException {
        return teacherDAO.queryForAll();
    }
}

package com.makoto.repositories;

import java.sql.SQLException;
import java.util.Collection;
import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.makoto.domain.entities.Course;


public class CourseRepository {
    private Dao<Course, Integer> courseDAO;

    @Inject
    public CourseRepository(DBConnector connector) throws SQLException {

        this.courseDAO = DaoManager.createDao(connector.getConnection(), Course.class);

        TableUtils.createTableIfNotExists(connector.getConnection(), Course.class);
    }

    public void create(Course course) throws SQLException {
        this.courseDAO.create(course);
    }

    public Course findByCode(String code) throws SQLException {
        QueryBuilder<Course, Integer> query = this.courseDAO.queryBuilder();
        query.where().eq(Course.FIELD_CODE, code);
        var Course = this.courseDAO.queryForFirst(query.prepare());
        return Course;
    }

    public void update(Course course) throws SQLException {
        this.courseDAO.update(course);
    }

    public void delete(Course course) throws SQLException {
        this.courseDAO.delete(course);
    }

    public Collection<Course> getAll() throws SQLException {
        return this.courseDAO.queryForAll();
    }
}

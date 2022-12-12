package com.makoto.repositories;

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
    public CourseRepository(DBConnector connector) throws Exception {

        this.courseDAO = DaoManager.createDao(connector.getConnection(), Course.class);

        TableUtils.createTableIfNotExists(connector.getConnection(), Course.class);
    }

    public void create(Course course) throws Exception {
        this.courseDAO.create(course);
    }

    public Course findByCode(String code) throws Exception {
        QueryBuilder<Course, Integer> query = this.courseDAO.queryBuilder();
        query.where().eq(Course.FIELD_CODE, code);
        var Course = this.courseDAO.queryForFirst(query.prepare());
        return Course;
    }

    public void delete(Course course) throws Exception {
        this.courseDAO.delete(course);
    }

    public Collection<Course> getAll() throws Exception {
        return this.courseDAO.queryForAll();
    }
}

package com.makoto.repositories;

import java.sql.SQLException;
import java.util.Collection;
import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.makoto.domain.entities.Course;
import com.makoto.domain.entities.CourseRegister;

public class CourseRegisterRepository {

    private Dao<CourseRegister, Integer> registerDAO;

    @Inject
    public CourseRegisterRepository(DBConnector connector) throws SQLException {

        this.registerDAO = DaoManager.createDao(connector.getConnection(), CourseRegister.class);

        TableUtils.createTableIfNotExists(connector.getConnection(), CourseRegister.class);
    }

    public void create(CourseRegister register) throws SQLException {
        this.registerDAO.create(register);
    }

    public void deleteById(int id) throws SQLException {
        this.registerDAO.deleteById(id);
    }

    public Collection<CourseRegister> getAll() throws SQLException {
        return this.registerDAO.queryForAll();
    }

    public int countRegisters(Course course) throws SQLException {
        QueryBuilder<CourseRegister, Integer> query = this.registerDAO.queryBuilder();
        query.setCountOf(true);
        query.where().eq(CourseRegister.FIELD_COURSE_ID, course.getId());
        return (int) this.registerDAO.countOf(query.prepare());
    }
}

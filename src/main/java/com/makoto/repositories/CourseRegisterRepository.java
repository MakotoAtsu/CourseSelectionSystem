package com.makoto.repositories;

import java.util.Collection;
import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import com.makoto.domain.entities.CourseRegister;

public class CourseRegisterRepository {

    private Dao<CourseRegister, Integer> courseRegisterDAO;

    @Inject
    public CourseRegisterRepository(DBConnector connector) throws Exception {

        this.courseRegisterDAO =
                DaoManager.createDao(connector.getConnection(), CourseRegister.class);

        TableUtils.createTableIfNotExists(connector.getConnection(), CourseRegister.class);
    }

    public void create(CourseRegister register) throws Exception {
        this.courseRegisterDAO.create(register);
    }

    public void deleteById(int id) throws Exception {
        this.courseRegisterDAO.deleteById(id);
    }

    public Collection<CourseRegister> getAll() throws Exception {
        return this.courseRegisterDAO.queryForAll();
    }
}

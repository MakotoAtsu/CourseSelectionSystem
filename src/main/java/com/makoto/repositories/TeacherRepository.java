package com.makoto.repositories;

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
    public TeacherRepository(DBConnector connector) throws Exception {

        this.teacherDAO = DaoManager.createDao(connector.getConnection(), Teacher.class);

        TableUtils.createTableIfNotExists(connector.getConnection(), Teacher.class);
    }

    public void create(Teacher teacher) throws Exception {
        this.teacherDAO.create(teacher);
    }

    public Teacher findByTeacherId(String teacher_id) throws Exception {
        QueryBuilder<Teacher, Integer> query = this.teacherDAO.queryBuilder();
        query.where().eq(Teacher.FEILD_TEACHER_ID, teacher_id);
        var teacher = this.teacherDAO.queryForFirst(query.prepare());
        return teacher;
    }

    public void delete(Teacher teacher) throws Exception {
        this.teacherDAO.delete(teacher);
    }

    public Collection<Teacher> getAll() throws Exception {
        return teacherDAO.queryForAll();
    }
}

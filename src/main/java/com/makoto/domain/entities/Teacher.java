package com.makoto.domain.entities;

import java.util.Collection;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "Teachers")
public class Teacher {
    public final static String FEILD_TEACHER_ID = "teacher_id";
    public final static String FIELD_NAME = "name";


    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = FEILD_TEACHER_ID, unique = true, canBeNull = false)
    private String teacherId;

    @DatabaseField(columnName = FIELD_NAME, canBeNull = false)
    private String name;

    @ForeignCollectionField
    private Collection<Course> courses;
}

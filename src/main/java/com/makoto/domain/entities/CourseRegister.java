package com.makoto.domain.entities;

import com.j256.ormlite.field.*;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DatabaseTable(tableName = "CourseRegister")
public class CourseRegister {
    public final static String FIELD_COURSE_ID = "course_id";
    public final static String FIELD_STUDENT_ID = "student_id";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true,columnName = FIELD_COURSE_ID, canBeNull = false,
            uniqueCombo = true)
    private Course course;

    @DatabaseField(foreign = true, foreignAutoRefresh = true,columnName = FIELD_STUDENT_ID, canBeNull = false,
            uniqueCombo = true)
    private Student student;
}

package com.makoto.domain.entities;

import com.fasterxml.jackson.module.kotlin.ReflectionCache.BooleanTriState.False;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "Students")
public class Student {
    public static final String FIELD_STUDENT_ID = "student_id";
    public static final String FIELD_NAME = "name";

    @DatabaseField(id = true, generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_STUDENT_ID, unique = true, canBeNull = false)
    public String student_id;

    @DatabaseField(columnName = FIELD_NAME, canBeNull = false)
    public String name;

    @DatabaseField
    public int grade;


}

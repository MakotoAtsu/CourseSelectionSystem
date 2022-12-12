package com.makoto.domain.entities;

import java.util.Collection;
import com.j256.ormlite.field.*;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "Course")
public class Course {

    public final static String FIELD_NAME = "name";
    public final static String FIELD_CODE = "code";
    public final static String FIELD_OWNER = "owner_id";

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(columnName = FIELD_CODE, unique = true, canBeNull = false)
    private String courseCode;

    @DatabaseField(columnName = FIELD_NAME, canBeNull = false)
    private String name;

    @DatabaseField(foreign = true, columnName = FIELD_OWNER, canBeNull = false)
    private Teacher teacher;

    @DatabaseField(canBeNull = false)
    private int amount;

    @DatabaseField
    private String semester;

    @DatabaseField
    private String description;

}

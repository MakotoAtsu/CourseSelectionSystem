package com.makoto.repositories;

import com.google.inject.Singleton;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import lombok.Getter;

@Getter
@Singleton
public class DBConnector {

    private JdbcConnectionSource connection;

    public DBConnector() throws Exception {
        this.connection = new JdbcConnectionSource("jdbc:sqlite:sample.db");
        System.out.println("DB Connect!");
    }
}

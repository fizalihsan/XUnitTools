package com.fizal.xunit.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by fmohamed on 9/13/2016.
 */
@Component
public class DemoDao {
    @Autowired
    private DataSource dataSource;

    public int query() {
        String sql = "select count(1) from SCHEMA1.TABLE1 a, SCHEMA2.TABLE2 b WHERE a.RSRC_ID = b.RSRC_ID";
        return new JdbcTemplate(dataSource).queryForObject(sql, Integer.class);
    }
}

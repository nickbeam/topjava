package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile(Profiles.HSQL_DB)
public class JdbcMealRepositoryHsqldbImpl extends AbstractJdbcMealRepositoryImpl {

    @Autowired
    public JdbcMealRepositoryHsqldbImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp toDbDate(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }
}

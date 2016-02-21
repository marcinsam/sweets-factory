package pl.marboz.myproject.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.marboz.myproject.model.User;
import pl.marboz.myproject.repository.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marcin Bozek on 2016-02-18.
 */
@Repository
public class UserRepository implements BaseRepository<User> {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int save(User user) {
        String sql = "insert into users (created, name, email) values (:created, :name, :email)";
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("created", new Timestamp(System.currentTimeMillis()));
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
        user.setCreated(LocalDateTime.now());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int id = namedParameterJdbcTemplate.update(sql, sqlParameterSource,keyHolder);
        return id;

    }

    @Override
    public User findByName(String name) {
        String sql = "select * from users where name=:name";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("name", name);
        User id = namedParameterJdbcTemplate.queryForObject(sql, sqlParameterSource, new UserMapper());
        return id;
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean delete(User user) {
        throw new UnsupportedOperationException();
    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setCreated(rs.getTimestamp("created").toLocalDateTime());
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }


}

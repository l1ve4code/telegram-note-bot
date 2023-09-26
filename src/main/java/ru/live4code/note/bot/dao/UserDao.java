package ru.live4code.note.bot.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void insertUser(String userName, Long chatId) {
        namedParameterJdbcTemplate.update(
                """
                    insert into telegram.users
                    (userName, chatId)
                    values (:userName, :chatId)
                    on conflict do nothing
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("userName", userName)
                        .addValue("chatId", chatId)
        );
    }

    public boolean checkUserExists(String userName) {
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(
                """
                    select exists(
                        select 1
                        from telegram.users
                        where userName = :userName
                    )
                    """.trim(),
                new MapSqlParameterSource("userName", userName),
                Boolean.class
        ));
    }

}

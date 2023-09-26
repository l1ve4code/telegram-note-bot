package ru.live4code.note.bot.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShareDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean checkShareExists(String shareUserName, String readUserName) {
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(
                """
                    select exists(
                        select 1
                        from telegram.shares
                        where shareUserName = :shareUserName and readUserName = :readUserName
                    )
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("shareUserName", shareUserName)
                        .addValue("readUserName", readUserName),
                Boolean.class
        ));
    }

    public void insertShare(String shareUserName, String readUserName) {
        namedParameterJdbcTemplate.update(
                """
                    insert into telegram.shares
                    (shareUserName, readUserName)
                    values (:shareUserName, :readUserName)
                    on conflict do nothing
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("shareUserName", shareUserName)
                        .addValue("readUserName", readUserName)
        );
    }

    public void deleteShare(String shareUserName, String readUserName) {
        namedParameterJdbcTemplate.update(
                """
                    delete from telegram.shares
                    where shareUserName = :shareUserName and readUserName = :readUserName
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("shareUserName", shareUserName)
                        .addValue("readUserName", readUserName)
        );
    }

}

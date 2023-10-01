package ru.live4code.note.bot.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<String> getUsersToShare(String shareUserName) {
        return namedParameterJdbcTemplate.query(
                """
                    select u.userName
                    from telegram.users u
                    left join telegram.shares s on u.userName = s.readUserName
                    where s.shareUserName != :shareUserName or s.shareUserName is null
                    """.trim(),
                new MapSqlParameterSource("shareUserName", shareUserName),
                (rs, rn) -> rs.getString("userName")
        );
    }

    public List<String> getMyShares(String shareUserName) {
        return namedParameterJdbcTemplate.query(
                """
                    select shareUserName
                    from telegram.shares
                    where shareUserName = :shareUserName
                    """.trim(),
                new MapSqlParameterSource("shareUserName", shareUserName),
                (rs, rn) -> rs.getString("shareUserName")
        );
    }

}

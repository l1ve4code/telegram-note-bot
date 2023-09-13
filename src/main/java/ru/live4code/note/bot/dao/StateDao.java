package ru.live4code.note.bot.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.live4code.note.bot.model.State;

@Component
@RequiredArgsConstructor
public class StateDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public boolean isUserWithState(Long chatId) {
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(
                """
                    select exists(
                        select 1
                        from telegram.states
                        where chatId = :chatId
                    )
                    """.trim(),
                new MapSqlParameterSource("chatId", chatId),
                Boolean.class
        ));
    }

    public State getUserState(Long chatId) {
        String state = namedParameterJdbcTemplate.queryForObject(
                """
                    select state
                    from telegram.states
                    where chatId = :chatId
                    """.trim(),
                new MapSqlParameterSource("chatId", chatId),
                String.class
        );
        return State.fromString(state);
    }

    public void setUserState(Long chatId, State state) {
        namedParameterJdbcTemplate.update(
                """
                    insert into telegram.states
                    (chatId, state) values
                    (:chatId, :state::telegram.state_enum_value)
                    on conflict (chatId) do update
                    set state = :state::telegram.state_enum_value
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("chatId", chatId)
                        .addValue("state", state.getStateName())
        );
    }

    public void deleteUserState(Long chatId) {
        namedParameterJdbcTemplate.update(
                """
                    delete from telegram.states
                    where chatId = :chatId
                    """.trim(),
                new MapSqlParameterSource("chatId", chatId)
        );
    }

}

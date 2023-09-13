package ru.live4code.note.bot.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.live4code.note.bot.model.Note;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoteDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void addNote(Long chatId, String note) {
        namedParameterJdbcTemplate.update(
                """
                    insert into telegram.notes
                    (chatId, note) values
                    (:chatId, :note)
                    """.trim(),
                new MapSqlParameterSource()
                    .addValue("chatId", chatId)
                    .addValue("note", note)
        );
    }

    public void deleteNote(Long chatId, Long noteId) {
        namedParameterJdbcTemplate.update(
                """
                    delete from telegram.notes
                    where chatId = :chatId and id = :noteId
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("chatId", chatId)
                        .addValue("noteId", noteId)
        );
    }

    public List<Note> getNotes(Long chatId) {
        return namedParameterJdbcTemplate.query(
                """
                    select id, note
                    from telegram.notes
                    where chatId = :chatId
                    """.trim(),
                new MapSqlParameterSource("chatId", chatId),
                (rs, rn) -> new Note(rs.getLong("id"), rs.getString("note"))
        );
    }

}

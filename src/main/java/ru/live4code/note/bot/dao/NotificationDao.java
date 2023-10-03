package ru.live4code.note.bot.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.live4code.note.bot.model.Notification;
import ru.live4code.note.bot.model.NotificationToSend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void initNotification(Long chatId) {
        namedParameterJdbcTemplate.update(
                """
                    insert into telegram.notifications (chatId)
                    values (:chatId)
                    """.trim(),
                new MapSqlParameterSource("chatId", chatId)
        );
    }

    public void fillNotificationDate(Long notificationId, LocalDate date) {
        namedParameterJdbcTemplate.update(
                """
                    update telegram.notifications
                    set date = :date
                    where id = :id
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("date", date)
                        .addValue("id", notificationId)
        );
    }

    public void fillNotificationTime(Long notificationId, LocalTime time) {
        namedParameterJdbcTemplate.update(
                """
                    update telegram.notifications
                    set time = :time
                    where id = :id
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("time", time)
                        .addValue("id", notificationId)
        );
    }

    public void fillNotificationText(Long notificationId, String message) {
        namedParameterJdbcTemplate.update(
                """
                    update telegram.notifications
                    set notification = :message
                    where id = :id
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("message", message)
                        .addValue("id", notificationId)
        );
    }

    public Long getIdLastNotFilledNotification(Long chatId) {
        return namedParameterJdbcTemplate.query(
                """
                    select id
                    from telegram.notifications
                    where chatId = :chatId and notification is null
                    """.trim(),
                new MapSqlParameterSource("chatId", chatId),
                (rs, rn) -> rs.getLong("id")
        ).stream().findFirst().orElse(null);
    }

    public void markNotificationSent(List<Long> notificationIds) {
        namedParameterJdbcTemplate.batchUpdate(
                """
                    update telegram.notifications
                    set isSent = true
                    where id = id
                    """.trim(),
                notificationIds.stream()
                        .map(item -> new MapSqlParameterSource("id", item))
                        .toList()
                        .toArray(new MapSqlParameterSource[]{})
        );
    }

    public List<Notification> getActualUserNotifications(Long chatId, LocalDateTime dateTime) {
        return namedParameterJdbcTemplate.query(
                """
                        select id, notification, (time + date)::timestamp as dateTime
                        from telegram.notifications
                        where
                            notification is not null
                            and chatId = :chatId
                            and date >= :date
                            and time >= :time
                            and isSent is false
                        """.trim(),
                new MapSqlParameterSource()
                        .addValue("chatId", chatId)
                        .addValue("date", dateTime.toLocalDate())
                        .addValue("time", dateTime.toLocalTime()),
                (rs, rn) -> new Notification(rs.getLong("id"), rs.getTimestamp("dateTime").toLocalDateTime(), rs.getString("notification"))
        );
    }

    public List<NotificationToSend> getNotificationToSend(LocalDateTime dateTime) {
        return namedParameterJdbcTemplate.query(
                """
                    select id, chatId, notification
                    from telegram.notifications
                    where notification is not null and date = :date and time <= :time and isSent is false
                    """.trim(),
                new MapSqlParameterSource()
                        .addValue("date", dateTime.toLocalDate())
                        .addValue("time", dateTime.toLocalTime()),
                (rs, rn) -> new NotificationToSend(rs.getLong("id"), rs.getLong("chatId"), rs.getString("notification"))
        );
    }

}

package ru.live4code.note.bot.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.live4code.note.bot.dao.NotificationDao;
import ru.live4code.note.bot.model.NotificationToSend;
import ru.live4code.note.bot.service.MessageSenderService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationJob {

    private final NotificationDao notificationDao;
    private final MessageSenderService messageSenderService;

    @Scheduled(cron = "0 * * * * *")
    public void execute() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<NotificationToSend> notifications = notificationDao.getNotificationToSend(currentDateTime);

        notifications.forEach(item -> messageSenderService.sendMessage(
                        item.chatId(),
                        String.format("[NOTIFICATION] %s", item.notification())
                )
        );

        notificationDao.markNotificationSent(notifications.stream().map(NotificationToSend::id).toList());
    }

}

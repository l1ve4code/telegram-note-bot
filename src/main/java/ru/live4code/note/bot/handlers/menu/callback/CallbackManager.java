package ru.live4code.note.bot.handlers.menu.callback;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.live4code.note.bot.model.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CallbackManager {

    @Resource
    private final List<Callback> existingCallbacks;

    @Resource
    private final List<CallbackAnswer> existingCallbackAnswers;

    @Bean
    public Map<CallbackType, Callback> initializeCallbacks() {
        var callbackMap = new HashMap<CallbackType, Callback> ();
        existingCallbacks.forEach(item -> {
            callbackMap.put(item.getCallbackType(), item);
        });
        return callbackMap;
    }

    @Bean
    public Map<State, CallbackAnswer> initializeCallbackAnswers() {
        var callbackAnswerMap = new HashMap<State, CallbackAnswer>();
        existingCallbackAnswers.forEach(item -> {
            callbackAnswerMap.put(item.getState(), item);
        });
        return callbackAnswerMap;
    }

}

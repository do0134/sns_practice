package com.practice.sns.Service;

import com.practice.sns.exception.ErrorCode;
import com.practice.sns.exception.SNSApplicationException;
import com.practice.sns.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {

    private final static Long DEFAULT_TiMEOUT = 60L + 1000 + 60;
    private final static String ALARM_NAME = "alarm";
    private final EmitterRepository emitterRepository;

    public SseEmitter connectAlarm(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TiMEOUT);
        emitterRepository.save(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("connect completed"));
        } catch (IOException e) {
            throw new SNSApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
        }

        return sseEmitter;
    }

    public void send(Long alarmId, Long userId) {
        emitterRepository.get(userId).ifPresentOrElse(sseEmitter -> {
            try{
                sseEmitter.send(SseEmitter.event().id(alarmId.toString()).name(ALARM_NAME).data("new alarm"));
            } catch (IOException e) {
                emitterRepository.delete(userId);
                throw new SNSApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        }, () -> log.info("No emitter founded"));
    }
}

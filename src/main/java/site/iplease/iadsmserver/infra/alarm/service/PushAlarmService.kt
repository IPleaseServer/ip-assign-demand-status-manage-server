package site.iplease.iadsmserver.infra.alarm.service

import reactor.core.publisher.Mono

interface PushAlarmService {
    fun sendAlarm(title: String, description: String): Mono<Unit>

}

package site.iplease.iadsmserver.infra.alarm.service

import reactor.core.publisher.Mono

interface PushAlarmService {
    fun publish(title: String, description: String): Mono<Unit>
}

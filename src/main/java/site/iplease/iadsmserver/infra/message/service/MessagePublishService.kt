package site.iplease.iadsmserver.infra.message.service

import reactor.core.publisher.Mono
import site.iplease.iadsmserver.infra.message.type.MessageType

interface MessagePublishService {
    fun publish(type: MessageType, message: Any): Mono<Unit>
}

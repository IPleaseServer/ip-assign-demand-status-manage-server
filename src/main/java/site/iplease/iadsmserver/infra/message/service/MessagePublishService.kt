package site.iplease.iadsmserver.infra.message.service

import reactor.core.publisher.Mono

interface MessagePublishService {
    fun sendError(routingKey: String, error: Any): Mono<Unit>
}

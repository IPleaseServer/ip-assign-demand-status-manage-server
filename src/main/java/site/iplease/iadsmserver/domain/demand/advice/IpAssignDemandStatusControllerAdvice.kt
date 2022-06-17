package site.iplease.iadsmserver.domain.demand.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.exception.DemandStatusAlreadyExistsException
import site.iplease.iadsmserver.domain.demand.exception.UnChangeableDemandStatusException

@RestControllerAdvice
class IpAssignDemandStatusControllerAdvice {
    @ExceptionHandler(DemandStatusAlreadyExistsException::class)
    fun handle(exception: DemandStatusAlreadyExistsException): Mono<ResponseEntity<String>> =
        ResponseEntity.badRequest()
            .body(exception.localizedMessage)
            .toMono()

    @ExceptionHandler(UnChangeableDemandStatusException::class)
    fun handle(exception: UnChangeableDemandStatusException): Mono<ResponseEntity<String>> =
        ResponseEntity.badRequest()
            .body(exception.localizedMessage)
            .toMono()
}
package site.iplease.iadsmserver.domain.demand.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.iadsmserver.domain.demand.exception.DemandStatusAlreadyExistsException
import site.iplease.iadsmserver.domain.demand.exception.MalformedIpException
import site.iplease.iadsmserver.domain.demand.exception.UnChangeableDemandStatusException
import site.iplease.iadsmserver.domain.demand.exception.UnknownDemandStatusException

@RestControllerAdvice
class IpAssignDemandStatusControllerAdvice {
    @ExceptionHandler(DemandStatusAlreadyExistsException::class)
    fun handle(exception: DemandStatusAlreadyExistsException): Mono<ResponseEntity<String>> =
        ResponseEntity.badRequest()
            .body(exception.localizedMessage)
            .toMono()

    @ExceptionHandler(UnknownDemandStatusException::class)
    fun handle(exception: UnknownDemandStatusException): Mono<ResponseEntity<String>> =
        ResponseEntity.badRequest()
            .body(exception.localizedMessage)
            .toMono()

    @ExceptionHandler(UnChangeableDemandStatusException::class)
    fun handle(exception: UnChangeableDemandStatusException): Mono<ResponseEntity<String>> =
        ResponseEntity.badRequest()
            .body(exception.localizedMessage)
            .toMono()

    @ExceptionHandler(MalformedIpException::class)
    fun handle(exception: MalformedIpException): Mono<ResponseEntity<String>> =
        ResponseEntity.badRequest()
            .body(exception.localizedMessage)
            .toMono()
}
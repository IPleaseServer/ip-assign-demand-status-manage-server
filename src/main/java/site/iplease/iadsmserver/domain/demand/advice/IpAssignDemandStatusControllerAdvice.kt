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
import site.iplease.iadsmserver.global.error.ErrorResponse
import site.iplease.iadsmserver.global.error.ErrorStatus

@RestControllerAdvice
class IpAssignDemandStatusControllerAdvice {
    @ExceptionHandler(DemandStatusAlreadyExistsException::class)
    fun handle(e: DemandStatusAlreadyExistsException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.DEMAND_STATUS_ALREADY_EXISTS,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()

    @ExceptionHandler(UnknownDemandStatusException::class)
    fun handle(e: UnknownDemandStatusException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.UNKNOWN_DEMAND_STATUS,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()

    @ExceptionHandler(UnChangeableDemandStatusException::class)
    fun handle(e: UnChangeableDemandStatusException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.UNCHANGEABLE_DEMAND_STATUS,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()

    @ExceptionHandler(MalformedIpException::class)
    fun handle(e: MalformedIpException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.MALFORMED_IP,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )
            ).toMono()
}
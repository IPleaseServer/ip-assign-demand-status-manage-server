package site.iplease.iadsmserver.domain.demand.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.request.RejectDemandRequest
import site.iplease.iadsmserver.domain.demand.data.response.ConfirmDemandResponse
import site.iplease.iadsmserver.domain.demand.service.DemandStatusService
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter
import site.iplease.iadsmserver.global.demand.message.IpAssignDemandConfirmMessage
import site.iplease.iadsmserver.infra.message.service.MessagePublishService
import site.iplease.iadsmserver.infra.message.type.MessageType

@RestController
@RequestMapping("/api/v1/demand/{demandId}/status")
class IpAssignDemandStatusController(
    private val demandStatusConverter: DemandStatusConverter,
    private val demandStatusService: DemandStatusService,
    private val messagePublishService: MessagePublishService
) {
    @PutMapping("/confirm")
    fun confirmDemand(@PathVariable demandId: Long): Mono<ResponseEntity<ConfirmDemandResponse>> =
        demandStatusService.confirmDemand(demandId)
            .map { demandStatus -> IpAssignDemandConfirmMessage(demandStatus.demandId) }//Message의 property가 많아지면 converter로 로직 격리할것
            .flatMap { message -> messagePublishService.publish(MessageType.IP_ASSIGN_DEMAND_CONFIRM, message) }
            .map { _ -> ConfirmDemandResponse(demandId = demandId) }
            .map { response -> ResponseEntity.ok(response) }

    @PutMapping("/reject")
    fun rejectDemand(@PathVariable demandId: Long, @RequestBody request: RejectDemandRequest): Mono<ResponseEntity<Unit>> =
        demandStatusService.rejectDemand(demandId, request.reason)
            .flatMap { demandStatus -> demandStatusConverter.toRejectMessage(demandStatus) }
            .map { _ -> ResponseEntity.ok(Unit) }
}
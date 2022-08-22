package site.iplease.iadsmserver.domain.demand.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.response.DemandStatusResponse
import site.iplease.iadsmserver.domain.demand.service.DemandStatusQueryService
import site.iplease.iadsmserver.domain.demand.util.DemandStatusConverter

@RestController
@RequestMapping("/api/v1/demand/status/query")
class IpAssignDemandStatusQueryController(
    private val demandStatusConverter: DemandStatusConverter,
    private val demandStatusQueryService: DemandStatusQueryService
) {
    @GetMapping("/{demandId}")
    fun getStatusByDemandId(@PathVariable demandId: Long): Mono<ResponseEntity<DemandStatusResponse>> =
        demandStatusQueryService.getStatusByDemandId(demandId)
            .flatMap { demandStatusConverter.toResponse(it) }
            .map { ResponseEntity.ok(it) }
}
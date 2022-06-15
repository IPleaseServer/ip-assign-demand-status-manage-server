package site.iplease.iadsmserver.domain.demand.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import site.iplease.iadsmserver.domain.demand.data.entity.DemandStatus

interface DemandStatusRepository: ReactiveCrudRepository<DemandStatus, Long> {
    fun existsByDemandId(demandId: Long): Mono<Boolean>
    fun deleteByDemandId(demandId: Long): Mono<Void>
}
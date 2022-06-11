package site.iplease.iadsmserver.domain.demand.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import site.iplease.iadsmserver.domain.demand.entity.DemandStatus

interface DemandStatusRepository: ReactiveCrudRepository<DemandStatus, Long>
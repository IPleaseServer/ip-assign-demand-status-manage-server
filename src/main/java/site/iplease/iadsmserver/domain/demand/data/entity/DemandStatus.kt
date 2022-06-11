package site.iplease.iadsmserver.domain.demand.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

@Table
data class DemandStatus (
    @Id val id: Long,
    val demandId: Long,
    val status: DemandStatusType
)

package site.iplease.iadsmserver.domain.demand.data.response

import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

data class DemandStatusResponse (
    val id: Long,
    val demandId: Long,
    val status: DemandStatusType
)
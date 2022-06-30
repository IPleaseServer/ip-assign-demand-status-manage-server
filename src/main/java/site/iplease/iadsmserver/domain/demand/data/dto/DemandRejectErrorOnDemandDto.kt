package site.iplease.iadsmserver.domain.demand.data.dto

import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

data class DemandRejectErrorOnDemandDto (
    val demandId: Long,
    val issuerId: Long,
    val originStatus: DemandStatusType,
    val message: String
)
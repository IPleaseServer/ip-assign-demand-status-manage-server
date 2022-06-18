package site.iplease.iadsmserver.global.demand.message

import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

data class IpAssignDemandRejectMessage (
    val demandId: Long,
    val originStatus: DemandStatusType,
    val reason: String
)
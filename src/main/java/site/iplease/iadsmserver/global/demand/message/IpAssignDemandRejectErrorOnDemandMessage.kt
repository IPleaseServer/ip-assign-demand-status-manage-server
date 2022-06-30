package site.iplease.iadsmserver.global.demand.message

import site.iplease.iadsmserver.domain.demand.data.type.DemandStatusType

data class IpAssignDemandRejectErrorOnDemandMessage (
    val demandId: Long,
    val issuerId: Long,
    val originStatus: DemandStatusType,
    val message: String
)
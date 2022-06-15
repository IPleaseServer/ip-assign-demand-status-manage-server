package site.iplease.iadsmserver.global.demand.message

import site.iplease.iadsmserver.global.demand.type.AssignIpUsageType
import java.time.LocalDate

data class IpAssignDemandCancelErrorOnStatusMessage(
    val id: Long,
    val issuerId: Long,
    val title: String,
    val description: String,
    val usage: AssignIpUsageType,
    val expireAt: LocalDate,
    val message: String,
)
package site.iplease.iadsmserver.domain.demand.data.dto

import site.iplease.iadsmserver.global.demand.type.AssignIpUsageType
import java.time.LocalDate

data class DemandDto (
    val id: Long,
    val issuerId: Long,
    val title: String,
    val description: String,
    val usage: AssignIpUsageType,
    val expireAt: LocalDate
)
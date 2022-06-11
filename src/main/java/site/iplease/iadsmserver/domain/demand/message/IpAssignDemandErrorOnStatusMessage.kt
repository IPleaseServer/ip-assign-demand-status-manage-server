package site.iplease.iadsmserver.domain.demand.message

data class IpAssignDemandErrorOnStatusMessage(
    val demandId: Long,
    val message: String
)
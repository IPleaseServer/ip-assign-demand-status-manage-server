package site.iplease.iadsmserver.global.demand.message

data class IpAssignDemandErrorOnStatusMessage(
    val demandId: Long,
    val message: String
)
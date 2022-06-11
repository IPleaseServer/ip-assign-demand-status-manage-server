package site.iplease.iadsmserver.infra.message.type

enum class MessageType(val routingKey: String) {
    DEMAND_ERROR_ON_STATUS("ipAssignDemandErrorOnStatus")
}

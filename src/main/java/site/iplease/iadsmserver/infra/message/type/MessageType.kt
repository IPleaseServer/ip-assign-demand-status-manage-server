package site.iplease.iadsmserver.infra.message.type

enum class MessageType(val routingKey: String) {
    IP_ASSIGN_DEMAND_CREATE("ipAssignDemandCreate"),
    DEMAND_ERROR_ON_STATUS("ipAssignDemandErrorOnStatus");

    companion object {
        fun of(routingKey: String) = values().first { it.routingKey == routingKey }
    }
}

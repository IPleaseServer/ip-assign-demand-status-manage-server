package site.iplease.iadsmserver.global.demand.subscriber

import site.iplease.iadsmserver.global.demand.message.IpAssignDemandRejectErrorOnDemandMessage

interface IpAssignDemandRejectErrorOnDemandSubscriber {
    fun subscribe(message: IpAssignDemandRejectErrorOnDemandMessage)
}
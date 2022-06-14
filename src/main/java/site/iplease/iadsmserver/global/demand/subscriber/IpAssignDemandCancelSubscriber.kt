package site.iplease.iadsmserver.global.demand.subscriber

import site.iplease.iadsmserver.global.demand.message.IpAssignDemandCancelMessage

interface IpAssignDemandCancelSubscriber {
    fun subscribe(message: IpAssignDemandCancelMessage)
}
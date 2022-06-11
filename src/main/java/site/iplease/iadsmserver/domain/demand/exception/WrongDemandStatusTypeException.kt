package site.iplease.iadsmserver.domain.demand.exception

import site.iplease.iadsmserver.domain.demand.type.DemandStatusType

class WrongDemandStatusTypeException(message: String, status: DemandStatusType): RuntimeException("잘못된 예약상태입니다! - $message - $status")

package site.iplease.iadsmserver.domain.demand.exception

class UnknownDemandStatusException(message: String): RuntimeException("예약상태정보를 찾을 수 없습니다! - $message")
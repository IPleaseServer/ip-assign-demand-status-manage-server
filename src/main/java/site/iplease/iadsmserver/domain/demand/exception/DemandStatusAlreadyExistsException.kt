package site.iplease.iadsmserver.domain.demand.exception

class DemandStatusAlreadyExistsException(message: String, demandId: Long): RuntimeException("이미 존재하는 예약상태정보입니다! - $message - $demandId")

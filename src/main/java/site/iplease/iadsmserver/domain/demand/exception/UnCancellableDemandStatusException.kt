package site.iplease.iadsmserver.domain.demand.exception

class UnCancellableDemandStatusException(message: String): RuntimeException("예약을 취소할 수 없습니다! - $message")

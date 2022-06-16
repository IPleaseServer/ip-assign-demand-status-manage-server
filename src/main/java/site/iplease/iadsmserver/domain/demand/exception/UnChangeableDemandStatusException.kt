package site.iplease.iadsmserver.domain.demand.exception

class UnChangeableDemandStatusException(message: String): RuntimeException("상태를 변경할 수 없는 예약상태정보입니다!- $message")
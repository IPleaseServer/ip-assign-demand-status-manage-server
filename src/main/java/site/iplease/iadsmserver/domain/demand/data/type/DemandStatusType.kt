package site.iplease.iadsmserver.domain.demand.data.type

//TODO DemandStatusType으로 이름변경
enum class DemandStatusType {
    CREATE, CONFIRM;

    companion object {
        private val cancellable: Set<DemandStatusType> = setOf(CREATE)
        fun isCancellable(status: DemandStatusType): Boolean = cancellable.contains(status)
    }
}

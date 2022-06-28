package site.iplease.iadsmserver.domain.demand.data.type

enum class DemandStatusType {
    CREATE, CONFIRM, REJECT, ACCEPT;

    fun isChangeable(status: DemandStatusType): Boolean = Companion.isChangeable(this, status)

    companion object {
        private val cancellable: Set<DemandStatusType> = setOf(CREATE)
        //initialize 문제로인해, 아래와 같은 방식으로 binding합니다.
        private val changeable: Map<DemandStatusType, Set<DemandStatusType>> = mapOf(
            CREATE to setOf(CONFIRM, ACCEPT, REJECT),
            CONFIRM to setOf(CREATE, ACCEPT, REJECT),
            REJECT to setOf()
        )
        fun isCancellable(status: DemandStatusType): Boolean = cancellable.contains(status)
        fun isChangeable(origin: DemandStatusType, tobe: DemandStatusType): Boolean = changeable[origin]!!.contains(tobe)
    }
}

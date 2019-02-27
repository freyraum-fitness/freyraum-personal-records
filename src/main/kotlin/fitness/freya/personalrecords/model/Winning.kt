package fitness.freya.personalrecords.model

enum class Winning {
  MORE{
    override fun comparator(): Comparator<Long> =
        Comparator { i1: Long, i2: Long -> i1.compareTo(i2) }
  },
  LESS{
    override fun comparator(): Comparator<Long> = MORE.comparator().reversed()
  };

  abstract fun comparator(): Comparator<Long>
}
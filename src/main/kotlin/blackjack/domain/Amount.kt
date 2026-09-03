package blackjack.domain

import java.math.BigDecimal

@JvmInline
value class Amount private constructor(
    val value: BigDecimal,
) {
    operator fun plus(other: Amount): Amount = from(value + other.value)

    operator fun times(multiplier: BigDecimal): Amount = from(value * multiplier)

    operator fun unaryMinus(): Amount = from(-value)

    companion object {
        val ZERO: Amount = from(BigDecimal.ZERO)

        fun from(value: String): Amount = from(value.toBigDecimal())

        private fun from(value: BigDecimal): Amount = Amount(value.stripTrailingZeros())
    }
}

package blackjack.domain

import java.math.BigDecimal

@JvmInline
value class Amount private constructor(
    val value: BigDecimal,
) {
    companion object {
        fun from(value: String): Amount = Amount(value.toBigDecimal().stripTrailingZeros())
    }
}

package blackjack.domain

import java.math.BigDecimal

enum class PlayerResult(
    private val profitMultiplier: BigDecimal,
) {
    BLACKJACK(BigDecimal("1.5")),
    WIN(BigDecimal.ONE),
    DRAW(BigDecimal.ZERO),
    LOSE(BigDecimal.ONE.negate()),
    ;

    fun profit(bettingAmount: Amount): Amount = bettingAmount * profitMultiplier

    companion object {
        fun from(
            player: Player,
            dealer: Dealer,
        ): PlayerResult {
            if (player.isBust()) return LOSE
            if (player.isBlackjack() && dealer.isBlackjack()) return DRAW
            if (player.isBlackjack()) return BLACKJACK
            if (dealer.isBlackjack()) return LOSE
            if (dealer.isBust()) return WIN
            return when {
                player.score() > dealer.score() -> WIN
                player.score() < dealer.score() -> LOSE
                else -> DRAW
            }
        }
    }
}

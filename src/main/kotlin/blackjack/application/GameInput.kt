package blackjack.application

import blackjack.domain.Amount
import blackjack.domain.Player

interface GameInput {
    fun readPlayerNames(): String

    fun readBettingAmount(name: String): Amount

    fun wantsHit(player: Player): Boolean
}

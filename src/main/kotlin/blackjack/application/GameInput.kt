package blackjack.application

import blackjack.domain.Player

interface GameInput {
    fun readPlayerNames(): String

    fun wantsHit(player: Player): Boolean
}

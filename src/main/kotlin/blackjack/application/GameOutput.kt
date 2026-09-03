package blackjack.application

import blackjack.domain.Player

interface GameOutput {
    fun showInitialHands(players: List<Player>)

    fun showHand(player: Player)

    fun showFinalHands(players: List<Player>)
}

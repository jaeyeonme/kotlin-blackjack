package blackjack.application

import blackjack.domain.Deck
import blackjack.domain.Player

class BlackjackGame(
    private val input: GameInput,
    private val output: GameOutput,
    private val deck: Deck,
) {
    fun start() {
        val players = createPlayers(input.readPlayerNames())
        players.forEach(::dealInitialCards)
        output.showInitialHands(players)
        players.forEach(::playTurn)
        output.showFinalHands(players)
    }

    private fun createPlayers(names: String): List<Player> = names.split(",").map(String::trim).map(::Player)

    private fun dealInitialCards(player: Player) {
        repeat(INITIAL_CARD_COUNT) { player.receive(deck.draw()) }
    }

    private fun playTurn(player: Player) {
        while (player.canHit() && input.wantsHit(player)) {
            player.receive(deck.draw())
            output.showHand(player)
        }
    }

    private companion object {
        const val INITIAL_CARD_COUNT = 2
    }
}

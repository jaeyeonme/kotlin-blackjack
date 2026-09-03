package blackjack.application

import blackjack.domain.Dealer
import blackjack.domain.Deck
import blackjack.domain.Participant
import blackjack.domain.Player
import blackjack.domain.ProfitReport

class BlackjackGame(
    private val deck: Deck,
) {
    fun start(
        input: GameInput,
        output: GameOutput,
    ) {
        val dealer = Dealer()
        val players = createPlayers(input)
        dealInitialCards(listOf(dealer) + players)
        output.showInitialHands(dealer.cards().first(), players)
        playPlayers(players, input, output)
        playDealer(dealer, output)
        output.showFinalHands(dealer, players)
        output.showProfits(ProfitReport.from(players, dealer))
    }

    private fun createPlayers(input: GameInput): List<Player> =
        input
            .readPlayerNames()
            .split(",")
            .map(String::trim)
            .map { Player(it, input.readBettingAmount(it)) }

    private fun dealInitialCards(participants: List<Participant>) = participants.forEach(::dealTwoCards)

    private fun dealTwoCards(participant: Participant) {
        repeat(INITIAL_CARD_COUNT) { participant.receive(deck.draw()) }
    }

    private fun playPlayers(
        players: List<Player>,
        input: GameInput,
        output: GameOutput,
    ) = players.forEach { playTurn(it, input, output) }

    private fun playTurn(
        player: Player,
        input: GameInput,
        output: GameOutput,
    ) {
        while (player.canHit() && input.wantsHit(player)) {
            player.receive(deck.draw())
            output.showHand(player)
        }
    }

    private fun playDealer(
        dealer: Dealer,
        output: GameOutput,
    ) {
        if (!dealer.shouldDraw()) return
        dealer.receive(deck.draw())
        output.showDealerHit()
    }

    private companion object {
        const val INITIAL_CARD_COUNT = 2
    }
}

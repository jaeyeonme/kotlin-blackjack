package blackjack.application

import blackjack.domain.Card
import blackjack.domain.Dealer
import blackjack.domain.DealerRecord
import blackjack.domain.Player
import blackjack.domain.PlayerResult

interface GameOutput {
    fun showInitialHands(
        dealerCard: Card,
        players: List<Player>,
    )

    fun showHand(player: Player)

    fun showDealerHit()

    fun showFinalHands(
        dealer: Dealer,
        players: List<Player>,
    )

    fun showResults(
        dealerRecord: DealerRecord,
        playerResults: Map<Player, PlayerResult>,
    )
}

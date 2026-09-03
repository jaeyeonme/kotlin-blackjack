package blackjack.view

import blackjack.application.GameOutput
import blackjack.domain.Card
import blackjack.domain.Dealer
import blackjack.domain.Participant
import blackjack.domain.Player
import blackjack.domain.ProfitReport

class ConsoleOutputView : GameOutput {
    override fun showInitialHands(
        dealerCard: Card,
        players: List<Player>,
    ) {
        println("딜러와 ${players.joinToString { it.name }}에게 2장의 카드를 나누었습니다.")
        println("딜러: $dealerCard")
        players.forEach(::showHand)
    }

    override fun showHand(player: Player) {
        println("${player.name}카드: ${player.cards().joinToString()}")
    }

    override fun showDealerHit() {
        println("딜러는 16 이하라 카드 한 장을 더 받았습니다.")
    }

    override fun showFinalHands(
        dealer: Dealer,
        players: List<Player>,
    ) {
        showFinalHand(dealer)
        players.forEach(::showFinalHand)
    }

    override fun showProfits(profitReport: ProfitReport) {
        println("## 최종 수익")
        println("딜러: ${profitReport.dealerProfit()}")
        profitReport.playerProfits().forEach { (player, profit) -> println("${player.name}: $profit") }
    }

    private fun showFinalHand(participant: Participant) {
        println("${participant.name} 카드: ${participant.cards().joinToString()} - 결과: ${participant.score()}")
    }
}

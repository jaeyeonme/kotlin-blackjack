package blackjack.view

import blackjack.application.GameOutput
import blackjack.domain.Player

class ConsoleOutputView : GameOutput {
    override fun showInitialHands(players: List<Player>) {
        println("카드를 나누겠습니다.")
        players.forEach(::showHand)
    }

    override fun showHand(player: Player) {
        println("${player.name}카드: ${player.cards().joinToString()}")
    }

    override fun showFinalHands(players: List<Player>) {
        println("최종 결과")
        players.forEach(::showResult)
    }

    private fun showResult(player: Player) {
        println("${player.name}카드: ${player.cards().joinToString()} - 결과: ${player.score()}")
    }
}

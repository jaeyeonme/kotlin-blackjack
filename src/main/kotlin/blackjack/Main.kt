package blackjack

import blackjack.application.BlackjackGame
import blackjack.domain.Deck
import blackjack.view.ConsoleInputView
import blackjack.view.ConsoleOutputView

fun main() {
    BlackjackGame(ConsoleInputView(), ConsoleOutputView(), Deck.shuffled()).start()
}

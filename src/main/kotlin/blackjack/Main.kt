package blackjack

import blackjack.application.BlackjackGame
import blackjack.domain.Deck
import blackjack.view.ConsoleInputView
import blackjack.view.ConsoleOutputView

fun main() {
    BlackjackGame(Deck.shuffled()).start(ConsoleInputView(), ConsoleOutputView())
}

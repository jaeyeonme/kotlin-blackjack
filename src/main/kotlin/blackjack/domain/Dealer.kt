package blackjack.domain

class Dealer : Participant(NAME) {
    fun shouldDraw(): Boolean = score() <= DRAW_THRESHOLD

    private companion object {
        const val NAME = "딜러"
        const val DRAW_THRESHOLD = 16
    }
}

package blackjack.domain

class Player(
    name: String,
    hand: Hand = Hand(),
) : Participant(name, hand) {
    fun canHit(): Boolean = score() <= BLACKJACK_SCORE

    private companion object {
        const val BLACKJACK_SCORE = 21
    }
}

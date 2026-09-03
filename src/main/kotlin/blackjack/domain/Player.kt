package blackjack.domain

class Player(
    val name: String,
    private val hand: Hand = Hand(),
) {
    fun receive(card: Card) {
        hand.add(card)
    }

    fun cards(): List<Card> = hand.cards()

    fun score(): Int = hand.score()

    fun canHit(): Boolean = score() <= BLACKJACK_SCORE

    private companion object {
        const val BLACKJACK_SCORE = 21
    }
}

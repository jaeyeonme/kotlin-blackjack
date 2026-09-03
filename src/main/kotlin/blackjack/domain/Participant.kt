package blackjack.domain

abstract class Participant(
    val name: String,
    private val hand: Hand = Hand(),
) {
    fun receive(card: Card) {
        hand.add(card)
    }

    fun cards(): List<Card> = hand.cards()

    fun score(): Int = hand.score()

    fun isBlackjack(): Boolean = hand.isBlackjack()

    fun isBust(): Boolean = hand.isBust()
}

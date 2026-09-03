package blackjack.domain

class Player(
    name: String,
    val bettingAmount: Amount,
    hand: Hand = Hand(),
) : Participant(name, hand) {
    fun canHit(): Boolean = !isBust()
}

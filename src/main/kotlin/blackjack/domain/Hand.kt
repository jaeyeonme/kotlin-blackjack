package blackjack.domain

class Hand(
    cards: List<Card> = emptyList(),
) {
    private val cards = cards.toMutableList()

    fun add(card: Card) {
        cards.add(card)
    }

    fun cards(): List<Card> = cards.toList()

    fun score(): Int {
        var score = cards.sumOf(Card::score)
        var aceCount = cards.count { it.rank == Rank.ACE }
        while (score > BLACKJACK_SCORE && aceCount > 0) {
            score -= ACE_ADJUSTMENT
            aceCount--
        }
        return score
    }

    private companion object {
        const val BLACKJACK_SCORE = 21
        const val ACE_ADJUSTMENT = 10
    }
}

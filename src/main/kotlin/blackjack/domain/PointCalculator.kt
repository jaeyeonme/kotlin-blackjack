package blackjack.domain

object PointCalculator {
    const val BLACKJACK_LIMIT = 21

    fun calculatePoint(deck: Deck): Int {
        var sum = deck.sum()
        if (deck.hasAce() && sum <= 11) {
            sum += 10
        }
        return sum
    }

    fun getCardPoints(card: Card): Int {
        return when (card.cardNumber) {
            CardNumber.JACK, CardNumber.QUEEN, CardNumber.KING -> 10
            else -> CardNumber.values().indexOf(card.cardNumber) + 1
        }
    }
}

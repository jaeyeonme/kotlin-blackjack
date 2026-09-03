package blackjack.domain

import kotlin.random.Random

class Deck private constructor(
    cards: List<Card>,
) {
    private val cards = ArrayDeque(cards)

    fun draw(): Card = cards.removeFirstOrNull() ?: error("덱에 카드가 없습니다.")

    fun size(): Int = cards.size

    companion object {
        fun shuffled(random: Random = Random.Default): Deck = Deck(standardCards().shuffled(random))

        fun ordered(cards: List<Card>): Deck = Deck(cards.toList())

        private fun standardCards(): List<Card> = Suit.entries.flatMap(::cardsOf)

        private fun cardsOf(suit: Suit): List<Card> = Rank.entries.map { rank -> Card(rank, suit) }
    }
}

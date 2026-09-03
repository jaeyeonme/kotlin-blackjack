package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardTest {
    @Test
    fun `숫자 카드는 표시된 숫자를 점수로 가진다`() {
        val card = Card(Rank.FIVE, Suit.HEARTS)

        assertThat(card.score).isEqualTo(5)
    }

    @Test
    fun `얼굴 카드는 10점을 가진다`() {
        val cards = listOf(Rank.JACK, Rank.QUEEN, Rank.KING).map { Card(it, Suit.SPADES) }

        assertThat(cards.map(Card::score)).containsExactly(10, 10, 10)
    }

    @Test
    fun `Ace는 기본 점수 11을 가진다`() {
        val card = Card(Rank.ACE, Suit.CLUBS)

        assertThat(card.score).isEqualTo(11)
    }
}

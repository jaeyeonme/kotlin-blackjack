package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import kotlin.random.Random

class DeckTest {
    @Test
    fun `표준 카드 52장을 섞어 게임 덱을 생성한다`() {
        val deck = Deck.shuffled(Random(1))

        val cards = List(52) { deck.draw() }

        assertThat(cards).hasSize(52).doesNotHaveDuplicates()
        assertThat(cards).isNotEqualTo(standardCards())
    }

    @Test
    fun `지정한 순서의 첫 카드를 뽑아 덱에서 제거한다`() {
        val first = card(Rank.ACE)
        val second = card(Rank.KING)
        val deck = Deck.ordered(listOf(first, second))

        assertThat(deck.draw()).isEqualTo(first)
        assertThat(deck.size()).isEqualTo(1)
        assertThat(deck.draw()).isEqualTo(second)
    }

    @Test
    fun `생성에 사용한 목록의 변경은 덱에 영향을 주지 않는다`() {
        val source = mutableListOf(card(Rank.ACE))
        val deck = Deck.ordered(source)

        source.clear()

        assertThat(deck.draw()).isEqualTo(card(Rank.ACE))
    }

    @Test
    fun `빈 덱에서 카드를 뽑으면 예외가 발생한다`() {
        val deck = Deck.ordered(emptyList())

        assertThatThrownBy(deck::draw)
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("덱에 카드가 없습니다.")
    }

    private fun standardCards(): List<Card> = Suit.entries.flatMap(::cardsOf)

    private fun cardsOf(suit: Suit): List<Card> = Rank.entries.map { rank -> Card(rank, suit) }

    private fun card(rank: Rank): Card = Card(rank, Suit.SPADES)
}

package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class HandTest {
    @Test
    fun `Ace가 없으면 카드 점수의 합을 반환한다`() {
        val hand = Hand(listOf(card(Rank.TEN), card(Rank.SEVEN)))

        assertThat(hand.score()).isEqualTo(17)
    }

    @Test
    fun `Ace는 21 이하인 합계 중 가장 큰 값을 만든다`() {
        val hand = Hand(listOf(card(Rank.ACE), card(Rank.NINE), card(Rank.ACE)))

        assertThat(hand.score()).isEqualTo(21)
    }

    @Test
    fun `모든 합계가 21을 넘으면 가장 작은 값을 반환한다`() {
        val hand = Hand(listOf(card(Rank.ACE), card(Rank.KING), card(Rank.KING), card(Rank.TWO)))

        assertThat(hand.score()).isEqualTo(23)
    }

    @Test
    fun `생성에 사용한 카드 목록의 변경은 손패에 영향을 주지 않는다`() {
        val source = mutableListOf(card(Rank.FIVE))
        val hand = Hand(source)

        source.clear()

        assertThat(hand.cards()).containsExactly(card(Rank.FIVE))
    }

    @Test
    fun `조회한 카드 목록은 이후 손패 변경과 분리된다`() {
        val hand = Hand(listOf(card(Rank.FIVE)))
        val snapshot = hand.cards()

        hand.add(card(Rank.SIX))

        assertThat(snapshot).containsExactly(card(Rank.FIVE))
    }

    private fun card(rank: Rank): Card = Card(rank, Suit.SPADES)
}

package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DealerTest {
    @Test
    fun `플레이어와 딜러는 참가자의 카드 동작을 재사용한다`() {
        assertThat(Player("pobi", Amount.from("10000"))).isInstanceOf(Participant::class.java)
        assertThat(Dealer()).isInstanceOf(Participant::class.java)
    }

    @Test
    fun `최초 두 장의 합계가 16이면 카드를 한 장 받아야 한다`() {
        val dealer = dealerWith(Rank.TEN, Rank.SIX)

        assertThat(dealer.shouldDraw()).isTrue()
    }

    @Test
    fun `최초 두 장의 합계가 17이면 카드를 받을 수 없다`() {
        val dealer = dealerWith(Rank.TEN, Rank.SEVEN)

        assertThat(dealer.shouldDraw()).isFalse()
    }

    private fun dealerWith(vararg ranks: Rank): Dealer {
        val dealer = Dealer()
        ranks.forEach { dealer.receive(Card(it, Suit.SPADES)) }
        return dealer
    }
}

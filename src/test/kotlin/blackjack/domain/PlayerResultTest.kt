package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlayerResultTest {
    @Test
    fun `플레이어가 21을 초과하면 딜러 점수와 관계없이 패배한다`() {
        assertThat(PlayerResult.from(playerScore = 22, dealerScore = 23)).isEqualTo(PlayerResult.LOSE)
    }

    @Test
    fun `플레이어가 21 이하이고 딜러가 21을 초과하면 승리한다`() {
        assertThat(PlayerResult.from(playerScore = 20, dealerScore = 22)).isEqualTo(PlayerResult.WIN)
    }

    @Test
    fun `양쪽 모두 21 이하이면 점수가 높은 참가자가 승리한다`() {
        assertThat(PlayerResult.from(playerScore = 20, dealerScore = 19)).isEqualTo(PlayerResult.WIN)
        assertThat(PlayerResult.from(playerScore = 18, dealerScore = 19)).isEqualTo(PlayerResult.LOSE)
    }

    @Test
    fun `양쪽 점수가 같으면 무승부다`() {
        assertThat(PlayerResult.from(playerScore = 19, dealerScore = 19)).isEqualTo(PlayerResult.DRAW)
    }

    @Test
    fun `플레이어 결과를 반대로 반영해 딜러 전적을 집계한다`() {
        val record = DealerRecord(listOf(PlayerResult.WIN, PlayerResult.LOSE, PlayerResult.DRAW, PlayerResult.LOSE))

        assertThat(record.wins()).isEqualTo(2)
        assertThat(record.draws()).isEqualTo(1)
        assertThat(record.losses()).isEqualTo(1)
    }
}

package blackjack.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AmountTest {
    @Test
    fun `문자열 금액을 도메인 값으로 생성한다`() {
        val amount = Amount.from("10000")

        assertThat(amount.value).isEqualByComparingTo("10000")
    }
}

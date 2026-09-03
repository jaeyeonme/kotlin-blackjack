package blackjack.domain

enum class PlayerResult {
    WIN,
    DRAW,
    LOSE,
    ;

    companion object {
        fun from(
            playerScore: Int,
            dealerScore: Int,
        ): PlayerResult {
            if (playerScore > BLACKJACK_SCORE) return LOSE
            if (dealerScore > BLACKJACK_SCORE) return WIN
            return when {
                playerScore > dealerScore -> WIN
                playerScore < dealerScore -> LOSE
                else -> DRAW
            }
        }

        private const val BLACKJACK_SCORE = 21
    }
}

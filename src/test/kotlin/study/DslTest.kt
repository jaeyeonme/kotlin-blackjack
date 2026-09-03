package study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class DslTest {
    @ParameterizedTest
    @ValueSource(strings = ["홍길동", "조재연"])
    fun `이름을 선언하면 해당 이름을 가진 사람을 생성한다`(name: String) {
        val person = introduce {
            name(name)
        }

        assertThat(person.name).isEqualTo(name)
    }

    @Test
    fun `회사를 선언하면 해당 회사가 저장된다`() {
        val person = introduce {
            name("조재연")
            company("Google")
        }

        assertThat(person.company).isEqualTo("Google")
    }

    @Test
    fun `회사를 선언하지 않으면 회사는 null이다`() {
        val person = introduce {
            name("조재연")
        }

        assertThat(person.company).isNull()
    }

    @Test
    fun `소프트 스킬과 하드 스킬은 선언한 순서대로 저장된다`() {
        val person = introduce {
            name("조재연")
            skills {
                soft("A passion for problem solving")
                soft("Good communication skills")
                hard("Kotlin")
            }
        }

        assertThat(person.skills).containsExactly(
            "A passion for problem solving",
            "Good communication skills",
            "Kotlin",
        )
    }

    @Test
    fun `언어와 숙련도는 중위 호출로 선언한다`() {
        val person = introduce {
            name("조재연")
            languages {
                "Korean" level 5
                "English" level 3
            }
        }

        assertThat(person.languages).containsExactlyEntriesOf(
            mapOf(
                "Korean" to 5,
                "English" to 3,
            ),
        )
    }
}

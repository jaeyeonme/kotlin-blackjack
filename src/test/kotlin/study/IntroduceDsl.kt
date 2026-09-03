package study

fun introduce(block: PersonBuilder.() -> Unit): Person =
    PersonBuilder()
        .apply(block)
        .build()

class PersonBuilder {
    private lateinit var name: String
    private var company: String? = null
    private val skills = SkillBuilder()
    private val languages = LanguageBuilder()

    fun name(value: String) {
        name = value
    }

    fun company(value: String) {
        company = value
    }

    fun skills(block: SkillBuilder.() -> Unit) {
        skills.apply(block)
    }

    fun languages(block: LanguageBuilder.() -> Unit) {
        languages.apply(block)
    }

    fun build(): Person = Person(name, company, skills.build(), languages.build())
}

class SkillBuilder {
    private val skills = mutableListOf<String>()

    fun soft(skill: String) {
        skills.add(skill)
    }

    fun hard(skill: String) {
        skills.add(skill)
    }

    fun build(): List<String> = skills.toList()
}

class LanguageBuilder {
    private val languages = mutableMapOf<String, Int>()

    infix fun String.level(value: Int) {
        languages[this] = value
    }

    fun build(): Map<String, Int> = languages.toMap()
}

data class Person(
    val name: String,
    val company: String?,
    val skills: List<String>,
    val languages: Map<String, Int>,
)

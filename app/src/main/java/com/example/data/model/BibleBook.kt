package com.example.data.model

data class BibleBook(
    val name: String,
    val testament: String, // "OT" or "NT"
    val chapters: Int,
    val abbreviation: String,
    val category: String,
    val id: String = name.lowercase().replace(" ", "")
)

object BibleCatalog {
    val OLD_TESTAMENT = listOf(
        BibleBook("Genesis", "OT", 50, "Gen", "Law"),
        BibleBook("Exodus", "OT", 40, "Exod", "Law"),
        BibleBook("Leviticus", "OT", 27, "Lev", "Law"),
        BibleBook("Numbers", "OT", 36, "Num", "Law"),
        BibleBook("Deuteronomy", "OT", 34, "Deut", "Law"),
        BibleBook("Joshua", "OT", 24, "Josh", "History"),
        BibleBook("Judges", "OT", 21, "Judg", "History"),
        BibleBook("Ruth", "OT", 4, "Ruth", "History"),
        BibleBook("1 Samuel", "OT", 31, "1Sam", "History"),
        BibleBook("2 Samuel", "OT", 24, "2Sam", "History"),
        BibleBook("1 Kings", "OT", 22, "1Kgs", "History"),
        BibleBook("2 Kings", "OT", 25, "2Kgs", "History"),
        BibleBook("1 Chronicles", "OT", 29, "1Chr", "History"),
        BibleBook("2 Chronicles", "OT", 36, "2Chr", "History"),
        BibleBook("Ezra", "OT", 10, "Ezra", "History"),
        BibleBook("Nehemiah", "OT", 13, "Neh", "History"),
        BibleBook("Esther", "OT", 10, "Esth", "History"),
        BibleBook("Job", "OT", 42, "Job", "Poetry & Wisdom"),
        BibleBook("Psalms", "OT", 150, "Ps", "Poetry & Wisdom"),
        BibleBook("Proverbs", "OT", 31, "Prov", "Poetry & Wisdom"),
        BibleBook("Ecclesiastes", "OT", 12, "Eccl", "Poetry & Wisdom"),
        BibleBook("Song of Solomon", "OT", 8, "Song", "Poetry & Wisdom"),
        BibleBook("Isaiah", "OT", 66, "Isa", "Major Prophets"),
        BibleBook("Jeremiah", "OT", 52, "Jer", "Major Prophets"),
        BibleBook("Lamentations", "OT", 5, "Lam", "Major Prophets"),
        BibleBook("Ezekiel", "OT", 48, "Ezek", "Major Prophets"),
        BibleBook("Daniel", "OT", 12, "Dan", "Major Prophets"),
        BibleBook("Hosea", "OT", 14, "Hos", "Minor Prophets"),
        BibleBook("Joel", "OT", 3, "Joel", "Minor Prophets"),
        BibleBook("Amos", "OT", 9, "Amos", "Minor Prophets"),
        BibleBook("Obadiah", "OT", 1, "Obad", "Minor Prophets"),
        BibleBook("Jonah", "OT", 4, "Jonah", "Minor Prophets"),
        BibleBook("Micah", "OT", 7, "Mic", "Minor Prophets"),
        BibleBook("Nahum", "OT", 3, "Nah", "Minor Prophets"),
        BibleBook("Habakkuk", "OT", 3, "Hab", "Minor Prophets"),
        BibleBook("Zephaniah", "OT", 3, "Zeph", "Minor Prophets"),
        BibleBook("Haggai", "OT", 2, "Hag", "Minor Prophets"),
        BibleBook("Zechariah", "OT", 14, "Zech", "Minor Prophets"),
        BibleBook("Malachi", "OT", 4, "Mal", "Minor Prophets")
    )

    val NEW_TESTAMENT = listOf(
        BibleBook("Matthew", "NT", 28, "Matt", "Gospels"),
        BibleBook("Mark", "NT", 16, "Mark", "Gospels"),
        BibleBook("Luke", "NT", 24, "Luke", "Gospels"),
        BibleBook("John", "NT", 21, "John", "Gospels"),
        BibleBook("Acts", "NT", 28, "Acts", "Church History"),
        BibleBook("Romans", "NT", 16, "Rom", "Paul's Letters"),
        BibleBook("1 Corinthians", "NT", 16, "1Cor", "Paul's Letters"),
        BibleBook("2 Corinthians", "NT", 13, "2Cor", "Paul's Letters"),
        BibleBook("Galatians", "NT", 6, "Gal", "Paul's Letters"),
        BibleBook("Ephesians", "NT", 6, "Eph", "Paul's Letters"),
        BibleBook("Philippians", "NT", 4, "Phil", "Paul's Letters"),
        BibleBook("Colossians", "NT", 4, "Col", "Paul's Letters"),
        BibleBook("1 Thessalonians", "NT", 5, "1Thess", "Paul's Letters"),
        BibleBook("2 Thessalonians", "NT", 3, "2Thess", "Paul's Letters"),
        BibleBook("1 Timothy", "NT", 6, "1Tim", "Paul's Letters"),
        BibleBook("2 Timothy", "NT", 4, "2Tim", "Paul's Letters"),
        BibleBook("Titus", "NT", 3, "Titus", "Paul's Letters"),
        BibleBook("Philemon", "NT", 1, "Phlm", "Paul's Letters"),
        BibleBook("Hebrews", "NT", 13, "Heb", "General Letters"),
        BibleBook("James", "NT", 5, "Jas", "General Letters"),
        BibleBook("1 Peter", "NT", 5, "1Pet", "General Letters"),
        BibleBook("2 Peter", "NT", 3, "2Pet", "General Letters"),
        BibleBook("1 John", "NT", 5, "1John", "General Letters"),
        BibleBook("2 John", "NT", 1, "2John", "General Letters"),
        BibleBook("3 John", "NT", 1, "3John", "General Letters"),
        BibleBook("Jude", "NT", 1, "Jude", "General Letters"),
        BibleBook("Revelation", "NT", 22, "Rev", "Prophecy")
    )

    val ALL_BOOKS = OLD_TESTAMENT + NEW_TESTAMENT

    fun findBook(nameOrAbbr: String): BibleBook? {
        val cleaned = nameOrAbbr.trim().lowercase()
        return ALL_BOOKS.firstOrNull {
            it.name.lowercase() == cleaned ||
            it.abbreviation.lowercase() == cleaned ||
            it.name.lowercase().startsWith(cleaned)
        }
    }
}

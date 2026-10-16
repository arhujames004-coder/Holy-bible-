package com.example.data.local

data class VerseExplanation(
    val reference: String,
    val summary: String,
    val historicalContext: String,
    val theologicalInsight: String,
    val lifeApplication: String,
    val crossReferences: List<String>
)

object VerseCommentaries {

    fun explain(book: String, chapter: Int, verse: Int, text: String): VerseExplanation {
        val ref = "$book $chapter:$verse"

        return when {
            book == "John" && chapter == 3 && verse == 16 -> VerseExplanation(
                reference = ref,
                summary = "The central theme of the entire Gospel: God's sacrificial love for humanity through Christ, offering eternal life through faith.",
                historicalContext = "Jesus is speaking privately at night with Nicodemus, a prominent Pharisee and member of the Sanhedrin seeking truth outside public controversy.",
                theologicalInsight = "The Greek word 'agape' denotes unconditional, deliberate, self-giving love. 'Whosoever' declares the universal availability of grace to all people regardless of background.",
                lifeApplication = "Rest in the certainty that you are deeply loved by God. Accept salvation not as earned wages, but as a priceless gift received through simple trust.",
                crossReferences = listOf("Romans 5:8", "1 John 4:9-10", "Ephesians 2:4-5")
            )
            book == "Psalms" && chapter == 23 && verse == 1 -> VerseExplanation(
                reference = ref,
                summary = "An intimate confession of total security under the protective leadership and provision of God.",
                historicalContext = "Composed by King David, who was himself an experienced shepherd in the Judean wilderness before becoming king of Israel.",
                theologicalInsight = "God is addressed as 'Yahweh Roi' (The Lord My Shepherd). In ancient sheep husbandry, sheep were completely reliant upon the shepherd for clean water, grazing, and defense against predators.",
                lifeApplication = "Surrender anxiety over future lack. When the Almighty is your Shepherd, what is truly necessary for your soul will never be withheld.",
                crossReferences = listOf("John 10:11", "Philippians 4:19", "Isaiah 40:11")
            )
            book == "Romans" && chapter == 8 && verse == 28 -> VerseExplanation(
                reference = ref,
                summary = "A declaration of God's overarching sovereignty weaving every circumstance into an ultimate good for His children.",
                historicalContext = "Paul writes to believers facing hardship and systemic Roman hostility, encouraging them with the unshakeable destiny of God's plan.",
                theologicalInsight = "Notice the text does not say all things are good in themselves, but that God synergizes ('syn-ergeo') all events—even painful ones—toward redemptive conformity to Christ.",
                lifeApplication = "When facing trials you cannot understand, trust the Weaver of history. God is not finished writing the chapter of your life.",
                crossReferences = listOf("Genesis 50:20", "Jeremiah 29:11", "2 Corinthians 4:17")
            )
            book == "Philippians" && chapter == 4 && verse == 13 -> VerseExplanation(
                reference = ref,
                summary = "Supernatural contentment and endurance through the indwelling presence of Christ.",
                historicalContext = "Paul wrote this epistle while chained under house arrest in Rome, having experienced both luxury and severe deprivation.",
                theologicalInsight = "The passage refers to enduring all circumstances—plenty or hunger, freedom or imprisonment—sustained by Christ's empowerment.",
                lifeApplication = "Call upon Christ's strength not just for personal ambition, but for endurance, grace, patience, and faithfulness in every valley.",
                crossReferences = listOf("2 Corinthians 12:9", "Ephesians 3:16", "Colossians 1:11")
            )
            book == "Proverbs" && chapter == 3 && verse == 5 -> VerseExplanation(
                reference = ref,
                summary = "A foundational call to wholehearted trust in divine wisdom over finite human rationalization.",
                historicalContext = "Solomonic wisdom literature instructing young learners how to navigate moral decision-making.",
                theologicalInsight = "'Lean not' draws the picture of leaning heavily on a cracked cane that might splinter. Human understanding is limited; God's perspective is boundless.",
                lifeApplication = "Bring major decisions to prayer. Before choosing a path based solely on earthly calculus, ask for God's wisdom and guidance.",
                crossReferences = listOf("Jeremiah 17:7", "Psalm 37:5", "James 1:5")
            )
            else -> VerseExplanation(
                reference = ref,
                summary = "A powerful verse from the book of $book highlighting God's eternal truth, covenant faithfulness, and righteous guidance.",
                historicalContext = "Part of $book chapter $chapter, recorded in canonical scripture to instruct, comfort, and guide the people of God across generations.",
                theologicalInsight = "Scripture reveals God's character and His ongoing redemptive work. In this passage, God speaks into human history with enduring authority.",
                lifeApplication = "Meditate on these words: \"$text\". Consider how this truth speaks directly to your present decisions, heart attitudes, and daily walk with God.",
                crossReferences = listOf("2 Timothy 3:16-17", "Psalm 119:105", "Hebrews 4:12")
            )
        }
    }
}

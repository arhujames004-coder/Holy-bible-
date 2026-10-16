package com.example.data.model

data class ReadingPlanDay(
    val dayNumber: Int,
    val title: String,
    val book: String,
    val chapter: Int,
    val description: String
)

data class ReadingPlan(
    val id: String,
    val title: String,
    val subtitle: String,
    val totalDays: Int,
    val category: String,
    val days: List<ReadingPlanDay>
)

object ReadingPlansCatalog {
    val PLANS = listOf(
        ReadingPlan(
            id = "gospels_30",
            title = "The Four Gospels",
            subtitle = "30-Day Walk with the Life and Words of Jesus Christ",
            totalDays = 30,
            category = "Gospels",
            days = listOf(
                ReadingPlanDay(1, "The Word Made Flesh", "John", 1, "The eternal divine nature of Christ and John's testimony."),
                ReadingPlanDay(2, "The Birth of the Messiah", "Matthew", 1, "The genealogy and angelic announcement to Joseph."),
                ReadingPlanDay(3, "The Sermon on the Mount: Beatitudes", "Matthew", 5, "Blessings for the humble and light of the world."),
                ReadingPlanDay(4, "The Sermon on the Mount: The Lord's Prayer", "Matthew", 6, "Authentic prayer, fasting, and seeking God's kingdom."),
                ReadingPlanDay(5, "The Sermon on the Mount: The Solid Rock", "Matthew", 7, "Building life on the rock of Christ's teachings."),
                ReadingPlanDay(6, "Authority Over the Storm", "Mark", 4, "Jesus calms the tempest and teaches in parables."),
                ReadingPlanDay(7, "Feeding the Five Thousand", "Mark", 6, "Miraculous compassion and walking on the water."),
                ReadingPlanDay(8, "The Transfiguration", "Matthew", 17, "Christ's radiant glory revealed upon the mount."),
                ReadingPlanDay(9, "The Good Samaritan", "Luke", 10, "Neighborly love that transcends prejudice."),
                ReadingPlanDay(10, "The Prodigal Son & The Loving Father", "Luke", 15, "Joy in heaven over the repentant sinner."),
                ReadingPlanDay(11, "Born of the Spirit", "John", 3, "Jesus explains rebirth and eternal life to Nicodemus."),
                ReadingPlanDay(12, "Living Water at the Well", "John", 4, "Encountering the Samaritan woman with truth and grace."),
                ReadingPlanDay(13, "The Bread of Life", "John", 6, "Christ the true sustenance that satisfies forever."),
                ReadingPlanDay(14, "The Good Shepherd", "John", 10, "The shepherd who lays down His life for the sheep."),
                ReadingPlanDay(15, "The Resurrection and the Life", "John", 11, "Jesus calls Lazarus out from the tomb."),
                ReadingPlanDay(16, "The Triumphal Entry", "Luke", 19, "Christ enters Jerusalem weeping over the holy city."),
                ReadingPlanDay(17, "The Greatest Commandments", "Mark", 12, "Loving God with all heart, soul, mind, and strength."),
                ReadingPlanDay(18, "Washing the Disciples' Feet", "John", 13, "Servant leadership and the new commandment of love."),
                ReadingPlanDay(19, "The Way, The Truth, and The Life", "John", 14, "The promise of peace and the Holy Spirit."),
                ReadingPlanDay(20, "The True Vine", "John", 15, "Abiding in Christ to bear lasting fruit."),
                ReadingPlanDay(21, "The High Priestly Prayer", "John", 17, "Christ prays for the unity and protection of believers."),
                ReadingPlanDay(22, "Gethsemane and Surrender", "Matthew", 26, "The cup of suffering and submission to the Father."),
                ReadingPlanDay(23, "The Crucifixion of Christ", "Luke", 23, "'Father, forgive them; for they know not what they do.'"),
                ReadingPlanDay(24, "It is Finished", "John", 19, "The completion of the redemptive sacrifice on Calvary."),
                ReadingPlanDay(25, "He is Risen!", "Matthew", 28, "The empty tomb and the Great Commission."),
                ReadingPlanDay(26, "The Road to Emmaus", "Luke", 24, "Hearts burning as scripture is opened by the risen Lord."),
                ReadingPlanDay(27, "Peace Be Unto You", "John", 20, "Jesus appears to Mary Magdalene and doubting Thomas."),
                ReadingPlanDay(28, "Restoration of Peter", "John", 21, "'Feed my sheep' - breakfast on the shore of Galilee."),
                ReadingPlanDay(29, "The Ascension of Christ", "Mark", 16, "Christ ascended to the right hand of majesty."),
                ReadingPlanDay(30, "The Continual Praise", "Luke", 24, "Returning to Jerusalem with great joy.")
            )
        ),
        ReadingPlan(
            id = "wisdom_30",
            title = "Psalms & Proverbs Wisdom",
            subtitle = "30 Days of Divine Guidance, Solace, and Life Principles",
            totalDays = 30,
            category = "Wisdom",
            days = listOf(
                ReadingPlanDay(1, "The Two Pathways", "Psalms", 1, "The blessed life rooted like a tree beside waters."),
                ReadingPlanDay(2, "The Lord is My Shepherd", "Psalms", 23, "No lack in the valley of the shadow of death."),
                ReadingPlanDay(3, "Wholehearted Trust", "Proverbs", 3, "Trust in the Lord with all thine heart."),
                ReadingPlanDay(4, "The Glory of God in Creation", "Psalms", 19, "The heavens declare the glory of God."),
                ReadingPlanDay(5, "Acquiring True Wisdom", "Proverbs", 4, "Wisdom is the principal thing; therefore get wisdom."),
                ReadingPlanDay(6, "The Secret Place of Protection", "Psalms", 91, "Dwelling under the shadow of the Almighty."),
                ReadingPlanDay(7, "Seven Things the Lord Hateth", "Proverbs", 6, "Guarding against pride, deceit, and discord."),
                ReadingPlanDay(8, "A Clean Heart", "Psalms", 51, "David's cry of deep repentance and renewal."),
                ReadingPlanDay(9, "The Voice of Wisdom", "Proverbs", 8, "Wisdom speaking before the foundations of the earth."),
                ReadingPlanDay(10, "God Our Refuge and Strength", "Psalms", 46, "Be still, and know that I am God."),
                ReadingPlanDay(11, "The Wise Tongue", "Proverbs", 15, "A soft answer turneth away wrath."),
                ReadingPlanDay(12, "My Help Cometh from the Lord", "Psalms", 121, "The Keeper of Israel neither slumbers nor sleeps."),
                ReadingPlanDay(13, "Humility and Honor", "Proverbs", 16, "Pride goeth before destruction, and a haughty spirit."),
                ReadingPlanDay(14, "Praise the Lord, O My Soul", "Psalms", 103, "Forget not all His benefits: who forgiveth all iniquities."),
                ReadingPlanDay(15, "A Friend at All Times", "Proverbs", 17, "A merry heart doeth good like a medicine."),
                ReadingPlanDay(16, "The Lamp to My Feet", "Psalms", 119, "Delight in the holy statutes and guidance of God."),
                ReadingPlanDay(17, "The Strong Tower", "Proverbs", 18, "The name of the Lord is a strong tower; the righteous run."),
                ReadingPlanDay(18, "Fearfully and Wonderfully Made", "Psalms", 139, "God's omnipresence and loving knowledge of your soul."),
                ReadingPlanDay(19, "Righteous Living", "Proverbs", 21, "Doing justice and judgment is more acceptable than sacrifice."),
                ReadingPlanDay(20, "A Song of Ascent", "Psalms", 126, "They that sow in tears shall reap in joy."),
                ReadingPlanDay(21, "A Good Name", "Proverbs", 22, "A good name is rather to be chosen than great riches."),
                ReadingPlanDay(22, "Praise with Every Instrument", "Psalms", 150, "Let every thing that hath breath praise the Lord."),
                ReadingPlanDay(23, "Kindness to Enemies", "Proverbs", 25, "If thine enemy be hungry, give him bread to eat."),
                ReadingPlanDay(24, "The Lord is My Light", "Psalms", 27, "Whom shall I fear? The Lord is the strength of my life."),
                ReadingPlanDay(25, "Trusting Tomorrow", "Proverbs", 27, "Boast not thyself of to morrow; iron sharpeneth iron."),
                ReadingPlanDay(26, "Waiting on God", "Psalms", 40, "He brought me up also out of an horrible pit."),
                ReadingPlanDay(27, "Boldness of the Righteous", "Proverbs", 28, "The wicked flee when no man pursueth: but the righteous are bold."),
                ReadingPlanDay(28, "The Great Hallelujah", "Psalms", 146, "Praise the Lord while I have my being."),
                ReadingPlanDay(29, "Words of King Lemuel", "Proverbs", 31, "Opening the mouth for the dumb and the virtuous character."),
                ReadingPlanDay(30, "Crown of Thanksgiving", "Psalms", 100, "Enter into his gates with thanksgiving, and into his courts with praise.")
            )
        ),
        ReadingPlan(
            id = "faith_14",
            title = "Foundations of Faith",
            subtitle = "14 Days to Deepen Your Spiritual Roots in Sacred Scripture",
            totalDays = 14,
            category = "Foundations",
            days = listOf(
                ReadingPlanDay(1, "The Creation of All Things", "Genesis", 1, "God speaks light into darkness and forms mankind."),
                ReadingPlanDay(2, "The Covenant of Faith", "Genesis", 15, "Abraham believes the Lord, counted unto him for righteousness."),
                ReadingPlanDay(3, "The Ten Commandments", "Exodus", 20, "Moral law revealed at Mount Sinai."),
                ReadingPlanDay(4, "Be Strong and Courageous", "Joshua", 1, "Meditating day and night upon the Word of God."),
                ReadingPlanDay(5, "The Suffering Servant", "Isaiah", 53, "Prophetic depiction of Christ bearing our iniquities."),
                ReadingPlanDay(6, "The Birth of the Savior", "Luke", 2, "Glory to God in the highest, and on earth peace."),
                ReadingPlanDay(7, "The Power of the Cross", "John", 3, "God so loved the world that He gave His only Son."),
                ReadingPlanDay(8, "Justified by Faith", "Romans", 5, "Peace with God through our Lord Jesus Christ."),
                ReadingPlanDay(9, "No Condemnation", "Romans", 8, "More than conquerors through Him that loved us."),
                ReadingPlanDay(10, "The Supremacy of Love", "1 Corinthians", 13, "Faith, hope, and charity, the greatest of which is love."),
                ReadingPlanDay(11, "Saved by Grace", "Ephesians", 2, "Not of works, lest any man should boast."),
                ReadingPlanDay(12, "Rejoicing in the Lord Always", "Philippians", 4, "The peace of God which passeth all understanding."),
                ReadingPlanDay(13, "The Hall of Faith", "Hebrews", 11, "Faith is the substance of things hoped for."),
                ReadingPlanDay(14, "A New Heaven and New Earth", "Revelation", 21, "God will wipe away every tear and make all things new.")
            )
        )
    )
}

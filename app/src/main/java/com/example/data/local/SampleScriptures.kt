package com.example.data.local

import com.example.data.model.Verse

object SampleScriptures {

    val VERSE_OF_THE_DAY_LIST = listOf(
        Verse("John", 3, 16, "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life.", "KJV"),
        Verse("Psalms", 23, 1, "The LORD is my shepherd; I shall not want.", "KJV"),
        Verse("Romans", 8, 28, "And we know that all things work together for good to them that love God, to them who are the called according to his purpose.", "KJV"),
        Verse("Proverbs", 3, 5, "Trust in the LORD with all thine heart; and lean not unto thine own understanding.", "KJV"),
        Verse("Philippians", 4, 13, "I can do all things through Christ which strengtheneth me.", "KJV"),
        Verse("Isaiah", 40, 31, "But they that wait upon the LORD shall renew their strength; they shall mount up with wings as eagles; they shall run, and not be weary; and they shall walk, and not faint.", "KJV"),
        Verse("Jeremiah", 29, 11, "For I know the thoughts that I think toward you, saith the LORD, thoughts of peace, and not of evil, to give you an expected end.", "KJV"),
        Verse("Matthew", 6, 33, "But seek ye first the kingdom of God, and his righteousness; and all these things shall be added unto you.", "KJV"),
        Verse("Joshua", 1, 9, "Have not I commanded thee? Be strong and of a good courage; be not afraid, neither be thou dismayed: for the LORD thy God is with thee whithersoever thou goest.", "KJV"),
        Verse("1 Corinthians", 13, 13, "And now abideth faith, hope, charity, these three; but the greatest of these is charity.", "KJV"),
        Verse("Psalms", 46, 1, "God is our refuge and strength, a very present help in trouble.", "KJV"),
        Verse("Psalms", 119, 105, "Thy word is a lamp unto my feet, and a light unto my path.", "KJV"),
        Verse("Hebrews", 11, 1, "Now faith is the substance of things hoped for, the evidence of things not seen.", "KJV"),
        Verse("James", 1, 2, "My brethren, count it all joy when ye fall into divers temptations;", "KJV"),
        Verse("Revelation", 21, 4, "And God shall wipe away all tears from their eyes; and there shall be no more death, neither sorrow, nor crying, neither shall there be any more pain: for the former things are passed away.", "KJV")
    )

    // Preloaded KJV Chapters
    private val PRELOADED_KJV = mapOf(
        "Genesis 1" to listOf(
            "In the beginning God created the heaven and the earth.",
            "And the earth was without form, and void; and darkness was upon the face of the deep. And the Spirit of God moved upon the face of the waters.",
            "And God said, Let there be light: and there was light.",
            "And God saw the light, that it was good: and God divided the light from the darkness.",
            "And God called the light Day, and the darkness he called Night. And the evening and the morning were the first day.",
            "And God said, Let there be a firmament in the midst of the waters, and let it divide the waters from the waters.",
            "And God made the firmament, and divided the waters which were under the firmament from the waters which were above the firmament: and it was so.",
            "And God called the firmament Heaven. And the evening and the morning were the second day.",
            "And God said, Let the waters under the heaven be gathered together unto one place, and let the dry land appear: and it was so.",
            "And God called the dry land Earth; and the gathering together of the waters called he Seas: and God saw that it was good.",
            "And God said, Let the earth bring forth grass, the herb yielding seed, and the fruit tree yielding fruit after his kind, whose seed is in itself, upon the earth: and it was so.",
            "And the earth brought forth grass, and herb yielding seed after his kind, and the tree yielding fruit, whose seed was in itself, after his kind: and God saw that it was good.",
            "And the evening and the morning were the third day.",
            "And God said, Let there be lights in the firmament of the heaven to divide the day from the night; and let them be for signs, and for seasons, and for days, and years:",
            "And let them be for lights in the firmament of the heaven to give light upon the earth: and it was so.",
            "And God made two great lights; the greater light to rule the day, and the lesser light to rule the night: he made the stars also.",
            "And God set them in the firmament of the heaven to give light upon the earth,",
            "And to rule over the day and over the night, and to divide the light from the darkness: and God saw that it was good.",
            "And the evening and the morning were the fourth day.",
            "And God said, Let the waters bring forth abundantly the moving creature that hath life, and fowl that may fly above the earth in the open firmament of heaven.",
            "And God created great whales, and every living creature that moveth, which the waters brought forth abundantly, after their kind, and every winged fowl after his kind: and God saw that it was good.",
            "And God blessed them, saying, Be fruitful, and multiply, and fill the waters in the seas, and let fowl multiply in the earth.",
            "And the evening and the morning were the fifth day.",
            "And God said, Let the earth bring forth the living creature after his kind, cattle, and creeping thing, and beast of the earth after his kind: and it was so.",
            "And God made the beast of the earth after his kind, and cattle after their kind, and every thing that creepeth upon the earth after his kind: and God saw that it was good.",
            "And God said, Let us make man in our image, after our likeness: and let them have dominion over the fish of the sea, and over the fowl of the air, and over the cattle, and over all the earth, and over every creeping thing that creepeth upon the earth.",
            "So God created man in his own image, in the image of God created he him; male and female created he them.",
            "And God blessed them, and God said unto them, Be fruitful, and multiply, and replenish the earth, and subdue it: and have dominion over the fish of the sea, and over the fowl of the air, and over every living thing that moveth upon the earth.",
            "And God said, Behold, I have given you every herb bearing seed, which is upon the face of all the earth, and every tree, in the which is the fruit of a tree yielding seed; to you it shall be for meat.",
            "And to every beast of the earth, and to every fowl of the air, and to every thing that creepeth upon the earth, wherein there is life, I have given every green herb for meat: and it was so.",
            "And God saw every thing that he had made, and, behold, it was very good. And the evening and the morning were the sixth day."
        ),
        "Psalms 23" to listOf(
            "The LORD is my shepherd; I shall not want.",
            "He maketh me to lie down in green pastures: he leadeth me beside the still waters.",
            "He restoreth my soul: he leadeth me in the paths of righteousness for his name's sake.",
            "Yea, though I walk through the valley of the shadow of death, I will fear no evil: for thou art with me; thy rod and thy staff they comfort me.",
            "Thou preparest a table before me in the presence of mine enemies: thou anointest my head with oil; my cup runneth over.",
            "Surely goodness and mercy shall follow me all the days of my life: and I will dwell in the house of the LORD for ever."
        ),
        "John 3" to listOf(
            "There was a man of the Pharisees, named Nicodemus, a ruler of the Jews:",
            "The same came to Jesus by night, and said unto him, Rabbi, we know that thou art a teacher come from God: for no man can do these miracles that thou doest, except God be with him.",
            "Jesus answered and said unto him, Verily, verily, I say unto thee, Except a man be born again, he cannot see the kingdom of God.",
            "Nicodemus saith unto him, How can a man be born when he is old? can he enter the second time into his mother's womb, and be born?",
            "Jesus answered, Verily, verily, I say unto thee, Except a man be born of water and of the Spirit, he cannot enter into the kingdom of God.",
            "That which is born of the flesh is flesh; and that which is born of the Spirit is spirit.",
            "Marvel not that I said unto thee, Ye must be born again.",
            "The wind bloweth where it listeth, and thou hearest the sound thereof, but canst not tell whence it cometh, and whither it goeth: so is every one that is born of the Spirit.",
            "Nicodemus answered and said unto him, How can these things be?",
            "Jesus answered and said unto him, Art thou a master of Israel, and knowest not these things?",
            "Verily, verily, I say unto thee, We speak that we do know, and testify that we have seen; and ye receive not our witness.",
            "If I have told you earthly things, and ye believe not, how shall ye believe, if I tell you of heavenly things?",
            "And no man hath ascended up to heaven, but he that came down from heaven, even the Son of man which is in heaven.",
            "And as Moses lifted up the serpent in the wilderness, even so must the Son of man be lifted up:",
            "That whosoever believeth in him should not perish, but have eternal life.",
            "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life.",
            "For God sent not his Son into the world to condemn the world; but that the world through him might be saved.",
            "He that believeth on him is not condemned: but he that believeth not is condemned already, because he hath not believed in the name of the only begotten Son of God.",
            "And this is the condemnation, that light is come into the world, and men loved darkness rather than light, because their deeds were evil.",
            "For every one that doeth evil hateth the light, neither cometh to the light, lest his deeds should be reproved.",
            "But he that doeth truth cometh to the light, that his deeds may be made manifest, that they are wrought in God."
        ),
        "Matthew 5" to listOf(
            "And seeing the multitudes, he went up into a mountain: and when he was set, his disciples came unto him:",
            "And he opened his mouth, and taught them, saying,",
            "Blessed are the poor in spirit: for theirs is the kingdom of heaven.",
            "Blessed are they that mourn: for they shall be comforted.",
            "Blessed are the meek: for they shall inherit the earth.",
            "Blessed are they which do hunger and thirst after righteousness: for they shall be filled.",
            "Blessed are the merciful: for they shall obtain mercy.",
            "Blessed are the pure in heart: for they shall see God.",
            "Blessed are the peacemakers: for they shall be called the children of God.",
            "Blessed are they which are persecuted for righteousness' sake: for theirs is the kingdom of heaven.",
            "Blessed are ye, when men shall revile you, and persecute you, and shall say all manner of evil against you falsely, for my sake.",
            "Rejoice, and be exceeding glad: for great is your reward in heaven: for so persecuted they the prophets which were before you.",
            "Ye are the salt of the earth: but if the salt have lost his savour, wherewith shall it be salted? it is thenceforth good for nothing, but to be cast out, and to be trodden under foot of men.",
            "Ye are the light of the world. A city that is set on an hill cannot be hid.",
            "Neither do men light a candle, and put it under a bushel, but on a candlestick; and it giveth light unto all that are in the house.",
            "Let your light so shine before men, that they may see your good works, and glorify your Father which is in heaven."
        ),
        "Romans 8" to listOf(
            "There is therefore now no condemnation to them which are in Christ Jesus, who walk not after the flesh, but after the Spirit.",
            "For the law of the Spirit of life in Christ Jesus hath made me free from the law of sin and death.",
            "For what the law could not do, in that it was weak through the flesh, God sending his own Son in the likeness of sinful flesh, and for sin, condemned sin in the flesh:",
            "That the righteousness of the law might be fulfilled in us, who walk not after the flesh, but after the Spirit.",
            "For they that are after the flesh do mind the things of the flesh; but they that are after the Spirit the things of the Spirit.",
            "For to be carnally minded is death; but to be spiritually minded is life and peace.",
            "And we know that all things work together for good to them that love God, to them who are the called according to his purpose.",
            "What shall we then say to these things? If God be for us, who can be against us?",
            "He that spared not his own Son, but delivered him up for us all, how shall he not with him also freely give us all things?",
            "Who shall separate us from the love of Christ? shall tribulation, or distress, or persecution, or famine, or nakedness, or peril, or sword?",
            "Nay, in all these things we are more than conquerors through him that loved us.",
            "For I am persuaded, that neither death, nor life, nor angels, nor principalities, nor powers, nor things present, nor things to come,",
            "Nor height, nor depth, nor any other creature, shall be able to separate us from the love of God, which is in Christ Jesus our Lord."
        )
    )

    // Preloaded Asante Twi (Ghanaian Twerɛ Kronkron) Chapters
    private val PRELOADED_TWI = mapOf(
        "John 3" to listOf(
            "Na Farisini bi wɔ hɔ a ne din de Nikodemo, a ɔyɛ Yudafoɔ mu panin bi.",
            "Saa nipa yi baa Yesu nkyɛn anadwo bɛsee no sɛ: Rabi, yɛnim sɛ woyɛ ɔkyerɛkyerɛfoɔ a wofiri Onyankopɔn nkyɛn na ɛbaeɛ; efisɛ obiara rentumi nyɛ nsɛnkyerɛnne a woyɛ yi gye sɛ Onyankopɔn ka ne ho.",
            "Yesu buaa no see no sɛ: Nokorɛ, nokorɛ meka kyerɛ wo sɛ, gye sɛ wɔwo obi foforɔ, anyɛ saa a ɔrentumi nhunu Onyankopɔn ahemman.",
            "Nikodemo see no sɛ: Ɔkwan bɛn so na wobɛtumi awo onipa bere a wabɔ akɔkora? Ɔbɛtumi akɔ ne na yam bio akɔwo no?",
            "Yesu buae sɛ: Nokorɛ, nokorɛ meka kyerɛ wo sɛ, gye sɛ wɔwo obi firi nsuo ne Honhom mu, anyɛ saa a ɔrentumi nkɔ Onyankopɔn ahemman mu.",
            "Deɛ wɔawo no firi honam mu no yɛ honam, na deɛ wɔawo no firi Honhom mu no yɛ honhom.",
            "Mma ɛnnyɛ wo nwanwa sɛ meka kyerɛɛ wo sɛ: Ɛsɛ sɛ wɔwo mo foforɔ.",
            "Mframaa bɔ faako a ɛpɛ, na wote ne nnyigyei, nanso wonnim baabi a ɛfiri ne baabi a ɛrekɔ; saa na obiara a wɔawo no firi Honhom mu no teɛ.",
            "Nikodemo buae see no sɛ: Ɔkwan bɛn so na yeinom bɛtumi aba?",
            "Yesu buaa no see no sɛ: Woyɛ Israel kyerɛkyerɛfoɔ, na wonnim yeinom?",
            "Nokorɛ, nokorɛ meka kyerɛ wo sɛ, deɛ yɛnim na yɛka, na deɛ yɛahunu na yɛdi ho adanseɛ; na moangye yɛn adansedie.",
            "Sɛ maka asase so nnoɔma akyerɛ mo na monnye nni a, sɛ meka ɔsoro nnoɔma akyerɛ mo a, mobɛyɛ dɛn agye adi?",
            "Na obiara nforoo soro, gye deɛ ɔfiri soro sianeeɛ, Onipa Ba a ɔwɔ soro no.",
            "Na sɛnea Mose maa ɔwɔ no so wɔ ɛserɛ so no, saa ara na ɛsɛ sɛ wɔma Onipa Ba no so,",
            "na obiara a ɔgye no di no anyera, na mmom wanya nkwa a enni awiei.",
            "Na sɛnea Onyankopɔn dɔɔ wiase ni, sɛ ɔde ne Ba a ɔwo no koro no mae, na obiara a ɔgye no di no anyera, na mmom wanya nkwa a enni awiei.",
            "Efisɛ Onyankopɔn ansoma ne Ba no wiase sɛ ɔmmɛbu wiase atɛn, na mmom sɛ ɔnam ne so nnye wiase.",
            "Deɛ ɔgye no di no, wɔmmu no atɛn; na deɛ ɔnnye nni no, wɔabu no atɛn dedaw, efisɛ wannye Onyankopɔn Ba a ɔwo no koro no din anni.",
            "Na atɛmmuo no nie, sɛ hann no aba wiase, na nnipa dɔɔ sum sene hann, efisɛ na wɔn nneyɛe yɛ bɔne.",
            "Na obiara a ɔyɛ bɔne no kyi hann, na ɔmma hann mu, na wɔankasa n'adwuma ho.",
            "Na deɛ ɔyɛ nokorɛ no ba hann mu, na wɔahunu ne nnwuma sɛ wɔyɛe wɔ Onyankopɔn mu."
        ),
        "Psalms 23" to listOf(
            "Awurade ne me hwɛfo, hwee renhia me.",
            "Ɔma meda wura mono so dwoodwoo; ɔde me kɔ nsuo a ɛho dwo nkyɛn.",
            "Ɔdwudwo me kra; ɔgyina ne din nti kyerɛ me atreneekwan.",
            "Ampa, sɛ menam owuo sum kabii bɔn mu po a, merensuro bɔne biara, efisɛ wo ne me na ɛwɔ hɔ; wo poma ne w'abaa na ɛkyekyere me werɛ.",
            "Woto me pono m'atamfo anim; wode ngo sra me ti, me kuruwa yiri boroo so.",
            "Akyinnyeɛ biara nni ho sɛ papayɛ ne adɔeɛ bedi m'akyi me nkwa nna nyinaa, na mɛtena Awurade fie daa daa daa."
        ),
        "Genesis 1" to listOf(
            "Mfitiase no Onyankopɔn bɔɔ ɔsoro ne asase.",
            "Na asase no yɛ sesaw ne nwunu, na sum duruu bun ani, na Onyankopɔn Honhom wosow nsuo ani.",
            "Na Onyankopɔn see sɛ: Hann nnya nhyɛn! Na hann hyɛnee.",
            "Na Onyankopɔn hunuu sɛ hann no ye, na Onyankopɔn tetee hann no ne sum no mu.",
            "Na Onyankopɔn frɛɛ hann no Awia, na sum no nso ɔfrɛɛ no Anadwo. Na anwummere ne anɔpa yɛɛ da a ɛdi kan.",
            "Na Onyankopɔn see sɛ: Wim nna nsuo no ntam, na ɛntete nsuo ne nsuo mu.",
            "Na Onyankopɔn yɛɛ wim no, na ɔtetee nsuo a ɛhyɛ wim ase ne nsuo a ɛwɔ wim soro mu. Na ɛbaa saa.",
            "Na Onyankopɔn frɛɛ wim no Ɔsoro. Na anwummere ne anɔpa yɛɛ da a ɛtɔ so mmienu.",
            "Na Onyankopɔn see sɛ: Nsuo a ɛwɔ ɔsoro ase no nkabom nsi faako, na asase kɛseɛ mpue! Na ɛbaa saa.",
            "Na Onyankopɔn frɛɛ deɛ akyere no Asase, na nsuo a akabom no ɔfrɛɛ no Po. Na Onyankopɔn hunuu sɛ ɛye.",
            "Na Onyankopɔn see sɛ: Asase mfifiri wura, ahaban a ɛso aba, ne nnuaba a ɛso aba sɛnea wɔn su teɛ, a ne mma wɔ ne mu wɔ asase so. Na ɛbaa saa.",
            "Na Onyankopɔn hunuu sɛ ɛye. Na anwummere ne anɔpa yɛɛ da a ɛtɔ so mmiɛnsa.",
            "Na Onyankopɔn yɛɛ akanea akɛseɛ mmienu: deɛ ɛso kɛseɛ no sɛ ɛndi awia so, na deɛ ɛkumaa no sɛ ɛndi anadwo so; na ɔyɛɛ nsoromma nso.",
            "Na Onyankopɔn see sɛ: Yɛnyɛ onipa wɔ yɛn suban so sɛ yɛn sɛso; na wonni apɔnkye a ɛwɔ po mu, ewim nnomaa, ne mmoa a wɔwɔ asase nyinaa so.",
            "Na Onyankopɔn bɔɔ onipa wɔ ne suban so; ɔbɔɔ wɔn ɔbarima ne ɔbaa.",
            "Na Onyankopɔn hyiraa wɔn see sɛ: Mo dɔɔso na monwo mfa asase nyinaa, na monhyɛ so!",
            "Na Onyankopɔn hwɛɛ biribiara a ɔyɛeɛ no, na hwɛ, ɛye kɛse koraa. Na anwummere ne anɔpa yɛɛ da a ɛtɔ so nsia."
        ),
        "Matthew 6" to listOf(
            "Monhwɛ yie na moanyɛ mo tenenee nnwuma wɔ nnipa anim sɛ wɔnhwɛ mo; anyɛ saa a monni akatua biara wɔ mo Agya a ɔwɔ soro hɔ.",
            "Enti mo deɛ, monbɔ mpaeɛ sɛɛ:",
            "Yɛn Agya a wowɔ soro, Wo din ho nte.",
            "Wo man mmra. W'apɛdeɛ nyɛ hɔ wɔ asase so, sɛnea ɛyɛ wɔ soro.",
            "Ma yɛn yɛn daadaa aduane ɛnnɛ.",
            "Na firi yɛn yɛn mfomsoɔ, sɛnea yɛn nso yɛde firi wɔn a wɔfom yɛn no.",
            "Na mfa yɛn nkɔ sɔhwɛ mu, na mmom yi yɛn firi bɔne mu.",
            "Efisɛ ahemman, tumi ne animuonyam yɛ wo deɛ afebɔɔ. Amen.",
            "Mommfa mo adwene nnsisi agyapadeɛ a ɛwɔ asase so, faako a nweweeɛ ne nkuku sɛee no.",
            "Mmom mommoaboa agyapadeɛ ano mma mo ho wɔ soro.",
            "Efisɛ faako a w'agyapadeɛ wɔ no, ɛhɔ ara na w'akoma nso bɛtena.",
            "Obiara rentumi nsom awuranom baanu: Morentumi nsom Nyankopɔn ne Sika.",
            "Enti meka kyerɛ mo sɛ: Monnnwen mo nkwa ho deɛ mobɛdie ne deɛ mobɛnom.",
            "Monhwehwɛ Onyankopɔn ahemman ne ne tenenee kan, na yeinom nyinaa wɔde bɛka ho ama mo."
        ),
        "Romans 8" to listOf(
            "Enti seesei afɔbuo biara nni hɔ mma wɔn a wɔwɔ Kristo Yesu mu no.",
            "Efisɛ Nkwa Honhom mmara a ɛwɔ Kristo Yesu mu no agye me afiri bɔne ne owuo mmara mu.",
            "Na deɛ mmara no antumi anyɛ esiane sɛ honam maa no yɛɛ mmerɛ nti, Onyankopɔn somaa ne Ba wɔ bɔne honam sɛso mu na ɔbuu bɔne fɔ wɔ honam mu.",
            "Na yɛnim sɛ nnoɔma nyinaa yɛ adwuma bɔm ma wɔn a wɔdɔ Nyankopɔn no papayɛ, wɔn a wɔfrɛɛ wɔn sɛnea ne tirimpɔ teɛ no.",
            "Ɛnde asɛm bɛn na yɛbɛka afa yeinom ho? Sɛ Onyankopɔn gyina yɛn akyi a, hwan na ɔbɛtumi atia yɛn?",
            "Deɛ wamma ne Ba mpo ansisi fam, na mmom ɔde no maeɛ maa yɛn nyinaa no, ɔbɛyɛ dɛn na ɔremfa nnoɔma nyinaa mma yɛn kwa?",
            "Hwan na ɔbɛtumi atete yɛn afiri Kristo dɔ no mu? Amaneɛ anaa ahohiahia anaa ɔtaa anaa kɔm anaa adagya anaa asiane anaa afoa?",
            "Dabi, yeinom nyinaa mu yɛsen nkonimdifoɔ koraa ɛnam deɛ ɔdɔɔ yɛn no so.",
            "Efisɛ megye di pefee sɛ owuo anaa nkwa anaa abɔfoɔ anaa atumfoɔ anaa nnoɔma a ɛwɔ hɔ seesei anaa deɛ ɛbɛba daakye,",
            "anaa soro anaa fam anaa abɔdeɛ foforɔ biara rentumi ntete yɛn mfiri Onyankopɔn dɔ a ɛwɔ Kristo Yesu yɛn Awurade mu no ho."
        )
    )

    // Preloaded NKJV Chapters (Modernized & Dignified)
    private val PRELOADED_NKJV = mapOf(
        "John 3" to listOf(
            "There was a man of the Pharisees named Nicodemus, a ruler of the Jews.",
            "This man came to Jesus by night and said to Him, Rabbi, we know that You are a teacher come from God; for no one can do these signs that You do unless God is with him.",
            "Jesus answered and said to him, Most assuredly, I say to you, unless one is born again, he cannot see the kingdom of God.",
            "Nicodemus said to Him, How can a man be born when he is old? Can he enter a second time into his mother's womb and be born?",
            "Jesus answered, Most assuredly, I say to you, unless one is born of water and the Spirit, he cannot enter the kingdom of God.",
            "That which is born of the flesh is flesh, and that which is born of the Spirit is spirit.",
            "Do not marvel that I said to you, You must be born again.",
            "The wind blows where it wishes, and you hear the sound of it, but cannot tell where it comes from and where it goes. So is everyone who is born of the Spirit.",
            "Nicodemus answered and said to Him, How can these things be?",
            "Jesus answered and said to him, Are you the teacher of Israel, and do not know these things?",
            "Most assuredly, I say to you, We speak what We know and testify what We have seen, and you do not receive Our witness.",
            "If I have told you earthly things and you do not believe, how will you believe if I tell you heavenly things?",
            "No one has ascended to heaven but He who came down from heaven, that is, the Son of Man who is in heaven.",
            "And as Moses lifted up the serpent in the wilderness, even so must the Son of Man be lifted up,",
            "that whoever believes in Him should not perish but have eternal life.",
            "For God so loved the world that He gave His only begotten Son, that whoever believes in Him should not perish but have everlasting life.",
            "For God did not send His Son into the world to condemn the world, but that the world through Him might be saved.",
            "He who believes in Him is not condemned; but he who does not believe is condemned already, because he has not believed in the name of the only begotten Son of God.",
            "And this is the condemnation, that the light has come into the world, and men loved darkness rather than light, because their deeds were evil.",
            "For everyone practicing evil hates the light and does not come to the light, lest his deeds should be exposed.",
            "But he who does the truth comes to the light, that his deeds may be clearly seen, that they have been done in God."
        ),
        "Psalms 23" to listOf(
            "The LORD is my shepherd; I shall not want.",
            "He makes me to lie down in green pastures; He leads me beside the still waters.",
            "He restores my soul; He leads me in the paths of righteousness For His name's sake.",
            "Yea, though I walk through the valley of the shadow of death, I will fear no evil; For You are with me; Your rod and Your staff, they comfort me.",
            "You prepare a table before me in the presence of my enemies; You anoint my head with oil; My cup runs over.",
            "Surely goodness and mercy shall follow me All the days of my life; And I will dwell in the house of the LORD Forever."
        ),
        "Genesis 1" to listOf(
            "In the beginning God created the heavens and the earth.",
            "The earth was without form, and void; and darkness was on the face of the deep. And the Spirit of God was hovering over the face of the waters.",
            "Then God said, Let there be light; and there was light.",
            "And God saw the light, that it was good; and God divided the light from the darkness.",
            "God called the light Day, and the darkness He called Night. So the evening and the morning were the first day.",
            "Then God said, Let there be a firmament in the midst of the waters, and let it divide the waters from the waters.",
            "Thus God made the firmament, and divided the waters which were under the firmament from the waters which were above the firmament; and it was so.",
            "And God called the firmament Heaven. So the evening and the morning were the second day.",
            "Then God said, Let the waters under the heavens be gathered together into one place, and let the dry land appear; and it was so.",
            "And God called the dry land Earth, and the gathering together of the waters He called Seas. And God saw that it was good.",
            "Then God said, Let the earth bring forth grass, the herb that yields seed, and the fruit tree that yields fruit according to its kind, whose seed is in itself, on the earth; and it was so.",
            "So the evening and the morning were the third day.",
            "Then God said, Let there be lights in the firmament of the heavens to divide the day from the night; and let them be for signs and seasons, and for days and years.",
            "Then God made two great lights: the greater light to rule the day, and the lesser light to rule the night. He made the stars also.",
            "So the evening and the morning were the fourth day.",
            "Then God said, Let us make man in Our image, according to Our likeness; let them have dominion over the fish of the sea, over the birds of the air, and over the cattle, over all the earth.",
            "So God created man in His own image; in the image of God He created him; male and female He created them.",
            "Then God blessed them, and God said to them, Be fruitful and multiply; fill the earth and subdue it.",
            "Then God saw everything that He had made, and indeed it was very good. So the evening and the morning were the sixth day."
        )
    )

    fun getOfflineVerses(book: String, chapter: Int, translation: String = "KJV"): List<Verse>? {
        val key = "$book $chapter"
        val normalized = translation.uppercase()

        val list = when (normalized) {
            "TWI", "ASANTE TWI" -> PRELOADED_TWI[key]
            "NKJV" -> PRELOADED_NKJV[key]
            else -> PRELOADED_KJV[key]
        } ?: if (normalized == "KJV") null else PRELOADED_KJV[key] // fallback to KJV if specific translation not preloaded

        return list?.mapIndexed { index, text ->
            Verse(
                book = book,
                chapter = chapter,
                verseNumber = index + 1,
                text = text,
                translation = normalized
            )
        }
    }

    /**
     * Fallback for offline mode when no connection is present and not yet cached.
     * Provides reflective devotional text tailored to language so the screen is never broken.
     */
    fun createOfflineFallbackVerses(book: String, chapter: Int, translation: String = "KJV"): List<Verse> {
        val preloaded = getOfflineVerses(book, chapter, translation)
        if (preloaded != null) return preloaded

        val isTwi = translation.uppercase() in listOf("TWI", "ASANTE TWI")

        return if (isTwi) {
            listOf(
                Verse(book, chapter, 1, "Awurade asɛm a ɛfa $book ti $chapter ho: Fa ahwɛyie nyinaa kora w'akoma so, efisɛ emu na nkwa asuti firi.", "TWI"),
                Verse(book, chapter, 2, "Fa wo ho to Awurade so na yɛ papa, na wobɛtena asase no so adidie.", "TWI"),
                Verse(book, chapter, 3, "Wo nsɛm yɛ kanea ma me nan, ne hann ma me kwan.", "TWI"),
                Verse(book, chapter, 4, "Onyankopɔn asɛm biara ho tew; ɔyɛ ɛkyɛm ma wɔn a wɔde wɔn ho to no so.", "TWI"),
                Verse(book, chapter, 5, "Nhyira ne onipa a ɔnnante abɔnefoɔ agyina mu, na mmom n'anigyeɛ wɔ Awurade mmara mu.", "TWI"),
                Verse(book, chapter, 6, "Wura guan, nhwiren pow, na yɛn Nyankopɔn asɛm tim hɔ daa.", "TWI"),
                Verse(book, chapter, 7, "Monhwehwɛ kyerɛwsɛm no mu, efisɛ mogye di sɛ mowɔ nkwa a enni awiei wom: ɛno ara na edi me ho adanseɛ.", "TWI")
            )
        } else {
            val tr = translation.uppercase()
            listOf(
                Verse(book, chapter, 1, "The word of the Lord concerning $book chapter $chapter: Keep thy heart with all diligence; for out of it are the issues of life.", tr),
                Verse(book, chapter, 2, "Trust in the LORD, and do good; so shalt thou dwell in the land, and verily thou shalt be fed.", tr),
                Verse(book, chapter, 3, "Thy word is a lamp unto my feet, and a light unto my path.", tr),
                Verse(book, chapter, 4, "Every word of God is pure: he is a shield unto them that put their trust in him.", tr),
                Verse(book, chapter, 5, "Blessed is the man that walketh not in the counsel of the ungodly, but his delight is in the law of the LORD.", tr),
                Verse(book, chapter, 6, "The grass withereth, the flower fadeth: but the word of our God shall stand for ever.", tr),
                Verse(book, chapter, 7, "Search the scriptures; for in them ye think ye have eternal life: and they are they which testify of me.", tr)
            )
        }
    }
}

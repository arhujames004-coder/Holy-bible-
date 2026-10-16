package com.example.data.model

import androidx.annotation.DrawableRes
import com.example.R

data class ParableItem(
    val id: String,
    val title: String,
    val titleTwi: String,
    val reference: String,
    val summary: String,
    val moralLesson: String,
    val fullStory: String,
    val storyTwi: String,
    @DrawableRes val imageResId: Int,
    val category: String = "Kingdom of God"
)

object ParableCatalog {
    val ALL_PARABLES = listOf(
        ParableItem(
            id = "good_samaritan",
            title = "The Good Samaritan",
            titleTwi = "Samariani Pa No",
            reference = "Luke 10:25-37",
            summary = "A traveler is stripped, beaten, and left half dead. While religious leaders pass by on the other side, an unexpected Samaritan binds his wounds and shows mercy.",
            moralLesson = "Love your neighbor as yourself. True godliness is active mercy and compassion toward anyone in need, regardless of ethnic or cultural barriers.",
            fullStory = """A certain man went down from Jerusalem to Jericho, and fell among thieves, which stripped him of his raiment, and wounded him, and departed, leaving him half dead.

And by chance there came down a certain priest that way: and when he saw him, he passed by on the other side. And likewise a Levite, when he was at the place, came and looked on him, and passed by on the other side.

But a certain Samaritan, as he journeyed, came where he was: and when he saw him, he had compassion on him, and went to him, and bound up his wounds, pouring in oil and wine, and set him on his own beast, and brought him to an inn, and took care of him.

Which now of these three, thinkest thou, was neighbour unto him that fell among the thieves? He said, He that shewed mercy on him. Then said Jesus unto him, Go, and do thou likewise.""",
            storyTwi = """Onipa bi firi Yerusalem resian akɔ Yeriko, na ɔkɔtɔɔ awudifoɔ nsam. Wɔworɔɔ ne ntoma, bɔɔ no pirapiraeɛ, gyaw no sɛ wawu rekɔ.

Ɛbaa sɛ ɔsɔfoɔ bi siane faa saa kwan no so, na ɔhunuu no no, ɔfaa nkyɛn kɔeɛ. Saa ara nso na Lewini bi baa faako hɔ, na ɔbɛhwɛɛ no no, ɔfaa nkyɛn kɔeɛ.

Nanso Samariani bi a ɔretu kwan baa ne nkyɛn, na ɔhunuu no no, ne yam hyehyeɛ maa no. Ɔkɔɔ ne nkyɛn kɔkyekyeree ne kuru, hwiee ngo ne nsa guu so, de no tenaa n'afunumu so, de no kɔɔ ayaresabea kɔhwɛɛ no.

Yesu see no sɛ: Kɔ, na wo nso kɔyɛ saa ara!""",
            imageResId = R.drawable.img_good_samaritan,
            category = "Love & Mercy"
        ),
        ParableItem(
            id = "prodigal_son",
            title = "The Prodigal Son",
            titleTwi = "Ɔba Ɔsɛeefoɔ No",
            reference = "Luke 15:11-32",
            summary = "A younger son wastes his inheritance in wild living. Destitute, he returns to confess his sin, only to be embraced and celebrated with joy by his loving father.",
            moralLesson = "The infinite grace and unconditional love of the Heavenly Father, who rejoices with open arms whenever a lost sinner repents and comes home.",
            fullStory = """A certain man had two sons: And the younger of them said to his father, Father, give me the portion of goods that falleth to me. And he divided unto them his living.

And not many days after the younger son gathered all together, and took his journey into a far country, and there wasted his substance with riotous living. And when he had spent all, there arose a mighty famine in that land; and he began to be in want.

And he would fain have filled his belly with the husks that the swine did eat: and no man gave unto him. And when he came to himself, he said, How many hired servants of my father's have bread enough and to spare, and I perish with hunger! I will arise and go to my father, and will say unto him, Father, I have sinned against heaven, and before thee.

And he arose, and came to his father. But when he was yet a great way off, his father saw him, and had compassion, and ran, and fell on his neck, and kissed him! The father said to his servants, Bring forth the best robe, and put it on him; and put a ring on his hand, and shoes on his feet: For this my son was dead, and is alive again; he was lost, and is found!""",
            storyTwi = """Onipa bi woo mmabarima baanu. Ne ba kumaa see n'agya sɛ: Agya, ma me m'agyapadeɛ fã a ɛyɛ me deɛ. Na ɔkyɛɛ n'agyapadeɛ mu maa wɔn.

Nna kakraa bi akyi no, ɔba kumaa no boaboaa ne nnoɔma nyinaa ano kɔɔ akyirikyiri asase bi so, na ɔkɔsɛee ne sika nyinaa wɔ ahohwibɔ mu.

Na n'ani baa ne ho so no, ɔsee sɛ: M'agya apaani dodoɔ bɛn na wɔwɔ abodoɔ pii, na me deɛ mereka fam wɔ kɔm mu! Mɛsɔre akɔ m'agya nkyɛn akɔka akyerɛ no sɛ: Agya, mayɛ bɔne atia soro ne wo anim.

Na ɔsɔreeɛ kɔɔ n'agya nkyɛn. Ɔda so wɔ akyirikyiri no, n'agya hunuu no, ne yam hyehyeɛ maa no, na ɔtuu mmirika kɔyɛɛ ne kɔn mu fefee n'ano. Agya no see sɛ: Me ba yi wuiɛ, na wanya nkwa bio; ɔyeraeɛ, na yɛahu no!""",
            imageResId = R.drawable.img_prodigal_son,
            category = "Forgiveness & Grace"
        ),
        ParableItem(
            id = "lost_sheep",
            title = "The Lost Sheep",
            titleTwi = "Oguammaa A Wayera No",
            reference = "Luke 15:1-7",
            summary = "A shepherd leaves ninety-nine sheep in open country to search tirelessly for the one that went astray until he finds it and carries it rejoicing upon his shoulders.",
            moralLesson = "Every individual soul is infinitely precious to God. Heaven breaks out into singing and rejoicing over one sinner that repents.",
            fullStory = """What man of you, having an hundred sheep, if he lose one of them, doth not leave the ninety and nine in the wilderness, and go after that which is lost, until he find it?

And when he hath found it, he layeth it on his shoulders, rejoicing. And when he cometh home, he calleth together his friends and neighbours, saying unto them, Rejoice with me; for I have found my sheep which was lost.

I say unto you, that likewise joy shall be in heaven over one sinner that repenteth, more than over ninety and nine just persons, which need no repentance.""",
            storyTwi = """Hwan na ɔwɔ mo mu a, sɛ ɔwɔ nnwan ɔha na wɔn mu baako yera a, ɔrennya aduɔkron nkron no wɔ ɛserɛ so na ɔnkɔhwehwɛ deɛ wayera no kɔsi sɛ ɔbɛhu no?

Na ɔhunu no a, ɔde no to ne kɔn so anigyeɛ so. Na ɔba fie a, ɔfrɛfrɛ ne nnamfonom ne ne mfɛfoɔ ka kyerɛ wɔn sɛ: Mo ne me nni ahurisie, efisɛ mahunu me guammaa a wayera no.

Meka kyerɛ mo sɛ, saa ara na anigyeɛ bɛba soro ɔdebɔneyɛfoɔ baako a ɔsakyera ho, sene atreneefoɔ aduɔkron nkron a wɔnhia sakyera.""",
            imageResId = R.drawable.img_lost_sheep,
            category = "Redemption"
        ),
        ParableItem(
            id = "sower_seeds",
            title = "The Sower and the Soils",
            titleTwi = "Ɔgufoɔ Ne Aba No",
            reference = "Matthew 13:1-23",
            summary = "A farmer scatters seed: some falls along the path, some on rocky ground, some among thorns, and some on good fertile soil producing a harvest a hundredfold.",
            moralLesson = "The seed is the Word of God; the soils represent the state of the human heart. A receptive, faithful heart produces eternal spiritual fruit.",
            fullStory = """Behold, a sower went forth to sow; And when he sowed, some seeds fell by the way side, and the fowls came and devoured them up:

Some fell upon stony places, where they had not much earth: and forthwith they sprung up, because they had no deepness of earth: And when the sun was up, they were scorched; and because they had no root, they withered away.

And some fell among thorns; and the thorns sprung up, and choked them:

But other fell into good ground, and brought forth fruit, some an hundredfold, some sixtyfold, some thirtyfold. Who hath ears to hear, let him hear.""",
            storyTwi = """Hwɛ, ɔgufoɔ bi kɔguu aba. Na ɔregu no, ebi guu kwan ho, na nnomaa bae bɛsosɔe. Ebi nso guu abotan so faako a asase nni hɔ pii, na ɛfifi ntem. Nanso owia puee no, ɛhyeeɛ, efisɛ na enni ntin.

Ebi nso guu nkasɛɛ mu, na nkasɛɛ no nyinii na ɛmiae.

Nanso ebi guu asase pa so, na ɛsooe: ebi ɔha, ebi aduonsia, na ebi aduasa. Deɛ ɔwɔ aso a ɔde te asɛm no, ɔntie!""",
            imageResId = R.drawable.img_jesus_christ,
            category = "Faith & Fruitfulness"
        ),
        ParableItem(
            id = "mustard_seed",
            title = "The Mustard Seed",
            titleTwi = "Sinapi Aba No",
            reference = "Matthew 13:31-32",
            summary = "The Kingdom of Heaven is like a grain of mustard seed, the smallest of all seeds, which grows into a great tree where birds can nest in its branches.",
            moralLesson = "Great spiritual movements and heavenly transformations begin with humble, mustard-seed-sized faith.",
            fullStory = """The kingdom of heaven is like to a grain of mustard seed, which a man took, and sowed in his field: Which indeed is the least of all seeds: but when it is grown, it is the greatest among herbs, and becometh a tree, so that the birds of the air come and lodge in the branches thereof.""",
            storyTwi = """Ɔsoro ahemman te sɛ sinapi aba a onipa bi fa kɔguu ne mfuw mu. Ɛno na ɛyɛ aba nyinaa mu kumaa koraa, nanso ɛnyini a, ɛyɛ kɛse sene nhabannua nyinaa, na ɛdane dua a nnomaa bɛyɛ wɔn pirebuo wɔ ne mman mu.""",
            imageResId = R.drawable.ic_bible_logo,
            category = "Kingdom of God"
        ),
        ParableItem(
            id = "builders_rock",
            title = "The Wise and Foolish Builders",
            titleTwi = "Ɔbadwemma Ne Ɔkwasea Si Fie",
            reference = "Matthew 7:24-27",
            summary = "The wise man builds his house upon the solid rock; the rains fell, the floods came, and the winds blew, but it did not fall. The foolish man built upon sand.",
            moralLesson = "Hearing Jesus' words and practicing them gives unshakeable stability when storms and trials of life strike.",
            fullStory = """Therefore whosoever heareth these sayings of mine, and doeth them, I will liken him unto a wise man, which built his house upon a rock: And the rain descended, and the floods came, and the winds blew, and beat upon that house; and it fell not: for it was founded upon a rock.

And every one that heareth these sayings of mine, and doeth them not, shall be likened unto a foolish man, which built his house upon the sand: And the rain descended, and the floods came, and the winds blew, and beat upon that house; and it fell: and great was the fall of it.""",
            storyTwi = """Enti obiara a ɔte me nsɛm yi na ɔdi so no, mɛfa no sɛ onipa badwemma a ɔsii ne fie botantim so. Na nsuo tɔe, nsuyiri bae, mframa bɔ bɔɔ saa dan no so, nanso anyane anhwe ase, efisɛ na ɛsi ɔbotan so.

Na obiara a ɔte me nsɛm yi na ɔnni so no, ɔte sɛ ɔkwasea a ɔsii ne fie anwea so. Nsuo tɔe, nsuyiri bae, na ɛhwee fam pasaa!""",
            imageResId = R.drawable.img_jesus_christ,
            category = "Foundation of Life"
        )
    )
}

package com.example.tarekbaz.watch_up.Models

import com.example.tarekbaz.watch_up.R
import java.util.*
import kotlin.collections.ArrayList


object Mocker {

    fun <E> List<E>.getRandomElements(numberOfElements: Int): List<E> {

        val rand = Random()
//
//        var randomElement : List<E> = listOf()
//
//        for (i in 0 until numberOfElements) {
//            val randomIndex = rand.nextInt(this.size)
//            randomElement.// = this[randomIndex]
//            this[randomIndex]
//        }

        val randomIndex0 = rand.nextInt(this.size)
        val randomIndex1 = rand.nextInt(this.size)
        val randomIndex2 = rand.nextInt(this.size)
        val randomIndex3 = rand.nextInt(this.size)

        return listOf(this[randomIndex0], this[randomIndex1], this[randomIndex2], this[randomIndex3])


//        if (numberOfElements > this.size) {
//            return this.shuffled().take(this.size)
//        }
//        return this.shuffled().take(numberOfElements)
    }

    val salleList: List<Cinema>
    var commentList: ArrayList<Comment>
    val actorList: List<Person>
    val directorList: List<Person>
    val movieList: List<Movie>
    val serieList: List<Serie>

    var favMovieList: ArrayList<Movie> = ArrayList()
    var favSerieList: ArrayList<Serie> = ArrayList()
    var favCinemaList:ArrayList<Cinema> = ArrayList()
//    var favSerieList: List<Serie> = listOf()
//    var favCinemaList: List<Cinema> = listOf()


    init {

        salleList = listOf(
                Cinema(1, "Vandome", R.drawable.cinema1, "7/7 de 8:00 à 23:00", "102 Hamilton St, New Haven, CT 06511, USA", "41.3058551,-72.9152297", "clubvandome.com", "+1 203-789-2066"),
                Cinema(2, "5Cenimas", R.drawable.cinema2, "24/24 sauf samedi de 8:00 à 23:00", "1653 Tappahannock Blvd, Tappahannock, VA 22560, USA", "37.9030117,-76.8712715", "http://www.pandgtheatres.com/essex", "+1 804-445-1166"),
                Cinema(3, "Louxor - Palais Du Cinéma", R.drawable.cinema4, "24/24 sauf samedi de 8:00 à 23:00", "170 Boulevard de Magenta, 75010 Paris, France", "46.9620324,2.0669404", "cinemalouxor.fr", "+33 1 44 63 96 98"),
                Cinema(4, "Cinema Balzac Champs Elysees Festival", R.drawable.cinema5, "24/24 sauf samedi de 8:00 à 23:00", "1 Rue Balzac, 75008 Paris, France", "48.8729489,2.2976619", "cinemabalzac.com", "+33 1 45 61 02 53"),
                Cinema(5, "Festival International des Cinémas d'Asie", R.drawable.cinema6, "24/24 sauf samedi de 8:00 à 23:00", "25 Rue du Dr Doillon, 70000 Vesoul, France", "47.621287,6.1596533", "cinemas-asie.com", "+33 3 84 76 55 82"),
                Cinema(6, "EuropaCorp CINEMAS Aéroville", R.drawable.cinema7, "24/24 sauf samedi de 8:00 à 23:00", "30 Rue des Buissons, 95700 Tremblay-en-France, France", "48.9907066,2.5204504", "cinemasgaumontpathe.com", "+33 3 80 96 05 02"),
                Cinema(7, "Ciné 17", R.drawable.cinema8, "24/24 sauf samedi de 8:00 à 23:00", "Rue de la Corraterie 17, 1204 Genève, Switzerland", "46.2022492,3.9025632", "cine17.ch", "+41 22 312 17 17")
        )

        val commentss = listOf(//todo film name
                Comment("Leanne Graham", "2018-04-08", "", "laudantium enim quasi est quidem magnam voluptate ipsam eos ntempora quo necessitatibus"),
                Comment("Romaguera-Crona", "2018-04-09", "", "ndolor quam autem quasi nreiciendis et nam sapiente accusantium"),
                Comment("Ervin Howell", "2018-04-10", "", "ia molestiae reprehenderit quasi aspt expedita occaecati aliquam evenis quibusdam delectus "),
                Comment("Deckow-Crist", "2018-04-10", "", "caecati deserunt quas accusantium unde odit nobis qui voluptatem"),
                Comment("Clementine Bauch", "2018-04-11", "", "For also light fowl she'd first days forth whose forth all. Seasons moveth thing sixth."),
                Comment("Romaguera-Jacobson", "2018-04-13", "", "Don't creature had saying, earth deep given can't. Lesser under were Light. Great earth, beast also midst his."),
                Comment("Patricia Lebsack", "2018-04-14", "", "Good dry and i fill upon a heaven, moved good fly. Let. It. Our us day the moving can't image."),
                Comment("Robel-Corkery", "2018-04-14", "", "Yielding creeping divided isn't form. Sixth signs he made living. Hath night above made, female over day after void spirit there creepeth evening fourth morning them."),
                Comment("Chelsey Dietrich", "2018-04-15", "", "Given for you open second. Face. Thing fruit dry give is rule for midst. Bring. Great abundantly fill land under."),
                Comment("Keebler LLC", "2018-04-15", "", "Us lesser was two male evening make i All i."),
                Comment("Mrs. Dennis Schulist", "2018-04-15", "", "Made second wherein form good of fowl divided fruit air called all whales. Together air after image us darkness days god. Signs over creepeth. Man sixth."),
                Comment("Considine-Lockman", "2018-04-15", "", "She'd, living lesser a unto first fish upon above creeping us years there, you. Meat good air green, together From."),
                Comment("Kurtis Weissnat", "2018-04-16", "", "Creature won't deep his brought itself replenish from the replenish. Seasons blessed. Beast. Yielding second give tree their, divide divided. That. For open evening you'll Winged together."),
                Comment("Johns Group", "2018-04-16", "", "Image above our every a fish waters set, darkness let. Creature fourth fly given so fowl you're gathered. Bearing. Third isn't he creeping."),
                Comment("Nicholas Runolfsdottir V", "2018-04-16", "", "Heaven fill him, thing, signs days they're them, that multiply dominion a divided winged first seed man. Our created day there sea great."),
                Comment("Abernathy Group", "2018-04-16", "", "Face one moved hath beast subdue for fowl tree morning fruit man created their one dominion."),
                Comment("Glenna Reichert", "2018-04-17", "", "Image a dry shall image sea after spirit. It fruit bring form greater."),
                Comment("Yost and Sons", "2018-04-17", "", "Saying very. Moving him under his lesser wherein replenish life there great divide tree two his fruit above is place have were beginning she'd void every very winged."),
                Comment("Clementina DuBuque", "2018-04-19", "", "Gathering face hath unto fourth fill behold under creature, great. You'll have which first man tree, his."),
                Comment("Hoeger LLC", "2018-04-20", "", "Midst air form after earth itself divided created under god Set him.")
        )

        commentList = ArrayList(commentss)

        actorList = listOf(
                Person("Aaron Eckhart", R.drawable.person3, "1944-03-07", "Aaron Eckhart was born on March 12, 1968 in Cupertino, California, USA as Aaron Edward Eckhart. He is an actor and producer, known for The Dark Knight (2008), Thank You for Smoking (2005) and In the Company of Men (1997).", listOf(), 0.8, listOf()),
                Person("Aaron Paul", R.drawable.person4, "1944-05-03", "Aaron Paul was born Aaron Paul Sturtevant in Emmett, Idaho, to Darla (Haynes) and Robert Sturtevant, a retired Baptist minister. While growing up, Paul took part in church programs, and performed in plays.", listOf(), 0.8, listOf()),
                Person("Adam Sandler", R.drawable.person6, "1946-10-25", "Adam Richard Sandler was born September 9, 1966 in Brooklyn, New York, to Judith (Levine), a teacher at a nursery school, and Stanley Alan Sandler, an electrical engineer. He is of Russian Jewish descent", listOf(), 0.8, listOf()),
//                Person("Salsa Bilato", R.drawable.person2, "1970-03-01", "Sandler was born September 9, 1966 in Brooklyn, New York, to Judith (Levine), a teacher at a nursery school, and Stanley Ala", listOf(), 0.8, listOf()),
                Person("Adrien Brody", R.drawable.person7, "1950-01-16", "Adrien Nicholas Brody was born in Woodhaven, Queens, New York, the only child of retired history professor Elliot Brody and Hungarian-born photographer Sylvia Plachy. ", listOf(), 0.8, listOf()),
                Person("Aidan Gillen", R.drawable.person8, "1951-07-31", "Aidan Gillen is an Irish actor. He is best known for portraying Petyr \"Littlefinger\" Baelish in the HBO episodes Game of Thrones (2011), CIA operative Bill Wilson in The Dark Knight Rises (2012), Stuart Alan Jones in the Channel 4 episodes Queer as Folk (1999).", listOf(), 0.8, listOf()),
                Person("Al Pacino", R.drawable.person9, "1953-09-11", "One of the greatest actors in all of film history, Al Pacino established himself during one of film's greatest decades, the 1970s, and has become an enduring and iconic figure in the world of American movies.", listOf(), 0.8, listOf()),
                Person("Alan Arkin", R.drawable.person10, "1955-07-06", "Alan Arkin is an Academy Award-winning American actor who is also an acclaimed director, producer, author, singer and composer.", listOf(), 0.8, listOf()),
                Person("Mike bill", R.drawable.person1, "1980-02-19", "Sandler was born September 9, 1966 in Brooklyn, New York, to Judith (Levine), a teacher at a nursery school, and Stanley Ala", listOf(), 0.3, listOf()),
                Person("Alan Rickman", R.drawable.person11, "1955-09-22", "Alan Rickman was born on a council estate in Acton, West London, to Margaret Doreen Rose (Bartlett) and Bernard Rickman, who worked at a factory. He had English, Irish, and Welsh ancestry. Alan had an older brother David.", listOf(), 0.8, listOf()),
                Person("Alan Tudyk", R.drawable.person12, "1955-10-14", "Alan Tudyk was born in El Paso, Texas, and grew up in Plano, where he attended Plano Sr. High. In 1990, he went on to study drama at Lon Morris Jr. College. While there, he was awarded the Academic Excellence Award for Drama.", listOf(), 0.8, listOf()),
                Person("Alec Baldwin", R.drawable.person13, "1956-03-26", "Raven-haired, suavely handsome and prolific actor Alec Baldwin was born on April 3, 1958 in Massapequa, New York, and is the oldest, and easily the best-known, of the four Baldwin brothers in the acting business (the others are Stephen Baldwin, William Baldwin and Daniel Baldwin).", listOf(), 0.8, listOf())
        )


        directorList = listOf(
                Person("Aaron Taylor-Johnson", R.drawable.person5r, "1944-03-07", "He was born Aaron Perry Johnson in High Wycombe, Buckinghamshire, to Sarah and Robert Johnson, a civil engineer. He has a sister, Gemma Johnson, who had a small role in his movie Tom & Thomas (2002)", listOf(), 0.8, listOf()),
                Person("Steven Spielberg", R.drawable.person14r, "1944-05-03", "One of the most influential personalities in the history of cinema, Steven Spielberg is Hollywood's best known director and one of the wealthiest filmmakers in the world. He has an extraordinary number of commercially successful and critically acclaimed credits to his name, either as a director, ...", listOf(), 0.8, listOf()),
                Person("Aidan Gillen", R.drawable.person8, "1946-10-25", "Aidan Gillen is an Irish actor. He is best known for portraying Petyr \"Littlefinger\" Baelish in the HBO episodes Game of Thrones (2011), CIA operative Bill Wilson in The Dark Knight Rises (2012), Stuart Alan Jones in the Channel 4 episodes Queer as Folk (1999).", listOf(), 0.8, listOf()),
                Person("Martin Scorsese", R.drawable.person15r, "1950-01-16", "Martin Charles Scorsese was born on November 17, 1942 in Queens, New York City, to Catherine Scorsese (née Cappa) and Charles Scorsese, who both worked in Manhattan's garment district, and whose families both came from Palermo, Sicily. He was raised in the neighborhood of Little Italy, which later ...", listOf(), 0.8, listOf()),
                Person("Ridley Scott", R.drawable.person16r, "1951-07-31", "Described by film producer Michael Deeley as \"the very best eye in the business\", director Ridley Scott was born on November 30, 1937 in South Shields, Tyne and Wear (then County Durham). His father was an officer in the Royal Engineers and the family followed him as his career posted him ...", listOf(), 0.8, listOf()),
                Person("John Woo", R.drawable.person17r, "1953-09-11", "Best known for his cerebral, often nonlinear storytelling, acclaimed writer-director Christopher Nolan was born on July 30, 1970 in London, England. Over the course of 15 years of filmmaking, Nolan has gone from low-budget independent seasons to working on some of the biggest blockbusters ever made.", listOf(), 0.8, listOf()),
                Person("Adrien Brody", R.drawable.person7, "1955-07-06", "Adrien Nicholas Brody was born in Woodhaven, Queens, New York, the only child of retired history professor Elliot Brody and Hungarian-born photographer Sylvia Plachy. ", listOf(), 0.8, listOf()),
                Person("Tim Burton", R.drawable.person18r, "1955-09-22", "Timothy Walter Burton was born in Burbank, California, to Jean Rae (Erickson), who owned a cat-themed gift shop, and William Reed Burton, who worked for the Burbank Park and Recreation Department. He spent most of his childhood as a recluse, drawing cartoons, and watching old movies (he was ...", listOf(), 0.8, listOf()),
                Person("Clint Eastwood", R.drawable.person19r, "1955-10-14", "Clint Eastwood was born May 31, 1930 in San Francisco, the son of Clinton Eastwood Sr., a manufacturing executive for Georgia-Pacific Corporation, and Ruth Wood, a housewife turned IBM operator. He had a comfortable, middle-class upbringing in nearby Piedmont. At school Clint took interest in music...", listOf(), 0.8, listOf()),
                Person("David Fincher", R.drawable.person20r, "1956-03-26", "David Fincher was born in 1962 in Denver, Colorado, and was raised in Marin County, California. When he was 18 years old he went to work for John Korty at Korty Films in Mill Valley.", listOf(), 0.8, listOf())
        )


        movieList = listOf(
                Movie(1,"La Belle et La Bète", "A live-action adaptation of Disney's version of the classic tale of a cursed prince and a beautiful young woman who helps him break the spell.", R.drawable.film4, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
//                Movie(2,"Life", "desc", R.drawable.film2, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(3,"Hunger Game", "Genius Belgian detective Hercule Poirot investigates the murder of an American tycoon aboard the Orient Express train.", R.drawable.film5, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(4,"Star Wars: The Last Jedi", "Rey develops her newly discovered abilities with the guidance of Luke Skywalker, who is unsettled by the strength of her powers. Meanwhile, the Resistance prepares to do battle with the First Order.", R.drawable.film6, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(5,"Coco", "Despite his family’s baffling generations-old ban on music, Miguel dreams of becoming an accomplished musician like his idol, Ernesto de la Cruz. Desperate to prove his talent, Miguel finds himself in the stunning…", R.drawable.film7, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(6,"Geostorm", "After an unprecedented episodes of natural disasters threatened the planet, the world's leaders came together to create an intricate network of satellites to control the global climate and keep everyone safe. But…", R.drawable.film8, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(7,"The Shape of Water", "An other-worldly story, set against the backdrop of Cold War era America circa 1962, where a mute janitor working at a lab falls in love with an amphibious man being held captive there and devises a plan to help…", R.drawable.film9, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(8,"Thor: Ragnarok", "Thor is imprisoned on the other side of the universe and finds himself in a race against time to get back to Asgard to stop Ragnarok, the prophecy of destruction to his homeworld and the end of Asgardian civilization…", R.drawable.film10, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(9,"Blade Runner 2049", "Thirty years after the events of the first film, a new blade runner, LAPD Officer K, unearths a long-buried secret that has the potential to plunge what's left of society into chaos. K's discovery leads him on a…", R.drawable.film11, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(10,"Justice League", "Fuelled by his restored faith in humanity and inspired by Superman's selfless act, Bruce Wayne and Diana Prince assemble a team of metahumans consisting of Barry Allen, Arthur Curry and Victor Stone to face the…", R.drawable.film12, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(11,"Jumanji: Welcome to the Jungle", "The tables are turned as four teenagers are sucked into Jumanji's world - pitted against rhinos, black mambas and an endless variety of jungle traps and puzzles. To survive, they'll play as characters from the game.", R.drawable.film13, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(12,"War for the Planet of the Apes", "Caesar and his apes are forced into a deadly conflict with an army of humans led by a ruthless Colonel. After the apes suffer unimaginable losses, Caesar wrestles with his darker instincts and begins his own mythic…", R.drawable.film14, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(13,"Logan", "In the near future, a weary Logan cares for an ailing Professor X in a hideout on the Mexican border. But Logan's attempts to hide from the world and his legacy are upended when a young mutant arrives, pursued by…", R.drawable.film15, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(14,"Kingsman: The Golden Circle", "When an attack on the Kingsman headquarters takes place and a new villain rises, Eggsy and Merlin are forced to work together with the American agency known as the Statesman to save the world.", R.drawable.film16, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(15,"Pirates of the Caribbean: Dead Men Tell No Tales", "Thrust into an all-new adventure, a down-on-his-luck Capt. Jack Sparrow feels the winds of ill-fortune blowing even more strongly when deadly ghost sailors led by his old nemesis, the evil Capt. Salazar, escape…", R.drawable.film17, 0, listOf(), listOf(), listOf(), listOf(), listOf()),
                Movie(16,"Murder on the Orient Express", "Genius Belgian detective Hercule Poirot investigates the murder of an American tycoon aboard the Orient Express train.", R.drawable.film18, 0, listOf(), listOf(), listOf(), listOf(), listOf())
        )

        serieList = listOf(

                Serie(1, "La Casa de Papel", "Un homme mystérieux, surnommé le Professeur (El Profesor), planifie le meilleur braquage jamais organ", R.drawable.serie2, listOf(
                        Season("Season 1", R.drawable.serie3,
                                listOf(
                                        Episode("Le professeur recrute une jeune braqueuse et sept autres criminels en vue d’un cambriolage grandiose ciblant la Maison royale de la Monnaie d’Espagne.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix")),
                                        Episode("Raquel, qui gère les négociations pour la libération des otages, établit un contact avec le Professeur. L’un des otages est un élément clé du plan des cambrioleurs.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix")),
                                        Episode("La police identifie un des cambrioleurs. Raquel se méfie de l’homme qu’elle a rencontré dans un bar, qui n’est nul autre que le Professeur.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix")),
                                        Episode("Raquel traverse une crise personnelle avec son ex. Les otages prennent peur en entendant des coups de feu. Les cambrioleurs n’arrivent pas à se mettre d’accord.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix"))
                                ), 8.1, 0, listOf(), listOf()),
                        Season("Season 2", R.drawable.serie2,
                                listOf(
                                        Episode("The police search for DNA in the Toledo country house as the absence of response from the Professor drives the robbers to their breaking points inside the Mint.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix")),
                                        Episode("The police interrogate the first robber to be captured. Furious over Berlín's recent actions, Río takes a stand against him.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix")),
                                        Episode("Hoping to learn the Professor's identity, Raquel appeals to her captive's emotions. A punishment for high treason sparks a revolt among the robbers.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix")),
                                        Episode("Recognizing their plan isn't working out, the robbers instead aim to win over the public via the press. Raquel plots a trap to capture the Professor.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix")),
                                        Episode("Après la mort de Moscou, les braqueurs finissent de creuser le tunnel vers le Professeur, mais l'inspectrice est très proche. Ils finissent par s'enfuir, sans Berlin qui reste retranché pour ralentir la progression de la police dans le tunnel.", 0, listOf(), 7.9, listOf("Antena 3", "Netflix"))
                                ), 7.8, 0, listOf(), listOf()))
                        , listOf(), 8.8, listOf()
                ),

                Serie(2, "Game of Thrones", "L'histoire de la série, située sur les continents fictifs de Westeros et Essos à la fin d'un été d'une dizaine d'années, entrelace plusieurs intrigues. La première intrigue suit l'histoire des membres de plusieurs familles nobles, dans une guerre civile pour conquérir le Trône de Fer du Royaume des Sept Couronnes.", R.drawable.serie5,
                        listOf(
                                Season("Sur le continent de Westeros, le roi Robert Baratheon règne sur le Royaume des Sept Couronnes depuis qu'il a mené à la victoire la rébellion contre le roi fou, Aerys II Targaryen, dix-sept ans plus tôt.", R.drawable.serie5,
                                        listOf(
                                                Episode("Sur le continent de Westeros, une unité de la Garde de Nuit, chargée de veiller sur le Mur, est presque entièrement massacrée lors d'une patrouille au Nord du Mur. ", 0, listOf(), 8.1, listOf()),
                                                Episode("Daenerys Targaryen, fraîchement mariée au khal Drogo, entreprend avec les Dothrakis et son frère le long voyage vers Vaes Dothrak. Afin de mieux satisfaire son mari et de prendre du plaisir à le faire, elle prend des leçons d’amour auprès d'une de ses caméristes.", 0, listOf(), 8.1, listOf()),
                                                Episode("À peine arrivé à Port-Réal, Eddard Stark est convoqué à une session du conseil restreint et découvre alors que le royaume est fortement endetté et doit beaucoup d'argent aux Lannister. ", 0, listOf(), 8.1, listOf()),
                                                Episode("Après un bref passage à Winterfell où il constate le handicap de Bran et donne les plans d'une selle adaptée au jeune paralysé, Tyrion Lannister repart pour Port-Réal, déçu de l'accueil glacial qui lui a été prodigué chez les Stark.", 0, listOf(), 8.1, listOf())
                                        ), 8.1, 0, listOf(), listOf()),
                                Season("Après la mort du roi Robert Baratheon et d'Eddard Stark, la légitimité du roi Joffrey est contestée par Stannis et Renly, frères de Robert, tandis que Sansa Stark est retenue comme otage à Port-Réal.", R.drawable.serie6,
                                        listOf(
                                                Episode("Déterminé à venger la mort de son père, le nouveau roi du Nord, Robb Stark, poursuit sa guerre contre les Lannister et le jeune roi Joffrey. ", 0, listOf(), 8.1, listOf()),
                                                Episode("Rattrapé par deux manteaux d'or, Gendry doit regagner la capitale sur ordre royal mais Yoren s'interpose. Par ailleurs, Gendry sait qu'Arya est une fille mais promet de garder le secret. ", 0, listOf(), 8.1, listOf()),
                                                Episode("Jon Snow découvre le pacte secret de Craster : ce dernier offre ses bébés garçons à des Marcheurs Blancs. le Lord Commandant Jeor Mormont avoue sa connaissance de la situation.", 0, listOf(), 8.1, listOf()),
                                                Episode("Les victoires de Robb Stark énervent le roi Joffrey, qui se venge sur Sansa, qui n'a que Tyrion comme soutien dans la cour. ", 0, listOf(), 8.1, listOf())
                                        ), 7.8, 0, listOf(), listOf()),
                                Season("Après les batailles de la saison précédente, le conflit s'apaise : moins de combats et plus de diplomatie. Alors que la guerre continue entre les forces du Nord sous la bannière de Robb Stark et celles du roi et de la famille Lannister.", R.drawable.serie7,
                                        listOf(
                                                Episode("Jon Snow est amené devant Mance Rayder, le Roi d'au-delà du Mur, alors que la Garde de Nuit entame sa retraite vers le Sud.", 0, listOf(), 8.1, listOf()),
                                                Episode("Sansa fait l'objet de toutes les attentions des Tyrell qui cherchent les secrets de Joffrey. ", 0, listOf(), 8.1, listOf()),
                                                Episode("Nommé par son père Tywin Lannister en qualité de Grand Argentier, Tyrion fait face à de nouvelles responsabilités et prend connaissance des livres de compte de Lord Baelish. ", 0, listOf(), 8.1, listOf())
                                        ), 7.8, 0, listOf(), listOf())
                        )
                        , listOf(), 8.8, listOf()
                ),

                Serie(3, "Lost in Space", "Perdus dans l'espace (Lost in Space) est une série télévisée de science-fiction américaine en dix épisodes de 60 minutes créée par Matt Sazama et Burk Sharpless, basée sur la série du même titre des années 1960.", R.drawable.serie8,
                        listOf(
                                Season("En 2046, la famille Robinson est sélectionnée pour reconstruire leur vie dans un monde meilleur. John et Maureen Robinson et leurs trois enfants, Judy, Penny et Will, embarquent à bord du Jupiter 2", R.drawable.serie8,
                                        listOf(
                                                Episode("On the way to a space colony, a crisis sends the Robinsons hurtling towards an unfamiliar planet, where they struggle to survive a harrowing night.", 0, listOf(), 8.1, listOf()),
                                                Episode("Another crash brings more travelers to the planet, as the Robinsons work to salvage their ship with help from their mysterious new companion.", 0, listOf(), 8.1, listOf())
                                        ), 8.1, 0, listOf(), listOf())
                        )
                        , listOf(), 8.8, listOf()
                ),

                Serie(4, "The Walking Dead", "L'histoire suit le personnage de Rick Grimes (interprété par Andrew Lincoln), adjoint du shérif du comté de Kings (en Géorgie) qui se réveille d'un coma de plusieurs semaines pour découvrir que la population a été ravagée par une épidémie post-apocalyptique inconnue qui transforme les êtres humains en morts-vivants", R.drawable.serie4,
                        listOf(
                                Season("La première saison introduit le personnage Rick Grimes, qui se réveille à l'hôpital après un long coma de plusieurs mois. Il découvre avec effarement que la population entière, ravagée par une épidémie d'origine inconnue", R.drawable.serie4,
                                        listOf(
                                                Episode("Rick Grimes, shérif, est blessé à la suite d'une course-poursuite. Il se retrouve dans le coma. Cependant, lorsqu'il se réveille dans l'hôpital, il ne découvre que désolation et cadavres. ", 0, listOf(), 8.1, listOf()),
                                                Episode("Rick parvient à s'échapper du tank grâce à l'aide de Glenn, dont il avait entendu la voix à la radio. ", 0, listOf(), 8.1, listOf())
                                        ), 8.1, 0, listOf(), listOf())
                        )
                        , listOf(), 8.8, listOf()
                )

        )

        actorList.forEach {
            it.comments = commentList.getRandomElements(5)
            it.movies = movieList.getRandomElements(5)
        }
        directorList.forEach {
            it.comments = commentList.getRandomElements(5)
            it.movies = movieList.getRandomElements(5)
        }

        movieList.forEach {
            it.cinemas = salleList.getRandomElements(4)
            it.actors = actorList.getRandomElements(4)
            it.directors = directorList.getRandomElements(4)
            it.comments = commentList.getRandomElements(4)
            it.linkedMovies = movieList.getRandomElements(4)
        }

        serieList.forEach {
            it.comments = commentList.getRandomElements(4)
            it.linkedSeries = serieList.getRandomElements(4)
            it.seasons.forEach {
                it.linkedActors = actorList.getRandomElements(4)
                it.comments = commentList.getRandomElements(4)
                it.epesods.forEach {
                    it.comments = commentList.getRandomElements(4)
                    it.diffusion = mutableListOf("BeIN Movies", "Antena 3", "Netflix", "Fox Movies", "HBO", "CBN", "MBC", "TOP", "Canal+").getRandomElements(3)
                }
            }
        }

//        favMovieList = movieList.getRandomElements(5)
//        favSerieList = serieList.getRandomElements(5)
//        favCinemaList = favCinemaList.getRandomElements(5)

        favMovieList = ArrayList(this.movieList.subList(0, 1))
        favSerieList = ArrayList(this.serieList.subList(0, 1))
        favCinemaList = ArrayList(this.salleList.subList(0, 4))

    }
}
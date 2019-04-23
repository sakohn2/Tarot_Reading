package com.example.lib;

/**
 * This class is responsible for taking the user information and some mystical insight and turning that
 * into a classic three-card tarot reading. The cards represent the past, present, and future of the
 * user. This class with use the tarot card API here: https://github.com/ekelen/tarot-api to receive
 * information on each card.
 */
public class TarotReading {
    /**
     * The User of the current reading-run. Will contain all of the information from the setup of the User,
     * including favorite color, season, and star-sign.
     */
    private User currentUser;
    private TarotCard[] currentCards;

    public TarotReading(User user) {
        currentUser = user;
        currentCards = usersCards();
    }

    private TarotCard[] usersCards() {
        TarotCard[] cards = new TarotCard[3];
        cards[0] = new TarotCard(someAlgorithm("past"));
        cards[1] = new TarotCard(someAlgorithm("present"));
        cards[2] = new TarotCard(someAlgorithm("future"));
        return cards;
    }

    /**
     * This is the true fortune telling method.
     *
     * This method takes the User data (set earlier) and the tense (past,future,present) and spits out
     * a tarot card short name. The idea is to then use the short name with the tarot card API to get and store
     * the data within a TarotCard which will be callable by the activities in order for the info to be displayed.
     * @param tense one of the three cards, past, present, or future
     * @return the short name of the tarot card chosen
     */
    private String someAlgorithm(String tense) {
        switch (tense) {
            case "past":
                return "ar01";
            case "present":
                return "ar02";
            case "future":
                return "ar03";
        }
        return "back";
    }
}

package com.example.lib;


public class TarotCard {
    /**
     * URL used to call the API for the specific card.
     */
    private String URL = "https://rws-cards-api.herokuapp.com/api/v1/cards/ar01";
    /**
     * The short name of the card as per the API.
     */
    private String shortName = "ar01";

    /**
     * Constructor for the tarotCard class.
     * @param sName the short name keyword used for calling the API tarot card info.
     */
    public TarotCard(String sName) {
        shortName = sName;
        URL = "https://rws-cards-api.herokuapp.com/api/v1/cards/" + shortName;
    }
    /**
     * A method for comparing tarot cards
     * @param other the tarot card in comparison
     * @return true if they rep the same card
     */
    public boolean equals(TarotCard other) {
        return this.shortName.equals(other.shortName);
    }
}

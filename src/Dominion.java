import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Dominion {
    public static final int MONEY_POS = 0;
    public static final int ACTIONS_POS = 1;
    public static final int BUYS_POS = 2;

    // **************************** MAIN GAME CYCLE METHODS ****************************
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Card> allCards = new ArrayList<>();

        File file = new File("iofiles/dominionCards.txt");
        Scanner fileScan = new Scanner(file);
        ArrayList<Card> actionCards = loadCards(fileScan, allCards);

        Scanner scnr = new Scanner(System.in);
        Random rand = new Random();
        rand.setSeed(2797);

        ArrayList<Card> drawPile = new ArrayList<>();
        ArrayList<Card> discardPile = new ArrayList<>();
        ArrayList<Card> cardsInMiddle = setup(scnr, allCards, actionCards, drawPile, rand);
        ArrayList<Card> trashedCards = new ArrayList<>();

        PointTokens pointTokenMat = new PointTokens();

        System.out.println("Press enter to begin your first turn.");
        scnr.nextLine();

        int[] info = new int[3];
        takeTurns(scnr, cardsInMiddle, drawPile, discardPile, trashedCards, info, pointTokenMat);

        endGame(drawPile, discardPile, pointTokenMat);

    }

    public static ArrayList<Card> loadCards(Scanner fileScan, ArrayList<Card> allCards) {
        fileScan.useDelimiter("\n\n");

        // Reading from the input file to add all the cards
        while (fileScan.hasNext()) {
            String line = fileScan.next();
            String[] cardInfo = line.split(",");
            Card card = new Card(cardInfo[0].trim(),
                    cardInfo[1].trim(),
                    cardInfo[2].trim(),
                    Integer.parseInt(cardInfo[3].trim()),
                    Integer.parseInt(cardInfo[4].trim()),
                    Integer.parseInt(cardInfo[5].trim()),
                    0);
            allCards.add(card);
        }

        // Printing out all the action cards and their descriptions so the user can choose
        System.out.println("ALL ACTION CARDS:\n");
        ArrayList<Card> actionCards = new ArrayList<>();
        for (Card card : allCards) {
            if (card.getType().contains("Action")) {
                System.out.println(card + "\n");
                actionCards.add(card);
            }
        }

        System.out.println();
        return actionCards;
    }

    public static ArrayList<Card> setup(Scanner scnr, ArrayList<Card> allCards, ArrayList<Card> actionCards,
                                        ArrayList<Card> drawPile, Random rand) {
        System.out.println("Welcome! Please choose a game setup\n");

        System.out.println("Original Dominion Set");
        System.out.println("1. First Game: Cellar, Market, Militia, Mine, Moat, Remodel, Smithy, Village, Woodcutter," +
                " Workshop");
        System.out.println("2. Big Money: Adventurer, Bureaucrat, Chancellor, Chapel, Feast, Laboratory, Market, " +
                "Mine, Moneylender, Throne Room");
        System.out.println("3. Interaction: Bureaucrat, Chancellor, Council Room, Festival, Library, Militia, Moat, " +
                "Spy, Thief, Village");
        System.out.println("4. Size Distortion: Cellar, Chapel, Feast, Gardens, Laboratory, Thief, Village, Witch, " +
                "Woodcutter, Workshop");
        System.out.println("5. Village Square: Bureaucrat, Cellar, Festival, Library, Market, Remodel, Smithy, Throne" +
                " Room, Village, Woodcutter\n");

        System.out.println("Intrigue + Prosperity expansions");
        System.out.println("6. Paths to Victory: Baron, Harem, Pawn, Shanty Town, Upgrade, Bishop, Counting House, " +
                "Goons, Monument, Peddler\n");

        System.out.println("Other");
        System.out.println("7. Custom");
        System.out.println("8. Random");

        // Filling the cards in "middle" with the action cards chosen by the user
        System.out.println("\nType the corresponding number:");
        ArrayList<Card> cardsInMiddle = new ArrayList<>();
        String setupStr = scnr.nextLine();
        int setup = getValidDigit(scnr, setupStr, 8);
        if (setup == 1) {
            cardsInMiddle.add(findCard("Cellar", allCards));
            cardsInMiddle.add(findCard("Market", allCards));
            cardsInMiddle.add(findCard("Militia", allCards));
            cardsInMiddle.add(findCard("Mine", allCards));
            cardsInMiddle.add(findCard("Moat", allCards));
            cardsInMiddle.add(findCard("Remodel", allCards));
            cardsInMiddle.add(findCard("Smithy", allCards));
            cardsInMiddle.add(findCard("Village", allCards));
            cardsInMiddle.add(findCard("Woodcutter", allCards));
            cardsInMiddle.add(findCard("Workshop", allCards));
        } else if (setup == 2) {
            cardsInMiddle.add(findCard("Adventurer", allCards));
            cardsInMiddle.add(findCard("Bureaucrat", allCards));
            cardsInMiddle.add(findCard("Chancellor", allCards));
            cardsInMiddle.add(findCard("Chapel", allCards));
            cardsInMiddle.add(findCard("Feast", allCards));
            cardsInMiddle.add(findCard("Laboratory", allCards));
            cardsInMiddle.add(findCard("Market", allCards));
            cardsInMiddle.add(findCard("Mine", allCards));
            cardsInMiddle.add(findCard("Moneylender", allCards));
            cardsInMiddle.add(findCard("Throne Room", allCards));
        } else if (setup == 3) {
            cardsInMiddle.add(findCard("Bureaucrat", allCards));
            cardsInMiddle.add(findCard("Chancellor", allCards));
            cardsInMiddle.add(findCard("Council Room", allCards));
            cardsInMiddle.add(findCard("Festival", allCards));
            cardsInMiddle.add(findCard("Library", allCards));
            cardsInMiddle.add(findCard("Militia", allCards));
            cardsInMiddle.add(findCard("Moat", allCards));
            cardsInMiddle.add(findCard("Spy", allCards));
            cardsInMiddle.add(findCard("Thief", allCards));
            cardsInMiddle.add(findCard("Village", allCards));
        } else if (setup == 4) {
            cardsInMiddle.add(findCard("Cellar", allCards));
            cardsInMiddle.add(findCard("Chapel", allCards));
            cardsInMiddle.add(findCard("Feast", allCards));
            cardsInMiddle.add(findCard("Gardens", allCards));
            cardsInMiddle.add(findCard("Laboratory", allCards));
            cardsInMiddle.add(findCard("Thief", allCards));
            cardsInMiddle.add(findCard("Village", allCards));
            cardsInMiddle.add(findCard("Witch", allCards));
            cardsInMiddle.add(findCard("Woodcutter", allCards));
            cardsInMiddle.add(findCard("Workshop", allCards));
        } else if (setup == 5) {
            cardsInMiddle.add(findCard("Bureaucrat", allCards));
            cardsInMiddle.add(findCard("Cellar", allCards));
            cardsInMiddle.add(findCard("Festival", allCards));
            cardsInMiddle.add(findCard("Library", allCards));
            cardsInMiddle.add(findCard("Market", allCards));
            cardsInMiddle.add(findCard("Remodel", allCards));
            cardsInMiddle.add(findCard("Smithy", allCards));
            cardsInMiddle.add(findCard("Throne Room", allCards));
            cardsInMiddle.add(findCard("Village", allCards));
            cardsInMiddle.add(findCard("Woodcutter", allCards));
        } else if (setup == 6) {
            cardsInMiddle.add(findCard("Baron", allCards));
            cardsInMiddle.add(findCard("Harem", allCards));
            cardsInMiddle.add(findCard("Pawn", allCards));
            cardsInMiddle.add(findCard("Shanty Town", allCards));
            cardsInMiddle.add(findCard("Upgrade", allCards));
            cardsInMiddle.add(findCard("Bishop", allCards));
            cardsInMiddle.add(findCard("Counting House", allCards));
            cardsInMiddle.add(findCard("Goons", allCards));
            cardsInMiddle.add(findCard("Monument", allCards));
            cardsInMiddle.add(findCard("Peddler", allCards));
        } else if (setup == 7) {    // Custom
            System.out.println();
            for (int i = 0; i < 10; ++i) {
                System.out.println("Type the name action card #" + (i + 1));
                String name = scnr.nextLine();
                while (notValid(allCards, cardsInMiddle, name)) {
                    System.out.println("Not a valid card, or you may have already entered this card. Try again:");
                    name = scnr.nextLine();
                }
                cardsInMiddle.add(findCard(name, allCards));
            }
        } else {    // Random
            int randNum = rand.nextInt(actionCards.size());
            for (int i=0; i<10; ++i) {
                String name = actionCards.get(randNum).getName();
                while (notValid(allCards, cardsInMiddle, name)) {
                    randNum = rand.nextInt(actionCards.size());
                    name = actionCards.get(randNum).getName();
                }
                cardsInMiddle.add(findCard(actionCards.get(randNum).getName(), allCards));
                randNum = rand.nextInt(actionCards.size());
            }
        }

        // Printing out the user's choice of action cards
        System.out.println();
        System.out.println("Available decks:");
        for (int i = 0; i < 9; ++i) {
            System.out.print(cardsInMiddle.get(i).getName() + ", ");
        }
        System.out.println(cardsInMiddle.get(9).getName());

        // Fill cardsInMiddle with the core cards
        int i=0;
        Card card = allCards.get(i);
        while (!card.getType().contains("Action")) {
            cardsInMiddle.add(card);
            ++i;
            card = allCards.get(i);
        }
        System.out.println();

        // Adding to the draw pile and shuffling
        System.out.println("Perfect!");
        System.out.println("Drawing 7 copper and 3 estate cards...");
        for (int j = 0; j < 7; ++j) {
            drawPile.add(findCard("Copper", cardsInMiddle));
        }
        for (int j = 0; j < 3; ++j) {
            drawPile.add(findCard("Estate", cardsInMiddle));
        }

        System.out.println("Shuffling...");
        Collections.shuffle(drawPile);
        System.out.println("Ready.\n");

        return cardsInMiddle;
    }

    /**
     * Validates the cards being input by the user in the custom setting
     * @return !isValid
     */
    public static boolean notValid(ArrayList<Card> allCards, ArrayList<Card> cardsInMiddle, String name) {
        boolean isValid = false;
        for (Card card : allCards) {
            if (card.getName().equals(name)) {
                isValid = true;
                for (Card cardMid : cardsInMiddle) {
                    if (cardMid != null && cardMid.getName().equals(name)) {
                        isValid = false;
                        break;
                    }
                }
            }
        }

        return !isValid;
    }

    /**
     *
     * Take turns until the number of provinces run out
     */
    public static void takeTurns(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                 ArrayList<Card> discardPile, ArrayList<Card> trashedCards, int[] info,
                                 PointTokens pointTokenMat) {
        ArrayList<Card> hand = new ArrayList<>();
        ArrayList<Card> cardsPlayed = new ArrayList<>();
        ArrayList<Card> actionsPlayed = new ArrayList<>();

        // Keep taking turns as long as there are provinces left
        while (findCard("Province", cardsInMiddle).getNumRemaining() > 0) {
            // Reset info
            info[MONEY_POS] = 0;
            info[ACTIONS_POS] = 1;
            info[BUYS_POS] = 1;

            // Draw a hand of 5 cards, calculate info, display to player
            System.out.println("********************** Your next hand of cards **********************");
            for (int i = 0; i < 5; ++i) {
                drawCard(hand, drawPile, discardPile, info);
                System.out.println(hand.get(i).cardInHand());
            }

            // Print money, actions, and buys
            System.out.println();
            printInfo(hand, info);

            // Play any leftover duration cards
            playDuration(scnr, actionsPlayed, cardsInMiddle, drawPile, discardPile, hand, trashedCards, info,
                    pointTokenMat);

            // Let the player choose menu options
            System.out.println();
            actionsPlayed = manageMenu(scnr, cardsInMiddle, hand, drawPile, discardPile, cardsPlayed, trashedCards,
                    info, pointTokenMat);

            // Discard the used hand and cards played
            discardPile.addAll(hand);
            discardPile.addAll(cardsPlayed);
            cardsPlayed.clear();
            hand.clear();

            // Test if the game is over (besides provinces, game may also end if 3 action card stacks are empty)
            int numEmptyStacks = 0;
            for (Card card : cardsInMiddle) {
                if ((card.getNumRemaining() == 0) && (card.getType().contains("Action"))) {
                    numEmptyStacks += 1;
                }
            }
            if (numEmptyStacks >= 3) {
                break;
            }
        }

    }

    /**
     *
     * Cycle through the menu choices until the player ends their turn
     * @return arraylist of the cards played that round (useful for keeping track of duration cards)
     */
    public static ArrayList<Card> manageMenu(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                  ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> cardsPlayed,
                                  ArrayList<Card> trashedCards, int[] info, PointTokens pointTokenMat) {
        printMenuOptions();
        String menuChoiceStr = scnr.nextLine();
        int menuChoice = getValidDigit(scnr, menuChoiceStr, 5);
        int unusedMerchants = 0;
        ArrayList<Card> actionsPlayed = new ArrayList<>();

        while (menuChoice != 5) {

            // Keeps track of whether the menu options should be reprinted after user chooses a number
            boolean noPrintOptions = false;

            if (menuChoice == 1) {  // Opponent bought province
                boughtProvince(cardsInMiddle);
            } else if (menuChoice == 2) {   // Opponent played attack card or council room
                playedAttackCard(scnr, cardsInMiddle, hand, drawPile, discardPile, trashedCards, info, pointTokenMat);
            } else if (menuChoice == 3) {   // Choose and play an action from your hand
                Card card = chooseAction(scnr, cardsInMiddle, drawPile, discardPile, hand, cardsPlayed,
                        trashedCards, info, pointTokenMat);
                actionsPlayed.add(card);
                unusedMerchants = checkMerchant(card, unusedMerchants, hand, info);
            } else if (menuChoice == 4) {   // Buy a card
                noPrintOptions = buyCard(scnr, cardsInMiddle, discardPile, hand, info, actionsPlayed);
                checkGoons(actionsPlayed, pointTokenMat);
            }

            if (findCard("Province", cardsInMiddle).getNumRemaining() == 0) {
                break;
            }

            if (!noPrintOptions) {
                printMenuOptions();
            }
            menuChoiceStr = scnr.nextLine();
            menuChoice = getValidDigit(scnr, menuChoiceStr, 5);
        }

        System.out.println();
        return actionsPlayed;
    }

    public static void endGame(ArrayList<Card> drawPile, ArrayList<Card> discardPile, PointTokens pointTokenMat) {
        System.out.println("\n\n------------------------------- GAME OVER -------------------------------");

        int endPoints = pointTokenMat.getPoints();  // Starting value
        int numCards = 0;
        int numGardens = 0;
        int numEstates = 0;
        int numDuchys = 0;
        int numProvinces = 0;
        int numOtherVictoryCards = 0;
        int numCurses = 0;

        // Put all the cards into one pile and count up the points
        discardPile.addAll(drawPile);
        for (Card card : discardPile) {
            ++numCards;
            if (card.getType().contains("Victory") || card.getType().equals("Curse")) {
                endPoints += card.getValue();
                switch (card.getName()) {
                    case "Estate":
                        numEstates += 1;
                        break;
                    case "Duchy":
                        numDuchys += 1;
                        break;
                    case "Province":
                        numProvinces += 1;
                        break;
                    case "Curse":
                        numCurses += 1;
                        break;
                    case "Harem":
                        numOtherVictoryCards += 1;
                        break;
                }
            } else if (card.getName().equals("Gardens")) {
                numGardens += 1;
            }
        }
        int numPointsPerGarden = discardPile.size()/10;
        endPoints += numPointsPerGarden*numGardens;

        System.out.println("You ended with " +
                numCards + " total cards, " +
                numEstates + " estates, " +
                numDuchys + " duchys, " +
                numProvinces + " provinces, " +
                numOtherVictoryCards + " other victory cards, " +
                pointTokenMat.getPoints() + " victory point tokens, " +
                numGardens + " gardens, and " +
                numCurses + " curses.");
        System.out.println("Total points: " + endPoints);
    }

    /**
     *
     * User chooses an action to play, then plays the action
     * @return the card chosen to play (for purposes of the merchant/duration cards)
     */
    public static Card chooseAction(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                    ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> cardsPlayed,
                                    ArrayList<Card> trashedCards, int[] info, PointTokens pointTokenMat) {
        if (info[ACTIONS_POS] == 0) {
            System.out.println("\nSorry, you have no more actions left. Cannot execute option.");
            System.out.println("Please enter a different choice:\n");
        } else {
            int option = 1;
            ArrayList<Card> options = new ArrayList<>();

            System.out.println();
            boolean hasAction = false;
            System.out.println("Your options:");
            for (Card card : hand) {
                if (card.getType().contains("Action")) {
                    System.out.println(option + ". " + card.getName());
                    options.add(card);
                    hasAction = true;
                    ++option;
                }
            }
            System.out.println((options.size() + 1) + ". Cancel\n");

            Card cardPlayed = new Card();
            if (hasAction) {
                System.out.println("Choose an action to play:");
                String optionStr = scnr.nextLine();
                int choice = getValidDigit(scnr, optionStr, option);
                if (choice != (options.size() + 1)) {
                    cardPlayed = options.get(choice - 1);
                    System.out.println("\nPlaying the " + cardPlayed.getName() + " card...");
                    hand.remove(cardPlayed);
                    info[ACTIONS_POS] -= 1;
                    if (!cardPlayed.getName().equals("Feast")) {
                        cardsPlayed.add(cardPlayed);
                    }
                    cardPlayed = playAction(scnr, cardPlayed, cardsInMiddle, drawPile, discardPile, hand,
                            trashedCards, info, pointTokenMat);
                    cardPlayed.increaseNumDurationPlays();
                } else {
                    System.out.println("Action cancelled.");
                }
                System.out.println();
                printInfo(hand, info);
            } else {
                System.out.println("Sorry, you don't have any action cards. Cannot execute option.");
                System.out.println("Please enter a different choice:");
            }

            return cardPlayed;
        }

        return new Card();

    }

    /**
     *
     * Manages the function calls for the correct action card
     * @return the card played (for purposes of the merchant/duration cards)
     */
    public static Card playAction(Scanner scnr, Card cardPlayed, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                     ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> trashedCards,
                                     int[] info, PointTokens pointTokenMat) {

        switch (cardPlayed.getName()) {
            case "Adventurer":
                adventurer(drawPile, discardPile, hand, info);
                break;
            case "Ambassador":
                ambassador(scnr, cardsInMiddle, discardPile, hand, trashedCards, info, false);
                break;
            case "Bandit":
                bandit(scnr, drawPile, discardPile, cardsInMiddle, trashedCards, false, info);
                break;
            case "Baron":
                baron(scnr, cardsInMiddle, discardPile, hand, info);
                break;
            case "Bishop":
                bishop(scnr, hand, trashedCards, info, pointTokenMat, false);
                break;
            case "Border Village":
                borderVillage(drawPile, discardPile, hand, info);
                break;
            case "Bureaucrat":
                bureaucrat(scnr, cardsInMiddle, hand, drawPile, false);
                break;
            case "Cellar":
                cellar(scnr, drawPile, discardPile, hand, info);
                break;
            case "Chancellor":
                chancellor(drawPile, discardPile, info);
                break;
            case "Chapel":
                chapel(scnr, hand, trashedCards, info);
                break;
            case "Council Room":
                councilRoom(scnr, drawPile, discardPile, hand, info, false);
                break;
            case "Counting House":
                countingHouse(scnr, discardPile, hand);
                break;
            case "Feast":
                feast(scnr, cardsInMiddle, discardPile, hand, trashedCards, info);
                break;
            case "Festival":
                festival(info);
                break;
            case "Goons":
                goons(scnr, hand, discardPile, info, false);
                break;
            case "Harbinger":
                harbinger(scnr, hand, drawPile, discardPile, info);
                break;
            case "Junk Dealer":
                junkDealer(scnr, drawPile, discardPile, hand, trashedCards, info);
                break;
            case "King's Court":
                cardPlayed = throneRoomKingCourt(scnr, drawPile, discardPile, hand, cardsInMiddle, trashedCards,
                    info, 3, pointTokenMat);
                break;
            case "Laboratory":
                laboratory(drawPile, discardPile, hand, info);
                break;
            case "Library":
                library(scnr, drawPile, discardPile, hand, info);
                break;
            case "Market":
                market(drawPile, discardPile, hand, info);
                break;
            case "Menagerie":
                menagerie(drawPile, discardPile, hand, info);
                break;
            case "Merchant":
                merchant(hand, drawPile, discardPile, info);
                break;
            case "Militia":
                militia(scnr, hand, discardPile, info, false);
                break;
            case "Mine":
                mine(scnr, cardsInMiddle, hand, trashedCards, info);
                break;
            case "Moat":
                moat(drawPile, discardPile, hand, info);
                break;
            case "Moneylender":
                moneylender(hand, trashedCards, info);
                break;
            case "Monument":
                monument(info, pointTokenMat);
                break;
            case "Mountebank":
                mountebank(scnr, discardPile, hand, cardsInMiddle, info, false);
                break;
            case "Pawn":
                pawn(scnr, drawPile, discardPile, hand, info);
                break;
            case "Peddler":
                peddler(drawPile, discardPile, hand, info);
                break;
            case "Poacher":
                poacher(scnr, drawPile, discardPile, hand, cardsInMiddle, info);
                break;
            case "Remake":
                remake(scnr, discardPile, hand, cardsInMiddle, trashedCards, info);
                break;
            case "Remodel":
                remodel(scnr, discardPile, hand, cardsInMiddle, trashedCards, info);
                break;
            case "Sentry":
                sentry(scnr, drawPile, discardPile, hand, trashedCards, info);
                break;
            case "Shanty Town":
                shantyTown(drawPile, discardPile, hand, info);
                break;
            case "Smithy":
                smithy(drawPile, discardPile, hand, info);
                break;
            case "Spy":
                spy(scnr, drawPile, discardPile, hand, info, false);
                break;
            case "Steward":
                steward(scnr, drawPile, discardPile, hand, trashedCards, info);
                break;
            case "Thief":
                thief(scnr, drawPile, discardPile, cardsInMiddle, trashedCards, false);
                break;
            case "Throne Room":
                cardPlayed = throneRoomKingCourt(scnr, drawPile, discardPile, hand, cardsInMiddle, trashedCards, info,
                        2, pointTokenMat);
                break;
            case "Upgrade":
                upgrade(scnr, drawPile, discardPile, hand, cardsInMiddle, trashedCards, info);
                break;
            case "Vassal":
                vassal(scnr, cardsInMiddle, drawPile, discardPile, hand, trashedCards, info, pointTokenMat);
                break;
            case "Village":
                village(drawPile, discardPile, hand, info);
                break;
            case "Wandering Minstrel":
                wanderingMinstrel(scnr, drawPile, discardPile, hand, info);
                break;
            case "Wharf":
                wharf(drawPile, discardPile, hand, info);
                break;
            case "Witch":
                witch(scnr, cardsInMiddle, drawPile, discardPile, hand, info, false);
                break;
            case "Woodcutter":
                woodcutter(info);
                break;
            case "Workshop":
                workshop(scnr, discardPile, cardsInMiddle);
                break;
        }
        return cardPlayed;
    }

    /**
     *
     * Allows user to buy a card
     * @return whether a card was actually bought or not (determines whether info should be reprinted)
     */
    public static boolean buyCard(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  int[] info, ArrayList<Card> actionsPlayed) {

        if (info[BUYS_POS] == 0) {
            System.out.println("Sorry, you have no more buys left. Cannot execute option.\n");
            System.out.println("Please enter a different choice:");
            return true;
        } else {
            subtractPeddlerPrice(cardsInMiddle, actionsPlayed);

            // Choose a card to buy
            ArrayList<Card> options = new ArrayList<>();
            int choice = chooseBuy(scnr, options, cardsInMiddle, info);

            // Buy the card
            System.out.println();
            if (choice != options.size() + 1) {
                Card cardBought = options.get(choice - 1);
                gainCard(discardPile, cardBought);

                // Make special things happen when certain cards are bought
                specialBuys(cardBought, scnr, cardsInMiddle, discardPile);

                // Decrease the buys and money left for this turn
                info[BUYS_POS] -= 1;
                info[MONEY_POS] -= cardBought.getCost();

                addPeddlerPrice(cardsInMiddle);
            } else {
                System.out.println("Buy cancelled.\n");
            }

            System.out.println();
            printInfo(hand, info);
            return false;
        }

    }

    /**
     *
     * Manages the function calls for the correct action-attack card
     * "opponent" parameter means that your OPPONENT played the card on you
     */
    public static void playedAttackCard(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                        ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                        ArrayList<Card> trashedCards, int[] info, PointTokens pointTokenMat) {

        System.out.println("\nWhich of these cards did your opponent play?");
        System.out.println("1. Bureaucrat\n2. Militia\n3. Spy\n4. Thief\n5. Witch\n6. Mountebank\n7. Ambassador\n8. " +
                "Bandit\n9. Goons\n10. Council Room\n11. Bishop");
        System.out.println("\nChoose an option:");

        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 10);
        switch (choice) {
            case 1:
                bureaucrat(scnr, cardsInMiddle, hand, drawPile, true);
                break;
            case 2:
                militia(scnr, hand, discardPile, info, true);
                break;
            case 3:
                spy(scnr, drawPile, discardPile, hand, info, true);
                break;
            case 4:
                thief(scnr, drawPile, discardPile, cardsInMiddle, trashedCards, true);
                break;
            case 5:
                witch(scnr, cardsInMiddle, drawPile, discardPile, hand, info, true);
                break;
            case 6:
                mountebank(scnr, discardPile, hand, cardsInMiddle, info, true);
                break;
            case 7:
                ambassador(scnr, cardsInMiddle, discardPile, hand, trashedCards, info, true);
                break;
            case 8:
                bandit(scnr, drawPile, discardPile, cardsInMiddle, trashedCards, true, info);
                break;
            case 9:
                goons(scnr, hand, discardPile, info, true);
                break;
            case 10:
                councilRoom(scnr, drawPile, discardPile, hand, info, true);
                break;
            case 11:
                bishop(scnr, hand, trashedCards, info, pointTokenMat, true);
                break;
        }
    }


    // ************************************** HELPER METHODS **************************************

    public static void attack(Scanner scnr) {
        System.out.println("If your opponent does not have a reaction card, they must now choose option 2 of " +
                "their menu to be affected by your attack.");
        System.out.println("Press enter when they have done so:");
        scnr.nextLine();
    }

    /**
     *
     * Checks for and plays any leftover duration cards at the start of a new turn
     */
    public static void playDuration(Scanner scnr, ArrayList<Card> actionsPlayed, ArrayList<Card> cardsInMiddle,
                                    ArrayList<Card> drawPile,
                                    ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> trashedCards,
                                    int[] info, PointTokens pointTokenMat) {
        for (Card card : actionsPlayed) {
            if (card.getType().contains("Duration")) {
                for (int i=0; i<card.getNumDurationPlays(); ++i) {
                    System.out.println("Playing the " + card.getName() + " duration card...");
                    playAction(scnr, card, cardsInMiddle, drawPile, discardPile, hand, trashedCards, info,
                            pointTokenMat);
                }
                System.out.println("\nNew info:");
                printInfo(hand, info);
            }
            card.setNumDurationPlays(0);
        }
    }

    /**
     *
     * Choose a card to buy from the options available based on money
     * @return number corresponding to the user's choice of card to buy
     */
    public static int chooseBuy(Scanner scnr, ArrayList<Card> options, ArrayList<Card> cardsInMiddle, int[] info) {
        int option = 1;

        System.out.println("Your options:");
        for (Card card : cardsInMiddle) {   // Make sure the options are only cards the user can afford
            if ((card.getCost() <= info[MONEY_POS]) && card.getNumRemaining() > 0) {
                System.out.println(option + ". " + card + "\n");
                options.add(card);
                ++option;
            }
        }
        System.out.println((options.size() + 1) + ". Cancel");

        System.out.println();
        System.out.println("Choose an option to buy:");
        String optionStr = scnr.nextLine();
        return getValidDigit(scnr, optionStr, option);
    }

    public static void checkGoons(ArrayList<Card> actionsPlayed, PointTokens pointTokenMat) {
        int numGoons = 0;
        for (Card card : actionsPlayed) {
            if (card.getName().equals("Goons")) {
                ++numGoons;
                pointTokenMat.increasePoints(1);
            }
        }

        if (numGoons > 0) {
            System.out.println("You have " + numGoons + " Goons in play. Gaining victory point token(s)...");
            System.out.println("Done.\n");
        }
    }

    /**
     *
     * Makes extra things happen when province or border village is bought
     */
    public static void specialBuys(Card cardBought, Scanner scnr, ArrayList<Card> cardsInMiddle,
                                   ArrayList<Card> discardPile) {
        if (cardBought.getName().equals("Province")) {
            System.out.print("Provinces remaining: ");
            System.out.println(findCard("Province", cardsInMiddle).getNumRemaining());
        } else if (cardBought.getName().equals("Border Village")) {
            System.out.println("You may now gain a cheaper card.");
            Card cardToGain = printOptions(scnr, cardsInMiddle, "Border Village", 5);
            gainCard(discardPile, cardToGain);
        }
    }

    public static void boughtProvince(ArrayList<Card> cardsInMiddle) {

        findCard("Province", cardsInMiddle).decreaseNumRemaining();
        System.out.print("Provinces remaining: ");
        System.out.println(findCard("Province", cardsInMiddle).getNumRemaining());
        System.out.println();
    }

    public static void printMenuOptions() {
        System.out.println("------------- MENU OPTIONS -------------");
        System.out.println("1. Opponent bought a province");
        System.out.println("2. Opponent played an attack card or Council Room");
        System.out.println("3. Play action");
        System.out.println("4. Buy card");
        System.out.println("5. End turn");

        System.out.println();
        System.out.println("Enter your choice:");
    }

    public static void printInfo(ArrayList<Card> hand, int[] info) {
        System.out.println("Buys: " + info[BUYS_POS]);
        System.out.println("Money: " + info[MONEY_POS]);
        System.out.println("Actions: " + info[ACTIONS_POS]);
        System.out.println("Available action cards:");
        boolean hasAction = false;
        for (Card card : hand) {
            if (card.getType().contains("Action")) {
                System.out.println("\t" + card.cardInHand());
                hasAction = true;
            }
        }
        if (!hasAction) {
            System.out.println("\tNone");
        }

        System.out.println();

    }

    public static Card drawCard(ArrayList<Card> hand, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                int[] info) {
        // If draw pile has <1 card, shuffle discard pile and add it to bottom of draw pile
        if (drawPile.size() < 1) {
            if (discardPile.size() > 0) {
                Collections.shuffle(discardPile);
                drawPile.addAll(discardPile);
                discardPile.clear();
            } else {
                // problem
                // do nothing
                return new Card();
            }
        }
        Card card = drawPile.get(0);
        hand.add(card);
        if (card.getType().contains("Treasure")) {
            info[MONEY_POS] += card.getValue();
        }

        drawPile.remove(0);

        return card;
    }

    public static void gainCard(ArrayList<Card> discardPile, Card cardToGain) {
        if (cardToGain.getNumRemaining() > 0) {
            System.out.println("Gaining the " + cardToGain.getName() + " card...");
            discardPile.add(cardToGain);
            cardToGain.decreaseNumRemaining();
            System.out.println("Done.\n");
        } else {
            System.out.println("The " + cardToGain.getName() + " supply is out of cards.\n");
        }
    }

    public static void discardCard(ArrayList<Card> list, ArrayList<Card> discardPile, Card cardToDiscard, int[] info,
                                   boolean hand) {
        System.out.println("Discarding the " + cardToDiscard.getName() + " card...");
        discardPile.add(cardToDiscard);
        if (cardToDiscard.getType().contains("Treasure") && hand) {
            info[MONEY_POS] -= cardToDiscard.getValue();
        }
        list.remove(cardToDiscard);
        System.out.println("Done.\n");
    }

    public static void trashCard(ArrayList<Card> list, ArrayList<Card> trashedCards, Card cardToTrash, int[] info,
                                 boolean hand) {
        System.out.println("Trashing the " + cardToTrash.getName() + " card...");

        if (cardToTrash.getType().contains("Treasure") && hand) {
            info[MONEY_POS] -= cardToTrash.getValue();
        }

        list.remove(cardToTrash);
        trashedCards.add(cardToTrash);
        System.out.println("Done.\n");
    }

    public static void trashMultiple(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> trashedCards, int[] info,
                                     int numToTrash) {
        ArrayList<Card> options = new ArrayList<>();

        int option = 1;
        for (Card card : hand) {
            System.out.println(option + ". " + card);
            options.add(card);
            ++option;
        }

        System.out.println();

        System.out.println("Choose the cards you would like to trash, each separated by a space:");
        String choices = scnr.nextLine();
        Scanner scanChoices = new Scanner(choices);

        for (int i=0; i<numToTrash; ++i) {
            if (scanChoices.hasNextInt()) {
                option = scanChoices.nextInt();
                System.out.println();
                Card chosenCard = options.get(option - 1);
                trashCard(hand, trashedCards, chosenCard, info, true);
            }
        }

        if (scanChoices.hasNextInt()) {
            System.out.println("\nLimit has been reached. Could not trash any more cards.\n");
        }
    }

    // Finds the position of a given card to allow access
    public static Card findCard(String name, ArrayList<Card> cards) {
        Card card = new Card();
        for (Card value : cards) {
            if (value.getName().equals(name)) {
                card = value;
            }
        }
        return card;
    }

    public static int getValidDigit(Scanner scnr, String someString, int range) {
        Scanner checkScan = new Scanner(someString);
        int validDigit = 0;

        // If it's an integer, store it in validDigit
        if (checkScan.hasNextInt()) {
            validDigit = checkScan.nextInt();
        }

        // Re-prompt for input until validatedGuess is an int in the correct range
        while ((validDigit < 1) || (validDigit > range)) {
            System.out.println("Your choice must be in the range 1-" + range + ". Try again.");
            System.out.println("Please choose again:");
            someString = scnr.nextLine();
            checkScan = new Scanner(someString);
            if (checkScan.hasNextInt()) {
                validDigit = checkScan.nextInt();
            }
        }

        return validDigit;

    }

    /**
     *
     * Algorithm for printing a user's option
     *  (this function is called pretty much whenever the user has to choose something)
     * The condition for the options to be printed depends on the card in question
     * @return the card chosen (or a default card if no options were available)
     */
    public static Card printOptions(Scanner scnr, ArrayList<Card> arrayList, String cardName, int cost) {
        int option = 1;
        ArrayList<Card> options = new ArrayList<>();

        System.out.println("Your options:");
        for (Card card : arrayList) {
            boolean condition = true;   // assume true, meaning you'd print every card in the list

            switch (cardName) {
                case "Border Village":
                    condition = card.getCost() <= cost;
                    break;
                case "Feast":
                    condition = card.getCost() <= 5;
                    break;
                case "Mine":
                    condition = card.getType().contains("Treasure") && !card.getName().equals("Gold");
                    break;
                case "Remake-Gain":
                    condition = (card.getCost() == (1 + cost));
                    break;
                case "Remodel-Gain":
                    condition = card.getCost() <= (2 + cost);
                    break;
                case "Throne Room/King's Court":
                    condition = card.getType().contains("Action") &&
                                !card.getName().equals("Throne Room") &&
                                !card.getName().equals("King's Court");
                    break;
                case "Workshop":
                    condition = card.getCost() <= 4;
                    break;
            }

            // Print the correct options
            if (condition) {
                System.out.println(option + ". " + card.getName());
                options.add(card);
                ++option;
            }
        }

        if (options.size() > 0) {
            System.out.println("\nEnter your choice:");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, (option - 1));
            return options.get(choice - 1);
        }

        return new Card();
    }

    /**
     *
     * This method is called each time an action is played
     * Checks if the action played was a merchant, and if so, it adds to the number of unused merchants for that turn
     * @return the new number of unused merchants
     */
    public static int checkMerchant(Card actionPlayed, int unusedMerchants, ArrayList<Card> hand, int[] info) {
        if (actionPlayed.getName().equals("Merchant")) {
            ++unusedMerchants;
        }
        return useMerchant(hand, unusedMerchants, info);
    }

    /**
     *
     * This method is called each time an action is played
     * Uses the merchant if there is currently one unused, and a silver in play
     * @return the new number of unused merchants
     */
    public static int useMerchant(ArrayList<Card> hand, int unusedMerchants, int[] info) {
        boolean hasSilver = false;

        for (Card card : hand) {
            if (card.getName().equals("Silver")) {
                hasSilver = true;
                break;
            }
        }

        // If merchant(s) were played this round and there is a silver in hand, add +$1 for each unused merchant
        if (unusedMerchants > 0 && hasSilver) {
            for (int i=0; i<unusedMerchants; ++i) {
                System.out.println("You now have a silver and merchant in play. Adding +$1...");
                info[MONEY_POS] += 1;
                System.out.println("Done.\n");
            }
            unusedMerchants = 0;
            System.out.println("New info:");
            printInfo(hand, info);
        }

        return unusedMerchants;
    }

    public static void subtractPeddlerPrice(ArrayList<Card> cardsInMiddle, ArrayList<Card> actionsPlayed) {
        Card peddler = findCard("Peddler", cardsInMiddle);
        peddler.setCost(8 - (actionsPlayed.size()*2));
        if (peddler.getCost() < 0) {
            peddler.setCost(0);
        }
    }

    public static void addPeddlerPrice(ArrayList<Card> cardsInMiddle) {
        findCard("Peddler", cardsInMiddle).setCost(8);
    }



    // **************************************** ACTION CARDS ****************************************
    public static void adventurer(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  int[] info) {
        for (int i=0; i<2; ++i) {
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("Drew a(n) " + card.getName());
            while (!card.getType().contains("Treasure")) {
                System.out.println("Not a treasure card. Discarding and drawing another...");
                discardCard(hand, discardPile, card, info, true);
                card = drawCard(hand, drawPile, discardPile, info);
                System.out.println("Drew a(n) " + card.getName());
            }
            System.out.println();
        }
    }

    public static void ambassador(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> discardPile,
                                  ArrayList<Card> hand, ArrayList<Card> trashedCards, int[] info, boolean opponent) {
        if (!opponent) {
            System.out.println("Choose a card from your hand to return to the Supply.");
            Card cardToReturn = printOptions(scnr, hand, "Ambassador", 0);

            int numCopies = 0;
            for (Card card : hand) {
                if (card.getName().equals(cardToReturn.getName())) {
                    ++numCopies;
                }
            }

            int choice = 2;
            if (numCopies > 1) {
                System.out.println("You have more than 1 copy of this card. Would you like to return them both? (1) " +
                        "yes, (2) no:");
                String optionStr = scnr.nextLine();
                choice = getValidDigit(scnr, optionStr, 2);
            }

            trashCard(hand, trashedCards, cardToReturn, info, true);
            if (choice == 1) {  // Remove the card again if they wanted both copies trashed.
                trashCard(hand, trashedCards, cardToReturn, info, true);
            }

            attack(scnr);
        } else {
            System.out.println("How many cards is your opponent giving to you? Enter 1 or 2:");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, 2);

            System.out.println("Enter the name of the card your opponent said to gain:");
            String name = scnr.nextLine();

            // Gain the card(s) (with data validation)
            boolean isValid = false;
            for (Card card : cardsInMiddle) {
                if (card.getName().equals(name)) {
                    isValid = true;
                    gainCard(discardPile, card);
                    if (choice == 2) {
                        gainCard(discardPile, card);
                    }
                    break;
                }
            }
            while (!isValid) {
                System.out.println("Not a valid card. Try again:");
                name = scnr.nextLine();
                isValid = false;
                for (Card card : cardsInMiddle) {
                    if (card.getName().equals(name)) {
                        isValid = true;
                        gainCard(discardPile, card);
                        if (choice == 2) {
                            gainCard(discardPile, card);
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void bandit(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                              ArrayList<Card> cardsInMiddle, ArrayList<Card> trashedCards, boolean opponent,
                              int[] info) {
        if (!opponent) {
            System.out.println("Gaining a gold...");
            discardPile.add(findCard("Gold", cardsInMiddle));
            System.out.println("Done.\n");
            attack(scnr);
        } else {
            if (drawPile.size() < 2) {  // Make sure there are enough cards to draw
                Collections.shuffle(discardPile);
                drawPile.addAll(discardPile);
                discardPile.clear();
            }
            Card topCard = drawPile.get(0);
            Card secondCard = drawPile.get(1);
            System.out.println("The top 2 cards of your deck are: " + topCard.getName() + " and " + secondCard.getName() + ".");
            boolean topCardTreasure = (topCard.getType().contains("Treasure") && !topCard.getName().equals("Copper"));
            boolean nextCardTreasure = (secondCard.getType().contains("Treasure") && !secondCard.getName().equals(
                    "Copper"));
            if (topCardTreasure && !nextCardTreasure) {
                trashCard(drawPile, trashedCards, topCard, info, false);
                discardCard(drawPile, discardPile, secondCard, info, false);
            } else if (!topCardTreasure && nextCardTreasure) {
                trashCard(drawPile, trashedCards, secondCard, info, false);
                discardCard(drawPile, discardPile, topCard, info, false);
            } else if (topCardTreasure) {
                System.out.println("You have 2 treasure cards. Which would you prefer to trash?");
                System.out.println("1. " + topCard.getName() + "\n2. " + secondCard.getName());
                String optionStr = scnr.nextLine();
                int choice = getValidDigit(scnr, optionStr, 2);
                if (choice == 1) {
                    System.out.println("Trashing the " + topCard.getName() + " card...");
                    trashedCards.add(topCard);
                    System.out.println("Discarding the " + secondCard.getName() + " card...");
                    discardPile.add(secondCard);
                } else {
                    System.out.println("Trashing the " + secondCard.getName() + " card...");
                    trashedCards.add(secondCard);
                    System.out.println("Discarding the " + topCard.getName() + " card...");
                    discardPile.add(topCard);
                }
            } else {
                System.out.println("Neither is a treasure card other than copper. Discarding them both...");
                discardPile.add(topCard);
                discardPile.add(secondCard);
            }
            System.out.println("Done.");
        }
    }

    public static void baron(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> discardPile, ArrayList<Card> hand,
                             int[] info) {
        System.out.println("Adding +1 Buy...\n");
        info[BUYS_POS] += 1;

        boolean discardedEstate = false;
        for (Card card : hand) {
            if (card.getName().equals("Estate")) {
                System.out.println("Would you like to discard an estate for +$4? (1) yes, (2) no:");
                String choiceStr = scnr.nextLine();
                int choice = getValidDigit(scnr, choiceStr, 2);
                if (choice == 1) {
                    discardedEstate = true;
                    discardCard(hand, discardPile, card, info, true);
                    System.out.println("Adding +$4...");
                    info[MONEY_POS] += 4;
                }
                break;
            }
        }

        if (!discardedEstate) {
            System.out.println("No Estate discarded. Gaining an Estate...");
            gainCard(discardPile, findCard("Estate", cardsInMiddle));
            System.out.println("Done.\n");
        }


    }

    public static void bishop(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> trashedCards, int[] info,
                              PointTokens pointTokenMat, boolean opponent) {
        System.out.println("Choose a card from your hand to trash.");
        Card cardToTrash = printOptions(scnr, hand, "Bishop", 0);
        trashCard(hand, trashedCards, cardToTrash, info, true);

        if (!opponent) {
            int pointsToGain = cardToTrash.getCost()/2;
            System.out.println("Gaining " + pointsToGain + " point token(s) plus 1 free point token\n");
            pointTokenMat.increasePoints(pointsToGain + 1);

            System.out.println("Your opponent(s) may trash a card.");
            System.out.println("Press enter when they have done so:");
            scnr.nextLine();
        }
    }

    public static void borderVillage(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                     int[] info) {
        System.out.println("Drawing a card...");
        Card card = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + card.getName() + " card.\n");

        System.out.println("Adding +2 Actions...");
        info[ACTIONS_POS] += 2;
    }

    public static void bureaucrat(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand, ArrayList<Card> drawPile,
                                  boolean opponent) {
        if (!opponent) {
            System.out.println("Adding a silver card to your deck...");
            drawPile.add(0, findCard("Silver", cardsInMiddle));
            findCard("Silver", cardsInMiddle).decreaseNumRemaining();
            System.out.println("Done.\n");
            attack(scnr);
        } else {
            boolean hasVictory = false;
            for (Card card : hand) {
                if (card.getType().contains("Victory")) {
                    System.out.println("\nPlacing the " + card.getName() + " card on your deck...");
                    drawPile.add(0, card);
                    hand.remove(card);
                    System.out.println("Done.");
                    hasVictory = true;
                    break;
                }
            }
            if (!hasVictory) {
                System.out.println("You have no victory cards. No cards added to your draw pile.");
            }
        }
    }

    public static void cellar(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                              ArrayList<Card> hand, int[] info) {

        System.out.println("Adding +1 Action...");
        info[ACTIONS_POS] += 1;
        System.out.println("Done.\n");

        ArrayList<Card> options = new ArrayList<>();

        int option = 1;
        for (Card card : hand) {
            System.out.println(option + ". " + card);
            options.add(card);
            ++option;
        }

        System.out.println();

        System.out.println("Choose the number(s) of the card(s) you would like to discard, each separated by a space:");
        String choices = scnr.nextLine();
        Scanner scanChoices = new Scanner(choices);

        while (scanChoices.hasNextInt()) {
            option = scanChoices.nextInt();
            System.out.println();
            Card chosenCard = options.get(option - 1);
            System.out.println("Discarding the " + chosenCard.getName() + " card...");
            discardCard(hand, discardPile, chosenCard, info, true);

            System.out.println("Drawing a replacement...");
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("Drew the " + card.getName() + " card.");
        }

    }

    public static void chancellor(ArrayList<Card> drawPile, ArrayList<Card> discardPile, int[] info) {
        System.out.println("Adding $2...");
        info[MONEY_POS] += 2;
        System.out.println("Done.\n");
        System.out.println("Moving the draw pile to the discard pile...");
        while (drawPile.size() > 0) {
            discardPile.add(drawPile.get(0));
            drawPile.remove(0);
        }
        System.out.println("Done.");
    }

    public static void chapel(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> trashedCards, int[] info) {
        trashMultiple(scnr, hand, trashedCards, info, 4);
    }

    public static void councilRoom(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                   ArrayList<Card> hand, int[] info, boolean opponent) {
        if (!opponent) {
            System.out.println("+1 buy:");
            info[BUYS_POS] += 1;
            printInfo(hand, info);

            System.out.println("Drawing 4 cards...");
            for (int i=0; i<4; ++i) {
                Card card = drawCard(hand, drawPile, discardPile, info);
                System.out.println("You drew the " + card.getName() + " card.");
            }

            System.out.println();
            System.out.println("Your opponent must now choose option 2 of their menu to draw a card.");
            System.out.println("Press enter when they have done so:");
            scnr.nextLine();
        } else {
            System.out.println("\nDrawing a card...");
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + card.getName() + " card.\n");
            printInfo(hand, info);
            System.out.println();
        }

    }

    public static void countingHouse(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand) {
        // Gather all the coppers
        ArrayList<Card> coppers = new ArrayList<>();
        for (Card card : discardPile) {
            if (card.getName().equals("Copper")) {
                discardPile.remove(card);
                coppers.add(card);
            }
        }

        System.out.println("There are " + coppers.size() + " cards in your discard pile.");
        System.out.println("How many would you like to put into your hand?");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, coppers.size());

        // Put the coppers in hand
        for (int i=0; i<choice; ++i) {
            hand.add(coppers.get(0));
            coppers.remove(0);
        }

        // If there are still coppers that the player didn't put in hand, discard them
        while (coppers.size() > 0) {
            discardPile.add(coppers.get(0));
            coppers.remove(0);
        }
    }

    public static void feast(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> discardPile,
                             ArrayList<Card> hand, ArrayList<Card> trashedCards, int[] info) {
        trashCard(hand, trashedCards, findCard("Feast", cardsInMiddle), info, true);
        System.out.println();
        System.out.println("You may now gain a card costing up to $5.\n");

        Card cardGained = printOptions(scnr, cardsInMiddle, "Feast", 0);

        if (!cardGained.getName().equals("")) {
            gainCard(discardPile, cardGained);
        }
    }

    public static void festival(int[] info) {
        System.out.println("Adding +2 Actions, +1 Buy, +$2:");
        info[ACTIONS_POS] += 2;
        info[BUYS_POS] += 1;
        info[MONEY_POS] += 2;
    }

    public static void goons(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> discardPile, int[] info,
                             boolean opponent) {
        if (!opponent) {
            System.out.println("Adding +$2 and +1 Buy...");
            info[MONEY_POS] += 2;
            info[BUYS_POS] += 1;
            System.out.println("Done.\n");
            attack(scnr);
        } else {
            System.out.println("You must discard down to 3 cards.");
            while (hand.size() > 3) {
                System.out.println();
                for (int i=0; i<hand.size(); ++i) {
                    System.out.println((i+1) + ". " + hand.get(i).cardInHand());
                }
                System.out.println("\nChoose a card to discard (" + (hand.size() - 3) + " left):");
                String optionStr = scnr.nextLine();
                int option = getValidDigit(scnr, optionStr, hand.size());
                Card cardToDiscard = hand.get(option - 1);
                System.out.println("\nDiscarding the " + cardToDiscard.getName() + " card...");
                discardCard(hand, discardPile, cardToDiscard, info, true);
                System.out.println("Done.");
            }
            System.out.println("\nNew hand info:");
            printInfo(hand, info);
        }
    }

    public static void harbinger(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> drawPile,
                                 ArrayList<Card> discardPile, int[] info) {
        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + cardDrawn.getName() + " card.\n");

        System.out.println("Adding +1 Action...");
        info[ACTIONS_POS] += 1;
        System.out.println("Done.\n");

        System.out.println("You may now pick a card from your discard pile to add to your deck.");
        if (discardPile.size() > 0) {
            Card cardToAdd = printOptions(scnr, discardPile, "Harbinger", 0);
            System.out.println("Placing the " + cardToAdd.getName() + " card in your draw pile...");
            drawPile.add(0, cardToAdd);
            discardPile.remove(cardToAdd);
            System.out.println("Done.\n");
        } else {
            System.out.println("Sorry, you have no cards in your discard pile.\n");
        }

    }

    public static void junkDealer(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                  ArrayList<Card> hand, ArrayList<Card> trashedCards, int[] info) {
        System.out.println("Adding +1 Action and +$1...");
        info[ACTIONS_POS] += 1;
        info[MONEY_POS] += 1;
        System.out.println("Done\n");
        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + cardDrawn.getName() + " card.\n");

        System.out.println("Choose a card to trash.\n");
        Card cardToTrash = printOptions(scnr, hand, "Junk Dealer", 0);
        if (!cardToTrash.getType().equals("")) {
            trashCard(hand, trashedCards, cardToTrash, info, true);
        }
    }

    public static void laboratory(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  int[] info) {
        System.out.println("Adding +1 Action:");
        info[ACTIONS_POS] += 1;
        printInfo(hand, info);
        System.out.println();
        System.out.println("Drawing 2 cards...");
        for (int i=0; i<2; ++i) {
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + card.getName() + " card.");
        }
    }

    public static void library(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                               ArrayList<Card> hand, int[] info) {
        while (hand.size() < 7) {
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + card.getName() + " card.");
            if (card.getType().contains("Action")) {
                System.out.println("Would you like to discard this card? (1) yes, (2) no:");
                String optionStr = scnr.nextLine();
                int choice = getValidDigit(scnr, optionStr, 2);
                if (choice == 1) {
                    discardCard(hand, discardPile, card, info, true);
                }
            }
        }
    }

    public static void menagerie(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
        System.out.println("Adding +1 Action...");
        info[ACTIONS_POS] += 1;

        boolean differentCards = true;
        for (int i=0; i<hand.size(); ++i) {
            for (int j=1; j<hand.size(); ++j) {
                if (hand.get(j).getName().equals(hand.get(i).getName())) {
                    differentCards = false;
                    break;
                }
            }
        }

        if (differentCards) {
            System.out.println("Your hand contains all different cards. Drawing 3 cards...");
            for (int i=0; i<3; ++i) {
                System.out.println("Drawing a card...");
                Card card = drawCard(hand, drawPile, discardPile, info);
                System.out.println("You drew the " + card.getName() + " card.\n");
            }
        } else {
            System.out.println("Your hand does not contain all different cards.\n");
            System.out.println("Drawing a card...");
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + card.getName() + " card.\n");
        }

    }

    public static void market(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
        System.out.println("Drawing a card...");
        Card card = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + card.getName() + " card.\n");

        System.out.println("Adding +1 Action, +1 buy, +$1...");
        info[ACTIONS_POS] += 1;
        info[BUYS_POS] += 1;
        info[MONEY_POS] += 1;
        System.out.println("Done.");
    }

    public static void merchant(ArrayList<Card> hand, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                int[] info) {
        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + cardDrawn.getName() + " card.\n");

        System.out.println("Adding +1 Action...");
        info[ACTIONS_POS] += 1;
        System.out.println("Done.\n");
    }

    public static void militia(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> discardPile, int[] info,
                               boolean opponent) {
        if (!opponent) {
            System.out.println("Adding +$2...");
            info[MONEY_POS] += 2;
            System.out.println("Done.\n");
            attack(scnr);
        } else {
            System.out.println("You must discard down to 3 cards.");
            while (hand.size() > 3) {
                System.out.println();
                for (int i=0; i<hand.size(); ++i) {
                    System.out.println((i+1) + ". " + hand.get(i).cardInHand());
                }
                System.out.println("\nChoose a card to discard (" + (hand.size() - 3) + " left):");
                String optionStr = scnr.nextLine();
                int option = getValidDigit(scnr, optionStr, hand.size());
                Card cardToDiscard = hand.get(option - 1);
                System.out.println("\nDiscarding the " + cardToDiscard.getName() + " card...");
                discardCard(hand, discardPile, cardToDiscard, info, true);
                System.out.println("Done.");
            }
            System.out.println("\nNew hand info:");
            printInfo(hand, info);
        }
    }

    public static void mine(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                            ArrayList<Card> trashedCards, int[] info) {
        System.out.println("Choose a treasure card from your hand to trash.\n");

        Card cardTrashed = printOptions(scnr, hand, "Mine", 0);
        if (!cardTrashed.getType().equals("")) {
            trashCard(hand, trashedCards, cardTrashed, info, true);
        }

        // Gain a card costing 3 more
        Card cardToGain = new Card();
        if (cardTrashed.getName().equals("Copper")) {
            cardToGain = findCard("Silver", cardsInMiddle);
        } else if (cardTrashed.getName().equals("Silver")) {
            cardToGain = findCard("Gold", cardsInMiddle);
        }

        System.out.println();
        System.out.println("Adding a " + cardToGain.getName() + " to your hand...");
        hand.add(cardToGain);
        cardToGain.decreaseNumRemaining();
        info[MONEY_POS] += cardToGain.getValue();
        System.out.println("Done.\n");
    }

    public static void moat(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
        System.out.println("Drawing 2 cards...");
        for (int i=0; i<2; ++i) {
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + card.getName() + " card.");
        }
    }

    public static void moneylender(ArrayList<Card> hand, ArrayList<Card> trashedCards, int[] info) {
        boolean trashedCopper = false;
        for (Card card : hand) {
            if (card.getName().equals("Copper")) {
                trashCard(hand, trashedCards, card, info, true);
                trashedCopper = true;
                break;
            }
        }

        if (trashedCopper) {
            System.out.println("Adding +$3...");
            info[MONEY_POS] += 3;
        } else {
            System.out.println("Sorry, you did not have a copper to trash. No extra money was added.");
        }

    }

    public static void monument(int[] info, PointTokens pointTokenMat) {
        System.out.println("Adding +$2...\n");
        info[MONEY_POS] += 2;
        System.out.println("Gaining a victory point token...");
        pointTokenMat.increasePoints(1);
    }

    public static void mountebank(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  ArrayList<Card> cardsInMiddle, int[] info, boolean opponent) {
        if (!opponent) {
            System.out.println("Adding +$2...");
            info[MONEY_POS] += 2;
            System.out.println("Done.\n");
            attack(scnr);
            System.out.println("Did your opponent gain a curse? (1) yes, (2) no:");
            String option = scnr.nextLine();
            int choice = getValidDigit(scnr, option, 2);
            if (choice == 1) {
                findCard("Curse", cardsInMiddle).decreaseNumRemaining();
            }
        } else {
            boolean hasCurse = false;
            for (Card card : hand) {
                if (card.getType().equals("Curse")) {
                    hasCurse = true;
                    System.out.println("Discarding the curse...");
                    discardCard(hand, discardPile, card, info, true);
                    System.out.println("Done.");
                    break;
                }
            }
            if (!hasCurse) {
                System.out.println("You do not have a curse to discard. Gaining a curse and a copper...");
                discardPile.add(findCard("Curse", cardsInMiddle));
                findCard("Curse", cardsInMiddle).decreaseNumRemaining();
                discardPile.add(findCard("Copper", cardsInMiddle));
                findCard("Copper", cardsInMiddle).decreaseNumRemaining();
                System.out.println("Done.");
            }
        }
    }

    public static void pawn(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                            ArrayList<Card> hand, int[] info) {
        System.out.println("Choose 2 of the following.");
        System.out.println("1. +1 Card\n2. +1 Action\n3. +1 Buy\n4. +$1\n");
        System.out.println("Put the numbers of the options you would like to choose, separated by a space:");
        String optionStr = scnr.nextLine();
        int choice1 = getValidDigit(scnr, optionStr, 4);
        optionStr = optionStr.substring(1);
        int choice2 = getValidDigit(scnr, optionStr, 4);
        while (choice2 == choice1) {
            System.out.println("You have already chosen this number. Try again:");
            optionStr = scnr.nextLine();
            choice2 = getValidDigit(scnr, optionStr, 4);
        }

        if ((choice1 == 1) || (choice2 == 1)) {
            System.out.println("Drawing a card...");
            Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
            System.out.println("Drew the " + cardDrawn.getName() + " card.\n");
        }
        if ((choice1 == 2) || (choice2 == 2)) {
            System.out.println("Adding +1 Action...");
            info[ACTIONS_POS] += 1;
        }
        if ((choice1 == 3) || (choice2 == 3)) {
            System.out.println("Adding +1 Buy...");
            info[BUYS_POS] += 1;
        }
        if ((choice1 == 4) || (choice2 == 4)) {
            System.out.println("Adding +$1...");
            info[MONEY_POS] += 1;
        }
    }

    public static void peddler(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
        System.out.println("Adding +1 Action, +$1...");
        info[ACTIONS_POS] += 1;
        info[MONEY_POS] += 1;

        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("Drew the " + cardDrawn.getName() + " card.\n");
    }

    public static void poacher(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                               ArrayList<Card> hand, ArrayList<Card> cardsInMiddle, int[] info) {

        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("Drew the " + cardDrawn.getName() + " card.\n");

        System.out.println("Adding +$1 and +1 Action...");
        info[MONEY_POS] += 1;
        info[ACTIONS_POS] += 1;
        System.out.println("Done.\n");

        int numEmptySupplyPiles = 0;
        for (Card card : cardsInMiddle) {
            if (card.getNumRemaining() == 0) {
                ++numEmptySupplyPiles;
            }
        }

        System.out.println();
        for (int i=0; i<numEmptySupplyPiles; ++i) {
            System.out.println("Choose a card to discard.");
            Card cardToDiscard = printOptions(scnr, hand, "Poacher", 0);
            System.out.println("Discarding the " + cardToDiscard.getName() + " card....");
            discardCard(hand, discardPile, cardToDiscard, info, true);
            System.out.println("Done.\n");
        }

    }

    public static void remake(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> cardsInMiddle,
                              ArrayList<Card> trashedCards, int[] info) {
        for (int i=0; i<2; ++i) {
            System.out.println("Choose a card from your hand to trash.\n");
            Card cardTrashed = printOptions(scnr, hand, "Remake-Trash", 0);
            if (!cardTrashed.getType().equals("")) {
                trashCard(hand, trashedCards, cardTrashed, info, true);
            }

            System.out.println("Now choose a card to gain.\n");
            Card cardToGain = printOptions(scnr, cardsInMiddle, "Remake-Gain", cardTrashed.getCost());
            if (!cardToGain.getType().equals("")) {
                gainCard(discardPile, cardToGain);
                if (cardToGain.getName().equals("Border Village")) {
                    System.out.println("You may now gain a cheaper card.");
                    Card borderVillageGain = printOptions(scnr, cardsInMiddle, "Border Village", 5);
                    gainCard(discardPile, borderVillageGain);
                }
            }
        }
    }

    public static void remodel(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> cardsInMiddle,
                               ArrayList<Card> trashedCards, int[] info) {
        System.out.println("Choose a card from your hand to trash.\n");
        Card cardTrashed = printOptions(scnr, hand, "Remodel-Trash", 0);
        if (!cardTrashed.getType().equals("")) {
            trashCard(hand, trashedCards, cardTrashed, info, true);
        }

        // Gain a card costing up to $2 more
        System.out.println("Now choose a card to gain.\n");
        Card cardToGain = printOptions(scnr, cardsInMiddle, "Remodel-Gain", cardTrashed.getCost());
        if (!cardToGain.getType().equals("")) {
            gainCard(discardPile, cardToGain);
            if (cardToGain.getName().equals("Border Village")) {
                System.out.println("You may now gain a cheaper card.");
                Card borderVillageGain = printOptions(scnr, cardsInMiddle, "Border Village", 5);
                gainCard(discardPile, borderVillageGain);
            }
        }
    }

    public static void sentry(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                              ArrayList<Card> trashedCards, int[] info) {
        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + cardDrawn.getName() + " card.\n");

        System.out.println("Adding +1 Action...");
        info[ACTIONS_POS] += 1;
        System.out.println("Done.\n");

        if (drawPile.size() < 2) {
            Collections.shuffle(discardPile);
            drawPile.addAll(discardPile);
            discardPile.clear();
        }
        Card topCard = drawPile.get(0);
        Card secondCard = drawPile.get(1);
        System.out.println("The top 2 cards of your deck are " + topCard.getName() + " and " + secondCard.getName() + ".\n");
        drawPile.remove(topCard);
        drawPile.remove(secondCard);

        System.out.println("Which card would you like to trash, discard, or put back first?");
        System.out.println("1. " + topCard.getName() + "\n2. " + secondCard.getName());
        String whichCardStr = scnr.nextLine();
        int whichCard = getValidDigit(scnr, whichCardStr, 2);

        System.out.println();
        for (int i=0; i<2; ++i) {
            if (i == 0) {
                System.out.println("What would you like to do with it?");
            } else {
                System.out.println("What would you like to do with the other card?");
            }
            System.out.println("1. Trash\n2. Discard\n3. Put it back");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, 3);
            if (choice == 1) {
                switch (whichCard) {
                    case 1:
                        System.out.println("Trashing the " + topCard.getName() + " card...");
                        trashedCards.add(topCard);
                        System.out.println("Done.");
                        break;
                    case 2:
                        System.out.println("Trashing the " + secondCard.getName() + " card...");
                        trashedCards.add(secondCard);
                        System.out.println("Done.");
                        break;
                }
            } else if (choice == 2) {
                switch (whichCard) {
                    case 1:
                        System.out.println("Discarding the " + topCard.getName() + " card...");
                        discardPile.add(topCard);
                        System.out.println("Done.");
                        break;
                    case 2:
                        System.out.println("Discarding the " + secondCard.getName() + " card...");
                        discardPile.add(secondCard);
                        System.out.println("Done.");
                        break;
                }
            } else {
                switch (whichCard) {
                    case 1:
                        System.out.println("Putting it back in the draw pile...");
                        drawPile.add(0, topCard);
                        break;
                    case 2:
                        System.out.println("Putting it back in the draw pile...");
                        drawPile.add(0, secondCard);
                        break;
                }

            }
            if (whichCard == 1) {
                whichCard = 2;
            } else {
                whichCard = 1;
            }
        }

    }

    public static void shantyTown(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
        System.out.println("Adding +2 Actions...");
        info[ACTIONS_POS] += 2;

        boolean hasAction = false;
        for (Card card : hand) {
            if (card.getType().contains("Action")) {
                hasAction = true;
                break;
            }
        }

        if (!hasAction) {
            System.out.println("You have no action cards. Drawing 2 cards...");
            for (int i=0; i<2; ++i) {
                Card card = drawCard(hand, drawPile, discardPile, info);
                System.out.println("You drew the " + card.getName() + " card.");
            }
        }

    }

    public static void smithy(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
        System.out.println("Drawing 3 cards...");
        for (int i=0; i<3; ++i) {
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + card.getName() + " card.");
        }
    }

    public static void spy(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                           int[] info, boolean opponent) {
        String prompt = "Would your opponent like you to (1) keep it there or (2) discard it?";

        if (drawPile.size() < 1) {
            Collections.shuffle(discardPile);
            drawPile.addAll(discardPile);
            discardPile.clear();
        }
        if (!opponent) {
            prompt = "Would you like to (1) keep it there or (2) discard it?";
            System.out.println("Drawing a card...");
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + card.getName() + " card.");

            System.out.println("\nAdding +1 Action...");
            info[ACTIONS_POS] += 1;
            System.out.println("Done.\n");

            attack(scnr);
        }

        System.out.println("The top card of your deck is: " + drawPile.get(0).getName() + ".");
        System.out.println(prompt);
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 2);
        if (choice == 2) {
            discardCard(drawPile, discardPile, drawPile.get(0), info, false);
        }
    }

    public static void steward(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                               ArrayList<Card> hand, ArrayList<Card> trashedCards, int[] info) {
        System.out.println("1. +2 Cards\n2. +$2\n3. Trash 2 cards from your hand.\n");
        System.out.println("Choose an option:");
        String choiceStr = scnr.nextLine();
        int choice = getValidDigit(scnr, choiceStr, 3);

        if (choice == 1) {
            System.out.println("Drawing 2 cards...");
            for (int i=0; i<2; ++i) {
                Card card = drawCard(hand, drawPile, discardPile, info);
                System.out.println("You drew the " + card.getName() + " card.\n");
            }
        } else if (choice == 2) {
            System.out.println("Adding +$2...");
            info[MONEY_POS] += 2;
        } else {
            System.out.println("Trash 2 cards from your hand.\n");
            trashMultiple(scnr, hand, trashedCards, info, 2);
        }

    }

    public static void thief(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                             ArrayList<Card> cardsInMiddle, ArrayList<Card> trashedCards, boolean opponent) {
        if (!opponent) {
            System.out.println("Your opponent must now choose option 2 on their menu to be affected by your attack.");
            System.out.println("Did your opponent reveal any treasure cards? (1) yes, (2) no:");
            String choiceStr = scnr.nextLine();
            int choice = getValidDigit(scnr, choiceStr, 2);

            if (choice == 1) {
                System.out.println();
                System.out.println("Which treasure card did your opponent reveal?");
                System.out.println("1. Copper");
                System.out.println("2. Silver");
                System.out.println("3. Gold");

                System.out.println("\nChoose an option:");
                String trashedStr = scnr.nextLine();
                int trashed = getValidDigit(scnr, trashedStr, 3);

                System.out.println("\nWould you like to gain this card? (1) yes, (2) no:");
                String gainStr = scnr.nextLine();
                int gain = getValidDigit(scnr, gainStr, 2);

                if (gain == 1) {
                    switch (trashed) {
                        case 1:
                            gainCard(discardPile, findCard("Copper", cardsInMiddle));
                            break;
                        case 2:
                            gainCard(discardPile, findCard("Silver", cardsInMiddle));
                            break;
                        case 3:
                            gainCard(discardPile, findCard("Gold", cardsInMiddle));
                            break;
                    }
                }
            }
        } else {
            if (drawPile.size() < 2) {  // Make sure there are enough cards to draw
                Collections.shuffle(discardPile);
                drawPile.addAll(discardPile);
                discardPile.clear();
            }
            Card topCard = drawPile.get(0);
            Card secondCard = drawPile.get(1);

            int whichIsTreasure = -1;
            if (topCard.getType().contains("Treasure")) {
                whichIsTreasure = 1;
            } else if (secondCard.getType().contains("Treasure")) {
                whichIsTreasure = 2;
            }
            if (topCard.getType().contains("Treasure") && secondCard.getType().contains("Treasure")) {
                whichIsTreasure = 3;
            }

            System.out.println("The top 2 cards of your deck are the " + topCard.getName() + " card and the " +
                    secondCard.getName() + " card.");

            drawPile.remove(topCard);
            drawPile.remove(secondCard);

            System.out.println();
            if ((whichIsTreasure == 1) || (whichIsTreasure == 2)) {
                System.out.println("You have a treasure card. Would your opponent like you to trash it? (1) yes, (2) no:");
                String optionStr = scnr.nextLine();
                int option = getValidDigit(scnr, optionStr, 2);
                if (option == 1) {
                    if (whichIsTreasure == 1) {
                        System.out.println("Trashing the " + topCard.getName() + " card...");
                        trashedCards.add(topCard);
                        System.out.println("Discarding the " + secondCard.getName() + " card...");
                        discardPile.add(secondCard);
                    } else {
                        System.out.println("Trashing the " + secondCard.getName() + " card...");
                        trashedCards.add(secondCard);
                        System.out.println("Discarding the " + topCard.getName() + " card...");
                        discardPile.add(topCard);
                    }
                    System.out.println("Done.");
                }
            } else if (whichIsTreasure == 3) {
                System.out.println("You have 2 treasure cards.");
                System.out.println("What would your opponent like you to do?");
                System.out.println("1. Trash the " + topCard.getName() + " card.");
                System.out.println("2. Trash the " + secondCard.getName() + " card.");
                System.out.println("3. Keep both cards.");
                String optionStr = scnr.nextLine();
                int option = getValidDigit(scnr, optionStr, 3);
                if (option == 1) {
                    System.out.println("Trashing the " + topCard.getName() + " card...");
                    trashedCards.add(topCard);
                    System.out.println("Discarding the " + secondCard.getName() + " card...");
                    discardPile.add(secondCard);
                    System.out.println("Done.");
                } else if (option == 2) {
                    System.out.println("Trashing the " + secondCard.getName() + " card...");
                    trashedCards.add(secondCard);
                    System.out.println("Discarding the " + topCard.getName() + " card...");
                    discardPile.add(topCard);
                    System.out.println("Done.");
                }
            } else {
                System.out.println("You have no treasure cards. Discarding them...");
                discardPile.add(topCard);
                discardPile.add(secondCard);
                System.out.println("Done.");
            }
            System.out.println();

        }

    }

    public static Card throneRoomKingCourt(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                           ArrayList<Card> hand, ArrayList<Card> cardsInMiddle,
                                           ArrayList<Card> trashedCards, int[] info, int numPlays,
                                           PointTokens pointTokenMat) {
        boolean hasAction = false;
        for (Card card : hand) {
            if (card.getType().contains("Action")) {
                hasAction = true;
                break;
            }
        }

        if (hasAction) {
            System.out.println("Pick an action to play " + numPlays + " times.\n");

            Card action = printOptions(scnr, hand, "Throne Room/King's Court", 0);
            hand.remove(action);

            for (int i=0; i<numPlays; ++i) {
                action.increaseNumDurationPlays();
                System.out.println("\nPlaying the " + action.getName() + " card...\n");
                playAction(scnr, action, cardsInMiddle, drawPile, discardPile, hand, trashedCards, info, pointTokenMat);
            }
            action.decreaseNumDurationPlays();
            return action;
        } else {
            System.out.println("You have no action cards to play.\n");
            return new Card();
        }
    }

    public static void upgrade(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                               ArrayList<Card> cardsInMiddle, ArrayList<Card> trashedCards, int[] info) {
        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + cardDrawn.getName() + " card.\n");
        System.out.println("Adding +1 Action...");
        info[ACTIONS_POS] += 1;
        System.out.println("Done.\n");
        remodel(scnr, discardPile, hand, cardsInMiddle, trashedCards, info);
    }

    public static void vassal(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                              ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> trashedCards,
                              int[] info, PointTokens pointTokenMat) {
        System.out.println("Adding +$2...");
        info[MONEY_POS] += 2;
        System.out.println("Done.\n");

        if (drawPile.size() < 1) {
            Collections.shuffle(discardPile);
            drawPile.addAll(discardPile);
            discardPile.clear();
        }
        Card topCard = drawPile.get(0);
        System.out.println("The top card of your deck is " + topCard.getName() + ".\n");
        drawPile.remove(topCard);

        if (topCard.getType().contains("Action")) {
            System.out.println("It is an action card. Playing it now...\n");
            playAction(scnr, topCard, cardsInMiddle, drawPile, discardPile, hand, trashedCards, info, pointTokenMat);
        } else {
            System.out.println("It is not an action. Discarding it...");
            System.out.println("Done.\n");
        }

        discardPile.add(topCard);
    }

    public static void village(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                               int[] info) {
        System.out.println("Drawing a card...");
        Card card = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + card.getName() + " card.");

        System.out.println("\nAdding +2 Actions...");
        info[ACTIONS_POS] += 2;
        System.out.println("Done.");
    }

    public static void witch(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                             ArrayList<Card> hand, int[] info, boolean opponent) {
        if (!opponent) {
            System.out.println("Drawing 2 cards...");
            for (int i=0; i<2; ++i) {
                Card card = drawCard(hand, drawPile, discardPile, info);
                System.out.println("You drew the " + card.getName() + " card.");
            }

            attack(scnr);
            findCard("Curse", cardsInMiddle).decreaseNumRemaining();
        } else {
            gainCard(discardPile, findCard("Curse", cardsInMiddle));
        }
    }

    public static void wanderingMinstrel(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                         ArrayList<Card> hand, int[] info) {
        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + cardDrawn.getName() + " card.\n");

        System.out.println("Adding +2 Actions...");
        info[ACTIONS_POS] += 2;
        System.out.println("Done.\n");

        if (drawPile.size() < 3) {  // Make sure there are enough cards to draw
            Collections.shuffle(discardPile);
            drawPile.addAll(discardPile);
            discardPile.clear();
        }
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(drawPile.get(0));
        cards.add(drawPile.get(1));
        cards.add(drawPile.get(2));

        System.out.println("The top 3 cards of your deck are: " + cards.get(0).getName() + ", " + cards.get(1).getName()
                + ", and " + cards.get(2).getName() + ".");

        int numActions = 0;
        for (int i=0; i<3; ++i) {
            drawPile.remove(cards.get(numActions));
            if (!cards.get(numActions).getType().contains("Action")) {
                System.out.println("The " + cards.get(numActions).getName() + " card is not an action. Discarding it...");
                discardPile.add(cards.get(numActions));
                cards.remove(cards.get(numActions));
                System.out.println("Done.\n");
            } else {
                ++numActions;
            }
        }

        if (cards.size() > 0) {
            System.out.println("You have at least one action card.");
            while (cards.size() > 1) {
                System.out.println("Which would you like to put back now?");
                Card putBack = printOptions(scnr, cards, "Wandering Minstrel", 0);
                System.out.println("Putting the " + putBack.getName() + " card back in the draw pile...");
                drawPile.add(0, putBack);
                cards.remove(putBack);
                System.out.println("Done.\n");
            }
            System.out.println("Putting the " + cards.get(0).getName() + " card back in the draw pile...");
            drawPile.add(0, cards.get(0));
            System.out.println("Done.\n");
        }

        cards.clear();
    }

    public static void wharf(ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                             ArrayList<Card> hand, int[] info) {
        for (int i=0; i<2; ++i) {
            System.out.println("Drawing a card...");
            Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + cardDrawn.getName() + " card.\n");
        }
        System.out.println("Adding +1 Buy...");
        info[BUYS_POS] += 1;
        System.out.println("Done.\n");
    }

    public static void woodcutter(int[] info) {
        System.out.println("Adding +1 Buy and +$2:");
        info[BUYS_POS] += 1;
        info[MONEY_POS] += 2;
        System.out.println("Done.");
    }

    public static void workshop(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> cardsInMiddle) {
        System.out.println("You may gain a card costing up to $4.\n");

        Card cardGained = printOptions(scnr, cardsInMiddle, "Workshop", 0);
        if (!cardGained.getType().equals("")) {
            gainCard(discardPile, cardGained);
        }
    }

}

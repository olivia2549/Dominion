import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Dominion {
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
        ArrayList<Card> cardsInMiddle = new ArrayList<>();
        boolean usingProsperity = setup(scnr, allCards, cardsInMiddle, actionCards, drawPile, rand);
        ArrayList<Card> trashedCards = new ArrayList<>();

        PointTokens pointTokenMat = new PointTokens();

        System.out.println("Press enter to begin your first turn.");
        scnr.nextLine();

        takeTurns(scnr, cardsInMiddle, drawPile, discardPile, trashedCards, pointTokenMat, usingProsperity);

        endGame(drawPile, discardPile, pointTokenMat, usingProsperity);
    }

    public static ArrayList<Card> loadCards(Scanner fileScan, ArrayList<Card> allCards) {
        fileScan.useDelimiter("\n\n");

        // Reading from the input file to add all the cards
        while (fileScan.hasNext()) {
            String line = fileScan.next();
            String[] cardstats = line.split(",");
            Card card = new Card(cardstats[0].trim(),
                    cardstats[1].trim(),
                    cardstats[2].trim(),
                    Integer.parseInt(cardstats[3].trim()),
                    Integer.parseInt(cardstats[4].trim()),
                    Integer.parseInt(cardstats[5].trim()),
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

    public static boolean setup(Scanner scnr, ArrayList<Card> allCards, ArrayList<Card> cardsInMiddle,
                                        ArrayList<Card> actionCards, ArrayList<Card> drawPile, Random rand) {
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
                "Goons, Monument, Peddler");
        System.out.println("7. All Along the Watchtower: Hoard, Talisman, Bishop, Vault, Watchtower, Bridge, Mill, " +
                "Mining Village, Pawn, Torturer\n");

        System.out.println("Other");
        System.out.println("8. Custom");
        System.out.println("9. Random");

        // Filling the cards in "middle" with the action cards chosen by the user
        System.out.println("\nType the corresponding number:");
        String setupStr = scnr.nextLine();
        int setup = getValidDigit(scnr, setupStr, 9);
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
        } else if (setup == 7) {
            cardsInMiddle.add(findCard("Hoard", allCards));
            cardsInMiddle.add(findCard("Talisman", allCards));
            cardsInMiddle.add(findCard("Bishop", allCards));
            cardsInMiddle.add(findCard("Vault", allCards));
            cardsInMiddle.add(findCard("Watchtower", allCards));
            cardsInMiddle.add(findCard("Bridge", allCards));
            cardsInMiddle.add(findCard("Mill", allCards));
            cardsInMiddle.add(findCard("Mining Village", allCards));
            cardsInMiddle.add(findCard("Pawn", allCards));
            cardsInMiddle.add(findCard("Torturer", allCards));
        } else if (setup == 8) {    // Custom
            System.out.println();
            for (int i = 0; i < 10; ++i) {
                System.out.println("Type the name kingdom card #" + (i + 1));
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

        // Printing out the user's choice of kingdom cards
        System.out.println();
        System.out.println("Available decks:");
        for (int i = 0; i < 9; ++i) {
            System.out.print(cardsInMiddle.get(i).getName() + ", ");
        }
        System.out.println(cardsInMiddle.get(9).getName());
        System.out.println();

        // Fill cardsInMiddle with the core cards
        boolean usingProsperity = false;
        System.out.println("Are you playing with the prosperity expansion? (1) yes, (2) no");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 2);
        cardsInMiddle.add(findCard("Copper", allCards));
        cardsInMiddle.add(findCard("Silver", allCards));
        cardsInMiddle.add(findCard("Gold", allCards));
        if (choice == 1) {
            usingProsperity = true;
            cardsInMiddle.add(findCard("Platinum", allCards));
            cardsInMiddle.add(findCard("Colony", allCards));
        }
        cardsInMiddle.add(findCard("Province", allCards));
        cardsInMiddle.add(findCard("Duchy", allCards));
        cardsInMiddle.add(findCard("Estate", allCards));
        cardsInMiddle.add(findCard("Curse", allCards));

        // Adding to the draw pile and shuffling
        System.out.println("\nPerfect!");
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

        return usingProsperity;
    }

    /**
     *
     * Take turns until the number of provinces run out
     */
    public static void takeTurns(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                 ArrayList<Card> discardPile, ArrayList<Card> trashedCards, PointTokens pointTokenMat,
                                 boolean usingProsperity) {
        ArrayList<Card> hand = new ArrayList<>();
        ArrayList<Card> actionsPlayed = new ArrayList<>();
        boolean condition;

        if (usingProsperity) {
            condition = (findCard("Colony", cardsInMiddle).getNumRemaining() > 0);
        } else {
            condition = (findCard("Province", cardsInMiddle).getNumRemaining() > 0);
        }

        // Keep taking turns as long as there are provinces or colonies left (depending on prosperity condition)
        while (condition) {
            // Reset stats
            Stats stats = new Stats();

            // Draw a hand of 5 cards, calculate stats, display to player
            System.out.println("********************** Your next hand of cards **********************");
            for (int i = 0; i < 5; ++i) {
                drawCard(hand, drawPile, discardPile, stats, false);
                System.out.println(hand.get(i).cardInHand());
            }

            // Print money, actions, and buys
            System.out.println();
            printstats(hand, stats);

            // Play any leftover duration cards
            playDuration(scnr, actionsPlayed, cardsInMiddle, drawPile, discardPile, hand, trashedCards, stats,
                    pointTokenMat);

            // Let the player choose menu options
            System.out.println();
            manageMenu(scnr, cardsInMiddle, hand, drawPile, discardPile, actionsPlayed, trashedCards, stats,
                    pointTokenMat, usingProsperity);

            // Discard the used hand and cards played
            discardPile.addAll(hand);
            for (Card card : actionsPlayed) {
                if (!card.getTrashCard()) {
                    discardPile.add(card);
                }
            }
            actionsPlayed.clear();
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

            // Update the condition
            if (usingProsperity) {
                condition = (findCard("Colony", cardsInMiddle).getNumRemaining() > 0);
            } else {
                condition = (findCard("Province", cardsInMiddle).getNumRemaining() > 0);
            }
        }

    }

    /**
     *
     * Cycle through the menu choices until the player ends their turn
     */
    public static void manageMenu(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                  ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> actionsPlayed,
                                  ArrayList<Card> trashedCards, Stats stats, PointTokens pointTokenMat,
                                  boolean usingProsperity) {
        printMenuOptions(usingProsperity);
        String menuChoiceStr = scnr.nextLine();
        int menuChoice = getValidDigit(scnr, menuChoiceStr, 5);
        int unusedMerchants = 0;

        while (menuChoice != 5) {

            // Keeps track of whether the menu options should be reprinted after user chooses a number
            boolean noPrintOptions = false;

            if (menuChoice == 1) {  // Opponent bought province or colony
                boughtEndingVictory(cardsInMiddle, usingProsperity, true);
            } else if (menuChoice == 2) {   // Opponent played attack card or council room
                playedAttackCard(scnr, cardsInMiddle, hand, drawPile, discardPile, trashedCards, stats, pointTokenMat);
            } else if (menuChoice == 3) {   // Choose and play an action from your hand
                Card card = chooseAction(scnr, cardsInMiddle, drawPile, discardPile, hand, actionsPlayed,
                        trashedCards, stats, pointTokenMat);
                unusedMerchants = checkMerchant(card, unusedMerchants, hand, stats);
            } else if (menuChoice == 4) {   // Buy a card
                noPrintOptions = buyCard(scnr, cardsInMiddle, drawPile, discardPile, hand, stats, actionsPlayed,
                        pointTokenMat, usingProsperity);
            }

            if (usingProsperity) {
                if (findCard("Colony", cardsInMiddle).getNumRemaining() == 0) {
                    break;
                }
            } else {
                if (findCard("Province", cardsInMiddle).getNumRemaining() == 0) {
                    break;
                }
            }

            if (!noPrintOptions) {
                printMenuOptions(usingProsperity);
            }
            menuChoiceStr = scnr.nextLine();
            menuChoice = getValidDigit(scnr, menuChoiceStr, 5);
        }

        System.out.println();
    }

    public static void endGame(ArrayList<Card> drawPile, ArrayList<Card> discardPile, PointTokens pointTokenMat,
                               boolean usingProsperity) {
        System.out.println("\n\n------------------------------- GAME OVER -------------------------------");

        int endPoints = pointTokenMat.getPoints();  // Starting value
        int numCards = 0;
        int numGardens = 0;
        int numEstates = 0;
        int numDuchys = 0;
        int numProvinces = 0;
        int numColonies = 0;
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
                    case "Colony":
                        numColonies += 1;
                        break;
                    case "Curse":
                        numCurses += 1;
                        break;
                    case "Harem":
                    case "Mill":
                        numOtherVictoryCards += 1;
                        break;
                }
            } else if (card.getName().equals("Gardens")) {
                numGardens += 1;
            }
        }
        int numPointsPerGarden = discardPile.size()/10;
        endPoints += numPointsPerGarden*numGardens;

        System.out.print("You ended with " +
                numCards + " total cards, " +
                numEstates + " estates, " +
                numDuchys + " duchys, " +
                numProvinces + " provinces, ");
        if (usingProsperity) {
            System.out.print(numColonies + " colonies, ");
        }
        System.out.println(numOtherVictoryCards + " other victory card(s), " +
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
                                    ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> actionsPlayed,
                                    ArrayList<Card> trashedCards, Stats stats, PointTokens pointTokenMat) {
        if (stats.getActions() == 0) {
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
                    stats.changeActions(-1);
                    if (!cardPlayed.getName().equals("Feast")) {
                        actionsPlayed.add(cardPlayed);
                    }
                    cardPlayed = playAction(scnr, cardPlayed, actionsPlayed, cardsInMiddle, drawPile, discardPile, hand,
                            trashedCards, stats, pointTokenMat);
                    cardPlayed.increaseNumDurationPlays();
                } else {
                    System.out.println("Action cancelled.");
                }
                System.out.println();
                printstats(hand, stats);
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
    public static Card playAction(Scanner scnr, Card cardPlayed, ArrayList<Card> actionsPlayed, ArrayList<Card> cardsInMiddle,
                                  ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  ArrayList<Card> trashedCards, Stats stats, PointTokens pointTokenMat) {

        switch (cardPlayed.getName()) {
            case "Adventurer":
                adventurer(drawPile, discardPile, hand, stats);
                break;
            case "Ambassador":
                ambassador(scnr, cardsInMiddle, drawPile, discardPile, hand, trashedCards, stats, false);
                break;
            case "Bandit":
                bandit(scnr, drawPile, discardPile, cardsInMiddle, trashedCards, false, stats);
                break;
            case "Baron":
                baron(scnr, cardsInMiddle, drawPile, discardPile, hand, stats);
                break;
            case "Bishop":
                bishop(scnr, hand, trashedCards, stats, pointTokenMat, false);
                break;
            case "Bridge":
                bridge(stats);
                break;
            case "Border Village":
                borderVillage(drawPile, discardPile, hand, stats);
                break;
            case "Bureaucrat":
                bureaucrat(scnr, cardsInMiddle, hand, drawPile, false);
                break;
            case "Cellar":
                cellar(scnr, drawPile, discardPile, hand, stats);
                break;
            case "Chancellor":
                chancellor(drawPile, discardPile, stats);
                break;
            case "Chapel":
                chapel(scnr, hand, trashedCards, stats);
                break;
            case "Council Room":
                councilRoom(scnr, drawPile, discardPile, hand, stats, false);
                break;
            case "Counting House":
                countingHouse(scnr, discardPile, hand);
                break;
            case "Feast":
                feast(scnr, cardsInMiddle, drawPile, discardPile, hand, trashedCards, stats);
                break;
            case "Festival":
                festival(stats);
                break;
            case "Goons":
                goons(scnr, hand, discardPile, stats, false);
                break;
            case "Harbinger":
                harbinger(scnr, hand, drawPile, discardPile, stats);
                break;
            case "Junk Dealer":
                junkDealer(scnr, drawPile, discardPile, hand, trashedCards, stats);
                break;
            case "King's Court":
                cardPlayed = throneRoomKingCourt(scnr, actionsPlayed, drawPile, discardPile, hand, cardsInMiddle,
                        trashedCards, stats, 3, pointTokenMat);
                break;
            case "Laboratory":
                laboratory(drawPile, discardPile, hand, stats);
                break;
            case "Library":
                library(scnr, drawPile, discardPile, hand, stats);
                break;
            case "Market":
                market(drawPile, discardPile, hand, stats);
                break;
            case "Menagerie":
                menagerie(drawPile, discardPile, hand, stats);
                break;
            case "Merchant":
                merchant(hand, drawPile, discardPile, stats);
                break;
            case "Militia":
                militia(scnr, hand, discardPile, stats, false);
                break;
            case "Mill":
                mill(scnr, hand, drawPile, discardPile, stats);
                break;
            case "Mine":
                mine(scnr, cardsInMiddle, hand, trashedCards, stats);
                break;
            case "Mining Village":
                miningVillage(scnr, drawPile, discardPile, hand, stats, actionsPlayed);
                break;
            case "Moat":
                moat(drawPile, discardPile, hand, stats);
                break;
            case "Moneylender":
                moneylender(hand, trashedCards, stats);
                break;
            case "Monument":
                monument(stats, pointTokenMat);
                break;
            case "Mountebank":
                mountebank(scnr, discardPile, hand, cardsInMiddle, stats, false);
                break;
            case "Pawn":
                pawn(scnr, drawPile, discardPile, hand, stats);
                break;
            case "Peddler":
                peddler(drawPile, discardPile, hand, stats);
                break;
            case "Poacher":
                poacher(scnr, drawPile, discardPile, hand, cardsInMiddle, stats);
                break;
            case "Remake":
                remake(scnr, drawPile, discardPile, hand, cardsInMiddle, trashedCards, stats);
                break;
            case "Remodel":
                remodel(scnr, drawPile, discardPile, hand, cardsInMiddle, trashedCards, stats);
                break;
            case "Sentry":
                sentry(scnr, drawPile, discardPile, hand, trashedCards, stats);
                break;
            case "Shanty Town":
                shantyTown(drawPile, discardPile, hand, stats);
                break;
            case "Smithy":
                smithy(drawPile, discardPile, hand, stats);
                break;
            case "Spy":
                spy(scnr, drawPile, discardPile, hand, stats, false);
                break;
            case "Steward":
                steward(scnr, drawPile, discardPile, hand, trashedCards, stats);
                break;
            case "Thief":
                thief(scnr, hand, drawPile, discardPile, cardsInMiddle, trashedCards, false);
                break;
            case "Throne Room":
                cardPlayed = throneRoomKingCourt(scnr, actionsPlayed, drawPile, discardPile, hand, cardsInMiddle,
                        trashedCards, stats, 2, pointTokenMat);
                break;
            case "Torturer":
                torturer(scnr, drawPile, discardPile, hand, cardsInMiddle, stats, false);
                break;
            case "Upgrade":
                upgrade(scnr, drawPile, discardPile, hand, cardsInMiddle, trashedCards, stats);
                break;
            case "Vassal":
                vassal(scnr, actionsPlayed, cardsInMiddle, drawPile, discardPile, hand, trashedCards, stats,
                        pointTokenMat);
                break;
            case "Vault":
                vault(scnr, drawPile, discardPile, hand, stats, false);
                break;
            case "Village":
                village(drawPile, discardPile, hand, stats);
                break;
            case "Wandering Minstrel":
                wanderingMinstrel(scnr, drawPile, discardPile, hand, stats);
                break;
            case "Watchtower":
                watchtower(drawPile, discardPile, hand, stats);
                break;
            case "Wharf":
                wharf(drawPile, discardPile, hand, stats);
                break;
            case "Witch":
                witch(scnr, cardsInMiddle, drawPile, discardPile, hand, stats, false);
                break;
            case "Woodcutter":
                woodcutter(stats);
                break;
            case "Workshop":
                workshop(scnr, hand, drawPile, discardPile, cardsInMiddle);
                break;
        }
        return cardPlayed;
    }

    /**
     *
     * Allows user to buy a card
     * @return whether a card was actually bought or not (determines whether stats should be reprinted)
     */
    public static boolean buyCard(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                  ArrayList<Card> discardPile, ArrayList<Card> hand, Stats stats,
                                  ArrayList<Card> actionsPlayed, PointTokens pointTokenMat, boolean usingProsperity) {

        if (stats.getBuys() == 0) {
            System.out.println("Sorry, you have no more buys left. Cannot execute option.\n");
            System.out.println("Please enter a different choice:");
            return true;
        } else {
            subtractPrices(cardsInMiddle, actionsPlayed);   // Some action cards subtract pricing of other cards

            // Choose a card to buy
            ArrayList<Card> options = new ArrayList<>();
            int choice = chooseBuy(scnr, options, cardsInMiddle, stats);

            // Buy the card
            System.out.println();
            if (choice != options.size() + 1) {
                Card cardBought = options.get(choice - 1);
                gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, cardBought);

                // Make special things happen when certain cards are bought
                specialBuys(cardBought, scnr, cardsInMiddle, hand, drawPile, discardPile, actionsPlayed,
                        pointTokenMat, usingProsperity);

                // Decrease the buys and money left for this turn
                stats.changeBuys(-1);
                stats.changeMoney(cardBought.getCost()*-1);

                addPrices(cardsInMiddle, actionsPlayed);    // Put prices back to normal
            } else {
                System.out.println("Buy cancelled.\n");
            }

            System.out.println();
            printstats(hand, stats);
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
                                        ArrayList<Card> trashedCards, Stats stats, PointTokens pointTokenMat) {

        System.out.println("\nWhich of these cards did your opponent play?");
        System.out.println("1. Bureaucrat\n2. Militia\n3. Spy\n4. Thief\n5. Witch\n6. Mountebank\n7. Ambassador\n8. " +
                "Bandit\n9. Torturer\n10. Goons\n11. Council Room\n12. Bishop\n13. Vault");
        System.out.println("\nChoose an option:");

        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 13);
        switch (choice) {
            case 1:
                bureaucrat(scnr, cardsInMiddle, hand, drawPile, true);
                break;
            case 2:
                militia(scnr, hand, discardPile, stats, true);
                break;
            case 3:
                spy(scnr, drawPile, discardPile, hand, stats, true);
                break;
            case 4:
                thief(scnr, hand, drawPile, discardPile, cardsInMiddle, trashedCards, true);
                break;
            case 5:
                witch(scnr, cardsInMiddle, drawPile, discardPile, hand, stats, true);
                break;
            case 6:
                mountebank(scnr, discardPile, hand, cardsInMiddle, stats, true);
                break;
            case 7:
                ambassador(scnr, cardsInMiddle, drawPile, discardPile, hand, trashedCards, stats, true);
                break;
            case 8:
                bandit(scnr, drawPile, discardPile, cardsInMiddle, trashedCards, true, stats);
                break;
            case 9:
                torturer(scnr, drawPile, discardPile, hand, cardsInMiddle, stats, true);
                break;
            case 10:
                goons(scnr, hand, discardPile, stats, true);
                break;
            case 11:
                councilRoom(scnr, drawPile, discardPile, hand, stats, true);
                break;
            case 12:
                bishop(scnr, hand, trashedCards, stats, pointTokenMat, true);
                break;
            case 13:
                vault(scnr, drawPile, discardPile, hand, stats, true);
                break;
        }
    }



    // ************************************** HELPER METHODS **************************************

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

    public static Card drawCard(ArrayList<Card> hand, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                Stats stats, boolean printCard) {
        // If draw pile has <1 card, shuffle discard pile and add it to bottom of draw pile
        if (drawPile.size() < 1) {
            if (discardPile.size() > 0) {
                Collections.shuffle(discardPile);
                drawPile.addAll(discardPile);
                discardPile.clear();
            } else {
                System.out.println("No cards left to draw.\n");
                return new Card();
            }
        }

        Card card = drawPile.get(0);
        hand.add(card);
        if (printCard) {
            System.out.println("You drew the " + card.getName() + " card.");
        }

        if (card.getType().contains("Treasure")) {
            stats.changeMoney(card.getValue());
        }

        drawPile.remove(0);
        return card;
    }

    public static void gainCard(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                ArrayList<Card> drawPile, ArrayList<Card> discardPile, Card cardToGain) {
        if (cardToGain.getNumRemaining() > 0) {
            System.out.println("Gaining the " + cardToGain.getName() + " card...");
            discardPile.add(cardToGain);
            cardToGain.decreaseNumRemaining();
            System.out.println("Done.\n");
            specialGains(scnr, cardsInMiddle, hand, drawPile, discardPile, cardToGain);
        } else {
            System.out.println("The " + cardToGain.getName() + " supply is out of cards.\n");
        }
    }

    public static void discardCard(ArrayList<Card> list, ArrayList<Card> discardPile, Card cardToDiscard, Stats stats,
                                   boolean hand) {
        System.out.println("Discarding the " + cardToDiscard.getName() + " card...");
        discardPile.add(cardToDiscard);
        if (cardToDiscard.getType().contains("Treasure") && hand) {
            stats.changeMoney(cardToDiscard.getValue()*-1);
        }
        list.remove(cardToDiscard);
        System.out.println("Done.\n");
    }

    public static void trashCard(ArrayList<Card> list, ArrayList<Card> trashedCards, Card cardToTrash, Stats stats,
                                 boolean hand) {
        System.out.println("Trashing the " + cardToTrash.getName() + " card...");

        if (cardToTrash.getType().contains("Treasure") && hand) {
            stats.changeMoney(cardToTrash.getValue()*-1);
        }

        list.remove(cardToTrash);
        trashedCards.add(cardToTrash);
        System.out.println("Done.\n");
    }

    public static void trashMultiple(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> trashedCards, Stats stats,
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
                trashCard(hand, trashedCards, chosenCard, stats, true);
            }
        }

        if (scanChoices.hasNextInt()) {
            System.out.println("\nLimit has been reached. Could not trash any more cards.\n");
        }
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

    public static void printMenuOptions(boolean usingProsperity) {
        System.out.println("------------- MENU OPTIONS -------------");
        if (usingProsperity) {
            System.out.println("1. Opponent bought a Colony");
        } else {
            System.out.println("1. Opponent bought a Province");
        }
        System.out.println("2. Opponent played attack/benefit card affecting me");
        System.out.println("3. Play action");
        System.out.println("4. Buy card");
        System.out.println("5. End turn");

        System.out.println();
        System.out.println("Enter your choice:");
    }

    public static void printstats(ArrayList<Card> hand, Stats stats) {
        System.out.println(stats);
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
                                    Stats stats, PointTokens pointTokenMat) {
        for (Card card : actionsPlayed) {
            if (card.getType().contains("Duration")) {
                for (int i=0; i<card.getNumDurationPlays(); ++i) {
                    System.out.println("Playing the " + card.getName() + " duration card...");
                    playAction(scnr, card, actionsPlayed, cardsInMiddle, drawPile, discardPile, hand, trashedCards,
                            stats, pointTokenMat);
                }
                System.out.println("\nNew stats:");
                printstats(hand, stats);
            }
            card.setNumDurationPlays(0);
        }
    }

    /**
     *
     * Choose a card to buy from the options available based on money
     * @return number corresponding to the user's choice of card to buy
     */
    public static int chooseBuy(Scanner scnr, ArrayList<Card> options, ArrayList<Card> cardsInMiddle, Stats stats) {
        int option = 1;

        System.out.println("Your options:");
        for (Card card : cardsInMiddle) {   // Make sure the options are only cards the user can afford
            if ((card.getCost() <= stats.getMoney()) && card.getNumRemaining() > 0) {
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

    public static void boughtEndingVictory(ArrayList<Card> cardsInMiddle, boolean usingProsperity, boolean opponent) {
        String name;
        if (usingProsperity) {
            name = "Colony";
        } else {
            name = "Province";
        }

        if (opponent) {
            findCard(name, cardsInMiddle).decreaseNumRemaining();
        }
        System.out.print(name + "s remaining: ");
        System.out.println(findCard(name, cardsInMiddle).getNumRemaining());
        System.out.println();
    }

    /**
     *
     * Makes extra things happen when certain cards are bought
     */
    public static void specialBuys(Card cardBought, Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                   ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                   ArrayList<Card> actionsPlayed, PointTokens pointTokenMat, boolean usingProsperity) {
        checkBorderVillage(scnr, cardBought, cardsInMiddle, hand, drawPile, discardPile);
        checkGoons(actionsPlayed, pointTokenMat);
        checkHoard(scnr, cardBought, cardsInMiddle, hand, drawPile, discardPile);
        checkEndingVictory(cardBought, cardsInMiddle, usingProsperity);
        checkTalisman(scnr, cardBought, cardsInMiddle, hand, drawPile, discardPile);
    }

    public static void checkBorderVillage(Scanner scnr, Card cardBought, ArrayList<Card> cardsInMiddle,
                                          ArrayList<Card> hand, ArrayList<Card> drawPile, ArrayList<Card> discardPile) {
        if (cardBought.getName().equals("Border Village")) {
            System.out.println("You may now gain a cheaper card.");
            Card cardToGain = printOptions(scnr, cardsInMiddle, "Border Village", 5);
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, cardToGain);
        }
    }

    public static void checkGoons(ArrayList<Card> actionsPlayed, PointTokens pointTokenMat) {
        int numGoons = Collections.frequency(actionsPlayed, findCard("Goons", actionsPlayed));
        pointTokenMat.increasePoints(numGoons);

        if (numGoons > 0) {
            System.out.println("You have " + numGoons + " Goons in play. Gaining " + numGoons + " victory point token" +
                    "...");
            System.out.println("Done.\n");
        }
    }

    public static void checkHoard(Scanner scnr, Card cardBought, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                  ArrayList<Card> drawPile, ArrayList<Card> discardPile) {
        boolean hasHoard = hand.contains(findCard("Hoard", hand));

        if (cardBought.getType().contains("Victory") && hasHoard) {
            System.out.println("You have a Hoard in play. Gaining a gold...");
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, findCard("Gold", cardsInMiddle));
            System.out.println("Done.\n");
        }
    }

    public static void checkEndingVictory(Card cardBought, ArrayList<Card> cardsInMiddle, boolean usingProsperity) {
        String name;
        if (usingProsperity) {
            name = "Colony";
        } else {
            name = "Province";
        }
        if (cardBought.getName().equals(name)) {
            boughtEndingVictory(cardsInMiddle, usingProsperity, false);
        }
    }

    public static void checkTalisman(Scanner scnr, Card cardBought, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                     ArrayList<Card> drawPile, ArrayList<Card> discardPile) {
        boolean hasTalisman = hand.contains(findCard("Talisman", hand));

        if (!cardBought.getType().contains("Victory") && cardBought.getCost() <= 4 && hasTalisman) {
            System.out.println("You have a Talisman in play. Gaining a copy of the " + cardBought.getName() + " card." +
                    "..");
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, findCard(cardBought.getName(), cardsInMiddle));
        }
    }

    /**
     *
     * Certain cards have special effects when a card is gained, so this checks for that
     */
    public static void specialGains(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                    ArrayList<Card> drawPile, ArrayList<Card> discardPile, Card cardGained) {
        checkWatchtower(scnr, hand, drawPile, discardPile, cardGained);
        checkBorderVillage(scnr, cardsInMiddle, hand, drawPile, discardPile, cardGained);
    }

    public static void checkBorderVillage(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                             ArrayList<Card> drawPile, ArrayList<Card> discardPile, Card cardGained) {
        if (cardGained.getName().equals("Border Village")) {
            System.out.println("You may also gain a cheaper card.");
            Card borderVillageGain = printOptions(scnr, cardsInMiddle, "Border Village", 5);
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, borderVillageGain);
        }
    }

    public static void checkWatchtower(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> drawPile,
                                       ArrayList<Card> discardPile, Card cardGained) {
        boolean hasWatchtower = hand.contains(findCard("Watchtower", hand));

        if (hasWatchtower) {
            discardPile.remove(0);
            System.out.println("You have the watchtower in hand. Would you like to (1) trash the " + cardGained.getName() +
                    " card or (2) put it on your deck?");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, 2);
            if (choice == 1) {
                System.out.println("Trashing the " + cardGained.getName() + " card...");
                System.out.println("Done.\n");
            }
            if (choice == 2) {
                System.out.println("Adding the " + cardGained.getName() + " card to your draw pile...");
                drawPile.add(0, cardGained);
                System.out.println("Done.\n");
            }
        }
    }

    /**
     *
     * This method is called each time an action is played
     * Checks if the action played was a merchant, and if so, it adds to the number of unused merchants for that turn
     * @return the new number of unused merchants
     */
    public static int checkMerchant(Card actionPlayed, int unusedMerchants, ArrayList<Card> hand, Stats stats) {
        if (actionPlayed.getName().equals("Merchant")) {
            ++unusedMerchants;
        }
        return useMerchant(hand, unusedMerchants, stats);
    }

    /**
     *
     * This method is called each time an action is played
     * Uses the merchant if there is currently one unused, and a silver in play
     * @return the new number of unused merchants
     */
    public static int useMerchant(ArrayList<Card> hand, int unusedMerchants, Stats stats) {
        boolean hasSilver = hand.contains(findCard("Silver", hand));

        // If merchant(s) were played this round and there is a silver in hand, add +$1 for each unused merchant
        if (unusedMerchants > 0 && hasSilver) {
            for (int i=0; i<unusedMerchants; ++i) {
                System.out.println("You now have a silver and merchant in play.");
                System.out.println(stats.changeMoney(1));
                System.out.println("Done.\n");
            }
            unusedMerchants = 0;
            System.out.println("New stats:");
            printstats(hand, stats);
        }

        return unusedMerchants;
    }

    public static void subtractPrices(ArrayList<Card> cardsInMiddle, ArrayList<Card> actionsPlayed) {
        manageBridgePrice(cardsInMiddle, actionsPlayed, true);
        managePeddlerPrice(cardsInMiddle, actionsPlayed, true);
    }

    public static void managePeddlerPrice(ArrayList<Card> cardsInMiddle, ArrayList<Card> actionsPlayed,
                                        boolean subtract) {
        if (subtract) {
            Card peddler = findCard("Peddler", cardsInMiddle);
            peddler.setCost(8 - (actionsPlayed.size()*2));
            if (peddler.getCost() < 0) {
                peddler.setCost(0);
            }
        } else {
            findCard("Peddler", cardsInMiddle).setCost(8);
        }
    }

    public static void manageBridgePrice(ArrayList<Card> cardsInMiddle, ArrayList<Card> actionsPlayed,
                                        boolean subtract) {
        int numBridges = Collections.frequency(actionsPlayed, findCard("Bridge", actionsPlayed));

        for (int i=0; i<numBridges; ++i) {
            if (subtract) {
                for (Card card : cardsInMiddle) {
                    card.setCost(card.getCost() - 1);
                    if (card.getCost() < 0) {
                        card.setCost(0);
                    }
                }
            } else {
                for (Card card : cardsInMiddle) {
                    card.setCost(card.getCost() + 1);
                }
            }
        }
    }

    public static void addPrices(ArrayList<Card> cardsInMiddle, ArrayList<Card> actionsPlayed) {
        manageBridgePrice(cardsInMiddle, actionsPlayed, false);
        managePeddlerPrice(cardsInMiddle, actionsPlayed, false);
    }


    // **************************************** ACTION CARDS ****************************************
    public static void adventurer(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  Stats stats) {
        for (int i=0; i<2; ++i) {
            Card card = drawCard(hand, drawPile, discardPile, stats, true);

            while (!card.getType().contains("Treasure")) {
                System.out.println("Not a treasure card. Discarding and drawing another...");
                discardCard(hand, discardPile, card, stats, true);
                card = drawCard(hand, drawPile, discardPile, stats, true);
            }
            System.out.println();
        }
    }

    public static void ambassador(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                  ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> trashedCards,
                                  Stats stats, boolean opponent) {
        if (!opponent) {
            System.out.println("Choose a card from your hand to return to the Supply.");
            Card cardToReturn = printOptions(scnr, hand, "Ambassador", 0);

            int numCopies = Collections.frequency(hand, findCard(cardToReturn.getName(), hand));

            int choice = 2;
            if (numCopies > 1) {
                System.out.println("You have more than 1 copy of this card. Would you like to return them both? (1) " +
                        "yes, (2) no:");
                String optionStr = scnr.nextLine();
                choice = getValidDigit(scnr, optionStr, 2);
            }

            trashCard(hand, trashedCards, cardToReturn, stats, true);
            if (choice == 1) {  // Remove the card again if they wanted both copies trashed.
                trashCard(hand, trashedCards, cardToReturn, stats, true);
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
                    gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, card);
                    if (choice == 2) {
                        gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, card);
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
                        gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, card);
                        if (choice == 2) {
                            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, card);
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void bandit(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                              ArrayList<Card> cardsInMiddle, ArrayList<Card> trashedCards, boolean opponent,
                              Stats stats) {
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
                trashCard(drawPile, trashedCards, topCard, stats, false);
                discardCard(drawPile, discardPile, secondCard, stats, false);
            } else if (!topCardTreasure && nextCardTreasure) {
                trashCard(drawPile, trashedCards, secondCard, stats, false);
                discardCard(drawPile, discardPile, topCard, stats, false);
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

    public static void baron(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                             ArrayList<Card> discardPile, ArrayList<Card> hand, Stats stats) {
        System.out.println(stats.changeBuys(1));

        boolean discardedEstate = false;
        for (Card card : hand) {
            if (card.getName().equals("Estate")) {
                System.out.println("Would you like to discard an estate for +$4? (1) yes, (2) no:");
                String choiceStr = scnr.nextLine();
                int choice = getValidDigit(scnr, choiceStr, 2);
                if (choice == 1) {
                    discardedEstate = true;
                    discardCard(hand, discardPile, card, stats, true);
                    System.out.println(stats.changeMoney(4));
                }
                break;
            }
        }

        if (!discardedEstate) {
            System.out.println("No Estate discarded. Gaining an Estate...");
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, findCard("Estate", cardsInMiddle));
            System.out.println("Done.\n");
        }


    }

    public static void bishop(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> trashedCards, Stats stats,
                              PointTokens pointTokenMat, boolean opponent) {
        if (!opponent) {
            System.out.println("Gaining +$1...");
            stats.changeMoney(1);
        }

        boolean trashCard = true;
        if (opponent) {
            System.out.println("Would you like to trash a card from your hand? (1) yes, (2) no:");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, 2);
            if (choice == 2) {
                trashCard = false;
            }
        }

        if (trashCard) {
            System.out.println("Choose a card from your hand to trash.");
            Card cardToTrash = printOptions(scnr, hand, "Bishop", 0);
            trashCard(hand, trashedCards, cardToTrash, stats, true);

            if (!opponent) {
                int pointsToGain = cardToTrash.getCost()/2;
                System.out.println("Gaining " + pointsToGain + " point token(s) plus 1 free point token\n");
                pointTokenMat.increasePoints(pointsToGain + 1);

                System.out.println("Your opponent(s) may trash a card.");
                System.out.println("Press enter when they have done so:");
                scnr.nextLine();
            }
        }
    }

    public static void bridge(Stats stats) {
        System.out.println(stats.changeBuys(1));
        System.out.println(stats.changeMoney(1));
    }

    public static void borderVillage(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                     Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(2));
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
                              ArrayList<Card> hand, Stats stats) {

        System.out.println(stats.changeActions(1));

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
            discardCard(hand, discardPile, chosenCard, stats, true);

            System.out.println("Drawing a replacement...");
            drawCard(hand, drawPile, discardPile, stats, true);
        }

    }

    public static void chancellor(ArrayList<Card> drawPile, ArrayList<Card> discardPile, Stats stats) {
        System.out.println(stats.changeMoney(2));
        System.out.println("Moving the draw pile to the discard pile...");
        while (drawPile.size() > 0) {
            discardPile.add(drawPile.get(0));
            drawPile.remove(0);
        }
        System.out.println("Done.");
    }

    public static void chapel(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> trashedCards, Stats stats) {
        trashMultiple(scnr, hand, trashedCards, stats, 4);
    }

    public static void councilRoom(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                   ArrayList<Card> hand, Stats stats, boolean opponent) {
        if (!opponent) {
            System.out.println("+1 buy:");
            stats.changeBuys(1);
            printstats(hand, stats);

            System.out.println("Drawing 4 cards...");
            for (int i=0; i<4; ++i) {
                drawCard(hand, drawPile, discardPile, stats, true);
            }

            System.out.println();
            System.out.println("Your opponent must now choose option 2 of their menu to draw a card.");
            System.out.println("Press enter when they have done so:");
            scnr.nextLine();
        } else {
            System.out.println("\nDrawing a card...");
            drawCard(hand, drawPile, discardPile, stats, true);
            printstats(hand, stats);
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

    public static void feast(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                             ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> trashedCards,
                             Stats stats) {
        trashCard(hand, trashedCards, findCard("Feast", cardsInMiddle), stats, true);
        System.out.println();
        System.out.println("You may now gain a card costing up to $5.\n");

        Card cardGained = printOptions(scnr, cardsInMiddle, "Feast", 0);

        if (!cardGained.getName().equals("")) {
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, cardGained);
        }
    }

    public static void festival(Stats stats) {
        System.out.println(stats.changeActions(2));
        System.out.println(stats.changeBuys(1));
        System.out.println(stats.changeMoney(2));
    }

    public static void goons(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> discardPile, Stats stats,
                             boolean opponent) {
        if (!opponent) {
            System.out.println(stats.changeMoney(2));
            System.out.println(stats.changeBuys(1));
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
                discardCard(hand, discardPile, cardToDiscard, stats, true);
            }
            System.out.println("\nNew hand stats:");
            printstats(hand, stats);
        }
    }

    public static void harbinger(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> drawPile,
                                 ArrayList<Card> discardPile, Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(1));

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
                                  ArrayList<Card> hand, ArrayList<Card> trashedCards, Stats stats) {
        System.out.println(stats.changeActions(1));
        System.out.println(stats.changeMoney(1));
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println("Choose a card to trash.\n");
        Card cardToTrash = printOptions(scnr, hand, "Junk Dealer", 0);
        if (!cardToTrash.getType().equals("")) {
            trashCard(hand, trashedCards, cardToTrash, stats, true);
        }
    }

    public static void laboratory(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  Stats stats) {
        System.out.println(stats.changeActions(1));
        printstats(hand, stats);
        System.out.println();
        System.out.println("Drawing 2 cards...");
        for (int i=0; i<2; ++i) {
            drawCard(hand, drawPile, discardPile, stats, true);
        }
    }

    public static void library(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                               ArrayList<Card> hand, Stats stats) {
        while (hand.size() < 7) {
            Card card = drawCard(hand, drawPile, discardPile, stats, true);
            if (card.getType().contains("Action")) {
                System.out.println("Would you like to discard this card? (1) yes, (2) no:");
                String optionStr = scnr.nextLine();
                int choice = getValidDigit(scnr, optionStr, 2);
                if (choice == 1) {
                    discardCard(hand, discardPile, card, stats, true);
                }
            }
        }
    }

    public static void menagerie(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                 Stats stats) {
        System.out.println(stats.changeActions(1));

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
                drawCard(hand, drawPile, discardPile, stats, true);
            }
        } else {
            System.out.println("Your hand does not contain all different cards.\n");
            System.out.println("Drawing a card...");
            drawCard(hand, drawPile, discardPile, stats, true);
        }

    }

    public static void market(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(1));
        System.out.println(stats.changeBuys(1));
        System.out.println(stats.changeMoney(1));
    }

    public static void merchant(ArrayList<Card> hand, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(1));
    }

    public static void militia(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> discardPile, Stats stats,
                               boolean opponent) {
        if (!opponent) {
            System.out.println(stats.changeMoney(2));
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
                discardCard(hand, discardPile, cardToDiscard, stats, true);
            }
            System.out.println("\nNew hand stats:");
            printstats(hand, stats);
        }
    }

    public static void mill(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                            Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(1));

        System.out.println("Would you like to discard 2 cards? (1) yes, (2) no");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 2);
        if (choice == 1) {
            System.out.println("Choose 2 cards to discard.\n");
            for (int i=0; i<2; ++i) {
                System.out.println("Enter card " + (i+1) + ".");
                Card cardToDiscard = printOptions(scnr, hand, "Mill", 0);
                discardCard(hand, discardPile, cardToDiscard, stats, true);
            }

            System.out.println(stats.changeMoney(2));
        }
    }

    public static void mine(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                            ArrayList<Card> trashedCards, Stats stats) {
        System.out.println("Choose a treasure card from your hand to trash.\n");

        Card cardTrashed = printOptions(scnr, hand, "Mine", 0);
        if (!cardTrashed.getType().equals("")) {
            trashCard(hand, trashedCards, cardTrashed, stats, true);
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
        stats.changeMoney(cardToGain.getValue());
        System.out.println("Done.\n");
    }

    public static void miningVillage(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                     ArrayList<Card> hand, Stats stats, ArrayList<Card> actionsPlayed) {
        System.out.println(stats.changeActions(2));

        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println("Would you like to trash this Mining Village card for +$2? (1) yes, (2) no:");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 2);
        if (choice == 1) {
            stats.changeMoney(2);
            for (Card card : actionsPlayed) {
                if (card.getName().equals("Mining Village")) {
                    card.setTrashCard(true);
                    break;
                }
            }
        }
    }

    public static void moat(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, Stats stats) {
        System.out.println("Drawing 2 cards...");
        for (int i=0; i<2; ++i) {
            drawCard(hand, drawPile, discardPile, stats, true);
        }
    }

    public static void moneylender(ArrayList<Card> hand, ArrayList<Card> trashedCards, Stats stats) {
        boolean trashedCopper = false;
        for (Card card : hand) {
            if (card.getName().equals("Copper")) {
                trashCard(hand, trashedCards, card, stats, true);
                trashedCopper = true;
                break;
            }
        }

        if (trashedCopper) {
            System.out.println(stats.changeMoney(3));
        } else {
            System.out.println("Sorry, you did not have a copper to trash. No extra money was added.");
        }

    }

    public static void monument(Stats stats, PointTokens pointTokenMat) {
        System.out.println(stats.changeMoney(2));
        System.out.println("Gaining a victory point token...");
        pointTokenMat.increasePoints(1);
    }

    public static void mountebank(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  ArrayList<Card> cardsInMiddle, Stats stats, boolean opponent) {
        if (!opponent) {
            System.out.println(stats.changeMoney(2));
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
                    discardCard(hand, discardPile, card, stats, true);
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
                            ArrayList<Card> hand, Stats stats) {
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
            drawCard(hand, drawPile, discardPile, stats, true);
        }
        if ((choice1 == 2) || (choice2 == 2)) {
            System.out.println(stats.changeActions(1));
        }
        if ((choice1 == 3) || (choice2 == 3)) {
            System.out.println(stats.changeBuys(1));
        }
        if ((choice1 == 4) || (choice2 == 4)) {
            System.out.println(stats.changeMoney(1));
        }
    }

    public static void peddler(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, Stats stats) {
        System.out.println(stats.changeActions(1));
        System.out.println(stats.changeMoney(1));

        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);
    }

    public static void poacher(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                               ArrayList<Card> hand, ArrayList<Card> cardsInMiddle, Stats stats) {

        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeMoney(1));
        System.out.println(stats.changeActions(1));

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
            discardCard(hand, discardPile, cardToDiscard, stats, true);
            System.out.println("Done.\n");
        }

    }

    public static void remake(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                              ArrayList<Card> cardsInMiddle, ArrayList<Card> trashedCards, Stats stats) {
        for (int i=0; i<2; ++i) {
            System.out.println("Choose a card from your hand to trash.\n");
            Card cardTrashed = printOptions(scnr, hand, "Remake-Trash", 0);
            if (!cardTrashed.getType().equals("")) {
                trashCard(hand, trashedCards, cardTrashed, stats, true);
            }

            System.out.println("Now choose a card to gain.\n");
            Card cardToGain = printOptions(scnr, cardsInMiddle, "Remake-Gain", cardTrashed.getCost());
            if (!cardToGain.getType().equals("")) {
                gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, cardToGain);
            }
        }
    }

    public static void remodel(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                               ArrayList<Card> cardsInMiddle, ArrayList<Card> trashedCards, Stats stats) {
        System.out.println("Choose a card from your hand to trash.\n");
        Card cardTrashed = printOptions(scnr, hand, "Remodel-Trash", 0);
        if (!cardTrashed.getType().equals("")) {
            trashCard(hand, trashedCards, cardTrashed, stats, true);
        }

        // Gain a card costing up to $2 more
        System.out.println("Now choose a card to gain.\n");
        Card cardToGain = printOptions(scnr, cardsInMiddle, "Remodel-Gain", cardTrashed.getCost());
        if (!cardToGain.getType().equals("")) {
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, cardToGain);
        }
    }

    public static void sentry(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                              ArrayList<Card> trashedCards, Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(1));

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

    public static void shantyTown(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  Stats stats) {
        System.out.println(stats.changeActions(2));

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
                drawCard(hand, drawPile, discardPile, stats, true);
            }
        }

    }

    public static void smithy(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand, Stats stats) {
        System.out.println("Drawing 3 cards...");
        for (int i=0; i<3; ++i) {
            drawCard(hand, drawPile, discardPile, stats, true);
        }
    }

    public static void spy(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                           Stats stats, boolean opponent) {
        String prompt = "Would your opponent like you to (1) keep it there or (2) discard it?";

        if (drawPile.size() < 1) {
            Collections.shuffle(discardPile);
            drawPile.addAll(discardPile);
            discardPile.clear();
        }
        if (!opponent) {
            prompt = "Would you like to (1) keep it there or (2) discard it?";
            System.out.println("Drawing a card...");
            drawCard(hand, drawPile, discardPile, stats, true);

            System.out.println(stats.changeActions(1));

            attack(scnr);
        }

        System.out.println("The top card of your deck is: " + drawPile.get(0).getName() + ".");
        System.out.println(prompt);
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 2);
        if (choice == 2) {
            discardCard(drawPile, discardPile, drawPile.get(0), stats, false);
        }
    }

    public static void steward(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                               ArrayList<Card> hand, ArrayList<Card> trashedCards, Stats stats) {
        System.out.println("1. +2 Cards\n2. +$2\n3. Trash 2 cards from your hand.\n");
        System.out.println("Choose an option:");
        String choiceStr = scnr.nextLine();
        int choice = getValidDigit(scnr, choiceStr, 3);

        if (choice == 1) {
            System.out.println("Drawing 2 cards...");
            for (int i=0; i<2; ++i) {
                drawCard(hand, drawPile, discardPile, stats, true);
            }
        } else if (choice == 2) {
            System.out.println(stats.changeMoney(2));
        } else {
            System.out.println("Trash 2 cards from your hand.\n");
            trashMultiple(scnr, hand, trashedCards, stats, 2);
        }

    }

    public static void thief(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
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
                            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, findCard("Copper",
                                    cardsInMiddle));
                            break;
                        case 2:
                            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, findCard("Silver",
                                    cardsInMiddle));
                            break;
                        case 3:
                            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, findCard("Gold", cardsInMiddle));
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

    public static Card throneRoomKingCourt(Scanner scnr, ArrayList<Card> actionsPlayed, ArrayList<Card> drawPile,
                                           ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> cardsInMiddle,
                                           ArrayList<Card> trashedCards, Stats stats, int numPlays,
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
                playAction(scnr, action, actionsPlayed, cardsInMiddle, drawPile, discardPile, hand, trashedCards, stats,
                        pointTokenMat);
            }
            action.decreaseNumDurationPlays();
            return action;
        } else {
            System.out.println("You have no action cards to play.\n");
            return new Card();
        }
    }

    public static void torturer(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                ArrayList<Card> cardsInMiddle, Stats stats, boolean opponent) {
        if (!opponent) {
            System.out.println("Drawing 3 cards...");
            for (int i=0; i<3; ++i) {
                drawCard(hand, drawPile, discardPile, stats, true);
            }
            System.out.println("Your opponent may now choose option 2 of their menu to be affected by your attack.");
            System.out.println("Press enter when they have done so:");
            scnr.nextLine();
        } else {
            System.out.println("Do you choose to (1) discard 2 cards, or (2) gain a curse to your hand?");
            System.out.println("Enter your choice:");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, 2);

            if (choice == 1) {
                System.out.println("Choose 2 cards to discard.\n");
                for (int i=0; i<2; ++i) {
                    System.out.println("Enter card " + (i+1) + ".");
                    Card cardToDiscard = printOptions(scnr, hand, "Vault", 0);
                    discardCard(hand, discardPile, cardToDiscard, stats, true);
                }
            } else {
                gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, findCard("Curse", cardsInMiddle));
            }
        }
    }

    public static void upgrade(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                               ArrayList<Card> cardsInMiddle, ArrayList<Card> trashedCards, Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(1));

        System.out.println("Choose a card from your hand to trash.\n");
        Card cardTrashed = printOptions(scnr, hand, "Remodel-Trash", 0);
        if (!cardTrashed.getType().equals("")) {
            trashCard(hand, trashedCards, cardTrashed, stats, true);
        }

        // Gain a card costing up to $1 more
        System.out.println("Now choose a card to gain.\n");
        Card cardToGain = printOptions(scnr, cardsInMiddle, "Remake-Gain", cardTrashed.getCost());
        if (!cardToGain.getType().equals("")) {
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, cardToGain);
        }
    }

    public static void vassal(Scanner scnr, ArrayList<Card> actionsPlayed, ArrayList<Card> cardsInMiddle,
                              ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                              ArrayList<Card> trashedCards, Stats stats, PointTokens pointTokenMat) {
        System.out.println(stats.changeMoney(2));

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
            playAction(scnr, topCard, actionsPlayed, cardsInMiddle, drawPile, discardPile, hand, trashedCards, stats,
                    pointTokenMat);
        } else {
            System.out.println("It is not an action. Discarding it...");
            System.out.println("Done.\n");
        }

        discardPile.add(topCard);
    }

    public static void vault(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                             Stats stats, boolean opponent) {
        if (!opponent) {
            System.out.println("Drawing 2 cards...");
            for (int i=0; i<2; ++i) {
                drawCard(hand, drawPile, discardPile, stats, true);
            }

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

            int numDiscarded = 0;
            while (scanChoices.hasNextInt()) {
                option = scanChoices.nextInt();
                System.out.println();
                Card chosenCard = options.get(option - 1);
                discardCard(hand, discardPile, chosenCard, stats, true);
                ++numDiscarded;
            }

            if (numDiscarded > 0) {
                System.out.println(stats.changeMoney(numDiscarded));
            }

            System.out.println("Your opponent may now choose option 2 of their menu to discard 2 cards and draw 1.");
            System.out.println("Press enter when they have done so:");
        } else {
            System.out.println("Would you like to discard 2 cards to draw a new card? (1) yes, (2) no:");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, 2);
            if (choice == 1) {
                System.out.println("Choose 2 cards to discard.\n");
                for (int i=0; i<2; ++i) {
                    System.out.println("Enter card " + (i+1) + ".");
                    Card cardToDiscard = printOptions(scnr, hand, "Vault", 0);
                    discardCard(hand, discardPile, cardToDiscard, stats, true);
                }

                System.out.println("Drawing a replacement card...");
                drawCard(hand, drawPile, discardPile, stats, true);
            }
        }
    }

    public static void village(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                               Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(2));
    }

    public static void witch(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                             ArrayList<Card> hand, Stats stats, boolean opponent) {
        if (!opponent) {
            System.out.println("Drawing 2 cards...");
            for (int i=0; i<2; ++i) {
                drawCard(hand, drawPile, discardPile, stats, true);
            }

            attack(scnr);
            findCard("Curse", cardsInMiddle).decreaseNumRemaining();
        } else {
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, findCard("Curse", cardsInMiddle));
        }
    }

    public static void wanderingMinstrel(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                         ArrayList<Card> hand, Stats stats) {
        System.out.println("Drawing a card...");
        drawCard(hand, drawPile, discardPile, stats, true);

        System.out.println(stats.changeActions(2));

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

    public static void watchtower(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
     Stats stats) {
        while (hand.size() < 6) {
            System.out.println("Drawing a card...");
            drawCard(hand, drawPile, discardPile, stats, true);
        }
    }

    public static void wharf(ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                             ArrayList<Card> hand, Stats stats) {
        for (int i=0; i<2; ++i) {
            System.out.println("Drawing a card...");
            drawCard(hand, drawPile, discardPile, stats, true);
        }
        System.out.println(stats.changeBuys(1));
    }

    public static void woodcutter(Stats stats) {
        System.out.println(stats.changeBuys(1));
        System.out.println(stats.changeMoney(2));
    }

    public static void workshop(Scanner scnr, ArrayList<Card> hand, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                ArrayList<Card> cardsInMiddle) {
        System.out.println("You may gain a card costing up to $4.\n");

        Card cardGained = printOptions(scnr, cardsInMiddle, "Workshop", 0);
        if (!cardGained.getType().equals("")) {
            gainCard(scnr, cardsInMiddle, hand, drawPile, discardPile, cardGained);
        }
    }

}

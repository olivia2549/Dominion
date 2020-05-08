import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Dominion {
    public static final int MONEY_POS = 0;
    public static final int ACTIONS_POS = 1;
    public static final int BUYS_POS = 2;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Card> allCards = new ArrayList<>();

        File file = new File("iofiles/dominionCards.txt");
        Scanner fileScan = new Scanner(file);
        ArrayList<Card> actionCards = loadCards(fileScan, allCards);

        Scanner scnr = new Scanner(System.in);
        Random rand = new Random();
        rand.setSeed(7);

        ArrayList<Card> drawPile = new ArrayList<>();
        ArrayList<Card> discardPile = new ArrayList<>();
        ArrayList<Card> cardsInMiddle = setup(scnr, allCards, actionCards, drawPile, rand);

        System.out.println("Press enter to begin your first turn.");
        scnr.nextLine();

        int[] info = new int[3];
        takeTurns(scnr, cardsInMiddle, drawPile, discardPile, info);

        endGame(drawPile, discardPile);

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
                    Integer.parseInt(cardInfo[5].trim()));
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
        System.out.println("Welcome! Please choose a game setup");

        System.out.println("1. First Game: Cellar, Market, Militia, Mine, Moat, Remodel, Smithy, Village, Woodcutter," +
                " Workshop");
        System.out.println("2. Big Money: Adventurer, Bureaucrat, Chancellor, Chapel, Feast, Laboratory, Market, " +
                "Mine, Moneylender, Throne Room");
        System.out.println("3. Interaction: Bureaucrat, Chancellor, Council Room, Festival, Library, Militia, Moat, " +
                "Spy, Thief, Village");
        System.out.println("4. Size Distortion: Cellar, Chapel, Feast, Gardens, Laboratory, Thief, Village, Witch, " +
                "Woodcutter, Workshop");
        System.out.println("5. Village Square: Bureaucrat, Cellar, Festival, Library, Market, Remodel, Smithy, Throne" +
                " Room, Village, Woodcutter");
        System.out.println("6. Custom");
        System.out.println("7. Random");

        // Filling the cards in "middle" with the action cards chosen by the user
        System.out.println("\nType 1, 2, 3, 4, 5, 6, or 7:");
        ArrayList<Card> cardsInMiddle = new ArrayList<>();
        String setupStr = scnr.nextLine();
        int setup = getValidDigit(scnr, setupStr, 7);
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
        } else if (setup == 6) {    // Custom
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
        System.out.println("Available action cards:");
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

    public static void takeTurns(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                 ArrayList<Card> discardPile, int[] info) {
        ArrayList<Card> hand = new ArrayList<>();
        ArrayList<Card> cardsPlayed = new ArrayList<>();

        // Keep taking turns as long as there are provinces left
        while (findCard("Province", cardsInMiddle).getNumRemaining() > 0) {
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

            // Let the player choose menu options
            System.out.println();
            manageMenu(scnr, cardsInMiddle, hand, drawPile, discardPile, cardsPlayed, info);

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

    public static void manageMenu(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                  ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> cardsPlayed,
                                  int[] info) {
        printMenuOptions();
        String menuChoiceStr = scnr.nextLine();
        int menuChoice = getValidDigit(scnr, menuChoiceStr, 5);

        while (menuChoice != 5) {

            // Keeps track of whether the menu options should be reprinted after user chooses a number
            boolean noPrintOptions = false;

            if (menuChoice == 1) {  // Opponent bought province
                boughtProvince(cardsInMiddle);
            } else if (menuChoice == 2) {   // Opponent played attack card or council room
                playedAttackCard(scnr, cardsInMiddle, hand, drawPile, discardPile, info);
            } else if (menuChoice == 3) {   // Choose and play an action from your hand
                chooseAction(scnr, cardsInMiddle, drawPile, discardPile, hand, cardsPlayed, info);
            } else if (menuChoice == 4) {   // Buy a card
                noPrintOptions = buyCard(scnr, cardsInMiddle, discardPile, hand, info);
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

    }

    public static void endGame(ArrayList<Card> drawPile, ArrayList<Card> discardPile) {
        System.out.println("\n\n------------------------------- GAME OVER -------------------------------");

        int points = 0;
        int numCards = 0;
        int numGardens = 0;
        int numEstates = 0;
        int numDuchys = 0;
        int numProvinces = 0;
        int numCurses = 0;

        // Put all the cards into one pile and count up the points
        discardPile.addAll(drawPile);
        for (Card card : discardPile) {
            ++numCards;
            if (card.getType().equals("Victory") || card.getType().equals("Curse")) {
                points += card.getValue();
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
                }
            } else if (card.getName().equals("Gardens")) {
                numGardens += 1;
            }
        }
        int numPointsPerGarden = discardPile.size()/10;
        points += numPointsPerGarden*numGardens;

        System.out.println("You ended with " +
                numCards + " total cards, " +
                numEstates + " estates, " +
                numDuchys + " duchys, " +
                numProvinces + " provinces, " +
                numGardens + " gardens, and " +
                numCurses + " curses.");
        System.out.println("Total points: " + points);
    }

    public static void chooseAction(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                       ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> cardsPlayed,
                                        int[] info) {
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
            System.out.println((options.size() + 1) + ". Cancel");

            System.out.println();
            if (hasAction) {
                System.out.println("Choose an action to play:");
                String optionStr = scnr.nextLine();
                int choice = getValidDigit(scnr, optionStr, option);
                if (choice != (options.size() + 1)) {
                    Card cardPlayed = options.get(choice - 1);

                    System.out.println();
                    System.out.println("Playing the " + cardPlayed.getName() + " card...");
                    hand.remove(cardPlayed);
                    info[ACTIONS_POS] -= 1;
                    boolean addToPlayed = playAction(scnr, cardPlayed, cardsInMiddle, drawPile, discardPile, hand,
                            info);
                    if (addToPlayed) {
                        cardsPlayed.add(cardPlayed);
                    }
                } else {
                    System.out.println("Action cancelled.");
                }
                System.out.println();
                printInfo(hand, info);
            } else {
                System.out.println("Sorry, you don't have any action cards. Cannot execute option.");
                System.out.println("Please enter a different choice:");
            }
        }
    }

    public static boolean playAction(Scanner scnr, Card cardPlayed, ArrayList<Card> cardsInMiddle, ArrayList<Card> drawPile,
                                     ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
        boolean addToDiscard = true;

        switch (cardPlayed.getName()) {
            case "Adventurer":
                adventurer(drawPile, discardPile, hand, info);
                break;
            case "Ambassador":
                ambassador(scnr, cardsInMiddle, discardPile, hand, info, false);
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
                chapel(scnr, hand, info);
                break;
            case "Council Room":
                councilRoom(scnr, drawPile, discardPile, hand, info, false);
                break;
            case "Feast":
                feast(scnr, cardsInMiddle, discardPile, hand, info);
                addToDiscard = false;   // This card gets trashed so don't discard it after use
                break;
            case "Festival":
                festival(info);
                break;
            case "Junk Dealer":
                junkDealer(scnr, drawPile, discardPile, hand, info);
                break;
            case "King's Court":
                throneRoomKingCourt(scnr, drawPile, discardPile, hand, cardsInMiddle, info, 3);
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
            case "Militia":
                militia(scnr, hand, discardPile, info, false);
                break;
            case "Mine":
                mine(scnr, cardsInMiddle, hand, info);
                break;
            case "Moat":
                moat(drawPile, discardPile, hand, info);
                break;
            case "Moneylender":
                moneylender(hand, info);
                break;
            case "Mountebank":
                mountebank(scnr, discardPile, hand, cardsInMiddle, info, false);
                break;
            case "Remake":
                remake(scnr, discardPile, hand, cardsInMiddle, info);
                break;
            case "Remodel":
                remodel(scnr, discardPile, hand, cardsInMiddle, info);
                break;
            case "Smithy":
                smithy(drawPile, discardPile, hand, info);
                break;
            case "Spy":
                spy(scnr, drawPile, discardPile, hand, info, false);
                break;
            case "Steward":
                steward(scnr, drawPile, discardPile, hand, info);
                break;
            case "Thief":
                thief(scnr, drawPile, discardPile, cardsInMiddle, false);
                break;
            case "Throne Room":
                throneRoomKingCourt(scnr, drawPile, discardPile, hand, cardsInMiddle, info, 2);
                break;
            case "Village":
                village(drawPile, discardPile, hand, info);
                break;
            case "Wandering Minstrel":
                wanderingMinstrel(scnr, drawPile, discardPile, hand, info);
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
        return addToDiscard;
    }

    public static boolean buyCard(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  int[] info) {

        if (info[BUYS_POS] == 0) {
            System.out.println("Sorry, you have no more buys left. Cannot execute option.\n");
            System.out.println("Please enter a different choice:");
            return true;
        } else {
            // Allow user to choose a card to buy
            int option = 1;
            ArrayList<Card> options = new ArrayList<>();

            System.out.println();
            System.out.println("Your options:");
            for (Card card : cardsInMiddle) {   // Make sure the options are only cards the user can afford
                if (card.getCost() <= info[MONEY_POS]) {
                    System.out.println(option + ". " + card + "\n");
                    options.add(card);
                    ++option;
                }
            }
            System.out.println((options.size() + 1) + ". Cancel");

            System.out.println();
            System.out.println("Choose an option to buy:");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, option);

            System.out.println();
            if (choice != options.size() + 1) {
                Card cardBought = options.get(choice - 1);
                System.out.println("You bought the " + cardBought.getName() + " card.\n");
                boolean boughtProvince = false;
                if (cardBought.getName().equals("Province")) {
                    boughtProvince(cardsInMiddle);
                    boughtProvince = true;
                } else if (cardBought.getName().equals("Border Village")) {
                    System.out.println("You may now gain a cheaper card.");
                    Card cardToGain = printOptions(scnr, cardsInMiddle, "Border Village", 5);
                    gainCard(discardPile, cardToGain);
                }
                // Decrease the buys and money left for this turn
                info[BUYS_POS] -= 1;
                info[MONEY_POS] -= cardBought.getCost();

                discardPile.add(cardBought);
                if (!boughtProvince) {
                    cardBought.decreaseNumRemaining();
                }
            } else {
                System.out.println("Buy cancelled.\n");
            }

            System.out.println();
            printInfo(hand, info);
            return false;
        }

    }

    public static void playedAttackCard(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                                        ArrayList<Card> drawPile, ArrayList<Card> discardPile, int[] info) {

        System.out.println("\nWhich of these cards did your opponent play?");
        System.out.println("1. Bureaucrat\n2. Militia\n3. Spy\n4. Thief\n5. Witch\n6. Mountebank\n7. Ambassador\n8. " +
                "Council Room");
        System.out.println("\nChoose an option:");

        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 8);
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
                thief(scnr, drawPile, discardPile, cardsInMiddle, true);
                break;
            case 5:
                witch(scnr, cardsInMiddle, drawPile, discardPile, hand, info, true);
                break;
            case 6:
                mountebank(scnr, discardPile, hand, cardsInMiddle, info, true);
                break;
            case 7:
                ambassador(scnr, cardsInMiddle, discardPile, hand, info, true);
                break;
            case 8:
                councilRoom(scnr, drawPile, discardPile, hand, info, true);
                break;
        }
    }

    public static void attack(Scanner scnr) {
        System.out.println("If your opponent does not have a reaction card, they must now choose option 2 of " +
                "their menu to be affected by your attack.");
        System.out.println("Press enter when they have done so:");
        scnr.nextLine();
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
        if (card.getType().equals("Treasure")) {
            info[MONEY_POS] += card.getValue();
        }

        drawPile.remove(0);

        return card;
    }

    public static void gainCard(ArrayList<Card> discardPile, Card cardToGain) {
        System.out.println("Gaining the " + cardToGain.getName() + " card...");
        discardPile.add(cardToGain);
        cardToGain.decreaseNumRemaining();
        System.out.println("Done.\n");
    }

    public static void discardCard(ArrayList<Card> hand, ArrayList<Card> discardPile, Card cardToDiscard, int[] info) {
        discardPile.add(cardToDiscard);
        if (cardToDiscard.getType().equals("Treasure")) {
            info[MONEY_POS] -= cardToDiscard.getValue();
        }
        hand.remove(cardToDiscard);
    }

    public static void trashCard(ArrayList<Card> hand, Card cardToTrash, int[] info) {
        System.out.println("Trashing the " + cardToTrash.getName() + " card...");

        if (cardToTrash.getType().equals("Treasure")) {
            info[MONEY_POS] -= cardToTrash.getValue();
        }

        hand.remove(cardToTrash);
        System.out.println("Done.\n");
    }

    public static void trashMultiple(Scanner scnr, ArrayList<Card> hand, int[] info, int numToTrash) {
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
                trashCard(hand, chosenCard, info);
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
                    condition = card.getType().equals("Treasure") && !card.getType().equals("Gold");
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

    public static void adventurer(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  int[] info) {
        for (int i=0; i<2; ++i) {
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("Drew a(n) " + card.getName());
            while (!card.getType().equals("Treasure")) {
                System.out.println("Not a treasure card. Discarding and drawing another...");
                discardCard(hand, discardPile, card, info);
                card = drawCard(hand, drawPile, discardPile, info);
                System.out.println("Drew a(n) " + card.getName());
            }
            System.out.println();
        }
    }

    public static void ambassador(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> discardPile,
                                  ArrayList<Card> hand, int[] info, boolean opponent) {
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

            trashCard(hand, cardToReturn, info);
            if (choice == 1) {  // Remove the card again if they wanted both copies trashed.
                trashCard(hand, cardToReturn, info);
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
                if (card.getType().equals("Victory")) {
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

        System.out.println("Adding +1 Action:");
        info[ACTIONS_POS] += 1;
        System.out.println();

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
            discardCard(hand, discardPile, chosenCard, info);

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

    public static void chapel(Scanner scnr, ArrayList<Card> hand, int[] info) {
        trashMultiple(scnr, hand, info, 4);
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

    public static void feast(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> discardPile,
                             ArrayList<Card> hand, int[] info) {
        trashCard(hand, findCard("Feast", cardsInMiddle), info);
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

    public static void junkDealer(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                  ArrayList<Card> hand, int[] info) {
        System.out.println("Adding +1 Action and +$1...");
        info[ACTIONS_POS] += 1;
        info[MONEY_POS] += 1;
        System.out.println("Done\n");
        System.out.println("Drawing a card...");
        Card cardDrawn = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + cardDrawn.getName() + " card.\n");

        System.out.println("Choose a card to trash.\n");
        Card cardToTrash = printOptions(scnr, hand, "Junk Dealer", 0);
        if (!cardToTrash.getName().equals("")) {
            trashCard(hand, cardToTrash, info);
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
                    discardCard(hand, discardPile, card, info);
                }
            }
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
                discardCard(hand, discardPile, cardToDiscard, info);
                System.out.println("Done.");
            }
            System.out.println("\nNew hand info:");
            printInfo(hand, info);
        }
    }

    public static void mine(Scanner scnr, ArrayList<Card> cardsInMiddle, ArrayList<Card> hand,
                            int[] info) {
        System.out.println("Choose a treasure card from your hand to trash.\n");

        Card cardTrashed = printOptions(scnr, hand, "Mine", 0);
        if (!cardTrashed.getName().equals("")) {
            trashCard(hand, cardTrashed, info);
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

    public static void moneylender(ArrayList<Card> hand, int[] info) {
        boolean trashedCopper = false;
        for (Card card : hand) {
            if (card.getName().equals("Copper")) {
                trashCard(hand, card, info);
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

    public static void mountebank(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  ArrayList<Card> cardsInMiddle, int[] info, boolean opponent) {
        if (!opponent) {
            System.out.println("Adding +$2...");
            info[MONEY_POS] += 2;
            System.out.println("Done.\n");
            attack(scnr);
        } else {
            boolean hasCurse = false;
            for (Card card : hand) {
                if (card.getType().equals("Curse")) {
                    hasCurse = true;
                    System.out.println("Discarding the curse...");
                    discardCard(hand, discardPile, card, info);
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

    public static void remake(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> cardsInMiddle,
                              int[] info) {
        for (int i=0; i<2; ++i) {
            System.out.println("Choose a card from your hand to trash.\n");
            Card cardTrashed = printOptions(scnr, hand, "Remake-Trash", 0);
            if (!cardTrashed.getName().equals("")) {
                trashCard(hand, cardTrashed, info);
            }

            System.out.println("Now choose a card to gain.\n");
            Card cardToGain = printOptions(scnr, cardsInMiddle, "Remake-Gain", cardTrashed.getCost());
            if (!cardToGain.getName().equals("")) {
                gainCard(discardPile, cardToGain);
            }
        }
    }

    public static void remodel(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand, ArrayList<Card> cardsInMiddle,
                               int[] info) {
        System.out.println("Choose a card from your hand to trash.\n");
        Card cardTrashed = printOptions(scnr, hand, "Remodel-Trash", 0);
        if (!cardTrashed.getName().equals("")) {
            trashCard(hand, cardTrashed, info);
        }

        // Gain a card costing up to $2 more
        System.out.println("Now choose a card to gain.\n");
        Card cardToGain = printOptions(scnr, cardsInMiddle, "Remodel-Gain", cardTrashed.getCost());
        if (!cardToGain.getName().equals("")) {
            gainCard(discardPile, cardToGain);
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
        if (drawPile.size() < 1) {
            Collections.shuffle(discardPile);
            drawPile.addAll(discardPile);
            discardPile.clear();
        }
        if (!opponent) {
            System.out.println("Drawing a card...");
            Card card = drawCard(hand, drawPile, discardPile, info);
            System.out.println("You drew the " + card.getName() + " card.");

            System.out.println("\nAdding +1 Action...");
            info[ACTIONS_POS] += 1;
            System.out.println("Done.");

            System.out.println();
            System.out.println("The top card of your deck is: " + drawPile.get(0).getName() + ".");
            System.out.println("Would you like to (1) keep it there or (2) discard it?");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, 2);
            if (choice == 2) {
                discardPile.add(drawPile.get(0));
                drawPile.remove(0);
            }

            System.out.println();
            attack(scnr);
        } else {
            Card topCard = drawPile.get(0);
            System.out.println("The top card of your deck is the " + topCard.getName() + " card.");
            System.out.println("Would your opponent like you to (1) discard it, or (2) put it back? Choose an option:");
            String optionStr = scnr.nextLine();
            int option = getValidDigit(scnr, optionStr, 2);
            if (option == 1) {
                System.out.println("\nDiscarding the " + topCard.getName() + " card...");
                discardPile.add(topCard);
                drawPile.remove(topCard);
                System.out.println("Done.");
            } else {
                System.out.println("\nOkay, the card is now back in your draw pile.");
            }
        }
    }

    public static void steward(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                               ArrayList<Card> hand, int[] info) {
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
            trashMultiple(scnr, hand, info, 2);
        }

    }

    public static void thief(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                             ArrayList<Card> cardsInMiddle, boolean opponent) {
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
            if (topCard.getType().equals("Treasure")) {
                whichIsTreasure = 1;
            } else if (secondCard.getType().equals("Treasure")) {
                whichIsTreasure = 2;
            }
            if (topCard.getType().equals("Treasure") && secondCard.getType().equals("Treasure")) {
                whichIsTreasure = 3;
            }

            System.out.println("The top 2 cards of your deck are the " + topCard.getName() + " card and the " +
                    secondCard.getName() + " card.");

            System.out.println();
            if ((whichIsTreasure == 1) || (whichIsTreasure == 2)) {
                System.out.println("You have a treasure card. Would your opponent like you to trash it? (1) yes, (2) no:");
                String optionStr = scnr.nextLine();
                int option = getValidDigit(scnr, optionStr, 2);
                if (option == 1) {
                    if (whichIsTreasure == 1) {
                        System.out.println("Trashing the " + topCard.getName() + " card...");
                        drawPile.remove(topCard);
                        System.out.println("Discarding the " + secondCard.getName() + " card...");
                        discardPile.add(secondCard);
                    } else {
                        System.out.println("Trashing the " + secondCard.getName() + " card...");
                        drawPile.remove(secondCard);
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
                    drawPile.remove(topCard);
                    System.out.println("Discarding the " + secondCard.getName() + " card...");
                    drawPile.remove(secondCard);
                    discardPile.add(secondCard);
                    System.out.println("Done.");
                } else if (option == 2) {
                    System.out.println("Trashing the " + secondCard.getName() + " card...");
                    drawPile.remove(secondCard);
                    System.out.println("Discarding the " + topCard.getName() + " card...");
                    drawPile.remove(topCard);
                    discardPile.add(topCard);
                    System.out.println("Done.");
                }
            } else {
                System.out.println("You have no treasure cards. Discarding them...");
                discardPile.add(topCard);
                discardPile.add(secondCard);
                drawPile.remove(topCard);
                drawPile.remove(secondCard);
                System.out.println("Done.");
            }
            System.out.println();

        }

    }

    public static void throneRoomKingCourt(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                  ArrayList<Card> hand, ArrayList<Card> cardsInMiddle, int[] info, int numPlays) {

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
                System.out.println("\nPlaying the " + action.getName() + " card...");
                playAction(scnr, action, cardsInMiddle, drawPile, discardPile, hand, info);
            }

            System.out.println();
        } else {
            System.out.println("You have no action cards to play.\n");
        }
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

            System.out.println("\nYour opponent must now choose option 2 on their menu to be affected by your attack.");
            System.out.println("Press enter when they have done so:");
            scnr.nextLine();
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

    public static void woodcutter(int[] info) {
        System.out.println("Adding +1 Buy and +$2:");
        info[BUYS_POS] += 1;
        info[MONEY_POS] += 2;
        System.out.println("Done.");
    }

    public static void workshop(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> cardsInMiddle) {
        System.out.println("You may gain a card costing up to $4.\n");

        Card cardGained = printOptions(scnr, cardsInMiddle, "Workshop", 0);
        if (!cardGained.getName().equals("")) {
            gainCard(discardPile, cardGained);
        }
    }

}

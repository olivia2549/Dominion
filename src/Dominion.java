import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Dominion {
    public static final int MONEY_POS = 0;
    public static final int ACTIONS_POS = 1;
    public static final int BUYS_POS = 2;
    public static final int ALL_CARD_TYPES = 32;

    public static void main(String[] args) throws FileNotFoundException {
        Card[] allCards = new Card[ALL_CARD_TYPES];

        File file = new File("iofiles/dominionCards.txt");
        Scanner fileScan = new Scanner(file);
        loadCards(fileScan, allCards);

        Scanner scnr = new Scanner(System.in);
        ArrayList<Card> drawPile = new ArrayList<>();
        ArrayList<Card> discardPile = new ArrayList<>();
        Card[] cardsInMiddle = setup(scnr, allCards, drawPile);

        System.out.println("Press enter to begin your first turn.");
        scnr.nextLine();

        int[] info = new int[3];
        takeTurns(scnr, cardsInMiddle, drawPile, discardPile, info);

        endGame(drawPile, discardPile);

    }

    public static void loadCards(Scanner fileScan, Card[] allCards) {
        fileScan.useDelimiter("\n\n");

        // Reading from the input file to add all the cards
        for (int i = 0; i < ALL_CARD_TYPES; ++i) {
            String line = fileScan.next();
            String[] cardInfo = line.split(",");
            Card card = new Card(cardInfo[0].trim(),
                    cardInfo[1].trim(),
                    cardInfo[2].trim(),
                    Integer.parseInt(cardInfo[3].trim()),
                    Integer.parseInt(cardInfo[4].trim()),
                    Integer.parseInt(cardInfo[5].trim()));
            allCards[i] = card;
        }

        // Printing out all the action cards and their descriptions so the user can choose
        System.out.println("ALL ACTION CARDS:\n");
        for (Card card : allCards) {
            if (card.getType().contains("Action")) {
                System.out.println(card + "\n");
            }
        }

        System.out.println();
    }

    public static Card[] setup(Scanner scnr, Card[] allCards, ArrayList<Card> drawPile) {
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

        // Filling the cards in "middle" with the action cards chosen by the user
        System.out.println("\nType 1, 2, 3, 4, 5, or 6:");
        Card[] cardsInMiddle = new Card[17];
        String setupStr = scnr.nextLine();
        int setup = getValidDigit(scnr, setupStr, 6);
        if (setup == 1) {
            cardsInMiddle[0] = findCard("Cellar", allCards);
            cardsInMiddle[1] = findCard("Market", allCards);
            cardsInMiddle[2] = findCard("Militia", allCards);
            cardsInMiddle[3] = findCard("Mine", allCards);
            cardsInMiddle[4] = findCard("Moat", allCards);
            cardsInMiddle[5] = findCard("Remodel", allCards);
            cardsInMiddle[6] = findCard("Smithy", allCards);
            cardsInMiddle[7] = findCard("Village", allCards);
            cardsInMiddle[8] = findCard("Woodcutter", allCards);
            cardsInMiddle[9] = findCard("Workshop", allCards);
        } else if (setup == 2) {
            cardsInMiddle[0] = findCard("Adventurer", allCards);
            cardsInMiddle[1] = findCard("Bureaucrat", allCards);
            cardsInMiddle[2] = findCard("Chancellor", allCards);
            cardsInMiddle[3] = findCard("Chapel", allCards);
            cardsInMiddle[4] = findCard("Feast", allCards);
            cardsInMiddle[5] = findCard("Laboratory", allCards);
            cardsInMiddle[6] = findCard("Market", allCards);
            cardsInMiddle[7] = findCard("Mine", allCards);
            cardsInMiddle[8] = findCard("Moneylender", allCards);
            cardsInMiddle[9] = findCard("Throne Room", allCards);
        } else if (setup == 3) {
            cardsInMiddle[0] = findCard("Bureaucrat", allCards);
            cardsInMiddle[1] = findCard("Chancellor", allCards);
            cardsInMiddle[2] = findCard("Council Room", allCards);
            cardsInMiddle[3] = findCard("Festival", allCards);
            cardsInMiddle[4] = findCard("Library", allCards);
            cardsInMiddle[5] = findCard("Militia", allCards);
            cardsInMiddle[6] = findCard("Moat", allCards);
            cardsInMiddle[7] = findCard("Spy", allCards);
            cardsInMiddle[8] = findCard("Thief", allCards);
            cardsInMiddle[9] = findCard("Village", allCards);
        } else if (setup == 4) {
            cardsInMiddle[0] = findCard("Cellar", allCards);
            cardsInMiddle[1] = findCard("Chapel", allCards);
            cardsInMiddle[2] = findCard("Feast", allCards);
            cardsInMiddle[3] = findCard("Gardens", allCards);
            cardsInMiddle[4] = findCard("Laboratory", allCards);
            cardsInMiddle[5] = findCard("Thief", allCards);
            cardsInMiddle[6] = findCard("Village", allCards);
            cardsInMiddle[7] = findCard("Witch", allCards);
            cardsInMiddle[8] = findCard("Woodcutter", allCards);
            cardsInMiddle[9] = findCard("Workshop", allCards);
        } else if (setup == 5) {
            cardsInMiddle[0] = findCard("Bureaucrat", allCards);
            cardsInMiddle[1] = findCard("Cellar", allCards);
            cardsInMiddle[2] = findCard("Festival", allCards);
            cardsInMiddle[3] = findCard("Library", allCards);
            cardsInMiddle[4] = findCard("Market", allCards);
            cardsInMiddle[5] = findCard("Remodel", allCards);
            cardsInMiddle[6] = findCard("Smithy", allCards);
            cardsInMiddle[7] = findCard("Throne Room", allCards);
            cardsInMiddle[8] = findCard("Village", allCards);
            cardsInMiddle[9] = findCard("Woodcutter", allCards);
        } else {
            System.out.println();
            for (int i = 0; i < 10; ++i) {
                System.out.println("Type the name action card #" + (i + 1));
                String name = getValidName(scnr, allCards, cardsInMiddle);
                cardsInMiddle[i] = findCard(name, allCards);
            }
        }

        // Printing out the user's choice of action cards
        System.out.println();
        System.out.println("Available action cards:");
        for (int i = 0; i < 9; ++i) {
            System.out.print(cardsInMiddle[i].getName() + ", ");
        }
        System.out.println(cardsInMiddle[9].getName());

        // Fill cardsInMiddle with the core cards
        System.arraycopy(allCards, 0, cardsInMiddle, 10, 7);
        System.out.println();

        // Adding to the draw pile and shuffling
        System.out.println("Perfect!");
        System.out.println("Drawing 7 copper and 3 estate cards...");
        for (int i = 0; i < 7; ++i) {
            drawPile.add(findCard("Copper", cardsInMiddle));
        }
        for (int i = 0; i < 3; ++i) {
            drawPile.add(findCard("Estate", cardsInMiddle));
        }

        System.out.println("Shuffling...");
        Collections.shuffle(drawPile);
        System.out.println("Ready.\n");

        return cardsInMiddle;
    }

    public static String getValidName(Scanner scnr, Card[] allCards, Card[] cardsInMiddle) {
        String name = scnr.nextLine();
        boolean isValidName = false;
        while (!isValidName) {
            for (Card card : allCards) {
                if (card.getName().equals(name)) {
                    isValidName = true;
                    for (Card cardMid : cardsInMiddle) {
                        if (cardMid != null && cardMid.getName().equals(name)) {
                            System.out.println("You have already entered this card.");
                            isValidName = false;
                        }
                    }
                }
            }
            if (!isValidName) {
                System.out.println("Not a valid card. Must use uppercase first letters. Try again:");
                name = scnr.nextLine();
            }
        }

        return name;
    }

    public static void takeTurns(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> drawPile,
                                 ArrayList<Card> discardPile, int[] info) {
        ArrayList<Card> hand = new ArrayList<>();

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
            manageMenu(scnr, cardsInMiddle, hand, drawPile, discardPile, info);

            // Discard the used hand
            discardPile.addAll(hand);
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

    public static void manageMenu(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> hand,
                                  ArrayList<Card> drawPile, ArrayList<Card> discardPile, int[] info) {
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
                chooseAction(scnr, cardsInMiddle, drawPile, discardPile, hand, info);
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

    public static void chooseAction(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> drawPile,
                                       ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
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
                    boolean addToDiscard = playAction(scnr, cardPlayed, cardsInMiddle, drawPile, discardPile, hand,
                            info);
                    if (addToDiscard) {
                        discardPile.add(cardPlayed);
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

    public static boolean playAction(Scanner scnr, Card cardPlayed, Card[] cardsInMiddle, ArrayList<Card> drawPile,
                                     ArrayList<Card> discardPile, ArrayList<Card> hand, int[] info) {
        boolean addToDiscard = true;

        switch (cardPlayed.getName()) {
            case "Adventurer":
                adventurer(drawPile, discardPile, hand, info);
                break;
            case "Bureaucrat":
                bureaucrat(scnr, cardsInMiddle, hand, drawPile, false);
                break;
            case "Cellar":
                cellar(scnr, drawPile, discardPile, hand, info);
                break;
            case "Chancellor":
                chancellor(drawPile, discardPile);
                break;
            case "Chapel":
                chapel(scnr, hand, info);
                break;
            case "Council Room":
                councilRoom(scnr, drawPile, discardPile, hand, info, false);
                break;
            case "Feast":
                feast(scnr, cardsInMiddle, discardPile, hand);
                addToDiscard = false;   // This card gets trashed so don't discard it after use
                break;
            case "Festival":
                festival(info);
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
            case "Remodel":
                remodel(scnr, discardPile, hand, cardsInMiddle, info);
                break;
            case "Smithy":
                smithy(drawPile, discardPile, hand, info);
                break;
            case "Spy":
                spy(scnr, drawPile, discardPile, hand, info, false);
                break;
            case "Thief":
                thief(scnr, drawPile, discardPile, cardsInMiddle, false);
                break;
            case "Throne Room":
                throneRoom(scnr, drawPile, discardPile, hand, cardsInMiddle, info);
                break;
            case "Village":
                village(drawPile, discardPile, hand, info);
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

    public static boolean buyCard(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> discardPile, ArrayList<Card> hand,
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

            System.out.println();
            System.out.println("Choose an option to buy:");
            String optionStr = scnr.nextLine();
            int choice = getValidDigit(scnr, optionStr, (option-1));
            Card cardBought = options.get(choice - 1);

            System.out.println();
            System.out.println("You bought the " + cardBought.getName() + " card.");
            boolean boughtProvince = false;
            if (cardBought.getName().equals("Province")) {
                boughtProvince(cardsInMiddle);
                boughtProvince = true;
            }
            // Decrease the buys and money left for this turn
            info[BUYS_POS] -= 1;
            info[MONEY_POS] -= cardBought.getCost();

            discardPile.add(cardBought);
            if (!boughtProvince) {
                cardBought.decreaseNumRemaining();
            }
            System.out.println();
            printInfo(hand, info);
            System.out.println();
            return false;
        }

    }

    public static void playedAttackCard(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> hand,
                                        ArrayList<Card> drawPile, ArrayList<Card> discardPile, int[] info) {

        System.out.println("\nWhich of these cards did your opponent play?");
        System.out.println("1. Bureaucrat\n2. Militia\n3. Spy\n4. Thief\n5. Witch\n6. Council Room");
        System.out.println("\nChoose an option:");

        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, 6);
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
                councilRoom(scnr, drawPile, discardPile, hand, info, true);
                break;
        }
    }

    public static void boughtProvince(Card[] cardsInMiddle) {

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
            Collections.shuffle(discardPile);
            drawPile.addAll(discardPile);
            discardPile.clear();
        }

        Card card = drawPile.get(0);
        hand.add(card);
        if (card.getType().equals("Treasure")) {
            info[MONEY_POS] += card.getValue();
        }

        drawPile.remove(0);
        return card;
    }

    public static void discardCard(ArrayList<Card> hand, ArrayList<Card> discardPile, Card cardToDiscard, int[] info) {
        discardPile.add(cardToDiscard);
        hand.remove(cardToDiscard);
        if (cardToDiscard.getType().equals("Treasure")) {
            info[MONEY_POS] -= cardToDiscard.getValue();
        }
    }

    // Finds the position of a given card to allow access
    public static Card findCard(String name, Card[] cards) {
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

    public static void bureaucrat(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> hand, ArrayList<Card> drawPile,
                                  boolean opponent) {
        if (!opponent) {
            System.out.println("Adding a silver card to your deck...");
            drawPile.add(0, findCard("Silver", cardsInMiddle));
            findCard("Silver", cardsInMiddle).decreaseNumRemaining();
            System.out.println("Does your opponent have a reaction card? (1) yes, (2) no:");
            String inputStr = scnr.nextLine();
            int input = getValidDigit(scnr, inputStr, 2);
            System.out.println();
            if (input == 2) {
                System.out.println("Your opponent must now choose option 2 of their menu to be affected by your attack.");
                System.out.println("Press enter when they have done so:");
                scnr.nextLine();
            }
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
        ArrayList<Card> options = new ArrayList<>();

        // +1 Action
        System.out.println("+1 action:");
        info[ACTIONS_POS] += 1;
        System.out.println();

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

    public static void chancellor(ArrayList<Card> drawPile, ArrayList<Card> discardPile) {
        System.out.println("Moving the draw pile to the discard pile...");
        while (drawPile.size() > 0) {
            discardPile.add(drawPile.get(0));
            drawPile.remove(0);
        }
        System.out.println("Done.");
    }

    public static void chapel(Scanner scnr, ArrayList<Card> hand, int[] info) {
        ArrayList<Card> options = new ArrayList<>();

        int option = 1;
        for (Card card : hand) {
            System.out.println(option + ". " + card);
            options.add(card);
            ++option;
        }

        System.out.println();

        System.out.println("Choose the number(s) of the card(s) you would like to trash, each separated by a space:");
        String choices = scnr.nextLine();
        Scanner scanChoices = new Scanner(choices);

        while (scanChoices.hasNextInt()) {
            option = scanChoices.nextInt();
            System.out.println();
            Card chosenCard = options.get(option - 1);
            System.out.println("Trashing the " + chosenCard.getName() + " card...");
            if (chosenCard.getType().equals("Treasure")) {
                info[MONEY_POS] -= chosenCard.getValue();
            }
            hand.remove(chosenCard);
        }
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

    public static void feast(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> discardPile, ArrayList<Card> hand) {
        System.out.println("Trashing this card...");
        hand.remove(findCard("Feast", cardsInMiddle));
        System.out.println();
        System.out.println("You may now gain a card costing up to $5.");

        System.out.println("Your options:");
        ArrayList<Card> options = new ArrayList<>();
        int option = 1;
        for (Card card : cardsInMiddle) {
            if (card.getCost() <= 5) {
                System.out.println(option + ". " + card.cardInHand() + "\n");
                options.add(card);
                ++option;
            }
        }

        System.out.println();
        System.out.println("Choose a card to gain:");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, (option-1));
        Card cardGained = findCard(options.get(choice - 1).getName(), cardsInMiddle);
        System.out.println("You got the " + cardGained.getName() + ".");
        discardPile.add(cardGained);
        cardGained.decreaseNumRemaining();
    }

    public static void festival(int[] info) {
        System.out.println("Adding +2 actions, +1 buy, +$2:");
        info[ACTIONS_POS] += 2;
        info[BUYS_POS] += 1;
        info[MONEY_POS] += 2;
    }

    public static void laboratory(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                                  int[] info) {
        System.out.println("Adding +1 action:");
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
        System.out.println("You drew the " + card.getName() + " card.");
        System.out.println();
        System.out.println("Adding +1 Action, +1 Buy, +$1...");
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
            System.out.println("Done.");
            System.out.println("\nYour opponent must now choose option 2 on their menu to be affected by your attack.");
            System.out.println("Press enter when they have done so:");
            scnr.nextLine();
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

    public static void mine(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> hand,
                            int[] info) {
        System.out.println("Choose a treasure card from your hand to trash.\n");
        System.out.println("Your options:");

        ArrayList<Card> optionsTrash = new ArrayList<>();
        int option = 1;
        for (Card card : hand) {
            if (card.getType().equals("Treasure") && !card.getType().equals("Gold")) {
                System.out.println(option + ". " + card.cardInHand());
                optionsTrash.add(card);
                ++option;
            }
        }

        System.out.println();
        System.out.println("Choose an option:");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, (option-1));
        Card cardTrashed = optionsTrash.get(choice - 1);
        System.out.println("Trashing the " + cardTrashed.getName() + " card...");
        info[MONEY_POS] -= cardTrashed.getValue();
        hand.remove(cardTrashed);
        System.out.println("Done.\n");

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
        System.out.println("Trashing a copper from your hand...");
        boolean trashedCopper = false;
        for (Card card : hand) {
            if (card.getName().equals("Copper")) {
                hand.remove(card);
                trashedCopper = true;
                break;
            }
        }

        if (trashedCopper) {
            System.out.println("Adding +$3...");
            info[MONEY_POS] += 2;
        } else {
            System.out.println("Sorry, you did not have a copper to trash. No extra money was added.");
        }

    }

    public static void remodel(Scanner scnr, ArrayList<Card> discardPile, ArrayList<Card> hand, Card[] cardsInMiddle,
                               int[] info) {
        System.out.println("Choose a card from your hand to trash.\n");
        System.out.println("Your options:");

        ArrayList<Card> optionsTrash = new ArrayList<>();
        int option = 1;
        for (Card card : hand) {
            System.out.println(option + ". " + card.cardInHand());
            optionsTrash.add(card);
            ++option;
        }

        System.out.println();
        System.out.println("Choose an option:");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, (option-1));
        Card cardTrashed = optionsTrash.get(choice - 1);
        System.out.println("Trashing the " + cardTrashed.getName() + " card...");
        if (cardTrashed.getType().equals("Treasure")) {
            info[MONEY_POS] -= cardTrashed.getValue();
        }
        hand.remove(cardTrashed);
        System.out.println("Done.\n");

        // Gain a card costing up to $2 more
        System.out.println("Now choose a card to gain.");
        ArrayList<Card> optionsGain = new ArrayList<>();
        option = 1;
        for (Card card : cardsInMiddle) {
            if (card.getCost() <= (2 + cardTrashed.getCost())) {
                System.out.println(option + ". " + card.cardInHand());
                optionsGain.add(card);
                ++option;
            }
        }

        System.out.println();
        System.out.println("Choose an option:");
        optionStr = scnr.nextLine();
        choice = getValidDigit(scnr, optionStr, (option-1));
        Card cardToGain = optionsGain.get(choice - 1);
        System.out.println("Gaining the " + cardToGain.getName() + " card...");
        discardPile.add(cardToGain);
        cardToGain.decreaseNumRemaining();
        System.out.println("Done.\n");
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
            System.out.println("Your opponent must now choose option 2 on their menu to be affected by your attack.");
            System.out.println("Press enter when they have done so:");
            scnr.nextLine();
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

    public static void thief(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                             Card[] cardsInMiddle, boolean opponent) {
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
                            discardPile.add(findCard("Copper", cardsInMiddle));
                            findCard("Copper", cardsInMiddle).decreaseNumRemaining();
                            System.out.println("Gained a Copper.");
                            break;
                        case 2:
                            discardPile.add(findCard("Silver", cardsInMiddle));
                            findCard("Silver", cardsInMiddle).decreaseNumRemaining();
                            System.out.println("Gained a Silver.");
                            break;
                        case 3:
                            discardPile.add(findCard("Gold", cardsInMiddle));
                            findCard("Gold", cardsInMiddle).decreaseNumRemaining();
                            System.out.println("Gained a Gold.");
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

    public static void throneRoom(Scanner scnr, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
                                  ArrayList<Card> hand, Card[] cardsInMiddle, int[] info) {
        int option = 1;
        ArrayList<Card> options = new ArrayList<>();
        System.out.println("Pick an action to play twice.");

        System.out.println("Your options:");
        for (Card card : hand) {
            if (card.getType().contains("Action") && !card.getName().equals("Throne Room")) {
                System.out.println(option + ". " + card.getName());
                options.add(card);
                ++option;
            }
        }

        System.out.println("\nEnter your choice:");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, (option-1));
        Card action = options.get(choice - 1);

        System.out.println("\nPlaying the " + action.getName() + " card the first time...");
        playAction(scnr, action, cardsInMiddle, drawPile, discardPile, hand, info);
        System.out.println("\nPlaying the " + action.getName() + " card the second time...");
        playAction(scnr, action, cardsInMiddle, drawPile, discardPile, hand, info);
        System.out.println();
    }

    public static void village(ArrayList<Card> drawPile, ArrayList<Card> discardPile, ArrayList<Card> hand,
                               int[] info) {
        System.out.println("Drawing a card...");
        Card card = drawCard(hand, drawPile, discardPile, info);
        System.out.println("You drew the " + card.getName() + " card.");

        System.out.println("\nAdding +2 Action...");
        info[ACTIONS_POS] += 2;
        System.out.println("Done.");
    }

    public static void witch(Scanner scnr, Card[] cardsInMiddle, ArrayList<Card> drawPile, ArrayList<Card> discardPile,
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
            System.out.println("Gaining a curse card...");
            discardPile.add(findCard("Curse", cardsInMiddle));
            findCard("Curse", cardsInMiddle).decreaseNumRemaining();
            System.out.println("Done.\n");
        }
    }

    public static void woodcutter(int[] info) {
        System.out.println("Adding +1 Buy and +$2:");
        info[BUYS_POS] += 1;
        info[MONEY_POS] += 2;
        System.out.println("Done.");
    }

    public static void workshop(Scanner scnr, ArrayList<Card> discardPile, Card[] cardsInMiddle) {
        System.out.println("You may gain a card costing up to $4.");

        System.out.println("Your options:");
        ArrayList<Card> options = new ArrayList<>();
        int option = 1;
        for (Card card : cardsInMiddle) {
            if (card.getCost() <= 4) {
                System.out.println(option + ". " + card.cardInHand() + "\n");
                options.add(card);
                ++option;
            }
        }

        System.out.println();
        System.out.println("Choose a card to gain:");
        String optionStr = scnr.nextLine();
        int choice = getValidDigit(scnr, optionStr, (option-1));
        Card cardGained = findCard(options.get(choice - 1).getName(), cardsInMiddle);
        System.out.println("You got the " + cardGained.getName() + ".");
        discardPile.add(cardGained);
        cardGained.decreaseNumRemaining();
    }

}

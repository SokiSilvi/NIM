import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

class Main {


    private static final String[] UNITS = {
            "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN",
            "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"
    };
    private static final String[] TENS = {
            "", "", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"
    };


    public static String numberToWords(int number) {
        if (number < 20) {
            return UNITS[number];
        }
        // 100 alatt: Tízesek + kötőjel + egyesek (pl. 21 -> TWENTY-ONE)
        if (number < 100) {
            return TENS[number / 10] + ((number % 10 != 0) ? "-" + UNITS[number % 10] : "");
        }
        if (number < 1000) {
            return UNITS[number / 100] + " HUNDRED" + ((number % 100 != 0) ? " AND " + numberToWords(number % 100) : "");
        }
        if (number < 1000000) {
            return numberToWords(number / 1000) + " THOUSAND" + ((number % 1000 != 0) ? " " + numberToWords(number % 1000) : "");
        }
        if (number < 1000000000) {
            return numberToWords(number / 1000000) + " MILLION" + ((number % 1000000 != 0) ? " " + numberToWords(number % 1000000) : "");
        }
        return numberToWords(number / 1000000000) + " BILLION" + ((number % 1000000000 != 0) ? " " + numberToWords(number % 1000000000) : "");
    }

    public static String makeOrdinal(String cardinal) {
        if (cardinal.equals("ZERO")) return "ZEROTH";

        // A substring levágja az eredeti szót (pl. ONE az 3 betű, így length() - 3)
        if (cardinal.endsWith("ONE")) return cardinal.substring(0, cardinal.length() - 3) + "FIRST";
        if (cardinal.endsWith("TWO")) return cardinal.substring(0, cardinal.length() - 3) + "SECOND";
        if (cardinal.endsWith("THREE")) return cardinal.substring(0, cardinal.length() - 5) + "THIRD";
        if (cardinal.endsWith("FIVE")) return cardinal.substring(0, cardinal.length() - 4) + "FIFTH";
        if (cardinal.endsWith("EIGHT")) return cardinal.substring(0, cardinal.length() - 5) + "EIGHTH";
        if (cardinal.endsWith("NINE")) return cardinal.substring(0, cardinal.length() - 4) + "NINTH";
        if (cardinal.endsWith("TWELVE")) return cardinal.substring(0, cardinal.length() - 6) + "TWELFTH";

        // Szabály: ami "Y"-ra végződik (pl. TWENTY), abból "IETH" lesz (TWENTIETH)
        if (cardinal.endsWith("Y")) return cardinal.substring(0, cardinal.length() - 1) + "IETH";

        // Minden más esetben "TH" a végére (pl. FOUR -> FOURTH, HUNDRED -> HUNDREDTH)
        return cardinal + "TH";
    }

    public static String getOrdinal(int number) {
        String cardinalWords = numberToWords(number); // Szám -> sima szöveg
        return makeOrdinal(cardinalWords);            // Sima szöveg -> Sorszámnév
    }



    public static void clearboard() {
        System.out.println("\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n ");
    }

    public static int[] init() throws IOException {
        int packets = 0;
        while (packets <= 1) {
            clearboard();
            welcome("inits.txt", false);
            System.out.println("                                                  _______");
            System.out.print("How many packs of stones would you like to have? | ");

            try {
                Scanner scanner = new Scanner(System.in);
                String packetstxt = scanner.nextLine();
                packets = Integer.parseInt(packetstxt);
            }
            catch(NumberFormatException e) {}

            System.out.println("                                                 |_______|");
        }

        int[] STONES = new int[packets];

        for (int i = 1; i <= packets; i++) {
            int stones = 0;
            while (stones <= 0) {
                clearboard();
                welcome("inits.txt", false);
                for (int j = 0; j < (56 + getOrdinal(i).length()); j++) System.out.print(" ");
                System.out.println("_______");
                System.out.print("How many stones would you like to have in the " + getOrdinal(i) + " packet? | ");

                try {
                    Scanner scanner = new Scanner(System.in);
                    String stonestxt = scanner.nextLine();
                    stones = Integer.parseInt(stonestxt);
                }
                catch(NumberFormatException e) {}

                System.out.println("                                                 |_______|");
            }
            STONES[i-1] = stones;
        }
        return STONES;
    }

    public static int menu() throws IOException {
        int choice = 0;
        boolean chosen = false;
        while (!chosen) {
            clearboard();
            System.out.println("            N: New game \n            I: Info \n            C: Credits \n \n \n \n \n \n");
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br2 = new BufferedReader(isr);
            String s = br2.readLine();

            if  (s == null) { break; }
            if (s.equals("N") || s.equals("n")) { chosen = true; return 0; }
            if (s.equals("I") || s.equals("i")) { chosen = true; return 1; }
            if (s.equals("C") || s.equals("c")) { chosen = true; return 2; }
        }
        return choice;
    }

    public static void welcome(String filename, boolean enter) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        while (true) {
            String line = br.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }

        if (enter) {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br2 = new BufferedReader(isr);
            String s = br2.readLine();
        }
    }

    public static int nimcount(int... stones) {
        int count = stones[0];
        for (int i = 1; i < stones.length; i++) {
            count ^= stones[i];
        }
        return count;
    }

    public static Selected ai(int... stones) throws IOException {
        int nim = nimcount(stones);
        int[] mibol = new int[stones.length];                // packs that can be reduced
        int[] mennyit = new int[stones.length];              // amount of rocks that should be removed
        int mennyibol = 0;
        for(int i = 0; i < stones.length; i++) {
            if ((stones[i] ^ nim) < stones[i]) {
                mibol[mennyibol] = i;
                mennyit[i] = stones[i] - (stones[i] ^ nim);
                mennyibol ++;
            }
        }
        Random rnd = new Random();
        int chosen_pack = rnd.nextInt(mennyibol);
        Selected selected = new Selected(mibol[chosen_pack], mennyit[mibol[chosen_pack]]);
        selected.setOffset((stones[selected.getFrom_which_packet()] - selected.getCount_of_stones()));
        drawboard("computer_choice.txt", selected, stones);
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        br.readLine();
        return selected;
    }

    public static Selected idiot(int... stones) throws IOException {
        int stones_remained = 0;
        int chosen_pack = 0;
        Random rnd = new Random();
        while (stones_remained == 0) {
            chosen_pack = rnd.nextInt(stones.length);
            stones_remained = stones[chosen_pack];
        }
        Selected selected = new Selected(chosen_pack, (rnd.nextInt(stones[chosen_pack]) + 1));
        selected.setOffset((stones[selected.getFrom_which_packet()] - selected.getCount_of_stones()));
        drawboard("computer_choice.txt", selected, stones);
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        br.readLine();
        return selected;
    }

    public static void updateSelection(Selected selected, int chosen_pack, int amount, int offset, int... stones) {
        selected.setFrom_which_packet(chosen_pack);
        selected.setCount_of_stones(amount);
        selected.setOffset(offset);
        try {
            drawboard("yourMove.txt", selected, stones);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Selected humanSelect(int... stones) {
        Controller ctrl = new Controller();
        final int[] chosen_pack = {0};
        for (int i = 0; i < stones.length; i++) {
            if (stones[i] > 0) {
                chosen_pack[0] = i;
                break;
            }
        }
        final int[] offset = {0};
        final int[] amount = {stones[chosen_pack[0]]};
        Selected selected = new Selected(chosen_pack[0], amount[0]);

        java.awt.event.ActionListener goUp = e -> {
            offset[0] = 0;
            boolean empty = true;
            while (empty) {
                if (chosen_pack[0] == 0) { chosen_pack[0] = stones.length - 1; }
                else { chosen_pack[0] -= 1; }
                if (stones[chosen_pack[0]] > 0) empty = false;
            }
            amount[0] = stones[chosen_pack[0]];

            updateSelection(selected, chosen_pack[0], amount[0], offset[0], stones);
        };
        java.awt.event.ActionListener goDown = e -> {
            offset[0] = 0;
            boolean empty = true;
            while(empty) {
                if (chosen_pack[0] == stones.length - 1) { chosen_pack[0] = 0; }
                else { chosen_pack[0] += 1; }
                if (stones[chosen_pack[0]] > 0) empty = false;
            }
            amount[0] = stones[chosen_pack[0]];

            updateSelection(selected, chosen_pack[0], amount[0], offset[0], stones);
        };

        // <+ : A bal szélét tolja balra (terjeszkedik balra)
        java.awt.event.ActionListener goLeftPlus = e -> {
            if (offset[0] > 0) {
                offset[0] -= 1;
                amount[0] += 1;
            }
            selected.setCount_of_stones(amount[0]);
            selected.setOffset(offset[0]);
            try { drawboard("yourMove.txt", selected, stones); } catch (IOException ex) {}
        };

        // <- : A jobb szélét húzza balra (csökken jobbról)
        java.awt.event.ActionListener goLeftMinus = e -> {
            if (amount[0] > 1) {
                amount[0] -= 1;
            }
            selected.setCount_of_stones(amount[0]);
            selected.setOffset(offset[0]);
            try { drawboard("yourMove.txt", selected, stones); } catch (IOException ex) {}
        };

        // >+ : A jobb szélét tolja jobbra (terjeszkedik jobbra)
        java.awt.event.ActionListener goRightPlus = e -> {
            if ((offset[0] + amount[0]) < stones[chosen_pack[0]]) {
                amount[0] += 1;
            }
            selected.setCount_of_stones(amount[0]);
            selected.setOffset(offset[0]);
            try { drawboard("yourMove.txt", selected, stones); } catch (IOException ex) {}
        };

        // >- : A bal szélét húzza jobbra (csökken balról)
        java.awt.event.ActionListener goRightMinus = e -> {
            if (amount[0] > 1) {
                offset[0] += 1;
                amount[0] -= 1;
            }
            selected.setCount_of_stones(amount[0]);
            selected.setOffset(offset[0]);
            try { drawboard("yourMove.txt", selected, stones); } catch (IOException ex) {}
        };

        ctrl.getBtnLeftPlus().addActionListener(goLeftPlus);
        ctrl.getBtnLeftMinus().addActionListener(goLeftMinus);
        ctrl.getBtnRightMinus().addActionListener(goRightMinus);
        ctrl.getBtnRightPlus().addActionListener(goRightPlus);
        ctrl.getBtnup().addActionListener(goUp);
        ctrl.getBtndown().addActionListener(goDown);

        ctrl.getBtnok().addActionListener(e -> ctrl.getDialog().dispose());

        try {
            drawboard("yourMove.txt", selected, stones);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        ctrl.getDialog().setVisible(true);
        return selected;
    }

    public static void animation() throws IOException {
        String[] frames = {
                "thinking0.txt",
                "thinking1.txt",
                "thinking2.txt",
                "thinking3.txt"
        };

        for (int i = 0; i < 2; i++) {
            for (String frame : frames) {
                clearboard();
                welcome(frame, false);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static int[] robotstep(int... stones) throws IOException {
        drawboard("cmoves.txt", new Selected(0, 0), stones);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        animation();
        if (nimcount(stones) == 0) return step(idiot(stones), stones);
        else return step(ai(stones), stones);
    }

    public static void guess() throws IOException {
        final boolean[] humanwins = {false};
        Controller ctrl = new Controller();

        ctrl.getBtnLeftPlus().setEnabled(false);
        ctrl.getBtnLeftMinus().setEnabled(false);
        ctrl.getBtnRightPlus().setEnabled(false);
        ctrl.getBtnRightMinus().setEnabled(false);

        java.awt.event.ActionListener toggleAction = e -> {
            humanwins[0] = !humanwins[0];
            clearboard();
            try {
                welcome(humanwins[0] ? "human_wins.txt" : "computer_wins.txt", false);
            } catch (Exception ex) {}
        };

        ctrl.getBtnup().addActionListener(toggleAction);
        ctrl.getBtndown().addActionListener(toggleAction);

        ctrl.getBtnok().addActionListener(e -> ctrl.getDialog().dispose());

        clearboard();
        try {
            welcome(humanwins[0] ? "human_wins.txt" : "computer_wins.txt", false);
        } catch (Exception ex) {}

        ctrl.getDialog().setVisible(true);
    }


    public static void drawboard(String message, Selected selection, int... stones) throws IOException {
        clearboard();
        welcome(message, false);
        for (int row = 0; row < stones.length; row++) {
            System.out.println();
            if (selection.getFrom_which_packet() == row && selection.getCount_of_stones() > 0) {
                //_____________The_top_of_the_highlighted_stones_______________________
                for (int i = 0; i < (selection.getOffset() * 2 + 2); i++) {
                    System.out.print(" ");
                }
                for (int i = 0; i < (selection.getCount_of_stones() * 2 + 1); i++) {
                    System.out.print("_");
                }
                System.out.println();
                //______________Highlighting_the_stones_________________________________
                for (int i = 0; i < selection.getOffset(); i++) {
                    System.out.print(" O");
                }
                System.out.print(" |");
                for (int i = 0; i < selection.getCount_of_stones(); i++) {
                    System.out.print(" O");
                }
                System.out.print(" |");
                for (int i = 0; i < (stones[row] - (selection.getOffset() + selection.getCount_of_stones())); i++) {
                    System.out.print(" O");
                }
                System.out.println();
                //______________The_bottom_of_the_highlighted_stones______________________
                for (int i = 0; i < selection.getOffset(); i++) {
                    System.out.print("  ");
                }
                System.out.print(" |");
                for (int i = 0; i < selection.getCount_of_stones(); i++) {
                    System.out.print("__");
                }
                System.out.print("_|");
            }
            else {
                for (int packet = 0; packet < stones[row]; packet++) {
                    System.out.print(" O");
                }
            }
            System.out.println();
        }
        System.out.println("\n \n Press Enter to continue...");
    }

    public static int[] step(Selected selected, int... stones) {
        stones[selected.getFrom_which_packet()] -= selected.getCount_of_stones();
        return stones;
    }

    public static boolean play(int... stones) throws IOException {
        int stepCounter = 0;
        while (true) {
            boolean end = true;
            for (int i = 0; i < stones.length; i++) {
                if (stones[i] > 0) {
                    end = false;
                    break;
                }
            }
            if (end) {
                clearboard();
                System.out.println(" From " + stepCounter + " steps...");
                return (stepCounter % 2 == 0);
            }

            if (stepCounter % 2 == 0) {
                stones = robotstep(stones);
            }
            else { stones = step(humanSelect(stones), stones); }

            stepCounter ++;
        }
    }


    public static void game() throws IOException {
        int[] stones = init();
        int nimcount = nimcount(stones);
        boolean win = nimcount != 0;
        guess();
        drawboard("begin.txt", new Selected(0, 0), stones);
        InputStreamReader ir = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(ir);
        br.readLine();
        welcome(play(stones) ? "winnerM.txt" : "winnerC.txt", true);
    }

    public static void main(String[] args) throws IOException {

        welcome("welcome.txt", true);
        clearboard();
        while (true) {
            int choice = menu();
            if (choice == 1) welcome("rulez.txt", true);
            if (choice == 2) welcome("credits.txt", true);
            if (choice == 0) game();
        }
    }
}

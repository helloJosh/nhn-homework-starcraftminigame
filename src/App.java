import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import unit.*;
import unit.protos.*;
import unit.terren.*;
import unit.zerg.*;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        List<Unit> playerUnits = new ArrayList<>();
        List<Unit> computerUnits = new ArrayList<>();

        List<Unit> protosUnits = new ArrayList<>();
        List<Unit> terrenUnits = new ArrayList<>();
        List<Unit> zergUnits = new ArrayList<>();
        init(protosUnits, terrenUnits, zergUnits);

        System.out.print("종족을 선택 해주세요(1:Terren, 2:Protos, 3:Zerg):");
        int playerRace = scanner.nextInt();

        generatePlayerUnits(playerRace, protosUnits, terrenUnits, zergUnits, playerUnits);
        generateComputerUnits(playerRace, protosUnits, terrenUnits, zergUnits, computerUnits);

        displayUnits("아군", playerUnits);
        displayUnits("적군", computerUnits);

        while (true) {
            playerAttack(playerUnits, computerUnits, scanner);
            computerAttack(playerUnits, computerUnits);

            checkGameEnd(playerUnits, computerUnits);

            displayUnits("아군", playerUnits);
            displayUnits("적군", computerUnits);
            // break;
        }
    }

    private static void init(List<Unit> protosUnits, List<Unit> terrenUnits, List<Unit> zergUnits) {
        for (int i = 0; i < 3; i++) {
            protosUnits.add(new Corsair());
            protosUnits.add(new Dragoon());
            protosUnits.add(new HighTempler());
            protosUnits.add(new Scout());
            protosUnits.add(new Zealot());
            protosUnits.add(new Carrier());
        }

        for (int i = 0; i < 3; i++) {
            terrenUnits.add(new Goliath());
            terrenUnits.add(new Marine());
            terrenUnits.add(new Tank());
            terrenUnits.add(new Valkyrie());
            terrenUnits.add(new Wraith());
            terrenUnits.add(new BattleCruzer());
        }

        for (int i = 0; i < 3; i++) {
            zergUnits.add(new Guardian());
            zergUnits.add(new Hydralisk());
            zergUnits.add(new Mutalisk());
            zergUnits.add(new Ultralisk());
            zergUnits.add(new Zergling());
            zergUnits.add(new Queen());
        }
    }

    private static void generatePlayerUnits(int playerRace, List<Unit> protosUnits, List<Unit> terrenUnits,
            List<Unit> zergUnits, List<Unit> playerUnits) {
        Random random = new Random();
        int numberOfPlayerUnits = 0;
        List<Unit> selectedUnit = null;
        Set<Integer> selectedNumber = new HashSet<>();

        switch (playerRace) {
            case 1:
                numberOfPlayerUnits = 5;
                selectedUnit = terrenUnits;
                break;
            case 2:
                numberOfPlayerUnits = 4;
                selectedUnit = protosUnits;
                break;
            case 3:
                numberOfPlayerUnits = 8;
                selectedUnit = zergUnits;
                break;
            default:
                System.out.println("올바르지 않은 선택입니다.");
                System.exit(0);
        }

        for (int i = 0; i < numberOfPlayerUnits; i++) {
            int randomIndex = random.nextInt(selectedUnit.size());
            while (true) {
                if (selectedNumber.add(randomIndex)) {
                    playerUnits.add(selectedUnit.get(randomIndex));
                    break;
                } else {
                    randomIndex = random.nextInt(selectedUnit.size());
                }
            }
        }
    }

    private static void generateComputerUnits(int playerRace, List<Unit> protosUnits, List<Unit> terrenUnits,
            List<Unit> zergUnits, List<Unit> computerUnits) {
        Random random = new Random();
        int numberOfComputerUnits = 0;
        List<Unit> selectedUnit = null;
        Set<Integer> selectedNumber = new HashSet<>();

        switch (playerRace) {
            case 1:
                if (random.nextInt(2) == 0) {
                    numberOfComputerUnits = 4;
                    selectedUnit = protosUnits;
                } else {
                    numberOfComputerUnits = 8;
                    selectedUnit = zergUnits;
                }
                break;
            case 2:
                if (random.nextInt(2) == 0) {
                    numberOfComputerUnits = 5;
                    selectedUnit = terrenUnits;
                } else {
                    numberOfComputerUnits = 8;
                    selectedUnit = zergUnits;
                }
                break;
            case 3:
                if (random.nextInt(2) == 0) {
                    numberOfComputerUnits = 4;
                    selectedUnit = protosUnits;
                } else {
                    numberOfComputerUnits = 5;
                    selectedUnit = terrenUnits;
                }
                break;
            default:
                System.out.println("올바르지 않은 선택입니다.");
                System.exit(0);
        }

        for (int i = 0; i < numberOfComputerUnits; i++) {
            int randomIndex = random.nextInt(selectedUnit.size());
            while (true) {
                if (selectedNumber.add(randomIndex)) {
                    computerUnits.add(selectedUnit.get(randomIndex));
                    break;
                } else {
                    randomIndex = random.nextInt(selectedUnit.size());
                }
            }
        }
    }

    private static void displayUnits(String side, List<Unit> units) {
        System.out.println(side + " : " + units.get(0).getRace());

        for (int i = 0; i < units.size(); i++) {
            System.out.println(i + ". " + units.get(i) + units.get(i).hashCode());
        }
        System.out.println();
    }

    private static void playerAttack(List<Unit> playerUnits, List<Unit> computerUnits, Scanner scanner) {
        System.out.print("공격을 수행할 아군 유닛과 공격할 적군 유닛을 목록의 번호로 선택하세요 :");
        int playerIndex = scanner.nextInt();
        int computerIndex = scanner.nextInt();

        if (0 <= playerIndex && playerIndex <= playerUnits.size() && 0 <= computerIndex
                && computerIndex <= computerUnits.size()) {
            Unit attacker = playerUnits.get(playerIndex);
            Unit defender = computerUnits.get(computerIndex);

            if (attacker.isReachable(defender)) {
                attacker.attackUnit(defender);
                if (defender.isDead()) {
                    computerUnits.remove(computerIndex);
                    defender = null;
                }
            } else {
                System.out.println("이 유닛은 공중 유닛을 공격할 수 없습니다. 다시 입력해주세요");
                playerAttack(playerUnits, computerUnits, scanner);
            }
        }
    }

    private static void computerAttack(List<Unit> playerUnits, List<Unit> computerUnits) {
        Random random = new Random();
        System.out.println("컴퓨터가 공격을 진행합니다");
        int playerIndex = random.nextInt(playerUnits.size());
        int computerIndex = random.nextInt(computerUnits.size());

        if (0 <= playerIndex && playerIndex <= playerUnits.size() && 0 <= computerIndex
                && computerIndex <= computerUnits.size()) {
            Unit attacker = computerUnits.get(computerIndex);
            Unit defender = playerUnits.get(playerIndex);

            if (attacker.isReachable(defender)) {
                attacker.attackUnit(defender);
                if (defender.isDead()) {
                    computerUnits.remove(computerIndex);
                    defender = null;
                }
            } else {
                System.out.println("이 유닛은 공중 유닛을 공격할 수 없습니다. 다시 입력해주세요");
                computerAttack(playerUnits, computerUnits);
            }
        }
    }

    private static void checkGameEnd(List<Unit> playerUnits, List<Unit> computerUnits) {
        if (playerUnits.isEmpty()) {
            System.out.println("패-배-");
            System.exit(0);
        } else if (computerUnits.isEmpty()) {
            System.out.println("승-리-");
            System.exit(0);
        }
    }
}

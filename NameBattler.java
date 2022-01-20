import java.util.Random;
import java.util.Scanner;

public class NameBattler {
    private static final int FIRST_MENBER_NUM = 0;
    private static final int SECOND_MEBER_NUM = 1;
    private static final int FIRST_TURN_NUM = 1;
    private static final int MAX_PLAYER_NUM = 2;
    private static final int PERFECT_GUARD_DAMAGE_NUM = 0;
    private static final int KILLED_NUM = 0;
    private static final Scanner STDIN = new Scanner(System.in);
    private static final Random RAND = new Random();
    private static final String SHOW_FORMAT_MSG_REQUEST_INPUT_NAME = "プレイヤー%dの名前を入力してください：";
    private static final String SHOW_FORMAT_MSG_PLAYER_STATUS = "プレイヤー%d : %s (HP:%d  ATK:%d  DEF:%d  LCK:%d) %n";
    private static final String SHOW_MSG_BUTTLE_START = "==========バトル開始=========";
    private static final String SHOW_MSG_FORMAT_TURN_NUM = "%n＜＜%dターン目＞＞%n";
    private static final String SHOW_MSG_FORMAT_ATTACK = "%sの攻撃！！%n";
    private static final String SHOW_MSG_FORMAT_DAMAGE = "%sに%dダメージ！%n%n";
    private static final String SHOW_MSG_ATTACK_MISS = "攻撃ミス！！";
    private static final String SHOW_MSG_CRITICAL_HIT = "会心の一撃！！";
    private static final String SHOW_MSG_NEXT_TURN = "-次のターン-";
    private static final String SHOW_MSG_FORMAT_GAME_WIN = "%n %sは力尽きた・・・%n %sの勝利！！%n";

    public static void main(String[] args) {
        String [] nameList = new String[MAX_PLAYER_NUM];
        generateAddName(nameList);
        Character player1 = new Character();
        Character player2 = new Character();

        generateMembersStatus(nameList, player1, player2);
        showMembersStatus(player1, player2);

        newLine();
        showMsg(SHOW_MSG_BUTTLE_START);

        int turnCount = FIRST_TURN_NUM;

        playBattle(player1, player2, turnCount);

        gameResult(player1, player2);
        
    }

    private static void playBattle(Character player1, Character player2, int turnCount) {
        while(!isKilled(player1, player2)){
            showTurnNum(turnCount);

            attack(player1, player2);
            if(isKilled(player1, player2)) break;

            attack(player2, player1);
            if(isKilled(player1, player2)) break;
    
            showMsg(SHOW_MSG_NEXT_TURN);
            showMembersStatus(player1, player2);
            turnCount++;
        }
    }



    private static boolean isKilled(Character player1, Character player2) {
        return player1.hp <= KILLED_NUM || player2.hp <= KILLED_NUM;
    }

    private static void gameResult(Character player1, Character player2){
        if(player1.hp <= player2.hp){
            System.out.printf(SHOW_MSG_FORMAT_GAME_WIN,player1.name,player2.name);
            return;
        }
        System.out.printf(SHOW_MSG_FORMAT_GAME_WIN,player2.name,player1.name);
    }

    private static void attack(Character attack, Character defense) {
        showAttackMsg(attack.name);
        int damage = calcDamage(attack, defense);
        showDamageMsg(defense.name, damage, attack);
    }

    private static int calcDamage(Character attack, Character defense){
        int power = RAND.nextInt(attack.getStrength());
        int def = defense.getDefense();
        int damage = power - def;
        int luck = randomGetNum(attack.getLuck());
        if(perfectGuard(def, damage)){
            damage = PERFECT_GUARD_DAMAGE_NUM;
        }
        if(isCritical(luck)){
            damage = attack.getStrength();
        }
        resultHp(defense, damage);
        return damage;
    }

    private static boolean perfectGuard(int def, int damage) {
        return def >= damage;
    }

    private static int randomGetNum(int num){
        return RAND.nextInt(num);
    }

    private static boolean isCritical(int num){
        return num > 4;
    }

    private static int resultHp(Character defense, int damage){
        return defense.hp -= damage;
    }

    private static void showDamageMsg(String name, int damage, Character attack){
        if(damage == PERFECT_GUARD_DAMAGE_NUM){
            showMsg(SHOW_MSG_ATTACK_MISS);
            return;
        }

        if(damage == attack.getStrength()){
            showMsg(SHOW_MSG_CRITICAL_HIT);
        }
        System.out.printf(SHOW_MSG_FORMAT_DAMAGE,name,damage);
    }

    private static void showTurnNum(int turnCount){
        System.out.printf(SHOW_MSG_FORMAT_TURN_NUM,turnCount);
    }

    private static void showAttackMsg(String name){
        System.out.printf(SHOW_MSG_FORMAT_ATTACK, name);
    }

    private static void showMsg(String msg){
        System.out.println(msg);
    }


    private static void showMembersStatus(Character player1, Character player2) {
        showStatus(player1, FIRST_MENBER_NUM);
        showStatus(player2, SECOND_MEBER_NUM);
    }

    private static void generateMembersStatus(String[] nameList, Character player1, Character player2) {
        generateStatus(player1, nameList, FIRST_MENBER_NUM);
        generateStatus(player2, nameList, SECOND_MEBER_NUM);
    }

    private static void showStatus(Character player,int index){
        System.out.printf(SHOW_FORMAT_MSG_PLAYER_STATUS,index+1,player.getName(),player.getHp(),player.getStrength(),player.getDefense(),player.getLuck());
    }

    private static void generateStatus(Character player, String [] name, int index){
        player.setName(name[index]);
        player.setHp(name[index]);
        player.setStrength(name[index]);
        player.setDefense(name[index]);
        player.setLuck(name[index]);
    }

    private static void generateAddName(String[] nameList) {
        for(int i = 0; i < nameList.length; i++){
            System.out.printf(SHOW_FORMAT_MSG_REQUEST_INPUT_NAME,i+1);
            nameList[i] = inputName();
        }
    }

    private static String inputName(){
        return STDIN.nextLine();
    }

    private static void newLine(){
        System.out.println();
    }

}

import java.math.BigInteger;
import java.security.MessageDigest;

public class Character { 
    private static final int DIVIDING_HP_NUM = 5;
    private static final int DIVIDING_STRENGTH_NUM = 7;
    private static final int DIVIDING_DEFENSE_NUM = 15;
    private static final int DIVIDING_LUCK_NUM = 20;
    private static final int DECIDE_STATUS_NUM = 1;
    String name;
    int hp;
    int strength;
    int defense;
    int luck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp (String name){
        int hp = generateNumber(name,DECIDE_STATUS_NUM);
        this.hp = hp / DIVIDING_HP_NUM;
    }

    public int getStrength(){
        return strength;
    }

    public void setStrength(String name){
        int strength = generateNumber(name,DECIDE_STATUS_NUM);
        this.strength = strength / DIVIDING_STRENGTH_NUM;
    }

    public int getDefense(){
        return defense;
    }

    public void setDefense(String name){
        int defense = generateNumber(name, DECIDE_STATUS_NUM);
        this.defense = defense / DIVIDING_DEFENSE_NUM;
    }

    public int getLuck(){
        return luck;
    }

    public void setLuck(String name){
        int luck = generateNumber(name, DECIDE_STATUS_NUM);
        this.luck = luck / DIVIDING_LUCK_NUM;
    }

    public static int generateNumber(String name, int index) {
        try {
            String digest = getHashDigest(name);
            String hex = digest.substring(
                index * 2, index * 2 + 2
            );
            return Integer.parseInt(hex, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getHashDigest(String name) {
        try {
            // ハッシュ値を取得する
            byte[] result =
                MessageDigest.getInstance("SHA-1")
                .digest(name.getBytes());
            return String.format(
                "%040x",
                new BigInteger(1, result)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
   
}

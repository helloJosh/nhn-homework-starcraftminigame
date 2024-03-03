package unit;

public class Unit {
    private int attackPower;
    private int hp;
    private String unitName;
    private String race;

    public Unit(int attackPower, int hp, String unitName, String race) {
        this.attackPower = attackPower;
        this.hp = hp;
        this.unitName = unitName;
        this.race = race;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void attackUnit(Unit unit) {
        System.out
                .println(unit.getUnitName() + "이 " + this.getUnitName() + "을 공격하여 방어력이 " + this.getHp() + "만큼 감소했습니다.");
        unit.setHp(unit.getHp() - this.getAttackPower());
    }

    public boolean isReachable(Unit unit) {
        if (this instanceof AirDefensable)
            return true;
        if (this instanceof Walkable) {
            if (unit instanceof Walkable) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean isDead() {
        if (this.getHp() <= 0)
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return this.getUnitName() + "(현재 방어력: " + this.getHp() + ")";
    }

}

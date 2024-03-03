package unit.zerg;

import unit.AirDefensable;
import unit.Unit;
import unit.Walkable;

public class Hydralisk extends Unit implements Walkable, AirDefensable {
    public Hydralisk() {
        super(5, 15, "Hydralisk", "Zerg");
    }

    @Override
    public void attackUnit(Unit unit) {
        System.out.println("Spit shredder than a needle");
        System.out
                .println(unit.getUnitName() + "이 " + this.getUnitName() + "을 공격하여 방어력이 " + this.getHp() + "만큼 감소했습니다.");
        unit.setHp(unit.getHp() - this.getAttackPower());
    }
}

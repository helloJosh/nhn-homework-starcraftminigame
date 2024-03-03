package unit.protos;

import unit.AirDefensable;
import unit.Unit;
import unit.Walkable;

public class Dragoon extends Unit implements Walkable, AirDefensable {
    public Dragoon() {
        super(3, 15, "Dragoon", "Protos");
    }

    @Override
    public void attackUnit(Unit unit) {
        System.out.println("Laser");
        System.out
                .println(unit.getUnitName() + "이 " + this.getUnitName() + "을 공격하여 방어력이 " + this.getHp() + "만큼 감소했습니다.");
        unit.setHp(unit.getHp() - this.getAttackPower());
    }
}

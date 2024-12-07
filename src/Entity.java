public class Entity {
	private String name;
	private int health;
	private int attack;
	private int energy;
	private int defence;
	private String sign;
	Entity(String name, int health, int attack, int energy, int defence) {
		this.name = name;
		this.health = health;
		this.attack = attack;
		this.energy = energy;
		this.defence = defence;
	}
	protected void setName(String name) {
		this.name = name;
	}
	protected String getName() {
		return this.name;
	}
	protected void setHealth(int health) {
		if (health >= 1000) {
			health = 1000;
		} else if (health <= 0) {
			health = 0;
		}
		this.health = health;
	}
	protected int getHealth() {
		return this.health;
	}
	protected void setAttack(int attack) {
		this.attack = attack;
	}
	protected int getAttack() {
		return this.attack;
	}
	protected void setDefence(int defence) {
		this.defence = defence;
	}
	protected int getDefence() {
		return this.defence;
	}
	protected int getEnergy() {
		return energy;
	}
	protected void setEnergy(int energy) {
		if (energy >= 200) {
			energy = 200;
		}
		this.energy = energy;
	}
	protected String getSign() {
		return sign;
	}
	protected void setSign(String sign) {
		this.sign = sign;
	}
}
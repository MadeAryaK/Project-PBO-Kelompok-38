public class Bot extends Entity implements Behaviour{
	
	Bot(String name, int health, int attack, int energy, int defence) {
		super(name, health, attack, energy, defence);
	}

	@Override
	public boolean dead() {
		if(super.getHealth()<= 0) {
			return true;
		}
		return false;
	}

	@Override
	public void skill1() {
		setHealth(getHealth() + (int)(getHealth()*0.5));
	}

	@Override
	public int skill2() {
		return (int)(getAttack()*1.5);
	}

}

package GameObjects;

import java.io.File;

public class Detonator extends Powerup {

	public Detonator(int x, int y, int framesPerSecond, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, framesPerSecond, map, bombMap);
		this.sprite = new File[]{new File("Sprites/Detonator.png")};
	}

	@Override
	public void doSomething(Player player) {
		player.getDetonator();
	}

	@Override
	public void collideWithDoor(Door door) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithPowerup(Powerup powerup) {
		// TODO Auto-generated method stub.

	}

}

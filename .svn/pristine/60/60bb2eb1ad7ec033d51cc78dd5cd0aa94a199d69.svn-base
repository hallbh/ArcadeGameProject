package GameObjects;

import java.io.File;

public class IncreaseSpeed extends Powerup {

	public IncreaseSpeed(int x, int y, int framesPerSecond, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, framesPerSecond, map, bombMap);
		this.sprite = new File[]{new File("Sprites/Speed.png")};
	}

	@Override
	public void doSomething(Player player) {
		player.increaseSpeed();
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

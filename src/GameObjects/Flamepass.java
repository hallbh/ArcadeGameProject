package GameObjects;

import java.io.File;

public class Flamepass extends Powerup {

	public Flamepass(int x, int y, int framesPerSecond, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, framesPerSecond, map, bombMap);
		this.sprite = new File[]{new File("Sprites/flamepass.png")};
	}

	@Override
	public void doSomething(Player player) {
		player.becomeFlameResist();
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

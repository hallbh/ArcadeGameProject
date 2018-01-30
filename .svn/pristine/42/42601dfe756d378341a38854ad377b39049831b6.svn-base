package GameObjects;

public abstract class Powerup extends GameObject {
	private boolean consumed;

	public Powerup(int x, int y, int framesPerSecond, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, framesPerSecond, map, bombMap);
		this.consumed = false;
	}
	//Method for each powerup to change the player's characteristics
	public abstract void doSomething(Player player);

	@Override
	public void run() {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collide(GameObject obj) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithBrick(Brick brick) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithWall(Wall wall) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithPlayer(Player player) {
		if(!this.consumed){
			this.doSomething(player);
			this.consumed = true;
		}		
	}

	@Override
	public void collideWithBomb(Bomb bomb) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithExplosion(Explosion explosion) {
		// TODO Auto-generated method stub.

	}

	@Override
	public void collideWithEnemy(Enemy enemy) {
		// TODO Auto-generated method stub.

	}
	public void collideWithFireball(Fireball fireball){
		//TODO write code
	}

}

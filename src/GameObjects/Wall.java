package GameObjects;


import java.awt.geom.Point2D;
import java.io.File;

public class Wall extends Block {

	public Wall(int x, int y, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, 1, map, bombMap);
		this.sprite = new File[]{new File("Sprites/squareWall.png")};
	}

	@Override
	public void collide(GameObject obj) {
		obj.collideWithWall(this);
		
	}

	@Override
	public void collideWithBrick(Brick brick) {
		// literally never going to happen, Brick, so stop texting me
		
	}

	@Override
	public void collideWithWall(Wall wall) {
		// walls can't move
		
	}

	@Override
	public void collideWithBomb(Bomb bomb) {
		//bombs don't collide with walls silly
	}

	@Override
	public void run() {
		//nothing to do
	}

	@Override
	public void collideWithExplosion(Explosion explosion) {
		// nothing to do
	}

	@Override
	public void collideWithEnemy(Enemy enemy) {
		int direction = (enemy).getDirection();
		Point2D grid = enemy.convertToGrid();
		int tempDirection = (int)(Math.random() * 4);
		while(tempDirection == direction){				
			tempDirection = (int)(Math.random() * 4);
		}
		(enemy).setDirection(tempDirection);
		enemy.setY((int)(grid.getY() * 32));
		enemy.setX((int)(grid.getX() * 32));
	}

}

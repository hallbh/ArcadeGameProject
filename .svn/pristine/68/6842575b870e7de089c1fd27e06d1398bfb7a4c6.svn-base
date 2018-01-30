package GameObjects;


import java.awt.geom.Point2D;
import java.io.File;

public class Brick extends Block {
	
	private boolean isDoor;
	private int isPowerup;
	
	public Brick(boolean isDoor, int isPowerup, int x, int y, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, 30, map, bombMap);
		this.isDoor = isDoor;
		this.isPowerup = isPowerup;
		this.sprite = new File[]{new File("Sprites/brickWall.png")};
	}
	
	@Override
	public void collide(GameObject obj) {
		obj.collideWithBrick(this);		
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
	public void collideWithBomb(Bomb bomb) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void collideWithExplosion(Explosion explosion) {
		Point2D temp = this.convertToGrid();
		Powerup p = null;
		Door d = null;
		switch(this.isPowerup){
			case 0: this.map[(int) temp.getY()][(int) temp.getX()] = explosion;
					new Thread(explosion).start();
					break;
			case 1: p = new MoreBombs((int)this.x, (int)this.y, 1, this.map, this.bombMap);
					this.map[(int) temp.getY()][(int) temp.getX()] = p;
					new Thread(p).start();
					break;
			case 2: p = new IncreaseSpeed((int)this.x, (int)this.y, 1, this.map, this.bombMap);
					this.map[(int) temp.getY()][(int) temp.getX()] = p;
					new Thread(p).start();
					break;
			case 3: p = new Detonator((int)this.x, (int)this.y, 1, this.map, this.bombMap);
					this.map[(int) temp.getY()][(int) temp.getX()] = p;
					new Thread(p).start();
					break;
			case 4: p = new IncreaseBombSize((int)this.x, (int)this.y, 1, this.map, this.bombMap);
					this.map[(int) temp.getY()][(int) temp.getX()] = p;
					new Thread(p).start();
					break;
			case 5: p = new Flamepass((int)this.x, (int)this.y, 1, this.map, this.bombMap);
					this.map[(int) temp.getY()][(int) temp.getX()] = p;
					new Thread(p).start();
					break;
			default:
					this.map[(int) temp.getY()][(int) temp.getX()] = explosion;
					new Thread(explosion).start();
					break;
		}
		if(this.isDoor){
			d = new Door((int)this.x, (int)this.y, 1, this.map, this.bombMap);
			this.map[(int) temp.getY()][(int) temp.getX()] = d;
			new Thread(d).start();
		}
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

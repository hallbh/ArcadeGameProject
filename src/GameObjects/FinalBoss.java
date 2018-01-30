package GameObjects;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class FinalBoss extends Enemy {
	
	private int health;

	public FinalBoss(int x, int y, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, map, bombMap);
		this.sprite = new File[]{new File("Sprites/finalboss.png"), new File("Sprites/finalboss2.png")};
		this.hitbox = new Rectangle2D.Double(x, y, 188, 250);
		this.area = new Area(this.hitbox);
		this.center = new Point2D.Double(x + 94, y + 125);
		this.health = 3;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub.
		int timer = 0;
		try{
			while(!this.blownUp && this.map != null && !this.isPaused){
				Thread.sleep(this.frameTime);
				if(timer > 20){
					this.shootFire();
					timer = 0;
				}
				timer++;
			}
		}
		catch(InterruptedException e){
			
		}
	}
	
	public void shootFire(){
		double xSpeed = -(Math.random() * 3) - 1;
		double ySpeed = (Math.random() * 3);
		if(Math.random() > .5){
			ySpeed = -ySpeed;
		}
		Fireball spit = new Fireball((int)this.x - 33, (int)this.y + 137, this.map, this.bombMap, xSpeed, ySpeed);
		new Thread(spit).start();
	}
	
	@Override
	public void collideWithExplosion(Explosion explosion){		
		Point2D grid = this.convertToGrid();
		if(this.health > 0){
			this.health --;
		}
		explosion.die();
		if(this.health <= 0 && this.map != null){
			this.blownUp = true;
			this.map[(int) grid.getY()][(int) grid.getX()] = null;
			this.die();
			this.map = null;			
		}
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
	public void collideWithBomb(Bomb bomb) {
		// TODO Auto-generated method stub.

	}
}

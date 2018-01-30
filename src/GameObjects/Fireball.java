package GameObjects;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class Fireball extends Enemy {
	
	private double xSpeed;
	private double ySpeed;
	private Point2D gridPos;

	public Fireball(int x, int y, GameObject[][] map, Bomb[][] bombMap, double xSpeed, double ySpeed) {
		super(x, y, map, bombMap);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.gridPos = this.convertToGrid();
		this.sprite = new File[]{new File("Sprites/fireball1.png"), new File("Sprites/fireball2.png"), new File("Sprites/fireball3.png")};
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub.
		try{
			while(this.map != null && !this.blownUp && !this.isPaused){
				GameObject closest = this.nearestObject();
				if(this.area.intersects(closest.getHitBox())){
					this.collide(closest);
				}
				Bomb closestBomb = this.nearestBomb();
				if(closestBomb != null){
					if(this.area.intersects(closestBomb.getHitBox())){
						this.collide(closestBomb);
					}
				}
				Thread.sleep(this.frameTime);
				this.x += this.xSpeed;
				this.y += this.ySpeed;
				this.update();
			}
		}
		catch(InterruptedException e){
			
		}
	}
	public void update(){
		this.center = new Point2D.Double(this.x + 16, this.y + 16);
		this.hitbox = new Rectangle2D.Double(this.x, this.y, 32, 32);
		this.area = new Area(this.hitbox);
		Point2D grid = this.convertToGrid();
		if(!grid.equals(this.gridPos) && !this.blownUp){
			try{
				this.map[(int) grid.getY()][(int) grid.getX()] = this;
				this.map[(int) this.gridPos.getY()][(int) this.gridPos.getX()] = null;
				this.gridPos = grid;
			} catch (NullPointerException e){
				this.map = null;
				this.bombMap = null;
			}
		}
	}

	@Override
	public void collide(GameObject obj) {
		// TODO Auto-generated method stub.
		obj.collideWithFireball(this);
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

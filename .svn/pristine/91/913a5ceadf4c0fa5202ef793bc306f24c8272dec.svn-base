package GameObjects;

import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;

public class Ballom extends Enemy {
	private double speed;
	private Point2D gridPos;
	public Ballom(int x, int y, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, map, bombMap);
		this.sprite = new File[]{new File("Sprites/Enemy1.png"),
									new File("Sprites/Enemy1-2.png"),
									new File("Sprites/Enemy1-3.png"),
									new File("Sprites/Enemy1-2.png")};
		this.speed = 1;
		this.gridPos = this.convertToGrid();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub.
		try{
			while(this.map != null && !this.blownUp){
				if(!this.isPaused){
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
					switch(this.direction){
						case 0:
							this.x += this.speed;
							break;
						case 1:
							this.x -= this.speed;
							break;
						case 2:
							this.y += this.speed;
							break;
						case 3: 
							this.y -= this.speed;
							break;
						default:
							break;
					}
					
				}
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
		obj.collideWithEnemy(this);
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

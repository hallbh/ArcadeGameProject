package GameObjects;


import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

public class Player extends GameObject {
	
	private boolean isMoving;
	private boolean canPlaceBomb;
	private int direction; //1 = left, 2 = right, 3 = up, 4 = down 
	//arraylist of powerups
	private double speed;
	private int bombCap;
	private ArrayList<Bomb> bombs;
	private boolean isColliding;
	private Point2D gridPos;
	private int bombSize;
	private boolean hasDetonator;
	private int lives;
	private boolean flameResist;
	
	public Player(int x, int y, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, 30, map, bombMap);
		this.hitbox = new Rectangle2D.Double(this.x + 2, this.y + 2, 22, 22);
		this.area = new Area(this.hitbox);
		this.direction = 2;
		this.speed = 3;
		this.bombCap = 1;
		this.bombSize = 1;
		this.hasDetonator = false;
		this.flameResist = false;
		this.bombs = new ArrayList<Bomb>();
		this.isColliding = false;
		this.gridPos = this.convertToGrid();
		this.sprite = new File[]{new File("Sprites/Sanic1.png"),
								new File("Sprites/Sanic2.png"),
								new File("Sprites/Sanic3.png"), 
								new File("Sprites/Sanic4.png")};
		this.lives = 3;
	}
	
	@Override
	public void run(){
		try{
			while(this.map != null && !Thread.interrupted()){
				if(!this.isPaused){
					GameObject closest = this.nearestObject();
					if(this.area.intersects(closest.getHitBox())){
						this.collide(closest);
					}
					for(int i = 0; i < this.bombs.size(); i++){
						if(this.bombs.get(i).hasExploded()){
							this.bombs.remove(i);
						}
					}
					if(this.bombs.size() > 0){//check if colliding with a bomb
						closest = this.nearestBomb();
						if(this.area.intersects(closest.getHitBox())){
							this.collide(closest);
						}
					}
					Thread.sleep(this.frameTime);
					if(this.isMoving && !this.isColliding){
						switch(this.direction){
							case 1:
								this.x -= this.speed;
							break;
							case 2:
								this.x += this.speed;
							break;
							case 3:
								this.y -= this.speed;
							break;
							case 4:
								this.y += this.speed;
							break;
							default:
							break;
						}
					}
				}
				this.update();
			}
		}
		catch(InterruptedException e){
			//nothing to do
		}
		finally{
			//nothing to do
		}
	}
	
	public void moveX(int dist){
		this.x += dist;
	}
	public void moveY(int dist){
		this.y += dist;
	}
	
	@Override
	public void update(){
		this.center = new Point2D.Double(this.x + 16, this.y + 16);
		this.hitbox = new Rectangle2D.Double(this.x + 2, this.y + 2, 22, 22);
		this.area = new Area(this.hitbox);
		Point2D grid = this.convertToGrid();
		if(!grid.equals(this.gridPos)){
			this.map[(int) grid.getY()][(int) grid.getX()] = this;
			this.map[(int) this.gridPos.getY()][(int) this.gridPos.getX()] = null;
			this.gridPos = grid;
		}
	}
	
	public Bomb placeBomb(){
		if(this.bombs.size() < this.bombCap){
			Point2D grid = this.convertToGrid();
			for(Bomb b : this.bombs){
				if(b.convertToGrid().equals(grid)){
					return null;
				}
			}
			Point2D bombPoint = new Point2D.Double((grid.getX() * 32), (grid.getY() * 32));

			Bomb bomb = new Bomb((int)bombPoint.getX(), (int)bombPoint.getY(), this, this.map, this.bombMap, this.hasDetonator, this.bombSize);

			this.bombs.add(bomb);
			return bomb;
		}	
		return null;
	}
	
	public void increaseBombCap(){
		this.bombCap ++;
	}
	
	public void increaseBombSize(){
		this.bombSize ++;
	}
	
	public double getSpeed(){
		return this.speed;
	}
	
	public void increaseSpeed(){
		this.speed ++;
	}
	
	public void detonateAllBombs(){
		while(this.bombs.size() > 0){
			this.bombs.get(0).explode();
		}
	}
	//gives player the detonator
	public void getDetonator(){
		this.hasDetonator = true;
	}
	
	public boolean checkForDetonator(){
		return this.hasDetonator;
	}
	
	public void becomeFlameResist(){
		this.flameResist = true;
	}
	public boolean checkFlameResist(){
		return this.flameResist;
	}
	
	public void loseLife(){
		if(this.lives > 1){
			this.lives--;
		}
		else {
			this.lives = 3;
		}
	}
	
	public int getLives(){
		return this.lives;
	}

	public void transferPowerups(Player newP){
		for(int i = 3; i < this.speed; i++){
			newP.increaseSpeed();
		}
		for(int i = 1; i < this.bombCap; i++){
			newP.increaseBombCap();
		}
		for(int i = 1; i < this.bombSize; i++){
			newP.increaseBombSize();
		}
		if(this.hasDetonator){
			newP.getDetonator();
		}
		if(this.flameResist){
			newP.becomeFlameResist();
		}
		for(int i = 3; i > this.lives; i--){
			newP.loseLife();
		}
	}
	
	@Override
	public void collide(GameObject obj) {
		obj.collideWithPlayer(this);
	}
	
	public void stopMoving() {
		this.isMoving = !this.isMoving;
	}
	
	public void startMoving(int dir) {
		this.isMoving = true;
		this.direction = dir;
	}
	public int getDirection(){
		return this.direction;
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}

	@Override
	public void collideWithBrick(Brick brick) {
		// brick deals with a player hitting it on its own
	}

	@Override
	public void collideWithWall(Wall wall) {
		// wall deals with a player hitting it on its own
	}

	@Override
	public void collideWithPlayer(Player player) {
		// player shouldn't collide with player until there are multiplayer
		// TODO Option for expansion in phase 4 
	}

	@Override
	public void collideWithBomb(Bomb bomb) {
		// bomb deals with a player hitting it on its own
		
	}

	@Override
	public void collideWithExplosion(Explosion explosion) {
		// explosion deals with a player hitting it on its own
		//TODO figure out how to make player die when bomb initially explodes
	}

	@Override
	public void collideWithEnemy(Enemy enemy) {
		//nothing, enemies deal with this		
	}

	@Override
	public void collideWithDoor(Door door) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void collideWithPowerup(Powerup powerup) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void collideWithFireball(Fireball fireball) {
		// TODO Auto-generated method stub.
		
	}

}

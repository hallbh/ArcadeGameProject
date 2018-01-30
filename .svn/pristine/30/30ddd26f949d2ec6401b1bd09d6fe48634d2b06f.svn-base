package GameObjects;

import java.io.File;

import javax.swing.JOptionPane;

public class Explosion extends GameObject {
	
	private int time = 0;
	private boolean gameOver = false;

	public Explosion(int x, int y, int framesPerSecond, int sprite, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, framesPerSecond, map, bombMap);
		switch(sprite){
			case 1:	this.sprite = new File[]{new File("Sprites/fire_center.png")};
			break;
			case 2: this.sprite = new File[]{new File("Sprites/fire_top.png")};
			break;
			case 3: this.sprite = new File[]{new File("Sprites/fire_bottom.png")};
			break;
			case 4: this.sprite = new File[]{new File("Sprites/fire_right.png")};
			break;
			case 5: this.sprite = new File[]{new File("Sprites/fire_left.png")};
			break;
			case 6: this.sprite = new File[]{new File("Sprites/fire_horiz.png")};
			break;
			case 7: this.sprite = new File[]{new File("Sprites/fire_vert.png")};
			break;
			default:
			break;
		}
	}

	@Override
	public void run() {
		try{
			while(this.map != null){
				if(!this.isPaused){
					this.checkKill();
					Thread.sleep(this.frameTime);
					this.time ++;
					if(this.time >= 60){
						this.die();
						break;
					}
				}
			}
		}
		catch(InterruptedException e){
			//nothing
		}
	}

	public void checkKill(){
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.map[i][j] != null && this.area.intersects(this.map[i][j].getHitBox())){
					this.collide(this.map[i][j]);
				}
				if(this.bombMap[i][j] != null && this.area.intersects(this.bombMap[i][j].getHitBox())){
					this.collide(this.bombMap[i][j]);
				}
			}
		}
	}
	
	public Bomb nearestBomb(){
		Bomb closest = null;
		double nearestDistance = Double.MAX_VALUE;
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.bombMap[i][j] != null){
					double distance = this.center.distance(this.bombMap[i][j].getCenterPoint());
					if(distance < nearestDistance){
						nearestDistance = distance;
						closest = this.bombMap[i][j];
					}
				}
			}
		}
		return closest;
	}

	@Override
	public void collide(GameObject obj) {
		obj.collideWithExplosion(this);
	}

	@Override
	public void collideWithBrick(Brick brick) {
		// nothing to do, brick deals with this
	}

	@Override
	public void collideWithWall(Wall wall) {
		// nothing to do, Wall deals with this
	}

	@Override
	public void collideWithPlayer(Player player) {
		if(!player.checkFlameResist()){
			player.map = null;
			if(player.getLives() > 1){
				JOptionPane.showMessageDialog(null, "You have " + (player.getLives() - 1) + " lives left!");
				this.map[0][0] = player;
			} else {
				int restart = JOptionPane.showConfirmDialog(null, "You Lose! Try again?");
				if(restart == JOptionPane.OK_OPTION){
					this.map[0][0] = player;
				} else {
					System.exit(0);
				}
			}
		}
	}
	
	public boolean getGameOver(){
		return this.gameOver;
	}

	@Override
	public void collideWithBomb(Bomb bomb) {
		// nothing to do, Bomb deals with this
	}
	
	@Override
	public void collideWithExplosion(Explosion explosion) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void collideWithEnemy(Enemy enemy) {
		// TODO Auto-generated method stub.
		
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

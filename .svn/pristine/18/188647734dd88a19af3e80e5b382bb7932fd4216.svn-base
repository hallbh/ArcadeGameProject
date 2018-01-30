package GameObjects;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.File;

public class Door extends Block {
	public Door(int x, int y, int framesPerSecond, GameObject[][] map, Bomb[][] bombMap) {
		super(x, y, framesPerSecond, map, bombMap);
		this.sprite = new File[]{new File("Sprites/door.png")};
		
	}
	

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
		if(!this.checkEnemies()){
			try {
				Robot r = new Robot();
				r.keyPress(KeyEvent.VK_U);
				r.keyRelease(KeyEvent.VK_U);
			} catch (AWTException exception) {
				exception.printStackTrace();
			}
			finally{
				int direction = player.getDirection();
				double speed = player.getSpeed();
				switch(direction){
					case 1: player.moveX((int) speed);
					break;
					case 2: player.moveX((int) -speed);
					break;
					case 3: player.moveY((int) speed);
					break;
					case 4: player.moveY((int) -speed);
					break;
					default:
					break;
				}
				player.stopMoving();
			}
		}
		else{
			int direction = player.getDirection();
			double speed = player.getSpeed();
			switch(direction){
				case 1: player.moveX((int) speed);
				break;
				case 2: player.moveX((int) -speed);
				break;
				case 3: player.moveY((int) speed);
				break;
				case 4: player.moveY((int) -speed);
				break;
				default:
				break;
			}
			player.stopMoving();
		}

	}
	public boolean checkEnemies(){
		//TODO finish this method
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 29; j++){
				if(this.map[i][j] instanceof Enemy){
					return true;
				}
			}
		}
		return false;
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

	@Override
	public void collideWithDoor(Door door) {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void collideWithPowerup(Powerup powerup) {
		// TODO Auto-generated method stub.
		
	}

}

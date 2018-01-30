
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import GameObjects.Player;
import GameObjects.SoundPlayer;

public class KeyboardListener implements KeyListener {

	private Player p;
	private LevelPanel level;
	private boolean isPaused;
	private boolean uPressed;//can the u key be pressed
	SoundPlayer normal, boss;

	public KeyboardListener(LevelPanel level, SoundPlayer normal, SoundPlayer boss) {
		this.p = level.getPlayer();
		this.level = level;
		this.isPaused = false;
		this.uPressed = true;
		this.normal = normal;
		this.boss = boss;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.setPlayer();
		int key = e.getKeyCode();
		char pressed = e.getKeyChar();
		if(!this.isPaused){
			switch (key) {
			case KeyEvent.VK_LEFT:
				this.p.startMoving(1);
				break;
			case KeyEvent.VK_RIGHT:
				this.p.startMoving(2);
				break;
			case KeyEvent.VK_UP:
				this.p.startMoving(3);
				break;
			case KeyEvent.VK_DOWN:
				this.p.startMoving(4);
				break;
			default:
				break;
			}
		}
		if(key == KeyEvent.VK_U){
			this.uPressed = false;
			if (this.level.getLevelNum() + 1 < new File("Levels").listFiles().length) {
				if(this.level.getLevelNum() == 4){
					this.normal.kill();
					this.boss.loop();
				}
				this.level.loadLevel(this.level.getLevelNum() + 1);
				this.p = this.level.getPlayer();
			}			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		char released = e.getKeyChar();
		if(!this.isPaused){
			switch (key) {
			case KeyEvent.VK_LEFT:
				this.p.stopMoving();
				break;
			case KeyEvent.VK_RIGHT:
				this.p.stopMoving();
				break;
			case KeyEvent.VK_UP:
				this.p.stopMoving();
				break;
			case KeyEvent.VK_DOWN:
				this.p.stopMoving();
				break;
			default:
				break;
			}
		}
		if(released == 'u'){
			this.uPressed = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();

		switch (key) {
		case 'd':// go down a level
			if (this.level.getLevelNum() > 1) {
				if(this.level.getLevelNum() == 5){
					this.boss.kill();
					this.normal.loop();
				}
				this.level.loadLevel(this.level.getLevelNum() - 1);
				this.p = this.level.getPlayer();
			}
			break;
			//u was acting funky so we put it in key pressed/released
		case 'e':
			if(!this.isPaused){
				this.level.addBomb();
			}
			break;
		case 'p' :
			this.isPaused = !this.isPaused;
			this.level.pause();
			break;
		case 'r':
			if(this.p.checkForDetonator()){
				this.p.detonateAllBombs();
			}
			break;
		default:
			break;
		}
	}
	
	public void setPlayer(){
		this.p = this.level.getPlayer();
	}

}

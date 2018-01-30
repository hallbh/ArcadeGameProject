import javax.swing.JFrame;

/**
 * 
 *
 */
public class Main {
	
	private static GameFrame game;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		game = new GameFrame();
		
		game.setSize(LevelPanel.WIDTH + 12, LevelPanel.HEIGHT + StatsPanel.HEIGHT + 12);
		game.setResizable(true);
		
		game.level.setBounds(0, 64, LevelPanel.WIDTH, LevelPanel.HEIGHT);
		game.stats.setBounds(0, 0, StatsPanel.WIDTH, StatsPanel.HEIGHT);
		
		game.add(game.level);
		game.add(game.stats);
		
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.setVisible(true);
	}

}

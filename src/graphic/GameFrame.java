package graphic;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
	
	private CardLayout cl = new CardLayout();
	//private JPanel panel = new JPanel(); //dummy panel
	
	public GameFrame() {
		setTitle("¿À¸ñ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(490, 510);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		
		setLayout(cl);
		add("menu", new MenuPanel(this));
		add("game", new GamePanel(this));
		//add("game", panel);
	}
	
	public void changePanel(String name) {
//		if(name.equals("game")) {
//			remove(panel);
//			panel = new GamePanel(this);
//			add("game", panel);
//		}
		cl.show(getContentPane(), name);
	}
}

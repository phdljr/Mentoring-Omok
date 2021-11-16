

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	public MenuPanel(GameFrame gf) {
		setLayout(null);
		setBackground(new Color(206,167,61));
		
		JLabel lb = new JLabel("오목");
		lb.setFont(new Font("Serif", Font.BOLD, 20));
		lb.setBounds(225, 30, 100, 50);
		add(lb);
		
		JButton btn = new JButton("게임 시작");
		btn.setBounds(195, 300, 100, 50);
		btn.addActionListener((e)->{
			gf.changePanel("game");
		});
		add(btn);
	}
}



import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	/* 
	 * �ٵ� �� ũ�� : 450*450
	 * �� ���� : 18��
	 * ���� ��ǥ : 10, 10
	 * �� ��ǥ : 460, 460
	 * �� ũ�� : 450/18=25
	 */
	public static final int WIDTH = 450;
	public static final int HEIGHT = 450;
	private static final int LINE = 18;
	private static final int SIZE = WIDTH/LINE;
	private static final int MIN_X = 10;
	private static final int MIN_Y = 10;
	private static final int MAX_X = MIN_X + WIDTH;
	private static final int MAX_Y = MIN_Y + HEIGHT;
	
	private int ghost_x = 0, ghost_y = 0;
	private boolean next_black = false; //������ ������ ��, true
	private int[][] map = new int[19][19];
	
	private Vector<Stone> white_stone = new Vector<Stone>(); //�� �� ����
	private Vector<Stone> black_stone = new Vector<Stone>(); //���� �� ����
	
	private GameFrame gf; //�г� ��ȯ�� ���� �θ� �����̳ʸ� ������
	
	public GamePanel(GameFrame gf) {
		this.gf = gf;
		
		setBackground(new Color(206,167,61));
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		addMouseMotionListener(ml);
	}
	
	//�ش� Panel�� �ٽ� �׷��� �޼ҵ�
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // ���� ���
		drawMapLine(g);
		drawStone(g);
		drawGhost(g);
	}

	//�ٵ��� �� �׸���
	private void drawMapLine(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.drawRect(MIN_X, MIN_Y, WIDTH, HEIGHT);
		
		for(int i=0;i<=LINE;i++){
			g.drawLine(MIN_X, MIN_Y + i*SIZE, MAX_X, MIN_Y + i*SIZE); //����
			g.drawLine(MIN_X + i*SIZE, MIN_Y, MIN_X + i*SIZE, MAX_Y); //����
		}
	}
	
	//���� �� �׸���
	private void drawGhost(Graphics g) {
		g.drawOval(ghost_x, ghost_y, SIZE, SIZE);
	}
	
	private void drawStone(Graphics g) {
		//�� �� �׸���
		g.setColor(Color.WHITE);
		for(Stone s: white_stone) {
			g.fillOval(s.getX(), s.getY(), SIZE, SIZE);
		}

		//���� �� �׸���
		g.setColor(Color.BLACK);
		for(Stone s: black_stone) {
			g.fillOval(s.getX(), s.getY(), SIZE, SIZE);
		}
	}
	
	private void checkEndGame() {
		int count_white = 0; // 5�� �Ǹ� ��
		int count_black = 0;
		
		//���� �������� 5�� ã��
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map.length;j++) {
				switch(map[i][j]) {
				case 1:
					count_black = 0;
					count_white++;
					break;
				case 2:
					count_black++;
					count_white = 0;
					break;
				default:
					count_black = 0;
					count_white = 0;
				}
				if(count_white == 5 || count_black == 5) {
					String endStr = (count_white == 5) ? "�� �¸�" : "������ �¸�";
					JOptionPane.showMessageDialog(null, endStr, "���� ��", JOptionPane.PLAIN_MESSAGE);
					gf.changePanel("menu");
					return;
				}
			}
		}
	}
	
	private class MyMouseListener extends MouseAdapter{
		
		public void mouseClicked(MouseEvent e) {
			if(e.isMetaDown()) { //��Ŭ����
				gf.changePanel("menu");
			}
			else {
				try {
					if(map[e.getY()/SIZE][e.getX()/SIZE] != 0) {
						throw new AlreadyExist();
					}
					
					int x = MIN_X + (int)(e.getX()/SIZE)*SIZE - SIZE/2;
					int y = MIN_Y + (int)(e.getY()/SIZE)*SIZE - SIZE/2;
					
					Stone current_stone = new Stone(x, y);
					
					if(next_black == false) {
						white_stone.add(current_stone);
					}
					else {
						black_stone.add(current_stone);
					}
					map[e.getY()/SIZE][e.getX()/SIZE] = (next_black == false) ? 1 : 2; //���̸� 1, �������̸� 2
							
					next_black = !next_black;
					
					repaint();
					checkEndGame();
				}
				catch(AlreadyExist e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "�̹� ���� �����մϴ�.", "����", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		public void mouseMoved(MouseEvent e) {
			int tempx = MIN_X + (int)(e.getX()/SIZE)*SIZE - SIZE/2;
			int tempy = MIN_Y + (int)(e.getY()/SIZE)*SIZE - SIZE/2;
			ghost_x = tempx;
			ghost_y = tempy;
			repaint();
		}
	}
}


package graphic;

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
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	/* 
	 * 바둑 판 크기 : 450*450
	 * 줄 개수 : 18개
	 * 시작 좌표 : 10, 10
	 * 끝 좌표 : 460, 460
	 * 돌 크기 : 450/18=25
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
	private boolean next_black = false; //검은돌 차례일 때, true
	private int[][] map = new int[19][19];
	private String endStr = null;
	
	private Vector<Stone> white_stone = new Vector<Stone>(); //흰 돌 벡터
	private Vector<Stone> black_stone = new Vector<Stone>(); //검은 돌 벡터
	
	public GamePanel(GameFrame gf) {
		setBackground(new Color(206,167,61));
		MyMouseListener ml = new MyMouseListener(gf);
		addMouseListener(ml);
		addMouseMotionListener(ml);
	}
	
	//해당 Panel을 다시 그려줄 메소드
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // 원래 모양
		drawMapLine(g);
		drawStone(g);
		drawGhost(g);
	}

	//바둑판 선 그리기
	private void drawMapLine(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.drawRect(MIN_X, MIN_Y, WIDTH, HEIGHT);
		
		for(int i=0;i<=LINE;i++){
			g.drawLine(MIN_X, MIN_Y + i*SIZE, MAX_X, MIN_Y + i*SIZE); //가로
			g.drawLine(MIN_X + i*SIZE, MIN_Y, MIN_X + i*SIZE, MAX_Y); //세로
		}
	}
	
	//유령 돌 그리기
	private void drawGhost(Graphics g) {
		g.drawOval(ghost_x, ghost_y, SIZE, SIZE);
	}
	
	private void drawStone(Graphics g) {
		//흰 돌 그리기
		g.setColor(Color.WHITE);
		for(Stone s: white_stone) {
			g.fillOval(s.getX(), s.getY(), SIZE, SIZE);
		}

		//검은 돌 그리기
		g.setColor(Color.BLACK);
		for(Stone s: black_stone) {
			g.fillOval(s.getX(), s.getY(), SIZE, SIZE);
		}
	}
	
	private void checkEndGame() {
		int count_white = 0; // 5가 되면 끝
		int count_black = 0;
		
		//가로 방향으로 5개 찾기
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
					endStr = (count_white == 5) ? "흰돌 승리" : "검은돌 승리";
					System.out.println(endStr);
					return;
				}
			}
		}
	}
	
	private class MyMouseListener extends MouseAdapter{
		
		private GameFrame gf;
		
		public MyMouseListener(GameFrame gf) {
			this.gf = gf;
		}
		
		public void mouseClicked(MouseEvent e) {
			if(e.isMetaDown()) { //우클릭시
				gf.changePanel("menu");
			}
			else {
				int x = (int)(e.getX()/SIZE)*SIZE - 3;
				int y = (int)(e.getY()/SIZE)*SIZE - 3;
				
				Stone current_stone = new Stone(x, y);
				
				if(next_black == false) {
					white_stone.add(current_stone);
				}
				else {
					black_stone.add(current_stone);
				}
				map[e.getY()/SIZE][e.getX()/SIZE] = (next_black == false) ? 1 : 2; //흰돌이면 1, 검은돌이면 2
						
				next_black = !next_black;
				
				repaint();
				checkEndGame();
			}
		}
		
		public void mouseMoved(MouseEvent e) {
			int tempx = (int)(e.getX()/SIZE)*SIZE - 3;
			int tempy = (int)(e.getY()/SIZE)*SIZE - 3;
			ghost_x = tempx;
			ghost_y = tempy;
			repaint();
		}
	}
}


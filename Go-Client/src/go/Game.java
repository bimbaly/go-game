package go;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Game {

	public static final int SCALE = 4;
	public static final int WIDTH = 140 * SCALE;	//width should be multiple of 140 for 9x9, 13x13 and 19x19 board sizes
	public static final int HEIGHT = WIDTH + 80;	
	public static final String NAME = "go";
	
	private int space;
	
	private JFrame frame;
	private Painter painter;
	Graphics2D g2d;
	
	private int size;
	private StoneColor playerColor;
	private StoneColor opponentColor;
	private StoneColor ghostColor;
	
	private int ghostX = -100;
	private int ghostY = -100;
	
	private GameGraphics gameGraphics;
	private Board gameBoard;
	
	public Game(int size, int colorIndex) {
		
		this.size = size;
		this.space = WIDTH/(size+1);
		
		if (colorIndex == 0) {
			playerColor = StoneColor.BLACK;
			ghostColor = StoneColor.BLACK_GHOST;
			opponentColor = StoneColor.WHITE;
		} else if (colorIndex == 1) {
			playerColor = StoneColor.WHITE;
			ghostColor = StoneColor.WHITE_GHOST;
			opponentColor = StoneColor.BLACK;
		}
		
		gameGraphics = new GameGraphics(size, space);
		gameBoard = new Board(size, playerColor, opponentColor);
		
		painter = new Painter();
		painter.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		frame = new JFrame(NAME);
		frame.setContentPane(painter);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(frame, 
			            "Are you sure you wish to leave this match?", "Exit?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
//			            System.exit(0);
			            frame.dispose();	//close only frame
			        }
			}
		});
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
		gameGraphics.drawBoard(g2d);
		
		for (Stone stone : gameBoard.getStones().values()) {
			gameGraphics.drawStone(g2d, stone);
		}
		
		if (gameBoard.isMoveLegal(ghostX, ghostY, playerColor)) 
			gameGraphics.drawGhost(g2d, ghostX, ghostY, ghostColor);

	}
	
	private class Painter extends JPanel implements MouseListener, MouseMotionListener {
		private static final long serialVersionUID = 1L;

		private int lastX;
		private int lastY;
		
		public Painter() {
			setFocusable(true);
			requestFocus();
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			render(g);
			Toolkit.getDefaultToolkit().sync();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (e.getX() > space/2 && e.getY() > space/2 && e.getX() < space*size+space/2  && e.getY() < space*size+space/2) {
				ghostX = (e.getX() - space/2) / space;
				ghostY = (e.getY() - space/2) / space;
				if (ghostX < size || ghostY < size) {
					if (!(ghostX == lastX && ghostY == lastY)) {
						lastX = ghostX;
						lastY = ghostY;
						repaint();
					}
				}
			} else {
				if (ghostX != -100 && ghostY != -100) {
					lastX = -1;
					lastY = -1;
					ghostX = -100;
					ghostY = -100;
					repaint();
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			if (!(e.getX() > space/2 && e.getY() > space/2 && e.getX() < space*size+space/2  && e.getY() < space*size+space/2)) {
				return;
			}
			int x = (e.getX() - space/2) / space;
			int y = (e.getY() - space/2) / space;
			
			if (x < size && y < size) {
				if (gameBoard.isMoveLegal(x, y, playerColor)) {
					gameBoard.addStone(x+1+y*size, playerColor);
				}
			}
			
			repaint();
			Toolkit.getDefaultToolkit().sync();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseDragged(MouseEvent e) {
		}

	}

}



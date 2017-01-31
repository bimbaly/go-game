package go;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class GameGraphics {

	public static final Color BLACK_BG = Color.black;
	public static final Color BLACK_STROKE = Color.white;
	public static final Color BLACK_GHOST = new Color(0, 0, 0, 200);
	
	public static final Color WHITE_BG = new Color(230, 230, 230);
	public static final Color WHITE_STROKE = Color.white;
	public static final Color WHITE_GHOST = new Color(235, 235, 235, 200);
	
	public static final Color BOARD_BG = new Color(251, 197, 53);
	public static final Color BOARD_FG = Color.black;
	
	public static final Color LAST_MOVE_HIGHLIGHT = Color.red;
	
	private int space, size;
	
	public GameGraphics(int size, int space) {
		
		this.space = space;
		this.size = size;
		
	}
	
	public void drawBoard(Graphics2D g2d) {
		
		//draw background
		g2d.setColor(BOARD_BG);
		g2d.fillRect(0, 0, Game.WIDTH, Game.WIDTH);
		g2d.setColor(BOARD_FG);
		
		//draw grid
		for (int i = 1; i < size+1; i++) {
			g2d.drawLine(space*i, space, space*i, Game.WIDTH-space);	//vertical
			g2d.drawLine(space, space*i, Game.WIDTH-space, space*i);	//horizontal
		}
		
		//draw dots
		g2d.fillOval(Game.WIDTH/2-2, Game.WIDTH/2-2, 5, 5);
		
	}
	
	public void drawStone(Graphics2D g2d, Stone stone) {
		
		int index = stone.getIndex();
		
		int y = (index - 1) / size;
		int x = index - 1 - y * size;
		
		int xx = x*space+space/2;
		int yy = y*space+space/2;
		
		if (stone.getColor() == StoneColor.BLACK)
			g2d.setColor(BLACK_BG);
		else if (stone.getColor() == StoneColor.WHITE)
			g2d.setColor(WHITE_BG);
		
		g2d.fillOval(xx, yy, space, space);
		
		if (stone.getColor() == StoneColor.BLACK || stone.getColor() == StoneColor.WHITE) {
			g2d.setColor(Color.white);
			g2d.setStroke(new BasicStroke(space/16));
			g2d.drawArc(xx+space/6/2, yy+space/6/2, space-space/6, space-space/6, 30, 30);
		}
		
		//last move highlight
		if (stone.getIndex() == Stone.getLastMoveIndex()) {
			g2d.setColor(LAST_MOVE_HIGHLIGHT);
			g2d.drawOval(xx, yy, space, space);
//			g2d.fillOval(xx+space/4*2, yy+space/4*2, space/4, space/4);
		}
			
	}
	
	public void drawGhost(Graphics2D g2d, int x, int y, StoneColor color) {
		if (color == StoneColor.BLACK_GHOST)
			g2d.setColor(BLACK_GHOST);
		if (color == StoneColor.WHITE_GHOST)
			g2d.setColor(WHITE_GHOST);
		
		int xx = x*space+space/2;
		int yy = y*space+space/2;
		
		g2d.fillOval(xx, yy, space, space);
	}
	
}

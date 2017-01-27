package go;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class GameGraphics {

	public static final Color BLACK_BG = Color.black;
	public static final Color BLACK_STROKE = Color.white;
	public static final Color BLACK_GHOST = new Color(40, 40, 40, 200);
	
	public static final Color WHITE_BG = new Color(235, 235, 235);
	public static final Color WHITE_STROKE = Color.white;
	public static final Color WHITE_GHOST = new Color(220, 220, 220, 200);
	
	public static final Color BOARD_BG = new Color(251, 197, 53);
	public static final Color BOARD_FG = Color.black;
	
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
		else if (stone.getColor() == StoneColor.BLACK_GHOST)
			g2d.setColor(BLACK_GHOST);
		else if (stone.getColor() == StoneColor.WHITE_GHOST)
			g2d.setColor(WHITE_GHOST);
		
		g2d.fillOval(xx, yy, space, space);
		
		if (stone.getColor() == StoneColor.BLACK || stone.getColor() == StoneColor.WHITE) {
			g2d.setColor(Color.white);
			g2d.setStroke(new BasicStroke(space/16));
			g2d.drawArc(xx+space/6/2, yy+space/6/2, space-space/6, space-space/6, 30, 30);
		}
			
		
		
//		if (color == StoneColor.BLACK) {
//		g2d.setColor(Color.black);
//		g2d.fillOval(x, y, space, space);
////		g2d.setColor(Color.white);
////		g2d.setStroke(new BasicStroke(SPACE/28f));
////		g2d.drawArc(x+SPACE/6/2, y+SPACE/6/2, SPACE-SPACE/6, SPACE-SPACE/6, 0, 90);
//	}
//	if (color == StoneColor.WHITE) {
//		g2d.setColor(new Color(245, 245, 245));
//		g2d.fillOval(x, y, space, space);
////		g2d.setColor(Color.white);
////		g2d.setStroke(new BasicStroke(SPACE/28f));
////		g2d.drawArc(x+SPACE/6/2, y+SPACE/6/2, SPACE-SPACE/6, SPACE-SPACE/6, 0, 90);
//	}
//	if (color == StoneColor.GHOST) {
//		if (counter%2 == 0) {
//			g2d.setColor(new Color(40, 40, 40, 200));
//		} else {
//			g2d.setColor(new Color(220, 220, 220, 200));
//		}
//		
//		g2d.fillOval(x, y, space, space);
//	}
		
	}
	
}

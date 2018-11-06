package pokerGame

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;



public interface PokerConstants{
	
	Color TABLE_COLOR = new Color(0,128,0);
	Color TEXT_COLOR = Color.GREEN;
	Border LABEL_BORDER = new LineBorder(Color.BLACK, 1);
	Border PANEL_BORDER = new CompoundBorder(
	new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10));
}
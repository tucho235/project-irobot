import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;



public class HyperLinkLabel extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4482457683463438966L;
	String url;
	Color rc;
	 
	public HyperLinkLabel(String text, Icon icon, String url){
	super(text);
	this.setIcon(icon);
	this.url=url;
	addListeners();
	}
	
	private void addListeners(){
	final String thisUrl=url;
	this.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent e){
		HyperLinker.displayURL(thisUrl);
		}
		public void mouseEntered(MouseEvent e){
		HyperLinkLabel.this.rc=HyperLinkLabel.this.getForeground();
		HyperLinkLabel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		HyperLinkLabel.this.setForeground(Color.blue);
		}
		public void mouseExited(MouseEvent e){
		HyperLinkLabel.this.setForeground(HyperLinkLabel.this.rc);
		HyperLinkLabel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		 
		});
	}
}
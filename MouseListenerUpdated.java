package projectPart3;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class MouseListenerUpdated implements MouseListener {
	private JLabel label;

	@Override
	public void mouseClicked(MouseEvent e) {
		label.setText("(" + e.getX() + "," + e.getY() + ")");
		System.out.println(e.getPoint());
		
	}
	public MouseListenerUpdated(JLabel label) {
		this.label = label;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

package hw9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Viewer/controller for campus map and path
 * This panel contains the view plus buttons input controllers
 * that allow the user to enter the start and dest location to 
 * find shortest path in UW campus
 */
public class MapControl extends JPanel{
	private PathView viewPanel;
	private FoundPath path;
	private JTextField startName;
	private JTextField destName;
	
  /**
   * Construct a viewer/controller for the campus map with empty path
   */
	public MapControl() {
		setLayout(new BorderLayout());
		
		// create panel with campus map displayed
		path = new FoundPath();
		viewPanel = new PathView(path);
		viewPanel.setPreferredSize(new Dimension(900, 600));
		add(viewPanel, BorderLayout.CENTER);
		
		// register the view with the model
		path.addViewer(viewPanel);
		
		 // create panel with buttons and add it at the bottom
		JLabel from = new JLabel("From:");
		startName = new JTextField(5);
		startName.setActionCommand("setFrom");
		JLabel to = new JLabel("to:");
		destName = new JTextField(5);
		destName.setActionCommand("setTo");
		JPanel buttons = new JPanel();
		JButton find = new JButton("Find Path");
		JButton reset = new JButton("Reset");
		buttons.add(from);
		buttons.add(startName);
		buttons.add(to);
		buttons.add(destName);
		buttons.add(find);
		buttons.add(reset);
		add(buttons,BorderLayout.SOUTH);
		
		// set up listener for mouse clicks on the set and reset button
		MapButtonListener buttonListener = new MapButtonListener();
		find.addActionListener(buttonListener);
		reset.addActionListener(buttonListener);
	}
	
	/**
	 * Handle button clicks for the MapControl window.
	 */
	class MapButtonListener implements ActionListener {
	    /**
	     * Process button clicks by 
	     * 		1) display path on map if path is found
	     * 		2) display re-enter prompt if path is not found
	     * 		3) re-initialize the window if reset if clicked
	     * @param e the event created by the button click
	     */
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Find Path")) {
				path.findPath(startName.getText(), destName.getText());
			} else if (e.getActionCommand().equals("Reset")) {
				startName.setText("");
				destName.setText("");
				path.reset();
			}
		}
	}
}

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScrollPaneDemo extends Tab implements AdjustmentListener
{
	/*
	 * component:
	 */
	private CenterPanel centerPanel = new CenterPanel();
	private JScrollPane scrollPane = new JScrollPane(centerPanel);
	private JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
	private JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
	/*
	 * constructor
	 */
	ScrollPaneDemo()
	{
		super();
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		/*
		 * add AdjustmentListener to repaint centerPanel
		 */
		horizontalScrollBar.addAdjustmentListener(this);
		verticalScrollBar.addAdjustmentListener(this);
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent ev)
	{
		centerPanel.setDrawX(horizontalScrollBar.getValue()*1.0/(horizontalScrollBar.getMaximum()-horizontalScrollBar.getModel().getExtent()));
		centerPanel.setDrawY(verticalScrollBar.getValue()*1.0/(verticalScrollBar.getMaximum()-verticalScrollBar.getModel().getExtent()));
		centerPanel.repaint(centerPanel.getVisibleRect());
	}
	
	/*
	 * drawing panel:
	 */
	static class CenterPanel extends JComponent
	{
		private static final int CIRCLE_DIAMETER_1 = 220;
		private static final int CIRCLE_DIAMETER_2 = 136;
		private static final Color DARK_YELLOW = new Color(255,226,68,60);
		private static final Color VIOLET = new Color(84,39,130,60);
		private double x, y;
		CenterPanel()
		{
			super();
			this.setPreferredSize(new Dimension(3000,3000));
		}
		
		void setDrawX(double x)
		{
			this.x = x;
		}
		
		void setDrawY(double y)
		{
			this.y = y;
		}
		
		@Override
		protected void paintComponent(Graphics g)
		{
			//cast to Graphics2D object
			Graphics2D g2d = (Graphics2D)(g.create());
			//visible part information
			Rectangle rect = this.getVisibleRect();
			double width = rect.getWidth();
			double height = rect.getHeight();
			//now draw
			g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
			g2d.setColor(DARK_YELLOW);
			g2d.fillOval((int)(rect.x+x*(width-CIRCLE_DIAMETER_1)), (int)(rect.y+height-CIRCLE_DIAMETER_1), CIRCLE_DIAMETER_1, CIRCLE_DIAMETER_1);
			g2d.setColor(VIOLET);
			g2d.fillOval((int)(rect.x+width-CIRCLE_DIAMETER_2), (int)(rect.y+y*(height-CIRCLE_DIAMETER_2)), CIRCLE_DIAMETER_2, CIRCLE_DIAMETER_2);
			g2d.dispose();
		}
	}
}

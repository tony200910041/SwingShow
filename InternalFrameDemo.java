/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class InternalFrameDemo extends Tab
{
	private static HashSet<MyInternalFrame> newFrameSet = new HashSet<>();
	//Icons:
	private static final ImageIcon red = InternalFrameDemo.getIcon("INTERNALFRAME_RED");
	private static final ImageIcon yellow = InternalFrameDemo.getIcon("INTERNALFRAME_YELLOW");
	private static final ImageIcon blue = InternalFrameDemo.getIcon("INTERNALFRAME_BLUE");
	private static final ImageIcon green = InternalFrameDemo.getIcon("INTERNALFRAME_GREEN");
	//Components:
	private JDesktopPane desktopPane = new JDesktopPane();
	private JCheckBox box1 = new JCheckBox("Resizable",true);
	private JCheckBox box2 = new JCheckBox("Closable",true);
	private JCheckBox box3 = new JCheckBox("Maximizable",true);
	private JCheckBox box4 = new JCheckBox("Iconifiable",true);
	private JTextField textField = new JTextField("Frame",20);
	private JInternalFrame mainFrame = this.createMainFrame();
	InternalFrameDemo()
	{
		/*
		 * no-arg constructor will be invoked in SwingShow
		 */
		super();
		this.setLayout(new BorderLayout());
		this.add(desktopPane, BorderLayout.CENTER);
		//
		desktopPane.add(mainFrame,3);
		/*
		 * add back MyInternalFrame
		 */
		for (MyInternalFrame frame: newFrameSet)
		{
			desktopPane.add(frame);
		}
		mainFrame.setVisible(true);
	}
	
	JInternalFrame createMainFrame()
	{
		/* 
		 * return the main JInternalFrame
		 */
		JInternalFrame mainFrame = new JInternalFrame("",true,false,false,false);
		mainFrame.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
		mainFrame.setLayout(new BorderLayout());
		JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
		JPanel P1 = new JPanel(new GridLayout(2,2,0,0));
		P1.add(box1);
		P1.add(box2);
		P1.add(box3);
		P1.add(box4);
		center.add(P1);
		JPanel P2 = new JPanel(new GridLayout(2,0,10,10));
		P2.add(new MyButton("Generate",1));
		P2.add(new MyButton("Generate",2));
		P2.add(new MyButton("Generate",3));
		P2.add(new MyButton("Generate",4));
		center.add(P2);
		center.add(new MyButton("Close all",-1));
		mainFrame.add(center, BorderLayout.CENTER);
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel label = new JLabel("Title:");
		bottom.add(label);
		bottom.add(textField);
		mainFrame.add(bottom, BorderLayout.PAGE_END);
		mainFrame.pack();
		mainFrame.setLocation(0,0);
		return mainFrame;
	}
	
	class MyButton extends JButton implements ActionListener
	{
		/*
		 * buttons in the main JInternalFrame
		 */
		private int index;
		MyButton(String text, int index)
		{
			super(text);
			this.index = index;
			this.addActionListener(this);
			if (index > 0) this.setBackground(InternalFrameDemo.getColor(index,false));
		}
		
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			if (index > 0)
			{
				//create new frame
				MyInternalFrame frame = new MyInternalFrame(textField.getText(),box1.isSelected(),box2.isSelected(),box3.isSelected(),box4.isSelected(),index);
				desktopPane.add(frame);
				frame.setVisible(true);
			}
			else if (index == -1)
			{
				//close all frame
				for (MyInternalFrame frame: newFrameSet)
				{
					frame.dispose();
				}
				MyInternalFrame.map.clear();
			}
		}
	}
	
	static class MyInternalFrame extends JInternalFrame
	{
		/*
		 * MyInternalFrame: the new frames created by users
		 * map: store MyInternalFrame count information
		 * label: show ImageIcon
		 */
		static HashMap<String, Integer> map = new HashMap<>();
		private JLabel label = new JLabel();
		MyInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable, int colorMode)
		{
			super();
			int count;
			if (map.containsKey(title))
			{
				count = map.get(title);
			}
			else
			{
				count = 0;				
			}
			this.setTitle(title+" "+count);
			this.setResizable(resizable);
			this.setClosable(closable);
			this.setMaximizable(maximizable);
			this.setIconifiable(iconifiable);
			this.setLayout(new GridBagLayout());
			this.add(label);
			this.setSize(260,220);
			this.setLocation((count%5)*30+10, (count%5)*30+140);
			this.getContentPane().setBackground(InternalFrameDemo.getColor(colorMode,true));
			this.label.setIcon(getIcon(colorMode));
			newFrameSet.add(this);
			map.put(title,count+1);
		}
	}
	
	static Color getColor(int colorMode, boolean isDark)
	{
		/*
		 * get Color object from int
		 */
		int r=0, g=0, b=0;
		switch (colorMode)
		{
			case 1: default:
			r=255;
			break;
			
			case 2:
			r=255;
			g=255;
			break;
			
			case 3:
			b=255;
			break;
			
			case 4:
			g=255;
			break;
		}
		if (isDark)
		{
			r=Math.max(0,r-50);
			g=Math.max(0,g-50);
			b=Math.max(0,b-50);
		}
		return new Color(r,g,b);
	}
	
	static ImageIcon getIcon(int index)
	{
		/*
		 * get ImageIcon object from int
		 */
		switch (index)
		{
			case 1: return red;
			case 2: return yellow;
			case 3: return blue;	
			case 4: return green;		
			default: return null;
		}
	}
	
	@Override
	Runnable getExtraRunnable()
	{
		return new Runnable()
		{
			@Override
			public void run()
			{
				mainFrame.pack();
			}
		};
	}
}

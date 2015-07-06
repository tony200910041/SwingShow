/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class ButtonDemo extends Tab
{
	private JTabbedPane tabbedPane = new JTabbedPane();
	ButtonDemo()
	{
		super();
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		/*
		 * tab1
		 */
		JPanel P1 = new JPanel(new GridLayout(3,1,0,0));
		P1.add(ButtonDemo.createDefaultButtonPanel());
		P1.add(ButtonDemo.createColoredAndBorderedButtonPanel());
		P1.add(ButtonDemo.createSpecialButtonPanel());
		tabbedPane.addTab("JButton", P1);
		/*
		 * tab2
		 */
		JPanel P2 = new JPanel(new GridLayout(4,1,0,0));
		P2.add(ButtonDemo.createCheckBoxPanel());
		P2.add(ButtonDemo.createRadioButtonPanel());
		P2.add(ButtonDemo.createToggleButtonPanel());
		tabbedPane.addTab("Others", P2);
	}
	
	static JPanel createDefaultButtonPanel()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.setBorder(new TitledBorder("Default style"));
		//
		panel.add(new JButton("Default"));
		panel.add(new JButton("----------Longer----------"));
		panel.add(new JButton(" "));
		//
		JButton b1 = new JButton("80x80");
		b1.setPreferredSize(new Dimension(80,80));
		panel.add(b1);
		//
		JButton b2 = new JButton("Focus not painted");
		b2.setFocusPainted(false);
		panel.add(b2);
		return panel;
	}
	
	static JPanel createColoredAndBorderedButtonPanel()
	{
		/* 
		 * first four buttons: colored
		 */
		JPanel P1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton b1 = new JButton("Button 1");
		b1.setBackground(Color.RED);
		b1.setForeground(Color.WHITE);
		P1.add(b1);
		//
		JButton b2 = new JButton("Button 2");
		b2.setBackground(Color.YELLOW);
		b2.setForeground(new Color(151,124,50));
		b2.setFont(new Font("Serif",Font.BOLD,12));
		P1.add(b2);
		//
		JButton b3 = new JButton("Button 3");
		b3.setBackground(Color.BLUE);
		b3.setForeground(Color.WHITE);
		P1.add(b3);
		//
		JButton b4 = new JButton("Button 4");
		b4.setBackground(new Color(170,255,170));
		P1.add(b4);
		/*
		 * another four buttons: bordered
		 */
		JPanel P2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton b5 = new JButton("Black border");
		b5.setBackground(Color.WHITE);
		b5.setFocusPainted(false);
		b5.setBorder(LineBorder.createBlackLineBorder());
		P2.add(b5);
		//
		JButton b6 = new JButton("Red thicker border");
		b6.setBorder(new LineBorder(Color.RED,5));
		P2.add(b6);
		//
		JButton b7 = new JButton("RAISED");
		b7.setBorder(new BevelBorder(BevelBorder.RAISED));
		P2.add(b7);
		//
		JButton b8 = new JButton("LOWERED");
		b8.setBorder(new BevelBorder(BevelBorder.LOWERED));
		P2.add(b8);
		//
		JPanel panel = new JPanel(new GridLayout(2,1,0,0));
		panel.setBorder(new TitledBorder("Colored and bordered"));
		panel.add(P1);
		panel.add(P2);
		return panel;
	}
	
	static JPanel createSpecialButtonPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Special"));
		//
		JButton b1 = new JButton("Disabled");
		b1.setEnabled(false);
		panel.add(b1);
		//
		final JButton b2 = new JButton("Click me!");
		b2.setFont(new Font("Carlito",Font.PLAIN,14));
		b2.setFocusPainted(false);
		b2.setBackground(new Color(252,255,206));
		b2.setPreferredSize(new Dimension(80,28));
		b2.setBorder(LineBorder.createBlackLineBorder());
		b2.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent ev)
			{
				b2.setBorder(LineBorder.createGrayLineBorder());
			}
			
			@Override
			public void mouseExited(MouseEvent ev)
			{
				b2.setBorder(LineBorder.createBlackLineBorder());
			}
			
			@Override
			public void mouseReleased(MouseEvent ev)
			{
				if (b2.getText().equals("Clicked!"))
				{
					b2.setText("Click me!");
				}
				else
				{
					b2.setText("Clicked!");
				}
				this.mouseExited(ev);
			}
		});
		panel.add(b2);
		//
		panel.add(new Button("System button (not Swing)"));
		return panel;
	}
	
	static JPanel createCheckBoxPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Check boxes"));
		panel.add(new JCheckBox("Egg"));
		panel.add(new JCheckBox("Sausage"));
		panel.add(new JCheckBox("Tomato"));
		panel.add(new JCheckBox("Carrot"));
		panel.add(new JCheckBox("Butter"));
		JCheckBox checkBox = new JCheckBox("Salmon",false);
		checkBox.setEnabled(false);
		panel.add(checkBox);
		panel.add(new Checkbox("System checkbox"));
		return panel;
	}
	
	static JPanel createRadioButtonPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Radio buttons"));
		final JRadioButton button1 = new JRadioButton("Coffee",true);
		final JRadioButton button2 = new JRadioButton("Orange juice",false);
		final JRadioButton button3 = new JRadioButton("Milk",false);
		final JRadioButton button4 = new JRadioButton("Water",false);
		final JRadioButton button5 = new JRadioButton("Lemon tea",false);
		final JRadioButton button6 = new JRadioButton("Wine",false);
		ActionListener listener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				/*
				 * deselect other radio buttons automatically
				 */
				button1.setSelected(false);
				button2.setSelected(false);
				button3.setSelected(false);
				button4.setSelected(false);
				button5.setSelected(false);
				((JRadioButton)(ev.getSource())).setSelected(true);
			}
		};
		button1.addActionListener(listener);
		button2.addActionListener(listener);
		button3.addActionListener(listener);
		button4.addActionListener(listener);
		button5.addActionListener(listener);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);
		button6.setEnabled(false);
		button6.setToolTipText("Wine is too expensive!");
		return panel;
	}
	
	static JPanel createToggleButtonPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("JToggleButton"));
		JToggleButton button1 = new JToggleButton("Toggle button 1");
		JToggleButton button2 = new JToggleButton("Toggle button 2");
		/*
		 * another ways to select and deselect
		 */
		ButtonGroup group = new ButtonGroup();
		group.add(button1);
		group.add(button2);
		panel.add(button1);
		panel.add(button2);
		button1.setSelected(true);
		return panel;
	}
}

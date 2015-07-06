/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class ComboBoxDemo extends Tab
{
	private static final Color GREEN = new Color(238,255,192);
	private static final Color YELLOW = new Color(255,254,231);
	private static final Border BLACK_BORDER = LineBorder.createBlackLineBorder();
	ComboBoxDemo()
	{
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel panel = new JPanel(new GridLayout(5,1,0,0));
		panel.add(ComboBoxDemo.createFontComboBoxPanel());
		panel.add(ComboBoxDemo.createEditableComboBoxPanel());
		panel.add(ComboBoxDemo.createDisabledComboBoxPanel());
		panel.add(ComboBoxDemo.createSpecialComboBoxPanel());
		panel.add(ComboBoxDemo.createAWTPanel());
		this.add(panel);
		
	}
	
	static JPanel createFontComboBoxPanel()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel P0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		P0.setPreferredSize(new Dimension(150,30));
		P0.add(new JLabel("Available font: "));
		panel.add(P0);
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		Vector<String> vector = new Vector<>();
		for (Font f: fonts)
		{
			vector.add(f.getName());
		}
		panel.add(new JComboBox<String>(vector));
		return panel;
	}
	
	static JPanel createEditableComboBoxPanel()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel P0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		P0.setPreferredSize(new Dimension(150,30));
		P0.add(new JLabel("Editable JComboBox: "));
		panel.add(P0);
		JComboBox<String> editable = new JComboBox<>(new String[]{"Aaron","Begonia","Chris","David","Eric"});
		editable.setEditable(true);
		panel.add(editable);
		return panel;
	}
	
	static JPanel createDisabledComboBoxPanel()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel P0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		P0.setPreferredSize(new Dimension(150,30));
		P0.add(new JLabel("Disabled: "));
		panel.add(P0);
		JComboBox<String> disabled = new JComboBox<>(new String[]{"Disabled"});
		disabled.setEnabled(false);
		panel.add(disabled);
		return panel;
	}
	
	static JPanel createSpecialComboBoxPanel()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel P0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		P0.setPreferredSize(new Dimension(150,30));
		P0.add(new JLabel("Special: "));
		panel.add(P0);
		JComboBox<String> special = new JComboBox<>(new String[]{"<html><b><font color\"red\" size=\"8\">Java</font></b></html>","C++","Python","Pascal","Fortran","Ruby","SmallTalk","Perl"});
		special.setPreferredSize(new Dimension(300,50));
		special.setBackground(Color.WHITE);
		special.setRenderer(new DefaultListCellRenderer()
		{
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
			{
				JLabel label = (JLabel)(super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus));
				if (isSelected)
				{
					label.setBackground(GREEN);
					label.setBorder(BLACK_BORDER);
				}
				else
				{
					label.setBackground(YELLOW);
					label.setBorder(null);
				}
				return label;
			}
		});
		panel.add(special);
		return panel;
	}
	
	static JPanel createAWTPanel()
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton button = new JButton("Show AWT \"Choice\"");
		button.setToolTipText("NOT Swing Component");
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				JDialog dialog = new JDialog();
				dialog.setTitle("AWT Choice");
				dialog.setModal(true);
				dialog.setLayout(new BorderLayout());
				Choice choice = new Choice();
				choice.add("Not Swing");
				choice.add("AWT Choice");
				choice.add("heavyweight");
				choice.add("System combo box");
				//
				JPanel center = new JPanel(new GridBagLayout());
				center.setPreferredSize(new Dimension(250,100));
				center.add(choice);
				dialog.add(center,BorderLayout.CENTER);
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		});
		panel.add(button);
		return panel;
	}
}

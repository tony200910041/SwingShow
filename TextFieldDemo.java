/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class TextFieldDemo extends Tab
{
	TextFieldDemo()
	{
		super();
		this.setLayout(new GridLayout(3,1,0,0));
		this.add(TextFieldDemo.createDefaultTextFieldPanel());
		this.add(TextFieldDemo.createCustomTextFieldPanel());
		this.add(TextFieldDemo.createSpecialTextFieldPanel());
	}
	
	static JPanel createDefaultTextFieldPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Default JTextField"));
		panel.add(new JTextField("6 columns",6));
		panel.add(new JTextField("10 columns",10));
		panel.add(new JTextField("25 columns",25));
		return panel;
	}
	
	static JPanel createCustomTextFieldPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Styled JTextField"));
		JTextField tf1 = new JTextField(12);
		tf1.setBorder(LineBorder.createBlackLineBorder());
		tf1.setFont(new Font("Serif",Font.PLAIN,18));
		panel.add(tf1);
		JTextField tf2 = new JTextField("Large");
		tf2.setPreferredSize(new Dimension(100,100));
		panel.add(tf2);
		JTextField tf3 = new JTextField("Colored",12);
		tf3.setBorder(new LineBorder(Color.RED,2));
		tf3.setBackground(new Color(254,255,196));
		tf3.setForeground(new Color(49,13,13));
		tf3.setSelectionColor(new Color(200,200,200));
		panel.add(tf3);
		JTextField tf4 = new JTextField("Disabled",8);
		tf4.setEnabled(false);
		panel.add(tf4);
		return panel;
	}
	
	static JPanel createSpecialTextFieldPanel()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("JFormattedTextField, JPasswordField and AWT TextField"));
		JFormattedTextField ftf1;
		try
		{
			ftf1 = new JFormattedTextField(new MaskFormatter("####"));
		}
		catch (Exception ex)
		{
			ftf1 = null;
		}
		ftf1.setFocusLostBehavior(JFormattedTextField.COMMIT);
		ftf1.setColumns(12);
		ftf1.setText("1234");
		panel.add(ftf1);
		final JPasswordField pf1 = new JPasswordField(12);
		final JLabel password = new JLabel("Your password is: N/A");
		pf1.getDocument().addDocumentListener(new DocumentListener()
		{
			/*
			 * show the password automatically
			 */
			@Override
			public void changedUpdate(DocumentEvent ev)
			{
				update();
			}
			
			@Override
			public void insertUpdate(DocumentEvent ev)
			{
				update();
			}
			
			@Override
			public void removeUpdate(DocumentEvent ev)
			{
				update();
			}
			void update()
			{
				String str = new String(pf1.getPassword());
				password.setText("Your password is: " + (str.isEmpty()?"N/A":str));
			}
		});
		panel.add(pf1);
		panel.add(password);
		panel.add(new TextField("not Swing",12));
		return panel;
	}
}

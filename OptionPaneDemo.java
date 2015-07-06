/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class OptionPaneDemo extends Tab
{
	public OptionPaneDemo()
	{
		super();
		this.setLayout(new GridBagLayout());
		//only one component, placed at center
		this.add(createPanel());
	}
	
	static JPanel createPanel()
	{
		final JPanel panel = new JPanel(new GridLayout(4,1));
		panel.add(createMessagePanel());
		panel.add(createConfirmPanel());
		panel.add(createOtherPanel());
		return panel;
	}
	
	static JPanel createMessagePanel()
	{
		final JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Message dialog"));
		final JRadioButton info = new JRadioButton("Information",true);
		final JRadioButton question = new JRadioButton("Question",false);
		final JRadioButton warning = new JRadioButton("Warning",false);
		final JRadioButton error = new JRadioButton("Error",false);
		final JRadioButton plain = new JRadioButton("Plain",false);
		//
		ButtonGroup group = new ButtonGroup();
		group.add(info);
		group.add(question);
		group.add(warning);
		group.add(error);
		group.add(plain);
		//
		panel.add(info);
		panel.add(question);
		panel.add(warning);
		panel.add(error);
		panel.add(plain);
		//
		JButton showMessage = new JButton("Show");
		showMessage.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				int messageType;
				if (info.isSelected()) messageType = JOptionPane.INFORMATION_MESSAGE;
				else if (question.isSelected()) messageType = JOptionPane.QUESTION_MESSAGE;
				else if (warning.isSelected()) messageType = JOptionPane.WARNING_MESSAGE;
				else if (error.isSelected()) messageType = JOptionPane.ERROR_MESSAGE;
				else messageType = JOptionPane.PLAIN_MESSAGE;
				JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(panel), "This is a message dialog.", "Message", messageType);
			}
		});
		panel.add(showMessage);
		return panel;
	}
	
	static JPanel createConfirmPanel()
	{
		final JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Confirm dialog"));
		final JRadioButton yes_no = new JRadioButton("Yes, No",true);
		final JRadioButton yes_no_cancel = new JRadioButton("Yes, No, Cancel",false);
		final JRadioButton ok_cancel = new JRadioButton("OK, Cancel",false);
		//
		ButtonGroup group = new ButtonGroup();
		group.add(yes_no);
		group.add(yes_no_cancel);
		group.add(ok_cancel);
		//
		panel.add(yes_no);
		panel.add(yes_no_cancel);
		panel.add(ok_cancel);
		//
		JButton showConfirm = new JButton("Show");
		showConfirm.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				Window parent = SwingUtilities.windowForComponent(panel);
				int confirmType;
				if (yes_no.isSelected()) confirmType = JOptionPane.YES_NO_OPTION;
				else if (yes_no_cancel.isSelected()) confirmType = JOptionPane.YES_NO_CANCEL_OPTION;
				else confirmType = JOptionPane.OK_CANCEL_OPTION;
				int option = JOptionPane.showConfirmDialog(parent, "Do you love Java Swing?", "Voting", confirmType);
				String chosen = null;
				switch (option)
				{
					case JOptionPane.YES_OPTION:
					chosen = "Sure! I love Swing too!";
					break;
					
					case JOptionPane.NO_OPTION:
					chosen = "Why not?";
					break;
					
					case JOptionPane.CANCEL_OPTION:
					chosen = "Cancelled =(";
					break;
					
					case JOptionPane.CLOSED_OPTION:
					default:
					chosen = "Closed =(";
					break;
				}
				JOptionPane.showMessageDialog(parent, chosen, "Chosen", JOptionPane.PLAIN_MESSAGE, getIcon("JAVA"));
			}
		});
		panel.add(showConfirm);
		return panel;
	}
	
	static JPanel createOtherPanel()
	{
		final JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Input and Option"));
		//input
		JButton input = new JButton("Show input dialog");
		input.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				Window parent = SwingUtilities.windowForComponent(panel);
				String entered = JOptionPane.showInputDialog(parent, "Enter your name:", "Input", JOptionPane.QUESTION_MESSAGE);
				if (entered != null)
				{
					if (!entered.isEmpty())
					{
						JOptionPane.showMessageDialog(parent, "Hi, " + entered + "!", "Hi!", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		panel.add(input);
		//option
		JButton option = new JButton("Show option dialog");
		option.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				Window parent = SwingUtilities.windowForComponent(panel);
				String[] options = new String[]{"Swing", "AWT", "SWT"};
				String chosen = (String)JOptionPane.showInputDialog(parent, "Choose:", "Options", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (chosen != null)
				{
					if (chosen.equals(options[0]))
					{
						JOptionPane.showMessageDialog(parent, "<html><b>Swing</b> is really powerful!</html>", "Chosen", JOptionPane.INFORMATION_MESSAGE);
					}
					else if (chosen.equals(options[1]))
					{
						JOptionPane.showMessageDialog(parent, "<html>AWT is outdated...</html>", "Chosen", JOptionPane.INFORMATION_MESSAGE);
					}
					else if (chosen.equals(options[2]))
					{
						JOptionPane.showMessageDialog(parent, "Swing is better than SWT!", "Chosen", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		panel.add(option);
		return panel;
	}
}

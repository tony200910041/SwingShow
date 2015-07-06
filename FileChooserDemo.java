/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;
import java.util.*;

public class FileChooserDemo extends Tab
{
	private JTabbedPane tabbedPane = new JTabbedPane();
	public FileChooserDemo()
	{
		super();
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		//tab1: basic
		tabbedPane.addTab("Basic", createBasicFileChooserTab());
		tabbedPane.addTab("Advanced", createCustomFileChooserTab());
	}
	
	static JPanel createBasicFileChooserTab()
	{
		final JFileChooser chooser = new JFileChooser();
		chooser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(chooser), "Chosen: " + chooser.getSelectedFile().getPath());
			}
		});
		JPanel panel = new JPanel(new GridLayout(1,1));
		panel.add(chooser);
		return panel;
	}
	
	static JPanel createCustomFileChooserTab()
	{
		JPanel panel = new JPanel(new GridLayout(4,1,0,0));
		//
		JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder("General"));
		//hidden file
		final JCheckBox showHidden = new JCheckBox("Show hidden file", true);
		panel1.add(showHidden);
		//drag
		final JCheckBox drag = new JCheckBox("Enable drag support", true);
		panel1.add(drag);
		//multi-selection mode
		final JCheckBox multiSelection = new JCheckBox("Enable multi-selection", false);
		panel1.add(multiSelection);
		//title
		final JTextField title = new JTextField("Choose:",12);
		panel1.add(new JLabel("Title: "));
		panel1.add(title);
		panel.add(panel1);
		//
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder("File selection mode"));
		//dir/file
		final JRadioButton dirOnly = new JRadioButton("Directories only", true);
		final JRadioButton fileOnly = new JRadioButton("Files only", false);
		final JRadioButton both = new JRadioButton("Both", false);
		ButtonGroup group = new ButtonGroup();
		group.add(dirOnly);
		group.add(fileOnly);
		group.add(both);
		panel2.add(dirOnly);
		panel2.add(fileOnly);
		panel2.add(both);
		panel.add(panel2);
		//
		JPanel panel3 = new JPanel();
		//list:
		final DefaultListModel<String> listModel = new DefaultListModel<>();
		final JList<String> extList = new JList<>(listModel);
		extList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listModel.addElement("txt");
		//textField: extension input
		panel3.add(new JLabel("Extension to add: "));
		final JTextField extension = new JTextField(12);
		panel3.add(extension);
		//button1: add		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				listModel.addElement(extension.getText());
			}
		});
		panel3.add(add);
		//button2: remove
		final JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				if (listModel.size() > 1)
				{
					int index = extList.getSelectedIndex();
					if (index != -1)
					{
						listModel.remove(index);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(SwingUtilities.windowForComponent(remove), "You must specify at least one extension.");
				}
			}
		});
		panel3.add(remove);
		JScrollPane scrollPane = new JScrollPane(extList);
		scrollPane.setPreferredSize(new Dimension(100,150));
		panel3.add(scrollPane);
		panel.add(panel3);
		//
		JPanel panel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton show = new JButton("Show JFileChooser");
		show.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				JFileChooser chooser = new JFileChooser();
				chooser.setFileHidingEnabled(showHidden.isSelected());
				chooser.setDragEnabled(drag.isSelected());
				chooser.setMultiSelectionEnabled(multiSelection.isSelected());
				chooser.setDialogTitle(title.getText());
				chooser.setAccessory(new JLabel("Custom accessory"));
				if (dirOnly.isSelected())
				{
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				}
				else if (fileOnly.isSelected())
				{
					chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				}
				else if (both.isSelected())
				{
					chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				}
				//get format from JList
				Set<String> format = new HashSet<>(Collections.list(listModel.elements()));
				ArrayList<String> formatList = new ArrayList<>();
				for (String s: format)
				{
					formatList.add(s.toUpperCase());
					formatList.add(s.toLowerCase());
				}
				FileFilter filter1 = new FileNameExtensionFilter("Custom", formatList.toArray(new String[0]));
				chooser.addChoosableFileFilter(filter1);
				FileFilter filter2 = new FileFilter()
				{
					@Override
					public boolean accept(java.io.File f)
					{
						return f.isDirectory();
					}
					
					@Override
					public String getDescription()
					{
						return "Directories only";
					}
				};
				chooser.addChoosableFileFilter(filter2);
				chooser.setFileFilter(filter1);
				chooser.showDialog(SwingUtilities.windowForComponent(chooser), "Choose");
			}
		});
		panel4.add(show);
		panel.add(panel4);
		return panel;
	}
}

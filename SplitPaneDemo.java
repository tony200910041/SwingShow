/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.text.*;
import java.lang.reflect.*;

public class SplitPaneDemo extends Tab
{
	/*
	 * for format number
	 */
	private static final DecimalFormat format = new DecimalFormat("#0.#####");
	/*
	 * list:
	 */
	private DefaultListModel<Keyword> lm = new DefaultListModel<>();
	private JList<Keyword> list = new JList<>(lm);
	/*
	 * table:
	 */
	private DefaultTableModel tam = new DefaultTableModel();
	private JTable table = new JTable(tam)
	{
		@Override
		public boolean isCellEditable(int row, int column)
		{
			if (column == 0) return false;
			else return super.isCellEditable(row,column);
		}
	};
	/*
	 * split pane:
	 */
	private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(list),new JScrollPane(table));
	private JPanel ctrlPanel = new JPanel(new GridLayout(3,1,0,0));
	SplitPaneDemo()
	{
		super();
		this.setLayout(new BorderLayout());
		this.add(splitPane, BorderLayout.CENTER);
		this.add(ctrlPanel, BorderLayout.PAGE_END);
		ctrlPanel.setPreferredSize(new Dimension(0,100)); //set preferred height
		//initialization:
		this.initializeList();
		this.initializeTable();
		this.initializePanel();
		splitPane.setOneTouchExpandable(false);
		splitPane.setContinuousLayout(false);
	}
	
	void initializeList()
	{
		list.setCellRenderer(new DefaultListCellRenderer()
		{
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
			{
				JLabel label = (JLabel)(super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus));
				Keyword k = (Keyword)value;
				label.setText(k.name().toLowerCase());
				return label;
			}
		});
		for (Keyword k: Keyword.values())
		{
			lm.addElement(k);
		}
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	void initializeTable()
	{
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setRowHeight(28);
		tam.addColumn("Color");
		tam.addColumn("RGB");
		tam.addColumn("HSB");
		table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer()
		{
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
			{
				JLabel label = (JLabel)(super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column));
				NamedColor c = (NamedColor)value;
				label.setText(c.name);
				if (!isSelected)
				{
					label.setBackground(c.color);
				}
				return label;
			}
		});
		Field[] fields = Color.class.getDeclaredFields();
		for (Field field: fields)
		{
			if (Modifier.isStatic(field.getModifiers()))
			{
				String name = field.getName();
				if (name.equals(name.toUpperCase()))
				{
					try
					{
						Color c = (Color)(field.get(null));
						float[] hsb = Color.RGBtoHSB(c.getRed(),c.getGreen(),c.getBlue(),null);
						tam.addRow(new Object[]{new NamedColor(c,name),toString(new float[]{c.getRed(),c.getGreen(),c.getBlue()}),toString(hsb)});
					}
					catch (Exception ex)
					{
					}
				}
			}			
		}
	}
	
	void initializePanel()
	{
		/*
		 * panel 1: JSplitPane control panel
		 */
		JPanel p1 = new JPanel();
		//one-touch expandable
		final JCheckBox oneTouchExpandable = new JCheckBox("One-touch expandable",false);
		oneTouchExpandable.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				SplitPaneDemo.this.splitPane.setOneTouchExpandable(oneTouchExpandable.isSelected());
			}
		});
		p1.add(oneTouchExpandable);
		//horizontal/vertical
		final JCheckBox horizontal = new JCheckBox("Horizontal",true);
		horizontal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				SplitPaneDemo.this.splitPane.setOrientation(horizontal.isSelected()?JSplitPane.HORIZONTAL_SPLIT:JSplitPane.VERTICAL_SPLIT);
			}
		});
		p1.add(horizontal);
		//continuous
		final JCheckBox continuous = new JCheckBox("Continuous",false);
		continuous.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				SplitPaneDemo.this.splitPane.setContinuousLayout(continuous.isSelected());
			}
		});		
		p1.add(continuous);
		this.ctrlPanel.add(p1);
		/*
		 * panel 2: JList control panel
		 */
		JPanel p2 = new JPanel();
		//list selection mode:
		final JRadioButton r1 = new JRadioButton("Single selection",true);
		final JRadioButton r2 = new JRadioButton("Single interval selection",false);
		final JRadioButton r3 = new JRadioButton("Multiple interval selection",false);
		ActionListener smListener = new ActionListener()
		{
			/*
			 * lazy approach to ensure only one JRadioButton is selected
			 */
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				r1.setSelected(false);
				r2.setSelected(false);
				r3.setSelected(false);
				JRadioButton selected = (JRadioButton)(ev.getSource());
				selected.setSelected(true);
				if (selected == r1)
				{
					list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				}
				else if (selected == r2)
				{
					list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
				}
				else if (selected == r3)
				{
					list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				}
			}
		};
		final JCheckBox listEnabled = new JCheckBox("Enabled",true);
		listEnabled.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				list.setEnabled(listEnabled.isSelected());
			}
		});
		r1.addActionListener(smListener);
		r2.addActionListener(smListener);
		r3.addActionListener(smListener);
		p2.add(new JLabel("JList: "));
		p2.add(r1);
		p2.add(r2);
		p2.add(r3);
		p2.add(listEnabled);
		this.ctrlPanel.add(p2);
		/*
		 * panel 3: JTable
		 */
		JPanel p3 = new JPanel();
		final JRadioButton t1 = new JRadioButton("Resize off",false);
		final JRadioButton t2 = new JRadioButton("Next column",true);
		final JRadioButton t3 = new JRadioButton("Subsequent columns",false);
		final JRadioButton t4 = new JRadioButton("Last column",false);
		final JRadioButton t5 = new JRadioButton("All columns",false);
		ActionListener rmListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				t1.setSelected(false);
				t2.setSelected(false);
				t3.setSelected(false);
				t4.setSelected(false);
				t5.setSelected(false);
				JRadioButton selected = (JRadioButton)(ev.getSource());
				selected.setSelected(true);
				int mode;
				if (selected == t1)
				{
					mode = JTable.AUTO_RESIZE_OFF;
				}
				else if (selected == t2)
				{
					mode = JTable.AUTO_RESIZE_NEXT_COLUMN;
				}
				else if (selected == t3)
				{
					mode = JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS;
				}
				else if (selected == t4)
				{
					mode = JTable.AUTO_RESIZE_LAST_COLUMN;
				}
				else
				{
					mode = JTable.AUTO_RESIZE_ALL_COLUMNS;
				}
				SplitPaneDemo.this.table.setAutoResizeMode(mode);
			}
		};
		t1.addActionListener(rmListener);
		t2.addActionListener(rmListener);
		t3.addActionListener(rmListener);
		t4.addActionListener(rmListener);
		t5.addActionListener(rmListener);
		final JCheckBox tableEnabled = new JCheckBox("Enabled",true);
		tableEnabled.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				SplitPaneDemo.this.table.setEnabled(tableEnabled.isSelected());
			}
		});
		p3.add(new JLabel("JTable: "));
		p3.add(t1);
		p3.add(t2);
		p3.add(t3);
		p3.add(t4);
		p3.add(t5);
		p3.add(tableEnabled);
		this.ctrlPanel.add(p3);
	}
	
	class NamedColor
	{
		final Color color;
		final String name;
		NamedColor(Color color, String name)
		{
			this.color = color;
			this.name = name;
		}
	}
	
	private String toString(float[] f)
	{
		return "(" + toString(f[0]) + ", " + toString(f[1]) + ", " + toString(f[2]) + ")";
	}
	
	private String toString(float x)
	{
		return format.format(x);
	}
	
	@Override
	Runnable getExtraRunnable()
	{
		return new Runnable()
		{
			@Override
			public void run()
			{
				splitPane.setDividerLocation(0.4);
			}
		};
	}
	
	enum Keyword
	{
		/*
		 * Java keywords
		 */
		ABSTRACT, ASSERT, BOOLEAN, BREAK, BYTE, CASE, CATCH, CHAR, CLASS, CONST,
		CONTINUE, DEFAULT, DO, DOUBLE, ELSE, ENUM, EXTENDS, FINAL, FINALLY, FLOAT,
		FOR, GOTO, IF, IMPLEMENTS, IMPORT, INSTANCEOF, INT, INTERFACE, LONG, NATIVE,
		NEW, PACKAGE, PRIVATE, PROTECTED, PUBLIC, RETURN, SHORT, STATIC, STRICTFP, SUPER,
		SWITCH, SYNCHRONIZED, THIS, THROW, THROWS, TRANSIENT, TRY, VOID, VOLATILE, WHILE;
	}
}

/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.text.*;
import java.net.*;
import java.lang.reflect.*;

public class EditorPaneDemo extends Tab
{
	private static final Font LAYER_FONT = new JButton().getFont().deriveFont(100f);
	private static final Color LAYER_FOREGROUND = new Color(0,0,0,15);
	private static final String LAYER_TEXT = "JLayer";
	private static final SimpleAttributeSet DEFAULT_SET = new SimpleAttributeSet();
	private static final SimpleAttributeSet BOLD_SET = new SimpleAttributeSet();
	private static final SimpleAttributeSet ITALIC_SET = new SimpleAttributeSet();
	private static final SimpleAttributeSet UNDERLINE_SET = new SimpleAttributeSet();
	private static final SimpleAttributeSet BIG_SIZE_SET = new SimpleAttributeSet();
	private static final SimpleAttributeSet RED_SET = new SimpleAttributeSet();
	static
	{
		StyleConstants.setBold(BOLD_SET, true);
		StyleConstants.setItalic(ITALIC_SET, true);
		StyleConstants.setUnderline(UNDERLINE_SET, true);
		StyleConstants.setFontSize(BIG_SIZE_SET, 40);
		StyleConstants.setForeground(RED_SET, Color.RED);
	}
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JEditorPane editorPane = new JEditorPane();
	private JTextPane textPane = new JTextPane();
	private JTextArea textArea = new JTextArea("Plain text only!");
	public EditorPaneDemo()
	{
		super();
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		//
		tabbedPane.addTab("JEditorPane", new JScrollPane(editorPane));
		try
		{
			editorPane.setPage(EditorPaneDemo.class.getResource("EDITORPANE_DEMO.HTML"));
			editorPane.setEditable(false);
			editorPane.addHyperlinkListener(new HyperlinkListener()
			{
				@Override
				public void hyperlinkUpdate(HyperlinkEvent ev)
				{
					if (ev.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
					{
						if (Desktop.isDesktopSupported())
						{
							try
							{
								URI uri = ev.getURL().toURI();
								int option = JOptionPane.showConfirmDialog(SwingUtilities.windowForComponent(EditorPaneDemo.this), "Open in default browser?", "Open", JOptionPane.YES_NO_OPTION);
								if (option == JOptionPane.YES_OPTION)
								{
									Desktop.getDesktop().browse(uri);
								}
							}
							catch (Exception ex)
							{
							}
						}
					}
				}
			});
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		//
		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.add(createToolBar(), BorderLayout.PAGE_START);
		textPanel.add(new JScrollPane(textPane), BorderLayout.CENTER);
		tabbedPane.addTab("JTextPane", textPanel);
		//
		textArea.setFont(textArea.getFont().deriveFont(16f));
		textArea.setComponentPopupMenu(createPopup(textArea));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		JPanel textPanel2 = new JPanel(new BorderLayout());
		textPanel2.add(new JScrollPane(createLayer(textArea)), BorderLayout.CENTER);
		//
		JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JCheckBox useWrap = new JCheckBox("Line wrap", true);
		final JCheckBox wrapStyleWord = new JCheckBox("Wrap by word", true);
		ActionListener actionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				if (ev.getSource() == useWrap)
				{
					boolean selected = useWrap.isSelected();
					wrapStyleWord.setEnabled(selected);
					textArea.setLineWrap(selected);
				}
				else if (ev.getSource() == wrapStyleWord)
				{
					textArea.setWrapStyleWord(wrapStyleWord.isSelected());
				}
			}
		};
		useWrap.addActionListener(actionListener);
		wrapStyleWord.addActionListener(actionListener);
		checkBoxPanel.add(useWrap);
		checkBoxPanel.add(wrapStyleWord);
		textPanel2.add(checkBoxPanel, BorderLayout.PAGE_END);
		tabbedPane.addTab("JTextArea", textPanel2);
	}
	
	JToolBar createToolBar()
	{
		JToolBar toolBar = new JToolBar("JEditorPane toolbar");
		JButton _default = toolBar.add(new ToolBarAction(DEFAULT_SET));
		_default.setToolTipText("Default");
		_default.setIcon(getIcon("DEFAULT"));
		JButton bold = toolBar.add(new ToolBarAction(BOLD_SET));
		bold.setToolTipText("Bold");
		bold.setIcon(getIcon("BOLD"));
		JButton italic = toolBar.add(new ToolBarAction(ITALIC_SET));
		italic.setToolTipText("Italic");
		italic.setIcon(getIcon("ITALIC"));
		JButton underline = toolBar.add(new ToolBarAction(UNDERLINE_SET));
		underline.setToolTipText("Underline");
		underline.setIcon(getIcon("UNDERLINE"));
		JButton bigSize = toolBar.add(new ToolBarAction(BIG_SIZE_SET));
		bigSize.setToolTipText("Big size");
		bigSize.setIcon(getIcon("BIG_TEXT"));
		JButton red = toolBar.add(new ToolBarAction(RED_SET));
		red.setToolTipText("Red");
		red.setIcon(getIcon("RED"));
		return toolBar;
	}
	
	JLayer<JTextArea> createLayer(JTextArea textArea)
	{
		JLayer<JTextArea> layer = new JLayer<JTextArea>(textArea, new LayerUI<JTextArea>()
		{
			@Override
			public void paint(Graphics g, JComponent c)
			{
				super.paint(g,c);
				Rectangle rect = c.getVisibleRect();
				g.setFont(LAYER_FONT);
				g.setColor(LAYER_FOREGROUND);
				FontMetrics metrics = g.getFontMetrics();
				int stringWidth = metrics.stringWidth(LAYER_TEXT);
				int stringHeight = metrics.getAscent()-metrics.getDescent();
				g.drawString(LAYER_TEXT, (int)(rect.getX()+(rect.getWidth()-stringWidth)/2), (int)(rect.getY()+(rect.getHeight()+stringHeight)/2));
			}
		});
		return layer;
	}
	
	JPopupMenu createPopup(JTextArea textArea)
	{
		JPopupMenu popup = new JPopupMenu();
		popup.add(new TextAreaAction(textArea,"Cut"));
		popup.add(new TextAreaAction(textArea,"Copy"));
		popup.add(new TextAreaAction(textArea,"Paste"));
		return popup;
	}
	
	class ToolBarAction extends AbstractAction
	{
		private SimpleAttributeSet set;
		ToolBarAction(SimpleAttributeSet set)
		{
			super();
			this.set = set;
		}
		
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			int start = EditorPaneDemo.this.textPane.getSelectionStart();
			int end = EditorPaneDemo.this.textPane.getSelectionEnd();
			EditorPaneDemo.this.textPane.getStyledDocument().setCharacterAttributes(start,end-start,this.set,this.set==DEFAULT_SET);
		}
	}
	
	class TextAreaAction extends AbstractAction
	{
		private JTextArea textArea;
		private String method;
		TextAreaAction(JTextArea textArea, String method)
		{
			super(method);
			this.textArea = textArea;
			this.method = method.toLowerCase();
		}
		
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			//a quick solution
			try
			{
				Method m = JTextArea.class.getMethod(this.method);
				m.invoke(textArea);
			}
			catch (Exception ex)
			{
				throw new InternalError();
			}
		}
	}
}

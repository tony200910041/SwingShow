/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Welcome extends Tab implements MouseListener
{
	private static final Font f50 = new Font("Carlito",Font.PLAIN,50);
	private JLabel label = new JLabel("Welcome to Swing!");
	Welcome()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.add(label);
		this.setBackground(new Color(239,255,226));
		//
		label.setFont(f50);
		label.addMouseListener(this);
	}
	
	@Override
	ImageIcon getIcon()
	{
		return Welcome.getIcon("JAVA");
	}
	
	@Override
	boolean hasSourceCode()
	{
		return false;
	}
	
	@Override
	public void mouseEntered(MouseEvent ev)
	{
		label.setForeground(new Color(157,2,2));
	}
	
	@Override
	public void mouseExited(MouseEvent ev)
	{
		label.setForeground(Color.BLACK);
	}
	
	@Override
	public void mouseReleased(MouseEvent ev)
	{
	}
	
	@Override
	public void mouseClicked(MouseEvent ev)
	{
	}
	
	@Override
	public void mousePressed(MouseEvent ev)
	{
	}	
}

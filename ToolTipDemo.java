/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ToolTipDemo extends Tab
{
	ToolTipDemo()
	{
		super();
		this.setLayout(new GridBagLayout());
		this.add(createLabel());
	}
	
	static JLabel createLabel()
	{
		JLabel label = new JLabel("<html>The quick brown fox<br>jumps over the lazy dog.</html>");
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 40));
		label.setToolTipText("<html><h1><font face=\"Times New Roman\"color=\"red\">Java Swing Tool Tip Text</font></h1><br>You can use html attributes to <b>decorate</b> <i>Swing</i> <u>Component</u>.<br>Different <font color=\"yellow\">styles</font> and <font color=\"green\">formats</font> are supported,<br>including <sup>superscript</sup> and <sub>subscript</sub> like <font size=\"4\">log<sub>2</sub>5<sup>8</sup></font>.</html>");
		return label;
	}
}

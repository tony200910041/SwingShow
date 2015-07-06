/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ColorChooserDemo extends Tab
{
	ColorChooserDemo()
	{
		super();
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder("JColorChooser"));
		this.add(new JColorChooser(Color.BLACK));
	}
}

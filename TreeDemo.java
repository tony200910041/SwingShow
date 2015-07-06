/** This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.imageio.*;
import javax.sound.sampled.*;
import java.nio.charset.*;
import java.util.*;

public class TreeDemo extends Tab
{
	TreeDemo()
	{
		super();
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(TreeDemo.createTree()));
	}
	
	static JTree createTree()
	{
		/*
		 * now create JTree structure
		 */
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		JTree tree = new JTree(root);
		/*
		 * first node: charset
		 */
		DefaultMutableTreeNode charset = new DefaultMutableTreeNode("Charset");
		DefaultMutableTreeNode registered = new DefaultMutableTreeNode("Registered in IANA");
		DefaultMutableTreeNode notRegistered = new DefaultMutableTreeNode("Not registered");
		//
		for (Charset c: Charset.availableCharsets().values())
		{
			String name = c.displayName();
			if (c.isRegistered())
			{
				registered.add(new DefaultMutableTreeNode(name));
			}
			else
			{
				notRegistered.add(new DefaultMutableTreeNode(name));
			}
		}
		root.add(charset);
		charset.add(registered);
		charset.add(notRegistered);
		/*
		 * second node: primitive type
		 */
		DefaultMutableTreeNode primitive = new DefaultMutableTreeNode("Primitive type");
		DefaultMutableTreeNode _boolean = new DefaultMutableTreeNode("boolean");
		DefaultMutableTreeNode _byte = new DefaultMutableTreeNode("byte");
		DefaultMutableTreeNode _short = new DefaultMutableTreeNode("short");
		DefaultMutableTreeNode _char = new DefaultMutableTreeNode("char");
		DefaultMutableTreeNode _int = new DefaultMutableTreeNode("int");
		DefaultMutableTreeNode _long = new DefaultMutableTreeNode("long");
		DefaultMutableTreeNode _float = new DefaultMutableTreeNode("float");
		DefaultMutableTreeNode _double = new DefaultMutableTreeNode("double");
		root.add(primitive);
		primitive.add(_boolean);
		primitive.add(_byte);
		primitive.add(_short);
		primitive.add(_char);
		primitive.add(_int);
		primitive.add(_long);
		primitive.add(_float);
		primitive.add(_double);
		/*
		 * third node: collection
		 */
		DefaultMutableTreeNode collection = new DefaultMutableTreeNode("Collection (Some only)");
		DefaultMutableTreeNode list = new DefaultMutableTreeNode("List");
		DefaultMutableTreeNode set = new DefaultMutableTreeNode("Set");
		DefaultMutableTreeNode map = new DefaultMutableTreeNode("Map");
		DefaultMutableTreeNode old = new DefaultMutableTreeNode("Old API (not in framework)");
		//
		DefaultMutableTreeNode arrayList = new DefaultMutableTreeNode("ArrayList");
		DefaultMutableTreeNode linkedList = new DefaultMutableTreeNode("LinkedList");
		DefaultMutableTreeNode vector = new DefaultMutableTreeNode("Vector (old)");
		DefaultMutableTreeNode hashSet = new DefaultMutableTreeNode("HashSet");
		DefaultMutableTreeNode linkedHashSet = new DefaultMutableTreeNode("LinkedHashSet");
		DefaultMutableTreeNode enumSet = new DefaultMutableTreeNode("EnumSet");
		DefaultMutableTreeNode treeSet = new DefaultMutableTreeNode("TreeSet");
		DefaultMutableTreeNode hashMap = new DefaultMutableTreeNode("HashMap");
		DefaultMutableTreeNode treeMap = new DefaultMutableTreeNode("TreeMap");
		DefaultMutableTreeNode properties = new DefaultMutableTreeNode("Properties (old)");
		DefaultMutableTreeNode enumeration = new DefaultMutableTreeNode("Enumeration");
		//
		root.add(collection);
		collection.add(list);
		collection.add(set);
		collection.add(map);
		collection.add(old);
		list.add(arrayList);
		list.add(linkedList);
		list.add(vector);
		set.add(hashSet);
		set.add(linkedHashSet);
		set.add(enumSet);
		set.add(treeSet);
		map.add(hashMap);
		map.add(treeMap);
		map.add(properties);
		old.add(enumeration);
		/*
		 * fourth node: io
		 */
		DefaultMutableTreeNode io = new DefaultMutableTreeNode("I/O");
		DefaultMutableTreeNode inputStream = new DefaultMutableTreeNode("InputStream");
		DefaultMutableTreeNode outputStream = new DefaultMutableTreeNode("OutputStream");
		DefaultMutableTreeNode reader = new DefaultMutableTreeNode("Reader");
		DefaultMutableTreeNode writer = new DefaultMutableTreeNode("Writer");
		DefaultMutableTreeNode audioInputStream = new DefaultMutableTreeNode("AudioInputStream");
		DefaultMutableTreeNode byteArrayInputStream = new DefaultMutableTreeNode("ByteArrayInputStream");
		DefaultMutableTreeNode fileInputStream = new DefaultMutableTreeNode("FileInputStream");
		DefaultMutableTreeNode byteArrayOutputStream = new DefaultMutableTreeNode("ByteArrayOutputStream");
		DefaultMutableTreeNode fileOutputStream = new DefaultMutableTreeNode("FileOutputStream");
		DefaultMutableTreeNode objectOutputStream = new DefaultMutableTreeNode("ObjectOutputStream");
		DefaultMutableTreeNode bufferedReader = new DefaultMutableTreeNode("BufferedReader");
		DefaultMutableTreeNode fileReader = new DefaultMutableTreeNode("FileReader");
		DefaultMutableTreeNode inputStreamReader = new DefaultMutableTreeNode("InputStreamReader");
		DefaultMutableTreeNode bufferedWriter = new DefaultMutableTreeNode("BufferedWriter");
		DefaultMutableTreeNode fileWriter = new DefaultMutableTreeNode("FileWriter");
		DefaultMutableTreeNode printWriter = new DefaultMutableTreeNode("PrintWriter");
		root.add(io);
		io.add(inputStream);
		io.add(outputStream);
		io.add(reader);
		io.add(writer);
		inputStream.add(audioInputStream);
		inputStream.add(byteArrayInputStream);
		inputStream.add(fileInputStream);
		outputStream.add(byteArrayOutputStream);
		outputStream.add(fileOutputStream);
		outputStream.add(objectOutputStream);
		reader.add(bufferedReader);
		reader.add(fileReader);
		reader.add(inputStreamReader);
		writer.add(bufferedWriter);
		writer.add(fileWriter);
		writer.add(printWriter);
		/*
		 * fifth node: image format
		 */
		DefaultMutableTreeNode imageFormat = new DefaultMutableTreeNode("Image format");
		TreeSet<String> nameSet = new TreeSet<>();
		String names[] = ImageIO.getReaderFormatNames();
		for (String name: names)
		{
			nameSet.add(name.toLowerCase());
		}
		for (String name: nameSet)
		{
			imageFormat.add(new DefaultMutableTreeNode(name));
		}
		root.add(imageFormat);
		/* 
		 * sixth node: audio format
		 */
		DefaultMutableTreeNode audioFormat = new DefaultMutableTreeNode("Audio format");
		TreeSet<String> audioSet = new TreeSet<>();
		AudioFileFormat.Type audioTypes[] = AudioSystem.getAudioFileTypes();
		for (AudioFileFormat.Type type: audioTypes)
		{
			audioSet.add(type.getExtension().toLowerCase());
		}
		for (String type: audioSet)
		{
			audioFormat.add(new DefaultMutableTreeNode(type));
		}
		root.add(audioFormat);
		/*
		 * end
		 */
		TreePath path = new TreePath(new DefaultMutableTreeNode[]{root,charset});
		tree.scrollPathToVisible(path);
		return tree;
	}
}

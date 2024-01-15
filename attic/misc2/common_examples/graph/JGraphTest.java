package graph;
/*
=====================================================================

	JGraphTest.java
	
	Created by Claude Duguay
	Copyright (c) 1999
	
=====================================================================
*/

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import common.ui.graph.*;

public class JGraphTest extends JFrame
	implements ActionListener, ItemListener
{
	protected JGraph graph;
	protected JButton scramble, shake;
	protected JCheckBox stress, random, organize;
	
	protected GraphAdjuster shakeAdjuster, scrambleAdjuster;
	protected AutomaticAdjuster organizer, randomizer;

	public JGraphTest()
	{
		graph = new JGraph();
 		shakeAdjuster = new ShakeAdjuster(graph);
 		scrambleAdjuster = new ScrambleAdjuster(graph);
		
		getContentPane().setLayout(new BorderLayout());
		
		Panel controlPanel = new Panel();
		getContentPane().add("Center", graph);
		getContentPane().add("South", controlPanel);

		controlPanel.add(scramble = new JButton("Scramble"));
		scramble.addActionListener(this);
		controlPanel.add(shake = new JButton("Shake"));
		shake.addActionListener(this);
		controlPanel.add(stress = new JCheckBox("Stress"));
		stress.addItemListener(this);
		controlPanel.add(random = new JCheckBox("Random"));
		random.addItemListener(this);
		controlPanel.add(organize = new JCheckBox("Organize"));
		organize.addItemListener(this);

		addEdge("zero", "one", 50);
		addEdge("one", "two", 50);
		addEdge("two", "three", 50);
		addEdge("three", "four", 50);
		addEdge("four", "five", 50);
		addEdge("five", "six", 50);
		addEdge("six", "seven", 50);
		addEdge("seven", "eight", 50);
		addEdge("eight", "nine", 50);
		addEdge("nine", "ten", 50);
		addEdge("ten", "zero", 50);

		Dimension d = getSize();
		String center = null;
		if (center != null)
		{
	    GraphNode node = findNode(center);
	    node.setX(d.width / 2);
	    node.setY(d.height / 2);
	    node.setFixed(true);
		}
  }

	public GraphNode addNode(String name)
	{
		GraphNode node = new OrganizeGraphNode(name);
		graph.getModel().addNode(node);
		return node;
  }
  
	public GraphNode findNode(String name)
	{
		GraphModel model = graph.getModel();
		for (int i = 0; i < model.getNodeCount(); i++)
		{
	    if (model.getNode(i).getName().equals(name))
			{
				return model.getNode(i);
	    }
		}
		return addNode(name);
  }
  
	public void addEdge(String from, String to, int len)
	{
		GraphEdge edge = new DefaultGraphEdge(
			findNode(from), findNode(to), len);
		graph.getModel().addEdge(edge);
  }

  public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		if (source == scramble)
	    scrambleAdjuster.adjust();
		if (source == shake)
	    shakeAdjuster.adjust();
  }

  public void itemStateChanged(ItemEvent event)
	{
		Object source = event.getSource();
		boolean on = event.getStateChange() == ItemEvent.SELECTED;
		if (source == stress)
		{
			graph.showStress(on);			
		}
		if (source == organize)
		{
	 		if (organizer != null)
				organizer.quit();
			if (on) organizer =
				new AutomaticAdjuster(new OrganizeAdjuster(graph));
		}
		if (source == random)
		{
	 		if (randomizer != null)
				randomizer.quit();
			if (on) randomizer =
				new AutomaticAdjuster(new RandomAdjuster(graph));
		}
  }
	
	public static void main(String[] args)
	{
		JGraphTest frame = new JGraphTest();
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(3);
		frame.show();
	}
}


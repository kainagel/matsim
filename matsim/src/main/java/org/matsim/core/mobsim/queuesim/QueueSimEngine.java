/* *********************************************************************** *
 * project: org.matsim.*
 * QueueSimEngine.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */

package org.matsim.core.mobsim.queuesim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.matsim.core.config.Config;

/**
 * Coordinates the movement of vehicles on the links and the nodes.
 *
 * @author mrieser
 * @author dgrether
 * @author dstrippgen
 */
/*package*/ class QueueSimEngine {
	private QueueNetwork qNetwork = null ;

	private final List<QueueLink> allLinks;
	/** This is the collection of links that have to be moved in the simulation */
	private final List<QueueLink> simLinksArray = new ArrayList<QueueLink>();
	/** This is the collection of nodes that have to be moved in the simulation */
	private final QueueNode[] simNodesArray;
	/** This is the collection of links that have to be activated in the current time step */
	private final ArrayList<QueueLink> simActivateThis = new ArrayList<QueueLink>();

	private final ArrayList<QueueVehicle> departingVehicles = new ArrayList<QueueVehicle>();

	private final Random random;

	private final Config config;

	/*package*/ QueueSimEngine(final QueueNetwork network, final Random random, final Config config) {
		this(network.getQueueLinks().values(), network.getQueueNodes().values(), random, config);
		this.qNetwork = network ; // simEngine cannot exist without qNetwork, and so it makes sense to keep the back ptr here.
	}

	private QueueSimEngine(final Collection<QueueLink> links, final Collection<QueueNode> nodes, final Random random, final Config config) {
		this.random = random;
		this.allLinks = new ArrayList<QueueLink>(links);
		this.config = config;

		this.simNodesArray = nodes.toArray(new QueueNode[nodes.size()]);
		//dg[april08] as the order of nodes has an influence on the simulation
		//results they are sorted to avoid indeterministic simulations
		Arrays.sort(this.simNodesArray, new Comparator<QueueNode>() {
			@Override
			public int compare(final QueueNode o1, final QueueNode o2) {
				return o1.getNode().getId().compareTo(o2.getNode().getId());
			}
		});
		for (QueueLink link : this.allLinks) {
			link.setSimEngine(this);
		}
	}

	protected void afterSim() {
		/* Reset vehicles on ALL links. We cannot iterate only over the active links
		 * (this.simLinksArray), because there may be links that have vehicles only
		 * in the buffer (such links are *not* active, as the buffer gets emptied
		 * when handling the nodes.
		 */
		for (QueueLink link : this.allLinks) {
			link.clearVehicles();
		}
	}

	/**
	 * Implements one simulation step, called from simulation framework
	 * @param time The current time in the simulation.
	 * @return 
	 */
	protected Collection<QueueVehicle> simStep(final double time) {
		moveNodes(time);
		moveLinks(time);
		return departingVehicles;
	}

	protected void moveNodes(final double time) {
		for (QueueNode node : this.simNodesArray) {
			if (node.isActive()) {
				/* It is faster to first test if the node is active, and only then call moveNode(),
				 * than calling moveNode() directly and that one returns immediately when it's not
				 * active. Most likely, the getter isActive() can be in-lined by the compiler, while
				 * moveNode() cannot, resulting in fewer method-calls when isActive() is used.
				 * -marcel/20aug2008
				 */
				node.moveNode(time, random);
			}
		}
	}

	protected void moveLinks(final double time) {
		reactivateLinks();
		departingVehicles.clear();
		ListIterator<QueueLink> simLinks = this.simLinksArray.listIterator();
		while (simLinks.hasNext()) {
			QueueLink link = simLinks.next();
			ArrayList<QueueVehicle> departingVehiclesFromLink = link.moveLink(time);
			departingVehicles.addAll(departingVehiclesFromLink);
			if (!link.isActive()) {
				simLinks.remove();
			}
		}
	}

	protected void activateLink(final QueueLink link) {
		this.simActivateThis.add(link);
	}

	private void reactivateLinks() {
		this.simLinksArray.addAll(this.simActivateThis);
		this.simActivateThis.clear();
	}

	/**
	 * @return Returns the simLinksArray.
	 */
	protected int getNumberOfSimulatedLinks() {
		return this.simLinksArray.size();
	}

	/*package*/ Config getConfig() {
		return config;
	}

	/*package*/ QueueNetwork getQNetwork() {
		return qNetwork;
	}
}

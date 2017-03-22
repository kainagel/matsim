/* *********************************************************************** *
 * project: org.matsim.*
 * CompletedVehicleList.java
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

/**
 * 
 */
package playground.southafrica.freight.digicore.algorithms.djcluster.containers;

import java.io.Serializable;
import java.util.ArrayList;

import org.matsim.api.core.v01.Id;
import org.matsim.vehicles.Vehicle;

/**
 * Simple container to keep the {@link Vehicle} {@link Id}s of those vehicles
 * that has already been processed as part of building a point list, prior to
 * clustering. The main objective of having this dedicated container is so that
 * it can be serialised and written to file.
 *  
 * @author jwjoubert
 */
public class CompletedVehicleList extends ArrayList<Id<Vehicle>> implements Serializable {

	private static final long serialVersionUID = 1L;

}
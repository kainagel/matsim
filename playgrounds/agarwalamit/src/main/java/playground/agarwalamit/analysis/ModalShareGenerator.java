/* *********************************************************************** *
 * project: org.matsim.*
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2014 by the members listed in the COPYING,        *
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
package playground.agarwalamit.analysis;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.population.PlanImpl;

import playground.benjamin.utils.BkNumberUtils;

/**
 * Generate Modal Share from population file and events file.
 * 
 * @author amit
 */
public class ModalShareGenerator {
	
	private final Logger logger = Logger.getLogger(ModalShareGenerator.class);
	
	public void getModalShareFromEvents(){
		// TODO [AA] should get modal share from events as well.
	}
	
	public SortedMap<String, double []> getModalShareFromPlans (Population population){
		SortedMap<String , double[]> mode2NoOfLegsAndPctShare = new TreeMap<String, double[]>();
		this.logger.info("=====Modal split is calculated using input plans file.=====");
		SortedSet<String> usedModes = getUsedModes(population);
		getMode2NoOfLegs(population, usedModes);
		this.logger.info("=====The following transport modes are are used: " + usedModes+".=====");
		Map<String, Integer> mode2NoOfLegs = getMode2NoOfLegs(population, usedModes);
		int totalNoOfLegs = getTotalNoOfLegs(mode2NoOfLegs);
		
		for(String mode : mode2NoOfLegs.keySet()){
			double noOfLegs = (double) mode2NoOfLegs.get(mode);
			double noOfLegPct = BkNumberUtils.roundDouble(100. * ((double) noOfLegs / (double) totalNoOfLegs), 3);
			double [] numberOfLegs2PctShare  = {noOfLegs, noOfLegPct};
			mode2NoOfLegsAndPctShare.put(mode, numberOfLegs2PctShare);
		}
		return mode2NoOfLegsAndPctShare;
	}
	
	private int getTotalNoOfLegs(Map<String, Integer> mode2NoOfLegs) {
		int totalNoOfLegs = 0;
		for(String mode : mode2NoOfLegs.keySet()){
			int noOfLegs = mode2NoOfLegs.get(mode);
			totalNoOfLegs += noOfLegs;
		}
		return totalNoOfLegs;
	}

	private Map<String, Integer> getMode2NoOfLegs(Population pop, SortedSet<String> usedModes) {
		SortedMap<String, Integer> mode2noOfLegs = new TreeMap<String, Integer>();
		
		for(String mode : usedModes){
			int noOfLegs = 0;
			for(Person person : pop.getPersons().values()){
				PlanImpl plan = (PlanImpl) person.getSelectedPlan();
				List<PlanElement> planElements = plan.getPlanElements();
				for(PlanElement pe : planElements){
					if(pe instanceof Leg){
						Leg leg = (Leg) pe;
						String legMode = leg.getMode();
						if(legMode.equals(mode)){
							noOfLegs ++;
						}

					}
				}
			}
			mode2noOfLegs.put(mode, noOfLegs);
		}
		return mode2noOfLegs;
	}

	private SortedSet<String> getUsedModes(Population pop) {
		SortedSet<String> usedModes = new TreeSet<String>();
		for(Person person : pop.getPersons().values()){
			PlanImpl plan = (PlanImpl) person.getSelectedPlan();
			List<PlanElement> planElements = plan.getPlanElements();
			for(PlanElement pe : planElements){
				if(pe instanceof Leg){
					Leg leg = (Leg) pe;
					String legMode = leg.getMode();
					if(!usedModes.contains(legMode)){
						usedModes.add(legMode);
					}
				}
			}
		}
		return usedModes;
	}
}

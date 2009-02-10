package org.matsim.mobsim.deqsim;

import java.util.ArrayList;

import org.matsim.mobsim.deqsim.EventLog;
import org.matsim.mobsim.deqsim.util.CppEventFileParser;
import org.matsim.testcases.MatsimTestCase;


public class TestEventLog extends MatsimTestCase {

	
	
	public void testGetTravelTime(){
		ArrayList<EventLog> deqSimLog=CppEventFileParser.parseFile("test/input/org/matsim/mobsim/deqsim/deq_events.txt");
		assertEquals(3599.0, Math.floor(EventLog.getTravelTime(deqSimLog,1)));
	}
	
	
	public void testGetAverageTravelTime(){
		ArrayList<EventLog> deqSimLog=CppEventFileParser.parseFile("test/input/org/matsim/mobsim/deqsim/deq_events.txt");
		assertEquals(EventLog.getTravelTime(deqSimLog,1), EventLog.getSumTravelTime(deqSimLog));
	}
}

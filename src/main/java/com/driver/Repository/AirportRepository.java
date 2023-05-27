package com.driver.Repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    HashMap<String, Airport> airportData=new HashMap<>();
    HashMap<Integer, Flight> flightData=new HashMap<>();
    HashMap<Integer, Passenger> passengerData=new HashMap<>();

    HashMap<Integer,ArrayList<Integer>> bookTicket=new HashMap<>(); //flightId, list of Passengers

    public void addAirport(Airport airport)
    {
        airportData.put(airport.getAirportName(),airport);
    }
    public String getLargestAirportName()
    {
        ArrayList<Airport> list=new ArrayList<Airport>(airportData.values());
        Collections.sort(list,new Comparator<Airport>()
        {
            public int compare(Airport a1,Airport a2)
            {
                int flag=a2.getNoOfTerminals()-a1.getNoOfTerminals();
                if(flag==0) return a1.getAirportName().compareTo(a2.getAirportName());
                return flag;
            }
        });
        return list.get(0).getAirportName();
    }
    public void addPassenger(Passenger passenger)
    {
        passengerData.put(passenger.getPassengerId(),passenger);
    }

    public void addFlight(Flight flight)
    {
        flightData.put(flight.getFlightId(),flight);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City c1, City c2)
    {
        ArrayList<Flight> list=new ArrayList<>();
        for(Flight flight: flightData.values())
        {
            if(flight.getFromCity().equals(c1) && flight.getToCity().equals(c2)) list.add(flight);
        }
        if(list.size()==0) return -1;
        else if(list.size()==1) return list.get(0).getDuration();
        double min=Double.MAX_VALUE;
        for(Flight flight: list)
        {
            min=Math.min(flight.getDuration(),min);
        }
        return min;
    }
    public String bookATicket(int flightId,int passengerId)
    {
         if(!flightData.containsKey(flightId) || !passengerData.containsKey(passengerId)) return "FAILURE";
         else if(flightData.get(flightId).getMaxCapacity()>=bookTicket.get(flightId).size()) return "FAILURE";
         else if(bookTicket.get(flightId).contains(passengerId)) return"FAILURE";
         if(bookTicket.containsKey(flightId)) {
             ArrayList<Integer> list = bookTicket.get(flightId);
             list.add(passengerId);
             bookTicket.put(flightId, list);
         }
         else
         {
             bookTicket.put(flightId,new ArrayList<>(passengerId));
         }
      return  "SUCCESS";
    }

    public String cancelATicket(int flightId,int passengerId)
    {
        if(!flightData.containsKey(flightId) || !passengerData.containsKey(passengerId)) return "FAILURE";
        else if(!bookTicket.get(flightId).contains(passengerId)) return "FAILURE";

        bookTicket.get(flightId).remove(passengerId);
        return "SUCCESS";
    }

    public int countOfBookingsDoneByPassengerAllCombined(int passengerId)
    {
        int count=0;
        for(int i:bookTicket.keySet())
        {
            if(bookTicket.get(i).contains(passengerId)) count++;
        }
        return count;
    }

    public String getAirportNameFromFlightId(int flightId)
    {
        if(!flightData.containsKey(flightId)) return null;
        City c1=flightData.get(flightId).getFromCity();
        for(Airport airport:airportData.values())
        {
            if(airport.getCity().equals(c1)) return airport.getAirportName();
        }

        return null;
    }
    public int calculateFlightFare(int flightId)
    {
        return 3000+bookTicket.get(flightId).size()*50;
    }

    public int getNumberOfPeopleOn(Date date,String airportName)
    {
          int count=0;
          for(Flight flight: flightData.values())
          {
              if(flight.getFlightDate().equals(date))
              {
                  if(flight.getToCity().equals(airportData.get(airportName).getCity()) ||
                       flight.getFromCity().equals(airportData.get(airportName).getCity()))
                  {
                      count+=bookTicket.get(flight.getFlightId()).size();
                  }
              }
          }
          return count;
    }
    public int getCurrFlightSize(int flightId)
    {
        if(bookTicket.containsKey(flightId)) return bookTicket.get(flightId).size();
        else return 0;
    }
}

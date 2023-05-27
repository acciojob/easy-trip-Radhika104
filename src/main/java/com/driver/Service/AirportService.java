package com.driver.Service;

import com.driver.Repository.AirportRepository;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {

    private AirportRepository airportRepository=new AirportRepository();

    public void addAirport(Airport airport)
    {
        airportRepository.addAirport(airport);
    }

    public String getLargestAirportName()
    {
        return airportRepository.getLargestAirportName();
    }

    public void addPassenger(Passenger passenger)
    {
        airportRepository.addPassenger(passenger);
    }

    public void addFlight(Flight flight)
    {
        airportRepository.addFlight(flight);
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City c1,City c2)
    {
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(c1,c2);
    }
    public String bookATicket(int flightId,int passengerId)
    {
        return airportRepository.bookATicket(flightId,passengerId);
    }
    public String cancelATicket(int flightId,int passengerId)
    {
        return airportRepository.cancelATicket(flightId,passengerId);
    }
    public int countOfBookingsDoneByPassengerAllCombined(int passengerId)
    {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }
    public String getAirportNameFromFlightId(int flightId)
    {
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int calculateFlightFare(int flightId)
    {
        return airportRepository.calculateFlightFare(flightId);
    }

    public int getNumberOfPeopleOn(Date date,String airportName)
    {
        return getNumberOfPeopleOn(date,airportName);
    }

    public int calculateRevenueOfAFlight(int flightId)
    {
        int curr_flight_size = airportRepository.getCurrFlightSize(flightId);
        int total3000 = curr_flight_size * 3000;
        int addtional50 = 0;
        for(int i=0; i<curr_flight_size-1; i++){
            addtional50 += 50 + addtional50;
        }
        int totalrevenue = total3000 + addtional50;
        return  totalrevenue;
    }
}

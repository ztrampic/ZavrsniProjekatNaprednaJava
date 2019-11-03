package com.comtrade.airport.service;

import com.comtrade.airport.entity.Flight;
import com.comtrade.airport.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService{

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final FlightScheduleRepository flightScheduleRepository;
    private final AirCompanyRepository airCompanyRepository;
    private final AirplaneRepository airplaneRepository;
    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, AirportRepository airportRepository, FlightScheduleRepository flightScheduleRepository, AirCompanyRepository airCompanyRepository, AirplaneRepository airplaneRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.flightScheduleRepository = flightScheduleRepository;
        this.airCompanyRepository = airCompanyRepository;
        this.airplaneRepository = airplaneRepository;
    }

    @Override
    @Transactional
    public boolean checkDepartureTermin(Long id, String date) throws ParseException {
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
        Integer maxDepartures = airportRepository.getMaxDepartures(id);
        Integer numberOfFlightsForDate = flightRepository.getNumberOfFlightsThatDay(date1,id);
        if(maxDepartures-numberOfFlightsForDate > 0){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Flight> getAllDepartureFlightsForDate(Long id, String date) throws ParseException {
        LocalDate convertedDate = LocalDate.parse(date);
        List<Flight> flights = flightRepository.getAllDepartureFlightsForDate(convertedDate,id);

        return flights;
    }

    @Override
    @Transactional
    public Flight insertNewFlight(Flight flight) {
        Flight flightWithId = flightRepository.save(flight);
        return flightWithId;
    }

    @Override
    @Transactional
    public Flight getFlightById(Long id) {
        Flight flight = flightRepository.getFlightById(id);
        return flight;
    }
}

package com.modesteam.pardal.models;

import com.modesteam.pardal.Pardal;

import junit.framework.TestCase;

import java.sql.SQLException;

import helpers.Condition;
import helpers.Operator;
import libraries.NotNullableException;
import models.HighwayStretch;
import models.Model;
import models.Tickets;

public class TicketsTest extends TestCase{
    Tickets tickets1, tickets2;
    Model model1;
    HighwayStretch highwayStretch1;
    public void setUp() throws SQLException, NotNullableException, ClassNotFoundException {
        //(int year, int semester, int totalTickets,Double velocityLimit, Double averageExceded,
        // Double maximumMeasuredVelocity, int idHighwayStretch, int idModel)
        Pardal.getInstance().setDatabaseName("database_test.sqlite3.db");
        for (Model model: Model.getAll()) {
            model.delete();
        }
        for (HighwayStretch highwayStretch: HighwayStretch.getAll()){
            highwayStretch.delete();
        }
        model1 = new Model("model1",true,1,1);
        highwayStretch1 = new HighwayStretch("040", 10, 1);
        model1.save();
        highwayStretch1.save();
        tickets1 = new Tickets(2011,1,2,Double.parseDouble("80"),Double.parseDouble("8"),Double.parseDouble("88"),highwayStretch1.getId(),model1.getId());
        tickets2 = new Tickets(2012,2,2,Double.parseDouble("60"),Double.parseDouble("8"),Double.parseDouble("68"),1,1);
        tickets1.save();
        tickets2.save();

    }
    public void tearDown() throws SQLException, ClassNotFoundException {
        tickets1.delete();
        tickets2.delete();
        model1.delete();
        highwayStretch1.delete();
    }
    public void testShouldGetFirstticketsFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(tickets1.getId(), Tickets.first().getId());
        assertEquals(tickets1.getTotalTickets(), Tickets.first().getTotalTickets());
        assertEquals(tickets1.getVelocityLimit(),Tickets.first().getVelocityLimit());
        assertEquals(tickets1.getMaximumMeasuredVelocity(), Tickets.first().getMaximumMeasuredVelocity());
        assertEquals(tickets1.getAverageExceded(), Tickets.first().getAverageExceded());
        assertEquals(tickets1.getIdHighwayStretch(), Tickets.first().getIdHighwayStretch());
        assertEquals(tickets1.getIdModel(), Tickets.first().getIdModel());
    }

    public void testShouldGetLastticketsFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(tickets2.getId(), Tickets.last().getId());
        assertEquals(tickets2.getTotalTickets(), Tickets.last().getTotalTickets());
        assertEquals(tickets2.getVelocityLimit(), Tickets.last().getVelocityLimit());
        assertEquals(tickets2.getMaximumMeasuredVelocity(), Tickets.last().getMaximumMeasuredVelocity());
        assertEquals(tickets2.getAverageExceded(), Tickets.last().getAverageExceded());
        assertEquals(tickets2.getIdHighwayStretch(), Tickets.last().getIdHighwayStretch());
        assertEquals(tickets2.getIdModel(), Tickets.last().getIdModel());
    }

    public void testShouldCountticketsFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, Tickets.count());
    }

    public void testShouldGetAllticketsFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, Tickets.getAll().size());
    }

    public void testShouldSaveticketsFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(2, Tickets.getAll().size());
        Tickets tickets3 = new Tickets(2013,3,3,Double.parseDouble("100"),Double.parseDouble("8"),Double.parseDouble("108"),3,3);
        tickets3.save();
        assertEquals(3, Tickets.getAll().size());
        tickets3.delete();
    }

    public void testShouldDeleteticketsFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Tickets tickets3 = new Tickets(2013,3,3,Double.parseDouble("100"),Double.parseDouble("8"),Double.parseDouble("108"),3,3);
        tickets3.save();
        assertEquals(3, Tickets.getAll().size());
        tickets3.delete();
        assertEquals(2, Tickets.getAll().size());
    }

    public void testShouldGetTicketFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(tickets2.getId(), Tickets.get(Tickets.last().getId()).getId());
    }

    public void testShouldGetWhereTicketFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Condition condition = new Condition(new Tickets(),"velocityLimit", Operator.EQUAL,Double.parseDouble("80"));
        assertEquals(tickets1.getVelocityLimit(), Tickets.getWhere(condition).get(0).getVelocityLimit());
    }

    public void testShouldGetModelOfTicketFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(model1.getName(), Tickets.first().getModel().getName());
    }
    public void testShouldGetHighwayStretchOfTicketFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(highwayStretch1.getNumber(), Tickets.first().getHighwayStretch().getNumber());
    }

    public void testShouldInstantiateTicketsId() throws SQLException, ClassNotFoundException, NotNullableException {
        Tickets tickets3 = new Tickets(5);
        tickets3.setAverageExceded(Double.parseDouble("20"));
        tickets3.setIdHighwayStretch(5);
        tickets3.setIdModel(5);
        tickets3.setMaximumMeasuredVelocity(Double.parseDouble("200"));
        tickets3.setSemester(2);
        tickets3.setTotalTickets(1);
        tickets3.setVelocityLimit(Double.parseDouble("60"));
        tickets3.setYear(2011);
        tickets3.save();
        Condition condition = new Condition(new Tickets(),"maximumMeasuredVelocity", Operator.EQUAL,Double.parseDouble("200"));
        assertEquals(tickets3.getMaximumMeasuredVelocity(), Tickets.getWhere(condition).get(0).getMaximumMeasuredVelocity());
        tickets3.delete();
    }

    public void testToStringTicket() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(tickets1.toString(), Tickets.first().toString());
    }

}

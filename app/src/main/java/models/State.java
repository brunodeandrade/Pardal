package models;

import com.modesteam.pardal.ComparableCategory;

import java.sql.SQLException;
import java.util.ArrayList;

import annotations.OrderBy;
import helpers.Category;
import libraries.NotNullableException;
import helpers.Condition;
import helpers.GenericPersistence;
import annotations.Column;
import annotations.Entity;
import annotations.HasMany;
import annotations.ManyRelations;

@Entity(table="state", primaryKey="id")
@ManyRelations({
	@HasMany(entity=City.class, foreignKey="idState")
})
@OrderBy(field = "name")
public class State implements ComparableCategory {

	@Column(name="_id", nullable=false)
	private int id;
	private String name;

    @Column(name="total_tickets", nullable=true)
    private int totalTickets;
    @Column(name="average_exceded", nullable=true)
    private Double averageExceded;
    @Column(name="maximum_measured_velocity", nullable=true)
    private Double maximumMeasuredVelocity;
	
	public State() {
		super();
	}
	
	public State(int id) {
		super();
		this.id = id;
	}
	
	public State(String name) {
		super();
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    public int getTotalTickets() {
        return totalTickets;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }
    public Double getAverageExceded() {
        return averageExceded;
    }
    public void setAverageExceded(Double averageExceded) {
        this.averageExceded = averageExceded;
    }
    public Double getMaximumMeasuredVelocity() {
        return maximumMeasuredVelocity;
    }
    public void setMaximumMeasuredVelocity(Double maximumMeasuredVelocity) {
        this.maximumMeasuredVelocity = maximumMeasuredVelocity;
    }
	
	public boolean save() throws NotNullableException{
		GenericPersistence gP = new GenericPersistence();
		boolean result = gP.insertBean(this);
		this.setId(State.last().getId());
		return result;
	}
	
	public static State get(int id) {
		GenericPersistence gP = new GenericPersistence();
		return (State) gP.selectBean(new State(id));
	}
	
	public static ArrayList<State> getAll() {
		GenericPersistence gP = new GenericPersistence();
		ArrayList<State> states = new ArrayList<State>();
		for (Object bean : gP.selectAllBeans(new State())) {
			states.add((State)bean);
		}
		return states;
	}
	
	public static int count()  {
		GenericPersistence gDB = new GenericPersistence();
		return gDB.countBean(new State());
	}
	
	public static State first() {
		GenericPersistence gP = new GenericPersistence();
		return (State) gP.firstOrLastBean(new State() , false);
	}
	
	public static State last() {
		GenericPersistence gP = new GenericPersistence();
		return (State) gP.firstOrLastBean(new State() , true);
	}
	
	public static ArrayList<State> getWhere(Condition condition) {
		GenericPersistence gP = new GenericPersistence();
		ArrayList<State> states = new ArrayList<State>();
		for (Object bean : gP.selectWhere(new State(), condition)) {
			states.add((State)bean);
		}
		return states;
	}
	
	public boolean delete()  {
		GenericPersistence gP = new GenericPersistence();
		return gP.deleteBean(this);
	}
	
	public ArrayList<City> getCities() {
		GenericPersistence gP = new GenericPersistence();
		ArrayList<City> beans = new ArrayList<City>();
		for (Object bean : gP.selectMany(this, new City())) {
			beans.add((City)bean);
		}
		return beans;
	}

	@Override
	public Category getCategory() {
		return Category.STATE;
	}

	@Override
	public String toString() {
		return "" + name;
	}
}

package models;

import com.modesteam.pardal.ComparableCategory;

import annotations.OrderBy;
import helpers.Condition;
import helpers.GenericPersistence;

import java.sql.SQLException;
import java.util.ArrayList;

import libraries.NotNullableException;
import annotations.Column;
import annotations.Entity;
import annotations.HasMany;
import annotations.HasOne;
import annotations.ManyRelations;
import annotations.OneRelations;

@Entity(table="city", primaryKey="id")
@OneRelations({@HasOne(entity=State.class, reference="idState", belongs=true)})
@ManyRelations({@HasMany(entity=HighwayStretch.class, foreignKey="idCity")})
@OrderBy(field = "name")
public class City implements ComparableCategory {

	@Column(name="_id", nullable=false)
	private int id;
	
	private String code;
	private String name;

    @Column(name="total_tickets", nullable=true)
    private int totalTickets;
    @Column(name="average_exceded", nullable=true)
    private Double averageExceded;
    @Column(name="maximum_measured_velocity", nullable=true)
    private Double maximumMeasuredVelocity;
	
	@Column(name="id_state", nullable=false)
	private int idState;
	
	public City(){
		super();
	}
	
	public City(int id) {
		this.id = id;
	}
	
	public City(String code, String name, int idState) {
		super();
		this.code = code;
		this.name = name;
		this.idState = idState;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdState() {
		return idState;
	}

	public void setIdState(int idState) {
		this.idState = idState;
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
	
	public boolean save() throws ClassNotFoundException, SQLException, NotNullableException{
		GenericPersistence gP = new GenericPersistence();
		boolean result = gP.insertBean(this);
		this.setId(City.last().getId());
		return result;
	}
	
	public static City get(int id) throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (City) gP.selectBean(new City(id));
	}
	
	public static ArrayList<City> getAll() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<City> cities = new ArrayList<City>();
		for (Object bean : gP.selectAllBeans(new City())) {
			cities.add((City)bean);
		}
		return cities;
	}
	
	public static int count() throws ClassNotFoundException, SQLException {
		GenericPersistence gDB = new GenericPersistence();
		return gDB.countBean(new City());
	}
	
	public static City first() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (City) gP.firstOrLastBean(new City() , false);
	}
	
	public static City last() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (City) gP.firstOrLastBean(new City() , true);
	}
	
	public static ArrayList<City> getWhere(Condition condition) throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<City> cities = new ArrayList<City>();
		for (Object bean : gP.selectWhere(new City(), condition)) {
			cities.add((City)bean);
		}
		return cities;
	}

	public boolean delete() throws ClassNotFoundException, SQLException {
		GenericPersistence gP = new GenericPersistence();
		return gP.deleteBean(this);
	}

	public State getState() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (State) gP.selectOne(this, new State());
	}
	
	public ArrayList<HighwayStretch> getHighwayStretches() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<HighwayStretch> stretches = new ArrayList<HighwayStretch>();
		for (Object bean : gP.selectMany(this, new HighwayStretch())) {
			stretches.add((HighwayStretch)bean);
		}
		return stretches;
	}

	@Override
	public String toString() {
		return  name;
	}
	
}

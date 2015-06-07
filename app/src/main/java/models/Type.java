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
import annotations.ManyRelations;

@Entity(table="type", primaryKey="id")
@ManyRelations({@HasMany(entity=Model.class, foreignKey="idType")})
@OrderBy(field = "name")
public class Type implements ComparableCategory {
	
	@Column(name="_id", nullable=false)
	private int id;
	private String description;
	private String name;

    @Column(name="total_tickets", nullable=true)
    private int totalTickets;
    @Column(name="average_exceded", nullable=true)
    private Double averageExceded;
    @Column(name="maximum_measured_velocity", nullable=true)
    private Double maximumMeasuredVelocity;
	
	public Type() {
		super();
	}
	
	public Type(int id) {
		super();
		this.id = id;
	}
	
	public Type(String description, String name) {
		super();
		this.description = description;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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


    public boolean save() throws ClassNotFoundException, SQLException, NotNullableException{
		GenericPersistence gP = new GenericPersistence();
		boolean result = gP.insertBean(this);
		this.setId(Type.last().getId());
		return result;
	}
	
	public static Type get(int id) throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (Type) gP.selectBean(new Type(id));
	}
	
	public static ArrayList<Type> getAll() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<Type> types = new ArrayList<Type>();
		for (Object bean : gP.selectAllBeans(new Type())) {
			types.add((Type)bean);
		}
		return types;
	}
	
	public static int count() throws ClassNotFoundException, SQLException {
		GenericPersistence gDB = new GenericPersistence();
		return gDB.countBean(new Type());
	}
	
	public static Type first() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (Type) gP.firstOrLastBean(new Type() , false);
	}
	
	public static Type last() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (Type) gP.firstOrLastBean(new Type() , true);
	}
	
	public static ArrayList<Type> getWhere(Condition condition) throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<Type> types = new ArrayList<Type>();
		for (Object bean : gP.selectWhere(new Type(), condition)) {
			types.add((Type)bean);
		}
		return types;
	}
	
	public boolean delete() throws ClassNotFoundException, SQLException {
		GenericPersistence gP = new GenericPersistence();
		return gP.deleteBean(this);
	}
	
	public ArrayList<Model> getModels() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<Model> beans = new ArrayList<Model>();
		for (Object bean : gP.selectMany(this, new Model())) {
			beans.add((Model)bean);
		}
		return beans;
	}

	@Override
	public String toString() {
        //acentos nao estao vindo
		return name;
	}
	
	
}

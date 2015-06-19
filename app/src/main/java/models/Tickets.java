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
import annotations.HasOne;
import annotations.OneRelations;

@Entity(table="tickets", primaryKey="id")
@OneRelations({
	@HasOne(entity=HighwayStretch.class, reference ="idHighwayStretch", belongs=true),
	@HasOne(entity=Model.class, reference="idModel", belongs=true)
})
public class Tickets {
	
	@Column(name="_id", nullable=false)
	private int id;
	private int year;
	private int semester;
	@Column(name="total_tickets", nullable=true)
	private int totalTickets;
	@Column(name="velocity_limit", nullable=true)
	private Double velocityLimit;
	@Column(name="average_exceded", nullable=true)
	private Double averageExceded;
	@Column(name="maximum_measured_velocity", nullable=true)
	private Double maximumMeasuredVelocity;
	@Column(name="id_highway_stretch", nullable=false)
	private int idHighwayStretch;
	@Column(name="id_model", nullable=false)
	private int idModel;
	
	public Tickets() {
		super();
	}

	public Tickets(int id) {
		super();
		this.id = id;
	}

	public Tickets(int year, int semester, int totalTickets,
			Double velocityLimit, Double averageExceded,
			Double maximumMeasuredVelocity, int idHighwayStretch, int idModel) {
		super();
		this.year = year;
		this.semester = semester;
		this.totalTickets = totalTickets;
		this.velocityLimit = velocityLimit;
		this.averageExceded = averageExceded;
		this.maximumMeasuredVelocity = maximumMeasuredVelocity;
		this.idHighwayStretch = idHighwayStretch;
		this.idModel = idModel;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getTotalTickets() {
		return totalTickets;
	}
	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}
	public Double getVelocityLimit() {
		return velocityLimit;
	}
	public void setVelocityLimit(Double velocityLimit) {
		this.velocityLimit = velocityLimit;
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
	public int getIdHighwayStretch() {
		return idHighwayStretch;
	}
	public void setIdHighwayStretch(int idHighwayStretch) {
		this.idHighwayStretch = idHighwayStretch;
	}
	public int getIdModel() {
		return idModel;
	}
	public void setIdModel(int idModel) {
		this.idModel = idModel;
	}
	
	public boolean save() throws NotNullableException{
		GenericPersistence gP = new GenericPersistence();
		boolean result = gP.insertBean(this);
		this.setId(Tickets.last().getId());
		return result;
	}
	
	public static Tickets get(int id) {
		GenericPersistence gP = new GenericPersistence();
		return (Tickets) gP.selectBean(new Tickets(id));
	}
	
	public static ArrayList<Tickets> getAll() {
		GenericPersistence gP = new GenericPersistence();
		ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		for (Object bean : gP.selectAllBeans(new Tickets())) {
			tickets.add((Tickets)bean);
		}
		return tickets;
	}
	
	public static int count()  {
		GenericPersistence gDB = new GenericPersistence();
		return gDB.countBean(new Tickets());
	}
	
	public static Tickets first() {
		GenericPersistence gP = new GenericPersistence();
		return (Tickets) gP.firstOrLastBean(new Tickets() , false);
	}
	
	public static Tickets last() {
		GenericPersistence gP = new GenericPersistence();
		return (Tickets) gP.firstOrLastBean(new Tickets() , true);
	}
	
	public static ArrayList<Tickets> getWhere(Condition condition) {
		GenericPersistence gP = new GenericPersistence();
		ArrayList<Tickets> tickets = new ArrayList<Tickets>();
		for (Object bean : gP.selectWhere(new Tickets(), condition)) {
			tickets.add((Tickets)bean);
		}
		return tickets;
	}

	public boolean delete()  {
		GenericPersistence gP = new GenericPersistence();
		return gP.deleteBean(this);
	}

	public Model getModel() {
		GenericPersistence gP = new GenericPersistence();
		return (Model) gP.selectOne(this, new Model());
	}
	
	public HighwayStretch getHighwayStretch() {
		GenericPersistence gP = new GenericPersistence();
		return (HighwayStretch) gP.selectOne(this, new HighwayStretch());
	}

	@Override
	/*public String toString() {
		return "Tickets [id=" + id + ", year=" + year + ", semester="
				+ semester + ", totalTickets=" + totalTickets
				+ ", velocityLimit=" + velocityLimit + ", averageExceded="
				+ averageExceded + ", maximumMeasuredVelocity="
				+ maximumMeasuredVelocity + ", idHighwayStretch="
				+ idHighwayStretch + ", idModel=" + idModel + "]";
	}*/
	public String toString(){
		return "Multa do Ano "+ year +", semestre "+ semester +" limite de velocidade: "+
				velocityLimit +" velocidade mensurada: "+ maximumMeasuredVelocity;
	}
}

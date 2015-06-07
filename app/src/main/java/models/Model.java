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

@Entity(table="model", primaryKey="id")
@OneRelations({
	@HasOne(entity=Brand.class, reference="idBrand", belongs=true),
	@HasOne(entity=Type.class, reference="idType", belongs=true)
})
@ManyRelations({@HasMany(entity=Tickets.class, foreignKey="idModel")})
@OrderBy(field = "name")
public class Model implements ComparableCategory{
	
	@Column(name="_id", nullable=false)
	private int id;
	private String name;
	@Column(name="is_national", nullable=true)
	private boolean isNational;
	@Column(name="id_brand", nullable=false)
	private int idBrand;
	@Column(name="id_type", nullable=false)
	private int idType;
	
	public Model() {
		super();
	}
	
	public Model(int id) {
		super();
		this.id = id;
	}
	
	public Model(String name, boolean isNational, int idBrand, int idType) {
		super();
		this.name = name;
		this.isNational = isNational;
		this.idBrand = idBrand;
		this.idType = idType;
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
	public int getIdBrand() {
		return idBrand;
	}
	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
	}
	public int getIdType() {
		return idType;
	}
	public void setIdType(int idType) {
		this.idType = idType;
	}
	public boolean isNational() {
		return isNational;
	}
	public void setNational(boolean isNational) {
		this.isNational = isNational;
	}

	public boolean save() throws ClassNotFoundException, SQLException, NotNullableException{
		GenericPersistence gP = new GenericPersistence();
		boolean result = gP.insertBean(this);
		this.setId(Model.last().getId());
		return result;
	}
	
	public static Model get(int id) throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (Model) gP.selectBean(new Model(id));
	}
	
	public static ArrayList<Model> getAll() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<Model> models = new ArrayList<Model>();
		for (Object bean : gP.selectAllBeans(new Model())) {
			models.add((Model)bean);
		}
		return models;
	}
	
	public static int count() throws ClassNotFoundException, SQLException {
		GenericPersistence gDB = new GenericPersistence();
		return gDB.countBean(new Model());
	}
	
	public static Model first() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (Model) gP.firstOrLastBean(new Model() , false);
	}
	
	public static Model last() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (Model) gP.firstOrLastBean(new Model() , true);
	}
	
	public static ArrayList<Model> getWhere(Condition condition) throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<Model> models = new ArrayList<Model>();
		for (Object bean : gP.selectWhere(new Model(), condition)) {
			models.add((Model)bean);
		}
		return models;
	}
	
	public boolean delete() throws ClassNotFoundException, SQLException {
		GenericPersistence gP = new GenericPersistence();
		return gP.deleteBean(this);
	}
	
	public Type getType() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (Type) gP.selectOne(this, new Type());
	}
	
	public Brand getBrand() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		return (Brand) gP.selectOne(this, new Brand());
	}
	
	public ArrayList<Tickets> getTickets() throws ClassNotFoundException, SQLException{
		GenericPersistence gP = new GenericPersistence();
		ArrayList<Tickets> beans = new ArrayList<Tickets>();
		for (Object bean : gP.selectMany(this, new Tickets())) {
			beans.add((Tickets)bean);
		}
		return beans;
	}

	@Override
	public String toString() {
		return ""+name;
	}

    @Override
    public int getTotalTickets() {
        try {
            int total = 0;
            ArrayList<Tickets> tickets = getTickets();
            for(Tickets ticket : tickets){
                total += ticket.getTotalTickets();
            }
            return total;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Double getAverageExceded() {
        try {
            double average = 0;
            ArrayList<Tickets> tickets = getTickets();
            for(Tickets ticket : tickets){
                average += ticket.getAverageExceded();
            }
            return average/tickets.size();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    @Override
    public Double getMaximumMeasuredVelocity() {
        try {
            double maximum = 0;
            ArrayList<Tickets> tickets = getTickets();
            for(Tickets ticket : tickets){
                if(maximum<ticket.getMaximumMeasuredVelocity()){
                    maximum = ticket.getMaximumMeasuredVelocity();
                }
            }
            return maximum;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

}

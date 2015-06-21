package models;

import com.modesteam.pardal.ComparableCategory;

import java.sql.SQLException;
import java.util.ArrayList;

import annotations.Column;
import annotations.Entity;
import annotations.HasMany;
import annotations.ManyRelations;
import annotations.OrderBy;
import helpers.Condition;
import helpers.GenericPersistence;
import libraries.NotNullableException;

@ManyRelations({@HasMany(entity = Model.class, foreignKey = "idBrand")})
@Entity(table="brand",primaryKey = "id")
@OrderBy(field = "name")
public class Brand implements ComparableCategory{

    @Column(name="_id", nullable = false)
    private int id;
    private String name;

    @Column(name="total_tickets", nullable=true)
    private int totalTickets;
    @Column(name="average_exceded", nullable=true)
    private Double averageExceded;
    @Column(name="maximum_measured_velocity", nullable=true)
    private Double maximumMeasuredVelocity;

    public Brand(){

    }

    public Brand(int id){
        this.id = id;
    }

    public Brand(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean save() throws NotNullableException {
        GenericPersistence gP = new GenericPersistence();
        boolean result = gP.insertBean(this);
        this.setId(Brand.last().getId());
        return result;
    }

    public static Brand last(){

        GenericPersistence gP = new GenericPersistence();
        return ((Brand)gP.firstOrLastBean(new Brand(), true));
    }

    public static Brand get(int id) {
        GenericPersistence gP = new GenericPersistence();
        return (Brand) gP.selectBean(new Brand(id));

    }

    public static ArrayList<Brand> getAll(){
        GenericPersistence gP = new GenericPersistence();
        ArrayList<Brand> brands = new ArrayList<Brand>();
        for (Object bean : gP.selectAllBeans(new Brand())) {
            brands.add((Brand) bean);
        }
        return brands;
    }

    public static int count(){
        GenericPersistence gDB = new GenericPersistence();
        return gDB.countBean(new Brand());
    }

    public static Brand first(){
        GenericPersistence gP = new GenericPersistence();
        return (Brand) gP.firstOrLastBean(new Brand() , false);

    }

    public static ArrayList<Brand> getWhere(Condition condition){
        GenericPersistence gP = new GenericPersistence();
        ArrayList<Brand> brands = new ArrayList<Brand>();
        for (Object bean : gP.selectWhere(new Brand(), condition)) {
            brands.add((Brand)bean);
        }
        return brands;

    }

    public boolean delete(){
        GenericPersistence gP = new GenericPersistence();
        return gP.deleteBean(this);

    }
    public ArrayList<Model> getModels(){
        GenericPersistence gP = new GenericPersistence();
        ArrayList<Model> models = new ArrayList<Model>();
        for (Object bean : gP.selectMany(this, new Model())) {
            models.add((Model)bean);
        }

        return models;

    }

    @Override
    public String toString() {
        return "" + name;
    }
}

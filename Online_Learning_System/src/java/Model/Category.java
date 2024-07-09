/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class Category {
    private String category_id;
    private String category_name;
    private int numberofCate;
    private double percentage;

    public Category() {
    }

    public Category(String category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public Category(String category_id, String category_name, int numberofCate) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.numberofCate = numberofCate;
    }

    public Category(String category_id, String category_name, int numberofCate, double percentage) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.numberofCate = numberofCate;
        this.percentage = percentage;
    }
    
    

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getNumberofCate() {
        return numberofCate;
    }

    public void setNumberofCate(int numberofCate) {
        this.numberofCate = numberofCate;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Category{" + "category_id=" + category_id + ", category_name=" + category_name + ", numberofCate=" + numberofCate + ", percentage=" + percentage + '}';
    }
    
    
    

    
    
    
}

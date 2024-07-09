/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tuong
 */
public class WishlistDTO {
    
    private int course_id;
    private String course_name;
    private String image;
    private float price;
    private float discount;
    private String create_by;
    private float star;

    public WishlistDTO() {
    }

     public WishlistDTO(int course_id) {
        this.course_id = course_id;
    }
    
    public WishlistDTO(int course_id, String course_name, String image, float price, float discount, String create_by, float star) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.create_by = create_by;
        this.star = star;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "WishlistDTO{" + "course_id=" + course_id + ", course_name=" + course_name + ", image=" + image + ", price=" + price + ", discount=" + discount + ", create_by=" + create_by + ", star=" + star + '}';
    }
    
    
    
}

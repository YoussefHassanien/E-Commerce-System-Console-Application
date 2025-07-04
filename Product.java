
public class Product {
    private String name;
    private int quantity;
    private double price;
    
    public Product(String name, int quantity, double price){
        setName(name);
        setQuantity(quantity);
        setPrice(price);
    }

    private boolean setName(String name){
        if(!name.equals("") || !name.equals(" ")){
            this.name = name;
            return true;
        }  
        return false;
    }

    private boolean setQuantity(int quantity){
        if(quantity > 0){
            this.quantity = quantity;
            return true;
        }
        return false;
    }

    private boolean setPrice(double price){
        if(price > 0){
            this.price = price;
            return true;
        }
        return false;
    }

    public String getName(){
        return this.name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getPrice(){
        return this.price;
    }

    public boolean reduceQuantity(int quantity){
        if(this.quantity - quantity >= 0){
            this.quantity -= quantity;
            return true;
        } else {
            throw new RuntimeException("Cannot reduce" + this.name + "quantity to below 0");
        }
    }
}
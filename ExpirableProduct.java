import java.time.LocalDate;

public class ExpirableProduct extends Product implements Expirable {
    private LocalDate expiryDate;

    public ExpirableProduct(String name, int quantity, double price, LocalDate expiryDate){
        super(name, quantity, price);
        setExpiryDate(expiryDate);
    }

    @Override
    public boolean setExpiryDate(LocalDate expiryDate){
        if(expiryDate.isAfter(LocalDate.now())){
            this.expiryDate = expiryDate;
            return true;
        }
        return false;
    }

    @Override
    public boolean isExpired(){
        return this.expiryDate.isBefore(LocalDate.now());
    }
}
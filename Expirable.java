import java.time.LocalDate;

public interface Expirable {
    boolean isExpired();
    boolean setExpiryDate(LocalDate expiryDate);
}

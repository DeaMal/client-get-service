package shop.household.client.get.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.household.client.get.entity.Client;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findByFirstnameContainingIgnoreCase(String firstname);
    List<Client> findByLastnameContainingIgnoreCase(String lastname);
    List<Client> findByPhoneContainingIgnoreCase(String phone);
    List<Client> findByPhoneExtraContainingIgnoreCase(String phoneExtra);
    Optional<Client> findByEmailIgnoreCase(String email);
    List<Client> findByAddressContainingIgnoreCase(String address);
    List<Client> findByCreateAtBetween(Timestamp minCreateAt, Timestamp maxCreateAt);
    List<Client> findByUpdatedAtBetween(Timestamp minUpdatedAt, Timestamp maxUpdatedAt);
    List<Client> findByNotesContainingIgnoreCase(String notes);
    List<Client> findByLastOrderDateBetween(Date minLastOrderDate, Date maxLastOrderDate);
    List<Client> findByTotalOrdersBetween(Integer minTotalOrders, Integer maxTotalOrders);
    List<Client> findByTotalSpentBetween(BigDecimal minTotalSpent, BigDecimal maxTotalSpent);
}

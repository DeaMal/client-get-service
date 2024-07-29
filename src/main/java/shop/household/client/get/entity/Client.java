package shop.household.client.get.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "client", schema = "shop")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname", nullable = false)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "phone_extra")
    private String phoneExtra;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "notes")
    private String notes;

    @Column(name = "last_order_date")
    private Date lastOrderDate;

    @Column(name = "total_orders")
    private Integer totalOrders;

    @Column(name = "total_spent")
    private BigDecimal totalSpent;

    public Client() {
    }
}

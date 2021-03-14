package kakaopay.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "userId", insertable = false,updatable = false)
    private List<Invest> invest;

    public List<Invest> getInvest() {return invest;};

}

package apap.tugas.BOBAXIXIXI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "manager")
public class ManagerModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String full_name;

    @NotNull
    @Column(nullable = false)
    private Integer gender;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date_of_birth;

    // relasi dengan store
    @OneToOne(mappedBy = "manager")
    private StoreModel store;
}

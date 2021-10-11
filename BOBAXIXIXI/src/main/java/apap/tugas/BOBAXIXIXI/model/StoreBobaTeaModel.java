package apap.tugas.BOBAXIXIXI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "store_boba_tea")
public class StoreBobaTeaModel implements Serializable {
    @Id
    @Size(max = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relation with store
    @ManyToOne
    @JoinColumn(name = "id_store")
    StoreModel store;

    // relation with boba tea
    @ManyToOne
    @JoinColumn(name = "id_boba")
    BobaTeaModel bobaTea;

    @NotNull
    @Size(max = 12)
    @Column(nullable = false, unique = true)
    private String production_code;
}

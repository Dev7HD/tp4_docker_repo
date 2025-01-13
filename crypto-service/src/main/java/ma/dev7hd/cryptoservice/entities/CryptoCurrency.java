package ma.dev7hd.cryptoservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class CryptoCurrency {
    @Id @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private String name;
    private String type;
    private  String unit;
    private double emission;
    private String consensusRule;
    private  String algorithm;
    private String protocol;
    private String platform;
    private String webSite;
    private String community;
}

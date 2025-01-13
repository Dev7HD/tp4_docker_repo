package ma.dev7hd.cryptoservice.repositories;

import ma.dev7hd.cryptoservice.entities.CryptoCurrency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, UUID> {

    @Query("SELECT c FROM CryptoCurrency c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE %:name% ) AND " +
            "(:type IS NULL OR LOWER(c.type) LIKE %:type% ) AND " +
            "(:unit IS NULL OR LOWER(c.unit) LIKE %:unit% ) AND " +
            "(:platform IS NULL OR LOWER(c.platform) LIKE %:platform% )")
    Page<CryptoCurrency> getCryptosByCriteria(
            @Param("name") String name,
            @Param("type") String type,
            @Param("unit") String unit,
            @Param("platform") String platform,
            Pageable pageable);
}

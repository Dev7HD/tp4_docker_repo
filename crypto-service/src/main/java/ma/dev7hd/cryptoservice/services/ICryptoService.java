package ma.dev7hd.cryptoservice.services;

import ma.dev7hd.cryptoservice.entities.CryptoCurrency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface ICryptoService {
    Page<CryptoCurrency> getAllCryptos(String name, String type, String unit, String platform, Pageable pageable);

    ResponseEntity<CryptoCurrency> getCryptoById(UUID id);

    @Transactional
    ResponseEntity<CryptoCurrency> createCrypto(CryptoCurrency cryptoCurrency);

    @Transactional
    ResponseEntity<CryptoCurrency> updateCrypto(UUID id, CryptoCurrency cryptoCurrency);

    @Transactional
    ResponseEntity<String> deleteCrypto(UUID id);
}

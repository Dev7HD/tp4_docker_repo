package ma.dev7hd.cryptoservice.services;

import lombok.AllArgsConstructor;
import ma.dev7hd.cryptoservice.entities.CryptoCurrency;
import ma.dev7hd.cryptoservice.repositories.CryptoCurrencyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CryptoServiceImpl implements ICryptoService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    @Override
    public Page<CryptoCurrency> getAllCryptos(String name, String type, String unit, String platform, Pageable pageable){
        return cryptoCurrencyRepository.getCryptosByCriteria(name.toLowerCase(), type.toLowerCase(), unit.toLowerCase(), platform.toLowerCase(), pageable);
    }
    
    @Override
    public ResponseEntity<CryptoCurrency> getCryptoById(UUID id){
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findById(id).orElse(null);
        return cryptoCurrency != null ? ResponseEntity.ok(cryptoCurrency) : ResponseEntity.notFound().build();
    }
    
    @Transactional
    @Override
    public ResponseEntity<CryptoCurrency> createCrypto(CryptoCurrency cryptoCurrency){
        try {
            CryptoCurrency currency = cryptoCurrencyRepository.save(cryptoCurrency);
            return ResponseEntity.ok(currency);
        } catch (Exception e){
            System.out.println("Error saving new crypto.");
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    @Override
    public ResponseEntity<CryptoCurrency> updateCrypto(UUID id, CryptoCurrency cryptoCurrency){
        Optional<CryptoCurrency> optionalCryptoCurrency = cryptoCurrencyRepository.findById(id);
        if (optionalCryptoCurrency.isPresent()){
            CryptoCurrency currency = getCryptoCurrency(cryptoCurrency, optionalCryptoCurrency);
            try {
                CryptoCurrency saved = cryptoCurrencyRepository.save(currency);
                return ResponseEntity.ok(saved);
            } catch (Exception e){
                System.out.println("Error updating Crypto!");
                System.out.println(e);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private static CryptoCurrency getCryptoCurrency(CryptoCurrency cryptoCurrency, Optional<CryptoCurrency> optionalCryptoCurrency) {
        CryptoCurrency currency = optionalCryptoCurrency.get();
        if (cryptoCurrency.getName() != null && !cryptoCurrency.getName().isBlank()) currency.setName(cryptoCurrency.getName());
        if (cryptoCurrency.getType() != null && !cryptoCurrency.getType().isBlank()) currency.setType(cryptoCurrency.getType());
        if (cryptoCurrency.getUnit() != null && !cryptoCurrency.getUnit().isBlank()) currency.setUnit(cryptoCurrency.getUnit());
        if (cryptoCurrency.getPlatform() != null && !cryptoCurrency.getPlatform().isBlank()) currency.setPlatform(cryptoCurrency.getPlatform());
        if (cryptoCurrency.getAlgorithm() != null && !cryptoCurrency.getAlgorithm().isBlank()) currency.setAlgorithm(cryptoCurrency.getAlgorithm());
        return currency;
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteCrypto(UUID id){
        Optional<CryptoCurrency> optionalCryptoCurrency = cryptoCurrencyRepository.findById(id);
        if (optionalCryptoCurrency.isPresent()){
            try {
                cryptoCurrencyRepository.delete(optionalCryptoCurrency.get());
                return ResponseEntity.ok(String.format("Crypto with id: %s deleted successfully", id));
            } catch (Exception e){
                System.out.println("Error deleting Crypto with id: " + id);
            }
        }
        return ResponseEntity.badRequest().build();
    }
}

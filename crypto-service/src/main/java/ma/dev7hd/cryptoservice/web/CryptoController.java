package ma.dev7hd.cryptoservice.web;

import lombok.AllArgsConstructor;
import ma.dev7hd.cryptoservice.entities.CryptoCurrency;
import ma.dev7hd.cryptoservice.services.ICryptoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/cryptos")
public class CryptoController {
    private final ICryptoService cryptoService;

    @GetMapping("")
    Page<CryptoCurrency> getAllCryptos(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String type,
            @RequestParam(required = false, defaultValue = "") String unit,
            @RequestParam(required = false, defaultValue = "") String platform,
            @RequestParam(required = true, defaultValue = "0") int page,
            @RequestParam(required = true, defaultValue = "10") int size
    ){
        return cryptoService.getAllCryptos(name, type, unit, platform, Pageable.ofSize(size).withPage(page));
    }

    @PostMapping("/new")
    ResponseEntity<CryptoCurrency> createCrypto(@RequestBody CryptoCurrency cryptoCurrency){
        return cryptoService.createCrypto(cryptoCurrency);
    }

    @GetMapping("/{id}")
    ResponseEntity<CryptoCurrency> getCryptoById(@PathVariable UUID id){
        return cryptoService.getCryptoById(id);
    }

    @PatchMapping("/{id}/update")
    ResponseEntity<CryptoCurrency> updateCrypto(@PathVariable UUID id,@RequestBody CryptoCurrency cryptoCurrency){
        return cryptoService.updateCrypto(id, cryptoCurrency);
    }

    @DeleteMapping("/{id}/delete")
    ResponseEntity<String> deleteCrypto(@PathVariable UUID id){
        return cryptoService.deleteCrypto(id);
    }
}


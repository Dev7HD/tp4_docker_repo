package ma.dev7hd.cryptoservice;

import ma.dev7hd.cryptoservice.entities.CryptoCurrency;
import ma.dev7hd.cryptoservice.repositories.CryptoCurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CryptoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CryptoCurrencyRepository cryptoCurrencyRepository) {
        return args -> {
            cryptoCurrencyRepository.save(
                    CryptoCurrency.builder()
                            .name("Binance Coin (BNB)")
                            .unit("BNB")
                            .emission(200_000_000.0)
                            .type("service")
                            .protocol("open source")
                            .algorithm("Ethash")
                            .platform("Binance")
                            .consensusRule("Proof of Work")
                            .webSite("https://binance.com")
                            .community("Annonces projet, Facebook, Medium, Reddit, Steemit, Telegram")
                            .build()
            );
            cryptoCurrencyRepository.save(
                    CryptoCurrency.builder()
                            .name("Bitcoin (BTC)")
                            .unit("BTC")
                            .emission(21_000_000.0)
                            .type("Paiement")
                            .protocol("open source")
                            .algorithm("SHA 256")
                            .platform("Binance, Bitfinex, Bittrex, Coinbase, GDAX")
                            .consensusRule("Proof of Work")
                            .webSite("https://bitcoin.fr")
                            .community("Bitcointalk (forum), GitHub, Reddit.")
                            .build()
            );
            cryptoCurrencyRepository.save(
                    CryptoCurrency.builder()
                            .name("Ethereum (ETH)")
                            .unit("ETH")
                            .emission(72_000_000.0)
                            .type("infrastructure")
                            .protocol("open source")
                            .algorithm("Ethash")
                            .platform("Binance, Bitfinex, Bittrex, Coinbase, GDAX")
                            .consensusRule("Proof of Work")
                            .webSite("https://www.ethereum.org/")
                            .community("Blog, Twitter, GitHub, Youtube, Reddit, Facebook, Gitter")
                            .build()
            );
            cryptoCurrencyRepository.save(
                    CryptoCurrency.builder()
                            .name("TRON (TRX)")
                            .unit("TRX")
                            .emission(100_000_000_000.0)
                            .type("service")
                            .protocol("open source")
                            .algorithm("Ethash")
                            .platform("Binance, HitBTC, Huobi, Yobit")
                            .consensusRule("Proof of Replication")
                            .webSite("https://tron.network/")
                            .community("Discord, Facebook, GitHub, Reddit, Telegram.")
                            .build()
            );
        };
    }
}

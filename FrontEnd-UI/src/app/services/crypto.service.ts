import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CryptoCurrency} from '../models/CryptoCurrency';
import {Page} from '../models/Page';

@Injectable({
  providedIn: 'root'
})
export class CryptoService {

  private baseUrl = 'http://localhost:8888/crypto-service/cryptos';

  pageCryptos!: Page<CryptoCurrency>;
  isLoading: boolean = false;

  constructor(private http: HttpClient) {}

  /**
   * Fetches a paginated list of cryptocurrencies with optional filters.
   */
  private fetchCryptos(
    name: string,
    type: string,
    unit: string,
    platform: string,
    page: number,
    size: number
  ): Observable<Page<CryptoCurrency>> {
    this.isLoading = true
    const params = new HttpParams()
      .set('name', name)
      .set('type', type)
      .set('unit', unit)
      .set('platform', platform)
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<Page<CryptoCurrency>>(`${this.baseUrl}`, { params });
  }

  /**
   * Creates a new cryptocurrency.
   */
  public createCrypto(cryptoCurrency: CryptoCurrency): Observable<CryptoCurrency> {
    return this.http.post<CryptoCurrency>(`${this.baseUrl}/new`, cryptoCurrency);
  }

  /**
   * Fetches a cryptocurrency by ID.
   */
  public getCryptoById(id: string): Observable<CryptoCurrency> {
    return this.http.get<CryptoCurrency>(`${this.baseUrl}/${id}`);
  }

  /**
   * Updates a cryptocurrency by ID.
   */
  public updateCrypto(id: string, cryptoCurrency: CryptoCurrency): Observable<CryptoCurrency> {
    return this.http.patch<CryptoCurrency>(
      `${this.baseUrl}/${id}/update`,
      cryptoCurrency
    );
  }

  /**
   * Deletes a cryptocurrency by ID.
   */
  public deleteCrypto(id: string): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${id}/delete`, {responseType: "text"});
  }

  public getCryptos(
    name: string = '',
    type: string = '',
    unit: string = '',
    platform: string = '',
    page: number = 0,
    size: number = 10
  ){
    let req = this.fetchCryptos(name, type, unit, platform, page, size)
    req.subscribe({
      next: data => {
        this.isLoading = false
        this.pageCryptos = data
      }, error: err => console.error("Error fetching Cryptos", err)
    })
  }

}

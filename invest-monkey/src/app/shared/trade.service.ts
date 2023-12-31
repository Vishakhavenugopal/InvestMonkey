import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Instrument } from '../models/instrument';
import { Price } from '../models/price';

@Injectable({
  providedIn: 'root'
})
export class TradeService {
  price_url="http://localhost:3000/fmts/trades/prices";
  instrument_url="http://localhost:3000/fmts/trades/instruments";
  constructor(private http:HttpClient) { }

  getTradePriceList(filter:string):Observable<Price[]>{
    return this.http.get<Price[]>(`${this.price_url}${filter}`);
  }

  getTradeInstrumentList(filter:string):Observable<Instrument[]>{
    return this.http.get<Instrument[]>(`${this.instrument_url}${filter}`);
  }
}

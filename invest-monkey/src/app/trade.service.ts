import { Injectable, OnInit } from '@angular/core';
import { Trade } from './models/trade';
import { Order } from './models/order';
import { Observable, catchError, map, of } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Instruments } from './models/instruments';
import { InstrumentService } from './instrument.service';

@Injectable({
  providedIn: 'root'
})
export class TradeService
 {
  trade:Trade | undefined;
  trades:Trade[]=[];
  instruments: Instruments[] =[]
  allInstruments:Instruments[]=[]
   buyReportData: Trade[]=[]
   sellReportData:Trade[]=[]
  url='http://localhost:3000/fmts/trades/trade';

  constructor(private http:HttpClient,private instrumentService:InstrumentService) { 
      this.instrumentService.getInstruments().subscribe({
        next:x=>{
          this.allInstruments=x;
        }
      })
  }
  generateNewTrade(order: Order): Observable<Trade | null> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const tradeUrl = "https://a721308.roifmr.com/client/trade"; 

    return this.http.post<Trade>(tradeUrl, order, { headers: headers }).pipe(
      map((data) => {
        this.trade = data;
        console.log(data);
        console.log("CURRENT TRADE => ", this.trade);

        if (this.trade.clientId !== "") {
          return this.trade;
        } else {
          return null;
        }
      }),
      catchError((error) => {
        console.error("Error:", error);
        throw new Error("Error occurred during trade generation"); // You can handle errors appropriately
      })
    );
  }
  // generateNewSellTrade(order:Order):Observable<Trade[]>{
  //   const cashValue=order.quantity*order.targetPrice;
  //   for(let i=0;i<this.allInstruments.length;i++){
  //     if(order.instrumentI===this.allInstruments[i].instrument.instrumentId){
  //       console.log('INSTRUMENT PUSHED=>',this.allInstruments[i]);
  //       this.instruments.push(this.allInstruments[i]);
  //     }
  //   }
  //   const newTrade={
  //     tradeId:this.generateTradeid(),
  //     quantity:order.quantity,
  //     executionPrice:order.targetPrice,
  //     direction:order.direction,
  //     order:order,
  //     cashValue:cashValue,
  //     clientId:order.clientId,
  //     instrumentId:order.instrumentI
  //   }
  //   this.trades.push(newTrade);
  //   return of(this.trades);
  // }
  getCurrentUserTrades(clientId:string):Observable<Trade[]>
  {
    const tradeHistoryUrl = "https://a721308.roifmr.com/client/tradeHistory/"+clientId;

    return this.http.get<Trade[]>(tradeHistoryUrl);
  }

  getCurrentUserBuyReport(clientId:string): Observable<Trade[]> {
    this.getCurrentUserTrades(clientId).subscribe((data) =>{
    this.trades = data;
   this.buyReportData=  this.trades.filter((trade) => trade.direction === 'B');
     
    });
    return of(this.buyReportData);
    
  }

  getCurrentUserSellReport(clientId:string): Observable<Trade[]> {
    
    this.getCurrentUserTrades(clientId).subscribe((data) =>{
      this.trades = data;
     this.sellReportData=  this.trades.filter((trade) => trade.direction === 'S');
       
      });
      return of(this.sellReportData);
  }

  getInstrumentDescription(instrumentId:string):Observable<string>
  {
    for(let i=0;i<this.instruments.length;i++)
    {
      if(this.instruments[i].instrument.instrumentId===instrumentId)
      {
        return of(this.instruments[i].instrument.instrumentDescription);
      }
    }
    return of("NULL123");
  }

  getCurrentUserInstruments():Observable<Instruments[]>
  {
    return of(this.instruments);
  }
  generateTradeid():string{
    const characters='ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
      let tradeId='';
      for(let i=0;i<9;i++){
        tradeId+=characters.charAt(Math.floor(Math.random()*characters.length));
      }
      return tradeId;
    }
  
    
}

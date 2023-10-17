import { Injectable, OnInit } from '@angular/core';
import { Observable, of, switchMap } from 'rxjs';
import { InstrumentService } from 'src/app/instrument.service';
import { Instruments } from 'src/app/models/instruments';
import { Trade } from 'src/app/models/trade';
import { TradeService } from 'src/app/trade.service';
import { VerifyService } from 'src/app/verify.service';

@Injectable({
  providedIn: 'root'
})
export class TradeHistoryService implements OnInit{
  currentClientId:any;

  constructor(private tradeService:TradeService,
    private verifyService:VerifyService,
    private instrumentService: InstrumentService,) { }

    ngOnInit(): void {
      this.verifyService.getCurrentClientId().pipe(
        switchMap(x => {
          this.currentClientId = x;
          return this.getTradeHistory(this.currentClientId); // Assuming getTradeHistory returns an observable
        })
      ).subscribe(tradeHistory => {
        // Use tradeHistory here
        console.log("Trade history:", tradeHistory);
      });
    }

   tradeHistory: Trade[] = [];
  instruments: Instruments[] = [];

  getTradeHistory(clientId:string): Observable<Trade[]> {
     this.tradeService.getCurrentUserTrades(clientId).subscribe((data) => {
      this.tradeHistory = data;
      console.log("IN DATA SERVICE TRADES => ",this.tradeHistory);
      
    });
    return of(this.tradeHistory);
  }

  getInstruments():Instruments[]
  {
     this.tradeService.getCurrentUserInstruments().subscribe((data) => {
      this.instruments = data;
     });
     return this.instruments;
  }

  loadInstruments(): void {
    this.instrumentService.getInstruments().subscribe((data) => {
      this.instruments = data;
    });
  }



    // getInstrumentDescription(instrumentId:Number):void
    // {
    //   for(instrument:Instruments in this.instruments)
    //   {
    //      this.displayedInstruments.push(instrument.find(instrumentId))
    //   }
    // }
}

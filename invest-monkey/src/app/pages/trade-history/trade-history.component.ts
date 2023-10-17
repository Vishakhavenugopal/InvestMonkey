import { Component, OnInit } from '@angular/core';
import { TradeHistoryService } from './trade-history.service';
import { Trade } from 'src/app/models/trade';
import { Instruments } from 'src/app/models/instruments';
import { VerifyService } from 'src/app/verify.service';
import { switchMap } from 'rxjs';
import { TradeService } from 'src/app/trade.service';

@Component({
  selector: 'app-trade-history',
  templateUrl: './trade-history.component.html',
  styleUrls: ['./trade-history.component.css']
})
export class TradeHistoryComponent implements OnInit {
  tradeHistory: Trade[] = [];
  instruments:Instruments[] = [];
  currentClientId:any;


  constructor(private tradeService:TradeService,
    private verifyService:VerifyService,private tradeHistoryService:TradeHistoryService) { }

    ngOnInit(): void {
      this.verifyService.getCurrentClientId().subscribe({
        next: (x) => {
          this.currentClientId = x;
          console.log("ID IN COMPONENT => ",this.currentClientId);
        },
      });
      this.tradeService.getCurrentUserTrades(this.currentClientId).subscribe({
        next:(data)=>{
          this.tradeHistory=data;
          console.log("TRADE HISTORY IN COMP=> ",this.tradeHistory);
          
        }
      });
      this.instruments = this.tradeHistoryService.getInstruments();
          
      
    }
    
    
}

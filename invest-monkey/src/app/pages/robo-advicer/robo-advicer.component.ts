import { Component, OnInit } from '@angular/core';
import { PortfolioDataService } from 'src/app/portfolio-data.service';
import { Price } from 'src/app/models/price';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Holdings } from 'src/app/models/holdings';
import { PortfolioComponent } from '../portfolio/portfolio.component';
import { SharedDataService } from 'src/app/shared-data-service.service';
import { Instruments } from 'src/app/models/instruments';
import { Instrument } from 'src/app/models/instrument';
import { Order } from 'src/app/models/order';
import { VerifyService } from 'src/app/verify.service';
import { TradeService } from 'src/app/trade.service';
import { Router } from '@angular/router';
import { InstrumentService } from 'src/app/instrument.service';
import { switchMap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-robo-advicer',
  templateUrl: './robo-advicer.component.html',
  styleUrls: ['./robo-advicer.component.css']
})
export class RoboAdvicerComponent implements OnInit {
  topBuyInstruments: Instruments[] = [];
  topSellInstruments: Instruments[] = [];
  allInstruments:Instruments[]=[];
  allHoldings:Holdings[]=[];
  isBuyDialogOpen = false;
  isSellDialogOpen = false;
  selectedInstrument: any = {};
  quantity: number = 1;
  currentClientId:any;
  isTermsAgreed: boolean = false;
  holdings:Holdings[]=[];


  constructor(
    private portfolioDataService: PortfolioDataService,
    private snackBar: MatSnackBar,
    private verifyService : VerifyService,
    private tradeService:TradeService,
    private router: Router,
    private http:HttpClient,
    private instrumentService:InstrumentService
  ) {
    this.verifyService.getCurrentClientId().subscribe({
      next: (x) => {
        this.currentClientId = x;
        console.log("ID IN COMPONENT => ",this.currentClientId);
      },
    });

    this.instrumentService.getInstruments().subscribe((data) => {
      this.allInstruments = data;
    })
    
        const roboUrl = "http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:3200/client/roboAdvisor/"+this.currentClientId;
        this.http.get<Instruments[]>(roboUrl).subscribe((data)=>
        {
          this.topBuyInstruments = data;
        })
        console.log('all instruments =>', this.allInstruments);
    
     
    
        // Fetch holdings data from portfolioDataService
         this.portfolioDataService.getHolding(this.currentClientId).subscribe((data) =>{
          this.allHoldings=data;
          console.log("ALL HOLDINGS => ",this.allHoldings);
          console.log("TOP BUY INSTRUMENTS => ",this.topBuyInstruments);
          
          let flag=0;
          // for (let i = 0; i < this.topBuyInstruments.length; i++) {
           
          //   for (let j = 0; j < this.allHoldings.length; j++) {
          //     console.log("BUY INST ID => ",this.topBuyInstruments[i].instrument.instrumentId);
          //     console.log("all INST ID => ",this.allHoldings[j].instrumentId);
              
          //     if (this.topBuyInstruments[i].instrument.instrumentId === this.allHoldings[j].instrumentId) {
          //       break;
          //     }
          //     this.topSellInstruments.push(this.topBuyInstruments[i]);
              
          //   }
           
          // }
          
          for(let i=0;i<this.allHoldings.length;i++){
            if(this.topBuyInstruments.filter((instrument)=>instrument.instrument.instrumentId==this.allHoldings[i].instrumentId).length==0){
                let instr=this.allInstruments.filter((instr)=>instr.instrument.instrumentId==this.allHoldings[i].instrumentId);
                this.topSellInstruments.push(instr[0]);
            }
          }
          this.topSellInstruments= this.topSellInstruments.slice(0,5);

         });
      
   
    
     
    
      // Now that you have both sets of data, you can process them together
      

      
      

   
   
  }

  ngOnInit(): void {
    // Fetch top buy stocks from the PortfolioDataService using getPrice()
    // this.portfolioDataService.getPrice().subscribe((data: Price[]) => {
    //   this.topBuyInstruments = data
    //     .slice(0, 5); // Select the top 5
    // });
    
    
    
    


    // Extract the last 5 instruments
    // this.portfolioDataService.getPrice().subscribe((data:Price[]) => {
      //this.topSellInstruments = data.slice(-5)   });
  }
  agreeToTerms(): void {
    // Handle the user's agreement to terms
    this.isTermsAgreed = true;
  }

  cancelAndRedirect(): void {
    // Redirect to the home page if the user cancels without agreeing to the terms
    this.router.navigate(['/home']); // Adjust the route as needed
  }

  openBuyDialog(instrument: Instruments): void {
    this.isBuyDialogOpen = true;
    this.selectedInstrument = instrument;
    this.quantity = instrument.instrument.minQuantity;
  }

  buyInstrument(): void {
    this.verifyService.getCurrentClientId().subscribe({
      next:x=>{
        this.currentClientId=x;
      }
    });
    if (this.quantity < 1) {
      this.snackBar.open(`You can buy at least 1 quantity.`, 'Dismiss', {
        duration: 3000,
      });
    } else if (this.quantity > this.selectedInstrument?.instrument.maxQuantity) {
      this.snackBar.open(`Quantity cannot exceed ${this.selectedInstrument?.instrument.maxQuantity}.`, 'Dismiss', {
        duration: 3000,
      });
    } else {
      // Handle the buy action here
      // You can implement the buy logic
      const order: Order = {
        instrumentI: this.selectedInstrument.instrument.instrumentId,
        quantity: this.quantity,
        targetPrice: this.selectedInstrument.bidPrice,
        direction: 'B',
        clientId: this.currentClientId,
        orderId: this.generateOrderId(),
      };
      
      console.log(this.selectedInstrument.instrument.instrumentDescription);
      
      console.log(order);
      
      this.tradeService.generateNewTrade(order).subscribe(
        (trade) => {
          if (trade) {
            console.log("Trade generated:", trade);
            // Handle the trade here
          } else {
            console.error("Trade generation failed.");
            // Handle the failure here
          }
        },
        (error) => {
          console.error("Error:", error);
          // Handle the error here
        }
      );
      const message = `Bought instrument: ${this.selectedInstrument?.instrument.instrumentDescription} (Quantity: ${this.quantity})`;
      this.snackBar.open(message, 'Dismiss', {
        duration: 3000,
      });
      this.closeBuyDialog();
    }
  }
  generateOrderId():string{
    const characters='ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let orderId='';
    for(let i=0;i<3;i++){
      orderId+=characters.charAt(Math.floor(Math.random()*characters.length));
    }
    return orderId;
  }
  closeBuyDialog(): void {
    this.isBuyDialogOpen = false;
    this.selectedInstrument = null;
    this.quantity = 1;
  }

  openSellDialog(instrument: Instruments): void {
    this.isSellDialogOpen = true;
    this.selectedInstrument = instrument;
    this.quantity = 1;
  }

  sellInstrument(): void {
    console.log('SELECTED INSTRUMENT QUANTITY => ',this.selectedInstrument.quantity);
   
    if (this.quantity < 1) {
      // Quantity is below the minimum allowed
      this.snackBar.open(`u can sell atleast 1 quantity`, 'Dismiss', {
        duration: 3000,
      });
    } else if (this.quantity > this.selectedInstrument.quantity) {
      // Quantity is above the maximum allowed
      this.snackBar.open(`Quantity cannot exceed ${this.selectedInstrument.quantity}.`, 'Dismiss', {
        duration: 3000,
      });
    } else {
      // Handle the buy action here
      console.log("SELECTED INSTRUMENT",this.selectedInstrument);
    
      const order: Order = {
        instrumentI: this.selectedInstrument.instrument.instrumentId,
        quantity: this.quantity,
        targetPrice: this.selectedInstrument.askPrice,
        direction: 'S',
        clientId: this.currentClientId,
        orderId: this.generateOrderId(),
      };
      
      console.log(this.selectedInstrument.instrumentDescription);
      
      console.log(order);
      
      this.tradeService.generateNewTrade(order).subscribe(
        (trade) => {
          if (trade) {
            console.log("Trade generated:", trade);
            this.verifyService.getCurrentClientId().subscribe({
              next: (x) => {
                this.currentClientId = x;
                console.log("ID IN COMPONENT => ",this.currentClientId);
              },
            });
        
            this.instrumentService.getInstruments().subscribe((data) => {
              this.allInstruments = data;
            })
            
                const roboUrl = "http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:3200/client/roboAdvisor/"+this.currentClientId;
                this.http.get<Instruments[]>(roboUrl).subscribe((data)=>
                {
                  this.topBuyInstruments = data;
                })
                console.log('all instruments =>', this.allInstruments);
            this.portfolioDataService.getHolding(this.currentClientId).subscribe((data) =>{
              this.allHoldings=data;
              console.log("ALL HOLDINGS => ",this.allHoldings);
              console.log("TOP BUY INSTRUMENTS => ",this.topBuyInstruments);
              
            this.topSellInstruments=[];
              
              for(let i=0;i<this.allHoldings.length;i++){
                if(this.topBuyInstruments.filter((instrument)=>instrument.instrument.instrumentId==this.allHoldings[i].instrumentId).length==0){
                    let instr=this.allInstruments.filter((instr)=>instr.instrument.instrumentId==this.allHoldings[i].instrumentId);
                    this.topSellInstruments.push(instr[0]);
                }
              }
              this.topSellInstruments= this.topSellInstruments.slice(0,5);
    
             });
          
            // Handle the trade here
          } else {
            console.error("Trade generation failed.");
            // Handle the failure here
          }
        },
        (error) => {
          console.error("Error:", error);
          // Handle the error here
        }
      );

      //this.price.filter(item => item.instrument.instrumentId !== this.selectedInstrument.instrumentId);
      const message = `Sold instrument: ${this.selectedInstrument.instrumentDescription} (Quantity: ${this.quantity})`;
      // this.getPrice();
      
      this.snackBar.open(message, 'Dismiss', {
        duration: 3000,
      });
      this.closeSellDialog();
    }
  }

  closeSellDialog(): void {
    this.isSellDialogOpen = false;
    this.selectedInstrument = null;
    this.quantity = 1;
  }
}

import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { InstrumentService } from 'src/app/instrument.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Instruments } from 'src/app/models/instruments';
import { Order } from 'src/app/models/order';
import { VerifyService } from 'src/app/verify.service';
import { Client } from 'src/app/models/client';
import { TradeService } from 'src/app/trade.service';
import { PortfolioDataService } from 'src/app/portfolio-data.service';
import { Holdings } from 'src/app/models/holdings';
import { BalanceService } from 'src/app/balance.service';
@Component({
  selector: 'app-client-buy',
  templateUrl: './client-buy.component.html',
  styleUrls: ['./client-buy.component.css']
})
export class ClientBuyComponent {
  instruments: any[] = [];
  displayedInstruments: any[] = [];
  searchTerm: string = '';
  selectedInstrument: any = {};
  quantity: number = 1;
  private displayedCount: number = 5;
  isBuyDialogOpen = false;
  currentClientId:any;
  selectedCategory: string = '';
  currentClientBalance:any;

   constructor(
    private instrumentService: InstrumentService,
    private dialog: MatDialog,private snackBar: MatSnackBar,
    private verifyService : VerifyService,
    private tradeService:TradeService,
    private portfolioDataService:PortfolioDataService,
    private balanceService:BalanceService) {}

 

  ngOnInit(): void {
    this.verifyService.getCurrentClientId().subscribe({
      next:x=>{
        this.currentClientId=x;
      }
    })
    
    
    this.balanceService.getClientBalance(this.currentClientId).subscribe((data)=>{
      this.currentClientBalance=data;
    });
    this.loadInstruments();

  }

  filterInstrumentsByCategory(category: string): void {
    this.selectedCategory = category;
    if (category === '') {
      // If no category is selected, reset the displayed instruments
      this.displayedInstruments = this.instruments.slice(0, 5);
    } else {
      // Filter instruments based on the selected category
      this.displayedInstruments = this.instruments.filter((instrument) =>
        instrument.instrument.categoryId === category
      ).slice(0, this.displayedCount);
    }
  }

  openBuyDialog(instrument:Instruments): void {
    this.isBuyDialogOpen = true;
    this.selectedInstrument = instrument;
    this.quantity = instrument.instrument.minQuantity;
  }

 

  buyInstrument(): void {
    this.verifyService.getCurrentClientId().subscribe({
      next:x=>{
        this.currentClientId=x;
      }
    })
    
    
    this.balanceService.getClientBalance(this.currentClientId).subscribe((data)=>{
      this.currentClientBalance=data;
    });
    // this.currentClientBalance=this.balanceService.getClientBalance(this.currentClientId);
    console.log("Client Balance",this.currentClientBalance);
    if (this.quantity < this.selectedInstrument.instrument.minQuantity) {
      // Quantity is below the minimum allowed
      this.snackBar.open(`Quantity must be at least ${this.selectedInstrument.instrument.minQuantity}.`, 'Dismiss', {
        duration: 3000,
      });
    } else if (this.quantity > this.selectedInstrument.instrument.maxQuantity) {
      // Quantity is above the maximum allowed
      this.snackBar.open(`Quantity cannot exceed ${this.selectedInstrument.instrument.maxQuantity}.`, 'Dismiss', {
        duration: 3000,
      });
    } 
    else if((this.quantity*this.selectedInstrument.bidPrice)>this.currentClientBalance){
      this.snackBar.open(`Insufficient Balance`, 'Dismiss', {
        duration: 3000,
      });
    }
    else {
      // Handle the buy action here
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
            this.verifyService.getCurrentClientId().subscribe({
              next:x=>{
                this.currentClientId=x;
              }
            })
            
            
            this.balanceService.getClientBalance(this.currentClientId).subscribe((data)=>{
              this.currentClientBalance=data;
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
      
      
      this.portfolioDataService.updateHolding(order.instrumentI,order.quantity).subscribe({
        next:x=>{
          console.log('HOLDINGS=>',x);

        }
      });
      
      const message = `Bought instrument: ${this.selectedInstrument.instrument.instrumentDescription} (Quantity: ${this.quantity})`;
      this.snackBar.open(message, 'Dismiss', {
        duration: 3000,
      });
      this.closeBuyDialog();
    }
  }

 

  closeBuyDialog(): void {
    this.isBuyDialogOpen = false;
    this.selectedInstrument = null;
    this.quantity = 0;
  }

 

  loadInstruments(): void {
    this.instrumentService.getInstruments().subscribe((data) => {
      this.instruments = data;
      this.displayedInstruments = this.instruments.slice(0, this.displayedCount);
    });
  }

 

  onSearch(): void {
    if (this.searchTerm.trim() === '') {
      // If the search term is empty, reset the displayed instruments
      this.displayedInstruments = this.instruments.slice(0, 5);
    } else {
      // Filter instruments based on the search term
      this.displayedInstruments = this.instruments.filter((instrument) =>
        instrument.instrument.instrumentDescription.toLowerCase().includes(this.searchTerm.toLowerCase())
      ).slice(0, 5);
    }
  }

 

  loadMore(): void {
    this.displayedCount += 5; // Increase the count to display the next 5 instruments
    this.displayedInstruments = this.instruments.slice(0, this.displayedCount);
  }
  generateOrderId():string{
    const characters='ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let orderId='';
    for(let i=0;i<3;i++){
      orderId+=characters.charAt(Math.floor(Math.random()*characters.length));
    }
    return orderId;
  }

 

 

}
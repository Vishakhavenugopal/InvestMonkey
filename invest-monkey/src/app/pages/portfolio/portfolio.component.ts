import { Component, OnInit } from '@angular/core';
import { PortfolioDataService } from 'src/app/portfolio-data.service';
import { Price } from 'src/app/models/price';
import { Holdings } from 'src/app/models/holdings';
import { Instruments } from 'src/app/models/instruments';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SharedDataService } from 'src/app/shared-data-service.service';
import { Order } from 'src/app/models/order';
import { VerifyService } from 'src/app/verify.service';
import { TradeService } from 'src/app/trade.service';
import { ChangeDetectionStrategy } from '@angular/compiler';
import { ChangeDetectorRef } from '@angular/core';
import { BalanceService } from 'src/app/balance.service';
import { Client } from 'src/app/models/client';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.css']
})
export class PortfolioComponent implements OnInit {
  holdings:Holdings[]=[];
  currentClientId:any;
  currentClientBalance:any;
  catChartData = [{
    "name": "Category",
    "series": [
      {
        "name": "STOCK",
        "value": 0
      },
      {
        "name": "CD",
        "value": 0
      }, 
      {
        "name": "GOVT",
        "value": 0
      }
    ]
  }
];
cashChartData=[
  {
    "name": "Cash",
    "series": [
      {
        "name": "INVESTED",
        "value": 0
      },
      {
        "name": "BALANCE",
        "value": 0
      }
    ]
  }]

  


  currentClient?:Client;
  constructor(
    public portfolioDataService:PortfolioDataService,
    private snackBar: MatSnackBar,
    private sharedDataService: SharedDataService,
    private verifyService:VerifyService,
    private tradeService:TradeService,
    private cdr: ChangeDetectorRef,
    private balanceService:BalanceService
  ){ }

  ngOnInit(): void {
    //this.getPrice();
    this.verifyService.getCurrentClientId().subscribe({
      next:x=>{
        this.currentClientId=x;
      }
    })
    
    
    this.balanceService.getClientBalance(this.currentClientId).subscribe((data)=>{
      this.currentClientBalance=data;
    });
    this.portfolioDataService.getHolding(this.currentClientId).subscribe({
      next:x=>{
        this.holdings=x;
        console.log('PORTFOLIO Holdings=>',this.holdings);
        this.createCategoryChart();
        this.createCashChart();
      }
    })
  }

  price: Price[] = [];
  //holdings: Holdings[] = [];
  displayedColumns: string[] = ['instrumentId', 'description', 'category', 'askPrice', 'quantity', 'totalPrice', 'actions'];
  isSellDialogOpen:boolean = false;
  selectedInstrument: any = {};
  quantity: number = 1;

  createCategoryChart():any{
    
    this.catChartData = [{
      "name": "Category",
      "series": this.holdings.map(item => {
        return {
          "name": item.categoryId,
          "value": item.totalPrice
        };
      })
    }];
  }
  createCashChart():any{
    const totalInvestedValue = this.holdings.reduce((sum, item) => sum + item.totalPrice, 0);
    this.cashChartData=[{
      "name": "Cash",
      "series": [
        {
          "name": "INVESTED",
          "value": totalInvestedValue
        },
        {
          "name": "BALANCE",
          "value": this.currentClientBalance
        }
      ]
    }]
  }
  
  

  calculateTotalStockValue(): number {
    // Calculate the total value of stocks bought here
    // You can replace this with your own logic
    return this.holdings.reduce(
      (total, holding) => total + holding.quantity * holding.askNumber,
      0
    );
  }






  getPrice() {
    this.portfolioDataService.getPrice().subscribe((price) => {
      this.price = price;

      // Clear holdings array before populating it
      this.holdings = [];

      price.forEach((priceItem) => {
        const holding = new Holdings(
          priceItem.instrument.instrumentId,
          priceItem.instrument.instrumentDescription,
          priceItem.instrument.categoryId,
          priceItem.askPrice,
          
          5, // Set your default quantity here
          0 // Set your default totalPrice here
        );
        this.holdings.push(holding);
      });
    });
  }
  openSellDialog(instrument:Instruments): void {
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
      const order: Order = {
        instrumentI: this.selectedInstrument.instrumentId,
        quantity: this.quantity,
        targetPrice: this.selectedInstrument.askNumber,
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
            this.portfolioDataService.getHolding(this.currentClientId).subscribe({
              next:x=>{
                this.holdings=x;
                console.log('Holdings=>',this.holdings);
                
              }
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
      this.balanceService.updateBalance(this.currentClientId,(order.quantity*order.targetPrice),'S');
      this.currentClientBalance=this.balanceService.getClientBalance(this.currentClientId);

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
    this.quantity = 0;
  }
  // Other methods for getting stock, govtbond, instruments, and user
  generateOrderId():string{
    const characters='ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let orderId='';
    for(let i=0;i<3;i++){
      orderId+=characters.charAt(Math.floor(Math.random()*characters.length));
    }
    return orderId;
  }
  user: string = "TestUser";
  groupHoldingsByCategory() {
    const grouped: { [categoryId: string]: Holdings[] } = {};

  for (const holding of this.holdings) {
    if (!grouped[holding.categoryId]) {
      grouped[holding.categoryId] = [];
    }

    grouped[holding.categoryId].push(holding);
  }

  return grouped;
  }
  // Function to populate chart data based on holdings
populateChartData() {
  // Group holdings by categoryId and calculate total quantity
  const groupedHoldings = this.groupHoldingsByCategory();

  // Initialize an empty object to store the grouped data
  const chartDataMap: { [categoryId: string]: { category: string; totalQuantity: number } } = {};

  // Iterate through the grouped holdings and calculate total quantity
  for (const categoryId in groupedHoldings) {
    if (Object.prototype.hasOwnProperty.call(groupedHoldings, categoryId)) {
      const categoryHoldings = groupedHoldings[categoryId];

      // Calculate total quantity for the category
      const totalQuantity = categoryHoldings.reduce((total, holding) => total + holding.quantity, 0);

      // Store the result in the chart data map
      chartDataMap[categoryId] = {
        category: categoryHoldings[0].categoryId, // Assuming all holdings in the same category have the same name
        totalQuantity: totalQuantity,
      };
    }
  }


}

}

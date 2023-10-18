import { Injectable,OnInit } from '@angular/core';
import { PortfolioMockData } from './models/portfolio-mock-data';
import {HttpClient} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { GovtBond } from './models/govtbond';
import { Price } from './models/price';
import { Stock } from './models/stock';
import { Instrument } from './models/instrument';
import { Holdings } from './models/holdings';
import { Instruments } from './models/instruments';
import { InstrumentService } from './instrument.service';
import { VerifyService } from './verify.service';
import { Client } from './models/client';
@Injectable({
  providedIn: 'root'
})
export class PortfolioDataService implements OnInit {
  private apiUrl='http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:3000/fmts/trades';
  private holdings:Holdings[]=[];
  private instruments:Instruments[]=[];
  currentClient?:Client;
  constructor(private http:HttpClient,private instrumentService:InstrumentService, private verifyService:VerifyService) { 
    this.instrumentService.getInstruments().subscribe({
      next:x=>{
        this.instruments=x;
        console.log('GOT INSTRUMENTS=>',this.instruments);
      }
    });
  }

  ngOnInit(): void {
   // this.getHolding();
   this.currentClient= this.verifyService.getCurrentClient();
   this.getHolding(this.currentClient.clientId);
  }
  getGovtBonds():Observable<GovtBond[]>{
    return this.http.get<GovtBond[]>(`${this.apiUrl}/prices/GOVT`);
  }
  getInstruments():Observable<Instrument[]>{
    return this.http.get<Instrument[]>(`${this.apiUrl}/instruments`);
  }
  getPrice():Observable<Price[]>{
    return this.http.get<Price[]>(`${this.apiUrl}/prices`);
  }
  getStockInstruments():Observable<Stock[]>{
    return this.http.get<Stock[]>(`${this.apiUrl}/instruments/STOCK`);
  }
  mockData:PortfolioMockData={
    cash: 250,
    bondDetails: [{
      issuer:'NextDC Ltd.',
      type:'Fixed',
      expectedDate:new Date(2025,8,27),
      faceValue:20000,
      capitalValue:23000
    },
    {
      issuer:'Sydney Airport Finance',
      type:'Inflation Linked',
      expectedDate:new Date('2024-02-07'),
      faceValue:23456,
      capitalValue:25432
    }
  ],
    mutualFunds: [
      {
        name:'Axis Long-term equity',
        category:'Tax Saving',
        sipamt:1000
      },
      {
        name:'Principal Emerging Bluechip',
        category:'Mid-cap',
        sipamt:1500
      },
      {
        name:'SBI Small & Midcap',
        category:'Small-cap',
        sipamt:1000
      }
    ],
    stocks: [{
      ticker:'MSFT',
      buyPrice:102.00,
      actualPrice:104.06,
      shares:200
    },
    {
      ticker:'AAPL',
      buyPrice:45.68,
      actualPrice:56.02,
      shares:300
    },
    {
      ticker:'GOOGL',
      buyPrice:67.23,
      actualPrice:68.97,
      shares:100
    },
    {
      ticker:'NKE',
      buyPrice:800.95,
      actualPrice:825.76,
      shares:80
    }
  ]
  };
  updateHolding(instrumentId:string,quantity:number):Observable<Holdings[]>{
    let holdingPresent=false;
    for(let i=0;i<this.holdings.length;i++){
      if(this.holdings[i].instrumentId===instrumentId){
        this.holdings[i].quantity+=quantity;
        holdingPresent=true;
        //return of(this.holdings)
      }
    }
   
    if(!holdingPresent){
      //console.log("Inside here");
      for(let i=0;i<this.instruments.length;i++){
       // console.log("Current: "+this.instruments[i].instrument.instrumentId);
        //console.log("What I got from function: "+ instrumentId);
        if(this.instruments[i].instrument.instrumentId===instrumentId){
          const newHolding:Holdings={
            instrumentId:instrumentId,
            instrumentDescription:this.instruments[i].instrument.instrumentDescription,
            categoryId:this.instruments[i].instrument.categoryId,
            askNumber:this.instruments[i].askPrice,
            // bidPrice:this.instruments[i].bidPrice,
            quantity:quantity,
            totalPrice:quantity*this.instruments[i].askPrice
        }
        this.holdings.push(newHolding);
       // return of(this.holdings)
        }
      }
    }
    return of(this.holdings)

  }
  updateSellHolding(instrumentId:string,quantity:number):Observable<Holdings[]>{
    for(let i=0;i<this.holdings.length;i++){
      if(this.holdings[i].instrumentId===instrumentId){
        this.holdings[i].quantity-=quantity;
        //return of(this.holdings)
      }
    }
    this.holdings=this.holdings.filter(holding=>holding.quantity!=0);
    return of(this.holdings);
  }
  getHolding(clientId:string):Observable<Holdings[]>{
    console.log("***********************************************************");
    
    let holdingsUrl="http://ec2-3-111-214-97.ap-south-1.compute.amazonaws.com:3000:3200/client/holdings/"+clientId;
    console.log("holding url=> ",holdingsUrl);
    
    return this.http.get<Holdings[]>(holdingsUrl);
  }
}

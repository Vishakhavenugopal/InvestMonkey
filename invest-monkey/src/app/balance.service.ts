import { Injectable } from '@angular/core';
import { Balance } from './models/balance';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, catchError, map, of } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class BalanceService {
  balances:Balance[]=[
    {
      clientId:'ABC123',
      balance:1000000
    }
  ]
  bal: Number | undefined = 0;
  constructor(private http:HttpClient){

  }
  public readonly url:string="https://a721308.roifmr.com/client";

  pushNewBalance(clientId:string){
    const balance={
      clientId:clientId,
      balance:1000000
    }

    const bal:number=0;
    console.log("NEW CLIENT BALANCE PUSHED=>",this.balances);
    
    this.balances.push(balance);
  }
   getClientBalance(clientId: string): Observable<any> {
    
      // const data = await this.http
      //   .get<number>(this.url + "/clientBalance/" + clientId)
      //   .toPromise();
      let test=this.url+"/clientBalance/"+clientId;
      console.log("TEST=> ",test);
      
       return this.http.get<number>(this.url+"/clientBalance/"+clientId);
      //this.bal = data;
      //console.log(data);
      
     // return data;
    } 
  
  
  updateBalance(clientId:string,amount:number,direction:string){
    for(let i=0;i<this.balances.length;i++){
      if(this.balances[i].clientId===clientId){
        if(direction==='B'){
          this.balances[i].balance-=amount;
          console.log('Updated client Balance =>',this.balances[i].balance);
        }
        else{
          this.balances[i].balance+=amount;
          console.log('Updated client Balance =>',this.balances[i].balance);
        }
      }
    }
  }
}

import { Component, OnInit } from '@angular/core';
import { Client } from 'src/app/models/client';
import { VerifyService } from 'src/app/verify.service';
import { Router } from '@angular/router';
import { BalanceService } from 'src/app/balance.service';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit
{
  currentClient?:Client;
  currentClientBalance:any;
  showFullIdentification = false;
  constructor(private verifyService:VerifyService,private router: Router,private balanceService:BalanceService) {}

  ngOnInit(): void {
   this.currentClient= this.verifyService.getCurrentClient();
   this.currentClientBalance=this.balanceService.getClientBalance(this.currentClient.clientId);
  }

  signout():void 
  {
    this.router.navigate(['/home']);
  }

  

  // ... (other functions) ...

  toggleIdentificationDisplay() {
    this.showFullIdentification = !this.showFullIdentification;
  }


}

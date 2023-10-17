import { Injectable } from '@angular/core';
import { ClientInvestmentPreferences } from './models/client-investment-preferences';
import { Client } from './models/client';
import { VerifyService } from './verify.service';

@Injectable({
  providedIn: 'root'
})
export class InvestmentPrefsService {
  investmentPrefs : ClientInvestmentPreferences[] = []
  
  verifyService!: VerifyService;
  roboAgree: boolean = false;
  edit: boolean = (this.investmentPrefs)? true : false;
  // clientId: string = this.verifyService.getCurrentClientId();

  setInvestmentprefs(investmentPref : ClientInvestmentPreferences) {
    this.investmentPrefs.push(investmentPref);
  }

  agree(choice: boolean) {
    this.roboAgree = choice;
  }

  toggleEdit(value: boolean) {
    this.edit = value;
    return value;
  }

  constructor() {}
}

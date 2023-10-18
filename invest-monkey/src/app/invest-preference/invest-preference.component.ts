import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ClientInvestmentPreferences } from '../../app/models/client-investment-preferences';
import { VerifyService } from '../verify.service';
import { Router } from '@angular/router';

enum IncomeCategory {
  CATEGORY_1_10000 = 'CATEGORY_1_10000',
  CATEGORY_10001_250000 = 'CATEGORY_10001_250000',
  CATEGORY_250001_4000000 = 'CATEGORY_250001_4000000',
  CATEGORY_ABOVE_4000000 = 'CATEGORY_ABOVE_4000000'
}

enum RiskTolerance {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

@Component({
  selector: 'app-invest-preference',
  templateUrl: './invest-preference.component.html',
  styleUrls: ['./invest-preference.component.css']
})
export class InvestPreferenceComponent implements OnInit {
  private apiBaseUrl = 'http://65.2.3.57:8080/client/pref';
  public clientInvestmentPreferences: ClientInvestmentPreferences;
  public loggedIn = false;  
  currentClientId: any;
  isTermsAgreed: boolean = false;

  incomeCategories = Object.values(IncomeCategory);
  riskTolerances = Object.values(RiskTolerance);

  constructor(private http: HttpClient, private router: Router, private verifyService: VerifyService) {
    this.clientInvestmentPreferences = new ClientInvestmentPreferences('', '', '', '', 0);
  }

  ngOnInit(): void {
    
    this.verifyService.getCurrentClientId().subscribe({
      next: (x) => {
        this.currentClientId = x;
        this.loggedIn = this.verifyService.isUserLoggedIn();
        if (this.loggedIn) {
          
          
          this.getClientInvestmentPreferences();
        }
      },
      error: (error) => {
        console.error('Error fetching current client ID', error);
      }
    });
  }

  agreeToTerms(): void {
    
    this.isTermsAgreed = true;
  }

  cancelAndRedirect(): void {
   
    this.router.navigate(['/home']); 
  }

  getClientInvestmentPreferences() {
    const url = `${this.apiBaseUrl}/${this.currentClientId}`;
    this.http.get<ClientInvestmentPreferences>(url)
      .subscribe(response => {
        this.clientInvestmentPreferences = response;
      });
  }

  postClientInvestmentPreferences() {
    this.verifyService.getCurrentClientId().subscribe({
      next: (x) => {
        this.currentClientId = x;
        this.loggedIn = this.verifyService.isUserLoggedIn();
        if (this.loggedIn) {
          
          
          this.getClientInvestmentPreferences();
        }
      },
      error: (error) => {
        console.error('Error fetching current client ID', error);
      }
    });
    console.log(" This is Post: ",this.currentClientId);
    this.clientInvestmentPreferences.clientId = this.currentClientId;
  if(this.http.post(this.apiBaseUrl, this.clientInvestmentPreferences)
      .subscribe(() => {
        console.log('Preferences posted successfully.');
       
      }))
      {
      this.router.navigate(['/login']);
  }
}

  updateClientInvestmentPreferences() {
 
    this.http.put(this.apiBaseUrl, this.clientInvestmentPreferences)
      .subscribe(() => {
        console.log('Preferences updated successfully.');
      });
      this.router.navigate(['/home']);
  }
}

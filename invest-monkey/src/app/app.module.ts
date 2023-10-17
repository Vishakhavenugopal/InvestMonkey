import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar'; 
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatCardModule} from '@angular/material/card';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { MatSelectModule} from '@angular/material/select';
import { Navbar2Component } from './components/navbar2/navbar2.component';
import { LandingComponent } from './pages/landing/landing.component';
import { InvestPreferenceComponent } from '../app/invest-preference/invest-preference.component';

import { TradeHistoryComponent } from './pages/trade-history/trade-history.component';
import { AccountDetailsComponent } from './pages/account-details/account-details.component';
import { ClientBuyComponent } from './pages/client-buy/client-buy.component';
import { CommonModule } from '@angular/common';
import { PortfolioComponent } from './pages/portfolio/portfolio.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';

import { RoboAdvicerComponent } from './pages/robo-advicer/robo-advicer.component';
import { BuyPipe } from './shared/buy.pipe';
import { ReportComponent } from './pages/report/report.component';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    SignupComponent,
    Navbar2Component,
    LandingComponent,
    TradeHistoryComponent,
    AccountDetailsComponent,
    ClientBuyComponent,
    PortfolioComponent,
    RoboAdvicerComponent,
    BuyPipe,
    ReportComponent,
    InvestPreferenceComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSlideToggleModule,
    MatToolbarModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
    MatSelectModule,
    HttpClientModule,
    MatSnackBarModule,
    MatCardModule,
    MatIconModule,
    ReactiveFormsModule,
    MatDialogModule,
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    NgxChartsModule,
    FormsModule,        // Add this line to include FormsModule
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LandingComponent } from './pages/landing/landing.component';
import { InvestPreferenceComponent } from '../app/invest-preference/invest-preference.component';
import { TradeHistoryComponent } from './pages/trade-history/trade-history.component';
import { AccountDetailsComponent } from './pages/account-details/account-details.component';
import { ClientBuyComponent } from './pages/client-buy/client-buy.component';
import { PortfolioComponent } from './pages/portfolio/portfolio.component';
import { RoboAdvicerComponent } from './pages/robo-advicer/robo-advicer.component';
import { AuthGuard } from './auth-guard.service';
import { ReportComponent } from './pages/report/report.component';

const routes: Routes = [
  {
    path:'',
    component:LandingComponent,
    pathMatch:'full',
  },
  {
    path:'report',
    component:ReportComponent,
    pathMatch:'full',
    canActivate: [AuthGuard]
  },
  {
    path:'investPrefernce',
    component:InvestPreferenceComponent,
    pathMatch:'full',
    canActivate: [AuthGuard]
  },
  {
    path:'home',
    component:HomeComponent,
    pathMatch:'full',
    canActivate: [AuthGuard]
  },
  {
    path:'tradeHistory',
    component:TradeHistoryComponent,
    pathMatch:'full',
    canActivate: [AuthGuard]
  },

  {
    path:'signup',
    component:SignupComponent,
    pathMatch:'full',
  },
  {
    path:'login',
    component:LoginComponent,
    pathMatch:'full',
  },
  {
    path:'accountDetails',
    component:AccountDetailsComponent,
    pathMatch:'full',
    canActivate: [AuthGuard]
  },
  {
    path:'buy',
    component:ClientBuyComponent,
    pathMatch:'full',
    canActivate: [AuthGuard]
  },
  {
    path:'portfolio',
    component:PortfolioComponent,
    pathMatch:'full',
    canActivate: [AuthGuard]
  },
  
  {
    path:'roboAdvicer',
    component:RoboAdvicerComponent,
    pathMatch:'full',
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
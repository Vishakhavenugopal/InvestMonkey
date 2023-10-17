import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { Navbar2Component } from './components/navbar2/navbar2.component';
import { LandingComponent } from './pages/landing/landing.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { TradeHistoryComponent } from './pages/trade-history/trade-history.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FooterComponent } from './components/footer/footer.component';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
      ],
      declarations: [
        AppComponent,
        NavbarComponent,
        Navbar2Component,
        LandingComponent,
        HomeComponent,
        LoginComponent,
        SignupComponent,
        TradeHistoryComponent,
        FooterComponent,
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'invest-monkey'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('invest-monkey');
  });

  /****Routing Test****/

  // it('should navigate to /trade-history route', () => {
  //   const router = TestBed.inject(Router);
  //   const navigateSpy = spyOn(router, 'navigate');
  //   const fixture = TestBed.createComponent(AppComponent);
  //   const app = fixture.componentInstance;
  //   app.navigateToTradeHistory();
  //   expect(navigateSpy).toHaveBeenCalledWith(['/trade-history']);
  // });

});
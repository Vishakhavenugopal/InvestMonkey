import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeComponent } from './home.component';
import { Navbar2Component } from 'src/app/components/navbar2/navbar2.component';
import { MatIcon } from '@angular/material/icon';
import { RouterTestingModule } from '@angular/router/testing';
import { MatToolbar } from '@angular/material/toolbar';
import { PortfolioComponent } from '../portfolio/portfolio.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCard, MatCardModule } from '@angular/material/card';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatTableDataSource } from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { FooterComponent } from 'src/app/components/footer/footer.component';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FooterComponent,HomeComponent,Navbar2Component,MatIcon,MatToolbar,PortfolioComponent, MatCard,MatFormField,MatLabel,MatIcon,FooterComponent ],
      imports:[MatTableModule, RouterTestingModule, HttpClientTestingModule, MatSnackBarModule, MatCardModule],
      providers: [MatTableDataSource]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

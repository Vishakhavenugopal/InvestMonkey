import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoboAdvicerComponent } from './robo-advicer.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { Navbar2Component } from 'src/app/components/navbar2/navbar2.component';
import { MatCard } from '@angular/material/card';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { FooterComponent } from 'src/app/components/footer/footer.component';

describe('RoboAdvicerComponent', () => {
  let component: RoboAdvicerComponent;
  let fixture: ComponentFixture<RoboAdvicerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoboAdvicerComponent,Navbar2Component,MatCard,MatFormField,MatLabel,MatIcon,MatToolbar,FooterComponent ],
      imports:[HttpClientTestingModule,MatSnackBarModule, RouterTestingModule]

    })
    .compileComponents();

    fixture = TestBed.createComponent(RoboAdvicerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientBuyComponent } from './client-buy.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { Navbar2Component } from 'src/app/components/navbar2/navbar2.component';
import { MatCard } from '@angular/material/card';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { RouterTestingModule } from '@angular/router/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FooterComponent } from 'src/app/components/footer/footer.component';

describe('ClientBuyComponent', () => {
  let component: ClientBuyComponent;
  let fixture: ComponentFixture<ClientBuyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FooterComponent,ClientBuyComponent,Navbar2Component,MatCard,MatFormField,MatLabel,MatIcon,MatToolbar ],
      imports:[BrowserAnimationsModule,HttpClientTestingModule,MatDialogModule,MatSnackBarModule,FormsModule,MatInputModule,MatFormFieldModule,RouterTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientBuyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

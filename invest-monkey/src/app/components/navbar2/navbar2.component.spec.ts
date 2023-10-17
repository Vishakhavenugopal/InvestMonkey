import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Navbar2Component } from './navbar2.component';
import {MatToolbarModule} from '@angular/material/toolbar'; 
import {MatIconModule} from '@angular/material/icon';
import { RouterTestingModule } from '@angular/router/testing';

describe('Navbar2Component', () => {
  let component: Navbar2Component;
  let fixture: ComponentFixture<Navbar2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Navbar2Component ],
      imports: [MatToolbarModule, MatIconModule,RouterTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Navbar2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

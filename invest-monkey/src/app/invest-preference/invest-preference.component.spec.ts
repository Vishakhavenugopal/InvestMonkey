import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvestPreferenceComponent } from './invest-preference.component';

describe('InvestPreferenceComponent', () => {
  let component: InvestPreferenceComponent;
  let fixture: ComponentFixture<InvestPreferenceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InvestPreferenceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvestPreferenceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeHistoryComponent } from './trade-history.component';
import { TradeHistoryService } from './trade-history.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { Navbar2Component } from 'src/app/components/navbar2/navbar2.component';
import { MatCard } from '@angular/material/card';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { FooterComponent } from 'src/app/components/footer/footer.component';

describe('TradeHistoryComponent', () => {
  let component: TradeHistoryComponent;
  let fixture: ComponentFixture<TradeHistoryComponent>;

  // let mock_data : TradeHistory[] = [{

  // }]
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FooterComponent,TradeHistoryComponent,Navbar2Component,MatCard,MatFormField,MatLabel,MatIcon,MatToolbar ],
      imports:[HttpClientTestingModule, RouterTestingModule],
      providers: [TradeHistoryService],
    })
    .compileComponents();

    fixture = TestBed.createComponent(TradeHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  
  it('should render the table header', () => {
    const header = fixture.nativeElement.querySelector('thead');
    expect(header).toBeTruthy();
    expect(header.textContent).toContain('ID');
    expect(header.textContent).toContain('Instrument Name');
    expect(header.textContent).toContain('Quantity');
    expect(header.textContent).toContain('Price');
    expect(header.textContent).toContain('Direction');
  });

  // it('should display trade history data', () => {
  //   const mockTradeHistory = [mock_data];
  //   component.tradeHistory = mockTradeHistory;
  //   fixture.detectChanges();
  
  //   const rows = fixture.nativeElement.querySelectorAll('tbody tr');
  //   expect(rows.length).toBe(mockTradeHistory.length);
  // });

  // it('should fetch trade history from the service', () => {
  //   const tradeDataService = TestBed.inject(TradeHistoryService);
  //   spyOn(tradeDataService, 'getTradeHistory').and.returnValue(mock_data);
  
  //   component.ngOnInit();
  //   expect(tradeDataService.getTradeHistory).toHaveBeenCalled();
  //   expect(component.tradeHistory).toEqual(mock_data);
  // });
  
});

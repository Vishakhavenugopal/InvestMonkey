import { TestBed } from '@angular/core/testing';

import { TradeService } from './trade.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Order } from './models/order';

describe('TradeService', () => {
  let service: TradeService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(TradeService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  xit('Should get called', () => {
    expect(service).toBeTruthy();
  });

  xit('should retrieve and return the client trade history data', () => {
    expect(service).toBeTruthy();
  });
});

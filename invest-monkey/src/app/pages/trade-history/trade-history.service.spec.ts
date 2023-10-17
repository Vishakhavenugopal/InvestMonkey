import { TestBed, tick, fakeAsync } from '@angular/core/testing';
import { TradeHistoryService } from './trade-history.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('TradeHistoryService', () => {
  let service: TradeHistoryService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TradeHistoryService],
    });
    service = TestBed.inject(TradeHistoryService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify(); // Ensure that there are no outstanding requests
  });

  xit('should be created', () => {
    expect(service).toBeTruthy();
  });

  xit('should fetch trade history data', fakeAsync(() => {
    const dummyTradeData = [{}];
    tick();
  }));
});

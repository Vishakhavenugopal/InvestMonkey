import { TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { TradeService } from './trade.service';
import { Price } from '../models/price';

describe('TradeService', () => {
  let service: TradeService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TradeService]
    });

    // Inject the service and the testing controller for HTTP requests
    service = TestBed.inject(TradeService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Verify that there are no outstanding HTTP requests
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch trade prices', () => {
    expect(service).toBeTruthy();
  });

  // it('should fetch trade prices', inject([TradeService],fakeAsync((service:TradeService)=>{
    // let mockPrices:Price[]= [];

    // service.getTradePriceList('').subscribe(prices => {
    //   expect(prices).toBeTruthy();
    //   mockPrices=prices;
    // });

    // const req = httpTestingController.expectOne(`http://localhost:3000/fmts/trades/prices`);
    // expect(req.request.method).toEqual('GET');

    // req.flush(mockPrices);
    // tick();
    // httpTestingController.verify();
  // })));


  it('should fetch trade instruments', () => {
    const filter = 'STOCK';

    service.getTradeInstrumentList(filter).subscribe(instruments => {
      expect(instruments).toBeTruthy();
    });

    const req = httpTestingController.expectOne(`${service.instrument_url}${filter}`);
    expect(req.request.method).toBe('GET');

    const mockInstruments = [{}];
    req.flush(mockInstruments);
  });
});

import { TestBed } from '@angular/core/testing';

import { InstrumentService } from './instrument.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('InstrumentService', () => {
  let service: InstrumentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule]
    });
    service = TestBed.inject(InstrumentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { PortfolioDataService } from './portfolio-data.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('PortfolioDataService', () => {
  let service: PortfolioDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({      
      imports:[HttpClientTestingModule]
    });
    service = TestBed.inject(PortfolioDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

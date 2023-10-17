import { TestBed } from '@angular/core/testing';

import { InvestmentPrefsService } from './investment-prefs.service';

describe('InvestmentPrefsService', () => {
  let service: InvestmentPrefsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InvestmentPrefsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { ClientInvestmentPreferences } from './client-investment-preferences';

describe('ClientInvestmentPreferences', () => {
  it('should create an instance', () => {
    expect(new ClientInvestmentPreferences('', false, '', '', '', 0)).toBeTruthy();
  });
});

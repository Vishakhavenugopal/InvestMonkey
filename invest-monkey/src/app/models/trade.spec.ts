import { Order } from './order';
import { Trade } from './trade';

describe('Trade', () => {
  it('should create an instance', () => {
    const order=new Order('',0,0,'','','');
    expect(new Trade('',0,0,'',order,0,'','')).toBeTruthy();
  });
});

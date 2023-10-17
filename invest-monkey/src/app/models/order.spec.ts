import { Order } from './order';

describe('Order', () => {
  it('should create an instance', () => {
    expect(new Order('',0,0,'','','')).toBeTruthy();
  });
});

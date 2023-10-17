import { Stock } from "./stock";

describe('Stock', () => {
  it('should create an instance', () => {
    const stock: Stock = {
      instrument_id: '',
      external_type: '',
      external_id: '',
      category_id: '',
      instrumentDescription: '',
      max_quantity: 0,
      min_quantity: 0,
    };

    expect(stock).toBeTruthy();
  });
});

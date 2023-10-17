import { Price } from "./price";
import { Instrument } from "./instrument";

describe('Price', () => {
  it('should create an instance', () => {
    const instrument: Instrument = {
      instrumentId: '',
      externalType: '',
      externalId: '',
      categoryId: '',
      instrumentDescription: '',
      maxQuantity: 0,
      minQuantity: 0,
    };

    const price: Price = {
      askPrice: 10.0,
      bidPrice: 9.0,
      priceTimestamp: new Date(),
      instrument: instrument,
    };

    expect(price).toBeTruthy();
  });
});

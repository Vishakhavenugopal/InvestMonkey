import { GovtBond } from "./govtbond";
import { Instrument } from "./instrument";

describe('GovtBond', () => {
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

    const govtBond: GovtBond = {
      askPrice: 0,
      bidPrice: 0,
      priceTimestamp: new Date(),
      instrument: instrument,
    };

    expect(govtBond).toBeTruthy();
  });
});

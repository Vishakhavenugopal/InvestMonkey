import { Instrument } from "./instrument";


describe('Instrument', () => {
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

    expect(instrument).toBeTruthy();
  });
});

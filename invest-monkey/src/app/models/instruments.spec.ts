import { Instruments } from './instruments';

describe('Instruments', () => {
  it('should create an instance', () => {
    const instrument = {
      instrumentId: '123',
      externalIdType: 'Type',
      externalId: '456',
      categoryId: 'Category',
      instrumentDescription: 'Description',
      maxQuantity: 100,
      minQuantity: 10,
    };
    const instruments = new Instruments(10, 20, '2023-09-04', instrument);
    expect(instruments).toBeTruthy();
  });
});

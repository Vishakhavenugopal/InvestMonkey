export class Instruments {
    askPrice: number;
    bidPrice: number;
    priceTimestamp: string;
    instrument: {
      instrumentId: string;
      externalIdType: string;
      externalId: string;
      categoryId: string;
      instrumentDescription: string;
      maxQuantity: number;
      minQuantity: number;
    };
  
    constructor(
      askPrice: number,
      bidPrice: number,
      priceTimestamp: string,
      instrument: {
        instrumentId: string;
        externalIdType: string;
        externalId: string;
        categoryId: string;
        instrumentDescription: string;
        maxQuantity: number;
        minQuantity: number;
      }
    ) {
      this.askPrice = askPrice;
      this.bidPrice = bidPrice;
      this.priceTimestamp = priceTimestamp;
      this.instrument = instrument;
    }
  }
  
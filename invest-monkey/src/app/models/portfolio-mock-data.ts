export interface PortfolioMockData{
    cash:number;
    bondDetails:{
        issuer:string;
        type:string;
        expectedDate:Date;
        faceValue:number;
        capitalValue:number;
    }[];
    mutualFunds:{
        name:string;
        category:string;
        sipamt:number;
    }[];
    stocks:{
        ticker:string;
        buyPrice:number;
        actualPrice:number;
        shares:number;
    }[];
}
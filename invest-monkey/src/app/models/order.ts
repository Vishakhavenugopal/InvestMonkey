export class Order {
    constructor(
        public instrumentI:string,
        public quantity:number,
        public targetPrice:number,
        public direction:string,
        public clientId:string,
        public orderId:string
        ){}

        
}

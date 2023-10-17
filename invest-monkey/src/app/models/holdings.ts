export class Holdings {
    constructor(
        public instrumentId:string,
        public instrumentDescription:string,
        public categoryId:string,
        public askNumber:number,
        public quantity:number,
        public totalPrice:number
    ){}
}

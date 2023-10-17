export class Stock{
    constructor(
        public instrument_id:string,
        public external_type:string,
        public external_id:string,
        public category_id:string,
        public instrumentDescription:string,
        public max_quantity:number,
        public min_quantity:number
    ){
    }
}
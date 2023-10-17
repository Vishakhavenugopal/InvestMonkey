export class Client {
    constructor(public firstName:string,
        public lastName:string,
        public phone:string,
        public clientId:string,
        public email:string,
        public dateOfBirth:string,
        public country:string,
        public postalCode: string,
        public password:string,
        public clientIdentification:{
            clientIdentificationType:string,
            clientIdentificationValue:string
        }){

    }

}

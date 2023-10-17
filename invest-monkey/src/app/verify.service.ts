import { Injectable } from '@angular/core';
import { Client } from './models/client';
import { Observable, catchError, map, of } from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { loginRequest } from './models/loginRequest';
@Injectable({
  providedIn: 'root'
})
export class VerifyService {
  registeredClients : Client[]=[{
    clientId:'ABC123',
    email:'abc@gmail.com',
    dateOfBirth:'27082001',
    country:'INDIA',
    postalCode:'570011',
    password:'jd123',
    firstName:'Jitin',
    lastName:'Dodeja',
    phone:'9663321676',
    clientIdentification:{
      clientIdentificationType:'Aadhar',
      clientIdentificationValue:'123456789'
    }
  }];

  public readonly url:string="http://localhost:3200/client";
  currentClient:Client={
    clientId:'',
    email:'',
    dateOfBirth:'',
    country:'',
    postalCode:'',
    password:'',
    firstName:'',
    lastName:'',
    phone:'',
    clientIdentification:{
      clientIdentificationType:'',
      clientIdentificationValue:''
    }};
    constructor(private http:HttpClient){

    }

    private isLoggedIn = false;

    // Check if the user is logged in
    autoLogin(){
      this.isLoggedIn=true;
      //return this.isLoggedIn
    }
    isUserLoggedIn(): boolean {
      return this.isLoggedIn;
    }

    verifyUser(email: string, password: string): Observable<string> {
      let login: loginRequest = {
        email: email,
        password: password
      };
    const loginUrl:string = "http://localhost:3200/client";

      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    
      return this.http.post<Client>(this.url+"/login", login, { headers: headers }).pipe(
        map((data) => {
          this.currentClient = data;
          console.log(data);
          console.log("CURRENT CLIENT => ", this.currentClient);
    
          if (this.currentClient.clientId != "") {
            this.isLoggedIn=true;
            return "USER LOGGED IN";
            
          } else {
            return "INVALID CLIENT";
          }
        }),
        catchError((error) => {
          console.error("Error:", error);
          return of("Error occurred during login"); // You can handle errors appropriately
        })
      );
    }
    
  checkUniqueEmail(email:string):boolean{
    return this.registeredClients.find(client=>client.email===email)?false:true;
  }
  registerNewUser(client: Client): void {
    console.log("Form client => ", client);
  
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    console.log("URL => ", this.url);
  
    this.http.post<Client>(this.url + "/register", client, { headers: headers }).pipe(
      map((data) => {
        console.log("API HIT");
        this.currentClient = data;
        console.log(data);
        console.log("CURRENT CLIENT => ", this.currentClient);
      }),
      catchError((error) => {
        console.error("Error:", error);
        return of("Error occurred during registration"); // You can handle errors appropriately
      })
    ).subscribe(
      () => {
        // The subscription function here is empty because we are just interested in making the HTTP request
      },
      (error) => {
        // Handle any errors that might occur during the HTTP request
        console.error("Error:", error);
      }
    );
  }
  
  getCurrentClient():Client{
    return this.currentClient;
  }
  getCurrentClientId():Observable<string>{
    return of(this.currentClient.clientId);
  }

}

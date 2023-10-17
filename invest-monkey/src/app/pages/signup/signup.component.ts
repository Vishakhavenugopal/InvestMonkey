import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractControl, ValidationErrors } from '@angular/forms';
import { Client } from 'src/app/models/client';
import { VerifyService } from 'src/app/verify.service';
import {Router} from '@angular/router';
import { BalanceService } from 'src/app/balance.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent
{
  client:Client={
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
    }
  };
  clientForm!: FormGroup; // Initialize here

  

   countries: any[] = [
    { value: 'IND', name: 'India' },
    { value: 'US', name: 'United States' },
    { value: 'CA', name: 'Canada' },
    { value: 'UK', name: 'United Kingdom' },
    
  ];
  selectedCountry: string = '';
  constructor(private fb: FormBuilder, private verifyService:VerifyService,private router:Router,private balanceService:BalanceService) {
    this.clientForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      lname: ['',[Validators.required]],
      fname: ['',[Validators.required]],
      dateOfBirth: ['',[Validators.required,Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]],
      password: ['',[Validators.required]],
      country: ['',[Validators.required]],
      postalCode: ['',[Validators.required, Validators.pattern('[0-9]{6}')]],
      documentType: [''],
      phone:['',[Validators.required]],
      aadhar: [''],
      ssn: ['']
    });
    this.clientForm.get('country')?.valueChanges.subscribe(value => {
      this.selectedCountry = value;
    });
  }
    submitForm() {
      console.log('here');   
      if (this.clientForm.valid) {
        console.log('Form submitted');
        this.client.firstName= this.clientForm.get('fname')?.value;
        this.client.lastName=this.clientForm.get('lname')?.value;
        this.client.country=this.clientForm.get('country')?.value;
        this.client.postalCode=this.clientForm.get('postalCode')?.value;
        this.client.dateOfBirth=this.clientForm.get('dateOfBirth')?.value;
        this.client.email=this.clientForm.get('email')?.value;
        this.client.password=this.clientForm.get('password')?.value;
        this.client.phone=this.clientForm.get('phone')?.value;
        this.client.clientIdentification.clientIdentificationType=this.client.country==='IND'?'Aadhar':'SSN';
        if(this.client.clientIdentification.clientIdentificationType==='Aadhar'){
          this.client.clientIdentification.clientIdentificationValue=this.clientForm.get('aadhar')?.value;
        }
        else{
          this.client.clientIdentification.clientIdentificationValue=this.clientForm.get('ssn')?.value;
        }
        if(this.verifyService.checkUniqueEmail(this.client.email)){
          this.verifyService.autoLogin();
          this.client.clientId=this.generateClientId();
          this.verifyService.registerNewUser(this.client);
          this.balanceService.pushNewBalance(this.client.clientId);
          this.router.navigate(['/investPrefernce']);
        
        }
        else{
          alert('USER ALREADY EXISTS!!!');
        }
        
      }
}
    generateClientId():string{
      const characters='ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
      let clientId='';
      for(let i=0;i<6;i++){
        clientId+=characters.charAt(Math.floor(Math.random()*characters.length));
      }
      return clientId;
    }

}

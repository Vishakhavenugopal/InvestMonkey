import { getLocaleMonthNames } from '@angular/common';
import { Component } from '@angular/core';
import { VerifyService } from 'src/app/verify.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Router} from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  clientForm!: FormGroup;

  constructor(private verifyService:VerifyService,private fb: FormBuilder, private router:Router){
    this.clientForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['',[Validators.required]]
    });
  }
  userEmail:string='';
  userPass:string='';
  message?:string;

 
  verifyUser(): void {
    console.log(this.userEmail);
    console.log("USER IS IN");
    
    this.verifyService.verifyUser(this.userEmail, this.userPass).subscribe(data => {
      this.message = data;
      console.log("Login error: ",this.message);
      
      if (this.message === "USER LOGGED IN") {
        console.log("HEREEEEE");
        this.router.navigate(['/home']);
      }
    }, error => {
      // Handle any errors that might occur during the subscription
      console.error("Error:", error);
    });
  }
  
}
